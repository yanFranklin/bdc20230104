package cn.gtmap.realestate.exchange.core.dto.yhts.cf.request;

import io.swagger.annotations.ApiModelProperty;

public class CfCfrDTO {
    @ApiModelProperty(value = "查封人")
    private String cfr;

    @ApiModelProperty(value = "查封人证件号")
    private String cfrzjh;

    @ApiModelProperty(value = "证件种类")
    private Integer zjzl;

    public String getCfr() {
        return cfr;
    }

    public void setCfr(String cfr) {
        this.cfr = cfr;
    }

    public String getCfrzjh() {
        return cfrzjh;
    }

    public void setCfrzjh(String cfrzjh) {
        this.cfrzjh = cfrzjh;
    }

    public Integer getZjzl() {
        return zjzl;
    }

    public void setZjzl(Integer zjzl) {
        this.zjzl = zjzl;
    }
}
