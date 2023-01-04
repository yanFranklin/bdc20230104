package cn.gtmap.realestate.portal.ui.web.rest;

import cn.gtmap.gtc.clients.ElementClient;
import cn.gtmap.gtc.clients.OauthManagerClient;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.common.BaseResult;
import cn.gtmap.gtc.workflow.domain.common.ProcessOptResultDto;
import cn.gtmap.gtc.workflow.domain.manage.BackNodesDto;
import cn.gtmap.gtc.workflow.domain.manage.BackTaskDto;
import cn.gtmap.gtc.workflow.domain.manage.ForwardTaskDto;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.gtc.workflow.enums.task.CommentType;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmDO;
import cn.gtmap.realestate.common.core.enums.HlwShztEnum;
import cn.gtmap.realestate.common.core.enums.HlwSlztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.natural.ZrzyXmFeignService;
import cn.gtmap.realestate.common.matcher.FlowableNodeClientMatcher;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.matcher.SyncAutoCmpleteClientMatcher;
import cn.gtmap.realestate.common.matcher.TaskHandleClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.TaskUtils;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import cn.gtmap.realestate.portal.ui.core.ForwardRuleFactory;
import cn.gtmap.realestate.portal.ui.core.dto.BdcPlBackDTO;
import cn.gtmap.realestate.portal.ui.core.dto.ForwardPLDTO;
import cn.gtmap.realestate.portal.ui.core.dto.ForwardYzDTO;
import cn.gtmap.realestate.portal.ui.core.dto.WorkFlowDTO;
import cn.gtmap.realestate.portal.ui.core.vo.WorkFlowVO;
import cn.gtmap.realestate.portal.ui.service.BdcForwardService;
import cn.gtmap.realestate.portal.ui.service.ProcessService;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowAbstactService;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowServiceFactory;
import cn.gtmap.realestate.portal.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/2/26.
 * @description 流程操作服务接口 转发、退回、挂起、激活、删除 、取回、认领
 */
@RestController
@RequestMapping("/rest/v1.0/workflow/zrzy/process-instances")
@Api(tags = "自然资源流程操作服务接口")
public class ZrzyWorkFlowController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZrzyWorkFlowController.class);
    @Autowired
    private ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    private TaskHandleClientMatcher taskHandleClient;
    @Autowired
    private FlowableNodeClientMatcher flowableNodeClient;
    @Autowired
    private OauthManagerClient oauthManagerClient;
    @Autowired
    private ProcessTaskClient processTaskClient;
    @Autowired
    private WorkFlowUtils workFlowUtils;
    @Autowired
    private TaskUtils taskUtils;
    @Autowired
    private ElementClient elementCient;
    @Autowired
    ForwardRuleFactory forwardRuleFactory;
    @Autowired
    ZrzyXmFeignService zrzyXmFeignService;
    /**
     * 流程任务服务
     */
    @Autowired
    private ProcessService processService;

    @Autowired
    BdcForwardService bdcForwardService;

    /**
     * 处理流程自动办结出现的问题
     */
    @Autowired
    private SyncAutoCmpleteClientMatcher syncAutoCmpleteClient;
    @Value("${security.oauth2.client.client-id}")
    private String portalUiClientId;
    /**
     * 不动产登记模块代码
     */
    @Value("${module.code.bdcdj}")
    private String moduleCodeBdcdj;
    /**
     * 不动产登记模块代码
     */
    @Value("${module.code.client:}")
    private String moduleClient;
    /**
     * 角色分组标识,默认筛选人员同组织下的角色
     */
    @Value("${forward.group:true}")
    private String forwardGroup;
    /**
     * 退回意见必填check
     */
    @Value("${check.thyjcheck}")
    private String thyjCheck;

    /**
     * 转发自动签名配置
     */
    @Value("${forward.zfzdqm:0}")
    private String zfzdqm;
    /**
     * 指定节点转发自动签名配置
     */
    @Value("${forward.zfzdqmjd: }")
    private String zfzdqmjd;
    /**
     * 转发签名显示配置
     */
    @Value("${forward.zfqmxs:1}")
    private String zfqmxs;
    /**
     * 删除显示原因配置
     */
    @Value("${check.deletereason:0}")
    private String deletereason;
    /**
     * 非首节点可以删除
     */
    @Value("${fsjd.del:false}")
    private boolean fsjdDel;
    /**
     * 非首节点可以删除的受理编号规则
     */
    @Value("#{'${fsjd.tspz.slbh_startwith:}'.split(',')}")
    private List<String> fsjdtspz;

    /**
     * 删除方法是否先执行登记删除方法(true|flase,默认 true)
     */
    @Value("${delete.cloud:true}")
    private boolean deleteCloud;

    @Value("${html.version:}")
    private String version;

    // 是否验证初审和二审的审核人是否一致 true；要验证，false或者不配置，不验证
    @Value("${checkHasSameShr:false}")
    private String checkHasSameShr;

    /**
     * 受理时间以受理人认领时间为准,首节点认领任务时更新受理时间,取消认领清空受理时间
     */
    @Value("${sjd.rl.slsj:false}")
    private Boolean sjdrlslsj;


    /**
     * @param workFlowDTO
     * @return TaskData
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 创建流程
     */
    @PutMapping
    @ApiOperation(value = "创建流程")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "workFlowDTO", value = "工作流参数", required = true, dataType = "WorkFlowDTO")})
    @ResponseStatus(HttpStatus.CREATED)
    public TaskData startUpProcess(@RequestBody WorkFlowDTO workFlowDTO) {
        if (StringUtils.isBlank(workFlowDTO.getProcessDefKey())) {
            throw new MissingArgumentException("流程定义id不能为空！");
        }
        String userName = workFlowDTO.getUserName();
        if (StringUtils.isBlank(userName)) {
            userName = userManagerUtils.getCurrentUserName();
        }
        return processInstanceClient.directStartProcessInstance(workFlowDTO.getProcessDefKey(), userName, workFlowDTO.getProcessInstanceName(), null, null);
    }

    @PostMapping(value = "/stopTask")
    @ApiOperation(value = "终止当前任务")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "workFlowDTOList", value = "流程任务、工作流实例id", paramType = "body", dataType = "WorkFlowDTO")})
    @ResponseStatus(HttpStatus.OK)
    public void stopTask(@RequestBody List<WorkFlowDTO> workFlowDTOList) {
        if (CollectionUtils.isEmpty(workFlowDTOList)) {
            throw new MissingArgumentException("删除当前任务taskId和gzlslid不能为空！");
        }
        String slzt = HlwSlztEnum.TERMINATE.getSlzt();
        StringBuilder builder = new StringBuilder();
        for (WorkFlowDTO workFlowDTO : workFlowDTOList) {
            if (StringUtils.isNotBlank(workFlowDTO.getProcessInstanceId())) {
                //非首节点是否可以删除
                boolean sjd = checkSjdDel(workFlowDTO.getProcessInstanceId());
                LOGGER.info("当前流程是否检查是首节点：{}, gzlslid:{}", sjd, workFlowDTO.getProcessInstanceId());
                if (sjd) {//是否是首节点
                    //是否是首节点
                    boolean issjd = flowableNodeClient.isStartUserTaskRunning(workFlowDTO.getProcessInstanceId());
                    if (!issjd) {
                        builderLogStr(builder, workFlowDTO, "非受理节点无法删除");
                        continue;
                    }
                }
                if (deleteCloud) {
                    try {
                        zrzyXmFeignService.deleteZrzyXm(workFlowDTO.getProcessInstanceId());
                    } catch (Exception e) {
                        LOGGER.error("请求 init 删除接口异常:{}", workFlowDTO.getProcessInstanceId(), e);
                        builderLogStr(builder, workFlowDTO, "删除失败");
                        continue;
                    }
                }
                LOGGER.warn("portal deleteCloud：{}， 删除数据:{}", deleteCloud, JSON.toJSONString(workFlowDTO));
                taskUtils.deleteTask(workFlowDTO.getProcessInstanceId(), workFlowDTO.getReason());
            }
        }

        //存在删除失败的流程抛出异常
        if (StringUtils.isNotBlank(builder.toString())) {
            throw new AppException(builder.toString());
        }
    }

    /**
     * @param workFlowDTOList
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除当前任务
     */
    @DeleteMapping
    @ApiOperation(value = "删除当前任务")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "workFlowDTOList", value = "流程任务、工作流实例id", paramType = "body", dataType = "WorkFlowDTO")})
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@RequestBody List<WorkFlowDTO> workFlowDTOList) {
        if (CollectionUtils.isEmpty(workFlowDTOList)) {
            throw new MissingArgumentException("删除当前任务taskId和gzlslid不能为空！");
        }
        this.deleteOrStopTask(workFlowDTOList, HlwSlztEnum.DELETE.getSlzt());
    }

    /**
     * 判断节点是否需要判断是首节点
     *
     * @param gzlslid gzlslid
     * @return boolean 是否需要判断是首节点
     * @author <a href="mailto:foxfocus@163.com">fox</a>
     */
    private boolean checkSjdDel(String gzlslid) {
        if (fsjdDel) {
            return false;
        }
        if (CollectionUtils.isNotEmpty(fsjdtspz) && StringUtils.isNotBlank(fsjdtspz.get(0))) {
            List<ZrzyXmDO> zrzyXmDOList = zrzyXmFeignService.listZrzyXmByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(zrzyXmDOList) && null != zrzyXmDOList.get(0)) {
                String slbh = zrzyXmDOList.get(0).getSlbh();
                for (String s : fsjdtspz) {
                    return !StringUtils.startsWith(slbh, s);
                }
            }
        }
        return true;
    }

    /**
     * slzt 用于控制任务删除还是终止
     */
    private void deleteOrStopTask(List<WorkFlowDTO> workFlowDTOList, String slzt) {
        StringBuilder builder = new StringBuilder();
        for (WorkFlowDTO workFlowDTO : workFlowDTOList) {
            if (StringUtils.isNotBlank(workFlowDTO.getProcessInstanceId())) {
                //非首节点是否可以删除
                boolean sjd = checkSjdDel(workFlowDTO.getProcessInstanceId());
                LOGGER.info("当前流程是否检查是首节点：{}, gzlslid:{}", sjd, workFlowDTO.getProcessInstanceId());
                if (sjd) {
                    //是否是首节点
                    boolean issjd = flowableNodeClient.isStartUserTaskRunning(workFlowDTO.getProcessInstanceId());
                    if (!issjd) {
                        builderLogStr(builder, workFlowDTO, "非受理节点无法删除");
                        continue;
                    }
                }
                taskUtils.deleteTask(workFlowDTO.getProcessInstanceId(), workFlowDTO.getReason());
                // 成功 删除受理信息
                zrzyXmFeignService.deleteZrzyXm(workFlowDTO.getProcessInstanceId());
            } else {
                throw new MissingArgumentException("工作流实例ID不能为空!");
            }
        }
        //存在删除失败的流程抛出异常
        if (StringUtils.isNotBlank(builder.toString())) {
            throw new AppException(builder.toString());
        }
    }



    /**
     * 构造删除的日志信息
     *
     * @param builder     stringbuilder
     * @param workFlowDTO 删除的DTO
     * @param msg         删除文本内容
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private void builderLogStr(StringBuilder builder, WorkFlowDTO workFlowDTO, String msg) {
        if (StringUtils.isNotBlank(builder.toString())) {
            builder.append("\n");
        }
        builder.append(msg);
        if (StringUtils.isNotBlank(workFlowDTO.getSlbh())) {
            builder.append(",受理编号为").append(workFlowDTO.getSlbh());
        }
    }


    /**
     * @param taskId 任务id
     * @return boolean
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 流程办结
     */
    @PostMapping("/end")
    @ApiOperation(value = "流程办结")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "forwardTaskDto", value = "转发流程任务", required = true, dataType = "ForwardTaskDto")})
    @ResponseStatus(HttpStatus.OK)
    public void processEnd(@RequestParam(value = "taskId") String taskId) {
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("办结流程的taskId不能为空！");
        }
        ForwardTaskDto forwardTaskDto = new ForwardTaskDto();
        forwardTaskDto.setTaskId(taskId);

        Map<String, Boolean> isEnd = flowableNodeClient.isForwardEndOrForceClosedEvent(taskId);
        if (isEnd.get("isForceEnd")) {
            // 判断是否是强制办结
            TaskData taskData = processTaskClient.getTaskById(taskId);
            LOGGER.error("强制办结：{}", JSONObject.toJSONString(taskData));
            taskHandleClient.mandatoryFinishProcess(taskData.getProcessInstanceId(), userManagerUtils.getCurrentUserName());
        } else {
            LOGGER.error("办结：{}", JSONObject.toJSONString(forwardTaskDto));
            taskHandleClient.processEnd(forwardTaskDto);
        }
    }

    /**
     * @param taskId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 办结转为工作流事件处理
     * @date : 2021/8/25 9:34
     */
    @GetMapping("/end/gzlsj")
    @ApiOperation(value = "流程办结工作流事件")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String")})
    @ResponseStatus(HttpStatus.OK)
    public void processEndCz(@RequestParam(value = "taskId") String taskId) {
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("办结流程的taskId不能为空！");
        }
        // 执行办结前特殊处理
        BdcWorkFlowAbstactService workFlowService = BdcWorkFlowServiceFactory.getWorkFlowService(version);
        if (workFlowService != null) {
            workFlowService.processEnd(taskId);
        }
    }


    /**
     * @param taskId
     * @return taskNodeType
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取任务节点类型
     */
    @GetMapping("/taskNodeType")
    @ApiOperation(value = "获取任务节点类型")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "taskId", value = "流程任务id", paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public String queryTaskNodeType(@RequestParam(value = "taskId") String taskId) {
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("获取任务节点类型taskId不能为空！");
        }
        return flowableNodeClient.getForwardNodeType(taskId);
    }

    /**退回相关接口*/

    /**
     * @param backTaskDtos taskid activityId opinion为必填项
     * @return boolean
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 退回任务流程
     */
    @PostMapping("/back")
    @ApiOperation(value = "退回任务流程")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "backTaskDtos", value = "taskid activityId opinion为必填项", required = true, dataType = "BackTaskDto")})
    @ResponseStatus(HttpStatus.OK)
    public void backTask(@RequestBody List<BackTaskDto> backTaskDtos) {
        if (CollectionUtils.isEmpty(backTaskDtos)) {
            throw new MissingArgumentException("转发任务信息不能为空！");
        }
        taskHandleClient.back(backTaskDtos);
    }



    /**
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 批量退回任务流程
     */
    @PostMapping("/back/pl")
    @ApiOperation(value = "批量退回任务流程")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskIds", value = "taskIds", required = true, dataType = "list", paramType = "body"),
            @ApiImplicitParam(name = "opinion", value = "opinion", required = true, dataType = "string", paramType = "query")
    })
    @ResponseStatus(HttpStatus.OK)
    public Object batchBackTask(@RequestBody BdcPlBackDTO bdcPlBackDTO) {
        List<WorkFlowDTO> workFlowDTOList = bdcPlBackDTO.getWorkFlowDTOList();
        String opinion = bdcPlBackDTO.getOpinion();
        if (CollectionUtils.isEmpty(workFlowDTOList)) {
            throw new MissingArgumentException("未传入有效参数");
        }
        List<String> taskIds = Lists.newArrayListWithCapacity(workFlowDTOList.size());
        ForwardPLDTO forwardPLDTO = new ForwardPLDTO();
        List<ForwardYzDTO> forwardYzDTOS = Lists.newArrayListWithCapacity(workFlowDTOList.size());
        HashMap<String, WorkFlowDTO> dealDatas = new HashMap<>();
        for (WorkFlowDTO workFlowDTO : workFlowDTOList) {
            ForwardYzDTO forwardYzDTO = new ForwardYzDTO(workFlowDTO.getSlbh());
            // 直接将转发验证信息加入集合（避免代码重复漏写）
            forwardYzDTOS.add(forwardYzDTO);
            // 转发规则验证
            boolean yzResult = plThYzgz(workFlowDTO, forwardYzDTO);
            if (!yzResult) {
                forwardPLDTO.setGzyzMsg(yzResult);
            } else {
                taskIds.add(workFlowDTO.getTaskId());
                dealDatas.put(workFlowDTO.getTaskId(), workFlowDTO);
            }
        }

        if (CollectionUtils.isNotEmpty(taskIds)) {
            LOGGER.warn("批量退回的 taskId 为：{}", taskIds);
            List<ProcessOptResultDto> resultDtos = taskHandleClient.batchBack(taskIds, opinion);
            int success = 0;
            // 退回失败消息
            StringBuilder message = new StringBuilder();
            for (ProcessOptResultDto resultDto : resultDtos) {
                // 取消成功
                if (resultDto.getCode() == 0) {
                    if (dealDatas.get(resultDto.getTaskId()) != null) {
                        success++;
                    }
                } else {
                    // 大云返回值包含异常信息，只向前端返回不允许退回消息
                    message.append(dealDatas.get(resultDto.getTaskId()).getSlbh())
                            .append("不允许退回".equals(resultDto.getMessage()) ? resultDto.getMessage() : "退回失败")
                            .append("</br>");
                }
            }
            forwardPLDTO.setSuccessMsg(success != 0 ? "退回成功：" + success + "条" : "");
            // 设置错误信息
            int fail = resultDtos.size() - success;
            forwardPLDTO.setFailMsg(fail != 0 ? "退回失败：" + fail + "条" : "");
            forwardPLDTO.setMessage(message.toString());
        } else {
            forwardPLDTO.setFailMsg("退回失败：" + workFlowDTOList.size() + "条");
        }

        // 组装规则信息
        forwardPLDTO.setForwardYzDTOS(forwardYzDTOS);

        return forwardPLDTO;
    }

    /**
     * @param backNodesDto 首节点和上一节点至少传递一个，暂时不支持中间节点
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 退回任务流程「支持退回头结点」
     */
    @PostMapping("/back/general")
    @ApiOperation(value = "退回任务流程（支持退回头节点）")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "backNodesDto", value = "首节点和上一节点至少传递一个，暂时不支持中间节点", required = true, dataType = "BackNodesDto")})
    @ResponseStatus(HttpStatus.OK)
    public void backTask(@RequestBody BackNodesDto backNodesDto) {
        if (null == backNodesDto) {
            throw new MissingArgumentException("转发任务信息不能为空！");
        }
        taskHandleClient.backForGeneral(backNodesDto);
    }

    /**
     * @param taskId 任务id
     * @return BaseResult  code 0: 成功， 1：不允许 , msg :不允许退回的原因
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 判断是否可以退回
     */
    @GetMapping("/back/isAllow")
    @ApiOperation(value = "判断是否可以退回")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "taskId", value = "流程任务id", paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public BaseResult allowBack(@RequestParam(value = "taskId") String taskId) {
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("判断是否可以退回的taskId不能为空！");
        }
        return flowableNodeClient.isAllowBack(taskId);
    }

    /**
     * @param taskId 任务id
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取退回节点列表
     */
    @GetMapping("/back/userTasks")
    @ApiOperation(value = "获取退回节点列表")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "taskId", value = "流程任务id", paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public List<BackTaskDto> listBackUserTasks(@RequestParam(value = "taskId") String taskId) {
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("获取退回节点列表的taskId不能为空！");
        }
        return flowableNodeClient.getBackUserTasks(taskId);
    }


    /**
     * @param workFlowDTOList
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 认领任务
     */
    @PostMapping("/task/claim")
    @ApiOperation(value = "认领任务")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code =
            500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "workFlowDTOList", value = "流程任务、工作流实例id", required = true, dataType = "List<WorkFlowDTO>")
    })
    @ResponseStatus(HttpStatus.OK)
    public void claimTask(@RequestBody List<WorkFlowDTO> workFlowDTOList) {
        if (CollectionUtils.isEmpty(workFlowDTOList)) {
            throw new MissingArgumentException("需要认领的任务ID不能为空！");
        }
        // taskids
        List<String> taskIds = workFlowDTOList.stream().filter(workFlowDTO -> StringUtils.isNotBlank(workFlowDTO.getTaskId())).map(WorkFlowDTO::getTaskId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(taskIds)) {
            throw new MissingArgumentException("需要认领的任务ID不能为空！");
        }
        String userName = userManagerUtils.getCurrentUserName();
        boolean result = taskHandleClient.taskClaim(userName, taskIds);
        //领取成功
        if (result) {
            UserDto userDto = userManagerUtils.getCurrentUser();
            if (userDto != null) {
                //获取用户信息
                JSONObject obj = new JSONObject();
                obj.put("slr", userDto.getAlias());
                obj.put("slrid", userDto.getId());

                //循环更新
                BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
                List<String> gzlslids = workFlowDTOList
                        .stream()
                        .filter(workFlowDTO -> StringUtils.isNotBlank(workFlowDTO.getProcessInstanceId()))
                        .map(WorkFlowDTO::getProcessInstanceId)
                        .collect(Collectors.toList());
                LOGGER.info("认领流程，gzlslids：{}", gzlslids);
                for (WorkFlowDTO workFlowDTO : workFlowDTOList) {
                    if (StringUtils.isNotBlank(workFlowDTO.getProcessInstanceId())) {
                        BdcXmQO bdcXmQO = new BdcXmQO();
                        bdcXmQO.setGzlslid(workFlowDTO.getProcessInstanceId());
                        //查询判定受理人和登记部门代码是否有空的，有的话做更新处理
                        //List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
                    }
                }
            }
        }
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 取消认领任务
     */
    @PostMapping("/task/claim/cancel")
    @ApiOperation(value = "取消认领任务")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "taskId", value = "认领任务ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = false, dataType = "String")
    })
    @ResponseStatus(HttpStatus.OK)
    public void cancelClaimTask(@RequestParam(value = "taskId") String taskId, @RequestParam(value = "gzlslid", required = false) String gzlslid, @RequestParam(value = "sljd", required = false) Boolean sljd) {
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("需要认领的任务ID不能为空！");
        }
        boolean result = processTaskClient.cancelTaskClaimBack(taskId);
        //如果取消成功，并且sljd为true做清空认领数据的处理
        executeCancelTask(taskId, gzlslid, result,sljd);
    }

    /**
     * 审核退回任务
     *
     * @param
     * @return void
     * @author <a href="mailto:foxfocus@163.com">fox</a>
     */
    @PostMapping("/task/check/back")
    @ApiOperation(value = "审核退回任务")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "taskId", value = "认领任务ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "reason", value = "审核不通过原因", required = true, dataType = "String")
    })
    @ResponseStatus(HttpStatus.OK)
    public void checkBackTask(@RequestParam(value = "taskId") String taskId, @RequestParam(value = "gzlslid") String gzlslid
            , @RequestParam(value = "reason") String reason) {
        if (StringUtils.isAnyBlank(taskId, gzlslid, reason)) {
            throw new MissingArgumentException("taskid, gzlslid, reason");
        }
        boolean result = processTaskClient.cancelTaskClaimBack(taskId);
        taskHandleClient.taskHang(gzlslid, reason);
        executeCancelTask(taskId, gzlslid, result,true);
        exchangeInterfaceFeignService.workflowSyncRequestInterface("wwsqshztts", gzlslid,
                null, reason, HlwShztEnum.ABANDON.getShzt());
    }

    /**
     * 取消认领处理登记数据
     */
    private void executeCancelTask(@RequestParam("taskId") String taskId, @RequestParam(value = "gzlslid", required = false) String gzlslid, boolean result,Boolean sljd) {
        //如果取消成功，并且sljd为true做清空认领数据的处理
        if (result && Boolean.TRUE.equals(sljd) &&StringUtils.isNoneBlank(gzlslid)) {
            //获取用户信息
            JSONObject obj = new JSONObject();
            obj.put("slr", "");
            obj.put("slrid", "");
            obj.put("djbmdm", "");
            obj.put("djjg", "");
            if(Boolean.TRUE.equals(sjdrlslsj)) {
                obj.put("slsj","");
            }
            //循环更新
            BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
            bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(obj));
            Map<String, Object> map = new HashMap<>();
            map.put("gzlslid", gzlslid);
            bdcDjxxUpdateQO.setWhereMap(map);
            //bdcXmFeignService.updateBatchBdcXm(bdcDjxxUpdateQO);
        }
        //删除审核意见、签名
    }

    /**
     * @param workflowList 取消认领的项目
     * @return {WorkFlowVO} 成功/失败 数目
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 批量取消认领任务
     */
    @PostMapping("/task/claim/cancel/pl")
    @ApiOperation(value = "批量取消认领任务")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "workflowList", value = "取消认领对象集合", required = true, dataType = "List<WorkFlowDTO>")})
    @ResponseStatus(HttpStatus.OK)
    public WorkFlowVO batchUnClaimTasks(@RequestBody List<WorkFlowDTO> workflowList) {
       return null;
    }

    /**
     * @param
     * @param reason
     * @return boolean
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 流程挂起
     */
    @PostMapping("/hang")
    @ApiOperation(value = "流程挂起")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInstanceId", value = "流程实例id", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "reason", value = "挂起原因", paramType = "query", dataType = "string")
    })
    @ResponseStatus(HttpStatus.OK)
    public String taskHang(@RequestParam(value = "processInstanceIds") List<String> processInstanceIds, @RequestParam(value = "reason", required = false) String reason) {
        if (CollectionUtils.isEmpty(processInstanceIds)) {
            throw new MissingArgumentException("流程挂起的processInstanceId不能为空！");
        }
        StringBuffer stringBuffer = new StringBuffer();
        List<ProcessOptResultDto> list = taskHandleClient.batchTaskHang(processInstanceIds, reason);
        if (CollectionUtils.isNotEmpty(list)) {
            for (ProcessOptResultDto processOptResultDto : list) {
                //失败的
                if (CommonConstantUtils.SF_S_DM.equals(processOptResultDto.getCode())) {
                    if (StringUtils.isNotBlank(stringBuffer.toString())) {
                        stringBuffer.append("\n");
                    }
                    stringBuffer.append(processOptResultDto.getProcessDefName() + "挂起失败:" + processOptResultDto.getMessage());
                }
            }
        }
        if (StringUtils.isBlank(stringBuffer.toString())) {
            stringBuffer.append("挂起成功");
        }
        return stringBuffer.toString();
    }

    /**
     * @param
     * @param reason
     * @return boolean
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 激活流程
     */
    @PostMapping("/activate")
    @ApiOperation(value = "激活流程")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInstanceIds", value = "流程实例id集合", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "reason", value = "激活原因", paramType = "query", dataType = "string")})
    @ResponseStatus(HttpStatus.OK)
    public String taskActivation(@RequestParam(value = "processInstanceIds") List<String> processInstanceIds, @RequestParam(value = "reason", required = false) String reason) {
        if (CollectionUtils.isEmpty(processInstanceIds)) {
            throw new MissingArgumentException("激活流程的processInstanceId不能为空！");
        }
        StringBuffer stringBuffer = new StringBuffer();
        List<ProcessOptResultDto> list = taskHandleClient.batchTaskActivation(processInstanceIds, reason);
        if (CollectionUtils.isNotEmpty(list)) {
            for (ProcessOptResultDto processOptResultDto : list) {
                //失败的
                if (CommonConstantUtils.SF_S_DM.equals(processOptResultDto.getCode())) {
                    if (StringUtils.isNotBlank(stringBuffer.toString())) {
                        stringBuffer.append("\n");
                    }
                    stringBuffer.append(processOptResultDto.getProcessDefName() + "激活失败:" + processOptResultDto.getMessage());
                }
            }
        }
        if (StringUtils.isBlank(stringBuffer.toString())) {
            stringBuffer.append("激活成功");
        }
        return stringBuffer.toString();
    }

    /**
     * @param taskId
     * @return code; // 0: 成功， 1：不允许 msg: // 保存不允许退回的原因
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 判断是否可以取回
     */
    @GetMapping("/task/isAllowFetchBack")
    @ApiOperation(value = "判断是否可以取回(code 0: 成功， 1：不允许 )")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "taskId", value = "流程任务id", paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public BaseResult isAllowFetchBack(@RequestParam(value = "taskId") String taskId) {
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("判断是否可以取回的taskId不能为空！");
        }
        return flowableNodeClient.isAllowFetchBack(taskId);
    }

    /**
     * @param taskId
     * @return true false
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 取回操作
     */
    @GetMapping("/task/fetchBack")
    @ApiOperation(value = "取回")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "taskId", value = "流程任务id", paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public void fetchBack(@RequestParam(value = "taskId") String taskId) {
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("取回的taskId不能为空！");
        }
        taskHandleClient.fetchBack(taskId);
    }

    /**
     * @param taskIdList
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 批量取回
     */
    @GetMapping("/task/plFetchBack")
    @ApiOperation(value = "批量取回")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "taskIdList", value = "流程任务id", paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public String plFetchBack(@RequestParam(value = "taskIdList") List<String> taskIdList) {
        if (CollectionUtils.isEmpty(taskIdList)) {
            throw new MissingArgumentException("取回的taskId不能为空！");
        }
        StringBuilder result = new StringBuilder();
        List<ProcessOptResultDto> processOptResultDtos = taskHandleClient.batchFetchBack(taskIdList);
        if (CollectionUtils.isNotEmpty(processOptResultDtos)) {
            for (ProcessOptResultDto processOptResultDto : processOptResultDtos) {
                //失败的
                if (CommonConstantUtils.SF_S_DM.equals(processOptResultDto.getCode())) {
                    if (StringUtils.isNotBlank(result.toString())) {
                        result.append("\n");
                    }
                    result.append(processOptResultDto.getProcessDefName() + ":" + processOptResultDto.getTaskName() + " 取回失败");
                    if (StringUtils.isNotBlank(processOptResultDto.getMessage())) {
                        result.append(":" + processOptResultDto.getMessage());
                    }
                }
            }
        }
        if (StringUtils.isBlank(result)) {
            result.append("取回成功");
        }
        return result.toString();
    }

    /**
     * @param gzldyid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/9/23 20:05
     */
    @GetMapping("/process/overcount")
    @ApiOperation(value = "判断流程是否超出创建次数")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功")})
    @ResponseStatus(HttpStatus.OK)
    public Object queryProcessCount(String gzldyid) {
        if (StringUtils.isNotBlank(gzldyid)) {
            Map<String, Integer> result = processInstanceClient.countCreateProcessFrequencyForMonth(gzldyid);
            if (MapUtils.isEmpty(result) || null == result.get("processConfigNum") || null == result.get("currentMonthCreateNum")) {
                return null;
            }
            return result.get("processConfigNum") - result.get("currentMonthCreateNum");
        }
        return null;
    }

    /**
     * @param moduleCode 页面编号
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取模块的权限状态
     */
    @ResponseBody
    @GetMapping("/moduleAuthority/{moduleCode}")
    public Object queryModuleState(@PathVariable(name = "moduleCode") String moduleCode) {
        String currentUserName = userManagerUtils.getCurrentUserName();
        if (StringUtils.isBlank(currentUserName)) {
            throw new MissingArgumentException("无法获取到当前用户信息");
        }
        return elementCient.getAuthorities(currentUserName, moduleCode);
    }

    /**
     * @param moduleCode 模块编码
     * @return String
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取角色 模块权限
     */
    @GetMapping("/module/authority")
    @ApiOperation(value = "获取角色模块权限")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "moduleCode", value = "模块编码", paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public Object queryModuleAuthority(@RequestParam(name = "moduleCode", required = false) String moduleCode) {
        if (StringUtils.isBlank(moduleCode)) {
            moduleCode = moduleCodeBdcdj;
        }
        String clientId;
        // 配置了 moduleClient 优先读取 moduleClient，未配置则读取 portalui 的 clientid
        if (StringUtils.isNotBlank(moduleClient)) {
            clientId = moduleClient;
        } else {
            clientId = portalUiClientId;
        }
        String userName = userManagerUtils.getCurrentUserName();
        return oauthManagerClient.findModuleAuthority(userName, moduleCode, clientId);
    }

    /**
     * @param processInstanceId taskId type
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取流程状态意见（挂起、退回、转发、激活）
     */
    @PostMapping("/opinion")
    @ApiOperation(value = "获取流程状态意见（挂起、退回、转发、激活）", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInstanceId", value = "流程实例id", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "taskId", value = "任务id", paramType = "query", dataType = "string"), @ApiImplicitParam(name = "type", value = "意见类型", paramType = "query", dataType = "string")})
    @ResponseStatus(HttpStatus.OK)
    public Object queryOpinion(@RequestParam(name = "processInstanceId") String processInstanceId, @RequestParam(name = "taskId", required = false) String taskId, @RequestParam(name = "type") String type) {
        if (StringUtils.isAnyBlank(processInstanceId, type)) {
            throw new MissingArgumentException("参数流程实例id，任务id，意见类型不能为空！");
        }
        if (StringUtils.isBlank(taskId) && StringUtils.equals(type, CommentType.BACK_OPINION.value())) {
            throw new MissingArgumentException("退回意见的 任务id不能为空！");
        }
        return processTaskClient.queryProcessOpinion(processInstanceId, taskId, type);
    }


    /**
     * @param
     * @param
     * @return boolean
     * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 流程锁定
     */
    @PostMapping("/lock")
    @ApiOperation(value = "流程锁定", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "taskId", value = "流程任务ID", paramType = "query", dataType = "string")
    })
    @ResponseStatus(HttpStatus.OK)
    public void taskLock(@RequestParam(value = "taskId") String taskId) {
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("流程锁定的taskId不能为空！");
        }
        taskHandleClient.taskLock(taskId, userManagerUtils.getCurrentUserName());
    }

    /**
     * 检查流程是否需要人工参与指定走向
     *
     * @param taskIds ,隔开
     * @return BaseResult  0成功  1失败  和msg
     * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 检查流程是否需要人工参与指定走向
     */
    @PostMapping("/batch/forward/tasks")
    @ApiOperation(value = "检查流程是否需要人工参与指定走向")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "taskIds", value = "流程任务ID", paramType = "query", dataType = "string")
    })
    @ResponseStatus(HttpStatus.OK)
    public BaseResult checkTasksForward(@RequestBody List<String> taskIds) {
        if (CollectionUtils.isEmpty(taskIds)) {
            throw new MissingArgumentException("流程的taskId不能为空！");
        }
        return flowableNodeClient.checkTasksForward(taskIds);
    }


    /**
     * 批量转发流程
     *
     * @param workFlowDTOList 转发的工作量 DTO 集合
     * @return {ForwardPLDTO} 批量转发返回信息
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/task-handel/batch-forward/complete")
    @ApiOperation(value = "批量转发流程")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "taskIds", value = "流程任务ID", paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public Object batchComplete(@RequestBody List<WorkFlowDTO> workFlowDTOList) {
        if (CollectionUtils.isEmpty(workFlowDTOList)) {
            throw new MissingArgumentException("批量转发流程 taskId和gzlslid不能为空！");
        }
        StringBuilder taskIds = new StringBuilder();
        ForwardPLDTO forwardPLDTO = new ForwardPLDTO();
        List<ForwardYzDTO> forwardYzDTOS = Lists.newArrayListWithCapacity(workFlowDTOList.size());
        List<ForwardTaskDto> forwardTaskDtos = Lists.newArrayList();
        for (WorkFlowDTO workFlowDTO : workFlowDTOList) {
            ForwardYzDTO forwardYzDTO = new ForwardYzDTO(workFlowDTO.getSlbh());
            // 直接将转发验证信息加入集合（避免代码重复漏写）
            forwardYzDTOS.add(forwardYzDTO);
            // 转发规则验证
            boolean yzResult = plZfYzgz(workFlowDTO, forwardYzDTO);
            // 必填项验证
            if (yzResult && plZfBtxYz(workFlowDTO, forwardYzDTO)) {
                taskIds.append(workFlowDTO.getTaskId()).append(",");
                // 参数均不为空
                if (!StringUtils.isAnyBlank(workFlowDTO.getSelectRoleIds(),
                        workFlowDTO.getTaskId(), workFlowDTO.getSelectUserNames(), workFlowDTO.getActivityId())) {
                    // 组织参数
                    ForwardTaskDto forwardTaskDto = new ForwardTaskDto();
                    forwardTaskDto.setTaskId(workFlowDTO.getTaskId());
                    forwardTaskDto.setSelectRoleIds(workFlowDTO.getSelectRoleIds());
                    forwardTaskDto.setSelectUserNames(workFlowDTO.getSelectUserNames());
                    forwardTaskDto.setActivityId(workFlowDTO.getActivityId());
                    forwardTaskDto.setOpinion(workFlowDTO.getReason());
                    forwardTaskDtos.add(forwardTaskDto);
                }
            }
            if (!yzResult) {
                forwardPLDTO.setGzyzMsg(yzResult);
            }
        }
        // 批量转发
        LOGGER.warn("批量转发的 taskId 为：{}， forwardTaskDtos：{}", taskIds, JSON.toJSONString(forwardTaskDtos));
        int failSize = 0, successSize = 0;
        if (StringUtils.isNotBlank(taskIds)) {
            // 去除最后一个 逗号
            String forwardTasks = taskIds.substring(0, taskIds.length() - 1);
            String group = StringUtils.equals(forwardGroup, "true") ? "organization" : null;

            // 批量转发
            List<ProcessOptResultDto> forwardResults = CollectionUtils.isNotEmpty(forwardTaskDtos) ?
                    taskHandleClient.batchCompleteSameNode(forwardTasks, forwardTaskDtos) :
                    taskHandleClient.batchComplete(forwardTasks, "", group);
            LOGGER.warn("转发结果forwardResults{}", JSONObject.toJSONString(forwardResults));
            if (CollectionUtils.isNotEmpty(forwardResults)) {
                for (ProcessOptResultDto processOptResultDto : forwardResults) {
                    //失败的
                    if (CommonConstantUtils.SF_S_DM.equals(processOptResultDto.getCode())) {
                        failSize++;
                        LOGGER.warn("流程:" + processOptResultDto.getProcessDefName() + ",节点:" + processOptResultDto.getTaskName() + ",转发失败.");
                    }
                }
                successSize = forwardResults.size() - failSize;
            }
        } else {
            failSize = workFlowDTOList.size();
        }
        forwardPLDTO.initForwardSizeMsg(successSize, failSize);
        forwardPLDTO.setForwardYzDTOS(forwardYzDTOS);
        return forwardPLDTO;
    }

    /**
     * 批量验证必填项（验证前先自动签名）
     *
     * @param workFlowDTO  工作流DTO
     * @param forwardYzDTO 转发验证DTO
     * @return {boolean} 必填项验证通过返回 true, 出现异常返回 false
     */
    private boolean plZfBtxYz(WorkFlowDTO workFlowDTO, ForwardYzDTO forwardYzDTO) {
        return false;
    }

    /**
     * 批量退回规则验证（数据异常也会检验）
     *
     * @param workFlowDTO  工作流DTO
     * @param forwardYzDTO 转发验证DTO
     * @return {boolean} 规则验证通过 true, 未通过或者出现异常返回 false
     */
    private boolean plThYzgz(WorkFlowDTO workFlowDTO, ForwardYzDTO forwardYzDTO) {
        return false;
    }

    /**
     * 批量转发规则验证（数据异常也会检验）
     *
     * @param workFlowDTO  工作流DTO
     * @param forwardYzDTO 转发验证DTO
     * @return {boolean} 规则验证通过 true, 未通过或者出现异常返回 false
     */
    private boolean plZfYzgz(WorkFlowDTO workFlowDTO, ForwardYzDTO forwardYzDTO) {
        // 批量转发数据验证
        if (StringUtils.isAnyBlank(workFlowDTO.getProcessDefKey(), workFlowDTO.getProcessInstanceId())) {
            forwardYzDTO.generateDataError();
            return false;
        }
        // 忽略的项目不需要走规则验证
        if (!workFlowDTO.isIgnore()) {
            try {
                forwardYzDTO.generateGzyz(gzyz(workFlowDTO.getTaskId(), workFlowDTO.getProcessDefKey(), workFlowDTO.getProcessInstanceId()));
                return CollectionUtils.isEmpty(forwardYzDTO.getBdcGzyzVOS());
            } catch (Exception e) {
                LOGGER.error("验证规则异常", e);
                forwardYzDTO.generateGzyzError();
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 规则验证
     *
     * @param taskid     taskid
     * @param processKey processKey
     * @param gzlslid    gzlslid
     * @return
     */
    public Object gzyz(String taskid, String processKey, String gzlslid) {
       return null;
    }

    /**
     * @return String
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取退回意见配置
     */
    @GetMapping("/getThyjCheck")
    @ApiOperation(value = "获取退回意见配置")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public String getThyjCheck() {
        return thyjCheck;
    }

    /**
     * @return {String} 返回配置内容
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 获取转发自动签名配置
     */
    @GetMapping("/getZfzdqm")
    @ApiOperation(value = "获取转发自动签名配置")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "taskId", value = "流程任务ID", paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public Object getZfzdqm(@RequestParam(name = "taskId") String taskId) {
        if (!StringUtils.equals(zfzdqm, "1") && StringUtils.isNotBlank(zfzdqmjd)) {
            if (StringUtils.isBlank(taskId)) {
                throw new MissingArgumentException("缺失taskId参数！");
            }
            TaskData taskData = workFlowUtils.getTaskById(taskId);
            if (null == taskData) {
                throw new MissingArgumentException("当前taskId：" + taskId + "未查询到流程节点信息！");
            }
            // 自动签名为 0，节点有值则进入循环判断
            String[] jdmcs = zfzdqmjd.split(",");
            for (String jdmc : jdmcs) {
                if (StringUtils.equals(taskData.getTaskName(), jdmc)) {
                    return '1';
                }
            }
        }
        return zfzdqm;
    }

    /**
     * 单个项目的自动转发
     *
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/zfyz")
    @ApiOperation(value = "自动转发多线程验证接口")
    @ApiImplicitParam(name = "workFlowDTO", value = "流程任务ID", paramType = "body", dataType = "WorkFlowDTO")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public Object zfyz(@RequestBody WorkFlowDTO workFlowDTO) throws ExecutionException, InterruptedException {
        if (StringUtils.isAnyBlank(workFlowDTO.getTaskId(), workFlowDTO.getFormKey(), workFlowDTO.getProcessInstanceId(),
                workFlowDTO.getProcessDefKey(), workFlowDTO.getTaskName())) {
            throw new MissingArgumentException("taskid, formkey, gzlslid, processDefkey, taskName");
        }
        // 根据配置和节点判断是否自动签名
        boolean autoSign = StringUtils.equals(zfzdqm, "1");
        if (!StringUtils.equals(zfzdqm, "1") && StringUtils.isNotBlank(zfzdqmjd)) {
            // 自动签名为 0，节点有值则进入循环判断
            String[] jdmcs = zfzdqmjd.split(",");
            for (String jdmc : jdmcs) {
                if (StringUtils.equals(workFlowDTO.getTaskName(), jdmc)) {
                    autoSign = true;
                    break;
                }
            }
        }
        // 验证判断是否可以转发
//        List<Future<ForwardYzDTO>> futures = bdcForwardService.zfyz(workFlowDTO, autoSign);
//        LOGGER.info("验证判断是否可以转发: {}", JSON.toJSONString(futures));
        // 处理返回结果
        ForwardYzDTO yzDTO = new ForwardYzDTO();
        yzDTO.setGzlslid(workFlowDTO.getProcessInstanceId());
        yzDTO.setSlbh(workFlowDTO.getSlbh());
//        for (Future<ForwardYzDTO> future : futures) {
//            ForwardYzDTO forwardYzDTO = future.get();
//            if (forwardYzDTO == null) {
//                continue;
//            }
//            // 组合 必填项验证 和 规则验证 的结果
//            if (CollectionUtils.isNotEmpty(forwardYzDTO.getBdcBtxyzVOS())) {
//                yzDTO.setBdcBtxyzVOS(forwardYzDTO.getBdcBtxyzVOS());
//                return yzDTO;
//            }
//            if (CollectionUtils.isNotEmpty(forwardYzDTO.getBdcGzyzVOS())) {
//                yzDTO.setBdcGzyzVOS(forwardYzDTO.getBdcGzyzVOS());
//                LOGGER.info("规则验证结果: {}", JSON.toJSONString(yzDTO));
//                return yzDTO;
//            }
//        }
//        LOGGER.info("验证结果: {}", JSON.toJSONString(yzDTO));
        return yzDTO;
    }

    /**
     * @param taskId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 转发必填项验证
     * @date : 2021/8/26 16:12
     */
    @GetMapping("/btxyz")
    @ApiOperation(value = "自动转发多线程验证接口")
    @ApiImplicitParam(name = "taskId", value = "taskId", required = true, paramType = "query", dataType = "String")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public Object btxYz(@RequestParam(value = "taskId") String taskId) throws ExecutionException, InterruptedException {
        return null;
    }


    /**
     * @param taskId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 转发规则验证
     * @date : 2021/8/26 16:12
     */
    @GetMapping("/zfgzyz")
    @ApiOperation(value = "自动转发多线程验证接口")
    @ApiImplicitParam(name = "taskId", value = "taskId", paramType = "query", dataType = "String")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public Object zfGzyz(@RequestParam(name = "taskId", required = true) String taskId) throws ExecutionException, InterruptedException {
       return null;
    }

    /**
     * @return {String} 返回配置内容
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取转发签名显示配置
     */
    @GetMapping("/getZfqmxs")
    @ApiOperation(value = "获取转发签名显示配置")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public String getZfqmxs() {
        return zfqmxs;
    }

    /**
     * @return {String} 返回配置内容
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 获取删除原因配置
     */
    @GetMapping("/getDeleteReason")
    @ApiOperation(value = "获取删除原因配置")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public String getDeleteReason() {
        return deletereason;
    }

    /**
     * 判断是否可以批量转发
     *
     * @return {String} 返回配置内容
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/isPlZf")
    @ApiOperation(value = "判断是否可以批量转发")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public String getIsPlZf() {
        return zfzdqm;
    }

    /**
     * @param gzlslid gzlslid
     * @return {String} 是否签名成功，成功返回 success, 失败返回 ""
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 当前流程是否显示撤销按钮
     */
    @GetMapping("/abandon")
    @ApiOperation(value = "是否可以撤销", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "gzlslid", required = true, dataType = "string")})
    @ResponseStatus(HttpStatus.OK)
    public String isAbandon(@RequestParam String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("流程实例 ID 不能为空！");
        }
        // 判断是否显示撤销按钮
        BdcWorkFlowAbstactService workFlowService = BdcWorkFlowServiceFactory.getWorkFlowService(version);
        return workFlowService != null ? workFlowService.isAbandon(gzlslid) : StringUtils.EMPTY;
    }

    /**
     * @param gzlslids gzlslids
     * @return
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 检查初审和审核的人是否一致
     */
    @PostMapping("/task/claim/checkHasSameShr")
    @ApiOperation(value = "检查认领任务的初审和二审是否是一样的审核人")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslids", value = "工作流实例ID", required = true, dataType = "String")
    })
    @ResponseStatus(HttpStatus.OK)
    public Object checkHasSameShr(@RequestParam(value = "gzlslids") List<String> gzlslids) {
        UserDto userDto = userManagerUtils.getCurrentUser();
        // 当配置是false选项时，直接return 管理员不走这个限制
        if ("false".equals(checkHasSameShr) || userManagerUtils.isAdminByUserId(userDto.getId())) {
            return false;
        }

//        return bdcShxxFeignService.hasSameShr(gzlslids, userDto.getUsername());
        return false;
    }

    /**
     * 新建任务列表
     *
     * @param processDefName
     * @return 任务列表
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @GetMapping(value = "/task/list")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取新建任务列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "processDefName", value = "工作流定义名", required = false, dataType = "String", paramType = "query")})
    public Object listCategoryProcess(@RequestParam(value = "processDefName", required = false) String processDefName) {
        UserDto userDto = userManagerUtils.getCurrentUser();
        return processService.listZrzyCategoryProcess(userDto, processDefName);
    }
}
