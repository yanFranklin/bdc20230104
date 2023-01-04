package cn.gtmap.realestate.common.core.dto.inquiry;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/7/15
 * @description  住房查询——房产档案：抵押权信息
 */
public class BdcFcdaDyaqDTO {
    @ApiModelProperty(value = "不动产证明号")
    private String bdcqzh;

    @ApiModelProperty(value = "抵押权人")
    private String qlr;

    @ApiModelProperty(value = "房屋抵押面积（单位：平方米）")
    private Double fwdymj;

    @ApiModelProperty(value = "抵押方式")
    private Integer dyfs;

    @ApiModelProperty(value = "被担保主债权数额（单位：万元）")
    private Double bdbzzqse;

    @ApiModelProperty(value = "最高债权数额（单位：万元）")
    private Double zgzqe;

    @ApiModelProperty(value = "债务履行开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date zwlxqssj;

    @ApiModelProperty(value = "债务履行结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date zwlxjssj;

    @ApiModelProperty(value = "登记时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date djsj;

    @ApiModelProperty(value = "附记")
    private String fj;

    @ApiModelProperty(value = "禁止转让")
    private Integer jzzr;

    public Integer getJzzr() {
        return jzzr;
    }

    public void setJzzr(Integer jzzr) {
        this.jzzr = jzzr;
    }

    public Double getZgzqe() {
        return zgzqe;
    }

    public void setZgzqe(Double zgzqe) {
        this.zgzqe = zgzqe;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public Double getFwdymj() {
        return fwdymj;
    }

    public void setFwdymj(Double fwdymj) {
        this.fwdymj = fwdymj;
    }

    public Integer getDyfs() {
        return dyfs;
    }

    public void setDyfs(Integer dyfs) {
        this.dyfs = dyfs;
    }

    public Double getBdbzzqse() {
        return bdbzzqse;
    }

    public void setBdbzzqse(Double bdbzzqse) {
        this.bdbzzqse = bdbzzqse;
    }

    public Date getZwlxqssj() {
        return zwlxqssj;
    }

    public void setZwlxqssj(Date zwlxqssj) {
        this.zwlxqssj = zwlxqssj;
    }

    public Date getZwlxjssj() {
        return zwlxjssj;
    }

    public void setZwlxjssj(Date zwlxjssj) {
        this.zwlxjssj = zwlxjssj;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    @Override
    public String toString() {
        return "BdcFcdaDyaqDTO{" +
                "bdcqzh='" + bdcqzh + '\'' +
                ", qlr='" + qlr + '\'' +
                ", fwdymj=" + fwdymj +
                ", dyfs=" + dyfs +
                ", bdbzzqse=" + bdbzzqse +
                ", zwlxqssj=" + zwlxqssj +
                ", zwlxjssj=" + zwlxjssj +
                ", djsj=" + djsj +
                ", fj='" + fj + '\'' +
                '}';
    }
}
