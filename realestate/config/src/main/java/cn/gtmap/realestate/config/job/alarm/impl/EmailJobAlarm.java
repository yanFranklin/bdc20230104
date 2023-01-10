package cn.gtmap.realestate.config.job.alarm.impl;


import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobInfoDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobLogDO;
import cn.gtmap.realestate.common.job.biz.model.ReturnT;
import cn.gtmap.realestate.config.job.alarm.JobAlarm;
import cn.gtmap.realestate.config.job.conf.XxlJobAdminConfig;
import cn.gtmap.realestate.config.job.util.I18nUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * job alarm by email
 *
 * @author  2020-01-19
 */
@Component
public class EmailJobAlarm implements JobAlarm {
    private static Logger logger = LoggerFactory.getLogger(EmailJobAlarm.class);

    /**
     * fail alarm
     *
     * @param bdcJobLogDO
     */
    @Override
    public boolean doAlarm(BdcJobInfoDO info, BdcJobLogDO bdcJobLogDO){
        boolean alarmResult = true;

        // send monitor email
        if (info!=null && info.getAlarmEmail()!=null && info.getAlarmEmail().trim().length()>0) {

            // alarmContent
            String alarmContent = "Alarm Job LogId=" + bdcJobLogDO.getId();
            if (bdcJobLogDO.getTriggerCode() != ReturnT.SUCCESS_CODE) {
                alarmContent += "<br>TriggerMsg=<br>" + bdcJobLogDO.getTriggerMsg();
            }
            if (bdcJobLogDO.getHandleCode()>0 && bdcJobLogDO.getHandleCode() != ReturnT.SUCCESS_CODE) {
                alarmContent += "<br>HandleCode=" + bdcJobLogDO.getHandleMsg();
            }

            // email info
            BdcJobGroupDO group = XxlJobAdminConfig.getAdminConfig().getBdcJobGroupMapper().load(Integer.valueOf(info.getJobGroup()));
            String personal = I18nUtil.getString("admin_name_full");
            String title = I18nUtil.getString("jobconf_monitor");
            String content = MessageFormat.format(loadEmailJobAlarmTemplate(),
                    group!=null?group.getTitle():"null",
                    info.getId(),
                    info.getJobDesc(),
                    alarmContent);

            Set<String> emailSet = new HashSet<String>(Arrays.asList(info.getAlarmEmail().split(",")));
            for (String email: emailSet) {

                // make mail
                try {
                    MimeMessage mimeMessage = XxlJobAdminConfig.getAdminConfig().getMailSender().createMimeMessage();

                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                    helper.setFrom(XxlJobAdminConfig.getAdminConfig().getEmailFrom(), personal);
                    helper.setTo(email);
                    helper.setSubject(title);
                    helper.setText(content, true);

                    XxlJobAdminConfig.getAdminConfig().getMailSender().send(mimeMessage);
                } catch (Exception e) {
                    logger.error(">>>>>>>>>>> xxl-job, job fail alarm email send error, JobLogId:{}", bdcJobLogDO.getId(), e);

                    alarmResult = false;
                }

            }
        }

        return alarmResult;
    }

    /**
     * load email job alarm template
     *
     * @return
     */
    private static final String loadEmailJobAlarmTemplate(){
        String mailBodyTemplate = "<h5>" + I18nUtil.getString("jobconf_monitor_detail") + "：</span>" +
                "<table border=\"1\" cellpadding=\"3\" style=\"border-collapse:collapse; width:80%;\" >\n" +
                "   <thead style=\"font-weight: bold;color: #ffffff;background-color: #ff8c00;\" >" +
                "      <tr>\n" +
                "         <td width=\"20%\" >"+ I18nUtil.getString("jobinfo_field_jobgroup") +"</td>\n" +
                "         <td width=\"10%\" >"+ I18nUtil.getString("jobinfo_field_id") +"</td>\n" +
                "         <td width=\"20%\" >"+ I18nUtil.getString("jobinfo_field_jobdesc") +"</td>\n" +
                "         <td width=\"10%\" >"+ I18nUtil.getString("jobconf_monitor_alarm_title") +"</td>\n" +
                "         <td width=\"40%\" >"+ I18nUtil.getString("jobconf_monitor_alarm_content") +"</td>\n" +
                "      </tr>\n" +
                "   </thead>\n" +
                "   <tbody>\n" +
                "      <tr>\n" +
                "         <td>{0}</td>\n" +
                "         <td>{1}</td>\n" +
                "         <td>{2}</td>\n" +
                "         <td>"+ I18nUtil.getString("jobconf_monitor_alarm_type") +"</td>\n" +
                "         <td>{3}</td>\n" +
                "      </tr>\n" +
                "   </tbody>\n" +
                "</table>";

        return mailBodyTemplate;
    }

}
