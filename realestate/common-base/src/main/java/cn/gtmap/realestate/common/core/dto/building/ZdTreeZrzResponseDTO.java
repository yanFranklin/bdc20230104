package cn.gtmap.realestate.common.core.dto.building;

import cn.gtmap.realestate.common.core.domain.building.FwKDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-26
 * @description
 */
public class ZdTreeZrzResponseDTO extends FwKDO {

    /**
     * 逻辑幢列表
     */
    @ApiModelProperty(value = "逻辑幢列表")
    private List<FwLjzDO> fwLjzDOList;

    public List<FwLjzDO> getFwLjzDOList() {
        return fwLjzDOList;
    }

    public void setFwLjzDOList(List<FwLjzDO> fwLjzDOList) {
        this.fwLjzDOList = fwLjzDOList;
    }
}
