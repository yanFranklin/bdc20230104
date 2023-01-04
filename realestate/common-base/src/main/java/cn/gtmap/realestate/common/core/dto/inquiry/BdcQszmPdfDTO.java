package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/08/25
 * @description 查询子系统：权属证明PDF信息
 */
public class BdcQszmPdfDTO {
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "不动产类型")
    private Integer bdclx;

    @ApiModelProperty(value = "证明编号")
    private String zmbh;

    @ApiModelProperty(value = "证明时间")
    private String zmsj;

    @ApiModelProperty(value = "经办人")
    private String jbr;

    @ApiModelProperty(value = "情况说明")
    private String qksm;

    @ApiModelProperty(value = "证明XML数据")
    private String xml;

    @ApiModelProperty(value = "证明PDF base64编码字符串")
    private String pdf;

    @ApiModelProperty(value = "项目id")
    private String xmid;


    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public Integer getBdclx() {
        return bdclx;
    }

    public void setBdclx(Integer bdclx) {
        this.bdclx = bdclx;
    }

    public String getZmbh() {
        return zmbh;
    }

    public void setZmbh(String zmbh) {
        this.zmbh = zmbh;
    }

    public String getZmsj() {
        return zmsj;
    }

    public void setZmsj(String zmsj) {
        this.zmsj = zmsj;
    }

    public String getJbr() {
        return jbr;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    public String getQksm() {
        return qksm;
    }

    public void setQksm(String qksm) {
        this.qksm = qksm;
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

    public String getXmid() {return xmid;}

    public void setXmid(String xmid) {this.xmid = xmid;}

    @Override
    public String toString() {
        return "BdcQszmPdfDTO{" +
                "bdcdyh='" + bdcdyh + '\'' +
                ", bdclx='" + bdclx + '\'' +
                ", zmbh='" + zmbh + '\'' +
                ", zmsj='" + zmsj + '\'' +
                ", jbr='" + jbr + '\'' +
                ", qksm='" + qksm + '\'' +
                ", xml='" + xml + '\'' +
                ", pdf='" + pdf + '\'' +
                ", xmid='" + xmid + '\'' +
                '}';
    }
}
