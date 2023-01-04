package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/12/18
 * @description 一窗受理配置
 */
@ApiModel(value = "BdcYcslPzDTO", description = "一窗受理配置信息")
public class BdcYcslPzDTO {

    @ApiModelProperty(value = "自动转发")
    private boolean autoTurn;

    @ApiModelProperty(value = "共用受理编号")
    private boolean gyslbh;

    @ApiModelProperty(value = "自动办结流程")
    private boolean autoComplete;

    // 是否进入认领列表
    // 当 进入认领列表为true 时 不管slr是否有值 都 进入认领列表
    // 如果进入认领列表为false 则 判断 slr是否有值 有值指定到受理人名下  没有值仍然进入认领列表
    @ApiModelProperty(value = "是否进入认领流程")
    private boolean jrrllb;

    public boolean isAutoTurn() {
        return autoTurn;
    }

    public void setAutoTurn(boolean autoTurn) {
        this.autoTurn = autoTurn;
    }

    public boolean isGyslbh() {
        return gyslbh;
    }

    public void setGyslbh(boolean gyslbh) {
        this.gyslbh = gyslbh;
    }


    public boolean isAutoComplete() {
        return autoComplete;
    }

    public void setAutoComplete(boolean autoComplete) {
        this.autoComplete = autoComplete;
    }

    public boolean isJrrllb() {
        return jrrllb;
    }

    public void setJrrllb(boolean jrrllb) {
        this.jrrllb = jrrllb;
    }
}
