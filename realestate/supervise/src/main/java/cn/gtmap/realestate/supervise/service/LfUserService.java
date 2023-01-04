package cn.gtmap.realestate.supervise.service;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.supervise.core.qo.LfRoleQO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/08/32
 * @description 用户相关逻辑定义
 */
public interface LfUserService {
    /**
     * 获取所有的角色信息
     * @param lfRoleQO 角色查询参数
     * @return {List} 角色集合
     */
    List<RoleDto> listAllRoles(LfRoleQO lfRoleQO);

    /**
     * 获取所有的用户
     * @return {List} 用户集合
     */
    List<UserDto> listAllUsers();

    /**
     * 获取用户对应的部门名称
     *
     * @param userids 用户id
     * @return 部门名称集合
     */
    List<OrganizationDto> getUserByIds(String userids);

    /**
     * 获取所有的组织机构
     * @return {List} 机构集合
     */
    List<OrganizationDto> listAllOrgs();

    /**
     * 获取所有的用户和组织机构
     * @return {List} 用户和机构集合
     */
    Map<String, List> listAllUsersAndOrgs();
}
