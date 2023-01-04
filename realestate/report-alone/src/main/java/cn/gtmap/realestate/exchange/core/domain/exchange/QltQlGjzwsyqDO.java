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
@Table(name = "QLT_QL_GJZWSYQ")
@XmlRootElement(name = "QLT_QL_GJZWSYQ")
@XmlAccessorType(XmlAccessType.NONE)
public class QltQlGjzwsyqDO implements Serializable, AccessData {

    private String ysdm = "6002010220";//要素代码
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
    @ApiModelProperty(value = "坐落非空")
    private String zl;
    @ApiModelProperty(value = "土地/海域使用权人非空")
    private String tdhysyqr;
    @ApiModelProperty(value = "土地/海域使用面积＞0单位：平方米")
    private String tdhysymj;
    @ApiModelProperty(value = "土地/海域使用起始时间")
    private Date tdhysyqssj;
    @ApiModelProperty(value = "土地/海域使用结束时间")
    private Date tdhysyjssj;
    @ApiModelProperty(value = "构（建）筑物类型构(建)筑物类型字典表")
    private String gjzwlx;
    @ApiModelProperty(value = "构（建）筑物规划用途非空")
    private String gjzwghyt;
    @ApiModelProperty(value = "构(建)筑物面积＞0单位：平方米")
    private String gjzwmj;
    @ApiModelProperty(value = "竣工时间")
    private Date jgsj;
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
    @ApiModelProperty(value = "构（建）筑物平面图")
    private String gjzwpmt;
    @ApiModelProperty(value = "null")
    private Date createtime;
    @ApiModelProperty(value = "null")
    private Date updatetime;

    @XmlAttribute(name = "YSDM")
    public String getYsdm() {
        return ysdm;
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

    @XmlAttribute(name = "ZL")
    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    @XmlAttribute(name = "TDHYSYQR")
    public String getTdhysyqr() {
        return tdhysyqr;
    }

    public void setTdhysyqr(String tdhysyqr) {
        this.tdhysyqr = tdhysyqr;
    }

    @XmlAttribute(name = "TDHYSYMJ")
    public String getTdhysymj() {
        return tdhysymj;
    }

    public void setTdhysymj(String tdhysymj) {
        this.tdhysymj = tdhysymj;
    }

    @XmlAttribute(name = "TDHYSYQSSJ")
    public Date getTdhysyqssj() {
        return tdhysyqssj;
    }

    public void setTdhysyqssj(Date tdhysyqssj) {
        this.tdhysyqssj = tdhysyqssj;
    }

    @XmlAttribute(name = "TDHYSYJSSJ")
    public Date getTdhysyjssj() {
        return tdhysyjssj;
    }

    public void setTdhysyjssj(Date tdhysyjssj) {
        this.tdhysyjssj = tdhysyjssj;
    }

    @XmlAttribute(name = "GJZWLX")
    public String getGjzwlx() {
        return gjzwlx;
    }

    public void setGjzwlx(String gjzwlx) {
        this.gjzwlx = gjzwlx;
    }

    @XmlAttribute(name = "GJZWGHYT")
    public String getGjzwghyt() {
        return gjzwghyt;
    }

    public void setGjzwghyt(String gjzwghyt) {
        this.gjzwghyt = gjzwghyt;
    }

    @XmlAttribute(name = "GJZWMJ")
    public String getGjzwmj() {
        return gjzwmj;
    }

    public void setGjzwmj(String gjzwmj) {
        this.gjzwmj = gjzwmj;
    }

    @XmlAttribute(name = "JGSJ")
    public Date getJgsj() {
        return jgsj;
    }

    public void setJgsj(Date jgsj) {
        this.jgsj = jgsj;
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

    @XmlAttribute(name = "GJZWPMT")
    public String getGjzwpmt() {
        return gjzwpmt;
    }

    public void setGjzwpmt(String gjzwpmt) {
        this.gjzwpmt = gjzwpmt;
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
