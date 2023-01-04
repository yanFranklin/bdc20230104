package cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
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
@Table(name = "DJF_DJ_SH")
@XmlRootElement(name = "DJF_DJ_SH")
@XmlAccessorType(XmlAccessType.NONE)
public class DjfDjShOldDO implements Serializable, AccessData {

    @ApiModelProperty(value = "业务号")
    @Id
    private String ywh;
    private String ysdm = "6004050000";//要素代码
    @ApiModelProperty(value = "节点名称")
    @Id
    private String jdmc;
    @ApiModelProperty(value = "顺序号")
    private String sxh;
    @ApiModelProperty(value = "审核人员姓名")
    private String shryxm;
    @ApiModelProperty(value = "审核开始时间")
    private Date shkssj;
    @ApiModelProperty(value = "审核结束时间")
    private Date shjssj;
    @ApiModelProperty(value = "审核意见")
    private String shyj;
    @ApiModelProperty(value = "操作结果")
    private String czjg;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    @ApiModelProperty(value = "更新时间")
    private Date updatetime;

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

    @XmlAttribute(name = "JDMC")
    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    @XmlAttribute(name = "SXH")
    public String getSxh() {
        return sxh;
    }

    public void setSxh(String sxh) {
        this.sxh = sxh;
    }

    @XmlAttribute(name = "SHRYXM")
    public String getShryxm() {
        return shryxm;
    }

    public void setShryxm(String shryxm) {
        this.shryxm = shryxm;
    }

    @XmlAttribute(name = "SHKSSJ")
    public Date getShkssj() {
        return shkssj;
    }

    public void setShkssj(Date shkssj) {
        this.shkssj = shkssj;
    }

    @XmlAttribute(name = "SHJSSJ")
    public Date getShjssj() {
        return shjssj;
    }

    public void setShjssj(Date shjssj) {
        this.shjssj = shjssj;
    }

    @XmlAttribute(name = "SHYJ")
    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj;
    }

    @XmlAttribute(name = "CZJG")
    public String getCzjg() {
        return czjg;
    }

    public void setCzjg(String czjg) {
        this.czjg = czjg;
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
