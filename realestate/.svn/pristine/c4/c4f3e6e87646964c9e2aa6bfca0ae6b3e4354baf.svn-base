package cn.gtmap.realestate.exchange.core.dto.zrpay.tk;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022/10/13
 * @description 政融支付平台 退款申请请求参数
 */
public class ZrTksqRequest {

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
     * 客户方系统的流水号，用于记录唯一一笔退款流水，每次请求不允许重复
     */
    @JSONField(name = "IttParty_Jrnl_No")
    private String ittPartyJrnlNo;
    /**
     * 政融支付银行订单号
     */
    @JSONField(name = "Py_Ordr_No")
    private String pyOrdrNo;
    /**
     * 客户方商品订单号与银行支付订单号不能同时为空，任选其一
     */
    @JSONField(name = "CstPty_Cmdty_Ordr_No")
    private String cstPtyCmdtyOrdrNo;
    /**
     * 退款合计总金额
     */
    @JSONField(name = "Rfnd_Amt")
    private String rfndAmt;
    /**
     * 退款原因描述
     */
    @JSONField(name = "Rfnd_RDsc")
    private String rfndRdsc;
    /**
     * 子订单维度退款循环域
     */
    @JSONField(name = "LIST1")
    private List<ZrTksqReqZdd> zrTksqReqZddList;
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

    public String getRfndRdsc() {
        return rfndRdsc;
    }

    public void setRfndRdsc(String rfndRdsc) {
        this.rfndRdsc = rfndRdsc;
    }

    public List<ZrTksqReqZdd> getZrTksqZddList() {
        return zrTksqReqZddList;
    }

    public void setZrTksqZddList(List<ZrTksqReqZdd> zrTksqReqZddList) {
        this.zrTksqReqZddList = zrTksqReqZddList;
    }

    public String getSignInf() {
        return signInf;
    }

    public void setSignInf(String signInf) {
        this.signInf = signInf;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ZrTksqRequest{");
        sb.append("VNo='").append(VNo).append('\'');
        sb.append(", sgnAlgr='").append(sgnAlgr).append('\'');
        sb.append(", ittPartyJrnlNo='").append(ittPartyJrnlNo).append('\'');
        sb.append(", pyOrdrNo='").append(pyOrdrNo).append('\'');
        sb.append(", cstPtyCmdtyOrdrNo='").append(cstPtyCmdtyOrdrNo).append('\'');
        sb.append(", rfndAmt='").append(rfndAmt).append('\'');
        sb.append(", rfndRdsc='").append(rfndRdsc).append('\'');
        sb.append(", zrTksqZddList=").append(zrTksqReqZddList);
        sb.append(", signInf='").append(signInf).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
