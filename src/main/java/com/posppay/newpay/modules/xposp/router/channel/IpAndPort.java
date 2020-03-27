package com.posppay.newpay.modules.xposp.router.channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * 构建IpAndPort对象以List来存储各个渠道的IP和端口
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IpAndPort implements Serializable {


    private String ip;
    private Integer port;

}
