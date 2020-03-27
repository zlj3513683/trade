package com.newland.model.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * posp——迷联交易
 * </p>
 *
 * @author zoulinjun
 * @since 2020-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("POSP_MILIAN_TRANS")
public class PospMilianTrans extends Model<PospMilianTrans> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @TableId("ORDER_ID")
    private String orderId;
    /**
     * 请求uri
     */
    @TableField("REQ_URI")
    private String reqUri;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;
    /**
     * posp请求xml
     */
    @TableField("REQ_XML")
    private String reqXml;
    /**
     * 迷联通知时间
     */
    @TableField("NOTIFY_XML")
    private String notifyXml;
    @TableField("NOTIFY_TIME")
    private Date notifyTime;


    @Override
    protected Serializable pkVal() {
        return this.orderId;
    }

}
