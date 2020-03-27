package com.posppay.newpay.common.utils;

import lombok.extern.java.Log;

/**
 * @author SuZhenhui
 */
@Log
public class ThreadUtils {
    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            log.warning(e.getMessage());
        }
    }
}
