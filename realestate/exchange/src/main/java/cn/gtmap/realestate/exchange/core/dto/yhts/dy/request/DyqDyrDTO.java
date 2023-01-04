package cn.gtmap.realestate.exchange.core.dto.yhts.dy.request;

import io.swagger.annotations.ApiModelProperty;

public class DyqDyrDTO {
    @ApiModelProperty(value = "抵押人")
    private String dyr;

    @ApiModelProperty(value = "抵押人证件号")
    private String dyrzjh;

    @ApiModelProperty(value = "证件种类")
    private Integer zjzl;

    public String getDyr() {
        return dyr;
    }

    public void setDyr(String dyr) {
        this.dyr = dyr;
    }

    public String getDyrzjh() {
        return dyrzjh;
    }

    public void setDyrzjh(String dyrzjh) {
        this.dyrzjh = dyrzjh;
    }

    public Integer getZjzl() {
        return zjzl;
    }

    public void setZjzl(Integer zjzl) {
        this.zjzl = zjzl;
    }
}
