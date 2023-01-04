package cn.gtmap.realestate.common.core.dto.init;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import io.swagger.annotations.ApiModelProperty;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/1/2
 * @description 登记簿查封VO
 */
public class BdcCfDTO extends BdcCfDO {
    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }
}

