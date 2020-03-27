package com.posppay.newpay.modules.xposp.router.channel.pingan.handle;

import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.modules.model.OrgChannelType;
import com.posppay.newpay.modules.xposp.common.AppExCode;
import com.posppay.newpay.modules.xposp.entity.CupMerchant;
import com.posppay.newpay.modules.xposp.entity.HpsJnl;
import com.posppay.newpay.modules.xposp.entity.UrmStoe;
import com.posppay.newpay.modules.xposp.innertrans.InnerTransService;
import com.posppay.newpay.modules.xposp.router.TransferFailedException;
import com.posppay.newpay.modules.xposp.router.channel.ChannelRequestInfo;
import com.posppay.newpay.modules.xposp.router.channel.ChannelResponseInfo;
import com.posppay.newpay.modules.xposp.router.channel.cup.model.PayChannelPath;
import com.posppay.newpay.modules.xposp.router.channel.cup.model.req.QrCodeSdkRequest;
import com.posppay.newpay.modules.xposp.router.channel.cup.model.resp.QrCodeSdkResponse;
import com.posppay.newpay.modules.xposp.utils.JsonUtils;
import com.posppay.newpay.modules.xposp.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * 功能：银联消费与第三方通讯接口实现
 *
 * @author zengjianwens
 */
@Service("pAQrCodeProcess")
@Slf4j
public class QrCodeProcess {
    @Resource
    private InnerTransService innerTransService;
//    @Resource
//    private RestTemplate restTemplate;
    @Value("${cup.url}")
    private String address;
    private static String OPERATOR_ID = "000001";
    private static String ACCEPT = "Accept";
    private static String PRE_PAY = "A";

    /**
     * @param cupMerchant
     * @param request
     * @return
     * @throws TransferFailedException
     * @throws UnsupportedEncodingException
     * @throws HSMException
     */
    public ChannelResponseInfo process(UrmStoe urmStoe, CupMerchant cupMerchant, ChannelRequestInfo request, HpsJnl hpsJnl)
            throws TransferFailedException, AppBizException {
        log.info("银联C扫B模式(扫Pos)生成二维码开始.................");

//        QrCodeSdkRequest entity = packUp(urmStoe, cupMerchant, request);
//        HttpHeaders headers = new HttpHeaders();
//        MediaType type = MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        headers.setContentType(type);
//        headers.add(ACCEPT, MediaType.APPLICATION_JSON.toString());
//        String json = JsonUtils.objectToJson(entity);
//        HttpEntity<String> formEntity = new HttpEntity<String>(json, headers);
//        String result = restTemplate.postForObject(address+ PayChannelPath.QRCODE.getPath(), formEntity, String.class);
//        QrCodeSdkResponse response = unpack(result);
//        if (null == response) {
//            //TODO  流程待定
//            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "网络读写异常请重试");
//        }
//        if (PRE_PAY.equals(response.getResult())) {
//            hpsJnl.setTtxnSts(PRE_PAY)
//                    .setCorgNo(OrgChannelType.CUP_CHANNEL.getOrgNo());
//            innerTransService.updateHpsFlow(hpsJnl);
//        }else {
//            hpsJnl.setCorgNo(OrgChannelType.CUP_CHANNEL.getOrgNo()).setAccRspMsg(response.getMessageData());
//            innerTransService.updateHpsFlow(hpsJnl);
//            throw new TransferFailedException(response.getReturnCode(),response.getMessageData());
//        }
        //接收报文
        ChannelResponseInfo cupsResponseInfo = new ChannelResponseInfo();
//        cupsResponseInfo.setTransCode(response.getReturnCode());
//        cupsResponseInfo.setPayNo(request.getPayNo());

        log.info("银联C扫B模式(扫Pos)生成二维码结束................");
        return cupsResponseInfo;
    }


    private QrCodeSdkRequest packUp(UrmStoe urmStoe, CupMerchant cupMerchant, ChannelRequestInfo request) throws TransferFailedException {
        //获取磁道信息
        QrCodeSdkRequest sdkRequest = QrCodeSdkRequest
                .builder()
                .body(urmStoe.getStoeNm())
                .merchantId(cupMerchant.getCupMerchantId())
                .operatorId(OPERATOR_ID)
                .outTradeNo(request.getPayNo())
                .totalFee(StringUtils.decimal2bcd4cent(request.getAmount()))
                .build();
        return sdkRequest;
    }


    /**
     * 解析返回的结果
     *
     * @param respData
     * @return
     */
    private QrCodeSdkResponse unpack(String respData) {
        QrCodeSdkResponse response = JsonUtils.jsonToObject(respData, QrCodeSdkResponse.class);
        return response;
    }


}
