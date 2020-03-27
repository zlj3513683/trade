package com.posppay.newpay.modules.xposp.aspect;

import com.posppay.newpay.common.utils.StringUtils;
import com.shouft.newpay.base.formater.Formatter;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.*;

public class AbstractXpospProxy {

	private static final Logger logger = LoggerFactory.getLogger(AbstractXpospProxy.class);
	private static final String SYMBOL_EQUAL = "=";
	private static final String SYMBOL_AND = "&";

	public AbstractXpospProxy(){

	}

	public AbstractXpospProxy(JSONObject json){
		String version = (String) json.getString("version");

		if(StringUtils.isBlank(version)){
			throw new IllegalArgumentException("version should not be null!");
		}

//		switch (Integer.valueOf(version)) {
//			case 1:
//				try {
//					parseV3(json);
//				} catch (Exception e) {
//					throw new IllegalArgumentException("parse json by v3 failed!",e);
//				}
//				break;
//
//			default:
//				throw new IllegalArgumentException("unknown version:"+version);
//		}
		try {
			parseV3(json);
		} catch (Exception e) {
			throw new IllegalArgumentException("parse json by v3 failed!",e);
		}

	}

	protected void parseV3(JSONObject json) throws IllegalArgumentException, IllegalAccessException, InstantiationException, UnsupportedEncodingException, IOException {
		Field[] fields = getClass().getDeclaredFields();
		for(Field f:fields){
			ProxyParams params = null;
			if((params = f.getAnnotation(ProxyParams.class)) != null){
				String name = f.getName();
				String value = null;
				try{
					value = (String) json.getString(name);
				}catch (JSONException e) {
					continue;
				}
				if(value == null){
					logger.debug("parse json:"+name+" is null!");
					continue;
				}
				Formatter formatter = params.formatter().newInstance();
				String pattern = params.formatPattern();
				if(!StringUtils.isEmpty(pattern)){
					formatter.setPattern(pattern);
				}
				Object rslt = formatter.unformat(value);
				f.setAccessible(true);
				f.set(this, rslt);
			}
		}
	}

//	public byte[] macData() {
//		Map<String, String> map = fieldMap();
//		String macData = createLink(map);
//		logger.debug("original mac data:"+macData);
//		//转成大写，加密机不支持小写字母
//		String macStr = DigestUtils.md5Hex(macData).toUpperCase();
//        System.out.println(macStr);
//		return macStr.getBytes();
//	}

	public Map<String, String> fieldMap(){
		Map<String, String> map = new HashMap<String, String>(16);
		try {
			Field[] fields = getClass().getDeclaredFields();
			for(Field f:fields){
				if(f.isAnnotationPresent(ProxyParams.class)&&f.getAnnotation(ProxyParams.class).joinMac()){
					String propertyName = f.getName();
					String propertyValue = BeanUtils.getProperty(this, propertyName);
					//空字符串或者null不参与计算
					if(StringUtils.isBlank(propertyValue)){
						logger.debug("propertyName["+propertyName+"] value is null!");
						continue;
					}
					map.put(propertyName, propertyValue);
				}
			}
		} catch (Exception e) {
			logger.error("get macData failed!");
			e.printStackTrace();
		}
		return map;
	}

	protected String createLink(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		List<String> keys = new ArrayList<String>(map.keySet());
		// 字段排序
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = map.get(key);
			if(StringUtils.isBlank(value)){
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
