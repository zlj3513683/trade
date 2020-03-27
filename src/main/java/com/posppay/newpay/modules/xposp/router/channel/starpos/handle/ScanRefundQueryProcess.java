package com.posppay.newpay.modules.xposp.router.channel.starpos.handle;

import cn.hutool.core.date.DateUtil;
import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.common.utils.StringUtils;
import com.posppay.newpay.modules.model.ChannelType;
import com.posppay.newpay.modules.model.OrgChannelType;
import com.posppay.newpay.modules.sdk.cup.config.CupConfig;
import com.posppay.newpay.modules.sdk.cup.model.PayType;
import com.posppay.newpay.modules.sdk.cup.model.resp.TradeQueryResponse;
import com.posppay.newpay.modules.sdk.starpos.constant.StarPosConstant;
import com.posppay.newpay.modules.sdk.starpos.model.req.TradeScanQueryReq;
import com.posppay.newpay.modules.sdk.starpos.model.resp.TradeScanQueryResp;
import com.posppay.newpay.modules.sdk.starpos.service.ScanPayService;
import com.posppay.newpay.modules.xposp.common.AppExCode;
import com.posppay.newpay.modules.xposp.entity.QrChnFlowGt;
import com.posppay.newpay.modules.xposp.entity.QrChnFlowPa;
import com.posppay.newpay.modules.xposp.entity.UrmMmap;
import com.posppay.newpay.modules.xposp.innertrans.InnerRouteService;
import com.posppay.newpay.modules.xposp.router.TransferFailedException;
import com.posppay.newpay.modules.xposp.router.channel.ChannelRequestInfo;
import com.posppay.newpay.modules.xposp.router.channel.ChannelResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 功能：星pos扫码查询与第三方交互实现
 *
 * @author zengjianwens
 */
@Service("starPosRefQueryProcess")
@Slf4j
public class ScanRefundQueryProcess {
    @Autowired
    private CupConfig cupConfig;
    @Resource
    private ScanPayService scanPayService;
    @Resource
    private InnerRouteService innerRouteService;


    /**
     * 星pos扫码查询与第三方交互实现
     * @param request
     * @param originChnFlow
     * @return
     * @throws TransferFailedException
     * @throws AppBizException
     */
    public ChannelResponseInfo process(UrmMmap urmMmap,ChannelRequestInfo request, QrChnFlowGt originChnFlow)
            throws TransferFailedException, AppBizException {
        log.info("星pos扫码消费查询与sdk交互开始.................");
        request.setSignInstNo(cupConfig.getOrgNo());
        TradeScanQueryReq entity = packUp(request,originChnFlow);
        TradeScanQueryResp response = null;
        response = scanPayService.scanQuery(entity,urmMmap.getStarKey());
        if (null == response) {
            //TODO  流程待定
            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "网络读写异常请重试");
        }
        //业务异常
        if (!StarPosConstant.BUINESS_SUCESS.equals(response.getReturnCode())) {
            originChnFlow.setRespCode(response.getReturnCode())
                    .setRespMsg(response.getMessage());
            innerRouteService.updateChnFlow(originChnFlow);
            throw new TransferFailedException(response.getReturnCode(), response.getMessage());
        }

        if (!StarPosConstant.BUINESS_SUCESS.equals(response.getReturnCode())) {
            //0代表查询成功
//            qrChnFlowPa.setRespCode(CupConstant.PA_FAILED_CODE);
            //如果needQuery为N则认为失败
            throw new TransferFailedException(response.getReturnCode(), response.getMessage());
        }
        updateChnFlow(originChnFlow,response);
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
        sdkRequest.setTerminalNo(oriQrchnFlow.getTrmnlNoGt())
                .setOldTxnLogId(oriQrchnFlow.getOrderId())
                .setIpAddress(oriQrchnFlow.getIpAddress())
                .setMerchantId(oriQrchnFlow.getMercIdGt())
                .setOprId(StarPosConstant.OPERATOR)
                .setOrgNo(StarPosConstant.ORG_NO);
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

