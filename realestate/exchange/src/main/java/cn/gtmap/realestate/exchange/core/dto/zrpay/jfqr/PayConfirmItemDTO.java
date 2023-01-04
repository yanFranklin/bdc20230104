package cn.gtmap.realestate.exchange.core.dto.zrpay.jfqr;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
 * @version 1.0  2022/08/10
 * @description 政融支付平台 支付确认返回
 */
public class PayConfirmItemDTO {

    /**
     * 序号
     */
    @JSONField(name = "SN")
    private String sn;

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

    /**
     * 订单状态代码
     */
    @JSONField(name = "Sub_Ordr_StCd")
    private String subOrdrStCd;

    /**
     * 费项代码
     */
    @JSONField(name = "Fee_Itm_Cd")
    private String feeItmCd;

    /**
     * 收款单位代码
     */
    @JSONField(name = "RvPyUnt_Cd")
    private String rvPyUntCd;

    /**
     * 费项项目金额
     */
    @JSONField(name = "Fee_Itm_Prj_Amt")
    private String feeItmPrjAmt;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
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

    public String getSubOrdrStCd() {
        return subOrdrStCd;
    }

    public void setSubOrdrStCd(String subOrdrStCd) {
        this.subOrdrStCd = subOrdrStCd;
    }

    public String getFeeItmCd() {
        return feeItmCd;
    }

    public void setFeeItmCd(String feeItmCd) {
        this.feeItmCd = feeItmCd;
    }

    public String getRvPyUntCd() {
        return rvPyUntCd;
    }

    public void setRvPyUntCd(String rvPyUntCd) {
        this.rvPyUntCd = rvPyUntCd;
    }

    public String getFeeItmPrjAmt() {
        return feeItmPrjAmt;
    }

    public void setFeeItmPrjAmt(String feeItmPrjAmt) {
        this.feeItmPrjAmt = feeItmPrjAmt;
    }

    @Override
    public String toString() {
        return "PayConfirmItemDTO{" +
                "sn=" + sn +
                ", cmdtySubOrdrNo='" + cmdtySubOrdrNo + '\'' +
                ", pySubOrdrNo='" + pySubOrdrNo + '\'' +
                ", subOrdrStCd='" + subOrdrStCd + '\'' +
                ", feeItmCd='" + feeItmCd + '\'' +
                ", rvPyUntCd='" + rvPyUntCd + '\'' +
                ", feeItmPrjAmt='" + feeItmPrjAmt + '\'' +
                '}';
    }
}
