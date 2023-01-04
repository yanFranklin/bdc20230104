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
@Table(name = "KTT_FW_ZRZ")
@XmlRootElement(name = "KTT_FW_ZRZ")
@XmlAccessorType(XmlAccessType.NONE)
public class KttFwZrzDO implements Serializable, AccessData {

    private String ysdm = "6001030110";
    @ApiModelProperty(value = "标识码/原为非空")
    private Integer bsm;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "宗地代码非空/原为非空")
    @Id
    private String zddm;
    @ApiModelProperty(value = "自然幢号非空/原为非空")
    @Id
    private String zrzh;
    @ApiModelProperty(value = "项目名称")
    private String xmmc;
    @ApiModelProperty(value = "建筑物名称")
    private String jzwmc;
    @ApiModelProperty(value = "竣工日期")
    private Date jgrq;
    @ApiModelProperty(value = "建筑物高度＞0单位：米")
    private Double jzwgd;
    @ApiModelProperty(value = "幢占地面积＞0单位：平方米")
    private Double zzdmj;
    @ApiModelProperty(value = "幢用地面积＞0单位：平方米")
    private Double zydmj;
    @ApiModelProperty(value = "预测建筑面积＞0单位：平方米")
    private Double ycjzmj;
    @ApiModelProperty(value = "实测建筑面积＞0单位：平方米")
    private Double scjzmj;
    @ApiModelProperty(value = "总层数＞0")
    private String zcs;
    @ApiModelProperty(value = "地上层数＞=0")
    private String dscs;
    @ApiModelProperty(value = "地下层数＞=0")
    private String dxcs;
    @ApiModelProperty(value = "地下深度＞=0单位：米")
    private Double dxsd;
    @ApiModelProperty(value = "规划用途房屋用途字典表")
    private String ghyt;
    @ApiModelProperty(value = "房屋结构房屋结构字典表")
    private String fwjg;
    @ApiModelProperty(value = "总套数＞0")
    private Integer zts;
    @ApiModelProperty(value = "建筑物基本用途")
    private String jzwjbyt;
    @ApiModelProperty(value = "档案号")
    private String dah;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "状态不动产单元状态字典表")
    private String zt;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @ApiModelProperty(value = "null")
    private Date createtime;
    @ApiModelProperty(value = "null")
    private Date updatetime;

    @ApiModelProperty(value = "楼幢代码")
    private String lddm;
    @ApiModelProperty(value = "楼幢坐落")
    private String ldzl;
    @ApiModelProperty(value = "用途名称")
    private String ytmc;
    @ApiModelProperty(value = "批准用途")
    private String pzyt;
    @ApiModelProperty(value = "实际用途")
    private String sjyt;

    @XmlAttribute(name = "LDDM")
    public String getLddm() {
        return lddm;
    }

    public void setLddm(String lddm) {
        this.lddm = lddm;
    }
    @XmlAttribute(name = "LDZL")
    public String getLdzl() {
        return ldzl;
    }

    public void setLdzl(String ldzl) {
        this.ldzl = ldzl;
    }
    @XmlAttribute(name = "YTMC")
    public String getYtmc() {
        return ytmc;
    }

    public void setYtmc(String ytmc) {
        this.ytmc = ytmc;
    }
    @XmlAttribute(name = "PZYT")
    public String getPzyt() {
        return pzyt;
    }

    public void setPzyt(String pzyt) {
        this.pzyt = pzyt;
    }
    @XmlAttribute(name = "SJYT")
    public String getSjyt() {
        return sjyt;
    }

    public void setSjyt(String sjyt) {
        this.sjyt = sjyt;
    }

    @XmlAttribute(name = "YSDM")
    public String getYsdm() {
        return ysdm;
    }

    @XmlAttribute(name = "BSM")
    public Integer getBsm() {
        return bsm;
    }

    public void setBsm(Integer bsm) {
        this.bsm = bsm;
    }

    @XmlAttribute(name = "BDCDYH")
    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @XmlAttribute(name = "ZDDM")
    public String getZddm() {
        return zddm;
    }

    public void setZddm(String zddm) {
        this.zddm = zddm;
    }

    @XmlAttribute(name = "ZRZH")
    public String getZrzh() {
        return zrzh;
    }

    public void setZrzh(String zrzh) {
        this.zrzh = zrzh;
    }

    @XmlAttribute(name = "XMMC")
    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    @XmlAttribute(name = "JZWMC")
    public String getJzwmc() {
        return jzwmc;
    }

    public void setJzwmc(String jzwmc) {
        this.jzwmc = jzwmc;
    }

    @XmlAttribute(name = "JGRQ")
    public Date getJgrq() {
        return jgrq;
    }

    public void setJgrq(Date jgrq) {
        this.jgrq = jgrq;
    }

    @XmlAttribute(name = "JZWGD")
    public Double getJzwgd() {
        return jzwgd;
    }

    public void setJzwgd(Double jzwgd) {
        this.jzwgd = jzwgd;
    }

    @XmlAttribute(name = "ZZDMJ")
    public Double getZzdmj() {
        return zzdmj;
    }

    public void setZzdmj(Double zzdmj) {
        this.zzdmj = zzdmj;
    }

    @XmlAttribute(name = "ZYDMJ")
    public Double getZydmj() {
        return zydmj;
    }

    public void setZydmj(Double zydmj) {
        this.zydmj = zydmj;
    }

    @XmlAttribute(name = "YCJZMJ")
    public Double getYcjzmj() {
        return ycjzmj;
    }

    public void setYcjzmj(Double ycjzmj) {
        this.ycjzmj = ycjzmj;
    }

    @XmlAttribute(name = "SCJZMJ")
    public Double getScjzmj() {
        return scjzmj;
    }

    public void setScjzmj(Double scjzmj) {
        this.scjzmj = scjzmj;
    }

    @XmlAttribute(name = "ZCS")
    public String getZcs() {
        return zcs;
    }

    public void setZcs(String zcs) {
        this.zcs = zcs;
    }

    @XmlAttribute(name = "DSCS")
    public String getDscs() {
        return dscs;
    }

    public void setDscs(String dscs) {
        this.dscs = dscs;
    }

    @XmlAttribute(name = "DXCS")
    public String getDxcs() {
        return dxcs;
    }

    public void setDxcs(String dxcs) {
        this.dxcs = dxcs;
    }

    @XmlAttribute(name = "DXSD")
    public Double getDxsd() {
        return dxsd;
    }

    public void setDxsd(Double dxsd) {
        this.dxsd = dxsd;
    }

    @XmlAttribute(name = "GHYT")
    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    @XmlAttribute(name = "FWJG")
    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    @XmlAttribute(name = "ZTS")
    public Integer getZts() {
        return zts;
    }

    public void setZts(Integer zts) {
        this.zts = zts;
    }

    public String getJzwjbyt() {
        return jzwjbyt;
    }

    public void setJzwjbyt(String jzwjbyt) {
        this.jzwjbyt = jzwjbyt;
    }

    @XmlAttribute(name = "DAH")
    public String getDah() {
        return dah;
    }

    public void setDah(String dah) {
        this.dah = dah;
    }

    @XmlAttribute(name = "BZ")
    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @XmlAttribute(name = "ZT")
    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
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
