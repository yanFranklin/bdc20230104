package cn.gtmap.realestate.common.core.qo.etl;

import io.swagger.annotations.ApiModelProperty;

/**
 * @program: realestate
 * @description: 合同备案信息查询条件
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-14 14:50
 **/
public class HtbaxxQO {

    @ApiModelProperty("受理编号")
    private String slbh;
    @ApiModelProperty("合同编号")
    private String htbh;
    @ApiModelProperty("坐落")
    private String zl;
    @ApiModelProperty("备案id")
    private String baid;
    @ApiModelProperty("权利人名称")
    private String qlrmc;
    @ApiModelProperty("证件号")
    private String zjh;
    @ApiModelProperty("房屋id")
    private String fwid;
    @ApiModelProperty("不动产单元号")
    private String bdcdyh;
    @ApiModelProperty("备案状态")
    private Integer bazt;
    @ApiModelProperty(name = "备案时间起")
    private String basjq;
    @ApiModelProperty(name = "备案时间止")
    private String basjz;
    @ApiModelProperty(name = "同步状态")
    private String tbzt;

    public String getTbzt() {
        return tbzt;
    }

    public void setTbzt(String tbzt) {
        this.tbzt = tbzt;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

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

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getFwid() {
        return fwid;
    }

    public void setFwid(String fwid) {
        this.fwid = fwid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public Integer getBazt() {
        return bazt;
    }

    public void setBazt(Integer bazt) {
        this.bazt = bazt;
    }

    public String getBasjq() {
        return basjq;
    }

    public void setBasjq(String basjq) {
        this.basjq = basjq;
    }

    public String getBasjz() {
        return basjz;
    }

    public void setBasjz(String basjz) {
        this.basjz = basjz;
    }

    @Override
    public String toString() {
        return "HtbaxxQO{" +
                "slbh='" + slbh + '\'' +
                ", htbh='" + htbh + '\'' +
                ", zl='" + zl + '\'' +
                ", baid='" + baid + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", zjh='" + zjh + '\'' +
                ", fwid='" + fwid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bazt=" + bazt +
                ", basjq='" + basjq + '\'' +
                ", basjz='" + basjz + '\'' +
                '}';
    }
}
