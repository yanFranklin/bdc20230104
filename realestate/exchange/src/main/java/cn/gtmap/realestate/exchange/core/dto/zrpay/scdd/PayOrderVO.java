package cn.gtmap.realestate.exchange.core.dto.zrpay.scdd;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
 * @version 1.0  2022/08/10
 * @description 政融支付平台 支付订单返回
 */
public class PayOrderVO {

    /**
     * 客户订单号
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
    private String ordrGenTm;

    /**
     * 订单超时时间
     */
    @JSONField(name = "Ordr_Ovtm_Tm")
    private String orderOvtmTm;

    /**
     * url
     */
    @JSONField(name = "Py_URL")
    private String pyUrl;


    /**
     * 订单状态代码
     */
    @JSONField(name = "Ordr_StCd")
    private String orderStCd;

    /**
     * 金额
     */
    @JSONField(name = "TAmt")
    private String tAmt;

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

    public String getOrdrGenTm() {
        return ordrGenTm;
    }

    public void setOrdrGenTm(String ordrGenTm) {
        this.ordrGenTm = ordrGenTm;
    }

    public String getOrderOvtmTm() {
        return orderOvtmTm;
    }

    public void setOrderOvtmTm(String orderOvtmTm) {
        this.orderOvtmTm = orderOvtmTm;
    }

    public String getPyUrl() {
        return pyUrl;
    }

    public void setPyUrl(String pyUrl) {
        this.pyUrl = pyUrl;
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

    @Override
    public String toString() {
        return "PayOrderVO{" +
                "cmdtyOrdrNo='" + cmdtyOrdrNo + '\'' +
                ", pyOrderNo='" + pyOrderNo + '\'' +
                ", ordrGenTm='" + ordrGenTm + '\'' +
                ", orderOvtmTm='" + orderOvtmTm + '\'' +
                ", pyUrl='" + pyUrl + '\'' +
                ", orderStCd='" + orderStCd + '\'' +
                ", tAmt='" + tAmt + '\'' +
                '}';
    }
}
