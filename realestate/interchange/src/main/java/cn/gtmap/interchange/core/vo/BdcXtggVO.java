package cn.gtmap.interchange.core.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: realestate
 * @description: 盐城外部定时任务系统调用exchange入参
 * @author: <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @create: 2021-04-21 15:55
 **/
public class BdcXtggVO implements Serializable {
    private static final long serialVersionUID = 4699885787198790202L;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 审批来源
     */
    private List<Integer> sply;

    /**
     * 公告类型
     */
    private List<Integer> gglx;

    /**
     * 工作流定义id
     */
    private String gzldyId;


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<Integer> getSply() {
        return sply;
    }

    public void setSply(List<Integer> sply) {
        this.sply = sply;
    }

    public List<Integer> getGglx() {
        return gglx;
    }

    public void setGglx(List<Integer> gglx) {
        this.gglx = gglx;
    }

    public String getGzldyId() {
        return gzldyId;
    }

    public void setGzldyId(String gzldyId) {
        this.gzldyId = gzldyId;
    }
}
