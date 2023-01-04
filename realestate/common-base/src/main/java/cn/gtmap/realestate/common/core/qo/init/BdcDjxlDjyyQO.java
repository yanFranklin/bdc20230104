package cn.gtmap.realestate.common.core.qo.init;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/6/25
 * @description 登记小类登记原因查询QO,主要用于多维度获取登记原因关系
 */
public class BdcDjxlDjyyQO {

    @ApiModelProperty(value = "登记小类,必填")
    private String djxl;

    @ApiModelProperty(value = "工作流定义ID")
    private String gzldyid;

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }
}
