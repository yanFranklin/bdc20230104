package cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/10/10 13:54
 * @description 提交冲红票据开具参数
 */
public class HandleRedemptionTicketInfoRequestDTO {

    @ApiModelProperty(value = "缴款书号")
    private String billno;

    @ApiModelProperty(value = "序列号")
    private String serialNumber;

    @ApiModelProperty(value = "开票人")
    private String drawer;

    @ApiModelProperty(value = "票据代码")
    private String invoiceCode;

    @ApiModelProperty(value = "票据号码号")
    private String invoiceNumber;

    @ApiModelProperty(value = "票据信息")
    private String invoiceData;

    @ApiModelProperty(value = "签名信息 根据invoiceData 调用ca 后的签名结果")
    private String signData;

    public String getBillno() {
        if(billno == null){
            return "";
        }
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getSerialNumber() {
        if(serialNumber == null){
            return "";
        }
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDrawer() {
        if(drawer == null){
            return "";
        }
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    public String getInvoiceCode() {
        if(invoiceCode == null){
            return "";
        }
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceNumber() {
        if(invoiceNumber == null){
            return "";
        }
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceData() {
        if(invoiceData == null){
            return "";
        }
        return invoiceData;
    }

    public void setInvoiceData(String invoiceData) {
        this.invoiceData = invoiceData;
    }

    public String getSignData() {
        if(signData == null){
            return "";
        }
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

    @Override
    public String toString() {
        return "HandleRedemptionTicketInfoRequestDTO{" +
                "billno='" + billno + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", drawer='" + drawer + '\'' +
                ", invoiceCode='" + invoiceCode + '\'' +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", invoiceData='" + invoiceData + '\'' +
                ", signData='" + signData + '\'' +
                '}';
    }
}
