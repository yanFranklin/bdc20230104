package cn.gtmap.realestate.common.core.dto.exchange.provincialpublicsecuritydepartment;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2021/2/3
 * @description 4.1省公安厅-公民基本信息在线比对接口查询参数
 */
public class PoliceRealNameParamDTO {

    @ApiModelProperty(value = "身份证号")
    private String identityNumber;

    @ApiModelProperty(value = "姓名")
    private String name;

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
