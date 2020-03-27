package com.posppay.newpay.modules.xposp.router.channel.pingan.baffle;

import com.posppay.newpay.modules.sdk.pingan.model.resp.PassiveTradeResponse;
import com.posppay.newpay.modules.sdk.pingan.model.resp.TradeQueryResponse;
import com.posppay.newpay.modules.sdk.pingan.model.resp.TradeRefundResponse;

/**
 * 平安渠道挡板处理类
 */
public class PinganBaffle {

    private static PinganBaffle pinganBaffle = null ;

    private void PinganBaffle(){ }

    public static PinganBaffle getInstance () {
        if(null == pinganBaffle) {
            pinganBaffle = new PinganBaffle();
        }
        return pinganBaffle;
    }
    /**
     * 消费挡板
     * @return
     */
    public PassiveTradeResponse consume(){
        PassiveTradeResponse response = new PassiveTradeResponse();
        response.setNeedQuery("N");
//        response.setCode("10003");
        response.setAppId("2017061507494001");
        response.setCharset("UTF-8");
        response.setVersion("2.0");
        response.setSignType("MD5");
        response.setStatus("0");
        response.setResultCode("0");
        response.setMerchantId("530530043164");
        response.setNonceString("xFxm2Rtqc3PehvRV");
        response.setErrorCode("10003");
        response.setErrorMessage("交易成功");
        response.setSign("0CB3A9CD5207D775128683B8516EDA12");
        return response;
    }
    /**
     * 退款挡板
     * @return
     */
    public TradeRefundResponse refund(){
        TradeRefundResponse response = new TradeRefundResponse();
        response.setCode("04195_1211015");
        response.setVersion("2.0");
        response.setCharset("UTF-8");
        response.setSignType("MD5");
        response.setStatus("0");
        response.setResultCode("1");
        response.setMerchantId("530530043164");
        response.setNonceString("zkyq2eamD2hpJD4m");
        response.setErrorCode("Refund exists");
        response.setErrorMessage("退款已存在");
        response.setSign("3EFFDDF1CAB4CB5BB6C851CC5DBC730B");
        return response;
    }
}
