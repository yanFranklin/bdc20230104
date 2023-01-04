package cn.gtmap.realestate.common.core.qo.init;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/8/30
 * @description 登记信息更新
 */
@ApiModel(value = "BdcDjxxUpdateQO", description = "登记信息更新对象")
public class BdcDjxxUpdateQO {

    @ApiModelProperty(value = "更新JSON字符串")
    private String jsonStr;

    @ApiModelProperty(value = "更新查询条件")
    private Map<String,Object> whereMap;

    @ApiModelProperty(value = "类名字符串")
    private String className;

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public Map<String, Object> getWhereMap() {
        return whereMap;
    }

    public void setWhereMap(Map<String, Object> whereMap) {
        this.whereMap = whereMap;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
