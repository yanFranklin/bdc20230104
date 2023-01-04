package cn.gtmap.realestate.common.core.qo.certificate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/5
 * @description 不动产印制号使用情况QO
 */
@ApiModel(value = "BdcYzhSyqkQO", description = "不动产印制号使用情况QO")
public class BdcYzhSyqkQO {
    @ApiModelProperty(value = "印制号ID")
    String yzhid;
    @ApiModelProperty(value = "权证印刷序列号")
    String qzysxlh;
    @ApiModelProperty(value = "证书ID")
    String zsid;
    @ApiModelProperty(value = "使用情况")
    Integer syqk;
    @ApiModelProperty(value = "使用人")
    String syr;
    @ApiModelProperty(value = "使用人ID")
    String syrid;
    @ApiModelProperty(value = "使用原因")
    String syyy;
    @ApiModelProperty(value = "受理编号")
    String slbh;
    @ApiModelProperty(value = "使用部门")
    String sybmmc;

    public String getYzhid() {
        return yzhid;
    }

    public void setYzhid(String yzhid) {
        this.yzhid = yzhid;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public Integer getSyqk() {
        return syqk;
    }

    public void setSyqk(Integer syqk) {
        this.syqk = syqk;
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

    public String getSyyy() {
        return syyy;
    }

    public void setSyyy(String syyy) {
        this.syyy = syyy;
    }

    public String getQzysxlh() {
        return qzysxlh;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getSybmmc() {
        return sybmmc;
    }

    public void setSybmmc(String sybmmc) {
        this.sybmmc = sybmmc;
    }

    public void setQzysxlh(String qzysxlh) {
        this.qzysxlh = qzysxlh;
    }

    @Override
    public String toString() {
        return "BdcYzhSyqkQO{" +
                "yzhid='" + yzhid + '\'' +
                ", qzysxlh='" + qzysxlh + '\'' +
                ", zsid='" + zsid + '\'' +
                ", syqk=" + syqk +
                ", syr='" + syr + '\'' +
                ", syrid='" + syrid + '\'' +
                ", syyy='" + syyy + '\'' +
                ", slbh='" + slbh + '\'' +
                ", sybmmc='" + sybmmc + '\'' +
                '}';
    }
}
