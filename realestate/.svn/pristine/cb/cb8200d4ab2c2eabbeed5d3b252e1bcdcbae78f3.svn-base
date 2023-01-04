package cn.gtmap.realestate.exchange.core.dto.yhts;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("抵押信息")
public class YhDyTsxxDTO extends YhTsxxDTO {
    @ApiModelProperty(value = "交行抵押证明号")
    private String tzyhdyzmh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "抵押金额")
    private Double dyje;

    @ApiModelProperty(value = "抵押方式")
    private Integer dyfs;

    @ApiModelProperty(value = "债务履行起始时间", example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date zwlxqssj;

    @ApiModelProperty(value = "债务履行结束时间", example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date zwlxjssj;

    @ApiModelProperty(value = "登记时间", example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date djsj;

    public String getTzyhdyzmh() {
        return tzyhdyzmh;
    }

    public void setTzyhdyzmh(String tzyhdyzmh) {
        this.tzyhdyzmh = tzyhdyzmh;
    }

    public Double getDyje() {
        return dyje;
    }

    public void setDyje(Double dyje) {
        this.dyje = dyje;
    }

    public Integer getDyfs() {
        return dyfs;
    }

    public void setDyfs(Integer dyfs) {
        this.dyfs = dyfs;
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

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }
}
