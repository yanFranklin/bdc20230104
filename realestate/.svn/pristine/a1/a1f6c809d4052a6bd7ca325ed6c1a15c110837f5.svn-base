package cn.gtmap.realestate.common.core.domain;

import cn.gtmap.realestate.common.core.annotations.Zd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/31
 * @description 宗地基本信息
 */
@Table(
        name = "BDC_BDCDJB_ZDJBXX"
)
@ApiModel(value = "BdcBdcdjbZdjbxxDO", description = "登记簿宗地基本信息")
public class BdcBdcdjbZdjbxxDO {
    @Id
    /**宗地代码*/
    @ApiModelProperty(value = "宗地代码")
    private String zddm;
    /**不动产单元号*/
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    /**宗地特征码*/
    @ApiModelProperty(value = "宗地特征码")
    private String zdtzm;
    /** 坐落*/
    @ApiModelProperty(value = "坐落")
    private String zl;
    /** 宗地面积*/
    @ApiModelProperty(value = "宗地面积")
    private Double zdmj;
    /**面积单位*/
    @ApiModelProperty(value = "面积单位")
    private Integer mjdw;
    /**用途*/
    @Zd(tableClass = BdcZdTdytDO.class)
    @ApiModelProperty(value = "用途")
    private String yt;
    /**等级*/
    @Zd(tableClass = BdcZdTddjDO.class)
    @ApiModelProperty(value = "等级")
    private Integer dj;
    /**价格*/
    @ApiModelProperty(value = "价格")
    private Double jg;
    /**权利类型*/
    @ApiModelProperty(value = "权利类型")
    private Integer qllx;
    /**权利性质*/
    @ApiModelProperty(value = "权利性质")
    private Integer qlxz;
    /**权利设定方式*/
    @Zd(tableClass = BdcZdQlsdfsDO.class)
    @ApiModelProperty(value = "权利设定方式")
    private Integer qlsdfs;
    /**容积率*/
    @ApiModelProperty(value = "容积率")
    private Double rjl;
    /**建筑密度*/
    @ApiModelProperty(value = "建筑密度")
    private Double jzmd;
    /**建筑限高*/
    @ApiModelProperty(value = "建筑限高")
    private Double jzxg;
    /**宗地四至-东*/
    @ApiModelProperty(value = "宗地四至—东")
    private String zdszd;
    /**宗地四至-南*/
    @ApiModelProperty(value = "宗地四至-南")
    private String zdszn;
    /**宗地四至-西*/
    @ApiModelProperty(value = "宗地四至-西")
    private String zdszx;
    /**宗地四至-北*/
    @ApiModelProperty(value = "宗地四至-北")
    private String zdszb;
    /**图幅号*/
    @ApiModelProperty(value = "图幅号")
    private String tfh;
    /**状态*/
    @ApiModelProperty(value = "状态")
    private Integer zt;
    /**登记时间*/
    @ApiModelProperty(value = "登记时间",example = "2018-10-01 12:18:48")
    private Date djsj;
    /**登簿人*/
    @ApiModelProperty(value = "登簿人")
    private String dbr;
    /**附记*/
    @ApiModelProperty(value = "附记")
    private String fj;
    /**不动产类型*/
    @ApiModelProperty(value = "不动产类型")
    private Integer bdclx;
    /**宗地用途2*/
    @ApiModelProperty(value = "宗地用途2")
    private String zdyt2;
    /**宗地用途3*/
    @ApiModelProperty(value = "宗地用途3")
    private String zdyt3;
    @ApiModelProperty(value = "土地使用起始时间", example = "2018-10-01 12:18:48")
    private Date tdsyqssj;
    @ApiModelProperty(value = "土地使用结束时间", example = "2018-10-01 12:18:48")
    private Date tdsyjssj;
    @ApiModelProperty(value = "土地使用起始时间2", example = "2018-10-01 12:18:48")
    private Date tdsyqssj2;
    @ApiModelProperty(value = "土地使用结束时间2", example = "2018-10-01 12:18:48")
    private Date tdsyjssj2;
    @ApiModelProperty(value = "土地使用起始时间3", example = "2018-10-01 12:18:48")
    private Date tdsyqssj3;
    @ApiModelProperty(value = "土地使用结束时间3", example = "2018-10-01 12:18:48")
    private Date tdsyjssj3;

    @Override
    public String toString() {
        return "BdcBdcdjbZdjbxxDO{" +
                "zddm='" + zddm + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zdtzm='" + zdtzm + '\'' +
                ", zl='" + zl + '\'' +
                ", zdmj=" + zdmj +
                ", mjdw=" + mjdw +
                ", yt='" + yt + '\'' +
                ", dj=" + dj +
                ", jg=" + jg +
                ", qllx=" + qllx +
                ", qlxz=" + qlxz +
                ", qlsdfs=" + qlsdfs +
                ", rjl=" + rjl +
                ", jzmd=" + jzmd +
                ", jzxg=" + jzxg +
                ", zdszd='" + zdszd + '\'' +
                ", zdszn='" + zdszn + '\'' +
                ", zdszx='" + zdszx + '\'' +
                ", zdszb='" + zdszb + '\'' +
                ", tfh='" + tfh + '\'' +
                ", zt=" + zt +
                ", djsj=" + djsj +
                ", dbr='" + dbr + '\'' +
                ", fj='" + fj + '\'' +
                ", bdclx=" + bdclx +
                ", zdyt2='" + zdyt2 + '\'' +
                ", zdyt3='" + zdyt3 + '\'' +
                ", tdsyqssj=" + tdsyqssj +
                ", tdsyjssj=" + tdsyjssj +
                ", tdsyqssj2=" + tdsyqssj2 +
                ", tdsyjssj2=" + tdsyjssj2 +
                ", tdsyqssj3=" + tdsyqssj3 +
                ", tdsyjssj3=" + tdsyjssj3 +
                '}';
    }

    public String getZdyt2() {
        return zdyt2;
    }

    public void setZdyt2(String zdyt2) {
        this.zdyt2 = zdyt2;
    }

    public String getZdyt3() {
        return zdyt3;
    }

    public void setZdyt3(String zdyt3) {
        this.zdyt3 = zdyt3;
    }

    public Date getTdsyqssj() {
        return tdsyqssj;
    }

    public void setTdsyqssj(Date tdsyqssj) {
        this.tdsyqssj = tdsyqssj;
    }

    public Date getTdsyjssj() {
        return tdsyjssj;
    }

    public void setTdsyjssj(Date tdsyjssj) {
        this.tdsyjssj = tdsyjssj;
    }

    public Date getTdsyqssj2() {
        return tdsyqssj2;
    }

    public void setTdsyqssj2(Date tdsyqssj2) {
        this.tdsyqssj2 = tdsyqssj2;
    }

    public Date getTdsyjssj2() {
        return tdsyjssj2;
    }

    public void setTdsyjssj2(Date tdsyjssj2) {
        this.tdsyjssj2 = tdsyjssj2;
    }

    public Date getTdsyqssj3() {
        return tdsyqssj3;
    }

    public void setTdsyqssj3(Date tdsyqssj3) {
        this.tdsyqssj3 = tdsyqssj3;
    }

    public Date getTdsyjssj3() {
        return tdsyjssj3;
    }

    public void setTdsyjssj3(Date tdsyjssj3) {
        this.tdsyjssj3 = tdsyjssj3;
    }

    public String getZddm() {
        return zddm;
    }

    public void setZddm(String zddm) {
        this.zddm = zddm;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZdtzm() {
        return zdtzm;
    }

    public void setZdtzm(String zdtzm) {
        this.zdtzm = zdtzm;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Double getZdmj() {
        return zdmj;
    }

    public void setZdmj(Double zdmj) {
        this.zdmj = zdmj;
    }

    public Integer getMjdw() {
        return mjdw;
    }

    public void setMjdw(Integer mjdw) {
        this.mjdw = mjdw;
    }

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }

    public Integer getDj() {
        return dj;
    }

    public void setDj(Integer dj) {
        this.dj = dj;
    }

    public Double getJg() {
        return jg;
    }

    public void setJg(Double jg) {
        this.jg = jg;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public Integer getQlxz() {
        return qlxz;
    }

    public void setQlxz(Integer qlxz) {
        this.qlxz = qlxz;
    }

    public Integer getQlsdfs() {
        return qlsdfs;
    }

    public void setQlsdfs(Integer qlsdfs) {
        this.qlsdfs = qlsdfs;
    }

    public Double getRjl() {
        return rjl;
    }

    public void setRjl(Double rjl) {
        this.rjl = rjl;
    }

    public Double getJzmd() {
        return jzmd;
    }

    public void setJzmd(Double jzmd) {
        this.jzmd = jzmd;
    }

    public Double getJzxg() {
        return jzxg;
    }

    public void setJzxg(Double jzxg) {
        this.jzxg = jzxg;
    }

    public String getZdszd() {
        return zdszd;
    }

    public void setZdszd(String zdszd) {
        this.zdszd = zdszd;
    }

    public String getZdszn() {
        return zdszn;
    }

    public void setZdszn(String zdszn) {
        this.zdszn = zdszn;
    }

    public String getZdszx() {
        return zdszx;
    }

    public void setZdszx(String zdszx) {
        this.zdszx = zdszx;
    }

    public String getZdszb() {
        return zdszb;
    }

    public void setZdszb(String zdszb) {
        this.zdszb = zdszb;
    }

    public String getTfh() {
        return tfh;
    }

    public void setTfh(String tfh) {
        this.tfh = tfh;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public Integer getBdclx() {
        return bdclx;
    }

    public void setBdclx(Integer bdclx) {
        this.bdclx = bdclx;
    }

}
