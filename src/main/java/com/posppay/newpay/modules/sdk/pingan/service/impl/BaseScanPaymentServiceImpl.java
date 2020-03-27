package com.posppay.newpay.modules.sdk.pingan.service.impl;


import cn.hutool.http.HttpUtil;
import com.posppay.newpay.modules.sdk.pingan.config.ParamConfig;
import com.posppay.newpay.modules.sdk.pingan.constant.PinganConstant;
import com.posppay.newpay.modules.sdk.pingan.constant.ResponseCodeConstant;
import com.posppay.newpay.modules.sdk.pingan.model.req.*;
import com.posppay.newpay.modules.sdk.pingan.model.resp.*;
import com.posppay.newpay.modules.sdk.pingan.service.BaseScanPaymentService;
import com.posppay.newpay.modules.sdk.pingan.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 银联扫码交易基础服务
 * @author MaoNing
 * @date 2019/4/29
 */
@Slf4j
@Service("baseScanPaymentService")
public class BaseScanPaymentServiceImpl implements BaseScanPaymentService {

    @Autowired
    private ParamConfig paramConfig;

    @Override
    public PassiveTradeResponse addPassiveTrade(PassiveTradeRequest request, String secretKey) {
        log.info("开始执行银联B扫C交易sdk");
        PassiveTradeResponse response = new PassiveTradeResponse();
        request.setService(PinganConstant.ServerName.UNIFIED_TRADE_MICROPAY);
        request.setNonceString(RandomStringUtils.random(16,true,true));
        try {
            request.setSign(SecurityUtil.sign(request,secretKey, PinganConstant.DEFAULT_CHARSET));
            log.info("请求数据：{}",request.toString());
            String xml = XmlUtils.parseBeanToXml(PassiveTradeRequest.class,request,true);
            String data = HttpConnectionPoolUtil.send(paramConfig.getPaymentUrl(),xml);
            log.info("请求响应：{}",data);
            if (StringUtils.isBlank(data)){
                response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
                response.setMessage(ResponseCodeConstant.RESPONSE_NULL_MSG);
                return response;
            }
            response = XmlUtils.parseXmlToBean(PassiveTradeResponse.class,data);
            //为0通信成功
            if (PinganConstant.COMMUNICATE_SUCCESS.equals(response.getStatus())){
                //响应校验采用map是因为银联文档与实际返回参数差别大
                Map<String,String> param = XmlUtils.toMap(data.getBytes(), PinganConstant.DEFAULT_CHARSET);
                if (!SecurityUtil.sign(param,secretKey, PinganConstant.DEFAULT_CHARSET)){
                    response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
                    response.setMessage(ResponseCodeConstant.RESPONSE_SIGN_MESSAGE);
                    return response;
                }
            }
        }catch (Exception e){
            log.error("未知错误",e);
            log.info(e.getLocalizedMessage());
            response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
            response.setMessage(ResponseCodeConstant.REQUEST_ERROR_MSG);
        }
        return response;
    }

//    @Override
//    public NativeTradeResponse addNativeTrade(NativeTradeRequest request, String secretKey) {
//        log.info("开始执行银联C扫B交易sdk");
//        NativeTradeResponse response = new NativeTradeResponse();
//        request.setService(CupConstant.ServerName.UNIFIED_TRADE_NATIVE);
//        request.setNonceString(RandomStringUtils.random(16,true,true));
//        try {
//            request.setSign(SecurityUtil.sign(request,secretKey, CupConstant.DEFAULT_CHARSET));
//            log.info("请求数据：{}",request.toString());
//            String xml = XmlUtils.parseBeanToXml(NativeTradeRequest.class,request,true);
//            String data = HttpClientUtil.sendHttpMessage(paramConfig.getPaymentUrl(),xml);
//            log.info("请求响应：{}",data);
//            if (StringUtils.isBlank(data)){
//                response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
//                response.setMessage(ResponseCodeConstant.RESPONSE_NULL_MSG);
//                return response;
//            }
//            response = XmlUtils.parseXmlToBean(NativeTradeResponse.class,data);
//            //为0通信成功
//            if (CupConstant.STRING_ZERO.equals(response.getStatus())){
//                //响应校验采用map是因为银联文档与实际返回参数差别大
//                Map<String,String> param = XmlUtils.toMap(data.getBytes(), CupConstant.DEFAULT_CHARSET);
//                if (!SecurityUtil.sign(param,secretKey, CupConstant.DEFAULT_CHARSET)){
//                    response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
//                    response.setMessage(ResponseCodeConstant.RESPONSE_SIGN_MESSAGE);
//                    return response;
//                }
//            }
//        }catch (Exception e){
//            log.info(e.getLocalizedMessage());
//            response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
//            response.setMessage(ResponseCodeConstant.REQUEST_ERROR_MSG);
//        }
//        return response;
//    }

    @Override
    public TradeQueryResponse tradeResultQuery(TradeQueryRequest request, String secretKey) {
        log.info("开始执行银联B扫C交易结果查询sdk");
        TradeQueryResponse response  = new TradeQueryResponse();
        request.setService(PinganConstant.ServerName.UNIFIED_TRADE_QUERY);
        request.setNonceString(RandomStringUtils.random(16,true,true));
        try {
            request.setSign(SecurityUtil.sign(request,secretKey, PinganConstant.DEFAULT_CHARSET));
            log.info("请求数据：{}",request.toString());
            String xml = XmlUtils.parseBeanToXml(TradeQueryRequest.class,request,true);
            String data = HttpConnectionPoolUtil.send(paramConfig.getPaymentUrl(),xml);
            log.info("请求响应：{}",data);
            if (StringUtils.isBlank(data)){
                response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
                response.setMessage(ResponseCodeConstant.RESPONSE_NULL_MSG);
                return response;
            }
            response = XmlUtils.parseXmlToBean(TradeQueryResponse.class,data);
            if (PinganConstant.COMMUNICATE_SUCCESS.equals(response.getStatus())){
                //响应校验采用map是因为银联文档与实际返回参数差别大
                Map<String,String> param = XmlUtils.toMap(data.getBytes(), PinganConstant.DEFAULT_CHARSET);
                if (!SecurityUtil.sign(param,secretKey, PinganConstant.DEFAULT_CHARSET)){
                    response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
                    response.setMessage(ResponseCodeConstant.RESPONSE_SIGN_MESSAGE);
                    return response;
                }
            }
        }catch (Exception e){
            log.info(e.getLocalizedMessage());
            response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
            response.setMessage(ResponseCodeConstant.REQUEST_ERROR_MSG);
        }
        return response;
    }

//    @Override
//    public PaymentResponse tradeRevoke(PassiveTradeRevokeRequest request, String secretKey) {
//        log.info("开始执行银联B扫C交易撤销sdk");
//        PaymentResponse response  = new PaymentResponse();
//        request.setService(CupConstant.ServerName.UNIFIED_MICROPAY_REVERSE);
//        request.setNonceString(RandomStringUtils.random(16,true,true));
//        try {
//            request.setSign(SecurityUtil.sign(request,secretKey, CupConstant.DEFAULT_CHARSET));
//            String xml = XmlUtils.parseBeanToXml(PassiveTradeRevokeRequest.class,request,true);
//            String data = HttpClientUtil.sendHttpMessage(paramConfig.getPaymentUrl(),xml);
//            log.info("请求响应：{}",data);
//            if (StringUtils.isBlank(data)){
//                response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
//                response.setMessage(ResponseCodeConstant.RESPONSE_NULL_MSG);
//                return response;
//            }
//            response = XmlUtils.parseXmlToBean(PaymentResponse.class,data);
//            if (CupConstant.STRING_ZERO.equals(response.getStatus())){
//                //响应校验采用data而不用response是因为银联文档与实际返回参数差别大
//                Map<String,String> param = XmlUtils.toMap(data.getBytes(), CupConstant.DEFAULT_CHARSET);
//                if (!SecurityUtil.sign(param,secretKey, CupConstant.DEFAULT_CHARSET)){
//                    response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
//                    response.setMessage(ResponseCodeConstant.RESPONSE_SIGN_MESSAGE);
//                    return response;
//                }
//            }
//        }catch (Exception e){
//            log.info(e.getLocalizedMessage());
//            response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
//            response.setMessage(ResponseCodeConstant.REQUEST_ERROR_MSG);
//        }
//        return response;
//    }

    @Override
    public TradeRefundResponse tradeRefund(TradeRefundRequest request, String secretKey) {
        log.info("开始执行银联B扫C交易退款申请sdk");
        TradeRefundResponse response  = new TradeRefundResponse();
        request.setService(PinganConstant.ServerName.UNIFIED_TRADE_REFUND);
        request.setNonceString(RandomStringUtils.random(16,true,true));
        try {
            request.setSign(SecurityUtil.sign(request,secretKey, PinganConstant.DEFAULT_CHARSET));
            String xml = XmlUtils.parseBeanToXml(TradeRefundRequest.class,request,true);
            String data = HttpConnectionPoolUtil.send(paramConfig.getPaymentUrl(),xml);
            log.info("请求响应：{}",data);
            if (StringUtils.isBlank(data)){
                response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
                response.setMessage(ResponseCodeConstant.RESPONSE_NULL_MSG);
                return response;
            }
            response = XmlUtils.parseXmlToBean(TradeRefundResponse.class,data);
            if (PinganConstant.COMMUNICATE_SUCCESS.equals(response.getStatus())){
                //响应校验采用data而不用response是因为银联文档与实际返回参数差别大
                Map<String,String> param = XmlUtils.toMap(data.getBytes(), PinganConstant.DEFAULT_CHARSET);
                if (!SecurityUtil.sign(param,secretKey, PinganConstant.DEFAULT_CHARSET)){
                    response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
                    response.setMessage(ResponseCodeConstant.RESPONSE_SIGN_MESSAGE);
                    return response;
                }
            }
        }catch (Exception e){
            log.info(e.getLocalizedMessage());
            response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
            response.setMessage(ResponseCodeConstant.REQUEST_ERROR_MSG);
        }
        return response;
    }

    @Override
    public TradeRefundQueryResponse tradeRefundQuery(TradeRefundQueryRequest request, String secretKey) {
        log.info("开始执行银联B扫C交易退款结果查询sdk");
        TradeRefundQueryResponse response  = new TradeRefundQueryResponse();
        request.setService(PinganConstant.ServerName.UNIFIED_TRADE_REFUND_QUERY);
        request.setNonceString(RandomStringUtils.random(16,true,true));
        try {
            request.setSign(SecurityUtil.sign(request,secretKey, PinganConstant.DEFAULT_CHARSET));
            String xml = XmlUtils.parseBeanToXml(TradeRefundQueryRequest.class,request,true);
            String data = HttpConnectionPoolUtil.send(paramConfig.getPaymentUrl(),xml);
            log.info("请求响应：{}",data);
            if (StringUtils.isBlank(data)){
                response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
                response.setMessage(ResponseCodeConstant.RESPONSE_NULL_MSG);
                return response;
            }
            response = XmlUtils.parseXmlToBean(TradeRefundQueryResponse.class,data);
            if (PinganConstant.COMMUNICATE_SUCCESS.equals(response.getStatus())){
                //响应校验采用data而不用response是因为银联文档与实际返回参数差别大
                Map<String,String> param = XmlUtils.toMap(data.getBytes(), PinganConstant.DEFAULT_CHARSET);
                if (!SecurityUtil.sign(param,secretKey, PinganConstant.DEFAULT_CHARSET)){
                    response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
                    response.setMessage(ResponseCodeConstant.RESPONSE_SIGN_MESSAGE);
                    return response;
                }
            }
        }catch (Exception e){
            log.info(e.getLocalizedMessage());
            response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
            response.setMessage(ResponseCodeConstant.REQUEST_ERROR_MSG);
        }
        return response;
    }
}
