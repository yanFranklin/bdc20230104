package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfrwjs.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/12
 * @description 房源基本信息
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "FYJBXX")
public class YrbZlfFyjbxxRequest {


    /**
     * fyjbxxuuid : 房源基本信息uuid
     * wbid : 外部id
     * xzqhszdm : 行政区划代码
     * jdxzdm : 街道乡镇代码
     * tdfwdz : 土地房屋地址
     * tdsyzbh : 土地使用证编号
     * tdsybh : 土地税源编号
     * fwzh : 房屋栋号
     * dyh : 单元号
     * lc2 : 2
     * fjh : 房间号
     * jzjglxdm : 建筑结构代码
     * cxdm : 朝向代码
     * mj : 2.22
     * tnmj : 2.22
     * glmj : 2.22
     * ccsmj : 2.22
     * zxcckmj : 2.22
     * ckmj : 2.22
     * jznf : 建筑年份
     * dlmc : 道路名称
     * xqmc : 小区名称
     * lczs : 楼层总数
     * fwtdzjg : 2.22
     * fyxxly : 房源信息来源
     * sjgsdq : 数据归属地区
     * bz : 备注
     */

    private String fyjbxxuuid;
    private String wbid;
    private String bdcdyh;
    private String xzqhszdm;
    private String jdxzdm;
    private String tdfwdz;
    private String tdsyzbh;
    private String tdsybh;
    private String fwzh;
    private String dyh;
    private Integer lc2;
    private String fjh;
    private String jzjglxdm;
    private String cxdm;
    private Double mj;
    private Double tnmj;
    private Double glmj;
    private Double ccsmj;
    private Double zxcckmj;
    private Double ckmj;
    private String jznf;
    private String dlmc;
    private String xqmc;
    private String lczs;
    private Double fwtdzjg;
    private String fyxxly;
    private String sjgsdq;
    private String bz;

    @XmlElement(name = "BDCDYH")
    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @XmlElement(name = "FYJBXXUUID")
    public String getFyjbxxuuid() {
        return fyjbxxuuid;
    }

    public void setFyjbxxuuid(String fyjbxxuuid) {
        this.fyjbxxuuid = fyjbxxuuid;
    }

    @XmlElement(name = "WBID")
    public String getWbid() {
        return wbid;
    }

    public void setWbid(String wbid) {
        this.wbid = wbid;
    }

    @XmlElement(name = "XZQHSZDM")
    public String getXzqhszdm() {
        return xzqhszdm;
    }

    public void setXzqhszdm(String xzqhszdm) {
        this.xzqhszdm = xzqhszdm;
    }

    @XmlElement(name = "JDXZDM")
    public String getJdxzdm() {
        return jdxzdm;
    }

    public void setJdxzdm(String jdxzdm) {
        this.jdxzdm = jdxzdm;
    }

    @XmlElement(name = "TDFWDZ")
    public String getTdfwdz() {
        return tdfwdz;
    }

    public void setTdfwdz(String tdfwdz) {
        this.tdfwdz = tdfwdz;
    }

    @XmlElement(name = "TDSYZBH")
    public String getTdsyzbh() {
        return tdsyzbh;
    }

    public void setTdsyzbh(String tdsyzbh) {
        this.tdsyzbh = tdsyzbh;
    }

    @XmlElement(name = "TDSYBH")
    public String getTdsybh() {
        return tdsybh;
    }

    public void setTdsybh(String tdsybh) {
        this.tdsybh = tdsybh;
    }

    @XmlElement(name = "FWZH")
    public String getFwzh() {
        return fwzh;
    }

    public void setFwzh(String fwzh) {
        this.fwzh = fwzh;
    }

    @XmlElement(name = "DYH")
    public String getDyh() {
        return dyh;
    }

    public void setDyh(String dyh) {
        this.dyh = dyh;
    }

    @XmlElement(name = "LC2")
    public Integer getLc2() {
        return lc2;
    }

    public void setLc2(Integer lc2) {
        this.lc2 = lc2;
    }

    @XmlElement(name = "FJH")
    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    @XmlElement(name = "JZJGLXDM")
    public String getJzjglxdm() {
        return jzjglxdm;
    }

    public void setJzjglxdm(String jzjglxdm) {
        this.jzjglxdm = jzjglxdm;
    }

    @XmlElement(name = "CXDM")
    public String getCxdm() {
        return cxdm;
    }

    public void setCxdm(String cxdm) {
        this.cxdm = cxdm;
    }

    @XmlElement(name = "MJ")
    public Double getMj() {
        return mj;
    }

    public void setMj(Double mj) {
        this.mj = mj;
    }

    @XmlElement(name = "TNMJ")
    public Double getTnmj() {
        return tnmj;
    }

    public void setTnmj(Double tnmj) {
        this.tnmj = tnmj;
    }

    @XmlElement(name = "GLMJ")
    public Double getGlmj() {
        return glmj;
    }

    public void setGlmj(Double glmj) {
        this.glmj = glmj;
    }

    @XmlElement(name = "CCSMJ")
    public Double getCcsmj() {
        return ccsmj;
    }

    public void setCcsmj(Double ccsmj) {
        this.ccsmj = ccsmj;
    }

    @XmlElement(name = "ZXCCKMJ")
    public Double getZxcckmj() {
        return zxcckmj;
    }

    public void setZxcckmj(Double zxcckmj) {
        this.zxcckmj = zxcckmj;
    }

    @XmlElement(name = "CKMJ")
    public Double getCkmj() {
        return ckmj;
    }

    public void setCkmj(Double ckmj) {
        this.ckmj = ckmj;
    }

    @XmlElement(name = "JZNF")
    public String getJznf() {
        return jznf;
    }

    public void setJznf(String jznf) {
        this.jznf = jznf;
    }

    @XmlElement(name = "DLMC")
    public String getDlmc() {
        return dlmc;
    }

    public void setDlmc(String dlmc) {
        this.dlmc = dlmc;
    }

    @XmlElement(name = "XQMC")
    public String getXqmc() {
        return xqmc;
    }

    public void setXqmc(String xqmc) {
        this.xqmc = xqmc;
    }

    @XmlElement(name = "LCZS")
    public String getLczs() {
        return lczs;
    }

    public void setLczs(String lczs) {
        this.lczs = lczs;
    }

    @XmlElement(name = "FWTDZJG")
    public Double getFwtdzjg() {
        return fwtdzjg;
    }

    public void setFwtdzjg(Double fwtdzjg) {
        this.fwtdzjg = fwtdzjg;
    }

    @XmlElement(name = "FYXXLY")
    public String getFyxxly() {
        return fyxxly;
    }

    public void setFyxxly(String fyxxly) {
        this.fyxxly = fyxxly;
    }

    @XmlElement(name = "SJGSDQ")
    public String getSjgsdq() {
        return sjgsdq;
    }

    public void setSjgsdq(String sjgsdq) {
        this.sjgsdq = sjgsdq;
    }

    @XmlElement(name = "BZ")
    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }
}
