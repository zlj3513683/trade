package com.posppay.newpay.modules.xposp.channel.aspect;

import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.modules.model.ProxyResponse;
import com.posppay.newpay.modules.xposp.common.AppExCode;
import com.posppay.newpay.modules.xposp.entity.UrmComi;
import com.posppay.newpay.modules.xposp.innertrans.InnerTransService;
import com.shouft.newpay.base.exception.base.CommonException;
import com.shouft.newpay.base.exception.base.CommonRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author zengjianwen
 */
@Aspect
@Component
@Slf4j
public class XpospProxyController {

	@Pointcut("target(com.posppay.newpay.modules.xposp.controller.XpospController)")
	public void xpospServicePointCut(){}

	/**
	 * 交易处理
	 * @param jp
	 * @return
	 * @throws Throwable
	 */
	@Around("xpospServicePointCut()")
	public Object processXpospService(ProceedingJoinPoint jp) throws Throwable {
		try{
			Object obj = jp.proceed(jp.getArgs());
			return obj;
		}catch (AppBizException e){
			log.error("xposp service failed!",e);
			return responseBySystemErr(e.getCode()+"",e.getMessage());
		}catch (CommonRuntimeException e) {
			log.error("xposp service failed!",e);
			return responseBySystemErr(e.getCode(),e.getMessage());
		}catch(CommonException e){
			log.error("xposp service failed!",e);
			return responseBySystemErr(e.getCode(),e.getMessage());
		}catch (Exception e) {
			log.error("xposp service failed!",e);
			return responseBySystemErr(AppExCode.UNKNOWN,"未知异常");
		}
	}

	private ProxyResponse responseBySystemErr(String retCode, String retMsg) throws IOException {
		ProxyResponse response = new ProxyResponse();
//		response.setReturnMsg(ExceptionCodeUtils.getInstance().getDeclare(retCode));
		response.setReturnCode(retCode);
		response.setReturnMsg(retMsg);
		response.setSignType("MD5");
		response.setVersion("1.0.0");

		return response;
	}
}
