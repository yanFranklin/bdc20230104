package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 常州查询证明明细单房屋信息DTO （自然状况中展示一证多房对应的房屋信息）
 */
public class BdcCxzmdPdfFwxxDTO {
    @ApiModelProperty(value = "幢号/房号")
    private String fh;

    @ApiModelProperty(value = "所在层")
    private String szc;

    @ApiModelProperty(value = "总层数")
    private String zcs;

    @ApiModelProperty(value = "房屋结构")
    private String fwjg;

    @ApiModelProperty(value = "建筑面积")
    private String jzmj;

    @ApiModelProperty(value = "套内建筑面积")
    private String tnjzmj;

    @ApiModelProperty(value = "房屋用途")
    private String ghyt;


    public String getFh() {
        return fh;
    }

    public void setFh(String fh) {
        this.fh = fh;
    }

    public String getSzc() {
        return szc;
    }

    public void setSzc(String szc) {
        this.szc = szc;
    }

    public String getZcs() {
        return zcs;
    }

    public void setZcs(String zcs) {
        this.zcs = zcs;
    }

    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public String getTnjzmj() {
        return tnjzmj;
    }

    public void setTnjzmj(String tnjzmj) {
        this.tnjzmj = tnjzmj;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    @Override
    public String toString() {
        return "BdcCxzmdPdfFwxxDTO{" +
                "fh='" + fh + '\'' +
                ", szc='" + szc + '\'' +
                ", zcs='" + zcs + '\'' +
                ", fwjg='" + fwjg + '\'' +
                ", jzmj='" + jzmj + '\'' +
                ", tnjzmj='" + tnjzmj + '\'' +
                ", ghyt='" + ghyt + '\'' +
                '}';
    }
}
