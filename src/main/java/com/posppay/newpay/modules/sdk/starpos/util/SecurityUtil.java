package com.posppay.newpay.modules.sdk.starpos.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;


/**
 * @author zengjw
 * 验签工具类
 */
@Slf4j
public class SecurityUtil {
    static final String SIGN = "hmac";

	/**
	 * 签名计算(支付)
	 * @param params   请求参数
	 * @param secretKey   秘钥
	 * @param charset   字符集
	 * @return
	 * @throws Exception
	 */
	public static String sign(Map<String,String>params, String secretKey, String charset)
			throws Exception {
		log.info("开始签名计算");
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
