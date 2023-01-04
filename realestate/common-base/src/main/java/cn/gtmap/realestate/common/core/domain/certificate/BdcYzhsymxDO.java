package cn.gtmap.realestate.common.core.domain.certificate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/4
 * @description 不动产印制号使用明细
 */
@Table(name = "bdc_yzhsymx")
@ApiModel(value = "BdcYzhsymxDO", description = "不动产印制号使用明细对象")
public class BdcYzhsymxDO {
    @Id
    @ApiModelProperty(value = "主键")
    String yzhsymxid;
    @ApiModelProperty(value = "印制号ID")
    String yzhid;
    @ApiModelProperty(value = "印制号使用情况")
    Integer syqk;
    @ApiModelProperty(value = "使用人")
    String syr;
    @ApiModelProperty(value = "使用人ID")
    String syrid;
    @ApiModelProperty(value = "使用时间",example = "2018-10-01 12:18:48")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date sysj;
    @ApiModelProperty(value = "使用原因")
    String syyy;
    @ApiModelProperty(value = "使用部门")
    String sybmmc;
    @ApiModelProperty(value = "受理编号")
    String slbh;
    @ApiModelProperty(value = "作废原因")
    Integer zfyy;

    public String getSybmmc() {
        return sybmmc;
    }

    public void setSybmmc(String sybmmc) {
        this.sybmmc = sybmmc;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getYzhsymxid() {
        return yzhsymxid;
    }

    public void setYzhsymxid(String yzhsymxid) {
        this.yzhsymxid = yzhsymxid;
    }

    public String getSyr() {
        return syr;
    }

    public void setSyr(String syr) {
        this.syr = syr;
    }

    public String getSyrid() {
        return syrid;
    }

    public void setSyrid(String syrid) {
        this.syrid = syrid;
    }

    public Date getSysj() {
        return sysj;
    }

    public void setSysj(Date sysj) {
        this.sysj = sysj;
    }

    public String getSyyy() {
        return syyy;
    }

    public void setSyyy(String syyy) {
        this.syyy = syyy;
    }

    public String getYzhid() {
        return yzhid;
    }

    public void setYzhid(String yzhid) {
        this.yzhid = yzhid;
    }

    public Integer getSyqk() {
        return syqk;
    }

    public void setSyqk(Integer syqk) {
        this.syqk = syqk;
    }

    public Integer getZfyy() {
        return zfyy;
    }

    public void setZfyy(Integer zfyy) {
        this.zfyy = zfyy;
    }

    @Override
    public String toString() {
        return "BdcYzhsymxDO{" +
                "yzhsymxid='" + yzhsymxid + '\'' +
                ", yzhid='" + yzhid + '\'' +
                ", syqk=" + syqk +
                ", syr='" + syr + '\'' +
                ", syrid='" + syrid + '\'' +
                ", sysj=" + sysj +
                ", syyy='" + syyy + '\'' +
                ", sybmmc='" + sybmmc + '\'' +
                ", slbh='" + slbh + '\'' +
                ", zfyy=" + zfyy +
                '}';
    }
}
