package com.posppay.newpay.modules.xposp.controller;

import com.posppay.newpay.common.validator.ValidatorUtils;
import com.posppay.newpay.modules.model.MethodType;
import com.posppay.newpay.modules.model.ProxyRequest;
import com.posppay.newpay.modules.model.ProxyResponse;
import com.posppay.newpay.modules.xposp.common.AppExCode;

import com.posppay.newpay.modules.xposp.entity.UrmMinf;
import com.posppay.newpay.modules.xposp.innertrans.InnerRouteService;
import com.shouft.newpay.base.exception.AppBizException;
import com.shouft.newpay.base.utils.CodecUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author zengjw
 * 功能： 这里处理pos上送的表单信息并应答给pos表单数据
 */
@Slf4j
@RestController
@RequestMapping("")
public class XpospController {

    @Resource
    private InnerRouteService innerRouteService;

    private static final String SYMBOL_EQUAL = "=";
    private static final String SYMBOL_AND = "&";
    private static final String HMAC = "hmac";

    /**
     * 统一入口
     */
    @PostMapping(value = "/service")
    public ProxyResponse service(HttpServletRequest request, @RequestBody JSONObject jsonObject) throws Throwable {
        ValidatorUtils.validateEntity(jsonObject);
        ProxyRequest proxyRequest = new ProxyRequest(jsonObject);
        if (StringUtils.isBlank(proxyRequest.getType())) {
            throw new AppBizException(AppExCode.PARAMS_NULL, " method[" + proxyRequest.getType() + "] is null");
        }
        UrmMinf urmMinf = innerRouteService.queryUrmMinf(proxyRequest.getMerchantId());
//        UrmComi urmComi = innerTransService.queryByMercId(proxyRequest.getMerchantId());
        // 校验method是否合法
        String methodName = proxyRequest.getType();
        methodName = MethodType.getValue(methodName);
        if (StringUtils.isBlank(methodName)) {
            throw new AppBizException(AppExCode.PARAMS_ILLEGAL, "method[" + proxyRequest.getType() + "] is illegal");
        }
        //mac校验
        if (StringUtils.isBlank(proxyRequest.getHmac())) {
            throw new AppBizException(AppExCode.PARAMS_NULL, "mac[" + proxyRequest.getType() + "] is null");
        }
        if (null==urmMinf) {
            throw new AppBizException(AppExCode.MERCHANT_NOT_EXIST, "商户信息不存在，请联系客户经理");
        }
        if (StringUtils.isBlank(urmMinf.getKey())) {
            throw new AppBizException(AppExCode.TERMINAL_NOT_ACTIVE, "商户未激活，请联系客户");
        }
        String resultSign = sign(proxyRequest.fieldMap(), urmMinf);
//        if (!proxyRequest.getOrgNo().equals("12425")) {
            if (!proxyRequest.getHmac().equals(resultSign)) {
                log.info("验签失败 requestHmac=" + proxyRequest.getHmac() + ", serviceHmac=" + resultSign);
                throw new AppBizException(AppExCode.CHECK_MAC_ERROR, "验签失败，请检查秘钥信息");
            }
//        }
        //2.交易分发
        int methodIdx = methodName.lastIndexOf(".");
        String invokeService = methodName.substring(0, methodIdx);
        String invokeMethod = methodName.substring(methodIdx + 1);
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        Object serviceObj = context.getBean(invokeService);
        Method method = serviceObj.getClass().getMethod(invokeMethod, ProxyRequest.class);

        //3.响应
        ProxyResponse proxyResponse = new ProxyResponse();
        try {
            proxyResponse = (ProxyResponse) method.invoke(serviceObj, proxyRequest);
        } catch (InvocationTargetException ite) {
            throw ite.getTargetException();
        }
        proxyResponse.setType(proxyRequest.getType());
        proxyResponse.setReturnCode(AppExCode.SUCCESS);
        proxyResponse.setReturnMsg("");
        proxyResponse.setOrgNo(proxyRequest.getOrgNo());
        proxyResponse.setSignType("MD5");
        proxyResponse.setVersion(proxyRequest.getVersion());
        //生成签名
        String returnSign = sign(proxyResponse.fieldMap(), urmMinf);
        proxyResponse.setHmac(returnSign);
        log.info("应答给商户系统的json报文是:" + JSONObject.fromObject(proxyResponse).toString());
        return proxyResponse;
    }


    /**
     * 功能： 验证签名
     *
     * @param request
     * @param urmMinf
     * @return
     */
    private String sign(Map<String, String> request, UrmMinf urmMinf) {
        if (null != request.get("hmac")) {
            request.remove("hmac");
        }
        String params = createLink(request);
//        System.out.println(params);
        params = params + "&key=" + urmMinf.getKey();
//        System.out.println(params);
        return CodecUtils.hexString(DigestUtils.md5(params));


    }

    protected String createLink(Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        List<String> keys = new ArrayList<String>(map.keySet());
        // 字段排序
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = (String) map.get(key);
            if (StringUtils.isBlank(value)) {
                continue;
            }
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                sb.append(key).append(SYMBOL_EQUAL).append(value);
            } else {
                sb.append(key).append(SYMBOL_EQUAL).append(value).append(SYMBOL_AND);
            }

        }
        return sb.toString();
    }
}
