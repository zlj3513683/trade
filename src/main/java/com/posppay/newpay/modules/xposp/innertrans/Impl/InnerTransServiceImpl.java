package com.posppay.newpay.modules.xposp.innertrans.Impl;

import cn.hutool.core.date.DateUtil;
import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.common.utils.StringUtils;
import com.posppay.newpay.modules.model.*;
import com.posppay.newpay.modules.sdk.pingan.model.ErrCodeType;
import com.posppay.newpay.modules.xposp.common.AppExCode;
import com.posppay.newpay.modules.xposp.common.Const;
import com.posppay.newpay.modules.xposp.dao.service.*;
import com.posppay.newpay.modules.xposp.entity.*;
import com.posppay.newpay.modules.xposp.innertrans.InnerRouteService;
import com.posppay.newpay.modules.xposp.innertrans.InnerTransService;
import com.posppay.newpay.modules.xposp.router.TransferChannel;
import com.posppay.newpay.modules.xposp.router.TransferFailedException;
import com.posppay.newpay.modules.xposp.router.TransferRouter;
import com.posppay.newpay.modules.xposp.transfer.model.req.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Slf4j
@Service("innerTransService")
public class InnerTransServiceImpl implements InnerTransService {

    @Resource
    private MercTransChnService mercTransChnService;
    @Resource
    private UrmMfeeService urmMfeeService;
    @Resource
    private TransferRouter transferRouter;
    @Resource
    private OrderService orderService;
    @Resource
    private PaymentService paymentService;
    @Resource
    private ChannelInfoService channelInfoService;
    @Resource
    private InnerRouteService innerRouteService;
    @Resource
    private ChannelScanSupportService channelScanSupportService;



    /**
     * 机构签到相关实现
     *
     * @throws AppBizException
     */
    @Override
    public boolean orgSignUp() throws AppBizException {
        return false;
    }

    /**
     * 消费
     *
     * @param urmStoe
     * @param consumeRequest
     * @throws AppBizException
     */
    @Override
    public Payment consume(Order order, UrmStoe urmStoe, ConsumeRequest consumeRequest, Payment payment) throws AppBizException {

        //获取扫码渠道真实的交易渠道路由
        MercTransChn mercTransChn = null;
        try {
            mercTransChn = mercTransChnService.findByMercId(consumeRequest.getMerchantId());
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "系统繁忙");
        }
        if (null == mercTransChn) {
            throw new AppBizException(AppExCode.MERC_NO_CHANNEL, "商户未指定交易渠道,请联系客户经理");
        }
        OrgChannelType orgChannelType = OrgChannelType.fromOrgNo(mercTransChn.getScanChn());
        if (null == orgChannelType) {
            throw new AppBizException(AppExCode.CHNNOTAPPLY, "机构异常请联系客服经理");
        }
        TransferChannel channel = transferRouter.findChannelByName(orgChannelType.getEngName());
        payment.setChnCode(orgChannelType.getOrgNo());
        order.setChnCode(orgChannelType.getOrgNo());
        try {
            channel.consume(urmStoe, consumeRequest, payment);
        } catch (TransferFailedException e) {
            if (e.getCode().equals(AppExCode.NETWORK_CONNECTION_FAILED)) {
                payment.setTransStatus(Const.TransStatus.UNKNOWN_STR);
                payment.setTransCode(e.getCode());
                payment.setTransMsg(e.getMessage());
                payment.setRemark(e.getMessage());
            } else {
                ErrCodeType errCodeType = ErrCodeType.fromTransCode(e.getCode());
                payment.setTransStatus(errCodeType.getStatus());
                payment.setTransCode(e.getCode());
                payment.setTransMsg(e.getMessage());
                payment.setRemark(e.getMessage());
            }
            log.error("[消费交易，发生业务异常，支付失败！]", e);
        } catch (AppBizException e) {
            payment.setTransStatus(Const.TransStatus.FAILED_STR);
            payment.setTransCode(e.getCode() + "");
            payment.setTransMsg(e.getMessage());
            payment.setRemark(e.getMessage());
            log.error("[消费交易，发生业务异常，支付失败！]", e);
        } catch (Exception e) {
            payment.setTransStatus(Const.TransStatus.UNKNOWN_STR);
            payment.setTransCode(AppExCode.UNKNOWN);
            payment.setTransMsg(e.getMessage());
            payment.setRemark(e.getMessage());
            //未知异常
            log.error("未知错误", e);
        } finally {
            updateConsumeFlow(payment, order);

        }
        return payment;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void updateConsumeFlow(Payment payment, Order order) {
        try {
            Date updDate = new Date();
            payment.setUpdTime(updDate);
            payment = paymentService.updatePayment(payment);
            if (Const.TransStatus.SUCCESS_STR.equals(payment.getTransStatus())) {
                order.setOrdStatus(Const.OrderStatus.PAYED_STR);
                order.setRefundBalance(order.getOrdAmt());
            } else if (Const.TransStatus.FAILED_STR.equals(payment.getTransStatus())) {
                order.setOrdStatus(Const.OrderStatus.PAY_FAILED_STR);
            } else if (Const.TransStatus.UNKNOWN_STR.equals(payment.getTransStatus())) {
                order.setOrdStatus(Const.OrderStatus.UNKNOWN_STR);
            } else if (Const.TransStatus.WAIT_PAY_STR.equals(payment.getTransStatus())) {
                order.setRefundBalance(order.getRefundBalance().subtract(payment.getTransAmount()));
                order.setOrdStatus(Const.OrderStatus.WAIT_PAY_STR);
            } else {
                log.error("[非法交易状态， status:" + payment.getTransStatus() + "]");
            }
            order.setUpdTime(new Date());
            orderService.updateOrder(order);
        } catch (Exception e) {
            log.error("未知错误", e);
            throw new AppBizException(AppExCode.UNKNOWN, "系统繁忙");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void updateRefundFlow(Payment payment, Order order) {
        try {
            Date updDate = new Date();
            payment.setUpdTime(updDate);
            payment = paymentService.updatePayment(payment);
            if (Const.TransStatus.SUCCESS_STR.equals(payment.getTransStatus())) {
                if (order.getRefundBalance().compareTo(payment.getTransAmount()) > 0) {
                    //部分退款
                    order.setRefundBalance(order.getRefundBalance().subtract(payment.getTransAmount()));
                    order.setOrdStatus(Const.OrderStatus.PARTIALLY_REFUNED_STR);
                } else {
                    //全额退款
                    order.setRefundBalance(order.getRefundBalance().subtract(payment.getTransAmount()));
                    order.setOrdStatus(Const.OrderStatus.REFUNED_STR);
                }
            } else if (Const.TransStatus.FAILED_STR.equals(payment.getTransStatus())) {
                log.error("退货交易失败");
            } else if (Const.TransStatus.UNKNOWN_STR.equals(payment.getTransStatus())) {
                order.setRefundBalance(order.getRefundBalance().subtract(payment.getTransAmount()));
                order.setOrdStatus(Const.OrderStatus.UNKNOWN_STR);
            } else if (Const.TransStatus.WAIT_PAY_STR.equals(payment.getTransStatus())) {
                order.setRefundBalance(order.getRefundBalance().subtract(payment.getTransAmount()));
                order.setOrdStatus(Const.OrderStatus.WAIT_PAY_STR);
            } else {
                log.error("[非法交易状态， status:" + payment.getTransStatus() + "]");
            }
            order.setUpdTime(new Date());
            orderService.updateOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void updateRefundQueryFlow(Payment payment, Order order, String status) {
        try {
            Date updDate = new Date();
            payment.setUpdTime(updDate);
            payment = paymentService.updatePayment(payment);
            if (Const.TransStatus.SUCCESS_STR.equals(payment.getTransStatus())) {
                if (order.getRefundBalance().compareTo(payment.getTransAmount()) > 0) {
                    //部分退款
                    order.setRefundBalance(order.getRefundBalance().subtract(payment.getTransAmount()));
                    order.setOrdStatus(Const.OrderStatus.PARTIALLY_REFUNED_STR);
                } else {
                    //全额退款
                    order.setRefundBalance(order.getRefundBalance().subtract(payment.getTransAmount()));
                    order.setOrdStatus(Const.OrderStatus.REFUNED_STR);
                }
            } else if (Const.TransStatus.FAILED_STR.equals(payment.getTransStatus())) {
                if (status.equals("2")) {
                    order.setRefundBalance(order.getRefundBalance().add(payment.getTransAmount()));
                    order.setOrdStatus(Const.OrderStatus.PAYED_STR);
                }
                log.error("退货交易失败");
            } else if (Const.TransStatus.UNKNOWN_STR.equals(payment.getTransStatus())) {
                order.setRefundBalance(order.getRefundBalance().subtract(payment.getTransAmount()));
                order.setOrdStatus(Const.OrderStatus.UNKNOWN_STR);
            }else if (Const.TransStatus.WAIT_PAY_STR.equals(payment.getTransStatus())) {
                order.setRefundBalance(order.getRefundBalance().subtract(payment.getTransAmount()));
                order.setOrdStatus(Const.OrderStatus.WAIT_PAY_STR);
            } else {
                log.error("[非法交易状态， status:" + payment.getTransStatus() + "]");
            }
            order.setUpdTime(new Date());
            orderService.updateOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Payment qrCode(Order order, UrmStoe urmStoe, QrcodeRequest request, Payment payment) throws AppBizException {
        //获取主扫预订单渠道真实的交易渠道路由
        MercTransChn mercTransChn = null;
        try {
            mercTransChn = mercTransChnService.findByMercId(request.getMerchantId());
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "系统繁忙");
        }
        if (null == mercTransChn) {
            throw new AppBizException(AppExCode.MERC_NO_CHANNEL, "商户未指定交易渠道,请联系客户经理");
        }
        OrgChannelType orgChannelType = OrgChannelType.fromOrgNo(mercTransChn.getScanChn());
        if (null == orgChannelType) {
            throw new AppBizException(AppExCode.CHNNOTAPPLY, "机构异常请联系客服经理");
        }
        TransferChannel channel = transferRouter.findChannelByName(orgChannelType.getEngName());
        payment.setChnCode(orgChannelType.getOrgNo());
        order.setChnCode(orgChannelType.getOrgNo());
        try {
            channel.qrCode(urmStoe, request, payment);
        } catch (TransferFailedException e) {
            if (e.getCode().equals(AppExCode.NETWORK_CONNECTION_FAILED)) {
                payment.setTransStatus(Const.TransStatus.UNKNOWN_STR);
                payment.setTransCode(e.getCode());
                payment.setTransMsg(e.getMessage());
                payment.setRemark(e.getMessage());
            } else {
                ErrCodeType errCodeType = ErrCodeType.fromTransCode(e.getCode());
                payment.setTransStatus(errCodeType.getStatus());
                payment.setTransCode(e.getCode());
                payment.setTransMsg(e.getMessage());
                payment.setRemark(e.getMessage());
            }
            log.error("[主扫预订单，发生业务异常，支付失败！]", e);
        } catch (AppBizException e) {
            payment.setTransStatus(Const.TransStatus.FAILED_STR);
            payment.setTransCode(e.getCode() + "");
            payment.setTransMsg(e.getMessage());
            payment.setRemark(e.getMessage());
            log.error("[主扫预订单，发生业务异常，支付失败！]", e);
        } catch (Exception e) {
            payment.setTransStatus(Const.TransStatus.UNKNOWN_STR);
            payment.setTransCode(AppExCode.UNKNOWN);
            payment.setTransMsg(e.getMessage());
            payment.setRemark(e.getMessage());
            //未知异常
            log.error("未知错误", e);
        } finally {
            updateConsumeFlow(payment, order);

        }
        return payment;
    }

    @Override
    public Payment jsapi(Order order, UrmStoe urmStoe, JsapiRequest request, Payment payment) throws AppBizException {
        //获取主扫预订单渠道真实的交易渠道路由
        MercTransChn mercTransChn = null;
        try {
            mercTransChn = mercTransChnService.findByMercId(request.getMerchantId());
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "系统繁忙");
        }
        if (null == mercTransChn) {
            throw new AppBizException(AppExCode.MERC_NO_CHANNEL, "商户未指定交易渠道,请联系客户经理");
        }
        OrgChannelType orgChannelType = OrgChannelType.fromOrgNo(mercTransChn.getScanChn());
        if (null == orgChannelType) {
            throw new AppBizException(AppExCode.CHNNOTAPPLY, "机构异常请联系客服经理");
        }
        TransferChannel channel = transferRouter.findChannelByName(orgChannelType.getEngName());
        payment.setChnCode(orgChannelType.getOrgNo());
        order.setChnCode(orgChannelType.getOrgNo());
        try {
            channel.jsapi(urmStoe, request, payment);
        } catch (TransferFailedException e) {
            if (e.getCode().equals(AppExCode.NETWORK_CONNECTION_FAILED)) {
                payment.setTransStatus(Const.TransStatus.UNKNOWN_STR);
                payment.setTransCode(e.getCode());
                payment.setTransMsg(e.getMessage());
                payment.setRemark(e.getMessage());
            } else {
                ErrCodeType errCodeType = ErrCodeType.fromTransCode(e.getCode());
                payment.setTransStatus(errCodeType.getStatus());
                payment.setTransCode(e.getCode());
                payment.setTransMsg(e.getMessage());
                payment.setRemark(e.getMessage());
            }
            log.error("[公众号支付，发生业务异常，支付失败！]", e);
        } catch (AppBizException e) {
            payment.setTransStatus(Const.TransStatus.FAILED_STR);
            payment.setTransCode(e.getCode() + "");
            payment.setTransMsg(e.getMessage());
            payment.setRemark(e.getMessage());
            log.error("[公众号支付，发生业务异常，支付失败！]", e);
        } catch (Exception e) {
            payment.setTransStatus(Const.TransStatus.UNKNOWN_STR);
            payment.setTransCode(AppExCode.UNKNOWN);
            payment.setTransMsg(e.getMessage());
            payment.setRemark(e.getMessage());
            //未知异常
            log.error("未知错误", e);
        } finally {
            updateConsumeFlow(payment, order);

        }
        return payment;
    }

    @Override
    public Payment scanQuery(Order order, UrmStoe urmStoe, QueryRequest request, Payment payment) throws AppBizException {

        //获取扫码渠道真实的交易渠道路由
//        MercTransChn mercTransChn = mercTransChnService.findByMercId(request.getMerchantId());
//        OrgChannelType orgChannelType = OrgChannelType.fromOrgNo(mercTransChn.getScanChn());
//        TransferChannel channel = transferRouter.findChannelByName(orgChannelType.getEngName());
        OrgChannelType orgChannelType = null;
        TransferChannel channel = null;
        if (StringUtils.isNotBlank(order.getChnCode())) {
            orgChannelType = OrgChannelType.fromOrgNo(order.getChnCode());
            channel = transferRouter.findChannelByName(orgChannelType.getEngName());
        }else {
            throw new AppBizException(AppExCode.UNKNOWN,"系统繁忙");
        }
        //---------------------------统计费率和费率金额----------------------------------
        BigDecimal feeRate = null;
        UrmMfee urmMfee = urmMfeeService.findByMrchAndStoe(request.getMerchantId(), order.getStoreNo(), "1002");
        UrmAgtfee urmAgtfee = innerRouteService.queryByOrgNo(request.getOrgNo());
        if (null == urmAgtfee) {
            throw new AppBizException(AppExCode.ORG_ERR, "机构信息有误,请联系客户经理");
        }
        PayChannelType payChannelType = PayChannelType.fromCode(payment.getPayChannel());
        BigDecimal rate = null;
        switch (payChannelType) {
            case CUP_CHANNEL:
                if (new BigDecimal("1000.00").compareTo(payment.getTransAmount()) <= 0) {
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
                    request.setCupOrgFeeCredict(urmAgtfee.getFeeUnionLoanFavour());
                    request.setOrgFeeMax(urmAgtfee.getFeeUnionBorrowNormalMax());

                }
                break;
            case WX_CHANNEL:
                rate = urmMfee.getFeeWxCode();
                feeRate = rate
                        .movePointLeft(3)
                        .multiply(payment.getTransAmount())
                        .setScale(4, RoundingMode.HALF_UP);
                payment.setMrchRates(rate);
                payment.setMrchRatesAmount(feeRate);
                break;
            case ALI_CHANNEL:
                rate = urmMfee.getFeeAliCode();
                feeRate = rate
                        .movePointLeft(3)
                        .multiply(payment.getTransAmount())
                        .setScale(4, RoundingMode.HALF_UP);
                payment.setMrchRates(rate);
                payment.setMrchRatesAmount(feeRate);
                break;
            case CUP_ZS:
                if (new BigDecimal("1000.00").compareTo(payment.getTransAmount()) <= 0) {
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
                    request.setCupOrgFeeCredict(urmAgtfee.getFeeUnionLoanFavour());
                    request.setOrgFeeMax(urmAgtfee.getFeeUnionBorrowNormalMax());

                }
                break;
            case WX_ZS:
                rate = urmMfee.getFeeWxScan();
                feeRate = rate
                        .movePointLeft(3)
                        .multiply(payment.getTransAmount())
                        .setScale(4, RoundingMode.HALF_UP);
                payment.setMrchRates(rate);
                payment.setMrchRatesAmount(feeRate);
                break;
            case ALI_ZS:
                rate = urmMfee.getFeeAliScan();
                feeRate = rate
                        .movePointLeft(3)
                        .multiply(payment.getTransAmount())
                        .setScale(4, RoundingMode.HALF_UP);
                payment.setMrchRates(rate);
                payment.setMrchRatesAmount(feeRate);
                break;
            case CUP_GZH:
                if (new BigDecimal("1000.00").compareTo(payment.getTransAmount()) <= 0) {
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
                    request.setCupOrgFeeCredict(urmAgtfee.getFeeUnionLoanFavour());
                    request.setOrgFeeMax(urmAgtfee.getFeeUnionBorrowNormalMax());

                }
                break;
            case WX_GZH:
                rate = urmMfee.getFeeWxTable();
                feeRate = rate
                        .movePointLeft(3)
                        .multiply(payment.getTransAmount())
                        .setScale(4, RoundingMode.HALF_UP);
                payment.setMrchRates(rate);
                payment.setMrchRatesAmount(feeRate);
                break;
            case ALI_GZH:
                rate = urmMfee.getFeeAliTable();
                feeRate = rate
                        .movePointLeft(3)
                        .multiply(payment.getTransAmount())
                        .setScale(4, RoundingMode.HALF_UP);
                payment.setMrchRates(rate);
                payment.setMrchRatesAmount(feeRate);
                break;
        }
        try {
            channel.scanQuery(urmStoe, request, payment);
        } catch (TransferFailedException e) {
            if (e.getCode().equals(AppExCode.NETWORK_CONNECTION_FAILED)) {
                throw new AppBizException(AppExCode.NETWORK_CONNECTION_FAILED, "网络连接超时");
            } else {
                ErrCodeType errCodeType = ErrCodeType.fromTransCode(e.getCode());
                if (!Const.TransStatus.UNKNOWN_STR.equals(errCodeType.getStatus())) {
                    payment.setTransStatus(errCodeType.getStatus());
                    payment.setTransCode(e.getCode());
                    payment.setTransMsg(e.getMessage());
                    payment.setRemark(e.getMessage());
                }
            }
            log.error("[消费交易，发生业务异常，支付失败！]", e);
        } catch (AppBizException e) {
            payment.setTransStatus(Const.TransStatus.UNKNOWN_STR);
            payment.setTransCode(e.getCode() + "");
            payment.setTransMsg(e.getMessage());
            payment.setRemark(e.getMessage());
            log.error("[消费交易，发生业务异常，支付失败！]", e);
        } catch (Exception e) {
            payment.setTransStatus(Const.TransStatus.UNKNOWN_STR);
            payment.setTransCode(AppExCode.UNKNOWN);
            payment.setTransMsg(e.getMessage());
            payment.setRemark(e.getMessage());
            //未知异常
            log.error("未知错误", e);
        } finally {
            updateConsumeFlow(payment, order);

        }
        return payment;
    }

    /**
     * B扫C状态查询
     *
     * @param order
     * @param urmStoe
     * @param request
     * @param payment
     * @return
     * @throws AppBizException
     */
    @Override
    public Payment scanRefundQuery(Order order, UrmStoe urmStoe, QueryRequest request, Payment payment) throws AppBizException {
        //获取扫码渠道真实的交易渠道路由
//        MercTransChn mercTransChn = mercTransChnService.findByMercId(request.getMerchantId());
//        OrgChannelType orgChannelType = OrgChannelType.fromOrgNo(mercTransChn.getScanChn());
//        TransferChannel channel = transferRouter.findChannelByName(orgChannelType.getEngName());
        OrgChannelType orgChannelType = null;
        TransferChannel channel = null;
        if (StringUtils.isNotBlank(order.getChnCode())) {
            orgChannelType = OrgChannelType.fromOrgNo(order.getChnCode());
            channel = transferRouter.findChannelByName(orgChannelType.getEngName());
        }else {
            throw new AppBizException(AppExCode.UNKNOWN,"系统繁忙");
        }
        String status = "";
        try {
            channel.sanRefundQuery(urmStoe, request, payment, status);
        } catch (TransferFailedException e) {
            //目前暂定TransferFailedException为第三方错误
            log.error("第三方平台报错", e);
//            不对原始流水处理继续发起查询
            ErrCodeType errCodeType = ErrCodeType.fromTransCode(e.getCode());
            payment.setTransStatus(errCodeType.getStatus())
                    .setTransCode(errCodeType.getTransCode())
                    .setRemark(errCodeType.getChnName())
                    .setTransMsg(errCodeType.getChnName());
//            System.out.println(e.getCode());
            if (e.getCode().equals(ErrCodeType.REFUNDNOTEXITS.getTransCode())) {
                status = "2";
            } else {
                status = "3";
            }
//            throw e;
        } catch (AppBizException e) {
            //目前暂定AppBizException为平台内部错误
            payment.setRemark(e.getMsg())
                    .setTransCode(e.getCode())
                    .setTransStatus(Const.TransStatus.UNKNOWN_STR)
                    .setTransMsg(e.getMsg());
            log.error("平台内部出错", e);
            throw e;
        } catch (Exception e) {
            status = "3";
            payment.setTransStatus(Const.TransStatus.UNKNOWN_STR);
            payment.setTransMsg("未知错误");
            payment.setRemark("未知错误");
            //未知异常
            log.error("未知错误", e);
            throw e;
        } finally {
            updateRefundQueryFlow(payment, order, status);
        }
        return payment;
    }

    /**
     * 扫码退货
     *
     * @param urmStoe
     * @param refundRequest
     * @param refundPayment
     * @param
     * @return
     * @throws AppBizException
     */
    @Override
    public Payment refund(Order order, UrmStoe urmStoe, RefundRequest refundRequest, Payment refundPayment) throws AppBizException {
        //获取扫码渠道真实的交易渠道路由
        OrgChannelType orgChannelType = null;
        TransferChannel channel = null;
        if (StringUtils.isNotBlank(order.getChnCode())) {
            orgChannelType = OrgChannelType.fromOrgNo(order.getChnCode());
            channel = transferRouter.findChannelByName(orgChannelType.getEngName());
        }else {
            throw new AppBizException(AppExCode.UNKNOWN,"系统繁忙");
        }

        try {
            channel.refund(urmStoe, refundRequest, refundPayment);
        } catch (TransferFailedException e) {
            //目前暂定TransferFailedException为第三方错误
            log.error("第三方平台报错", e);
            if (e.getCode().equals(AppExCode.NETWORK_CONNECTION_FAILED)) {
                refundPayment.setTransStatus(Const.TransStatus.UNKNOWN_STR);
                refundPayment.setTransCode(e.getCode());
                refundPayment.setTransMsg(e.getMessage());
                refundPayment.setRemark(e.getMessage());
            } else {
                ErrCodeType errCodeType = ErrCodeType.fromTransCode(e.getCode());
                refundPayment.setTransStatus(errCodeType.getStatus());
                refundPayment.setTransCode(e.getCode());
                refundPayment.setTransMsg(e.getMessage());
                refundPayment.setRemark(e.getMessage());
            }
//            throw e;

        } catch (AppBizException e) {
            if (e.getCode().equals(ResultStatus.PROCESS.getCode())) {
                refundPayment.setTransStatus(Const.TransStatus.UNKNOWN_STR);
            } else {
                refundPayment.setTransStatus(Const.TransStatus.FAILED_STR);
            }
            refundPayment.setTransCode(e.getCode() + "");
            refundPayment.setTransMsg(e.getMessage());
            refundPayment.setRemark(e.getMessage());
            log.error("[消费交易，发生业务异常，支付失败！]", e);
//            throw e;
        } catch (Exception e) {
            refundPayment.setTransStatus(Const.TransStatus.UNKNOWN_STR);
            refundPayment.setTransCode(AppExCode.UNKNOWN);
            refundPayment.setTransMsg(e.getMessage());
            refundPayment.setRemark(e.getMessage());
            //未知异常
            log.error("未知错误", e);
//            throw e;
        } finally {
            updateRefundFlow(refundPayment, order);

        }
        return refundPayment;
    }

//    /**
//     * 参数下载/同步
//     *
//     * @param request
//     * @throws AppBizException
//     */
//    @Override
//    public ParamLoadResponse paramLoad(ParamLoadRequest request) throws AppBizException, UnsupportedEncodingException {
//        String paramMsg = "00000000000000000000";
//        TmsPos tmsPos = this.queryTrmnl(request.getSn());
//        UrmStoe urmStoe = this.queryStore(tmsPos.getStoeId());
//        List<PrdtPerm> prdtPerms = this.queeyPrdtPermisson(request.getMerchantId(), tmsPos.getStoeId(), "1002");
//        for (PrdtPerm prdtPerm : prdtPerms) {
//            PrdtPerMissionType perMissionType = PrdtPerMissionType.getValue(prdtPerm.getTranTyp());
//            switch (perMissionType) {
//                case POS_CONSUME:
//                    paramMsg.replace(paramMsg.substring(perMissionType.getIndex() - 1, perMissionType.getIndex()), perMissionType.getValue());
//                    break;
//                case POS_CANCEL:
//                    paramMsg.replace(paramMsg.substring(perMissionType.getIndex() - 1, perMissionType.getIndex()), perMissionType.getValue());
//                    break;
//                case POS_AUTH:
//                    paramMsg.replace(paramMsg.substring(perMissionType.getIndex() - 1, perMissionType.getIndex()), perMissionType.getValue());
//                    break;
//                case POS_AUTHCANCEL:
//                    paramMsg.replace(paramMsg.substring(perMissionType.getIndex() - 1, perMissionType.getIndex()), perMissionType.getValue());
//                    break;
//                case POS_AUTHCOMPLETE:
//                    paramMsg.replace(paramMsg.substring(perMissionType.getIndex() - 1, perMissionType.getIndex()), perMissionType.getValue());
//                    break;
//                case POS_AUTHCOMPLETE_CANCEL:
//                    paramMsg.replace(paramMsg.substring(perMissionType.getIndex() - 1, perMissionType.getIndex()), perMissionType.getValue());
//                    break;
//                case POS_REFUND:
//                    paramMsg.replace(paramMsg.substring(perMissionType.getIndex() - 1, perMissionType.getIndex()), perMissionType.getValue());
//                    break;
//                case POS_BALANCE:
//                    paramMsg.replace(paramMsg.substring(perMissionType.getIndex() - 1, perMissionType.getIndex()), perMissionType.getValue());
//                    break;
//                case POS_SCAN:
//                    paramMsg.replace(paramMsg.substring(perMissionType.getIndex() - 1, perMissionType.getIndex() + 1), perMissionType.getValue());
//                    break;
//                case POS_QRCODE:
//                    paramMsg.replace(paramMsg.substring(perMissionType.getIndex() - 1, perMissionType.getIndex() + 1), perMissionType.getValue());
//                    break;
//                case POS_SCAN_REFUND:
//                    paramMsg.replace(paramMsg.substring(perMissionType.getIndex() - 1, perMissionType.getIndex()), perMissionType.getValue());
//                    break;
//                case ULINK_FLOW_PAY:
//                    paramMsg.replace(paramMsg.substring(perMissionType.getIndex() - 1, perMissionType.getIndex()), perMissionType.getValue());
//                    break;
//                default:
//                    break;
//
//            }
//            if (urmStoe.getRetgoodSts().equals(Const.StarPos.IS_ACTIVE)) {
//                paramMsg.replace(paramMsg.substring(PrdtPerMissionType.POS_REFUND.getIndex() - 1, PrdtPerMissionType.POS_REFUND.getIndex()), PrdtPerMissionType.POS_REFUND.getValue());
//
//            }
//        }
//        ParamLoadResponse response = ParamLoadResponse
//                .builder()
//                .txnList(paramMsg)
//                .merNam(URLDecoder.decode(urmStoe.getStoeNm(), "UTF-8"))
//                .scanMerNam(URLDecoder.decode(urmStoe.getScanStoeCnm(), "UTF-8"))
//                .servList("00000000000000000000")
//                .supAutOut("0")
//                .supInputCard("1")
//                .supTip("0")
//                .timeOut("30")
//                .tipPer("00")
//                .trmTyp("60")
//                .retranNm("3")
//                .retryNum("3")
//                .phNoMng("1234567891345")
//                .phNo3("1234567891345")
//                .phNo2("1234567891345")
//                .phNo1("1234567891345")
//                .binflg(tmsPos.getBinFlg())
//                .keyIndex("2")
//                .build();
//        return response;
//
//    }




    /**
     * 查询通道费率
     *
     * @param chnCode
     */
    @Override
    public ChannelInfo queryChannel(String chnCode) {
        ChannelInfo channelInfo = null;
        try {

            channelInfo = channelInfoService.findByChnCode(chnCode);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.CHN_ERR, "通道信息有误，请联系客户经理");
        }
        return channelInfo;
    }

    @Override
    public ChannelScanSupport queryChannelScanSupport(String chnCode,String mercType) {
        ChannelScanSupport channelScanSupport = null;
        try {

            channelScanSupport = channelScanSupportService.findBychnCode(chnCode,mercType);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.CHN_ERR, "通道信息有误，请联系客户经理");
        }
        return channelScanSupport;
    }
}
