package com.posppay.newpay.modules.xposp.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.posppay.newpay.modules.xposp.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统订单表 Mapper 接口
 * </p>
 *
 * @author zengjw
 * @since 2019-06-13
 */
@Mapper
public interface OrderDao extends BaseMapper<Order> {

}
