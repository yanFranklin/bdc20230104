package cn.gtmap.realestate.config.job.alarm;


import cn.gtmap.realestate.common.core.domain.job.JobInfo;
import cn.gtmap.realestate.common.core.domain.job.JobLog;

/**
 * @author xuxueli 2020-01-19
 */
public interface JobAlarm {

    /**
     * job alarm
     *
     * @param info
     * @param jobLog
     * @return
     */
    public boolean doAlarm(JobInfo info, JobLog jobLog);

}
