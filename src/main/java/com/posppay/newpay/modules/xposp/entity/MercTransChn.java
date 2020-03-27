package com.posppay.newpay.modules.xposp.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.ConstructorArgs;

import java.io.Serializable;

/**
 * <p>
 * 商户指定渠道表
 * </p>
 *
 * @author zengjw
 * @since 2019-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_MERC_TRANS_CHN")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MercTransChn extends Model<MercTransChn> {

    private static final long serialVersionUID = 1L;
    /**
     * 商户号
     */
    @TableField("MERC_ID")
    private String mercId;
    /**
     * 扫码交易渠道
     * 允许为空
     */
    @TableField(value = "SCAN_CHN",strategy = FieldStrategy.IGNORED)
    private String scanChn;
    /**
     * 银行卡交易
     * 允许为空
     */
    @TableField(value = "POS_CHN",strategy = FieldStrategy.IGNORED)
    private String posChn;
    /**
     * 时间戳
     */
    @TableField("TM_SMP")
    private String tmSmp;
    /**
     * 更新时间
     */
    @TableField("UPD_TM")
    private String updTm;


    /**
     * 主键
     * @return
     */
    @Override
    protected Serializable pkVal() {
        return mercId;
    }

}
