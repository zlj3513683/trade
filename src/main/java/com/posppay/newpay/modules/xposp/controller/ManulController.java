package com.posppay.newpay.modules.xposp.controller;

import com.posppay.newpay.modules.xposp.innertrans.InnerTransService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 功能：手工操作控制层
 * @author zengjw
 */
@RestController
@Slf4j
public class ManulController {
    @Resource(name = "innerTransService")
    private InnerTransService innerTransService;
    @RequestMapping("/")
    String orgSignUp(){
        log.info("进来了");
        boolean result = innerTransService.orgSignUp();

        return "result="+result;
    }
}
