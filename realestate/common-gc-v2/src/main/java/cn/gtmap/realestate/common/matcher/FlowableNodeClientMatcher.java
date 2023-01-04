package cn.gtmap.realestate.common.matcher;

import cn.gtmap.gtc.workflow.clients.manage.FlowableNodeClient;
import cn.gtmap.gtc.workflow.domain.common.BaseResult;
import cn.gtmap.gtc.workflow.domain.manage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/03/24
 * @description  大云流程定义活动节点 V2.x版本适配
 */
@Component
public class FlowableNodeClientMatcher {
    @Autowired
    private FlowableNodeClient flowableNodeClient;

    /**
     * 根据任务id获取下一个节点列表 (参数按照V1.X版本组织)
     * @param taskId 任务id
     * @param group
     * @param completeBack 是否转发退回用户
     * @param variableDTOS 过滤节点
     * @return
     * @throws Exception
     */
    public List<ForwardTaskDto> getForwardUserTasksWithVariable(String taskId, String group, boolean completeBack, List<NodeVariableDTO> variableDTOS) throws Exception {
        return flowableNodeClient.getForwardUserTasksWithVariable(taskId, group, variableDTOS);
    }

    /**
     * @param taskId 任务活动id
     * @return TaskNodeType.value();
     * @description 转发下个节点是，获取下个节点的节点类型
     */
    public String getForwardNodeType(String taskId) {
        return flowableNodeClient.getForwardNodeType(taskId);
    }

    /**
     * 获取后续任务节点中有特定流程条件的用户任务节点接口(参数按照V1.X版本组织，对业务层统一)
     * @param taskId 任务id
     * @param group
     * @param variableDTOS 流程变量id
     * @return 返回节点数据
     * @throws Exception
     */
    public List<ForwardTaskDto> getForwardUserTasksWithVariableWithNull(String taskId, String group, List<NodeVariableDTO> variableDTOS) throws Exception {
        return flowableNodeClient.getForwardUserTasksWithVariable(taskId, group, variableDTOS);
    }

    /**
     * 根据流程实例id获取流程定义的所有任务节点
     */
    public List<UserTaskDto> getAllUserTaskByProcInsId(String gzlslid) {
        return flowableNodeClient.getAllUserTaskByProcInsId(gzlslid);
    }

    /**
     * 根据流程定义Key获取流程定义的所有人工节点
     */
    public List<UserTaskDto> getAllUserTaskByProcDefKey(String processDefKey) {
        return flowableNodeClient.getAllUserTaskByProcDefKey(processDefKey);
    }

    /**
     * 判断当前任务是否是首个人工节点
     * @param taskId
     * @return
     */
    public boolean isStartUserTask(String taskid) {
        return flowableNodeClient.isStartUserTask(taskid);
    }

    /**
     * @param taskId 任务活动id
     * @description 根据任务id获取退回到上个节点列表
     */
    public List<BackTaskDto> getBackUserTasks(String taskId) {
        return flowableNodeClient.getBackUserTasks(taskId);
    }

    /**
     * 转发判断是下个节点是否是结束或者当前节点是否强制办结
     * @param taskId
     * @return
     */
    public Map<String, Boolean> isForwardEndOrForceClosedEvent(String taskid) {
        return flowableNodeClient.isForwardEndOrForceClosedEvent(taskid);
    }

    /**
     * @param taskId 任务活动id'
     * @param group 角色分组标识， organization: 筛选人员同组织下的角色  null: 所有角色
     * @return TaskNodeType.value();
     * @description 根据任务id获取转发到下个节点列表
     */
    public List<ForwardTaskDto> getForwardUserTasks(String taskId, String group) {
        return flowableNodeClient.getForwardUserTasks(taskId, group);
    }

    /**
     * 判断是否允许退回
     */
    public BaseResult isAllowBack(String taskId) {
        return flowableNodeClient.isAllowBack(taskId);
    }

    /**
     * 判断当前任务是否是首个人工节点
     * @param processInstanceId
     * @return
     */
    public boolean isStartUserTaskRunning(String processInstanceId) {
        return flowableNodeClient.isStartUserTaskRunning(processInstanceId);
    }

    /**
     * 根据任务id获取退回到上个节点列表包含首节点和其他节点
     * @param taskId
     * @return
     */
    public BackNodesDto getBackUserTasksWithFirstNode(String taskId) {
        return flowableNodeClient.getBackUserTasksWithFirstNode(taskId);
    }

    /**
     * 判断是否允许取回
     */
    public BaseResult isAllowFetchBack(String taskId) {
        return flowableNodeClient.isAllowFetchBack(taskId);
    }

    /**
     * 根据任务id判断是否平台转发验证
     * @param taskId
     * @return
     */
    public boolean isPlatformVerify(String taskId) {
        return flowableNodeClient.isPlatformVerify(taskId);
    }

    /**
     * @param taskIds 任务活动id列表
     * @description 根据任务ID列表，判断批量转发是否需要人工选择转发节点
     */
    public BaseResult checkTasksForward(List<String> taskIds) {
        return flowableNodeClient.checkTasksForward(taskIds);
    }

    /**
     * 获取所有配置的任务节点列表
     * @return
     */
    public Collection<String> listAllTaskNames() {
        return flowableNodeClient.listAllTaskNames();
    }

    /**
     * 验证下一节点转发状态
     * @param taskId 任务id
     * @return TransmitStatus枚举类型 包括(办结,强制办结,正常转发)
     */
    public TransmitStatusDto verifyTransmitStatus(String taskId) {
        return flowableNodeClient.verifyTransmitStatus(taskId);
    }
}
