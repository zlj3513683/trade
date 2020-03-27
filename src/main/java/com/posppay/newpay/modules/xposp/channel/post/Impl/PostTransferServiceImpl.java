package com.posppay.newpay.modules.xposp.channel.post.Impl;


import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.common.utils.StringUtils;
import com.posppay.newpay.modules.model.PayChannelType;
import com.posppay.newpay.modules.model.ProxyRequest;
import com.posppay.newpay.modules.model.ProxyResponse;
import com.posppay.newpay.modules.model.TransType;
import com.posppay.newpay.modules.sdk.pingan.config.ParamConfig;
import com.posppay.newpay.modules.xposp.common.AppExCode;
import com.posppay.newpay.modules.xposp.entity.Payment;
import com.posppay.newpay.modules.xposp.transfer.TransferServer;
import com.posppay.newpay.modules.xposp.channel.post.PostTransferService;
import com.posppay.newpay.modules.xposp.transfer.model.req.*;
import com.posppay.newpay.modules.xposp.transfer.model.resp.ConsumeResponse;
import com.posppay.newpay.modules.xposp.transfer.model.resp.JsapiResponse;
import com.posppay.newpay.modules.xposp.transfer.model.resp.QrcodeResponse;
import com.posppay.newpay.modules.xposp.transfer.model.resp.RefundResponse;
import com.shouft.newpay.base.exception.AppRTException;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 功能： 交易系统扫码入口
 *
 * @author zengjw
 */
@Slf4j
@Service("com.pospay.xposp.post")
public class PostTransferServiceImpl implements PostTransferService {
    @Resource
    private TransferServer transferServer;
    @Autowired
    private ParamConfig paramConfig;
//    @Resource
//    private InnerTransService innerTransService;

    /**
     * 扫码-消费业务入口实现
     *
     * @param proxyRequest
     * @return
     * @throws AppBizException
     */
    @Override
    public ProxyResponse consume(ProxyRequest proxyRequest) throws AppBizException {
        log.info("扫码-消费交易开始------------------------------------------------");
        // 1.参数校验
        if (StringUtils.isBlank(proxyRequest.getReqDetail())) {
            log.info("[消费交易请求 reqDetail为空！]");
            throw new AppBizException(AppExCode.PARAMS_NULL, "400 bad request");
        }
        JSONObject reqJson = null;
        try {
            log.info("ReqDetail:" + proxyRequest.getReqDetail());
            reqJson = JSONObject.fromObject(proxyRequest.getReqDetail());
        } catch (Exception e) {
            log.error("[解析交易请求失败，" + proxyRequest.getReqDetail() + "！].", e);
            throw new AppRTException(AppExCode.PARAMS_ILLEGAL, "400 bad request");
        }
        // 校验下游凭证号
        String outOrderNo = proxyRequest.getOutOrderNo();
        if (StringUtils.isBlank(outOrderNo)) {
            log.info("[消费交易，下游凭证号不存在！]");
            throw new AppBizException(AppExCode.PARAMS_NULL, "401 unauthorized");
        }
        ConsumeRequest request = null;
        try {
            request = new ConsumeRequest(reqJson);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "请求参数有误");
        }
        if (null == request.getTransTime()) {
            throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "请求参数有误");
        }
//        if (paramConfig.isBaffle()) {
//
//            request.setOutOrderNo(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32));
//        }

        if (StringUtils.isBlank(request.getAuthCode())) {
            throw new AppBizException(AppExCode.AUTHCODE_ERR, "付款码不允许为空");
        }
        if (StringUtils.isBlank(request.getPayChannel())) {
            throw new AppBizException(AppExCode.PAYCHANNEL_ERR, "付款类型不允许为空");
        }
        PayChannelType payChannelType = PayChannelType.fromCode(request.getPayChannel());
        if (null == payChannelType) {
            throw new AppBizException(AppExCode.UNKNOWN_PAYCHANNEL, "付款类型未知");
        }
        request.setOutOrderNo(proxyRequest.getOutOrderNo());
        request.setRandomData(proxyRequest.getRandomData());
        request.setCharacterSet(proxyRequest.getCharacterSet());
        request.setMerchantId(proxyRequest.getMerchantId());
        request.setOprId(proxyRequest.getOprId());
        request.setIpAddress(proxyRequest.getIpAddress());
        request.setType(proxyRequest.getType());
        request.setOrgNo(proxyRequest.getOrgNo());
        request.setStoeNo(proxyRequest.getStoeNo());


        //业务处理
        Payment payment = transferServer.consume(request);
        //返回数据处理
        ConsumeResponse response = new ConsumeResponse();
        response.setPayNo(payment.getPayNo())
                .setOutOrderNo(payment.getOutOrderNo())
                .setMrchNo(payment.getMrchNo())
                .setMrchName(payment.getMrchName())
                .setCurrency(payment.getCurrency())
                .setTransType(payment.getTransType())
                .setTransCode(payment.getTransCode())
                .setTransMsg(payment.getTransMsg())
                .setSubTransType(payment.getSubTransType())
                .setOutTranstionId(request.getOutTranstionId())
                .setMrchName(payment.getMrchName())
                .setGoodsTag(request.getGoodsTag())
                .setGoodsDetail(request.getGoodsDetail())
                .setAttach(request.getAttach())
                .setStatus(payment.getTransStatus())
                .setBody(payment.getOutBody())
                .setOutTranstionId(payment.getOutTransactionId())
                .setBankType(request.getBankType());
        if (null != payment.getTransAmount()) {
            response.setAmount(StringUtils.decimal2bcd4yuan(payment.getTransAmount()));
        }
        if (StringUtils.isNotBlank(payment.getTransType())) {
            response.setTransTypeNm(TransType.fromTransTypeOrdinal(payment.getTransType()).getChineseName());
        }
        log.info("扫码-消费交易结束------------------------------------------------");
        //交易返回处理
//        return response;
        ProxyResponse proxyResponse = new ProxyResponse();
        proxyResponse.setRespDetail(JSONObject.fromObject(response).toString());
        return proxyResponse;
    }

    /**
     * 扫码-查询业务入口实现
     *
     * @param proxyRequest
     * @return
     * @throws AppBizException
     */
    @Override
    public ProxyResponse query(ProxyRequest proxyRequest) throws AppBizException {
        log.info("消费查询交易开始------------------------------------------------");
        // 1.参数校验
        if (StringUtils.isBlank(proxyRequest.getReqDetail())) {
            log.info("[查询交易请求 reqDetail为空！]");
            throw new AppBizException(AppExCode.PARAMS_NULL, "400 bad request");
        }
        JSONObject reqJson = null;
        try {
            log.info("ReqDetail:" + proxyRequest.getReqDetail());
            reqJson = JSONObject.fromObject(proxyRequest.getReqDetail());
        } catch (Exception e) {
            log.error("[解析交易请求失败，" + proxyRequest.getReqDetail() + "！].", e);
            throw new AppRTException(AppExCode.PARAMS_ILLEGAL, "400 bad request");
        }
        // 校验下游凭证号
        String outOrderNo = proxyRequest.getOutOrderNo();
        if (StringUtils.isBlank(outOrderNo)) {
            log.info("[查询交易，下游凭证号不存在！]");
            throw new AppBizException(AppExCode.PARAMS_NULL, "401 unauthorized");
        }


        QueryRequest request = null;
        try {
            request = new QueryRequest(reqJson);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "请求参数有误");
        }
        if (null == request.getOriTransDate()) {
            throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "请检查原始交易日期");
        }
        request.setOutOrderNo(proxyRequest.getOutOrderNo());
        request.setRandomData(proxyRequest.getRandomData());
        request.setCharacterSet(proxyRequest.getCharacterSet());
        request.setMerchantId(proxyRequest.getMerchantId());
        request.setOprId(proxyRequest.getOprId());
        request.setIpAddress(proxyRequest.getIpAddress());
        request.setType(proxyRequest.getType());
        request.setOrgNo(proxyRequest.getOrgNo());
        request.setStoeNo(proxyRequest.getStoeNo());
        //业务处理
        Payment payment = transferServer.scanQuery(request);
        //返回数据处理
        ConsumeResponse response = new ConsumeResponse();
        response.setPayNo(payment.getPayNo())
                .setOutOrderNo(payment.getOutOrderNo())
                .setMrchNo(payment.getMrchNo())
                .setMrchName(payment.getMrchName())
                .setCurrency(payment.getCurrency())
                .setTransType(payment.getTransType())
                .setTransCode(payment.getTransCode())
                .setTransMsg(payment.getRemark())
                .setSubTransType(payment.getSubTransType())
                .setOutTranstionId(request.getOutTranstionId())
                .setGoodsTag(request.getGoodsTag())
                .setGoodsDetail(request.getGoodsDetail())
                .setAttach(payment.getAttach())
                .setStatus(payment.getTransStatus())
                .setOutTranstionId(payment.getOutTransactionId())
                .setBody(request.getBody())
                .setGoodsTag(request.getGoodsTag())
                .setGoodsDetail(request.getGoodsDetail())
                .setBankType(payment.getBankType());
        if (null != payment.getTransAmount()) {
            response.setAmount(StringUtils.decimal2bcd4yuan(payment.getTransAmount()));
        }
        if (StringUtils.isNotBlank(payment.getTransType())) {
            response.setTransTypeNm(TransType.fromTransTypeOrdinal(payment.getTransType()).getChineseName());
        }
        log.info("消费查询交易结束------------------------------------------------");
        //交易返回处理
//        return response;
        //交易返回处理
//        return response;
        ProxyResponse proxyResponse = new ProxyResponse();
        proxyResponse.setRespDetail(JSONObject.fromObject(response).toString());
        return proxyResponse;
    }

    /**
     * 功能: 状态查询
     *
     * @param proxyRequest
     * @return
     * @throws AppBizException
     */
    @Override
    public ProxyResponse refundquery(ProxyRequest proxyRequest) throws AppBizException {
        log.info("退货查询交易开始------------------------------------------------");
        // 1.参数校验
        if (StringUtils.isBlank(proxyRequest.getReqDetail())) {
            log.info("[查询交易请求 reqDetail为空！]");
            throw new AppBizException(AppExCode.PARAMS_NULL, "400 bad request");
        }
        JSONObject reqJson = null;
        try {
            log.info("ReqDetail:" + proxyRequest.getReqDetail());
            reqJson = JSONObject.fromObject(proxyRequest.getReqDetail());
        } catch (Exception e) {
            log.error("[解析交易请求失败，" + proxyRequest.getReqDetail() + "！].", e);
            throw new AppRTException(AppExCode.PARAMS_ILLEGAL, "请求参数有误");
        }
        // 校验下游凭证号
        String outOrderNo = proxyRequest.getOutOrderNo();
        if (StringUtils.isBlank(outOrderNo)) {
            log.info("[查询交易，下游凭证号不存在！]");
            throw new AppBizException(AppExCode.PARAMS_NULL, "401 unauthorized");
        }


        QueryRequest request=null;
        try{

            request = new QueryRequest(reqJson);
        }catch (Exception e){
            throw new AppBizException(AppExCode.PARAMS_ILLEGAL,"请求参数有误");
        }
        if (null == request.getOriTransDate()) {
            throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "请求参数有误");
        }
        request.setOutOrderNo(proxyRequest.getOutOrderNo());
        request.setRandomData(proxyRequest.getRandomData());
        request.setCharacterSet(proxyRequest.getCharacterSet());
        request.setMerchantId(proxyRequest.getMerchantId());
        request.setOprId(proxyRequest.getOprId());
        request.setIpAddress(proxyRequest.getIpAddress());
        request.setType(proxyRequest.getType());
        request.setOrgNo(proxyRequest.getOrgNo());
        request.setStoeNo(proxyRequest.getStoeNo());
        //业务处理
        Payment payment = transferServer.refundQuery(request);
        //返回数据处理
        RefundResponse response = new RefundResponse();
        response.setPayNo(payment.getPayNo())
                .setOutOrderNo(payment.getOutOrderNo())
                .setMrchNo(payment.getMrchNo())
                .setMrchName(payment.getMrchName())
                .setCurrency(payment.getCurrency())
                .setTransType(payment.getTransType())
                .setTransCode(payment.getTransCode())
                .setTransMsg(payment.getRemark())
                .setSubTransType(payment.getSubTransType())
                .setOutTranstionId(request.getOutTranstionId())
                .setMrchName(payment.getMrchName())
                .setGoodsTag(request.getGoodsTag())
                .setGoodsDetail(request.getGoodsDetail())
                .setAttach(payment.getAttach())
                .setStatus(payment.getTransStatus())
                .setOutTranstionId(payment.getOutTransactionId())
                .setBankType(payment.getBankType())
                .setBody(payment.getOutBody());
        if (null != payment.getTransAmount()) {
            response.setAmount(StringUtils.decimal2bcd4yuan(payment.getTransAmount()));
        }
        if (StringUtils.isNotBlank(payment.getTransType())) {
            response.setTransTypeNm(TransType.fromTransTypeOrdinal(payment.getTransType()).getChineseName());
        }

        log.info("退货查询交易结束------------------------------------------------");
        //交易返回处理
//        return response;
        //交易返回处理
//        return response;
        ProxyResponse proxyResponse = new ProxyResponse();
        proxyResponse.setRespDetail(JSONObject.fromObject(response).toString());
        return proxyResponse;
    }

    /**
     * 扫码-退款业务入口实现
     *
     * @param proxyRequest
     * @return
     * @throws AppBizException
     */
    @Override
    public ProxyResponse refund(ProxyRequest proxyRequest) throws AppBizException {
        log.info("退货交易开始了.......................");
        // 1.参数校验
        if (StringUtils.isBlank(proxyRequest.getReqDetail())) {
            log.info("[查询交易请求 reqDetail为空！]");
            throw new AppBizException(AppExCode.PARAMS_NULL, "400 bad request");
        }
        JSONObject reqJson = null;
        try {
            log.info("ReqDetail:" + proxyRequest.getReqDetail());
            reqJson = JSONObject.fromObject(proxyRequest.getReqDetail());
        } catch (Exception e) {
            log.error("[解析交易请求失败，" + proxyRequest.getReqDetail() + "！].", e);
            throw new AppRTException(AppExCode.PARAMS_ILLEGAL, "400 bad request");
        }
        // 校验下游凭证号
        String outOrderNo = proxyRequest.getOutOrderNo();
        if (StringUtils.isBlank(outOrderNo)) {
            log.info("[退货交易，下游凭证号不存在！]");
            throw new AppBizException(AppExCode.PARAMS_NULL, "请求参数有误");
        }
        RefundRequest request = null;
        try {
            request = new RefundRequest(reqJson);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppBizException(AppExCode.PAYCHANNEL_ERR, "请求参数有误");
        }
        if (null == request.getTransTime()) {
            throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "请求参数有误");
        }
        if (null == request.getOriTransDate()) {
            throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "请求参数有误");
        }
        request.setOutOrderNo(proxyRequest.getOutOrderNo());
        request.setRandomData(proxyRequest.getRandomData());
        request.setCharacterSet(proxyRequest.getCharacterSet());
        request.setMerchantId(proxyRequest.getMerchantId());
        request.setOprId(proxyRequest.getOprId());
        request.setIpAddress(proxyRequest.getIpAddress());
        request.setType(proxyRequest.getType());
        request.setOrgNo(proxyRequest.getOrgNo());
        request.setStoeNo(proxyRequest.getStoeNo());

        //业务处理
        Payment payment = transferServer.refund(request);
        //返回数据处理
        RefundResponse response = new RefundResponse();
        response.setPayNo(payment.getPayNo())
                .setOutOrderNo(payment.getOutOrderNo())
                .setMrchNo(payment.getMrchNo())
                .setMrchName(payment.getMrchName())
                .setCurrency(payment.getCurrency())
                .setTransType(payment.getTransType())
                .setTransCode(payment.getTransCode())
                .setTransMsg(payment.getTransMsg())
                .setSubTransType(payment.getSubTransType())
                .setOutTranstionId(payment.getOutTransactionId())
                .setStatus(payment.getTransStatus())
                .setBankType(payment.getBankType())
                .setBody(payment.getOutBody())
                .setAttach(payment.getAttach());
        if (null != payment.getTransAmount()) {
            response.setAmount(StringUtils.decimal2bcd4yuan(payment.getTransAmount()));
        }
        if (StringUtils.isNotBlank(payment.getTransType())) {
            response.setTransTypeNm(TransType.fromTransTypeOrdinal(payment.getTransType()).getChineseName());
        }
//
        ProxyResponse proxyResponse = new ProxyResponse();
        proxyResponse.setRespDetail(JSONObject.fromObject(response).toString());
        log.info("退货交易结束了.........................");
        return proxyResponse;
    }

    /**
     * 扫码-二维码生成业务入口实现
     *
     * @param proxyRequest
     * @return
     * @throws AppBizException
     */
    @Override
    public ProxyResponse qrCode(ProxyRequest proxyRequest) throws AppBizException {
        log.info("C扫B下单开始了-----------------------------------------------");
        // 1.参数校验
        if (StringUtils.isBlank(proxyRequest.getReqDetail())) {
            log.info("[C扫B下单请求 reqDetail为空！]");
            throw new AppBizException(AppExCode.PARAMS_NULL, "400 bad request");
        }
        JSONObject reqJson = null;
        try {
            log.info("ReqDetail:" + proxyRequest.getReqDetail());
            reqJson = JSONObject.fromObject(proxyRequest.getReqDetail());
        } catch (Exception e) {
            log.error("[解析C扫B下单请求失败，" + proxyRequest.getReqDetail() + "！].", e);
            throw new AppRTException(AppExCode.PARAMS_ILLEGAL, "400 bad request");
        }
        // 校验下游凭证号
        String outOrderNo = proxyRequest.getOutOrderNo();
        if (StringUtils.isBlank(outOrderNo)) {
            log.info("[C扫B下单，下游凭证号不存在！]");
            throw new AppBizException(AppExCode.PARAMS_NULL, "401 unauthorized");
        }
        QrcodeRequest request = null;
        try {
            request = new QrcodeRequest(reqJson);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "请求参数有误");
        }
        if (null == request.getTransTime()) {
            throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "请求参数有误");
        }
//        if (paramConfig.isBaffle()) {
//
//            request.setOutOrderNo(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32));
//        }

        if (StringUtils.isBlank(request.getPayChannel())) {
            throw new AppBizException(AppExCode.PAYCHANNEL_ERR, "付款类型不允许为空");
        }
        PayChannelType payChannelType = PayChannelType.fromCode(request.getPayChannel());
        if (null == payChannelType) {
            throw new AppBizException(AppExCode.UNKNOWN_PAYCHANNEL, "付款类型未知");
        }
        if (PayChannelType.WX_ZS != payChannelType &&
                PayChannelType.ALI_ZS != payChannelType &&
                PayChannelType.CUP_ZS != payChannelType) {
            throw new AppBizException(AppExCode.PAYCHANNEL_ERR, "付款类型非法");
        }
        request.setOutOrderNo(proxyRequest.getOutOrderNo());
        request.setRandomData(proxyRequest.getRandomData());
        request.setCharacterSet(proxyRequest.getCharacterSet());
        request.setMerchantId(proxyRequest.getMerchantId());
        request.setOprId(proxyRequest.getOprId());
        request.setIpAddress(proxyRequest.getIpAddress());
        request.setType(proxyRequest.getType());
        request.setOrgNo(proxyRequest.getOrgNo());
        request.setStoeNo(proxyRequest.getStoeNo());


        //业务处理
        Payment payment = transferServer.qrCode(request);
        //返回数据处理
        QrcodeResponse response = new QrcodeResponse();
        response.setPayNo(payment.getPayNo())
                .setOutOrderNo(payment.getOutOrderNo())
                .setMrchNo(payment.getMrchNo())
                .setMrchName(payment.getMrchName())
                .setCurrency(payment.getCurrency())
                .setTransType(payment.getTransType())
                .setTransCode(payment.getTransCode())
                .setTransMsg(payment.getTransMsg())
                .setSubTransType(payment.getSubTransType())
                .setOutTranstionId(request.getOutTranstionId())
                .setMrchName(payment.getMrchName())
                .setGoodsTag(request.getGoodsTag())
                .setGoodsDetail(request.getGoodsDetail())
                .setAttach(request.getAttach())
                .setStatus(payment.getTransStatus())
                .setBody(payment.getOutBody())
                .setOutTranstionId(payment.getOutTransactionId())
                .setCodeUrl(payment.getCodeUrl())
                .setCodeImg(payment.getCodeImg())
                .setBankType(request.getBankType());
        if (null != payment.getTransAmount()) {
            response.setAmount(StringUtils.decimal2bcd4yuan(payment.getTransAmount()));
        }
        if (StringUtils.isNotBlank(payment.getTransType())) {
            response.setTransTypeNm(TransType.fromTransTypeOrdinal(payment.getTransType()).getChineseName());
        }
        log.info("C扫B下单结束------------------------------------------------");
        //交易返回处理
//        return response;
        ProxyResponse proxyResponse = new ProxyResponse();
        proxyResponse.setRespDetail(JSONObject.fromObject(response).toString());
        return proxyResponse;
    }

    @Override
    public ProxyResponse jsapi(ProxyRequest proxyRequest) throws AppBizException {
        log.info("公众号下单开始了-----------------------------------------------");
        // 1.参数校验
        if (StringUtils.isBlank(proxyRequest.getReqDetail())) {
            log.info("[公众号下单请求 reqDetail为空！]");
            throw new AppBizException(AppExCode.PARAMS_NULL, "400 bad request");
        }
        JSONObject reqJson = null;
        try {
            log.info("ReqDetail:" + proxyRequest.getReqDetail());
            reqJson = JSONObject.fromObject(proxyRequest.getReqDetail());
        } catch (Exception e) {
            log.error("[解析公众号下单请求失败，" + proxyRequest.getReqDetail() + "！].", e);
            throw new AppRTException(AppExCode.PARAMS_ILLEGAL, "400 bad request");
        }
        // 校验下游凭证号
        String outOrderNo = proxyRequest.getOutOrderNo();
        if (StringUtils.isBlank(outOrderNo)) {
            log.info("[公众号下单，下游凭证号不存在！]");
            throw new AppBizException(AppExCode.PARAMS_NULL, "401 unauthorized");
        }
        JsapiRequest request = null;
        try {
            request = new JsapiRequest(reqJson);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "请求参数有误");
        }
        if (null == request.getTransTime()) {
            throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "请求参数有误");
        }
//        if (paramConfig.isBaffle()) {
//
//            request.setOutOrderNo(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32));
//        }

        if (StringUtils.isBlank(request.getPayChannel())) {
            throw new AppBizException(AppExCode.PAYCHANNEL_ERR, "付款类型不允许为空");
        }
        PayChannelType payChannelType = PayChannelType.fromCode(request.getPayChannel());
        if (null == payChannelType) {
            throw new AppBizException(AppExCode.UNKNOWN_PAYCHANNEL, "付款类型未知");
        }
        if (PayChannelType.WX_GZH != payChannelType &&
                PayChannelType.ALI_GZH != payChannelType &&
                PayChannelType.CUP_GZH != payChannelType) {
            throw new AppBizException(AppExCode.PAYCHANNEL_ERR, "付款类型非法");
        }
        request.setOutOrderNo(proxyRequest.getOutOrderNo());
        request.setRandomData(proxyRequest.getRandomData());
        request.setCharacterSet(proxyRequest.getCharacterSet());
        request.setMerchantId(proxyRequest.getMerchantId());
        request.setOprId(proxyRequest.getOprId());
        request.setIpAddress(proxyRequest.getIpAddress());
        request.setType(proxyRequest.getType());
        request.setOrgNo(proxyRequest.getOrgNo());
        request.setStoeNo(proxyRequest.getStoeNo());


        //业务处理
        Payment payment = transferServer.jsapi(request);
        //返回数据处理
        JsapiResponse response = new JsapiResponse();
        response.setPayNo(payment.getPayNo())
                .setOutOrderNo(payment.getOutOrderNo())
                .setMrchNo(payment.getMrchNo())
                .setMrchName(payment.getMrchName())
                .setCurrency(payment.getCurrency())
                .setTransType(payment.getTransType())
                .setTransCode(payment.getTransCode())
                .setTransMsg(payment.getTransMsg())
                .setSubTransType(payment.getSubTransType())
                .setOutTranstionId(request.getOutTranstionId())
                .setMrchName(payment.getMrchName())
                .setGoodsTag(request.getGoodsTag())
                .setGoodsDetail(request.getGoodsDetail())
                .setAttach(request.getAttach())
                .setStatus(payment.getTransStatus())
                .setBody(payment.getOutBody())
                .setOutTranstionId(payment.getOutTransactionId())
                .setPayInfo(payment.getPayInfo())
                .setBankType(request.getBankType());
        if (null != payment.getTransAmount()) {
            response.setAmount(StringUtils.decimal2bcd4yuan(payment.getTransAmount()));
        }
        if (StringUtils.isNotBlank(payment.getTransType())) {
            response.setTransTypeNm(TransType.fromTransTypeOrdinal(payment.getTransType()).getChineseName());
        }
        log.info("公众号下单结束------------------------------------------------");
        //交易返回处理
//        return response;
        ProxyResponse proxyResponse = new ProxyResponse();
        proxyResponse.setRespDetail(JSONObject.fromObject(response).toString());
        return proxyResponse;
    }
}
