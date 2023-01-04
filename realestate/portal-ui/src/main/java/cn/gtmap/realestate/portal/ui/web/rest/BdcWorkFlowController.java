package cn.gtmap.realestate.portal.ui.web.rest;

import cn.gtmap.gtc.clients.ElementClient;
import cn.gtmap.gtc.clients.OauthManagerClient;
import cn.gtmap.gtc.feign.common.exception.GtFeignException;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.common.BaseResult;
import cn.gtmap.gtc.workflow.domain.common.ProcessOptResultDto;
import cn.gtmap.gtc.workflow.domain.manage.*;
import cn.gtmap.gtc.workflow.enums.task.CommentType;
import cn.gtmap.gtc.workflow.enums.task.PriorityStatus;
import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.dto.AutoCompleteDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcZsXmDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.yh.YhThParamDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.yh.YhThResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcDeleteYwxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.enums.HlwShztEnum;
import cn.gtmap.realestate.common.core.enums.HlwSlztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.service.feign.natural.ZrzyXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcShxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcXxblFeignService;
import cn.gtmap.realestate.common.matcher.FlowableNodeClientMatcher;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.matcher.SyncAutoCmpleteClientMatcher;
import cn.gtmap.realestate.common.matcher.TaskHandleClientMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.portal.ui.core.ForwardRuleFactory;
import cn.gtmap.realestate.portal.ui.core.dto.*;
import cn.gtmap.realestate.portal.ui.core.vo.BdcTslcVO;
import cn.gtmap.realestate.portal.ui.core.vo.WorkFlowVO;
import cn.gtmap.realestate.portal.ui.service.BdcBtxYzService;
import cn.gtmap.realestate.portal.ui.service.BdcForwardService;
import cn.gtmap.realestate.portal.ui.service.BdcGzyzDealService;
import cn.gtmap.realestate.portal.ui.service.BdcSignService;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowAbstactService;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowServiceFactory;
import cn.gtmap.realestate.portal.ui.utils.Constants;
import cn.gtmap.realestate.portal.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static cn.gtmap.realestate.common.util.BdcGzyzTsxxUtils.checkTsxx;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/2/26.
 * @description 流程操作服务接口 转发、退回、挂起、激活、删除 、取回、认领
 */
@RestController
@RequestMapping("/rest/v1.0/workflow/process-instances")
@Api(tags = "流程操作服务接口")
public class BdcWorkFlowController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcWorkFlowController.class);
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
    private BdcSlFeignService bdcSlFeignService;
    @Autowired
    private BdcInitFeignService bdcInitFeignService;
    @Autowired
    private BdcBhFeignService bdcBhFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private WorkFlowUtils workFlowUtils;
    @Autowired
    private TaskUtils taskUtils;
    @Autowired
    private BdcShxxFeignService bdcShxxFeignService;
    @Autowired
    private BdcBtxYzService bdcBtxYzService;
    @Autowired
    private BdcSignService bdcSignService;
    @Autowired
    private BdcYcslDjywDzbFeignService bdcYcslDjywDzbFeignService;
    @Autowired
    private ElementClient elementCient;
    @Autowired
    private BdcGzyzDealService bdcGzyzDealService;
    @Autowired
    private BdcXxblFeignService bdcXxblFeignService;
    @Autowired
    BdcSwFeignService bdcSwFeignService;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcForwardService bdcForwardService;
    @Autowired
    private BdcZsFeignService bdcZsFeignService;
    @Autowired
    BdcCzrzFeignService bdcCzrzFeignService;
    @Autowired
    ForwardRuleFactory forwardRuleFactory;
    @Autowired
    BdcDbxxFeignService bdcDbxxFeignService;
    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;
    @Autowired
    ZrzyXmFeignService zrzyXmFeignService;
    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;
    @Autowired
    BdcSdFeignService bdcSdFeignService;

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
     * 是否区分任务列表、项目办件详情页按钮权限（转发、退回、删除等），默认false不区分，即个人或角色模块设权对两个页面通用
     */
    @Value("${module.qfrwlbxqqx:false}")
    private Boolean qfrwlbxqqx;
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
     * 非登记业务流程, 未配置则为 empty,支持配置多个
     */
    @Value("${fdjywlc.processDefKey:empty}")
    private String fdjywlc;
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

    //特殊流程删除数据
    @Value("#{'${tslc.delete.gzldyid:}'.split(',')}")
    private List<String> tslcGzldyidList;
    @Autowired
    private BdcTslcsDTO tslcs;
    /**
     * 删除方法是否先执行登记删除方法(true|flase,默认 true)
     */
    @Value("${delete.cloud:true}")
    private boolean deleteCloud;

    /**
     * 补录修改流程流程定义 ID
     */
    @Value("${tslc.bdcTslcDTO[1].processDefKey:}")
    private String blModifyKey;
    /*
     * 信息补录修改是否填写解锁原因
     * */
    @Value("${xxblxg.jsyy:true}")
    private boolean xxblxgJsyy;
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
     * 认领时需要标记证书已认领的节点名称，可以配置多个,用英文逗号隔开
     */
    @Value("#{'${rl.zsrl.jdmc:缮证}'.split(',')}")
    private List<String> zsrlJdmcList;

    /**
     * 是否覆盖原有选择不动产单元台账(选择台账只允许存在一个)
     */
    @Value("${xztz.cover:false}")
    private String xztzCover;

    /**
     * 南通 审核节点退回提示 受理编号前缀配置
     */
    @Value("#{'${shjdthts.slbh_startwith:}'.split(',')}")
    private List<String> shjdthtsSlbhList;

    /**
     * 流程图标题是否展示受理编号、不动产单元号
     */
    @Value("${lct.title:false}")
    private boolean lctTitle;

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

    /**
     * 创建非登记业务流程
     *
     * @param workFlowDTO 流程参数
     * @return TaskData 任务实体
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PutMapping("/fdjyw")
    @ApiOperation(value = "创建非登记业务流程")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "workFlowDTO", value = "工作流参数", required = true, dataType = "WorkFlowDTO")})
    @ResponseStatus(HttpStatus.CREATED)
    public TaskData startUpFdjywProcess(@RequestBody WorkFlowDTO workFlowDTO) throws Exception {
        if (StringUtils.isBlank(workFlowDTO.getProcessDefKey())) {
            throw new MissingArgumentException("流程定义id不能为空！");
        }
        String userName = workFlowDTO.getUserName();
        if (StringUtils.isBlank(userName)) {
            userName = userManagerUtils.getCurrentUserName();
        }
        // 创建流程
        TaskData taskData = processInstanceClient.directStartProcessInstance(workFlowDTO.getProcessDefKey(), userName, workFlowDTO.getProcessInstanceName(), "", null);
        // 组织受理初始化对象
        BdcSlCshDTO bdcSlCshDTO = new BdcSlCshDTO();
        bdcSlCshDTO.setJbxxid(UUIDGenerator.generate16());
        bdcSlCshDTO.setGzldyid(workFlowDTO.getProcessDefKey());
        bdcSlCshDTO.setGzlslid(taskData.getProcessInstanceId());
        // 初始化受理申请信息
        bdcSlFeignService.cshBdcSlSqxx(bdcSlCshDTO);
        return taskData;
    }

    /**
     * 创建外网业务流程
     *
     * @param hlwxmid 互联网项目 id
     * @return TaskData 任务实体
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PutMapping("/wwyw")
    @ApiOperation(value = "创建外网业务流程")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "hlwxmid", value = "互联网项目 id", required = true, dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.CREATED)
    @LogNote(value = "创建外网业务流程", action = Constants.LOG_ACTION_PROCESS_CREATE)
    public TaskData startUpWwProcess(@RequestParam String hlwxmid) {
        if (StringUtils.isBlank(hlwxmid) || StringUtils.equals("undefined", hlwxmid)) {
            throw new MissingArgumentException("互联网项目 id 不能为空！");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hlwxmid", hlwxmid);
        jsonObject.put("slr", userManagerUtils.getCurrentUserName());
        LOGGER.warn("创建外网请求发起的参数 ：{}", jsonObject);
        Object wwxm = exchangeInterfaceFeignService.requestInterface("initWwsqXxByEtl", jsonObject);
        LOGGER.warn("外网创建的返回结果 ：{}", wwxm);
        LinkedHashMap result;
        if (wwxm instanceof LinkedHashMap) {
            result = (LinkedHashMap) wwxm;
        } else {
            throw new AppException("外网创建返回类型转换异常");
        }
        String gzlslid = (String) result.get("gzlslid");
        // 只有创建失败才会返回 msg，成功才会返回 gzlslid
        String msg = (String) result.get("msg");
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException(StringUtils.isBlank(msg) ? "创建失败，未返回失败原因" : msg);
        }
        List<TaskData> processRunningTasks = processTaskClient.processRunningTasks(gzlslid);
        if (CollectionUtils.isEmpty(processRunningTasks)) {
            throw new MissingArgumentException("未查询到流程信息！");
        }
        String taskId = processRunningTasks.get(0).getTaskId();
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("未查询到大云 taskid");
        }
        return workFlowUtils.getTaskById(taskId);
    }

    @PostMapping(value = "/stopTask")
    @ApiOperation(value = "终止当前任务")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "workFlowDTOList", value = "流程任务、工作流实例id", paramType = "body", dataType = "WorkFlowDTO")})
    @ResponseStatus(HttpStatus.OK)
    @LogNote(value = "流程终止", action = Constants.LOG_ACTION_PROCESS_STOP)
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
                //是否是首节点
                if (sjd) {
                    //是否是首节点
                    boolean issjd = flowableNodeClient.isStartUserTaskRunning(workFlowDTO.getProcessInstanceId());
                    if (!issjd) {
                        builderLogStr(builder, workFlowDTO, "非受理节点无法删除");
                        continue;
                    }
                }
                //获取关联的项目信息
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setGzlslid(workFlowDTO.getProcessInstanceId());
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                    if(!checkAllowDel(bdcXmDOList)){
                        LOGGER.error("流程删除失败:{},已办结或已登簿项目不允许删除", workFlowDTO.getProcessInstanceId());
                        builderLogStr(builder, workFlowDTO, "已办结或已登簿项目不允许删除");
                        continue;
                    }
                }
                if (deleteCloud) {
                    try {
                        BdcDeleteYwxxDTO bdcDeleteYwxxDTO = new BdcDeleteYwxxDTO();
                        bdcDeleteYwxxDTO.setGzlslid(workFlowDTO.getProcessInstanceId());
                        bdcDeleteYwxxDTO.setReason(workFlowDTO.getReason());
                        bdcDeleteYwxxDTO.setSlzt(slzt);
                        bdcInitFeignService.deleteYwxxAllSubsystem(bdcDeleteYwxxDTO);
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
    @LogNote(value = "删除任务", action = Constants.LOG_ACTION_TASK_DELETE, custom = LogConstans.LOG_TYPE_LCSC)
    public void deleteTask(@RequestBody List<WorkFlowDTO> workFlowDTOList) {
        if (CollectionUtils.isEmpty(workFlowDTOList)) {
            throw new MissingArgumentException("删除当前任务taskId和gzlslid不能为空！");
        }
        this.deleteOrStopTask(workFlowDTOList, HlwSlztEnum.DELETE.getSlzt());
    }

    /**
     * 认领列表删除任务「盐城」
     * 区别于正常删除，调用 perform
     *
     * @param
     * @return void
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    @DeleteMapping("/rl")
    @ApiOperation(value = "删除当前任务「盐城认领」")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "workFlowDTOList", value = "流程任务、工作流实例id", paramType = "body", dataType = "WorkFlowDTO")})
    @ResponseStatus(HttpStatus.OK)
    @LogNote(value = "删除任务「盐城认领」", action = Constants.LOG_ACTION_TASK_DELETE, custom = LogConstans.LOG_TYPE_LCSC)
    public void deleteRlTask(@RequestBody List<WorkFlowDTO> workFlowDTOList) {
        if (CollectionUtils.isEmpty(workFlowDTOList)) {
            throw new MissingArgumentException("删除当前任务taskId和gzlslid不能为空！");
        }
        this.deleteOrStopTask(workFlowDTOList, HlwSlztEnum.REJECT.getSlzt());
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
            List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOS) && null != bdcXmDTOS.get(0)) {
                String slbh = bdcXmDTOS.get(0).getSlbh();
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
                //获取关联的项目信息
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setGzlslid(workFlowDTO.getProcessInstanceId());
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);


                //登记业务删除是否验证ajzt和qszt
                boolean checkDel =true;
                // 获取工作流定义 id 对比判断是否属于 特殊流程（包括一窗流程），直接执行大云删除方法。
                if (StringUtils.isNotBlank(workFlowDTO.getProcessDefKey())
                        && (StringUtils.isNotBlank(fdjywlc)
                        && StringUtils.contains(fdjywlc, workFlowDTO.getProcessDefKey())
                        || bdcYcslDjywDzbFeignService.checkIsYcslLc(workFlowDTO.getProcessDefKey())
                        || (CollectionUtils.isNotEmpty(tslcGzldyidList) && tslcGzldyidList.contains(workFlowDTO.getProcessDefKey())))
                ) {
                    checkDel=false;
                    taskUtils.deleteTask(workFlowDTO.getProcessInstanceId(), workFlowDTO.getReason());
                    // 成功 删除受理信息
                    bdcSlFeignService.deleteBdcSlxx(workFlowDTO.getProcessInstanceId());
                    continue;
                }

                // 获取工作流定义 id 对比判断是否属于 补录修改流程。
                if (StringUtils.isNotBlank(workFlowDTO.getProcessDefKey()) && (StringUtils.equals(workFlowDTO.getProcessDefKey(), blModifyKey))) {
                    checkDel=false;
                    try {
                        bdcXxblFeignService.deleteBllcModify(workFlowDTO.getProcessInstanceId());
                    } catch (Exception e) {
                        LOGGER.error("请求补录修改流程删除接口异常:{}", workFlowDTO.getProcessInstanceId(), e);
                        builderLogStr(builder, workFlowDTO, "补录修改流程删除失败");
                        continue;
                    }
                }
                if(checkDel &&CollectionUtils.isNotEmpty(bdcXmDOList)){
                    if(!checkAllowDel(bdcXmDOList)){
                        LOGGER.error("流程删除失败:{},已办结或已登簿项目不允许删除", workFlowDTO.getProcessInstanceId());
                        builderLogStr(builder, workFlowDTO, "已办结或已登簿项目不允许删除");
                        continue;
                    }
                }
                // 执行删除前方法
                BdcWorkFlowAbstactService workFlowService = BdcWorkFlowServiceFactory.getWorkFlowService(version);
                if (workFlowService != null) {
                    workFlowService.processBeforeDelete(new EventDTO(workFlowDTO.getProcessInstanceId(), workFlowDTO.getReason(), slzt), CollectionUtils.isNotEmpty(bdcXmDOList) ? bdcXmDOList.get(0) : null, workFlowDTO.getUserName());
                }
                if (deleteCloud) {
                    try {
                        BdcDeleteYwxxDTO bdcDeleteYwxxDTO = new BdcDeleteYwxxDTO();
                        bdcDeleteYwxxDTO.setGzlslid(workFlowDTO.getProcessInstanceId());
                        bdcDeleteYwxxDTO.setReason(workFlowDTO.getReason());
                        bdcDeleteYwxxDTO.setSlzt(slzt);
                        bdcInitFeignService.deleteYwxxAllSubsystem(bdcDeleteYwxxDTO);
                    } catch (Exception e) {
                        LOGGER.error("请求 init 删除接口异常:{}", workFlowDTO.getProcessInstanceId(), e);
                        builderLogStr(builder, workFlowDTO, "删除失败");
                        continue;
                    }
                }
                LOGGER.error("portal deleteCloud：{}， 删除数据:{}", deleteCloud, JSON.toJSONString(workFlowDTO));
                taskUtils.deleteTask(workFlowDTO.getProcessInstanceId(), workFlowDTO.getReason());
                LOGGER.info("删除抵押任务回调银行系统开始");
                callYhThInterface(workFlowDTO, bdcXmDOList);
                if (workFlowService != null && CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    workFlowService.processAfterDelete(bdcXmDOList.get(0), userManagerUtils.getCurrentUserName(), workFlowDTO.getReason());
                }

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
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  验证是否允许删除,true为允许，false为不允许
     */
    private boolean checkAllowDel(List<BdcXmDO> bdcXmDOList){
        boolean allowDel =true;
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            for(BdcXmDO bdcXmDO:bdcXmDOList) {
                //登记业务删除,ajzt=2(已办结) 或者权属状态非临时状态（排除查封登记）,不允许删除
                if (CommonConstantUtils.AJZT_YB_DM.equals(bdcXmDO.getAjzt())) {
                    allowDel =false;
                    break;
                }
                //查封登记创建即现势
                if (!CommonConstantUtils.QSZT_TEMPORY.equals(bdcXmDO.getQszt()) && !(CommonConstantUtils.QLLX_CF.equals(bdcXmDO.getQllx()) && CommonConstantUtils.QSZT_VALID.equals(bdcXmDO.getQszt()))) {
                    allowDel =false;
                    break;
                }
            }
        }
        return allowDel;
    }





    /**
     * 调用银行退回接口
     *
     * @param workFlowDTO
     * @param bdcXmDOList
     */
    private void callYhThInterface(WorkFlowDTO workFlowDTO, List<BdcXmDO> bdcXmDOList) {
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && StringUtils.isNotBlank(bdcXmDOList.get(0).getSpxtywh()) && CommonConstantUtils.SPLY_YHXT.equals(bdcXmDOList.get(0).getSply())) {
            Optional<BdcXmDO> any = bdcXmDOList.stream().filter(bdcXmDO -> {
                if (!CommonConstantUtils.DJLX_QTDJ_DM.equals(bdcXmDO.getDjlx()) || !CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
                    return true;
                }
                return false;
            }).findAny();
            if (!any.isPresent()) {
                YhThParamDTO yhThParamDTO = YhThParamDTO.YhThParamDTOBuilder.anYhThParamDTO().withReturnReason(workFlowDTO.getReason()).withSpxtywh(bdcXmDOList.get(0).getSpxtywh()).withYhmc(bdcXmDOList.get(0).getQlr()).build();
                Object response = exchangeInterfaceFeignService.requestInterface("delete_yhhd", yhThParamDTO);
                LOGGER.info("删除抵押任务回调银行系统接口返回结果：{}", response);
                if (response != null) {
                    YhThResponseDTO yhThResponseDTO = JSONObject.parseObject(JSON.toJSONString(response), YhThResponseDTO.class);
                    if (!StringUtils.equals("0000", yhThResponseDTO.getCode())) {
                        throw new MissingArgumentException("调用银行退回系统失败！");
                    }
                }
            }
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
     * @param workFlowDTO reason gzlslid 为必填项
     * @return boolean
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 撤销流程
     */
    @PostMapping("/abandon")
    @ApiOperation(value = "撤销流程")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "workFlowDTO", value = "reason gzlslid 为必填项", required = true, dataType = "WorkFlowDTO")})
    @ResponseStatus(HttpStatus.OK)
    @LogNote(value = "撤销流程", action = Constants.LOG_ACTION_PROCESS_REVOKE)
    public void abandonTask(@RequestBody WorkFlowDTO workFlowDTO) {
        if (StringUtils.isBlank(workFlowDTO.getReason()) && StringUtils.isBlank(workFlowDTO.getProcessInstanceId())) {
            throw new MissingArgumentException("撤销原因和流程实例 ID 不能为空！");
        }
        // 执行撤销前特殊处理
        BdcWorkFlowAbstactService workFlowService = BdcWorkFlowServiceFactory.getWorkFlowService(version);
        if (workFlowService != null) {
            workFlowService.abandonTask(workFlowDTO);
        }
        LOGGER.error("撤销流程：{}", JSONObject.toJSONString(workFlowDTO));
        taskHandleClient.taskAbandoned(workFlowDTO.getProcessInstanceId(), workFlowDTO.getReason());
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
        LOGGER.info("退回任务backForGeneral：{}", JSONObject.toJSONString(backNodesDto));
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
     * @param taskId 任务id
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description （南通）获取退回节点列表
     */
    @GetMapping("/nantong/back/userTasks")
    public List<BackTaskDto> listNtBackUserTasks(@RequestParam(value = "taskId") String taskId) {
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("获取退回节点列表的taskId不能为空！");
        }

        // 南通需要支持退回到首节点
        BackNodesDto backNodesDto = flowableNodeClient.getBackUserTasksWithFirstNode(taskId);
        if (null == backNodesDto) {
            return Collections.emptyList();
        }

        List<BackTaskDto> backTaskDtoList = new ArrayList<>(5);
        // 首节点
        backTaskDtoList.addAll(backNodesDto.getFirstNodeList());
        // 上一个节点
        backTaskDtoList.addAll(backNodesDto.getPrevNodeList());
        return backTaskDtoList;
    }


    /**
     * @param workFlowDTOList
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 认领任务
     */
    @PostMapping("/task/claim")
    @ApiOperation(value = "认领任务")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
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
        String userName =workFlowDTOList.get(0).getCurrentUserName();
        UserDto userDto =null;
        if(StringUtils.isBlank(userName)) {
            userName = userManagerUtils.getCurrentUserName();
        }else{
            userDto =userManagerUtils.getUserByName(userName);
        }
        boolean result = taskHandleClient.taskClaim(userName, taskIds);
        //领取成功
        if (result) {
            if(userDto ==null) {
                userDto = userManagerUtils.getCurrentUser();
            }
            if (userDto != null) {
                //获取用户信息
                JSONObject obj = new JSONObject();
                obj.put("slr", userDto.getAlias());
                obj.put("slrid", userDto.getId());

                //循环更新
                BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
                List<String> gzlslids = workFlowDTOList.stream().filter(workFlowDTO -> StringUtils.isNotBlank(workFlowDTO.getProcessInstanceId())).map(WorkFlowDTO::getProcessInstanceId).collect(Collectors.toList());
                LOGGER.info("认领流程，gzlslids：{}", gzlslids);
                for (WorkFlowDTO workFlowDTO : workFlowDTOList) {
                    if (StringUtils.isNotBlank(workFlowDTO.getProcessInstanceId())) {
                        BdcXmQO bdcXmQO = new BdcXmQO();
                        bdcXmQO.setGzlslid(workFlowDTO.getProcessInstanceId());
                        //查询判定受理人和登记部门代码是否有空的，有的话做更新处理
                        List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
                        if (CollectionUtils.isNotEmpty(list)) {
                            if ((StringUtils.isBlank(list.get(0).getSlr()) || StringUtils.isBlank(list.get(0).getDjbmdm()))) {
                                if (CollectionUtils.isNotEmpty(userDto.getOrgRecordList())) {
                                    OrganizationDto organizationDto = userDto.getOrgRecordList().get(0);
                                    if (StringUtils.isNotBlank(list.get(0).getQxdm())) {
                                        //区县代码匹配组织
                                        for (OrganizationDto organ : userDto.getOrgRecordList()) {
                                            if (StringUtils.equals(list.get(0).getQxdm(), organ.getRegionCode())) {
                                                organizationDto = organ;
                                                break;
                                            }
                                        }
                                    }
                                    if (organizationDto != null) {
                                        List<OrganizationDto> organizationDtoList = new ArrayList<>();
                                        organizationDtoList.add(organizationDto);
                                        userDto.setOrgRecordList(organizationDtoList);
                                        obj.put("djbmdm", organizationDto.getCode());
                                        obj.put("djjg", bdcSlJbxxFeignService.queryDjjgByUser(userDto));
                                        if (Boolean.TRUE.equals(sjdrlslsj)) {
                                            obj.put("slsj", new Date());
                                        }
                                    }
                                }
                                bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(obj));
                                Map<String, Object> map = new HashMap<>();
                                map.put("gzlslid", workFlowDTO.getProcessInstanceId());
                                bdcDjxxUpdateQO.setWhereMap(map);
                                bdcXmFeignService.updateBatchBdcXm(bdcDjxxUpdateQO);
                            }
                            if (StringUtils.isNotBlank(workFlowDTO.getTaskId()) && CollectionUtils.isNotEmpty(zsrlJdmcList)) {
                                TaskData taskData = workFlowUtils.getTaskById(workFlowDTO.getTaskId());
                                if (taskData != null && zsrlJdmcList.contains(taskData.getTaskName())) {
                                    //更新证书认领状态
                                    bdcXmfbFeignService.updateZsrlzt(Collections.singletonList(workFlowDTO.getProcessInstanceId()), CommonConstantUtils.SF_S_DM);

                                }
                            }

                        }
                        BdcSlJbxxDO jbxxdo = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(workFlowDTO.getProcessInstanceId());
                        if (null != jbxxdo && StringUtils.isNotBlank(jbxxdo.getJbxxid())) {
                            //首节点也就是受理人是空的时候才更新
                            if (StringUtils.isBlank(jbxxdo.getSlr())) {
                                if (CollectionUtils.isNotEmpty(userDto.getOrgRecordList())) {
                                    OrganizationDto organizationDto = userDto.getOrgRecordList().get(0);
                                    if (StringUtils.isNotBlank(jbxxdo.getQxdm())) {
                                        //区县代码匹配组织
                                        for (OrganizationDto organ : userDto.getOrgRecordList()) {
                                            if (StringUtils.equals(jbxxdo.getQxdm(), organ.getRegionCode())) {
                                                organizationDto = organ;
                                                break;
                                            }
                                        }
                                    }
                                    if (organizationDto != null) {
                                        List<OrganizationDto> organizationDtoList = new ArrayList<>();
                                        organizationDtoList.add(organizationDto);
                                        userDto.setOrgRecordList(organizationDtoList);
                                        obj.put("djbmdm", organizationDto.getCode());
                                        obj.put("djjg", bdcSlJbxxFeignService.queryDjjgByUser(userDto));
                                    }
                                }
                                LOGGER.info("认领外网创建一体化流程，基本信息：{}", jbxxdo.toString());
                                jbxxdo.setSlr(obj.get("slr") == null ? "" : obj.get("slr").toString());
                                jbxxdo.setSlrid(obj.get("slrid") == null ? "" : obj.get("slrid").toString());
                                jbxxdo.setDjjg(obj.get("djjg") == null ? "" : obj.get("djjg").toString());
                                jbxxdo.setDjbmdm(obj.get("djbmdm") == null ? "" : obj.get("djbmdm").toString());
                                if (Boolean.TRUE.equals(sjdrlslsj)) {
                                    jbxxdo.setSlsj(new Date());
                                }
                                bdcSlJbxxFeignService.updateBdcSlJbxx(jbxxdo);
                            }
                        }
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
        executeCancelTask(taskId, gzlslid, result, sljd);
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
    @LogNote(value = "审核退回任务", action = Constants.LOG_ACTION_TASK_BACK_CHECK)
    public void checkBackTask(@RequestParam(value = "taskId") String taskId, @RequestParam(value = "gzlslid") String gzlslid
            , @RequestParam(value = "reason") String reason) {
        if (StringUtils.isAnyBlank(taskId, gzlslid, reason)) {
            throw new MissingArgumentException("taskid, gzlslid, reason");
        }
        boolean result = processTaskClient.cancelTaskClaimBack(taskId);
        taskHandleClient.taskHang(gzlslid, reason);
        executeCancelTask(taskId, gzlslid, result, true);
        exchangeInterfaceFeignService.workflowSyncRequestInterface("wwsqshztts", gzlslid,
                null, reason, HlwShztEnum.ABANDON.getShzt());
    }

    /**
     * 取消认领处理登记数据
     */
    private void executeCancelTask(@RequestParam("taskId") String taskId, @RequestParam(value = "gzlslid", required = false) String gzlslid, boolean result, Boolean sljd) {
        //如果取消成功，并且sljd为true做清空认领数据的处理
        if (result && Boolean.TRUE.equals(sljd) && StringUtils.isNoneBlank(gzlslid)) {
            //获取用户信息
            JSONObject obj = new JSONObject();
            obj.put("slr", "");
            obj.put("slrid", "");
            obj.put("djbmdm", "");
            obj.put("djjg", "");
            if (Boolean.TRUE.equals(sjdrlslsj)) {
                obj.put("slsj", "");
            }
            //循环更新
            BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
            bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(obj));
            Map<String, Object> map = new HashMap<>();
            map.put("gzlslid", gzlslid);
            bdcDjxxUpdateQO.setWhereMap(map);
            bdcXmFeignService.updateBatchBdcXm(bdcDjxxUpdateQO);


        }
        //更新认领状态
        if (StringUtils.isNoneBlank(taskId, gzlslid) && CollectionUtils.isNotEmpty(zsrlJdmcList)) {
            TaskData taskData = workFlowUtils.getTaskById(taskId);
            if (taskData != null && zsrlJdmcList.contains(taskData.getTaskName())) {
                //更新证书认领状态
                bdcXmfbFeignService.updateZsrlzt(Collections.singletonList(gzlslid), CommonConstantUtils.SF_F_DM);

            }
        }
        //删除审核意见、签名
        bdcShxxFeignService.deleteShxxSign(taskId);
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
        if (CollectionUtils.isEmpty(workflowList)) {
            throw new MissingArgumentException("传入的参数不能为空！");
        }
        // 需要转发的 taskid
        List<String> taskIds = Lists.newArrayListWithCapacity(workflowList.size());
        HashMap<String, WorkFlowDTO> dealDatas = new HashMap<>();

        // 待取消认领失败进行对比
        HashMap<String, WorkFlowDTO> pendCancelDatas = new HashMap<>();
        for (WorkFlowDTO workFlowDTO : workflowList) {
            if (StringUtils.isNotBlank(workFlowDTO.getTaskId())) {
                taskIds.add(workFlowDTO.getTaskId());
                pendCancelDatas.put(workFlowDTO.getTaskId(), workFlowDTO);
                // 受理节点数据需要特殊处理
                if (StringUtils.isNotBlank(workFlowDTO.getProcessInstanceId()) && Boolean.TRUE.equals(workFlowDTO.getSljd())) {
                    dealDatas.put(workFlowDTO.getTaskId(), workFlowDTO);
                }
            }
        }

        if (CollectionUtils.isEmpty(taskIds)) {
            throw new MissingArgumentException("传入的 Taskid 不能为空！");
        }

        LOGGER.error("取消认领：{}", JSONObject.toJSONString(taskIds));
        List<ProcessOptResultDto> resultDtos = taskHandleClient.batchUnClaimTasks(taskIds, null);
        LOGGER.error("取消认领调用接口返回数据：{}", JSONObject.toJSONString(resultDtos));
        // 取消认领成功，并且前端传递了 gzlslid 的数据
        List<String> gzlslids = Lists.newArrayListWithCapacity(resultDtos.size());
        // 取消认领需要，需要删除审核信息的 taskid 集合
        List<String> deleteTaskIds = Lists.newArrayListWithCapacity(resultDtos.size());
        //取消认领成功的所有taskid集合
        List<String> successTaskIds = Lists.newArrayListWithCapacity(resultDtos.size());
        // 取消认领失败的集合
        List<String> failTaskIds = Lists.newArrayListWithCapacity(resultDtos.size());
        // 失败消息
        List<String> messages = Lists.newArrayList();
        for (ProcessOptResultDto resultDto : resultDtos) {
            // 取消成功
            if (resultDto.getCode() == 0) {
                successTaskIds.add(resultDto.getTaskId());
                if (dealDatas.keySet().contains(resultDto.getTaskId()) && dealDatas.get(resultDto.getTaskId()) != null) {
                    deleteTaskIds.add(resultDto.getTaskId());
                    gzlslids.add(dealDatas.get(resultDto.getTaskId()).getTaskId());
                }
            } else {
                messages.add(pendCancelDatas.get(resultDto.getTaskId()).getSlbh() + resultDto.getMessage());
                failTaskIds.add(resultDto.getTaskId());
            }
        }

        //如果取消成功，并且传递了gzlslid的话，处理数据
        if (CollectionUtils.isNotEmpty(gzlslids)) {
            //获取用户信息
            JSONObject obj = new JSONObject();
            obj.put("slr", "");
            obj.put("slrid", "");
            obj.put("djbmdm", "");
            obj.put("djjg", "");
            if (Boolean.TRUE.equals(sjdrlslsj)) {
                obj.put("slsj", "");
            }
            //循环更新
            BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
            bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(obj));
            Map<String, Object> map = new HashMap<>();
            map.put("gzlslids", gzlslids);
            bdcDjxxUpdateQO.setWhereMap(map);
            bdcXmFeignService.updateBatchBdcXm(bdcDjxxUpdateQO);
            // 清除签名信息
            bdcShxxFeignService.deleteShxxSign(deleteTaskIds);

        }
        //更新认领状态
        if (CollectionUtils.isNotEmpty(successTaskIds) && CollectionUtils.isNotEmpty(zsrlJdmcList)) {
            List<String> successGzlslids = new ArrayList<>();
            for (String successTaskId : successTaskIds) {
                TaskData taskData = workFlowUtils.getTaskById(successTaskId);
                if (taskData != null && zsrlJdmcList.contains(taskData.getTaskName())) {
                    successGzlslids.add(pendCancelDatas.get(successTaskId).getProcessInstanceId());
                }
            }
            if (CollectionUtils.isNotEmpty(successGzlslids)) {
                //更新证书认领状态
                bdcXmfbFeignService.updateZsrlzt(successGzlslids, CommonConstantUtils.SF_F_DM);
            }
        }
        // 返回失败数据，前台页面提示
        if (CollectionUtils.isNotEmpty(failTaskIds)) {
            LOGGER.error("取消认领失败：{}", JSONObject.toJSONString(failTaskIds));
        }

        return new WorkFlowVO(resultDtos.size() - failTaskIds.size(), failTaskIds.size(), messages);
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
    @LogNote(value = "流程挂起", action = Constants.LOG_ACTION_PROCESS_HANG)
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
    @LogNote(value = "流程激活", action = Constants.LOG_ACTION_PROCESS_ACTIVE)
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
    @LogNote(value = "任务取回", action = Constants.LOG_ACTION_TASK_RETRIEVE)
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
    @LogNote(value = "任务取回", action = Constants.LOG_ACTION_TASK_RETRIEVE)
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
     * @return Map 生成受理编号和基本信息ID
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 生成受理编号和基本信息ID
     */
    @GetMapping("/process/param")
    @ApiOperation(value = "生成受理编号和基本信息ID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功")})
    @ResponseStatus(HttpStatus.OK)
    public Map initParam() {
        String slbh = bdcBhFeignService.queryBh("slbh", "");
        String jbxxid = UUIDGenerator.generate16();
        Map<String, String> param = Maps.newHashMap();
        param.put("slbh", slbh);
        param.put("jbxxid", jbxxid);
        param.put("xztzCover",xztzCover);
        return param;
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
        if (StringUtils.isBlank(moduleCode) || !qfrwlbxqqx) {
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
     * @param processInsId taskId type
     * @return
     * @author <a href ="mailto:hongqin@gtmap.cn">hongqin</a>
     * @description 获取流程状态意见（挂起、退回、转发、激活）
     */
    @PostMapping(value = "/range/opinions")
    @ApiOperation(value = "获取流程状态意见（挂起、退回、转发、激活）", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInstanceId", value = "流程实例id", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "taskId", value = "任务id", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "type", value = "意见类型", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "rangeType", value = "查询范围，0 查询最近的一条意见信息, 1 查询全部的意见信息", paramType = "query", dataType = "string")})
    @ResponseStatus(HttpStatus.OK)
    public List<OpinionDto> queryNodeHistoryOpinions(@RequestParam("processInsId")String processInsId,@RequestParam("taskId")String taskId,@RequestParam("types")String types,@RequestParam("rangeType")Integer rangeType) {
        if (StringUtils.isAnyBlank(processInsId, types)) {
            throw new MissingArgumentException("参数流程实例id，任务id，意见类型不能为空！");
        }
        if (StringUtils.isBlank(taskId) && StringUtils.equals(types, CommentType.BACK_OPINION.value())) {
            throw new MissingArgumentException("退回意见的 任务id不能为空！");
        }
        return processTaskClient.queryNodeHistoryOpinions(processInsId, taskId, types,rangeType);
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
    @LogNote(value = "批量转发流程", action = LogConstans.LOG_TYPE_LCZF, custom = LogConstans.LOG_TYPE_LCZF)
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
        try {
            bdcSignService.signCheck(Lists.newArrayList(workFlowDTO));
            forwardYzDTO.generateBtx(bdcBtxYzService.checkBtx(workFlowDTO.getFormKey(), workFlowDTO.getProcessInstanceId(), workFlowDTO.getTaskId()));
            return CollectionUtils.isEmpty(forwardYzDTO.getBdcBtxyzVOS());
        } catch (Exception e) {
            forwardYzDTO.generateSignError();
            return false;
        }
    }

    /**
     * 批量退回规则验证（数据异常也会检验）
     *
     * @param workFlowDTO  工作流DTO
     * @param forwardYzDTO 转发验证DTO
     * @return {boolean} 规则验证通过 true, 未通过或者出现异常返回 false
     */
    private boolean plThYzgz(WorkFlowDTO workFlowDTO, ForwardYzDTO forwardYzDTO) {
        // 批量转发数据验证
        if (StringUtils.isAnyBlank(workFlowDTO.getProcessDefKey(), workFlowDTO.getProcessInstanceId())) {
            forwardYzDTO.generateDataError();
            return false;
        }
        // 忽略的项目不需要走规则验证
        if (!workFlowDTO.isIgnore()) {
            try {
                UserDto userDto =userManagerUtils.getCurrentUser();
                List<BdcGzYzTsxxDTO> listBdcGzYzTsxx = bdcGzyzDealService.yzgz(workFlowDTO.getProcessInstanceId(), CommonConstantUtils.GZYZ_MB_TH, workFlowDTO.getProcessDefKey(),userDto);
                forwardYzDTO.generateGzyz(checkTsxx(listBdcGzYzTsxx));
                return CollectionUtils.isEmpty(forwardYzDTO.getBdcGzyzVOS());
            } catch (Exception e) {
                //获取规则的时候会抛出异常，当code为 101 时表示未配置验证项，不做处理
                GtFeignException gte = null;
                if (e.getCause() instanceof GtFeignException) {
                    gte = (GtFeignException) e.getCause();
                    if (gte != null) {
                        String responseBody = gte.getMsgBody();
                        Map bodyMap = JSONObject.parseObject(responseBody, Map.class);
                        if (MapUtils.getInteger(bodyMap, "code") != null && StringUtils.isNotBlank(MapUtils.getString(bodyMap, "msg"))) {
                            Integer errorCode = MapUtils.getInteger(bodyMap, "code");
                            if (errorCode != 101) {
                                forwardYzDTO.generateGzyzError();
                            } else {
                                return true;
                            }
                        }
                    }
                } else {
                    forwardYzDTO.generateGzyzError();
                }
                return false;
            }
        } else {
            return true;
        }
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
        //判定是否走平台
        boolean isptyz = flowableNodeClient.isPlatformVerify(taskid);
        //平台验证
        if (isptyz) {
            return bdcGzyzDealService.ptyz(taskid);
        }
        UserDto userDto =userManagerUtils.getCurrentUser();
        List<BdcGzYzTsxxDTO> listBdcGzYzTsxx = bdcGzyzDealService.yzgz(gzlslid, CommonConstantUtils.GZYZ_MB_ZF, processKey,userDto);
        return checkTsxx(listBdcGzYzTsxx);
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
    @LogNote(value = "自动转发", action = LogConstans.LOG_TYPE_LCZF, custom = LogConstans.LOG_TYPE_LCZF)
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
        List<Future<ForwardYzDTO>> futures = bdcForwardService.zfyz(workFlowDTO, autoSign);
        LOGGER.info("验证判断是否可以转发: {}", JSON.toJSONString(futures));
        // 处理返回结果
        ForwardYzDTO yzDTO = new ForwardYzDTO();
        yzDTO.setGzlslid(workFlowDTO.getProcessInstanceId());
        yzDTO.setSlbh(workFlowDTO.getSlbh());
        for (Future<ForwardYzDTO> future : futures) {
            ForwardYzDTO forwardYzDTO = future.get();
            if (forwardYzDTO == null) {
                continue;
            }
            // 组合 必填项验证 和 规则验证 的结果
            if (CollectionUtils.isNotEmpty(forwardYzDTO.getBdcBtxyzVOS())) {
                yzDTO.setBdcBtxyzVOS(forwardYzDTO.getBdcBtxyzVOS());
                return yzDTO;
            }
            if (CollectionUtils.isNotEmpty(forwardYzDTO.getBdcGzyzVOS())) {
                yzDTO.setBdcGzyzVOS(forwardYzDTO.getBdcGzyzVOS());
                LOGGER.info("规则验证结果: {}", JSON.toJSONString(yzDTO));
                return yzDTO;
            }
        }
        LOGGER.info("验证结果: {}", JSON.toJSONString(yzDTO));
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
        if (StringUtils.isBlank(taskId)) {
            throw new AppException("转发验证必填项确实taskId");
        }
        TaskData taskData = processTaskClient.getTaskById(taskId);
        if (Objects.isNull(taskData)) {
            throw new AppException("根据taskid" + taskId + "未获取到任务信息");
        }
        WorkFlowDTO workFlowDTO = new WorkFlowDTO();
        workFlowDTO.setTaskId(taskData.getTaskId());
        workFlowDTO.setTaskName(taskData.getTaskName());
        workFlowDTO.setProcessDefKey(taskData.getProcessKey());
        workFlowDTO.setProcessInstanceId(taskData.getProcessInstanceId());
        workFlowDTO.setFormKey(taskData.getFormKey());
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
        List<Future<ForwardYzDTO>> futures = bdcForwardService.zfyzBtx(workFlowDTO, autoSign);
        LOGGER.info("验证判断是否可以转发: {}", JSON.toJSONString(futures));
        // 处理返回结果
        ForwardYzDTO yzDTO = new ForwardYzDTO();
        yzDTO.setGzlslid(workFlowDTO.getProcessInstanceId());
        yzDTO.setSlbh(workFlowDTO.getSlbh());
        for (Future<ForwardYzDTO> future : futures) {
            ForwardYzDTO forwardYzDTO = future.get();
            if (forwardYzDTO == null) {
                continue;
            }
            // 组合 必填项验证 和 规则验证 的结果
            if (CollectionUtils.isNotEmpty(forwardYzDTO.getBdcBtxyzVOS())) {
                yzDTO.setBdcBtxyzVOS(forwardYzDTO.getBdcBtxyzVOS());
                LOGGER.info("规则验证结果: {}", JSON.toJSONString(yzDTO));
                if (CollectionUtils.isNotEmpty(yzDTO.getBdcBtxyzVOS())) {
                    throw new AppException("以下表单有为空项，请填写完整" + JSON.toJSONString(yzDTO.getBdcBtxyzVOS()));
                }
                return yzDTO;
            }
        }
        LOGGER.info("验证结果: {}", JSON.toJSONString(yzDTO));
        return yzDTO;
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
        if (StringUtils.isBlank(taskId)) {
            throw new AppException("转发验证必填项确实taskId");
        }
        TaskData taskData = processTaskClient.getTaskById(taskId);
        if (Objects.isNull(taskData)) {
            throw new AppException("根据taskid" + taskId + "未获取到任务信息");
        }
        WorkFlowDTO workFlowDTO = new WorkFlowDTO();
        workFlowDTO.setTaskId(taskData.getTaskId());
        workFlowDTO.setTaskName(taskData.getTaskName());
        workFlowDTO.setProcessDefKey(taskData.getProcessKey());
        workFlowDTO.setProcessInstanceId(taskData.getProcessInstanceId());
        workFlowDTO.setFormKey(taskData.getFormKey());
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
        List<Future<ForwardYzDTO>> futures = bdcForwardService.zfyzZhgz(workFlowDTO, autoSign);
        LOGGER.info("验证判断是否可以转发: {}", JSON.toJSONString(futures));
        // 处理返回结果
        ForwardYzDTO yzDTO = new ForwardYzDTO();
        yzDTO.setGzlslid(workFlowDTO.getProcessInstanceId());
        yzDTO.setSlbh(workFlowDTO.getSlbh());
        for (Future<ForwardYzDTO> future : futures) {
            ForwardYzDTO forwardYzDTO = future.get();
            if (forwardYzDTO == null) {
                continue;
            }
            if (CollectionUtils.isNotEmpty(forwardYzDTO.getBdcGzyzVOS())) {
                yzDTO.setBdcGzyzVOS(forwardYzDTO.getBdcGzyzVOS());
                LOGGER.info("规则验证结果: {}", JSON.toJSONString(yzDTO));
                if (CollectionUtils.isNotEmpty(yzDTO.getBdcGzyzVOS())) {
                    throw new AppException(JSON.toJSONString(yzDTO.getBdcBtxyzVOS()));
                }
                return yzDTO;
            }
        }
        LOGGER.info("验证结果: {}", JSON.toJSONString(yzDTO));
        return yzDTO;
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
     * 获取非登记业务流程
     *
     * @return {String} 返回配置内容
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/getFdjywlc")
    @ApiOperation(value = "获取非登记业务流程")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public BdcTslcVO getFdjywlc() {
        BdcTslcVO bdcTslcVO = new BdcTslcVO();
        bdcTslcVO.setFdjywlc(fdjywlc);
        if (tslcs != null && CollectionUtils.isNotEmpty(tslcs.getBdcTslcDTO())) {
            bdcTslcVO.setTslcList(tslcs.getBdcTslcDTO());
        }
        return bdcTslcVO;
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
     * @param gzlslid gzlslid
     * @param taskId  taskId
     * @param jsyy    解锁原因
     * @return boolean
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 补录修改流程办结
     */
    @PostMapping("/end/blmodify")
    @ApiOperation(value = "激活流程")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInstanceIds", value = "流程实例id集合", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "reason", value = "激活原因", paramType = "query", dataType = "string")})
    @ResponseStatus(HttpStatus.OK)
    public void blModifyEnd(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "taskId") String taskId,
                            @RequestParam(value = "jsyy", required = false) String jsyy) {
        if (StringUtils.isAnyBlank(gzlslid, taskId)) {
            throw new MissingArgumentException("工作流实例ID、任务ID不能为空！");
        }
        bdcXxblFeignService.endModify(gzlslid, jsyy);
        processEnd(taskId);
    }

    /**
     * @return {boolean}
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 判断是否属于补录修改流程
     */
    @GetMapping("/blmodify")
    @ApiOperation(value = "获取转发的用户")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "username", value = "username", paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public Object isBlModify(@RequestParam(value = "gzlslid") String gzlslid) {
        Map<String, Boolean> result = new HashMap<>(2);
        if (StringUtils.isBlank(blModifyKey)) {
            result.put("bllc", false);
        }
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("办结的工作流实例ID不能为空！");
        }
        result.put("bllc", false);
        result.put("zssd", false);
        //  判断当前流程是否补录流程
        List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        LOGGER.warn("当前配置信息补录修改流程的流程定义id{},当前流程实例id{}", blModifyKey, gzlslid);
        for (BdcXmDTO bdcXmDTO : bdcXmDTOS) {
            if (StringUtils.equals(blModifyKey, bdcXmDTO.getGzldyid())) {
                result.put("bllc", true);
                break;
            }
        }

        // 判断证书是否锁定
        // 获取原项目id
        String yxmid = getYxmid(gzlslid);
        if(StringUtils.isNotBlank(yxmid)){
            List<BdcZssdDO> bdcZssdDOSd = getBdcZssdDOS(yxmid);
            if (CollectionUtils.isNotEmpty(bdcZssdDOSd) && xxblxgJsyy) {
                result.put("zssd", true);
            } else {
                result.put("zssd", false);
            }
        }
        return result;
    }

    // 获取原项目id
    private String getYxmid(String processInsId) {
        List<BdcXmLsgxDO> bdcXmLsgxDOS = bdcLsgxFeignService.listXmLsgxBySlid(processInsId);
        if (CollectionUtils.isNotEmpty(bdcXmLsgxDOS)) {
            return bdcXmLsgxDOS.get(0).getYxmid();
        }
        return null;
    }

    // 查询证书锁定信息
    private List<BdcZssdDO> getBdcZssdDOS(String xmid) {
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setXmid(xmid);
        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
        List<BdcZssdDO> bdcZssdDOSd = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcZsDOS)) {
            for (BdcZsDO bdcZsDO : bdcZsDOS) {
                // 确认证书是否已被锁定过
                BdcZssdDO bdcZssdDO = new BdcZssdDO();
                bdcZssdDO.setSdzt(CommonConstantUtils.SDZT_SD);
                bdcZssdDO.setZsid(bdcZsDO.getZsid());
                bdcZssdDO.setCqzh(bdcZsDO.getBdcqzh());
                List<BdcZssdDO> bdcZssdDOSdzt = bdcSdFeignService.queryBdczsSd(bdcZssdDO);
                if (CollectionUtils.isNotEmpty(bdcZssdDOSdzt)) {
                    bdcZssdDOSd.addAll(bdcZssdDOSdzt);
                }
            }
        }
        return bdcZssdDOSd;
    }

    /**
     * 流程设置优先级
     *
     * @param workFlowDTO 设置优先级的参数 gzlslid 必填，reason 加急原因
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    @PostMapping("/priority")
    @ApiOperation(value = "设置优先级")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "workFlowDTO", value = "加急原因、工作流实例id", paramType = "body", dataType = "WorkFlowDTO")})
    @ResponseStatus(HttpStatus.OK)
    @LogNote(value = "流程加急", action = Constants.LOG_ACTION_PROCESS_URGENT)
    public void setPriority(@RequestBody WorkFlowDTO workFlowDTO) {
        if (workFlowDTO == null || StringUtils.isBlank(workFlowDTO.getProcessInstanceId())) {
            throw new MissingArgumentException("gzlslid");
        }
        LOGGER.warn("流程设置优先级：{}", JSONObject.toJSONString(workFlowDTO));
        // 默认设置为特急
        processInstanceClient.setProcPriority(workFlowDTO.getProcessInstanceId(), PriorityStatus.EXTRA_URGENT.getValue(),
                workFlowDTO.getReason());
    }

    /**
     * 流程取消加急
     *
     * @param workFlowDTO 设置优先级的参数 gzlslid 必填，reason 加急原因
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    @PostMapping("/canclePriority")
    @ApiOperation(value = "设置优先级")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "workFlowDTO", value = "工作流实例id", paramType = "body", dataType = "WorkFlowDTO")})
    @ResponseStatus(HttpStatus.OK)
    @LogNote(value = "流程取消加急", action = Constants.LOG_ACTION_PROCESS_CANCEL_URGENT)
    public void cancleLcPriority(@RequestBody WorkFlowDTO workFlowDTO) {
        if (workFlowDTO == null || StringUtils.isBlank(workFlowDTO.getProcessInstanceId())) {
            throw new MissingArgumentException("gzlslid");
        }
        LOGGER.warn("流程取消加急：{}", JSONObject.toJSONString(workFlowDTO));
        // 默认设置为特急
        processInstanceClient.setProcPriority(workFlowDTO.getProcessInstanceId(), PriorityStatus.NORMAL.getValue(), null);
    }

    /**
     * 任务设置优先级
     *
     * @param workFlowDTO 设置优先级的参数 gzlslid 必填，reason 加急原因
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    @PostMapping("/task/priority")
    @ApiOperation(value = "设置优先级")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "workFlowDTO", value = "加急原因、工作流实例id", paramType = "body", dataType = "WorkFlowDTO")})
    @ResponseStatus(HttpStatus.OK)
    @LogNote(value = "任务加急", action = Constants.LOG_ACTION_TASK_URGENT)
    public void setTaskPriority(@RequestBody WorkFlowDTO workFlowDTO) {
        if (workFlowDTO == null || StringUtils.isBlank(workFlowDTO.getTaskId())) {
            throw new MissingArgumentException("taskid");
        }
        LOGGER.warn("任务设置优先级：{}", JSONObject.toJSONString(workFlowDTO));
        // 默认设置为特急
        processInstanceClient.setTaskPriority(workFlowDTO.getTaskId(), PriorityStatus.EXTRA_URGENT.getValue(),
                workFlowDTO.getReason());
    }

    /**
     * @return void
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [workFlowDTO]
     * @description 任务取消加急
     */
    @PostMapping("/task/canclePriority")
    @ApiOperation(value = "取消加急")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "workFlowDTO", value = "加急原因、工作流实例id", paramType = "body", dataType = "WorkFlowDTO")})
    @ResponseStatus(HttpStatus.OK)
    @LogNote(value = "任务取消加急", action = Constants.LOG_ACTION_TASK_CANCEL_URGENT)
    public void canclePriority(@RequestBody WorkFlowDTO workFlowDTO) {
        if (workFlowDTO == null || StringUtils.isBlank(workFlowDTO.getTaskId())) {
            throw new MissingArgumentException("taskid");
        }
        LOGGER.warn("任务取消加急：{}", JSONObject.toJSONString(workFlowDTO));
        // 任务设置为普通
        processInstanceClient.setTaskPriority(workFlowDTO.getTaskId(), PriorityStatus.NORMAL.getValue(), null);
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

        return bdcShxxFeignService.hasSameShr(gzlslids, userDto.getUsername());
    }

    /**
     * 未办结业务补偿服务</br>
     * 针对需求 42397 提供的补偿接口服务
     *
     * @param szrid 缮证人id
     * @return {int} 更新数据
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    @PostMapping("/task/makeup")
    @ApiOperation(value = "未办结业务补偿服务")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "szrid", value = "缮证人id", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "autoCompleteDtoList", value = "处理单个数据传入参数 （传入此参数则只处理参数中的数据）", paramType = "body", dataType = "AutoCompleteDto")})
    @ResponseStatus(HttpStatus.OK)
    public int makeUpEndProcess(@RequestParam(value = "szrid", required = false) String szrid, @RequestBody(required = false) List<AutoCompleteDTO> autoCompleteDtoList) {
        // autoCompleteDtoList 传入数据则 只 处理传入的数据
        if (CollectionUtils.isNotEmpty(autoCompleteDtoList)) {
            LOGGER.warn("未办结业务补偿服务传入 传入集合信息：{}", JSON.toJSONString(autoCompleteDtoList));
            syncAutoCmpleteClient.syncAutoCmpleteDataList(autoCompleteDtoList);

            List<BdcZsXmDTO> bdcZsXmDTOS = autoCompleteDtoList.stream().map(autoCompleteDto -> {
                BdcZsXmDTO bdcZsXmDTO = new BdcZsXmDTO();
                bdcZsXmDTO.setGzlslid(autoCompleteDto.getProcessInsId());
                bdcZsXmDTO.setSzsj(autoCompleteDto.getEndTime());
                return bdcZsXmDTO;
            }).collect(Collectors.toList());
            LOGGER.warn("未办结业务补偿服务传入 批量处理证书表中发证时间：{}", JSON.toJSONString(bdcZsXmDTOS));
            // 更新工作流事件导致的数据问题 fzsj
            return bdcZsFeignService.updateWbjxm(bdcZsXmDTOS);
        }

        if (StringUtils.isBlank(szrid)) {
            throw new MissingArgumentException("szrid");
        }

        LOGGER.warn("未办结业务补偿服务传入 缮证人：{}", szrid);
        // 查询缮证人名称为 szr 且 ajzt 不等于 2 ｛未办结｝
        List<BdcZsXmDTO> bdcZsXmDTOS = bdcZsFeignService.listWbjywxx(szrid);
        if (CollectionUtils.isEmpty(bdcZsXmDTOS)) {
            LOGGER.warn("未查询到问题数据");
            return 0;
        }

        // 处理数据
        bdcForwardService.makeUpWbjyw(bdcZsXmDTOS, szrid);

        // 更新工作流事件导致的数据问题 fzsj
        LOGGER.warn("未办结业务补偿服务传入 批量处理证书表中发证时间：{}", JSON.toJSONString(bdcZsXmDTOS));
        return bdcZsFeignService.updateWbjxm(bdcZsXmDTOS);
    }

    @GetMapping("/lcsc/withCzrz/{processInsId}")
    @ApiOperation(value = "删除当前流程并记录操作日志")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例id", paramType = "body", dataType = "string")})
    @ResponseStatus(HttpStatus.OK)
    public void deleteProcessWithCzrz(@PathVariable(name = "processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("删除当前流程gzlslid不能为空！");
        }
        // 获取流程审批意见
        List<OpinionDto> opinionDtoList = this.processTaskClient.queryProcessOpinions(processInsId, null, CommentType.FORWARD_BEFORE_OPINION.value());
        StringJoiner joiner = new StringJoiner(";");
        if (CollectionUtils.isNotEmpty(opinionDtoList)) {
            for (OpinionDto opinionDto : opinionDtoList) {
                joiner.add(opinionDto.getUserAlisa() + ":" + opinionDto.getOpinion());
            }
        }
        // 删除流程
        boolean isDelete = taskUtils.deleteTask(processInsId, null);

        if (isDelete) {
            try {
                // 删除成功记录操作日志
                bdcCzrzFeignService.addScCzrzWithOpinion(processInsId, joiner.toString());
            } catch (Exception e) {
                LOGGER.error("记录删除操作日志失败，工作流实例ID:{}", processInsId, e);
            }
            try {
                // 更改项目状态
                bdcDbxxFeignService.cancelProcessQsztAndAjzt(processInsId);
            } catch (Exception e) {
                LOGGER.error("更新项目案件状态失败，工作流实例ID:{}", processInsId, e);
            }

        }
    }


    /**
     * @param processInstanceId taskId type
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取当前节点之前的流程退回意见--》当前节点之前的操作时的退回意见
     */
    @PostMapping("/opinion/back")
    @ApiOperation(value = "获取当前节点之前的流程退回意见", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInstanceId", value = "流程实例id", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "taskId", value = "任务id", paramType = "query", dataType = "string")
    })
    @ResponseStatus(HttpStatus.OK)
    public Object queryBackOpinion(@RequestParam(name = "processInstanceId") String processInstanceId,
                                   @RequestParam(name = "taskId", required = false) String taskId,
                                   @RequestParam(name = "type") String type) {
        if (StringUtils.isAnyBlank(processInstanceId, type)) {
            throw new MissingArgumentException("参数流程实例id，任务id，意见类型不能为空！");
        }
        if (StringUtils.isBlank(taskId) && StringUtils.equals(type, CommentType.BACK_OPINION.value())) {
            throw new MissingArgumentException("退回意见的 任务id不能为空！");
        }
        List<TaskData> taskData = processTaskClient.listProcessTask(processInstanceId);
        if (CollectionUtils.isEmpty(taskData)) {
            return null;
        }
        //找出当前的节点信息
        TaskData currentTask = processTaskClient.getTaskById(taskId);

        if (Objects.isNull(currentTask)) {
            throw new MissingArgumentException("退回意见的 未找到当前任务！");
        }

        //取之前任务中同样节点名称的回退意见
        List<TaskData> taskDataList = taskData.stream().filter(task -> task.getTaskName().equals(currentTask.getTaskName())).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        for (TaskData data : taskDataList) {
            if (MapUtils.isNotEmpty(data.getComments()) && StringUtils.isNotBlank(data.getComments().get(type))) {
                stringBuilder.append(DateUtils.formateTime(data.getEndTime(),DateUtils.DATE_FORMAT))
                        .append(":")
                        .append(data.getComments().get(type))
                        .append("</br>");
            }
        }
        if (stringBuilder.length() > 0) {
            OpinionDto opinionDto = new OpinionDto();
            opinionDto.setOpinion(stringBuilder.toString());
            return opinionDto;
        }

        return null;
    }

    /**
     * @param
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 南通市获取审核节点退回提示 受理编号前缀配置
     * @date : 2022/8/1
     */
    @GetMapping("/getShjdthtsSlbhStartwith")
    @ApiOperation(value = "获取审核节点退回提示受理编号前缀配置")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public List<String> getShjdthtsSlbhStartwith() {
        return shjdthtsSlbhList;
    }

    /**
     * @param
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 获取流程图标题是否展示受理编号、不动产单元号
     * @date : 2022/12/20
     */
    @GetMapping("/getLctTitle")
    @ApiOperation(value = "获取流程图标题是否展示受理编号、不动产单元号")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public boolean getLctTitle() {
        return lctTitle;
    }

}
