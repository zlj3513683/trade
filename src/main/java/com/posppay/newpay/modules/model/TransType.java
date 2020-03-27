package com.posppay.newpay.modules.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 交易类型
 * @author VJ
 * Created by yl on 2018/11/7.
 */
public enum TransType {
    //========================================================//
    CONSUME("2101", "消费", "consume"),
    REFUND("2103", "退货", "refund"),
    QUERY("2105", "查询", "query");

    /**
     * 交易类型值
     **/
    private String ordinal;
    /**
     * 中文名称
     **/
    private String chineseName;
    /**
     * 英文名称
     **/
    private String englishName;

    private TransType(String idx, String chineseName, String englishName) {
        this.ordinal = idx;
        this.chineseName = chineseName;
        this.englishName = englishName;
    }

    public String getOrdinal() {
        return ordinal;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public String getChineseName() {
        return chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    private static final Map<String, TransType> TRANS_TYPE_MAPPING = new HashMap<String, TransType>();
    private static final Map<String, TransType> TRANS_TYPE_NAME_MAPPING = new HashMap<String, TransType>();

    static {
        for (TransType it : TransType.values()) {
            TRANS_TYPE_MAPPING.put(it.ordinal, it);
        }
        for (TransType type : TransType.values()) {
            TRANS_TYPE_NAME_MAPPING.put(type.englishName, type);
        }
    }

    public static final TransType fromTransTypeName(String name) {
        return TRANS_TYPE_NAME_MAPPING.get(name);
    }

    public static final TransType fromTransTypeOrdinal(String ordinal) {
        return TRANS_TYPE_MAPPING.get(ordinal);
    }
}
