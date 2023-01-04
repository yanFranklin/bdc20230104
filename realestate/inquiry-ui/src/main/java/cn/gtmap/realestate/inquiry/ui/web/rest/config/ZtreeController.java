package cn.gtmap.realestate.inquiry.ui.web.rest.config;


import cn.gtmap.gtc.clients.UserManagerClient;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.vo.inquiryui.ZtreeNodeVO;
import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.gtc.clients.RoleManagerClient;
import cn.gtmap.gtc.sso.domain.dto.*;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.inquiry.ui.core.qo.BdcXtOrgQO;
import cn.gtmap.realestate.inquiry.ui.core.vo.BdcXtOrgUserVO;
import cn.gtmap.realestate.inquiry.ui.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.wml.U;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/03/23
 * @description 页面使用 ztree 展现树形结构的人员、部门、角色信息数据请求处理控制器
 */
@RestController
@RequestMapping("/rest/v1.0/ztree")
public class ZtreeController {

    /**
     * 印制号人员过滤角色配置
     */
    @Value("#{'${zsyzh.roles:}'.split(',')}")
    private List<String> rolesList;


    /**
     * 是否要过滤部门的条件
     */
    @Value("${cxtj.sfglbm:false}")
    private boolean sfglbm;

    /**
     * 是否要过滤部门的条件
     */
    @Value("#{'${cxtj.sfglbm.roles:}'.split(',')}")
    private List<String> cxtjTsRoleList;


    /**
     * 组织机构服务
     */
    @Autowired
    private OrganizationManagerClientMatcher organizationManagerClient;
    /**
     * 角色信息
     */
    @Autowired
    private RoleManagerClient roleManagerClient;

    @Autowired
    UserManagerClient userManagerClient;

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">yaoyi</a>
     * @return  {List} 机构集合
     * @description 异步加载根据pid获取可用组织机构
     */
    @PostMapping("/orgs/all")
    public List<ZtreeNodeVO> queryAllOrgs(){
        List<OrganizationDto> rootOrgList =  organizationManagerClient.findRootOrgs();
        if(CollectionUtils.isEmpty(rootOrgList)){
            throw new AppException(ErrorCode.CUSTOM, "未获取到组织机构信息");
        }
        List<OrganizationDto> orgList = organizationManagerClient.listOrgs(1);

        List<ZtreeNodeVO> ztreeNodeVOList = new ArrayList<>(rootOrgList.size());
        for(OrganizationDto rootOrg : rootOrgList){
            ZtreeNodeVO parent = new ZtreeNodeVO();
            BeanUtils.copyProperties(rootOrg, parent);
            handlerOrgs(parent, orgList);
            ztreeNodeVOList.add(parent);
        }
        return ztreeNodeVOList;
    }

    /**
     * 递归构造树形结构数据
     */
    private static void handlerOrgs(ZtreeNodeVO ztreeNodeVO, List<OrganizationDto> orgList){
        for(OrganizationDto organizationDto : orgList){
            ZtreeNodeVO children = new ZtreeNodeVO();
            BeanUtils.copyProperties(organizationDto, children);
            if(ztreeNodeVO.getId().equals(children.getParentId())){
                if(CollectionUtils.isNotEmpty(ztreeNodeVO.getChildren())){
                    ztreeNodeVO.getChildren().add(children);
                }else{
                    List<ZtreeNodeVO> childrenList = new ArrayList<>();
                    childrenList.add(children);
                    ztreeNodeVO.setChildren(childrenList);
                }
                if(ztreeNodeVO.getIsParent()){
                    handlerOrgs(children, orgList);
                }
            }
        }
    }

    @PostMapping("/orgs")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8")
    public List<OrganizationDto> queryOrgs(BdcXtOrgQO bdcXtOrgQO){
        if(null == bdcXtOrgQO || StringUtils.isBlank(bdcXtOrgQO.getPid())){
            return organizationManagerClient.findRootOrgs();
        }

        return organizationManagerClient.findChildren(bdcXtOrgQO.getPid(), 1);
    }


    @PostMapping("/orgs/gl")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8")
    public List<OrganizationDto> queryGlOrgs(BdcXtOrgQO bdcXtOrgQO){
        if(Objects.nonNull(bdcXtOrgQO)
                && sfglbm
                && Objects.nonNull(userManagerClient.getCurrentUser())){
            UserDto currentUser = userManagerClient.getCurrentUser();
            if (CollectionUtils.isEmpty(cxtjTsRoleList) || StringUtils.isEmpty(cxtjTsRoleList.get(0))){
                return getOrgByUserIds(currentUser.getId());
            }
            List<RoleDto> roleRecordList = userManagerClient.getCurrentUser().getRoleRecordList();
            if (CollectionUtils.isNotEmpty(roleRecordList)){
                //是否有不需要过滤部门的角色
                boolean containSpicalRole = roleRecordList
                        .stream()
                        .anyMatch(roleDto -> cxtjTsRoleList.contains(roleDto.getName()));
                if(!containSpicalRole){
                    return getOrgByUserIds(currentUser.getId());
                }
            }
        }
        if(null == bdcXtOrgQO || StringUtils.isBlank(bdcXtOrgQO.getPid())){
            return organizationManagerClient.findRootOrgs();
        }

        return organizationManagerClient.findChildren(bdcXtOrgQO.getPid(), 1);
    }

    @GetMapping("/orgs/{userids}")
    public List<OrganizationDto> getOrgByUserIds(@PathVariable("userids") String userids) {
        List<String> idList = Arrays.asList(userids.split(","));
        List<OrganizationDto> orgList = new ArrayList<>();
        for (String id : idList) {
            UserDto userDto = userManagerClient.getUserDetail(id);
            if (userDto != null) {
                List<OrganizationDto> organizationDtoList = userDto.getOrgRecordList();
                if (CollectionUtils.isNotEmpty(organizationDtoList)) {
                    orgList.addAll(organizationDtoList);
                }
            }
        }
        return orgList;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return  {List} 用户集合
     * @description  获取指定机构下的机构、用户集合
     */
    @PostMapping("/org/users")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8")
    public List<BdcXtOrgUserVO> queryOrgUsers(BdcXtOrgQO bdcXtOrgQO){
        if(null == bdcXtOrgQO || StringUtils.isBlank(bdcXtOrgQO.getPid())){
            return this.generateOrgData(organizationManagerClient.listGradeOrgUsers(CommonConstantUtils.SF_S_DM, null));
        }
        List<BdcXtOrgUserVO> orgUserVOList = new ArrayList<>(10);
        // 先查询机构并处理机构数据格式
        List<BdcXtOrgUserVO> orgList = this.generateOrgData(organizationManagerClient.listGradeOrgUsers(CommonConstantUtils.SF_S_DM, bdcXtOrgQO.getPid()));
        orgUserVOList.addAll(orgList);

        // 获取有用户并处理用户数据格式, 由于查询用户只有分页接口，所以这里直接将分页值设置较大些，以查询出当前机构下所有用户
        List<UserDto> userDtoList = organizationManagerClient.findUsersByOrg(new PageRequest(0, 5000), bdcXtOrgQO.getPid()).getContent();
        orgUserVOList.addAll(this.generateUserData(userDtoList, bdcXtOrgQO.getPid()));

        return orgUserVOList;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return  {List} 用户集合
     * @description  获取指定机构下的机构、用户集合（需要过滤指定角色）
     */
    @PostMapping("/org/roles/users")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8")
    public List<BdcXtOrgUserVO> queryOrgUsersWithRoles(BdcXtOrgQO bdcXtOrgQO){
        // 如果指定了角色，则直接展示用户
        if(CollectionUtils.isNotEmpty(rolesList) && StringUtils.isNotBlank(rolesList.get(0))){
            List<UserDto> userDtoList = this.getUsersByRoles();
            return this.generateUserData(userDtoList, null);
        }

        if(null == bdcXtOrgQO || StringUtils.isBlank(bdcXtOrgQO.getPid())){
            return this.generateOrgData(organizationManagerClient.listGradeOrgUsers(CommonConstantUtils.SF_S_DM, null));
        }

        // 先查询机构并处理机构数据格式
        List<BdcXtOrgUserVO> orgUserVOList = new ArrayList<>(10);
        List<BdcXtOrgUserVO> orgList = this.generateOrgData(organizationManagerClient.listGradeOrgUsers(CommonConstantUtils.SF_S_DM, bdcXtOrgQO.getPid()));
        orgUserVOList.addAll(orgList);

        // 再获取有用户并处理用户数据格式
        /// 由于查询用户只有分页接口，所以这里直接将分页值设置较大些，以查询出当前机构下所有用户（不递归查询子部门）
//        List<UserDto> userDtoList = organizationManagerClient.findUsersByOrg(new PageRequest(0, 5000), bdcXtOrgQO.getPid()).getContent();
        List<UserDto> userDtoList = organizationManagerClient.queryUsersByOrg(new PageRequest(0, 5000), bdcXtOrgQO.getPid()).getContent();
        orgUserVOList.addAll(this.generateUserData(userDtoList, bdcXtOrgQO.getPid()));

        return orgUserVOList;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return  {List} 机构用户集合
     * @description  将查询到的机构数据封装成指定格式实体数据
     */
    private List<BdcXtOrgUserVO> generateOrgData(List<OrgUserDto> orgUserDtoList){
        if(CollectionUtils.isEmpty(orgUserDtoList)){
            return Collections.emptyList();
        }
        List<BdcXtOrgUserVO> orgUserVOList = new ArrayList<>(orgUserDtoList.size());

        for(OrgUserDto orgUserDto : orgUserDtoList){
            BdcXtOrgUserVO bdcXtOrgUserVO = new BdcXtOrgUserVO();
            bdcXtOrgUserVO.setId(orgUserDto.getId());
            bdcXtOrgUserVO.setParentId(orgUserDto.getParentId());
            bdcXtOrgUserVO.setName("[机构]" + orgUserDto.getName());
            bdcXtOrgUserVO.setType(Constants.TYPE_ORG);
            //单独判断时，导致获取用户出现问题，故将两个判断以或的关系进行判断
            if (orgUserDto.getIsParent() || CollectionUtils.isNotEmpty(orgUserDto.getUserDtos())){
                bdcXtOrgUserVO.setIsParent(true);
            }else {
                bdcXtOrgUserVO.setIsParent(false);
            }
            orgUserVOList.add(bdcXtOrgUserVO);
        }
        return orgUserVOList;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return  {List} 机构用户集合
     * @description  将查询到的用户数据封装成指定格式实体数据
     */
    private List<BdcXtOrgUserVO> generateUserData(List<UserDto> userDtoList, String pid){
        if(CollectionUtils.isEmpty(userDtoList)){
            return Collections.emptyList();
        }

        List<BdcXtOrgUserVO> orgUserVOList = new ArrayList<>(userDtoList.size());
        for (UserDto userDto : userDtoList){
            BdcXtOrgUserVO bdcXtOrgUserVO = new BdcXtOrgUserVO();
            bdcXtOrgUserVO.setId(userDto.getId());
            bdcXtOrgUserVO.setParentId(pid);
            bdcXtOrgUserVO.setName("[人员]" + userDto.getAlias());
            bdcXtOrgUserVO.setType(Constants.TYPE_USER);
            bdcXtOrgUserVO.setAccount(userDto.getUsername());
            bdcXtOrgUserVO.setIsParent(false);

            orgUserVOList.add(bdcXtOrgUserVO);
        }
        return orgUserVOList;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  获取配置的角色对应的人员
     */
    private List<UserDto> getUsersByRoles(){
        if(CollectionUtils.isEmpty(rolesList)){
            return Collections.emptyList();
        }

        // 先根据角色编码获取角色
        List<RoleDto> roleDtos = roleManagerClient.queryRoleByRoleNames(rolesList);
        if(CollectionUtils.isEmpty(roleDtos)){
            return Collections.emptyList();
        }

        // 根据角色ID获取人员
        List<RoleUserDto> roleUserDtoList =  roleManagerClient.listRoleUsers(
                roleDtos.stream().map(RoleDto::getId).collect(Collectors.toList()));
        if(CollectionUtils.isEmpty(roleUserDtoList)){
            return Collections.emptyList();
        }

        // 过滤用户
        List<UserDto> userDtoList = new ArrayList<>(10);
        for(RoleUserDto roleUserDto : roleUserDtoList){
            if(null != roleUserDto && CollectionUtils.isNotEmpty(roleUserDto.getUserDtos())){
                for(UserDto user : roleUserDto.getUserDtos()){
                    List<String> ids = userDtoList.stream().map(UserDto::getId).collect(Collectors.toList());
                    if(CollectionUtils.isEmpty(ids) ||  !ids.contains(user.getId())){
                        userDtoList.add(user);
                    }
                }
            }
        }
        return userDtoList;
    }
}
