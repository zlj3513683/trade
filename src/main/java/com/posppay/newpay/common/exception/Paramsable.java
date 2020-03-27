package com.posppay.newpay.common.exception;

/**
 * <p>异常可参数化传递接口</p>
 *
 * <p>该接口一般使用于异常需要进行上下文的数据对象传递，但通常不推荐这么使用</p>
 * <p>一般建议在界面类似需要调用<tt>MessageFormat.format(String pattern,Object ... args)</tt>时使用。</p>
 *
 *
 * <p>对该行为的处理实现参考：</p>
 * <p><tt>@see com.intensoft.exception.msgtranslate.SimpleExceptionTranslate</tt></p>
 *
 * @author lance
 *
 */
public interface Paramsable {

	/**
	 * <p>返回需要进行上下文传递的参数对象</p>
	 *
	 * @return
	 * 		参数对象
	 */
	public Object[] getParams();

}
