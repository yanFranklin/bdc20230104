package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/12/12
 * @description 推送待缴费信息返回结果
 */
public class BdcTsdjfxxResponseDTO {

    @ApiModelProperty(value = "提示信息")
    private String msg;

    @ApiModelProperty(value = "缴费二维码内容")
    private String jfsewmurl;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "缴费书编码")
    private String jfsbm;

    @ApiModelProperty(value = "状态码")
    private String statusCode;

    @ApiModelProperty(value = "核收金额")
    private Double hsje;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getJfsewmurl() {
        return jfsewmurl;
    }

    public void setJfsewmurl(String jfsewmurl) {
        this.jfsewmurl = jfsewmurl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJfsbm() {
        return jfsbm;
    }

    public void setJfsbm(String jfsbm) {
        this.jfsbm = jfsbm;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Double getHsje() {
        return hsje;
    }

    public void setHsje(Double hsje) {
        this.hsje = hsje;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }
}
