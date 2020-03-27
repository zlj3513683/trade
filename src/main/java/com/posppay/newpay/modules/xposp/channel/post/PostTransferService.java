package com.posppay.newpay.modules.xposp.channel.post;

import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.modules.model.ProxyRequest;
import com.posppay.newpay.modules.model.ProxyResponse;


/**
 *
 * 功能：杉屿系统交易前置入口
 *
 * @author zengjianwen
 */
public interface PostTransferService {

	/**
	 * 功能: 消费
	 * @param request
	 * @return
	 * @throws AppBizException
	 */
	public ProxyResponse consume(ProxyRequest request)throws AppBizException;
	/**
	 * 功能: 状态查询
	 * @param request
	 * @return
	 * @throws AppBizException
	 */
	public ProxyResponse query(ProxyRequest request)throws AppBizException;
	/**
	 * 功能: 状态查询
	 * @param request
	 * @return
	 * @throws AppBizException
	 */
	public ProxyResponse refundquery(ProxyRequest request)throws AppBizException;
	/**
	 * 功能: 退货
	 * @param proxyRequest
	 * @return
	 * @throws AppBizException
	 */
	public ProxyResponse refund(ProxyRequest proxyRequest)throws AppBizException;
	/**
	 * 功能: 生成二维码
	 * @param request
	 * @return
	 * @throws AppBizException
	 */
	public ProxyResponse qrCode(ProxyRequest request)throws AppBizException;
	/**
	 * 功能: 公众号支付
	 * @param request
	 * @return
	 * @throws AppBizException
	 */
	public ProxyResponse jsapi(ProxyRequest request)throws AppBizException;



}
