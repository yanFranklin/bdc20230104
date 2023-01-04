package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.gtc.clients.RegionManagerClient;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.RegionDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcXtZsbhmbDO;
import cn.gtmap.realestate.common.core.qo.config.BdcXtZsbhmbQO;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXtZsbhmbFeignService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/01/11
 * @description 业务配置系统：证书编号模板配置请求处理控制器
 */
@RestController
@RequestMapping("/rest/v1.0")
public class BdcXtZsbhmbController extends BaseController {
    /**
     * 用户信息
     */
    @Autowired
    private UserManagerUtils userManagerUtils;
    /**
     * 证书编号模板服务
     */
    @Autowired
    private BdcXtZsbhmbFeignService bdcXtZsbhmbFeignService;
    /**
     * 行政区划服务
     */
    @Autowired
    private RegionManagerClient regionManagerClient;
    /**
     * 组织部门服务
     */
    @Autowired
    private OrganizationManagerClientMatcher organizationManagerClient;


    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  pageable 分页对象
     * @param  zsbhmbQO 查询条件
     * @return 证书编号模板
     * @description  查询证书编号模板数据列表
     */
    @GetMapping("/zsbhmb")
    public Object queryBdcZsbhmb(@LayuiPageable Pageable pageable, BdcXtZsbhmbQO zsbhmbQO){
        Page<BdcXtZsbhmbDO> bdcXtZsbhmbDOPage = bdcXtZsbhmbFeignService.queryBdcXtZsbhmb(pageable, JSON.toJSONString(zsbhmbQO));
        return super.addLayUiCode(bdcXtZsbhmbDOPage);
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcXtZsbhmbDO 证书编号模板实体
     * @return {int} 操作数据记录数
     * @description  保存证书编号模板配置
     */
    @PutMapping("/zsbhmb")
    public int saveBdcXtZsbhmb(@RequestBody BdcXtZsbhmbDO bdcXtZsbhmbDO){
        return bdcXtZsbhmbFeignService.saveBdcXtZsbhmb(bdcXtZsbhmbDO);
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcXtZsbhmbDOList 证书编号模板实体
     * @return {int} 操作数据记录数
     * @description  保存证书编号模板配置
     */
    @DeleteMapping("/zsbhmb")
    public int deleteBdcXtZsbhmb(@RequestBody List<BdcXtZsbhmbDO> bdcXtZsbhmbDOList){
        return bdcXtZsbhmbFeignService.deleteBdcXtZsbhmb(bdcXtZsbhmbDOList);
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {List} 字典
     * @description  获取区县代码字典
     *   说明：
     *   旧版是调用楼盘表服务，现在改为调用大云，但是为了接口对外一致，所以还是返回List<Map>
     */
    @GetMapping("/zsbhmb/zd/qxdm")
    public List<Map> queryQdxmZd(){
        List<Map> result = new ArrayList<>(20);
        List<RegionDto> regionDtolist = new ArrayList<>(20);
        // 获取市级代码
        regionDtolist.addAll(regionManagerClient.findRegionsByLevel(2));
        // 获取县级代码
        regionDtolist.addAll(regionManagerClient.findRegionsByLevel(3));
        if(CollectionUtils.isNotEmpty(regionDtolist)){
            for(RegionDto regionDto : regionDtolist){
                Map<String, Object> region = new HashMap<>(3);
                region.put("XZDM", regionDto.getCode());
                region.put("XZMC", regionDto.getName());
                region.put("XZJB", regionDto.getLevel());
                result.add(region);
            }
        }
        return result;
    }

    /**
     * 根据当前用户的区县代码获取区县代码字典 如果是管理员则调用queryQdxmZd方法   ccx 2019-10-06
     * @return
     */
    @GetMapping("/zsbhmb/zd/qxdmbyuser")
    public List<Map> queryQdxmZdByUser(){
        List<Map> result = new ArrayList<>(20);
        List<RegionDto> regionDtolist = new ArrayList<>(20);
        UserDto user = userManagerUtils.getCurrentUser();
        // 是否有管理员角色
        boolean isAdmin = false;
        if(user != null && StringUtils.isNotBlank(user.getId())){
            isAdmin = userManagerUtils.isAdminByUserId(user.getId());
        }
        if(isAdmin){
            result = queryQdxmZd();
        }else{
            // 根据当前用户查询区县代码
            String reginCode = userManagerUtils.getRegionCodeByUserName(user.getUsername());
            if(StringUtils.isNotBlank(reginCode)){
                regionDtolist.add(regionManagerClient.findRegionByCode(reginCode));
            }
            if(CollectionUtils.isNotEmpty(regionDtolist)){
                for(RegionDto regionDto : regionDtolist){
                    Map<String, Object> region = new HashMap<>(3);
                    region.put("XZDM", regionDto.getCode());
                    region.put("XZMC", regionDto.getName());
                    region.put("XZJB", regionDto.getLevel());
                    result.add(region);
                }
            }
        }
        return result;
    }
    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {String} 省区代码
     * @description  从证书模板配置中获取省区代码（正常来说所有模板配置的是一致的）
     */
    @GetMapping("/zsbhmb/sqdm")
    public String querySqdm(){
        return bdcXtZsbhmbFeignService.querySqdm();
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param zsbhmbid 证书编号模板ID
     * @return {String} 新的证书编号模板id
     * @description 根据证书编号模板id复制一条当前的证书信息
     */
    @PostMapping(value = "/copyZsbhmb/{zsbhmbid}")
    public String copyZsbhmbByZsbhmbid(@PathVariable("zsbhmbid") String zsbhmbid){
        return bdcXtZsbhmbFeignService.copyBdcXtZsbhmb(zsbhmbid);
    }

    /**
     * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
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
        // 去重
        users=users.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                () -> new TreeSet<>(Comparator.comparing(UserDto::getId))), ArrayList::new));
        return users;
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 获取用户信息
     */
    @GetMapping("/user/info")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取用户基本资料")
    public Object queryUserInfo() {
        return userManagerUtils.getCurrentUser();
    }

    /**
     * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
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
