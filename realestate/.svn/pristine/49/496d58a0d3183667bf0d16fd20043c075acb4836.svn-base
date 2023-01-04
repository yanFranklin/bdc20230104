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
@Table(name = "KTT_FW_H")
@XmlRootElement(name = "KTT_FW_H")
@XmlAccessorType(XmlAccessType.NONE)
public class KttFwHDO implements Serializable, AccessData {

    private String ysdm = "6001030140";
    @ApiModelProperty(value = "不动产单元号")
    @Id
    private String bdcdyh;
    @ApiModelProperty(value = "房屋编码/原为非空")
    private String fwbm;
    @ApiModelProperty(value = "自然幢号")
    private String zrzh;
    @ApiModelProperty(value = "逻辑幢号")
    private String ljzh;
    @ApiModelProperty(value = "层号见本")
    private String ch;
    @ApiModelProperty(value = "坐落非空")
    private String zl;
    @ApiModelProperty(value = "面积单位面积单位字典表")
    private String mjdw;
    @ApiModelProperty(value = "实际层数＞0见本表注3")
    private String sjcs;
    @ApiModelProperty(value = "户号＞0见本表注4")
    private String hh;
    @ApiModelProperty(value = "室号部位非空")
    private String shbw;
    @ApiModelProperty(value = "户型户型字典表")
    private String hx;
    @ApiModelProperty(value = "户型结构户型结构字典表")
    private String hxjg;
    @ApiModelProperty(value = "房屋用途1房屋用途字典表")
    private String fwyt1;
    @ApiModelProperty(value = "房屋用途2房屋用途字典表")
    private String fwyt2;
    @ApiModelProperty(value = "房屋用途3房屋用途字典表")
    private String fwyt3;
    @ApiModelProperty(value = "预测建筑面积＞=0见本表注5")
    private Double ycjzmj;
    @ApiModelProperty(value = "预测套内建筑面积＞=0见本表注5")
    private Double yctnjzmj;
    @ApiModelProperty(value = "预测分摊建筑面积＞=0见本表注5")
    private Double ycftjzmj;
    @ApiModelProperty(value = "预测地下部分建筑面积＞=0见本表注5")
    private Double ycdxbfjzmj;
    @ApiModelProperty(value = "预测其它建筑面积＞=0见本表注5")
    private Double ycqtjzmj;
    @ApiModelProperty(value = "预测分摊系数＞=0")
    private Double ycftxs;
    @ApiModelProperty(value = "实测建筑面积 ＞=0见本表注6")
    private Double scjzmj;
    @ApiModelProperty(value = "实测套内建筑面积＞=0见本表注6")
    private Double sctnjzmj;
    @ApiModelProperty(value = "实测分摊建筑面积＞=0见本表注6")
    private Double scftjzmj;
    @ApiModelProperty(value = "实测地下部分建筑面积＞=0见本表注6")
    private Double scdxbfjzmj;
    @ApiModelProperty(value = "实测其它建筑面积＞=0见本表注6")
    private Double scqtjzmj;
    @ApiModelProperty(value = "实测分摊系数＞=0")
    private Double scftxs;
    @ApiModelProperty(value = "共有土地面积＞=0")
    private Double gytdmj;
    @ApiModelProperty(value = "分摊土地面积＞=0")
    private Double fttdmj;
    @ApiModelProperty(value = "独用土地面积＞=0")
    private Double dytdmj;
    @ApiModelProperty(value = "房屋类型房屋类型字典表")
    private String fwlx;
    @ApiModelProperty(value = "房屋性质房屋性质字典表")
    private String fwxz;
    @ApiModelProperty(value = "房产分户图")
    private String fcfht;
    @ApiModelProperty(value = "状态不动产单元状态字典表")
    private String zt;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @ApiModelProperty(value = "null")
    private String fwfht;
    @ApiModelProperty(value = "null")
    private Date createtime;
    @ApiModelProperty(value = "null")
    private Date updatetime;
    @ApiModelProperty(value = "主房不动产单元号")
    private String zfbdcdyh;

    @ApiModelProperty(value = "宗地代码")
    private String zddm;
    @ApiModelProperty(value = "自然幢标识码")
    private Integer zrzbsm;
    @ApiModelProperty(value = "用途名称")
    private String ytmc;
    @ApiModelProperty(value = "批准名称")
    private String pzyt;
    @ApiModelProperty(value = "实际用途")
    private String sjyt;
    @ApiModelProperty(value = "房屋类型名称")
    private String fwlxmc;
    @ApiModelProperty(value = "房屋性质名称")
    private String fwxzmc;


    @XmlAttribute(name = "ZDDM")
    public String getZddm() {
        return zddm;
    }

    public void setZddm(String zddm) {
        this.zddm = zddm;
    }

    @XmlAttribute(name = "ZRZBSM")
    public Integer getZrzbsm() {
        return zrzbsm;
    }

    public void setZrzbsm(Integer zrzbsm) {
        this.zrzbsm = zrzbsm;
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

    @XmlAttribute(name = "FWLXMC")
    public String getFwlxmc() {
        return fwlxmc;
    }

    public void setFwlxmc(String fwlxmc) {
        this.fwlxmc = fwlxmc;
    }

    @XmlAttribute(name = "FWXZMC")
    public String getFwxzmc() {
        return fwxzmc;
    }

    public void setFwxzmc(String fwxzmc) {
        this.fwxzmc = fwxzmc;
    }

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

    @XmlAttribute(name = "FWBM")
    public String getFwbm() {
        return fwbm;
    }

    public void setFwbm(String fwbm) {
        this.fwbm = fwbm;
    }

    @XmlAttribute(name = "ZRZH")
    public String getZrzh() {
        return zrzh;
    }

    public void setZrzh(String zrzh) {
        this.zrzh = zrzh;
    }

    @XmlAttribute(name = "LJZH")
    public String getLjzh() {
        return ljzh;
    }

    public void setLjzh(String ljzh) {
        this.ljzh = ljzh;
    }

    @XmlAttribute(name = "CH")
    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    @XmlAttribute(name = "ZL")
    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    @XmlAttribute(name = "MJDW")
    public String getMjdw() {
        return mjdw;
    }

    public void setMjdw(String mjdw) {
        this.mjdw = mjdw;
    }

    @XmlAttribute(name = "SJCS")
    public String getSjcs() {
        return sjcs;
    }

    public void setSjcs(String sjcs) {
        this.sjcs = sjcs;
    }

    @XmlAttribute(name = "HH")
    public String getHh() {
        return hh;
    }

    public void setHh(String hh) {
        this.hh = hh;
    }

    @XmlAttribute(name = "SHBW")
    public String getShbw() {
        return shbw;
    }

    public void setShbw(String shbw) {
        this.shbw = shbw;
    }

    @XmlAttribute(name = "HX")
    public String getHx() {
        return hx;
    }

    public void setHx(String hx) {
        this.hx = hx;
    }

    @XmlAttribute(name = "HXJG")
    public String getHxjg() {
        return hxjg;
    }

    public void setHxjg(String hxjg) {
        this.hxjg = hxjg;
    }

    @XmlAttribute(name = "FWYT1")
    public String getFwyt1() {
        return fwyt1;
    }

    public void setFwyt1(String fwyt1) {
        this.fwyt1 = fwyt1;
    }

    @XmlAttribute(name = "FWYT2")
    public String getFwyt2() {
        return fwyt2;
    }

    public void setFwyt2(String fwyt2) {
        this.fwyt2 = fwyt2;
    }

    @XmlAttribute(name = "FWYT3")
    public String getFwyt3() {
        return fwyt3;
    }

    public void setFwyt3(String fwyt3) {
        this.fwyt3 = fwyt3;
    }

    @XmlAttribute(name = "YCJZMJ")
    public Double getYcjzmj() {
        return ycjzmj;
    }

    public void setYcjzmj(Double ycjzmj) {
        this.ycjzmj = ycjzmj;
    }

    @XmlAttribute(name = "YCTNJZMJ")
    public Double getYctnjzmj() {
        return yctnjzmj;
    }

    public void setYctnjzmj(Double yctnjzmj) {
        this.yctnjzmj = yctnjzmj;
    }

    @XmlAttribute(name = "YCFTJZMJ")
    public Double getYcftjzmj() {
        return ycftjzmj;
    }

    public void setYcftjzmj(Double ycftjzmj) {
        this.ycftjzmj = ycftjzmj;
    }

    @XmlAttribute(name = "YCDXBFJZMJ")
    public Double getYcdxbfjzmj() {
        return ycdxbfjzmj;
    }

    public void setYcdxbfjzmj(Double ycdxbfjzmj) {
        this.ycdxbfjzmj = ycdxbfjzmj;
    }

    @XmlAttribute(name = "YCQTJZMJ")
    public Double getYcqtjzmj() {
        return ycqtjzmj;
    }

    public void setYcqtjzmj(Double ycqtjzmj) {
        this.ycqtjzmj = ycqtjzmj;
    }

    @XmlAttribute(name = "YCFTXS")
    public Double getYcftxs() {
        return ycftxs;
    }

    public void setYcftxs(Double ycftxs) {
        this.ycftxs = ycftxs;
    }

    @XmlAttribute(name = "SCJZMJ")
    public Double getScjzmj() {
        return scjzmj;
    }

    public void setScjzmj(Double scjzmj) {
        this.scjzmj = scjzmj;
    }

    @XmlAttribute(name = "SCTNJZMJ")
    public Double getSctnjzmj() {
        return sctnjzmj;
    }

    public void setSctnjzmj(Double sctnjzmj) {
        this.sctnjzmj = sctnjzmj;
    }

    @XmlAttribute(name = "SCFTJZMJ")
    public Double getScftjzmj() {
        return scftjzmj;
    }

    public void setScftjzmj(Double scftjzmj) {
        this.scftjzmj = scftjzmj;
    }

    @XmlAttribute(name = "SCDXBFJZMJ")
    public Double getScdxbfjzmj() {
        return scdxbfjzmj;
    }

    public void setScdxbfjzmj(Double scdxbfjzmj) {
        this.scdxbfjzmj = scdxbfjzmj;
    }

    @XmlAttribute(name = "SCQTJZMJ")
    public Double getScqtjzmj() {
        return scqtjzmj;
    }

    public void setScqtjzmj(Double scqtjzmj) {
        this.scqtjzmj = scqtjzmj;
    }

    @XmlAttribute(name = "SCFTXS")
    public Double getScftxs() {
        return scftxs;
    }

    public void setScftxs(Double scftxs) {
        this.scftxs = scftxs;
    }

    @XmlAttribute(name = "GYTDMJ")
    public Double getGytdmj() {
        return gytdmj;
    }

    public void setGytdmj(Double gytdmj) {
        this.gytdmj = gytdmj;
    }

    @XmlAttribute(name = "FTTDMJ")
    public Double getFttdmj() {
        return fttdmj;
    }

    public void setFttdmj(Double fttdmj) {
        this.fttdmj = fttdmj;
    }

    @XmlAttribute(name = "DYTDMJ")
    public Double getDytdmj() {
        return dytdmj;
    }

    public void setDytdmj(Double dytdmj) {
        this.dytdmj = dytdmj;
    }

    @XmlAttribute(name = "FWLX")
    public String getFwlx() {
        return fwlx;
    }

    public void setFwlx(String fwlx) {
        this.fwlx = fwlx;
    }

    @XmlAttribute(name = "FWXZ")
    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    @XmlAttribute(name = "FCFHT")
    public String getFcfht() {
        return fcfht;
    }

    public void setFcfht(String fcfht) {
        this.fcfht = fcfht;
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

    public String getFwfht() {
        return fwfht;
    }

    public void setFwfht(String fwfht) {
        this.fwfht = fwfht;
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

    public String getZfbdcdyh() {
        return zfbdcdyh;
    }

    public void setZfbdcdyh(String zfbdcdyh) {
        this.zfbdcdyh = zfbdcdyh;
    }
}
