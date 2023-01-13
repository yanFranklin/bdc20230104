package cn.gtmap.realestate.common.core.domain.job;

import javax.persistence.Table;
import java.util.Date;
@Table(name = "BDC_JOB_LOG_REPORT")
public class BdcJobLogReportDO {

    private Integer id;

    private Date triggerDay;

    private Integer runningCount;
    private Integer sucCount;
    private Integer failCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTriggerDay() {
        return triggerDay;
    }

    public void setTriggerDay(Date triggerDay) {
        this.triggerDay = triggerDay;
    }

    public Integer getRunningCount() {
        return runningCount;
    }

    public void setRunningCount(Integer runningCount) {
        this.runningCount = runningCount;
    }

    public Integer getSucCount() {
        return sucCount;
    }

    public void setSucCount(Integer sucCount) {
        this.sucCount = sucCount;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }
}
