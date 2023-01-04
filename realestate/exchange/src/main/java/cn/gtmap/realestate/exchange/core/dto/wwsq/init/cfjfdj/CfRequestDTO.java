package cn.gtmap.realestate.exchange.core.dto.wwsq.init.cfjfdj;

import cn.gtmap.realestate.exchange.core.dto.wwsq.init.InitFjxxDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CfRequestDTO {

    @ApiModelProperty(value = "受理来源")
    private String slly;
    @ApiModelProperty(value = "受理人")
    private String slr;
    @ApiModelProperty(value = "申请登记类型")
    private String sqdjlx;
    @ApiModelProperty(value = "办件编号")
    private String bjbh;

    @ApiModelProperty(value = "附件信息")
    private List<InitFjxxDTO> fjxx;

    @ApiModelProperty(value = "查封信息")
    private List<CfCfxxRequestDTO> cfxx;

    public String getSlly() {
        return slly;
    }

    public void setSlly(String slly) {
        this.slly = slly;
    }

    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public String getSqdjlx() {
        return sqdjlx;
    }

    public void setSqdjlx(String sqdjlx) {
        this.sqdjlx = sqdjlx;
    }

    public String getBjbh() {
        return bjbh;
    }

    public void setBjbh(String bjbh) {
        this.bjbh = bjbh;
    }

    public List<InitFjxxDTO> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<InitFjxxDTO> fjxx) {
        this.fjxx = fjxx;
    }

    public List<CfCfxxRequestDTO> getCfxx() {
        return cfxx;
    }

    public void setCfxx(List<CfCfxxRequestDTO> cfxx) {
        this.cfxx = cfxx;
    }

    @Override
    public String toString() {
        return "CfRequestDTO{" +
                "slly='" + slly + '\'' +
                ", slr='" + slr + '\'' +
                ", sqdjlx='" + sqdjlx + '\'' +
                ", bjbh='" + bjbh + '\'' +
                ", fjxx=" + fjxx +
                ", cfxx=" + cfxx +
                '}';
    }
}
