package cn.gtmap.realestate.exchange.core.dto.zrpay.tk;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022/10/13
 * @description 政融支付平台 退款结果查询请求值
 */
public class ZrTkjgcxRequest {

    /**
     * 接口版本号  固定值4
     */
    @JSONField(name = "VNo")
    private String VNo = "4";
    /**
     * 签名算法  只支持SHA256withRSA
     */
    @JSONField(name = "Sgn_Algr")
    private String sgnAlgr = "SHA256withRSA";
    /**
     * 客户方系统的退款流水号，退款申请时请求的流水号
     */
    @JSONField(name = "IttParty_Jrnl_No")
    private String ittPartyJrnlNo;
    /**
     * 退款申请时银行返回的退款流水号，与客户方流水号两者不能同时为空
     */
    @JSONField(name = "Rfnd_TrcNo")
    private String rfndTrcNo;
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

    public String getIttPartyJrnlNo() {
        return ittPartyJrnlNo;
    }

    public void setIttPartyJrnlNo(String ittPartyJrnlNo) {
        this.ittPartyJrnlNo = ittPartyJrnlNo;
    }

    public String getRfndTrcNo() {
        return rfndTrcNo;
    }

    public void setRfndTrcNo(String rfndTrcNo) {
        this.rfndTrcNo = rfndTrcNo;
    }

    public String getSignInf() {
        return signInf;
    }

    public void setSignInf(String signInf) {
        this.signInf = signInf;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ZrTkjgcxRequest{");
        sb.append("VNo='").append(VNo).append('\'');
        sb.append(", sgnAlgr='").append(sgnAlgr).append('\'');
        sb.append(", ittPartyJrnlNo='").append(ittPartyJrnlNo).append('\'');
        sb.append(", rfndTrcNo='").append(rfndTrcNo).append('\'');
        sb.append(", signInf='").append(signInf).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
