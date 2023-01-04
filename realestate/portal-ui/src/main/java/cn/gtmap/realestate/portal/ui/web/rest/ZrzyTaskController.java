package cn.gtmap.realestate.portal.ui.web.rest;

import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.gtc.formclient.common.client.FormThirdResourceClient;
import cn.gtmap.gtc.formclient.common.dto.FormElementConfigDTO;
import cn.gtmap.gtc.formclient.common.dto.FormStateDTO;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskCustomExtendClient;
import cn.gtmap.gtc.workflow.domain.common.RequestCondition;
import cn.gtmap.gtc.workflow.domain.manage.OpinionDto;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.gtc.workflow.domain.manage.UserTaskDto;
import cn.gtmap.gtc.workflow.enums.manage.QueryJudge;
import cn.gtmap.gtc.workflow.enums.task.CommentType;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcDjxlPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCshXtPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.matcher.FlowableNodeClientMatcher;
import cn.gtmap.realestate.common.matcher.FormStateClientMatcher;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import cn.gtmap.realestate.portal.ui.config.ZrzyQueryParamsConfig;
import cn.gtmap.realestate.portal.ui.core.dto.OrderCondition;
import cn.gtmap.realestate.portal.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0, 2019/02/25
 * @description 任务信息请求处理控制器，包括：待办任务、已办任务、项目列表、认领
 */
@RestController
@RequestMapping("/rest/v1.0/task/zrzy")
@Api(tags = "自然资源任务列表服务接口")
public class ZrzyTaskController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZrzyTaskController.class);
    private final static String MULTIPLE_PARAM_SPLITTER = ",";

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private FormThirdResourceClient formThirdResourceClient;
    /**
     * 参数服务
     */
    @Autowired
    private ZrzyQueryParamsConfig queryParamsConfig;
    /**
     * 表单服务
     */
    @Autowired
    private FormStateClientMatcher formStateClient;
    @Autowired
    private WorkFlowUtils workFlowUtils;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private OrganizationManagerClientMatcher organizationManagerClient;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    private FlowableNodeClientMatcher flowableNodeClient;
    @Autowired
    private BdcDjxlPzFeignService bdcDjxlPzFeignService;
    @Autowired
    private BdcCshXtPzFeignService bdcCshXtPzFeignService;
    @Autowired
    private ProcessTaskCustomExtendClient processTaskCustomExtendClient;
    @Autowired
    private StorageClientMatcher storageClient;

    private String processDefNamePrefix = "自然资源";

    @Value("#{'${zrzy.gzldyid:}'.split(',')}")
    private List<String> processDefKey;

    /**
     * @param pageable           分页对象
     * @param httpServletRequest
     * @return 待办任务信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 查询待办任务信息列表
     */
    @PostMapping("/todo")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询指定用户待办任务列表", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Object queryTodoTask(@LayuiPageable Pageable pageable, HttpServletRequest httpServletRequest) {
        List<RequestCondition> requestConditions = querySearchParams(httpServletRequest, "dbContent");
        // 处理待办列表排序
        queryOrderByConfig(requestConditions, "order_todo");

        Page<Map<String, Object>> todoTaskPage = processTaskCustomExtendClient.todoTaskExtendList(requestConditions, pageable.getPageSize(), pageable.getPageNumber());
        return addLayUiCode(todoTaskPage);
    }

    /**
     * @param pageable           分页对象
     * @param httpServletRequest
     * @return 已办任务信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 查询已办任务信息列表
     */
    @PostMapping("/complete")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询指定用户已办任务列表", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Object queryCompleteTask(@LayuiPageable Pageable pageable, HttpServletRequest httpServletRequest) {
        List<RequestCondition> requestConditions = querySearchParams(httpServletRequest, "ybContent");
        defaultQueryOrder(requestConditions, "order");
        Page<Map<String, Object>> completeTaskPage = processTaskCustomExtendClient.completeTaskExtendList(requestConditions, pageable.getPageSize(), pageable.getPageNumber());
        return addLayUiCode(completeTaskPage);
    }

    /**
     * @param pageable           分页对象
     * @param httpServletRequest
     * @return 任务信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 查询任务信息列表
     */
    @PostMapping("/all")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询指定用户项目列表", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Object queryAllTask(@LayuiPageable Pageable pageable, HttpServletRequest httpServletRequest) {
        Page<Map<String, Object>> allProcessInsPage = null;
        List<RequestCondition> list = querySearchParams(httpServletRequest, "xmContent");
        //默认查询方式
        Integer queryType = 0;

        allProcessInsPage = processTaskCustomExtendClient.queryProcessInsWithProject(list, queryType, pageable.getPageSize(), pageable.getPageNumber());

        return addLayUiCode(allProcessInsPage);
    }


    /**
     * 根据任务ID获取任务信息
     *
     * @param taskId
     * @return 任务信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @GetMapping("/info/{taskId}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据任务ID去查询任务信息", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "taskId", value = "任务ID", required = true, dataType = "String", paramType = "path")})
    public TaskData getTaskById(@PathVariable("taskId") String taskId) {
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("根据任务ID去查询任务信息，任务 ID 不能为空！");
        }
        TaskData taskData = processTaskClient.getTaskById(taskId);
        return taskData;
    }


    /**
     * 获取任务的资源列表
     *
     * @param formViewKey
     * @return 获取任务的资源列表
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @GetMapping(value = "/form-center/{formViewKey}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取任务的资源列表", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "formViewKey", value = "formKey", required = true, dataType = "String", paramType = "path")})
    public List<FormStateDTO> listResource(@PathVariable(name = "formViewKey") String formViewKey) {
        if (StringUtils.isBlank(formViewKey)) {
            throw new MissingArgumentException("获取任务的资源列表 formViewKey 不能为空！");
        }
        String[] forms = formViewKey.split(",");
        // 获取任务的资源列表
        List<FormStateDTO> list = new ArrayList<>();
        if (forms.length == 1) {
            list = formStateClient.listByFormViewKey(formViewKey,
                    CommonConstantUtils.FORMKEY_FILTER_OPERATE_NOTLIKE, CommonConstantUtils.FORMKEY_FILTER_URL_PORTAL_UI);
        } else {
            Set<String> formKeySet = new HashSet<>(Arrays.asList(forms));
            Set<String> formModelSet = new HashSet<>();
            for (String key : formKeySet) {
                List<FormStateDTO> formStateDTOList = formStateClient.listByFormViewKey(key,
                        CommonConstantUtils.FORMKEY_FILTER_OPERATE_NOTLIKE, CommonConstantUtils.FORMKEY_FILTER_URL_PORTAL_UI);
                list.addAll(formStateDTOList.stream().filter(formStateDTO -> formModelSet.add(formStateDTO.getFormModelId())).collect(Collectors.toList()));
            }
        }
        //排序
        list.sort((o1, o2) -> {
            if (NumberUtils.isNumber(o1.getRelOrder()) && NumberUtils.isNumber(o2.getRelOrder())) {
                return Integer.parseInt(o1.getRelOrder()) - Integer.parseInt(o2.getRelOrder());
            }
            return -1;
        });
        return list;
    }


    /**
     * @param httpServletRequest
     * @param pageable
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @PostMapping("/claim/list")
    @ApiOperation(value = "获取认领任务列表", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    public Object listClaimTask(@LayuiPageable Pageable pageable, HttpServletRequest httpServletRequest) {
        String userName = userManagerUtils.getCurrentUserName();
        List<RequestCondition> requestConditions = querySearchParams(httpServletRequest, "rlContent");
        defaultQueryOrder(requestConditions, "rl_order");
        Page<Map<String, Object>> taskExtendDtoPage = processTaskCustomExtendClient.claimTaskExtendList(requestConditions, userName, pageable.getPageSize(), pageable.getPageNumber());
        return addLayUiCode(taskExtendDtoPage);
    }

    /**
     * 查找组织机构
     *
     * @return List<OrganizationDto>
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/dept/all")
    @ApiOperation(value = "查找组织机构")
    @ResponseStatus(HttpStatus.OK)
    public List<OrganizationDto> listRootOrgs() {
        return organizationManagerClient.listOrgs(1);
    }

    /**
     * 查找组织机构
     *
     * @return List<OrganizationDto>
     * @author <a href ="mailto:wutao2@gtmap.cn">wutao2</a>
     */
    @GetMapping("/dept/allnew")
    @ApiOperation(value = "查找组织机构")
    @ResponseStatus(HttpStatus.OK)
    public Object listRootOrgsNew() {
        return userManagerUtils.listOrganizations();
    }

    @GetMapping("/dept/allusers")
    @ApiOperation(value = "查找组织机构下所有用户")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> listUsersByDept(@RequestParam("deptId") String deptId) {
        if (StringUtils.isNotBlank(deptId)) {
            return organizationManagerClient.listUsersByOrg(deptId);
        }
        return null;
    }

    /**
     * 从配置文件中读取默认查询顺序
     *
     * @param requestConditions 查询条件
     * @param type              默认顺序类型
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    private void defaultQueryOrder(List<RequestCondition> requestConditions, String type) {
        if (MapUtils.isNotEmpty(queryParamsConfig.getParamMap()) && queryParamsConfig.getParamMap().get(type) != null) {
            RequestCondition requestCondition = new RequestCondition();
            Map<String, String> order = queryParamsConfig.getParamMap().get(type);
            if (order != null && !StringUtils.isAnyBlank(order.get("requestKey"), order.get("queryJudge"), order.get("requestValue"))) {
                //获取配置的比对逻辑
                requestCondition.setRequestKey(order.get("requestKey"));
                requestCondition.setRequestJudge(order.get("queryJudge"));
                requestCondition.setRequestValue(order.get("requestValue"));
                requestConditions.add(requestCondition);
                LOGGER.info("查询顺序数据：{}", JSON.toJSONString(requestCondition));
            }
        }
    }


    /**
     * @param processDefKey 工作流定义key
     * @return 节点信息
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取节点信息
     */
    @GetMapping("/jdmcs")
    @ApiOperation(value = "获取节点信息")
    @ResponseStatus(HttpStatus.OK)
    public Object queryUserTasks(String processDefKey) {
        if (StringUtils.isBlank(processDefKey)) {
            throw new MissingArgumentException("processDefKey,缺少流程定义Key");
        }
        return workFlowUtils.getUserTasks(processDefKey);
    }


    /**
     * @param processDefKey 流程定义key
     * @return List<String> 流程节点信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取流程节点信息processDefKey 有值，则查询流程节点信息。没值，则获取默认配置信息
     */
    @GetMapping("/taskName")
    @ApiOperation(value = "查询流程节点信息")
    @ResponseStatus(HttpStatus.OK)
    public List<String> queryTaskName(@RequestParam(name = "processDefKey", required = false) String processDefKey) {
        // 没有流程信息时，获取默认配置信息
        if (StringUtils.isEmpty(processDefKey)) {
            if (MapUtils.isNotEmpty(queryParamsConfig.getParamMap())) {
                String taskNameConfig = queryParamsConfig.getParamMap().get("taskName").get("defaultValue");
                if (StringUtils.isNotBlank(taskNameConfig)) {
                    return Arrays.asList(StringUtils.split(taskNameConfig, CommonConstantUtils.ZF_YW_DH));
                }
            }
        }
        // 有流程信息时，则查询当前流程的节点信息
        List<String> taskNameList = new ArrayList();
        List<UserTaskDto> userTaskDtos = flowableNodeClient.getAllUserTaskByProcDefKey(processDefKey);
        if (CollectionUtils.isNotEmpty(userTaskDtos)) {
            for (UserTaskDto taskDto : userTaskDtos) {
                taskNameList.add(taskDto.getName());
            }
        }
        return taskNameList;
    }

    /**
     * @param processDefKey 流程定义key
     * @return List<String> 流程节点信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取流程登记原因信息processDefKey 有值，则查询流程节点信息。没值，则获取默认配置信息
     */
    @GetMapping("/djyy")
    @ApiOperation(value = "查询流程登记原因")
    @ResponseStatus(HttpStatus.OK)
    public List<String> queryDjyy(@RequestParam(name = "processDefKey", required = false) String processDefKey) {
        List<String> djxlList = new ArrayList();
        // 没有流程信息时，获取所有的登记原因
        if (StringUtils.isEmpty(processDefKey)) {
            return bdcCshXtPzFeignService.listDjyys(djxlList);
        }
        // 有流程信息时，则查询当前流程相关的登记原因
        List<BdcDjxlPzDO> bdcDjxlPzDOList = bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyid(processDefKey);
        if (CollectionUtils.isNotEmpty(bdcDjxlPzDOList)) {
            Set<String> djxlSet = new HashSet();
            for (BdcDjxlPzDO bdcDjxlPzDO : bdcDjxlPzDOList) {
                djxlSet.add(bdcDjxlPzDO.getDjxl());
            }
            djxlList = new ArrayList(djxlSet);
        }
        return bdcCshXtPzFeignService.listDjyys(djxlList);
    }

    /**
     * 获取流程挂起意见信息
     *
     * @param processInsId processInsId
     * @param taskId       taskId
     * @return {List} 意见列表
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/opinions")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取流程挂起意见信息", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "processInsId", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "taskId", dataType = "string", paramType = "query")})
    public List<OpinionDto> queryProcessOpinions(@RequestParam(value = "processInsId") String processInsId,
                                                 @RequestParam(value = "taskId", required = false) String taskId) {
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("taskId");
        }
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("processInsId");
        }
        return processTaskClient.queryProcessOpinions(processInsId, taskId, CommentType.SUSPEND_OPINION.value());
    }

    /**
     * @return java.util.Map
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params []
     * @description 查询审批来源字典项
     */
    @GetMapping("/zd")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询portal 页面所需字典项")
    public Map getZdxx() {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        Map<String, List<Map>> map = new HashMap<>();
        map.put("sply", zdMap.get("sply"));
        map.put("qlrlx", zdMap.get("qlrlx"));
        return map;
    }

    /**
     * @return java.lang.Object
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [taskName]
     * @description 查询任务节点是否存在限制 true：表示存在限制，false：不存在限制
     */
    @GetMapping("/nodePermission")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询任务节点限制")
    public Object nodePermissionNew(HttpServletRequest httpServletRequest) {
        String taskName = httpServletRequest.getParameter("taskName");
        if (StringUtils.isBlank(taskName)) {
            throw new MissingArgumentException("taskName");
        }
        // 1、判断当前任务的节点，是否需要先受理的件先审核
//        if (CollectionUtils.isNotEmpty(nodePermissionList) && nodePermissionList.contains(taskName)) {
//            // 2、根据流程名称、节点、开始时间，判断当前任务之前是否有相同类型的任务
//            List<RequestCondition> requestConditions = querySearchParams(httpServletRequest, "dbContent");
//            Page<Map<String, Object>> todoTaskPage = processTaskCustomExtendClient.todoTaskExtendList(requestConditions, 10, 0);
//            //目前大云时间判断精确了毫秒,小于等于不包含本身
//            if (todoTaskPage.getContent().size() > 0) {
//                return true;
//            }
//        }
        return false;
    }

    /**
     * 查询项目信息
     *
     * @param gzlslid gzlslid
     * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @GetMapping("/xm")
    @ApiOperation(value = "查询项目信息")
    @ResponseStatus(HttpStatus.OK)
    public List<BdcXmDO> matchSdPpData(@Valid @NotBlank(message = "gzlslid 不能为空") @RequestParam(value = "gzlslid") String gzlslid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        return bdcXmFeignService.listBdcXm(bdcXmQO);
    }


    /**
     * @param slbh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据受理编号获取绑定转发的数据
     * @date : 2021/10/9 14:05
     */
    @GetMapping("/forward/bind")
    @ApiOperation(value = "根据受理编号获取绑定转发的数据")
    public Object listBindForwardData(HttpServletRequest httpServletRequest, String slbh) {
        List<RequestCondition> requestConditions = querySearchParams(httpServletRequest, "dbContent");
        // 处理待办列表排序
        queryOrderByConfig(requestConditions, "order_todo");

        Page<Map<String, Object>> todoTaskPage = processTaskCustomExtendClient.todoTaskExtendList(requestConditions, 10, 0);
        return todoTaskPage.getContent();
    }


    /**
     * 根据前台传回的查询参数去组织条件
     *
     * @param httpServletRequest
     * @param lx                 列表的类型（认领列表：rlContent; 待办：dbContent; 已办：ybContent; 项目：xmContent; 个人：grContent;）
     * @return 分页查询需要的条件
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    public List<RequestCondition> querySearchParams(HttpServletRequest httpServletRequest, String lx) {
        List<RequestCondition> list = new ArrayList<>();
        if (MapUtils.isNotEmpty(queryParamsConfig.getParamMap()) && httpServletRequest != null) {
            // 前端传递的查询条件
            boolean pagerOrder = generatePageOrder(httpServletRequest, list);
            for (Map.Entry<String, Map<String, String>> param : queryParamsConfig.getParamMap().entrySet()) {
                String value = httpServletRequest.getParameter(param.getKey());
                if (StringUtils.equals(param.getKey(), QueryJudge.ORDER.value()) && pagerOrder) {
                    continue;
                }
                //根据配置的key去查询参数  如果不为空做封装
                if (StringUtils.isNotBlank(value)) {
                    List<String> params = Arrays.asList(value.split(MULTIPLE_PARAM_SPLITTER));
                    Set<String> set = new HashSet<>(params);
                    RequestCondition requestCondition = new RequestCondition();
                    //获取配置的比对逻辑
                    requestCondition.setRequestKey(param.getValue().get("requestKey"));
                    requestCondition.setRequestJudge(param.getValue().get("queryJudge"));
                    if (set.size() > 1 && "slbh".equals(param.getValue().get("requestKey"))) {
                        requestCondition.setRequestJudge("in");
                    }
                    if (QueryJudge.IN.value().equals(requestCondition.getRequestJudge())
                            || QueryJudge.NOT_IN.value().equals(requestCondition.getRequestJudge())) {
                        requestCondition.setRequestValue(set);
                    } else if (QueryJudge.OR_EQUALS.value().equals(requestCondition.getRequestJudge())) {
                        String[] requestKeys = param.getValue().get("requestKey").split(MULTIPLE_PARAM_SPLITTER);
                        for (String requestKey : requestKeys) {
                            RequestCondition orCondition = new RequestCondition();
                            orCondition.setRequestKey(requestKey);
                            orCondition.setRequestJudge(QueryJudge.OR_EQUALS.value());
                            orCondition.setRequestValue(value);
                            list.add(orCondition);
                        }
                        continue;
                    } else {
                        requestCondition.setRequestValue(value);
                    }
                    list.add(requestCondition);
                }
            }
        }
        // 追加默认的查询参数
        if (StringUtils.isNotBlank(lx)) {
            String value = httpServletRequest.getParameter("lx");
            if (StringUtils.isNotBlank(value)) {
                lx = value;
            }
            this.addDefaultParam(lx, list);
        }
        return list;
    }

    /**
     *
     * @param lx
     * @param requestConditionList
     */
    private void addDefaultParam(String lx, List<RequestCondition> requestConditionList) {
        if (MapUtils.isNotEmpty(queryParamsConfig.getDefaultParamMap())
                && MapUtils.isNotEmpty(queryParamsConfig.getDefaultParamMap().get(lx))) {
            for (Map.Entry<String, Map<String, String>> param : queryParamsConfig.getDefaultParamMap().get(lx).entrySet()) {
                // 去除配置为空的
                if (StringUtils.isAnyBlank(param.getValue().get("requestKey"),
                        param.getValue().get("requestValue"), param.getValue().get("requestValue"))) {
                    continue;
                }
                String requestValue = param.getValue().get("requestValue");
                List<String> params = Arrays.asList(requestValue.split(MULTIPLE_PARAM_SPLITTER));
                Set<String> set = new HashSet<>(params);
                RequestCondition requestCondition = new RequestCondition();
                requestCondition.setRequestKey(param.getValue().get("requestKey"));
                requestCondition.setRequestJudge(param.getValue().get("queryJudge"));
                if (QueryJudge.IN.value().equals(requestCondition.getRequestJudge())
                        || QueryJudge.NOT_IN.value().equals(requestCondition.getRequestJudge())) {
                    requestCondition.setRequestValue(set);
                } else if (QueryJudge.OR_EQUALS.value().equals(requestCondition.getRequestJudge())) {
                    String[] requestKeys = param.getValue().get("requestKey").split(MULTIPLE_PARAM_SPLITTER);
                    for (String requestKey : requestKeys) {
                        RequestCondition orCondition = new RequestCondition();
                        orCondition.setRequestKey(requestKey);
                        orCondition.setRequestJudge(QueryJudge.OR_EQUALS.value());
                        orCondition.setRequestValue(requestValue);
                        requestConditionList.add(orCondition);
                    }
                    continue;
                } else {
                    requestCondition.setRequestValue(requestValue);
                }
                requestConditionList.add(requestCondition);
            }
        }

        //加上只查询自然资源的数据的条件
        boolean defNameMustCondition = requestConditionList
                .stream()
                .noneMatch(requestCondition -> "processDefName".equals(requestCondition.getRequestKey()));

        if(defNameMustCondition && CollectionUtils.isNotEmpty(processDefKey)){
            RequestCondition zrzyCondition = new RequestCondition();
            zrzyCondition.setRequestJudge("in");
            zrzyCondition.setRequestKey("processKey");
            zrzyCondition.setRequestValue(processDefKey);
            requestConditionList.add(zrzyCondition);
        }
    }

    /**
     * 组织前端传递的排序参数<br>
     * 前端未传递则返回false，传递了则组织查询条件
     *
     * @return {boolean} 前端页面是否按照顺序查询
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    private boolean generatePageOrder(HttpServletRequest httpServletRequest, List<RequestCondition> list) {
        String field = httpServletRequest.getParameter("field");
        String order = httpServletRequest.getParameter(QueryJudge.ORDER.value());
        // 如果前端有传递 order 以前端查询顺序为准
        if (StringUtils.isNotBlank(field) && StringUtils.isNotBlank(order)) {
            RequestCondition requestCondition = new RequestCondition();
            requestCondition.setRequestKey(field);
            requestCondition.setRequestJudge(QueryJudge.ORDER.value());
            requestCondition.setRequestValue(order);
            list.add(requestCondition);
            return true;
        }
        return false;
    }

    /**
     * 向请求参数中添加排序信息
     *
     * @param requestConditions 请求参数
     * @param prefix            排序配置前缀
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private void queryOrderByConfig(List<RequestCondition> requestConditions, String prefix) {
        if (MapUtils.isEmpty(queryParamsConfig.getParamMap())) {
            return;
        }

        // 遍历配置，筛出以 prefix 开头的键值对加入到查询条件
        List<OrderCondition> orderConditions = Lists.newArrayList();
        for (Map.Entry<String, Map<String, String>> param : queryParamsConfig.getParamMap().entrySet()) {
            if (StringUtils.startsWith(param.getKey(), prefix)) {
                Map<String, String> order = param.getValue();
                OrderCondition orderCondition = new OrderCondition(order.get("requestKey"), order.get("requestValue"), Integer.parseInt(order.get("sort")));
                orderConditions.add(orderCondition);
            }
        }

        // 根据排序优先级 （sort） 添加到请求参数中， sort 越小的查询排序就优先
        if (CollectionUtils.isNotEmpty(orderConditions)) {
            List<RequestCondition> orderRequestConditions = orderConditions.stream().sorted(Comparator.comparing(OrderCondition::getSort)).map(
                    orederConditon -> {
                        RequestCondition requestCondition = new RequestCondition();
                        requestCondition.setRequestKey(orederConditon.getRequestKey());
                        requestCondition.setRequestJudge(QueryJudge.ORDER.value());
                        requestCondition.setRequestValue(orederConditon.getRequestValue());
                        return requestCondition;
                    }
            ).collect(Collectors.toList());

            LOGGER.info("{}: 组织查询顺序数据：{}", prefix, JSON.toJSONString(orderRequestConditions));
            requestConditions.addAll(orderRequestConditions);
        }
    }

    /**
     * 根据节点获取 portal-ui 按钮权限
     * @param taskId 工作流实例ID
     * @return 按钮控制的资源权限
     */
    @GetMapping(value = "/form-center/portalBtn/authority")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "taskId", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "resourceName", value = "taskId", required = true, dataType = "String", paramType = "query"),
    })
    public Object queryBtnAuthority(@RequestParam(name = "taskId") String taskId, @RequestParam(name = "resourceName") String resourceName) {
        if (StringUtils.isAnyBlank(taskId, resourceName)) {
            throw new MissingArgumentException("未获取到任务信息ID 或 资源名称");
        }
        TaskData taskData = processTaskClient.getTaskById(taskId);
        if(Objects.isNull(taskData) || StringUtils.isBlank(taskData.getFormKey())){
            throw new AppException(ErrorCode.CUSTOM, "未查询到任务信息");
        }
        String formKey = taskData.getFormKey();
        List<FormStateDTO> formStateDTOList = formStateClient.listByFormViewKey(formKey, CommonConstantUtils.FORMKEY_FILTER_OPERATE_LIKE, CommonConstantUtils.FORMKEY_FILTER_URL_PORTAL_UI);
        List<FormElementConfigDTO> formElementConfigDTOList = new ArrayList<>(10);
        if(CollectionUtils.isNotEmpty(formStateDTOList)){
            formElementConfigDTOList = formThirdResourceClient.getElementWithChildConfig(formStateDTOList.get(0).getFormStateId(), resourceName);
        }
        return formElementConfigDTOList;
    }
}