package cn.gtmap.realestate.portal.ui.web.rest;


import cn.gtmap.gtc.clients.RoleManagerClient;
import cn.gtmap.gtc.sso.domain.dto.OrgUserDto;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.portal.ui.core.qo.BdcXtOrgQO;
import cn.gtmap.realestate.portal.ui.core.vo.BdcXtOrgUserVO;
import cn.gtmap.realestate.portal.ui.utils.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
     * 角色信息
     */
    @Autowired
    private RoleManagerClient roleManagerClient;

    @PostMapping("/orgs")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8")
    public List<OrganizationDto> queryOrgs(BdcXtOrgQO bdcXtOrgQO){
        if(null == bdcXtOrgQO || StringUtils.isBlank(bdcXtOrgQO.getPid())){
            return organizationManagerClient.findRootOrgs();
        }

        return organizationManagerClient.findChildren(bdcXtOrgQO.getPid(), 1);
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
}
