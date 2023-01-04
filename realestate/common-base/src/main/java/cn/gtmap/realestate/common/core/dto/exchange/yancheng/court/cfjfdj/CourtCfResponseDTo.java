package cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.cfjfdj;

import java.util.List;

public class CourtCfResponseDTo {

    private String slbh;

    private List<CourtCfxmxxResponseDTo> xmxx;

    /**
     * 任务id
     */
    private String taskId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<CourtCfxmxxResponseDTo> getXmxx() {
        return xmxx;
    }

    public void setXmxx(List<CourtCfxmxxResponseDTo> xmxx) {
        this.xmxx = xmxx;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }
}
