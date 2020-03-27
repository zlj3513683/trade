package com.posppay.newpay.modules.xposp.router.channel.pingan.handle;

import cn.hutool.core.date.DateUtil;
import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.modules.model.ChannelType;
import com.posppay.newpay.modules.model.OrgChannelType;
import com.posppay.newpay.modules.model.ResultStatus;
import com.posppay.newpay.modules.sdk.pingan.config.ParamConfig;
import com.posppay.newpay.modules.sdk.pingan.constant.PinganConstant;
import com.posppay.newpay.modules.sdk.pingan.model.ErrCodeType;
import com.posppay.newpay.modules.sdk.pingan.model.PayStatus;
import com.posppay.newpay.modules.sdk.pingan.model.PayType;
import com.posppay.newpay.modules.sdk.pingan.model.req.TradeQueryRequest;
import com.posppay.newpay.modules.sdk.pingan.model.req.TradeRefundQueryRequest;
import com.posppay.newpay.modules.sdk.pingan.model.resp.PassiveTradeResponse;
import com.posppay.newpay.modules.sdk.pingan.model.resp.TradeQueryResponse;
import com.posppay.newpay.modules.sdk.pingan.model.resp.TradeRefundQueryResponse;
import com.posppay.newpay.modules.sdk.pingan.service.BaseScanPaymentService;
import com.posppay.newpay.modules.xposp.common.AppExCode;
import com.posppay.newpay.modules.xposp.common.Const;
import com.posppay.newpay.modules.xposp.dao.service.QrChnFlowPaTmpService;
import com.posppay.newpay.modules.xposp.entity.*;
import com.posppay.newpay.modules.xposp.innertrans.InnerRouteService;
import com.posppay.newpay.modules.xposp.innertrans.InnerTransService;
import com.posppay.newpay.modules.xposp.router.TransferFailedException;
import com.posppay.newpay.modules.xposp.router.channel.ChannelRequestInfo;
import com.posppay.newpay.modules.xposp.router.channel.ChannelResponseInfo;
import com.posppay.newpay.modules.xposp.router.channel.cup.model.PayChannelPath;
import com.posppay.newpay.modules.xposp.router.channel.cup.model.req.ScanQuerySdkRequest;
import com.posppay.newpay.modules.xposp.router.channel.cup.model.resp.RefundSdkResponse;
import com.posppay.newpay.modules.xposp.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能：平安扫码退货查询与sdk交互
 *
 * @author zengjianwens
 */
@Service("pinganScanRefundQueryProcess")
@Slf4j
public class ScanRefundQueryProcess {
    @Resource
    private InnerTransService innerTransService;
    @Resource
    private BaseScanPaymentService baseScanPaymentService;
    @Autowired
    private ParamConfig paramConfig;
    @Resource
    private QrChnFlowPaTmpService qrChnFlowPaTmpService;
    @Resource
    private InnerRouteService innerRouteService;


    /**
     * @param paMercinfo
     * @param request
     * @return
     * @throws TransferFailedException
     * @throws UnsupportedEncodingException
     * @throws
     */
    public ChannelResponseInfo process(PaMercinfo paMercinfo, ChannelRequestInfo request, QrChnFlowPa originChnFlow)
            throws TransferFailedException, AppBizException {
        log.info("平安扫码退款查询与sdk交互开始.................");
        if(paramConfig.isTest()){
            log.info("此为测试专用");
            paMercinfo.setPaMercId(paramConfig.getMercId());
            request.setSignInstNo(paramConfig.getOrgNo());
            request.setScrekey(paramConfig.getHMacKey());
        }
        TradeRefundQueryRequest entity = packUp(paMercinfo, request);
        QrChnFlowPa qrChnFlowPa = addChnFlow(request, paMercinfo);
        String secretKey = request.getScrekey();
        TradeRefundQueryResponse response = baseScanPaymentService.tradeRefundQuery(entity,secretKey);
        if (null == response) {
            //没有返回状态未知
            qrChnFlowPa.setRespCode(AppExCode.NETWORK_CONNECTION_FAILED);
            innerRouteService.updateChnFlow(qrChnFlowPa);
            //平安没返回则抛通讯异常
            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "网络读写异常请重试");
        }
        if (!PinganConstant.COMMUNICATE_SUCCESS.equals(response.getStatus())) {
//            0表示成功，非0表示失败此字段是通信标识
            qrChnFlowPa.setRespCode(AppExCode.NETWORK_CONNECTION_FAILED);
            innerRouteService.updateChnFlow(qrChnFlowPa);
            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "平安通讯异常");
        }

        if (!PinganConstant.BUS_SUCCESS.equals(response.getResultCode())) {
            //0代表查询成功
            ErrCodeType errCodeType = ErrCodeType.fromCode(response.getErrorCode());
            qrChnFlowPa.setRespCode(PinganConstant.PA_FAILED_CODE);
            //如果needQuery为N则认为失败
            throw new TransferFailedException(errCodeType.getTransCode(), errCodeType.getChnName());
        }
        PayStatus payStatus = PayStatus.fromCode(response.getRefundStatus());
        QrChnFlowPaTmp qrChnFlowTmp = qrChnFlowPaTmpService.findBypayNo(originChnFlow.getOutTradeNo(), originChnFlow.getOrgNo());

        switch (payStatus) {
            case REFUNDFAIL:
                request.setStatus(Const.TransStatus.FAILED_STR);
                originChnFlow.setRespCode(ErrCodeType.PAYFAIL.getTransCode());
                originChnFlow.setRemark(ErrCodeType.PAYFAIL.getChnName());
                if (null != qrChnFlowTmp) {
                    try {
                        qrChnFlowPaTmpService.deleteByPayNo(originChnFlow.getOutTradeNo(), originChnFlow.getOrgNo());
                    } catch (Exception e) {
                        //如果删除失败则记录回滚原始订单信息
                        log.error("系统异常",e);
                    }
                }
                innerRouteService.updateChnFlow(originChnFlow);
                throw new AppBizException(originChnFlow.getRespCode(),originChnFlow.getRemark());
                //已经名确失败 记录为失败
            case SUCCESS:
                request.setStatus(Const.TransStatus.SUCCESS_STR);
                originChnFlow.setRespCode(Const.ISO_RESPCODE_00);
                originChnFlow.setRemark("交易成功");
                if (null != qrChnFlowTmp) {
                    try {
                        qrChnFlowPaTmpService.deleteByPayNo(originChnFlow.getOutTradeNo(), originChnFlow.getOrgNo());
                    } catch (Exception e) {
                        //如果删除失败则记录回滚原始订单信息
                        log.error("系统异常",e);
                    }
                }
                updateChnFlow(originChnFlow,response);
                break;
            case PROCESSING:
                request.setStatus(Const.TransStatus.UNKNOWN_STR);
                originChnFlow.setRespCode(ErrCodeType.REFUNDPROCESSING.getTransCode());
                originChnFlow.setRemark(ErrCodeType.REFUNDPROCESSING.getChnName());
                innerRouteService.updateChnFlow(originChnFlow);
                throw new AppBizException(originChnFlow.getRespCode(),originChnFlow.getRemark());

            default:
                request.setStatus(Const.TransStatus.FAILED_STR);
                originChnFlow.setRespCode(ErrCodeType.PAYFAIL.getTransCode());
                originChnFlow.setRemark("交易失败");
                if (null != qrChnFlowTmp) {
                    try {
                        qrChnFlowPaTmpService.deleteByPayNo(originChnFlow.getOutTradeNo(), originChnFlow.getOrgNo());
                    } catch (Exception e) {
                        //如果删除失败则记录回滚原始订单信息
                        log.error("系统异常",e);
                    }
                }
                innerRouteService.updateChnFlow(originChnFlow);
                throw new AppBizException(originChnFlow.getRespCode(),originChnFlow.getRemark());
        }
        //接收报文
        ChannelResponseInfo cupsResponseInfo = new ChannelResponseInfo();
        cupsResponseInfo.setTransCode(originChnFlow.getRespCode());
        cupsResponseInfo.setPayNo(request.getOriginalPayNo());
//        cupsResponseInfo.setSuperTransType(PayType.fromEngName(response.getTradeType()).getCode());

        log.info("平安扫码退款查询与sdk交互开始...............");
        return cupsResponseInfo;
    }

    private TradeRefundQueryRequest packUp(PaMercinfo paMercinfo, ChannelRequestInfo request) throws
            TransferFailedException {

        //上送 平安的商户号以及原始凭证号
        TradeRefundQueryRequest sdkRequest = new TradeRefundQueryRequest();
        sdkRequest.setMerchantId(paMercinfo.getPaMercId())
                .setOutRefundNo(request.getOutRefundNo())
                .setSignAgentNo(request.getSignInstNo());
        return sdkRequest;

    }


    /**
     * 新增渠道流水信息
     *
     * @param requestInfo
     * @param paMercinfo
     * @return
     * @throws AppBizException
     */
    private QrChnFlowPa addChnFlow(ChannelRequestInfo requestInfo, PaMercinfo paMercinfo) throws AppBizException {
        QrChnFlowPa qrChnFlowPa = new QrChnFlowPa();
        qrChnFlowPa.setOrgNo(requestInfo.getSignInstNo())
                .setMrchNo(paMercinfo.getMercId())
                .setMrchName(requestInfo.getMrchName())
                .setCurrency(requestInfo.getCurrency())
//                .setAmount(requestInfo.getAmount().doubleValue())
                .setDeviceip(requestInfo.getDeviceIp())
                .setPaMercId(paMercinfo.getPaMercId())
                .setOprid(requestInfo.getOperator())
                .setTrmnlType("")
                .setOutTradeNo(requestInfo.getPayNo())
                .setTxntime(requestInfo.getDeviceTime())
                .setSysTime(DateUtil.format(new Date(), "yyyyMMddHHmmsss"))
//                .setAmount(requestInfo.getAmount().doubleValue())
//                .setTotalAmount(requestInfo.getAmount().doubleValue())
                .setAuthCode(requestInfo.getAuthCode())
                .setGoodsTag(requestInfo.getGoodsTag())
                .setGoodsDetail(requestInfo.getGoodsDetail())
                .setAttach(requestInfo.getAttach())
                .setNeedReceipt(requestInfo.getNeedReceipt());
        qrChnFlowPa = innerRouteService.addChnFlow(qrChnFlowPa);
        return qrChnFlowPa;
    }

    /**
     * 功能：更新渠道流水
     *
     * @param chnFlowPa
     * @param response
     * @return
     */
    private QrChnFlowPa updateChnFlow(QrChnFlowPa chnFlowPa, TradeRefundQueryResponse response) {
        chnFlowPa.setPayChannel(OrgChannelType.PINGAN_CHANNEL.getOrgNo())
                .setOutTransactionId(response.getTransactionId())
//                .setInvoiceAmount(StringUtils.bcdSimp2decimal4yuan(response.getInvoiceAmount()).doubleValue())
                .setTimeEnd(DateUtil.parse(response.getRefundTime(), "yyyyMMddHHmmss"))
//                .setSubTransType(PayType.fromEngName(response.getTradeType()).getCode())
                //TODO  现金卷金额待定
//                .setCouponFee(response.getCouponFee().doubleValue())
//                .setBankType(response.get)
                .setBuyerUserId("")
                .setBuyerLogonId("")
                .setAppid("")
                .setTxnCnl(ChannelType.INTELPOS.getCode())
                .setRefundChannel(response.getRefundChannel())
                .setRefundFee(new BigDecimal(response.getRefundFee()));
        innerRouteService.updateChnFlow(chnFlowPa);
        return chnFlowPa;

    }


}
