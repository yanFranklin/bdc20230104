package cn.gtmap.realestate.config.job.trigger;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDTO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobInfoDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobLogDO;
import cn.gtmap.realestate.common.job.biz.ExecutorBiz;
import cn.gtmap.realestate.common.core.dto.ReturnT;
import cn.gtmap.realestate.common.job.biz.model.TriggerParam;
import cn.gtmap.realestate.common.job.enums.ExecutorBlockStrategyEnum;
import cn.gtmap.realestate.common.job.util.IpUtil;
import cn.gtmap.realestate.common.job.util.ThrowableUtil;
import cn.gtmap.realestate.config.job.conf.XxlJobAdminConfig;
import cn.gtmap.realestate.config.job.route.ExecutorRouteStrategyEnum;
import cn.gtmap.realestate.config.job.scheduler.XxlJobScheduler;
import cn.gtmap.realestate.config.job.util.I18nUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * xxl-job trigger
 * Created by  on 17/7/13.
 */
public class XxlJobTrigger {
    private static Logger logger = LoggerFactory.getLogger(XxlJobTrigger.class);

    /**
     * trigger job
     *
     * @param jobId
     * @param triggerType
     * @param failRetryCount
     * 			>=0: use this param
     * 			<0: use param from job info config
     * @param executorShardingParam
     * @param executorParam
     *          null: use job param
     *          not null: cover job param
     * @param addresslist
     *          null: use executor addresslist
     *          not null: cover
     */
    public static void trigger(int jobId,
                               TriggerTypeEnum triggerType,
                               int failRetryCount,
                               String executorShardingParam,
                               String executorParam,
                               String addresslist) {

        // load data
        BdcJobInfoDO bdcJobInfoDO = XxlJobAdminConfig.getAdminConfig().getBdcJobInfoMapper().loadById(jobId);
        if (bdcJobInfoDO == null) {
            logger.warn(">>>>>>>>>>>> trigger fail, jobId invalid，jobId={}", jobId);
            return;
        }
        if (executorParam != null) {
            bdcJobInfoDO.setExecutorParam(executorParam);
        }
        int finalFailRetryCount = failRetryCount>=0?failRetryCount: bdcJobInfoDO.getExecutorFailRetryCount();
        BdcJobGroupDTO group = XxlJobAdminConfig.getAdminConfig().getBdcJobGroupMapper().load(bdcJobInfoDO.getJobGroup());

        // cover addresslist
        if (addresslist!=null && addresslist.trim().length()>0) {
            group.setAddresstype(1);
            group.setAddresslist(addresslist.trim());
        }

        // sharding param
        int[] shardingParam = null;
        if (executorShardingParam!=null){
            String[] shardingArr = executorShardingParam.split("/");
            if (shardingArr.length==2 && isNumeric(shardingArr[0]) && isNumeric(shardingArr[1])) {
                shardingParam = new int[2];
                shardingParam[0] = Integer.valueOf(shardingArr[0]);
                shardingParam[1] = Integer.valueOf(shardingArr[1]);
            }
        }
        if (ExecutorRouteStrategyEnum.SHARDING_BROADCAST==ExecutorRouteStrategyEnum.match(bdcJobInfoDO.getExecutorRouteStrategy(), null)
                && group.getRegistryList()!=null && !group.getRegistryList().isEmpty()
                && shardingParam==null) {
            for (int i = 0; i < group.getRegistryList().size(); i++) {
                processTrigger(group, bdcJobInfoDO, finalFailRetryCount, triggerType, i, group.getRegistryList().size());
            }
        } else {
            if (shardingParam == null) {
                shardingParam = new int[]{0, 1};
            }
            processTrigger(group, bdcJobInfoDO, finalFailRetryCount, triggerType, shardingParam[0], shardingParam[1]);
        }

    }

    private static boolean isNumeric(String str){
        try {
            int result = Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param group                     job group, registry list may be empty
     * @param bdcJobInfoDO
     * @param finalFailRetryCount
     * @param triggerType
     * @param index                     sharding index
     * @param total                     sharding index
     */
    private static void processTrigger(BdcJobGroupDTO group, BdcJobInfoDO bdcJobInfoDO, int finalFailRetryCount, TriggerTypeEnum triggerType, int index, int total){

        // param
        ExecutorBlockStrategyEnum blockStrategy = ExecutorBlockStrategyEnum.match(bdcJobInfoDO.getExecutorBlockStrategy(), ExecutorBlockStrategyEnum.SERIAL_EXECUTION);  // block strategy
        ExecutorRouteStrategyEnum executorRouteStrategyEnum = ExecutorRouteStrategyEnum.match(bdcJobInfoDO.getExecutorRouteStrategy(), null);    // route strategy
        String shardingParam = (ExecutorRouteStrategyEnum.SHARDING_BROADCAST==executorRouteStrategyEnum)?String.valueOf(index).concat("/").concat(String.valueOf(total)):null;

        // 1、save log-id
        BdcJobLogDO bdcJobLogDO = new BdcJobLogDO();
        bdcJobLogDO.setJobGroup(bdcJobInfoDO.getJobGroup());
        bdcJobLogDO.setJobId(bdcJobInfoDO.getId());
        bdcJobLogDO.setTriggerTime(new Date());
        XxlJobAdminConfig.getAdminConfig().getBdcJobLogMapper().save(bdcJobLogDO);
        logger.debug(">>>>>>>>>>> xxl-job trigger start, jobId:{}", bdcJobLogDO.getId());

        // 2、init trigger-param
        TriggerParam triggerParam = new TriggerParam();
        triggerParam.setJobId(bdcJobInfoDO.getId());
        triggerParam.setExecutorHandler(bdcJobInfoDO.getExecutorHandler());
        triggerParam.setExecutorParams(bdcJobInfoDO.getExecutorParam());
        triggerParam.setExecutorBlockStrategy(bdcJobInfoDO.getExecutorBlockStrategy());
        triggerParam.setExecutorTimeout(bdcJobInfoDO.getExecutorTimeout());
        triggerParam.setLogId(bdcJobLogDO.getId());
        triggerParam.setLogDateTime(bdcJobLogDO.getTriggerTime().getTime());
        triggerParam.setGlueType(bdcJobInfoDO.getGlueType());
        triggerParam.setGlueSource(bdcJobInfoDO.getGlueSource());
        triggerParam.setGlueUpdatetime(bdcJobInfoDO.getGlueUpdatetime().getTime());
        triggerParam.setBroadcastIndex(index);
        triggerParam.setBroadcastTotal(total);

        // 3、init address
        String address = null;
        ReturnT<String> routeAddressResult = null;
        if (group.getRegistryList()!=null && !group.getRegistryList().isEmpty()) {
            if (ExecutorRouteStrategyEnum.SHARDING_BROADCAST == executorRouteStrategyEnum) {
                if (index < group.getRegistryList().size()) {
                    address = group.getRegistryList().get(index);
                } else {
                    address = group.getRegistryList().get(0);
                }
            } else {
                routeAddressResult = executorRouteStrategyEnum.getRouter().route(triggerParam, group.getRegistryList());
                if (routeAddressResult.getCode() == ReturnT.SUCCESS_CODE) {
                    address = routeAddressResult.getContent();
                }
            }
        } else {
            routeAddressResult = new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("jobconf_trigger_address_empty"));
        }

        // 4、trigger remote executor
        ReturnT<String> triggerResult = null;
        if (address != null) {
            triggerResult = runExecutor(triggerParam, address);
        } else {
            triggerResult = new ReturnT<String>(ReturnT.FAIL_CODE, null);
        }

        // 5、collection trigger info
        StringBuffer triggerMsgSb = new StringBuffer();
        triggerMsgSb.append(I18nUtil.getString("jobconf_trigger_type")).append("：").append(triggerType.getTitle());
        triggerMsgSb.append("<br>").append(I18nUtil.getString("jobconf_trigger_admin_adress")).append("：").append(IpUtil.getIp());
        triggerMsgSb.append("<br>").append(I18nUtil.getString("jobconf_trigger_exe_regtype")).append("：")
                .append( (group.getAddresstype() == 0)?I18nUtil.getString("jobgroup_field_addresstype_0"):I18nUtil.getString("jobgroup_field_addresstype_1") );
        triggerMsgSb.append("<br>").append(I18nUtil.getString("jobconf_trigger_exe_regaddress")).append("：").append(group.getRegistryList());
        triggerMsgSb.append("<br>").append(I18nUtil.getString("jobinfo_field_executorRouteStrategy")).append("：").append(executorRouteStrategyEnum.getTitle());
        if (shardingParam != null) {
            triggerMsgSb.append("("+shardingParam+")");
        }
        triggerMsgSb.append("<br>").append(I18nUtil.getString("jobinfo_field_executorBlockStrategy")).append("：").append(blockStrategy.getTitle());
        triggerMsgSb.append("<br>").append(I18nUtil.getString("jobinfo_field_timeout")).append("：").append(bdcJobInfoDO.getExecutorTimeout());
        triggerMsgSb.append("<br>").append(I18nUtil.getString("jobinfo_field_executorFailRetryCount")).append("：").append(finalFailRetryCount);

        triggerMsgSb.append("<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>"+ I18nUtil.getString("jobconf_trigger_run") +"<<<<<<<<<<< </span><br>")
                .append((routeAddressResult!=null&&routeAddressResult.getMsg()!=null)?routeAddressResult.getMsg()+"<br><br>":"").append(triggerResult.getMsg()!=null?triggerResult.getMsg():"");

        // 6、save log trigger-info
        bdcJobLogDO.setExecutorAddress(address);
        bdcJobLogDO.setExecutorHandler(bdcJobInfoDO.getExecutorHandler());
        bdcJobLogDO.setExecutorParam(bdcJobInfoDO.getExecutorParam());
        bdcJobLogDO.setExecutorShardingParam(shardingParam);
        bdcJobLogDO.setExecutorFailRetryCount(finalFailRetryCount);
        //jobLog.setTriggerTime();
        bdcJobLogDO.setTriggerCode(triggerResult.getCode());
        bdcJobLogDO.setTriggerMsg(triggerMsgSb.toString());
        XxlJobAdminConfig.getAdminConfig().getBdcJobLogMapper().updateTriggerInfo(bdcJobLogDO);

        logger.debug(">>>>>>>>>>> xxl-job trigger end, jobId:{}", bdcJobLogDO.getId());
    }

    /**
     * run executor
     * @param triggerParam
     * @param address
     * @return
     */
    public static ReturnT<String> runExecutor(TriggerParam triggerParam, String address){
        ReturnT<String> runResult = null;
        try {
            ExecutorBiz executorBiz = XxlJobScheduler.getExecutorBiz(address);
            runResult = executorBiz.run(triggerParam);
        } catch (Exception e) {
            logger.error(">>>>>>>>>>> xxl-job trigger error, please check if the executor[{}] is running.", address, e);
            runResult = new ReturnT<String>(ReturnT.FAIL_CODE, ThrowableUtil.toString(e));
        }

        StringBuffer runResultSB = new StringBuffer(I18nUtil.getString("jobconf_trigger_run") + "：");
        runResultSB.append("<br>address：").append(address);
        runResultSB.append("<br>code：").append(runResult.getCode());
        runResultSB.append("<br>msg：").append(runResult.getMsg());

        runResult.setMsg(runResultSB.toString());
        return runResult;
    }

}
