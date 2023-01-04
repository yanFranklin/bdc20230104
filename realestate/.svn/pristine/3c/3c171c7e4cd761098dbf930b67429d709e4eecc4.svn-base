package cn.gtmap.realestate.exchange.core.dto.hefei.ems.ddjr.request;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-17
 * @description
 */
public class EmsDdjrRequestOrderNormal {

    // 物流运单号，解决实物返单中正向4联单、反向热敏单的情况
    private String mailNo;

    //产品代码，0-经济快递 1-标准快递 3-E标准
    private String serviceType;

    // 订单类型(1-普通订单)
    private String orderType;

    // 电商订单号
    private String orderNo;

    // 实际重量，单位：克
    private String weight;

    // 备注
    private String remark;

    // 体积重量，单位：克
    private String volumeWeight;

    //计费重量，单位：克
    private String feeWeight;

    // 保险金额，单位：分
    private String insuredAmount;

    // 保价金额，单位：分
    private String valuationAmount;

    // 收件人付费
    private String receiverPay;

    // 代收货款
    private String collectionMoney;

    // 邮费
    private String postage;

    // 商品金额
    private String commodityMoney;

    // 自定义标志
    private String state;

    //揽收地址信息（上门取件地址）
    private EmsDdjrRequestAddress collect;


    // 投递时间(yyyy-mm-dd hh:mm:ss)
    private String deliveryTime;

    // 收件人信息
    private EmsDdjrRequestAddress receiver;

    // 是否返单，0：返单，1：不返单
    private String revertBill;

    // 寄件人信息
    private EmsDdjrRequestAddress sender;

    // 反向运单号
    //revertBill传值为0，并且
    //revertMailNo 传值为gotMail时，认为是实物返单，会自动绑定反向运单号
    private String revertMailNo;

    // 物流订单号
    @NotBlank(message = "物流订单号不能为空")
    private String txLogisticID;

    //商品信息
    private List<EmsDdjrRequestItem> items;

    // 一票多件信息
    private List<EmsDdjrRequestSubOrderNormal> subOrders;

    // EMS客户代码
    private String custCode;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVolumeWeight() {
        return volumeWeight;
    }

    public void setVolumeWeight(String volumeWeight) {
        this.volumeWeight = volumeWeight;
    }

    public String getFeeWeight() {
        return feeWeight;
    }

    public void setFeeWeight(String feeWeight) {
        this.feeWeight = feeWeight;
    }

    public String getInsuredAmount() {
        return insuredAmount;
    }

    public void setInsuredAmount(String insuredAmount) {
        this.insuredAmount = insuredAmount;
    }

    public String getValuationAmount() {
        return valuationAmount;
    }

    public void setValuationAmount(String valuationAmount) {
        this.valuationAmount = valuationAmount;
    }

    public String getReceiverPay() {
        return receiverPay;
    }

    public void setReceiverPay(String receiverPay) {
        this.receiverPay = receiverPay;
    }

    public String getCollectionMoney() {
        return collectionMoney;
    }

    public void setCollectionMoney(String collectionMoney) {
        this.collectionMoney = collectionMoney;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public String getCommodityMoney() {
        return commodityMoney;
    }

    public void setCommodityMoney(String commodityMoney) {
        this.commodityMoney = commodityMoney;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public EmsDdjrRequestAddress getCollect() {
        return collect;
    }

    public void setCollect(EmsDdjrRequestAddress collect) {
        this.collect = collect;
    }

    public List<EmsDdjrRequestItem> getItems() {
        return items;
    }

    public void setItems(List<EmsDdjrRequestItem> items) {
        this.items = items;
    }

    public List<EmsDdjrRequestSubOrderNormal> getSubOrders() {
        return subOrders;
    }

    public void setSubOrders(List<EmsDdjrRequestSubOrderNormal> subOrders) {
        this.subOrders = subOrders;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public EmsDdjrRequestAddress getReceiver() {
        return receiver;
    }

    public void setReceiver(EmsDdjrRequestAddress receiver) {
        this.receiver = receiver;
    }

    public String getRevertBill() {
        return revertBill;
    }

    public void setRevertBill(String revertBill) {
        this.revertBill = revertBill;
    }

    public EmsDdjrRequestAddress getSender() {
        return sender;
    }

    public void setSender(EmsDdjrRequestAddress sender) {
        this.sender = sender;
    }

    public String getRevertMailNo() {
        return revertMailNo;
    }

    public void setRevertMailNo(String revertMailNo) {
        this.revertMailNo = revertMailNo;
    }

    public String getTxLogisticID() {
        return txLogisticID;
    }

    public void setTxLogisticID(String txLogisticID) {
        this.txLogisticID = txLogisticID;
    }
}
