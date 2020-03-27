package com.posppay.newpay.modules.xposp.transfer.Impl;

import cn.hutool.core.date.DateUtil;
import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.common.utils.StringUtils;
import com.posppay.newpay.modules.model.PayChannelType;
import com.posppay.newpay.modules.model.TransType;
import com.posppay.newpay.modules.sdk.pingan.config.ParamConfig;
import com.posppay.newpay.modules.xposp.common.AppExCode;
import com.posppay.newpay.modules.xposp.common.Const;
import com.posppay.newpay.modules.xposp.dao.service.QrChnFlowPaTmpService;
import com.posppay.newpay.modules.xposp.dao.service.UrmMfeeService;
import com.posppay.newpay.modules.xposp.entity.*;
import com.posppay.newpay.modules.xposp.innertrans.InnerRouteService;
import com.posppay.newpay.modules.xposp.transfer.TransferServer;
import com.posppay.newpay.modules.xposp.transfer.model.req.*;
import com.posppay.newpay.modules.xposp.innertrans.InnerTransService;
import com.posppay.newpay.modules.xposp.utils.SnowIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * 交易相关的服务实现
 *
 * @author zengjw
 */
@Slf4j
@Service("transferServer")
public class TransferServerImpl implements TransferServer {
    @Resource(name = "innerTransService")
    private InnerTransService innerTransService;
    @Resource
    private UrmMfeeService urmMfeeService;

    @Resource
    private ParamConfig paramConfig;
    @Resource
    private QrChnFlowPaTmpService qrChnFlowPaTmpService;
    @Resource
    private InnerRouteService innerRouteService;

    /**
     * 消费业务层实现
     * 这里流水考虑到回填可以取请求的
     *
     * @param request
     * @throws AppBizException
     */
    @Override
    public Payment consume(ConsumeRequest request) throws AppBizException {
        //合法性校验
        UrmMinf urmMinf = innerRouteService.queryUrmMinf(request.getMerchantId());
        if (null == urmMinf) {
            throw new AppBizException(AppExCode.MERC_NOT_EXITS, "系统商户信息不存在");
        } else if (!"0".equals(urmMinf.getMercSts())) {
            throw new AppBizException(AppExCode.MERC_IS_STOP, "商户已停用");
        }
        request.setUrmMinf(urmMinf);
        if (!request.getOrgNo().equals(urmMinf.getOrgCod())) {
//            System.out.println(request.getOrgNo());
//            System.out.println(urmMinf.getOrgCod());
            throw new AppBizException(AppExCode.MERC_INST_ERR, "该商户机构信息有误,请联系客服经理");
        }
        UrmStoe urmStoe = innerRouteService.queryStore(request.getMerchantId(), request.getStoeNo());
        if (null == urmStoe) {
            throw new AppBizException(AppExCode.STOE_NOT_EXIST, "门店信息不存在");
        } else if (!"0".equals(urmStoe.getStoeSts())) {
            throw new AppBizException(AppExCode.STOE_IS_CLOSED, "门店已关闭");
        }

        UrmMstl urmMstl = innerRouteService.queryScanMstl(request.getMerchantId(), urmStoe.getStoeId());
        if (null == urmMstl) {
            throw new AppBizException(AppExCode.MSTL_NOT_EXITS, "结算信息有误");
        }
        UrmPrdt urmPrdt = innerRouteService.queryScanProdt(request.getMerchantId(), urmStoe.getStoeId());
        if (null == urmPrdt) {
            throw new AppBizException(AppExCode.MERC_NOT_SCAN, "该商户不存在扫码产品");
        }
        String transDate = null;
        if (null != request.getTransTime()) {
            try {
//                DateTime dateTime = DateUtil.parse(request.getTransTime(), "yyyyMMddHHmmss");
                transDate = DateUtil.format(request.getTransTime(), "yyyyMMdd");
            } catch (Exception e) {
                throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "请求参数有误");
            }
        }
        Payment payment = null;
        payment = innerRouteService.querySinglePayment(request.getOutOrderNo(), request.getOrgNo(), transDate);
        if (payment != null) {
            throw new AppBizException(AppExCode.ORDER_EXITS, "订单号重复");
        }
        Order order = createOrder(request, urmMinf, urmStoe, false);
        payment = createConsumePayment(request, order, TransType.CONSUME);
        try {
            payment = innerTransService.consume(order, urmStoe, request, payment);
        } catch (AppBizException e) {
            throw e;
        }
        return payment;

    }

    /**
     * 创建订单
     *
     * @param request 请求参数
     * @return 订单
     */
    private Order createOrder(ConsumeRequest request, UrmMinf urmMinf, UrmStoe urmStoe, boolean isPreOrder) {
        String ordNo = SnowIdWorker.getInstance(paramConfig.getWorkId(), paramConfig.getCenterId()).nextId();
        // 创建订单
        Order order = new Order();
        try {

            order.setOrdNo(ordNo)
                    .setOrdDesc(request.getBody())
                    .setOrdAmt(request.getAmount())
                    .setMrchName(urmMinf.getMercNm())
                    .setMrchNo(request.getMerchantId())
                    .setCurrency(request.getCurrency())
                    .setStoreNo(urmStoe.getStoeId())
                    .setStoreName(urmStoe.getStoeNm())
                    .setRefundBalance(BigDecimal.ZERO)
                    .setOrgNo(request.getOrgNo())
                    .setOrdStatus(isPreOrder ? Const.OrderStatus.NOT_PAY_STR : Const.OrderStatus.PROCESSING_STR);
            order = innerRouteService.addOrder(order);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return order;
    }

    private Order createOrder(JsapiRequest request, UrmMinf urmMinf, UrmStoe urmStoe, boolean isPreOrder) {
        String ordNo = SnowIdWorker.getInstance(paramConfig.getWorkId(), paramConfig.getCenterId()).nextId();
        // 创建订单
        Order order = new Order();
        try {

            order.setOrdNo(ordNo)
                    .setOrdDesc(request.getBody())
                    .setOrdAmt(request.getAmount())
                    .setMrchName(urmMinf.getMercNm())
                    .setMrchNo(request.getMerchantId())
                    .setCurrency(request.getCurrency())
                    .setStoreNo(urmStoe.getStoeId())
                    .setStoreName(urmStoe.getStoeNm())
                    .setRefundBalance(BigDecimal.ZERO)
                    .setOrgNo(request.getOrgNo())
                    .setOrdStatus(isPreOrder ? Const.OrderStatus.NOT_PAY_STR : Const.OrderStatus.PROCESSING_STR);
            order = innerRouteService.addOrder(order);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return order;
    }

    /**
     * 创建订单
     *
     * @param request 请求参数
     * @return 订单
     */
    private Order createOrder(QrcodeRequest request, UrmMinf urmMinf, UrmStoe urmStoe, boolean isPreOrder) {
        String ordNo = SnowIdWorker.getInstance(paramConfig.getWorkId(), paramConfig.getCenterId()).nextId();
        // 创建订单
        Order order = new Order();
        try {

            order.setOrdNo(ordNo)
                    .setOrdDesc(request.getBody())
                    .setOrdAmt(request.getAmount())
                    .setMrchName(urmMinf.getMercNm())
                    .setMrchNo(request.getMerchantId())
                    .setCurrency(request.getCurrency())
                    .setStoreNo(urmStoe.getStoeId())
                    .setStoreName(urmStoe.getStoeNm())
                    .setRefundBalance(BigDecimal.ZERO)
                    .setOrgNo(request.getOrgNo())
                    .setOrdStatus(isPreOrder ? Const.OrderStatus.NOT_PAY_STR : Const.OrderStatus.PROCESSING_STR);
            order = innerRouteService.addOrder(order);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return order;
    }


    /**
     * 创建平台流水
     *
     * @param request   请求
     * @param order     订单
     * @param transType 交易类型
     * @return 平台流水
     */
    private Payment createConsumePayment(ConsumeRequest request, Order order, TransType transType)
            throws AppBizException {
        BigDecimal feeRate = null;
        BigDecimal orgRate = null;
        BigDecimal orgRateAmt = null;
        UrmMfee urmMfee = urmMfeeService.findByMrchAndStoe(request.getMerchantId(), order.getStoreNo(), "1002");
        UrmAgtfee urmAgtfee = innerRouteService.queryByOrgNo(request.getOrgNo());
        if (null == urmAgtfee) {
            throw new AppBizException(AppExCode.ORG_ERR, "机构信息有误,请联系客户经理");
        }
        String payNo = SnowIdWorker.getInstance(paramConfig.getWorkId(), paramConfig.getCenterId()).nextId();
        Payment payment = new Payment();
        PayChannelType payChannelType = PayChannelType.fromCode(request.getPayChannel());
        BigDecimal rate = null;
        switch (payChannelType) {
            case CUP_CHANNEL:
                if (new BigDecimal("1000.00").compareTo(request.getAmount()) <= 0) {
                    //商户费率
                    request.setCupFeeBank(urmMfee.getFeeUnionScanBorrowNormal());
                    request.setCupFeeCredict(urmMfee.getFeeUnionCodeLoanNormal());
                    request.setCupFeeMax(urmMfee.getFeeUnionScanBorrowNormalMax());

                    //机构费率
                    request.setCupOrgFeeBank(urmAgtfee.getFeeUnionBorrowNormal());
                    request.setCupOrgFeeCredict(urmAgtfee.getFeeUnionLoanNormal());
                    request.setOrgFeeMax(urmAgtfee.getFeeUnionBorrowNormalMax());
                } else {
                    //商户费率
                    request.setCupFeeBank(urmMfee.getFeeUnionScanBorrowFavour());
                    request.setCupFeeCredict(urmMfee.getFeeUnionScanLoanFavour());
                    request.setCupFeeMax(urmMfee.getFeeUnionScanBorrowNormalMax());

                    //机构费率
                    request.setCupOrgFeeBank(urmAgtfee.getFeeUnionBorrowFavour());
                    request.setCupFeeCredict(urmAgtfee.getFeeUnionLoanFavour());
                    request.setOrgFeeMax(urmAgtfee.getFeeUnionBorrowNormalMax());

                }
//                if (new BigDecimal("1000.00").compareTo(request.getAmount()) <= 0) {
//                    request.setCupOrgFeeBank(urmAgtfee.getFeeUnionBorrowNormal());
//                    request.setCupOrgFeeCredict(urmAgtfee.getFeeUnionLoanNormal());
//                } else {
//                    request.setCupFeeBank(urmAgtfee.getFeeUnionBorrowFavour());
//                    request.setCupFeeCredict(urmMfee.getFeeUnionScanLoanFavour());
//                }
                break;
            case WX_CHANNEL:
                rate = urmMfee.getFeeWxCode();
                feeRate = rate
                        .movePointLeft(3)
                        .multiply(request.getAmount())
                        .setScale(4, RoundingMode.HALF_UP);
                orgRate = urmAgtfee.getFeeWx();
                orgRateAmt = orgRate
                        .movePointLeft(3)
                        .multiply(request.getAmount())
                        .setScale(4, RoundingMode.HALF_UP);
                payment.setMrchRates(rate);
                payment.setMrchRatesAmount(feeRate);
                payment.setOrgFee(orgRate);
                payment.setOrgFeeAmt(orgRateAmt);
                break;
            case ALI_CHANNEL:
                rate = urmMfee.getFeeAliCode();
                feeRate = rate
                        .movePointLeft(3)
                        .multiply(request.getAmount())
                        .setScale(4, RoundingMode.HALF_UP);
                orgRate = urmAgtfee.getFeeWx();
                orgRateAmt = orgRate
                        .movePointLeft(3)
                        .multiply(request.getAmount())
                        .setScale(4, RoundingMode.HALF_UP);
                payment.setMrchRates(rate);
                payment.setMrchRatesAmount(feeRate);
                payment.setOrgFee(orgRate);
                payment.setOrgFeeAmt(orgRateAmt);
                break;
        }
        Date transDate = null;
        try {

            transDate = request.getTransTime();
        } catch (Exception e) {
            log.error("交易时间格式有误");
            throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "请求参数有误");
        }
        payment.setMrchNo(request.getMerchantId())
                .setMrchName(order.getMrchName())
                .setCurrency(request.getCurrency())
                .setTransAmount(request.getAmount())
                .setTransStatus(Const.TransStatus.PREPARED_STR)
                .setStoreNo(order.getStoreNo())
                .setStoreName(order.getStoreName())
                .setTransTime(transDate)
                .setTransType(transType.getOrdinal())
                .setOutOrderNo(request.getOutOrderNo())
                .setOrdNo(order.getOrdNo())
                .setOrgNo(request.getOrgNo())
                .setPayNo(payNo)
                .setCreTime(new Date())
                .setOutBody(request.getBody())
                .setIpAndAddress(request.getIpAddress())
                .setPayChannel(request.getPayChannel())
                .setOutBody(request.getBody())
                .setAttach(request.getAttach());
        innerRouteService.addPaymet(payment);
        return payment;
    }

    /**
     * 创建平台流水
     *
     * @param request   请求
     * @param order     订单
     * @param transType 交易类型
     * @return 平台流水
     */
    private Payment createQrcodePayment(QrcodeRequest request, Order order, TransType transType)
            throws AppBizException {
        BigDecimal feeRate = null;
        BigDecimal orgRate = null;
        BigDecimal orgRateAmt = null;
        UrmMfee urmMfee = urmMfeeService.findByMrchAndStoe(request.getMerchantId(), order.getStoreNo(), "1002");
        UrmAgtfee urmAgtfee = innerRouteService.queryByOrgNo(request.getOrgNo());
        if (null == urmAgtfee) {
            throw new AppBizException(AppExCode.ORG_ERR, "机构信息有误,请联系客户经理");
        }
        String payNo = SnowIdWorker.getInstance(paramConfig.getWorkId(), paramConfig.getCenterId()).nextId();
        Payment payment = new Payment();
        PayChannelType payChannelType = PayChannelType.fromCode(request.getPayChannel());
        BigDecimal rate = null;
        switch (payChannelType) {
            case WX_ZS:
                rate = urmMfee.getFeeWxScan();
                feeRate = rate
                        .movePointLeft(3)
                        .multiply(request.getAmount())
                        .setScale(4, RoundingMode.HALF_UP);
                orgRate = urmAgtfee.getFeeWx();
                orgRateAmt = orgRate
                        .movePointLeft(3)
                        .multiply(request.getAmount())
                        .setScale(4, RoundingMode.HALF_UP);
                payment.setMrchRates(rate);
                payment.setMrchRatesAmount(feeRate);
                payment.setOrgFee(orgRate);
                payment.setOrgFeeAmt(orgRateAmt);
                break;
            case ALI_ZS:
                rate = urmMfee.getFeeAliScan();
                feeRate = rate
                        .movePointLeft(3)
                        .multiply(request.getAmount())
                        .setScale(4, RoundingMode.HALF_UP);
                orgRate = urmAgtfee.getFeeAli();
                orgRateAmt = orgRate
                        .movePointLeft(3)
                        .multiply(request.getAmount())
                        .setScale(4, RoundingMode.HALF_UP);
                payment.setMrchRates(rate);
                payment.setMrchRatesAmount(feeRate);
                payment.setOrgFee(orgRate);
                payment.setOrgFeeAmt(orgRateAmt);
                break;
        }
        Date transDate = null;
        try {

            transDate = request.getTransTime();
        } catch (Exception e) {
            log.error("交易时间格式有误");
            throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "请求参数有误");
        }
        payment.setMrchNo(request.getMerchantId())
                .setMrchName(order.getMrchName())
                .setCurrency(request.getCurrency())
                .setTransAmount(request.getAmount())
                .setTransStatus(Const.TransStatus.PREPARED_STR)
                .setStoreNo(order.getStoreNo())
                .setStoreName(order.getStoreName())
                .setTransTime(transDate)
                .setTransType(transType.getOrdinal())
                .setOutOrderNo(request.getOutOrderNo())
                .setOrdNo(order.getOrdNo())
                .setOrgNo(request.getOrgNo())
                .setPayNo(payNo)
                .setCreTime(new Date())
                .setOutBody(request.getBody())
                .setIpAndAddress(request.getIpAddress())
                .setPayChannel(request.getPayChannel())
                .setOutBody(request.getBody())
                .setGoodsTag(request.getGoodsTag())
                .setGoodsDetail(request.getGoodsDetail())
                .setSubAppid(request.getSubAppid())
                .setAttach(request.getAttach());
        innerRouteService.addPaymet(payment);
        return payment;
    }

    private Payment createJsapiPayment(JsapiRequest request, Order order, TransType transType)
            throws AppBizException {
        BigDecimal feeRate = null;
        BigDecimal orgRate = null;
        BigDecimal orgRateAmt = null;
        UrmMfee urmMfee = urmMfeeService.findByMrchAndStoe(request.getMerchantId(), order.getStoreNo(), "1002");
        UrmAgtfee urmAgtfee = innerRouteService.queryByOrgNo(request.getOrgNo());
        if (null == urmAgtfee) {
            throw new AppBizException(AppExCode.ORG_ERR, "机构信息有误,请联系客户经理");
        }
        String payNo = SnowIdWorker.getInstance(paramConfig.getWorkId(), paramConfig.getCenterId()).nextId();
        Payment payment = new Payment();
        PayChannelType payChannelType = PayChannelType.fromCode(request.getPayChannel());
        BigDecimal rate = null;
        switch (payChannelType) {
            case WX_GZH:
                rate = urmMfee.getFeeWxTable();
                feeRate = rate
                        .movePointLeft(3)
                        .multiply(request.getAmount())
                        .setScale(4, RoundingMode.HALF_UP);
                orgRate = urmAgtfee.getFeeWx();
                orgRateAmt = orgRate
                        .movePointLeft(3)
                        .multiply(request.getAmount())
                        .setScale(4, RoundingMode.HALF_UP);
                payment.setMrchRates(rate);
                payment.setMrchRatesAmount(feeRate);
                payment.setOrgFee(orgRate);
                payment.setOrgFeeAmt(orgRateAmt);
                break;
            case ALI_GZH:
                rate = urmMfee.getFeeAliTable();
                feeRate = rate
                        .movePointLeft(3)
                        .multiply(request.getAmount())
                        .setScale(4, RoundingMode.HALF_UP);
                orgRate = urmAgtfee.getFeeAli();
                orgRateAmt = orgRate
                        .movePointLeft(3)
                        .multiply(request.getAmount())
                        .setScale(4, RoundingMode.HALF_UP);
                payment.setMrchRates(rate);
                payment.setMrchRatesAmount(feeRate);
                payment.setOrgFee(orgRate);
                payment.setOrgFeeAmt(orgRateAmt);
                break;
        }
        Date transDate = null;
        try {

            transDate = request.getTransTime();
        } catch (Exception e) {
            log.error("交易时间格式有误");
            throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "请求参数有误");
        }
        payment.setMrchNo(request.getMerchantId())
                .setMrchName(order.getMrchName())
                .setCurrency(request.getCurrency())
                .setTransAmount(request.getAmount())
                .setTransStatus(Const.TransStatus.PREPARED_STR)
                .setStoreNo(order.getStoreNo())
                .setStoreName(order.getStoreName())
                .setTransTime(transDate)
                .setTransType(transType.getOrdinal())
                .setOutOrderNo(request.getOutOrderNo())
                .setOrdNo(order.getOrdNo())
                .setOrgNo(request.getOrgNo())
                .setPayNo(payNo)
                .setCreTime(new Date())
                .setOutBody(request.getBody())
                .setIpAndAddress(request.getIpAddress())
                .setPayChannel(request.getPayChannel())
                .setOutBody(request.getBody())
                .setGoodsTag(request.getGoodsTag())
                .setGoodsDetail(request.getGoodsDetail())
                .setOpenid(request.getOpenid())
                .setSubAppid(request.getSubAppid())
                .setAttach(request.getAttach());
        innerRouteService.addPaymet(payment);
        return payment;
    }

    /**
     * 创建平台流水
     *
     * @param request 请求
     * @param order   订单
     * @param rate    费率
     * @return 平台流水
     */
    private Payment createRefundPayment(RefundRequest request, Order order, TransType transType, BigDecimal rate, String originPayNo)
            throws AppBizException {
        BigDecimal orgFee = request.getOrgFee();
        BigDecimal chnFee = request.getChnFee();
//        BigDecimal feeRate = innerRouteService.calculateRefundFee(request.getRefAmt(), rate);
        BigDecimal feeRate = request.getMrchRateAmt();
        String payNo = SnowIdWorker.getInstance(paramConfig.getWorkId(), paramConfig.getCenterId()).nextId();
        Payment payment = new Payment();
        payment.setMrchNo(request.getMerchantId())
                .setMrchName(order.getMrchName())
                .setCurrency(order.getCurrency())
                .setTransAmount(request.getRefAmt())
                .setTransStatus(Const.TransStatus.PREPARED_STR)
                .setStoreNo(order.getStoreNo())
                .setStoreName(order.getStoreName())
//                .setTransTime(DateUtil.parse(request.getTransTime(), "yyyyMMddHHmmss"))
                .setTransType(transType.getOrdinal())
                .setOutOrderNo(request.getOutOrderNo())
                .setOrdNo(order.getOrdNo())
                .setOrgNo(request.getOrgNo())
                .setPayNo(payNo)
                .setCreTime(new Date())
                .setOriginPayno(originPayNo)
                .setChnCode(request.getChnCode())
                .setSubTransType(request.getSubTransType())
                .setPayChannel(request.getPayChannel())
                .setIpAndAddress(request.getIpAddress())
                .setBankType(request.getBankType());
        if (null != feeRate) {
            payment.setMrchRatesAmount(feeRate);
        }
        if (null != rate) {
            payment.setMrchRates(rate);
        }
        if (null != orgFee) {
            payment.setOrgFee(orgFee);
            payment.setOrgFeeAmt(request.getOrgFeeAmt());
        }
        if (null != chnFee) {
            payment.setChnFee(chnFee);
            payment.setChnFeeAmt(request.getChnFeeAmt());
        }
        innerRouteService.addPaymet(payment);
        return payment;
    }


    @Override
    public Payment refund(RefundRequest request) throws AppBizException {

        UrmMinf urmMinf = innerRouteService.queryUrmMinf(request.getMerchantId());
        if (null == urmMinf) {
            throw new AppBizException("1601", "系统商户信息不存在");
        } else if (!"0".equals(urmMinf.getMercSts())) {
            throw new AppBizException("1602", "商户已停用");
        }
        if (!request.getOrgNo().equals(urmMinf.getOrgCod())) {
            throw new AppBizException(AppExCode.MERC_INST_ERR, "该商户不属于这个机构,请联系客服经理");
        }
        UrmStoe urmStoe = innerRouteService.queryStore(request.getMerchantId(), request.getStoeNo());
        if (null == urmStoe) {
            throw new AppBizException("150100", "门店信息不存在");
        } else if (!"0".equals(urmStoe.getStoeSts())) {
            throw new AppBizException("150101", "门店已关闭");
        }
        UrmMstl urmMstl = innerRouteService.queryScanMstl(request.getMerchantId(), urmStoe.getStoeId());
        if (null == urmMstl) {
            throw new AppBizException("170100", "结算信息有误");
        }

        UrmPrdt urmPrdt = innerRouteService.queryScanProdt(request.getMerchantId(), urmStoe.getStoeId());
        if (null == urmPrdt) {
            throw new AppBizException("180100", "该商户不存在扫码产品");
        }
        Payment oriPayment = null;
        if (StringUtils.isNotBlank(request.getOriPayNo())) {
            //根据平台凭证号来获取平台流水
            oriPayment = innerRouteService.querySinglePaymentByPayNo(request.getOriPayNo(), request.getOrgNo(), DateUtil.format(request.getOriTransDate(), "yyyyMMdd"));

        } else if (StringUtils.isNotBlank(request.getOriOutOrderNo())) {
            //根据下游流水号来获取流水
            oriPayment = innerRouteService.querySinglePayment(request.getOriOutOrderNo(), request.getOrgNo(), DateUtil.format(request.getOriTransDate(), "yyyyMMdd"));
        } else if (StringUtils.isNotBlank(request.getOriOutTransactionId())) {
            //根据第三方凭证号来获取原始流水
            oriPayment = innerRouteService.queryPaymentByTransationId(request.getOriOutTransactionId(), request.getOrgNo(), TransType.CONSUME.getOrdinal(), DateUtil.format(request.getOriTransDate(), "yyyyMMdd"));
            if (null == oriPayment) {
                //真对使用微信支付宝订单号来查询的商户
                throw new AppBizException(AppExCode.ORDER_NOT_FOUND, "交易订单不存在请检查微信支付宝第三方流水号或检查交易类型");
            }
        } else {
            throw new AppBizException(AppExCode.PARAMS_NULL, "请求数据有误");
        }
//        if (null == payment) {
//            throw new AppBizException(AppExCode.ORDER_NOT_FOUND, "交易订单不存在请检查微信支付宝第三方流水号或检查交易类型");
//        }
//        Payment oriPayment = innerTransService.querySinglePayment(request.getOriOutOrderNo(), request.getOrgNo(), request.getOriTransDate());
        if (null == oriPayment) {
            throw new AppBizException(AppExCode.ORDER_NOT_FOUND, "原交易不存在");
        }
        if (!Const.TransStatus.SUCCESS_STR.equals(oriPayment.getTransStatus())) {
            //如果原始状态不为成功则不允许退货
            throw new AppBizException(AppExCode.ERRSTATUS, "原交易未成功不允许退款");
        }
        //限制部分退款
        if (request.getRefAmt().compareTo(oriPayment.getTransAmount()) != 0) {
            throw new AppBizException(AppExCode.REFUND_NOT_ALLOW, "退款金额有误");
        }
        Order originOrder = innerRouteService.querySingleOrder(oriPayment.getOrdNo(), request.getOrgNo(), DateUtil.format(request.getOriTransDate(), "yyyyMMdd"));
        if (null == originOrder) {
            throw new AppBizException(AppExCode.ORDER_NOT_FOUND, "交易订单不存在");
        }
        if (originOrder.getRefundBalance().compareTo(request.getRefAmt()) < 0) {
            throw new AppBizException(AppExCode.ORDER_REFUND_LIMIT, "退款金额超限");
        }
        String transDate = null;
//        if (StringUtils.isNotBlank(request.getTransTime())) {
        try {
            Date dateTime = request.getTransTime();
            transDate = DateUtil.format(dateTime, "yyyyMMdd");
        } catch (Exception e) {
            throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "请求参数有误");
        }
//        }
        Payment payment = null;
        payment = innerRouteService.querySinglePayment(request.getOutOrderNo(), request.getOrgNo(), transDate);
        if (payment != null) {
            throw new AppBizException(AppExCode.ORDER_EXITS, "订单号重复");
        }
        request.setChnCode(oriPayment.getChnCode());
        request.setSubTransType(oriPayment.getSubTransType());
        request.setPayChannel(oriPayment.getPayChannel());
        request.setMrchRateAmt(oriPayment.getMrchRatesAmount());
        request.setOrgFee(oriPayment.getOrgFee());
        request.setOrgFeeAmt(oriPayment.getOrgFeeAmt());
        request.setChnFee(oriPayment.getChnFee());
        request.setChnFeeAmt(oriPayment.getChnFeeAmt());
        request.setBankType(oriPayment.getBankType());
        //新建退货平台流水
        Payment refundPayment = createRefundPayment(request, originOrder, TransType.REFUND, oriPayment.getMrchRates(), oriPayment.getPayNo());
        try {
            innerTransService.refund(originOrder, urmStoe, request, refundPayment);
        } catch (AppBizException e) {
            throw e;
        }
        return refundPayment;


//        HpsJnl hpsJnl = innerTransService.addScanRefundHpsFlow(urmStoe, urmMstl, urmMinf, request);
//        try {
//            innerTransService.refund(tmsPos, urmStoe, request, hpsJnl, organizationRoute);
//        } catch (AppBizException e) {
//            throw e;
//        }
//        return hpsJnl;
    }

    /**
     * C扫B生成二维码
     *
     * @param request
     * @return
     * @throws AppBizException
     */
    @Override
    public Payment qrCode(QrcodeRequest request) throws AppBizException {
        //合法性校验
        UrmMinf urmMinf = innerRouteService.queryUrmMinf(request.getMerchantId());
        if (null == urmMinf) {
            throw new AppBizException(AppExCode.MERC_NOT_EXITS, "系统商户信息不存在");
        } else if (!"0".equals(urmMinf.getMercSts())) {
            throw new AppBizException(AppExCode.MERC_IS_STOP, "商户已停用");
        }
        if (!request.getOrgNo().equals(urmMinf.getOrgCod())) {
            throw new AppBizException(AppExCode.MERC_INST_ERR, "该商户机构信息有误,请联系客服经理");
        }
        UrmStoe urmStoe = innerRouteService.queryStore(request.getMerchantId(), request.getStoeNo());
        if (null == urmStoe) {
            throw new AppBizException(AppExCode.STOE_NOT_EXIST, "门店信息不存在");
        } else if (!"0".equals(urmStoe.getStoeSts())) {
            throw new AppBizException(AppExCode.STOE_IS_CLOSED, "门店已关闭");
        }

        UrmMstl urmMstl = innerRouteService.queryScanMstl(request.getMerchantId(), urmStoe.getStoeId());
        if (null == urmMstl) {
            throw new AppBizException(AppExCode.MSTL_NOT_EXITS, "结算信息有误");
        }
        UrmPrdt urmPrdt = innerRouteService.queryScanProdt(request.getMerchantId(), urmStoe.getStoeId());
        if (null == urmPrdt) {
            throw new AppBizException(AppExCode.MERC_NOT_SCAN, "该商户不存在扫码产品");
        }
        String transDate = null;
        if (null != request.getTransTime()) {
            try {
//                DateTime dateTime = DateUtil.parse(request.getTransTime(), "yyyyMMddHHmmss");
                transDate = DateUtil.format(request.getTransTime(), "yyyyMMdd");
            } catch (Exception e) {
                throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "请求参数有误");
            }
        }
        Payment payment = null;
        payment = innerRouteService.querySinglePayment(request.getOutOrderNo(), request.getOrgNo(), transDate);
        if (payment != null) {
            throw new AppBizException(AppExCode.ORDER_EXITS, "订单号重复");
        }
        Order order = createOrder(request, urmMinf, urmStoe, false);
        payment = createQrcodePayment(request, order, TransType.CONSUME);
        try {
            payment = innerTransService.qrCode(order, urmStoe, request, payment);
        } catch (AppBizException e) {
            throw e;
        }
        return payment;

    }

    @Override
    public Payment jsapi(JsapiRequest request) throws AppBizException {
        //合法性校验
        UrmMinf urmMinf = innerRouteService.queryUrmMinf(request.getMerchantId());
        if (null == urmMinf) {
            throw new AppBizException(AppExCode.MERC_NOT_EXITS, "系统商户信息不存在");
        } else if (!"0".equals(urmMinf.getMercSts())) {
            throw new AppBizException(AppExCode.MERC_IS_STOP, "商户已停用");
        }
        if (!request.getOrgNo().equals(urmMinf.getOrgCod())) {
            throw new AppBizException(AppExCode.MERC_INST_ERR, "该商户机构信息有误,请联系客服经理");
        }
        UrmStoe urmStoe = innerRouteService.queryStore(request.getMerchantId(), request.getStoeNo());
        if (null == urmStoe) {
            throw new AppBizException(AppExCode.STOE_NOT_EXIST, "门店信息不存在");
        } else if (!"0".equals(urmStoe.getStoeSts())) {
            throw new AppBizException(AppExCode.STOE_IS_CLOSED, "门店已关闭");
        }

        UrmMstl urmMstl = innerRouteService.queryScanMstl(request.getMerchantId(), urmStoe.getStoeId());
        if (null == urmMstl) {
            throw new AppBizException(AppExCode.MSTL_NOT_EXITS, "结算信息有误");
        }
        UrmPrdt urmPrdt = innerRouteService.queryScanProdt(request.getMerchantId(), urmStoe.getStoeId());
        if (null == urmPrdt) {
            throw new AppBizException(AppExCode.MERC_NOT_SCAN, "该商户不存在扫码产品");
        }
        String transDate = null;
        if (null != request.getTransTime()) {
            try {
//                DateTime dateTime = DateUtil.parse(request.getTransTime(), "yyyyMMddHHmmss");
                transDate = DateUtil.format(request.getTransTime(), "yyyyMMdd");
            } catch (Exception e) {
                throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "请求参数有误");
            }
        }
        Payment payment = null;
        payment = innerRouteService.querySinglePayment(request.getOutOrderNo(), request.getOrgNo(), transDate);
        if (payment != null) {
            throw new AppBizException(AppExCode.ORDER_EXITS, "订单号重复");
        }
        Order order = createOrder(request, urmMinf, urmStoe, false);
        payment = createJsapiPayment(request, order, TransType.CONSUME);
        try {
            payment = innerTransService.jsapi(order, urmStoe, request, payment);
        } catch (AppBizException e) {
            throw e;
        }
        return payment;
    }

    /**
     * 扫码状态查询
     *
     * @param request
     * @return
     * @throws AppBizException
     */
    @Override
    public Payment scanQuery(QueryRequest request) throws AppBizException {
        //扫码查询先根据下游上送的c端流水号来查找原始流水
        //合法性校验
        UrmMinf urmMinf = innerRouteService.queryUrmMinf(request.getMerchantId());
        if (null == urmMinf) {
            throw new AppBizException("1601", "系统商户信息不存在");
        } else if (!"0".equals(urmMinf.getMercSts())) {
            throw new AppBizException("1602", "商户已停用");
        }
        if (!request.getOrgNo().equals(urmMinf.getOrgCod())) {
            throw new AppBizException(AppExCode.MERC_INST_ERR, "该商户不属于这个机构,请联系客服经理");
        }
        UrmStoe urmStoe = innerRouteService.queryStore(request.getMerchantId(), request.getStoeNo());
        if (null == urmStoe) {
            throw new AppBizException("1501", "门店信息不存在");
        } else if (!"0".equals(urmStoe.getStoeSts())) {
            throw new AppBizException("1502", "门店已关闭");
        }

        UrmMstl urmMstl = innerRouteService.queryScanMstl(request.getMerchantId(), urmStoe.getStoeId());
        if (null == urmMstl) {
            throw new AppBizException("1701", "结算信息有误");
        }
        UrmPrdt urmPrdt = innerRouteService.queryScanProdt(request.getMerchantId(), urmStoe.getStoeId());
        if (null == urmPrdt) {
            throw new AppBizException("1801", "该商户不存在扫码产品");
        }

        Payment payment = null;
        if (StringUtils.isNotBlank(request.getOriPayNo())) {
            //根据平台凭证号来获取平台流水
            payment = innerRouteService.querySinglePaymentByPayNo(request.getOriPayNo(), request.getOrgNo(), DateUtil.format(request.getOriTransDate(), "yyyyMMdd"));

        } else if (StringUtils.isNotBlank(request.getOriOutOrderNo())) {
            //根据下游流水号来获取流水
            payment = innerRouteService.querySinglePayment(request.getOriOutOrderNo(), request.getOrgNo(), DateUtil.format(request.getOriTransDate(), "yyyyMMdd"));
        } else if (StringUtils.isNotBlank(request.getOriOutTransactionId())) {
            //根据第三方凭证号来获取原始流水
            payment = innerRouteService.queryPaymentByTransationId(request.getOriOutTransactionId(), request.getOrgNo(), TransType.CONSUME.getOrdinal(), DateUtil.format(request.getOriTransDate(), "yyyyMMdd"));
            if (null == payment) {
                //真对使用微信支付宝订单号来查询的商户
                throw new AppBizException(AppExCode.ORDER_NOT_FOUND, "交易订单不存在请检查微信支付宝第三方流水号或检查交易类型");
            }
        } else {
            throw new AppBizException(AppExCode.PARAMS_NULL, "请求数据有误");
        }
        if (null == payment) {
            throw new AppBizException(AppExCode.ORDER_NOT_FOUND, "交易订单不存在请检查微信支付宝第三方流水号或检查交易类型");
        }
        Order originOrder = innerRouteService.querySingleOrder(payment.getOrdNo(), request.getOrgNo(), DateUtil.format(request.getOriTransDate(), "yyyyMMdd"));
        if (null == originOrder) {
            throw new AppBizException(AppExCode.ORDER_NOT_FOUND, "交易订单不存在");
        }
        if (TransType.CONSUME.getOrdinal().equals(payment.getTransType())) {
            if (Const.TransStatus.SUCCESS_STR.equals(payment.getTransStatus()) || Const.TransStatus.FAILED_STR.equals(payment.getTransStatus())) {
                QrChnFlowPaTmp qrChnFlowTmp = qrChnFlowPaTmpService.findBypayNo(payment.getPayNo());
                if (null != qrChnFlowTmp) {
                    try {
                        qrChnFlowPaTmpService.deleteByPayNo(qrChnFlowTmp.getOutTradeNo(), qrChnFlowTmp.getOrgNo());
                    } catch (Exception e) {
                        //如果删除失败则记录回滚原始订单信息
                        log.error("系统异常", e);
                    }
                }
                return payment;
            } else {
                payment = innerTransService.scanQuery(originOrder, urmStoe, request, payment);
                return payment;
            }
        } else {
            throw new AppBizException(AppExCode.TYPE_ERR, "交易类型有误");
        }
    }

    /**
     * 扫码状态查询
     *
     * @param request
     * @return
     * @throws AppBizException
     */
    @Override
    public Payment refundQuery(QueryRequest request) throws AppBizException {
        //扫码查询先根据下游上送的c端流水号来查找原始流水
        //合法性校验

        UrmMinf urmMinf = innerRouteService.queryUrmMinf(request.getMerchantId());
        if (null == urmMinf) {
            throw new AppBizException("1601", "系统商户信息不存在");
        } else if (!"0".equals(urmMinf.getMercSts())) {
            throw new AppBizException("1602", "商户已停用");
        }
        if (!request.getOrgNo().equals(urmMinf.getOrgCod())) {
            throw new AppBizException(AppExCode.MERC_INST_ERR, "该商户不属于这个机构,请联系客服经理");
        }
        UrmStoe urmStoe = innerRouteService.queryStore(request.getMerchantId(), request.getStoeNo());
        if (null == urmStoe) {
            throw new AppBizException("150100", "门店信息不存在");
        } else if (!"0".equals(urmStoe.getStoeSts())) {
            throw new AppBizException("150101", "门店已关闭");
        }

        UrmMstl urmMstl = innerRouteService.queryScanMstl(request.getMerchantId(), urmStoe.getStoeId());
        if (null == urmMstl) {
            throw new AppBizException("170100", "结算信息有误");
        }
        UrmPrdt urmPrdt = innerRouteService.queryScanProdt(request.getMerchantId(), urmStoe.getStoeId());
        if (null == urmPrdt) {
            throw new AppBizException("180100", "该商户不存在扫码产品");
        }

        Payment payment = null;
        if (StringUtils.isNotBlank(request.getOriPayNo())) {
            //根据平台凭证号来获取平台流水
            payment = innerRouteService.querySinglePaymentByPayNo(request.getOriPayNo(), request.getOrgNo(), DateUtil.format(request.getOriTransDate(), "yyyyMMdd"));

        } else if (StringUtils.isNotBlank(request.getOriOutOrderNo())) {
            //根据下游流水号来获取流水
            payment = innerRouteService.querySinglePayment(request.getOriOutOrderNo(), request.getOrgNo(), DateUtil.format(request.getOriTransDate(), "yyyyMMdd"));
        } else if (StringUtils.isNotBlank(request.getOriOutTransactionId())) {
            //根据第三方凭证号来获取原始流水
            payment = innerRouteService.queryPaymentByTransationId(request.getOriOutTransactionId(), request.getOrgNo(), TransType.REFUND.getOrdinal(), DateUtil.format(request.getOriTransDate(), "yyyyMMdd"));

        } else {
            throw new AppBizException(AppExCode.PARAMS_NULL, "请求数据有误");
        }
        if (null == payment) {
            throw new AppBizException(AppExCode.ORDER_NOT_FOUND, "交易订单不存在请检查微信支付宝第三方流水号或检查交易类型");
        }
        Order originOrder = innerRouteService.querySingleOrder(payment.getOrdNo(), request.getOrgNo(), DateUtil.format(request.getOriTransDate(), "yyyyMMdd"));
        if (null == originOrder) {
            throw new AppBizException(AppExCode.ORDER_NOT_FOUND, "交易订单不存在");
        }
        if (TransType.REFUND.getOrdinal().equals(payment.getTransType())) {
            if (Const.TransStatus.SUCCESS_STR.equals(payment.getTransStatus()) || Const.TransStatus.FAILED_STR.equals(payment.getTransStatus())) {
                QrChnFlowPaTmp qrChnFlowTmp = qrChnFlowPaTmpService.findBypayNo(payment.getPayNo());
                if (null != qrChnFlowTmp) {
                    try {
                        qrChnFlowPaTmpService.deleteByPayNo(qrChnFlowTmp.getOutTradeNo(), qrChnFlowTmp.getOrgNo());
                    } catch (Exception e) {
                        //如果删除失败则记录回滚原始订单信息
                        log.error("系统异常", e);
                    }
                }
                return payment;
            } else {
                payment = innerTransService.scanRefundQuery(originOrder, urmStoe, request, payment);
                return payment;
            }
        } else {
            throw new AppBizException(AppExCode.UNKNOWN, "当前订单并非退货订单请确认");
        }
    }
}
