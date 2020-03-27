package com.posppay.newpay.modules.xposp.router;

import java.util.Map;

/**
 * 交易渠道路由管理
 *
 * @author lance
 *
 */
public interface TransferRouter {

	/**
	 * 根据通道名称获取一个交易渠道数据
	 *
	 * @param chnName
	 * @return
	 */
	TransferChannel findChannelByName(String chnName);


	/**
	 * 获取具体的渠道
	 * @return
	 */
	Map<String,TransferChannel> getChannels();


}
