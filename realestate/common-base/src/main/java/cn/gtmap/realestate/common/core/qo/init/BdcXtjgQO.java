package cn.gtmap.realestate.common.core.qo.init;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @description 机构查询QO
 */
@ApiModel(value = "BdcXtjgQO",description = "机构查询QO")
public class BdcXtjgQO {

    @ApiModelProperty(value = "机构类别")
    private String jglb;

    public String getJglb() {
        return jglb;
    }

    public void setJglb(String jglb) {
        this.jglb = jglb;
    }
}
