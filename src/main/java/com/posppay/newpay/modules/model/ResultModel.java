package com.posppay.newpay.modules.model;

import lombok.Data;

@Data
public class ResultModel {
    private String status;
    private String errcode;
    private String errMsg;
    private String transctionId;
    private String bankBillNo;
}
