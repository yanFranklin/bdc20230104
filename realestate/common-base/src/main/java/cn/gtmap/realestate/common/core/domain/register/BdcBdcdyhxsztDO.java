package cn.gtmap.realestate.common.core.domain.register;

import io.swagger.annotations.ApiModel;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018/11/13
 * @description 不动产单元号权利状态
 */
@Table(name = "s_sj_bdcdyhxszt")
@ApiModel(value = "BdcBdcdyhxsztDO", description = "不动产单元状态返回实体")
public class BdcBdcdyhxsztDO {

    /**
     * 不动产单元号
     */
    @Id
    private String bdcdyh;
    /**
     * 不动产类型
     */
    private String bdclx;
    /**
     * 不动产单元状态
     */
    private String bdcdyzt;
    /**
     * 登记状态
     */
    private String djzt;
    /**
     * 权利人
     */
    private String qlr;
    /**
     * 坐落
     */
    private String zl;
    /**
     * 用途
     */
    private String yt;
    /**
     * 现势在建工程抵押次数
     */
    private Integer xszjgcdycs;
    /**
     * 现势预告次数
     */
    private Integer xsygcs;
    /**
     * 现势预抵押次数
     */
    private Integer xsydyacs;
    /**
     * 现势抵押次数
     */
    private Integer xsdyacs;
    /**
     * 现势预查封次数
     */
    private Integer xsycfcs;
    /**
     * 现势查封次数
     */
    private Integer xscfcs;
    /**
     * 现势异议次数
     */
    private Integer xsyycs;
    /**
     * 现势地役次数
     */
    private Integer xsdyics;
    /**
     * 现势锁定次数
     */
    private Integer xssdcs;
    /**
     * 现势居住权次数
     */
    private Integer xsjzqcs;
    /**
     * 可售
     */
    private Integer ks;
    /**
     * 已售
     */
    private Integer ys;
    /**
     * 新建商品房可售
     */
    private Integer xjspfks;
    /**
     * 新建商品房已售
     */
    private Integer xjspfys;
    /**
     * 存量房可售
     */
    private Integer clfks;
    /**
     * 存量房已售
     */
    private Integer clfys;
	/**
	 * 南通需求  增加更新时间
	 */
	private Date gxsj;

	/*
	* 是否备案*/
    private String sfba;

    /**
     * 林权流转状态 1：流转
     */
    private Integer lqlzzt;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    public String getBdcdyzt() {
        return bdcdyzt;
    }

    public void setBdcdyzt(String bdcdyzt) {
        this.bdcdyzt = bdcdyzt;
    }

    public String getDjzt() {
        return djzt;
    }

    public void setDjzt(String djzt) {
        this.djzt = djzt;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }

    public Integer getXszjgcdycs() {
        return xszjgcdycs;
    }

    public void setXszjgcdycs(Integer xszjgcdycs) {
        this.xszjgcdycs = xszjgcdycs;
    }

    public Integer getXsygcs() {
        return xsygcs;
    }

    public void setXsygcs(Integer xsygcs) {
        this.xsygcs = xsygcs;
    }

    public Integer getXsydyacs() {
        return xsydyacs;
    }

    public void setXsydyacs(Integer xsydyacs) {
        this.xsydyacs = xsydyacs;
    }

    public Integer getXsdyacs() {
        return xsdyacs;
    }

    public void setXsdyacs(Integer xsdyacs) {
        this.xsdyacs = xsdyacs;
    }

    public Integer getXsycfcs() {
        return xsycfcs;
    }

    public void setXsycfcs(Integer xsycfcs) {
        this.xsycfcs = xsycfcs;
    }

    public Integer getXscfcs() {
        return xscfcs;
    }

    public void setXscfcs(Integer xscfcs) {
        this.xscfcs = xscfcs;
    }

    public Integer getXsyycs() {
        return xsyycs;
    }

    public void setXsyycs(Integer xsyycs) {
        this.xsyycs = xsyycs;
    }

    public Integer getXsdyics() {
        return xsdyics;
    }

    public void setXsdyics(Integer xsdyics) {
        this.xsdyics = xsdyics;
    }

    public Integer getXssdcs() {
        return xssdcs;
    }

    public void setXssdcs(Integer xssdcs) {
        this.xssdcs = xssdcs;
    }

    public Integer getKs() {
        return ks;
    }

    public void setKs(Integer ks) {
        this.ks = ks;
    }

    public Integer getYs() {
        return ys;
    }

    public void setYs(Integer ys) {
        this.ys = ys;
    }

    public Integer getXjspfks() {
        return xjspfks;
    }

    public void setXjspfks(Integer xjspfks) {
        this.xjspfks = xjspfks;
    }

    public Integer getXjspfys() {
        return xjspfys;
    }

    public void setXjspfys(Integer xjspfys) {
        this.xjspfys =xjspfys;
    }

    public Integer getClfks() {
        return clfks;
    }

    public void setClfks(Integer clfks) {
        this.clfks = clfks;
    }

    public Integer getClfys() {
        return clfys;
    }

    public void setClfys(Integer clfys) {
        this.clfys = clfys;
    }
	
	public Date getGxsj() {
		return gxsj;
	}
	
	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
	}

    public String getSfba() {
        return sfba;
    }

    public void setSfba(String sfba) {
        this.sfba = sfba;
    }

    public Integer getXsjzqcs() {
        return xsjzqcs;
    }

    public void setXsjzqcs(Integer xsjzqcs) {
        this.xsjzqcs = xsjzqcs;
    }

    public Integer getLqlzzt() {
        return lqlzzt;
    }

    public void setLqlzzt(Integer lqlzzt) {
        this.lqlzzt = lqlzzt;
    }

    @Override
    public String toString() {
        return "BdcBdcdyhxsztDO{" +
                "bdcdyh='" + bdcdyh + '\'' +
                ", bdclx='" + bdclx + '\'' +
                ", bdcdyzt='" + bdcdyzt + '\'' +
                ", djzt='" + djzt + '\'' +
                ", qlr='" + qlr + '\'' +
                ", zl='" + zl + '\'' +
                ", yt='" + yt + '\'' +
                ", xszjgcdycs=" + xszjgcdycs +
                ", xsygcs=" + xsygcs +
                ", xsydyacs=" + xsydyacs +
                ", xsdyacs=" + xsdyacs +
                ", xsycfcs=" + xsycfcs +
                ", xscfcs=" + xscfcs +
                ", xsyycs=" + xsyycs +
                ", xsdyics=" + xsdyics +
                ", xssdcs=" + xssdcs +
                ", xsjzqcs=" + xsjzqcs +
                ", ks=" + ks +
                ", ys=" + ys +
                ", xjspfks=" + xjspfks +
                ", xjspfys=" + xjspfys +
                ", clfks=" + clfks +
                ", clfys=" + clfys +
                ", gxsj=" + gxsj +
                ", sfba='" + sfba + '\'' +
                ", lqlzzt=" + lqlzzt +
                '}';
    }
}
