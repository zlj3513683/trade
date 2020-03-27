package com.posppay.newpay.modules.xposp.router.channel;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zengjianwen
 */
@Data
public class ChannelResponseInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String payNo;

	/**
	 * 12
	 */
	private String transTime;
	/**
	 * 13
	 */
	private String transDate;

	/**
	 * 38
	 */
	private String authCode;
	/**返回码**/
	/**
	 * 39
	 */
	private String transCode;
	/**
	 * 响应消息
	 */
	private String transMsg;

	/**
	 * 终端流水号
	 */
	private String trmnlFlowNo;

	/**
	 * b扫c原始交易结果
	 */
	private String originalStatus;
	private String superTransType;
	private String chnOrdNo;


	/**
	 * 二维码地址
	 */
	private String payCode;
	private String amount;
	private String timestamp;
	private String result;
	private String transationId;
	private String outTranstionId;
	private String trmType;
	private String banBillNo;
	private String tradeStatus;
	private String body;
	private String goodTag;
	private String goodDetail;

	private String codeUrl;
	private String codeImgUrl;
	private String tokenId;
	private String payInfo;

	/**
	 * 借贷记标志
	 */
	private String bankType;
}
