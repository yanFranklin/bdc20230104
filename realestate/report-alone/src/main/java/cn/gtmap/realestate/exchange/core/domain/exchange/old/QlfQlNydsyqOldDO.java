package cn.gtmap.realestate.exchange.core.domain.exchange.old;

import cn.gtmap.realestate.exchange.core.domain.exchange.AccessData;
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
@Table(name = "QLF_QL_NYDSYQ")
@XmlRootElement(name = "QLF_QL_NYDSYQ")
@XmlAccessorType(XmlAccessType.NONE)
public class QlfQlNydsyqOldDO implements Serializable, AccessData {

    private String ysdm = "6002020500";//要素代码
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
    @ApiModelProperty(value = "发包方代码非空引用《农村土地承包经营权要素编码规则》（NY/T 2538）")
    private String fbfdm;
    @ApiModelProperty(value = "发包方名称非空")
    private String fbfmc;
    @ApiModelProperty(value = "承包（使用权）面积＞0单位：亩")
    private String cbmj;
    @ApiModelProperty(value = "承包(使用)起始时间非空")
    private Date cbqssj;
    @ApiModelProperty(value = "承包(使用)结束时间非空")
    private Date cbjssj;
    @ApiModelProperty(value = "土地所有权性质土地所有权性质字典表")
    private String tdsyqxz;
    @ApiModelProperty(value = "水域滩涂类型水域滩涂类型字典表")
    private String syttlx;
    @ApiModelProperty(value = "养殖业方式养殖业方式字典表")
    private String yzyfs;
    @ApiModelProperty(value = "草原质量")
    private String cyzl;
    @ApiModelProperty(value = "适宜载畜量＞=0单位：头（只）")
    private String syzcl;
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
    private Date createtime;
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

    @XmlAttribute(name = "FBFDM")
    public String getFbfdm() {
        return fbfdm;
    }

    public void setFbfdm(String fbfdm) {
        this.fbfdm = fbfdm;
    }

    @XmlAttribute(name = "FBFMC")
    public String getFbfmc() {
        return fbfmc;
    }

    public void setFbfmc(String fbfmc) {
        this.fbfmc = fbfmc;
    }

    @XmlAttribute(name = "CBMJ")
    public String getCbmj() {
        return cbmj;
    }

    public void setCbmj(String cbmj) {
        this.cbmj = cbmj;
    }

    @XmlAttribute(name = "CBQSSJ")
    public Date getCbqssj() {
        return cbqssj;
    }

    public void setCbqssj(Date cbqssj) {
        this.cbqssj = cbqssj;
    }

    @XmlAttribute(name = "CBJSSJ")
    public Date getCbjssj() {
        return cbjssj;
    }

    public void setCbjssj(Date cbjssj) {
        this.cbjssj = cbjssj;
    }

    @XmlAttribute(name = "TDSYQXZ")
    public String getTdsyqxz() {
        return tdsyqxz;
    }

    public void setTdsyqxz(String tdsyqxz) {
        this.tdsyqxz = tdsyqxz;
    }

    @XmlAttribute(name = "SYTTLX")
    public String getSyttlx() {
        return syttlx;
    }

    public void setSyttlx(String syttlx) {
        this.syttlx = syttlx;
    }

    @XmlAttribute(name = "YZYFS")
    public String getYzyfs() {
        return yzyfs;
    }

    public void setYzyfs(String yzyfs) {
        this.yzyfs = yzyfs;
    }

    @XmlAttribute(name = "CYZL")
    public String getCyzl() {
        return cyzl;
    }

    public void setCyzl(String cyzl) {
        this.cyzl = cyzl;
    }

    @XmlAttribute(name = "SYZCL")
    public String getSyzcl() {
        return syzcl;
    }

    public void setSyzcl(String syzcl) {
        this.syzcl = syzcl;
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
}
