package cn.gtmap.realestate.exchange.core.dto.zrpay.tk;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022/10/13
 * @description 政融支付平台 退款申请返回值子订单
 */
public class ZrTksqRespZdd {

    /**
     * 客户方商品子订单号
     */
    @JSONField(name = "CstPty_Cmdty_Sub_Ordr_No")
    private String cstPtyCmdtySubOrdrNo;
    /**
     * 政融支付子订单号
     */
    @JSONField(name = "Py_Sub_Ordr_No")
    private String pySubOrdrNo;
    /**
     * 1-待退款 2-处理中   3-退款失败  4-退款成功 5-待查询
     */
    @JSONField(name = "Sub_Ordr_Rfnd_StCd")
    private String subOrdrRfndStCd;
    /**
     * 子订单退款金额
     */
    @JSONField(name = "Sub_Ordr_Rfnd_Amt")
    private String subOrdrRfndAmt;

    public String getCstPtyCmdtySubOrdrNo() {
        return cstPtyCmdtySubOrdrNo;
    }

    public void setCstPtyCmdtySubOrdrNo(String cstPtyCmdtySubOrdrNo) {
        this.cstPtyCmdtySubOrdrNo = cstPtyCmdtySubOrdrNo;
    }

    public String getPySubOrdrNo() {
        return pySubOrdrNo;
    }

    public void setPySubOrdrNo(String pySubOrdrNo) {
        this.pySubOrdrNo = pySubOrdrNo;
    }

    public String getSubOrdrRfndStCd() {
        return subOrdrRfndStCd;
    }

    public void setSubOrdrRfndStCd(String subOrdrRfndStCd) {
        this.subOrdrRfndStCd = subOrdrRfndStCd;
    }

    public String getSubOrdrRfndAmt() {
        return subOrdrRfndAmt;
    }

    public void setSubOrdrRfndAmt(String subOrdrRfndAmt) {
        this.subOrdrRfndAmt = subOrdrRfndAmt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ZrTksqRespZdd{");
        sb.append("cstPtyCmdtySubOrdrNo='").append(cstPtyCmdtySubOrdrNo).append('\'');
        sb.append(", pySubOrdrNo='").append(pySubOrdrNo).append('\'');
        sb.append(", subOrdrRfndStCd='").append(subOrdrRfndStCd).append('\'');
        sb.append(", subOrdrRfndAmt='").append(subOrdrRfndAmt).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
