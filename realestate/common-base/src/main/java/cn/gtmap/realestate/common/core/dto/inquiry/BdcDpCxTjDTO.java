package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;


/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/04/08/17:03
 * @Description:
 */
@ApiModel(value = "BdcDpCxTjDTO", description = "不动产大屏统计DTO")
public class BdcDpCxTjDTO {

    @ApiModelProperty("区县代码")
    private String qxdm;

    @ApiModelProperty("区县名称")
    private String qxmc;

    @ApiModelProperty("套数")
    private BigDecimal ts;

    @ApiModelProperty("面积")
    private BigDecimal mj;

    @ApiModelProperty("金额")
    private BigDecimal je;

    @ApiModelProperty("月")
    private String month;

    @ApiModelProperty("年")
    private String year;

    @ApiModelProperty("日")
    private String day;
    @ApiModelProperty("抵押不动产类型")
    private String dybdclx;

    @ApiModelProperty("登记类型名称")
    private String djlxmc;

    @ApiModelProperty("登记类型")
    private String djlx;

    @ApiModelProperty("数量")
    private Integer sl;

    @ApiModelProperty("证书类型")
    private String zslx;

    public String getZslx() {
        return zslx;
    }

    public void setZslx(String zslx) {
        this.zslx = zslx;
    }

    public String getDjlxmc() {
        return djlxmc;
    }

    public void setDjlxmc(String djlxmc) {
        this.djlxmc = djlxmc;
    }

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public BigDecimal getTs() {
        return ts;
    }

    public void setTs(BigDecimal ts) {
        this.ts = ts;
    }

    public BigDecimal getMj() {
        return mj;
    }

    public void setMj(BigDecimal mj) {
        this.mj = mj;
    }

    public BigDecimal getJe() {
        return je;
    }

    public void setJe(BigDecimal je) {
        this.je = je;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDybdclx() {
        return dybdclx;
    }

    public void setDybdclx(String dybdclx) {
        this.dybdclx = dybdclx;
    }

    public String getQxmc() {
        return qxmc;
    }

    public void setQxmc(String qxmc) {
        this.qxmc = qxmc;
    }

    @Override
    public String toString() {
        return "BdcDpCxTjDTO{" +
                "qxdm='" + qxdm + '\'' +
                ", qxmc='" + qxmc + '\'' +
                ", ts=" + ts +
                ", mj=" + mj +
                ", je=" + je +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", day='" + day + '\'' +
                ", dybdclx='" + dybdclx + '\'' +
                ", djlxmc='" + djlxmc + '\'' +
                ", djlx='" + djlx + '\'' +
                ", sl=" + sl +
                '}';
    }
}
