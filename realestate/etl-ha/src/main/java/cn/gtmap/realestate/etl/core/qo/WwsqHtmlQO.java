package cn.gtmap.realestate.etl.core.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/6/15
 * @description 外网申请前端请求QO
 */
@ApiModel(value = "WwsqHtmlQO", description = "外网申请前端请求QO")
public class WwsqHtmlQO {

    @ApiModelProperty(value = "外网项目ID")
    private String wwxmid;


    public String getWwxmid() {
        return wwxmid;
    }

    public void setWwxmid(String wwxmid) {
        this.wwxmid = wwxmid;
    }


}
