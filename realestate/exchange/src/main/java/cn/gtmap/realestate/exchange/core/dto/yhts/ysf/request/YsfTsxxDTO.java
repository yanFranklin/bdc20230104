package cn.gtmap.realestate.exchange.core.dto.yhts.ysf.request;

import cn.gtmap.realestate.exchange.core.dto.yhts.YhTsxxDTO;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel("抵押权推送银行请求dto")
public class YsfTsxxDTO  extends YhTsxxDTO {

    @ApiModelProperty(value = "抵押人")
    List<YsfDyrDTO> dyrList;

    @ApiModelProperty(value = "抵押权人")
    List<YsfDyqrDTO> dyqrList;

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

    @ApiModelProperty(value = "预抵押证明号")
    private String tzyhdydyzmh;

    @ApiModelProperty(value = "共有情况")
    private String gyqk;

    @ApiModelProperty(value = "建筑面积")
    private Double jzmj;

    @ApiModelProperty(value = "竣工时间", example = "2018-10-01")
    private String jgsj;

    @ApiModelProperty(value = "抵押不动产权证号")
    private String dybdcqzh;

    @ApiModelProperty(value = "抵押电子证照")
    private String dzzz;

    public List<YsfDyrDTO> getDyrList() {
        return dyrList;
    }

    public void setDyrList(List<YsfDyrDTO> dyrList) {
        this.dyrList = dyrList;
    }

    public List<YsfDyqrDTO> getDyqrList() {
        return dyqrList;
    }

    public void setDyqrList(List<YsfDyqrDTO> dyqrList) {
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

    public String getTzyhdydyzmh() {
        return tzyhdydyzmh;
    }

    public void setTzyhdydyzmh(String tzyhdydyzmh) {
        this.tzyhdydyzmh = tzyhdydyzmh;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    public String getJgsj() {
        return jgsj;
    }

    public void setJgsj(String jgsj) {
        this.jgsj = jgsj;
    }

    public String getDzzz() {
        return dzzz;
    }

    public void setDzzz(String dzzz) {
        this.dzzz = dzzz;
    }

    public String getDybdcqzh() {
        return dybdcqzh;
    }

    public void setDybdcqzh(String dybdcqzh) {
        this.dybdcqzh = dybdcqzh;
    }
}
