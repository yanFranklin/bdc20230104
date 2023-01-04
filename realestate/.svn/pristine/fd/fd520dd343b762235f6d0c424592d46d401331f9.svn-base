package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.identitycheck;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/2 15:24
 * @description
 */
public class PoliceIdentityCheckParamDTO {

    @ApiModelProperty(value = "业务类型代码 例：1000101")
    private String businesstypecode;

    @ApiModelProperty(value = "业务类型名称 例：商品房首次登记")
    private String businesstypename;

    private List<PoliceIdentityCheckConditionDTO> conditionDTOS;

    public String getBusinesstypecode() {
        return businesstypecode;
    }

    public void setBusinesstypecode(String businesstypecode) {
        this.businesstypecode = businesstypecode;
    }

    public String getBusinesstypename() {
        return businesstypename;
    }

    public void setBusinesstypename(String businesstypename) {
        this.businesstypename = businesstypename;
    }

    public List<PoliceIdentityCheckConditionDTO> getConditionDTOS() {
        return conditionDTOS;
    }

    public void setConditionDTOS(List<PoliceIdentityCheckConditionDTO> conditionDTOS) {
        this.conditionDTOS = conditionDTOS;
    }

    @Override
    public String toString() {
        return "PoliceIdentityCheckParamDTO{" +
                "businesstypecode='" + businesstypecode + '\'' +
                ", businesstypename='" + businesstypename + '\'' +
                ", conditionDTOS=" + conditionDTOS +
                '}';
    }
}
