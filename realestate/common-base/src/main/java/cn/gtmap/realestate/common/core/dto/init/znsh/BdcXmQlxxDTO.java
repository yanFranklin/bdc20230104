package cn.gtmap.realestate.common.core.dto.init.znsh;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/5/5
 * @description 智能审核，数据校验中的项目权利信息
 */
public class BdcXmQlxxDTO {
    @ApiModelProperty(value = "存储项目相关参数")
    private BdcXmDO bdcXm;

    @ApiModelProperty(value = "存储权利信息相关参数")
    private BdcQl bdcQl;

    public BdcXmDO getBdcXm() {
        return bdcXm;
    }

    public void setBdcXm(BdcXmDO bdcXm) {
        this.bdcXm = bdcXm;
    }

    public BdcQl getBdcQl() {
        return bdcQl;
    }

    public void setBdcQl(BdcQl bdcQl) {
        this.bdcQl = bdcQl;
    }
}
