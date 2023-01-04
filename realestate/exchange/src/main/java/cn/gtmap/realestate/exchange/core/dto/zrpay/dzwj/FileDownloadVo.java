package cn.gtmap.realestate.exchange.core.dto.zrpay.dzwj;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
 * @version 1.0  2022/08/10
 * @description 政融支付平台 对账文件下载入参
 */
public class FileDownloadVo {

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
     * 发起方时间戳 yyyymmddhhmmssfff
     */
    @JSONField(name = "IttParty_Tms")
    private String ittPartyTms;

    /**
     * 发起方流水号
     */
    @JSONField(name = "IttParty_Jrnl_No")
    private String ittPartyJrnlNo;

    /**
     * 对账日期 交易日期 20210429
     */
    @JSONField(name = "Rcncl_Dt")
    private String rcnclDt;

    /**
     * 批次号
     */
    @JSONField(name = "BtNo_cd")
    private String btNocd = "01";

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

    public String getRcnclDt() {
        return rcnclDt;
    }

    public void setRcnclDt(String rcnclDt) {
        this.rcnclDt = rcnclDt;
    }

    public String getBtNocd() {
        return btNocd;
    }

    public void setBtNocd(String btNocd) {
        this.btNocd = btNocd;
    }

    public String getSignInf() {
        return signInf;
    }

    public void setSignInf(String signInf) {
        this.signInf = signInf;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FileDownloadVo{");
        sb.append("VNo='").append(VNo).append('\'');
        sb.append(", sgnAlgr='").append(sgnAlgr).append('\'');
        sb.append(", ittPartyTms='").append(ittPartyTms).append('\'');
        sb.append(", ittPartyJrnlNo='").append(ittPartyJrnlNo).append('\'');
        sb.append(", rcnclDt='").append(rcnclDt).append('\'');
        sb.append(", btNocd='").append(btNocd).append('\'');
        sb.append(", signInf='").append(signInf).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
