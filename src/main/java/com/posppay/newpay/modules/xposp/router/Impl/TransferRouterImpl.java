package com.posppay.newpay.modules.xposp.router.Impl;


import com.posppay.newpay.modules.xposp.router.TransferChannel;
import com.posppay.newpay.modules.xposp.router.TransferRouter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author VJ
 */
@Service("transferRouter")
public class TransferRouterImpl implements TransferRouter,BeanPostProcessor {

	private static Map<String, TransferChannel> channels = new HashMap<String,TransferChannel>();

	@Override
	public TransferChannel findChannelByName(String chnName) {
		return channels.get(chnName);
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)	throws BeansException {
		if(bean instanceof TransferChannel){
			TransferChannel channel = (TransferChannel)bean;
			channels.put(beanName, channel);
		}
		return bean;
	}

	@Override
	public Map<String, TransferChannel> getChannels() {
		return Collections.unmodifiableMap(channels);
	}

}
