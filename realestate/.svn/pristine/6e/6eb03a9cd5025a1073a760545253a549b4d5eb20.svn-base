package cn.gtmap.realestate.exchange.core.domain.exchange;

import cn.gtmap.realestate.exchange.core.annotations.RequiredFk;
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
@Table(name = "KTT_GY_JZD")
@XmlRootElement(name = "KTT_GY_JZD")
@XmlAccessorType(XmlAccessType.NONE)
public class KttGyJzdDO implements Serializable, AccessData {

    private String ysdm = "6001070000";//要素代码
    @ApiModelProperty(value = "标识码")
    private Integer bsm;
    @ApiModelProperty(value = "宗地/宗海代码")
    @RequiredFk
    private String zdzhdm;
    @ApiModelProperty(value = "界址点号")
    private String jzdh;
    @ApiModelProperty(value = "顺序号")
    private String sxh;
    @ApiModelProperty(value = "界标类型")
    private String jblx;
    @ApiModelProperty(value = "界址点类型")
    private String jzdlx;
    @ApiModelProperty(value = "X 坐标值")
    private String xzbz;
    @ApiModelProperty(value = "Y 坐标值")
    private String yzbz;
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

    @XmlAttribute(name = "JZDH")
    public String getJzdh() {
        return jzdh;
    }

    public void setJzdh(String jzdh) {
        this.jzdh = jzdh;
    }

    @XmlAttribute(name = "SXH")
    public String getSxh() {
        return sxh;
    }

    public void setSxh(String sxh) {
        this.sxh = sxh;
    }

    @XmlAttribute(name = "JBLX")
    public String getJblx() {
        return jblx;
    }

    public void setJblx(String jblx) {
        this.jblx = jblx;
    }

    @XmlAttribute(name = "JZDLX")
    public String getJzdlx() {
        return jzdlx;
    }

    public void setJzdlx(String jzdlx) {
        this.jzdlx = jzdlx;
    }

    @XmlAttribute(name = "XZBZ")
    public String getXzbz() {
        return xzbz;
    }

    public void setXzbz(String xzbz) {
        this.xzbz = xzbz;
    }

    @XmlAttribute(name = "YZBZ")
    public String getYzbz() {
        return yzbz;
    }

    public void setYzbz(String yzbz) {
        this.yzbz = yzbz;
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
