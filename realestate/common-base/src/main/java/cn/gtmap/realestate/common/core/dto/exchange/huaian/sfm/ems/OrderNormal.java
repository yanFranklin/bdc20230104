package cn.gtmap.realestate.common.core.dto.exchange.huaian.sfm.ems;

import java.util.List;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @date 2022/11/02  10:10
 * @description 中国邮政，下单取号的请求参数
 */
public class OrderNormal {

    // 订单接入时间
    private String created_time;
    // 电商客户标识
    private String ecommerce_user_id;
    // 协议客户代码
    private String sender_no;
    // 物流订单号
    private String logistics_order_no;
    // 批次号
    private String batch_no;
    // 运单号
    private String waybill_no;
    // 一票多件标识
    private String one_bill_flag;
    // 子单数量
    private String submail_no;
    // 一票多件计费方式
    private String one_bill_fee_type;
    // 内件性质
    private String contents_attribute;
    // 基础产品代码
    private String base_product_no;
    // 业务产品分类
    private String biz_product_no;
    // 订单重量
    private String weight;
    // 订单体积
    private String volume;
    // 长
    private String length;
    // 宽
    private String width;
    // 高
    private String height;
    // 邮费
    private String postage_total;
    // 备注
    private String pickup_notes;
    // 保险保价标志
    private String insurance_flag;
    // 保价金额
    private String insurance_amount;
    // 保险金额
    private String insurance_premium_amount;
    // 投递方式
    private String deliver_type;
    // 投递预约时间
    private String deliver_pre_date;
    // 揽收方式
    private String pickup_type;
    // 揽收预约起始时间
    private String pickup_pre_begin_time;
    // 揽收预约截至时间
    private String pickup_pre_end_time;
    // 付款方式
    private String payment_mode;
    // 代收款标志
    private String cod_flag;
    // 代收款金额
    private String cod_amount;
    // 回单标识
    private String receipt_flag;
    // 回单运单号
    private String receipt_waybill_no;
    // 电子优惠券号
    private String electronic_preferential_no;
    // 电子优惠券金额
    private String electronic_preferential_amount;
    // 贵品标识
    private String valuable_flag;
    // 寄件人安全码
    private String sender_safety_code;
    // 收件人安全码
    private String receiver_safety_code;
    // 备注
    private String note;
    // 寄件人信息
    private Sender sender;
    // 收件人信息
    private Receiver receiver;
    // 商品信息
    private List<Cargo> cargos;

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getEcommerce_user_id() {
        return ecommerce_user_id;
    }

    public void setEcommerce_user_id(String ecommerce_user_id) {
        this.ecommerce_user_id = ecommerce_user_id;
    }

    public String getSender_no() {
        return sender_no;
    }

    public void setSender_no(String sender_no) {
        this.sender_no = sender_no;
    }

    public String getLogistics_order_no() {
        return logistics_order_no;
    }

    public void setLogistics_order_no(String logistics_order_no) {
        this.logistics_order_no = logistics_order_no;
    }

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public String getWaybill_no() {
        return waybill_no;
    }

    public void setWaybill_no(String waybill_no) {
        this.waybill_no = waybill_no;
    }

    public String getOne_bill_flag() {
        return one_bill_flag;
    }

    public void setOne_bill_flag(String one_bill_flag) {
        this.one_bill_flag = one_bill_flag;
    }

    public String getSubmail_no() {
        return submail_no;
    }

    public void setSubmail_no(String submail_no) {
        this.submail_no = submail_no;
    }

    public String getOne_bill_fee_type() {
        return one_bill_fee_type;
    }

    public void setOne_bill_fee_type(String one_bill_fee_type) {
        this.one_bill_fee_type = one_bill_fee_type;
    }

    public String getContents_attribute() {
        return contents_attribute;
    }

    public void setContents_attribute(String contents_attribute) {
        this.contents_attribute = contents_attribute;
    }

    public String getBase_product_no() {
        return base_product_no;
    }

    public void setBase_product_no(String base_product_no) {
        this.base_product_no = base_product_no;
    }

    public String getBiz_product_no() {
        return biz_product_no;
    }

    public void setBiz_product_no(String biz_product_no) {
        this.biz_product_no = biz_product_no;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getPostage_total() {
        return postage_total;
    }

    public void setPostage_total(String postage_total) {
        this.postage_total = postage_total;
    }

    public String getPickup_notes() {
        return pickup_notes;
    }

    public void setPickup_notes(String pickup_notes) {
        this.pickup_notes = pickup_notes;
    }

    public String getInsurance_flag() {
        return insurance_flag;
    }

    public void setInsurance_flag(String insurance_flag) {
        this.insurance_flag = insurance_flag;
    }

    public String getInsurance_amount() {
        return insurance_amount;
    }

    public void setInsurance_amount(String insurance_amount) {
        this.insurance_amount = insurance_amount;
    }

    public String getInsurance_premium_amount() {
        return insurance_premium_amount;
    }

    public void setInsurance_premium_amount(String insurance_premium_amount) {
        this.insurance_premium_amount = insurance_premium_amount;
    }

    public String getDeliver_type() {
        return deliver_type;
    }

    public void setDeliver_type(String deliver_type) {
        this.deliver_type = deliver_type;
    }

    public String getDeliver_pre_date() {
        return deliver_pre_date;
    }

    public void setDeliver_pre_date(String deliver_pre_date) {
        this.deliver_pre_date = deliver_pre_date;
    }

    public String getPickup_type() {
        return pickup_type;
    }

    public void setPickup_type(String pickup_type) {
        this.pickup_type = pickup_type;
    }

    public String getPickup_pre_begin_time() {
        return pickup_pre_begin_time;
    }

    public void setPickup_pre_begin_time(String pickup_pre_begin_time) {
        this.pickup_pre_begin_time = pickup_pre_begin_time;
    }

    public String getPickup_pre_end_time() {
        return pickup_pre_end_time;
    }

    public void setPickup_pre_end_time(String pickup_pre_end_time) {
        this.pickup_pre_end_time = pickup_pre_end_time;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getCod_flag() {
        return cod_flag;
    }

    public void setCod_flag(String cod_flag) {
        this.cod_flag = cod_flag;
    }

    public String getCod_amount() {
        return cod_amount;
    }

    public void setCod_amount(String cod_amount) {
        this.cod_amount = cod_amount;
    }

    public String getReceipt_flag() {
        return receipt_flag;
    }

    public void setReceipt_flag(String receipt_flag) {
        this.receipt_flag = receipt_flag;
    }

    public String getReceipt_waybill_no() {
        return receipt_waybill_no;
    }

    public void setReceipt_waybill_no(String receipt_waybill_no) {
        this.receipt_waybill_no = receipt_waybill_no;
    }

    public String getElectronic_preferential_no() {
        return electronic_preferential_no;
    }

    public void setElectronic_preferential_no(String electronic_preferential_no) {
        this.electronic_preferential_no = electronic_preferential_no;
    }

    public String getElectronic_preferential_amount() {
        return electronic_preferential_amount;
    }

    public void setElectronic_preferential_amount(String electronic_preferential_amount) {
        this.electronic_preferential_amount = electronic_preferential_amount;
    }

    public String getValuable_flag() {
        return valuable_flag;
    }

    public void setValuable_flag(String valuable_flag) {
        this.valuable_flag = valuable_flag;
    }

    public String getSender_safety_code() {
        return sender_safety_code;
    }

    public void setSender_safety_code(String sender_safety_code) {
        this.sender_safety_code = sender_safety_code;
    }

    public String getReceiver_safety_code() {
        return receiver_safety_code;
    }

    public void setReceiver_safety_code(String receiver_safety_code) {
        this.receiver_safety_code = receiver_safety_code;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }
}
