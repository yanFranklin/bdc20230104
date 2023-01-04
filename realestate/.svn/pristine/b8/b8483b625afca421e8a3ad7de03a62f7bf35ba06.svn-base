package cn.gtmap.realestate.exchange.core.domain.exchange;

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
@Table(name = "KTF_ZH_YHYDZB")
@XmlRootElement(name = "KTF_ZH_YHYDZB")
@XmlAccessorType(XmlAccessType.NONE)
public class KtfZhYhydzbDO implements Serializable, AccessData {

    @ApiModelProperty(value = "宗海/海岛代码")
    @Id
    private String zhhddm;
    @ApiModelProperty(value = "序号")
    @Id
    private String xh;
    @ApiModelProperty(value = "北纬")
    private String bw;
    @ApiModelProperty(value = "东经")
    private String dj;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    @ApiModelProperty(value = "更新时间")
    private Date updatetime;

    @XmlAttribute(name = "ZHHDDM")
    public String getZhhddm() {
        return zhhddm;
    }

    public void setZhhddm(String zhhddm) {
        this.zhhddm = zhhddm;
    }

    @XmlAttribute(name = "XH")
    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    @XmlAttribute(name = "BW")
    public String getBw() {
        return bw;
    }

    public void setBw(String bw) {
        this.bw = bw;
    }

    @XmlAttribute(name = "DJ")
    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
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
