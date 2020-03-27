package com.posppay.newpay.modules.xposp.transfer.model.req;

import com.posppay.newpay.modules.model.ProxyRequest;
import lombok.*;

/**
 * 功能： 参数同步/下载请求参数
 * @author zengjw
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParamLoadRequest extends ProxyRequest {

    /**
     * 商户号
     */
    private String merchantId;
    /**
     * 终端号
     */
    private String terminalNo;


}
