package cn.gtmap.realestate.common.core.dto.register;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/09/07
 * @description 审核信息PDF信息
 */
public class BdcShxxPdfDTO {
    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "打印时间")
    private String dysj;

    @ApiModelProperty(value = "审批表XML数据")
    private String xml;

    @ApiModelProperty(value = "审批表PDF base64编码字符串")
    private String pdf;


    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getDysj() {
        return dysj;
    }

    public void setDysj(String dysj) {
        this.dysj = dysj;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    @Override
    public String toString() {
        return "BdcShxxPdfDTO{" +
                "slbh='" + slbh + '\'' +
                ", dysj='" + dysj + '\'' +
                ", xml='" + xml + '\'' +
                ", pdf='" + pdf + '\'' +
                '}';
    }
}
