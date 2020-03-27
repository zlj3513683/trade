package com.posppay.newpay.modules.xposp.router;

import com.shouft.newpay.base.exception.AppRTException;

/**
 * @author zengjianwen
 */
public class TransferFailedException extends AppRTException {

	private static final long serialVersionUID = 411968644561385851L;

	public TransferFailedException(String code, String msg) {
		super(code, msg);
	}
	public TransferFailedException(String code, String msg, Throwable e) {
		super(code, msg,e);
	}
}
