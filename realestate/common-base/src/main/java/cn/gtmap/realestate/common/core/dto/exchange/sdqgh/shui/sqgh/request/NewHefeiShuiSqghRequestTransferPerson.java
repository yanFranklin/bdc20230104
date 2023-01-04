package cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.sqgh.request;

public class NewHefeiShuiSqghRequestTransferPerson {

    private String slbh;

    private String xmid;

    //受理编号
    private String outId;

    //业务类型：此业务固定值JMHGH
    private String businessType;

    //户号
    private String customerId;

    //原户名
    private String customerName;

    //地址，传登记坐落
    private String customerAddress;

    //类型：1.户主信息；2.双方信息，登记传2
    private String type;

    //新户主姓名
    private String newCustomerName;

    //新户主身份证号
    private String newIdcard;

    //联系人手机号
    private String contactPhone;

    //附件：json
    private String attachment;

    //变更理由，传登记原因
    private String reason;

    //数据来源：1:微信公众号营业厅；2.微信小程序营业厅；3:网站营业厅；4.智慧营业厅；9.其他，5联动过户；（登记部门传5）
    private String dataSource;

    //证件类型：身份证
    private String certType;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNewCustomerName() {
        return newCustomerName;
    }

    public void setNewCustomerName(String newCustomerName) {
        this.newCustomerName = newCustomerName;
    }

    public String getNewIdcard() {
        return newIdcard;
    }

    public void setNewIdcard(String newIdcard) {
        this.newIdcard = newIdcard;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }
}
