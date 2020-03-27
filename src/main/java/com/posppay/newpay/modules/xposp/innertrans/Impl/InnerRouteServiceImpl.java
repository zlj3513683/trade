package com.posppay.newpay.modules.xposp.innertrans.Impl;

import cn.hutool.core.date.DateUtil;
import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.common.utils.DateUtils;
import com.posppay.newpay.common.utils.StringUtils;
import com.posppay.newpay.modules.model.PayChannelType;
import com.posppay.newpay.modules.model.PrdtPerMissionType;
import com.posppay.newpay.modules.sdk.pingan.config.ParamConfig;
import com.posppay.newpay.modules.xposp.common.AppExCode;
import com.posppay.newpay.modules.xposp.common.Const;
import com.posppay.newpay.modules.xposp.dao.service.*;
import com.posppay.newpay.modules.xposp.entity.*;
import com.posppay.newpay.modules.xposp.innertrans.InnerRouteService;
import com.posppay.newpay.modules.xposp.router.TransferRouter;
import com.posppay.newpay.modules.xposp.router.channel.MrchAndTrmnl;
import com.posppay.newpay.modules.xposp.router.channel.cup.CupTransferChannel;
import com.posppay.newpay.modules.xposp.transfer.model.req.ConsumeRequest;
import com.posppay.newpay.modules.xposp.transfer.model.req.QrcodeRequest;
import com.posppay.newpay.modules.xposp.transfer.model.req.RefundRequest;
import com.shouft.newpay.base.utils.Currency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Slf4j
@Service("innerRouteService")
public class InnerRouteServiceImpl implements InnerRouteService {
    @Resource
    private UrmMmapService urmMmapService;
    @Resource
    private UrmTprdService urmTprdService;
    @Resource
    private CmmInfService cmmInfService;
//    @Resource
//    private HsmUtil hsmUtil;

    @Resource
    private SequenceService sequenceService;
    @Resource
    private TmsPosService tmsPosService;
    @Resource
    private TmsSnnoService tmsSnnoService;
    @Resource
    private UrmComiService urmComiService;
    @Resource
    private UrmStoeService urmStoeService;
    @Resource
    private CupTransferChannel cupTransferChannel;
    @Resource
    private CupMerchantService cupMerchantService;
    @Resource
    private PrdtPermService prdtPermService;
    @Resource
    private MercTransChnService mercTransChnService;
    @Resource
    private UrmMinfService urmMinfService;
    @Resource
    private UrmPrdtService urmPrdtService;
    @Resource
    private UrmMstlService urmMstlService;
    @Resource
    private HpsJnlService hpsJnlService;
    @Resource
    private UrmMfeeService urmMfeeService;
    @Resource
    private TransferRouter transferRouter;
    @Resource
    private CupAreaService cupAreaService;
    @Resource
    private OrganizationRouteService organizationRouteService;
    @Resource
    private OrderService orderService;
    @Resource
    private PaymentService paymentService;
    @Resource
    private QrChnFlowPaService qrChnFlowPaService;
    @Resource
    private QrChnFlowGtService qrChnFlowGtService;
    @Resource
    private PaMercinfoService paMercinfoService;
    @Resource
    private UrmAgntService urmAgntService;
    @Resource
    private UrmAgtfeeService urmAgtfeeService;
    @Resource
    private ChannelInfoService channelInfoService;
    @Resource
    private ChannelScanSupportService channelScanSupportService;
    @Value("${gt.prdId}")
    private String prdId;
    @Resource
    private ParamConfig paramConfig;

    /**
     * 功能： 查询机构商户信息
     *
     * @param mrcId
     * @return
     */
    @Override
    public UrmMmap queryChnMrch(String mrcId) {
        return urmMmapService.findByMrcId(mrcId, prdId);
    }

    /**
     * 功能： 查询机构终端信息
     *
     * @param trmnlNo
     * @return
     */
    @Override
    public UrmTprd queryChnTrmnl(String trmnlNo) {
        return urmTprdService.findByTrmnlNo(trmnlNo, prdId);
    }

    @Override
    public UrmStoe queryStore(String mercId, String urmStoreNo) throws AppBizException {
        UrmStoe urmStoe = null;
        try {
            List<UrmStoe> urmStoes = urmStoeService.findByStoreNo(mercId, urmStoreNo);
            if (null != urmStoes && 1 == urmStoes.size()) {
                urmStoe = urmStoes.get(0);
            }
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "系统繁忙");
        }
        return urmStoe;
    }

    /**
     * 考虑并发下这里更新采用读提交
     *
     * @param cmmInf
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void updateByMrchAndTrmnl(CmmInf cmmInf) {
        cmmInfService.updateByMrchAndTrmNo(cmmInf);
    }


    /**
     * 根据设备sn获取终端门店信息
     *
     * @param snNo
     * @return
     */
    //TODO  错误码具体待定
    @Override
    public TmsPos queryTrmnl(String snNo) {
        TmsPos tmspos = null;
        try {
            tmspos = tmsPosService.findBySnNo(snNo);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "未知错误");
        }

        return tmspos;
    }

    /**
     * 根据商户号门店号获取终端
     *
     * @param mercId
     * @param stoeId
     * @return
     * @throws AppBizException
     */
    @Override
    public TmsPos queryTrmnlBymerc(String mercId, String stoeId) throws AppBizException {
        TmsPos tmspos = null;
        try {
            tmspos = tmsPosService.findBymercId(mercId, stoeId);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "未知错误");
        }

        return tmspos;
    }

    /**
     * g功能： 根据商户号和门店号获取扫码产品
     *
     * @param mercId
     * @param storeId
     * @return
     * @throws AppBizException
     */
    @Override
    public UrmPrdt queryScanProdt(String mercId, String storeId) throws AppBizException {
        UrmPrdt urmPrdt = null;
        try {
            urmPrdt = urmPrdtService.findByMrchAndStore(mercId, storeId, "1002");
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "未知错误");
        }
        return urmPrdt;
    }

    @Override
    public UrmMstl queryScanMstl(String mercId, String stoeId) throws AppBizException {
        UrmMstl urmMstl = null;
        try {
            urmMstl = urmMstlService.findByMrchAndStoeNo(mercId, stoeId, "1002");
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "未知异常");
        }

        return urmMstl;
    }

    @Override
    public UrmMinf queryUrmMinf(String mercId) throws AppBizException {
        UrmMinf urmMinf = null;
        try {
            urmMinf = urmMinfService.findBymercId(mercId);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "系统繁忙");
        }

        return urmMinf;
    }

    /**
     * 根据地区编码查找转换后的平安地址编号
     *
     * @param areaCode
     */
    @Override
    public CupArea queryPaAreaByCode(String areaCode) {
        CupArea cupArea = null;
        try {
            cupArea = cupAreaService.findAreaCode(areaCode);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "系统繁忙");
        }
        return cupArea;
    }

    /**
     * 根据省份编号和城市编号来获取平安机构秘钥
     *
     * @param provCode
     * @param cityCode
     * @return
     */
    @Override
    public OrganizationRoute queryOrganiByareaCode(String provCode, String cityCode) {
        OrganizationRoute organizationRoute = null;
        try {
            organizationRoute = organizationRouteService.findByAreaCode(provCode, cityCode);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "系统繁忙");
        }
        //如果查不到的话直接根据省份查找默认机构
        if (null == organizationRoute) {
            organizationRoute = organizationRouteService.findByProCode(provCode);
        }
        return organizationRoute;
    }

    /**
     * 根据省份编号和城市编号来获取平安机构秘钥
     *
     * @param provCode
     * @return
     */
    @Override
    public OrganizationRoute queryOrganiByProvinCode(String provCode) {
        OrganizationRoute organizationRoute = null;
        try {
            organizationRoute = organizationRouteService.findByProCode(provCode);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "系统繁忙");
        }
        return organizationRoute;

    }

    @Override
    public UrmComi queryByMercId(String mercId) throws AppBizException {
        return urmComiService.findByMercId(mercId);
    }

    @Override
    public CupMerchant queryByMerIdAndStoreNo(String mercId, String storeNo) throws AppBizException {
        return cupMerchantService.findByMercIdAndStoreNo(mercId, storeNo);
    }

    /**
     * 根据系统商户号来获取真实的商户
     *
     * @param mercId
     */
    @Override
    public PaMercinfo queryPaByMerId(String mercId) throws AppBizException {
        PaMercinfo paMercInfo = null;
        try {

            paMercInfo = paMercinfoService.findBymercId(mercId);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "系统繁忙");
        }
        return paMercInfo;
    }

    @Override
    public List<PrdtPerm> queeyPrdtPermisson(String mercId, String storeId, String prdId) throws AppBizException {
        return prdtPermService.queryByMrchAndTrmnlNo(mercId, storeId, prdId);
    }

    /**
     * @return
     */
    @Override
    public CmmInf queryBymrchAndTrmNo() {
//        MrchAndTrmnl mrchAndTrmnl = StarPosProperty.mrchAndTrmnl;
//        return cmmInfService.findByMrchAndTrmNo(mrchAndTrmnl);
        return null;
    }

    /**
     * 信息渠道流水
     *
     * @param urmStoe
     * @param request
     * @throws AppBizException
     */
    @Override
    public HpsJnl addScanPayHpsFlow(UrmStoe urmStoe, UrmMstl urmMstl, UrmMinf urmMinf, ConsumeRequest request) throws AppBizException {
        String logNo = this.generateLogNo();
        String tlogNo = logNo.substring(logNo.length() - 12);
        BigDecimal feeAmt = this.calculateFee(request.getMerchantId(), urmStoe.getStoeId(), "1002", request.getAmount(), request.getPayChannel());
        HpsJnl hpsJnl = HpsJnl
                .builder()
                .logNo(logNo)
                .acDt(DateUtils.format(new Date(), "yyyyMMdd"))
                .txnTyp("N")
                .txnCd(PrdtPerMissionType.POS_SCAN.getPermission())
//                .txnTm(request.getTransTime())
                .txnAmt(request.getAmount().doubleValue())
                .refAmt(request.getAmount().doubleValue())
                .ccy(Currency.CNY.getCodeN())
                .mercId(urmMinf.getMercId())
                .posOprId("001")
//                .ctxnDt(request.getTransDate())
//                .ctxnTm(request.getTransHour())
//                .cseqNo(request.getTransFlowNo())
                .mccCd(urmStoe.getMccCd())
                .mercNm(urmMinf.getMercNm())
                .tmsTm(DateUtil.format(new Date(), "yyyyMMddHHmmsss"))
                .paychannel("2")
                .payMod("02")
                .ctxnDt(DateUtils.format(new Date(), "yyyyMMdd"))
                .ctxnTm(DateUtils.format(new Date(), "HHmmss"))
                .cseqNo(request.getOutOrderNo())
                //落地机构
                .ownOrgNo(urmStoe.getOwnOrgNo())
                .txnAmt(request.getAmount().doubleValue())
                .stlTyp(urmMstl.getStlTyp())
                .basefeedt1(feeAmt.doubleValue())
                .rcpOrdId(request.getOutOrderNo())
                .mercType(urmMinf.getMercType())
                .posOprId("001")
                .orgNo(urmStoe.getSigOrgNo())
                .mercNm(urmMinf.getMercNm())
                .txnTm(DateUtils.format(new Date(), "yyyyMMddHHmmss"))
                .srefNo(tlogNo)
                .usrNo(urmStoe.getUsrNo())
                .txnTyp("N")
                .stoeId(urmStoe.getStoeId())
                .mccCd(urmStoe.getMccCd())
                .corgSrefNo(tlogNo)
//                .rtrCod()
                .corgFeeAmt(feeAmt.doubleValue())
                .payMod("02")
                .tmSmp(DateUtils.format(new Date(), "yyyyMMddHHmmss"))
                .txnCd(PrdtPerMissionType.POS_SCAN.getPermission())
//                .txnCnl(request.getTxnCnl())
                .logNo(logNo)
                .acDt(DateUtils.format(new Date(), "yyyyMMdd"))
                .mercId(request.getMerchantId())
                .build();
        hpsJnlService.addFlow(hpsJnl);
        return hpsJnl;

    }

    /**
     * 新增扫码交易平台流水
     *
     * @param payment
     * @return
     * @throws AppBizException
     */
    @Override
    public Payment addPaymet(Payment payment) throws AppBizException {
        try {
            payment = paymentService.addPayment(payment);
        } catch (Exception e) {
            log.error("未知错误", e);
            throw new AppBizException(AppExCode.UNKNOWN, "系统繁忙");
        }
        return payment;
    }

    /**
     * 新增扫码交易渠道流水
     *
     * @param qrChnFlowPa
     * @return
     * @throws AppBizException
     */
    @Override
    public QrChnFlowPa addChnFlow(QrChnFlowPa qrChnFlowPa) throws AppBizException {
        try {
            qrChnFlowPa = qrChnFlowPaService.inserQrFow(qrChnFlowPa);
        } catch (Exception e) {
            log.error("未知错误", e);
            throw new AppBizException(AppExCode.UNKNOWN, "系统繁忙");
        }
        return qrChnFlowPa;
    }

    /**
     * 新增扫码交易渠道流水
     *
     * @param qrChnFlowGt
     * @return
     * @throws AppBizException
     */
    @Override
    public QrChnFlowGt addChnFlow(QrChnFlowGt qrChnFlowGt) throws AppBizException {
        try {
            qrChnFlowGt = qrChnFlowGtService.inserQrFow(qrChnFlowGt);
        } catch (Exception e) {
            log.error("未知错误", e);
            throw new AppBizException(AppExCode.UNKNOWN, "系统繁忙");
        }
        return qrChnFlowGt;
    }

    /**
     * 新增扫码交易渠道流水
     *
     * @param qrChnFlowPa
     * @return
     * @throws AppBizException
     */
    @Override
    public QrChnFlowPa updateChnFlow(QrChnFlowPa qrChnFlowPa) throws AppBizException {
        try {
            qrChnFlowPa = qrChnFlowPaService.updateQeFlow(qrChnFlowPa);
        } catch (Exception e) {
            log.error("未知错误", e);
            throw new AppBizException(AppExCode.UNKNOWN, "系统繁忙");
        }
        return qrChnFlowPa;
    }

    /**
     * 更新扫码渠道流水
     *
     * @param qrChnFlowGt
     * @return
     * @throws AppBizException
     */
    @Override
    public QrChnFlowGt updateChnFlow(QrChnFlowGt qrChnFlowGt) throws AppBizException {
        try {
            qrChnFlowGt = qrChnFlowGtService.updateQrFlow(qrChnFlowGt);
        } catch (Exception e) {
            log.error("未知错误", e);
            throw new AppBizException(AppExCode.UNKNOWN, "系统繁忙");
        }
        return qrChnFlowGt;
    }

    /**
     * 新增退货渠道流水
     *
     * @param urmStoe
     * @param request
     * @throws AppBizException
     */
    @Override
    public HpsJnl addScanRefundHpsFlow(UrmStoe urmStoe, UrmMstl urmMstl, UrmMinf urmMinf, RefundRequest request) throws AppBizException {
        String logNo = this.generateLogNo();
        String tlogNo = logNo.substring(logNo.length() - 12);
        BigDecimal feeAmt = this.calculateFee(request.getMerchantId(), urmStoe.getStoeId(), "1002", request.getRefAmt(), "");
        HpsJnl hpsJnl = HpsJnl
                .builder()
                .ccy(Currency.CNY.getCodeN())
                .ctxnDt(DateUtils.format(new Date(), "yyyyMMdd"))
                .ctxnTm(DateUtils.format(new Date(), "HHmmss"))
                .cseqNo(request.getOutOrderNo())
                .txnAmt(request.getRefAmt().doubleValue())
                .stlTyp(urmMstl.getStlTyp())
                .basefeedt1(feeAmt.doubleValue())
                .rcpOrdId(request.getOutOrderNo())
                .mercType(urmMinf.getMercType())
                .posOprId("001")
                .orgNo(urmStoe.getSigOrgNo())
                .mercNm(urmMinf.getMercNm())
                .txnTm(DateUtils.format(new Date(), "yyyyMMddHHmmss"))
                .srefNo(tlogNo)
                .usrNo(urmStoe.getUsrNo())
                .txnTyp("N")
                .stoeId(urmStoe.getStoeId())
                .mccCd(urmStoe.getMccCd())
                .corgSrefNo(tlogNo)
//                .rtrCod()
                .corgFeeAmt(feeAmt.doubleValue())
                .payMod("02")
                .tmSmp(DateUtils.format(new Date(), "yyyyMMddHHmmss"))
                .txnCd(PrdtPerMissionType.POS_SCAN.getPermission())
                .paychannel("2")
//                .txnCnl(request.getTxnCnl())
                .logNo(logNo)
                .acDt(DateUtils.format(new Date(), "yyyyMMdd"))
                .mercId(request.getMerchantId())
                .build();
        hpsJnlService.addFlow(hpsJnl);
        return hpsJnl;

    }

    /**
     * C扫B生成二维码预订单流水生成
     *
     * @param urmStoe
     * @param urmMstl
     * @param urmMinf
     * @param request
     * @throws AppBizException
     */
    @Override
    public HpsJnl addQrCodeHpsFlow(UrmStoe urmStoe, UrmMstl urmMstl, UrmMinf urmMinf, QrcodeRequest request) throws AppBizException {
        String logNo = this.generateLogNo();
        String tlogNo = logNo.substring(logNo.length() - 12);
        BigDecimal feeAmt = this.calculateFee(request.getMerchantId(), urmStoe.getStoeId(), "1002", request.getAmount() ,"");
        HpsJnl hpsJnl = HpsJnl
                .builder()
                .ccy(Currency.CNY.getCodeN())
//                .tlogNo(request.())
                .ctxnDt(DateUtils.format(new Date(), "yyyyMMdd"))
                .ctxnTm(DateUtils.format(new Date(), "HHmmss"))
//                .cseqNo(request.getOrderId())
                .ownOrgNo(urmStoe.getSigOrgNo())
                .txnAmt(request.getAmount().doubleValue())
                .stlTyp(urmMstl.getStlTyp())
                .basefeedt1(feeAmt.doubleValue())
//                .rcpOrdId(request.getOrderId())
                .mercType(urmMinf.getMercType())
                .posOprId("001")
                .orgNo(urmStoe.getSigOrgNo())
//                .corgBatNo(request.getBatchNo())
                .mercNm(urmMinf.getMercNm())
                .txnTm(DateUtils.format(new Date(), "yyyyMMddHHmmss"))
                .srefNo(tlogNo)
                .usrNo(urmStoe.getUsrNo())
                .txnTyp("N")
                .stoeId(urmStoe.getStoeId())
                .mccCd(urmStoe.getMccCd())
                .corgSrefNo(tlogNo)
//                .rtrCod()
                .corgFeeAmt(feeAmt.doubleValue())
                .payMod("02")
//                .batNo(request.getBatchNo())
                .tmSmp(DateUtils.format(new Date(), "yyyyMMddHHmmss"))
//                .snNo(request.getSn())
//                .trmNo(request.getTerminalNo())
                .txnCd(PrdtPerMissionType.POS_SCAN.getPermission())
                .paychannel("2")
//                .txnCnl(request.getTxnCnl())
                .logNo(logNo)
                .acDt(DateUtils.format(new Date(), "yyyyMMdd"))
                .mercId(request.getMerchantId())
//                .csnNo(request.getSn())
                .build();
        hpsJnlService.addFlow(hpsJnl);
        return hpsJnl;

    }


    /**
     * 更新读写操作
     *
     * @param hpsJnl
     * @return
     * @throws AppBizException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public HpsJnl updateHpsFlow(HpsJnl hpsJnl) throws AppBizException {
        hpsJnlService.updateFlow(hpsJnl);
        return hpsJnl;
    }

    /**
     * 根据金额和费率计算费率金额
     *
     * @param mercId
     * @param stoeId
     * @param prdId
     * @param amout
     * @param payChanel
     * @return
     * @throws AppBizException
     */
    @Override
    public BigDecimal calculateFee(String mercId, String stoeId, String prdId, BigDecimal amout, String payChanel) throws AppBizException {
        UrmMfee urmMfee = urmMfeeService.findByMrchAndStoe(mercId, stoeId, prdId);
        PayChannelType payChannelType = PayChannelType.fromCode(payChanel);
        BigDecimal freeAmt = null;
        switch (payChannelType) {
            case WX_CHANNEL:
                freeAmt = urmMfee.getFeeRat()
                        .movePointLeft(3)
                        .multiply(amout)
                        .setScale(2, RoundingMode.HALF_UP);
                break;
            case ALI_CHANNEL:
                freeAmt = urmMfee.getFeeRat3()
                        .movePointLeft(3)
                        .multiply(amout)
                        .setScale(2, RoundingMode.HALF_UP);
                break;
            case CUP_CHANNEL:
                freeAmt = urmMfee.getFeeRat2()
                        .movePointLeft(3)
                        .multiply(amout)
                        .setScale(2, RoundingMode.HALF_UP);
                break;
            default:
                break;
        }
        return freeAmt;

    }

    /**
     * 根据商户号门店号获取费率并计算费率金额
     *
     * @param rate
     * @param amt
     * @return
     * @throws AppBizException
     */
    @Override
    public BigDecimal calculateRefundFee(BigDecimal rate, BigDecimal amt) throws AppBizException {
        BigDecimal rateFee = null;
        if (null != rate && null != amt) {
            rateFee = rate.multiply(amt)
                    .movePointLeft(3)
                    .setScale(4, RoundingMode.HALF_UP);
        }
        return rateFee;
    }
    /**
     * 功能：转换pin秘钥
     *
     * @param pinKeyZmk
     * @return
     * @throws HSMException
     */
//    @Override
//    public String switchWorkingPin(String pinKeyZmk) throws HSMException {
//        String zmk = StarPosProperty.mrchAndTrmnl.getZmk();
//        CMDFAFBOut out = new CMDFAFBOut();
//        out = hsmUtil.pinKeyZmk2Lmk(zmk, pinKeyZmk);
//        log.info("转换后的pin是：" + out.getZpkEncryptByLmk());
//        log.info("转换pin的checkvalue是：" + out.getKeyCheckValue());
//        return out.getZpkEncryptByLmk();
//    }

    /**
     * 功能： 转换mac秘钥
     *
     * @param
     * @return
     * @throws
     */
//    @Override
//    public String switchWorkingMac(String macKeyZmk) throws HSMException {
//        String zmk = StarPosProperty.mrchAndTrmnl.getZmk();
//        CMDFKFLOut out = new CMDFKFLOut();
//        out = hsmUtil.macKeyZmk2Lmk(zmk, macKeyZmk);
//        log.info("转换后的mac是：" + out.getKeyEncryptByLmk());
//        log.info("转换mac的checkvalue是：" + out.getKeyCheckValue());
//        return out.getKeyEncryptByLmk();
//    }
    @Override
    public HpsJnl querySingleFlow(String logNo) {
        HpsJnl hpsJnl = null;
        try {
            hpsJnl = hpsJnlService.findBylogNO(logNo);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "未知错误");
        }
        return hpsJnl;
    }

    /**
     * 根据payNo和orgNo来查找单笔流水
     *
     * @param payNo
     * @param orgNo
     * @return
     */
    @Override
    public QrChnFlowPa querySingleFlow(String payNo, String orgNo, String transDate) {
        return qrChnFlowPaService.findBypayNo(payNo, orgNo, transDate);
    }

    /**
     * 根据payNo和orgNo来查找单笔流水
     *
     * @param payNo
     * @param orgNo
     * @param transDate
     * @return
     */
    @Override
    public QrChnFlowGt querySingleGtFlow(String payNo, String orgNo, String transDate) {
        return qrChnFlowGtService.findByPayNo(payNo, orgNo, transDate);
    }

    /**
     * 根据c端流水号来查询流水
     *
     * @param cseqNo
     * @return
     */
    @Override
    public HpsJnl querySingleFlowByCseqNo(String cseqNo) {
        HpsJnl hpsJnl = null;
        try {
            hpsJnl = hpsJnlService.findBylogNO(cseqNo);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "未知错误");
        }
        return hpsJnl;
    }

    /**
     * 根据下游订单号和机构号来获取单笔平台流水
     * 如果查不到则查前一天或者后一天
     *
     * @param outOrderNo
     * @param orgNo
     * @return
     */
    @Override
    public Payment querySinglePayment(String outOrderNo, String orgNo, String transDate) {
        if (StringUtils.isBlank(transDate)) {
            //如果下游不上送时间则认为是当前日期
            transDate = DateUtil.format(new Date(), "yyyyMMdd");
        }
        Payment payment = paymentService.findByOutOrderNo(outOrderNo, orgNo, transDate);
        Calendar calendar = new GregorianCalendar();
        Date date = DateUtil.parse(transDate, "yyyyMMdd");
        if (null == payment) {
            //查后一天
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);
            date = calendar.getTime();
            payment = paymentService.findByOutOrderNo(outOrderNo, orgNo, DateUtil.format(date, "yyyyMMdd"));
            if (null == payment) {
                //查前一天
                calendar.setTime(date);
                calendar.add(calendar.DATE, -1);
                date = calendar.getTime();
                payment = paymentService.findByOutOrderNo(outOrderNo, orgNo, DateUtil.format(date, "yyyyMMdd"));
            }
        }

        return payment;
    }

    /**
     * 根据下游订单号和机构号来获取单笔平台流水
     *
     * @param payNo
     * @param orgNo
     * @param transDate
     * @return
     */
    @Override
    public Payment querySinglePaymentByPayNo(String payNo, String orgNo, String transDate) {
        if (StringUtils.isBlank(transDate)) {
            //如果下游不上送时间则认为是当前日期
            transDate = DateUtil.format(new Date(), "yyyyMMdd");
        }
        Payment payment = paymentService.findByPayNo(payNo, orgNo, transDate);
        Calendar calendar = new GregorianCalendar();
        Date date = DateUtil.parse(transDate, "yyyyMMdd");
        if (null == payment) {
            //查后一天
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);
            date = calendar.getTime();
            payment = paymentService.findByPayNo(payNo, orgNo, DateUtil.format(date, "yyyyMMdd"));
            if (null == payment) {
                //查前一天
                calendar.setTime(date);
                calendar.add(calendar.DATE, -1);
                date = calendar.getTime();
                payment = paymentService.findByPayNo(payNo, orgNo, DateUtil.format(date, "yyyyMMdd"));
            }
        }

        return payment;
    }

    /**
     * 根据第三方凭证号来获取流水
     *
     * @param outTransactionId
     * @param orgNo
     * @return
     */
    @Override
    public Payment queryPaymentByTransationId(String outTransactionId, String orgNo, String transType, String oriTransDate) {
//        String transDate = outTransactionId.substring(12, 20);
        String transDate = oriTransDate;
        if (StringUtils.isBlank(transDate)) {
            //如果下游不上送时间则认为是当前日期
            transDate = DateUtil.format(new Date(), "yyyyMMdd");
        }
//        String[] transTypes = new String[2]
//        List<String>transTypes = new ArrayList<String>();
//        transTypes.add(TransType.CONSUME.getOrdinal()+"");
//        transTypes.add(TransType.REFUND.getOrdinal()+"");

        Payment payment = paymentService.findByOutTransctionId(outTransactionId, orgNo, transDate, transType);
        Calendar calendar = new GregorianCalendar();
        Date date = DateUtil.parse(transDate, "yyyyMMdd");
        if (null == payment) {
            //查后一天
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);
            date = calendar.getTime();
            payment = paymentService.findByOutTransctionId(outTransactionId, orgNo, DateUtil.format(date, "yyyyMMdd"), transType);
            if (null == payment) {
                //查前一天
                calendar.setTime(date);
                calendar.add(calendar.DATE, -1);
                date = calendar.getTime();
                payment = paymentService.findByOutTransctionId(outTransactionId, orgNo, DateUtil.format(date, "yyyyMMdd"), transType);
            }
        }


        return payment;
//        return null;
    }

    /**
     * 根据订单号和机构号来获取单笔平台流水
     *
     * @param ordNo
     * @param orgNo
     * @param transDate
     * @return
     */
    @Override
    public Order querySingleOrder(String ordNo, String orgNo, String transDate) {
        Order order = null;
        try {
            order = orderService.querySingleOrder(ordNo, orgNo, transDate);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "未知错误");
        }
        return order;
    }

    /**
     * 新增订单流水
     *
     * @param order
     * @return
     */
    @Override
    public Order addOrder(Order order) throws AppBizException {
        try {
            orderService.insertOne(order);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.UNKNOWN, "未知错误");
        }
        return order;
    }


//
//    @Override
//    public byte[] calcMac(String zpkEncryptByLmk, String msg) throws HSMException {
//        String mac = hsmUtil.calculateMACByX99(zpkEncryptByLmk, msg.toUpperCase().getBytes());
//        return CodecUtils.hex2byte(mac);
//    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void updateConsumeFlow(Payment payment, Order order) {
        try {
            Date updDate = new Date();
            payment.setUpdTime(updDate);
            payment = paymentService.updatePayment(payment);
            if (Const.TransStatus.SUCCESS_STR.equals(payment.getTransStatus())) {
                order.setOrdStatus(Const.OrderStatus.PAYED_STR);
                order.setRefundBalance(order.getOrdAmt());
            } else if (Const.TransStatus.FAILED_STR.equals(payment.getTransStatus())) {
                order.setOrdStatus(Const.OrderStatus.PAY_FAILED_STR);
            } else if (Const.TransStatus.UNKNOWN_STR.equals(payment.getTransStatus())) {
                order.setOrdStatus(Const.OrderStatus.UNKNOWN_STR);
            } else {
                log.error("[非法交易状态， status:" + payment.getTransStatus() + "]");
            }
            order.setUpdTime(new Date());
            orderService.updateOrder(order);
        } catch (Exception e) {
            log.error("未知错误", e);
            throw new AppBizException(AppExCode.UNKNOWN, "系统繁忙");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void updateRefundFlow(Payment payment, Order order) {
        try {
            Date updDate = new Date();
            payment.setUpdTime(updDate);
            payment = paymentService.updatePayment(payment);
            if (Const.TransStatus.SUCCESS_STR.equals(payment.getTransStatus())) {
                if (order.getRefundBalance().compareTo(payment.getTransAmount()) > 0) {
                    //部分退款
                    order.setRefundBalance(order.getRefundBalance().subtract(payment.getTransAmount()));
                    order.setOrdStatus(Const.OrderStatus.PARTIALLY_REFUNED_STR);
                } else {
                    //全额退款
                    order.setRefundBalance(order.getRefundBalance().subtract(payment.getTransAmount()));
                    order.setOrdStatus(Const.OrderStatus.REFUNED_STR);
                }
            } else if (Const.TransStatus.FAILED_STR.equals(payment.getTransStatus())) {
                log.error("退货交易失败");
            } else if (Const.TransStatus.UNKNOWN_STR.equals(payment.getTransStatus())) {
                order.setRefundBalance(order.getRefundBalance().subtract(payment.getTransAmount()));
                order.setOrdStatus(Const.OrderStatus.UNKNOWN_STR);
            } else {
                log.error("[非法交易状态， status:" + payment.getTransStatus() + "]");
            }
            order.setUpdTime(new Date());
            orderService.updateOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void updateRefundQueryFlow(Payment payment, Order order, String status) {
        try {
            Date updDate = new Date();
            payment.setUpdTime(updDate);
            payment = paymentService.updatePayment(payment);
            if (Const.TransStatus.SUCCESS_STR.equals(payment.getTransStatus())) {
                if (order.getRefundBalance().compareTo(payment.getTransAmount()) > 0) {
                    //部分退款
                    order.setRefundBalance(order.getRefundBalance().subtract(payment.getTransAmount()));
                    order.setOrdStatus(Const.OrderStatus.PARTIALLY_REFUNED_STR);
                } else {
                    //全额退款
                    order.setRefundBalance(order.getRefundBalance().subtract(payment.getTransAmount()));
                    order.setOrdStatus(Const.OrderStatus.REFUNED_STR);
                }
            } else if (Const.TransStatus.FAILED_STR.equals(payment.getTransStatus())) {
                if (status.equals("2")) {
                    order.setRefundBalance(order.getRefundBalance().add(payment.getTransAmount()));
                    order.setOrdStatus(Const.OrderStatus.PAYED_STR);
                }
                log.error("退货交易失败");
            } else if (Const.TransStatus.UNKNOWN_STR.equals(payment.getTransStatus())) {
                order.setRefundBalance(order.getRefundBalance().subtract(payment.getTransAmount()));
                order.setOrdStatus(Const.OrderStatus.UNKNOWN_STR);
            } else {
                log.error("[非法交易状态， status:" + payment.getTransStatus() + "]");
            }
            order.setUpdTime(new Date());
            orderService.updateOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 11域流水号生成器
     *
     * @return
     * @throws AppBizException
     */
    @Override
    public String generateFlowNo() throws AppBizException {
        String sequence = sequenceService.findSequence().toString();
        return sequence.substring(sequence.length() - 6, sequence.length());

    }

    /**
     * logNo生成器
     *
     * @return
     * @throws AppBizException
     */
    @Override
    public String generateLogNo() throws AppBizException {
        String sequence = sequenceService.findSequence().toString();
        String sequenstr = StringUtils.leftPad(sequence, 10, '0');
        Date now = new Date();
        String nowstr = DateUtils.format(now, "YYYYMMdd");
        return nowstr + sequenstr;
    }

    /**
     * 获取机构秘钥信息
     *
     * @param orgNo
     */
    @Override
    public UrmAgtfee queryByOrgNo(String orgNo) {
        UrmAgnt urmAgnt = null;
        try {
            urmAgnt = urmAgntService.findByOrgNo(orgNo);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.ORG_ERR, "机构信息有误,请联系客户经理");
        }
        UrmAgtfee urmAgfee = null;
        try {

            urmAgfee = urmAgtfeeService.findByAgtMercId(urmAgnt.getAgtMercId());
        } catch (Exception e) {
            throw new AppBizException(AppExCode.ORG_ERR, "机构信息有误,请联系客户经理");
        }
        return urmAgfee;
    }

    /**
     * 查询通道费率
     *
     * @param chnCode
     */
    @Override
    public ChannelInfo queryChannel(String chnCode) {
        ChannelInfo channelInfo = null;
        try {

            channelInfo = channelInfoService.findByChnCode(chnCode);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.CHN_ERR, "通道信息有误，请联系客户经理");
        }
        return channelInfo;
    }

    /**
     * 根据渠道号来获取渠道支持表
     *
     * @param chnCode
     * @return
     */
    @Override
    public ChannelScanSupport queryChannelSupport(String chnCode,String mercType) {
        ChannelScanSupport channelScanSupport = null;
        try {

            channelScanSupport = channelScanSupportService.findBychnCode(chnCode,mercType);
        } catch (Exception e) {
            throw new AppBizException(AppExCode.CHN_SUPPORT_ERR, "通道扫码信息有误，请联系客户经理");
        }
        return channelScanSupport;
    }
}
