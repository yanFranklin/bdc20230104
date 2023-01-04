package cn.gtmap.realestate.common.core.dto.exchange.yancheng;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author wangyinghao
 */
public class YzwDTO {
    @ApiModelProperty(value = "业务编号")
    private String ywbh;

    @ApiModelProperty(value = "申请人名称")
    private String sqrmc;

    @ApiModelProperty(value = "申请人证件号")
    private String sqrzjh;

    public String getYwbh() {
        return ywbh;
    }

    public void setYwbh(String ywbh) {
        this.ywbh = ywbh;
    }

    public String getSqrmc() {
        return sqrmc;
    }

    public void setSqrmc(String sqrmc) {
        this.sqrmc = sqrmc;
    }

    public String getSqrzjh() {
        return sqrzjh;
    }

    public void setSqrzjh(String sqrzjh) {
        this.sqrzjh = sqrzjh;
    }
}
