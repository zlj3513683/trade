package com.posppay.newpay.modules.sdk.starpos.service;

import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.modules.sdk.starpos.model.req.*;
import com.posppay.newpay.modules.sdk.starpos.model.resp.*;

public interface ScanPayService {
    /**
     * sdk消费
     * @param req
     * @return
     * @throws Exception
     */
    TradeScanPayResp scanPay(TradeScanPayReq req,String key) throws AppBizException;

    /**
     * sdk退款
     * @param req
     * @return
     * @throws Exception
     */
    TradeScanRefundResp scanRefund(TradeScanRefundReq req,String key) throws AppBizException;
    /**
     * sdk订单查询
     * @param req
     * @return
     * @throws Exception
     */
    TradeScanQueryResp scanQuery(TradeScanQueryReq req,String key) throws AppBizException;

    /**
     * 客户主扫 动态码
     * @param req
     * @return
     * @throws AppBizException
     */
    TradeQrcodeResp qrCode(TradeQrCodeReq req) throws AppBizException;

    /**
     * 公众号查询
     * @param req
     * @return
     * @throws AppBizException
     */
    TradePubAuthQueryResp pubQuery(TradePubAuthQueryReq req) throws AppBizException;

    /**
     * 公众号支付
     * @param req
     * @return
     * @throws AppBizException
     */
    TradePubAuthPayResp pubPay(TradePubAuthPayReq req,String key)throws AppBizException;
}
