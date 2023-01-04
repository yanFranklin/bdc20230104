package cn.gtmap.realestate.portal.ui.web.rest;

import cn.gtmap.gtc.clients.RegionManagerClient;
import cn.gtmap.gtc.clients.RoleManagerClient;
import cn.gtmap.gtc.formclient.common.client.FormThirdResourceClient;
import cn.gtmap.gtc.formclient.common.dto.FormElementConfigDTO;
import cn.gtmap.gtc.formclient.common.dto.FormStateDTO;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.RegionDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessInsCustomExtendClient;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskCustomExtendClient;
import cn.gtmap.gtc.workflow.domain.common.RequestCondition;
import cn.gtmap.gtc.workflow.domain.manage.OpinionDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.gtc.workflow.domain.manage.UserTaskDto;
import cn.gtmap.gtc.workflow.enums.manage.QueryJudge;
import cn.gtmap.gtc.workflow.enums.task.CommentType;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcYjdTaskGxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcLzQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlBdcdyhQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcDjxlPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.AcceptBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcYjdFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.matcher.*;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.common.util.office.ExcelUtil;
import cn.gtmap.realestate.portal.ui.config.QueryParamsConfig;
import cn.gtmap.realestate.portal.ui.core.dto.BdcSplyDTO;
import cn.gtmap.realestate.portal.ui.core.dto.OrderCondition;
import cn.gtmap.realestate.portal.ui.core.dto.ProcessLastTaskDTO;
import cn.gtmap.realestate.portal.ui.core.thread.PpLzThread;
import cn.gtmap.realestate.portal.ui.service.BdcPpLzService;
import cn.gtmap.realestate.portal.ui.service.BdcYbYjdService;
import cn.gtmap.realestate.portal.ui.service.ProcessService;
import cn.gtmap.realestate.portal.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0, 2019/02/25
 * @description 任务信息请求处理控制器，包括：待办任务、已办任务、项目列表
 */
@RestController
@RequestMapping("/rest/v1.0/task")
@Api(tags = "任务列表服务接口")
public class BdcTaskController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcTaskController.class);
    private final static String MULTIPLE_PARAM_SPLITTER = ",";

    private final static String SHOW_FOLDER = "showfolder";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询总数参数
     */
    private final static String LOAD_TOTAL = "loadTotal";

    @Autowired
    private ProcessTaskClient processTaskClient;
    @Autowired
    private ProcessTaskRestClientMatcher processTaskRestClient;
    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    private ProcessInstanceClientMatcher processInstanceClient;
    /**
     * 参数服务
     */
    @Autowired
    private QueryParamsConfig queryParamsConfig;
    /**
     * 表单服务
     */
    @Autowired
    private FormStateClientMatcher formStateClient;
    /**
     * 流程任务服务
     */
    @Autowired
    private ProcessService processService;

    @Autowired
    private ProcessDefinitionClientMatcher processDefinitionClient;
    @Autowired
    private FormThirdResourceClient formThirdResourceClient;
    @Autowired
    private WorkFlowUtils workFlowUtils;
    @Autowired
    private BdcdyFeignService bdcdyFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcPpLzService bdcPpLzService;
    @Autowired
    private BdcYwsjHxFeignService bdcYwsjHxFeignService;
    @Autowired
    private ThreadEngine threadEngine;
    @Autowired
    private RoleManagerClient roleManagerClient;
    @Autowired
    private OrganizationManagerClientMatcher organizationManagerClient;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    private AcceptBdcdyFeignService acceptBdcdyFeignService;
    @Autowired
    private BdcPpFeignService bdcPpFeignService;
    @Autowired
    private FlowableNodeClientMatcher flowableNodeClient;
    @Autowired
    private BdcDjxlPzFeignService bdcDjxlPzFeignService;
    @Autowired
    private BdcCshXtPzFeignService bdcCshXtPzFeignService;
    @Autowired
    private ProcessTaskCustomExtendClient processTaskCustomExtendClient;
    @Autowired
    private BdcYjdFeignService bdcYjdFeignService;
    @Autowired
    private ProcessInsCustomExtendClient processInsCustomExtendClient;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    private RegionManagerClient regionManagerClient;
    @Autowired
    ProcessModelClientMatcher processModelClient;
    @Autowired
    RedisUtils redisUtils;
    @Value("${wwsq.glgz:}")
    private String wwsqglgz;

    @Value("${html.version:}")
    private String version;

    @Value("#{'${node.permission:}'.split(',')}")
    private List<String> nodePermissionList;

    /**
     * 盐城网上业务台账审批来源配置展示
     */
    @Value("${wsyw.sply:}")
    private String wsywSply;

    /*
     * 附件个数超过数量显示文件夹图标，不配置不显示
     */
    @Value("${fjnum.judge.showfolder:}")
    private String fjNum;

    /**
     * 附件目录排除的文件夹和附件，排除后还剩下文件夹或附件，显示文件夹标识
     */
    @Value("#{'${pcwjjmcs.judge.showfolder:}'.split(',')}")
    private List<String> pcwjjmcs;


    /*
     * 项目列表是否区分当前用户的部门 只查当前用户(包含在配置的某个qxdm下面)部门下的数据
     */
    @Value("${xmlb.qfbm.qxdm:}")
    private String xmlbQfBmQxdms;

    //是否过滤自然资源的流程，默认不过滤
    @Value("${zrzy.filter:false}")
    private boolean zrzyFilter;

    @Value("#{'${zrzy.gzldyid:}'.split(',')}")
    private List<String> zrzyProcessDefKey;

    /**
     * 是否启用批量件查询：true 是 false 否
     */
    @Value("${task.sfpljcx:false}")
    private boolean sfpljcx;

    /**
     * 查询条件是否自定义模糊类型
     */
    @Value("${task.cxtj.zdymhlx:false}")
    private boolean zdymhlx;

    /**
     * 限制非管理员只允许打开首条待办任务
     */
    @Value("${permission.todo:0}")
    private String todoPermission;


    /**
     * 任务下载最数据数量
     */
    @Value("${task.download.maxdatanum:10000}")
    private Integer maxdatanum;

    /**
     * 任务下载每个文件数据数量
     */
    @Value("${task.download.perfiledatanum:1000}")
    private Integer perfiledatanum;

    /**
     * 移交单服务
     */
    @Autowired
    private BdcYbYjdService bdcYbYjdService;

    /**
     * 业务办理各列表是否显示项目来源：true 是 false 否
     */
    @Value("${ywlb.xmly:false}")
    private boolean ywlbXmly;

    /**
     * 任务列表项目来源配置展示
     */
    @Value("${rwlb.xmly.xs:3_外网申请;5_外网申请;6_外网申请}")
    private String rwlbXmly;

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
        queryOrderByConfig(requestConditions, httpServletRequest, "order_todo");
        // 批量查询时候处理查询参数
        this.batchSearchParams(requestConditions, CommonConstantUtils.AJZT_ZB_DM);

        Page<Map<String, Object>> todoTaskPage = processTaskCustomExtendClient.todoTaskExtendList(requestConditions, pageable.getPageSize(), pageable.getPageNumber());
        if ((StringUtils.isNotBlank(fjNum) || CollectionUtils.isNotEmpty(pcwjjmcs))&& CollectionUtils.isNotEmpty(todoTaskPage.getContent())) {
            // 判断是否展示文件夹图标
            Page page = judgeShowFolder(todoTaskPage, pageable);
            return addLayUiCode(page);
        }
        if (CollectionUtils.isNotEmpty(todoTaskPage.getContent())) {
            //记录日志用于处理打开的任务不是点击的任务问题
            List<Map<String, Object>> resultMapList = listSomeInfo(todoTaskPage.getContent());
            LOGGER.warn("待办任务查询条件{}查询结果{},当前登录用户{}", JSON.toJSONString(requestConditions), resultMapList, userManagerUtils.getCurrentUserName());
            //转换审批来源名称
            convertSply(todoTaskPage.getContent());
        }
        return addLayUiCode(todoTaskPage);
    }


    /**
     * @return org.springframework.data.domain.Page
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [resultPage, pageable]
     * @description 判断是否展示文件夹图标
     */
    private Page judgeShowFolder(Page<Map<String, Object>> resultPage, Pageable pageable) {
        List<Map<String, Object>> todoTaskList = new ArrayList<>();
        for (Map<String, Object> todoTask : resultPage.getContent()) {
            if (todoTask.get("processInstanceId") != null) {
                Integer fileCount = 0;
                try {
                    // 查有文件的目录
                    List<StorageDto> storageDtos = storageClient.queryMenus("clientId", (String) todoTask.get("processInstanceId"), "", 1, 1, null, null, null);
                    // 如果配置了pcwjjmcs,过滤配置的文件夹和附件
                    this.filterStorageFiles(storageDtos);
                    //返回最外层为文件夹，需要计算内部文件个数
                    if (CollectionUtils.isNotEmpty(storageDtos)) {
                        for (StorageDto storageDto : storageDtos) {
                            if (CollectionUtils.isNotEmpty(storageDto.getChildren())) {
                                // 计算该流程所有附件的个数
                                fileCount += storageDto.getChildren().size();
                            }
                        }
                    }
                    if (StringUtils.isNotBlank(fjNum) && fileCount > Integer.parseInt(fjNum)) {
                        todoTask.put(SHOW_FOLDER, 1);
                    } else {
                        todoTask.put(SHOW_FOLDER, 0);
                    }
                } catch (Exception e) {
                    LOGGER.error("获取大云文件数量异常{}", e.getMessage());
                }
            }

            //查询字典信息
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
            //转换审批来源名称
            if (Objects.nonNull(MapUtils.getInteger(todoTask,"sply")) && Objects.nonNull(zdMap)) {
                String splymc = StringToolUtils.convertBeanPropertyValueOfZd(MapUtils.getInteger(todoTask,"sply"), zdMap.get("sply"));
                todoTask.put("splymc",splymc);
            }
            todoTaskList.add(todoTask);
        }
        return new PageImpl(todoTaskList, pageable, resultPage.getTotalElements());
    }


    /**
     * 递归方法，根据配置过滤文件夹或附件
     *
     * @param storageDtoList 附件信息
     */
    public void filterStorageFiles(List<StorageDto> storageDtoList) {
        if (CollectionUtils.isNotEmpty(pcwjjmcs) && CollectionUtils.isNotEmpty(storageDtoList)) {
            Iterator<StorageDto> it = storageDtoList.iterator();
            while (it.hasNext()) {
                StorageDto storageDto = it.next();
                if (pcwjjmcs.contains(storageDto.getName())) {
                    it.remove();
                } else {
                    if (CollectionUtils.isNotEmpty(storageDto.getChildren())) {
                        this.filterStorageFiles(storageDto.getChildren());
                    }
                }
            }
        }
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
        defaultQueryOrder(requestConditions, httpServletRequest, "order");

        // 批量查询时候处理查询参数
        this.batchSearchParams(requestConditions, null);

        Page<Map<String, Object>> completeTaskPage = processTaskCustomExtendClient.completeTaskExtendList(requestConditions, pageable.getPageSize(), pageable.getPageNumber());
        if (StringUtils.isNotBlank(fjNum) && CollectionUtils.isNotEmpty(completeTaskPage.getContent())) {
            // 判断是否展示文件夹图标
            Page page = judgeShowFolder(completeTaskPage, pageable);
            return addLayUiCode(page);
        }
        if (CollectionUtils.isNotEmpty(completeTaskPage.getContent())) {
            //记录日志用于处理打开的任务不是点击的任务问题
            List<Map<String, Object>> resultMapList = listSomeInfo(completeTaskPage.getContent());
            LOGGER.warn("已办任务查询条件{}查询结果{},当前登录用户{}", JSON.toJSONString(requestConditions), resultMapList, userManagerUtils.getCurrentUserName());
            //转换审批来源名称
            convertSply(completeTaskPage.getContent());
        }
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
        if (StringUtils.isNotBlank(xmlbQfBmQxdms)) {
            String userName = userManagerUtils.getCurrentUserName();
            if (StringUtils.isNotBlank(userName)) {
                String qxdm = userManagerUtils.getRegionCodeByUserName(userName);
                if (StringUtils.isNotBlank(qxdm) && xmlbQfBmQxdms.indexOf(qxdm) > -1) {
                    //默认按照用户的第一个部门id去查
                    queryType = 2;
                }
            } else {
                LOGGER.info("未获取到当前登录用户的部门id");
            }
        }

        // 批量查询时候处理查询参数
        this.batchSearchParams(list, null);

        allProcessInsPage = processTaskCustomExtendClient.queryProcessInsWithProject(list, queryType, pageable.getPageSize(), pageable.getPageNumber());
        if (StringUtils.isNotBlank(fjNum) && CollectionUtils.isNotEmpty(allProcessInsPage.getContent())) {
            // 判断是否展示文件夹图标
            Page page = judgeShowFolder(allProcessInsPage, pageable);
            return addLayUiCode(page);
        }
        if (CollectionUtils.isNotEmpty(allProcessInsPage.getContent())) {
            //记录日志用于处理打开的任务不是点击的任务问题
            List<Map<String, Object>> resultMapList = listSomeInfo(allProcessInsPage.getContent());
            LOGGER.warn("项目列表查询条件{}查询结果{},当前登录用户{}", JSON.toJSONString(list), resultMapList, userManagerUtils.getCurrentUserName());
            //转换审批来源名称
            convertSply(allProcessInsPage.getContent());
        }
        return addLayUiCode(allProcessInsPage);
    }

    /**
     * 查询指定个人项目列表
     *
     * @param pageable           分页对象
     * @param httpServletRequest
     * @return 任务信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/person")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询指定个人项目列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Object queryPersonTask(@LayuiPageable Pageable pageable, HttpServletRequest httpServletRequest) {
        List<RequestCondition> querySearchParams = querySearchParams(httpServletRequest, "grContent");

        // 批量查询时候处理查询参数
        this.batchSearchParams(querySearchParams, null);

        Page<Map<String, Object>> personalProcessInsPage = processTaskCustomExtendClient.queryPersonalProcessIns(
                querySearchParams, pageable.getPageSize(), pageable.getPageNumber());
        if (StringUtils.isNotBlank(fjNum) && CollectionUtils.isNotEmpty(personalProcessInsPage.getContent())) {
            // 判断是否展示文件夹图标
            Page page = judgeShowFolder(personalProcessInsPage, pageable);
            return addLayUiCode(page);
        }
        if (CollectionUtils.isNotEmpty(personalProcessInsPage.getContent())) {
            //记录日志用于处理打开的任务不是点击的任务问题
            List<Map<String, Object>> resultMapList = listSomeInfo(personalProcessInsPage.getContent());
            LOGGER.warn("个人项目列表查询条件{}查询结果{}", JSON.toJSONString(querySearchParams), resultMapList);
            //转换审批来源名称
            convertSply(personalProcessInsPage.getContent());

        }
        return addLayUiCode(personalProcessInsPage);
    }

    /**
     * @description 转换审批来源
     * @param dataList
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @date 2022/12/16 16:32
     */
    public void convertSply(List<Map<String, Object>> dataList) {
        //获取字典信息
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        //转换审批来源名称
        for (Map<String, Object> map : dataList) {
            if (Objects.nonNull(MapUtils.getInteger(map,"sply")) && Objects.nonNull(zdMap)) {
                String splymc = StringToolUtils.convertBeanPropertyValueOfZd(MapUtils.getInteger(map,"sply"), zdMap.get("sply"));
                map.put("splymc",splymc);
            }
        }

    }

    /**
     * 查询外网信息列表
     *
     * @param pageable           分页对象
     * @param httpServletRequest 请求对象
     * @return 互联网信息列表
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/ww")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询指定外网任务列表", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Object queryWwTask(@LayuiPageable Pageable pageable, HttpServletRequest httpServletRequest) {
        JSONObject pageDTO = new JSONObject();
        pageDTO.put("page", pageable.getPageNumber());
        pageDTO.put("size", pageable.getPageSize());
        pageDTO.put("sort", pageable.getSort());

        JSONObject data = new JSONObject();
        data.put("ywh", httpServletRequest.getParameter("ywh"));
        data.put("zl", httpServletRequest.getParameter("zl"));
        data.put("sqr", httpServletRequest.getParameter("sqr"));
        data.put("gzldyid", httpServletRequest.getParameter("gzldyid"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageDTO", pageDTO);
        jsonObject.put("data", data);
        LOGGER.warn("查询外网传参：{}", jsonObject);
        Object getWwsqYwxxFy = exchangeInterfaceFeignService.requestInterface("getWwsqYwxxFy", jsonObject);
        return addLayUiCodeForWw(getWwsqYwxxFy);
    }


    /**
     * 查询指定个人项目列表
     *
     * @param pageable           分页对象
     * @param httpServletRequest
     * @return 任务信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/lqr")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询指定个人项目列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Object queryLqrTask(@LayuiPageable Pageable pageable, HttpServletRequest httpServletRequest) {
        List<RequestCondition> querySearchParams = querySearchParams(httpServletRequest, "lqr");

        //添加必填项
        RequestCondition requestCondition = new RequestCondition();
        requestCondition.setRequestJudge("eq");
        requestCondition.setRequestKey("torsion_type");
        requestCondition.setRequestValue(3);
        querySearchParams.add(requestCondition);

        Page<Map<String, Object>> personalProcessInsPage = processTaskCustomExtendClient.allTaskExtendList(
                querySearchParams, pageable.getPageSize(), pageable.getPageNumber()
        );
        return addLayUiCode(personalProcessInsPage);
    }

    /**
     * 处理外网请求返回值
     *
     * @param obj
     * @return 增加 code 和 msg
     */
    public Object addLayUiCodeForWw(Object obj) {
        Map map = null;
        if (obj instanceof LinkedHashMap) {
            map = (LinkedHashMap) obj;
            Object content = map.get("content");
            if (content != null) {
                map.put("msg", "成功");
                if (content instanceof ArrayList) {
                    ArrayList list = (ArrayList) content;
                    if (list.size() == 0) {
                        map.put("msg", "无数据");
                    } else {
                        map.put("msg", "成功");
                    }
                }
            } else {
                map.put("msg", "无数据");
            }
            map.put("code", 0);
        }
        return map;
    }


    /**
     * 调用现在的查询接口查看要下载的数据总量,因为数据量过大的话分在不同的页面里面,先查询总数返回前台,前台分文件后分别调用
     *
     * @param pageable
     * @param lx
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/download/total/{lx}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询数据下载总量", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Object queryTaskDownload(@LayuiPageable Pageable pageable, @PathVariable("lx") String lx, HttpServletRequest httpServletRequest) {
        if (Objects.isNull(lx)) {
            throw new MissingArgumentException("参数错误");
        }
        Map result = new HashMap();
        result.put("maxdatanum", maxdatanum);
        result.put("perfiledatanum", perfiledatanum);
        Object data = null;
        switch (lx) {
            case "db":
                data = queryTodoTask(pageable, httpServletRequest);
                break;
            case "yb":
                data = queryCompleteTask(pageable, httpServletRequest);
                break;
            case "xm":
                data = queryAllTask(pageable, httpServletRequest);
                break;
            case "rl":
                data = listClaimTask(pageable, httpServletRequest);
                break;
            default:
        }
        Map map = JSONObject.parseObject(JSONObject.toJSONString(data), Map.class);
        result.put("total", map.get("totalElements"));
        return result;
    }


    /**
     * 下载当前文件内容
     *
     * @param pageable
     * @param lx
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/download/{lx}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "数据下载", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public void queryTaskDownload(@LayuiPageable Pageable pageable,
                                  @PathVariable("lx") String lx,
                                  @RequestParam(value = "page" ,required = false) String page,
                                  HttpServletRequest httpServletRequest,
                                  HttpServletResponse response
    ) {
        if (Objects.isNull(lx)) {
            throw new MissingArgumentException("参数错误");
        }
        Object data = null;
        //导出字段
        Map<String, String> exportColumn = new LinkedHashMap<>();
        //导出字典项
        Map<String, Consumer<Map<String, String>>> exportColumnFunction = new HashMap<>();
        switch (lx) {
            case "db":
                data = queryTodoTask(pageable, httpServletRequest);
                exportColumn.put("流程状态","state");
                exportColumn.put("受理编号","slbh");
                exportColumn.put("权利人","qlr");
                exportColumn.put("义务人","ywr");
                exportColumn.put("坐落","zl");
                exportColumn.put("受理人","procStartUserName");
                exportColumn.put("流程名称","processDefName");
                exportColumn.put("登记原因","djyy");
                exportColumn.put("节点名称","taskName");
                exportColumn.put("开始时间","startTime");
                exportColumn.put("不动产单元号","bdcdyh");
                exportColumn.put("缴费状态","jfzt");
//                exportColumn.put("项目名称","rwNameTpl");
                if (CommonConstantUtils.SYSTEM_VERSION_HF.equals(version)) {
                    exportColumn.put("预约登记中心","yyzx");
                    exportColumn.put("预约时间","yysj");
                }
                exportColumn.put("业务类型","category");
                exportColumn.put("认领状态","claimStatusName");
                break;
            case "yb":
                data = queryCompleteTask(pageable, httpServletRequest);
                exportColumn.put("流程状态","state");
                exportColumn.put("受理编号","slbh");
                exportColumn.put("权利人","qlr");
                exportColumn.put("义务人","ywr");
                exportColumn.put("坐落","zl");
                exportColumn.put("流程名称","processDefName");
                exportColumn.put("登记原因","djyy");
                exportColumn.put("节点名称","taskName");
                exportColumn.put("开始时间","startTime");
                exportColumn.put("结束时间","endTime");
                exportColumn.put("不动产单元号","bdcdyh");
//                exportColumn.put("项目名称","rwNameTpl");
                if (CommonConstantUtils.SYSTEM_VERSION_HF.equals(version)) {
                    exportColumn.put("预约登记中心","yyzx");
                    exportColumn.put("预约时间","yysj");
                }
                exportColumn.put("业务类型","category");
                exportColumn.put("处理人","taskAssName");
                break;
            case "xm":
                data = queryAllTask(pageable, httpServletRequest);
                exportColumn.put("流程状态","state");
                exportColumn.put("受理编号","slbh");
                exportColumn.put("权利人","qlr");
                exportColumn.put("义务人","ywr");
                exportColumn.put("坐落","zl");
                exportColumn.put("流程名称","procDefName");
                exportColumn.put("登记原因","djyy");
                exportColumn.put("受理人","startUserName");
                exportColumn.put("受理时间","startTime");
                exportColumn.put("不动产单元号","bdcdyh");
                exportColumn.put("业务类型","category");
//                exportColumn.put("项目名称","rwNameTpl");
                if (CommonConstantUtils.SYSTEM_VERSION_HF.equals(version)) {
                    exportColumn.put("预约登记中心","yyzx");
                    exportColumn.put("预约时间","yysj");
                }
                exportColumn.put("超期状态","procTimeoutStatus");
                break;
            case "rl":
                data = listClaimTask(pageable, httpServletRequest);
                exportColumn.put("受理编号","slbh");
                exportColumn.put("权利人","qlr");
                exportColumn.put("义务人","ywr");
                exportColumn.put( "坐落","zl");
                exportColumn.put( "流程名称","processDefName");
                exportColumn.put("登记原因","djyy");
                exportColumn.put( "受理人","procStartUserName");
                exportColumn.put("节点名称","taskName");
                exportColumn.put("开始时间","startTime");
                exportColumn.put("结束时间","endTime");
                exportColumn.put("不动产单元号","bdcdyh");
//                exportColumn.put( "项目名称","rwNameTpl");
                if (CommonConstantUtils.SYSTEM_VERSION_HF.equals(version)) {
                    exportColumn.put("预约登记中心","yyzx");
                    exportColumn.put("预约时间","yysj");
                }
                exportColumn.put("业务类型","category");
                exportColumn.put("处理人","taskAssName");
                break;
            default:
        }
        if (Objects.isNull(data)) {
            throw new MissingArgumentException("参数错误");
        }
        //流程状态处理
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        List<Map> yyfzxZdList = zdMap.get("yyfzx");
        exportColumnFunction.put("stateTpl", new Consumer<Map<String, String>>() {
            @Override
            public void accept(Map<String, String> stringStringMap) {
                try {
                    if (stringStringMap.containsKey("state") && "2".equals(stringStringMap.get("state"))) {
                        stringStringMap.put("state", "挂起");
                    } else if (stringStringMap.containsKey("backStatus") && "2".equals(stringStringMap.get("backStatus"))) {
                        stringStringMap.put("state", "退回");
                    } else if (stringStringMap.containsKey("taskTimeoutStatusName") && "超期".equals(stringStringMap.get("taskTimeoutStatusName"))) {
                        stringStringMap.put("state", "超期");
                    } else {
                        stringStringMap.put("state", "正常");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        exportColumnFunction.put("lcmcTpl", new Consumer<Map<String, String>>() {
            @Override
            public void accept(Map<String, String> stringStringMap) {
                try {
                    if (stringStringMap.containsKey("processDefName")) {
                        if (stringStringMap.containsKey("sply") && "3".equals(stringStringMap.get("sply"))) {
                            stringStringMap.put("processDefName", "(互联网)" + stringStringMap.get("processDefName"));
                        } else if (stringStringMap.containsKey("sply") && "5".equals(stringStringMap.get("sply"))) {
                            stringStringMap.put("processDefName", "(互联网+一窗)" + stringStringMap.get("processDefName"));
                        }else {
                            stringStringMap.put("processDefName", stringStringMap.get("processDefName"));
                        }
                    }
                    if (stringStringMap.containsKey("procDefName")) {
                        if (stringStringMap.containsKey("sply") && "3".equals(stringStringMap.get("sply"))) {
                            stringStringMap.put("procDefName", "(互联网)" + stringStringMap.get("procDefName"));
                        } else if (stringStringMap.containsKey("sply") && "5".equals(stringStringMap.get("sply"))) {
                            stringStringMap.put("procDefName", "(互联网+一窗)" + stringStringMap.get("procDefName"));
                        }else {
                            stringStringMap.put("procDefName", stringStringMap.get("procDefName"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        exportColumnFunction.put("jfztTpl", new Consumer<Map<String, String>>() {
            @Override
            public void accept(Map<String, String> stringStringMap) {
                try {
                    if (stringStringMap.containsKey("jfzt")) {
                        if ("1".equals(stringStringMap.get("jfzt"))) {
                            stringStringMap.put("jfzt", "已缴费");
                        } else {
                            stringStringMap.put("jfzt", "");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        exportColumnFunction.put("rwNameTpl", new Consumer<Map<String, String>>() {
            @Override
            public void accept(Map<String, String> stringStringMap) {
                try {
                    if (stringStringMap.containsKey("processDefName") && stringStringMap.containsKey("priority")) {
                        if ("150".equals(stringStringMap.get("priority"))) {
                            stringStringMap.put("priority", "【紧急】" + stringStringMap.get("processDefName"));
                        } else if ("100".equals(stringStringMap.get("priority"))) {
                            stringStringMap.put("priority", "【一般】" + stringStringMap.get("processDefName"));
                        } else if ("50".equals(stringStringMap.get("priority"))) {
                            stringStringMap.put("priority", "【普通】" + stringStringMap.get("processDefName"));
                        } else {
                            stringStringMap.put("priority", "");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        exportColumnFunction.put("flowStateTpl", new Consumer<Map<String, String>>() {
            @Override
            public void accept(Map<String, String> stringStringMap) {
                try {
                    if (stringStringMap.containsKey("procStatus")) {
                        if ("2".equals(stringStringMap.get("procStatus"))) {
                            stringStringMap.put("procStatus", "办结");
                        } else if ("3".equals(stringStringMap.get("procStatus"))) {
                            stringStringMap.put("procStatus", "挂起");
                        } else {
                            stringStringMap.put("procStatus", "办理中");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        exportColumnFunction.put("overStateTpl", new Consumer<Map<String, String>>() {
            @Override
            public void accept(Map<String, String> stringStringMap) {
                try {
                    if (stringStringMap.containsKey("procTimeoutStatus")) {
                        if ("1".equals(stringStringMap.get("procTimeoutStatus"))) {
                            stringStringMap.put("procTimeoutStatus", "已超期");
                        } else {
                            stringStringMap.put("procTimeoutStatus", "未超期");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        exportColumnFunction.put("yydjzx", new Consumer<Map<String, String>>() {
            @Override
            public void accept(Map<String, String> stringStringMap) {
                try {
                    if (stringStringMap.containsKey("yyfzx")) {
                        for (Map map : yyfzxZdList) {
                            if(map.containsKey("DM") && map.get("DM").equals("yyfzx")){
                                stringStringMap.put("yyzx", String.valueOf(map.get("MC")));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        exportColumnFunction.put("yydjsj", new Consumer<Map<String, String>>() {
            @Override
            public void accept(Map<String, String> stringStringMap) {
                if (stringStringMap.containsKey("yykssj") && stringStringMap.containsKey("yyjssj")) {
                    stringStringMap.put("yysj",
                            stringStringMap.get("yykssj") + "至" + stringStringMap.get("yyjssj"));
                }
            }
        });
        try {
            Map map = JSONObject.parseObject(JSONObject.toJSONString(data), Map.class);
            List<Map<String, String>> content = JSON.parseObject(JSON.toJSONString(map.get("content")), new TypeReference<List<Map<String, String>>>() {
            });
            ExcelUtil.exportExcelWithConsumer("任务"+ new SimpleDateFormat("yyyyMMddHH").format(new Date()) +"导出-" + page + ".xls",
                    exportColumn,
                    content,
                    response,
                    exportColumnFunction);
        } catch (Exception e) {
            throw new AppException("导出Excel失败");
        }
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
        if (Objects.nonNull(taskData)) {
            LOGGER.warn("打开任务列表时根据任务taskid={} 查询任务信息,流程实例id={},点击用户{}", taskId, taskData.getProcessInstanceId(), userManagerUtils.getCurrentUserName());
        }
        return taskData;
    }

    /**
     * 新建任务列表
     *
     * @param processDefName
     * @return 任务列表
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @GetMapping(value = "/list")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取新建任务列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "processDefName", value = "工作流定义名", required = false, dataType = "String", paramType = "query")})
    public Object listCategoryProcess(@RequestParam(value = "processDefName", required = false) String processDefName) {
        UserDto userDto = userManagerUtils.getCurrentUser();
        return processService.listCategoryProcess(userDto, processDefName);
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
     * 根据节点获取 portal-ui 按钮权限
     *
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
        if (Objects.isNull(taskData) || StringUtils.isBlank(taskData.getFormKey())) {
            throw new AppException(ErrorCode.CUSTOM, "未查询到任务信息");
        }
        String formKey = taskData.getFormKey();
        List<FormStateDTO> formStateDTOList = formStateClient.listByFormViewKey(formKey, CommonConstantUtils.FORMKEY_FILTER_OPERATE_LIKE, CommonConstantUtils.FORMKEY_FILTER_URL_PORTAL_UI);
        List<FormElementConfigDTO> formElementConfigDTOList = new ArrayList<>(10);
        if (CollectionUtils.isNotEmpty(formStateDTOList)) {
            formElementConfigDTOList = formThirdResourceClient.getElementWithChildConfig(formStateDTOList.get(0).getFormStateId(), resourceName);
        }
        return formElementConfigDTOList;
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
        defaultQueryOrder(requestConditions, httpServletRequest, "rl_order");
        Page<Map<String, Object>> taskExtendDtoPage = processTaskCustomExtendClient.claimTaskExtendList(requestConditions, userName, pageable.getPageSize(), pageable.getPageNumber());
        if (StringUtils.isNotBlank(fjNum) && CollectionUtils.isNotEmpty(taskExtendDtoPage.getContent())) {
            // 判断是否展示文件夹图标
            Page page = judgeShowFolder(taskExtendDtoPage, pageable);
            return addLayUiCode(page);
        }
        if (CollectionUtils.isNotEmpty(taskExtendDtoPage.getContent())) {
            //转换审批来源名称
            convertSply(taskExtendDtoPage.getContent());
        }
        return addLayUiCode(taskExtendDtoPage);
    }

    @PostMapping("/claim/wsyw")
    @ApiOperation(value = "获取网上业务列表", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    public Object listWsywTask(@LayuiPageable Pageable pageable, HttpServletRequest httpServletRequest) {
        List<RequestCondition> requestConditions = querySearchParams(httpServletRequest, null);
        // 处理网上业务台账顺序
        defaultQueryOrder(requestConditions, httpServletRequest, "wsyw_order");

        String userName = userManagerUtils.getCurrentUserName();
        Page<Map<String, Object>> taskExtendDtoPage = processTaskCustomExtendClient.claimTaskExtendList(requestConditions, userName, pageable.getPageSize(), pageable.getPageNumber());
        if (StringUtils.isNotBlank(fjNum) && CollectionUtils.isNotEmpty(taskExtendDtoPage.getContent())) {
            // 判断是否展示文件夹图标
            Page page = judgeShowFolder(taskExtendDtoPage, pageable);
            return addLayUiCode(page);
        }
        return addLayUiCode(taskExtendDtoPage);
    }

    /**
     * @param httpServletRequest
     * @param pageable
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @PostMapping("/claim/fcwq")
    @ApiOperation(value = "获取房产网签认领任务列表", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    public Object listFcwqClaimTask(@LayuiPageable Pageable pageable, HttpServletRequest httpServletRequest) {
        String userName = userManagerUtils.getCurrentUserName();
        List<RequestCondition> requestConditions = querySearchParams(httpServletRequest, "fcwqrlContent");
        defaultQueryOrder(requestConditions, httpServletRequest, "rl_order");
        Page<Map<String, Object>> taskExtendDtoPage = processTaskCustomExtendClient.claimTaskExtendList(requestConditions, userName, pageable.getPageSize(), pageable.getPageNumber());
        if (StringUtils.isNotBlank(fjNum) && CollectionUtils.isNotEmpty(taskExtendDtoPage.getContent())) {
            // 判断是否展示文件夹图标
            Page page = judgeShowFolder(taskExtendDtoPage, pageable);
            return addLayUiCode(page);
        }
        return addLayUiCode(taskExtendDtoPage);
    }

    /**
     * @param httpServletRequest
     * @param pageable
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 外网申请 南通
     */
    @PostMapping("/claim/wwsq")
    @ApiOperation(value = "获取外网申请任务列表", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    public Object listWwsqTask(@LayuiPageable Pageable pageable, HttpServletRequest httpServletRequest) {
        String userName = userManagerUtils.getCurrentUserName();
        Page<Map<String, Object>> taskExtendDtoPage = null;
        // 先处理其他搜索参数
        List<RequestCondition> requestConditions = querySearchParams(httpServletRequest, null);
        List<RequestCondition> otherRequestCollection = requestConditions.stream().filter(requestCondition -> !"slbh".equals(requestCondition.getRequestKey())).collect(Collectors.toList());
        // 拼接受理编号查询参数
        if (StringUtils.isBlank(wwsqglgz)) {
            LOGGER.warn("外网申请过滤规则为空！直接以搜索参数查询大云平台");
            taskExtendDtoPage = processTaskCustomExtendClient.claimTaskExtendList(requestConditions, userName, pageable.getPageSize(), pageable.getPageNumber());
            return addLayUiCode(taskExtendDtoPage);
        }
        LOGGER.info("wwsqglgz{}", wwsqglgz);
        String[] slbhPrefixArr = wwsqglgz.split(",");
        LOGGER.info("slbhPrefixArr{}", Arrays.toString(slbhPrefixArr));
        List<String> slbhQueryParam = new ArrayList<>();
        for (int i = 0; i < slbhPrefixArr.length; i++) {
            String tempSlbh = slbhPrefixArr[i];
            if (StringUtils.isBlank(tempSlbh)) {
                continue;
            }
            tempSlbh += "%";
            if (StringUtils.isNotBlank(httpServletRequest.getParameter("slbh"))) {
                tempSlbh += httpServletRequest.getParameter("slbh");
            }
            LOGGER.info("tempSlbh{}", tempSlbh);
            slbhQueryParam.add(tempSlbh);
        }
        RequestCondition rc = new RequestCondition();
        rc.setRequestKey("slbh");
        rc.setRequestJudge("likes");
        rc.setRequestValue(slbhQueryParam);
        otherRequestCollection.add(rc);
        taskExtendDtoPage = processTaskCustomExtendClient.claimTaskExtendList(otherRequestCollection, userName, pageable.getPageSize(), pageable.getPageNumber());
        return addLayUiCode(taskExtendDtoPage);
    }

    /**
     * @param processInstanceId
     * @return List<TaskData>
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 流程实例当前运行的人工节点列表或最后一个节点列表
     */
    @GetMapping("/process/last")
    @ApiOperation(value = "流程实例当前运行的人工节点列表或最后一个节点列表")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "processInstanceId", value = "流程实例id", paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public ProcessLastTaskDTO processLastTasks(@RequestParam(value = "processInstanceId") String processInstanceId) {
        if (StringUtils.isBlank(processInstanceId)) {
            throw new MissingArgumentException("获取当前流程最后一个节点， processInstanceId 不能为空！");
        }
        ProcessLastTaskDTO processLastTaskDTO = new ProcessLastTaskDTO();
        processLastTaskDTO.setProcessInstanceData(processInstanceClient.getProcessInstance(processInstanceId));
        List<TaskData> taskData = processTaskClient.processLastTasks(processInstanceId);
        if (CollectionUtils.isEmpty(taskData)) {
            throw new MissingArgumentException("获取全部节点信息失败！");
        }
        processLastTaskDTO.setTaskId(taskData.get(0).getTaskId());
        processLastTaskDTO.setStartUserId(taskData.get(0).getStartUserId());
        LOGGER.warn("{}:获取项目列表展示节点：{}", BdcTaskController.class.getName(), JSON.toJSON(processLastTaskDTO));
        return processLastTaskDTO;
    }

    /**
     * 获取所有已经发布的最新版本的流程定义
     *
     * @return List<ProcessDefData>
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/process/all")
    @ApiOperation(value = "获取所有已经发布的最新版本的流程定义")
    @ResponseStatus(HttpStatus.OK)
    public List<ProcessDefData> getAllProcessDefData() {
        return processDefinitionClient.getAllProcessDefData();
    }

    /**
     * 获取分类下的工作流
     */
    @GetMapping(value = "/list/category/processId")
    public Object listBdcZjxx(@RequestParam String category) {
        if (StringUtils.isBlank(category)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数业务分类。");
        }
        // 获取业务分类下所有流程
        List<ProcessDefData>  processModelList = processDefinitionClient.getProcessDefDataForCategory(category);
        return processModelList.stream()
                .map(ProcessDefData::getProcessDefKey)
                .collect(Collectors.toList());

    }

    /**
     * 获取分类
     *
     * @return
     */
    @GetMapping(value = "/listAllCategoryProcess")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取分类")
    public Object listAllCategoryProcess() {
        Map<String, Object> result = new HashMap<>();
        result.put("content", processTaskClient.listAllCategoryProcess());
        return result;
    }

    /**
     * 获取用户待办下所有的流程定义
     *
     * @return List<ProcessDefData>
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/process/gr")
    @ApiOperation(value = "获取用户待办下所有的流程定义")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, String>> getGrProcessDefData() {
        String userName = userManagerUtils.getCurrentUserName();
        return processTaskRestClient.queryUserProcessDefKeyConditionForBacklog(userName);
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

    @GetMapping("/getQxdm")
    @ApiOperation(value = "获取区县代码")
    @ResponseStatus(HttpStatus.OK)
    public Object getQxdmData() {
        List<Map> result = new ArrayList<>(20);
        List<RegionDto> regionDtolist = new ArrayList<>(20);
        // 获取县级代码
        regionDtolist.addAll(regionManagerClient.findRegionsByLevel(3));
        if (CollectionUtils.isNotEmpty(regionDtolist)) {
            for (RegionDto regionDto : regionDtolist) {
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
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a> 2019年10月24日更新
     * @description 获取移交单信息
     */
    @GetMapping("/bdcYjd/page")
    @ApiOperation(value = "获取所有受理节点已转发的任务")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Object getBdcYjdData(@LayuiPageable Pageable pageable, HttpServletRequest httpServletRequest) {
        List<RequestCondition> requestConditions = querySearchParams(httpServletRequest, null);
        // 判断前端是否传递了排序条件，不存在则获取移交单的默认顺序
        if (!isPageOrder(httpServletRequest)) {
            defaultQueryOrder(requestConditions, httpServletRequest, "yjd_order");
        }
        //分页查询已办任务
        Page<Map<String, Object>> ybTaskPage = processTaskCustomExtendClient.allReceiptExtend(requestConditions, pageable.getPageSize(), pageable.getPageNumber());

        if (ybTaskPage == null || CollectionUtils.isEmpty(ybTaskPage.getContent())) {
            return this.addLayUiCode(ybTaskPage);
        }
        if (this.version.equals(CommonConstantUtils.SYSTEM_VERSION_HF)) {
            return this.addLayUiCode(ybTaskPage);
        }
        // 添加打印状态
        List<Map<String, Object>> ybTaskWithDyzt = bdcYbYjdService.addYjdDyzt(ybTaskPage.getContent());
        return this.addLayUiCode(new PageImpl<>(ybTaskWithDyzt, pageable, ybTaskPage.getTotalElements()));
    }

    @GetMapping("/bdcYjd/yjdTaskData")
    @ApiOperation(value = "获取已转发任务和移交单号")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Object getBdcYdyYjdData(@LayuiPageable Pageable pageable, HttpServletRequest httpServletRequest) {
        // 移交单号不为空时，先按移交单号查询对应任务id，获取流程信息，再根据受理编号，权利人，坐落过滤
        if (httpServletRequest != null && StringUtils.isNotBlank(httpServletRequest.getParameter("yjdh"))) {
            Map paramMap = new HashMap();
            paramMap.put("yjdh", httpServletRequest.getParameter("yjdh"));
            List<BdcYjdTaskGxDO> yjdTaskGxDOS = bdcYjdFeignService.getYjdTaskGx(paramMap);
            if (CollectionUtils.isEmpty(yjdTaskGxDOS)) {
                return this.addLayUiCode(new PageImpl(new ArrayList(), pageable, 0));
            }

            // 补充流程实例信息
            List<Map<String, Object>> content = addProcessInsExtendData(yjdTaskGxDOS, httpServletRequest);
            return this.addLayUiCode(PageUtils.listToPage(content, pageable));
        }

        // 当查询参数移交单号为空时，按受理编号，权利人，坐落直接查大云平台
        List<RequestCondition> requestConditions = querySearchParams(httpServletRequest, null);
        if (!isPageOrder(httpServletRequest)) {
            defaultQueryOrder(requestConditions, httpServletRequest, "yjd_order");
        }
        Page<Map<String, Object>> yjdTaskPage = processTaskCustomExtendClient.allReceiptExtend(requestConditions, pageable.getPageSize(), pageable.getPageNumber());
        if (yjdTaskPage == null || CollectionUtils.isEmpty(yjdTaskPage.getContent())) {
            return this.addLayUiCode(yjdTaskPage);
        }

        List<Map<String, Object>> yjdTaskList = bdcYbYjdService.addYjdh(yjdTaskPage.getContent());
        return this.addLayUiCode(new PageImpl<>(yjdTaskList, pageable, yjdTaskPage.getTotalElements()));
    }

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [yjdTaskGxDOS, httpServletRequest]
     * @description 根据任务id补充流程实例信息
     */
    private List<Map<String, Object>> addProcessInsExtendData(List<BdcYjdTaskGxDO> yjdTaskGxDOS, HttpServletRequest httpServletRequest) {
        List<Map<String, Object>> content = new ArrayList<>();
        for (BdcYjdTaskGxDO yjdTaskGxDO : yjdTaskGxDOS) {
            TaskData taskData = processTaskClient.getTaskById(yjdTaskGxDO.getTaskid());
            if (taskData != null) {
                if (StringUtils.isBlank(taskData.getProcessInstanceId())) {
                    LOGGER.warn("该任务缺少工作流实例id, 任务id：{}", yjdTaskGxDO.getTaskid());
                    continue;
                }
                List<Map<String, Object>> processInsCustomExtendList = processInsCustomExtendClient.getProcessInsCustomExtend(taskData.getProcessInstanceId());
                if (CollectionUtils.isEmpty(processInsCustomExtendList)) {
                    LOGGER.warn("该工作流实例ID未查到对应工作流实例信息, 工作流实例id：{}", taskData.getProcessInstanceId());
                    continue;
                }
                Map<String, Object> processInsExtend = processInsCustomExtendList.get(0);
                if (!matchProcessInsData(processInsExtend, httpServletRequest)) {
                    continue;
                }
                Map<String, Object> yjdTaskMap = new HashMap<>();
                yjdTaskMap.put("yjdh", httpServletRequest.getParameter("yjdh"));
                yjdTaskMap.put("taskAssName", taskData.getTaskAssName());
                yjdTaskMap.put("slbh", processInsExtend.get("SLBH"));
                yjdTaskMap.put("qlr", processInsExtend.get("QLR"));
                yjdTaskMap.put("zl", processInsExtend.get("ZL"));
                content.add(yjdTaskMap);
            }
        }
        return content;
    }

    /**
     * @return boolean
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [processInsExtend, httpServletRequest]
     * @description 根据查询参数（受理编号，权利人，坐落）过滤流程实例
     */
    private boolean matchProcessInsData(Map<String, Object> processInsExtend, HttpServletRequest httpServletRequest) {
        String slbhInprocessIns = (String) processInsExtend.get("SLBH");
        String qlrInProcessIns = (String) processInsExtend.get("QLR");
        String zlInProcessIns = (String) processInsExtend.get("ZL");

        // 受理编号模糊匹配
        if (StringUtils.isNotBlank(httpServletRequest.getParameter("slbh"))) {
            if (StringUtils.isBlank(slbhInprocessIns)) {
                return false;
            }
            if (!(slbhInprocessIns).contains(httpServletRequest.getParameter("slbh"))) {
                return false;
            }
        }
        // 权利人模糊匹配
        if (StringUtils.isNotBlank(httpServletRequest.getParameter("qlr"))) {
            if (StringUtils.isBlank(qlrInProcessIns)) {
                return false;
            }
            if (!(qlrInProcessIns).contains(httpServletRequest.getParameter("qlr"))) {
                return false;
            }
        }
        // 坐落模糊匹配
        if (StringUtils.isNotBlank(httpServletRequest.getParameter("zl"))) {
            if (StringUtils.isBlank(zlInProcessIns)) {
                return false;
            }
            return (zlInProcessIns).contains(httpServletRequest.getParameter("zl"));
        }
        return true;
    }


    /**
     * 从配置文件中读取默认查询顺序
     *
     * @param requestConditions  查询条件
     * @param httpServletRequest 请求参数
     * @param type               默认顺序类型
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    private void defaultQueryOrder(List<RequestCondition> requestConditions, HttpServletRequest httpServletRequest, String type) {
        // 获取页面上传的排序类型
        String value = httpServletRequest.getParameter("order_type");
        if (StringUtils.isNotBlank(value)) {
            type = value;
        }
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
     * 生成并回写移交单号
     *
     * @param taskids taskids
     * @return {String} 移交单号
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/bdcYjd/yjdh")
    @ApiOperation(value = "生成并回写移交单号")
    @ResponseStatus(HttpStatus.OK)
    public String generateYjdh(@RequestBody List<String> taskids) {
        if (CollectionUtils.isEmpty(taskids)) {
            throw new AppException("任务id列表为空！");
        }
        return bdcYbYjdService.generateYbYjdBh(taskids);
    }

    /**
     * @param taskids
     * @return true 更新成功
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 更新打印状态 蚌埠 修改移交单任务关系表打印状态
     */
    @PostMapping("/bdcYjd/updateDyztBB")
    @ApiOperation(value = "更新打印状态")
    @ResponseStatus(HttpStatus.OK)
    public Object updateDyztBB(@RequestBody List<String> taskids) {
        if (CollectionUtils.isEmpty(taskids)) {
            throw new AppException("打印的参数不能为空！");
        }
        Boolean result = true;
        try {
            bdcYjdFeignService.updateYjdDyztBytaskid(taskids);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * @param processInsExtendDtoList
     * @return true 更新成功
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 更新打印状态 打印状态回写大云
     */
    @PostMapping("/bdcYjd/updateDyzt")
    @ApiOperation(value = "更新打印状态")
    @ResponseStatus(HttpStatus.OK)
    public Object updateDyzt(@RequestBody List<Map> processInsExtendDtoList) {
        if (CollectionUtils.isEmpty(processInsExtendDtoList)) {
            throw new AppException("打印的参数不能为空！");
        }
        Boolean result = true;
        Map dyMap = new HashMap();
        dyMap.put("DYZT", 1);
        try {
            processInsExtendDtoList.forEach(processInsExtendDto -> {
                bdcYwsjHxFeignService.updateBdcYwsj(String.valueOf(MapUtils.getObject(processInsExtendDto, "procInsId")), dyMap);
            });
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改移交单备注
     */
    @PostMapping("/bdcYjd/updateYjdBz")
    @ApiOperation(value = "修改移交单备注")
    @ResponseStatus(HttpStatus.OK)
    public void updateYjdBz(@RequestBody Map<String, Object> processInsExtendDto) {
        if (processInsExtendDto == null) {
            throw new AppException("修改备注的参数不能为空！");
        }
        String gzlslid = MapUtils.getString(processInsExtendDto, "PROC_INS_ID");
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("工作流实例ID不能为空！");
        }

        bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, processInsExtendDto);
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取工作流定义名称
     */
    @GetMapping("/bdcYjd/gzldymcs")
    @ApiOperation(value = "获取工作流定义名称")
    @ResponseStatus(HttpStatus.OK)
    public Object queryGzldymcs() {
        List<ProcessDefData> processDefDataList = workFlowUtils.getAllProcessDefData();
        processDefDataList = processDefDataList.stream().filter(processDefData ->
                processDefData.getSuspensionState() == 1
        ).collect(Collectors.toList());
        return processDefDataList;
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
            Map stringMap = httpServletRequest.getParameterMap();
            HttpSession httpSession = httpServletRequest.getSession();
            String session = "";
            if (httpSession != null) {
                session = httpSession.getId();
            }
            LOGGER.warn("任务列表{}paramMap查询条件{},当前用户{},当前session{}", lx, JSON.toJSONString(stringMap), userManagerUtils.getCurrentUserName(), session);
            //是否查询总数,默认不传查询总数
            if (StringUtils.equals(CommonConstantUtils.BOOL_FALSE, httpServletRequest.getParameter(LOAD_TOTAL))) {
                RequestCondition condition = new RequestCondition();
                condition.setRequestKey("pageCount");
                condition.setRequestJudge(QueryJudge.EQUALS.value());
                condition.setRequestValue("");
                list.add(condition);
            }
            for (Map.Entry<String, Map<String, String>> param : queryParamsConfig.getParamMap().entrySet()) {
                String value = httpServletRequest.getParameter(param.getKey());
                //模糊类型
                String mhlxValue = httpServletRequest.getParameter(param.getKey() + "mh");
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
                    if (StringUtils.isNotBlank(mhlxValue)) {
                        if (StringUtils.equals(CommonConstantUtils.MHLX_JQ, mhlxValue)) {
                            requestCondition.setRequestJudge(QueryJudge.EQUALS.value());
                        } else if (StringUtils.equals(CommonConstantUtils.MHLX_QMH, mhlxValue)) {
                            requestCondition.setRequestJudge(QueryJudge.LIKE.value());
                        }
                    }
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

        //加上只查询自然资源的数据的条件
        boolean defNameMustCondition = list
                .stream()
                .noneMatch(requestCondition -> "processKey".equals(requestCondition.getRequestKey()));
        //没有流程查询条件且当前要筛选掉自然资源的流程
        if (defNameMustCondition && zrzyFilter) {
            if (CollectionUtils.isNotEmpty(zrzyProcessDefKey)) {
                zrzyProcessDefKey.forEach(def -> {
                    RequestCondition zrzyCondition = new RequestCondition();
                    zrzyCondition.setRequestJudge("like");
                    zrzyCondition.setRequestKey("processKey");
                    zrzyCondition.setRequestValue(def);
                    list.add(zrzyCondition);
                });
            }
        }
        return list;
    }

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
        //追加区县代码默认过滤
        if (StringUtils.equals("xmContent", lx) || StringUtils.equals("rlContent", lx) || StringUtils.equals("dajjrlContent", lx)) {
            List<String> qxdmList = Container.getBean(BdcConfigUtils.class).qxdmFilter("rwlb","","");
            if (CollectionUtils.isNotEmpty(qxdmList)) {
                // 当任务列表中查询参数中存在qxdm时，不添加默认区县代码查询参数，否则会导致调用大云接口查询报错
                if (!hasRequestCondition(requestConditionList, "qxdm")) {
                    RequestCondition requestCondition = new RequestCondition();
                    requestCondition.setRequestKey("qxdm");
                    requestCondition.setRequestJudge(QueryJudge.IN.value());
                    requestCondition.setRequestValue(qxdmList);
                    requestConditionList.add(requestCondition);
                }
            }
        }
    }

    /**
     * 判断查询参数中某个key是否存在
     *
     * @param requestConditionList 请求参数集合
     * @param key                  请求参数key
     * @return boolean true存在
     * @author <a href="mailto:yaoyi@lixin.com">yaoyi</a>
     */
    private boolean hasRequestCondition(List<RequestCondition> requestConditionList, String key) {
        boolean hasCondition = false;
        if (CollectionUtils.isNotEmpty(requestConditionList)) {
            for (RequestCondition requestCondition : requestConditionList) {
                if (requestCondition.getRequestKey().equals(key)) {
                    hasCondition = true;
                    break;
                }
            }
        }
        return hasCondition;
    }

    /**
     * 判断前端是否传递了排序参数
     *
     * @param httpServletRequest request 请求
     * @return boolean
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    private boolean isPageOrder(HttpServletRequest httpServletRequest) {
        String field = httpServletRequest.getParameter("field");
        String order = httpServletRequest.getParameter(QueryJudge.ORDER.value());
        return StringUtils.isNotBlank(field) && StringUtils.isNotBlank(order);
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
    private void queryOrderByConfig(List<RequestCondition> requestConditions, HttpServletRequest httpServletRequest, String prefix) {
        if (MapUtils.isEmpty(queryParamsConfig.getParamMap())) {
            return;
        }
        // 获取页面上传的排序前缀
        String value = httpServletRequest.getParameter("prefix");
        if (StringUtils.isNotBlank(value)) {
            prefix = value;
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
     * 获取权籍数据，判断是否可以落宗
     *
     * @param xnbdcdyh 虚拟不动产单元号
     * @param gzlslid  工作流实例 id
     * @return 是否可以落宗
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/sflz")
    @ApiOperation(value = "判断是否可以落宗")
    @ResponseStatus(HttpStatus.OK)
    public boolean queryQj(@RequestParam(value = "gzlslid", required = false) String gzlslid, @RequestParam(value = "xnbdcdyh", required = false) String xnbdcdyh, @RequestParam(value = "qjgldm", required = false) String qjgldm) {
        //去空格
        xnbdcdyh = StringUtils.deleteWhitespace(xnbdcdyh);
        if (StringUtils.isBlank(gzlslid) && StringUtils.isBlank(xnbdcdyh)) {
            throw new MissingArgumentException("缺少必要参数信息信息");
        }
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            for (BdcXmDTO bdcXmDTO : bdcXmDTOS) {
                // 先判断是否是虚拟的不动产单元号，再调用权籍的接口
                if (BdcdyhToolUtils.checkXnbdcdyh(bdcXmDTO.getBdcdyh())) {
                    BdcdyResponseDTO bdcdyResponseDTO = bdcdyFeignService.queryFwHsAndFwLjzByYbdcdyh(bdcXmDTO.getBdcdyh(), bdcXmDTO.getQjgldm());
                    if (bdcdyResponseDTO != null && StringUtils.isNotBlank(bdcdyResponseDTO.getBdcdyh())) {
                        // 有能匹配的就显示可匹配
                        return true;
                    }
                }
            }
        } else {
            // 先判断是否是虚拟的不动产单元号，再调用权籍的接口
            if (BdcdyhToolUtils.checkXnbdcdyh(xnbdcdyh)) {
                BdcdyResponseDTO bdcdyResponseDTO = bdcdyFeignService.queryFwHsAndFwLjzByYbdcdyh(xnbdcdyh, qjgldm);
                // 有能匹配的就显示可匹配
                return bdcdyResponseDTO != null && StringUtils.isNotBlank(bdcdyResponseDTO.getBdcdyh());
            }
        }
        return false;
    }

    /**
     * 匹配数据
     *
     * @param gzlslid 工作流实例 id
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/matchData")
    @ApiOperation(value = "匹配数据")
    @ResponseStatus(HttpStatus.OK)
    public void match(@RequestParam("gzlslid") String gzlslid) throws Exception {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺少必要参数信息！");
        }
        List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDTOS)) {
            throw new MissingArgumentException("未查询到项目信息！");
        }
        List<PpLzThread> listThread = Lists.newArrayList();

        boolean sfbjs = bdcXmDTOS.size() <= 1;
        //线程池对象
        Map<String, Object> taskMap = new ConcurrentHashMap<>();
        //业务循环
        for (BdcXmDTO bdcXmDTO : bdcXmDTOS) {
            PpLzThread ppLzThread = new PpLzThread(bdcXmDTO.getBdcdyh(), bdcXmDTO.getQjgldm(), bdcPpLzService, false, bdcPpFeignService);
            ppLzThread.setSfbjs(sfbjs);
            ppLzThread.setTaskMap(taskMap);
            listThread.add(ppLzThread);
        }

        if (CollectionUtils.isNotEmpty(listThread)) {
            //多线程处理操作
            threadEngine.excuteThread(listThread, true, taskMap);
            if (taskMap.containsKey("msg")) {
                throw new AppException(taskMap.get("msg") != null ? taskMap.get("msg").toString() : "数据匹配失败!");
            }
        }
        //多线程处理操作
        bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
    }

    /**
     * 匹配数据(存量)
     *
     * @param xnbdcdyhs 工作流实例 id
     * @param qxpp      取消匹配
     * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @GetMapping("/matchClData")
    @ApiOperation(value = "存量数据匹配不动产单元")
    @ResponseStatus(HttpStatus.OK)
    public void matchClData(@RequestParam(value = "xnbdcdyhs") List<String> xnbdcdyhs, boolean qxpp) throws Exception {
        if (CollectionUtils.isEmpty(xnbdcdyhs)) {
            throw new MissingArgumentException("缺少必要参数信息！");
        }
        List<PpLzThread> listThread = Lists.newArrayList();

        boolean sfbjs = xnbdcdyhs.size() <= 1;
        //线程池对象
        Map<String, Object> taskMap = new ConcurrentHashMap<>();
        //业务循环
        for (String xnbdcdyh : xnbdcdyhs) {
            PpLzThread ppLzThread = new PpLzThread(xnbdcdyh, "", bdcPpLzService, qxpp, bdcPpFeignService);
            ppLzThread.setSfbjs(sfbjs);
            ppLzThread.setTaskMap(taskMap);
            listThread.add(ppLzThread);
        }

        if (CollectionUtils.isNotEmpty(listThread)) {
            //多线程处理操作
            threadEngine.excuteThread(listThread, true, taskMap);
            if (taskMap.containsKey("msg")) {
                throw new AppException(taskMap.get("msg") != null ? taskMap.get("msg").toString() : "存量数据匹配失败!");
            }
        }
    }

    /**
     * 匹配数据(存量)
     *
     * @param xnbdcdyh 虚拟单元号
     * @param bdcdyh   单元号
     * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @PostMapping("/matchClSdData")
    @ApiOperation(value = "存量数据手动匹配不动产单元")
    @ResponseStatus(HttpStatus.OK)
    public void matchSdPpData(@RequestParam(value = "xnbdcdyh") String xnbdcdyh, @RequestParam(value = "bdcdyh") String bdcdyh) throws Exception {
        if (StringUtils.isBlank(xnbdcdyh) || StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException("缺少必要参数信息！");
        }
        BdcLzQO bdcLzQO = new BdcLzQO();
        bdcLzQO.setBdcdyh(bdcdyh);
        bdcLzQO.setLsdyh(xnbdcdyh);
        bdcPpFeignService.lz(bdcLzQO);
    }


    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return: Object 组织信息
     * @description 获取所有顶级组织信息
     */
    @GetMapping("/bdcYjd/rootOrgs")
    @ApiOperation(value = "获取所有顶级组织")
    @ResponseStatus(HttpStatus.OK)
    public Object queryRootOrgs() {
        return this.organizationManagerClient.findRootOrgs();
    }

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [id] 父组织id
     * @return: Object 子组织信息
     * @description 获取父组织下所有子组织信息
     */
    @GetMapping("/bdcYjd/childrenOrgs/{id}")
    @ApiOperation(value = "根据父组织id获取子组织")
    @ResponseStatus(HttpStatus.OK)
    public Object queryChildrenOrgs(@PathVariable(value = "id") String id) {
        // enabled 0为禁用，1为启用，null为所有
        if (StringUtils.isEmpty(id)) {
            return Collections.EMPTY_LIST;
        }
        return this.organizationManagerClient.findChildren(id, 1);
    }

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [id] 父组织id
     * @return: Object 角色信息
     * @description 根据组织id获取组织下所有角色
     */
    @GetMapping("/bdcYjd/roles/{id}")
    @ApiOperation(value = "根据组织id获取组织下所有角色")
    @ResponseStatus(HttpStatus.OK)
    public Object queryRolesByOrgId(@PathVariable(value = "id") String id) {
        // enabled 0为禁用，1为启用
        if (StringUtils.isEmpty(id)) {
            return Collections.EMPTY_LIST;
        }
        return this.organizationManagerClient.listRoles(id, 1);
    }

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [id] 角色id
     * @return: Object 用户信息
     * @description 根据角色id获取角色下所有人
     */
    @GetMapping("/bdcYjd/users/{id}")
    @ApiOperation(value = "根据角色id获取角色下所有人")
    @ResponseStatus(HttpStatus.OK)
    public Object queryUsersByRoleId(@PathVariable(value = "id") String id) {
        if (StringUtils.isEmpty(id)) {
            return Collections.EMPTY_LIST;
        }
        return this.roleManagerClient.listEnableUsersByRoleId(id);
    }

    @PostMapping("/bdcYjd/qllx/list")
    @ApiOperation(value = "根据受理编号获取权利信息")
    @ResponseStatus(HttpStatus.OK)
    public Object queryBdcQlBySlbhList(@RequestBody Set<String> slbhList) {
        if (CollectionUtils.isNotEmpty(slbhList)) {
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
            Map<String, Object> map = new HashMap<>(slbhList.size());
            List<Map> zdList = zdMap.get("dyfs");
            for (Map zd : zdList) {
                map.put(MapUtils.getInteger(zd, "DM").toString(), MapUtils.getString(zd, "MC"));
            }
            for (String slbh : slbhList) {
                List<BdcQl> bdcQlList = this.bdcQllxFeignService.listQlxxBySlbh(slbh);
                map.put(slbh, bdcQllxFeignService.listQlxxBySlbh(slbh));
            }
            return map;
        }
        return null;
    }

    /**
     * 获取存量数据匹配不动产单元分页查询数据
     *
     * @param pageable 分页对象
     * @param bdcQlQO  分页查询不动产权利页面参数封装对象
     * @return 分页信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @PostMapping("/clxnbdcdy/list")
    public Object listClXnBdcdy(@LayuiPageable Pageable pageable, BdcQlQO bdcQlQO) {
        // 设置项目来源为 3 和 2
        bdcQlQO.setXmly(Arrays.asList(CommonConstantUtils.XMLY_QT_DM, 2));
        bdcQlQO.setQszt(CommonConstantUtils.QSZT_VALID);
        if (StringUtils.isNotBlank(bdcQlQO.getBdcdyh())) {
            bdcQlQO.setBdcdyh(StringUtils.deleteWhitespace(bdcQlQO.getBdcdyh()));
        }
        String bdcQlQoStr = JSON.toJSONString(bdcQlQO);
        return addLayUiCode(bdcXmFeignService.bdcQlPageByPageJson(pageable, bdcQlQoStr));
    }

    /**
     * @param bdcSlBdcdyhQO 是否分页
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 实测户室分页查询
     */
    @ResponseBody
    @PostMapping("/listBdcdyhByPageJson")
    public Object listBdcdyhByPageJson(@LayuiPageable Pageable pageable, BdcSlBdcdyhQO bdcSlBdcdyhQO) {
        bdcSlBdcdyhQO.setBdcdyh(StringUtils.deleteWhitespace(bdcSlBdcdyhQO.getBdcdyh()));
        bdcSlBdcdyhQO.setZl(StringUtils.deleteWhitespace(bdcSlBdcdyhQO.getZl()));
        return acceptBdcdyFeignService.listBdcdyhByPageOrList(pageable, JSON.toJSONString(bdcSlBdcdyhQO), true);
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
        map.put("ywlx", zdMap.get("ywlx"));
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
        if (CollectionUtils.isNotEmpty(nodePermissionList) && nodePermissionList.contains(taskName)) {
            // 2、根据流程名称、节点、开始时间，判断当前任务之前是否有相同类型的任务
            List<RequestCondition> requestConditions = querySearchParams(httpServletRequest, "dbContent");
            Page<Map<String, Object>> todoTaskPage = processTaskCustomExtendClient.todoTaskExtendList(requestConditions, 10, 0);
            //目前大云时间判断精确了毫秒,小于等于不包含本身
            if (todoTaskPage.getContent().size() > 0) {
                return true;
            }
        }
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
     * 获取网上业务台账审批来源配置
     *
     * @return {List} 配置信息
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @GetMapping("/wsyw/sply")
    @ApiOperation(value = "获取网上业务台账审批来源配置")
    public List<BdcSplyDTO> getWsywSply() {
        if (StringUtils.isBlank(wsywSply)) {
            LOGGER.error("网上业务台账审批来源wsyw.sply未配置");
            return Collections.emptyList();
        }

        String[] wsywSplyArray = wsywSply.split(CommonConstantUtils.ZF_YW_FH);
        if (0 == wsywSplyArray.length) {
            LOGGER.error("网上业务台账审批来源wsyw.sply未配置");
            return Collections.emptyList();
        }

        List<BdcSplyDTO> result = new ArrayList<>();
        for (String sply : wsywSplyArray) {
            String[] splyArray = sply.split("_");
            if (splyArray.length < 2) {
                throw new AppException("网上业务台账审批来源wsyw.sply配置错误");
            }

            BdcSplyDTO splyDTO = new BdcSplyDTO();
            splyDTO.setValue(splyArray[0]);
            splyDTO.setName(splyArray[1]);
            if (3 == splyArray.length) {
                splyDTO.setColor(splyArray[2]);
            }
            result.add(splyDTO);
        }
        return result;
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
        queryOrderByConfig(requestConditions, httpServletRequest, "order_todo");

        Page<Map<String, Object>> todoTaskPage = processTaskCustomExtendClient.todoTaskExtendList(requestConditions, 10, 0);
        return todoTaskPage.getContent();
    }

    private List<Map<String, Object>> listSomeInfo(List<Map<String, Object>> dataList) {
        //记录日志用于处理打开的任务不是点击的任务问题
        List<Map<String, Object>> resultMapList = new ArrayList<>(CollectionUtils.size(dataList));
        for (Map<String, Object> resultMap : dataList) {
            HashMap<String, Object> result = new HashMap<>(2);
            result.put("slbh", MapUtils.getString(resultMap, "slbh", ""));
            result.put("taskId", MapUtils.getString(resultMap, "taskId", ""));
            result.put("formKey", MapUtils.getString(resultMap, "formKey", ""));
            result.put("gzlslid", MapUtils.getString(resultMap, "processInstanceId", ""));
            resultMapList.add(result);
        }
        return resultMapList;
    }


    /**
     * @param slbh    受理编号
     * @param taskId  任务id
     * @param gzlslid 工作流实例id
     * @param rwlb    待办，已办，项目列表，个人，档案交接待办，已办人，认领
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/1/17 15:31
     */
    @GetMapping("/log")
    public void recordLog(String slbh, String taskId, String gzlslid, String rwlb) {
        LOGGER.warn("当前点击数据受理编号={}taskid={}工作流实例id={}任务列表={}当前点击用户{}", slbh, taskId, gzlslid, rwlb, userManagerUtils.getCurrentUserName());
    }

    /**
     * 获取查询条件联想匹配数据，用作下拉选择
     *
     * @param bdcXmQO 查询参数
     * @return {List} 针对不同字段条件返回对应字段查询数据
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/search/matcheddata")
    public List<String> listMatchedData(@RequestBody BdcXmQO bdcXmQO) {
        if (!CheckParameter.checkAnyParameter(bdcXmQO, "bdcdyh", "zl")) {
            return Collections.emptyList();
        }
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXmData(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            return Collections.emptyList();
        }

        if (StringUtils.isNotBlank(bdcXmQO.getBdcdyh())) {
            List<String> bdcdyhList = bdcXmDOList.stream().map(BdcXmDO::getBdcdyh).distinct().collect(Collectors.toList());
            bdcdyhList.add(0, "请选择");
            return bdcdyhList;
        }

        if (StringUtils.isNotBlank(bdcXmQO.getZl())) {
            List<String> zlList = bdcXmDOList.stream().map(BdcXmDO::getZl).distinct().collect(Collectors.toList());
            zlList.add(0, "请选择");
            return zlList;
        }
        return Collections.emptyList();
    }

    /**
     * 批量查询时候处理查询参数：将输入的坐落、不动产单元号查询项目中对应记录受理编号传入大云，目的是为了可以利用批量件中的某个单元、坐落查询出批量件
     *
     * @param requestConditions 任务查询参数
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private void batchSearchParams(List<RequestCondition> requestConditions, Integer ajzt) {
        if (CollectionUtils.isEmpty(requestConditions) || !sfpljcx) {
            return;
        }

        BdcXmQO bdcXmQO = new BdcXmQO();
        for (RequestCondition condition : requestConditions) {
            if ("zl".equals(condition.getRequestKey())) {
                bdcXmQO.setZljq(String.valueOf(condition.getRequestValue()));
            }
            if ("bdcdyh".equals(condition.getRequestKey())) {
                bdcXmQO.setBdcdyh(String.valueOf(condition.getRequestValue()));
            }
        }

        if (StringUtils.isNotBlank(bdcXmQO.getBdcdyh()) || StringUtils.isNotBlank(bdcXmQO.getZljq())) {
            bdcXmQO.setAjzt(ajzt);
            // 页面选择后的坐落和单元号认为是完整数据，直接精确查询
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                Iterator<RequestCondition> iterator = requestConditions.iterator();
                while (iterator.hasNext()) {
                    RequestCondition condition = iterator.next();
                    if ("zl".equals(condition.getRequestKey())) {
                        iterator.remove();
                    }
                    if ("bdcdyh".equals(condition.getRequestKey())) {
                        iterator.remove();
                    }
                }

                RequestCondition slbhCondition = new RequestCondition();
                slbhCondition.setRequestKey("slbh");
                slbhCondition.setRequestJudge("in");
                slbhCondition.setRequestValue(bdcXmDOList.stream().map(BdcXmDO::getSlbh).collect(Collectors.toList()));
                requestConditions.add(slbhCondition);
            }
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取任务列表默认配置
     */
    @GetMapping("/mrpzx")
    @ApiOperation(value = "获取任务列表默认配置")
    public JSONObject getMrpzx() {
        JSONObject jsonObject = new JSONObject();
        //判断用户是否可以限制打开待办任务列表
        UserDto currentUser = userManagerUtils.getCurrentUser();
        jsonObject.put("todoPermission", currentUser.getAdmin() != 1 && StringUtils.equals(todoPermission, "1"));
        jsonObject.put("zdymhlx", zdymhlx);
        jsonObject.put("ywlbXmly", ywlbXmly);
        List<BdcSplyDTO> result = new ArrayList<>();
        if(StringUtils.isNotBlank(rwlbXmly)){
            String[] rwlbSplyArray = rwlbXmly.split(CommonConstantUtils.ZF_YW_FH);
            if (rwlbSplyArray.length>0) {
                for (String sply : rwlbSplyArray) {
                    String[] splyArray = sply.split("_");
                    if (splyArray.length < 2) {
                        throw new AppException("任务列表项目来源rwlb.xmly.xs配置错误");
                    }
                    BdcSplyDTO splyDTO = new BdcSplyDTO();
                    splyDTO.setValue(splyArray[0]);
                    splyDTO.setName(splyArray[1]);
                    if (3 == splyArray.length) {
                        splyDTO.setColor(splyArray[2]);
                    }
                    result.add(splyDTO);
                }
            }
        }
        jsonObject.put("rwlbSplyZdx",result);
        return jsonObject;
    }


}