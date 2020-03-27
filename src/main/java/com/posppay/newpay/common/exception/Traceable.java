package com.posppay.newpay.common.exception;


/**
 * <p>异常可追踪行为描述</p>
 * <p>继承该接口异常，需要保证，在上层调用
 * {@link com.newland.base.exception.base.Traceable#getTraceId getTraceId}时，能够获取
 * 一个唯一标识该异常的id号，对于不同继承的异常，也必须确保能够唯一追踪。
 * </p>
 * <p>鉴于对影响异常构造的担心，推荐对id生成延时处理</p>
 *
 * @author lance
 *
 */
public interface Traceable {

	/**
	 * 获取对应的追踪号
	 * @return
	 *
	 */
	public String getTraceId();

	/**
	 * 获取对应的发生异常的时间戳
	 * @return
	 */
	public long getTimestamp();

}
