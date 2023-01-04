package cn.gtmap.realestate.common.matcher;

import cn.gtmap.gtc.clients.OrganizationManagerClient;
import cn.gtmap.gtc.sso.domain.dto.OrgUserDto;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/03/24
 * @description  大云组织机构处理 V2.x版本适配
 */
@Component
public class OrganizationManagerClientMatcher {
    @Autowired
    private OrganizationManagerClient organizationManagerClient;

    /**
     * @return
     * @description 查询所有组织
     */
    public List<OrganizationDto> listOrgs(Integer enabled) {
        return organizationManagerClient.listOrgs(enabled);
    }

    /**
     * @param id 组织id
     * @return
     * @description 查找组织机构下所有用户
     */
    public List<UserDto> listUsersByOrg(String id) {
        return organizationManagerClient.listUsersByOrg(id, 1);
    }

    /**
     * @param
     * @return
     * @description 查找最顶级组织机构
     */
    public List<OrganizationDto> findRootOrgs() {
        return organizationManagerClient.findRootOrgs();
    }

    /**
     * @param id 组织id
     * @param enabled 0:禁用 1：正常 null: 全部
     * @return
     * @description 查找直接下级组织机构
     */
    public List<OrganizationDto> findChildren(String id, Integer enabled) {
        return organizationManagerClient.findChildren(id, enabled);
    }

    /**
     * @return parentId 为空查询根节点，不为空查询下级
     * @description 分级查询组织及用户
     */
    public List<OrgUserDto> listGradeOrgUsers(Integer enabled, String parentId) {
        return organizationManagerClient.listGradeOrgUsers(enabled, parentId);
    }

    /**
     * @param id 组织id
     * @return
     * @description 分页查找组织机构下的用户
     */
    public Page<UserDto> findUsersByOrg(PageRequest pageable, String id) {
        return organizationManagerClient.findUsersByOrg(pageable, id);
    }

    /**
     *
     * @param pageable
     * @param id 组织id
     * @return 分页查找组织机构下的用户（不会递归查找到子部门用户）
     */
    public Page<UserDto> queryUsersByOrg(PageRequest pageable, String id){
        return organizationManagerClient.queryUsersByOrg(pageable,id);
    }

    /**
     * @param id 当前组织id
     * @param enabled 1:正常 0：禁用
     * @return
     * @description 获取组织下角色
     */
    public List<RoleDto> listRoles(String id, Integer enabled) {
        return organizationManagerClient.listRoles(id, enabled);
    }

    /**
     * 根据部门id查询组织信息
     * @param bmidList 部门ID集合
     * @return 部门信息集合
     */
    public List<OrganizationDto> findOrgByIds(List<String> bmidList){
        return organizationManagerClient.findOrgByIds(bmidList);
    }
}
