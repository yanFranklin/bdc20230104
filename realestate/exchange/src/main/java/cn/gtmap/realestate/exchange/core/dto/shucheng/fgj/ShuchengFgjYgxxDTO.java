package cn.gtmap.realestate.exchange.core.dto.shucheng.fgj;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author wangyinghao
 * @Date 2022/4/6
 * @description 舒城县房管局-一手房登记预告数据DTO
 */
@ApiModel("舒城县房管局-存量房核验信息DTO")
public class ShuchengFgjYgxxDTO {
    @ApiModelProperty("预告登记证明号")
    private String ygdjzmh;

    @ApiModelProperty("预告登记种类")
    private String ygdjzl;

    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "登记时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date djsj;

    @ApiModelProperty(value = "附记")
    private String fj;

    @ApiModelProperty(value = "债务履行起始时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date zwlxjssj;

    @ApiModelProperty(value = "债务履行结束时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date zwlxqssj;

    public String getYgdjzmh() {
        return ygdjzmh;
    }

    public void setYgdjzmh(String ygdjzmh) {
        this.ygdjzmh = ygdjzmh;
    }

    public String getYgdjzl() {
        return ygdjzl;
    }

    public void setYgdjzl(String ygdjzl) {
        this.ygdjzl = ygdjzl;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
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

    public Date getZwlxjssj() {
        return zwlxjssj;
    }

    public void setZwlxjssj(Date zwlxjssj) {
        this.zwlxjssj = zwlxjssj;
    }

    public Date getZwlxqssj() {
        return zwlxqssj;
    }

    public void setZwlxqssj(Date zwlxqssj) {
        this.zwlxqssj = zwlxqssj;
    }
}
