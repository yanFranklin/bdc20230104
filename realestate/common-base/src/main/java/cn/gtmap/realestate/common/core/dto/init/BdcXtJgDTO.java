package cn.gtmap.realestate.common.core.dto.init;

import cn.gtmap.realestate.common.core.domain.BdcJgLzrGxDO;
import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @program: realestate
 * @description: 机构DTO
 * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @create: 2022/07/19
 **/
@ApiModel(value = "BdcXtJgDTO",description = "机构DTO")
public class BdcXtJgDTO extends BdcXtJgDO {

    @ApiModelProperty(value = "代理人证件种类名称")
    private String dlrzjzlmc;

    public String getDlrzjzlmc() {
        return dlrzjzlmc;
    }

    public void setDlrzjzlmc(String dlrzjzlmc) {
        this.dlrzjzlmc = dlrzjzlmc;
    }
}
