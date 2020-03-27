package com.posppay.newpay.common.exception;

import org.springframework.util.Assert;

/**
 * com.shouft.newpay.common.exception
 *
 * @author linqf
 * @date 2018/9/11
 * @Description 业务逻辑错误校验工具
 */
public class AppBizExceptionCheckUtils {

    public static <T> void checkParamsNotNull(T params, String paramsName) {
        try {
            Assert.notNull(params, paramsName + "不可为空");
        } catch (IllegalArgumentException ex) {
            throw new AppBizException(ex.getMessage());
        }
    }
}
