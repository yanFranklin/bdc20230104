package cn.gtmap.realestate.common.core.dto.exchange;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/4/26.
 * @description
 */
public class BdcAccessLogDTO {
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
     * 响应报文
     */
    @ApiModelProperty(value = "响应报文")
    private String xybw;

    @ApiModelProperty(value = "上次更新时间")
    private Date updatetime;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcAccessLog{");
        sb.append("ywbwid='").append(ywbwid).append('\'');
        sb.append(", ywlsh='").append(ywlsh).append('\'');
        sb.append(", ywdm='").append(ywdm).append('\'');
        sb.append(", bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", cgbs=").append(cgbs);
        sb.append(", xybm='").append(xybm).append('\'');
        sb.append(", xyxx='").append(xyxx).append('\'');
        sb.append(", fjxx='").append(fjxx).append('\'');
        sb.append(", fjxx1='").append(fjxx1).append('\'');
        sb.append(", fwewm='").append(fwewm).append('\'');
        sb.append(", jrbw='").append(jrbw).append('\'');
        sb.append(", jrrq=").append(jrrq);
        sb.append('}');
        return sb.toString();
    }

    public String getXybw() {
        return xybw;
    }

    public void setXybw(String xybw) {
        this.xybw = xybw;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
