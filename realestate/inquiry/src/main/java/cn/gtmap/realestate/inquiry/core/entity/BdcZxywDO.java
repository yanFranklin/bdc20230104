package cn.gtmap.realestate.inquiry.core.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/11
 * @description
 */
@Table(name = "BDC_ZXYW")
public class BdcZxywDO {
    @Id
    @ApiModelProperty(value = "中心业务id")
    private String zxywid;
    @ApiModelProperty(value = "中心名称")
    private String zxmc;
    @ApiModelProperty(value = "业务名称")
    private String ywmc;
    @ApiModelProperty(value = "业务编码")
    private String ywbm;
    @ApiModelProperty(value = "等待时间")
    private Integer ddsj;
    @ApiModelProperty(value = "窗口数")
    private Integer cks;
    @ApiModelProperty(value = "窗口")
    private String ck;

    public String getZxywid() {
        return zxywid;
    }

    public void setZxywid(String zxywid) {
        this.zxywid = zxywid;
    }

    public String getZxmc() {
        return zxmc;
    }

    public void setZxmc(String zxmc) {
        this.zxmc = zxmc;
    }

    public String getYwmc() {
        return ywmc;
    }

    public void setYwmc(String ywmc) {
        this.ywmc = ywmc;
    }

    public String getYwbm() {
        return ywbm;
    }

    public void setYwbm(String ywbm) {
        this.ywbm = ywbm;
    }

    public Integer getDdsj() {
        return ddsj;
    }

    public void setDdsj(Integer ddsj) {
        this.ddsj = ddsj;
    }

    public Integer getCks() {
        return cks;
    }

    public void setCks(Integer cks) {
        this.cks = cks;
    }

    public String getCk() {
        return ck;
    }

    public void setCk(String ck) {
        this.ck = ck;
    }

    @Override
    public String toString() {
        return "BdcZxywDO{" +
                "zxywid='" + zxywid + '\'' +
                ", zxmc='" + zxmc + '\'' +
                ", ywmc='" + ywmc + '\'' +
                ", ywbm='" + ywbm + '\'' +
                ", ddsj=" + ddsj +
                ", cks=" + cks +
                ", ck='" + ck + '\'' +
                '}';
    }
}
