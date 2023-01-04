package cn.gtmap.realestate.portal.ui.web.rest;

import cn.gtmap.gtc.clients.AuthorityManagerClient;
import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.clients.RoleManagerClient;
import cn.gtmap.gtc.clients.UserManagerClient;
import cn.gtmap.gtc.msg.client.MessageClient;
import cn.gtmap.gtc.msg.domain.dto.MessageDto;
import cn.gtmap.gtc.msg.domain.dto.ProduceMsgDto;
import cn.gtmap.gtc.sso.domain.dto.*;
import cn.gtmap.gtc.sso.domain.enums.ModuleTypeEnum;
import cn.gtmap.gtc.workflow.clients.define.v1.WorkDayClient;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskCustomExtendClient;
import cn.gtmap.gtc.workflow.clients.statistics.TaskStatisticsClient;
import cn.gtmap.gtc.workflow.domain.common.RequestCondition;
import cn.gtmap.gtc.workflow.domain.define.Work;
import cn.gtmap.gtc.workflow.domain.define.WorkDay;
import cn.gtmap.gtc.workflow.domain.statistics.UserTaskCountDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcCfxxFeignService;
import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.DateUtilForWorkDay;
import cn.gtmap.realestate.common.util.RSAUtil;
import cn.gtmap.realestate.portal.ui.core.dto.BdcModuleDTO;
import cn.gtmap.realestate.portal.ui.core.vo.WorkDayVO;
import cn.gtmap.realestate.portal.ui.utils.Constants;
import cn.gtmap.realestate.portal.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/02/25
 * @description 登陆用户信息请求处理控制器，包括：基本资料、修改密码、退出
 */
@Controller
@RequestMapping("/rest/v1.0/user")
@Api(tags = "门户网站服务接口")
public class BdcUserController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcUserController.class);
    private static final String BLANK = "blank";
    /**
     * 工作日
     */
    public static final int WORKDAY = 0;
    /**
     * 休息日
     */
    public static final int OFFDAY = 1;
    /**
     * 节假日
     */
    public static final int HOLIDAY = 2;
    private static final String MULTIPLE_PARAM_SPLITTER = ",";
    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private TaskStatisticsClient taskStatisticsClient;

    @Autowired
    private LogMessageClient logMessageClient;

    @Autowired
    private UserManagerClient userManagerClient;

    @Autowired
    private AuthorityManagerClient authorityManagerClient;

    @Autowired
    private MessageClient messageClient;

    @Autowired
    private WorkDayClient workDayClient;

    @Autowired
    private BdcCfxxFeignService bdcCfxxFeignService;

    @Autowired
    private RoleManagerClient roleManagerClient;
    @Autowired
    private ProcessTaskCustomExtendClient processTaskCustomExtendClient;
    /**
     * 组织机构服务
     */
    @Autowired
    private OrganizationManagerClientMatcher organizationManagerClient;

    @Value("${app.oauth}")
    private String logout;

    @Value("${app.publicOauth:none}")
    private String publicOauth;

    @Value("${xmlb.qfbm.qxdm:}")
    private String xmlbQfBmQxdms;
    /**
     * 台账显示顺序，默认顺序为 代办、已办、项目和认领
     */
    @Value("#{'${portal.order:dbContent,ybContent,xmContent,rlContent}'.split(',')}")
    private List<String> order;

    /**
     * 隐藏项目列表的角色
     */
    @Value("#{'${portal.xmContent.disabledCode: }'.split(',')}")
    private List<String> xmHideCodes;

    /**
     * 获取系统控件下载地址
     */
    @Value("${url.xtkj:}")
    private String xtkjUrl;

    /**
     * 材料补打
     */
    @Value("${clbd.roleCode:}")
    private String roleCode;
    /**
     * 叫号软件 URL
     */
    @Value("${url.callnumber:}")
    protected String callnumber;

    /**
     * 叫号软件 URL
     */
    @Value("${url.pjq:}")
    private String pjqUrl;

    /**
     * 首页隐藏的用户
     */
    @Value("#{'${home.hide.user:}'.split(',')}")
    private List<String> hideUser;

    /**
     * 限制非管理员只允许打开首条待办任务
     */
    @Value("${permission.todo:0}")
    private String todoPermission;

    /**
     * 自然资源台账显示顺序，默认顺序为 代办、已办、项目和认领
     */
    @Value("#{'${portal.zrzyOrder:dbContent,ybContent,xmContent,rlContent}'.split(',')}")
    private List<String> zrzyOrder;

    /**
     * 【连云港】系统主页 “办理情况”区块是否配置专项业务办理量，默认false
     */
    @Value("${zxywbll: false}")
    private boolean zxywbll;

    /**
     * 【连云港】在配置了zxywbll: true时生效，表明专项业务办理量区块显示的专项业务的流程id，默认为空，需要配置
     */
    @Value("${lcid:}")
    private String lcid;

    /**
     * @param request  请求
     * @param response 响应
     * @return 当前用户退出登录
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 当前用户退出登录
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    @ApiOperation("退出系统")
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        securityContext.setAuthentication(null);
        return getAccountLogoutPath(request).concat("?redirect_uri=");
    }

    private String getAccountLogoutPath(HttpServletRequest request) {
        if (!StringUtils.isEmpty(publicOauth) && !"none".equals(publicOauth)) {
            String serverName = request.getServerName();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("redirectUser request IP:" + serverName);
            }
            try {
                if (serverName.contains(".")) {
                    serverName = serverName.substring(0, serverName.indexOf(".", serverName.indexOf(".") + 1));
                }
            } catch (Exception e1) {
                LOGGER.warn("sub serverName IP err:", e1);
            }
            Set<String> urls = org.springframework.util.StringUtils.commaDelimitedListToSet(publicOauth);
            for (String url : urls) {
                if (!org.springframework.util.StringUtils.isEmpty(serverName) && url.contains(serverName)) {
                    return url.replace("/oauth/authorize", "/logout");
                }
            }
        }
        return logout.concat("/logout");
    }

    /**
     * @return 当前用户的基本资料
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 获取用户基本资料
     */
    @GetMapping("/info")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取用户基本资料")
    public Object queryUserInfo() {
        return userManagerUtils.getCurrentUser();
    }

    /**
     * @return 查询所有用户
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description
     */
    @GetMapping("/listAllUsers")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取所有用户")
    public List<UserDto> listAllUsers() {
        return userManagerClient.allUsers(null, null, 1);
    }

    /**
     * @param old      历史密码
     * @param password 新密码
     * @return 修改密码是否成功
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 修改密码
     */
    @PutMapping("/password")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("修改用户密码")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "old", value = "原密码", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "string", paramType = "query")})
    public BaseResultDto updateUserPwd(String old, String password) {
        if (StringUtils.isBlank(old) || StringUtils.isBlank(password)) {
            throw new MissingArgumentException("修改密码原密码和新密码均不能为空！");
        }
        UserDto userDto = userManagerUtils.getCurrentUser();
        String oldPwd = new String(Base64Utils.decodeBase64StrToByte(old));
        String newPwd = new String(Base64Utils.decodeBase64StrToByte(password));
        Boolean isTrue = userManagerClient.checkUserPassword(oldPwd, userDto.getId());
        if (!Boolean.TRUE.equals(isTrue)) {
            throw new AppException("原密码错误！");
        }
        userDto.setPassword(newPwd);
        BaseResultDto baseResultDto = userManagerClient.modifyUserPassword(userDto.getUsername(), newPwd);
        return baseResultDto;
    }

    /**
     * @param old      历史密码
     * @param password 新密码
     * @return 修改密码是否成功
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 修改密码
     */
    @PutMapping("/newpassword")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("修改用户密码")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "old", value = "原密码", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "string", paramType = "query")})
    public BaseResultDto updateNewUserPwd(String old, String password) {
        if (StringUtils.isBlank(old) || StringUtils.isBlank(password)) {
            throw new MissingArgumentException("修改密码原密码和新密码均不能为空！");
        }
        UserDto userDto = userManagerUtils.getCurrentUser();
        String oldPwd = new String(RSAUtil.decrypt(old));
        String newPwd = new String(RSAUtil.decrypt(password));
        Boolean isTrue = userManagerClient.checkUserPassword(oldPwd, userDto.getId());
        if (!Boolean.TRUE.equals(isTrue)) {
            throw new AppException("原密码错误！");
        }
        userDto.setPassword(newPwd);
        BaseResultDto baseResultDto = userManagerClient.modifyUserPassword(userDto.getUsername(), newPwd);
        return baseResultDto;
    }

    /**
     * @param clientId   应用 id
     * @param moduleType moduleType 模块类型  subsystem: 子系统， classification：业务分类， menu：菜单， form：表单  element：页面元素  api：服务
     * @return 业务分类列表
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 获取应用下用户可访问的模块
     */
    @GetMapping("/modules/{clientId}/{moduleType}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取应用下用户可访问的模块")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "clientId", value = "应用 id", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "moduleType", value = "模块列表", required = true, dataType = "string", paramType = "path")})
    public List<ModuleDto> listUserModules(@PathVariable("clientId") String clientId, @PathVariable("moduleType") String moduleType) {
        if (StringUtils.isBlank(clientId) || StringUtils.isBlank(moduleType)) {
            throw new MissingArgumentException("应用 ID 和模块类型参数均不能为空！");
        }
        String username = userManagerUtils.getCurrentUserName();
        // 获取业务分类
        List<ModuleDto> list = authorityManagerClient.findClientUserModules(username, clientId, moduleType);
        if (CollectionUtils.isNotEmpty(list)) {
            for (ModuleDto dto : list) {
                if (StringUtils.isNotBlank(dto.getUrl())) {
                    // 解析 URL 如果含有参数 description 为 blank 则在新标签页中打开
                    String[] split = dto.getUrl().split("\\?");
                    if (split.length > 1 && split[1] != null && split[1].contains(BLANK)) {
                        dto.setDescription(BLANK);
                    }
                }
            }

            //排序
            list.sort(Comparator.comparingInt(ModuleDto::getSequenceNumber));
        }
        return list;
    }

    /**
     * @param code 选中模块的 code 值
     * @return 子模块列表
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 获取当前模块下级可访问模块列表
     */
    @GetMapping("/submenu/{code}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取当前模块下级可访问模块列表")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "当前模块 code 值", required = true, paramType = "path")})
    public List<ModuleDto> listSubModules(@PathVariable("code") String code) {
        if (StringUtils.isBlank(code)) {
            throw new MissingArgumentException("获取当前模块下级可访问模块列表时需传入 code 值！");
        }
        String username = userManagerUtils.getCurrentUserName();
        return authorityManagerClient.findSubUserModules(username, code,
                ModuleTypeEnum.CLASSIFICATION.value() + "," + ModuleTypeEnum.MENU.value());
    }

    /**
     * @param clientId 应用 id
     * @return 全部菜单列表
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 获取当前用户全部的可访问模块列表
     */
    @GetMapping("/menu/{clientId}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiOperation("获取当前用户全部的可访问模块列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "clientId", value = "应用 id", required = true, paramType = "path")})
    public List<BdcModuleDTO> listAllModules(@PathVariable("clientId") String clientId) {
        if (StringUtils.isBlank(clientId)) {
            throw new MissingArgumentException("获取当前用户全部的可访问模块列表, 应用 ID 不能为空！");
        }
        List<ModuleDto> moduleDtoList = listUserModules(clientId, ModuleTypeEnum.CLASSIFICATION.value() + "," + ModuleTypeEnum.MENU.value());
        List<BdcModuleDTO> menuList = new ArrayList<>();
        List<ModuleDto> restList = new ArrayList<>();
        Map<String, BdcModuleDTO> difMap = new HashMap();
        for (ModuleDto moduleDto : moduleDtoList) {
            BdcModuleDTO bdcModuleDTO = new BdcModuleDTO(moduleDto);
            if (StringUtils.equals(moduleDto.getType(), ModuleTypeEnum.CLASSIFICATION.value())) {
                menuList.add(bdcModuleDTO);
                difMap.put(bdcModuleDTO.getId(), bdcModuleDTO);
            } else {
                restList.add(moduleDto);
            }
        }
        combineMenu(restList, difMap);
        return menuList;
    }

    /**
     * @param restList 模块列表
     * @param difMap   对照
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 拼接菜单和子菜单方法
     */
    private void combineMenu(List<ModuleDto> restList, Map<String, BdcModuleDTO> difMap) {
        List<ModuleDto> restNew = new ArrayList<>();
        Map<String, BdcModuleDTO> newMap = new HashMap<>();
        MultiMap<String> values;
        for (ModuleDto dto : restList) {
            values = new MultiMap<>();
            if (StringUtils.isNotBlank(dto.getUrl())) {
                // 解析 URL 如果含有参数 description 为 blank 则在新标签页中打开
                String[] split = dto.getUrl().split("\\?");
                if (split.length > 1 && split[1] != null) {
                    UrlEncoded.decodeTo(split[1], values, "UTF-8", 1000);
                    if (StringUtils.equals(values.getValue("description", 0), BLANK)) {
                        dto.setDescription(BLANK);
                    }
                }
            }
            if (difMap.containsKey(dto.getParentId())) {
                BdcModuleDTO bdcModuleDTO = new BdcModuleDTO(dto);
                difMap.get(dto.getParentId()).getChildTree().add(bdcModuleDTO);
                newMap.put(dto.getId(), bdcModuleDTO);
            } else {
                restNew.add(dto);
            }
        }
        if (restNew.size() < restList.size()) {
            combineMenu(restNew, newMap);
        }
    }

    /**
     * 获取当前用户的办理情况信息
     *
     * @return 返回 待办、已办和超期的任务量
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/backlog")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取当前用户的办理情况信息")
    @ApiResponse(code = 200, message = "请求获取成功")
    public Object queryUserBackLog() {
        String username = userManagerUtils.getCurrentUserName();
        Integer todoTaskCount = processTaskClient.todoTaskCount(username);
        /**
         * 获取当前月的完成已办工作
         */
        Long starttime = getFirstDayByType("month");
        Long endtime = System.currentTimeMillis();
        Integer completeTaskCount = processTaskClient.completeTaskCount(username, starttime, endtime);
        Integer timeOutTaskCount = processTaskClient.timeOutTaskCount(username);
        Map result = new HashMap(3);
        result.put("todo", todoTaskCount);
        result.put("complete", completeTaskCount);
        result.put("timeout", timeOutTaskCount);
        if (zxywbll) {
            Long specialTaskCount = querySpecialTask();
            result.put("special", specialTaskCount);
        }
        return result;
    }

    /**
     * 统计个人项目列表中专项业务办理量
     *
     * @return
     * @author <a href="mailto:hongqin@gtmap.cn">hongqin</a>
     */
    public Long querySpecialTask() {
        List<RequestCondition> requestConditions = new ArrayList<>();
        // 解析lcid并封装
        List<String> params = Arrays.asList(lcid.split(MULTIPLE_PARAM_SPLITTER));
        Set<String> set = new HashSet<>(params);
        RequestCondition condition1 = new RequestCondition();
        condition1.setRequestValue(set);
        condition1.setRequestKey("processKey");
        condition1.setRequestJudge("in");
        requestConditions.add(condition1);

        Page<Map<String, Object>> todoTaskPage;
        todoTaskPage = processTaskCustomExtendClient.todoTaskExtendList(requestConditions, 1, 0);
        return todoTaskPage.getTotalElements();
    }

    /**
     * 获取 x 年、月、周、日之前的时间
     *
     * @param dateType 年月周日
     * @return Long x 年、月、周、日之前的时间
     */
    private Long getFirstDayByType(String dateType) {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        // 清空时间
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MINUTE);

        switch (dateType) {
            case "year":
                calendar.add(Calendar.YEAR, -12);
                break;
            case "month":
                calendar.add(Calendar.MONTH, -12);
                break;
            case "week":
                calendar.add(Calendar.MONTH, -3);
                break;
            case "day":
                calendar.add(Calendar.DATE, -12);
                break;
            default:
                throw new MissingArgumentException("日期类型不是规范类型！");
        }
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当前用户的工作信息
     *
     * @param dateType 查询时间类型 year，month,week,day
     * @return 返回当前用户的工作信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/workdata")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取当前用户的工作信息")
    @ApiResponse(code = 200, message = "请求获取成功")
    @ApiImplicitParams({@ApiImplicitParam(name = "dateType", value = "日期类型", required = true, paramType = "query")})
    public Object queryUserWorkData(@RequestParam("dateType") String dateType) {
        if (StringUtils.isBlank(dateType)) {
            throw new MissingArgumentException("日期类型不能为空！");
        }
        long begin = getFirstDayByType(dateType);
        long end = System.currentTimeMillis();
        String username = userManagerUtils.getCurrentUserName();
        List<UserTaskCountDto> completeTasks = taskStatisticsClient.queryUserTaskCount(username, 4, begin, end, dateType);
        List<UserTaskCountDto> allTasks = taskStatisticsClient.queryUserTaskCount(username, null, begin, end, dateType);
        Map result = new HashMap(2);
        result.put("complete", completeTasks);
        result.put("all", allTasks);
        return result;
    }

    /**
     * 获取当前用户的操作日志信息
     *
     * @param pageable 分页数据
     * @return 返回用户操作日志信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/loginfo")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取当前用户的操作日志信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @ApiResponse(code = 200, message = "请求获取成功")
    public Object listUserLogInfo(@LayuiPageable Pageable pageable) {
        String username = userManagerUtils.getCurrentUserName();
        Page<AuditLogDto> page = logMessageClient.listLogs(pageable.getPageNumber(), pageable.getPageSize(), "LOGIN_SUCCESS,LOGOUT_SUCCESS", username, "platform", null, null, Lists.emptyList());
        return dealAuditLogDto(page.getContent());
    }

    /**
     * 处理 大云平台 返回的日志信息
     *
     * @param content 日志信息列表
     * @return {List} 处理过的数据
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private List dealAuditLogDto(List<AuditLogDto> content) {
        List<Map> list = Lists.newArrayList();
        // 对照 map 存储内容为 <时间，index>
        Map<Date, Integer> compare = Maps.newHashMap();
        for (AuditLogDto auditLog : content) {
            // 页面只展示 登录成功 和 登出成功
            if (StringUtils.equals(auditLog.getEvent(), "LOGIN_SUCCESS") || StringUtils.equals(auditLog.getEvent(), "LOGOUT_SUCCESS")) {
                // 1. 处理日期，去除时间的 时分秒 用于分组
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(auditLog.getTimestamp_millis());
                calendar.clear(Calendar.SECOND);
                calendar.clear(Calendar.MINUTE);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                Date time = calendar.getTime();

                List details;
                Map log;
                // 2. 判断在对照 map 中是否存在
                if (compare.get(time) == null) {
                    compare.put(time, list.size());
                    log = Maps.newHashMap();
                    log.put("time", time);
                    log.put("principal", auditLog.getPrincipal());
                    details = Lists.newArrayList();
                    // 解析数据
                    dealWithData(auditLog, details, log);
                    list.add(log);
                } else {
                    // 存在则在 details 中添加即可
                    log = list.get(compare.get(time));
                    details = (List) log.get(Constants.DETAILS);
                    dealWithData(auditLog, details, log);
                }
            }
        }
        return list;
    }

    /**
     * 处理日志信息中的 BinaryAnnotations 数据
     *
     * @param auditLog 大云的日志类
     * @param details  同一天的日志详情
     * @param log      当天的日志外层 map
     * @return
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private void dealWithData(AuditLogDto auditLog, List details, Map log) {
        Map detail = Maps.newHashMap();
        detail.put("event", auditLog.getEvent());
        detail.put("time", auditLog.getTimestamp_millis());
        List<DataValue> dataValueList = auditLog.getBinaryAnnotations();
        if (CollectionUtils.isNotEmpty(dataValueList)) {
            // 遍历 dataValueList 中的数据
            for (DataValue dataValue : dataValueList) {
                String key = dataValue.getKey().toLowerCase();
                switch (key) {
                    case Constants.REMOTEADDR:
                        detail.put(Constants.REMOTEADDR, dataValue.getValue());
                        break;
                    case Constants.DETAILS:
                        JSONObject jsonDetails = JSON.parseObject(dataValue.getValue());
                        if (detail.get(Constants.REMOTEADDR) == null && jsonDetails.get("remoteAddress") != null) {
                            detail.put(Constants.REMOTEADDR, jsonDetails.get("remoteAddress"));
                        }
                        break;
                    default:
                }
            }
            // 将 log 添加到同一天的日志集合
            details.add(detail);
        }
        log.put(Constants.DETAILS, details);
    }

    /**
     * 保存待办工作项（保存消息）
     *
     * @param produceMsgDto 生产消息 DTO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/workmsg")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存待办工作项")
    @ApiImplicitParams({@ApiImplicitParam(name = "produceMsgDto", value = "待办工作项", dataType = "string", paramType = "query")})
    @ApiResponse(code = 200, message = "请求获取成功")
    public void saveWorkMsg(@RequestBody ProduceMsgDto produceMsgDto) {
        produceMsgDto.setMsgCode("WORKMSG");
        produceMsgDto.setMsgType("WORK");
        produceMsgDto.setConsumer(userManagerUtils.getCurrentUserName());
        produceMsgDto.setConsumerType("personal");
        produceMsgDto.setProducer(userManagerUtils.getCurrentUserName());
        produceMsgDto.setProducerType("personal");
        messageClient.saveProduceMsg(produceMsgDto);
    }

    /**
     * 获取当前用户的待办工作事项
     *
     * @param pageable 分页对象
     * @return {Page<MessageDto>} 分页信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/workmsg")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取当前用户的待办工作事项")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @ApiResponse(code = 200, message = "请求获取成功")
    public Object listUserWorkMsg(@LayuiPageable Pageable pageable) {
        String username = userManagerUtils.getCurrentUserName();
        Page<MessageDto> messageDtos = messageClient.pageMessage(pageable, null, "WORKMSG", "WORK", username, username, "", 0);
        return addLayUiCode(messageDtos);
    }

    /**
     * 根据 ID 查询待办工作事项
     *
     * @param id 日志 id
     * @return {MessageDto} 消息 DTO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/workmsg/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据 ID 查询待办工作事项")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", dataType = "string", paramType = "path")})
    @ApiResponse(code = 200, message = "请求获取成功")
    public Object queryMessageById(@PathVariable("id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new MissingArgumentException("缺失必要参数 ID");
        }
        return messageClient.queryMessageById(id);
    }

    /**
     * 根据 ID 设置待办任务为已办
     *
     * @param id 日志 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/workmsg/read/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据 ID 设置待办任务为已办")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", dataType = "string", paramType = "path")})
    @ApiResponse(code = 200, message = "请求获取成功")
    public void readMessages(@PathVariable("id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new MissingArgumentException("缺失必要参数 ID");
        }
        List<String> ids = Collections.singletonList(id);
        messageClient.readMessages(userManagerUtils.getCurrentUserName(), ids);
    }

    /**
     * 获取到查封到期信息
     *
     * @param pageable
     */
    @GetMapping("/cfdq")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取到查封到期信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @ApiResponse(code = 200, message = "请求获取成功")
    public Object listCfdq(@LayuiPageable Pageable pageable) {
        return bdcCfxxFeignService.bdcCfDqList(pageable, "");
    }

    /**
     * 获取到台账 tab 页顺序
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/order")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取台账 tab 页顺序")
    @ApiResponse(code = 200, message = "请求获取成功")
    public List<String> queryPortalOrder() {
        // 只有配置显示 xmContent xmHideCodes 才会生效
        if (order.contains("xmContent") && CollectionUtils.isNotEmpty(xmHideCodes)) {
            UserDto currentUser = userManagerUtils.getCurrentUser();
            // 管理员不做限制处理
            if (currentUser.getAdmin() == 1) {
                return order;
            }
            List<String> roleRecordList = currentUser.getRoleRecordList().stream()
                    .map(RoleDto::getName).collect(Collectors.toList());
            // 页面上显示的编码对应字段中的 name
            for (String role : xmHideCodes) {
                if (roleRecordList.contains(role)) {
                    return order.stream().filter(content -> !StringUtils.equals(content, "xmContent")).collect(Collectors.toList());
                }
            }
        }
        return order;
    }

    /**
     * 是否加载首页
     *
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @GetMapping("/loadHome")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("是否加载首页")
    @ApiResponse(code = 200, message = "请求获取成功")
    public boolean loadHome() {
        boolean loadHome = true;
        UserDto currentUser = userManagerUtils.getCurrentUser();
        if (currentUser != null && hideUser.contains(currentUser.getUsername())) {
            loadHome = false;
        }
        return loadHome;
    }

    /**
     * 获取到附件下载 url
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/xtkj")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取到系统控件下载 url")
    @ApiResponse(code = 200, message = "请求获取成功")
    public String queryXtkjUrl() {
        return xtkjUrl;
    }

    /**
     * 获取叫号软件服务器 url
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/callnumber")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取叫号软件服务器 url")
    @ApiResponse(code = 200, message = "请求获取成功")
    public String queryCallNumberUrl() {
        return callnumber;
    }

    /**
     * 获取评价器 url
     *
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/pjq")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取评价器url")
    @ApiResponse(code = 200, message = "请求获取成功")
    public String queryPjqUrl() {
        return pjqUrl;
    }

    /**
     * 判断是否属于补打角色
     * 是：返回 {String} true
     * 否：返回 {String} false
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/isbd")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("判断是否属于补打角色")
    @ApiResponse(code = 200, message = "请求获取成功")
    public Object queryIsbd() {
        if (StringUtils.isBlank(roleCode)) {
            throw new MissingArgumentException("未配置补打角色");
        }
        List<String> roleList = Arrays.asList(roleCode.split(","));
        UserDto currentUser = userManagerUtils.getCurrentUser();
        List<RoleDto> roleRecordList = currentUser.getRoleRecordList();
        // 页面上显示的编码对应字段中的 name
        for (RoleDto roleDto : roleRecordList) {
            if (roleList.contains(roleDto.getName())) {
                return currentUser;
            }
        }
        return null;
    }

    /**
     * 获取所有的启用角色
     *
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @GetMapping("/queryEnabledRoles")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取所有的启用角色")
    @ApiResponse(code = 200, message = "请求获取成功")
    public List<RoleDto> queryEnabledRoles() {
        return roleManagerClient.getEnabledRoleList();
    }

    /**
     * 根据角色id获取用户集合
     *
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @GetMapping("/queryUsersByRoles")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取所有的启用角色")
    @ApiResponse(code = 200, message = "请求获取成功")
    public List<UserDto> queryUsersByRoles(@RequestParam(value = "roles", required = false) List<String> roleids, @RequestParam(value = "deptids", required = false) List<String> deptids) {
        List<UserDto> result = new ArrayList<>();

        //第一种情况：如果角色id和部门id都不为空，则加载对应的用户，取出共有用户
        if (CollectionUtils.isNotEmpty(roleids) && CollectionUtils.isNotEmpty(deptids)) {
            //根据角色获取用户
            List<UserDto> resultRoles = queryUsersByRoleIds(roleids);
            if (CollectionUtils.isEmpty(resultRoles)) {
                resultRoles = new ArrayList<>(5);
            }
            //根据部门获取用户
            List<UserDto> resultDepts = queryUsersByDeptIds(deptids);
            //取出共有用户
            result = resultRoles.stream().filter((item) -> resultDepts.stream().map((item2) -> item2.getId()).collect(Collectors.toList()).contains(item.getId())).collect(Collectors.toList());
        }
        //第二种情况：如果角色id不为空但部门为空，加载对应角色的与用户信息
        if (CollectionUtils.isNotEmpty(roleids) && CollectionUtils.isEmpty(deptids)) {
            result = queryUsersByRoleIds(roleids);
        }

        //第三种情况：如角色为空但部门id不为空，加载对应部门下面的用户信息
        if (CollectionUtils.isEmpty(roleids) && CollectionUtils.isNotEmpty(deptids)) {
            result = queryUsersByDeptIds(deptids);
        }

        return result;
    }

    /**
     * @param roleids
     * @description 根据角色id查询用户
     * @return: java.util.List<cn.gtmap.gtc.sso.domain.dto.UserDto>
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @date 2022/10/27 20:24
     */
    private List<UserDto> queryUsersByRoleIds(List<String> roleids) {
        List<UserDto> result = new ArrayList<>();
        List<String> userName = new ArrayList<>();
        List<RoleUserDto> list = roleManagerClient.listRoleUsers(roleids);
        if (CollectionUtils.isNotEmpty(list)) {
            for (RoleUserDto roleUserDto : list) {
                if (CollectionUtils.isNotEmpty(roleUserDto.getUserDtos())) {
                    for (UserDto userDto : roleUserDto.getUserDtos()) {
                        //不包含做处理
                        if (!userName.contains(userDto.getUsername())) {
                            result.add(userDto);
                            userName.add(userDto.getUsername());
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * @param deptids
     * @description 根据部门id查询用户信息
     * @return: java.util.List<cn.gtmap.gtc.sso.domain.dto.UserDto>
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @date 2022/10/27 20:26
     */
    private List<UserDto> queryUsersByDeptIds(List<String> deptids) {
        List<UserDto> result = new ArrayList<>();
        for (String orgId : deptids) {
            List<UserDto> users = organizationManagerClient.listUsersByOrg(orgId);
            if (CollectionUtils.isNotEmpty(users)) {
                result.addAll(users);
            }
        }
        if (CollectionUtils.isNotEmpty(result)) {
            //对用户进行去重
            result = result.stream().
                    collect(Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(UserDto::getId))), ArrayList::new));
        }
        return result;
    }

    /**
     * 获取大云配置工作日
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * <p>
     * 休息日中非周末的以及节假日均显示「休」
     * 工作日中周末显示「班」
     */
    @GetMapping("/queryWorkDays")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取所有的启用角色")
    @ApiResponse(code = 200, message = "请求获取成功")
    public WorkDayVO queryWorkDays(@RequestParam("startDate") String startDate,
                                   @RequestParam("endDate") String endDate) throws Exception {
        List<Work> works = workDayClient.getWorks();
        if (CollectionUtils.isEmpty(works) && works.get(0) == null) {
            throw new AppException("未能获取工作日的设置列表");
        }
        List<WorkDay> workDays = workDayClient.getWorkDays(works.get(0).getId(), startDate, endDate);
        WorkDayVO workDayVO = new WorkDayVO();
        List<String> workList = new ArrayList<>(workDays.size());
        List<String> holidayList = new ArrayList<>(workDays.size());
        for (WorkDay workDay : workDays) {
            switch (workDay.getDayType()) {
                case WORKDAY:
                    if (DateUtilForWorkDay.isWeekends(workDay.getWorkDay())) {
                        workList.add(workDay.getWorkDay());
                    }
                    break;
                case OFFDAY:
                    if (!DateUtilForWorkDay.isWeekends(workDay.getWorkDay())) {
                        holidayList.add(workDay.getWorkDay());
                    }
                    break;
                case HOLIDAY:
                    holidayList.add(workDay.getWorkDay());
                    break;
            }
        }
        workDayVO.setWorkList(workList);
        workDayVO.setHolidayList(holidayList);
        return workDayVO;
    }

    /**
     * 判断用户是否可以限制打开待办任务列表<br>
     * 当前用户非管理员，并且系统配置了限制非管理员只允许打开首条待办任务
     *
     * @return {boolean} true：表示存在限制，false：不存在显示
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/todo/permissions")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("判断用户是否可以限制打开待办任务列表")
    public Object queryUserToDOPermissions() {
        UserDto currentUser = userManagerUtils.getCurrentUser();
        return currentUser.getAdmin() != 1 && StringUtils.equals(todoPermission, "1");
    }

    /**
     * 判断当前用户是否管理员
     *
     * @return {boolean} true：管理员，false：非管理员
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     */
    @GetMapping("/admin")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("判断用户是否可以限制打开待办任务列表")
    public Object isAdmin() {
        UserDto currentUser = userManagerUtils.getCurrentUser();
        return currentUser.getAdmin() == 1;
    }

    /**
     * 获取到自然资源台账 tab 页顺序
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/zrzy/order")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取自然资源台账 tab 页顺序")
    @ApiResponse(code = 200, message = "请求获取成功")
    public List<String> queryPortalZrzyOrder() {
        return zrzyOrder;
    }
}
