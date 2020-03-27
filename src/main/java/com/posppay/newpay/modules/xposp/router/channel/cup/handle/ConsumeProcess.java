package com.posppay.newpay.modules.xposp.router.channel.cup.handle;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.common.utils.StringUtils;
import com.posppay.newpay.modules.model.ChannelType;
import com.posppay.newpay.modules.model.OrgChannelType;
import com.posppay.newpay.modules.model.ResultModel;
import com.posppay.newpay.modules.sdk.cup.config.CupConfig;
import com.posppay.newpay.modules.sdk.cup.constant.CupConstant;
import com.posppay.newpay.modules.sdk.cup.model.req.PassiveTradeRequest;
import com.posppay.newpay.modules.sdk.cup.model.req.TradeQueryRequest;
import com.posppay.newpay.modules.sdk.cup.model.resp.PassiveTradeResponse;
import com.posppay.newpay.modules.sdk.cup.model.resp.TradeQueryResponse;
import com.posppay.newpay.modules.sdk.cup.service.BaseScanPaymentService;
import com.posppay.newpay.modules.sdk.cup.model.ErrCodeType;
import com.posppay.newpay.modules.sdk.cup.model.PayStatus;
import com.posppay.newpay.modules.sdk.cup.model.PayType;
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
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能：银联消费与第三方通讯接口实现
 *
 * @author zengjianwens
 */
@Service("cupsconsumeProcess")
@Slf4j
public class ConsumeProcess {
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
     */
    public ChannelResponseInfo process(UrmStoe urmStoe, CupMerchant cupMerchant, ChannelRequestInfo request)
            throws TransferFailedException, AppBizException {

        log.info("银联服务化B扫C消费与sdk交互开始.................");
        if (cupConfig.isTest()) {
            log.info("此为测试专用");
            cupMerchant.setCupMerchantId(cupConfig.getMercId());
        }         //判断是否走挡板程序
        ChannelResponseInfo responseInfo = new ChannelResponseInfo();
        QrChnFlowPa qrChnFlowPa = addChnFlow(request, cupMerchant);
        request.setSignInstNo(cupConfig.getOrgNo());
        PassiveTradeRequest entity = packUp(urmStoe, cupMerchant, request);
        String secrekey = cupConfig.getKey();
        PassiveTradeResponse response = null;
        response = baseScanPaymentService.addPassiveTrade(entity, secrekey);
        log.info("返回的sdk信息是" + JSONObject.fromObject(response).toString());
        if (null == response) {
            //没有返回状态未知
            qrChnFlowPa.setRespCode(AppExCode.NETWORK_CONNECTION_FAILED);
            innerRouteService.updateChnFlow(qrChnFlowPa);
            //银联没返回则抛通讯异常
            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "网络读写异常请重试");
        }
        if (!CupConstant.COMMUNICATE_SUCCESS.equals(response.getStatus())) {
//            0表示成功，非0表示失败此字段是通信标识
//            qrChnFlowPa.setRespCode(AppExCode.NETWORK_CONNECTION_FAILED);
//            innerRouteService.updateChnFlow(qrChnFlowPa);
//            throw new TransferFailedException(AppExCode.UNKNOWN, response.getMessage());
            if (StringUtils.isNotBlank(response.getNeedQuery()) && "Y".equals(response.getNeedQuery())) {

                //TODO 这里对是否查询需要处理
                ResultModel resultModel = jobTime(cupMerchant, request, secrekey, qrChnFlowPa);
                request.setStatus(resultModel.getStatus());
                if ("3".equals(resultModel.getStatus())) {
                    log.info("银联服务化B扫C消费与sdk交互结束.................");
                    throw new TransferFailedException(resultModel.getErrcode(), resultModel.getErrMsg());
                } else {
//                    updateChnFlow(qrChnFlowPa);
                    if ("2".equals(resultModel.getStatus())) {
                        log.info("银联服务化B扫C消费与sdk交互结束.................");
                        throw new TransferFailedException(resultModel.getErrcode(), resultModel.getErrMsg());
                    } else {
                        qrChnFlowPa.setRespCode(Const.ISO_RESPCODE_00);
                        qrChnFlowPa.setRemark("交易成功");
                        if (null != request.getEndTime()) {
                            qrChnFlowPa.setTimeEnd(DateUtil.parse(request.getEndTime(), "yyyyMMddHHmmss"));
                        }
                        if (StringUtils.isNotBlank(request.getSubTransType())) {
                            qrChnFlowPa.setSubTransType(PayType.fromEngName(request.getSubTransType()).getCode());
                        }
                        //更新渠道流水
                        updateChnFlow(qrChnFlowPa, response);
                        //接收报文
                        responseInfo.setTransCode(response.getErrorCode());
                        responseInfo.setResult(qrChnFlowPa.getRespCode());
                        responseInfo.setPayNo(request.getPayNo());
                        responseInfo.setSuperTransType(qrChnFlowPa.getSubTransType());
                        responseInfo.setTransCode(response.getResultCode());
                        responseInfo.setTransationId(response.getTransactionId());
                        responseInfo.setOutTranstionId(response.getOutTransactionId());
                        responseInfo.setBanBillNo(response.getBankBillno());
                        responseInfo.setBankType(request.getBankType());
                        log.info("银联服务化B扫C消费与sdk交互结束.................");
                        return responseInfo;
                    }
                }
            } else {
                qrChnFlowPa.setRespCode(AppExCode.NETWORK_CONNECTION_FAILED);
                innerRouteService.updateChnFlow(qrChnFlowPa);
                log.info("银联服务化B扫C消费与sdk交互结束.................");
                throw new TransferFailedException(AppExCode.UNKNOWN, response.getMessage());
            }
        }
        if (!CupConstant.BUS_SUCCESS.equals(response.getResultCode())) {
            if (CupConstant.CUP_NOT_NEED_CODE.equals(response.getNeedQuery())) {
                ErrCodeType errCodeType = ErrCodeType.fromCode(response.getErrorCode());
                qrChnFlowPa.setRespCode(CupConstant.CUP_FAILED_CODE);
                log.info("银联服务化B扫C消费与sdk交互结束.................");
                //如果needQuery为N则认为失败
                throw new TransferFailedException(errCodeType.getTransCode(), errCodeType.getChnName());
            } else {
                //轮询处理
                ResultModel resultModel = jobTime(cupMerchant, request, secrekey, qrChnFlowPa);
                request.setStatus(resultModel.getStatus());
                if ("3".equals(resultModel.getStatus())) {
                    throw new TransferFailedException(resultModel.getErrcode(), resultModel.getErrMsg());
                } else {
//                    updateChnFlow(qrChnFlowPa);
                    if ("2".equals(resultModel.getStatus())) {
                        throw new TransferFailedException(resultModel.getErrcode(), resultModel.getErrMsg());
                    } else {
                        qrChnFlowPa.setRespCode(Const.ISO_RESPCODE_00);
                        qrChnFlowPa.setRemark("交易成功");
                        if (null != request.getEndTime()) {
                            qrChnFlowPa.setTimeEnd(DateUtil.parse(request.getEndTime(), "yyyyMMddHHmmss"));
                        }
                        if (StringUtils.isNotBlank(request.getSubTransType())) {
                            qrChnFlowPa.setSubTransType(PayType.fromEngName(request.getSubTransType()).getCode());
                        }
                        //更新渠道流水
                        updateChnFlow(qrChnFlowPa, response);
                        //接收报文
                        responseInfo.setTransCode(response.getErrorCode());
                        responseInfo.setResult(qrChnFlowPa.getRespCode());
                        responseInfo.setPayNo(request.getPayNo());
                        responseInfo.setSuperTransType(qrChnFlowPa.getSubTransType());
                        responseInfo.setTransCode(response.getResultCode());
                        responseInfo.setTransationId(response.getTransactionId());
                        responseInfo.setOutTranstionId(response.getOutTransactionId());
                        responseInfo.setBanBillNo(response.getBankBillno());
                        responseInfo.setBankType(request.getBankType());
                    }
                }
            }
        } else {
            //如果没有问题则记录预计状态等待查询成功才返回
            qrChnFlowPa.setRespCode(Const.ISO_RESPCODE_00);
            qrChnFlowPa.setRemark("交易成功");
            //更新渠道流水
            updateChnFlow(qrChnFlowPa, response);
            //接收报文

            responseInfo.setTransCode(response.getErrorCode());
            responseInfo.setResult(qrChnFlowPa.getRespCode());
            responseInfo.setPayNo(request.getPayNo());
            responseInfo.setSuperTransType(qrChnFlowPa.getSubTransType());
            responseInfo.setTransCode(response.getResultCode());
            responseInfo.setTransationId(response.getTransactionId());
            responseInfo.setOutTranstionId(response.getTransactionId());
            responseInfo.setBanBillNo(response.getBankBillno());
            responseInfo.setBankType(response.getBankType());
        }
        log.info("平安服务化B扫C消费与sdk交互结束.................");
        return responseInfo;
    }


    /**
     * 对接sdk封装请求数据
     *
     * @param urmStoe
     * @param cupMerchant
     * @param request
     * @return
     * @throws TransferFailedException
     */
    private PassiveTradeRequest packUp(UrmStoe urmStoe, CupMerchant cupMerchant, ChannelRequestInfo request) throws TransferFailedException {
        PassiveTradeRequest sdkRequest = new PassiveTradeRequest();
        sdkRequest.setAuthCode(request.getAuthCode())
                .setMerchantId(cupMerchant.getCupMerchantId())
                .setOperatorId(request.getOperator())
                .setOutTradeNo(request.getPayNo())
                .setTotalFee(Integer.parseInt(StringUtils.decimal2bcdSimp4yuan(request.getAmount())))
                .setMachineIp(request.getDeviceIp())
                .setSignAgentNo(request.getSignInstNo());
        if (StringUtils.isNotBlank(request.getBody())) {
            sdkRequest.setBody(request.getBody());
        } else {
            sdkRequest.setBody(urmStoe.getStoeNm());
        }
        return sdkRequest;
    }

    /**
     * 新增渠道流水信息
     *
     * @param requestInfo
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
                .setBodydetail(requestInfo.getBody())
                .setPayChannel(OrgChannelType.CUP_CHANNEL.getOrgNo());
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
    private QrChnFlowPa updateChnFlow(QrChnFlowPa chnFlowPa, PassiveTradeResponse response) {
        chnFlowPa.setOutTransactionId(response.getOutTransactionId())
                .setTransactionId(response.getTransactionId())
                .setSubIsSubscribe(response.getSubSubscribe())
                .setSubAppid(response.getSubAppId())
//                .setInvoiceAmount(StringUtils.bcdSimp2decimal4yuan(response.getInvoiceAmount()).doubleValue())
                .setBankBillno(response.getBankBillno())
//                .setSubTransType(PayType.fromEngName(response.getTradeType()).getCode())
                .setIsSubscribe(response.getSubscribe())
                .setPromotionDetail(response.getPromotionDetail())
                .setFundBillList(response.getFundBillList())
                .setBankType(response.getBankType())
                .setBuyerUserId("")
                .setBuyerLogonId("")
                .setAppid("")
                .setOpenid(response.getOpenId())
                .setSubOpenid(response.getSubOpenId())
                .setTxnCnl(ChannelType.INTELPOS.getCode());
        if (null == chnFlowPa.getTimeEnd()) {
            if (StringUtils.isNotBlank(response.getTimeEnd())) {
                chnFlowPa.setTimeEnd(DateUtil.parse(response.getTimeEnd(), "yyyyMMddHHmmss"));
            }
        }
        if (null == chnFlowPa.getSubTransType()) {
            if (StringUtils.isNotBlank(response.getTradeType())) {
                chnFlowPa.setSubTransType(PayType.fromEngName(response.getTradeType()).getCode());
            }
        }
        if (null != response.getCouponFee()) {
            chnFlowPa.setCouponFee(new BigDecimal(response.getCouponFee()));
        }
        innerRouteService.updateChnFlow(chnFlowPa);
        return chnFlowPa;

    }

    private void updateChnFlow(QrChnFlowPa chnFlowPa) {
        innerRouteService.updateChnFlow(chnFlowPa);
    }


    /**
     * 功能：更新渠道流水对象
     *
     * @param chnFlowPa
     * @param response
     * @return
     */
    private QrChnFlowPa updateChnFlow(QrChnFlowPa chnFlowPa, TradeQueryResponse response) {
        chnFlowPa.setOutTransactionId(response.getOutTransactionId())
                .setTransactionId(response.getTransactionId())
                .setSubIsSubscribe(response.getSubSubscribe())
                .setSubAppid(response.getSubAppId())
//                .setInvoiceAmount(StringUtils.bcdSimp2decimal4yuan(response.getInvoiceAmount()).doubleValue())
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
        if (null == chnFlowPa.getTimeEnd()) {
            if (StringUtils.isNotBlank(response.getTimeEnd())) {
                chnFlowPa.setTimeEnd(DateUtil.parse(response.getTimeEnd(), "yyyyMMddHHmmss"));
            }
        }
        if (null == chnFlowPa.getSubTransType()) {
            if (StringUtils.isNotBlank(response.getTradeType())) {
                chnFlowPa.setSubTransType(PayType.fromEngName(response.getTradeType()).getCode());
            }
        }
        if (null != response.getCouponFee()) {
            chnFlowPa.setCouponFee(new BigDecimal(response.getCouponFee()));
        }
//        innerTransService.updateChnFlow(chnFlowPa);
        return chnFlowPa;

    }

    /**
     * 组包
     *
     * @param cupMerchant
     * @param request
     * @return
     * @throws TransferFailedException
     */
    private TradeQueryRequest packUp(CupMerchant cupMerchant, ChannelRequestInfo request) throws
            TransferFailedException {

        //上送 银联的商户号以及原始凭证号
        TradeQueryRequest sdkRequest = new TradeQueryRequest();

        sdkRequest.setMerchantId(cupMerchant.getCupMerchantId())
                .setOutTradeNo(request.getPayNo())
                .setSignAgentNo(request.getSignInstNo());
        return sdkRequest;

    }


    private ResultModel analysisResult(TradeQueryResponse response) {
        ResultModel resultModel = new ResultModel();
        ErrCodeType errCodeType = null;
        if (null == response) {
            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "网络读写异常请重试");
        }
        if (!CupConstant.COMMUNICATE_SUCCESS.equals(response.getStatus())) {
            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "通讯异常");
        }

        if (!CupConstant.BUS_SUCCESS.equals(response.getResultCode())) {
            //0代表查询成功
            errCodeType = ErrCodeType.fromCode(response.getErrorCode());
//            qrChnFlowPa.setRespCode(CupConstant.PA_FAILED_CODE);
            //如果needQuery为N则认为失败
            throw new TransferFailedException(errCodeType.getTransCode(), errCodeType.getChnName());
        }
        PayStatus payStatus = PayStatus.fromCode(response.getTradeState());
        switch (payStatus) {
            case SUCCESS:
                resultModel.setStatus("1");
                resultModel.setErrcode(Const.ISO_RESPCODE_00);
                resultModel.setErrMsg("交易成功");
                break;
            case REFUND:
                resultModel.setStatus("1");
                resultModel.setErrcode(Const.ISO_RESPCODE_00);
                resultModel.setErrMsg("交易成功");
                break;
            case NOTPAY:
                if (StringUtils.isNotBlank(response.getErrorCode())) {
                    errCodeType = ErrCodeType.fromCode(response.getErrorCode());
                    resultModel.setStatus(errCodeType.getStatus());
                    resultModel.setErrcode(errCodeType.getTransCode());
                    resultModel.setErrMsg(errCodeType.getChnName());
                } else {
                    resultModel.setStatus(ErrCodeType.NOTPAY.getStatus());
                    resultModel.setErrcode(ErrCodeType.NOTPAY.getTransCode());
                    resultModel.setErrMsg(ErrCodeType.NOTPAY.getChnName());
                }
                break;
            case CLOSED:
                resultModel.setStatus(ErrCodeType.ORDERCLOSED.getStatus());
                resultModel.setErrcode(ErrCodeType.ORDERCLOSED.getTransCode());
                resultModel.setErrMsg(ErrCodeType.ORDERCLOSED.getChnName());
                break;
            case CANCEL:
                resultModel.setStatus(ErrCodeType.ORDERREVERSED.getStatus());
                resultModel.setErrcode(ErrCodeType.ORDERREVERSED.getTransCode());
                resultModel.setErrMsg(ErrCodeType.ORDERCLOSED.getChnName());
                break;
            case USEPAY:
                resultModel.setStatus(ErrCodeType.USERPAYING.getStatus());
                resultModel.setErrcode(ErrCodeType.USERPAYING.getTransCode());
                resultModel.setErrMsg(ErrCodeType.USERPAYING.getChnName());
                break;
            case FAIL:
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
     * 轮询查询交易结果
     *
     * @param cupMerchant
     * @param request
     * @param secrekey
     * @param qrChnFlowPa
     */
    private ResultModel jobTime(CupMerchant cupMerchant, ChannelRequestInfo request, String secrekey, QrChnFlowPa qrChnFlowPa) {
        ResultModel resultModel = new ResultModel();
        //取消定时任务只查一次
        TradeQueryRequest tradeQueryRequest = packUp(cupMerchant, request);
        TradeQueryResponse tradeQueryResponse = baseScanPaymentService.tradeResultQuery(tradeQueryRequest, secrekey);
        try {
            resultModel = analysisResult(tradeQueryResponse);
            if (null != tradeQueryResponse) {
                request.setSubTransType(tradeQueryResponse.getTradeType());
                request.setEndTime(tradeQueryResponse.getTimeEnd());
            }
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
            if (!resultModel.getStatus().equals(Const.TransStatus.UNKNOWN_STR)) {
                //回填信息到请求
                request.setBankType(tradeQueryResponse.getBankType());
                updateChnFlow(qrChnFlowPa, tradeQueryResponse);
//                    resultModel.setErrMsg(tradeQueryResponse.getErrorMessage());
                return resultModel;
            }
        }
        if ("3".equals(resultModel.getStatus())) {

            QrChnFlowPaTmp qrChnFlowPaTmp = new QrChnFlowPaTmp();

            BeanUtil.copyProperties(qrChnFlowPa, qrChnFlowPaTmp);
//                    BeanUtils.copyProperties(qrChnFlowPaTmp, qrChnFlowPa);
            try {
                QrChnFlowPaTmp qrChnFlowPaTmpold = qrChnFlowPaTmpService.findBypayNo(qrChnFlowPaTmp.getOutTradeNo(), qrChnFlowPa.getOrgNo());
                if (null == qrChnFlowPaTmpold) {
                    qrChnFlowPaTmpService.inserQrFow(qrChnFlowPaTmp);
                }
            } catch (Exception e) {
                log.error("新增临时流水失败", e);
            }

        }

//        }
        return resultModel;
    }


}
