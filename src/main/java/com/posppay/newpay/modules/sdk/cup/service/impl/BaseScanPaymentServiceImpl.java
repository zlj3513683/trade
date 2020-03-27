package com.posppay.newpay.modules.sdk.cup.service.impl;

import com.posppay.newpay.modules.sdk.cup.config.CupConfig;
import com.posppay.newpay.modules.sdk.cup.constant.CupConstant;
import com.posppay.newpay.modules.sdk.cup.constant.ResponseCodeConstant;
import com.posppay.newpay.modules.sdk.cup.model.req.*;
import com.posppay.newpay.modules.sdk.cup.model.resp.*;
import com.posppay.newpay.modules.sdk.cup.service.BaseScanPaymentService;
import com.posppay.newpay.modules.sdk.cup.util.HttpConnectionPoolUtil;
import com.posppay.newpay.modules.sdk.cup.util.SecurityUtil;
import com.posppay.newpay.modules.sdk.cup.util.XmlUtils;
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
@Service("cupBaseScanPaymentService")
public class BaseScanPaymentServiceImpl implements BaseScanPaymentService {

    @Autowired
    private CupConfig cupConfig;

    @Override
    public PassiveTradeResponse addPassiveTrade(PassiveTradeRequest request, String secretKey) {
        log.info("开始执行银联B扫C交易sdk");
        PassiveTradeResponse response = new PassiveTradeResponse();
        request.setService(CupConstant.ServerName.UNIFIED_TRADE_MICROPAY);
        request.setNonceString(RandomStringUtils.random(16,true,true));
        try {
            request.setSign(SecurityUtil.sign(request,secretKey, CupConstant.DEFAULT_CHARSET));
            log.info("请求数据：{}",request.toString());
            String xml = XmlUtils.parseBeanToXml(PassiveTradeRequest.class,request,true);
            String data = HttpConnectionPoolUtil.send(cupConfig.getPaymentUrl(),xml);
            log.info("请求响应：{}",data);
            if (StringUtils.isBlank(data)){
                response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
                response.setMessage(ResponseCodeConstant.RESPONSE_NULL_MSG);
                return response;
            }
            response = XmlUtils.parseXmlToBean(PassiveTradeResponse.class,data);
            //为0通信成功
            if (CupConstant.STRING_ZERO.equals(response.getStatus())){
                //响应校验采用map是因为银联文档与实际返回参数差别大
                Map<String,String> param = XmlUtils.toMap(data.getBytes(), CupConstant.DEFAULT_CHARSET);
                if (!SecurityUtil.sign(param,secretKey, CupConstant.DEFAULT_CHARSET)){
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
    public NativeTradeResponse addC2bTrade(NativeTradeRequest request, String secretKey) {
        log.info("开始执行银联c扫b下单sdk");
        NativeTradeResponse response = new NativeTradeResponse();
        request.setService(CupConstant.ServerName.UNIFIED_TRADE_NATIVE);
        request.setNonceString(RandomStringUtils.random(16,true,true));
        try {
            request.setSign(SecurityUtil.sign(request,secretKey, CupConstant.DEFAULT_CHARSET));
            log.info("请求数据：{}",request.toString());
            String xml = XmlUtils.parseBeanToXml(NativeTradeRequest.class,request,true);
            String data = HttpConnectionPoolUtil.send(cupConfig.getPaymentUrl(),xml);
            log.info("请求响应：{}",data);
            if (StringUtils.isBlank(data)){
                response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
                response.setMessage(ResponseCodeConstant.RESPONSE_NULL_MSG);
                return response;
            }
            response = XmlUtils.parseXmlToBean(NativeTradeResponse.class,data);
            //为0通信成功
            if (CupConstant.STRING_ZERO.equals(response.getStatus())){
                //响应校验采用map是因为银联文档与实际返回参数差别大
                Map<String,String> param = XmlUtils.toMap(data.getBytes(), CupConstant.DEFAULT_CHARSET);
                if (!SecurityUtil.sign(param,secretKey, CupConstant.DEFAULT_CHARSET)){
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
    public JsapiTradeResponse addJsapiTrade(JsapiTradeRequest request, String secretKey) {
        log.info("开始执行公众号下单sdk");
        JsapiTradeResponse response = new JsapiTradeResponse();
        request.setService(CupConstant.ServerName.UNIFIED_TRADE_JSAPI);
        request.setNonceString(RandomStringUtils.random(16,true,true));
        try {
            request.setSign(SecurityUtil.sign(request,secretKey, CupConstant.DEFAULT_CHARSET));
            log.info("请求数据：{}",request.toString());
            String xml = XmlUtils.parseBeanToXml(JsapiTradeRequest.class,request,true);
            String data = HttpConnectionPoolUtil.send(cupConfig.getPaymentUrl(),xml);
            log.info("请求响应：{}",data);
            if (StringUtils.isBlank(data)){
                response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
                response.setMessage(ResponseCodeConstant.RESPONSE_NULL_MSG);
                return response;
            }
            response = XmlUtils.parseXmlToBean(JsapiTradeResponse.class,data);
            //为0通信成功
            if (CupConstant.STRING_ZERO.equals(response.getStatus())){
                //响应校验采用map是因为银联文档与实际返回参数差别大
                Map<String,String> param = XmlUtils.toMap(data.getBytes(), CupConstant.DEFAULT_CHARSET);
                if (!SecurityUtil.sign(param,secretKey, CupConstant.DEFAULT_CHARSET)){
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
    public TradeQueryResponse tradeResultQuery(TradeQueryRequest request, String secretKey) {
        log.info("开始执行银联B扫C交易结果查询sdk");
        TradeQueryResponse response  = new TradeQueryResponse();
        request.setService(CupConstant.ServerName.UNIFIED_TRADE_QUERY);
        request.setNonceString(RandomStringUtils.random(16,true,true));
        try {
            request.setSign(SecurityUtil.sign(request,secretKey, CupConstant.DEFAULT_CHARSET));
            log.info("请求数据：{}",request.toString());
            String xml = XmlUtils.parseBeanToXml(TradeQueryRequest.class,request,true);
            String data = HttpConnectionPoolUtil.send(cupConfig.getPaymentUrl(),xml);
            log.info("请求响应：{}",data);
            if (StringUtils.isBlank(data)){
                response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
                response.setMessage(ResponseCodeConstant.RESPONSE_NULL_MSG);
                return response;
            }
            response = XmlUtils.parseXmlToBean(TradeQueryResponse.class,data);
            if (CupConstant.STRING_ZERO.equals(response.getStatus())){
                //响应校验采用map是因为银联文档与实际返回参数差别大
                Map<String,String> param = XmlUtils.toMap(data.getBytes(), CupConstant.DEFAULT_CHARSET);
                if (!SecurityUtil.sign(param,secretKey, CupConstant.DEFAULT_CHARSET)){
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
    public TradeRefundResponse tradeRefund(TradeRefundRequest request, String secretKey) {
        log.info("开始执行银联B扫C交易退款申请sdk");
        TradeRefundResponse response  = new TradeRefundResponse();
        request.setService(CupConstant.ServerName.UNIFIED_TRADE_REFUND);
        request.setNonceString(RandomStringUtils.random(16,true,true));
        try {
            request.setSign(SecurityUtil.sign(request,secretKey, CupConstant.DEFAULT_CHARSET));
            String xml = XmlUtils.parseBeanToXml(TradeRefundRequest.class,request,true);
            String data = HttpConnectionPoolUtil.send(cupConfig.getPaymentUrl(),xml);
            log.info("请求响应：{}",data);
            if (StringUtils.isBlank(data)){
                response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
                response.setMessage(ResponseCodeConstant.RESPONSE_NULL_MSG);
                return response;
            }
            response = XmlUtils.parseXmlToBean(TradeRefundResponse.class,data);
            if (CupConstant.STRING_ZERO.equals(response.getStatus())){
                //响应校验采用data而不用response是因为银联文档与实际返回参数差别大
                Map<String,String> param = XmlUtils.toMap(data.getBytes(), CupConstant.DEFAULT_CHARSET);
                if (!SecurityUtil.sign(param,secretKey, CupConstant.DEFAULT_CHARSET)){
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
        request.setService(CupConstant.ServerName.UNIFIED_TRADE_REFUND_QUERY);
        request.setNonceString(RandomStringUtils.random(16,true,true));
        try {
            request.setSign(SecurityUtil.sign(request,secretKey, CupConstant.DEFAULT_CHARSET));
            String xml = XmlUtils.parseBeanToXml(TradeRefundQueryRequest.class,request,true);
            String data = HttpConnectionPoolUtil.send(cupConfig.getPaymentUrl(),xml);
            log.info("请求响应：{}",data);
            if (StringUtils.isBlank(data)){
                response.setStatus(ResponseCodeConstant.DEFAULT_ERROR_CODE);
                response.setMessage(ResponseCodeConstant.RESPONSE_NULL_MSG);
                return response;
            }
            response = XmlUtils.parseXmlToBean(TradeRefundQueryResponse.class,data);
            if (CupConstant.STRING_ZERO.equals(response.getStatus())){
                //响应校验采用data而不用response是因为银联文档与实际返回参数差别大
                Map<String,String> param = XmlUtils.toMap(data.getBytes(), CupConstant.DEFAULT_CHARSET);
                if (!SecurityUtil.sign(param,secretKey, CupConstant.DEFAULT_CHARSET)){
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
