package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/11/26
 * @description 自助查询机查询收费信息实体
 */
@ApiModel(value = "ZzcxjSfxxDTO", description = "自助查询机查询收费信息实体")
public class ZzcxjSfxxDTO {

    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "收费状态")
    private Integer sfzt;

    public Integer getSfzt() {
        return sfzt;
    }

    public void setSfzt(Integer sfzt) {
        this.sfzt = sfzt;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    @Override
    public String toString() {
        return "ZzcxjSfxxDTO{" +
                "qlrmc='" + qlrmc + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", slbh='" + slbh + '\'' +
                '}';
    }
}
