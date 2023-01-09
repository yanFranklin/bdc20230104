package cn.gtmap.realestate.config.job.complete;

import cn.gtmap.realestate.common.core.domain.job.JobInfo;
import cn.gtmap.realestate.common.core.domain.job.JobLog;
import cn.gtmap.realestate.common.job.biz.model.ReturnT;
import cn.gtmap.realestate.common.job.context.XxlJobContext;
import cn.gtmap.realestate.config.job.conf.XxlJobAdminConfig;
import cn.gtmap.realestate.config.job.thread.JobTriggerPoolHelper;
import cn.gtmap.realestate.config.job.trigger.TriggerTypeEnum;
import cn.gtmap.realestate.config.job.util.I18nUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * @author xuxueli 2020-10-30 20:43:10
 */
public class XxlJobCompleter {
    private static Logger logger = LoggerFactory.getLogger(XxlJobCompleter.class);

    /**
     * common fresh handle entrance (limit only once)
     *
     * @param jobLog
     * @return
     */
    public static int updateHandleInfoAndFinish(JobLog jobLog) {

        // finish
        finishJob(jobLog);

        // text最大64kb 避免长度过长
        if (jobLog.getHandleMsg().length() > 15000) {
            jobLog.setHandleMsg( jobLog.getHandleMsg().substring(0, 15000) );
        }

        // fresh handle
        return XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().updateHandleInfo(jobLog);
    }


    /**
     * do somethind to finish job
     */
    private static void finishJob(JobLog jobLog){

        // 1、handle success, to trigger child job
        String triggerChildMsg = null;
        if (XxlJobContext.HANDLE_CODE_SUCCESS == jobLog.getHandleCode()) {
            JobInfo jobInfo = XxlJobAdminConfig.getAdminConfig().getXxlJobInfoDao().loadById(jobLog.getJobId());
            if (jobInfo !=null && jobInfo.getChildJobId()!=null && jobInfo.getChildJobId().trim().length()>0) {
                triggerChildMsg = "<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>"+ I18nUtil.getString("jobconf_trigger_child_run") +"<<<<<<<<<<< </span><br>";

                String[] childJobIds = jobInfo.getChildJobId().split(",");
                for (int i = 0; i < childJobIds.length; i++) {
                    int childJobId = (childJobIds[i]!=null && childJobIds[i].trim().length()>0 && isNumeric(childJobIds[i]))?Integer.valueOf(childJobIds[i]):-1;
                    if (childJobId > 0) {

                        JobTriggerPoolHelper.trigger(childJobId, TriggerTypeEnum.PARENT, -1, null, null, null);
                        ReturnT<String> triggerChildResult = ReturnT.SUCCESS;

                        // add msg
                        triggerChildMsg += MessageFormat.format(I18nUtil.getString("jobconf_callback_child_msg1"),
                                (i+1),
                                childJobIds.length,
                                childJobIds[i],
                                (triggerChildResult.getCode()==ReturnT.SUCCESS_CODE?I18nUtil.getString("system_success"):I18nUtil.getString("system_fail")),
                                triggerChildResult.getMsg());
                    } else {
                        triggerChildMsg += MessageFormat.format(I18nUtil.getString("jobconf_callback_child_msg2"),
                                (i+1),
                                childJobIds.length,
                                childJobIds[i]);
                    }
                }

            }
        }

        if (triggerChildMsg != null) {
            jobLog.setHandleMsg( jobLog.getHandleMsg() + triggerChildMsg );
        }

        // 2、fix_delay trigger next
        // on the way

    }

    private static boolean isNumeric(String str){
        try {
            int result = Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
