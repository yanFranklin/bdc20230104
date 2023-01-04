package cn.gtmap.realestate.common.util;

import cn.gtmap.gtc.clients.OrganizationManagerClient;
import cn.gtmap.gtc.clients.UserManagerClient;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.ex.UserInformationAccessException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0  2018/11/5 19:34
 * @description 用户操作工具类
 */
@Component
public class UserManagerUtils {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 引入基础平台用户操作FeignClient
     */
    @Autowired
    private UserManagerClient userManagerClient;

    @Autowired
    private OrganizationManagerClient orgClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserManagerUtils.class);


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param userName 用户名称
     * @return {UserDto} 用户信息实体
     * @description 根据用户名称获取用户信息实体
     */
    public UserDto getUserByName(String userName) {
        if (StringUtils.isBlank(userName)) {
            return null;
        }
        return this.userManagerClient.getUserDetailByUsername(userName);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param userName 用户名称
     * @param xzqdm 行政区代码
     * @return {UserDto} 用户信息实体
     * @description 根据用户名称和行政代码获取用户信息实体（用户组织要匹配行政区代码）
     */
    public UserDto getUserByNameAndXzqdm(String userName,String xzqdm) {
        if (StringUtils.isBlank(userName)) {
            return null;
        }
        UserDto userDto= this.userManagerClient.getUserDetailByUsername(userName);
        if(StringUtils.isBlank(xzqdm)){
            return userDto;
        }
        List<OrganizationDto> organizationDtoList =new ArrayList<>();
        if(userDto != null &&CollectionUtils.isNotEmpty(userDto.getOrgRecordList())){
            for(OrganizationDto organizationDto:userDto.getOrgRecordList()){
                if(StringUtils.equals(xzqdm,organizationDto.getRegionCode())){
                    organizationDtoList.add(organizationDto);
                }
            }
            userDto.setOrgRecordList(organizationDtoList);
        }
        return userDto;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {UserDto} 用户信息实体
     * @description 获取当前系统登录用户
     */
    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication || StringUtils.isBlank(authentication.getName())) {
            return null;
        }
        return this.getUserByName(authentication.getName());
    }

    /**
     * 获取当前系统登录用户 <br>
     * <strong>增加对于用户信息是否为空的验证</strong>
     *
     * @return {UserDto} 用户信息 DTO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    public UserDto getUserDto() {
        UserDto currentUser = this.getCurrentUser();
        if (null == currentUser) {
            throw new UserInformationAccessException("未获取到当前用户信息");
        }
        return currentUser;
    }

    /**
     * @param userName 用户名
     * @return {List} 用户所在机构（可能对应多个）
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据用户名称获取所在机构列表
     */
    public List<OrganizationDto> getOrgListByUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            return Collections.emptyList();
        }

        //根据用户名获取用户
        UserDto userDto = this.getUserByName(userName);
        if (null == userDto || StringUtils.isBlank(userDto.getUsername())) {
            return Collections.emptyList();
        }

        //获取用户所在机构
        return userDto.getOrgRecordList();
    }

    /**
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @return  bmmc
     * @description 获取当前登录用户的部门名称
     */
    public String getYhBmmc() {
        UserDto user;
        List<OrganizationDto> orgList = new ArrayList<>();
        try{
            user = this.getCurrentUser();
            orgList = user.getOrgRecordList();
        } catch (Exception e){
            LOGGER.error("获取当前登录用户信息出错{}", e.getMessage());
        }

        List<String> orgNameList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orgList)) {
            for (OrganizationDto org : orgList) {
                if (StringUtils.isNotBlank(org.getName())) {
                    orgNameList.add(org.getName());
                }
            }
        }

        return orgNameList.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {String} 机构代码
     * @description 获取当前登录用户所在组织机构代码
     */
    public String getCurrentUserOrgCode() {
        UserDto userDto = this.getCurrentUser();
        if (null == userDto || StringUtils.isBlank(userDto.getUsername())) {
            return StringUtils.EMPTY;
        }

        return this.getOrgCodeByUserName(userDto.getUsername());
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param userName 用户名
     * @return {String} 机构代码
     * @description 根据用户名称（登录账号，数字/字母）获取用户所在机构编码
     */
    public String getOrgCodeByUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            return null;
        }

        //获取机构
        ///可能用户对应多个机构，目前因为基础平台支持不足先只考虑一个情况
        List<OrganizationDto> organizationDtoList = this.getOrgListByUserName(userName);
        if (CollectionUtils.isEmpty(organizationDtoList)) {
            return StringUtils.EMPTY;
        }

        //获取编码
        OrganizationDto organizationDto =
                (OrganizationDto) CollectionUtils.get(organizationDtoList, 0);
        return organizationDto.getCode();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param userName 用户名
     * @return {String} 用户所在机构行政区划编码
     * @description 根据用户名获取机构所在行政区划编码
     */
    public String getRegionCodeByUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            return null;
        }

        //获取机构
        List<OrganizationDto> organizationDtoList = this.getOrgListByUserName(userName);
        if (CollectionUtils.isEmpty(organizationDtoList)) {
            return StringUtils.EMPTY;
        }

        //获取机构所在区划编码
        OrganizationDto organizationDto =
                (OrganizationDto) CollectionUtils.get(organizationDtoList, 0);
        return organizationDto.getRegionCode();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {String} 用户所在机构行政区划编码
     * @description 获取当前用户机构所在行政区划编码
     */
    public String getCurrentUserRegionCode() {
        String userName = getCurrentUserName();
        if(StringUtils.isBlank(userName)) {
            return null;
        } else {
            return getRegionCodeByUserName(userName);
        }
    }


    /**
     * @return String userName 用户姓名
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 只获取当前用户姓名
     */
    public String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && StringUtils.isNotBlank(authentication.getName())) {
            return authentication.getName();
        }
        return "";
    }

    /**
     * @param userName 用户姓名
     * @return String organizatioName 机构名称
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取第一个机构名称
     */
    public String getOrganizationByUserName(String userName) {
        if (StringUtils.isNotBlank(userName)) {
            List<OrganizationDto> organizationDtos = getOrgListByUserName(userName);
            if (CollectionUtils.isNotEmpty(organizationDtos)) {
                return organizationDtos.get(0).getName();
            }
        }
        return "";
    }

    /**
     * @param userName
     * @return signid
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据用户名获取签名id
     */
    public String getUserSignByUserName(String userName) {
        if (StringUtils.isEmpty(userName)) {
            throw new MissingArgumentException("userName");
        }
        return userManagerClient.getUserSign(userName);
    }

    /**
     * @param userId 用户ID
     * @return UserDto
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据userId获取用户所有的信息
     */
    public UserDto getUserByUserid(String userId) {
        return userManagerClient.getUserDetail(userId);
    }

    /**
     * 根据userID获取用户角色  ccx
     * @param userId
     * @return
     */
    public List<RoleDto> getRolesByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        return this.userManagerClient.findRoles(userId);
    }

    /*
     * 根据用户名获取所有的角色信息
     * */
    public List<RoleDto> getRolesByUserName(String userName) {
        UserDto userDto = this.getUserByName(userName);
        if (null == userDto || StringUtils.isBlank(userDto.getUsername())) {
            return Collections.emptyList();
        }
        return this.getRolesByUserId(userDto.getId());
    }

    /**
     * 获取当前用户所有角色
     * @return {List} 用户角色
     */
    public List<RoleDto> getCurrentUserRoles() {
        UserDto userDto = getCurrentUser();
        if(null == userDto || StringUtils.isBlank(userDto.getId())) {
            return null;
        }

        return getRolesByUserId(userDto.getId());
    }

    /**
     * 获取当前用户所有角色英文名称
     * @return {List} 用户角色名称
     */
    public List<String> getCurrentUserRoleNames() {
        List<RoleDto> roleDtoList = getCurrentUserRoles();
        if(CollectionUtils.isEmpty(roleDtoList)) {
            return null;
        }

        List<String> roleNames = new ArrayList<>();
        for(RoleDto roleDto : roleDtoList) {
            if(null == roleDto) {
                continue;
            }
            roleNames.add(roleDto.getName());
        }
        return roleNames;
    }

    /**
     * 获取当前用户所有角色英文名称 Set集合
     * @return {List} 用户角色名称
     */
    public Set<String> getCurrentUserRoleNameSet() {
       List<String> roleNames = getCurrentUserRoleNames();
       if(CollectionUtils.isEmpty(roleNames)) {
           return null;
       }

       Set<String> roleNameSet = new HashSet<>();
       for(String roleName : roleNames) {
           roleNameSet.add(roleName);
       }
       return roleNameSet;
    }

    /**
     * 判断当前用户是否有管理员角色  ccx
     *
     * @param userId
     * @return
     */
    public boolean isAdminByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            return false;
        }
        UserDto userDetail = userManagerClient.getUserDetail(userId);
        return userDetail.getAdmin() == 1;
    }

    /**
     * 判断当前用户是否有 roleName 角色
     *
     * @param userId
     * @param roleName
     * @return boolean
     */
    public boolean hasRoleByUserIdAndRoleName(String userId, String roleName) {
        if (StringUtils.isAnyBlank(userId, roleName)) {
            return false;
        }
        List<RoleDto> roleList = getRolesByUserId(userId);
        if (CollectionUtils.isNotEmpty(roleList)) {
            for (RoleDto role : roleList) {
                if (role != null && StringUtils.equals(role.getName(), roleName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取当前用户中文名称
     */
    public String getUserAlias() {
        UserDto userDto = this.getCurrentUser();
        if (null != userDto) {
            return userDto.getAlias();
        }
        return "";
    }

    /**
     * 获取所有的机构信息
     * @return {Map} 所有机构编码、名称
     */
    public Map<String, List<String>> getAllOrgInfo() {
        List<OrganizationDto> orgList = orgClient.listOrgs(CommonConstantUtils.SF_S_DM);
        if(CollectionUtils.isEmpty(orgList)) {
            return null;
        }

        // 按照排序号排序
        orgList.sort((o1, o2) -> o1.getSort() - o2.getSort());

        List<String> orgCodeList = new ArrayList<>();
        List<String> orgNameList = new ArrayList<>();
        for (OrganizationDto org : orgList) {
            orgCodeList.add(org.getCode());
            orgNameList.add(org.getName());
        }

        Map<String, List<String>> orgInfo = new HashMap<>();
        orgInfo.put("orgCode", orgCodeList);
        orgInfo.put("orgName", orgNameList);
        return orgInfo;
    }

    /**
     * 检查用户名和密码是否匹配，如果用户不存在也返回 false
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    public boolean checkUserPassword(String id, String password) {
        if (StringUtils.isAnyBlank(id, password)) {
            return false;
        }
        return userManagerClient.checkUserPassword(password, id);
    }

    /**
     * 查找组织机构
     *
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     */
    public Object listOrganizations() {
        List<OrganizationDto> orgList=orgClient.findRootOrgs();
        List<HashMap<String, Object>> result=new ArrayList<>();
        return analysisOrganizations(orgList,result);
    }

    /**
     * 递归获取子机构
     *
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     */
    private Object analysisOrganizations(List<OrganizationDto> list, List<HashMap<String, Object>> result) {
        for (OrganizationDto d : list) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", d.getId());
            map.put("title", d.getName());
            map.put("spread", false);      //设置是否展开
            List<OrganizationDto> listchildren=orgClient.findChildren(d.getId(),1);
            List<HashMap<String, Object>> result1 = new ArrayList<>();
            map.put("children", analysisOrganizations(listchildren,result1));
            result.add(map);
        }
        return result;
    }



    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 获取用户父部门
      */
    public List<OrganizationDto> listUserParentOrgs(String username){
        return orgClient.listUserParentOrgs(username);

    }
}
