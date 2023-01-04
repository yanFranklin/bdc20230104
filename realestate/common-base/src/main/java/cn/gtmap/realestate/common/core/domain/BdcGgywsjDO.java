package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
 * @version 1.0  2021/4/19
 * @description 不动产公告信息
 */
@Table(name = "BDC_GG_YWSJ")
public class BdcGgywsjDO {

    @Id
    @ApiModelProperty(value = "主键")
    private String ywsjid;

    @ApiModelProperty(value = "项目id")
    private String xmid;

    @ApiModelProperty(value = "公告id")
    private String ggid;

    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "权利类型")
    private String qllx;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "面积")
    private String zdzhmj;

    @ApiModelProperty(value = "用途")
    private String dzwyt;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;
    @ApiModelProperty(value = "更正原因")
    private String gzyy;
    @ApiModelProperty(value = "撤销原因")
    private String cxyy;
    @ApiModelProperty(value = "注销原因")
    private String zxyy;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "义务人")
    private String ywr;

    @ApiModelProperty(value = "原产权证号")
    private String ycqzh;

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getGzyy() {
        return gzyy;
    }

    public void setGzyy(String gzyy) {
        this.gzyy = gzyy;
    }

    public String getCxyy() {
        return cxyy;
    }

    public void setCxyy(String cxyy) {
        this.cxyy = cxyy;
    }

    public String getZxyy() {
        return zxyy;
    }

    public void setZxyy(String zxyy) {
        this.zxyy = zxyy;
    }

    public String getYwsjid() { return ywsjid; }

    public void setYwsjid(String ywsjid) { this.ywsjid = ywsjid; }

    public String getGgid() { return ggid; }

    public void setGgid(String ggid) { this.ggid = ggid; }

    public String getQlr() { return qlr; }

    public void setQlr(String qlr) { this.qlr = qlr; }

    public String getQllx() { return qllx; }

    public void setQllx(String qllx) { this.qllx = qllx; }

    public String getZl() { return zl; }

    public void setZl(String zl) { this.zl = zl; }

    public String getBdcdyh() { return bdcdyh; }

    public void setBdcdyh(String bdcdyh) { this.bdcdyh = bdcdyh; }

    public String getBz() { return bz; }

    public void setBz(String bz) { this.bz = bz; }

    public String getXmid() { return xmid; }

    public void setXmid(String xmid) { this.xmid = xmid; }

    public String getZdzhmj() { return zdzhmj; }

    public void setZdzhmj(String zdzhmj) { this.zdzhmj = zdzhmj; }

    public String getDzwyt() { return dzwyt; }

    public void setDzwyt(String dzwyt) { this.dzwyt = dzwyt; }

    public String getQxdm() { return qxdm; }

    public void setQxdm(String qxdm) { this.qxdm = qxdm; }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }
}
