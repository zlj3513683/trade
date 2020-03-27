package com.posppay.newpay.modules.xposp.router.channel.cup.handle;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.modules.model.ChannelType;
import com.posppay.newpay.modules.model.OrgChannelType;
import com.posppay.newpay.modules.model.ResultModel;
import com.posppay.newpay.modules.sdk.cup.config.CupConfig;
import com.posppay.newpay.modules.sdk.cup.constant.CupConstant;
import com.posppay.newpay.modules.sdk.cup.model.req.TradeRefundQueryRequest;
import com.posppay.newpay.modules.sdk.cup.model.req.TradeRefundRequest;
import com.posppay.newpay.modules.sdk.cup.model.resp.TradeRefundQueryResponse;
import com.posppay.newpay.modules.sdk.cup.model.resp.TradeRefundResponse;
import com.posppay.newpay.modules.sdk.cup.service.BaseScanPaymentService;
import com.posppay.newpay.modules.sdk.cup.model.ErrCodeType;
import com.posppay.newpay.modules.sdk.cup.model.PayStatus;
import com.posppay.newpay.modules.xposp.common.AppExCode;
import com.posppay.newpay.modules.xposp.common.Const;
import com.posppay.newpay.modules.xposp.dao.service.QrChnFlowPaTmpService;
import com.posppay.newpay.modules.xposp.entity.*;
import com.posppay.newpay.modules.xposp.innertrans.InnerRouteService;
import com.posppay.newpay.modules.xposp.router.TransferFailedException;
import com.posppay.newpay.modules.xposp.router.channel.ChannelRequestInfo;
import com.posppay.newpay.modules.xposp.router.channel.ChannelResponseInfo;
import com.posppay.newpay.modules.xposp.utils.StringUtils;
import com.posppay.newpay.modules.xposp.innertrans.InnerTransService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能：银联退货与第三方通讯接口实现
 *
 * @author zengjianwens
 */
@Service("cupRefundProcess")
@Slf4j
public class RefundProcess {
    @Resource
    private InnerTransService innerTransService;
    @Resource(name = "cupBaseScanPaymentService")
    private BaseScanPaymentService baseScanPaymentService;
    @Resource
    private QrChnFlowPaTmpService qrChnFlowPaTmpService;
    @Autowired
    private CupConfig cupConfig;
    @Resource
    private InnerRouteService innerRouteService;


    /**
     * @param cupMerchant
     * @param request
     * @return
     * @throws TransferFailedException
     * @throws UnsupportedEncodingException
     * @throws
     */
    public ChannelResponseInfo process(CupMerchant cupMerchant, ChannelRequestInfo request, QrChnFlowPa oriChnFlow)
            throws TransferFailedException, AppBizException {
        log.info("银联B扫C扫码退款与sdk交互开始了.................");
        if (cupConfig.isTest()) {
            log.info("此为测试专用");
            cupMerchant.setCupMerchantId(cupConfig.getMercId());
        }
        request.setSignInstNo(cupConfig.getOrgNo());
        TradeRefundRequest entity = packUp(cupMerchant, request);
        QrChnFlowPa qrChnFlowPa = addChnFlow(request, cupMerchant);
        //秘钥信息获取
        String secrekey = cupConfig.getKey();
        TradeRefundResponse response = null;
        response = baseScanPaymentService.tradeRefund(entity, secrekey);

//        if (null == response) {
//            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "网络读写异常请重试");
//        }
        if (null == response) {
            //没有返回状态未知
            qrChnFlowPa.setRespCode(AppExCode.NETWORK_CONNECTION_FAILED);
            innerRouteService.updateChnFlow(qrChnFlowPa);
            //平安没返回则抛通讯异常
            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "网络读写异常请重试");
        }
        if (!CupConstant.COMMUNICATE_SUCCESS.equals(response.getStatus())) {
//            0表示成功，非0表示失败此字段是通信标识
            qrChnFlowPa.setRespCode(AppExCode.NETWORK_CONNECTION_FAILED);
            innerRouteService.updateChnFlow(qrChnFlowPa);
            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "通讯异常");
        }
        if (!CupConstant.BUS_SUCCESS.equals(response.getResultCode())) {
            ErrCodeType errCodeType = ErrCodeType.fromCode(response.getErrorCode());
            qrChnFlowPa.setRespCode(CupConstant.CUP_FAILED_CODE);
            throw new TransferFailedException(errCodeType.getTransCode(), errCodeType.getChnName());
        }
        log.info("需要发起查询");
        ChannelResponseInfo responseInfo = new ChannelResponseInfo();
        request.setTransactionId(response.getTransactionId());
//        responseInfo.setTransationId(response.getTransactionId());
//        responseInfo.setOutTranstionId(response.getOutTransactionId());
        ResultModel resultModel = jobTime(cupMerchant, request, secrekey, qrChnFlowPa);
        request.setStatus(resultModel.getStatus());
        if ("3".equals(resultModel.getStatus())) {
            throw new TransferFailedException(resultModel.getErrcode(), resultModel.getErrMsg());
        } else {
            updateChnFlow(qrChnFlowPa);
            if ("2".equals(resultModel.getStatus())) {
                throw new TransferFailedException(resultModel.getErrcode(), resultModel.getErrMsg());
            } else {
                qrChnFlowPa.setRespCode(Const.ISO_RESPCODE_00);
                qrChnFlowPa.setRemark("交易成功");
                //更新渠道流水
                updateChnFlow(qrChnFlowPa, response);
                //接收报文

                responseInfo.setTransCode(response.getErrorCode());
                responseInfo.setResult(qrChnFlowPa.getRespCode());
                responseInfo.setPayNo(request.getPayNo());
                responseInfo.setSuperTransType(qrChnFlowPa.getSubTransType());

            }
        }
        log.info("银联B扫C扫码退款与sdk交互结束.................");
        return responseInfo;
    }

    private void updateChnFlow(QrChnFlowPa chnFlowPa) {
        innerRouteService.updateChnFlow(chnFlowPa);
    }

    /**
     * 功能: 组装退货请求参数
     *
     * @param cupMerchant
     * @param request
     * @return
     * @throws TransferFailedException
     */
    private TradeRefundRequest packUp(CupMerchant cupMerchant, ChannelRequestInfo request) throws TransferFailedException {
        TradeRefundRequest sdkRequest = new TradeRefundRequest();
        sdkRequest.setMerchantId(cupMerchant.getCupMerchantId())
                .setOutTradeNo(request.getOriginalPayNo())
                .setOutRefundNo(request.getPayNo())
                .setTotalFee(StringUtils.decimal2string(request.getAmount().movePointRight(2)))
                .setRefundFee(StringUtils.decimal2string(request.getRefundAmt().movePointRight(2)))
                .setOperatorId(request.getOperator())
                .setSignAgentNo(request.getSignInstNo());
        return sdkRequest;
    }

    /**
     * 新增渠道流水信息
     *
     * @param requestInfo
     * @param
     * @param cupMerchant
     * @return
     * @throws AppBizException
     */
    private QrChnFlowPa addChnFlow(ChannelRequestInfo requestInfo, CupMerchant cupMerchant) throws AppBizException {
        QrChnFlowPa qrChnFlowPa = new QrChnFlowPa();
        qrChnFlowPa.setOrgNo(requestInfo.getSignInstNo())
                .setMrchNo(cupMerchant.getMerchantId())
                .setMrchName(requestInfo.getMrchName())
                .setCurrency(requestInfo.getCurrency())
                .setAmount(requestInfo.getAmount())
                .setDeviceip(requestInfo.getDeviceIp())
                .setPaMercId(cupMerchant.getCupMerchantId())
                .setOprid(requestInfo.getOperator())
                .setTrmnlType("")
                .setOutTradeNo(requestInfo.getPayNo())
                .setTxntime(requestInfo.getDeviceTime())
                .setSysTime(DateUtil.format(new Date(), "yyyyMMddHHmmsss"))
                .setAmount(requestInfo.getAmount())
                .setTotalAmount(requestInfo.getAmount())
                .setAuthCode(requestInfo.getAuthCode())
                .setGoodsTag(requestInfo.getGoodsTag())
                .setGoodsDetail(requestInfo.getGoodsDetail())
                .setAttach(requestInfo.getAttach())
                .setNeedReceipt(requestInfo.getNeedReceipt())
                .setBankType(requestInfo.getBankType());
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
    private QrChnFlowPa updateChnFlow(QrChnFlowPa chnFlowPa, TradeRefundResponse response) {
        chnFlowPa.setPayChannel(OrgChannelType.CUP_CHANNEL.getOrgNo())
                .setOutTransactionId(response.getTransactionId())
                .setRefundChannel(response.getRefundChannel())
//                .setCouponRefundFee(response.getCouponRefundFee().doubleValue())
                .setTxnCnl(ChannelType.INTELPOS.getCode());
//                .setSubAppid(PayType.fromEngName(response.getTradeType()).getCode());
        innerRouteService.updateChnFlow(chnFlowPa);
        return chnFlowPa;

    }

    /**
     * 轮询查询交易结果
     *
     * @param request
     * @param secrekey
     * @param qrChnFlowPa
     */
    private ResultModel jobTime(CupMerchant cupMerchant, ChannelRequestInfo request, String secrekey, QrChnFlowPa qrChnFlowPa) {
        ResultModel resultModel = new ResultModel();
//        int total = 1;
//        for (int i = 0; i < total; i++) {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                log.error("定时器启动失败", e);
////                continue;
//            }
        TradeRefundQueryRequest tradeQueryRequest = packUpQuery(cupMerchant, request);
        TradeRefundQueryResponse tradeQueryResponse = baseScanPaymentService.tradeRefundQuery(tradeQueryRequest, secrekey);
        try {
            resultModel = analysisResult(tradeQueryResponse);
        } catch (TransferFailedException e) {
            //查询失败记录为未知
            log.error("查询失败", e);
            resultModel.setStatus("3");
            resultModel.setErrcode("0008");
            resultModel.setErrMsg("网络读写异常");
//                continue;
        } catch (Exception e) {
            log.error("查询失败", e);
            resultModel.setStatus("3");
            resultModel.setErrcode("0008");
            resultModel.setErrMsg("网络读写异常");
//                continue;
        }
        if (StringUtils.isNotBlank(resultModel.getStatus())) {
            if ("3".equals(resultModel.getStatus())) {
                QrChnFlowPaTmp qrChnFlowPaTmp = new QrChnFlowPaTmp();
                BeanUtil.copyProperties(qrChnFlowPa, qrChnFlowPaTmp);
                try {
                    QrChnFlowPaTmp qrChnFlowPaTmpold = qrChnFlowPaTmpService.findBypayNo(qrChnFlowPaTmp.getOutTradeNo(), qrChnFlowPa.getOrgNo());
                    if (null == qrChnFlowPaTmpold) {
                        qrChnFlowPaTmpService.inserQrFow(qrChnFlowPaTmp);
                    }
                } catch (Exception e) {
                    log.error("新增临时流水失败", e);
                }

            }
        }


//        }
        return resultModel;
    }

    private TradeRefundQueryRequest packUpQuery(CupMerchant cupMerchant, ChannelRequestInfo request) throws
            TransferFailedException {

        //上送 平安的商户号以及原始凭证号
        TradeRefundQueryRequest sdkRequest = new TradeRefundQueryRequest();
        sdkRequest.setMerchantId(cupMerchant.getCupMerchantId())
                .setOutRefundNo(request.getPayNo())
                .setSignAgentNo(request.getSignInstNo());
        return sdkRequest;

    }


    private ResultModel analysisResult(TradeRefundQueryResponse response) {
        ResultModel resultModel = new ResultModel();
        if (null == response) {
            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "网络读写异常请重试");
        }
        if (!CupConstant.COMMUNICATE_SUCCESS.equals(response.getStatus())) {
            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "平安通讯异常");
        }

        if (!CupConstant.BUS_SUCCESS.equals(response.getResultCode())) {
            //0代表查询成功
            ErrCodeType errCodeType = ErrCodeType.fromCode(response.getErrorCode());
//            qrChnFlowPa.setRespCode(CupConstant.PA_FAILED_CODE);
            //如果needQuery为N则认为失败
            throw new TransferFailedException(errCodeType.getTransCode(), errCodeType.getChnName());
        }
        PayStatus payStatus = PayStatus.fromCode(response.getRefundStatus());
        switch (payStatus) {
            case SUCCESS:
                resultModel.setStatus("1");
                resultModel.setErrcode(Const.ISO_RESPCODE_00);
                resultModel.setErrMsg("交易成功");
                break;
            case PROCESSING:
                resultModel.setStatus(ErrCodeType.REFUNDPROCESSING.getStatus());
                resultModel.setErrcode(ErrCodeType.REFUNDPROCESSING.getTransCode());
                resultModel.setErrMsg(ErrCodeType.REFUNDPROCESSING.getChnName());
                break;
            case REFUNDFAIL:
                resultModel.setStatus(ErrCodeType.PAYFAIL.getStatus());
                resultModel.setErrcode(ErrCodeType.PAYFAIL.getTransCode());
                resultModel.setErrMsg(ErrCodeType.PAYFAIL.getChnName());
                break;
            //已经名确失败 记录为失败
            default:
                resultModel.setStatus("2");
                resultModel.setErrcode("9999");
                resultModel.setErrMsg("未知错误");
                break;
        }
        return resultModel;
    }

    /**
     * 功能：更新渠道流水对象
     *
     * @param chnFlowPa
     * @param response
     * @return
     */
    private QrChnFlowPa updateChnFlow(QrChnFlowPa chnFlowPa, TradeRefundQueryResponse response) {
        chnFlowPa.setPayChannel(OrgChannelType.CUP_CHANNEL.getOrgNo())
                .setOutTransactionId(response.getOutRefundNo())
//                .setInvoiceAmount(StringUtils.bcdSimp2decimal4yuan(response.getInvoiceAmount()).doubleValue())
                .setTimeEnd(DateUtil.parse(response.getRefundTime(), "yyyyMMddHHmmss"))
//                .setSubTransType(PayType.fromEngName(response.getTradeType()).getCode())
                //TODO  现金卷金额待定
//                .setCouponFee(response.getCouponFee().doubleValue())
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
