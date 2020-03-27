package com.posppay.newpay.modules.xposp.aspect;

import com.shouft.newpay.base.formater.Formatter;
import com.shouft.newpay.base.formater.StringFormatter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value= ElementType.FIELD)
public @interface TransParam {

	public boolean joinMac() default true;

	public int fieldNum() default 0;

	public String formatPattern() default "";

	public Class<? extends Formatter> formatter() default StringFormatter.class;
}
