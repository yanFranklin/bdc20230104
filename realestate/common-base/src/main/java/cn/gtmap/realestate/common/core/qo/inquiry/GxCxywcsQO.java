package cn.gtmap.realestate.common.core.qo.inquiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2021/1/28
 * @description 共享接口查询业务参数QO
 */
@ApiModel(value = "GxCxywcsQO",description = "共享接口查询业务参数QO")
public class GxCxywcsQO {

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "权利人类型")
    private String qlrlx;

    @ApiModelProperty(value = "需要排除的权利人类型")
    private String excludeQlrlx;

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getQlrlx() {
        return qlrlx;
    }

    public void setQlrlx(String qlrlx) {
        this.qlrlx = qlrlx;
    }

    public String getExcludeQlrlx() {
        return excludeQlrlx;
    }

    public void setExcludeQlrlx(String excludeQlrlx) {
        this.excludeQlrlx = excludeQlrlx;
    }
}
