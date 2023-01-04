package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/08/25
 * @description 查询子系统：房产证明信息（包含生成的有房无房证明PDF）
 */
public class BdcFczmPdfDTO {
    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;

    @ApiModelProperty(value = "证明编号")
    private String zmbh;

    @ApiModelProperty(value = "证明时间")
    private String zmsj;

    @ApiModelProperty(value = "经办人")
    private String jbr;

    @ApiModelProperty(value = "情况说明")
    private String qksm;

    @ApiModelProperty(value = "住房信息")
    private List<BdcZfxxDTO> zfxx;

    @ApiModelProperty(value = "证明PDF base64编码字符串")
    private String pdf;


    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
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

    public List<BdcZfxxDTO> getZfxx() {
        return zfxx;
    }

    public void setZfxx(List<BdcZfxxDTO> zfxx) {
        this.zfxx = zfxx;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    @Override
    public String toString() {
        return "BdcFczmPdfDTO{" +
                "qlrmc='" + qlrmc + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", zmbh='" + zmbh + '\'' +
                ", zmsj='" + zmsj + '\'' +
                ", jbr='" + jbr + '\'' +
                ", qksm='" + qksm + '\'' +
                ", zfxx=" + zfxx +
                ", pdf='" + pdf + '\'' +
                '}';
    }
}
