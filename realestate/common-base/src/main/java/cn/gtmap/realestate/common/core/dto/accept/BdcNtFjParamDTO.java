package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/1/19
 * @description
 */
@ApiModel(value = "BdcSwspFjDTO", description = "南通备案信息附件参数实体")
public class BdcNtFjParamDTO {

    @ApiModelProperty(value = "权利人证件号")
    private Set<String> qlrzjh;

    @ApiModelProperty(value = "义务人证件号")
    private Set<String> ywrzjh;

    public Set<String> getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(Set<String> qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public Set<String> getYwrzjh() {
        return ywrzjh;
    }

    public void setYwrzjh(Set<String> ywrzjh) {
        this.ywrzjh = ywrzjh;
    }
}
