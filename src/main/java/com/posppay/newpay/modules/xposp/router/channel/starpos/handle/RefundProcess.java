package com.posppay.newpay.modules.xposp.router.channel.starpos.handle;


import cn.hutool.core.date.DateUtil;
import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.modules.model.OrgChannelType;

import com.posppay.newpay.modules.sdk.pingan.config.ParamConfig;
import com.posppay.newpay.modules.sdk.starpos.constant.StarPosConstant;
import com.posppay.newpay.modules.sdk.starpos.model.req.TradeScanRefundReq;
import com.posppay.newpay.modules.sdk.starpos.model.resp.TradeScanPayResp;
import com.posppay.newpay.modules.sdk.starpos.model.resp.TradeScanRefundResp;
import com.posppay.newpay.modules.sdk.starpos.service.ScanPayService;
import com.posppay.newpay.modules.xposp.common.AppExCode;

import com.posppay.newpay.modules.xposp.entity.*;
import com.posppay.newpay.modules.xposp.innertrans.InnerRouteService;
import com.posppay.newpay.modules.xposp.router.TransferFailedException;
import com.posppay.newpay.modules.xposp.router.channel.ChannelRequestInfo;
import com.posppay.newpay.modules.xposp.router.channel.ChannelResponseInfo;
import com.posppay.newpay.modules.xposp.utils.SnowIdUtils;
import com.posppay.newpay.modules.xposp.utils.SnowIdWorker;
import com.posppay.newpay.modules.xposp.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


/**
 * 功能：星pos退货与第三方通讯接口实现
 *
 * @author zengjianwens
 */
@Service("starPosRefundProcess")
@Slf4j
public class RefundProcess {


    @Resource
    private InnerRouteService innerRouteService;
    @Resource
    private ScanPayService scanPayService;
    @Resource
    private ParamConfig paramConfig;


    /**
     * 星pos退货与第三方通讯接口实现
     * @param urmMmap
     * @param urmTprd
     * @param request
     * @param oriChnFlow
     * @return
     * @throws TransferFailedException
     * @throws AppBizException
     */
    public ChannelResponseInfo process(UrmMmap urmMmap, UrmTprd urmTprd, ChannelRequestInfo request, QrChnFlowGt oriChnFlow)
            throws TransferFailedException, AppBizException {
        log.info("星posB扫C扫码退款与sdk交互开始了.................");

        TradeScanRefundReq entity = packUp(request,oriChnFlow);
        QrChnFlowGt qrChnFlowGt = addChnFlow(urmMmap,urmTprd,request, entity,oriChnFlow);
        //秘钥信息获取
        TradeScanRefundResp response = scanPayService.scanRefund(entity,urmMmap.getStarKey());
        if (null == response) {
            //TODO  流程待定
            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "网络读写异常请重试");
        }
        //业务异常
        if (!StarPosConstant.BUINESS_SUCESS.equals(response.getReturnCode())) {
            qrChnFlowGt.setRespCode(response.getReturnCode())
                    .setRespMsg(response.getMessage());
            innerRouteService.updateChnFlow(qrChnFlowGt);
            throw new TransferFailedException(response.getReturnCode(), response.getMessage());
        }
        //如果返回结果为空则认为未知
        if (StringUtils.isBlank(response.getResult())) {
            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "网络读写异常请重试");
        }
        updateChnFlow(response,qrChnFlowGt);
        ChannelResponseInfo responseInfo = new ChannelResponseInfo();
        log.info("星posB扫C扫码退款与sdk交互开始了.................");
        return responseInfo;
    }


    /**
     * 退款组包
     * @param request
     * @param oriChnFlow
     * @return
     * @throws TransferFailedException
     */
    private TradeScanRefundReq packUp( ChannelRequestInfo request, QrChnFlowGt oriChnFlow) throws TransferFailedException {
        TradeScanRefundReq sdkRequest = new TradeScanRefundReq();
        sdkRequest.setOrderId(request.getPayNo().substring(12,32))
                .setTerminalNo(oriChnFlow.getTrmnlNoGt())
                .setSn("XA000009")
                .setRefAmt(StringUtils.decimal2bcdSimp4yuan(request.getAmount()))
                .setOrderNo(oriChnFlow.getGtOrderNo())
                .setMerchantId(oriChnFlow.getMercIdGt())
                .setIpAddress(request.getDeviceIp())
                .setOprId(StarPosConstant.OPERATOR)
                .setOrgNo(StarPosConstant.ORG_NO);

        return sdkRequest;
    }

    /**
     * 新增渠道流水信息
     * @param requestInfo
     * @param refundReq
     * @return
     * @throws AppBizException
     */
    private QrChnFlowGt addChnFlow(UrmMmap urmMmap, UrmTprd urmTprd,ChannelRequestInfo requestInfo, TradeScanRefundReq refundReq,QrChnFlowGt oriChnFlow) throws AppBizException {
        QrChnFlowGt qrChnFlowGt = new QrChnFlowGt();
        qrChnFlowGt.setOrgNo(refundReq.getOrgNo())
                .setMercIdGt(refundReq.getMerchantId())
                .setMercId(urmMmap.getMercId())
                .setStoeName(requestInfo.getStoreName())
                .setMercName(requestInfo.getMrchName())
                .setTrmnlNoGt(urmTprd.getBankTrmNo())
                .setTrmnlNo(urmTprd.getTrmNo())
                .setRequestId(refundReq.getRequestId())
                .setTelNo(refundReq.getTelNo())
                .setIpAddress(requestInfo.getDeviceIp())
                .setCharacterSet(refundReq.getCharacterSet())
                .setStoeNo(requestInfo.getStoreNo())
                .setOprId(refundReq.getOprId())
                .setScanType(refundReq.getType())
                .setScanVersion(refundReq.getVersion())
                .setOrderId(refundReq.getOrderId())
                .setServOrderNo(requestInfo.getServOrderNo())
                .setAmount(requestInfo.getAmount())
                .setBatchNo(refundReq.getBatchNo())
                .setTxnCnl(refundReq.getTxnCnl())
                .setChnCode(OrgChannelType.STARPOS_CHANNEL.getOrgNo())
                .setOrgNo(refundReq.getOrgNo())
                .setGtOrderNo(oriChnFlow.getGtOrderNo())
                .setPayChannel(oriChnFlow.getPayChannel())
                .setPayType(oriChnFlow.getPayType())
                .setPayTime(new Date())
                .setPayNo(requestInfo.getPayNo());
        qrChnFlowGt = innerRouteService.addChnFlow(qrChnFlowGt);
        return qrChnFlowGt;
    }

    /**
     * 更新渠道流水
     *
     * @param resp
     * @param qrChnFlowGt
     * @return
     */
    private QrChnFlowGt updateChnFlow(TradeScanRefundResp resp, QrChnFlowGt qrChnFlowGt) {
        qrChnFlowGt.setRespCode(resp.getReturnCode())
                .setRespMsg(resp.getMessage());
        qrChnFlowGt = innerRouteService.updateChnFlow(qrChnFlowGt);
        return qrChnFlowGt;

    }


}
