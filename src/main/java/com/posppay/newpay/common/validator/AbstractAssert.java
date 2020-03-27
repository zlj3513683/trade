package com.posppay.newpay.common.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * 数据校验
 *
 * @author wwa
 */
public abstract class AbstractAssert extends Assert {
    public static void notBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new IllegalArgumentException(message);
        }
    }
}
