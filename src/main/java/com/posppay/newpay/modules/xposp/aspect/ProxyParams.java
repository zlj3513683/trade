package com.posppay.newpay.modules.xposp.aspect;

import com.shouft.newpay.base.formater.Formatter;
import com.shouft.newpay.base.formater.StringFormatter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value= ElementType.FIELD)
public @interface ProxyParams {

	public String formatPattern() default "";

	public Class<? extends Formatter> formatter() default StringFormatter.class;

	public boolean joinMac() default true;
}
