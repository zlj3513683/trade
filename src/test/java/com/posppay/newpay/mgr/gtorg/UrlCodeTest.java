package com.posppay.newpay.mgr.gtorg;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlCodeTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode("交易成功", "UTF-8");
//        System.out.println(encode);
        String decode = URLDecoder.decode(encode, "UTF-8");// GBK解码
//        System.out.println(decode);
    }
}
