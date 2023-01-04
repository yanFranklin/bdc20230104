package cn.gtmap.realestate.common.core.domain.exchange;

import cn.gtmap.realestate.common.core.annotations.RequiredFk;
import cn.gtmap.realestate.common.util.jaxb.JaxbDoubleAdapter;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 2019/0417,1.0
 * @description
 */
@Table(name = "ZD_K")
@XmlRootElement(name = "ZD_K")
@XmlAccessorType(XmlAccessType.NONE)
public class ZdKDO implements Serializable, AccessData {

    @ApiModelProperty(value = "地籍号")
    private String djh;
    @ApiModelProperty(value = "不动产单元号")
    @RequiredFk
    private String bdcdyh;
    @ApiModelProperty(value = "子对象")
    private Integer zdx;
    @ApiModelProperty(value = "序号")
    private Integer xh;
    @ApiModelProperty(value = "X 坐标")
    private Double xzb;
    @ApiModelProperty(value = "Y 坐标")
    private Double yzb;
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    @ApiModelProperty(value = "更新时间")
    private Date updatetime;

    @ApiModelProperty(value = "z 坐标")
    private Double zzb;

    @ApiModelProperty(value = "空间类型")
    private String kjlx;

    @XmlAttribute(name = "ZZB")
    @XmlJavaTypeAdapter(JaxbDoubleAdapter.class)
    public Double getZzb() {
        return zzb;
    }

    public void setZzb(Double zzb) {
        this.zzb = zzb;
    }

    @XmlAttribute(name = "KJLX")
    public String getKjlx() {
        return kjlx;
    }

    public void setKjlx(String kjlx) {
        this.kjlx = kjlx;
    }

    @XmlAttribute(name = "BDCDYH")
    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @XmlAttribute(name = "ZDX")
    public Integer getZdx() {
        return zdx;
    }

    public void setZdx(Integer zdx) {
        this.zdx = zdx;
    }

    @XmlAttribute(name = "XH")
    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    @XmlAttribute(name = "XZB")
    @XmlJavaTypeAdapter(JaxbDoubleAdapter.class)
    public Double getXzb() {
        return xzb;
    }

    public void setXzb(Double xzb) {
        this.xzb = xzb;
    }

    @XmlAttribute(name = "YZB")
    @XmlJavaTypeAdapter(JaxbDoubleAdapter.class)
    public Double getYzb() {
        return yzb;
    }

    public void setYzb(Double yzb) {
        this.yzb = yzb;
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

    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }
}
