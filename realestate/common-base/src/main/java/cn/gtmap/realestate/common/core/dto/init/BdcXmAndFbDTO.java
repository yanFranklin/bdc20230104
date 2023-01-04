package cn.gtmap.realestate.common.core.dto.init;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.StringJoiner;

/**
 * @program: realestate
 * @description: 项目信息和项目附表信息同时获取
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-08 15:12
 **/
@ApiModel(value = "BdcXmAndFbDTO", description = "部分不动产项目和附表的数据对象")
public class BdcXmAndFbDTO extends BdcXmDTO {
    @ApiModelProperty(value = "收费用途")
    private Integer sfyt;

    @ApiModelProperty(value = "地段级别")
    private Integer ddjb;

    @ApiModelProperty(value = "定着物/规划用途")
    private Integer dzwyt;

    @ApiModelProperty(value = "宗地宗海面积")
    private Double zdzhmj;

    @ApiModelProperty(value = "土地使用权份额")
    private Double tdsyqfe;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "义务人")
    private String ywr;

    @ApiModelProperty(value = "原产权证号")
    private String ycqzh;

    public String getZl() { return zl; }

    public void setZl(String zl) { this.zl = zl; }

    public String getQlr() { return qlr; }

    public void setQlr(String qlr) { this.qlr = qlr; }

    public Integer getSfyt() {
        return sfyt;
    }

    public void setSfyt(Integer sfyt) {
        this.sfyt = sfyt;
    }

    public Integer getDdjb() {
        return ddjb;
    }

    public void setDdjb(Integer ddjb) {
        this.ddjb = ddjb;
    }

    public Integer getDzwyt() {
        return dzwyt;
    }

    public void setDzwyt(Integer dzwyt) {
        this.dzwyt = dzwyt;
    }

    public Double getZdzhmj() {
        return zdzhmj;
    }

    public void setZdzhmj(Double zdzhmj) {
        this.zdzhmj = zdzhmj;
    }

    public Double getTdsyqfe() {
        return tdsyqfe;
    }

    public void setTdsyqfe(Double tdsyqfe) {
        this.tdsyqfe = tdsyqfe;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }

    @Override
    public String toString() {
        return "BdcXmAndFbDTO{" +
                "sfyt=" + sfyt +
                ", ddjb=" + ddjb +
                ", dzwyt=" + dzwyt +
                ", zdzhmj=" + zdzhmj +
                ", tdsyqfe=" + tdsyqfe +
                ", zl='" + zl + '\'' +
                ", qlr='" + qlr + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", ywr='" + ywr + '\'' +
                ", ycqzh='" + ycqzh + '\'' +
                '}';
    }
}
