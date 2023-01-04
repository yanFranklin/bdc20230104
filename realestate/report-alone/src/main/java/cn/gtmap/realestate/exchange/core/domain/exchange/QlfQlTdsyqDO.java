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
@Table(name = "QLF_QL_TDSYQ")
@XmlRootElement(name = "QLF_QL_TDSYQ")
@XmlAccessorType(XmlAccessType.NONE)
public class QlfQlTdsyqDO implements Serializable, AccessData {

    private String ysdm = "6002010100";//要素代码
    @ApiModelProperty(value = "宗地代码非空")
    private String zddm;
    @ApiModelProperty(value = "不动产单元号非空")
    @Id
    private String bdcdyh;
    @ApiModelProperty(value = "业务号")
    @Id
    private String ywh;
    @ApiModelProperty(value = "权利类型权利类型字典表见本表注1")
    private String qllx;
    @ApiModelProperty(value = "登记类型登记类型字典表")
    private String djlx;
    @ApiModelProperty(value = "登记原因非空")
    private String djyy;
    @ApiModelProperty(value = "面积单位面积单位字典表")
    private String mjdw;
    @ApiModelProperty(value = "农用地面积＞=0")
    private String nydmj;
    @ApiModelProperty(value = "耕地面积＞=0")
    private String gdmj;
    @ApiModelProperty(value = "林地面积＞=0")
    private String ldmj;
    @ApiModelProperty(value = "草地面积＞=0")
    private String cdmj;
    @ApiModelProperty(value = "其它农用地面积＞=0")
    private String qtnydmj;
    @ApiModelProperty(value = "建设用地面积＞=0")
    private String jsydmj;
    @ApiModelProperty(value = "未利用地面积＞=0")
    private String wlydmj;
    @ApiModelProperty(value = "不动产权证号非空见本表注3")
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
    @ApiModelProperty(value = "过度房屋的fwbm，过度土地的djh")
    private String dzwbm;
    @ApiModelProperty(value = "上一手业务号")
    private String ssywh;
    @ApiModelProperty(value = "用途")
    private String yt;

    @XmlAttribute(name = "YSDM")
    public String getYsdm() {
        return ysdm;
    }

    @XmlAttribute(name = "ZDDM")
    public String getZddm() {
        return zddm;
    }

    public void setZddm(String zddm) {
        this.zddm = zddm;
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

    @XmlAttribute(name = "MJDW")
    public String getMjdw() {
        return mjdw;
    }

    public void setMjdw(String mjdw) {
        this.mjdw = mjdw;
    }

    @XmlAttribute(name = "NYDMJ")
    public String getNydmj() {
        return nydmj;
    }

    public void setNydmj(String nydmj) {
        this.nydmj = nydmj;
    }

    @XmlAttribute(name = "GDMJ")
    public String getGdmj() {
        return gdmj;
    }

    public void setGdmj(String gdmj) {
        this.gdmj = gdmj;
    }

    @XmlAttribute(name = "LDMJ")
    public String getLdmj() {
        return ldmj;
    }

    public void setLdmj(String ldmj) {
        this.ldmj = ldmj;
    }

    @XmlAttribute(name = "CDMJ")
    public String getCdmj() {
        return cdmj;
    }

    public void setCdmj(String cdmj) {
        this.cdmj = cdmj;
    }

    @XmlAttribute(name = "QTNYDMJ")
    public String getQtnydmj() {
        return qtnydmj;
    }

    public void setQtnydmj(String qtnydmj) {
        this.qtnydmj = qtnydmj;
    }

    @XmlAttribute(name = "JSYDMJ")
    public String getJsydmj() {
        return jsydmj;
    }

    public void setJsydmj(String jsydmj) {
        this.jsydmj = jsydmj;
    }

    @XmlAttribute(name = "WLYDMJ")
    public String getWlydmj() {
        return wlydmj;
    }

    public void setWlydmj(String wlydmj) {
        this.wlydmj = wlydmj;
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

    @XmlAttribute(name = "YT")
    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }
}
