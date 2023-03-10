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
 * @description ????????????????????????????????????????????????????????????????????????????????????????????????
 */
@RestController
@RequestMapping("/rest/v1.0/task/zrzy")
@Api(tags = "????????????????????????????????????")
public class ZrzyTaskController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZrzyTaskController.class);
    private final static String MULTIPLE_PARAM_SPLITTER = ",";

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private FormThirdResourceClient formThirdResourceClient;
    /**
     * ????????????
     */
    @Autowired
    private ZrzyQueryParamsConfig queryParamsConfig;
    /**
     * ????????????
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

    private String processDefNamePrefix = "????????????";

    @Value("#{'${zrzy.gzldyid:}'.split(',')}")
    private List<String> processDefKey;

    /**
     * @param pageable           ????????????
     * @param httpServletRequest
     * @return ??????????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ??????????????????????????????
     */
    @PostMapping("/todo")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "??????????????????????????????????????????", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "??????", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "??????", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "??????", dataType = "string", paramType = "query")})
    public Object queryTodoTask(@LayuiPageable Pageable pageable, HttpServletRequest httpServletRequest) {
        List<RequestCondition> requestConditions = querySearchParams(httpServletRequest, "dbContent");
        // ????????????????????????
        queryOrderByConfig(requestConditions, "order_todo");

        Page<Map<String, Object>> todoTaskPage = processTaskCustomExtendClient.todoTaskExtendList(requestConditions, pageable.getPageSize(), pageable.getPageNumber());
        return addLayUiCode(todoTaskPage);
    }

    /**
     * @param pageable           ????????????
     * @param httpServletRequest
     * @return ??????????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ??????????????????????????????
     */
    @PostMapping("/complete")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "??????????????????????????????????????????", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "??????", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "??????", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "??????", dataType = "string", paramType = "query")})
    public Object queryCompleteTask(@LayuiPageable Pageable pageable, HttpServletRequest httpServletRequest) {
        List<RequestCondition> requestConditions = querySearchParams(httpServletRequest, "ybContent");
        defaultQueryOrder(requestConditions, "order");
        Page<Map<String, Object>> completeTaskPage = processTaskCustomExtendClient.completeTaskExtendList(requestConditions, pageable.getPageSize(), pageable.getPageNumber());
        return addLayUiCode(completeTaskPage);
    }

    /**
     * @param pageable           ????????????
     * @param httpServletRequest
     * @return ????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ????????????????????????
     */
    @PostMapping("/all")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "????????????????????????????????????", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "??????", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "??????", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "??????", dataType = "string", paramType = "query")})
    public Object queryAllTask(@LayuiPageable Pageable pageable, HttpServletRequest httpServletRequest) {
        Page<Map<String, Object>> allProcessInsPage = null;
        List<RequestCondition> list = querySearchParams(httpServletRequest, "xmContent");
        //??????????????????
        Integer queryType = 0;

        allProcessInsPage = processTaskCustomExtendClient.queryProcessInsWithProject(list, queryType, pageable.getPageSize(), pageable.getPageNumber());

        return addLayUiCode(allProcessInsPage);
    }


    /**
     * ????????????ID??????????????????
     *
     * @param taskId
     * @return ????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @GetMapping("/info/{taskId}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "????????????ID?????????????????????", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "taskId", value = "??????ID", required = true, dataType = "String", paramType = "path")})
    public TaskData getTaskById(@PathVariable("taskId") String taskId) {
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("????????????ID?????????????????????????????? ID ???????????????");
        }
        TaskData taskData = processTaskClient.getTaskById(taskId);
        return taskData;
    }


    /**
     * ???????????????????????????
     *
     * @param formViewKey
     * @return ???????????????????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @GetMapping(value = "/form-center/{formViewKey}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "???????????????????????????", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "formViewKey", value = "formKey", required = true, dataType = "String", paramType = "path")})
    public List<FormStateDTO> listResource(@PathVariable(name = "formViewKey") String formViewKey) {
        if (StringUtils.isBlank(formViewKey)) {
            throw new MissingArgumentException("??????????????????????????? formViewKey ???????????????");
        }
        String[] forms = formViewKey.split(",");
        // ???????????????????????????
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
        //??????
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
    @ApiOperation(value = "????????????????????????", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiResponses(value = {@ApiResponse(code = 200, message = "??????????????????"), @ApiResponse(code = 500, message = "??????????????????")})
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "??????", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "??????", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "??????", dataType = "string", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    public Object listClaimTask(@LayuiPageable Pageable pageable, HttpServletRequest httpServletRequest) {
        String userName = userManagerUtils.getCurrentUserName();
        List<RequestCondition> requestConditions = querySearchParams(httpServletRequest, "rlContent");
        defaultQueryOrder(requestConditions, "rl_order");
        Page<Map<String, Object>> taskExtendDtoPage = processTaskCustomExtendClient.claimTaskExtendList(requestConditions, userName, pageable.getPageSize(), pageable.getPageNumber());
        return addLayUiCode(taskExtendDtoPage);
    }

    /**
     * ??????????????????
     *
     * @return List<OrganizationDto>
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/dept/all")
    @ApiOperation(value = "??????????????????")
    @ResponseStatus(HttpStatus.OK)
    public List<OrganizationDto> listRootOrgs() {
        return organizationManagerClient.listOrgs(1);
    }

    /**
     * ??????????????????
     *
     * @return List<OrganizationDto>
     * @author <a href ="mailto:wutao2@gtmap.cn">wutao2</a>
     */
    @GetMapping("/dept/allnew")
    @ApiOperation(value = "??????????????????")
    @ResponseStatus(HttpStatus.OK)
    public Object listRootOrgsNew() {
        return userManagerUtils.listOrganizations();
    }

    @GetMapping("/dept/allusers")
    @ApiOperation(value = "?????????????????????????????????")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> listUsersByDept(@RequestParam("deptId") String deptId) {
        if (StringUtils.isNotBlank(deptId)) {
            return organizationManagerClient.listUsersByOrg(deptId);
        }
        return null;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param requestConditions ????????????
     * @param type              ??????????????????
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    private void defaultQueryOrder(List<RequestCondition> requestConditions, String type) {
        if (MapUtils.isNotEmpty(queryParamsConfig.getParamMap()) && queryParamsConfig.getParamMap().get(type) != null) {
            RequestCondition requestCondition = new RequestCondition();
            Map<String, String> order = queryParamsConfig.getParamMap().get(type);
            if (order != null && !StringUtils.isAnyBlank(order.get("requestKey"), order.get("queryJudge"), order.get("requestValue"))) {
                //???????????????????????????
                requestCondition.setRequestKey(order.get("requestKey"));
                requestCondition.setRequestJudge(order.get("queryJudge"));
                requestCondition.setRequestValue(order.get("requestValue"));
                requestConditions.add(requestCondition);
                LOGGER.info("?????????????????????{}", JSON.toJSONString(requestCondition));
            }
        }
    }


    /**
     * @param processDefKey ???????????????key
     * @return ????????????
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description ??????????????????
     */
    @GetMapping("/jdmcs")
    @ApiOperation(value = "??????????????????")
    @ResponseStatus(HttpStatus.OK)
    public Object queryUserTasks(String processDefKey) {
        if (StringUtils.isBlank(processDefKey)) {
            throw new MissingArgumentException("processDefKey,??????????????????Key");
        }
        return workFlowUtils.getUserTasks(processDefKey);
    }


    /**
     * @param processDefKey ????????????key
     * @return List<String> ??????????????????
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ????????????????????????processDefKey ???????????????????????????????????????????????????????????????????????????
     */
    @GetMapping("/taskName")
    @ApiOperation(value = "????????????????????????")
    @ResponseStatus(HttpStatus.OK)
    public List<String> queryTaskName(@RequestParam(name = "processDefKey", required = false) String processDefKey) {
        // ????????????????????????????????????????????????
        if (StringUtils.isEmpty(processDefKey)) {
            if (MapUtils.isNotEmpty(queryParamsConfig.getParamMap())) {
                String taskNameConfig = queryParamsConfig.getParamMap().get("taskName").get("defaultValue");
                if (StringUtils.isNotBlank(taskNameConfig)) {
                    return Arrays.asList(StringUtils.split(taskNameConfig, CommonConstantUtils.ZF_YW_DH));
                }
            }
        }
        // ?????????????????????????????????????????????????????????
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
     * @param processDefKey ????????????key
     * @return List<String> ??????????????????
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ??????????????????????????????processDefKey ???????????????????????????????????????????????????????????????????????????
     */
    @GetMapping("/djyy")
    @ApiOperation(value = "????????????????????????")
    @ResponseStatus(HttpStatus.OK)
    public List<String> queryDjyy(@RequestParam(name = "processDefKey", required = false) String processDefKey) {
        List<String> djxlList = new ArrayList();
        // ???????????????????????????????????????????????????
        if (StringUtils.isEmpty(processDefKey)) {
            return bdcCshXtPzFeignService.listDjyys(djxlList);
        }
        // ???????????????????????????????????????????????????????????????
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
     * ??????????????????????????????
     *
     * @param processInsId processInsId
     * @param taskId       taskId
     * @return {List} ????????????
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/opinions")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "??????????????????????????????", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
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
     * @description ???????????????????????????
     */
    @GetMapping("/zd")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "??????portal ?????????????????????")
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
     * @description ???????????????????????????????????? true????????????????????????false??????????????????
     */
    @GetMapping("/nodePermission")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "????????????????????????")
    public Object nodePermissionNew(HttpServletRequest httpServletRequest) {
        String taskName = httpServletRequest.getParameter("taskName");
        if (StringUtils.isBlank(taskName)) {
            throw new MissingArgumentException("taskName");
        }
        // 1?????????????????????????????????????????????????????????????????????
//        if (CollectionUtils.isNotEmpty(nodePermissionList) && nodePermissionList.contains(taskName)) {
//            // 2??????????????????????????????????????????????????????????????????????????????????????????????????????
//            List<RequestCondition> requestConditions = querySearchParams(httpServletRequest, "dbContent");
//            Page<Map<String, Object>> todoTaskPage = processTaskCustomExtendClient.todoTaskExtendList(requestConditions, 10, 0);
//            //???????????????????????????????????????,???????????????????????????
//            if (todoTaskPage.getContent().size() > 0) {
//                return true;
//            }
//        }
        return false;
    }

    /**
     * ??????????????????
     *
     * @param gzlslid gzlslid
     * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @GetMapping("/xm")
    @ApiOperation(value = "??????????????????")
    @ResponseStatus(HttpStatus.OK)
    public List<BdcXmDO> matchSdPpData(@Valid @NotBlank(message = "gzlslid ????????????") @RequestParam(value = "gzlslid") String gzlslid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        return bdcXmFeignService.listBdcXm(bdcXmQO);
    }


    /**
     * @param slbh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ?????????????????????????????????????????????
     * @date : 2021/10/9 14:05
     */
    @GetMapping("/forward/bind")
    @ApiOperation(value = "?????????????????????????????????????????????")
    public Object listBindForwardData(HttpServletRequest httpServletRequest, String slbh) {
        List<RequestCondition> requestConditions = querySearchParams(httpServletRequest, "dbContent");
        // ????????????????????????
        queryOrderByConfig(requestConditions, "order_todo");

        Page<Map<String, Object>> todoTaskPage = processTaskCustomExtendClient.todoTaskExtendList(requestConditions, 10, 0);
        return todoTaskPage.getContent();
    }


    /**
     * ????????????????????????????????????????????????
     *
     * @param httpServletRequest
     * @param lx                 ?????????????????????????????????rlContent; ?????????dbContent; ?????????ybContent; ?????????xmContent; ?????????grContent;???
     * @return ???????????????????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    public List<RequestCondition> querySearchParams(HttpServletRequest httpServletRequest, String lx) {
        List<RequestCondition> list = new ArrayList<>();
        if (MapUtils.isNotEmpty(queryParamsConfig.getParamMap()) && httpServletRequest != null) {
            // ???????????????????????????
            boolean pagerOrder = generatePageOrder(httpServletRequest, list);
            for (Map.Entry<String, Map<String, String>> param : queryParamsConfig.getParamMap().entrySet()) {
                String value = httpServletRequest.getParameter(param.getKey());
                if (StringUtils.equals(param.getKey(), QueryJudge.ORDER.value()) && pagerOrder) {
                    continue;
                }
                //???????????????key???????????????  ????????????????????????
                if (StringUtils.isNotBlank(value)) {
                    List<String> params = Arrays.asList(value.split(MULTIPLE_PARAM_SPLITTER));
                    Set<String> set = new HashSet<>(params);
                    RequestCondition requestCondition = new RequestCondition();
                    //???????????????????????????
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
        // ???????????????????????????
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
                // ?????????????????????
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

        //?????????????????????????????????????????????
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
     * ?????????????????????????????????<br>
     * ????????????????????????false?????????????????????????????????
     *
     * @return {boolean} ????????????????????????????????????
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    private boolean generatePageOrder(HttpServletRequest httpServletRequest, List<RequestCondition> list) {
        String field = httpServletRequest.getParameter("field");
        String order = httpServletRequest.getParameter(QueryJudge.ORDER.value());
        // ????????????????????? order ???????????????????????????
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
     * ????????????????????????????????????
     *
     * @param requestConditions ????????????
     * @param prefix            ??????????????????
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private void queryOrderByConfig(List<RequestCondition> requestConditions, String prefix) {
        if (MapUtils.isEmpty(queryParamsConfig.getParamMap())) {
            return;
        }

        // ???????????????????????? prefix ???????????????????????????????????????
        List<OrderCondition> orderConditions = Lists.newArrayList();
        for (Map.Entry<String, Map<String, String>> param : queryParamsConfig.getParamMap().entrySet()) {
            if (StringUtils.startsWith(param.getKey(), prefix)) {
                Map<String, String> order = param.getValue();
                OrderCondition orderCondition = new OrderCondition(order.get("requestKey"), order.get("requestValue"), Integer.parseInt(order.get("sort")));
                orderConditions.add(orderCondition);
            }
        }

        // ????????????????????? ???sort??? ??????????????????????????? sort ??????????????????????????????
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

            LOGGER.info("{}: ???????????????????????????{}", prefix, JSON.toJSONString(orderRequestConditions));
            requestConditions.addAll(orderRequestConditions);
        }
    }

    /**
     * ?????????????????? portal-ui ????????????
     * @param taskId ???????????????ID
     * @return ???????????????????????????
     */
    @GetMapping(value = "/form-center/portalBtn/authority")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "taskId", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "resourceName", value = "taskId", required = true, dataType = "String", paramType = "query"),
    })
    public Object queryBtnAuthority(@RequestParam(name = "taskId") String taskId, @RequestParam(name = "resourceName") String resourceName) {
        if (StringUtils.isAnyBlank(taskId, resourceName)) {
            throw new MissingArgumentException("????????????????????????ID ??? ????????????");
        }
        TaskData taskData = processTaskClient.getTaskById(taskId);
        if(Objects.isNull(taskData) || StringUtils.isBlank(taskData.getFormKey())){
            throw new AppException(ErrorCode.CUSTOM, "????????????????????????");
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