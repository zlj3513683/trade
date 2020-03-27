package com.posppay.newpay.modules.xposp.router;


import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.modules.xposp.entity.UrmStoe;
import com.posppay.newpay.modules.xposp.entity.*;
import com.posppay.newpay.modules.xposp.router.channel.ChannelResponseInfo;
import com.posppay.newpay.modules.xposp.router.channel.MrchAndTrmnl;
import com.posppay.newpay.modules.xposp.transfer.model.req.*;

/**
 * 支付渠道定义<p>
 *
 * @author lance
 */
public interface TransferChannel {

    /**
     * 机构签到
     *
     * @param mrchAndTrmnl 商终信息
     * @return true-签到成功，false-签到失败
     * @throws TransferFailedException 交易异常
     * @throws AppBizException         业务异常
     */
    default boolean signUp(MrchAndTrmnl mrchAndTrmnl)
            throws TransferFailedException, AppBizException {
        return false;
    }

    /**
     * 扫码消费
     *
     * @param urmStoe
     * @param request
     * @throws TransferFailedException
     * @throws AppBizException
     */
    default ChannelResponseInfo consume( UrmStoe urmStoe, ConsumeRequest request, Payment payment)
            throws TransferFailedException, AppBizException {
        throw new AppBizException("201000", "该渠道不支持");
    }

    /**
     * 功能： C扫B模式生成二维码
     *
     * @param urmStoe
     * @param request
     * @param payment
     * @return
     * @throws TransferFailedException
     * @throws AppBizException
     */
    default ChannelResponseInfo qrCode( UrmStoe urmStoe, QrcodeRequest request, Payment payment)
            throws TransferFailedException, AppBizException {
        throw new AppBizException("201000", "该渠道不支持");
    }

    /**
     * 功能： C扫B模式生成二维码
     *
     * @param urmStoe
     * @param request
     * @param payment
     * @return
     * @throws TransferFailedException
     * @throws AppBizException
     */
    default ChannelResponseInfo jsapi(UrmStoe urmStoe, JsapiRequest request, Payment payment)
            throws TransferFailedException, AppBizException {
        throw new AppBizException("201000", "该渠道不支持");
    }

    /**
     * 功能： 消费交易状态查询
     *
     * @param urmStoe
     * @param request
     * @param payment
     * @return
     * @throws TransferFailedException
     * @throws AppBizException
     */
    default ChannelResponseInfo scanQuery(UrmStoe urmStoe, QueryRequest request, Payment payment)
            throws TransferFailedException, AppBizException {
        throw new AppBizException("201000", "该渠道不支持");
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

    default ChannelResponseInfo refund(UrmStoe urmStoe, RefundRequest refundRequest, Payment refundPayment)
            throws TransferFailedException, AppBizException {
        throw new AppBizException("201000", "该渠道不支持");
    }

    /**
     * 功能： 扫码退款查询
     * @param urmStoe
     * @param request
     * @param payment
     * @return
     * @throws TransferFailedException
     * @throws AppBizException
     */
    default ChannelResponseInfo sanRefundQuery(UrmStoe urmStoe, QueryRequest request, Payment payment,String status)
            throws TransferFailedException, AppBizException {
        throw new AppBizException("201000", "该渠道不支持");
    }

}

