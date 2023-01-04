package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/11/28
 * @description 银行查询产权信息
 */
public class BdcYhcxCqDTO {
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "总层数")
    private Integer zcs;
    @ApiModelProperty(value = "所在层")
    private Integer szc;
    @ApiModelProperty(value = "规划用途")
    private String ghyt;
    @ApiModelProperty(value = "房屋性质")
    private String fwxz;
    @ApiModelProperty(value = "建筑面积")
    private Double jzmj;
    @ApiModelProperty(value = "土地使用面积")
    private Double tdsyqmj;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Integer getZcs() {
        return zcs;
    }

    public void setZcs(Integer zcs) {
        this.zcs = zcs;
    }

    public Integer getSzc() {
        return szc;
    }

    public void setSzc(Integer szc) {
        this.szc = szc;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    public Double getTdsyqmj() {
        return tdsyqmj;
    }

    public void setTdsyqmj(Double tdsyqmj) {
        this.tdsyqmj = tdsyqmj;
    }

    @Override
    public String toString() {
        return "BdcYhcxCqDTO{" +
                "bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", zcs='" + zcs + '\'' +
                ", szc='" + szc + '\'' +
                ", ghyt='" + ghyt + '\'' +
                ", fwxz='" + fwxz + '\'' +
                ", jzmj='" + jzmj + '\'' +
                ", tdsyqmj='" + tdsyqmj + '\'' +
                '}';
    }
}
