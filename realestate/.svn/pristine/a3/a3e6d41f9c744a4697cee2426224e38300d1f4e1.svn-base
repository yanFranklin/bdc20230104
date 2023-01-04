package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-03-01
 * @description 不动产字典服务
 */
@Controller
@RequestMapping("/bdczd")
public class BdcZdController extends BaseController {

    @Autowired
    private OrganizationManagerClientMatcher organizationManagerClient;

    @Autowired
    private WorkFlowUtils workFlowUtils;



    /**
     * @param zdNames 逗号隔开的字典名称
     * @return java.util.Map
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @RequestMapping("/mul")
    @ResponseBody
    public Map mulListZd(String zdNames) {
        if (StringUtils.isNotBlank(zdNames)) {
            String[] arr = zdNames.split(",");
            return getZdList(arr);
        }
        return new HashMap(0);
    }

    /**
     * @param zdNames 逗号隔开的字典名称
     * @return java.util.Map
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description
     */
    @RequestMapping("/sl/mul")
    @ResponseBody
    public Map slMulListZd(String zdNames) {
        if (StringUtils.isNotBlank(zdNames)) {
            String[] arr = zdNames.split(",");
            return getSlZdList(arr);
        }
        return new HashMap(0);
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 获取字典
      */
    @RequestMapping("/all/mul")
    @ResponseBody
    public Map allMulListZd(String zdNames,String zdly) {
        if (StringUtils.isNotBlank(zdNames)) {
            String[] arr = zdNames.split(",");
            if(StringUtils.equals(CommonConstantUtils.ZDLY_SL,zdly)) {
                return getSlZdList(arr);
            }else if(StringUtils.equals(CommonConstantUtils.ZDLY_DJ,zdly) ||StringUtils.isBlank(zdly)){
                return getZdList(arr);

            }else if(StringUtils.equals(CommonConstantUtils.ZDLY_DY,zdly)){
                return getDyZdList(arr);
            }
        }
        return new HashMap(0);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {Map} 部门代码名称Map
     * @description 获取一级部门信息
     */
    @RequestMapping("/bm/list")
    @ResponseBody
    public Map<String,String> queryAllBm() {
        List<OrganizationDto> orgDtoList = organizationManagerClient.findRootOrgs();
        if(CollectionUtils.isEmpty(orgDtoList)){
            return null;
        }
        return orgDtoList.stream().collect(Collectors.toMap(org -> org.getCode(), org -> org.getName()));
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

    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @return {Map} 部门代码名称Map
     * @description 获取所有的组织机构信息
     */
    @GetMapping("/org/list/all")
    @ResponseBody
    public Map<String,String> queryAllBmDmAndMc() {
        List<OrganizationDto> orgDtoList = listOrgs();
        if(CollectionUtils.isEmpty(orgDtoList)){
            return null;
        }
        return orgDtoList.stream().collect(Collectors.toMap(org -> org.getCode(), org -> org.getName()));
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
     * 获得大云需要的所有字典项list
     *
     * @param zdNames
     * @return
     */
    private Map getDyZdList(String[] zdNames) {
        Map<String, List<Map>> resultMap = new HashMap<>(zdNames.length);
        if (ArrayUtils.isNotEmpty(zdNames)) {
            for (String zdName : zdNames) {
                if(StringUtils.equals(CommonConstantUtils.ZDNAME_LC,zdName)){
                    List<ProcessDefData> processDefDataList=workFlowUtils.getAllProcessDefData();
                    if(CollectionUtils.isNotEmpty(processDefDataList)){
                        List<Map> mapList =new ArrayList<>();
                        processDefDataList.forEach(processDefData -> {
                            Map map =new HashMap(2);
                            map.put("DM",processDefData.getKey());
                            map.put("MC",processDefData.getName());
                            mapList.add(map);
                        });
                        resultMap.put(zdName, mapList);
                    }

                }
            }
        }
        return resultMap;
    }

    /**
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @return {Map} 部门代码名称Map
     * @description 获取二级组织机构信息
     */
    @GetMapping("/childorg/list")
    @ResponseBody
    public List<OrganizationDto> listChildOrgs() {
        // 一级机构
        List<OrganizationDto> rootOrgList = organizationManagerClient.findRootOrgs();
        if(CollectionUtils.isEmpty(rootOrgList)){
            return Collections.emptyList();
        }

        List<OrganizationDto> secondOrgList = new ArrayList<>(10);

        for(OrganizationDto rootOrg : rootOrgList){
            if (null != rootOrg) {
                // 查找子机构（目前根据实际情况直接查询二级，)
                List<OrganizationDto> childOrgList = organizationManagerClient.findChildren(rootOrg.getId(), 1);
                if (CollectionUtils.isNotEmpty(childOrgList)) {
                    secondOrgList.addAll(childOrgList);
                }
            }
        }
        return secondOrgList;
    }

    @ResponseBody
    @PostMapping("")
    public Map<String, List<Map>> initzd() {
        Map<String, List<Map>> zdMap = new HashMap<>();
        Map<String, List<Map>> slZdMap = new HashMap<>();
        try {
            zdMap = bdcZdFeignService.listBdcZd();
            slZdMap = bdcSlZdFeignService.listBdcSlzd();
        } catch (Exception e) {
            LOGGER.error("字典项服务获取失败");
        }
        zdMap.putAll(slZdMap);
        return zdMap;
    }
}
