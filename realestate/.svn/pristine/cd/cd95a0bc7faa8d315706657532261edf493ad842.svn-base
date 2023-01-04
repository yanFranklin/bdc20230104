package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.gtc.workflow.domain.define.ProcessModel;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZjQO;
import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.realestate.common.matcher.FlowableNodeClientMatcher;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.matcher.ProcessDefinitionClientMatcher;
import cn.gtmap.realestate.common.matcher.ProcessModelClientMatcher;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/12/20.
 * @description 通用调用大云流程接口服务
 * <p>1、获取所有顶级部门</p>
 * <p>2、根据部门ID查询下级部门信息</p>
 * <p>3、根据部门ID获取人员信息</p>
 * <p>4、获取所有工作流定义名称</p>
 * <p>5、根据工作流定义ID获取流程节点信息</p>
 * <p>6、获取bpm-manage配置下的节点名称</p>
 */
@RestController
@RequestMapping("/rest/v1.0/process")
public class BdcProcessController extends BaseController {

    @Autowired
    OrganizationManagerClientMatcher organizationManagerClient;
    @Autowired
    WorkFlowUtils workFlowUtils;
    @Autowired
    FlowableNodeClientMatcher flowableNodeClient;
    @Autowired
    ProcessTaskClient processTaskClient;

    @Autowired
    ProcessModelClientMatcher processModelClient;

    @Autowired
    private ProcessDefinitionClientMatcher processDefinitionClient;
    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 用户信息工具
     */
    @Autowired
    protected UserManagerUtils userManagerUtils;

    /**
     * 1、获取所有顶级组织信息
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return: Object 组织信息
     */
    @GetMapping("/rootOrgs")
    @ApiOperation(value = "获取所有顶级组织")
    @ResponseStatus(HttpStatus.OK)
    public Object queryRootOrgs(){
        return this.organizationManagerClient.findRootOrgs();
    }

    /**
     * 2、获取父组织下所有子组织信息
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [id] 父组织id
     * @return: Object 子组织信息
     */
    @GetMapping("/childrenOrgs/{id}")
    @ApiOperation(value = "根据父组织id获取子组织")
    @ResponseStatus(HttpStatus.OK)
    public Object queryChildrenOrgs(@PathVariable(value="id") String id){
        // enabled 0为禁用，1为启用，null为所有
        if(StringUtils.isEmpty(id)){
            return Collections.EMPTY_LIST;
        }
        return this.organizationManagerClient.findChildren(id,1);
    }

    /**
     * 3、根据组织id获取人员信息
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: id 组织id
     * @return: Object
     */
    @GetMapping("/users/{id}")
    @ApiOperation(value = "根据组织id获取人员信息")
    @ResponseStatus(HttpStatus.OK)
    public Object queryUsersByOrgId(@PathVariable(value="id") String id){
        if(StringUtils.isEmpty(id)){
            return Collections.EMPTY_LIST;
        }
        return this.organizationManagerClient.listUsersByOrg(id);
    }

    @PostMapping("/users")
    @ApiOperation(value = "批量接口，根据组织id数组，获取人员信息")
    @ResponseStatus(HttpStatus.OK)
    public Object queryUsersByOrgId(@RequestBody List<String> orgIdList){
        if(CollectionUtils.isEmpty(orgIdList)){
            return Collections.EMPTY_LIST;
        }
        List<UserDto> userDtoList = new ArrayList<>(orgIdList.size());
        for(String orgId : orgIdList){
            List<UserDto> users = this.organizationManagerClient.listUsersByOrg(orgId);
            if(CollectionUtils.isNotEmpty(users)){
                userDtoList.addAll(users);
            }
        }
        if(CollectionUtils.isNotEmpty(userDtoList)){
            //对用户进行去重
            userDtoList = userDtoList.stream().
                    collect(Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(UserDto::getId))), ArrayList::new));
        }
        return userDtoList;
    }

    /**
     * 4、调用大云接口获取工作流定义名称
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return: java.lang.Object
     * @description
     */
    @GetMapping("/gzldymcs")
    @ApiOperation(value = "获取工作流定义名称")
    @ResponseStatus(HttpStatus.OK)
    public Object queryGzldymcs(){
        List<ProcessDefData> processDefDataList=workFlowUtils.getAllProcessDefData();
        processDefDataList=processDefDataList.stream().filter(processDefData ->
                processDefData.getSuspensionState()==1
        ).collect(Collectors.toList());
        return processDefDataList;
    }

    /**
     * 5、根据流程定义id获取节点信息
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: processDefKey 流程定义id
     * @return: Object
     * @description
     */
    @GetMapping("/node/{processDefKey}")
    @ApiOperation(value = "根据流程定义id获取节点信息")
    @ResponseStatus(HttpStatus.OK)
    public Object queryProcessNodeByOrgId(@PathVariable(value="processDefKey") String processDefKey){
        if(StringUtils.isEmpty(processDefKey)){
            return Collections.EMPTY_LIST;
        }
        return this.flowableNodeClient.getAllUserTaskByProcDefKey(processDefKey);
    }

    @GetMapping("/node/config")
    @ApiOperation(value = "获取bpm-manage配置下的所有节点名称")
    @ResponseStatus(HttpStatus.OK)
    public Object queryProcessNodeByConfig(){
        return this.flowableNodeClient.listAllTaskNames();
    }


    @GetMapping("/listOrgs")
    @ApiOperation(value = "获取所有组织")
    @ResponseStatus(HttpStatus.OK)
    public Object queryAllOrgs(){
        return this.organizationManagerClient.listOrgs(1);
    }

    /**
     * 查找组织机构
     *
     * @return List<OrganizationDto>
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/dept/allnew")
    @ApiOperation(value = "查找组织机构")
    @ResponseStatus(HttpStatus.OK)
    public Object  listRootOrgsNew() { return userManagerUtils.listOrganizations(); }

    @GetMapping(value = "/listAllCategoryProcess")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取所有流程")
    public Object listAllCategoryProcess() {
        Map<String, Object> result = new HashMap<>();
        result.put("content", processTaskClient.listAllCategoryProcess());
        return result;
    }

    /**
     * 获取分类下的工作流
     *
     */
    @GetMapping(value ="/list/category/processId")
    public Object listBdcZjxx(@RequestParam String category) {
        if(StringUtils.isBlank(category)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数业务分类。");
        }
        // 获取业务分类下所有流程
        List<ProcessDefData>  processModelList = processDefinitionClient.getProcessDefDataForCategory(category);
        return processModelList.stream()
                .map(ProcessDefData::getProcessDefKey)
                .collect(Collectors.toList());

    }
}
