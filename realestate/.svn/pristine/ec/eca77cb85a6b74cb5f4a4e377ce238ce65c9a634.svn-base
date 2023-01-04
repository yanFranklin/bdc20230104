package cn.gtmap.realestate.common.core.dto.building;

import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2018-12-26
 * @description 房屋户室与户室所属权利人信息
 */
@ApiModel(value = "FwhsAndFwQlrRequestDTO", description = "户室与户室所属权利人信息DTO")
public class FwhsAndFwQlrRequestDTO {

    @ApiModelProperty(value = "房屋户室信息")
    private FwHsDO fwHsDO;

    @ApiModelProperty(value = "权利人list")
    private List<FwFcqlrDO> qlrList;

    public FwHsDO getFwHsDO() {
        return fwHsDO;
    }

    public void setFwHsDO(FwHsDO fwHsDO) {
        this.fwHsDO = fwHsDO;
    }

    public List<FwFcqlrDO> getQlrList() {
        return qlrList;
    }

    public void setQlrList(List<FwFcqlrDO> qlrList) {
        this.qlrList = qlrList;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FwhsAndFwQlrRequestDTO{");
        sb.append("fwHsDO=").append(fwHsDO);
        sb.append(", qlrList='").append(qlrList);
        sb.append('}');
        return sb.toString();
    }
}
