package cn.gtmap.realestate.common.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wutao
 * @date 2022/5/7 16:53
 */
@ApiModel(value = "BdcQlrxxDTO", description = "不动产权利人信息")
public class BdcQlrxxDTO {
    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    @ApiModelProperty(value = "权利人证件号")
    private String zjh;

    public String getQlrmc() { return qlrmc; }

    public void setQlrmc(String qlrmc) { this.qlrmc = qlrmc; }

    public String getZjh() { return zjh; }

    public void setZjh(String zjh) { this.zjh = zjh; }
}
