package com.posppay.newpay.modules.xposp.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zengjw
 * createBy 2019-05-10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExceptionAnno {
	String declare() default "";

	String tip() default "";
}
