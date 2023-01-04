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
@Table(name = "QLF_QL_DYIQ")
@XmlRootElement(name = "QLF_QL_DYIQ")
@XmlAccessorType(XmlAccessType.NONE)
public class QlfQlDyiqDO implements Serializable, AccessData {

    private String ysdm = "6002020900";//要素代码
    @ApiModelProperty(value = "业务号")
    @Id
    private String ywh;
    @ApiModelProperty(value = "供役地不动产单元号非空")
    @Id
    private String gydbdcdyh;
    @ApiModelProperty(value = "供役地权利人非空")
    private String gydqlr;
    @ApiModelProperty(value = "供役地权利人证件种类证件种类字典表")
    private String gydqlrzjzl;
    @ApiModelProperty(value = "供役地权利人证件号")
    private String gydqlrzjh;
    @ApiModelProperty(value = "需役地不动产单元编号非空")
    private String xydbdcdyh;
    @ApiModelProperty(value = "需役地坐落非空")
    private String xydzl;
    @ApiModelProperty(value = "需役地权利人非空")
    private String xydqlr;
    @ApiModelProperty(value = "需役地权利人证件种类证件种类字典表")
    private String xydqlrzjzl;
    @ApiModelProperty(value = "需役地权利人证件号")
    private String xydqlrzjh;
    @ApiModelProperty(value = "登记类型登记类型字典表")
    private String djlx;
    @ApiModelProperty(value = "登记原因非空")
    private String djyy;
    @ApiModelProperty(value = "地役权内容非空")
    private String dyqnr;
    @ApiModelProperty(value = "权利起始时间")
    private Date qlqssj;
    @ApiModelProperty(value = "权利结束时间")
    private Date qljssj;
    @ApiModelProperty(value = "区县代码区县字典表记录属地。")
    private String qxdm;
    @ApiModelProperty(value = "登记机构非空")
    private String djjg;
    @ApiModelProperty(value = "登簿人非空")
    private String dbr;
    @ApiModelProperty(value = "登记时间非空")
    private Date djsj;
    @ApiModelProperty(value = "附记")
    private String fj;
    @ApiModelProperty(value = "权属状态权属状态字典表")
    private String qszt;
    @ApiModelProperty(value = "不动产登记证明号")
    private String bdcdjzmh;
    private Date createtime;
    private Date updatetime;
    @ApiModelProperty(value = "过度房屋的fwbm，过度土地的djh")
    private String dzwbm;
    @ApiModelProperty(value = "上一手业务号")
    private String ssywh;
    @ApiModelProperty(value = "权力期限")
    private String qlqx;

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

    @XmlAttribute(name = "GYDBDCDYH")
    public String getGydbdcdyh() {
        return gydbdcdyh;
    }

    public void setGydbdcdyh(String gydbdcdyh) {
        this.gydbdcdyh = gydbdcdyh;
    }

    @XmlAttribute(name = "GYDQLR")
    public String getGydqlr() {
        return gydqlr;
    }

    public void setGydqlr(String gydqlr) {
        this.gydqlr = gydqlr;
    }

    @XmlAttribute(name = "GYDQLRZJZL")
    public String getGydqlrzjzl() {
        return gydqlrzjzl;
    }

    public void setGydqlrzjzl(String gydqlrzjzl) {
        this.gydqlrzjzl = gydqlrzjzl;
    }

    @XmlAttribute(name = "GYDQLRZJH")
    public String getGydqlrzjh() {
        return gydqlrzjh;
    }

    public void setGydqlrzjh(String gydqlrzjh) {
        this.gydqlrzjh = gydqlrzjh;
    }

    @XmlAttribute(name = "XYDBDCDYH")
    public String getXydbdcdyh() {
        return xydbdcdyh;
    }

    public void setXydbdcdyh(String xydbdcdyh) {
        this.xydbdcdyh = xydbdcdyh;
    }

    @XmlAttribute(name = "XYDZL")
    public String getXydzl() {
        return xydzl;
    }

    public void setXydzl(String xydzl) {
        this.xydzl = xydzl;
    }

    @XmlAttribute(name = "XYDQLR")
    public String getXydqlr() {
        return xydqlr;
    }

    public void setXydqlr(String xydqlr) {
        this.xydqlr = xydqlr;
    }

    @XmlAttribute(name = "XYDQLRZJZL")
    public String getXydqlrzjzl() {
        return xydqlrzjzl;
    }

    public void setXydqlrzjzl(String xydqlrzjzl) {
        this.xydqlrzjzl = xydqlrzjzl;
    }

    @XmlAttribute(name = "XYDQLRZJH")
    public String getXydqlrzjh() {
        return xydqlrzjh;
    }

    public void setXydqlrzjh(String xydqlrzjh) {
        this.xydqlrzjh = xydqlrzjh;
    }

    @XmlAttribute(name = "DJLX")
    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    @XmlAttribute(name = "DJYY")
    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    @XmlAttribute(name = "DYQNR")
    public String getDyqnr() {
        return dyqnr;
    }

    public void setDyqnr(String dyqnr) {
        this.dyqnr = dyqnr;
    }

    @XmlAttribute(name = "QLQSSJ")
    public Date getQlqssj() {
        return qlqssj;
    }

    public void setQlqssj(Date qlqssj) {
        this.qlqssj = qlqssj;
    }

    @XmlAttribute(name = "QLJSSJ")
    public Date getQljssj() {
        return qljssj;
    }

    public void setQljssj(Date qljssj) {
        this.qljssj = qljssj;
    }

    @XmlAttribute(name = "QXDM")
    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    @XmlAttribute(name = "DJJG")
    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    @XmlAttribute(name = "DBR")
    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    @XmlAttribute(name = "DJSJ")
    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    @XmlAttribute(name = "FJ")
    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    @XmlAttribute(name = "QSZT")
    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    @XmlAttribute(name = "BDCDJZMH")
    public String getBdcdjzmh() {
        return bdcdjzmh;
    }

    public void setBdcdjzmh(String bdcdjzmh) {
        this.bdcdjzmh = bdcdjzmh;
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

    public String getDzwbm() {
        return dzwbm;
    }

    public void setDzwbm(String dzwbm) {
        this.dzwbm = dzwbm;
    }


    @XmlAttribute(name = "SSYWH")
    public String getSsywh() {
        return ssywh;
    }

    public void setSsywh(String ssywh) {
        this.ssywh = ssywh;
    }

    @XmlAttribute(name = "QLQX")
    public String getQlqx() {
        return qlqx;
    }

    public void setQlqx(String qlqx) {
        this.qlqx = qlqx;
    }
}
