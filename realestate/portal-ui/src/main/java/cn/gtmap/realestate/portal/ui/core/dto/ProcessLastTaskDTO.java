package cn.gtmap.realestate.portal.ui.core.dto;

import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;

public class ProcessLastTaskDTO {
    private ProcessInstanceData processInstanceData;
    private String taskId;
    private String startUserId;

    public String getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(String startUserId) {
        this.startUserId = startUserId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public ProcessInstanceData getProcessInstanceData() {
        return processInstanceData;
    }

    public void setProcessInstanceData(ProcessInstanceData processInstanceData) {
        this.processInstanceData = processInstanceData;
    }
}
