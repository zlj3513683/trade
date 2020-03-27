package com.posppay.newpay.modules.xposp.innertrans;

import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.modules.xposp.entity.*;
import com.posppay.newpay.modules.xposp.transfer.model.req.*;

public interface InnerTransService {


    /**
     * 机构签到
     *
     * @throws AppBizException
     */
    boolean orgSignUp() throws AppBizException;

    /**
     * 消费
     *
     * @param order
     * @param urmStoe
     * @param request
     * @throws AppBizException
     */
    Payment consume(Order order, UrmStoe urmStoe, ConsumeRequest request, Payment payment) throws AppBizException;

    /**
     * C扫B模式生成动态二维码
     *
     * @param urmStoe
     * @param request
     * @throws AppBizException
     */
    Payment qrCode( Order order, UrmStoe urmStoe, QrcodeRequest request, Payment payment) throws AppBizException;

    /**
     *
     *
     * @param urmStoe
     * @param request
     * @throws AppBizException
     */
    Payment jsapi(Order order, UrmStoe urmStoe, JsapiRequest request, Payment payment) throws AppBizException;

    /**
     * B扫C状态查询
     * @param order
     * @param urmStoe
     * @param request
     * @param payment
     * @return
     * @throws AppBizException
     */
    Payment scanQuery(Order order, UrmStoe urmStoe, QueryRequest request, Payment payment) throws AppBizException;

    /**
     * B扫C状态查询
     * @param order
     * @param urmStoe
     * @param request
     * @param payment
     * @return
     * @throws AppBizException
     */
    Payment scanRefundQuery(Order order, UrmStoe urmStoe, QueryRequest request, Payment payment) throws AppBizException;

    /**
     * 退货
     * @param order
     * @param urmStoe
     * @param request
     * @param refundPayment
     * @return
     * @throws AppBizException
     */
    Payment refund(Order order, UrmStoe urmStoe, RefundRequest request, Payment refundPayment) throws AppBizException;


    /**
     * 查询通道费率
     */
    ChannelInfo queryChannel(String chnCode);

    /**
     * 查询渠道支持信息
     * @param chnCode
     * @return
     */
    ChannelScanSupport queryChannelScanSupport(String chnCode,String mercType);

}
