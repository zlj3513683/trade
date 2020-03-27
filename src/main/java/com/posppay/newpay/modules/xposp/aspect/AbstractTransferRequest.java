package com.posppay.newpay.modules.xposp.aspect;


import com.posppay.newpay.common.utils.StringUtils;
import com.posppay.newpay.modules.sdk.pingan.model.ErrCodeType;
import com.posppay.newpay.modules.xposp.entity.UrmMinf;
import com.shouft.newpay.base.formater.Formatter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * @author zengjw
 */
@Slf4j
@Data
public class AbstractTransferRequest {
    /**
     * 设备sn
     */
    /**
     * 下游订单号(唯一标识)
     */
    private String outOrderNo;
    /**
     * 随机数不长于 32 位
     */
    private String randomData;
    /**
     * 字符集 TODO UTF-8
     */
    private String characterSet;
    /**
     * 商户编号
     */
    private String merchantId;
    /**
     * 门店号
     */
    private String stoeNo;
    /**
     * 版本号 TODO 1.0.0
     */
    private String version;
    /**
     * 操作员号
     */
    private String oprId;
    /**
     * IP地址
     */
    private String ipAddress;
    /**
     * 接口类型
     */
    private String type;
    /**
     * 机构号
     */
    private String orgNo;
    private String transationId;
    private String outTranstionId;
    private String trmlType;
    private String bankBillNo;
    private ErrCodeType errCodeType;
    /**
     //     * 商品
     //     */
//    @TransParam
    private String goodsTag;
    private String goodsDetail;
    private String attach;
    private String chnCode;
    private String bankType;

    /**
     *  银联贷记卡费率
     */
    private BigDecimal cupFeeCredict;
    /**
     *  银联借记卡费率
     */
    private BigDecimal cupFeeBank;
    /**
     *  银联贷记卡机构费率
     */
    private BigDecimal cupOrgFeeCredict;
    /**
     *  银联借记卡机构费率
     */
    private BigDecimal cupOrgFeeBank;
    /**
     *  银联封顶金额
     */
    private BigDecimal cupFeeMax;

    /**
     *  机构封顶金额
     */
    private BigDecimal orgFeeMax;
    private UrmMinf urmMinf;


    public AbstractTransferRequest() {

    }

    public AbstractTransferRequest(JSONObject json) {
        try {
            parseV1(json);
        } catch (Exception e) {
            throw new IllegalArgumentException("parse json by v1 failed!", e);
        }
    }

    protected void parseV1(JSONObject json) throws IllegalArgumentException, IllegalAccessException, InstantiationException, UnsupportedEncodingException, IOException {
        Field[] fields = getClass().getDeclaredFields();
        for (Field f : fields) {
            TransParam params = null;
            if ((params = f.getAnnotation(TransParam.class)) != null) {
                String name = f.getName();
                String value = null;
                try {
                    value = (String) json.getString(name);
                } catch (JSONException e) {
                    continue;
                }
                if (value == null) {
                    log.info("parse json:" + name + " is null!");
                    continue;
                }
                Formatter formatter = params.formatter().newInstance();
                String pattern = params.formatPattern();
                if (!StringUtils.isEmpty(pattern)) {
                    formatter.setPattern(pattern);
                }
                Object rslt = formatter.unformat(value);

                f.setAccessible(true);
                f.set(this, rslt);
            }
        }
    }
}
