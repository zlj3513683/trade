package com.posppay.newpay.modules.xposp.router.channel.cup.model;


/**
 * 功能： 路由枚举
 *
 * @author zengjw
 */

public enum PayChannelPath {

    /**
     * 路由枚举
     */
    ScanPay("/cup/payment/passive/add", "B扫C消费路由地址"),
    QRCODE("/cup/payment/native/add", "C扫B生成二维码地址"),
    REFUND("/cup/payment/refund", "扫码退货地址"),
    STATUS_QUERY("/cup/payment/result/query", "扫码状态查询");


    private String path;
    private String chnName;

    /**
     * 功能： 根据code来获取具体的枚举
     *
     * @param path
     * @return
     */
    public static PayChannelPath fromCode(String path) {
        for (PayChannelPath payChannelType : PayChannelPath.values()) {
            if (payChannelType.getPath().equals(path)) {
                return payChannelType;
            }
        }
        return null;
    }

    PayChannelPath(String path, String chnName) {
        this.path = path;
        this.chnName = chnName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getChnName() {
        return chnName;
    }

    public void setChnName(String chnName) {
        this.chnName = chnName;
    }
}
