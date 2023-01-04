package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.identitycheck;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/2 14:30
 */
public class PoliceIdentityCheckConditionDTO {

    @ApiModelProperty(value = "姓名")
    private String xm;

    @ApiModelProperty(value = "身份证")
    private String gmsfhm;

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getGmsfhm() {
        return gmsfhm;
    }

    public void setGmsfhm(String gmsfhm) {
        this.gmsfhm = gmsfhm;
    }

    @Override
    public String toString() {
        return "PoliceIdentityCheckConditionDTO{" +
                "xm='" + xm + '\'' +
                ", gmsfhm='" + gmsfhm + '\'' +
                '}';
    }
}
