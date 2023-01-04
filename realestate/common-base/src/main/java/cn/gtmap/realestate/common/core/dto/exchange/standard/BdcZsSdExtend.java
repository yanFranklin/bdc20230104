package cn.gtmap.realestate.common.core.dto.exchange.standard;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date  2021/10/10
 * @description 证书锁定DO扩展
 */
public class BdcZsSdExtend {

    /**证书锁定id*/
    @ApiModelProperty(value = "证书锁定ID")
    private String zssdid;
    /**通过流程锁定的gzlslid*/
    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;
    /**通过流程锁定的xmid*/
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    /**不产权证号*/
    @ApiModelProperty(value = "不动产权证号")
    private String  cqzh;
    /**证书ID*/
    @ApiModelProperty(value = "证书ID")
    private String zsid;
    /**不动产类型*/
    @ApiModelProperty(value = "不动产类型")
    private Integer bdclx;
    /**锁定状态*/
    @ApiModelProperty(value = "锁定状态")
    private Integer sdzt;
    /**锁定原因*/
    @ApiModelProperty(value = "锁定原因")
    private String sdyy;
    /**锁定人*/
    @ApiModelProperty(value = "锁定人")
    private String sdr;
    /**锁定人id*/
    @ApiModelProperty(value = "锁定人id")
    private String   sdrid;
    /**锁定时间*/
    @ApiModelProperty(value = "锁定时间",example = "2018-10-01 12:18:48")
    private Date sdsj;
    /**解锁人*/
    @ApiModelProperty(value = "解锁人")
    private String jsr;
    /**解锁人id*/
    @ApiModelProperty(value = "解锁人id")
    private String   jsrid;
    /**解锁时间*/
    @ApiModelProperty(value = "解锁时间",example = "2018-10-01 12:18:48")
    private Date jssj;
    /**解锁原因*/
    @ApiModelProperty(value = "解锁原因")
    private String jsyy;
    /**备注*/
    @ApiModelProperty(value = "备注")
    private String bz;
    /**锁定申请机关*/
    @ApiModelProperty(value = "锁定申请机关")
    private String sdsqjg;
    /**锁定申请文号*/
    @ApiModelProperty(value = "锁定申请文号")
    private String sdsqwh;

    @ApiModelProperty(value = "工作流定义名称")
    private String gzldymc;

    @ApiModelProperty(value = "涉及人员名称")
    private String sjrymc;

    @ApiModelProperty(value = "涉及人员证件号")
    private String sjryzjh;


    private String extend;

    public String getZssdid() {
        return zssdid;
    }

    public void setZssdid(String zssdid) {
        this.zssdid = zssdid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public Integer getBdclx() {
        return bdclx;
    }

    public void setBdclx(Integer bdclx) {
        this.bdclx = bdclx;
    }

    public Integer getSdzt() {
        return sdzt;
    }

    public void setSdzt(Integer sdzt) {
        this.sdzt = sdzt;
    }

    public String getSdyy() {
        return sdyy;
    }

    public void setSdyy(String sdyy) {
        this.sdyy = sdyy;
    }

    public String getSdr() {
        return sdr;
    }

    public void setSdr(String sdr) {
        this.sdr = sdr;
    }

    public String getSdrid() {
        return sdrid;
    }

    public void setSdrid(String sdrid) {
        this.sdrid = sdrid;
    }

    public Date getSdsj() {
        return sdsj;
    }

    public void setSdsj(Date sdsj) {
        this.sdsj = sdsj;
    }

    public String getJsr() {
        return jsr;
    }

    public void setJsr(String jsr) {
        this.jsr = jsr;
    }

    public String getJsrid() {
        return jsrid;
    }

    public void setJsrid(String jsrid) {
        this.jsrid = jsrid;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public String getJsyy() {
        return jsyy;
    }

    public void setJsyy(String jsyy) {
        this.jsyy = jsyy;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getSdsqjg() {
        return sdsqjg;
    }

    public void setSdsqjg(String sdsqjg) {
        this.sdsqjg = sdsqjg;
    }

    public String getSdsqwh() {
        return sdsqwh;
    }

    public void setSdsqwh(String sdsqwh) {
        this.sdsqwh = sdsqwh;
    }

    public String getGzldymc() {
        return gzldymc;
    }

    public void setGzldymc(String gzldymc) {
        this.gzldymc = gzldymc;
    }

    public String getSjrymc() {
        return sjrymc;
    }

    public void setSjrymc(String sjrymc) {
        this.sjrymc = sjrymc;
    }

    public String getSjryzjh() {
        return sjryzjh;
    }

    public void setSjryzjh(String sjryzjh) {
        this.sjryzjh = sjryzjh;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    @Override
    public String toString() {
        return "BdcZsSdExtend{" +
                "zssdid='" + zssdid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", cqzh='" + cqzh + '\'' +
                ", zsid='" + zsid + '\'' +
                ", bdclx=" + bdclx +
                ", sdzt=" + sdzt +
                ", sdyy='" + sdyy + '\'' +
                ", sdr='" + sdr + '\'' +
                ", sdrid='" + sdrid + '\'' +
                ", sdsj=" + sdsj +
                ", jsr='" + jsr + '\'' +
                ", jsrid='" + jsrid + '\'' +
                ", jssj=" + jssj +
                ", jsyy='" + jsyy + '\'' +
                ", bz='" + bz + '\'' +
                ", sdsqjg='" + sdsqjg + '\'' +
                ", sdsqwh='" + sdsqwh + '\'' +
                ", gzldymc='" + gzldymc + '\'' +
                ", sjrymc='" + sjrymc + '\'' +
                ", sjryzjh='" + sjryzjh + '\'' +
                ", extend='" + extend + '\'' +
                '}';
    }
}
