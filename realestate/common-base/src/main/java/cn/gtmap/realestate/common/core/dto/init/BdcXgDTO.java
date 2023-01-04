package cn.gtmap.realestate.common.core.dto.init;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/4/2
 * @description 不动产数据修改对象
 */
@ApiModel(value = "BdcXgDTO",description = "不动产数据修改对象")
public class BdcXgDTO {

    @ApiModelProperty(value = "修改参数")
    private String param;

    @ApiModelProperty(value = "修改前JSON字符串")
    private String before;

    @ApiModelProperty(value = "修改后字符串")
    private String after;

    @ApiModelProperty(value = "类型名称")
    private String viewTypeName;

    @ApiModelProperty(value = "修改信息")
    private String change;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getViewTypeName() {
        return viewTypeName;
    }

    public void setViewTypeName(String viewTypeName) {
        this.viewTypeName = viewTypeName;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }
}
