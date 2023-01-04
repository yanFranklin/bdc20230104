package cn.gtmap.realestate.supervise.core.qo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/08/31
 * @description 角色查询QO
 */
public class LfRoleQO {
    @ApiModelProperty(value = "需要过滤的角色ID")
    private List<String> roleIds;


    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return "LfRoleQO{" +
                "roleIds=" + roleIds +
                '}';
    }
}
