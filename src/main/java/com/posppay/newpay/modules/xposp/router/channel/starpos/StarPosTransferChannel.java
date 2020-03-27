package com.posppay.newpay.modules.xposp.router.channel.starpos;


import cn.hutool.core.date.DateUtil;
import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.common.utils.StringUtils;
import com.posppay.newpay.modules.model.OrgChannelType;
import com.posppay.newpay.modules.model.PayChannelType;
import com.posppay.newpay.modules.xposp.common.AppExCode;
import com.posppay.newpay.modules.xposp.common.Const;
import com.posppay.newpay.modules.xposp.entity.*;
import com.posppay.newpay.modules.xposp.innertrans.InnerRouteService;
import com.posppay.newpay.modules.xposp.innertrans.InnerTransService;
import com.posppay.newpay.modules.xposp.router.TransferChannel;
import com.posppay.newpay.modules.xposp.router.TransferFailedException;
import com.posppay.newpay.modules.xposp.router.channel.ChannelRequestInfo;
import com.posppay.newpay.modules.xposp.router.channel.ChannelResponseInfo;
import com.posppay.newpay.modules.xposp.router.channel.starpos.handle.ConsumeProcess;
import com.posppay.newpay.modules.xposp.router.channel.starpos.handle.RefundProcess;
import com.posppay.newpay.modules.xposp.router.channel.starpos.handle.ScanQueryProcess;
import com.posppay.newpay.modules.xposp.router.channel.starpos.handle.ScanRefundQueryProcess;
import com.posppay.newpay.modules.xposp.router.channel.starpos.model.PayResult;
import com.posppay.newpay.modules.xposp.transfer.model.req.ConsumeRequest;
import com.posppay.newpay.modules.xposp.transfer.model.req.QueryRequest;
import com.posppay.newpay.modules.xposp.transfer.model.req.RefundRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.UUID;


/**
 * @author zengjw
 * 功能：星pos渠道服务实现类
 */
@Service("starPosTransChannel")
@Slf4j
public class StarPosTransferChannel implements TransferChannel {

    @Resource(name = "starPosConsumeProcess")
    private ConsumeProcess consumeProcess;
    @Resource(name = "starPosQueryProcess")
    private ScanQueryProcess scanQueryProcess;
    @Resource(name = "starPosRefundProcess")
    private RefundProcess refundProcess;
    @Resource(name = "starPosRefQueryProcess")
    private ScanRefundQueryProcess scanRefundQueryProcess;
    @Resource
    private InnerRouteService innerRouteService;
    @Resource
    private InnerTransService innerTransService;

    /**
     * 扫码消费
     *
     * @param urmStoe
     * @param request
     * @param payment
     * @throws TransferFailedException
     * @throws AppBizException
     */
    @Override
    public ChannelResponseInfo consume(UrmStoe urmStoe, ConsumeRequest request, Payment payment) throws TransferFailedException, AppBizException {
        String payNo = payment.getPayNo();
        BigDecimal mrchRateAmt = null;
        BigDecimal orgRateAmt = null;
        BigDecimal chnRateAmt = null;
        UrmMmap urmMmap = innerRouteService.queryChnMrch(request.getMerchantId());
        if (null == urmMmap) {
            throw new AppBizException(AppExCode.MERCHANT_NOT_EXIST, "商户信息未生效，请联系客户经理");
        }
        TmsPos tmsPos = innerRouteService.queryTrmnlBymerc(urmMmap.getMercId(), urmStoe.getStoeId());
        UrmTprd urmTprd = innerRouteService.queryChnTrmnl(tmsPos.getTrmNo());
        ChannelInfo channelInfo = innerTransService.queryChannel(OrgChannelType.STARPOS_CHANNEL.getOrgNo());
        if (null == channelInfo) {
            throw new AppBizException(AppExCode.CHN_ERR, "通道信息有误，请联系客户经理");
        }
        UrmMinf urmMinf = request.getUrmMinf();
        ChannelScanSupport channelScanSupport = innerRouteService.queryChannelSupport(OrgChannelType.STARPOS_CHANNEL.getOrgNo(), urmMinf.getIncomType());
        if (null == channelScanSupport) {
            throw new AppBizException(AppExCode.CHN_SUPPORT_ERR, "通道扫码信息有误，请联系客户经理");
        }
        ChannelRequestInfo requestInfo = ChannelRequestInfo
                .builder()
                .amount(request.getAmount())
                .authCode(request.getAuthCode())
                .payNo(payNo)
                .screkey(urmMmap.getStarKey())
                .deviceIp(request.getIpAddress())
                .operator(request.getOprId())
                .body(request.getBody())
                .build();
        if (null != request.getCupFeeBank()) {
            requestInfo.setCupFeeBank(request.getCupFeeBank());
        }
        if (null != request.getCupFeeCredict()) {
            requestInfo.setCupFeeCredict(request.getCupFeeCredict());
        }
        PayChannelType payChannelType = PayChannelType.fromCode(request.getPayChannel());
        switch (payChannelType) {
            case ALI_CHANNEL:
                if (StringUtils.isBlank(channelScanSupport.getAliScanPaySupport()) || (!Const.IS_ACTIVE_STR.equals(channelScanSupport.getAliScanPaySupport()))) {
                    throw new AppBizException(AppExCode.SCAN_SUPPORT_ERR, "当前付款码类型不支持，请联系客户经理");
                }

                if (null != channelInfo.getAliFee()) {
                    payment.setChnFee(channelInfo.getAliFee());
                    payment.setChnFeeAmt(channelInfo.getAliFee()
                            .movePointLeft(3)
                            .multiply(request.getAmount()
                                    .setScale(4, RoundingMode.HALF_UP)));
                }
                break;
            case WX_CHANNEL:
                if (StringUtils.isBlank(channelScanSupport.getWxScanPaySupport()) || (!Const.IS_ACTIVE_STR.equals(channelScanSupport.getWxScanPaySupport()))) {
                    throw new AppBizException(AppExCode.SCAN_SUPPORT_ERR, "当前付款码类型不支持，请联系客户经理");
                }
                if (null != channelInfo.getWxFee()) {
                    payment.setChnFee(channelInfo.getWxFee());
                    payment.setChnFeeAmt(channelInfo.getWxFee()
                            .movePointLeft(3)
                            .multiply(request.getAmount()
                                    .setScale(4, RoundingMode.HALF_UP)));
                }
                break;
            case CUP_CHANNEL:
                if (StringUtils.isBlank(channelScanSupport.getCupScanPaySupport()) || (!Const.IS_ACTIVE_STR.equals(channelScanSupport.getCupScanPaySupport()))) {
                    throw new AppBizException(AppExCode.SCAN_SUPPORT_ERR, "当前付款码类型不支持，请联系客户经理");
                }
                if (request.getAmount().compareTo(new BigDecimal("1000.00")) < 0) {
                    //小于1000
                    if (null != channelInfo.getYlMicroCredictFee()) {
                        payment.setChnFee(channelInfo.getYlMicroCredictFee());
                        chnRateAmt = channelInfo.getYlMicroCredictFee()
                                .movePointLeft(3)
                                .multiply(request.getAmount()
                                        .setScale(4, RoundingMode.HALF_UP));
                        //判断封顶
                        if (null == channelInfo.getYlQrCommonBankFeeMax() || chnRateAmt.compareTo(channelInfo.getYlQrCommonBankFeeMax()) <= 0) {
                            //通道费率金额小于封顶
                            payment.setChnFeeAmt(chnRateAmt);
                        } else {
                            payment.setChnFeeAmt(channelInfo.getYlQrCommonBankFeeMax().setScale(4, RoundingMode.HALF_UP));
                        }
                    }
                } else {
                    //大于等于1000
                    if (null != channelInfo.getYlCommonCredictFee()) {
                        payment.setChnFee(channelInfo.getYlCommonCredictFee());
                        chnRateAmt = channelInfo.getYlCommonCredictFee()
                                .movePointLeft(3)
                                .multiply(request.getAmount()
                                        .setScale(4, RoundingMode.HALF_UP));
                        if (null == channelInfo.getYlQrCommonBankFeeMax() || chnRateAmt.compareTo(channelInfo.getYlQrCommonBankFeeMax()) <= 0) {
                            //通道费率金额小于封顶
                            payment.setChnFeeAmt(chnRateAmt);
                        } else {
                            payment.setChnFeeAmt(channelInfo.getYlQrCommonBankFeeMax().setScale(4, RoundingMode.HALF_UP));
                        }
                    }
                }
                if (null != requestInfo.getCupFeeCredict()) {
                    mrchRateAmt = requestInfo.getAmount()
                            .multiply(requestInfo.getCupFeeCredict())
                            .movePointLeft(3).setScale(4, RoundingMode.HALF_UP);
                    //商户费率
                    payment.setMrchRates(requestInfo.getCupFeeBank());
                    //如果封顶为空或者费率金额小于封顶则取费率金额
                    if (null == request.getCupFeeMax() || mrchRateAmt.compareTo(request.getCupFeeMax()) <= 0) {
                        //不高于封顶则取当前的
                        payment.setMrchRatesAmount(mrchRateAmt);
                    } else {
                        payment.setMrchRatesAmount(request.getCupFeeMax());
                    }
                }
                if (null != request.getCupOrgFeeCredict()) {
                    //机构费率
                    orgRateAmt = request.getCupOrgFeeCredict()
                            .movePointLeft(3)
                            .multiply(request.getAmount())
                            .setScale(4, RoundingMode.HALF_UP);
                    payment.setOrgFee(request.getCupOrgFeeCredict());
                    if (null == request.getOrgFeeMax() || orgRateAmt.compareTo(request.getOrgFeeMax()) <= 0) {
                        //小于封顶
                        payment.setOrgFeeAmt(orgRateAmt);
                    } else {
                        payment.setOrgFeeAmt(request.getOrgFeeMax().setScale(4, RoundingMode.HALF_UP));

                    }
                }
            default:
                break;

        }

        ChannelResponseInfo responseInfo = new ChannelResponseInfo();
        try {
            responseInfo = consumeProcess.process(urmMmap, urmTprd, requestInfo);
        } catch (
                TransferFailedException e) {
            if (StringUtils.isNotBlank(requestInfo.getStatus()) && Const.TransStatus.UNKNOWN_STR.equals(requestInfo.getStatus())) {
                log.error("消费第三方报错",e);
                payment.setTransStatus(Const.TransStatus.UNKNOWN_STR);
                payment.setTransCode(e.getCode());
                payment.setTransMsg(e.getMessage());
                payment.setOutTransactionId(responseInfo.getOutTranstionId());
                return responseInfo;
            } else {
                request.setErrCodeType(requestInfo.getErrCodeType());
                throw e;
            }
        } catch (
                AppBizException e) {
            throw e;
        }


        payment.setSubTransType(responseInfo.getSuperTransType());
        payment.setTransCode(Const.ISO_RESPCODE_00);
        payment.setRemark("交易成功");
        payment.setTransMsg("交易成功");
        payment.setTransStatus(Const.TransStatus.SUCCESS_STR);
        payment.setOutTransactionId(responseInfo.getOutTranstionId());
        //回填数据
        request.setTransationId(responseInfo.getTransationId());
        request.setOutTranstionId(responseInfo.getOutTranstionId());
        request.setBankBillNo(responseInfo.getBanBillNo());

        return responseInfo;
    }

    /**
     * 功能： 消费交易状态查询
     *
     * @param urmStoe
     * @param request
     * @param originPayment
     * @return
     * @throws TransferFailedException
     * @throws AppBizException
     */
    @Override
    public ChannelResponseInfo scanQuery(UrmStoe urmStoe, QueryRequest request, Payment originPayment) throws TransferFailedException, AppBizException {
        //消费已经对费率进行了计算这里不再需要重新计算
        String originPayNo = originPayment.getPayNo();
        UrmMmap urmMmap = innerRouteService.queryChnMrch(request.getMerchantId());
        if (null == urmMmap) {
            throw new AppBizException(AppExCode.MERCHANT_NOT_EXIST, "商户信息未生效，请联系客户经理");
        }
        UrmAgtfee urmAgtfee = innerRouteService.queryByOrgNo(request.getOrgNo());
        if (null == urmAgtfee) {
            throw new AppBizException(AppExCode.ORG_ERR, "机构信息异常，请联系客户经理");
        }

        ChannelRequestInfo requestInfo = ChannelRequestInfo
                .builder()
                .originalPayNo(originPayNo)
                .screkey(urmMmap.getStarKey())
                .amount(originPayment.getTransAmount())
                .build();
        if (null != request.getCupFeeBank()) {
            requestInfo.setCupFeeBank(request.getCupFeeBank());
        }
        if (null != request.getCupFeeCredict()) {
            requestInfo.setCupFeeCredict(request.getCupFeeCredict());
        }
        ChannelResponseInfo responseInfo = new ChannelResponseInfo();

        QrChnFlowGt originChnFlow = innerRouteService.querySingleGtFlow(originPayNo, null, DateUtil.format(request.getOriTransDate(), "yyyyMMdd"));
        if (null == originChnFlow) {
            throw new AppBizException(AppExCode.ORDER_NOT_FOUND, "原交易不存在，请核实");
        }

        try {
            responseInfo = scanQueryProcess.process(urmMmap, requestInfo, originChnFlow);
        } catch (TransferFailedException e) {
            log.error("错误信息是：\n", e);
            if (StringUtils.isNotBlank(requestInfo.getResult())) {
                if (PayResult.FAIL.getCode().equals(requestInfo.getResult())) {
                    throw e;
                }
            }
            return responseInfo;
        } catch (AppBizException e) {
            throw e;
        }

        originPayment.setSubTransType(responseInfo.getSuperTransType());
        originPayment.setTransCode(Const.ISO_RESPCODE_00);
        originPayment.setTransStatus(Const.TransStatus.SUCCESS_STR);
        originPayment.setRemark("交易成功");
        originPayment.setTransMsg("交易成功");
        originPayment.setOutTransactionId(responseInfo.getOutTranstionId());
        request.setBody(responseInfo.getBody());
        request.setGoodsTag(responseInfo.getGoodTag());
        request.setGoodsDetail(responseInfo.getGoodDetail());
        return responseInfo;
    }

    /**
     * 功能： 扫码退货
     *
     * @param urmStoe
     * @param refundRequest
     * @param refundPayment
     * @return
     * @throws TransferFailedException
     * @throws AppBizException
     */
    @Override
    public ChannelResponseInfo refund(UrmStoe urmStoe, RefundRequest refundRequest, Payment refundPayment) throws TransferFailedException, AppBizException {
        String orginPayNo = refundPayment.getOriginPayno();
        UrmMmap urmMmap = innerRouteService.queryChnMrch(refundRequest.getMerchantId());
        if (null == urmMmap) {
            throw new AppBizException(AppExCode.MERCHANT_NOT_EXIST, "商户信息未生效，请联系客户经理");
        }
        TmsPos tmsPos = innerRouteService.queryTrmnlBymerc(urmMmap.getMercId(), urmStoe.getStoeId());
        UrmTprd urmTprd = innerRouteService.queryChnTrmnl(tmsPos.getTrmNo());
        QrChnFlowGt originChnFlow = innerRouteService.querySingleGtFlow(orginPayNo, null, DateUtil.format(refundRequest.getOriTransDate(), "yyyyMMdd"));
        if (null == originChnFlow) {
            throw new AppBizException(AppExCode.ORDER_NOT_FOUND, "原交易不存在，请核实");
        }
        ChannelRequestInfo requestInfo = ChannelRequestInfo
                .builder()
                .amount(originChnFlow.getAmount())
                .originalPayNo(orginPayNo)
                .payNo(refundPayment.getPayNo())
                .refundAmt(refundRequest.getRefAmt())
                .screkey(urmMmap.getStarKey())
                .outRefundNo(refundPayment.getOrdNo())
                .operator(refundRequest.getOprId())
                .build();
        ChannelResponseInfo responseInfo = new ChannelResponseInfo();
        try {
            responseInfo = refundProcess.process(urmMmap, urmTprd, requestInfo, originChnFlow);
        } catch (TransferFailedException e) {
            refundRequest.setTransationId(requestInfo.getTransactionId());
            refundRequest.setOutTranstionId(requestInfo.getOutTransactionId());
            refundPayment.setOutTransactionId(requestInfo.getTransactionId());
            throw e;
        } catch (AppBizException e) {
            refundRequest.setTransationId(requestInfo.getTransactionId());
            refundRequest.setOutTranstionId(requestInfo.getOutTransactionId());
            refundPayment.setOutTransactionId(requestInfo.getTransactionId());
            throw e;
        }
        refundPayment.setSubTransType(responseInfo.getSuperTransType());
        refundPayment.setTransCode(Const.ISO_RESPCODE_00);
        refundPayment.setRemark("交易成功");
        refundPayment.setTransMsg("交易成功");
        refundPayment.setTransStatus(Const.TransStatus.SUCCESS_STR);
        refundPayment.setOutTransactionId(requestInfo.getTransactionId());

        //回填数据
        refundRequest.setTransationId(requestInfo.getTransactionId());
        refundRequest.setOutTranstionId(requestInfo.getOutTransactionId());
        refundRequest.setBankBillNo(responseInfo.getBanBillNo());
        return responseInfo;
    }

    /**
     * 功能： 扫码退款查询
     *
     * @param urmStoe
     * @param request
     * @param originPayment
     * @param status
     * @return
     * @throws TransferFailedException
     * @throws AppBizException
     */
    @Override
    public ChannelResponseInfo sanRefundQuery(UrmStoe urmStoe, QueryRequest request, Payment originPayment, String status) throws TransferFailedException, AppBizException {
        String orginPayNo = originPayment.getPayNo();
        String payNo = DateUtil.format(new Date(), "yyyyMMdd") + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 24);
        UrmMmap urmMmap = innerRouteService.queryChnMrch(request.getMerchantId());
        if (null == urmMmap) {
            throw new AppBizException(AppExCode.MERCHANT_NOT_EXIST, "商户信息未生效，请联系客户经理");
        }
        ChannelRequestInfo requestInfo = ChannelRequestInfo
                .builder()
                .originalPayNo(orginPayNo)
                .screkey(urmMmap.getStarKey())
                .payNo(payNo)
                .outRefundNo(originPayment.getOrdNo())
                .build();
        ChannelResponseInfo responseInfo = new ChannelResponseInfo();

        QrChnFlowGt originChnFlow = innerRouteService.querySingleGtFlow(orginPayNo, null, DateUtil.format(request.getOriTransDate(), "yyyyMMdd"));
        if (null == originChnFlow) {
            throw new AppBizException(AppExCode.ORDER_NOT_FOUND, "原交易不存在，请核实");
        }
        try {
            responseInfo = scanRefundQueryProcess.process(urmMmap, requestInfo, originChnFlow);
        } catch (TransferFailedException e) {
            throw e;
        } catch (AppBizException e) {
            if (Const.TransStatus.UNKNOWN_STR.equals(requestInfo.getStatus())) {
                status = "3";
                originPayment.setTransStatus(Const.TransStatus.UNKNOWN_STR);
                originPayment.setTransCode(e.getCode());
                originPayment.setRemark(e.getMessage());
                return responseInfo;
            } else {
                status = "2";
                throw e;
            }
        }
        originPayment.setSubTransType(responseInfo.getSuperTransType());
        originPayment.setTransCode(Const.ISO_RESPCODE_00);
        originPayment.setTransMsg("交易成功");
        originPayment.setRemark("交易成功");
        originPayment.setTransStatus(Const.TransStatus.SUCCESS_STR);
        originPayment.setOutTransactionId(responseInfo.getOutTranstionId());

        return responseInfo;
    }
}
