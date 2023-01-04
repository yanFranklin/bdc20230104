package cn.gtmap.realestate.common.core.domain.exchange;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 2019/0417,1.0
 * @description
 */
@Table(name = "QLF_QL_QTXGQL")
@XmlRootElement(name = "QLF_QL_QTXGQL")
@XmlAccessorType(XmlAccessType.NONE)
public class QlfQlQtxgqlDO {

    private String ysdm = "6002029900";//要素代码
    @ApiModelProperty(value = "不动产单元号非空")
    @Id
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
    @ApiModelProperty(value = "权利起始时间")
    private Date qlqssj;
    @ApiModelProperty(value = "权利结束时间")
    private Date qljssj;
    @ApiModelProperty(value = "取水方式")
    private String qsfs;
    @ApiModelProperty(value = "水源类型")
    private String sylx;
    @ApiModelProperty(value = "取水量＞0单位：立方米")
    private String qsl;
    @ApiModelProperty(value = "取水用途")
    private String qsyt;
    @ApiModelProperty(value = "勘查面积＞0单位：平方公里")
    private String kcmj;
    @ApiModelProperty(value = "开采方式")
    private String kcfs;
    @ApiModelProperty(value = "开采矿种")
    private String kckz;
    @ApiModelProperty(value = "生产规模")
    private String scgm;
    @ApiModelProperty(value = "不动产权证号非空")
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
    @ApiModelProperty(value = "附图")
    private String ft;
    @ApiModelProperty(value = "null")
    private Date createtime;
    @ApiModelProperty(value = "null")
    private Date updatetime;
    @ApiModelProperty(value = "上一手业务号")
    private String ssywh;
    @ApiModelProperty(value = "权力期限")
    private String qlqx;

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

    @XmlAttribute(name = "QSFS")
    public String getQsfs() {
        return qsfs;
    }

    public void setQsfs(String qsfs) {
        this.qsfs = qsfs;
    }

    @XmlAttribute(name = "SYLX")
    public String getSylx() {
        return sylx;
    }

    public void setSylx(String sylx) {
        this.sylx = sylx;
    }

    @XmlAttribute(name = "QSL")
    public String getQsl() {
        return qsl;
    }

    public void setQsl(String qsl) {
        this.qsl = qsl;
    }

    @XmlAttribute(name = "QSYT")
    public String getQsyt() {
        return qsyt;
    }

    public void setQsyt(String qsyt) {
        this.qsyt = qsyt;
    }

    @XmlAttribute(name = "KCMJ")
    public String getKcmj() {
        return kcmj;
    }

    public void setKcmj(String kcmj) {
        this.kcmj = kcmj;
    }

    @XmlAttribute(name = "KCFS")
    public String getKcfs() {
        return kcfs;
    }

    public void setKcfs(String kcfs) {
        this.kcfs = kcfs;
    }

    @XmlAttribute(name = "KCKZ")
    public String getKckz() {
        return kckz;
    }

    public void setKckz(String kckz) {
        this.kckz = kckz;
    }

    @XmlAttribute(name = "SCGM")
    public String getScgm() {
        return scgm;
    }

    public void setScgm(String scgm) {
        this.scgm = scgm;
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

    @XmlAttribute(name = "FT")
    public String getFt() {
        return ft;
    }

    public void setFt(String ft) {
        this.ft = ft;
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

    @XmlAttribute(name = "QLQX")
    public String getQlqx() {
        return qlqx;
    }

    public void setQlqx(String qlqx) {
        this.qlqx = qlqx;
    }
}
