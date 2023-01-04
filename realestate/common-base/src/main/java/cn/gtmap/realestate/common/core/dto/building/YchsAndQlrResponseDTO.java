package cn.gtmap.realestate.common.core.dto.building;

import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/13
 * @description
 */
@ApiModel(value = "YchsAndQlrResponseDTO", description = "预测户室和权利人实体")
public class YchsAndQlrResponseDTO {
    /**
     * 预测户室实体
     */
    @ApiModelProperty(value = "预测户室实体")
    private FwYchsDO fwYchsDO;
    /**
     * 权利人集合
     */
    @ApiModelProperty(value = "权利人集合")
    private List<FwFcqlrDO> fwFcqlrDOList;

    public FwYchsDO getFwYchsDO() {
        return fwYchsDO;
    }

    public void setFwYchsDO(FwYchsDO fwYchsDO) {
        this.fwYchsDO = fwYchsDO;
    }

    public List<FwFcqlrDO> getFwFcqlrDOList() {
        return fwFcqlrDOList;
    }

    public void setFwFcqlrDOList(List<FwFcqlrDO> fwFcqlrDOList) {
        this.fwFcqlrDOList = fwFcqlrDOList;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("YchsAndQlrResponseDTO{");
        sb.append("fwYchsDO=").append(fwYchsDO);
        sb.append(", fwFcqlrDOList=").append(fwFcqlrDOList);
        sb.append('}');
        return sb.toString();
    }
}