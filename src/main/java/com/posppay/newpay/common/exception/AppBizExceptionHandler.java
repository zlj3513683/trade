//package com.posppay.newpay.common.exception;
//
//
//import com.posppay.newpay.common.utils.Resp;
//import org.apache.shiro.authz.AuthorizationException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
///**
// * 异常处理器
// *
// * @author wwa
// */
//@RestControllerAdvice
//public class AppBizExceptionHandler {
//	private Logger logger = LoggerFactory.getLogger(getClass());
//
//	/**
//	 * 处理自定义异常
//	 */
//	@ExceptionHandler(AppBizException.class)
//	public Resp handleAppBizException(AppBizException e){
//		Resp r = new Resp();
//		r.put("code", e.getCode());
//		r.put("msg", e.getMessage());
//
//		return r;
//	}
//
//	@ExceptionHandler(NoHandlerFoundException.class)
//	public Resp handlerNoFoundException(Exception e) {
//		logger.error(e.getMessage(), e);
//		return Resp.error(404, "路径不存在，请检查路径是否正确");
//	}
//
//	@ExceptionHandler(DuplicateKeyException.class)
//	public Resp handleDuplicateKeyException(DuplicateKeyException e){
//		logger.error(e.getMessage(), e);
//		return Resp.error("数据库中已存在该记录");
//	}
//
//	@ExceptionHandler(AuthorizationException.class)
//	public Resp handleAuthorizationException(AuthorizationException e){
//		logger.error(e.getMessage(), e);
//		return Resp.error("没有权限，请联系管理员授权");
//	}
//
//	@ExceptionHandler(Exception.class)
//	public Resp handleException(Exception e){
//		logger.error(e.getMessage(), e);
//		return Resp.error();
//	}
//}
