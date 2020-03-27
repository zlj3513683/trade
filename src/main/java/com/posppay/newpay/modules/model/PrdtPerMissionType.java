package com.posppay.newpay.modules.model;


import com.posppay.newpay.modules.xposp.transfer.model.req.*;

/**
 * 功能： 权限枚举
 *
 * @author zengjw
 */

public enum PrdtPerMissionType {

    /**
     * 权限枚举
     */
    POS_CONSUME("2010020", "pos消费",6,"1"),
    POS_CANCEL("2010030","pos撤销",7,"1"),
    POS_AUTH("2010040","pos预授权",2,"1"),
    POS_AUTHCANCEL("2010050","pos预授权撤销",3,"1"),
    POS_AUTHCOMPLETE("2010060","pos预授权完成",4,"1"),
    POS_AUTHCOMPLETE_CANCEL("2010070","pos预授权完成撤销",5,"1"),
    POS_REFUND("2010080","pos退货",8,"1"),
    POS_BALANCE("2010090","pos余额查询",1,"1"),
    POS_SCAN("4433001","pos扫码(扫手机)",9,"11"),
    POS_QRCODE("4433002","pos扫码(扫pos)",11,"11"),
    POS_SCAN_REFUND("4433005","pos扫码退款",13,"1"),
    ULINK_FLOW_PAY("4433011","增值流水购买",14,"1");



    private String permission;
    private String chnName;
    private int index;
    private String value;

    PrdtPerMissionType(String permission, String chnName,int index,String value) {
        this.permission = permission;
        this.chnName = chnName;
        this.index=index;
        this.value=value;
    }

    /**
     * 根据编号获取具体的结果
     * @param permission
     * @return
     */
    public static PrdtPerMissionType getValue(String permission) {
        for (PrdtPerMissionType prdtPerMissionType : PrdtPerMissionType.values()) {
            if (prdtPerMissionType.getPermission().equals(permission)) {
                return prdtPerMissionType;
            }
        }
        return null;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getChnName() {
        return chnName;
    }

    public void setChnName(String chnName) {
        this.chnName = chnName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
