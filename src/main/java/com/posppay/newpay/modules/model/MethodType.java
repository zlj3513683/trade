package com.posppay.newpay.modules.model;




/**
 * 功能： 接口类型枚举
 * @author zengjw
 */

public enum MethodType {

	/**
	 * 交易入口
	 */
	QR_CONSUME("扫码-消费", "com.pospay.xposp.post.consume","scanPay"),
	QR_QUERY("扫码-查询", "com.pospay.xposp.post.query","scanQuery"),
	QR_REFUND("扫码-退货", "com.pospay.xposp.post.refund","scanRefund"),
	QR_REFUND_QUERY("扫码-退货-查询", "com.pospay.xposp.post.refundquery","scanRefundQuery"),
	QR_QRCODE("扫码-生成二维码", "com.pospay.xposp.post.qrCode","qrCode"),
	QR_JSAPI("公众号/小程序下单", "com.pospay.xposp.post.jsapi","jsapi");
//	QrcodeRequest.class
	private String code;
	private String chnName;
	private String value;


	public static String getValue(String code) {
		for (MethodType method : MethodType.values()) {
			if (method.getCode().equals(code)) {
				return method.value;
			}
		}
		return null;
	}


	MethodType(String chnName, String value, String code) {
		this.chnName = chnName;
		this.value = value;
		this.code = code;

	}

	public String getChnName() {
		return chnName;
	}

	public void setChnName(String chnName) {
		this.chnName = chnName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
