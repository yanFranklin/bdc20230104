package cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old;

import cn.gtmap.realestate.common.core.annotations.RequiredFk;
import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import io.swagger.annotations.ApiModel;
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
@Table(name = "ZD_K")
@XmlRootElement(name = "ZD_K")
@XmlAccessorType(XmlAccessType.NONE)
public class ZdKOldDO implements Serializable, AccessData {

    @ApiModelProperty(value = "地籍号")
    private String djh;
    @ApiModelProperty(value = "不动产单元号")
    @RequiredFk
    private String bdcdyh;
    @ApiModelProperty(value = "子对象")
    private String zdx;
    @ApiModelProperty(value = "序号")
    private String xh;
    @ApiModelProperty(value = "X 坐标")
    private String xzb;
    @ApiModelProperty(value = "Y 坐标")
    private String yzb;
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    @ApiModelProperty(value = "更新时间")
    private Date updatetime;

    @XmlAttribute(name = "BDCDYH")
    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @XmlAttribute(name = "ZDX")
    public String getZdx() {
        return zdx;
    }

    public void setZdx(String zdx) {
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
    public String getXzb() {
        return xzb;
    }

    public void setXzb(String xzb) {
        this.xzb = xzb;
    }

    @XmlAttribute(name = "YZB")
    public String getYzb() {
        return yzb;
    }

    public void setYzb(String yzb) {
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
