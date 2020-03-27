package com.posppay.newpay.modules.sdk.pingan.service;


import com.posppay.newpay.modules.sdk.pingan.model.req.*;
import com.posppay.newpay.modules.sdk.pingan.model.resp.*;

/**
 * 银联B扫C交易
 * @author MaoNing
 * @date 2019/4/29
 */
public interface BaseScanPaymentService {

    /**
     * 发起B扫C交易
     * @param request
     * @param secretKey
     * @return
     */
    PassiveTradeResponse addPassiveTrade(PassiveTradeRequest request, String secretKey);

//    /**
//     *C扫B交易请求
//     * @param request
//     * @param secretKey
//     * @return
//     */
//    NativeTradeResponse addNativeTrade(NativeTradeRequest request, String secretKey);

    /**
     * 交易结果查询
     * @param request
     * @param secretKey
     * @return
     */
    TradeQueryResponse tradeResultQuery(TradeQueryRequest request, String secretKey);

//    /**
//     * B扫C交易撤销
//     * @param request
//     * @param secretKey
//     * @return
//     */
//    PaymentResponse tradeRevoke(PassiveTradeRevokeRequest request, String secretKey);


    /**
     * 交易退款申请
     * @param request
     * @param secretKey
     * @return
     */
    TradeRefundResponse tradeRefund(TradeRefundRequest request, String secretKey);

    /**
     * 交易退款查询
     * @param request
     * @param secretKey
     * @return
     */
    TradeRefundQueryResponse tradeRefundQuery(TradeRefundQueryRequest request, String secretKey);
}
