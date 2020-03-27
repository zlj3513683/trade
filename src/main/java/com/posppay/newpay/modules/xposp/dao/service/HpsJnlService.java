package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.HpsJnl;

public interface HpsJnlService extends IService<HpsJnl> {

    /**
     * 新增流水
     *
     * @param hpsJnl
     */
    void addFlow(HpsJnl hpsJnl);

    /**
     * 更新流水
     *
     * @param hpsJnl
     */
    void updateFlow(HpsJnl hpsJnl);

    /**
     * 根据logNo来查找当前流水
     * @param logNo
     * @return
     */
    HpsJnl findBylogNO(String logNo);
    /**
     * 根据C端流水号来查找流水信息
     * @param cseqNo
     * @return
     */
    HpsJnl findByCseqNo(String cseqNo);


}
