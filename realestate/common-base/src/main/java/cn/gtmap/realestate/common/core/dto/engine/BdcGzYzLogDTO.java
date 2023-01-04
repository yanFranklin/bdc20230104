package cn.gtmap.realestate.common.core.dto.engine;

import cn.gtmap.realestate.common.core.domain.BdcGzyzLogDO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/05/13
 * @description 不动产规则验证日志DTO
 */
public class BdcGzYzLogDTO extends BdcGzyzLogDO {

    @ApiModelProperty(value = "验证子规则数量")
    private Integer yzzgzsl;

    @ApiModelProperty(value = "验证成功数量")
    private Integer yzcgsl;

    public Integer getYzzgzsl() {
        return yzzgzsl;
    }

    public void setYzzgzsl(Integer yzzgzsl) {
        this.yzzgzsl = yzzgzsl;
    }

    public Integer getYzcgsl() {
        return yzcgsl;
    }

    public void setYzcgsl(Integer yzcgsl) {
        this.yzcgsl = yzcgsl;
    }
}
