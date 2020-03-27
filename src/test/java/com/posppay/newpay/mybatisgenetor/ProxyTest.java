package com.posppay.newpay.mybatisgenetor;

import cn.hutool.core.date.DateUtil;
import com.google.gson.JsonObject;
import com.posppay.newpay.BaseTest;
import com.posppay.newpay.model.ConsumeTestRequest;
import com.posppay.newpay.model.ProxyTestRequest;
import com.posppay.newpay.model.QueryTestRequest;
import com.posppay.newpay.model.RefundTestRequest;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.Date;

public class ProxyTest extends BaseTest {


    @Test
    public void generateProxy(){
        ProxyTestRequest proxyTestRequest = new ProxyTestRequest();
        proxyTestRequest.setCharacterSet("UTF-8");
        proxyTestRequest.setDeviceSn("1234567");
        proxyTestRequest.setIpAddress("194.156.22.78");
        proxyTestRequest.setHmac("12345667");
        proxyTestRequest.setMerchantId("803345000000094");
        proxyTestRequest.setOprId("00001");
        proxyTestRequest.setOrgNo("12419");
        proxyTestRequest.setOutOrderNo("123456789066");
        proxyTestRequest.setSignType("MD5");
        proxyTestRequest.setVersion("1.0.0");
        proxyTestRequest.setRandomData("251324365748");
        proxyTestRequest.setType("scanPay");
        ConsumeTestRequest consumeTestRequest = new ConsumeTestRequest();
        consumeTestRequest.setAmount("000000000001");
        consumeTestRequest.setAttach("测试");
        consumeTestRequest.setAuthCode("284949605087083459");
        consumeTestRequest.setBody("测试");
        consumeTestRequest.setCurrency("CNY");
        consumeTestRequest.setGoodsDetail("");
        consumeTestRequest.setTotalAmt("000000000001");
        consumeTestRequest.setTransTime(DateUtil.format(new Date(),"yyyyMMddHHmmss"));
        consumeTestRequest.setPayChannel("ALIPAY");
        proxyTestRequest.setReqDetail(JSONObject.fromObject(consumeTestRequest).toString());
//        System.out.println(JSONObject.fromObject(proxyTestRequest).toString());
    }
    @Test
    public void generateRefundProxy(){
        ProxyTestRequest proxyTestRequest = new ProxyTestRequest();
        proxyTestRequest.setCharacterSet("UTF-8");
        proxyTestRequest.setDeviceSn("1234567");
        proxyTestRequest.setIpAddress("194.156.22.78");
        proxyTestRequest.setHmac("12345667");
        proxyTestRequest.setMerchantId("803345000000094");
        proxyTestRequest.setOprId("00001");
        proxyTestRequest.setOrgNo("12419");
        proxyTestRequest.setOutOrderNo("12345678999999");
        proxyTestRequest.setSignType("MD5");
        proxyTestRequest.setVersion("1.0.0");
        proxyTestRequest.setRandomData("251324365748");
        proxyTestRequest.setType("scanRefund");
        RefundTestRequest refundTestRequest = new RefundTestRequest();
        refundTestRequest.setOriginOrderNo("1234567891234567");
        refundTestRequest.setRefAmt("000000000001");
        refundTestRequest.setOriTransDate(DateUtil.format(new Date(),"yyyyMMdd"));
        proxyTestRequest.setReqDetail(JSONObject.fromObject(refundTestRequest).toString());
//        System.out.println(JSONObject.fromObject(proxyTestRequest).toString());
    }


    @Test
    public void generateQueryProxy(){
        ProxyTestRequest proxyTestRequest = new ProxyTestRequest();
        proxyTestRequest.setCharacterSet("UTF-8");
        proxyTestRequest.setDeviceSn("1234567");
        proxyTestRequest.setIpAddress("194.156.22.78");
        proxyTestRequest.setHmac("12345667");
        proxyTestRequest.setMerchantId("803345000000094");
        proxyTestRequest.setOprId("00001");
        proxyTestRequest.setOrgNo("12419");
        proxyTestRequest.setOutOrderNo("123456789170967264567");
        proxyTestRequest.setSignType("MD5");
        proxyTestRequest.setVersion("1.0.0");
        proxyTestRequest.setRandomData("251324365748");
        proxyTestRequest.setType("scanQuery");
        QueryTestRequest queryTestRequest = new QueryTestRequest();
        queryTestRequest.setOrioutTransactionId("123456789179967264567");
        queryTestRequest.setOriTransDate(DateUtil.format(new Date(),"yyyyMMdd"));
        proxyTestRequest.setReqDetail(JSONObject.fromObject(queryTestRequest).toString());
//        System.out.println(JSONObject.fromObject(proxyTestRequest).toString());
    }
}
