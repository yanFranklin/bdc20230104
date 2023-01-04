package cn.gtmap.realestate.exchange.core.dto.yhts.cf.request;

import cn.gtmap.realestate.exchange.core.dto.yhts.YhDyTsxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yhts.YhTsxxDTO;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel("抵押权推送银行请求dto")
public class CfTsxxDTO extends YhTsxxDTO {

    @ApiModelProperty(value = "查封人")
    List<CfCfrDTO> cfrList;

    @ApiModelProperty(value = "被查封人")
    List<CfBcfrDTO> bcfrList;

    @ApiModelProperty(value = "查封机关")
    private String cfjg;

    @ApiModelProperty(value = "查封机关证件号")
    private String cfjgzjh;

    @ApiModelProperty(value = "查封文号")
    private String cfwh;

    @ApiModelProperty(value = "被查封不动产权证号")
    private String cqzh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "查封开始时间", example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cfqssj;

    @ApiModelProperty(value = "查封结束时间", example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cfjssj;

    @ApiModelProperty(value = "查封类型")
    private Integer cflx;

    @ApiModelProperty(value = "登簿时间", example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date djsj;

    @ApiModelProperty(value = "抵押信息")
    List<YhDyTsxxDTO> yhDyxxList;

    public List<CfCfrDTO> getCfrList() {
        return cfrList;
    }

    public void setCfrList(List<CfCfrDTO> cfrList) {
        this.cfrList = cfrList;
    }

    public List<CfBcfrDTO> getBcfrList() {
        return bcfrList;
    }

    public void setBcfrList(List<CfBcfrDTO> bcfrList) {
        this.bcfrList = bcfrList;
    }

    public String getCfjg() {
        return cfjg;
    }

    public void setCfjg(String cfjg) {
        this.cfjg = cfjg;
    }

    public String getCfjgzjh() {
        return cfjgzjh;
    }

    public void setCfjgzjh(String cfjgzjh) {
        this.cfjgzjh = cfjgzjh;
    }

    public String getCfwh() {
        return cfwh;
    }

    public void setCfwh(String cfwh) {
        this.cfwh = cfwh;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public Date getCfqssj() {
        return cfqssj;
    }

    public void setCfqssj(Date cfqssj) {
        this.cfqssj = cfqssj;
    }

    public Date getCfjssj() {
        return cfjssj;
    }

    public void setCfjssj(Date cfjssj) {
        this.cfjssj = cfjssj;
    }

    public Integer getCflx() {
        return cflx;
    }

    public void setCflx(Integer cflx) {
        this.cflx = cflx;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public List<YhDyTsxxDTO> getYhDyxxList() {
        return yhDyxxList;
    }

    public void setYhDyxxList(List<YhDyTsxxDTO> yhDyxxList) {
        this.yhDyxxList = yhDyxxList;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }
}
