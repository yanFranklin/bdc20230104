package cn.gtmap.realestate.portal.ui.web.rest;

import cn.gtmap.gtc.clients.RoleManagerClient;
import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.FlowableNodeClientMatcher;
import cn.gtmap.realestate.common.matcher.TaskHandleClientMatcher;
import cn.gtmap.realestate.common.matcher.TaskUserClientMatcher;
import cn.gtmap.gtc.workflow.domain.manage.ForwardTaskDto;
import cn.gtmap.gtc.workflow.domain.manage.NodeVariableDTO;
import cn.gtmap.gtc.workflow.domain.manage.TransmitStatusDto;
import cn.gtmap.realestate.common.core.dto.portal.BdcZfhyzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSwFeignService;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.portal.ui.core.ForwardRuleFactory;
import cn.gtmap.realestate.portal.ui.core.dto.ForwardYzDTO;
import cn.gtmap.realestate.portal.ui.core.vo.ForWardTaskVO;
import cn.gtmap.realestate.portal.ui.core.vo.ForWardVO;
import cn.gtmap.realestate.portal.ui.service.BdcForwardService;
import cn.gtmap.realestate.portal.ui.service.GeneralForwardRule;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowAbstactService;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowServiceFactory;
import cn.gtmap.realestate.portal.ui.utils.Constants;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 流程操作服务接口 转发
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0  2020/11/12
 */
@RestController
@RequestMapping("/rest/v1.0/workflow/zrzy/process-instances/forward")
public class ZrzyForwardController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZrzyForwardController.class);

    @Autowired
    BdcForwardService bdcForwardService;
    @Autowired
    ForwardRuleFactory forwardRuleFactory;
    @Autowired
    private TaskHandleClientMatcher taskHandleClient;
    @Autowired
    private FlowableNodeClientMatcher flowableNodeClient;
    @Autowired
    private RoleManagerClient roleManagerClient;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private TaskUserClientMatcher taskUserClient;
    @Value("${html.version:}")
    private String version;
    /**
     * 角色分组标识,默认筛选人员同组织下的角色
     */
    @Value("${forward.group:true}")
    private String forwardGroup;

    /**
     * @param taskId
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取转发的活动
     */
    @GetMapping
    @ApiOperation(value = "获取转发的活动")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "taskId", value = "流程任务id", paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public List<ForwardTaskDto> listFordWardDtos(@RequestParam("taskId") String taskId) throws Exception {
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("转发任务的taskId不能为空！");
        }
        String group = null;
        if (StringUtils.equals(forwardGroup, "true")) {
            group = "organization";
        }
        return flowableNodeClient.getForwardUserTasksWithVariable(taskId, group, true, getNodeVariableDTOS(taskId));
    }


    /**
     * @param taskId
     * @return ForWardVO nodeType:节点类型 ->并行网关 独占网关
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取转发活动、角色、节点类型
     */
    @GetMapping("/taskAndRole")
    @ApiOperation(value = "获取转发活动、角色、节点类型")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "taskId", value = "流程任务id", paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public ForWardVO queryForwardTaskAndRole(@RequestParam("taskId") String taskId) throws Exception {
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("获取转发活动、角色、节点类型的taskId不能为空！");
        }
        String group = StringUtils.equals(forwardGroup, "true") ? "organization" : null;

        // 组织网关参数
        List<NodeVariableDTO> params = getNodeVariableDTOS(taskId);
        List<ForwardTaskDto> forwardTaskDtos = flowableNodeClient.getForwardUserTasksWithVariable(taskId, group, true, params);
        LOGGER.error("全部转发角色:{}", JSON.toJSONString(forwardTaskDtos));

        List<ForWardTaskVO> forWardTaskVOS = Lists.newArrayList();
        for (ForwardTaskDto forwardTaskDto : forwardTaskDtos) {
            forwardTaskDto.setTaskId(taskId);
            GeneralForwardRule dispatch = forwardRuleFactory.getGeneralForwardRule(forwardTaskDto.isDispatchEnable());
            forWardTaskVOS.add(dispatch.getForWardTaskVO(forwardTaskDto));
        }

        String nodeType = flowableNodeClient.getForwardNodeType(taskId);
        ForWardVO forWardVO = new ForWardVO();
        forWardVO.setForWardTaskVOList(forWardTaskVOS);
        forWardVO.setNodeType(nodeType);
        return forWardVO;
    }

    /**
     * 组织网关配置所需参数
     *
     * @param taskId taskId
     * @return {List} 网关配置参数
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private List<NodeVariableDTO> getNodeVariableDTOS(String taskId) {
        List<NodeVariableDTO> params = new ArrayList<>();
        BdcWorkFlowAbstactService workFlowService = BdcWorkFlowServiceFactory.getWorkFlowService(version);
        if (workFlowService != null) {
            params = workFlowService.getNodeVariableDTOS(taskId);
        }
        return params;
    }

    /**
     * @param forwardTaskDto
     * @return boolean
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 转发流程任务
     */
    @PostMapping
    @ApiOperation(value = "转发流程任务")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "forwardTaskDto", value = "转发流程任务", required = true, dataType = "ForwardTaskDto")})
    @ResponseStatus(HttpStatus.OK)
    public void forwardTask(ForwardTaskDto forwardTaskDto) {
        String taskId = forwardTaskDto.getTaskId();
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("转发流程的taskId不能为空！");
        }
        LOGGER.error("转发：{}", JSONObject.toJSONString(forwardTaskDto));
        if(StringUtils.isNotBlank(forwardTaskDto.getSelectUserNames())) {
            redisUtils.addStringValue("REDIS_ZFYH_" + forwardTaskDto.getTaskId(), forwardTaskDto.getSelectUserNames(), 30);
        }
        taskHandleClient.complete(Lists.newArrayList(forwardTaskDto));
    }

    /**
     * 转发流程并行任务
     * @param forwardTaskDtoList 多个任务节点
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/parallel")
    @ApiOperation(value = "转发流程并行任务")
    @ApiImplicitParams({@ApiImplicitParam(name = "forwardTaskDto", value = "转发流程任务", required = true, dataType = "ForwardTaskDto")})
    public void forwardTask(@RequestBody List<ForwardTaskDto> forwardTaskDtoList) {
        if (CollectionUtils.isEmpty(forwardTaskDtoList)) {
            throw new MissingArgumentException("未选择转发节点！");
        }

        for(ForwardTaskDto forwardTask : forwardTaskDtoList) {
            if(null == forwardTask || StringUtils.isBlank(forwardTask.getTaskId())) {
                throw new MissingArgumentException("转发节点taskId不能为空！");
            }
        }

        LOGGER.info("并行转发节点信息：{}", JSON.toJSONString(forwardTaskDtoList));
        taskHandleClient.complete(forwardTaskDtoList);
    }

    /**
     * @param username
     * @return
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 根据 username 获取用户信息
     */
    @GetMapping("/user")
    @ApiOperation(value = "获取转发的用户")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "username", value = "username", paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> queryUserByUsername(@RequestParam(value = "username") String username) {
        if (StringUtils.isBlank(username)) {
            throw new MissingArgumentException("获取转发的用户名不能为空！");
        }
        return Collections.singletonList(userManagerUtils.getUserByName(username));
    }

    /**
     * @param roleId
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取转发的用户
     */
    @GetMapping("/users")
    @ApiOperation(value = "获取转发的用户")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "roleId", value = "角色id", paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> listForwardUsers(@RequestParam(value = "roleId") String roleId , @RequestParam(value = "activityId") String activityId, @RequestParam(value = "taskId") String taskId) {
        if (StringUtils.isBlank(roleId)) {
            throw new MissingArgumentException("获取转发的用户roleId不能为空！");
        }
        return taskUserClient.getAllUsersByRoleId(activityId,taskId,1, Collections.singletonList(roleId));
    }


    /**
     * @param roleIds 角色ids
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取转发的角色
     */
    @GetMapping("/roles")
    @ApiOperation(value = "获取转发的角色")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "roleIds", value = "角色id", paramType = "query", dataType = "String")})
    @ResponseStatus(HttpStatus.OK)
    public List<RoleDto> listForWardRoles(@RequestParam(value = "roleIds") List<String> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            throw new MissingArgumentException("节点ID不能为空！");
        }
        return roleManagerClient.queryRolesByIds(roleIds);
    }

    /**
     * @param taskId 任务id
     * @return boolean
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 判断是否可以办结
     */
    @GetMapping("/isEnd")
    @ApiOperation(value = "判断是否可以办结")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "taskId", value = "流程任务id", paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public TransmitStatusDto isEnd(@RequestParam(value = "taskId") String taskId) {
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("判断是否可以办结的taskId不能为空！");
        }
        TransmitStatusDto transmitStatusDto = flowableNodeClient.verifyTransmitStatus(taskId);
        if (transmitStatusDto == null) {
            throw new AppException("未查询到是否节点可以办结");
        }
        return transmitStatusDto;
    }

    @PostMapping("/addZfcs/{taskid}")
    @ApiOperation(value = "添加转发参数至redis中")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskid", value = "任务id", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "map", value = "转发参数", paramType = "body", dataType = "map"),
    })
    @ResponseStatus(HttpStatus.OK)
    public void addZfcs(@PathVariable(value = "taskid") String taskid, @RequestBody Map<String, Object> map){
        if(StringUtils.isNotBlank(taskid) && MapUtils.isNotEmpty(map)){
            String redisKey = Constants.TASK_ZFCS_REDIS_PREFIX + taskid;
            for(Map.Entry<String, Object> entry : map.entrySet()){
                redisUtils.addHashValue(redisKey, entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
    }

    /**
     * 获取下级节点内容，并根据网关路径上的流程表达式过滤下级节点内容
     * <p>传递 {@code NodeVariableDTO} 参数，调用大云接口根据流程中配置的流程表达式进行过滤。
     * 没有符合的下级节点时，返回的空</p>
     * @param taskId 任务id
     * @param params 节点网关参数
     * @return
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/nextnode/flowexp/{taskId}")
    @ApiOperation(value = "添加转发参数至redis中")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskid", value = "任务id", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "map", value = "转发参数", paramType = "body", dataType = "map"),
    })
    @ResponseStatus(HttpStatus.OK)
    public Object getNextNodeByFlowExp(@PathVariable(value = "taskId") String taskId, @RequestBody List<NodeVariableDTO> params){
        if (StringUtils.isBlank(taskId)) {
            throw new MissingArgumentException("获取转发活动、角色、节点类型的taskId不能为空！");
        }
        String group = StringUtils.equals(forwardGroup, "true") ? "organization" : null;

        // 组织网关参数
        List<ForwardTaskDto> forwardTaskDtos = null;
        try {
            forwardTaskDtos = flowableNodeClient.getForwardUserTasksWithVariableWithNull(taskId, group, params);
        } catch (Exception e) {
            LOGGER.error("根据网关参数获取转发节点参数失败，{}", e.getMessage(), e);
        }
        LOGGER.info("转发节点:{}", JSON.toJSONString(forwardTaskDtos));

        return forwardTaskDtos;
    }

    @PostMapping("/zfhyz")
    @ApiOperation(value = "转发后验证")
    @ApiImplicitParam(name = "bdcZfhyzDTO", value = "转发后验证参数", paramType = "body", dataType = "BdcZfhyzDTO")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public Object zfhyz(BdcZfhyzDTO bdcZfhyzDTO)  {
        if (StringUtils.isAnyBlank(bdcZfhyzDTO.getGzlslid(), bdcZfhyzDTO.getActivityName(),bdcZfhyzDTO.getProcDefId())) {
            throw new MissingArgumentException("gzlslid, activityName,procDefId");
        }
        // 处理返回结果
        ForwardYzDTO yzDTO = new ForwardYzDTO();
        yzDTO.setGzlslid(bdcZfhyzDTO.getGzlslid());
//        //转发后验证
//        List<BdcGzyzVO> bdcGzyzVOList = bdcForwardService.zfhyz(bdcZfhyzDTO);
//        if(CollectionUtils.isNotEmpty(bdcGzyzVOList)){
//            yzDTO.setBdcGzyzVOS(bdcGzyzVOList);
//        }
        return yzDTO;

    }
}
