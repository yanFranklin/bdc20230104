package cn.gtmap.realestate.common.core.domain.exchange;

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
@Table(name = "QLF_QL_HYSYQ")
@XmlRootElement(name = "QLF_QL_HYSYQ")
@XmlAccessorType(XmlAccessType.NONE)
public class QlfQlHysyqDO implements Serializable, AccessData {

    private String ysdm = "6002020600";//要素代码
    @ApiModelProperty(value = "宗海/海岛代码非空")
    private String zhhddm;
    @ApiModelProperty(value = "不动产单元号非空")
    private String bdcdyh;
    @ApiModelProperty(value = "业务号")
    @Id
    private String ywh;
    @ApiModelProperty(value = "权利类型权利类型字典表")
    private String qllx;
    @ApiModelProperty(value = "登记类型登记类型字典表")
    private String djlx;
    @ApiModelProperty(value = "登记原因非空")
    private String djyy;
    @ApiModelProperty(value = "使用权面积＞0单位：公顷")
    private Double syqmj;
    @ApiModelProperty(value = "使用权起始时间")
    private Date syqqssj;
    @ApiModelProperty(value = "使用权结束时间")
    private Date syqjssj;
    @ApiModelProperty(value = "使用金总额＞=0单位：万元")
    private Double syjze;
    @ApiModelProperty(value = "使用金标准依据")
    private String syjbzyj;
    @ApiModelProperty(value = "使用金缴纳情况")
    private String syjjnqk;
    @ApiModelProperty(value = "不动产权证号非空")
    @Id
    private String bdcqzh;
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
    @ApiModelProperty(value = "null")
    private Date createtime;
    @ApiModelProperty(value = "null")
    private Date updatetime;
    @ApiModelProperty(value = "上一手业务号")
    private String ssywh;
    @ApiModelProperty(value = "使用权期限")
    private String syqqx;
    @ApiModelProperty(value = "金额单位")
    private String jedw;
    @ApiModelProperty(value = "使用金额交款凭证")
    private String syjjkpz;
    @ApiModelProperty(value = "海域管理号")
    private String hyglh;


    @XmlAttribute(name = "YSDM")
    public String getYsdm() {
        return ysdm;
    }

    @XmlAttribute(name = "ZHHDDM")
    public String getZhhddm() {
        return zhhddm;
    }

    public void setZhhddm(String zhhddm) {
        this.zhhddm = zhhddm;
    }

    @XmlAttribute(name = "BDCDYH")
    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @XmlAttribute(name = "YWH")
    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    @XmlAttribute(name = "QLLX")
    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
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

    @XmlAttribute(name = "SYQMJ")
    public Double getSyqmj() {
        return syqmj;
    }

    public void setSyqmj(Double syqmj) {
        this.syqmj = syqmj;
    }

    @XmlAttribute(name = "SYQQSSJ")
    public Date getSyqqssj() {
        return syqqssj;
    }

    public void setSyqqssj(Date syqqssj) {
        this.syqqssj = syqqssj;
    }

    @XmlAttribute(name = "SYQJSSJ")
    public Date getSyqjssj() {
        return syqjssj;
    }

    public void setSyqjssj(Date syqjssj) {
        this.syqjssj = syqjssj;
    }

    @XmlAttribute(name = "SYJZE")
    public Double getSyjze() {
        return syjze;
    }

    public void setSyjze(Double syjze) {
        this.syjze = syjze;
    }

    @XmlAttribute(name = "SYJBZYJ")
    public String getSyjbzyj() {
        return syjbzyj;
    }

    public void setSyjbzyj(String syjbzyj) {
        this.syjbzyj = syjbzyj;
    }

    @XmlAttribute(name = "SYJJNQK")
    public String getSyjjnqk() {
        return syjjnqk;
    }

    public void setSyjjnqk(String syjjnqk) {
        this.syjjnqk = syjjnqk;
    }

    @XmlAttribute(name = "BDCQZH")
    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
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

    @XmlAttribute(name = "SSYWH")
    public String getSsywh() {
        return ssywh;
    }

    public void setSsywh(String ssywh) {
        this.ssywh = ssywh;
    }

    @XmlAttribute(name = "SYQQX")
    public String getSyqqx() {
        return syqqx;
    }

    public void setSyqqx(String syqqx) {
        this.syqqx = syqqx;
    }

    @XmlAttribute(name = "JEDW")
    public String getJedw() {
        return jedw;
    }

    public void setJedw(String jedw) {
        this.jedw = jedw;
    }

    @XmlAttribute(name = "SYJJKPZ")
    public String getSyjjkpz() {
        return syjjkpz;
    }

    public void setSyjjkpz(String syjjkpz) {
        this.syjjkpz = syjjkpz;
    }

    @XmlAttribute(name = "HYGLH")
    public String getHyglh() {
        return hyglh;
    }

    public void setHyglh(String hyglh) {
        this.hyglh = hyglh;
    }
}
