package com.posppay.newpay;


import cn.hutool.core.date.DateUtil;
import com.posppay.newpay.modules.sdk.cup.model.req.PassiveTradeRequest;
import com.posppay.newpay.modules.sdk.cup.model.resp.PassiveTradeResponse;
import com.posppay.newpay.modules.sdk.cup.service.BaseScanPaymentService;
import com.posppay.newpay.modules.sdk.starpos.constant.StarPosConstant;
import com.posppay.newpay.modules.sdk.starpos.model.req.*;
import com.posppay.newpay.modules.sdk.starpos.model.resp.TradeScanPayResp;
import com.posppay.newpay.modules.sdk.starpos.service.ScanPayService;
import com.posppay.newpay.modules.xposp.utils.SnowIdUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;


/**
 * @author zengjw
 * create By 2019/07/02
 */
@SpringBootTest(classes = PospPayApplication.class)
@RunWith(SpringRunner.class)
public class StarPosScanPaymentServiceTest {
    @Resource
    private ScanPayService scanPayService;

    @Test
    public void consumeTest() throws Exception {
        TradeScanPayReq req = new TradeScanPayReq();
        String key = "D45B64A6813045D0CE88149122BA8AF2";
        req.setOrgNo("31041");
        req.setAmount("1");
        req.setAuthCode("134577547102682691")
                .setTerminalNo("95093033")
                .setOrderId(SnowIdUtils.getInstance(1,2).nextId())
                .setIpAddress("192.168.50.134")
                .setMerchantId("800290000012383");
        TradeScanPayResp tradeScanPayResp = scanPayService.scanPay(req, key);
    }

    @Test
    public void refundTest() throws Exception {
        TradeScanRefundReq req = new TradeScanRefundReq();
        String key = "012A89732E99577491A317F7B1D86770";
        req.setOrderId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32))
                .setSn("XA000009")
                .setRefAmt("1")
                .setOrderNo("20190722151539009804")
                .setTerminalNo("95092784")
                .setIpAddress("192.168.50.134")
                .setMerchantId("800799000000001")
                .setOrgNo("31041")
                .setOprId(StarPosConstant.OPERATOR);
        scanPayService.scanRefund(req, key);
    }

    @Test
    public void queryTest() throws Exception {
        TradeScanQueryReq req = new TradeScanQueryReq();
        String key = "012A89732E99577491A317F7B1D86770";
        req.setOldTxnLogId("da953229d1c84d4faa96882e4d78f87c")
                .setTerminalNo("95092784")
                .setIpAddress("192.168.50.134")
                .setMerchantId("800799000000001")
                .setOrgNo("31041");
        scanPayService.scanQuery(req, key);
    }

    @Test
    public void qrCodeTest() {
        TradeQrCodeReq req = new TradeQrCodeReq();
        req.setTerminalNo("95092561")
                .setOrderId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32))
                .setAmount("1")
                .setBatchNo("000001")
                .setVersion("1.0.0")
                .setIpAddress("192.168.50.134")
                .setMerchantId("800391000000351")
                .setRequestId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32))
                .setSignType("MD5")
                .setOprId("00")
                .setTelNo("000000");
        scanPayService.qrCode(req);
    }

    @Test
    public void pubAuthQueryTest() {
        TradePubAuthQueryReq req = new TradePubAuthQueryReq();
        req.setOrgNo("31041")
                .setTxnTime(DateUtil.format(new Date(), "yyyyMMddHHmmss"))
                .setTrmNo("95092561")
                .setVersion("1.0.0")
                .setIpAddress("192.168.50.134")
                .setMerchantId("800391000000351")
                .setRequestId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32))
                .setSignType("MD5")
                .setOprId("00")
                .setTelNo("000000");
        scanPayService.pubQuery(req);

    }

    @Test
    public void pubPayTest() {
        TradePubAuthPayReq req = new TradePubAuthPayReq();
        req.setOrgNo("31041")
                .setTrmNo("95092561")
                .setTxnTime("txnTime")
                .setCode("")
                .setAmount("1")
                .setTotalAmount("1")
                .setSubject("测试店")
                .setSelOrderNo(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32))
                .setGoodsTag("无优惠")
                .setAttach("测试")
                .setOrderId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32))
                .setVersion("1.0.0")
                .setIpAddress("192.168.50.134")
                .setMerchantId("800391000000351")
                .setRequestId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32))
                .setSignType("MD5")
                .setOprId("00")
                .setTelNo("000000");
        scanPayService.pubPay(req, "ea430def254d035f97e3e4153478085a");

    }
}
