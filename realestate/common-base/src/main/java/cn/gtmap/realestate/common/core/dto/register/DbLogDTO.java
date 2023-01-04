package cn.gtmap.realestate.common.core.dto.register;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/9/28
 * @description 登簿日志保存DTO
 */

@Api(value = "DbLogDTO", description = "登簿日志保存DTO")
public class DbLogDTO {
    @ApiModelProperty(value = "私有属性")
    private Map<String, Object> privateAttrMap;


    public Map<String, Object> getPrivateAttrMap() {
        return privateAttrMap;
    }

    public void setPrivateAttrMap(Map<String, Object> privateAttrMap) {
        this.privateAttrMap = privateAttrMap;
    }

    @Override
    public String toString() {
        return "DbLogDTO{" +
                "privateAttrMap=" + privateAttrMap +
                '}';
    }
}
