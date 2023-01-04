package cn.gtmap.realestate.exchange.core.dto.zrpay.jfqr;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
 * @version 1.0  2022/08/10
 * @description 政融支付平台 支付结果查询返回
 */
public class PayResultDTO {

    /**
     * 商品订单号
     */
    @JSONField(name = "Cmdty_Ordr_No")
    private String cmdtyOrdrNo;

    /**
     * 支付订单号
     */
    @JSONField(name = "Py_Ordr_No")
    private String pyOrderNo;

    /**
     * 订单生成时间
     */
    @JSONField(name = "Ordr_Gen_Tm")
    private String orderGenTm;

    /**
     * 订单超时时间
     */
    @JSONField(name = "Ordr_Ovtm_Tm")
    private String ordrOvtmTm;

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
     * 订单状态代码
     * 1-待缴费
     * 2-成功
     * 3-失败
     * 4-全部退费
     * 5-部分退费
     * 6-失效
     * 9-取消
     * a-处理中
     * b-待冲正
     * c-待系统退款
     * d-已落地
     */
    @JSONField(name = "Ordr_StCd")
    private String orderStCd;

    @JSONField(name = "TAmt")
    private String tAmt;

    @JSONField(name = "Py_URL")
    private String pyUrl;

    @JSONField(name = "Py_Scss_Tm")
    private String pyScssTm;

    @JSONField(name = "Err_Pcsg_Inf")
    private String errPcsgInf;

    @JSONField(name = "UDF_Inf_Dsc")
    private String udfInfDsc;

    @JSONField(name = "LIST1")
    private List<PayConfirmItemDTO> list;

    public String getPyUrl() {
        return pyUrl;
    }

    public void setPyUrl(String pyUrl) {
        this.pyUrl = pyUrl;
    }

    public String getPyScssTm() {
        return pyScssTm;
    }

    public void setPyScssTm(String pyScssTm) {
        this.pyScssTm = pyScssTm;
    }

    public String getErrPcsgInf() {
        return errPcsgInf;
    }

    public void setErrPcsgInf(String errPcsgInf) {
        this.errPcsgInf = errPcsgInf;
    }

    public String getUdfInfDsc() {
        return udfInfDsc;
    }

    public void setUdfInfDsc(String udfInfDsc) {
        this.udfInfDsc = udfInfDsc;
    }

    public String getCmdtyOrdrNo() {
        return cmdtyOrdrNo;
    }

    public void setCmdtyOrdrNo(String cmdtyOrdrNo) {
        this.cmdtyOrdrNo = cmdtyOrdrNo;
    }

    public String getPyOrderNo() {
        return pyOrderNo;
    }

    public void setPyOrderNo(String pyOrderNo) {
        this.pyOrderNo = pyOrderNo;
    }

    public String getOrderGenTm() {
        return orderGenTm;
    }

    public void setOrderGenTm(String orderGenTm) {
        this.orderGenTm = orderGenTm;
    }

    public String getOrdrOvtmTm() {
        return ordrOvtmTm;
    }

    public void setOrdrOvtmTm(String ordrOvtmTm) {
        this.ordrOvtmTm = ordrOvtmTm;
    }

    public String getPyStCd() {
        return pyStCd;
    }

    public void setPyStCd(String pyStCd) {
        this.pyStCd = pyStCd;
    }

    public String getOrderStCd() {
        return orderStCd;
    }

    public void setOrderStCd(String orderStCd) {
        this.orderStCd = orderStCd;
    }

    public String gettAmt() {
        return tAmt;
    }

    public void settAmt(String tAmt) {
        this.tAmt = tAmt;
    }

    public List<PayConfirmItemDTO> getList() {
        return list;
    }

    public void setList(List<PayConfirmItemDTO> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PayConfirmDTO{" +
                "cmdtyOrdrNo='" + cmdtyOrdrNo + '\'' +
                ", pyOrderNo='" + pyOrderNo + '\'' +
                ", orderGenTm='" + orderGenTm + '\'' +
                ", ordrOvtmTm='" + ordrOvtmTm + '\'' +
                ", pyStCd='" + pyStCd + '\'' +
                ", orderStCd='" + orderStCd + '\'' +
                ", tAmt='" + tAmt + '\'' +
                ", list=" + list +
                '}';
    }
}
