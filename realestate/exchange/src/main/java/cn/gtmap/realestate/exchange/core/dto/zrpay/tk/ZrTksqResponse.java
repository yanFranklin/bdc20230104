package cn.gtmap.realestate.exchange.core.dto.zrpay.tk;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022/10/13
 * @description 政融支付平台 退款申请请求返回值
 */
public class ZrTksqResponse {

    /**
     * 银行退款流水号
     */
    @JSONField(name = "Rfnd_TrcNo")
    private String rfndTrcNo;
    /**
     * 退款申请受理时间，格式YYYYMMDDHHMMSS
     */
    @JSONField(name = "Rfnd_Acpt_Tm")
    private String rfndAcptTm;
    /**
     * 政融支付银行订单号
     */
    @JSONField(name = "Py_Ordr_No")
    private String pyOrdrNo;
    /**
     * 客户方商品订单号
     */
    @JSONField(name = "CstPty_Cmdty_Ordr_No")
    private String cstPtyCmdtyOrdrNo;
    /**
     * 1-待退款 2-处理中   3-退款失败  4-退款成功 5-待查询
     */
    @JSONField(name = "Rfnd_StCd")
    private String rfndStCd;
    /**
     * 退款合计总金额
     */
    @JSONField(name = "Rfnd_Amt")
    private String rfndAmt;
    /**
     * 该域非空时参与验签
     */
    @JSONField(name = "LIST")
    private List<ZrTksqRespZdd> zrTksqRespZddList;
    /**
     * 签名信息
     */
    @JSONField(name = "SIGN_INF")
    private String signInf;

    public String getRfndTrcNo() {
        return rfndTrcNo;
    }

    public void setRfndTrcNo(String rfndTrcNo) {
        this.rfndTrcNo = rfndTrcNo;
    }

    public String getRfndAcptTm() {
        return rfndAcptTm;
    }

    public void setRfndAcptTm(String rfndAcptTm) {
        this.rfndAcptTm = rfndAcptTm;
    }

    public String getPyOrdrNo() {
        return pyOrdrNo;
    }

    public void setPyOrdrNo(String pyOrdrNo) {
        this.pyOrdrNo = pyOrdrNo;
    }

    public String getCstPtyCmdtyOrdrNo() {
        return cstPtyCmdtyOrdrNo;
    }

    public void setCstPtyCmdtyOrdrNo(String cstPtyCmdtyOrdrNo) {
        this.cstPtyCmdtyOrdrNo = cstPtyCmdtyOrdrNo;
    }

    public String getRfndStCd() {
        return rfndStCd;
    }

    public void setRfndStCd(String rfndStCd) {
        this.rfndStCd = rfndStCd;
    }

    public String getRfndAmt() {
        return rfndAmt;
    }

    public void setRfndAmt(String rfndAmt) {
        this.rfndAmt = rfndAmt;
    }

    public List<ZrTksqRespZdd> getZrTksqRespZddList() {
        return zrTksqRespZddList;
    }

    public void setZrTksqRespZddList(List<ZrTksqRespZdd> zrTksqRespZddList) {
        this.zrTksqRespZddList = zrTksqRespZddList;
    }

    public String getSignInf() {
        return signInf;
    }

    public void setSignInf(String signInf) {
        this.signInf = signInf;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ZrTksqResponse{");
        sb.append("rfndTrcNo='").append(rfndTrcNo).append('\'');
        sb.append(", rfndAcptTm='").append(rfndAcptTm).append('\'');
        sb.append(", pyOrdrNo='").append(pyOrdrNo).append('\'');
        sb.append(", cstPtyCmdtyOrdrNo='").append(cstPtyCmdtyOrdrNo).append('\'');
        sb.append(", rfndStCd='").append(rfndStCd).append('\'');
        sb.append(", rfndAmt='").append(rfndAmt).append('\'');
        sb.append(", zrTksqRespZddList=").append(zrTksqRespZddList);
        sb.append(", signInf='").append(signInf).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
