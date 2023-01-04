package cn.gtmap.realestate.common.core.domain.exchange;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 2019/0417,1.0
 * @description
 */
@Table(name = "QLT_QL_LQ")
@XmlRootElement(name = "QLT_QL_LQ")
@XmlAccessorType(XmlAccessType.NONE)
public class QltQlLqDO implements Serializable, AccessData {

    private String ysdm = "6002010300";//要素代码
    @ApiModelProperty(value = "不动产单元号非空")
    private String bdcdyh;
    @ApiModelProperty(value = "业务号")
    @Id
    private String ywh;
    @ApiModelProperty(value = "权利类型权利类型字典表")
    private String qllx;
    @ApiModelProperty(value = "登记类型登记类型字典表")
    private String djlx;
    @ApiModelProperty(value = "登记原因非空")
    private String djyy;
    @ApiModelProperty(value = "发包方非空")
    private String fbf;
    @ApiModelProperty(value = "使用权（承包）面积＞0单位：亩")
    private String syqmj;
    @ApiModelProperty(value = "林地使用（承包）起始时间非空")
    private Date ldsyqssj;
    @ApiModelProperty(value = "林地使用（承包）结束时间")
    private Date ldsyjssj;
    @ApiModelProperty(value = "林地所有权性质土地所有权性质字典表")
    private String ldsyqxz;
    @ApiModelProperty(value = "森林、林木所有权人")
    private String sllmsyqr1;
    @ApiModelProperty(value = "森林、林木使用权人")
    private String sllmsyqr2;
    @ApiModelProperty(value = "主要树种非空")
    private String zysz;
    @ApiModelProperty(value = "株数＞0")
    private String zs;
    @ApiModelProperty(value = "林种林种字典表")
    private String lz;
    @ApiModelProperty(value = "起源起源字典表")
    private String qy;
    @ApiModelProperty(value = "造林年度非空")
    private Integer zlnd;
    @ApiModelProperty(value = "林班非空")
    private String lb;
    @ApiModelProperty(value = "小班非空")
    private String xb;
    @ApiModelProperty(value = "小地名")
    private String xdm;
    @ApiModelProperty(value = "不动产权证号非空")
    @Id
    private String bdcqzh;
    @ApiModelProperty(value = "区县代码区县字典表记录属地。")
    private String qxdm;
    @ApiModelProperty(value = "登记机构非空")
    private String djjg;
    @ApiModelProperty(value = "登簿人非空")
    private String dbr;
    @ApiModelProperty(value = "登记时间非空")
    private Date djsj;
    @ApiModelProperty(value = "附记")
    private String fj;
    @ApiModelProperty(value = "权属状态权属状态字典表")
    private String qszt;
    private Date createtime;
    private Date updatetime;
    @ApiModelProperty(value = "上一手业务号")
    private String ssywh;
    @ApiModelProperty(value = "发包方代码")
    private String fbfdm;
    @ApiModelProperty(value = "林地使用（承包）期限")
    private String ldsyqx;
    @ApiModelProperty(value = "森林类别")
    private String sllb;

    @XmlAttribute(name = "YSDM")
    public String getYsdm() {
        return ysdm;
    }

    @XmlAttribute(name = "BDCDYH")
    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @XmlAttribute(name = "YWH")
    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    @XmlAttribute(name = "QLLX")
    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    @XmlAttribute(name = "DJLX")
    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    @XmlAttribute(name = "DJYY")
    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    @XmlAttribute(name = "FBF")
    public String getFbf() {
        return fbf;
    }

    public void setFbf(String fbf) {
        this.fbf = fbf;
    }

    @XmlAttribute(name = "SYQMJ")
    public String getSyqmj() {
        return syqmj;
    }

    public void setSyqmj(String syqmj) {
        this.syqmj = syqmj;
    }

    @XmlAttribute(name = "LDSYQSSJ")
    public Date getLdsyqssj() {
        return ldsyqssj;
    }

    public void setLdsyqssj(Date ldsyqssj) {
        this.ldsyqssj = ldsyqssj;
    }

    @XmlAttribute(name = "LDSYJSSJ")
    public Date getLdsyjssj() {
        return ldsyjssj;
    }

    public void setLdsyjssj(Date ldsyjssj) {
        this.ldsyjssj = ldsyjssj;
    }

    @XmlAttribute(name = "LDSYQXZ")
    public String getLdsyqxz() {
        return ldsyqxz;
    }

    public void setLdsyqxz(String ldsyqxz) {
        this.ldsyqxz = ldsyqxz;
    }

    @XmlAttribute(name = "SLLMSYQR1")
    public String getSllmsyqr1() {
        return sllmsyqr1;
    }

    public void setSllmsyqr1(String sllmsyqr1) {
        this.sllmsyqr1 = sllmsyqr1;
    }

    @XmlAttribute(name = "SLLMSYQR2")
    public String getSllmsyqr2() {
        return sllmsyqr2;
    }

    public void setSllmsyqr2(String sllmsyqr2) {
        this.sllmsyqr2 = sllmsyqr2;
    }

    @XmlAttribute(name = "ZYSZ")
    public String getZysz() {
        return zysz;
    }

    public void setZysz(String zysz) {
        this.zysz = zysz;
    }

    @XmlAttribute(name = "ZS")
    public String getZs() {
        return zs;
    }

    public void setZs(String zs) {
        this.zs = zs;
    }

    @XmlAttribute(name = "LZ")
    public String getLz() {
        return lz;
    }

    public void setLz(String lz) {
        this.lz = lz;
    }

    @XmlAttribute(name = "QY")
    public String getQy() {
        return qy;
    }

    public void setQy(String qy) {
        this.qy = qy;
    }

    @XmlAttribute(name = "ZLND")
    public Integer getZlnd() {
        return zlnd;
    }

    public void setZlnd(Integer zlnd) {
        this.zlnd = zlnd;
    }

    @XmlAttribute(name = "LB")
    public String getLb() {
        return lb;
    }

    public void setLb(String lb) {
        this.lb = lb;
    }

    @XmlAttribute(name = "XB")
    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    @XmlAttribute(name = "XDM")
    public String getXdm() {
        return xdm;
    }

    public void setXdm(String xdm) {
        this.xdm = xdm;
    }

    @XmlAttribute(name = "BDCQZH")
    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    @XmlAttribute(name = "QXDM")
    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    @XmlAttribute(name = "DJJG")
    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    @XmlAttribute(name = "DBR")
    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    @XmlAttribute(name = "DJSJ")
    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    @XmlAttribute(name = "FJ")
    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    @XmlAttribute(name = "QSZT")
    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    @XmlAttribute(name = "SSYWH")
    public String getSsywh() {
        return ssywh;
    }

    public void setSsywh(String ssywh) {
        this.ssywh = ssywh;
    }

    @XmlAttribute(name = "FBFDM")
    public String getFbfdm() {
        return fbfdm;
    }

    public void setFbfdm(String fbfdm) {
        this.fbfdm = fbfdm;
    }

    @XmlAttribute(name = "LDSYQX")
    public String getLdsyqx() {
        return ldsyqx;
    }

    public void setLdsyqx(String ldsyqx) {
        this.ldsyqx = ldsyqx;
    }

    @XmlAttribute(name = "SLLB")
    public String getSllb() {
        return sllb;
    }

    public void setSllb(String sllb) {
        this.sllb = sllb;
    }
}
