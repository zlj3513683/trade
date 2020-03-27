package com.posppay.newpay.modules.sdk.cup.util;

import com.alibaba.fastjson.JSONObject;
import com.posppay.newpay.modules.sdk.cup.model.req.CupRequest;
import com.posppay.newpay.modules.sdk.cup.model.req.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 验签工具类
 * @author MaoNing
 * @date 2019/3/26
 */
@Slf4j
public class SecurityUtil {

	static final String PARAM_SIGN_TYPE = "signType";
	static final String SIGN_TYPE_MD5 = "md5";
	static final String PARAM_SIGN = "dataSign";
    static final String SIGN = "sign";


	/**
	 * 签名计算（商户）
	 * @param request
	 * @param signType
	 * @param secretKey
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String sign(CupRequest request, String signType, String secretKey, String charset)
			throws Exception {
		Map<String,String> params = JSONObject.parseObject(JSONObject.toJSONString(request),Map.class);
		if (params.containsKey(PARAM_SIGN)){
            params.remove(PARAM_SIGN);
        }
        if (params.containsKey(PARAM_SIGN_TYPE)){
            params.remove(PARAM_SIGN_TYPE);
        }
		params = paraFilter(params);

		// 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String prestr = createLinkString(params);
		// 把拼接后的字符串再与安全校验码直接连接起来
		prestr = prestr + secretKey;
		String sign = "";
		log.info("md5校验前：{}",prestr);
		if (SIGN_TYPE_MD5.equalsIgnoreCase(signType)) {
			sign = md5(prestr, charset);
		}
		log.info("md5校验后：{}",sign);
		return sign;
	}

	/**
	 * 签名计算(支付)
	 * @param request   请求参数
	 * @param secretKey   秘钥
	 * @param charset   字符集
	 * @return
	 * @throws Exception
	 */
	public static String sign(PaymentRequest request, String secretKey, String charset)
			throws Exception {
		log.info("开始签名计算");
		Map<String,String> params = JSONObject.parseObject(JSONObject.toJSONString(request),Map.class);
		if (params.containsKey(SIGN)){
		    params.remove(SIGN);
        }
		params = paraFilter(params);
		// 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String prestr = createLinkString(params);
		// 把拼接后的字符串再与安全校验码直接连接起来
		prestr = new StringBuffer().append(prestr).append("&key=") + secretKey;
		String sign = "";
		log.info("md5校验前：{}",prestr);
		sign = md5(prestr, charset);
		log.info("md5校验后：{}",sign);
		return sign.toUpperCase();
	}

    /**
     * 签名计算(支付响应)
     * @param params   响应参数
     * @param secretKey   秘钥
     * @param charset   字符集
     * @return
     * @throws Exception
     */
    public static boolean sign(Map<String,String> params, String secretKey, String charset)
            throws Exception {
        String responseSign = new String();
        String localSign = new String();
        if (params.containsKey(SIGN)){
            responseSign = params.get(SIGN);
            log.info("responseSign:{}",responseSign);
            params.remove(SIGN);
        }else{
            return false;
        }
        params = paraFilter(params);
        // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String prestr = createLinkString(params);
        // 把拼接后的字符串再与安全校验码直接连接起来
        prestr = new StringBuffer().append(prestr).append("&key=") + secretKey;
        log.info("md5校验前：{}",prestr);
        localSign = md5(prestr, charset).toUpperCase();
        log.info("md5校验后：{}",localSign);
        return responseSign.equals(localSign);
    }

/*	public static String sign(Map<String, String> params, String signType, String secrtkey, String charset)
			throws Exception {
		params = paraFilter(params);
		// 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String prestr = createLinkString(params);
		// 把拼接后的字符串再与安全校验码直接连接起来
		prestr = prestr + secrtkey;
		String sign = "";
		if (SIGN_TYPE_MD5.equalsIgnoreCase(signType)) {
			sign = md5(prestr, charset);
		}
		return sign;
	}*/

	public static Map<String, String> paraFilter(Map<String, String> sArray) {

		Map<String, String> result = new HashMap<>(16);
		if (sArray == null || sArray.size() <= 0) {
			return result;
		}
		for (String key : sArray.keySet()) {
		    //fastjson转map取出的值不一定是string类型
			String value = String.valueOf(sArray.get(key));
			if (StringUtils.isEmpty(value)) {
				continue;
			}
			result.put(key, value);
		}
		return result;
	}

	public static String createLinkString(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuffer prestr = new StringBuffer();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
            // 拼接时，不包括最后一个&字符
			if (i == keys.size() - 1) {
				prestr.append(key).append("=").append(value);
			} else {
				prestr.append(key).append("=").append(value).append("&");
			}
		}
		log.info("拼接的字符串为：{}",prestr);
		return prestr.toString();
	}

	public static String md5(String text, String charset) throws Exception {
		return DigestUtils.md5Hex(getContentBytes(text, charset));
	}

	private static byte[] getContentBytes(String content, String charset) throws Exception {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new Exception(e);
		}
	}
}
