package cn.gtmap.realestate.exchange.core.dto.zrpay.tk;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022/10/13
 * @description 政融支付平台 退款结果查询返回值
 */
public class ZrTkjgcxResponse {

    /**
     * 客户方系统的退款流水号
     */
    @JSONField(name = "IttParty_Jrnl_No")
    private String ittPartyJrnlNo;
    /**
     * 退款申请时银行返回的退款流水号
     */
    @JSONField(name = "Rfnd_TrcNo")
    private String rfndTrcNo;
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
     * 退款合计总金额
     */
    @JSONField(name = "Rfnd_Amt")
    private String rfndAmt;
    /**
     * 向银行发起退款的时间，格式YYYYMMDDHHMMSS
     */
    @JSONField(name = "Rfnd_Tm")
    private String rfndTm;
    /**
     * 子订单退款明细循环域
     */
    @JSONField(name = "LIST1")
    private List<ZrTkjgcxRespZdd> zrTkjgcxRespZddList;
    /**
     * 签名信息
     */
    @JSONField(name = "SIGN_INF")
    private String signInf;

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

    public String getRfndAmt() {
        return rfndAmt;
    }

    public void setRfndAmt(String rfndAmt) {
        this.rfndAmt = rfndAmt;
    }

    public String getRfndTm() {
        return rfndTm;
    }

    public void setRfndTm(String rfndTm) {
        this.rfndTm = rfndTm;
    }

    public List<ZrTkjgcxRespZdd> getZrTkjgcxRespZddList() {
        return zrTkjgcxRespZddList;
    }

    public void setZrTkjgcxRespZddList(List<ZrTkjgcxRespZdd> zrTkjgcxRespZddList) {
        this.zrTkjgcxRespZddList = zrTkjgcxRespZddList;
    }

    public String getSignInf() {
        return signInf;
    }

    public void setSignInf(String signInf) {
        this.signInf = signInf;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ZrTkjgcxResponse{");
        sb.append("ittPartyJrnlNo='").append(ittPartyJrnlNo).append('\'');
        sb.append(", rfndTrcNo='").append(rfndTrcNo).append('\'');
        sb.append(", pyOrdrNo='").append(pyOrdrNo).append('\'');
        sb.append(", cstPtyCmdtyOrdrNo='").append(cstPtyCmdtyOrdrNo).append('\'');
        sb.append(", rfndAmt='").append(rfndAmt).append('\'');
        sb.append(", rfndTm='").append(rfndTm).append('\'');
        sb.append(", zrTkjgcxRespZddList=").append(zrTkjgcxRespZddList);
        sb.append(", signInf='").append(signInf).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
