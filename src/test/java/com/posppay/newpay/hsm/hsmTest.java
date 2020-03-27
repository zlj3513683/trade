//package com.posppay.newpay.hsm;
//
//import com.newland.hsm.HsmUtil;
//import com.newland.hsm.SHJ0902.model.CMDA0A1Out;
//import com.newland.hsm.exception.HSMException;
//import com.posppay.newpay.BaseTest;
//import com.posppay.newpay.config.HsmProperties;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class hsmTest extends BaseTest {
//    @Autowired
//    private HsmUtil hsmUtil;
//    @Autowired
//    private HsmProperties hsmProperties;
//
//    //生成主秘钥
//    @Test
//    public void generateDeviceMasterKey() throws HSMException {
//        CMDA0A1Out masterOut = hsmUtil.generateDeviceMasterKey(hsmProperties.getZmk());
//        System.out.println("LMK加密的主秘钥是："+masterOut.getKeyEncryptByLmk());
//        System.out.println("zmk加密的主秘钥是："+masterOut.getKeyEncryptByZmk());
//        System.out.println("checkvalue是："+masterOut.getKeyCheckValue());
//
//    }
//}
