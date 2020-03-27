package com.posppay.newpay.modules.xposp.router.channel.cup;

import cn.hutool.core.date.DateUtil;
import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.common.utils.StringUtils;
import com.posppay.newpay.modules.model.OrgChannelType;
import com.posppay.newpay.modules.model.PayChannelType;
import com.posppay.newpay.modules.sdk.cup.config.CupConfig;
import com.posppay.newpay.modules.sdk.cup.model.PayType;
import com.posppay.newpay.modules.xposp.common.AppExCode;
import com.posppay.newpay.modules.xposp.common.Const;
import com.posppay.newpay.modules.xposp.entity.*;
import com.posppay.newpay.modules.xposp.innertrans.InnerRouteService;
import com.posppay.newpay.modules.xposp.innertrans.InnerTransService;
import com.posppay.newpay.modules.xposp.router.TransferChannel;
import com.posppay.newpay.modules.xposp.router.TransferFailedException;
import com.posppay.newpay.modules.xposp.router.channel.ChannelRequestInfo;
import com.posppay.newpay.modules.xposp.router.channel.ChannelResponseInfo;
import com.posppay.newpay.modules.xposp.router.channel.cup.handle.*;
import com.posppay.newpay.modules.xposp.transfer.model.req.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.UUID;


/**
 * @author zengjw
 * 功能：银联扫码渠道服务实现类
 */
@Service("cupTransferChannel")
@Slf4j
public class CupTransferChannel implements TransferChannel {

    @Resource(name = "cupsconsumeProcess")
    private ConsumeProcess consumeProcess;
    @Resource(name = "cupQrCodeProcess")
    private QrCodeProcess qrCodeProcess;
    @Resource(name = "cupGzhProcess")
    private JsapiProcess jsapiProcess;
    @Resource(name = "cupRefundProcess")
    private RefundProcess refundProcess;
    @Resource(name = "cupScanQueryProcess")
    private ScanQueryProcess scanQueryProcess;
    @Resource
    private InnerTransService innerTransService;
    @Resource(name = "cupScanRefundQueryProcess")
    private ScanRefundQueryProcess scanRefundQueryProcess;
    @Autowired
    private CupConfig cupConfig;
    @Resource
    private InnerRouteService innerRouteService;
//


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
        CupMerchant cupMerchant = innerRouteService.queryByMerIdAndStoreNo(request.getMerchantId(), urmStoe.getStoeId());
        if (null == cupMerchant) {
            throw new AppBizException(AppExCode.MERCHANT_NOT_EXIST, "商户信息未生效，请联系客户经理");
        }
//        if (Const.IS_ACTIVE != cupMerchant.getExamineStatus().intValue() || Const.IS_ACTIVE != cupMerchant.getActivateStatus().intValue()) {
//            throw new AppBizException(AppExCode.MERCHANT_NOT_EXIST, "商户信息未生效，请联系客户经理");
//        }
        UrmMinf urmMinf = request.getUrmMinf();
        ChannelScanSupport channelScanSupport = innerRouteService.queryChannelSupport(OrgChannelType.CUP_CHANNEL.getOrgNo(), urmMinf.getIncomType());
        if (null == channelScanSupport) {
            throw new AppBizException(AppExCode.CHN_SUPPORT_ERR, "通道扫码信息有误，请联系客户经理");
        }
        ChannelInfo channelInfo = innerTransService.queryChannel(OrgChannelType.CUP_CHANNEL.getOrgNo());
        ChannelRequestInfo requestInfo = ChannelRequestInfo
                .builder()
                .amount(request.getAmount())
                .authCode(request.getAuthCode())
                .payNo(payNo)
                .screkey(cupMerchant.getCupPaySecretKey())
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
                break;
            default:
                break;

        }
        ChannelResponseInfo responseInfo = new ChannelResponseInfo();
        try {
            responseInfo = consumeProcess.process(urmStoe, cupMerchant, requestInfo);
        } catch (TransferFailedException e) {
            if (StringUtils.isNotBlank(requestInfo.getStatus()) && Const.TransStatus.UNKNOWN_STR.equals(requestInfo.getStatus())) {
                log.error("消费第三方报错");
                payment.setTransStatus(Const.TransStatus.UNKNOWN_STR);
                payment.setTransCode(e.getCode());
                payment.setTransMsg(e.getMessage());
                payment.setOutTransactionId(responseInfo.getOutTranstionId());
                return responseInfo;
            } else {
                request.setErrCodeType(requestInfo.getErrCodeType());
                throw e;
            }
        } catch (AppBizException e) {
            throw e;
        }
        if (StringUtils.isNotBlank(responseInfo.getBankType())) {
            payment.setBankType(responseInfo.getBankType());
            request.setBankType(responseInfo.getBankType());
            BigDecimal mrchRateAmt = null;
            BigDecimal orgRateAmt = null;
            BigDecimal chnRateAmt = null;
            if ("01".equals(responseInfo.getBankType())) {
                if (null != requestInfo.getCupFeeBank()) {
                    mrchRateAmt = requestInfo.getAmount()
                            .multiply(requestInfo.getCupFeeBank())
                            .movePointLeft(3).setScale(4, RoundingMode.HALF_UP);
                    //商户费率
                    payment.setMrchRates(requestInfo.getCupFeeBank());
                    //如果封顶为空或者费率金额小于封顶则取费率金额
                    if (null==request.getCupFeeMax()||mrchRateAmt.compareTo(request.getCupFeeMax()) <= 0) {
                        //不高于封顶则取当前的
                        payment.setMrchRatesAmount(mrchRateAmt);
                    } else {
                        payment.setMrchRatesAmount(request.getCupFeeMax());
                    }
                }
                if (null != request.getCupOrgFeeBank()) {
                    //机构费率
                    //借记卡
                    orgRateAmt = request.getCupOrgFeeBank()
                            .movePointLeft(3)
                            .multiply(request.getAmount())
                            .setScale(4, RoundingMode.HALF_UP);
                    payment.setOrgFee(request.getCupOrgFeeBank());
                    if (null==request.getOrgFeeMax()||orgRateAmt.compareTo(request.getOrgFeeMax()) <= 0) {
                        //小于封顶
                        payment.setOrgFeeAmt(orgRateAmt);
                    } else {
                        payment.setOrgFeeAmt(request.getOrgFeeMax().setScale(4, RoundingMode.HALF_UP));

                    }
                }
                //如果是银联借记卡那么判断金额
                if (request.getAmount().compareTo(new BigDecimal("1000.00")) < 0) {
                    //小于1000
                    //通道费率
                    if (null != channelInfo.getYlMicroBankFee()) {
                        chnRateAmt = channelInfo.getYlMicroBankFee()
                                .movePointLeft(3)
                                .multiply(request.getAmount())
                                .setScale(4, RoundingMode.HALF_UP);
                        payment.setChnFee(channelInfo.getYlMicroBankFee());
                        if (null==channelInfo.getYlQrCommonBankFeeMax()||chnRateAmt.compareTo(channelInfo.getYlQrCommonBankFeeMax()) <= 0) {
                            //通道费率金额小于封顶
                            payment.setChnFeeAmt(chnRateAmt);
                        } else {
                            payment.setChnFeeAmt(channelInfo.getYlQrCommonBankFeeMax().setScale(4, RoundingMode.HALF_UP));
                        }
                    }

                } else {
                    //大于1000
                    if (null != channelInfo.getYlCommonBankFee()) {
                        chnRateAmt = channelInfo.getYlCommonBankFee()
                                .movePointLeft(3)
                                .multiply(request.getAmount())
                                .setScale(4, RoundingMode.HALF_UP);
                        payment.setChnFee(channelInfo.getYlMicroBankFee());
                        if (null==channelInfo.getYlQrCommonBankFeeMax()||chnRateAmt.compareTo(channelInfo.getYlQrCommonBankFeeMax()) <= 0) {
                            //通道费率金额小于封顶
                            payment.setChnFeeAmt(chnRateAmt);
                        } else {
                            payment.setChnFeeAmt(channelInfo.getYlQrCommonBankFeeMax().setScale(4, RoundingMode.HALF_UP));
                        }
                        payment.setChnFee(channelInfo.getYlCommonBankFee())
                                .setChnFeeAmt(channelInfo.getYlCommonBankFee()
                                        .movePointLeft(3)
                                        .multiply(request.getAmount())
                                        .setScale(4, RoundingMode.HALF_UP));
                    }
                }

            } else if ("02".equals(responseInfo.getBankType())) {
                if (null != requestInfo.getCupFeeCredict()) {
                    payment.setMrchRates(requestInfo.getCupFeeCredict());
                    payment.setMrchRatesAmount(requestInfo.getAmount()
                            .multiply(requestInfo.getCupFeeCredict())
                            .movePointLeft(3).setScale(4, RoundingMode.HALF_UP));
                    //如果是银联贷记卡
                    if (request.getAmount().compareTo(new BigDecimal("1000.00")) < 0) {
                        //小于1000
                        if (null != channelInfo.getYlMicroCredictFee()) {
                            payment.setChnFee(channelInfo.getYlMicroCredictFee())
                                    .setChnFeeAmt(channelInfo.getYlMicroCredictFee()
                                            .movePointLeft(3)
                                            .multiply(request.getAmount())
                                            .setScale(4, RoundingMode.HALF_UP));
                        }
                    } else {
                        if (null != channelInfo.getYlCommonCredictFee()) {
                            payment.setChnFee(channelInfo.getYlCommonCredictFee())
                                    .setChnFeeAmt(channelInfo.getYlCommonCredictFee()
                                            .movePointLeft(3)
                                            .multiply(request.getAmount())
                                            .setScale(4, RoundingMode.HALF_UP));
                        }
                    }

                }
            }
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
     * 功能：C扫B模式生成二维码
     *
     * @param urmStoe
     * @param request
     * @param payment
     * @return
     * @throws TransferFailedException
     * @throws AppBizException
     */
    @Override
    public ChannelResponseInfo qrCode(UrmStoe urmStoe, QrcodeRequest request, Payment payment) throws TransferFailedException, AppBizException {
        String payNo = payment.getPayNo();
        CupMerchant cupMerchant = innerRouteService.queryByMerIdAndStoreNo(request.getMerchantId(), urmStoe.getStoeId());
        if (null == cupMerchant) {
            throw new AppBizException(AppExCode.MERCHANT_NOT_EXIST, "商户信息未生效，请联系客户经理");
        }
        ChannelInfo channelInfo = innerTransService.queryChannel(OrgChannelType.CUP_CHANNEL.getOrgNo());
        ChannelRequestInfo requestInfo = ChannelRequestInfo
                .builder()
                .amount(request.getAmount())
                .payNo(payNo)
                .subppid(request.getSubAppid())
                .notifyUrl(request.getNotifyUrl())
                .screkey(cupMerchant.getCupPaySecretKey())
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
        UrmMinf urmMinf = innerRouteService.queryUrmMinf(request.getMerchantId());
        ChannelScanSupport scanSupport = innerTransService.queryChannelScanSupport(OrgChannelType.CUP_CHANNEL.getOrgNo(),urmMinf.getIncomType());
        if (null == scanSupport) {
            throw new AppBizException(AppExCode.MERCHANT_NOT_EXIST, "通道是否支持未配置，请联系客户经理");
        }
        String payChannel  = request.getPayChannel();
        if( PayChannelType.ALI_ZS.getCode().equals(payChannel)
                &&  (scanSupport.getQrCodeAliPay() == null || "0".equals(scanSupport.getQrCodeAliPay()))){
            throw new AppBizException(AppExCode.NO_SUUPEROT_SCAN, "交易不支持，请联系客户经理");
        }
        if(PayChannelType.WX_ZS.getCode().equals(payChannel)
                && (scanSupport.getQrCodeWxPay() == null || "0".equals(scanSupport.getQrCodeWxPay()))){
            throw new AppBizException(AppExCode.NO_SUUPEROT_SCAN, "交易不支持，请联系客户经理");
        }
        if(PayChannelType.CUP_ZS.getCode().equals(payChannel)
                && (scanSupport.getQrCodeCupPay() == null || "0".equals(scanSupport.getQrCodeCupPay()))){
            throw new AppBizException(AppExCode.NO_SUUPEROT_SCAN, "交易不支持，请联系客户经理");
        }
        PayChannelType payChannelType = PayChannelType.fromCode(request.getPayChannel());
        switch (payChannelType) {
            case ALI_ZS:
                if (null != channelInfo.getAliFee()) {
                    payment.setChnFee(channelInfo.getAliFee());
                    payment.setChnFeeAmt(channelInfo.getAliFee()
                            .movePointLeft(3)
                            .multiply(request.getAmount()
                                    .setScale(4, RoundingMode.HALF_UP)));
                }
                break;
            case WX_ZS:
                if (null != channelInfo.getWxFee()) {
                    payment.setChnFee(channelInfo.getWxFee());
                    payment.setChnFeeAmt(channelInfo.getWxFee()
                            .movePointLeft(3)
                            .multiply(request.getAmount()
                                    .setScale(4, RoundingMode.HALF_UP)));
                }
                break;
            default:
                break;

        }
        ChannelResponseInfo responseInfo = null;
        try {
            responseInfo = qrCodeProcess.process(urmStoe, cupMerchant, requestInfo);
        } catch (TransferFailedException e) {
            if (StringUtils.isNotBlank(requestInfo.getStatus()) && Const.TransStatus.UNKNOWN_STR.equals(requestInfo.getStatus())) {
                log.error("主扫预订单第三方报错");
                payment.setTransStatus(Const.TransStatus.UNKNOWN_STR);
                payment.setTransCode(e.getCode());
                payment.setTransMsg(e.getMessage());
                payment.setOutTransactionId(responseInfo.getOutTranstionId());
                return responseInfo;
            } else {
                request.setErrCodeType(requestInfo.getErrCodeType());
                throw e;
            }
        } catch (AppBizException e) {
            throw e;
        }

        payment.setSubTransType(responseInfo.getSuperTransType());
        payment.setTransCode(Const.ISO_RESPCODE_00);
        payment.setRemark("下单成功");
        payment.setTransMsg("下单成功");
        payment.setTransStatus(Const.TransStatus.WAIT_PAY_STR);
        payment.setOutTransactionId(responseInfo.getOutTranstionId());
        payment.setBankType(responseInfo.getBankType());
        payment.setCodeUrl(responseInfo.getCodeUrl());
        payment.setCodeImg(responseInfo.getCodeImgUrl());
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
        String originPayNo = originPayment.getPayNo();
        String payNo = DateUtil.format(new Date(), "yyyyMMdd") + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 24);
        CupMerchant cupMerchant = innerRouteService.queryByMerIdAndStoreNo(request.getMerchantId(), urmStoe.getStoeId());
        if (null == cupMerchant) {
            throw new AppBizException(AppExCode.MERCHANT_NOT_EXIST, "商户信息未生效，请联系客户经理");
        }
//        if (Const.IS_ACTIVE != cupMerchant.getExamineStatus().intValue() || Const.IS_ACTIVE != cupMerchant.getActivateStatus().intValue()) {
//            throw new AppBizException(AppExCode.MERCHANT_NOT_EXIST, "商户信息未生效，请联系客户经理");
//        }
        UrmAgtfee urmAgtfee = innerRouteService.queryByOrgNo(request.getOrgNo());
        if (null == urmAgtfee) {
            throw new AppBizException(AppExCode.ORG_ERR, "机构信息异常，请联系客户经理");
        }

        ChannelRequestInfo requestInfo = ChannelRequestInfo
                .builder()
                .originalPayNo(originPayNo)
                .screkey(cupMerchant.getCupPaySecretKey())
                .payNo(payNo)
                .amount(originPayment.getTransAmount())
                .build();
        if (null != request.getCupFeeBank()) {
            requestInfo.setCupFeeBank(request.getCupFeeBank());
        }
        if (null != request.getCupFeeCredict()) {
            requestInfo.setCupFeeCredict(request.getCupFeeCredict());
        }
        ChannelInfo channelInfo = innerTransService.queryChannel(OrgChannelType.CUP_CHANNEL.getOrgNo());
        ChannelResponseInfo responseInfo = new ChannelResponseInfo();

        QrChnFlowPa originChnFlow = innerRouteService.querySingleFlow(originPayNo, null, DateUtil.format(request.getOriTransDate(), "yyyyMMdd"));
        if (null == originChnFlow) {
            throw new AppBizException(AppExCode.ORDER_NOT_FOUND, "原交易不存在，请核实");
        }
        try {
            responseInfo = scanQueryProcess.process(cupMerchant, requestInfo, originChnFlow);
        } catch (TransferFailedException e) {
            throw e;
        } catch (AppBizException e) {
            throw e;
        }

        BigDecimal mrchRateAmt = null;
        BigDecimal orgRateAmt = null;
        BigDecimal chnRateAmt = null;
        if (StringUtils.isNotBlank(responseInfo.getBankType())) {
            request.setBankType(responseInfo.getBankType());
            originPayment.setBankType(responseInfo.getBankType());
            if ("01".equals(responseInfo.getBankType())) {
                if (null != requestInfo.getCupFeeBank()) {
                    //商户费率
                   mrchRateAmt= originPayment.getTransAmount()
                            .multiply(requestInfo.getCupFeeBank())
                            .movePointLeft(3).setScale(4, RoundingMode.HALF_UP);
                    originPayment.setMrchRates(requestInfo.getCupFeeBank());
                    if (null == request.getCupFeeMax() || mrchRateAmt.compareTo(request.getCupFeeMax()) <= 0) {
                        //不高于封顶则取当前的
                        originPayment.setMrchRatesAmount(mrchRateAmt);
                    } else {
                        originPayment.setMrchRatesAmount(request.getCupFeeMax());
                    }
                }
                //机构费率
                if (null != request.getCupOrgFeeBank()) {
                    //机构费率
                    //借记卡
                    orgRateAmt = request.getCupOrgFeeBank()
                            .movePointLeft(3)
                            .multiply(originPayment.getTransAmount())
                            .setScale(4, RoundingMode.HALF_UP);
                    originPayment.setOrgFee(request.getCupOrgFeeBank());
                    if (null == request.getOrgFeeMax() || orgRateAmt.compareTo(request.getOrgFeeMax()) <= 0) {
                        //小于封顶
                        originPayment.setOrgFeeAmt(orgRateAmt);
                    } else {
                        originPayment.setOrgFeeAmt(request.getOrgFeeMax().setScale(4, RoundingMode.HALF_UP));

                    }
                }
                //如果是银联借记卡那么判断金额
                if (originPayment.getTransAmount().compareTo(new BigDecimal("1000.00")) < 0) {
                    //小于1000
                    //通道费率
                    if (null != channelInfo.getYlMicroBankFee()) {
                        chnRateAmt = channelInfo.getYlMicroBankFee()
                                .movePointLeft(3)
                                .multiply(originPayment.getTransAmount())
                                .setScale(4, RoundingMode.HALF_UP);
                        originPayment.setChnFee(channelInfo.getYlMicroBankFee());
                        if (null == channelInfo.getYlQrCommonBankFeeMax() || chnRateAmt.compareTo(channelInfo.getYlQrCommonBankFeeMax()) <= 0) {
                            //通道费率金额小于封顶
                            originPayment.setChnFeeAmt(chnRateAmt);
                        } else {
                            originPayment.setChnFeeAmt(channelInfo.getYlQrCommonBankFeeMax().setScale(4, RoundingMode.HALF_UP));
                        }
                    }

                } else {
                    //大于1000
                    if (null != channelInfo.getYlCommonBankFee()) {
                        chnRateAmt = channelInfo.getYlCommonBankFee()
                                .movePointLeft(3)
                                .multiply(originPayment.getTransAmount())
                                .setScale(4, RoundingMode.HALF_UP);
                        originPayment.setChnFee(channelInfo.getYlMicroBankFee());
                        if (chnRateAmt.compareTo(channelInfo.getYlQrCommonBankFeeMax()) <= 0) {
                            //通道费率金额小于封顶
                            originPayment.setChnFeeAmt(chnRateAmt);
                        } else {
                            originPayment.setChnFeeAmt(channelInfo
                                    .getYlQrCommonBankFeeMax()
                                    .setScale(4, RoundingMode.HALF_UP));
                        }

                    }
                }

            } else if ("02".equals(responseInfo.getBankType())) {
                if (null != requestInfo.getCupFeeCredict()) {
                    //商户费率
                    originPayment.setMrchRates(requestInfo.getCupFeeCredict());
                    originPayment.setMrchRatesAmount(requestInfo.getAmount()
                            .multiply(requestInfo.getCupFeeCredict())
                            .movePointLeft(3).setScale(4, RoundingMode.HALF_UP));
                }
                if (originPayment.getTransAmount().compareTo(new BigDecimal("1000.00")) < 0) {
                    //小于1000
                    if (null != channelInfo.getYlMicroCredictFee()) {
                        originPayment.setChnFee(channelInfo.getYlMicroCredictFee())
                                .setChnFeeAmt(channelInfo.getYlMicroCredictFee()
                                        .movePointLeft(3)
                                        .multiply(originPayment.getTransAmount())
                                        .setScale(4, RoundingMode.HALF_UP));
                        originPayment.setOrgFee(urmAgtfee.getFeeUnionLoanFavour())
                                .setOrgFeeAmt(urmAgtfee.getFeeUnionLoanFavour()
                                        .movePointLeft(3)
                                        .multiply(originPayment.getTransAmount())
                                        .setScale(4, RoundingMode.HALF_UP));
                    }
                } else {
                    //大于1000
                    if (null != channelInfo.getYlCommonCredictFee()) {
                        originPayment.setChnFee(channelInfo.getYlCommonCredictFee())
                                .setChnFeeAmt(channelInfo.getYlCommonCredictFee()
                                        .movePointLeft(3)
                                        .multiply(originPayment.getTransAmount())
                                        .setScale(4, RoundingMode.HALF_UP));
                        originPayment.setOrgFee(urmAgtfee.getFeeUnionLoanNormal())
                                .setOrgFeeAmt(urmAgtfee.getFeeUnionLoanNormal()
                                        .movePointLeft(3)
                                        .multiply(originPayment.getTransAmount())
                                        .setScale(4, RoundingMode.HALF_UP));
                    }
                }
            }
        }
        //如果是微信、支付宝扫码支付
        String payChannel = originPayment.getPayChannel();
        if(payChannel.equals(PayChannelType.ALI_ZS.getCode())){
            originPayment.setSubTransType(PayType.ALI_SCAN_PAY.getCode());
        }else if(payChannel.equals(PayChannelType.WX_ZS.getCode())){
            originPayment.setSubTransType(PayType.WX_SCAN_PAY.getCode());
        }else{
            originPayment.setSubTransType(responseInfo.getSuperTransType());
        }
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
        QrChnFlowPa originChnFlow = innerRouteService.querySingleFlow(refundPayment.getOriginPayno(), null, DateUtil.format(refundRequest.getOriTransDate(), "yyyyMMdd"));
        if (null == originChnFlow) {
            throw new AppBizException(AppExCode.TRADE_NOT_EXIST, "原交易流水不存在");
        }
        String orginPayNo = originChnFlow.getOutTradeNo();
        CupMerchant cupMerchant = innerRouteService.queryByMerIdAndStoreNo(refundRequest.getMerchantId(), urmStoe.getStoeId());
        ChannelRequestInfo requestInfo = ChannelRequestInfo
                .builder()
                .amount(originChnFlow.getAmount())
                .originalPayNo(orginPayNo)
                .payNo(refundPayment.getPayNo())
                .refundAmt(refundRequest.getRefAmt())
                .screkey(cupMerchant.getCupPaySecretKey())
                .outRefundNo(refundPayment.getOrdNo())
                .transactionId(originChnFlow.getOutTransactionId())
                .operator(refundRequest.getOprId())
                .bankType(originChnFlow.getBankType())
                .build();
        refundRequest.setBankType(originChnFlow.getBankType());
//        oriHpsJnl.setChannelid()//平安取这个号
        ChannelResponseInfo responseInfo = new ChannelResponseInfo();
        try {
            responseInfo = refundProcess.process(cupMerchant, requestInfo, originChnFlow);
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
        String originPayNo = originPayment.getPayNo();
        String payNo = DateUtil.format(new Date(), "yyyyMMdd") + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 24);
        CupMerchant cupMerchant = innerRouteService.queryByMerIdAndStoreNo(request.getMerchantId(), urmStoe.getStoeId());
        ChannelRequestInfo requestInfo = ChannelRequestInfo
                .builder()
                .originalPayNo(originPayNo)
                .screkey(cupMerchant.getCupPaySecretKey())
                .payNo(payNo)
                .outRefundNo(originPayment.getPayNo())
                .build();
        ChannelResponseInfo responseInfo = new ChannelResponseInfo();

        QrChnFlowPa originChnFlow = innerRouteService.querySingleFlow(originPayNo, null, DateUtil.format(request.getOriTransDate(), "yyyyMMdd"));
        if (null == originChnFlow) {
            throw new AppBizException(AppExCode.ORDER_NOT_FOUND, "原交易不存在，请核实");
        }
        request.setBankType(originChnFlow.getBankType());
        try {
            responseInfo = scanRefundQueryProcess.process(cupMerchant, requestInfo, originChnFlow);
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

    @Override
    public ChannelResponseInfo jsapi(UrmStoe urmStoe, JsapiRequest request, Payment payment) throws TransferFailedException, AppBizException {
        String payNo = payment.getPayNo();
        CupMerchant cupMerchant = innerRouteService.queryByMerIdAndStoreNo(request.getMerchantId(), urmStoe.getStoeId());
        if (null == cupMerchant) {
            throw new AppBizException(AppExCode.MERCHANT_NOT_EXIST, "商户信息未生效，请联系客户经理");
        }
        ChannelInfo channelInfo = innerTransService.queryChannel(OrgChannelType.CUP_CHANNEL.getOrgNo());
        ChannelRequestInfo requestInfo = ChannelRequestInfo
                .builder()
                .amount(request.getAmount())
                .payNo(payNo)
                .subppid(request.getSubAppid())
                .notifyUrl(request.getNotifyUrl())
                .openid(request.getOpenid())
                .screkey(cupMerchant.getCupPaySecretKey())
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
        UrmMinf urmMinf = innerRouteService.queryUrmMinf(request.getMerchantId());
        ChannelScanSupport scanSupport = innerTransService.queryChannelScanSupport(OrgChannelType.CUP_CHANNEL.getOrgNo(),urmMinf.getIncomType());
        if (null == scanSupport) {
            throw new AppBizException(AppExCode.MERCHANT_NOT_EXIST, "通道是否支持未配置，请联系客户经理");
        }
        if(scanSupport.getSubScribePay() == null || ("0".equals(scanSupport.getSubScribePay()))){
            throw new AppBizException(AppExCode.NO_SUUPEROT_SCAN, "交易不支持，请联系客户经理");
        }
        PayChannelType payChannelType = PayChannelType.fromCode(request.getPayChannel());
        switch (payChannelType) {
            case WX_GZH:
                if (null != channelInfo.getWxTpFee()) {
                    payment.setChnFee(channelInfo.getWxTpFee());
                    payment.setChnFeeAmt(channelInfo.getWxTpFee()
                            .movePointLeft(3)
                            .multiply(request.getAmount()
                                    .setScale(4, RoundingMode.HALF_UP)));
                }
                break;
            case ALI_GZH:
                if (null != channelInfo.getAliJsFee()) {
                    payment.setChnFee(channelInfo.getAliJsFee());
                    payment.setChnFeeAmt(channelInfo.getAliJsFee()
                            .movePointLeft(3)
                            .multiply(request.getAmount()
                                    .setScale(4, RoundingMode.HALF_UP)));
                }
                break;
            default:
                break;

        }
        ChannelResponseInfo responseInfo = null;
        try {
            responseInfo = jsapiProcess.process(urmStoe, cupMerchant, requestInfo);
        } catch (TransferFailedException e) {
            if (StringUtils.isNotBlank(requestInfo.getStatus()) && Const.TransStatus.UNKNOWN_STR.equals(requestInfo.getStatus())) {
                log.error("主扫预订单第三方报错");
                payment.setTransStatus(Const.TransStatus.UNKNOWN_STR);
                payment.setTransCode(e.getCode());
                payment.setTransMsg(e.getMessage());
                payment.setOutTransactionId(responseInfo.getOutTranstionId());
                return responseInfo;
            } else {
                request.setErrCodeType(requestInfo.getErrCodeType());
                throw e;
            }
        } catch (AppBizException e) {
            throw e;
        }

        payment.setSubTransType(responseInfo.getSuperTransType());
        payment.setTransCode(Const.ISO_RESPCODE_00);
        payment.setRemark("下单成功");
        payment.setTransMsg("下单成功");
        payment.setTransStatus(Const.TransStatus.WAIT_PAY_STR);
        payment.setOutTransactionId(responseInfo.getOutTranstionId());
        payment.setBankType(responseInfo.getBankType());
        payment.setTokenId(responseInfo.getTokenId());
        payment.setPayInfo(responseInfo.getPayInfo());
        //回填数据
        request.setTransationId(responseInfo.getTransationId());
        request.setOutTranstionId(responseInfo.getOutTranstionId());
        request.setBankBillNo(responseInfo.getBanBillNo());

        return responseInfo;
    }
}
