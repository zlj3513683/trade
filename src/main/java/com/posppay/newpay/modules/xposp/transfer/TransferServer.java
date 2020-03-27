package com.posppay.newpay.modules.xposp.transfer;
import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.modules.xposp.entity.Payment;
import com.posppay.newpay.modules.xposp.transfer.model.req.*;

/**
 * 功能：交易相关的服务接口
 * @author zengjw
 */
public interface TransferServer {

    default Payment consume(ConsumeRequest request)throws AppBizException {
        return null;
    };
    default Payment refund(RefundRequest request)throws AppBizException{
        return null;
    }

    /**
     * C扫B生成二维码
     * @param request
     * @return
     * @throws AppBizException
     */
    default Payment qrCode(QrcodeRequest request)throws AppBizException{
        return null;
    }

    default Payment jsapi(JsapiRequest request)throws AppBizException{
        return null;
    }

    /**
     * 扫码状态查询
     * @param request
     * @return
     * @throws AppBizException
     */
    default Payment scanQuery(QueryRequest request)throws AppBizException{
        return null;
    }
    /**
     * 扫码状态查询
     * @param request
     * @return
     * @throws AppBizException
     */
    default Payment refundQuery(QueryRequest request)throws AppBizException{
        return null;
    }


}
