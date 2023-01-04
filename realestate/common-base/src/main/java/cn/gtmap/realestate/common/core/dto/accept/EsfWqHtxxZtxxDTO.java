package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/12/16/16:52
 * @Description:
 */
public class EsfWqHtxxZtxxDTO {
    @ApiModelProperty(value = "证件类型")
    private String zjlx;
    @ApiModelProperty(value = "证件号")
    private String zjh;
    @ApiModelProperty(value = "电话号码")
    private String dhhm;
    @ApiModelProperty(value = "主体名称")
    private String ztmc;
    @ApiModelProperty(value = "主体类型 0是权利人1是义务人")
    private String ztlb;

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getDhhm() {
        return dhhm;
    }

    public void setDhhm(String dhhm) {
        this.dhhm = dhhm;
    }

    public String getZtmc() {
        return ztmc;
    }

    public void setZtmc(String ztmc) {
        this.ztmc = ztmc;
    }

    public String getZtlb() {
        return ztlb;
    }

    public void setZtlb(String ztlb) {
        this.ztlb = ztlb;
    }

    @Override
    public String toString() {
        return "EsfWqHtxxZtxxDTO{" +
                "zjlx='" + zjlx + '\'' +
                ", zjh='" + zjh + '\'' +
                ", dhhm='" + dhhm + '\'' +
                ", ztmc='" + ztmc + '\'' +
                ", ztlb='" + ztlb + '\'' +
                '}';
    }
}
