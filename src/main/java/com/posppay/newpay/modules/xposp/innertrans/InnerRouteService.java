package com.posppay.newpay.modules.xposp.innertrans;

import com.posppay.newpay.common.exception.AppBizException;
import com.posppay.newpay.modules.xposp.entity.*;
import com.posppay.newpay.modules.xposp.transfer.model.req.ConsumeRequest;
import com.posppay.newpay.modules.xposp.transfer.model.req.QrcodeRequest;
import com.posppay.newpay.modules.xposp.transfer.model.req.RefundRequest;

import java.math.BigDecimal;
import java.util.List;

public interface InnerRouteService {
    /**
     * 根据系统商户号来获取转换后的机构商户号
     */
    UrmMmap queryChnMrch(String mrcId);

    /**
     * 根据系统终端号来获取转换后的机构终端号
     */
    UrmTprd queryChnTrmnl(String trmnlNo);

    /**
     * 根据门店号获取门店信息
     */
    UrmStoe queryStore(String mercId, String urmStoreNo)throws AppBizException;
    /**
     * 更新机构工作秘钥
     *
     * @param cmmInf
     */
    void updateByMrchAndTrmnl(CmmInf cmmInf);

    /**
     * 根据终端号获取终端门店信息
     *
     * @param snNo
     * @return
     */
    TmsPos queryTrmnl(String snNo) throws AppBizException;

    /**
     * 根据商户号门店号获取终端
     * @param mercId
     * @param stoeId
     * @return
     * @throws AppBizException
     */
    TmsPos queryTrmnlBymerc(String mercId,String stoeId) throws AppBizException;
    /**
     * 功能： 根据门店号和商户号跟产品编号来获取产品信息
     *
     * @param mercId
     * @param storeId
     * @return
     * @throws AppBizException
     */
    UrmPrdt queryScanProdt(String mercId, String storeId) throws AppBizException;
    /**
     * 功能： 跟库商户号和门店号和产品编号来获取结算信息
     *
     * @param mercId
     * @param stoeId
     * @return
     * @throws AppBizException
     */
    UrmMstl queryScanMstl(String mercId, String stoeId) throws AppBizException;


    /**
     * 功能： 根据商户号来获取系统商户信息
     *
     * @param mercId
     * @return
     * @throws AppBizException
     */
    UrmMinf queryUrmMinf(String mercId) throws AppBizException;
    /**
     * 根据地区编码查找转换后的平安地址编号
     */
    CupArea queryPaAreaByCode(String areaCode);

    /**
     * 根据省份编号和城市编号来获取平安机构秘钥
     *
     * @param provCode
     * @param cityCode
     * @return
     */
    OrganizationRoute queryOrganiByareaCode(String provCode, String cityCode);
    /**
     * 根据省份编号和城市编号来获取平安机构秘钥
     *
     * @param provCode
     * @return
     */
    OrganizationRoute queryOrganiByProvinCode(String provCode);
    /**
     * 获取机构工作秘钥信息
     *
     * @return
     */
    CmmInf queryBymrchAndTrmNo();

    /**
     * 新增扫码消费渠道流水
     *
     * @param urmStoe
     * @param request
     * @throws AppBizException
     */
    public HpsJnl addScanPayHpsFlow(UrmStoe urmStoe, UrmMstl urmMstl, UrmMinf urmMinf, ConsumeRequest request) throws AppBizException;
    /**
     * 新增扫码交易平台流水
     *
     * @param payment
     * @return
     * @throws AppBizException
     */

    public Payment addPaymet(Payment payment) throws AppBizException;

    /**
     * 新增扫码交易渠道流水
     *
     * @param qrChnFlowPa
     * @return
     * @throws AppBizException
     */

    public QrChnFlowPa addChnFlow(QrChnFlowPa qrChnFlowPa) throws AppBizException;
    /**
     * 新增扫码交易渠道流水
     *
     * @param qrChnFlowGt
     * @return
     * @throws AppBizException
     */

    public QrChnFlowGt addChnFlow(QrChnFlowGt qrChnFlowGt) throws AppBizException;
    /**
     * 更新扫码渠道流水
     *
     * @param qrChnFlowPa
     * @return
     * @throws AppBizException
     */
    public QrChnFlowPa updateChnFlow(QrChnFlowPa qrChnFlowPa) throws AppBizException;
    /**
     * 更新扫码渠道流水
     *
     * @param qrChnFlowGt
     * @return
     * @throws AppBizException
     */
    public QrChnFlowGt updateChnFlow(QrChnFlowGt qrChnFlowGt) throws AppBizException;

    /**
     * 新增扫码退货渠道流水
     *
     * @param urmStoe
     * @param urmMstl
     * @param urmMinf
     * @param request
     * @return
     * @throws AppBizException
     */
    public HpsJnl addScanRefundHpsFlow(UrmStoe urmStoe, UrmMstl urmMstl, UrmMinf urmMinf, RefundRequest request) throws AppBizException;
    /**
     * C扫B生成二维码预订单流水生成
     *
     * @param urmStoe
     * @param request
     * @throws AppBizException
     */
    public HpsJnl addQrCodeHpsFlow(UrmStoe urmStoe, UrmMstl urmMstl, UrmMinf urmMinf, QrcodeRequest request) throws AppBizException;


    /**
     * 更新交易流水
     *
     * @param hpsJnl
     * @return
     * @throws AppBizException
     */
    public HpsJnl updateHpsFlow(HpsJnl hpsJnl) throws AppBizException;
    /**
     * 根据商户号门店号获取费率并计算费率金额
     *
     * @param mercId
     * @param stoeId
     * @param prdId
     * @param amout
     * @return
     * @throws AppBizException
     */
    public BigDecimal calculateFee(String mercId, String stoeId, String prdId, BigDecimal amout, String payChannel) throws AppBizException;
    /**
     * 根据商户号门店号获取费率并计算费率金额
     * @param rate
     * @param amt
     * @return
     * @throws AppBizException
     */
    public BigDecimal calculateRefundFee(BigDecimal rate, BigDecimal amt) throws AppBizException;

    /**
     * 根据商户id来获取公共属性
     */
    UrmComi queryByMercId(String mercId) throws AppBizException;

    /**
     * 根据系统商户号和门店号来获取真实的商户
     */
    CupMerchant queryByMerIdAndStoreNo(String mercId, String storeNo) throws AppBizException;
    /**
     * 根据系统商户号来获取真实的商户
     */
    PaMercinfo queryPaByMerId(String mercId) throws AppBizException;

    /**
     * 根据门店号 产品编号商户号获取产品权限
     */
    List<PrdtPerm> queeyPrdtPermisson(String mercId, String storeId, String prdId) throws AppBizException;
    /**
     * 根据logNo来查找单笔流水
     *
     * @param logNo
     * @return
     */
    HpsJnl querySingleFlow(String logNo);

    /**
     * 根据payNo和orgNo来查找单笔流水
     *
     * @param payNo
     * @return
     */
    QrChnFlowPa querySingleFlow(String payNo, String orgNo,String transDate);
    /**
     * 根据payNo和orgNo来查找单笔流水
     *
     * @param payNo
     * @return
     */
    QrChnFlowGt querySingleGtFlow(String payNo, String orgNo,String transDate);
    /**
     * 根据c端流水号来查询流水
     *
     * @param cseqNo
     * @return
     */
    HpsJnl querySingleFlowByCseqNo(String cseqNo);

    /**
     * 根据下游订单号和机构号来获取单笔平台流水
     *
     * @param outOrderNo
     * @param orgNo
     * @return
     */
    Payment querySinglePayment(String outOrderNo, String orgNo, String transDate);
    /**
     * 根据下游订单号和机构号来获取单笔平台流水
     *
     * @param payNo
     * @param orgNo
     * @return
     */
    Payment querySinglePaymentByPayNo(String payNo, String orgNo, String transDate);
    /**
     * 根据第三方凭证号来获取流水
     *
     * @param outTransactionId
     * @param orgNo
     * @return
     */
    Payment queryPaymentByTransationId(String outTransactionId, String orgNo,String transType,String oriTransDate);

    /**
     * 根据订单号和机构号来获取单笔平台流水
     *
     * @param ordNo
     * @param orgNo
     * @return
     */
    Order querySingleOrder(String ordNo, String orgNo, String transDate);

    /**
     * 新增订单流水
     *
     * @param order
     * @return
     */
    Order addOrder(Order order) throws AppBizException;



    /**
     * 流水号生成器
     */
    String generateFlowNo() throws AppBizException;

    /**
     * logNo生成器
     */
    String generateLogNo() throws AppBizException;

    /**
     * 获取机构秘钥信息
     */
    UrmAgtfee queryByOrgNo(String orgNo);
    /**
     * 查询通道费率
     */
    ChannelInfo queryChannel(String chnCode);

    /**
     * 根据渠道号来获取渠道支持表
     * @param chnCode
     * @return
     */
    ChannelScanSupport queryChannelSupport(String chnCode,String mercType);
}
