package cn.gtmap.realestate.common.core.dto.init;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.StringJoiner;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/09/07
 * @description 分页查询不动产证书锁定信息的返回对象
 */
@ApiModel(value = "BdcZssdPageResponseDTO",description = "分页查询不动产证书锁定信息的返回对象")
public class BdcZssdPageResponseDTO {
    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "权利类型")
    private String qllx;

    @ApiModelProperty(value = "登记小类")
    private String djxl;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "权利人")
    private String qlrmc;

    @ApiModelProperty(value = "义务人")
    private String ywrmc;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "产权证号")
    private String cqzh;

    @ApiModelProperty(value = "证书锁定ID")
    private String zssdid;

    @ApiModelProperty(value = "锁定状态")
    private Integer sdzt;

    @ApiModelProperty(value = "锁定原因")
    private String sdyy;

    @ApiModelProperty(value = "锁定人")
    private String sdr;

    @ApiModelProperty(value = "锁定时间",example = "2018-10-01 12:18:48")
    private Date sdsj;

    @ApiModelProperty(value = "解锁时间",example = "2018-10-01 12:18:48")
    private Date jssj;

    @ApiModelProperty(value = "解锁人")
    private String jsr;

    @ApiModelProperty(value = "解锁原因")
    private String jsyy;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "锁定类型")
    private Integer sdlx;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getYwrmc() {
        return ywrmc;
    }

    public void setYwrmc(String ywrmc) {
        this.ywrmc = ywrmc;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getZssdid() {
        return zssdid;
    }

    public void setZssdid(String zssdid) {
        this.zssdid = zssdid;
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

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
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

    public Integer getSdlx() {
        return sdlx;
    }

    public void setSdlx(Integer sdlx) {
        this.sdlx = sdlx;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcZssdPageResponseDTO.class.getSimpleName() + "[", "]")
                .add("xmid='" + xmid + "'")
                .add("qllx='" + qllx + "'")
                .add("djxl='" + djxl + "'")
                .add("bdcdyh='" + bdcdyh + "'")
                .add("qlrmc='" + qlrmc + "'")
                .add("ywrmc='" + ywrmc + "'")
                .add("zl='" + zl + "'")
                .add("cqzh='" + cqzh + "'")
                .add("zssdid='" + zssdid + "'")
                .add("sdzt=" + sdzt)
                .add("sdyy='" + sdyy + "'")
                .add("sdr='" + sdr + "'")
                .add("sdsj=" + sdsj)
                .add("jssj=" + jssj)
                .add("jsr='" + jsr + "'")
                .add("jsyy='" + jsyy + "'")
                .add("bz='" + bz + "'")
                .add("sdlx=" + sdlx)
                .toString();
    }
}
