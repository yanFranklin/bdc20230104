package cn.gtmap.realestate.common.matcher;

import cn.gtmap.gtc.workflow.clients.manage.TaskHandleClient;
import cn.gtmap.gtc.workflow.domain.common.BaseResult;
import cn.gtmap.gtc.workflow.domain.common.ProcessOptResultDto;
import cn.gtmap.gtc.workflow.domain.manage.BackNodesDto;
import cn.gtmap.gtc.workflow.domain.manage.BackTaskDto;
import cn.gtmap.gtc.workflow.domain.manage.EventExtendDTO;
import cn.gtmap.gtc.workflow.domain.manage.ForwardTaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/03/24
 * @description  大云转发活动 V2.x版本适配
 */
@Component
public class TaskHandleClientMatcher {
    @Autowired
    private TaskHandleClient taskHandleClient;

    /**
     * @param forwardTaskDtos
     * @return
     * @description 执行转发活动任务
     */
    public boolean complete(List<ForwardTaskDto> forwardTaskDtos) {
       return taskHandleClient.complete(forwardTaskDtos, true);
    }

    /**
     * @param processInstanceId 流程实例ID
     * @return
     * @description 根据流程实例id删除流程
     */
    public boolean deleteProcessWithReason(String processInstanceId, String reason, EventExtendDTO eventExtendDTO) {
        return taskHandleClient.deleteProcessWithReason(processInstanceId, reason, eventExtendDTO);
    }

    /**
     * @param backTaskDtoList
     * @return
     * @description 执行退回活动任务
     */
    public boolean back(List<BackTaskDto> backTaskDtoList) {
        return taskHandleClient.back(backTaskDtoList);
    }

    /**
     * 批量挂起
     *
     * @param processInsIds
     */
    public List<ProcessOptResultDto> batchTaskHang(List<String> processInsIds, String reason) {
        return taskHandleClient.batchTaskHang(processInsIds, reason);
    }

    /**
     * 批量激活流程
     *
     * @param processInsIds
     */
    public List<ProcessOptResultDto> batchTaskActivation(List<String> processInsIds, String reasons) {
        return taskHandleClient.batchTaskActivation(processInsIds, reasons);
    }

    /**
     * 批量转发同流程同节点转发
     * @param taskIds
     * @param forwardTaskDtos
     * @return
     */
    public List<ProcessOptResultDto> batchCompleteSameNode(String taskIds, List<ForwardTaskDto> forwardTaskDtos) {
        return taskHandleClient.batchCompleteSameNode(taskIds, forwardTaskDtos);
    }

    /**
     * @param forwardTaskDto
     * @return
     * @description 办结活动任务 (参数按照V1.X版本组织，对业务层统一)
     */
    public Boolean processEnd(ForwardTaskDto forwardTaskDto) {
        return taskHandleClient.processEnd(forwardTaskDto, true, true);
    }

    /**
     * 任务激活
     *
     * @param processInstanceId
     */
    public boolean taskActivation(String processInstanceId, String reason) {
        return taskHandleClient.taskActivation(processInstanceId, reason);
    }

    /**
     * 任务认领
     * @param username
     * @return
     */
    public boolean taskClaim(String username, List<String> taskIds) {
        return taskHandleClient.taskClaim(username, taskIds);
    }

    /**
     * 退回到首节点
     * @param backTaskDto
     * @return
     */
    public boolean backToFirst(BackTaskDto backTaskDto) {
        return taskHandleClient.backToFirst(backTaskDto);
    }

    /**
     * @param
     * @return 自动转发办结
     */
    public boolean autoComplete(String processInstanceId, String username) {
        return taskHandleClient.autoComplete(processInstanceId, username);
    }

    /**
     * 取消认领任务
     */
    public boolean unClaimTask(String taskId, String opinion) {
        return taskHandleClient.unClaimTask(taskId, opinion);
    }

    /**
     * @param taskId
     * @return
     * @description 删除任务
     */
    public boolean deleteTask(String taskId) {
        return taskHandleClient.deleteTask(taskId);
    }

    /**
     * 批量退回流程
     *
     * @param taskIds
     * @param opinion
     * @return
     */
    public List<ProcessOptResultDto> batchBack(List<String> taskIds, String opinion) {
        return taskHandleClient.batchBack(taskIds, opinion);
    }

    /**
     * @param taskId : 任务ID
     * @return
     * @description 执行转发前验证
     */
    public BaseResult completeVerify(String taskId) {
        return taskHandleClient.completeVerify(taskId);
    }

    /**
     * 强制办结
     * @param processInsId
     * @param username
     * @return
     */
    public boolean mandatoryFinishProcess(String processInsId, String username) {
        return taskHandleClient.mandatoryFinishProcess(processInsId, username);
    }

    /**
     * 流程实例废弃
     *
     * @param processInstanceId
     * @param deleteReason
     */
    public boolean taskAbandoned(String processInstanceId, String deleteReason) {
        return taskHandleClient.taskAbandoned(processInstanceId, deleteReason);
    }

    /**
     * 退回任务
     * @param backNodesDto
     * @return
     */
    public boolean backForGeneral(BackNodesDto backNodesDto) {
        return taskHandleClient.backForGeneral(backNodesDto);
    }

    /**
     * 任务挂起
     *
     * @param processInstanceId
     */
    public boolean taskHang(String processInstanceId, String reason) {
        return taskHandleClient.taskHang(processInstanceId, reason);
    }

    /**
     * 批量取消认领流程
     * @param taskIds
     * @return
     */
    public List<ProcessOptResultDto> batchUnClaimTasks(List<String> taskIds, String opinion) {
        return taskHandleClient.batchUnClaimTasks(taskIds);
    }

    /**
     * @param taskId
     * @return
     * @description 执行取回活动任务
     */
    public boolean fetchBack(String taskId) {
        return taskHandleClient.fetchBack(taskId);
    }

    /**
     * @param taskIds
     * @return
     * @description 批量取回流程
     */
    public List<ProcessOptResultDto> batchFetchBack(List<String> taskIds) {
        return taskHandleClient.batchFetchBack(taskIds);
    }

    /**
     * 任务锁定
     *
     * @param taskId
     */
    public boolean taskLock(String taskId, String userName) {
        return taskHandleClient.taskLock(taskId, userName);
    }

    /**
     * @param taskIds 任务ID
     * @param opinion 意见
     * @param group   角色分组标识， organization: 撒选人员同组织下的角色  null: 所有角色
     * @return
     * @description
     */
    public List<ProcessOptResultDto> batchComplete(String taskIds, String opinion, String group) {
        return taskHandleClient.batchComplete(taskIds, opinion, group);
    }
}
