package cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.cfjfdj;

import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.jsdjbjjgxx.CourtJsdjbjjgxxRequestYwwxxDTO;

import java.util.List;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0
 * @date 2021/03/25 15:13
 */
public class CfjfdjResponseDTO {

    /**
     * 项目id
     */
    private String proid;

    /**
     * 业务受理编号
     */
    private String ywslbh;

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

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getYwslbh() {
        return ywslbh;
    }

    public void setYwslbh(String ywslbh) {
        this.ywslbh = ywslbh;
    }
}

