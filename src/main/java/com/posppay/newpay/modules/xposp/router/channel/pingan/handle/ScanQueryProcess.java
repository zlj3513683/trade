package com.posppay.newpay.modules.xposp.router.channel.pingan.handle;

import cn.hutool.core.date.DateUtil;
import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.modules.model.ChannelType;
import com.posppay.newpay.modules.model.OrgChannelType;
import com.posppay.newpay.modules.sdk.pingan.config.ParamConfig;
import com.posppay.newpay.modules.sdk.pingan.constant.PinganConstant;
import com.posppay.newpay.modules.sdk.pingan.model.ErrCodeType;
import com.posppay.newpay.modules.sdk.pingan.model.PayStatus;
import com.posppay.newpay.modules.sdk.pingan.model.PayType;
import com.posppay.newpay.modules.sdk.pingan.model.req.TradeQueryRequest;
import com.posppay.newpay.modules.sdk.pingan.model.resp.TradeQueryResponse;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 功能：平安扫码消费查询与sdk交互实现
 * 此服务为查询交易不产生新的流水只更新原始流水
 *
 * @author zengjianwens
 */
@Service("pinganScanQueryProcess")
@Slf4j
public class ScanQueryProcess {
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
        log.info("平安扫码消费查询与sdk交互开始.................");
        if (paramConfig.isTest()) {
            log.info("此为测试专用");
            paMercinfo.setPaMercId(paramConfig.getMercId());
            request.setSignInstNo(paramConfig.getOrgNo());
            request.setScrekey(paramConfig.getHMacKey());
        }
        TradeQueryRequest entity = packUp(paMercinfo, request);
        String secretKey = request.getScrekey();
        TradeQueryResponse response = null;
        if (!paramConfig.isBaffle()) {        //判断是否启用挡板
            response = baseScanPaymentService.tradeResultQuery(entity, secretKey);
        }
        if (null == response) {
            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "网络读写异常请重试");
        }
        if (!PinganConstant.COMMUNICATE_SUCCESS.equals(response.getStatus())) {
            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "平安通讯异常");
        }

        if (!PinganConstant.BUS_SUCCESS.equals(response.getResultCode())) {
            //0代表查询成功
            ErrCodeType errCodeType = ErrCodeType.fromCode(response.getErrorCode());
//            qrChnFlowPa.setRespCode(CupConstant.PA_FAILED_CODE);
            //如果needQuery为N则认为失败
            throw new TransferFailedException(errCodeType.getTransCode(), errCodeType.getChnName());
        }
        PayStatus payStatus = PayStatus.fromCode(response.getTradeState());
        QrChnFlowPaTmp qrChnFlowTmp = qrChnFlowPaTmpService.findBypayNo(originChnFlow.getOutTradeNo(), originChnFlow.getOrgNo());
        switch (payStatus) {
            case REFUND:
                originChnFlow.setRespCode(Const.ISO_RESPCODE_00);
                originChnFlow.setRemark("已退货");
                if (null != qrChnFlowTmp) {
                    try {
                        qrChnFlowPaTmpService.deleteByPayNo(originChnFlow.getOutTradeNo(), originChnFlow.getOrgNo());
                    } catch (Exception e) {
                        //如果删除失败则记录回滚原始订单信息
                      log.error("系统异常",e);
                    }
                }
                innerRouteService.updateChnFlow(originChnFlow);
                break;
            case FAIL:
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
                throw new TransferFailedException(originChnFlow.getRespCode(), originChnFlow.getRemark());
                //已经名确失败 记录为失败
            case SUCCESS:
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
                updateChnFlow(originChnFlow, response);
                break;
            case USEPAY:
                originChnFlow.setRespCode(ErrCodeType.NOTPAY.getCode());
                originChnFlow.setRemark(ErrCodeType.NOTPAY.getChnName());
                innerRouteService.updateChnFlow(originChnFlow);
                throw new TransferFailedException(originChnFlow.getRespCode(), originChnFlow.getRemark());
            case NOTPAY:
                //未支付
                originChnFlow.setRespCode(ErrCodeType.NOTPAY.getCode());
                originChnFlow.setRemark(ErrCodeType.NOTPAY.getChnName());
                innerRouteService.updateChnFlow(originChnFlow);
                throw new TransferFailedException(originChnFlow.getRespCode(), originChnFlow.getRemark());
            case CLOSED:
                originChnFlow.setRespCode(ErrCodeType.ORDERCLOSED.getCode());
                originChnFlow.setRemark(ErrCodeType.ORDERCLOSED.getChnName());
                if (null != qrChnFlowTmp) {
                    try {
                        qrChnFlowPaTmpService.deleteByPayNo(originChnFlow.getOutTradeNo(), originChnFlow.getOrgNo());
                    } catch (Exception e) {
                        //如果删除失败则记录回滚原始订单信息
                        log.error("系统异常",e);
                    }
                }
                innerRouteService.updateChnFlow(originChnFlow);
                throw new AppBizException(originChnFlow.getRespCode(), originChnFlow.getRemark());
            default:
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
                throw new TransferFailedException(originChnFlow.getRespCode(), originChnFlow.getRemark());
        }
        //接收报文
        ChannelResponseInfo cupsResponseInfo = new ChannelResponseInfo();
        cupsResponseInfo.setTransCode(originChnFlow.getRespCode());
        cupsResponseInfo.setPayNo(request.getOriginalPayNo());
        cupsResponseInfo.setSuperTransType(PayType.fromEngName(response.getTradeType()).getCode());
        cupsResponseInfo.setBody(originChnFlow.getBodydetail());
        cupsResponseInfo.setGoodTag(originChnFlow.getGoodsTag());
        cupsResponseInfo.setGoodDetail(originChnFlow.getGoodsDetail());
        cupsResponseInfo.setBankType(response.getBankType());

        log.info("平安扫码消费查询与sdk交互开始...............");
        return cupsResponseInfo;
    }

    private TradeQueryRequest packUp(PaMercinfo paMercinfo, ChannelRequestInfo request) throws
            TransferFailedException {

        //上送 平安的商户号以及原始凭证号
        TradeQueryRequest sdkRequest = new TradeQueryRequest();
        sdkRequest.setMerchantId(paMercinfo.getPaMercId())
                .setOutTradeNo(request.getOriginalPayNo())
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
    private QrChnFlowPa updateChnFlow(QrChnFlowPa chnFlowPa, TradeQueryResponse response) {
        chnFlowPa.setPayChannel(OrgChannelType.PINGAN_CHANNEL.getOrgNo())
                .setOutTransactionId(response.getTransactionId())
                .setSubIsSubscribe(response.getSubSubscribe())
                .setSubAppid(response.getSubAppId())
//                .setInvoiceAmount(StringUtils.bcdSimp2decimal4yuan(response.getInvoiceAmount()).doubleValue())
                .setTimeEnd(DateUtil.parse(response.getTimeEnd(), "yyyyMMddHHmmss"))
                .setSubTransType(PayType.fromEngName(response.getTradeType()).getCode())
                .setIsSubscribe(response.getSubscribe())
                //TODO  现金卷金额待定
//                .setCouponFee(response.getCouponFee().doubleValue())
                .setPromotionDetail(response.getPromotionDetail())
                .setBankType(response.getBankType())
                .setBuyerUserId("")
                .setBuyerLogonId("")
                .setAppid("")
                .setOpenid(response.getOpenId())
                .setSubOpenid(response.getSubOpenId())
                .setTxnCnl(ChannelType.INTELPOS.getCode());
        innerRouteService.updateChnFlow(chnFlowPa);
        return chnFlowPa;

    }
}

