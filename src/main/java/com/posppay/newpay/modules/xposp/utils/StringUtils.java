package com.posppay.newpay.modules.xposp.utils;


import com.posppay.newpay.modules.xposp.common.Const;
import com.shouft.newpay.base.utils.DateUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理
 *
 * @author linxingh
 * create on 2018/10/18
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    private final static int UNIT_CONVERSION = 100;
    private final static int SCALE = 2;
    private final static char BCD_LEFT_PAD_CHAR = '0';
    private final static int BCD_FORMAT_LENGTH = 12;

    /**
     * 隐藏手机字符串部分字段
     *
     * @param mobileStr 待隐藏手机字符串
     * @return 已隐藏部分字段的手机字符串
     */
    public static String mobileFilter(String mobileStr) {
        return mobileStr.substring(0, 3) + "****" + mobileStr.substring(7, 11);
    }

    /**
     * 隐藏邮箱字符串部分字段
     *
     * @param mailStr 待隐藏邮箱字符串
     * @return 已隐藏部分字段的邮箱字符串
     */
    public static String mailFilter(String mailStr) {
        int needShowLength = 3;
        String[] email = mailStr.split("@");
        StringBuilder sb = new StringBuilder(email[0].substring(0, 3));
        for (int i = 0; i < email[0].length() - needShowLength; i++) {
            sb.append("*");
        }
        sb.append("@");
        sb.append(email[1]);
        return sb.toString();
    }

    /**
     * 隐藏银行卡号字符串部分字段,只显示前6后4位
     *
     * @param cardNoStr 待隐藏银行卡号字符串
     * @return 已隐藏部分字段的银行卡号字符串
     */
    public static String cardNoFilter(String cardNoStr) {
        StringBuilder sb = new StringBuilder();
        int length = cardNoStr.length();
        for (int i = 0; i < length; i++) {
            if (i < 6 || (i > length - 5)) {
                sb.append(cardNoStr.charAt(i));
            } else {
                sb.append("*");
            }
        }
        return sb.toString();

    }

    /**
     * 隐藏身份证字符串部分字段,只显示前10后4位
     *
     * @param cardNoStr 待隐藏身份证字符串
     * @return 已隐藏部分字段的身份证字符串
     */
    public static String idCardNoFilter(String cardNoStr) {
        StringBuilder sb = new StringBuilder();
        int length = cardNoStr.length();
        for (int i = 0; i < length; i++) {
            if (i < 10 || (i > length - 5)) {
                sb.append(cardNoStr.charAt(i));
            } else {
                sb.append("*");
            }
        }
        return sb.toString();
    }

    /**
     * BigDecimal 转 字符串
     *
     * @param bigDecimal BigDecimal
     * @return 字符串
     */
    public static String decimal2string(BigDecimal bigDecimal) {
        if (bigDecimal != null) {
            return bigDecimal.toPlainString();
        }
        return "";
    }

    /**
     * 字符串 转 BigDecimal
     *
     * @param string 字符串
     * @return BigDecimal
     */
    public static BigDecimal string2decimal(String string) {
        return new BigDecimal(string).setScale(SCALE, RoundingMode.HALF_UP);
    }

    /**
     * BigDecimal格式以元为单位的金额 转 BCD格式的金额  示例：1.11 转 000000000111
     *
     * @param bigDecimalAmt BigDecimal格式以元为单位的金额
     * @return BCD格式的金额
     */
    public static String decimal2bcd4yuan(BigDecimal bigDecimalAmt) {
        bigDecimalAmt = bigDecimalAmt.abs().multiply(new BigDecimal(UNIT_CONVERSION));
        return decimal2bcd4cent(bigDecimalAmt);
    }

    /**
     * BigDecimal格式以分为单位的金额 转 BCD格式的金额  示例：111 转 000000000111
     *
     * @param bigDecimalAmt BigDecimal格式以分为单位的金额
     * @return BCD格式的金额
     */
    public static String decimal2bcd4cent(BigDecimal bigDecimalAmt) {
        return StringUtils.leftPad(String.valueOf(bigDecimalAmt.abs().longValue()), BCD_FORMAT_LENGTH, BCD_LEFT_PAD_CHAR);
    }

    /**
     * BigDecimal格式以元为单位的金额 转 BCD简单格式的金额  示例：1.11 转 111
     *
     * @param bigDecimalAmt BigDecimal格式以元为单位的金额
     * @return BCD简单格式的金额
     */
    public static String decimal2bcdSimp4yuan(BigDecimal bigDecimalAmt) {
        return String.valueOf(bigDecimalAmt.multiply(new BigDecimal(UNIT_CONVERSION)).longValue());
    }

    /**
     * BigDecimal格式以分为单位的金额 转 BCD简单格式的金额  示例：111 转 111
     *
     * @param bigDecimalAmt BigDecimal格式以分为单位的金额
     * @return BCD简单格式的金额
     */
    public static String decimal2bcdSimp4cent(BigDecimal bigDecimalAmt) {
        return decimal2string(bigDecimalAmt);
    }

    /**
     * BCD简单格式的金额 转 BigDecimal格式以元为单位的金额 示例：111 转 1.11
     *
     * @param bcdSimpAmt BCD简单格式的金额
     * @return BigDecimal格式以元为单位的金额
     */
    public static BigDecimal bcdSimp2decimal4yuan(String bcdSimpAmt) {
        BigDecimal bcdSimpDecimal = string2decimal(bcdSimpAmt);
        return bcdSimpDecimal.divide(new BigDecimal(UNIT_CONVERSION), SCALE, RoundingMode.HALF_UP);
    }

    /**
     * BCD简单格式的金额 转 BigDecimal格式以分为单位的金额 示例：111 转 111
     *
     * @param bcdSimpAmt BCD简单格式的金额
     * @return BigDecimal格式以分为单位的金额
     */
    public static BigDecimal bcdSimp2decimal4cent(String bcdSimpAmt) {
        return string2decimal(bcdSimpAmt);
    }

    /**
     * BCD格式的金额 转 BigDecimal格式以元为单位的金额  示例：000000000111 转 1.11
     *
     * @param bcdAmt BCD格式的金额
     * @return BigDecimal格式以元为单位的金额
     */
    public static BigDecimal bcd2decimal4yuan(String bcdAmt) {
        return bcdSimp2decimal4yuan(bcdAmt);
    }

    /**
     * BCD格式的金额 转 BigDecimal格式以分为单位的金额  示例：000000000111 转 111
     *
     * @param bcdAmt BCD格式的金额
     * @return BigDecimal格式以分为单位的金额
     */
    public static BigDecimal bcd2decimal4cent(String bcdAmt) {
        return string2decimal(bcdAmt);
    }

    /**
     * 元转换格式为分，例如：1.11 转 111
     *
     * @param yuan 元格式金额
     * @return 分格式金额
     */
    public static String yuanToCent(String yuan) {
        if (isEmpty(yuan)) {
            return "";
        }
        int pointIdx = yuan.indexOf('.');
        if (pointIdx == -1) {
            return yuan + "00";
        } else {
            int centMaxLength = 2;
            String beforeStr = yuan.substring(0, pointIdx);
            String afterStr = yuan.substring(pointIdx);
            if (afterStr.length() <= centMaxLength) {
                return rightPad(beforeStr, beforeStr.length() + 2, '0');
            } else {
                return beforeStr + afterStr.substring(0, 2);
            }
        }
    }

    /**
     * 分转换格式为元，例如：111 转 1.11
     *
     * @param cent 分格式金额
     * @return 元格式金额
     */
    public static String centToYuan(String cent) {
        int centMaxLength = 2;
        if (isNotEmpty(cent)) {
            if (cent.length() == 1) {
                return "0.0" + cent;
            } else if (cent.length() == centMaxLength) {
                return "0." + cent;
            } else {
                return cent.substring(0, cent.length() - 3) + '.' + cent.substring(cent.length() - 3);
            }
        }
        return "";
    }

    /**
     * 金额是否符合 *.* 的格式
     *
     * @param amount 金额字符串
     * @return true-符合，false-不符合
     */
    public static boolean isAmountFormat(String amount) {
        int maxLength = 10;
        String point = ".";
        boolean isAmountFormat = false;
        if (amount.contains(point)) {
            String[] amounts = amount.split("\\.");
            if (amounts[0].length() <= maxLength && amounts[0].length() > 0) {
                isAmountFormat = true;
            }
        }
        return isAmountFormat;
    }

    /**
     * 字节级切割,确保切割出的字符对应的字节数小等于传入参数。<p>
     * 且字符完整
     *
     * @param input   输入字符串
     * @param charset 字符集
     * @param max     最大字节数
     * @return 按最大字节数切割后的字符串
     * @throws UnsupportedEncodingException 编码失败时抛出异常
     */
    public static String spiltByByteMaxLength(String input, String charset, int max) throws UnsupportedEncodingException {
        if (input == null) {
            return null;
        }
        int offset = 0;
        StringBuilder sb = new StringBuilder(max);
        for (int i = 0; i < input.length(); i++) {
            offset += input.substring(i, i + 1).getBytes(charset).length;
            if (offset > max) {
                break;
            }
            sb.append(input.charAt(i));
        }
        return sb.toString();
    }

    public static String toRemark(String input) {
        try {
            return spiltByByteMaxLength(input, "utf-8", 210);
        } catch (Exception e) {
            return input.substring(0, 210 / 3);
        }
    }

    /**
     * 验证邮箱
     *
     * @param email 邮箱字符串
     * @return true-验证成功，false-验证失败
     */
    public static boolean checkEmail(String email) {
        return checkRegex(email, "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }

    /**
     * 验证手机号码
     *
     * @param mobiles 手机号字符串
     * @return true-验证成功，false-验证失败
     */
    public static boolean checkMobile(String mobiles) {
        return checkRegex(mobiles, "^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]|(17[0-9])))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
    }

    /**
     * 验证字符串是否符合正则表达式
     *
     * @param str          待验证字符串
     * @param regexPattern 正则表达式
     * @return true-符合，false-不符合
     */
    private static boolean checkRegex(String str, String regexPattern) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile(regexPattern);
            Matcher matcher = regex.matcher(str);
            flag = matcher.matches();
        } catch (Exception ignored) {
        }
        return flag;
    }

    /**
     * 生成一个UUID
     *
     * @return
     */
    public static String generateUUID() {
        String randomNo = UUID.randomUUID().toString().replaceAll(Const.StarPos.MINUS, Const.StarPos.NON).substring(Const.StarPos.ZERO_NUM, Const.StarPos.SECOND);
        return randomNo;
    }

    /**
     * 根据logNo的前八位来获取日期
     * @param logNo
     * @return
     */
    public static Date getTransDateByLOgNo(String logNo) {
        Date date = DateUtils.getStartDate(new Date());
        if (StringUtils.isNotBlank(logNo) && logNo.length() > 8) {
            String dateStr = logNo.substring(0, 8);
            date = DateUtils.converToDateTime(dateStr, "yyyyMMdd");
        }
        return date;
    }


    public static void main(String[] args) {
        BigDecimal a = StringUtils.bcdSimp2decimal4yuan("0.00");
//        System.out.println(a);
    }
}

