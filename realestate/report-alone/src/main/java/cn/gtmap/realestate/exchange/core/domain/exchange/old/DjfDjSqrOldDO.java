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
@Table(name = "DJF_DJ_SQR")
@XmlRootElement(name = "DJF_DJ_SQR")
@XmlAccessorType(XmlAccessType.NONE)
public class DjfDjSqrOldDO implements Serializable, AccessData {

    private String ysdm = "6004020000";//要素代码

    @ApiModelProperty(value = "业务号")
    @RequiredFk
    private String ywh;
    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;
    @ApiModelProperty(value = "权利人证件种类")
    private String qlrzjzl;
    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;
    @ApiModelProperty(value = "权利人通讯地址")
    private String qlrtxdz;
    @ApiModelProperty(value = "权利人邮编")
    private String qlryb;
    @ApiModelProperty(value = "权利人法人名称")
    private String qlrfrmc;
    @ApiModelProperty(value = "权利人法人电话")
    private String qlrfrdh;
    @ApiModelProperty(value = "权利人代理人名称")
    private String qlrdlrmc;
    @ApiModelProperty(value = "权利人代理人电话")
    private String qlrdlrdh;
    @ApiModelProperty(value = "权利人代理机构")
    private String qlrdljg;
    @ApiModelProperty(value = "义务人名称")
    private String ywrmc;
    @ApiModelProperty(value = "义务人证件种类")
    private String ywrzjzl;
    @ApiModelProperty(value = "义务人证件号")
    private String ywrzjh;
    @ApiModelProperty(value = "义务人通讯地址")
    private String ywrtxdz;
    @ApiModelProperty(value = "义务人邮编")
    private String ywryb;
    @ApiModelProperty(value = "义务人法人名称")
    private String ywrfrmc;
    @ApiModelProperty(value = "义务人法人电话")
    private String ywrfrdh;
    @ApiModelProperty(value = "义务人代理人名称")
    private String ywrdlrmc;
    @ApiModelProperty(value = "义务人代理人电话")
    private String ywrdlrdh;
    @ApiModelProperty(value = "义务人代理机构")
    private String ywrdljg;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    @ApiModelProperty(value = "更新时间")
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

    @XmlAttribute(name = "QLRMC")
    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    @XmlAttribute(name = "QLRZJZL")
    public String getQlrzjzl() {
        return qlrzjzl;
    }

    public void setQlrzjzl(String qlrzjzl) {
        this.qlrzjzl = qlrzjzl;
    }

    @XmlAttribute(name = "QLRZJH")
    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    @XmlAttribute(name = "QLRTXDZ")
    public String getQlrtxdz() {
        return qlrtxdz;
    }

    public void setQlrtxdz(String qlrtxdz) {
        this.qlrtxdz = qlrtxdz;
    }

    @XmlAttribute(name = "QLRYB")
    public String getQlryb() {
        return qlryb;
    }

    public void setQlryb(String qlryb) {
        this.qlryb = qlryb;
    }

    @XmlAttribute(name = "QLRFRMC")
    public String getQlrfrmc() {
        return qlrfrmc;
    }

    public void setQlrfrmc(String qlrfrmc) {
        this.qlrfrmc = qlrfrmc;
    }

    @XmlAttribute(name = "QLRFRDH")
    public String getQlrfrdh() {
        return qlrfrdh;
    }

    public void setQlrfrdh(String qlrfrdh) {
        this.qlrfrdh = qlrfrdh;
    }

    @XmlAttribute(name = "QLRDLRMC")
    public String getQlrdlrmc() {
        return qlrdlrmc;
    }

    public void setQlrdlrmc(String qlrdlrmc) {
        this.qlrdlrmc = qlrdlrmc;
    }

    @XmlAttribute(name = "QLRDLRDH")
    public String getQlrdlrdh() {
        return qlrdlrdh;
    }

    public void setQlrdlrdh(String qlrdlrdh) {
        this.qlrdlrdh = qlrdlrdh;
    }

    @XmlAttribute(name = "QLRDLJG")
    public String getQlrdljg() {
        return qlrdljg;
    }

    public void setQlrdljg(String qlrdljg) {
        this.qlrdljg = qlrdljg;
    }

    @XmlAttribute(name = "YWRMC")
    public String getYwrmc() {
        return ywrmc;
    }

    public void setYwrmc(String ywrmc) {
        this.ywrmc = ywrmc;
    }

    @XmlAttribute(name = "YWRZJZL")
    public String getYwrzjzl() {
        return ywrzjzl;
    }

    public void setYwrzjzl(String ywrzjzl) {
        this.ywrzjzl = ywrzjzl;
    }

    @XmlAttribute(name = "YWRZJH")
    public String getYwrzjh() {
        return ywrzjh;
    }

    public void setYwrzjh(String ywrzjh) {
        this.ywrzjh = ywrzjh;
    }

    @XmlAttribute(name = "YWRTXDZ")
    public String getYwrtxdz() {
        return ywrtxdz;
    }

    public void setYwrtxdz(String ywrtxdz) {
        this.ywrtxdz = ywrtxdz;
    }

    @XmlAttribute(name = "YWRYB")
    public String getYwryb() {
        return ywryb;
    }

    public void setYwryb(String ywryb) {
        this.ywryb = ywryb;
    }

    @XmlAttribute(name = "YWRFRMC")
    public String getYwrfrmc() {
        return ywrfrmc;
    }

    public void setYwrfrmc(String ywrfrmc) {
        this.ywrfrmc = ywrfrmc;
    }

    @XmlAttribute(name = "YWRFRDH")
    public String getYwrfrdh() {
        return ywrfrdh;
    }

    public void setYwrfrdh(String ywrfrdh) {
        this.ywrfrdh = ywrfrdh;
    }

    @XmlAttribute(name = "YWRDLRMC")
    public String getYwrdlrmc() {
        return ywrdlrmc;
    }

    public void setYwrdlrmc(String ywrdlrmc) {
        this.ywrdlrmc = ywrdlrmc;
    }

    @XmlAttribute(name = "YWRDLRDH")
    public String getYwrdlrdh() {
        return ywrdlrdh;
    }

    public void setYwrdlrdh(String ywrdlrdh) {
        this.ywrdlrdh = ywrdlrdh;
    }

    @XmlAttribute(name = "YWRDLJG")
    public String getYwrdljg() {
        return ywrdljg;
    }

    public void setYwrdljg(String ywrdljg) {
        this.ywrdljg = ywrdljg;
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
