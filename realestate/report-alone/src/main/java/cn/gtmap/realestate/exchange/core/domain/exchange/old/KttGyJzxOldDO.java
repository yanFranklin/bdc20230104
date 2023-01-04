package cn.gtmap.realestate.exchange.core.domain.exchange.old;

import cn.gtmap.realestate.exchange.core.annotations.RequiredFk;
import cn.gtmap.realestate.exchange.core.domain.exchange.AccessData;
import io.swagger.annotations.ApiModelProperty;

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
@Table(name = "KTT_GY_JZX")
@XmlRootElement(name = "KTT_GY_JZX")
@XmlAccessorType(XmlAccessType.NONE)
public class KttGyJzxOldDO implements Serializable, AccessData {

    private String ysdm = "6001060000";//要素代码
    @ApiModelProperty(value = "标识码")
    private Integer bsm;
    @ApiModelProperty(value = "宗地/宗海代码")
    @RequiredFk
    private String zdzhdm;
    @ApiModelProperty(value = "界址线长度")
    private String jzxcd;
    @ApiModelProperty(value = "界址线类别")
    private String jzxlb;
    @ApiModelProperty(value = "界址线位置")
    private String jzxwz;
    @ApiModelProperty(value = "界线性质")
    private String jxxz;
    @ApiModelProperty(value = "权属界线协议书编号")
    private String qsjxxysbh;
    @ApiModelProperty(value = "权属界线协议书")
    private String qsjxxys;
    @ApiModelProperty(value = "权属争议原由书编号")
    private String qszyyysbh;
    @ApiModelProperty(value = "权属争议原由书")
    private String qszyyys;
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    @ApiModelProperty(value = "更新时间")
    private Date updatetime;

    @XmlAttribute(name = "YSDM")
    public String getYsdm() {
        return ysdm;
    }

    @XmlAttribute(name = "BSM")
    public Integer getBsm() {
        return bsm;
    }

    public void setBsm(Integer bsm) {
        this.bsm = bsm;
    }

    @XmlAttribute(name = "ZDZHDM")
    public String getZdzhdm() {
        return zdzhdm;
    }

    public void setZdzhdm(String zdzhdm) {
        this.zdzhdm = zdzhdm;
    }

    @XmlAttribute(name = "JZXCD")
    public String getJzxcd() {
        return jzxcd;
    }

    public void setJzxcd(String jzxcd) {
        this.jzxcd = jzxcd;
    }

    @XmlAttribute(name = "JZXLB")
    public String getJzxlb() {
        return jzxlb;
    }

    public void setJzxlb(String jzxlb) {
        this.jzxlb = jzxlb;
    }

    @XmlAttribute(name = "JZXWZ")
    public String getJzxwz() {
        return jzxwz;
    }

    public void setJzxwz(String jzxwz) {
        this.jzxwz = jzxwz;
    }

    @XmlAttribute(name = "JXXZ")
    public String getJxxz() {
        return jxxz;
    }

    public void setJxxz(String jxxz) {
        this.jxxz = jxxz;
    }

    @XmlAttribute(name = "QSJXXYSBH")
    public String getQsjxxysbh() {
        return qsjxxysbh;
    }

    public void setQsjxxysbh(String qsjxxysbh) {
        this.qsjxxysbh = qsjxxysbh;
    }

    @XmlAttribute(name = "QSJXXYS")
    public String getQsjxxys() {
        return qsjxxys;
    }

    public void setQsjxxys(String qsjxxys) {
        this.qsjxxys = qsjxxys;
    }

    @XmlAttribute(name = "QSZYYYSBH")
    public String getQszyyysbh() {
        return qszyyysbh;
    }

    public void setQszyyysbh(String qszyyysbh) {
        this.qszyyysbh = qszyyysbh;
    }

    @XmlAttribute(name = "QSZYYYS")
    public String getQszyyys() {
        return qszyyys;
    }

    public void setQszyyys(String qszyyys) {
        this.qszyyys = qszyyys;
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
