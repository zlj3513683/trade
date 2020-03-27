package com.posppay.newpay.common.exception;

/**
 * 自定义异常
 *
 * @author wwa
 */
public class AppBizException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private String code;

    public AppBizException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public AppBizException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public AppBizException(String code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public AppBizException(String msg, String code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
