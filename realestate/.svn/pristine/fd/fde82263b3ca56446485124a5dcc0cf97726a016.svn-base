package cn.gtmap.realestate.supervise.web;


import cn.gtmap.gtc.clients.RoleManagerClient;
import cn.gtmap.gtc.sso.domain.dto.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.vo.inquiryui.ZtreeNodeVO;
import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.supervise.core.qo.BdcXtOrgQO;
import cn.gtmap.realestate.supervise.core.vo.BdcXtOrgUserVO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
     * 组织机构服务
     */
    @Autowired
    private OrganizationManagerClientMatcher organizationManagerClient;

    /**
     * 机构类型
     */
    public static final String TYPE_ORG = "org";
    /**
     * 用户类型
     */
    public static final String TYPE_USER = "user";

    /**
     * @return {List} 机构集合
     * @author <a href="mailto:zhuyong@gtmap.cn">yaoyi</a>
     * @description 异步加载根据pid获取可用组织机构
     */
    @PostMapping("/orgs/all")
    public List<ZtreeNodeVO> queryAllOrgs() {
        List<OrganizationDto> rootOrgList = organizationManagerClient.findRootOrgs();
        if (CollectionUtils.isEmpty(rootOrgList)) {
            throw new AppException(ErrorCode.CUSTOM, "未获取到组织机构信息");
        }
        List<OrganizationDto> orgList = organizationManagerClient.listOrgs(1);

        List<ZtreeNodeVO> ztreeNodeVOList = new ArrayList<>(rootOrgList.size());
        for (OrganizationDto rootOrg : rootOrgList) {
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
    private static void handlerOrgs(ZtreeNodeVO ztreeNodeVO, List<OrganizationDto> orgList) {
        for (OrganizationDto organizationDto : orgList) {
            ZtreeNodeVO children = new ZtreeNodeVO();
            BeanUtils.copyProperties(organizationDto, children);
            if (ztreeNodeVO.getId().equals(children.getParentId())) {
                if (CollectionUtils.isNotEmpty(ztreeNodeVO.getChildren())) {
                    ztreeNodeVO.getChildren().add(children);
                } else {
                    List<ZtreeNodeVO> childrenList = new ArrayList<>();
                    childrenList.add(children);
                    ztreeNodeVO.setChildren(childrenList);
                }
                if (ztreeNodeVO.getIsParent()) {
                    handlerOrgs(children, orgList);
                }
            }
        }
    }

    @PostMapping("/orgs")
    public List<OrganizationDto> queryOrgs(BdcXtOrgQO bdcXtOrgQO) {
        if (null == bdcXtOrgQO || StringUtils.isBlank(bdcXtOrgQO.getPid())) {
            return organizationManagerClient.findRootOrgs();
        }

        return organizationManagerClient.findChildren(bdcXtOrgQO.getPid(), 1);
    }

    /**
     * @return {List} 用户集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取指定机构下的机构、用户集合
     */
    @PostMapping("/org/users")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8")
    public List<BdcXtOrgUserVO> queryOrgUsers(BdcXtOrgQO bdcXtOrgQO) {
        if (null == bdcXtOrgQO || StringUtils.isBlank(bdcXtOrgQO.getPid())) {
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
     * @return {List} 用户集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取指定机构下的机构、用户集合（
     */
    @PostMapping("/org/roles/users")
    public List<BdcXtOrgUserVO> queryOrgUsersWithRoles(BdcXtOrgQO bdcXtOrgQO) {
        if (null == bdcXtOrgQO || StringUtils.isBlank(bdcXtOrgQO.getPid())) {
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
     * @return {List} 机构用户集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 将查询到的机构数据封装成指定格式实体数据
     */
    private List<BdcXtOrgUserVO> generateOrgData(List<OrgUserDto> orgUserDtoList) {
        if (CollectionUtils.isEmpty(orgUserDtoList)) {
            return Collections.emptyList();
        }
        List<BdcXtOrgUserVO> orgUserVOList = new ArrayList<>(orgUserDtoList.size());

        for (OrgUserDto orgUserDto : orgUserDtoList) {
            BdcXtOrgUserVO bdcXtOrgUserVO = new BdcXtOrgUserVO();
            bdcXtOrgUserVO.setId(orgUserDto.getId());
            bdcXtOrgUserVO.setParentId(orgUserDto.getParentId());
            bdcXtOrgUserVO.setName("[机构]" + orgUserDto.getName());
            bdcXtOrgUserVO.setType(TYPE_ORG);
            //单独判断时，导致获取用户出现问题，故将两个判断以或的关系进行判断
            if (orgUserDto.getIsParent() || CollectionUtils.isNotEmpty(orgUserDto.getUserDtos())) {
                bdcXtOrgUserVO.setIsParent(true);
            } else {
                bdcXtOrgUserVO.setIsParent(false);
            }
            orgUserVOList.add(bdcXtOrgUserVO);
        }
        return orgUserVOList;
    }

    /**
     * @return {List} 机构用户集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 将查询到的用户数据封装成指定格式实体数据
     */
    private List<BdcXtOrgUserVO> generateUserData(List<UserDto> userDtoList, String pid) {
        if (CollectionUtils.isEmpty(userDtoList)) {
            return Collections.emptyList();
        }

        List<BdcXtOrgUserVO> orgUserVOList = new ArrayList<>(userDtoList.size());
        for (UserDto userDto : userDtoList) {
            BdcXtOrgUserVO bdcXtOrgUserVO = new BdcXtOrgUserVO();
            bdcXtOrgUserVO.setId(userDto.getId());
            bdcXtOrgUserVO.setParentId(pid);
            bdcXtOrgUserVO.setName("[人员]" + userDto.getAlias());
            bdcXtOrgUserVO.setType(TYPE_USER);
            bdcXtOrgUserVO.setAccount(userDto.getUsername());
            bdcXtOrgUserVO.setIsParent(false);

            orgUserVOList.add(bdcXtOrgUserVO);
        }
        return orgUserVOList;
    }

    @PostMapping("/users")
    @ApiOperation(value = "批量接口，根据组织id数组，获取人员信息")
    @ResponseStatus(HttpStatus.OK)
    public Object queryUsersByOrgId(@RequestBody List<String> orgIdList) {
        if (CollectionUtils.isEmpty(orgIdList)) {
            return Collections.EMPTY_LIST;
        }
        List<UserDto> userDtoList = new ArrayList<>(orgIdList.size());
        for (String orgId : orgIdList) {
            List<UserDto> users = this.organizationManagerClient.listUsersByOrg(orgId);
            if (CollectionUtils.isNotEmpty(users)) {
                userDtoList.addAll(users);
            }
        }
        if (CollectionUtils.isNotEmpty(userDtoList)) {
            //对用户进行去重
            userDtoList = userDtoList.stream().
                    collect(Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(UserDto::getId))), ArrayList::new));
        }
        return userDtoList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {Map} 部门代码名称Map
     * @description 获取所有用户信息
     */
    @GetMapping("/user/list")
    @ResponseBody
    public List<UserDto> listUsers() {
        List<OrganizationDto> orgList = this.listOrgs();
        if(CollectionUtils.isEmpty(orgList)){
            return Collections.emptyList();
        }

        List<UserDto> users = new ArrayList<>(10);
        for(OrganizationDto org : orgList){
            if(null != org){
                List<UserDto> userDtoList = organizationManagerClient.listUsersByOrg(org.getId());
                if(CollectionUtils.isNotEmpty(userDtoList)){
                    users.addAll(userDtoList);
                }
            }
        }
        return users;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {Map} 部门代码名称Map
     * @description 获取所有的组织机构信息
     */
    @GetMapping("/org/list")
    @ResponseBody
    public List<OrganizationDto> listOrgs() {
        // 一级机构
        List<OrganizationDto> rootOrgList = organizationManagerClient.findRootOrgs();
        if(CollectionUtils.isEmpty(rootOrgList)){
            return Collections.emptyList();
        }

        List<OrganizationDto> allOrgList = new ArrayList<>(10);
        allOrgList.addAll(rootOrgList);

        for(OrganizationDto rootOrg : rootOrgList){
            if(null != rootOrg){
                // 查找子机构（目前根据实际情况直接查询二级，)
                List<OrganizationDto> childOrgList = organizationManagerClient.findChildren(rootOrg.getId(), 1);
                if(CollectionUtils.isNotEmpty(childOrgList)){
                    allOrgList.addAll(childOrgList);
                }
            }
        }
        return allOrgList;
    }

}
