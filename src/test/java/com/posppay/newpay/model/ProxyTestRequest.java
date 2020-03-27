package com.posppay.newpay.model;


import com.posppay.newpay.modules.xposp.aspect.AbstractXpospProxy;
import com.posppay.newpay.modules.xposp.aspect.ProxyParams;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * @author
 * create on 2018/10/18
 */
@Data
@NoArgsConstructor
public class ProxyTestRequest {
	/**
	 * 设备sn
	 */

	private String deviceSn;
	/**
	 * 下游订单号(唯一标识)
	 */

	private String outOrderNo;
	/**
	 * 随机数不长于 32 位
	 */

	private String randomData;
	/**
	 * 字符集 TODO UTF-8
	 */

	private String characterSet;
	/**
	 * 商户编号
	 */

	private String merchantId;

	/**
	 * 签名加密方式
	 */

	private String signType;
	/**
	 * 版本号 TODO 1.0.0
	 */

	private String version;
	/**
	 * 签名数据
	 */
	private String hmac;
	/**
	 * 操作员号
	 */

	private String oprId;
	/**
	 * IP地址
	 */

	private String ipAddress;
	/**
	 * 接口类型
	 */

	private String type;
	/**
	 * 机构号
	 */

	private String orgNo;
	/**
	 * 业务参数json
	 */

	private String reqDetail;

//	public ProxyTestRequest(JSONObject json) {
//		super(json);
//	}

}
