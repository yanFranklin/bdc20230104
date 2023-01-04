package cn.gtmap.realestate.common.core.dto.exchange;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022-10-06
 * @description 工作流请求参数
 */
public class WorkFlowRequest {

    /**
     * 工作流实例ID
     */
    private String processInsId;
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 意见
     */
    private String opinion;
    /**
     * 是否删除
     */
    private String isDelete;

    public String getProcessInsId() {
        return processInsId;
    }

    public void setProcessInsId(String processInsId) {
        this.processInsId = processInsId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "WorkFlowRequest{" +
                "processInsId='" + processInsId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", opinion='" + opinion + '\'' +
                ", isDelete='" + isDelete + '\'' +
                '}';
    }
}
