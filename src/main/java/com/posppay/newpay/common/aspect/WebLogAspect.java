package com.posppay.newpay.common.aspect;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author zengjw
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

	/**
	 * /两个..代表所有子目录，最后括号里的两个..代表所有参数
	 */
	@Pointcut("execution(* com.posppay.newpay.modules.xposp.controller.XpospController.*(..))")
	public void logPointCut() {
	}

	@Before("logPointCut()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// 记录下请求内容
		log.debug("请求地址 : " + request.getRequestURL().toString());
		log.debug("HTTP METHOD : " + request.getMethod());
		log.debug("IP : " + request.getRemoteAddr());
		log.debug("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
			+ joinPoint.getSignature().getName());
		log.debug("参数 : " + Arrays.toString(joinPoint.getArgs()));

	}

	/**
	 * returning的值和doAfterReturning的参数名一致
	 * @param ret
	 * @throws Throwable
	 */
	@AfterReturning(returning = "ret", pointcut = "logPointCut()")
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		log.debug("返回值 : " + JSONObject.fromObject(ret));
	}

	@Around("logPointCut()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
		// ob 为方法的返回值
		Object ob = pjp.proceed();
		log.debug("耗时 : " + (System.currentTimeMillis() - startTime));
		return ob;
	}

}
