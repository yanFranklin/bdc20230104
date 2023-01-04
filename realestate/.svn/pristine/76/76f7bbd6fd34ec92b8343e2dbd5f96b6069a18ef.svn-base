package cn.gtmap.realestate.common.core.domain.exchange;

import cn.gtmap.realestate.common.core.annotations.RequiredFk;
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
@Table(name = "KTF_ZH_YHZK")
@XmlRootElement(name = "KTF_ZH_YHZK")
@XmlAccessorType(XmlAccessType.NONE)
public class KtfZhYhzkDO implements Serializable, AccessData {

    @ApiModelProperty(value = "宗海代码")
    @RequiredFk
    private String zhdm;
    @ApiModelProperty(value = "用海方式")
    private String yhfs;
    @ApiModelProperty(value = "用海面积")
    private String yhmj;
    @ApiModelProperty(value = "具体用途")
    private String jtyt;
    @ApiModelProperty(value = "使用金额数")
    private Double syjes;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    @ApiModelProperty(value = "更新时间")
    private Date updatetime;
    @ApiModelProperty(value = "面标识")
    private String mbs;

    @ApiModelProperty(value = "金额单位")
    private String jedw;


    @XmlAttribute(name = "JEDW")
    public String getJedw() {
        return jedw;
    }

    public void setJedw(String jedw) {
        this.jedw = jedw;
    }

    @XmlAttribute(name = "MBS")
    public String getMbs() {
        return mbs;
    }

    public void setMbs(String mbs) {
        this.mbs = mbs;
    }

    @XmlAttribute(name = "ZHDM")
    public String getZhdm() {
        return zhdm;
    }

    public void setZhdm(String zhdm) {
        this.zhdm = zhdm;
    }

    @XmlAttribute(name = "YHFS")
    public String getYhfs() {
        return yhfs;
    }

    public void setYhfs(String yhfs) {
        this.yhfs = yhfs;
    }

    @XmlAttribute(name = "YHMJ")
    public String getYhmj() {
        return yhmj;
    }

    public void setYhmj(String yhmj) {
        this.yhmj = yhmj;
    }

    @XmlAttribute(name = "JTYT")
    public String getJtyt() {
        return jtyt;
    }

    public void setJtyt(String jtyt) {
        this.jtyt = jtyt;
    }

    @XmlAttribute(name = "SYJES")
    public Double getSyjes() {
        return syjes;
    }

    public void setSyjes(Double syjes) {
        this.syjes = syjes;
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
