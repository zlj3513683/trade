package com.posppay.newpay.common.utils;

/**
 * Created by Vinchaos api on 13-12-12.
 * 卡号处理工具类
 * 对身份证,银行卡等卡号的格式化,验证等功能
 * @author zengjw
 */
public class CardNumberUtils {
    /**
     * 16位卡号
     */
    private static int CARD_LEN_16 = 16;
    /**
     * 19位卡号
     */
    private static int CARD_LEN_19 = 19;
    private static char DOWN_GRADE = 'N';

    /**
     * checkBankCard
     * 银行卡卡号验证start
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        if (cardId == null) {
            return false;
        }

        if (cardId.length() < CARD_LEN_16 || cardId.length() > CARD_LEN_19) {
            return false;
        }

        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == DOWN_GRADE) {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * getBankCardCheckCode
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    private static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }


    /**
     * formatCardNumberWithSpace
     * 银行卡格式化
     *
     * @param cardNumber
     * @return
     */
    public static String formatCardNumberWithSpace(String cardNumber) {

        cardNumber = cardNumber.replaceAll(" ", "");
        char[] chars = cardNumber.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (i != 0 && i % 4 == 0) {
                builder.append(" ");
            }
            builder.append(chars[i]);
        }

        return builder.toString();
    }
}
