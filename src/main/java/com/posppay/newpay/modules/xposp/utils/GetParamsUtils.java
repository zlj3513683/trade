package com.posppay.newpay.modules.xposp.utils;

import com.alibaba.fastjson.JSONObject;
import com.posppay.newpay.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zengjw
 */
@Slf4j
public class GetParamsUtils {


    public static String jsonReq(HttpServletRequest request) {
        BufferedReader br;
        StringBuilder sb = null;
        String reqBody = null;
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line = null;
            sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            if (sb.length() < 1) {
                return "";
            }
            reqBody = URLDecoder.decode(sb.toString(), "UTF-8");
            reqBody = reqBody.substring(reqBody.indexOf("{"));
            return reqBody;
        } catch (IOException e) {
            log.error("获取json参数错误！{}", e.getMessage());
            return "";
        }
    }

    public static Map<String, String> getParamsMap(HttpServletRequest request) {
        Map<String, String> paramsMap = new HashMap<>(16);
        String json = jsonReq(request);
        if (com.posppay.newpay.common.utils.StringUtils.isNotBlank(json)) {
            return JSONObject.parseObject(json, Map.class);
        }
        return paramsMap;
    }

    public static Map<String, String> getParamsMap() {
        Map<String, String> paramsMap = new HashMap<>(16);
        try {
            ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = sra.getRequest();
            String json = jsonReq(request);
            if (StringUtils.isNotBlank(json)) {
                return JSONObject.parseObject(json, Map.class);
            }
        } catch (Exception e) {
            log.error("json参数转换错误！{}", e.getMessage());
        }
        return paramsMap;
    }

}
