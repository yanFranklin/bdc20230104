package cn.gtmap.realestate.common.core.qo.etl;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class HtbaspfQO {
    @ApiModelProperty("合同编号")
    private String htbh;
    @ApiModelProperty("坐落")
    private String zl;

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBaid() {
        return baid;
    }

    public void setBaid(String baid) {
        this.baid = baid;
    }

    public String getZxshr() {
        return zxshr;
    }

    public void setZxshr(String zxshr) {
        this.zxshr = zxshr;
    }

    public String getZxsj() {
        return zxsj;
    }

    public void setZxsj(String zxsj) {
        this.zxsj = zxsj;
    }

    public String getZxyy() {
        return zxyy;
    }

    public void setZxyy(String zxyy) {
        this.zxyy = zxyy;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getFwbm() {
        return fwbm;
    }

    public void setFwbm(String fwbm) {
        this.fwbm = fwbm;
    }

    public Integer getBazt() {
        return bazt;
    }

    public void setBazt(Integer bazt) {
        this.bazt = bazt;
    }

    public Date getBasj() {
        return basj;
    }

    public void setBasj(Date basj) {
        this.basj = basj;
    }


    @ApiModelProperty("备案id")
    private String baid;
    @ApiModelProperty("权利人名称")
    private String qlrmc;
    @ApiModelProperty("房屋编码")
    private String fwbm;
    @ApiModelProperty("备案状态")
    private Integer bazt;
    @ApiModelProperty("备案时间")
    private Date basj;

    @Override
    public String toString() {
        return "HtbaspfQO{" +
                "htbh='" + htbh + '\'' +
                ", zl='" + zl + '\'' +
                ", baid='" + baid + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", fwbm='" + fwbm + '\'' +
                ", bazt=" + bazt +
                ", basj=" + basj +
                ", zxshr='" + zxshr + '\'' +
                ", zxsj='" + zxsj + '\'' +
                ", zxyy='" + zxyy + '\'' +
                ", fjid='" + fjid + '\'' +
                '}';
    }

    @ApiModelProperty("注销审核人")
    private String zxshr;
    @ApiModelProperty("注销时间")
    private String zxsj;
    @ApiModelProperty("注销原因")
    private String zxyy;

    public String getFjid() {
        return fjid;
    }

    public void setFjid(String fjid) {
        this.fjid = fjid;
    }


    @ApiModelProperty("附件id")
    private String fjid;

}
