package com.posppay.newpay.modules.xposp.router.channel.cup.handle;

import cn.hutool.core.date.DateUtil;
import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.modules.model.ChannelType;
import com.posppay.newpay.modules.model.OrgChannelType;
import com.posppay.newpay.modules.sdk.cup.config.CupConfig;
import com.posppay.newpay.modules.sdk.cup.constant.CupConstant;
import com.posppay.newpay.modules.sdk.cup.model.ErrCodeType;
import com.posppay.newpay.modules.sdk.cup.model.req.JsapiTradeRequest;
import com.posppay.newpay.modules.sdk.cup.model.req.NativeTradeRequest;
import com.posppay.newpay.modules.sdk.cup.model.resp.JsapiTradeResponse;
import com.posppay.newpay.modules.sdk.cup.model.resp.NativeTradeResponse;
import com.posppay.newpay.modules.sdk.cup.service.BaseScanPaymentService;
import com.posppay.newpay.modules.xposp.common.AppExCode;
import com.posppay.newpay.modules.xposp.common.Const;
import com.posppay.newpay.modules.xposp.entity.CupMerchant;
import com.posppay.newpay.modules.xposp.entity.QrChnFlowPa;
import com.posppay.newpay.modules.xposp.entity.UrmStoe;
import com.posppay.newpay.modules.xposp.innertrans.InnerRouteService;
import com.posppay.newpay.modules.xposp.router.TransferFailedException;
import com.posppay.newpay.modules.xposp.router.channel.ChannelRequestInfo;
import com.posppay.newpay.modules.xposp.router.channel.ChannelResponseInfo;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 功能：银联公众号与第三方通讯接口实现
 *
 * @author 2019/7/24
 * @author zoulinjun
 */
@Service("cupGzhProcess")
@Slf4j
public class JsapiProcess {

    @Value("${cup.url}")
    private String address;
    private static String OPERATOR_ID = "000001";
    private static String ACCEPT = "Accept";
    private static String PRE_PAY = "A";
    @Resource(name = "cupBaseScanPaymentService")
    private BaseScanPaymentService baseScanPaymentService;
    @Autowired
    private CupConfig cupConfig;
    @Resource
    private InnerRouteService innerRouteService;

    /**
     * @param cupMerchant
     * @param request
     * @return
     * @throws TransferFailedException
     * @throws UnsupportedEncodingException
     * @throws AppBizException
     */
    public ChannelResponseInfo process(UrmStoe urmStoe, CupMerchant cupMerchant, ChannelRequestInfo request)
            throws TransferFailedException, AppBizException {
        log.info("银联公众号开始.................");
        if (cupConfig.isTest()) {
            log.info("此为测试专用");
            cupMerchant.setCupMerchantId(cupConfig.getMercId());
        }         //判断是否走挡板程序
        ChannelResponseInfo responseInfo = new ChannelResponseInfo();
        QrChnFlowPa qrChnFlowPa = addChnFlow(request, cupMerchant);
        request.setSignInstNo(cupConfig.getOrgNo());
        JsapiTradeRequest entity = packUp(urmStoe, cupMerchant, request);
        String secrekey = cupConfig.getKey();
        JsapiTradeResponse response = null;
        response = baseScanPaymentService.addJsapiTrade(entity, secrekey);
        log.info("返回的sdk信息是" + JSONObject.fromObject(response).toString());
        if (null == response) {
            //没有返回状态未知
            qrChnFlowPa.setRespCode(AppExCode.NETWORK_CONNECTION_FAILED);
            innerRouteService.updateChnFlow(qrChnFlowPa);
            //银联没返回则抛通讯异常
            throw new TransferFailedException(AppExCode.NETWORK_CONNECTION_FAILED, "网络读写异常请重试");
        }
        if (!CupConstant.COMMUNICATE_SUCCESS.equals(response.getStatus())) {
//            0表示成功，非0表示失败此字段是通信标识
            qrChnFlowPa.setRespCode(AppExCode.NETWORK_CONNECTION_FAILED);
            innerRouteService.updateChnFlow(qrChnFlowPa);
            throw new TransferFailedException(AppExCode.UNKNOWN, response.getMessage());
        }
        if (!CupConstant.BUS_SUCCESS.equals(response.getResultCode())) {
            ErrCodeType errCodeType = ErrCodeType.fromCode(response.getErrorCode());
            qrChnFlowPa.setRespCode(CupConstant.CUP_FAILED_CODE);
            throw new TransferFailedException(errCodeType.getTransCode(), errCodeType.getChnName());
        } else {
            qrChnFlowPa.setRespCode(Const.ISO_RESPCODE_00);
            qrChnFlowPa.setRemark("下单成功");
            //更新渠道流水
            updateChnFlow(qrChnFlowPa, response);
            //接收报文
            responseInfo.setTransCode(response.getErrorCode());
            responseInfo.setResult(qrChnFlowPa.getRespCode());
            responseInfo.setPayNo(request.getPayNo());
            responseInfo.setSuperTransType(qrChnFlowPa.getSubTransType());
            responseInfo.setTransCode(response.getResultCode());
            responseInfo.setPayInfo(response.getPayInfo());
            responseInfo.setTokenId(response.getTokenId());
        }
        log.info("银联公众号结束................");
        return responseInfo;
    }

    /**
     * 新增渠道流水信息
     *
     * @param requestInfo
     * @param cupMerchant
     * @return
     * @throws AppBizException
     */
    private QrChnFlowPa addChnFlow(ChannelRequestInfo requestInfo, CupMerchant cupMerchant) throws AppBizException {
        QrChnFlowPa qrChnFlowPa = new QrChnFlowPa();
        qrChnFlowPa.setOrgNo(requestInfo.getSignInstNo())
                .setMrchNo(cupMerchant.getMerchantId())
                .setMrchName(requestInfo.getMrchName())
                .setCurrency(requestInfo.getCurrency())
                .setAmount(requestInfo.getAmount())
                .setDeviceip(requestInfo.getDeviceIp())
                .setPaMercId(cupMerchant.getCupMerchantId())
                .setOprid(requestInfo.getOperator())
                .setTrmnlType("")
                .setOutTradeNo(requestInfo.getPayNo())
                .setTxntime(requestInfo.getDeviceTime())
                .setSysTime(DateUtil.format(new Date(), "yyyyMMddHHmmsss"))
                .setAmount(requestInfo.getAmount())
                .setTotalAmount(requestInfo.getAmount())
                .setAuthCode(requestInfo.getAuthCode())
                .setGoodsTag(requestInfo.getGoodsTag())
                .setGoodsDetail(requestInfo.getGoodsDetail())
                .setAttach(requestInfo.getAttach())
                .setNeedReceipt(requestInfo.getNeedReceipt())
                .setBodydetail(requestInfo.getBody())
                .setNotifyUrl(requestInfo.getNotifyUrl())
                .setSubAppid(requestInfo.getSubppid())
                .setPayChannel(OrgChannelType.CUP_CHANNEL.getOrgNo());
        qrChnFlowPa = innerRouteService.addChnFlow(qrChnFlowPa);
        return qrChnFlowPa;
    }

    /**
     * 功能：更新渠道流水
     *
     * @param chnFlowPa
     * @param response
     * @return
     */
    private QrChnFlowPa updateChnFlow(QrChnFlowPa chnFlowPa, JsapiTradeResponse response) {
        //是否需要保存二维码
        chnFlowPa
                .setBuyerUserId("")
                .setBuyerLogonId("")
                .setAppid("")
                .setTxnCnl(ChannelType.INTELPOS.getCode());
        innerRouteService.updateChnFlow(chnFlowPa);
        return chnFlowPa;

    }

    private JsapiTradeRequest packUp(UrmStoe urmStoe, CupMerchant cupMerchant, ChannelRequestInfo request) throws TransferFailedException {
        JsapiTradeRequest sdkRequest = new JsapiTradeRequest();
        sdkRequest.setNotifyUrl(cupConfig.getNotifyUrl())
                .setIsRaw(CupConstant.IS_RAW)
                .setSubAppId(request.getSubAppid())
                .setSubOpenid(request.getOpenid())
                .setMerchantId(cupMerchant.getCupMerchantId())
                .setOperatorId(request.getOperator())
                .setOutTradeNo(request.getPayNo())
                .setTotalFee(Integer.parseInt(com.posppay.newpay.common.utils.StringUtils.decimal2bcdSimp4yuan(request.getAmount())))
                .setMachineIp(request.getDeviceIp())
                .setSignAgentNo(request.getSignInstNo())
                .setSignType(CupConstant.SIGN_TYPE);
        if (com.posppay.newpay.common.utils.StringUtils.isNotBlank(request.getBody())) {
            sdkRequest.setBody(request.getBody());
        } else {
            sdkRequest.setBody(urmStoe.getStoeNm());
        }
        return sdkRequest;
    }

}
