package cn.gtmap.realestate.common.core.domain.accept;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/11
 * @description 不动产受理房屋信息
 */
@Table(name = "BDC_SL_FWXX")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcSlFwxxDO.class)
@ApiModel(value = "BdcSlFwxxDO", description = "不动产受理房屋信息")
public class BdcSlFwxxDO implements Serializable,BdcSlQl {

    private static final long serialVersionUID = -3955689866729665395L;
    @Id
    @ApiModelProperty(value = "房屋信息ID")
    private String fwxxid;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "建筑年份")
    private Integer jznf;

    @ApiModelProperty(value = "建筑朝向")
    private String jzcx;

    @ApiModelProperty(value = "房间号")
    private String fjh;

    @ApiModelProperty(value = "跃层面积")
    private Double ycmj;

    @ApiModelProperty(value = "地下室面积")
    private Double dxsmj;

    @ApiModelProperty(value = "阁楼面积")
    private Double glmj;

    @ApiModelProperty(value = "车库面积")
    private Double ckmj;

    @ApiModelProperty(value = "出租情况")
    private String czqk;

    @ApiModelProperty(value = "小区名称")
    private String xqmc;

    @ApiModelProperty(value = "行政区划")
    private String xzqh;

    @ApiModelProperty(value = "房屋幢号")
    private String fwdh;

    @ApiModelProperty(value = "套内面积(专有建筑面积)")
    private Double tnmj;

    @ApiModelProperty(value = "房屋用途")
    private Integer fwyt;

    @ApiModelProperty(value = "房屋结构")
    private Integer fwjg;

    @ApiModelProperty(value = "所在层")
    private Integer szc;

    @ApiModelProperty(value = "总层数")
    private Integer zcs;

    @ApiModelProperty(value = "地上总层数")
    private Integer dszcs;

    @ApiModelProperty(value = "街道代码")
    private String jddm;

    @ApiModelProperty(value = "房产预售房屋编码")
    private String ysfwbm;

    @ApiModelProperty(value = "房屋编码")
    private String fwbm;

    @ApiModelProperty(value = "建筑面积")
    private Double jzmj;

    @ApiModelProperty(value = "房屋类型")
    private Integer fwlx;

    @ApiModelProperty(value = "房屋性质")
    private Integer fwxz;

    @ApiModelProperty(value = "分摊建筑面积")
    private Double ftjzmj;

    @ApiModelProperty(value = "土地使用起始时间")
    private Date tdsyqssj;

    @ApiModelProperty(value = "土地使用结束时间")
    private Date tdsyjssj;

    @ApiModelProperty(value = "土地使用权人")
    private String tdsyqr;

    @ApiModelProperty(value = "所在名义层")
    private String szmyc;

    @ApiModelProperty(value = "竣工时间", example = "2018-10-01")
    private String jgsj;

    @ApiModelProperty(value = "土地使用权面积")
    private Double tdsyqmj;

    @ApiModelProperty(value = "独用土地面积")
    private Double dytdmj;

    @ApiModelProperty(value = "分摊土地面积")
    private Double fttdmj;

    @ApiModelProperty(value = "权利其他状况")
    private String qlqtzk;

    @ApiModelProperty(value = "附记")
    private String fj;

    @ApiModelProperty(value = "规划用途细类-蚌埠一窗")
    private String ghytxl;

    @ApiModelProperty(value = "户室id")
    private String hsid;

    @ApiModelProperty(value = "小区代码")
    private String xqdm;

    @ApiModelProperty(value = "房屋总价")
    private Double fwzj;

    @ApiModelProperty(value = "限制状态")
    private Integer xzzt;

    @ApiModelProperty(value = "抵押状态")
    private Integer dyzt;

    @ApiModelProperty(value = "单元号")
    private String dyh;

    @ApiModelProperty(value = "房地产交易价格")
    private Double jyjg;

    @ApiModelProperty(value = "名义总层数")
    private String myzcs;


    @ApiModelProperty(value = "用途名称")
    private String ytmc;

    @ApiModelProperty(value = "房屋结构名称")
    private String fwjgmc;

    public String getFwxxid() {
        return fwxxid;
    }

    public void setFwxxid(String fwxxid) {
        this.fwxxid = fwxxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Integer getJznf() {
        return jznf;
    }

    public void setJznf(Integer jznf) {
        this.jznf = jznf;
    }

    public String getJzcx() {
        return jzcx;
    }

    public void setJzcx(String jzcx) {
        this.jzcx = jzcx;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public Double getYcmj() {
        return ycmj;
    }

    public void setYcmj(Double ycmj) {
        this.ycmj = ycmj;
    }

    public Double getDxsmj() {
        return dxsmj;
    }

    public void setDxsmj(Double dxsmj) {
        this.dxsmj = dxsmj;
    }

    public Double getGlmj() {
        return glmj;
    }

    public void setGlmj(Double glmj) {
        this.glmj = glmj;
    }

    public Double getCkmj() {
        return ckmj;
    }

    public void setCkmj(Double ckmj) {
        this.ckmj = ckmj;
    }

    public String getCzqk() {
        return czqk;
    }

    public void setCzqk(String czqk) {
        this.czqk = czqk;
    }

    public String getXqmc() {
        return xqmc;
    }

    public void setXqmc(String xqmc) {
        this.xqmc = xqmc;
    }

    public String getXzqh() {
        return xzqh;
    }

    public void setXzqh(String xzqh) {
        this.xzqh = xzqh;
    }

    public String getFwdh() {
        return fwdh;
    }

    public void setFwdh(String fwdh) {
        this.fwdh = fwdh;
    }

    public Double getTnmj() {
        return tnmj;
    }

    public void setTnmj(Double tnmj) {
        this.tnmj = tnmj;
    }

    public Integer getFwyt() {
        return fwyt;
    }

    public void setFwyt(Integer fwyt) {
        this.fwyt = fwyt;
    }

    public Integer getFwjg() {
        return fwjg;
    }

    public void setFwjg(Integer fwjg) {
        this.fwjg = fwjg;
    }

    public Integer getSzc() {
        return szc;
    }

    public void setSzc(Integer szc) {
        this.szc = szc;
    }

    public Integer getZcs() {
        return zcs;
    }

    public void setZcs(Integer zcs) {
        this.zcs = zcs;
    }

    public Integer getDszcs() {
        return dszcs;
    }

    public void setDszcs(Integer dszcs) {
        this.dszcs = dszcs;
    }

    public String getJddm() {
        return jddm;
    }

    public void setJddm(String jddm) {
        this.jddm = jddm;
    }

    public String getYsfwbm() {
        return ysfwbm;
    }

    public void setYsfwbm(String ysfwbm) {
        this.ysfwbm = ysfwbm;
    }

    public String getFwbm() {
        return fwbm;
    }

    public void setFwbm(String fwbm) {
        this.fwbm = fwbm;
    }

    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    public Integer getFwlx() {
        return fwlx;
    }

    public void setFwlx(Integer fwlx) {
        this.fwlx = fwlx;
    }

    public Integer getFwxz() {
        return fwxz;
    }

    public void setFwxz(Integer fwxz) {
        this.fwxz = fwxz;
    }

    public Double getFtjzmj() {
        return ftjzmj;
    }

    public void setFtjzmj(Double ftjzmj) {
        this.ftjzmj = ftjzmj;
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

    public String getTdsyqr() {
        return tdsyqr;
    }

    public void setTdsyqr(String tdsyqr) {
        this.tdsyqr = tdsyqr;
    }

    public String getSzmyc() {
        return szmyc;
    }

    public void setSzmyc(String szmyc) {
        this.szmyc = szmyc;
    }

    public String getJgsj() {
        return jgsj;
    }

    public void setJgsj(String jgsj) {
        this.jgsj = jgsj;
    }

    public Double getTdsyqmj() {
        return tdsyqmj;
    }

    public void setTdsyqmj(Double tdsyqmj) {
        this.tdsyqmj = tdsyqmj;
    }

    public Double getDytdmj() {
        return dytdmj;
    }

    public void setDytdmj(Double dytdmj) {
        this.dytdmj = dytdmj;
    }

    public Double getFttdmj() {
        return fttdmj;
    }

    public void setFttdmj(Double fttdmj) {
        this.fttdmj = fttdmj;
    }

    public String getQlqtzk() {
        return qlqtzk;
    }

    public void setQlqtzk(String qlqtzk) {
        this.qlqtzk = qlqtzk;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getGhytxl() {
        return ghytxl;
    }

    public void setGhytxl(String ghytxl) {
        this.ghytxl = ghytxl;
    }

    public String getHsid() {
        return hsid;
    }

    public void setHsid(String hsid) {
        this.hsid = hsid;
    }

    public String getXqdm() {
        return xqdm;
    }

    public void setXqdm(String xqdm) {
        this.xqdm = xqdm;
    }

    public Double getFwzj() {
        return fwzj;
    }

    public void setFwzj(Double fwzj) {
        this.fwzj = fwzj;
    }

    public Integer getXzzt() {
        return xzzt;
    }

    public void setXzzt(Integer xzzt) {
        this.xzzt = xzzt;
    }

    public Integer getDyzt() {
        return dyzt;
    }

    public void setDyzt(Integer dyzt) {
        this.dyzt = dyzt;
    }

    public String getDyh() {
        return dyh;
    }

    public void setDyh(String dyh) {
        this.dyh = dyh;
    }

    public Double getJyjg() {
        return jyjg;
    }

    public void setJyjg(Double jyjg) {
        this.jyjg = jyjg;
    }

    public String getMyzcs() {
        return myzcs;
    }

    public void setMyzcs(String myzcs) {
        this.myzcs = myzcs;
    }


    public String getYtmc() {
        return ytmc;
    }

    public void setYtmc(String ytmc) {
        this.ytmc = ytmc;
    }

    public String getFwjgmc() {
        return fwjgmc;
    }

    public void setFwjgmc(String fwjgmc) {
        this.fwjgmc = fwjgmc;
    }

    @Override
    public String toString() {
        return "BdcSlFwxxDO{" +
                "fwxxid='" + fwxxid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", jznf=" + jznf +
                ", jzcx='" + jzcx + '\'' +
                ", fjh='" + fjh + '\'' +
                ", ycmj=" + ycmj +
                ", dxsmj=" + dxsmj +
                ", glmj=" + glmj +
                ", ckmj=" + ckmj +
                ", czqk='" + czqk + '\'' +
                ", xqmc='" + xqmc + '\'' +
                ", xzqh='" + xzqh + '\'' +
                ", fwdh='" + fwdh + '\'' +
                ", tnmj=" + tnmj +
                ", fwyt=" + fwyt +
                ", fwjg=" + fwjg +
                ", szc=" + szc +
                ", zcs=" + zcs +
                ", dszcs=" + dszcs +
                ", jddm='" + jddm + '\'' +
                ", ysfwbm='" + ysfwbm + '\'' +
                ", fwbm='" + fwbm + '\'' +
                ", jzmj=" + jzmj +
                ", fwlx=" + fwlx +
                ", fwxz=" + fwxz +
                ", ftjzmj=" + ftjzmj +
                ", tdsyqssj=" + tdsyqssj +
                ", tdsyjssj=" + tdsyjssj +
                ", tdsyqr='" + tdsyqr + '\'' +
                ", szmyc='" + szmyc + '\'' +
                ", jgsj='" + jgsj + '\'' +
                ", tdsyqmj=" + tdsyqmj +
                ", dytdmj=" + dytdmj +
                ", fttdmj=" + fttdmj +
                ", qlqtzk='" + qlqtzk + '\'' +
                ", fj='" + fj + '\'' +
                ", ghytxl='" + ghytxl + '\'' +
                ", hsid='" + hsid + '\'' +
                ", xqdm='" + xqdm + '\'' +
                ", fwzj=" + fwzj +
                ", xzzt=" + xzzt +
                ", dyzt=" + dyzt +
                ", dyh='" + dyh + '\'' +
                ", jyjg=" + jyjg +
                ", myzcs=" + myzcs +
                ", ytmc='" + ytmc + '\'' +
                ", fwjgmc='" + fwjgmc + '\'' +
                '}';
    }
}
