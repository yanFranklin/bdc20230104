package cn.gtmap.realestate.config.job.alarm;


import cn.gtmap.realestate.common.core.domain.job.BdcJobInfoDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobLogDO;

/**
 * @author  2020-01-19
 */
public interface JobAlarm {

    /**
     * job alarm
     *
     * @param info
     * @param bdcJobLogDO
     * @return
     */
    public boolean doAlarm(BdcJobInfoDO info, BdcJobLogDO bdcJobLogDO);

}
