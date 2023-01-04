package cn.gtmap.realestate.common.core.domain;

import cn.gtmap.realestate.common.core.annotations.Zd;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:songjiawei@gtmap.cn">songjiawei</a>
 * @description 林权
 */
@Table(
        name = "BDC_LQ"
)
@ApiModel(value = "BdcLqDO", description = "林权")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcLqDO.class)
public class BdcLqDO implements BdcQl {
    @Id
    @ApiModelProperty(value = "权利id")
    private String qlid;
    @ApiModelProperty(value = "使用权（承包方）面积")
    private Double syqmj;
    @ApiModelProperty(value = "林地使用（承包）起始时间", example = "2018-10-01 12:18:48")
    private Date ldsyqssj;
    @ApiModelProperty(value = "林地使用（承包）结束时间", example = "2018-10-01 12:18:48")
    private Date ldsyjssj;
    @ApiModelProperty(value = "主要树种")
    private String zysz;
    @ApiModelProperty(value = "株数")
    private Integer zs;
    @Zd(tableClass = BdcZdLzDO.class)
    @ApiModelProperty(value = "林种")
    private Integer lz;
    @ApiModelProperty(value = "林种名称")
    private String lzmc;
    @ApiModelProperty(value = "造林年度")
    private Integer zlnd;
    @ApiModelProperty(value = "小地名")
    private String xdm;
    @ApiModelProperty(value = "林班")
    private String lb;
    @ApiModelProperty(value = "小班")
    private String xb;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @Zd(tableClass = BdcZdDjlxDO.class)
    @ApiModelProperty(value = "登记类型")
    private Integer djlx;
    @ApiModelProperty(value = "登记原因")
    private String djyy;
    @Zd(tableClass = BdcZdQllxDO.class)
    @ApiModelProperty(value = "权利类型")
    private Integer qllx;
    @ApiModelProperty(value = "权利类型名称")
    private String qllxmc;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "共有情况")
    private String gyqk;
    @ApiModelProperty(value = "登记机构")
    private String djjg;
    @ApiModelProperty(value = "登记时间", example = "2018-10-01 12:18:48")
    private Date djsj;
    @ApiModelProperty(value = "登簿人")
    private String dbr;
    @ApiModelProperty(value = "附记")
    private String fj;
    @Zd(tableClass = BdcZdQsztDO.class)
    @ApiModelProperty(value = "权属状态")
    private Integer qszt;
    @ApiModelProperty(value = "林地所有权性质")
    private Integer ldsyqxz;
    @ApiModelProperty(value = "林地所有权性质名称")
    private String ldsyqxzmc;
    @ApiModelProperty(value = "起源")
    private Integer qy;
    @ApiModelProperty(value = "起源名称")
    private String qymc;
    @ApiModelProperty(value = "发包方")
    private String fbf;
    @ApiModelProperty(value = "森林、林木所有权人")
    private String sllmsyqr1;
    @ApiModelProperty(value = "森林、林木使用权人")
    private String sllmsyqr2;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "不动产单元编号")
    private String bdcdywybh;
    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "注销业务号")
    private String zxywh;
    @ApiModelProperty(value = "注销原因")
    private String zxyy;
    @ApiModelProperty(value = "注销登簿人")
    private String zxdbr;
    @ApiModelProperty(value = "注销登记时间")
    private Date zxdjsj;
    @ApiModelProperty(value = "发包方代码")
    private String fbfdm;
    @ApiModelProperty(value = "森林类别")
    private String sllb;

    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public Double getSyqmj() {
        return syqmj;
    }

    public void setSyqmj(Double syqmj) {
        this.syqmj = syqmj;
    }

    public Date getLdsyqssj() {
        return ldsyqssj;
    }

    public void setLdsyqssj(Date ldsyqssj) {
        this.ldsyqssj = ldsyqssj;
    }

    public Date getLdsyjssj() {
        return ldsyjssj;
    }

    public void setLdsyjssj(Date ldsyjssj) {
        this.ldsyjssj = ldsyjssj;
    }

    public String getZysz() {
        return zysz;
    }

    public String getZxywh() {
        return zxywh;
    }

    public void setZxywh(String zxywh) {
        this.zxywh = zxywh;
    }

    public String getZxyy() {
        return zxyy;
    }

    public void setZxyy(String zxyy) {
        this.zxyy = zxyy;
    }

    public String getZxdbr() {
        return zxdbr;
    }

    public void setZxdbr(String zxdbr) {
        this.zxdbr = zxdbr;
    }

    public Date getZxdjsj() {
        return zxdjsj;
    }

    public void setZxdjsj(Date zxdjsj) {
        this.zxdjsj = zxdjsj;
    }

    public void setZysz(String zysz) {
        this.zysz = zysz;
    }

    public Integer getZs() {
        return zs;
    }

    public void setZs(Integer zs) {
        this.zs = zs;
    }

    public Integer getLz() {
        return lz;
    }

    public void setLz(Integer lz) {
        this.lz = lz;
    }

    public String getLzmc() {
        return lzmc;
    }

    public void setLzmc(String lzmc) {
        this.lzmc = lzmc;
    }

    public Integer getZlnd() {
        return zlnd;
    }

    public void setZlnd(Integer zlnd) {
        this.zlnd = zlnd;
    }

    public String getXdm() {
        return xdm;
    }

    public void setXdm(String xdm) {
        this.xdm = xdm;
    }

    public String getLb() {
        return lb;
    }

    public void setLb(String lb) {
        this.lb = lb;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public Integer getDjlx() {
        return djlx;
    }

    public void setDjlx(Integer djlx) {
        this.djlx = djlx;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public String getQllxmc() {
        return qllxmc;
    }

    public void setQllxmc(String qllxmc) {
        this.qllxmc = qllxmc;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
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

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public Integer getLdsyqxz() {
        return ldsyqxz;
    }

    public void setLdsyqxz(Integer ldsyqxz) {
        this.ldsyqxz = ldsyqxz;
    }

    public String getLdsyqxzmc() {
        return ldsyqxzmc;
    }

    public void setLdsyqxzmc(String ldsyqxzmc) {
        this.ldsyqxzmc = ldsyqxzmc;
    }

    public Integer getQy() {
        return qy;
    }

    public void setQy(Integer qy) {
        this.qy = qy;
    }

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    public String getFbf() {
        return fbf;
    }

    public void setFbf(String fbf) {
        this.fbf = fbf;
    }

    public String getSllmsyqr1() {
        return sllmsyqr1;
    }

    public void setSllmsyqr1(String sllmsyqr1) {
        this.sllmsyqr1 = sllmsyqr1;
    }

    public String getSllmsyqr2() {
        return sllmsyqr2;
    }

    public void setSllmsyqr2(String sllmsyqr2) {
        this.sllmsyqr2 = sllmsyqr2;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

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

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

    public String getFbfdm() {
        return fbfdm;
    }

    public void setFbfdm(String fbfdm) {
        this.fbfdm = fbfdm;
    }

    public String getSllb() {
        return sllb;
    }

    public void setSllb(String sllb) {
        this.sllb = sllb;
    }

    @Override
    public String toString() {
        return "BdcLqDO{" +
                "qlid='" + qlid + '\'' +
                ", syqmj=" + syqmj +
                ", ldsyqssj=" + ldsyqssj +
                ", ldsyjssj=" + ldsyjssj +
                ", zysz='" + zysz + '\'' +
                ", zs=" + zs +
                ", lz=" + lz +
                ", lzmc='" + lzmc + '\'' +
                ", zlnd=" + zlnd +
                ", xdm='" + xdm + '\'' +
                ", lb='" + lb + '\'' +
                ", xb='" + xb + '\'' +
                ", slbh='" + slbh + '\'' +
                ", djlx=" + djlx +
                ", djyy='" + djyy + '\'' +
                ", qllx=" + qllx +
                ", qllxmc='" + qllxmc + '\'' +
                ", xmid='" + xmid + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", djjg='" + djjg + '\'' +
                ", djsj=" + djsj +
                ", dbr='" + dbr + '\'' +
                ", fj='" + fj + '\'' +
                ", qszt=" + qszt +
                ", ldsyqxz=" + ldsyqxz +
                ", ldsyqxzmc='" + ldsyqxzmc + '\'' +
                ", qy=" + qy +
                ", qymc='" + qymc + '\'' +
                ", fbf='" + fbf + '\'' +
                ", sllmsyqr1='" + sllmsyqr1 + '\'' +
                ", sllmsyqr2='" + sllmsyqr2 + '\'' +
                ", bz='" + bz + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdywybh='" + bdcdywybh + '\'' +
                ", zl='" + zl + '\'' +
                ", zxywh='" + zxywh + '\'' +
                ", zxyy='" + zxyy + '\'' +
                ", zxdbr='" + zxdbr + '\'' +
                ", zxdjsj=" + zxdjsj +
                ", fbfdm='" + fbfdm + '\'' +
                ", sllb='" + sllb + '\'' +
                '}';
    }
}
