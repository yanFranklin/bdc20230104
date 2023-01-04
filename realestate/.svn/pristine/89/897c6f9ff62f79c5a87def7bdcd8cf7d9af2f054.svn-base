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
@Table(name = "KTF_ZHBHQK")
@XmlRootElement(name = "KTF_ZHBHQK")
@XmlAccessorType(XmlAccessType.NONE)
public class KtfZhbhqkOldDO implements Serializable, AccessData {

    @ApiModelProperty(value = "宗海代码")
    @RequiredFk
    private String zhdm;
    @ApiModelProperty(value = "变化原因")
    private String bhyy;
    @ApiModelProperty(value = "变化内容")
    private String bhnr;
    @ApiModelProperty(value = "登记时间")
    private Date djsj;
    @ApiModelProperty(value = "登簿人")
    private String dbr;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    @ApiModelProperty(value = "更新时间")
    private Date updatetime;

    @XmlAttribute(name = "ZHDM")
    public String getZhdm() {
        return zhdm;
    }

    public void setZhdm(String zhdm) {
        this.zhdm = zhdm;
    }

    @XmlAttribute(name = "BHYY")
    public String getBhyy() {
        return bhyy;
    }

    public void setBhyy(String bhyy) {
        this.bhyy = bhyy;
    }

    @XmlAttribute(name = "BHNR")
    public String getBhnr() {
        return bhnr;
    }

    public void setBhnr(String bhnr) {
        this.bhnr = bhnr;
    }

    @XmlAttribute(name = "DJSJ")
    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    @XmlAttribute(name = "DBR")
    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
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
