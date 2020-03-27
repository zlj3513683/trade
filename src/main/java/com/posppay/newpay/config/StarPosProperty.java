//
//package com.posppay.newpay.config;
//
//import com.posppay.newpay.modules.xposp.router.channel.IpAndPort;
//import com.posppay.newpay.modules.xposp.router.channel.MrchAndTrmnl;
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//
///**
// * @author zengjw
// * 国通相关配置
// */
//@Component
//@ConfigurationProperties(prefix = "gt")
//@Data
//public class StarPosProperty {
//
//    public static List<IpAndPort> addresss;
//    public static MrchAndTrmnl mrchAndTrmnl;
//
//
//    public Map<String, String> starpos = new HashMap<>();
//    public Map<String, String> mrchtrmnl = new HashMap<>();
//
//    @PostConstruct
//    public void init() {
//        addresss= new ArrayList<IpAndPort>(16);
//        starpos.forEach((key,address)->{
//            String[] ips = address.split(":");
//            IpAndPort ipAndPort = IpAndPort
//                    .builder()
//                    .ip(ips[0])
//                    .port(Integer.parseInt(ips[1]))
//                    .build();
//            addresss.add(ipAndPort);
//        });
//        mrchAndTrmnl=MrchAndTrmnl
//                .builder()
//                .corgNo(mrchtrmnl.get("corgNo"))
//                .corgMercId(mrchtrmnl.get("corgMercId"))
//                .corgTrmNo(mrchtrmnl.get("corgTrmNo"))
//                .zmk(mrchtrmnl.get("zmk"))
//                .trmnlNo(mrchtrmnl.get("trmnlNo"))
//                .mrchNo(mrchtrmnl.get("mrchNo"))
//                .build();
//    }
//}
