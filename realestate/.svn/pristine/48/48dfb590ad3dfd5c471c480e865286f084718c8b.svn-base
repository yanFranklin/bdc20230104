package cn.gtmap.realestate.exchange.core.dto.zrpay.scdd;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
 * @version 1.0  2022/08/10
 * @description 政融支付平台 生成订单入参
 */
public class PayMentInVo {

    /**
     * 接口版本号  固定值2
     */
    @JSONField(name = "VNo")
    private String VNo = "2";

    /**
     * 签名算法  只支持SHA256withRSA
     */
    @JSONField(name = "Sgn_Algr")
    private String sgnAlgr = "SHA256withRSA";

    /**
     * 发起方渠道编号 即政融分配的多实体
     */
    @JSONField(name = "IttParty_Stm_ID")
    private String ittPartyStmId;

    /**
     * 发起方时间戳 yyyymmddhhmmssfff
     */
    @JSONField(name = "IttParty_Tms")
    private String ittPartyTms;

    /**
     * 发起方流水号  不允许重复
     */
    @JSONField(name = "IttParty_Jrnl_No")
    private String ittPartyJrnlNo;

    /**
     * 支付渠道代码
     */
    @JSONField(name = "Py_Chnl_Cd")
    private String pyChnlCd;

    /**
     * 请求支付类型	0-收银台  1-二维码串
     */
    @JSONField(name = "Rqs_Py_Tp")
    private String rqsPyTp;

    /**
     * appId  支付渠道为8时必输
     */
    @JSONField(name = "APPID")
    private String appId;

    /**
     * 用户标识  支付渠道为8时必输
     */
    @JSONField(name = "OpenID")
    private String openId;
    /**
     * 线上线下标志代码	1-线上支付 2-线下支付（POS、现金）
     */
    @JSONField(name = "OnLn_Ofln_IndCd")
    private String onlnOflnIndCd;

    /**
     * 商品订单号  客户方系统的订单号，可用来查询缴费结果
     */
    @JSONField(name = "Cmdty_Ordr_No")
    private String cmdtyOrdrNo;

    /**
     * 操作员号  线上线下标志代码 为 2-线下支付 时，操作编号必输
     */
    @JSONField(name = "Opr_No")
    private String oprNo;

    /**
     * 用户ID
     */
    @JSONField(name = "Usr_ID")
    private String usrId;

    /**
     * 客户名称
     */
    @JSONField(name = "Cst_Nm")
    private String cstNm;

    /**
     * 证件类型		1010-居民身份证
     */
    @JSONField(name = "Crdt_Tp")
    private String crdtTp;

    /**
     * 证件号码
     */
    @JSONField(name = "Crdt_No")
    private String crdtNo;

    /**
     * 手机号码
     */
    @JSONField(name = "MblPh_No")
    private String mblPhNo;

    /**
     * 电子邮箱
     */
    @JSONField(name = "Email")
    private String email;

    /**
     * 缴费总金额
     */
    @JSONField(name = "TAmt")
    private BigDecimal tAmt;

    /**
     * 币种   156-人民币
     */
    @JSONField(name = "Ccy")
    private String ccy = "156";

    /**
     * 页面返回URL地址  支付完成后会跳转到此地址
     */
    @JSONField(name = "PgFc_Ret_URL_Adr")
    private String pgFcRetUrlAdr;

    /**
     * 备注一
     */
    @JSONField(name = "Rmrk1")
    private String rmrk1;

    /**
     * 备注二
     */
    @JSONField(name = "Rmrk2")
    private String rmrk2;

    /**
     * 钱包个人二维码
     */
    @JSONField(name = "Wlt_Idr_TDCD")
    private String wltIdrTdCd;

    /**
     * 平台方钱包ID
     */
    @JSONField(name = "PltFrm_Wlt_ID")
    private String pltFrmWltId;

    /**
     * 一般事项缴费循环域 该域非空时参与验签
     */
    @JSONField(name = "FEEGRP")
    private List<FeeGrpVo> feeGrpVoList;

    /**
     * 税务缴费要素信息循环域，该域非空时参与验签
     */
    @JSONField(name = "TAXGRP")
    private List<TaxGrpVo> taxGrpVoList;

    /**
     * 签名信息
     */
    @JSONField(name = "SIGN_INF")
    private String signInf;


    public String getVNo() {
        return VNo;
    }

    public void setVNo(String VNo) {
        this.VNo = VNo;
    }

    public String getSgnAlgr() {
        return sgnAlgr;
    }

    public void setSgnAlgr(String sgnAlgr) {
        this.sgnAlgr = sgnAlgr;
    }

    public String getIttPartyStmId() {
        return ittPartyStmId;
    }

    public void setIttPartyStmId(String ittPartyStmId) {
        this.ittPartyStmId = ittPartyStmId;
    }

    public String getIttPartyTms() {
        return ittPartyTms;
    }

    public void setIttPartyTms(String ittPartyTms) {
        this.ittPartyTms = ittPartyTms;
    }

    public String getIttPartyJrnlNo() {
        return ittPartyJrnlNo;
    }

    public void setIttPartyJrnlNo(String ittPartyJrnlNo) {
        this.ittPartyJrnlNo = ittPartyJrnlNo;
    }

    public String getPyChnlCd() {
        return pyChnlCd;
    }

    public void setPyChnlCd(String pyChnlCd) {
        this.pyChnlCd = pyChnlCd;
    }

    public String getRqsPyTp() {
        return rqsPyTp;
    }

    public void setRqsPyTp(String rqsPyTp) {
        this.rqsPyTp = rqsPyTp;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOnlnOflnIndCd() {
        return onlnOflnIndCd;
    }

    public void setOnlnOflnIndCd(String onlnOflnIndCd) {
        this.onlnOflnIndCd = onlnOflnIndCd;
    }

    public String getCmdtyOrdrNo() {
        return cmdtyOrdrNo;
    }

    public void setCmdtyOrdrNo(String cmdtyOrdrNo) {
        this.cmdtyOrdrNo = cmdtyOrdrNo;
    }

    public String getOprNo() {
        return oprNo;
    }

    public void setOprNo(String oprNo) {
        this.oprNo = oprNo;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getCstNm() {
        return cstNm;
    }

    public void setCstNm(String cstNm) {
        this.cstNm = cstNm;
    }

    public String getCrdtTp() {
        return crdtTp;
    }

    public void setCrdtTp(String crdtTp) {
        this.crdtTp = crdtTp;
    }

    public String getCrdtNo() {
        return crdtNo;
    }

    public void setCrdtNo(String crdtNo) {
        this.crdtNo = crdtNo;
    }

    public String getMblPhNo() {
        return mblPhNo;
    }

    public void setMblPhNo(String mblPhNo) {
        this.mblPhNo = mblPhNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal gettAmt() {
        return tAmt;
    }

    public void settAmt(BigDecimal tAmt) {
        this.tAmt = tAmt;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getPgFcRetUrlAdr() {
        return pgFcRetUrlAdr;
    }

    public void setPgFcRetUrlAdr(String pgFcRetUrlAdr) {
        this.pgFcRetUrlAdr = pgFcRetUrlAdr;
    }

    public String getRmrk1() {
        return rmrk1;
    }

    public void setRmrk1(String rmrk1) {
        this.rmrk1 = rmrk1;
    }

    public String getRmrk2() {
        return rmrk2;
    }

    public void setRmrk2(String rmrk2) {
        this.rmrk2 = rmrk2;
    }

    public String getWltIdrTdCd() {
        return wltIdrTdCd;
    }

    public void setWltIdrTdCd(String wltIdrTdCd) {
        this.wltIdrTdCd = wltIdrTdCd;
    }

    public String getPltFrmWltId() {
        return pltFrmWltId;
    }

    public void setPltFrmWltId(String pltFrmWltId) {
        this.pltFrmWltId = pltFrmWltId;
    }

    public List<FeeGrpVo> getFeeGrpVoList() {
        return feeGrpVoList;
    }

    public void setFeeGrpVoList(List<FeeGrpVo> feeGrpVoList) {
        this.feeGrpVoList = feeGrpVoList;
    }

    public List<TaxGrpVo> getTaxGrpVoList() {
        return taxGrpVoList;
    }

    public void setTaxGrpVoList(List<TaxGrpVo> taxGrpVoList) {
        this.taxGrpVoList = taxGrpVoList;
    }

    public String getSignInf() {
        return signInf;
    }

    public void setSignInf(String signInf) {
        this.signInf = signInf;
    }

    @Override
    public String toString() {
        return "PayMentInVo{" +
                "VNo='" + VNo + '\'' +
                ", sgnAlgr='" + sgnAlgr + '\'' +
                ", ittPartyStmId='" + ittPartyStmId + '\'' +
                ", ittPartyTms='" + ittPartyTms + '\'' +
                ", ittPartyJrnlNo='" + ittPartyJrnlNo + '\'' +
                ", pyChnlCd='" + pyChnlCd + '\'' +
                ", rqsPyTp='" + rqsPyTp + '\'' +
                ", appId='" + appId + '\'' +
                ", openId='" + openId + '\'' +
                ", onlnOflnIndCd='" + onlnOflnIndCd + '\'' +
                ", cmdtyOrdrNo='" + cmdtyOrdrNo + '\'' +
                ", oprNo='" + oprNo + '\'' +
                ", usrId='" + usrId + '\'' +
                ", cstNm='" + cstNm + '\'' +
                ", crdtTp='" + crdtTp + '\'' +
                ", crdtNo='" + crdtNo + '\'' +
                ", mblPhNo='" + mblPhNo + '\'' +
                ", email='" + email + '\'' +
                ", tAmt=" + tAmt +
                ", ccy='" + ccy + '\'' +
                ", pgFcRetUrlAdr='" + pgFcRetUrlAdr + '\'' +
                ", rmrk1='" + rmrk1 + '\'' +
                ", rmrk2='" + rmrk2 + '\'' +
                ", wltIdrTdCd='" + wltIdrTdCd + '\'' +
                ", pltFrmWltId='" + pltFrmWltId + '\'' +
                ", feeGrpVoList=" + feeGrpVoList +
                ", taxGrpVoList=" + taxGrpVoList +
                ", signInf='" + signInf + '\'' +
                '}';
    }
}
