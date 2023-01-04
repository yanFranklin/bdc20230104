package cn.gtmap.realestate.exchange.core.dto.zrpay.jfqr;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
 * @version 1.0  2022/08/10
 * @description 政融支付平台 pos缴费成功入参
 */
public class PosPaySuccessQO {

    /**
     * 接口版本号  固定值1
     */
    @JSONField(name = "VNo")
    private String VNo = "1";

    /**
     * 签名算法  只支持SHA256withRSA
     */
    @JSONField(name = "Sgn_Algr")
    private String sgnAlgr = "SHA256withRSA";

    /**
     * 发起方流水号
     */
    @JSONField(name = "IttParty_Jrnl_No")
    private String ittPartyJrnlNo;

    /**
     * 支付渠道代码
     */
    @JSONField(name = "Py_Ordr_No")
    private String pyOrdrNo;

    /**
     * 支付渠道代码, POS-7
     */
    @JSONField(name = "Py_Chnl_Cd")
    private String pyChnlCd = "7";

    /**
     * 支付方式代码
     */
    @JSONField(name = "PyMd_Cd")
    private String pyMdCd;

    /**
     * 总金额
     */
    @JSONField(name = "TAmt")
    private String tAmt;

    /**
     * 参考号
     */
    @JSONField(name = "Ref_No")
    private String refNo;

    /**
     * 商户代码
     */
    @JSONField(name = "MrchCd")
    private String mrchCd;

    /**
     * 终端号
     */
    @JSONField(name = "Tmnl_No")
    private String tmnlNo;
    /**
     * 付款方账号
     */
    @JSONField(name = "Pyr_AccNo")
    private String pyrAccNo;

    /**
     * 付款人名称
     */
    @JSONField(name = "Py_Psn_Nm")
    private String pyPsnNm;

    /**
     * 备注一
     */
    @JSONField(name = "Rmrk1")
    private String remark1;

    /**
     * 备注二
     */
    @JSONField(name = "Rmrk2")
    private String remark2;

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

    public String getIttPartyJrnlNo() {
        return ittPartyJrnlNo;
    }

    public void setIttPartyJrnlNo(String ittPartyJrnlNo) {
        this.ittPartyJrnlNo = ittPartyJrnlNo;
    }

    public String getPyOrdrNo() {
        return pyOrdrNo;
    }

    public void setPyOrdrNo(String pyOrdrNo) {
        this.pyOrdrNo = pyOrdrNo;
    }

    public String getPyChnlCd() {
        return pyChnlCd;
    }

    public void setPyChnlCd(String pyChnlCd) {
        this.pyChnlCd = pyChnlCd;
    }

    public String getPyMdCd() {
        return pyMdCd;
    }

    public void setPyMdCd(String pyMdCd) {
        this.pyMdCd = pyMdCd;
    }

    public String gettAmt() {
        return tAmt;
    }

    public void settAmt(String tAmt) {
        this.tAmt = tAmt;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getMrchCd() {
        return mrchCd;
    }

    public void setMrchCd(String mrchCd) {
        this.mrchCd = mrchCd;
    }

    public String getTmnlNo() {
        return tmnlNo;
    }

    public void setTmnlNo(String tmnlNo) {
        this.tmnlNo = tmnlNo;
    }

    public String getPyrAccNo() {
        return pyrAccNo;
    }

    public void setPyrAccNo(String pyrAccNo) {
        this.pyrAccNo = pyrAccNo;
    }

    public String getPyPsnNm() {
        return pyPsnNm;
    }

    public void setPyPsnNm(String pyPsnNm) {
        this.pyPsnNm = pyPsnNm;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    @Override
    public String toString() {
        return "PosPaySuccessQo{" +
                "VNo='" + VNo + '\'' +
                ", sgnAlgr='" + sgnAlgr + '\'' +
                ", ittPartyJrnlNo='" + ittPartyJrnlNo + '\'' +
                ", pyOrdrNo='" + pyOrdrNo + '\'' +
                ", pyChnlCd='" + pyChnlCd + '\'' +
                ", pyMdCd='" + pyMdCd + '\'' +
                ", tAmt='" + tAmt + '\'' +
                ", refNo='" + refNo + '\'' +
                ", mrchCd='" + mrchCd + '\'' +
                ", tmnlNo='" + tmnlNo + '\'' +
                ", pyrAccNo='" + pyrAccNo + '\'' +
                ", pyPsnNm='" + pyPsnNm + '\'' +
                ", remark1='" + remark1 + '\'' +
                ", remark2='" + remark2 + '\'' +
                '}';
    }
}
