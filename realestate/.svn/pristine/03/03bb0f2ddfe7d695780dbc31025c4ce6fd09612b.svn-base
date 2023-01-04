package cn.gtmap.realestate.inquiry.core.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/8/27
 * @description
 */
@Table(name = "BDC_ZXYW_YWBM_REL")
public class BdcZxywYwbmRelDO {
    @Id
    @ApiModelProperty(value = "中心业务id")
    private String relid;
    @ApiModelProperty(value = "中心名称")
    private String zxmc;
    @ApiModelProperty(value = "新业务名称")
    private String xywmc;
    @ApiModelProperty(value = "业务编码")
    private String ywbm;
    @ApiModelProperty(value = "是否显示 0:否 1:是")
    private String sfxs;
    @ApiModelProperty(value = "窗口数")
    private Integer cks;

    public String getRelid() {
        return relid;
    }

    public void setRelid(String relid) {
        this.relid = relid;
    }

    public String getZxmc() {
        return zxmc;
    }

    public void setZxmc(String zxmc) {
        this.zxmc = zxmc;
    }

    public String getXywmc() {
        return xywmc;
    }

    public void setXywmc(String xywmc) {
        this.xywmc = xywmc;
    }

    public String getYwbm() {
        return ywbm;
    }

    public void setYwbm(String ywbm) {
        this.ywbm = ywbm;
    }

    public String getSfxs() {
        return sfxs;
    }

    public void setSfxs(String sfxs) {
        this.sfxs = sfxs;
    }

    public Integer getCks() {
        return cks;
    }

    public void setCks(Integer cks) {
        this.cks = cks;
    }

    @Override
    public String toString() {
        return "BdcZxywYwbmRelDO{" +
                "relid='" + relid + '\'' +
                ", zxmc='" + zxmc + '\'' +
                ", xywmc='" + xywmc + '\'' +
                ", ywbm='" + ywbm + '\'' +
                ", sfxs='" + sfxs + '\'' +
                ", cks=" + cks +
                '}';
    }
}
