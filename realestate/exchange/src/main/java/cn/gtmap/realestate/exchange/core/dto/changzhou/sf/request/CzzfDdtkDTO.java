package cn.gtmap.realestate.exchange.core.dto.changzhou.sf.request;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-09-14
 * @description 常州订单退款实体
 */
public class CzzfDdtkDTO {

    private String service;
    private String outTradeNo;
    private String orderNo;
    private String outRefundNo;
    private String refundAmt;
    private String refundReason;
    private String tradeType;
    private String deviceNo;
    private String mchIp;
    private String extfld1;
    private String extfld2;
    private String extfld3;

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public String getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(String refundAmt) {
        this.refundAmt = refundAmt;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getExtfld1() {
        return extfld1;
    }

    public void setExtfld1(String extfld1) {
        this.extfld1 = extfld1;
    }

    public String getExtfld2() {
        return extfld2;
    }

    public void setExtfld2(String extfld2) {
        this.extfld2 = extfld2;
    }

    public String getExtfld3() {
        return extfld3;
    }

    public void setExtfld3(String extfld3) {
        this.extfld3 = extfld3;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getMchIp() {
        return mchIp;
    }

    public void setMchIp(String mchIp) {
        this.mchIp = mchIp;
    }
}
