package cn.gtmap.realestate.exchange.core.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import java.util.Date;

public class AccessLogDTO {
    @Id
    /**业务报文ID*/
    @ApiModelProperty(value = "业务报文ID")
    private String ywbwid;
    /**
     * 业务流水号
     */
    @ApiModelProperty(value = "业务流水号")
    private String ywlsh;
    /**
     * 业务代码
     */
    @ApiModelProperty(value = "业务代码")
    private String ywdm;

    @ApiModelProperty(value = "业务号")
    private String ywh;
    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    /**
     * 成功标识
     */
    @ApiModelProperty(value = "成功标识")
    private Integer cgbs;
    /**
     * 响应编码
     */
    @ApiModelProperty(value = "响应编码")
    private String xybm;
    /**
     * 响应信息
     */
    @ApiModelProperty(value = "响应信息")
    private String xyxx;
    /**
     * 附加信息
     */
    @ApiModelProperty(value = "附加信息")
    private String fjxx;
    /**
     * 附加信息1
     */
    @ApiModelProperty(value = "附加信息1")
    private String fjxx1;
    /**
     * 防伪二维码
     */
    @ApiModelProperty(value = "防伪二维码")
    private String fwewm;
    /**
     * 接入报文
     */
    @ApiModelProperty(value = "接入报文")
    private String jrbw;
    /**
     * 接入日期
     */
    @ApiModelProperty(value = "接入日期")
    private Date jrrq;
    /**
     * 权利人
     */
    @ApiModelProperty(value = "权利人")
    private String qlr;
    /**
     * 义务人
     */
    @ApiModelProperty(value = "义务人")
    private String ywr;
    /**
     * 坐落
     */
    @ApiModelProperty(value = "坐落")
    private String zl;
    /**
     * 不动产权证号
     */
    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;
    /**
     * 办结时间
     */
    @ApiModelProperty(value = "办结时间")
    private String bjsj;
    /**
     * 受理编号
     */
    @ApiModelProperty(value = "受理编号")
    private String slbh;

    /**
     * xmid
     */
    @ApiModelProperty(value = "xmid")
    private String xmid;

    /**
     * sblx(上报类型，国家，省级)
     */
    @ApiModelProperty(value = "sblx")
    private String sblx;

    /**
     * 响应报文
     */
    private String xybw;

    public String getSblx() {
        return sblx;
    }

    public void setSblx(String sblx) {
        this.sblx = sblx;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getYwbwid() {
        return ywbwid;
    }

    public void setYwbwid(String ywbwid) {
        this.ywbwid = ywbwid;
    }

    public String getYwlsh() {
        return ywlsh;
    }

    public void setYwlsh(String ywlsh) {
        this.ywlsh = ywlsh;
    }

    public String getYwdm() {
        return ywdm;
    }

    public void setYwdm(String ywdm) {
        this.ywdm = ywdm;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public Integer getCgbs() {
        return cgbs;
    }

    public void setCgbs(Integer cgbs) {
        this.cgbs = cgbs;
    }

    public String getXybm() {
        return xybm;
    }

    public void setXybm(String xybm) {
        this.xybm = xybm;
    }

    public String getXyxx() {
        return xyxx;
    }

    public void setXyxx(String xyxx) {
        this.xyxx = xyxx;
    }

    public String getFjxx() {
        return fjxx;
    }

    public void setFjxx(String fjxx) {
        this.fjxx = fjxx;
    }

    public String getFjxx1() {
        return fjxx1;
    }

    public void setFjxx1(String fjxx1) {
        this.fjxx1 = fjxx1;
    }

    public String getFwewm() {
        return fwewm;
    }

    public void setFwewm(String fwewm) {
        this.fwewm = fwewm;
    }

    public String getJrbw() {
        return jrbw;
    }

    public void setJrbw(String jrbw) {
        this.jrbw = jrbw;
    }

    public Date getJrrq() {
        return jrrq;
    }

    public void setJrrq(Date jrrq) {
        this.jrrq = jrrq;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getBjsj() {
        return bjsj;
    }

    public void setBjsj(String bjsj) {
        this.bjsj = bjsj;
    }

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getXybw() {
        return xybw;
    }

    public void setXybw(String xybw) {
        this.xybw = xybw;
    }
}
