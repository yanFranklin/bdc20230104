package cn.gtmap.realestate.common.core.dto.building;


import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-06
 * @description 批量新增修改权利人 实体
 */
@ApiModel(value = "FwFcQlrRequestDTO", description = "批量新增修改权利人实体")
public class FwFcQlrRequestDTO {

    /**
     * 权利人实体集合
     */
    @ApiModelProperty(value = "权利人实体集合")
    private List<FwFcqlrDO> qlrList;

    public List<FwFcqlrDO> getQlrList() {
        return qlrList;
    }

    public void setQlrList(List<FwFcqlrDO> qlrList) {
        this.qlrList = qlrList;
    }
}
