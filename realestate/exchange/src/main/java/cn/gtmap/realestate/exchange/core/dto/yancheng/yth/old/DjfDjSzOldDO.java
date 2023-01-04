package cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old;

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
@Table(name = "DJF_DJ_SZ")
@XmlRootElement(name = "DJF_DJ_SZ")
@XmlAccessorType(XmlAccessType.NONE)
public class DjfDjSzOldDO implements Serializable, AccessData {
    private String ysdm = "6004060000";//要素代码
    @ApiModelProperty(value = "业务号")
    @Id
    private String ywh;
    @ApiModelProperty(value = "缮证名称")
    private String szmc;
    @ApiModelProperty(value = "缮证证号")
    @Id
    private String szzh;
    @ApiModelProperty(value = "印刷序列号")
    private String ysxlh;
    @ApiModelProperty(value = "缮证人员")
    private String szry;
    @ApiModelProperty(value = "缮证时间")
    private Date szsj;
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

    @XmlAttribute(name = "SZMC")
    public String getSzmc() {
        return szmc;
    }

    public void setSzmc(String szmc) {
        this.szmc = szmc;
    }

    @XmlAttribute(name = "SZZH")
    public String getSzzh() {
        return szzh;
    }

    public void setSzzh(String szzh) {
        this.szzh = szzh;
    }

    @XmlAttribute(name = "YSXLH")
    public String getYsxlh() {
        return ysxlh;
    }

    public void setYsxlh(String ysxlh) {
        this.ysxlh = ysxlh;
    }

    @XmlAttribute(name = "SZRY")
    public String getSzry() {
        return szry;
    }

    public void setSzry(String szry) {
        this.szry = szry;
    }

    @XmlAttribute(name = "SZSJ")
    public Date getSzsj() {
        return szsj;
    }

    public void setSzsj(Date szsj) {
        this.szsj = szsj;
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
