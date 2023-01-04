package cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/10/10 10:33
 * @description 作废缴费书参数
 */
public class VoidPaymentFormRequestDTO {

    @ApiModelProperty(value = "缴费书号")
    private String billno;

    @ApiModelProperty(value = "作废原因")
    private String reason;

    public String getBillno() {
        if(billno == null){
            return "";
        }
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getReason() {
        if(reason == null){
            return "";
        }
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public VoidPaymentFormRequestDTO(){};

    public VoidPaymentFormRequestDTO(String billno, String reason){
        this.billno = billno;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "VoidPaymentFormRequestDTO{" +
                "billno='" + billno + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
