package com.posppay.newpay.modules.xposp.utils;

import cn.hutool.core.date.DateUtil;
import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static DateUtils dateUtils;

    public static DateUtils getInstall() {
        if (dateUtils == null) {
            synchronized (DateUtils.class) {
                dateUtils = new DateUtils();
            }
        }
        return dateUtils;
    }

    /**
     * 获取当天0秒
     * @param now
     * @return
     */
    public Date nowDate(Date now){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        Date endDate = calendar.getTime();
        return  endDate;
    }

    /**
     * 获取第二天0秒
     * @param now
     * @return
     */
    public Date nextDate(Date now){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        Date endDate = calendar.getTime();
        return  endDate;
    }
    public static String dateTimeToString(Date date) {
        if (date != null) {
            DateTime var1 = new DateTime(date);
            return var1.toString("yyyy-MM-dd HH:mm:ss");
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        Date endDate = calendar.getTime();
        System.out.println(DateUtil.format(endDate,"yyyyMMddHHmmss"));

    }

}
