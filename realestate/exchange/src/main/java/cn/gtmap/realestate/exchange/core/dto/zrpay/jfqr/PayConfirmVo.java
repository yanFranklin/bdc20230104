package cn.gtmap.realestate.exchange.core.dto.zrpay.jfqr;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
 * @version 1.0  2022/08/10
 * @description 政融支付平台 支付确认入参
 */
public class PayConfirmVo {

    /**
     * 接口版本号  固定值3
     */
    @JSONField(name = "VNo")
    private String VNo = "3";

    /**
     * 签名算法  只支持SHA256withRSA
     */
    @JSONField(name = "Sgn_Algr")
    private String sgnAlgr = "SHA256withRSA";

    /**
     * 发起方时间戳
     */
    @JSONField(name = "IttParty_Tms")
    private String ittPartyTms;

    /**
     * 商品订单号
     */
    @JSONField(name = "Cmdty_Ordr_No")
    private String cmstyOrdrNo;

    /**
     * 支付订单号
     */
    @JSONField(name = "Py_Ordr_No")
    private String pyOrdeNo;

    /**
     * 商品子订单号
     */
    @JSONField(name = "Cmdty_Sub_Ordr_No")
    private String cmdtySubOrdrNo;

    /**
     * 支付子订单号
     */
    @JSONField(name = "Py_Sub_Ordr_No")
    private String pySubOrdrNo;



    public String getIttPartyTms() {
        return ittPartyTms;
    }

    public void setIttPartyTms(String ittPartyTms) {
        this.ittPartyTms = ittPartyTms;
    }

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

    public String getCmstyOrdrNo() {
        return cmstyOrdrNo;
    }

    public void setCmstyOrdrNo(String cmstyOrdrNo) {
        this.cmstyOrdrNo = cmstyOrdrNo;
    }

    public String getPyOrdeNo() {
        return pyOrdeNo;
    }

    public void setPyOrdeNo(String pyOrdeNo) {
        this.pyOrdeNo = pyOrdeNo;
    }

    public String getCmdtySubOrdrNo() {
        return cmdtySubOrdrNo;
    }

    public void setCmdtySubOrdrNo(String cmdtySubOrdrNo) {
        this.cmdtySubOrdrNo = cmdtySubOrdrNo;
    }

    public String getPySubOrdrNo() {
        return pySubOrdrNo;
    }

    public void setPySubOrdrNo(String pySubOrdrNo) {
        this.pySubOrdrNo = pySubOrdrNo;
    }

    @Override
    public String toString() {
        return "PayConfirmVo{" +
                "VNo='" + VNo + '\'' +
                ", sgnAlgr='" + sgnAlgr + '\'' +
                ", cmstyOrdrNo='" + cmstyOrdrNo + '\'' +
                ", pyOrdeNo='" + pyOrdeNo + '\'' +
                ", cmdtySubOrdrNo='" + cmdtySubOrdrNo + '\'' +
                ", pySubOrdrNo='" + pySubOrdrNo + '\'' +
                '}';
    }
}
