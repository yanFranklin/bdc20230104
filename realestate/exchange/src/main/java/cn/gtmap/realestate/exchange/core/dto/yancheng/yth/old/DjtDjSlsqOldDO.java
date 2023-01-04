package cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
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
@Table(name = "DJT_DJ_SLSQ")
@XmlRootElement(name = "DJT_DJ_SLSQ")
@XmlAccessorType(XmlAccessType.NONE)
public class DjtDjSlsqOldDO implements Serializable, AccessData {

    private String ysdm = "6004010000";//要素代码
    @ApiModelProperty(value = "业务号")
    @Id
    private String ywh;
    @ApiModelProperty(value = "登记大类")
    private String djdl;
    @ApiModelProperty(value = "登记小类")
    private String djxl;
    @ApiModelProperty(value = "申请证书板式")
    private String sqzsbs;
    @ApiModelProperty(value = "申请分别持证")
    private String sqfbcz;
    @ApiModelProperty(value = "受理人员")
    private String slry;
    @ApiModelProperty(value = "受理时间")
    private Date slsj;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "通知人姓名")
    private String tzrxm;
    @ApiModelProperty(value = "通知方式")
    private String tzfs;
    @ApiModelProperty(value = "通知人电话")
    private String tzrdh;
    @ApiModelProperty(value = "通知人移动电话")
    private String tzryddh;
    @ApiModelProperty(value = "通知人电子邮件")
    private String tzrdzyj;
    @ApiModelProperty(value = "是否问题案件")
    private String sfwtaj;
    @ApiModelProperty(value = "结束时间")
    private Date jssj;
    @ApiModelProperty(value = "案件状态")
    private String ajzt;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    private Date createtime;
    private Date updatetime;

    @XmlAttribute(name = "YSDM")
    public String getYsdm() {
        return ysdm;
    }

    @XmlAttribute(name = "YWH")
    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    @XmlAttribute(name = "DJDL")
    public String getDjdl() {
        return djdl;
    }

    public void setDjdl(String djdl) {
        this.djdl = djdl;
    }

    @XmlAttribute(name = "DJXL")
    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    @XmlAttribute(name = "SQZSBS")
    public String getSqzsbs() {
        return sqzsbs;
    }

    public void setSqzsbs(String sqzsbs) {
        this.sqzsbs = sqzsbs;
    }

    @XmlAttribute(name = "SQFBCZ")
    public String getSqfbcz() {
        return sqfbcz;
    }

    public void setSqfbcz(String sqfbcz) {
        this.sqfbcz = sqfbcz;
    }

    @XmlAttribute(name = "SLRY")
    public String getSlry() {
        return slry;
    }

    public void setSlry(String slry) {
        this.slry = slry;
    }

    @XmlAttribute(name = "SLSJ")
    public Date getSlsj() {
        return slsj;
    }

    public void setSlsj(Date slsj) {
        this.slsj = slsj;
    }

    @XmlAttribute(name = "ZL")
    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    @XmlAttribute(name = "TZRXM")
    public String getTzrxm() {
        return tzrxm;
    }

    public void setTzrxm(String tzrxm) {
        this.tzrxm = tzrxm;
    }

    @XmlAttribute(name = "TZFS")
    public String getTzfs() {
        return tzfs;
    }

    public void setTzfs(String tzfs) {
        this.tzfs = tzfs;
    }

    @XmlAttribute(name = "TZRDH")
    public String getTzrdh() {
        return tzrdh;
    }

    public void setTzrdh(String tzrdh) {
        this.tzrdh = tzrdh;
    }

    @XmlAttribute(name = "TZRYDDH")
    public String getTzryddh() {
        return tzryddh;
    }

    public void setTzryddh(String tzryddh) {
        this.tzryddh = tzryddh;
    }

    @XmlAttribute(name = "TZRDZYJ")
    public String getTzrdzyj() {
        return tzrdzyj;
    }

    public void setTzrdzyj(String tzrdzyj) {
        this.tzrdzyj = tzrdzyj;
    }

    @XmlAttribute(name = "SFWTAJ")
    public String getSfwtaj() {
        return sfwtaj;
    }

    public void setSfwtaj(String sfwtaj) {
        this.sfwtaj = sfwtaj;
    }

    @XmlAttribute(name = "JSSJ")
    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    @XmlAttribute(name = "AJZT")
    public String getAjzt() {
        return ajzt;
    }

    public void setAjzt(String ajzt) {
        this.ajzt = ajzt;
    }

    @XmlAttribute(name = "BZ")
    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @XmlAttribute(name = "QXDM")
    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
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
}
