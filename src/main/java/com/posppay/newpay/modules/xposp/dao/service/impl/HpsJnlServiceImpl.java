package com.posppay.newpay.modules.xposp.dao.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.service.HpsJnlService;
import com.posppay.newpay.modules.xposp.entity.HpsJnl;
import com.posppay.newpay.modules.xposp.dao.mapper.HpsJnlDao;
import com.posppay.newpay.modules.xposp.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-16
 */
@Service
public class HpsJnlServiceImpl extends ServiceImpl<HpsJnlDao, HpsJnl> implements HpsJnlService {

    /**
     * 新增交易流水
     *
     * @param hpsJnl
     * @return
     */
    @Override
    public void addFlow(HpsJnl hpsJnl) {
        baseMapper.insert(hpsJnl);
    }

    /**
     * 根据logNo来更新流水
     *
     * @param hpsJnl
     */
    @Override
    public void updateFlow(HpsJnl hpsJnl) {
        EntityWrapper<HpsJnl> entityWrapper = new EntityWrapper<HpsJnl>();
        entityWrapper.eq("LOG_NO", hpsJnl.getLogNo());
        baseMapper.update(hpsJnl, entityWrapper);
    }

    /**
     * 功能： 根据logNo查找流水
     *
     * @param logNo
     * @return
     */
    @Override
    public HpsJnl findBylogNO(String logNo) {
        Date transDate = StringUtils.getTransDateByLOgNo(logNo);
        Date nextDate = DateUtil.offset(transDate, DateField.DAY_OF_MONTH, 1);
        EntityWrapper<HpsJnl> entityWrapper = new EntityWrapper<HpsJnl>();
        entityWrapper.ge("AC_DT", transDate)
                .eq("LOG_NO", logNo)
                .lt("AC_DT", DateUtil.format(nextDate, "yyyyMMdd"));
        List<HpsJnl> hpsJnls = baseMapper.selectList(entityWrapper);
        HpsJnl hpsJnl = null;
        if (null != hpsJnls && 0 != hpsJnls.size()) {
            hpsJnl = hpsJnls.get(0);
        }
        return hpsJnl;


    }

    /**
     * 根据C端流水号来查找流水信息
     *
     * @param cseqNo
     * @return
     */
    @Override
    public HpsJnl findByCseqNo(String cseqNo) {

        EntityWrapper<HpsJnl> entityWrapper = new EntityWrapper<HpsJnl>();
        entityWrapper.eq("CSEQ_NO", cseqNo);
        List<HpsJnl> hpsJnls = baseMapper.selectList(entityWrapper);
        HpsJnl hpsJnl = null;
        if (null != hpsJnls && 0 != hpsJnls.size()) {
            hpsJnl = hpsJnls.get(0);
        }
        return hpsJnl;
    }
}
