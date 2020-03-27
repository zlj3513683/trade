package com.posppay.newpay.common.exception;


/**
 * <p>异常描述性接口</p>
 * <p>该接口实现判定一般出现在需要将异常<b>本地化</b>时。</p>
 * <p>比较常见的，包含界面生成错误反馈信息和对外部系统的交互描述信息。</p>
 * <p>当该接口被接受时，可以判定该异常是通过对应异常所定义的资源和<tt>code</tt>格式化成具体本地化的消息。</p>
 * <p>具体格式化方式依赖于本地的实现。例如：</p>
 * <p><blockquote><pre>
 *
 * 		ResourceBundle resourceBundle;
 * 		String desc = null;
 * 		try{
 *			...
 * 		}catch(Exception e){
 * 			if(e instanceof Describable){
 * 				desc = resourceBundle.getString(((Describable)e).getCode());
 * 			}else{
 *				if(locale.equals(Locale.SIMPLIFIED_CHINESE)){
 *					return _UNKNOWN_EXCEPTION_MSG_ZHCN;
 *				}else{
 *					return _UNKNOWN_EXCEPTION_MSG_OTHERS;
 *				}
 * 			}
 *		}
 * </pre></p></blockquote>
 *
 *
 * <p>该接口的处理实现参考：</p>
 * <p><tt>@see com.intensoft.exception.msgtranslate.SimpleExceptionTranslate</tt></p>
 *
 * @author lance
 *
 */
public interface Describable {

	/**
	 * <p>获取可用于格式化异常的异常定义码</p>
	 * <ul>该方法返回的错误码<tt>code</tt>,存在以下2个意义：
	 * <li>将异常格式化成本地化的信息。</li>
	 * <li>用于定位异常类型。（不推荐）</li>
	 * </ul>
	 * @return
	 *     系统内异常描述码<tt>code</tt>
	 */
	public String getCode();

}
