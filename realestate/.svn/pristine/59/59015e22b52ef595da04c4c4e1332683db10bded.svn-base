package cn.gtmap.realestate.exchange.core.dto.yhts.dy.request;

import cn.gtmap.realestate.exchange.core.dto.yhts.YhDyTsxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yhts.YhTsxxDTO;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel("抵押权推送银行请求dto")
public class DyqTsxxDTO extends YhTsxxDTO {
    @ApiModelProperty(value = "抵押人")
    List<DyqDyrDTO> dyrList;

    @ApiModelProperty(value = "抵押权人")
    List<DyqDyqrDTO> dyqrList;

    @ApiModelProperty(value = "不动产权证号")
    private String cqzh;

    @ApiModelProperty(value = "抵押起始时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date zwlxqssj;

    @ApiModelProperty(value = "抵押结束时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date zwlxjssj;

    @ApiModelProperty(value = "抵押方式")
    private Integer dyfs;

    @ApiModelProperty(value = "抵押金额")
    private Double dyje;

    @ApiModelProperty(value = "登记时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date djsj;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "抵押信息")
    List<YhDyTsxxDTO> yhDyxxList;

    public List<DyqDyrDTO> getDyrList() {
        return dyrList;
    }

    public void setDyrList(List<DyqDyrDTO> dyrList) {
        this.dyrList = dyrList;
    }

    public List<DyqDyqrDTO> getDyqrList() {
        return dyqrList;
    }

    public void setDyqrList(List<DyqDyqrDTO> dyqrList) {
        this.dyqrList = dyqrList;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
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

    public Integer getDyfs() {
        return dyfs;
    }

    public void setDyfs(Integer dyfs) {
        this.dyfs = dyfs;
    }

    public Double getDyje() {
        return dyje;
    }

    public void setDyje(Double dyje) {
        this.dyje = dyje;
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

    public List<YhDyTsxxDTO> getYhDyxxList() {
        return yhDyxxList;
    }

    public void setYhDyxxList(List<YhDyTsxxDTO> yhDyxxList) {
        this.yhDyxxList = yhDyxxList;
    }
}
