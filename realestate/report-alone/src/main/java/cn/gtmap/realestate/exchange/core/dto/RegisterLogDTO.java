package cn.gtmap.realestate.exchange.core.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 登簿日上报日志 DTO
 */
public class RegisterLogDTO {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "行政区代码")
    private String xzqdm;

    @ApiModelProperty(value = "行政区名称")
    private String xzqmc;

    @ApiModelProperty(value = "日期")
    private String jrrq;

    @ApiModelProperty(value = "国家上报是否成功标识")
    private String cgbs;

    @ApiModelProperty(value = "国家上报是否成功名称")
    private String cgbsmc;

    @ApiModelProperty(value = "省级上报是否成功标识")
    private String sjcgbs;

    @ApiModelProperty(value = "省级上报是否成功名称")
    private String sjcgbsmc;

    @ApiModelProperty(value = "详细")
    private String mx;

    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }

    public String getXzqmc() {
        return xzqmc;
    }

    public void setXzqmc(String xzqmc) {
        this.xzqmc = xzqmc;
    }

    public String getJrrq() {
        return jrrq;
    }

    public void setJrrq(String jrrq) {
        this.jrrq = jrrq;
    }

    public String getCgbsmc() {
        return cgbsmc;
    }

    public void setCgbsmc(String cgbsmc) {
        this.cgbsmc = cgbsmc;
    }

    public String getSjcgbsmc() {
        return sjcgbsmc;
    }

    public void setSjcgbsmc(String sjcgbsmc) {
        this.sjcgbsmc = sjcgbsmc;
    }

    public String getMx() {
        return mx;
    }

    public void setMx(String mx) {
        this.mx = mx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCgbs() {
        return cgbs;
    }

    public void setCgbs(String cgbs) {
        this.cgbs = cgbs;
    }

    public String getSjcgbs() {
        return sjcgbs;
    }

    public void setSjcgbs(String sjcgbs) {
        this.sjcgbs = sjcgbs;
    }
}
