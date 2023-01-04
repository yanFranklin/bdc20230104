package cn.gtmap.realestate.common.core.dto.certificate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "BdcYzhExcelDTO", description = "不动产印制号及明细DTO对象")
public class BdcYzhExcelDTO {
    @ApiModelProperty(value = "领取部门")
    String lqbm;
    @ApiModelProperty(value = "领取人")
    String lqr;
    @ApiModelProperty(value = "证书类型")
    Integer zslx;
    @ApiModelProperty(value = "权证印刷序列号")
    String qzysxlh;
    @ApiModelProperty(value = "使用情况")
    Integer syqk;
    @ApiModelProperty(value = "使用原因")
    String syyy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "使用时间",example = "2018-10-01 12:18:48")
    Date sysj;

    public String getLqbm() {
        return lqbm;
    }

    public void setLqbm(String lqbm) {
        this.lqbm = lqbm;
    }

    public String getLqr() {
        return lqr;
    }

    public void setLqr(String lqr) {
        this.lqr = lqr;
    }

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public String getQzysxlh() {
        return qzysxlh;
    }

    public void setQzysxlh(String qzysxlh) {
        this.qzysxlh = qzysxlh;
    }

    public Integer getSyqk() {
        return syqk;
    }

    public void setSyqk(Integer syqk) {
        this.syqk = syqk;
    }

    public String getSyyy() {
        return syyy;
    }

    public void setSyyy(String syyy) {
        this.syyy = syyy;
    }

    public Date getSysj() {
        return sysj;
    }

    public void setSysj(Date sysj) {
        this.sysj = sysj;
    }

    @Override
    public String toString() {
        return "BdcYzhExcelDTO{" +
                "lqbm='" + lqbm + '\'' +
                ", lqr='" + lqr + '\'' +
                ", zslx=" + zslx +
                ", qzysxlh='" + qzysxlh + '\'' +
                ", syqk=" + syqk +
                ", syyy=" + syyy +
                ", sysj=" + sysj +
                '}';
    }
}
