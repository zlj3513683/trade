package com.posppay.newpay.modules.sdk.pingan.service.impl;

import com.posppay.newpay.modules.sdk.pingan.constant.PinganConstant;
import com.posppay.newpay.modules.sdk.pingan.constant.ResponseCodeConstant;
import com.posppay.newpay.modules.sdk.pingan.model.resp.NativeTradeResultNotice;
import com.posppay.newpay.modules.sdk.pingan.model.resp.ServiceMessage;
import com.posppay.newpay.modules.sdk.pingan.service.NoticeService;
import com.posppay.newpay.modules.sdk.pingan.util.SecurityUtil;
import com.posppay.newpay.modules.sdk.pingan.util.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 功能： C扫B回调
 * @author zengjw
 */
@Slf4j
@Service
public class NoticeServiceImpl implements NoticeService {
    /**
     * C扫B平安通知处理
     *
     * @param data
     * @return
     */
    @Override
    public ServiceMessage scanBusNotice(String data) {
        log.info("开始处理银联请求");
        NativeTradeResultNotice response = new NativeTradeResultNotice();
        try {
            response = XmlUtils.parseXmlToBean(NativeTradeResultNotice.class,data);
            if (!PinganConstant.COMMUNICATE_SUCCESS.equals(response.getStatus())){
                return new ServiceMessage(ResponseCodeConstant.DEFAULT_ERROR_CODE,response.getMessage());
            }
//            String secretKey = merchantService.findSecretKey(response.getMerchantId());
//            if (StringUtils.isBlank(secretKey)){
//                return new ServiceMessage(ResponseCodeConstant.DEFAULT_ERROR_CODE,ResponseCodeConstant.SECRET_KEY_NULL);
//            }
//            Map<String,String> param = XmlUtils.toMap(data.getBytes(), CupConstant.DEFAULT_CHARSET);
//            if (!SecurityUtil.sign(param,secretKey, CupConstant.DEFAULT_CHARSET)){
//                return new ServiceMessage(ResponseCodeConstant.DEFAULT_ERROR_CODE,ResponseCodeConstant.RESPONSE_SIGN_MESSAGE);
//            }
//            if (!CupConstant.STRING_ZERO.equals(response.getResultCode())){
//                return new ServiceMessage(ResponseCodeConstant.DEFAULT_ERROR_CODE,response.getErrorMessage());
//            }
//            TransactionFlow flow = transactionFlowService.selectById(response.getOutTradeNo());
//            if (flow == null){
//                return new ServiceMessage(ResponseCodeConstant.DEFAULT_ERROR_CODE,ResponseCodeConstant.INVALID_ORDER);
//            }
//            Integer localFee = new BigDecimal(flow.getTotalFee()).multiply(new BigDecimal(100)).intValue();
//            log.info("本地金额：{},银联金额：{}",localFee,response.getTotalFee());
//            if (!localFee.equals(response.getTotalFee())){
//                return new ServiceMessage(ResponseCodeConstant.DEFAULT_ERROR_CODE,ResponseCodeConstant.INVALID_ORDER);
//            }
        }catch (Exception e){
            log.info(e.getLocalizedMessage());
            return new ServiceMessage(ResponseCodeConstant.SUCCESS_CODE,ResponseCodeConstant.REQUEST_ERROR_MSG);
        }
        return new ServiceMessage(ResponseCodeConstant.SUCCESS_CODE,ResponseCodeConstant.SUCCESS_MSG);
    }
}
