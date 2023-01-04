package cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/10/10 10:33
 * @description 获取冲红票据信息参数
 */
public class ObtainRedemptionTicketInfoRequestDTO {

    @ApiModelProperty(value = "缴款书号")
    private String billno;

    @ApiModelProperty(value = "序列号")
    private String serialNumber;

    @ApiModelProperty(value = "开票人")
    private String drawer;

    public String getBillno() {
        if(billno==null){
            return "";
        }
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getSerialNumber() {
        if(serialNumber==null){
            return "";
        }
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDrawer() {
        if(drawer==null){
            return "";
        }
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    @Override
    public String toString() {
        return "ObtainRedemptionTicketInfoRequestDTO{" +
                "billno='" + billno + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", drawer='" + drawer + '\'' +
                '}';
    }
}
