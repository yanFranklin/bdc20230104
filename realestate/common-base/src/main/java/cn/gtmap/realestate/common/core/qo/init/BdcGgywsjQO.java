package cn.gtmap.realestate.common.core.qo.init;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @description 不动产项目查询QO
 */
@ApiModel(value = "BdcGgywsjQO",description = "不动产项目查询参数封装对象")
public class BdcGgywsjQO {
    @ApiModelProperty(value = "项目id")
    private String xmid;

    public String getXmid() { return xmid; }

    public void setXmid(String xmid) { this.xmid = xmid; }
}
