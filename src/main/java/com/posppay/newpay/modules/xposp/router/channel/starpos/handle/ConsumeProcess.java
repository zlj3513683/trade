package com.posppay.newpay.modules.xposp.router.channel.starpos.handle;

import cn.hutool.core.date.DateUtil;
import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.common.utils.StringUtils;
import com.posppay.newpay.modules.model.OrgChannelType;
import com.posppay.newpay.modules.sdk.pingan.config.ParamConfig;
import com.posppay.newpay.modules.sdk.starpos.constant.StarPosConstant;
import com.posppay.newpay.modules.sdk.starpos.model.req.TradeScanPayReq;
import com.posppay.newpay.modules.sdk.starpos.model.resp.TradeScanPayResp;
import com.posppay.newpay.modules.sdk.starpos.service.ScanPayService;
import com.posppay.newpay.modules.xposp.common.AppExCode;
import com.posppay.newpay.modules.xposp.common.Const;
import com.posppay.newpay.modules.xposp.entity.*;
import com.posppay.newpay.modules.xposp.innertrans.InnerRouteService;
import com.posppay.newpay.modules.xposp.router.TransferFailedException;
import com.posppay.newpay.modules.xposp.router.channel.ChannelRequestInfo;
import com.posppay.newpay.modules.xposp.router.channel.ChannelResponseInfo;

import com.posppay.newpay.modules.xposp.router.channel.starpos.model.PayResult;
import com.posppay.newpay.modules.xposp.utils.SnowIdUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.UUID;


/**
 * 功能：星pos消费与第三方通讯接口实现
 *
 * @author zengjianwens
 */
@Service("starPosConsumeProcess")
@Slf4j
public class ConsumeProcess {
    @Resource
    private InnerRouteService innerRouteService;
    @Resource
    private ScanPayService scanPayService;
    @Resource
    private ParamConfig paramConfig;


    /**
     * 星posB扫C消费与sdk对接
     *
     * @param urmMmap
     * @param request
     * @return
     * @throws TransferFailedException
     * @throws AppBizException
     */
    public ChannelResponseInfo process(UrmMmap urmMmap, UrmTprd urmTprd, ChannelRequestInfo request)
            throws TransferFailedException, AppBizException {
        log.info("星posB扫C消费与SDK交互开始.................");

        TradeScanPayReq entity = packUp(urmMmap, urmTprd, request);
        QrChnFlowGt qrChnFlowGt = addChnFlow(entity, request, urmMmap, urmTprd);

        TradeScanPayResp response = null;
        try {
            response = scanPayService.scanPay(entity, urmMmap.getStarKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null == response) {
            //TODO  流程待定
            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "网络读写异常请重试");
        }
        //业务异常
//        if (!StarPosConstant.BUINESS_SUCESS.equals(response.getReturnCode())) {
//            qrChnFlowGt.setRespCode(response.getReturnCode())
//                    .setRespMsg(response.getMessage());
//            innerRouteService.updateChnFlow(qrChnFlowGt);
//            throw new TransferFailedException(response.getReturnCode(), response.getMessage());
//        }
        //如果返回结果为空则认为未知
        if (StringUtils.isBlank(response.getResult())) {
            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "网络读写异常请重试");
        }
        PayResult payResult = PayResult.fromCode(response.getResult());
        switch (payResult) {
            case SUCCESS:
                request.setStatus(Const.TransStatus.SUCCESS_STR);
                qrChnFlowGt = updateChnFlow(response, qrChnFlowGt);
                break;
            case FAIL:
                request.setStatus(Const.TransStatus.FAILED_STR);
                qrChnFlowGt = updateChnFlow(response, qrChnFlowGt);
                throw new TransferFailedException(response.getReturnCode(), response.getMessage());
            case UNKNOW:
                request.setStatus(Const.TransStatus.UNKNOWN_STR);
                qrChnFlowGt = updateChnFlow(response, qrChnFlowGt);
                throw new TransferFailedException(response.getReturnCode(), response.getMessage());
            case WAITAUTH:
                request.setStatus(Const.TransStatus.UNKNOWN_STR);
                qrChnFlowGt = updateChnFlow(response, qrChnFlowGt);
                throw new TransferFailedException(response.getReturnCode(), response.getMessage());
            default:
                break;
        }

//        //接收报文
        ChannelResponseInfo responseInfo = new ChannelResponseInfo();
        responseInfo.setTransCode(response.getReturnCode());
        responseInfo.setResult(response.getResult());
        responseInfo.setPayNo(request.getPayNo());
        responseInfo.setOutTranstionId(response.getOrderNo());
        log.info("星posB扫C消费与SDK交互结束................");
        return responseInfo;
    }


    private TradeScanPayReq packUp(UrmMmap urmMmap, UrmTprd urmTprd, ChannelRequestInfo request) throws TransferFailedException {
        //获取磁道信息

        TradeScanPayReq sdkRequest = new TradeScanPayReq();
        sdkRequest.setAuthCode(request.getAuthCode())
                .setAmount(StringUtils.decimal2bcdSimp4yuan(request.getAmount()))
                .setOrderId(request.getPayNo().substring(12,32))
                .setTxnCnl(StarPosConstant.TXNCNL_I)
                .setTerminalNo(urmTprd.getBankTrmNo())
                .setIpAddress(request.getDeviceIp())
                .setMerchantId(urmMmap.getCorgMercId())
                .setOrgNo(StarPosConstant.ORG_NO)
                .setRequestId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32))
                .setOprId(StarPosConstant.OPERATOR);
        return sdkRequest;
    }


    /**
     * 新增渠道流水信息
     *
     * @param req
     * @param requestInfo
     * @param urmMmap
     * @param urmTprd
     * @return
     * @throws AppBizException
     */
    private QrChnFlowGt addChnFlow(TradeScanPayReq req, ChannelRequestInfo requestInfo, UrmMmap urmMmap, UrmTprd urmTprd) throws AppBizException {
        QrChnFlowGt qrChnFlowGt = new QrChnFlowGt();
        qrChnFlowGt.setOrgNo(req.getOrgNo())
                .setMercIdGt(urmMmap.getCorgMercId())
                .setMercId(urmMmap.getMercId())
                .setStoeName(requestInfo.getStoreName())
                .setMercName(requestInfo.getMrchName())
                .setTrmnlNoGt(urmTprd.getBankTrmNo())
                .setTrmnlNo(urmTprd.getTrmNo())
                .setRequestId(req.getRequestId())
                .setTelNo(req.getTelNo())
                .setIpAddress(requestInfo.getDeviceIp())
                .setCharacterSet(req.getCharacterSet())
                .setStoeNo(requestInfo.getStoreNo())
                .setOprId(req.getOprId())
                .setScanType(req.getType())
                .setScanVersion(req.getVersion())
                .setOrderId(req.getOrderId())
                .setServOrderNo(requestInfo.getServOrderNo())
                .setAmount(requestInfo.getAmount())
                .setBatchNo(req.getBatchNo())
                .setTxnCnl(req.getTxnCnl())
                .setChnCode(OrgChannelType.STARPOS_CHANNEL.getOrgNo())
                .setOrgNo(req.getOrgNo())
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
    private QrChnFlowGt updateChnFlow(TradeScanPayResp resp, QrChnFlowGt qrChnFlowGt) {
        qrChnFlowGt.setRespCode(resp.getReturnCode())
                .setRespMsg(resp.getMessage())
                .setGtOrderNo(resp.getOrderNo())
                .setPayChannel(resp.getPayChannel())
                .setPayType(resp.getPayType());
        if (StringUtils.isNotBlank(resp.getPayTime())){

            qrChnFlowGt.setPayTime(DateUtil.parse(resp.getPayTime(), "yyyyMMddHHmmss"));
        }
        qrChnFlowGt = innerRouteService.updateChnFlow(qrChnFlowGt);
        return qrChnFlowGt;

    }
}
