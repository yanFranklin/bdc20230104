package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018-11-13
 * @description 查询不动产单元状态返回的DTO
 */
@ApiModel(value = "BdcdyhZtResponseDTO", description = "不动产单元状态返回实体")
public class BdcdyhZtResponseDTO {

    /**
     * 宗地状态
     */
    private BdcdyhZtResponseDTO zdZtDTO;

    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    @Id
    private String bdcdyh;

    /**
     * 登记状态
     */
    @ApiModelProperty(value = "登记状态")
    private Boolean dj;

    /**
     * 注销状态
     */
    @ApiModelProperty(value = "注销状态")
    private Boolean zx;

    /**
     * 现势在建工程抵押
     */
    @ApiModelProperty(value = "现势在建工程抵押")
    private Boolean zjgcdy;
     /**
     * 现势预告
     */
    @ApiModelProperty(value = "现势预告")
    private Boolean yg;
    /**
     * 现势预抵押
     */
    @ApiModelProperty(value = "现势预抵押")
    private Boolean ydya;
    /**
     * 现势抵押
     */
    @ApiModelProperty(value = "现势抵押")
    private Boolean dya;
    /**
     * 现势预查封
     */
    @ApiModelProperty(value = "现势预查封")
    private Boolean ycf;
    /**
     * 现势查封
     */
    @ApiModelProperty(value = "现势查封")
    private Boolean cf;
    /**
     * 现势异议
     */
    @ApiModelProperty(value = "现势异议")
    private Boolean yy;
    /**
     * 现势地役
     */
    @ApiModelProperty(value = "现势地役")
    private Boolean dyi;
    /**
     * 现势锁定
     */
    @ApiModelProperty(value = "现势锁定")
    private Boolean sd;
    /**
     * 可售
     */
    @ApiModelProperty(value = "可售")
    private Boolean ks;
    /**
     * 已售
     */
    @ApiModelProperty(value = "已售")
    private Boolean ys;
    /**
     * 新建商品房可售
     */
    @ApiModelProperty(value = "新建商品房可售")
    private Boolean xjspfks;
    /**
     * 新建商品房已售
     */
    @ApiModelProperty(value = "新建商品房已售")
    private Boolean xjspfys;
    /**
     * 存量房可售
     */
    @ApiModelProperty(value = "存量房可售")
    private Boolean clfks;
    /**
     * 存量房已售
     */
    @ApiModelProperty(value = "存量房已售")
    private Boolean clfys;

    /**
     * 现势居住权
     */
    @ApiModelProperty(value = "现势居住权")
    private Boolean jzq;

    /**
     * 房屋拆迁
     */
    @ApiModelProperty(value = "房屋拆迁")
    private Boolean fwcq;

    /**
     * 林权流转状态
     */
    @ApiModelProperty(value = "林权流转状态")
    private Boolean lqlzzt;

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

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public Boolean getDj() {
        return dj;
    }

    public void setDj(Boolean dj) {
        this.dj = dj;
    }

    public Boolean getZjgcdy() {
        return zjgcdy;
    }

    public void setZjgcdy(Boolean zjgcdy) {
        this.zjgcdy = zjgcdy;
    }

    public Boolean getYg() {
        return yg;
    }

    public void setYg(Boolean yg) {
        this.yg = yg;
    }

    public Boolean getYdya() {
        return ydya;
    }

    public void setYdya(Boolean ydya) {
        this.ydya = ydya;
    }

    public Boolean getDya() {
        return dya;
    }

    public void setDya(Boolean dya) {
        this.dya = dya;
    }

    public Boolean getYcf() {
        return ycf;
    }

    public void setYcf(Boolean ycf) {
        this.ycf = ycf;
    }

    public Boolean getCf() {
        return cf;
    }

    public void setCf(Boolean cf) {
        this.cf = cf;
    }

    public Boolean getYy() {
        return yy;
    }

    public void setYy(Boolean yy) {
        this.yy = yy;
    }

    public Boolean getDyi() {
        return dyi;
    }

    public void setDyi(Boolean dyi) {
        this.dyi = dyi;
    }

    public Boolean getSd() {
        return sd;
    }

    public void setSd(Boolean sd) {
        this.sd = sd;
    }

    public Boolean getKs() {
        return ks;
    }

    public void setKs(Boolean ks) {
        this.ks = ks;
    }

    public Boolean getYs() {
        return ys;
    }

    public void setYs(Boolean ys) {
        this.ys = ys;
    }

    public Boolean getXjspfks() {
        return xjspfks;
    }

    public void setXjspfks(Boolean xjspfks) {
        this.xjspfks = xjspfks;
    }

    public Boolean getXjspfys() {
        return xjspfys;
    }

    public void setXjspfys(Boolean xjspfys) {
        this.xjspfys = xjspfys;
    }

    public Boolean getClfks() {
        return clfks;
    }

    public void setClfks(Boolean clfks) {
        this.clfks = clfks;
    }

    public Boolean getClfys() {
        return clfys;
    }

    public void setClfys(Boolean clfys) {
        this.clfys = clfys;
    }
    public Boolean getZx() {
        return zx;
    }

    public void setZx(Boolean zx) {
        this.zx = zx;
    }

    public Boolean getJzq() {
        return jzq;
    }

    public void setJzq(Boolean jzq) {
        this.jzq = jzq;
    }

    public Boolean getFwcq() {
        return fwcq;
    }

    public void setFwcq(Boolean fwcq) {
        this.fwcq = fwcq;
    }

    public Boolean getLqlzzt() {
        return lqlzzt;
    }

    public void setLqlzzt(Boolean lqlzzt) {
        this.lqlzzt = lqlzzt;
    }

    public Integer getXszjgcdycs() { return xszjgcdycs; }

    public void setXszjgcdycs(Integer xszjgcdycs) { this.xszjgcdycs = xszjgcdycs; }

    public Integer getXsygcs() { return xsygcs; }

    public void setXsygcs(Integer xsygcs) { this.xsygcs = xsygcs; }

    public Integer getXsydyacs() { return xsydyacs; }

    public void setXsydyacs(Integer xsydyacs) { this.xsydyacs = xsydyacs; }

    public Integer getXsdyacs() { return xsdyacs; }

    public void setXsdyacs(Integer xsdyacs) { this.xsdyacs = xsdyacs; }

    public Integer getXsycfcs() { return xsycfcs; }

    public void setXsycfcs(Integer xsycfcs) { this.xsycfcs = xsycfcs; }

    public Integer getXscfcs() { return xscfcs; }

    public void setXscfcs(Integer xscfcs) { this.xscfcs = xscfcs; }

    public Integer getXsyycs() { return xsyycs; }

    public void setXsyycs(Integer xsyycs) { this.xsyycs = xsyycs; }

    public Integer getXsdyics() { return xsdyics; }

    public void setXsdyics(Integer xsdyics) { this.xsdyics = xsdyics; }

    public Integer getXssdcs() { return xssdcs; }

    public void setXssdcs(Integer xssdcs) { this.xssdcs = xssdcs; }

    public Integer getXsjzqcs() { return xsjzqcs; }

    public void setXsjzqcs(Integer xsjzqcs) { this.xsjzqcs = xsjzqcs; }

    @Override
    public String toString() {
        return "BdcdyhZtResponseDTO{" +
                "zdZtDTO=" + zdZtDTO +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", dj=" + dj +
                ", zx=" + zx +
                ", zjgcdy=" + zjgcdy +
                ", yg=" + yg +
                ", ydya=" + ydya +
                ", dya=" + dya +
                ", ycf=" + ycf +
                ", cf=" + cf +
                ", yy=" + yy +
                ", dyi=" + dyi +
                ", sd=" + sd +
                ", ks=" + ks +
                ", ys=" + ys +
                ", xjspfks=" + xjspfks +
                ", xjspfys=" + xjspfys +
                ", clfks=" + clfks +
                ", clfys=" + clfys +
                ", jzq=" + jzq +
                ", fwcq=" + fwcq +
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
                '}';
    }

    public BdcdyhZtResponseDTO getZdZtDTO() {
        return zdZtDTO;
    }

    public void setZdZtDTO(BdcdyhZtResponseDTO zdZtDTO) {
        this.zdZtDTO = zdZtDTO;
    }
}
