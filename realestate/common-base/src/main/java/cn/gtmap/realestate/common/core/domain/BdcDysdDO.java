package cn.gtmap.realestate.common.core.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/31
 * @description 不动产单元锁定
 */
@Table(name = "BDC_DYSD")
public class BdcDysdDO {
    @Id
    /**单元锁定id*/
    @ApiModelProperty(value = "单元锁定ID")
    private String dysdid;
    /**不动产单元号*/
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    /**不动产单元号*/
    @ApiModelProperty(value = "不动产单元唯一编号")
    private String bdcdywybh;
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
    /**锁定人ID*/
    @ApiModelProperty(value = "锁定人ID")
    private String sdrid;
    /**锁定时间*/
    @ApiModelProperty(value = "锁定时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date sdsj;
    /**解锁人*/
    @ApiModelProperty(value = "解锁人")
    private String jsr;
    /**解锁人ID*/
    @ApiModelProperty(value = "解锁人ID")
    private String jsrid;
    /**解锁时间*/
    @ApiModelProperty(value = "解锁时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date jssj;
    /**解锁原因*/
    @ApiModelProperty(value = "解锁原因")
    private String jsyy;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;
    /**
     * 锁定申请机关
     */
    @ApiModelProperty(value = "锁定申请机关")
    private String sdsqjg;
    /**
     * 锁定申请文号
     */
    @ApiModelProperty(value = "锁定申请文号")
    private String sdsqwh;
    @ApiModelProperty(value = "锁定类型")
    private Integer sdlx;

    @ApiModelProperty("项目id")
    private String xmid;

    @ApiModelProperty("工作流实例id")
    private String gzlslid;

    @ApiModelProperty("产权项目id--此字段常州地区司法裁定用，其他地区慎用")
    private String cqxmid;

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

    public String getDysdid() {
        return dysdid;
    }

    public void setDysdid(String dysdid) {
        this.dysdid = dysdid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
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

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

    public Integer getSdlx() {
        return sdlx;
    }

    public void setSdlx(Integer sdlx) {
        this.sdlx = sdlx;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getCqxmid() {
        return cqxmid;
    }

    public void setCqxmid(String cqxmid) {
        this.cqxmid = cqxmid;
    }

    @Override
    public String toString() {
        return "BdcDysdDO{" +
                "dysdid='" + dysdid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdywybh='" + bdcdywybh + '\'' +
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
                ", sdlx=" + sdlx +
                ", xmid='" + xmid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", cqxmid='" + cqxmid + '\'' +
                '}';
    }
}
