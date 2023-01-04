package cn.gtmap.realestate.common.core.domain.exchange;

import cn.gtmap.realestate.common.core.annotations.RequiredFk;
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
@Table(name = "DJF_DJ_SJ")
@XmlRootElement(name = "DJF_DJ_SJ")
@XmlAccessorType(XmlAccessType.NONE)
public class DjfDjSjDO implements Serializable, AccessData {

    @Id
    private String sjid;
    @ApiModelProperty(value = "业务号")
    private String ywh;
    private String ysdm = "6004020100";//要素代码
    @ApiModelProperty(value = "收件时间非空")
    private Date sjsj;
    @ApiModelProperty(value = "收件类型收件类型字典表")
    private String sjlx;
    @ApiModelProperty(value = "收件名称　非空")
    private String sjmc;
    @ApiModelProperty(value = "收件数量＞0")
    private Integer sjsl;
    @ApiModelProperty(value = "是否收缴收验是否字典表")
    private String sfsjsy;
    @ApiModelProperty(value = "是否额外收件是否字典表")
    private String sfewsj;
    @ApiModelProperty(value = "是否补充收件是否字典表")
    private String sfbcsj;
    @ApiModelProperty(value = "页数＞0")
    private Integer ys;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    // 增加 RequiredFk 避免重复推送收件材料 
    @RequiredFk
    @ApiModelProperty(value = "文件中心ID")
    private String wjzxid;
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    @ApiModelProperty(value = "更新时间")
    private Date updatetime;

    public String getSjid() {
        return sjid;
    }

    public void setSjid(String sjid) {
        this.sjid = sjid;
    }

    @XmlAttribute(name = "YWH")
    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    @XmlAttribute(name = "YSDM")
    public String getYsdm() {
        return ysdm;
    }

    @XmlAttribute(name = "SJSJ")
    public Date getSjsj() {
        return sjsj;
    }

    public void setSjsj(Date sjsj) {
        this.sjsj = sjsj;
    }

    @XmlAttribute(name = "SJLX")
    public String getSjlx() {
        return sjlx;
    }

    public void setSjlx(String sjlx) {
        this.sjlx = sjlx;
    }

    @XmlAttribute(name = "SJMC")
    public String getSjmc() {
        return sjmc;
    }

    public void setSjmc(String sjmc) {
        this.sjmc = sjmc;
    }

    @XmlAttribute(name = "SJSL")
    public Integer getSjsl() {
        return sjsl;
    }

    public void setSjsl(Integer sjsl) {
        this.sjsl = sjsl;
    }

    @XmlAttribute(name = "SFSJSY")
    public String getSfsjsy() {
        return sfsjsy;
    }

    public void setSfsjsy(String sfsjsy) {
        this.sfsjsy = sfsjsy;
    }

    @XmlAttribute(name = "SFEWSJ")
    public String getSfewsj() {
        return sfewsj;
    }

    public void setSfewsj(String sfewsj) {
        this.sfewsj = sfewsj;
    }

    @XmlAttribute(name = "SFBCSJ")
    public String getSfbcsj() {
        return sfbcsj;
    }

    public void setSfbcsj(String sfbcsj) {
        this.sfbcsj = sfbcsj;
    }

    @XmlAttribute(name = "YS")
    public Integer getYs() {
        return ys;
    }

    public void setYs(Integer ys) {
        this.ys = ys;
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

    public String getWjzxid() {
        return wjzxid;
    }

    public void setWjzxid(String wjzxid) {
        this.wjzxid = wjzxid;
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
