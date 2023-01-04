package cn.gtmap.realestate.supervise.service.impl;

import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.gtc.clients.RoleManagerClient;
import cn.gtmap.gtc.clients.UserManagerClient;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.supervise.core.qo.LfRoleQO;
import cn.gtmap.realestate.supervise.service.LfUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xlsx4j.sml.Col;

import java.util.*;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/08/32
 * @description 用户相关逻辑处理
 */
@Service
public class LfUserServiceImpl implements LfUserService {
    @Autowired
    private RoleManagerClient roleManagerClient;
    @Autowired
    private OrganizationManagerClientMatcher orgManagerClient;
    @Autowired
    private UserManagerClient userManagerClient;

    /**
     * 需要监管的角色
     */
    @Value("${gwcxycjg.roles:}")
    private String roles;


    /**
     * 获取所有的角色信息
     * @param lfRoleQO 角色查询参数
     * @return {List} 角色集合
     */
    @Override
    public List<RoleDto> listAllRoles(LfRoleQO lfRoleQO) {
        List<RoleDto> roleDtoList = roleManagerClient.getEnabledRoleList();
        if((null == lfRoleQO || CollectionUtils.isEmpty(lfRoleQO.getRoleIds())) && StringUtils.isBlank(roles)) {
            return roleDtoList;
        }

        List<RoleDto> result = new ArrayList<>();
        if(null != lfRoleQO && CollectionUtils.isNotEmpty(lfRoleQO.getRoleIds())) {
            for(RoleDto roleDto : roleDtoList) {
                for(String roleId : lfRoleQO.getRoleIds()) {
                    if(StringUtils.equals(roleDto.getId(), roleId)) {
                        result.add(roleDto);
                    }
                }
            }
        }

        List<RoleDto> result2 = new ArrayList<>();
        if(StringUtils.isNotBlank(roles)) {
            for (RoleDto roleDto : CollectionUtils.isEmpty(result) ? roleDtoList : result) {
                for(String role : roles.split(CommonConstantUtils.ZF_YW_DH)) {
                    if(StringUtils.equals(roleDto.getName(), role)) {
                        result2.add(roleDto);
                    }
                }
            }
        }
        return result2;
    }

    /**
     * 获取所有的用户
     * @return {List} 用户集合
     */
    @Override
    public List<UserDto> listAllUsers() {
        List<OrganizationDto> orgs = this.listAllOrgs();
        if(CollectionUtils.isEmpty(orgs)) {
            return Collections.emptyList();
        }

        return this.getUsersOfOrgs(orgs);
    }

    /**
     * 获取用户对应的部门名称
     *
     * @param userids 用户id
     * @return 部门名称集合
     */
    @Override
    public List<OrganizationDto> getUserByIds(String userids) {
        List<String> idList = Arrays.asList(userids.split(","));
        List<OrganizationDto> orgList = new ArrayList<>();
        for (String id : idList) {
            UserDto userDto = userManagerClient.getUserDetail(id);
            if (userDto != null) {
                List<OrganizationDto> organizationDtoList = userDto.getOrgRecordList();
                if (CollectionUtils.isNotEmpty(organizationDtoList)) {
                    orgList.add(organizationDtoList.get(0));
                }
            }
        }
        return orgList;
    }

    /**
     * 获取所有的组织机构
     * @return {List} 机构集合
     */
    @Override
    public List<OrganizationDto> listAllOrgs() {
        return orgManagerClient.listOrgs(CommonConstantUtils.SF_S_DM);
    }

    /**
     * 获取所有的用户和组织机构
     * @return {List} 用户和机构集合
     */
    @Override
    public Map<String, List> listAllUsersAndOrgs() {
        Map<String, List> usersAndOrgsMap = new HashMap<>();

        List<OrganizationDto> orgs = this.listAllOrgs();
        if(CollectionUtils.isEmpty(orgs)) {
            return usersAndOrgsMap;
        }
        usersAndOrgsMap.put("orgs", orgs);
        usersAndOrgsMap.put("users", this.getUsersOfOrgs(orgs));
        return usersAndOrgsMap;
    }

    /**
     * 获取组织机构关联的用户
     * @param orgs 机构
     * @return 用户集合
     */
    private List<UserDto> getUsersOfOrgs(List<OrganizationDto> orgs) {
        List<UserDto> users = new ArrayList<>();
        Set<String> userIdSet = new HashSet<>();
        for(OrganizationDto orgDto : orgs) {
            List<UserDto> orgUsers = orgManagerClient.listUsersByOrg(orgDto.getId());
            if(CollectionUtils.isEmpty(orgUsers)) {
                continue;
            }

            for(UserDto user : orgUsers) {
                if(userIdSet.contains(user.getId())) {
                    continue;
                }
                UserDto newUser = new UserDto();
                newUser.setId(user.getId());
                newUser.setUsername(user.getUsername());
                newUser.setAlias(user.getAlias());

                userIdSet.add(user.getId());
                users.add(newUser);
            }
        }

        return users;
    }
}
