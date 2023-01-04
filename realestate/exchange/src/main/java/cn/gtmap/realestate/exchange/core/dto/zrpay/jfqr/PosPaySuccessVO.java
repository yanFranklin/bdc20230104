package cn.gtmap.realestate.exchange.core.dto.zrpay.jfqr;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
 * @version 1.0  2022/08/10
 * @description 政融支付平台 pos缴费成功返回
 */
public class PosPaySuccessVO {

    /**
     * 发起方流水号
     */
    @JSONField(name = "IttParty_Jrnl_No")
    private String ittPartyJrnlNo;

    /**
     * 支付订单号
     */
    @JSONField(name = "Py_Ordr_No")
    private String pyOrdrNo;

    /**
     * 处理结果代码
     * 1-	成功
     * 0-	失败
     */
    @JSONField(name = "PsRlt_Cd")
    private String psRltCd;

    /**
     * 错误信息
     */
    @JSONField(name = "Err_Pcsg_Inf")
    private String errPcsgInf;

    /**
     * 支付状态代码
     * 1待支付
     * 2成功
     * 3失败
     * 4不确定
     */
    @JSONField(name = "Py_StCd")
    private String pyStCd;

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

    public String getPsRltCd() {
        return psRltCd;
    }

    public void setPsRltCd(String psRltCd) {
        this.psRltCd = psRltCd;
    }

    public String getErrPcsgInf() {
        return errPcsgInf;
    }

    public void setErrPcsgInf(String errPcsgInf) {
        this.errPcsgInf = errPcsgInf;
    }

    public String getPyStCd() {
        return pyStCd;
    }

    public void setPyStCd(String pyStCd) {
        this.pyStCd = pyStCd;
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
        return "PosPaySuccessVO{" +
                "ittPartyJrnlNo='" + ittPartyJrnlNo + '\'' +
                ", pyOrdrNo='" + pyOrdrNo + '\'' +
                ", psRltCd='" + psRltCd + '\'' +
                ", errPcsgInf='" + errPcsgInf + '\'' +
                ", pyStCd='" + pyStCd + '\'' +
                ", remark1='" + remark1 + '\'' +
                ", remark2='" + remark2 + '\'' +
                '}';
    }
}
