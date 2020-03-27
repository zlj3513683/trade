package com.posppay.newpay;



import com.posppay.newpay.modules.sdk.cup.model.req.PassiveTradeRequest;
import com.posppay.newpay.modules.sdk.cup.model.resp.PassiveTradeResponse;
import com.posppay.newpay.modules.sdk.cup.service.BaseScanPaymentService;
import com.posppay.newpay.modules.sdk.pingan.util.XmlUtils;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;
import java.lang.reflect.Proxy;

/**
 * @author MaoNing
 * @date 2019/4/29
 */
@SpringBootTest(classes = PospPayApplication.class)
@RunWith(SpringRunner.class)
public class CupScanPaymentServiceTest {


    @Resource(name = "cupBaseScanPaymentService")
    private BaseScanPaymentService cupBaseScanPaymentService;

    @Test
    public void addPassiveTrade() {
        PassiveTradeRequest request = new PassiveTradeRequest();
        request.setMerchantId("QRA290459700FFM");
        request.setOutTradeNo("128985789999999");
        request.setBody("果汁");
        request.setTotalFee(1);
        request.setMachineIp("192.168.1.222");
        request.setAuthCode("135049151380755460");
//        request.setSignAgentNo("401580000261");

        PassiveTradeResponse response = new PassiveTradeResponse();
        response = cupBaseScanPaymentService.addPassiveTrade(request, "db1714406a0e783ea408df7c255885bc");
        System.out.println(response.toString());
    }

//    @Test
//    public void proxyTest() throws XMLStreamException {
//        StringWriter writer = new StringWriter();
//        XMLStreamWriter streamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(writer);
//        XMLStreamWriter cdataStreamWriter = (XMLStreamWriter) Proxy.getProxyClass(
//                streamWriter.getClass().getClassLoader(),
////                XMLStreamWriter.class,\
//                XmlUtils.ProtectDataHandler.class
////                streamWriter.getClass().getInterfaces(),
//        new XmlUtils.ProtectDataHandler(streamWriter)
//        );
//    }

//    @Test
//    public void PassiveTradeQuery(){
//        TradeQueryRequest request = new TradeQueryRequest();
//        request.setMerchantId("530560041866");
////        request.setTransactionId("530530043164201906168090748144");
//        request.setOutTradeNo("a4cc3a25f0cf4fe16098ff3f0dd8c1f9");
//        request.setSignAgentNo("530530041352");
//        TradeQueryResponse response = baseScanPaymentService.tradeResultQuery(request,"4a98bb710d8a3ec1131cf10cb88ee383");
//        System.out.println(JSONObject.fromObject(response));
////        System.out.println(response.toString());
//    }
////
//    @Test
//    public void refund(){
//        TradeRefundRequest request = new TradeRefundRequest();
//        request.setMerchantId("530530043164");
//        request.setTransactionId("530530043164201906158090669524");
//        request.setOutRefundNo("refund2019050714440002");
//        request.setTotalFee("1");
//        request.setRefundFee("1");
//        request.setOperatorId("100001");
//        request.setSignAgentNo("530540043406");
//        TradeRefundResponse response = baseScanPaymentService.tradeRefund(request,"4a98bb710d8a3ec1131cf10cb88ee383");
//        System.out.println(response.toString());
//    }
//
//
//
////
//    @Test
//    public void refundQuery(){
//        TradeRefundQueryRequest request = new TradeRefundQueryRequest();
//        request.setMerchantId("530530043164");
////        request.setTransactionId("530530043164201906178090631167");
////        request.setOutTradeNo("2019061748de0bc3a92f46ca84ded10e");
////        request.setTransactionId("102532336411201905221274707789");
//        request.setOutRefundNo("2019061731e3f4ee1247498da50f30c8");
////        request.setRefundId("530530043164201906168090766589");
//        request.setSignAgentNo("530540043406");
//        TradeRefundQueryResponse response = baseScanPaymentService.tradeRefundQuery(request,"4a98bb710d8a3ec1131cf10cb88ee383");
//        System.out.println(response.toString());
//    }
}
