package cn.gtmap.realestate.common.core.dto.config;

import cn.gtmap.realestate.common.core.domain.BdcXtZsmbPzDO;
import io.swagger.annotations.ApiModelProperty;

public class BdcZsmbpzDTO extends BdcXtZsmbPzDO {

    @ApiModelProperty(value = "权利类型名称")
    private String qllxmc;

    public String getQllxmc() {
        return qllxmc;
    }

    public void setQllxmc(String qllxmc) {
        this.qllxmc = qllxmc;
    }
}
