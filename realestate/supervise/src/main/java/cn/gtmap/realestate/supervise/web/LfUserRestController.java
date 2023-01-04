package cn.gtmap.realestate.supervise.web;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.supervise.service.LfUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/08/31
 * @description 用户信息服务类
 */
@RestController
@RequestMapping("/rest/v1.0/user")
public class LfUserRestController {
    @Autowired
    private LfUserService userService;

    /**
     * 获取所有的角色信息
     * @return {List} 角色集合
     */
    @GetMapping("/roles")
    public List<RoleDto> listAllRoles() {
        return userService.listAllRoles(null);
    }

    /**
     * 获取所有的用户
     * @return {List} 用户集合
     */
    @GetMapping("/users")
    public List<UserDto> listAllUsers() {
        return userService.listAllUsers();
    }

    /**
     * 获取用户对应的部门
     *
     * @param userids 用户id
     * @return {List} 部门名称集合
     */
    @GetMapping("/orgs/{userids}")
    public List<OrganizationDto> getUserByIds(@PathVariable("userids")String userids) {
        return userService.getUserByIds(userids);
    }

    /**
     * 获取所有的组织机构
     * @return {List} 机构集合
     */
    @GetMapping("/orgs")
    public List<OrganizationDto> listAllOrgs() {
        return userService.listAllOrgs();
    }

    /**
     * 获取所有的用户和组织机构
     * @return {List} 用户和机构集合
     */
    @GetMapping("/users_orgs")
    public Map<String, List> listAllUsersAndOrgs() {
        return userService.listAllUsersAndOrgs();
    }
}
