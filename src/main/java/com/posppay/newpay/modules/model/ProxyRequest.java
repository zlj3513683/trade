package com.posppay.newpay.modules.model;


import com.posppay.newpay.modules.xposp.aspect.AbstractXpospProxy;
import com.posppay.newpay.modules.xposp.aspect.ProxyParams;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * @author
 * create on 2018/10/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ProxyRequest extends AbstractXpospProxy implements Serializable{
//	/**
//	 * 设备sn
//	 */
//	@ProxyParams
//	private String deviceSn;
	/**
	 * 下游订单号(唯一标识)
	 */
	@ProxyParams
	private String outOrderNo;
	/**
	 * 随机数不长于 32 位
	 */
	@ProxyParams
	private String randomData;
	/**
	 * 字符集 TODO UTF-8
	 */
	@ProxyParams
	private String characterSet;
	/**
	 * 商户编号
	 */
	@ProxyParams
	private String merchantId;

	/**
	 * 签名加密方式
	 */
	@ProxyParams
	private String signType;
	/**
	 * 版本号 TODO 1.0.0
	 */
	@ProxyParams
	private String version;
	/**
	 * 签名数据
	 */
	@ProxyParams(joinMac = false)
	private String hmac;
	/**
	 * 操作员号
	 */
	@ProxyParams
	private String oprId;
	/**
	 * IP地址
	 */
	@ProxyParams
	private String ipAddress;
	/**
	 * 接口类型
	 */
	@ProxyParams
	private String type;
	/**
	 * 机构号
	 */
	@ProxyParams
	private String orgNo;
	/**
	 * 门店号
	 */
	@ProxyParams
	private String stoeNo;
	/**
	 * 业务参数json
	 */
	@ProxyParams
	private String reqDetail;

	public ProxyRequest(JSONObject json) {
		super(json);
	}

}
