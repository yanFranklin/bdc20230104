package cn.gtmap.realestate.exchange.core.dto.zrpay.tk;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022/10/13
 * @description 政融支付平台 退款申请请求值子订单
 */
public class ZrTksqReqZdd {

    /**
     * 费项的顺序号，从1开始顺序增加
     */
    @JSONField(name = "SN")
    private String sn;
    /**
     * 客户方商品订单号与银行支付订单号不能同时为空，任选其一
     */
    @JSONField(name = "CstPty_Cmdty_Sub_Ordr_No")
    private String cstPtyCmdtySubOrdrNo;
    /**
     * 政融支付子订单号
     */
    @JSONField(name = "Py_Sub_Ordr_No")
    private String pySubOrdrNo;
    /**
     * 子订单退款明细金额
     */
    @JSONField(name = "Sub_Ordr_Rfnd_Amt")
    private String subOrdrRfndAmt;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

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

    public String getSubOrdrRfndAmt() {
        return subOrdrRfndAmt;
    }

    public void setSubOrdrRfndAmt(String subOrdrRfndAmt) {
        this.subOrdrRfndAmt = subOrdrRfndAmt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ZrTksqZdd{");
        sb.append("sn='").append(sn).append('\'');
        sb.append(", cstPtyCmdtySubOrdrNo='").append(cstPtyCmdtySubOrdrNo).append('\'');
        sb.append(", pySubOrdrNo='").append(pySubOrdrNo).append('\'');
        sb.append(", subOrdrRfndAmt='").append(subOrdrRfndAmt).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
