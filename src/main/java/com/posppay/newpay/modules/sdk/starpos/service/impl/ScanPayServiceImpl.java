package com.posppay.newpay.modules.sdk.starpos.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.modules.sdk.starpos.config.StartPosParam;
import com.posppay.newpay.modules.sdk.starpos.constant.StarPosConstant;
import com.posppay.newpay.modules.sdk.starpos.model.req.*;
import com.posppay.newpay.modules.sdk.starpos.model.resp.*;
import com.posppay.newpay.modules.sdk.starpos.service.ScanPayService;
import com.posppay.newpay.modules.sdk.starpos.util.HttpConnectionPoolUtil;
import com.posppay.newpay.modules.sdk.starpos.util.JsonUtils;
import com.posppay.newpay.modules.sdk.starpos.util.SecurityUtil;
import com.posppay.newpay.modules.xposp.common.AppExCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class ScanPayServiceImpl implements ScanPayService {
    @Resource
    private StartPosParam startPosParam;

    /**
     * sdk消费
     *
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public TradeScanPayResp scanPay(TradeScanPayReq req,String key) throws AppBizException {
        req.setType(StarPosConstant.SCAN_PAY);
        req.setSignType(StarPosConstant.SIGN_TYPE);
        req.setTelNo(StarPosConstant.TEL_NO);
        req.setVersion(StarPosConstant.VERSION);
        req.setCharacterSet(StarPosConstant.CHARSET_UTF8);
        req.setBatchNo(StarPosConstant.BATCH_NO);
        req.setTxnCnl(StarPosConstant.TXNCNL_I);
        req.setRequestId(UUID.randomUUID().toString().replaceAll("-","").substring(0,32));
        String paramJson = JsonUtils.toJson(req);
        Map<String, String> param = (Map<String, String>) JsonUtils.toCollection(paramJson, new TypeReference<Map<String, String>>() {
        });
        try{
            String sign = SecurityUtil.sign(param, key, "UTF-8");
            param.put("hmac", sign);
            String response = HttpConnectionPoolUtil.send(startPosParam.getUrl(), param);
            response = URLDecoder.decode(response, "UTF-8");
            String[] responseStrs = response.split("&");
            Map<String, String> responseMap = new HashMap<String, String>();
            for (String responseStr : responseStrs) {
                String[] params = responseStr.split("=");
                responseMap.put(params[0], params[1]);
            }
            String responseJson = JsonUtils.toJson(responseMap);
            TradeScanPayResp resp = JsonUtils.toObject(responseJson, TradeScanPayResp.class);
            log.info("sdk返回数据是: \n" + JsonUtils.toJson(resp));
            return resp;
        }catch (Exception e){
            throw new AppBizException(AppExCode.UNKNOWN,"系统异常");
        }
    }

    /**
     * sdk退款
     *
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public TradeScanRefundResp scanRefund(TradeScanRefundReq req,String key) throws AppBizException {
        req.setType(StarPosConstant.SCAN_REFUND);
        req.setTxnCnl(StarPosConstant.TXNCNL_I);
        req.setBatchNo(StarPosConstant.BATCH_NO);
        req.setCharacterSet(StarPosConstant.CHARSET_UTF8);
        req.setVersion(StarPosConstant.VERSION);
        req.setRequestId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32));
        req.setSignType(StarPosConstant.SIGN_TYPE);
        req.setTelNo(StarPosConstant.TEL_NO);

        String paramJson = JsonUtils.toJson(req);
        Map<String, String> param = (Map<String, String>) JsonUtils.toCollection(paramJson, new TypeReference<Map<String, String>>() {
        });
        try{

            String sign = SecurityUtil.sign(param, key, "UTF-8");
            param.put("hmac", sign);
            String response = HttpConnectionPoolUtil.send(startPosParam.getUrl(), param);
            response = URLDecoder.decode(response, "UTF-8");
            String[] responseStrs = response.split("&");
            Map<String, String> responseMap = new HashMap<String, String>();
            for (String responseStr : responseStrs) {
                String[] params = responseStr.split("=");
                responseMap.put(params[0], params[1]);
            }
            String responseJson = JsonUtils.toJson(responseMap);
            TradeScanRefundResp resp = JsonUtils.toObject(responseJson, TradeScanRefundResp.class);
            log.info("sdk返回信息是： \n" + JsonUtils.toJson(resp));
            return resp;
        }catch (Exception e){
            throw new AppBizException(AppExCode.UNKNOWN,"系统异常");
        }
    }

    /**
     * sdk订单查询
     *
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public TradeScanQueryResp scanQuery(TradeScanQueryReq req,String key) throws AppBizException {
        req.setType(StarPosConstant.SCAN_QUERY);
        req.setBatchNo(StarPosConstant.BATCH_NO);
        req.setCharacterSet(StarPosConstant.CHARSET_UTF8);
        req.setVersion(StarPosConstant.VERSION);
        req.setRequestId(UUID.randomUUID().toString().replaceAll("-","").substring(0,32));
        req.setTelNo(StarPosConstant.TEL_NO);
        req.setSignType(StarPosConstant.SIGN_TYPE);
        String paramJson = JsonUtils.toJson(req);
        Map<String, String> param = (Map<String, String>) JsonUtils.toCollection(paramJson, new TypeReference<Map<String, String>>() {
        });
        try{

            String sign = SecurityUtil.sign(param, key, "UTF-8");
            param.put("hmac", sign);
            String response = HttpConnectionPoolUtil.send(startPosParam.getUrl(), param);
            response = URLDecoder.decode(response, "UTF-8");
            String[] responseStrs = response.split("&");
            Map<String, String> responseMap = new HashMap<String, String>();
            for (String responseStr : responseStrs) {
                String[] params = responseStr.split("=");
                responseMap.put(params[0], params[1]);
            }
            String responseJson = JsonUtils.toJson(responseMap);
            TradeScanQueryResp resp = JsonUtils.toObject(responseJson, TradeScanQueryResp.class);
            log.info("sdk返回信息是： \n" + JsonUtils.toJson(resp));
            return resp;
        }catch (Exception e){
            throw new AppBizException(AppExCode.UNKNOWN,"系统异常");
        }
    }

    /**
     * 客户主扫 动态码
     *
     * @param req
     * @return
     * @throws AppBizException
     */
    @Override
    public TradeQrcodeResp qrCode(TradeQrCodeReq req) throws AppBizException {
        req.setType(StarPosConstant.QR_CODE);
        req.setTxnCnl(StarPosConstant.TXNCNL_I);
        String paramJson = JsonUtils.toJson(req);
        Map<String, String> param = (Map<String, String>) JsonUtils.toCollection(paramJson, new TypeReference<Map<String, String>>() {
        });
        try {

            String sign = SecurityUtil.sign(param, startPosParam.getKey(), "UTF-8");
            param.put("hmac", sign);
            String response = HttpConnectionPoolUtil.send(startPosParam.getUrl(), param);
            response = URLDecoder.decode(response, "UTF-8");
            String[] responseStrs = response.split("&");
            Map<String, String> responseMap = new HashMap<String, String>();
            for (String responseStr : responseStrs) {
                String[] params = responseStr.split("=");
                responseMap.put(params[0], params[1]);
            }
            String responseJson = JsonUtils.toJson(responseMap);
            TradeQrcodeResp resp = JsonUtils.toObject(responseJson, TradeQrcodeResp.class);
            log.info("sdk返回信息是： \n" + JsonUtils.toJson(resp));
            return resp;
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "系统异常");
        }
    }

    /**
     * 公众号查询
     *
     * @param req
     * @return
     * @throws AppBizException
     */
    @Override
    public TradePubAuthQueryResp pubQuery(TradePubAuthQueryReq req) throws AppBizException {
        req.setType(StarPosConstant.PUB_AUTH_QUERY);
        try {
            String paramJson = JsonUtils.toJson(req);
            Map<String, String> param = (Map<String, String>) JsonUtils.toCollection(paramJson, new TypeReference<Map<String, String>>() {
            });

            String sign = SecurityUtil.sign(param, startPosParam.getKey(), "UTF-8");
            param.put("hmac", sign);
            String response = HttpConnectionPoolUtil.send(startPosParam.getUrl(), param);
            response = URLDecoder.decode(response, "UTF-8");
            String[] responseStrs = response.split("&");
            Map<String, String> responseMap = new HashMap<String, String>();
            for (String responseStr : responseStrs) {
                String[] params = responseStr.split("=");
                responseMap.put(params[0], params[1]);
            }
            String responseJson = JsonUtils.toJson(responseMap);
            TradePubAuthQueryResp resp = JsonUtils.toObject(responseJson, TradePubAuthQueryResp.class);
            log.info("sdk返回信息是： \n" + JsonUtils.toJson(resp));
            return resp;
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "系统异常");
        }
    }

    /**
     * 公众号支付
     *
     * @param req
     * @return
     * @throws AppBizException
     */
    @Override
    public TradePubAuthPayResp pubPay(TradePubAuthPayReq req,String key) throws AppBizException {
        req.setType(StarPosConstant.PUB_AUTH_QUERY);
        try{
            String paramJson = JsonUtils.toJson(req);
            Map<String, String> param = (Map<String, String>) JsonUtils.toCollection(paramJson, new TypeReference<Map<String, String>>() {
            });

            String sign = SecurityUtil.sign(param, key, "UTF-8");
            param.put("hmac", sign);
            String response = HttpConnectionPoolUtil.send(startPosParam.getUrl(), param);
            response = URLDecoder.decode(response, "UTF-8");
            String[] responseStrs = response.split("&");
            Map<String, String> responseMap = new HashMap<String, String>();
            for (String responseStr : responseStrs) {
                String[] params = responseStr.split("=");
                responseMap.put(params[0], params[1]);
            }
            String responseJson = JsonUtils.toJson(responseMap);
            TradePubAuthPayResp resp = JsonUtils.toObject(responseJson, TradePubAuthPayResp.class);
            log.info("sdk返回信息是： \n" + JsonUtils.toJson(resp));
            return resp;
        }catch (Exception e){
            throw new AppBizException(AppExCode.UNKNOWN,"系统异常");
        }
    }
}
