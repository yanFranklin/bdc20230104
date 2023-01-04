package cn.gtmap.realestate.common.core.dto.building;

import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019-03-26
 * @description 房屋预测户室与户室所属权利人信息
 */
@ApiModel(value = "FwychsAndFwQlrRequestDTO", description = "房屋预测户室与户室所属权利人信息")
public class FwychsAndFwQlrRequestDTO {

    @ApiModelProperty(value = "房屋户室信息")
    private FwYchsDO fwYchsDO;

    @ApiModelProperty(value = "权利人list")
    private List<FwFcqlrDO> qlrList;

    public FwYchsDO getFwYchsDO() {
        return fwYchsDO;
    }

    public void setFwYchsDO(FwYchsDO fwYchsDO) {
        this.fwYchsDO = fwYchsDO;
    }

    public List<FwFcqlrDO> getQlrList() {
        return qlrList;
    }

    public void setQlrList(List<FwFcqlrDO> qlrList) {
        this.qlrList = qlrList;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FwychsAndFwQlrRequestDTO{");
        sb.append("fwYchsDO=").append(fwYchsDO);
        sb.append(", qlrList=").append(qlrList);
        sb.append('}');
        return sb.toString();
    }
}
