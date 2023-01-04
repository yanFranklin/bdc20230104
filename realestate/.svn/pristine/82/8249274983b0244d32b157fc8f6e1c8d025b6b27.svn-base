package cn.gtmap.realestate.common.core.domain.exchange;

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
@Table(name = "ZH_K")
@XmlRootElement(name = "ZH_K")
@XmlAccessorType(XmlAccessType.NONE)
public class ZhKDO implements Serializable, AccessData {

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "子对象")
    private Integer zdx;
    @ApiModelProperty(value = "序号")
    private String xh;
    @ApiModelProperty(value = "X 坐标")
    private Double xzb;
    @ApiModelProperty(value = "Y 坐标")
    private Double yzb;
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    @ApiModelProperty(value = "更新时间")
    private Date updatetime;

    @ApiModelProperty(value = "面标识")
    private String mbs;

    @XmlAttribute(name = "MBS")
    public String getMbs() {
        return mbs;
    }

    public void setMbs(String mbs) {
        this.mbs = mbs;
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
    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    @XmlAttribute(name = "XZB")
    public Double getXzb() {
        return xzb;
    }

    public void setXzb(Double xzb) {
        this.xzb = xzb;
    }

    @XmlAttribute(name = "YZB")
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
}
