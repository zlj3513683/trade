package com.posppay.newpay.mgr.gtorg;

import com.posppay.newpay.BaseTest;
import com.posppay.newpay.modules.xposp.dao.service.TestService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class ExcelTest extends BaseTest {
    @Resource
    private TestService testService;

    @Test
    public void insertExcel(){
        com.posppay.newpay.modules.xposp.entity.Test test = new com.posppay.newpay.modules.xposp.entity.Test();
        test.setMerchantId(1234356l)
                .setMerchantName("aaa")
                .setMerchantShortName("bbb");
        com.posppay.newpay.modules.xposp.entity.Test test2 = new com.posppay.newpay.modules.xposp.entity.Test();
        test2.setMerchantId(12343567l)
                .setMerchantName("aaa")
                .setMerchantShortName("bbb");
        List<com.posppay.newpay.modules.xposp.entity.Test>tests = new ArrayList<com.posppay.newpay.modules.xposp.entity.Test>();
        tests.add(test);
        tests.add(test2);
        testService.insertAll(tests);

    }
}
