package com.posppay.newpay.modules.xposp.router.channel.starpos.handle;

import cn.hutool.core.date.DateUtil;
import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.common.utils.StringUtils;
import com.posppay.newpay.modules.sdk.cup.config.CupConfig;
import com.posppay.newpay.modules.sdk.starpos.constant.StarPosConstant;
import com.posppay.newpay.modules.sdk.starpos.model.req.TradeScanQueryReq;
import com.posppay.newpay.modules.sdk.starpos.model.resp.TradeScanQueryResp;
import com.posppay.newpay.modules.sdk.starpos.service.ScanPayService;
import com.posppay.newpay.modules.xposp.common.AppExCode;
import com.posppay.newpay.modules.xposp.entity.*;
import com.posppay.newpay.modules.xposp.innertrans.InnerRouteService;
import com.posppay.newpay.modules.xposp.router.TransferFailedException;
import com.posppay.newpay.modules.xposp.router.channel.ChannelRequestInfo;
import com.posppay.newpay.modules.xposp.router.channel.ChannelResponseInfo;
import com.posppay.newpay.modules.xposp.router.channel.starpos.model.PayResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 功能：星pos扫码查询与第三方交互实现
 *
 * @author zengjianwens
 */
@Service("starPosQueryProcess")
@Slf4j
public class ScanQueryProcess {
    @Autowired
    private CupConfig cupConfig;
    @Resource
    private ScanPayService scanPayService;
    @Resource
    private InnerRouteService innerRouteService;


    /**
     * 星pos扫码查询与第三方交互实现
     *
     * @param request
     * @param originChnFlow
     * @return
     * @throws TransferFailedException
     * @throws AppBizException
     */
    public ChannelResponseInfo process(UrmMmap urmMmap, ChannelRequestInfo request, QrChnFlowGt originChnFlow)
            throws TransferFailedException, AppBizException {
        log.info("星pos扫码消费查询与sdk交互开始.................");
        request.setSignInstNo(cupConfig.getOrgNo());
        TradeScanQueryReq entity = packUp(request, originChnFlow);
        TradeScanQueryResp response = null;
        response = scanPayService.scanQuery(entity, urmMmap.getStarKey());
        if (null == response) {
            //TODO  流程待定
            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "网络读写异常请重试");
        }
//        //业务异常
//        if (!StarPosConstant.BUINESS_SUCESS.equals(response.getReturnCode())) {
//            originChnFlow.setRespCode(response.getReturnCode())
//                    .setRespMsg(response.getMessage());
//            innerRouteService.updateChnFlow(originChnFlow);
//            throw new TransferFailedException(response.getReturnCode(), response.getMessage());
//        }
        //更新渠道流水
        updateChnFlow(originChnFlow, response);
        if (StringUtils.isNotBlank(response.getResult())) {
            request.setResult(response.getResult());
            PayResult payResult = PayResult.fromCode(response.getResult());
            switch (payResult) {
                case SUCCESS:
                    originChnFlow = updateChnFlow(originChnFlow, response);
                    break;
                case FAIL:
                    originChnFlow = updateChnFlow(originChnFlow, response);
                    throw new TransferFailedException(response.getReturnCode(), response.getMessage());
                case UNKNOW:
                    originChnFlow = updateChnFlow(originChnFlow, response);
                    throw new TransferFailedException(response.getReturnCode(), response.getMessage());
                case WAITAUTH:
                    originChnFlow = updateChnFlow(originChnFlow, response);
                    throw new TransferFailedException(response.getReturnCode(), response.getMessage());
                default:
                    break;
            }
        }
        //接收报文
        ChannelResponseInfo cupsResponseInfo = new ChannelResponseInfo();
        cupsResponseInfo.setTransCode(originChnFlow.getRespCode());
        cupsResponseInfo.setPayNo(request.getOriginalPayNo());
        cupsResponseInfo.setOutTranstionId(response.getOrderNo());

        log.info("银联扫码消费查询与sdk交互开始...............");
        return cupsResponseInfo;
    }

    private TradeScanQueryReq packUp(ChannelRequestInfo request, QrChnFlowGt oriQrchnFlow) throws
            TransferFailedException {

        //上送 平安的商户号以及原始凭证号
        TradeScanQueryReq sdkRequest = new TradeScanQueryReq();
        sdkRequest.setOrderNo(oriQrchnFlow.getGtOrderNo())
                .setOldTxnLogId(oriQrchnFlow.getOrderId())
                .setTerminalNo(oriQrchnFlow.getTrmnlNoGt())
                .setIpAddress(oriQrchnFlow.getIpAddress())
                .setMerchantId(oriQrchnFlow.getMercIdGt())
                .setOrgNo(StarPosConstant.ORG_NO)
                .setOprId(StarPosConstant.OPERATOR);
        return sdkRequest;

    }


    /**
     * 功能：更新渠道流水
     *
     * @param qrChnFlowGt
     * @param resp
     * @return
     */
    private QrChnFlowGt updateChnFlow(QrChnFlowGt qrChnFlowGt, TradeScanQueryResp resp) {
        qrChnFlowGt.setRespCode(resp.getReturnCode())
                .setRespMsg(resp.getMessage())
                .setGtOrderNo(resp.getOrderNo())
                .setPayType(resp.getPayType())
                .setPayChannel(resp.getPayChannel());
        if (StringUtils.isNotBlank(resp.getPayTime())) {
            qrChnFlowGt.setPayTime(DateUtil.parse(resp.getPayTime(), "yyyyMMddHHmmss"));
        }
        qrChnFlowGt = innerRouteService.updateChnFlow(qrChnFlowGt);
        return qrChnFlowGt;

    }


}

