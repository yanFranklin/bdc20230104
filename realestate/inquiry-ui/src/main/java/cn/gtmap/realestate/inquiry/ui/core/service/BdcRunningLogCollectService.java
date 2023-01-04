package cn.gtmap.realestate.inquiry.ui.core.service;

import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.inquiry.ui.config.ElasticLowerClient;
import cn.gtmap.realestate.inquiry.ui.util.Constants;
import cn.gtmap.server.core.exception.LogQueueConnectException;
import cn.gtmap.server.core.redis.RedisAbstractClient;
import cn.gtmap.server.core.redis.RedisClient;
import cn.gtmap.server.core.redis.RedisClusterClient;
import cn.gtmap.server.core.redis.RedisSentinelClient;
import cn.gtmap.server.utils.LogMessageConstant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Component
@EnableScheduling
public class BdcRunningLogCollectService implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcRunningLogCollectService.class);

    /**
     * 每次抓取日志的条数
     */
    public final static int LOG_COLLECT_MAX_SEND_SIZE = 5000;

    private RedisAbstractClient redisClient;

    @Value("${log.appender.redis-host:}")
    private String redisHost;
    @Value("${log.appender.redis-port:}")
    private String redisPort;
    @Value("${log.appender.redis-auth:}")
    private String redisAuth;
    @Value("${log.appender.run-model:1}")
    private String runModel;
    @Value("${log.appender.expand:}")
    private String expand;
    @Value("${log.appender.redis-model:}")
    private String redisModel;
    @Value("${log.appender.mastername:}")
    private String masterName;
    @Value("${log.es.index-type:}")
    private String indexType;
    @Value("${log.appender.enabled:false}")
    private boolean enabled;

    private int redisDb = 0;
    /**
     * 运行日志过期时间设定
     */
    @Value("${log.expire.days:15}")
    private int expireDays;
    @Value("${log.appender.enabled:false}")
    private boolean logEnabled;

    @Autowired(required = false)
    private ElasticLowerClient elasticLowerClient;

    @Scheduled(cron = "${quartz.collect.runninglog.cron:0 */2 * * * ?}")
    @RedissonLock(lockKey = Constants.QUARTZ_COLLECT_RUNNING_LOG_JOB_NAME, description = "采集运行时日志定时任务", waitTime = 30L, leaseTime = 120L)
    public void CollectRunningLogQuartzTask(){
        if(logEnabled){
            try {
                List<String> logs = redisClient.getMessage(LogMessageConstant.LOG_KEY, LOG_COLLECT_MAX_SEND_SIZE);
                if(CollectionUtils.isNotEmpty(logs)){
                    this.sendLog(this.getRunLogIndex(new Date()), logs);
                }
            } catch (LogQueueConnectException e) {
                LOGGER.error("从redis队列拉取日志失败！", e);
            }
        }
    }

    @Scheduled(cron = "${quartz.delete.runninglog.cron:0 0 0 * * ?}")
    @RedissonLock(lockKey = Constants.QUARTZ_COLLECT_RUNNING_LOG_JOB_NAME, description = "删除过期的运行时日志定时任务", waitTime = 30L, leaseTime = 300L)
    public void DeleteExpireRunningLogQuartzTask(){
        if(logEnabled && expireDays > 0){
            if(null == elasticLowerClient){
                return;
            }
            try {
                LOGGER.info("开始执行删除过期的运行时日志。");
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -expireDays);
                String runLogIndex = this.getRunLogIndex(cal.getTime());
                elasticLowerClient.deleteIndex(this.getRunLogIndex(cal.getTime()));
                LOGGER.info("删除运行时日志内容成功， 日志索引为： {}" , runLogIndex);
            } catch (Exception e) {
                LOGGER.error("删除运行时日志异常", e);
            }
        }
    }

    @Override
    public void afterPropertiesSet() {
        if(this.enabled){
            if (this.runModel != null) {
                LogMessageConstant.RUN_MODEL = Integer.parseInt(this.runModel);
            }
            if (this.expand != null && LogMessageConstant.EXPANDS.contains(this.expand)) {
                LogMessageConstant.EXPAND = this.expand;
            }
            if (this.redisClient == null && StringUtils.isNotBlank(redisHost)) {
                if (StringUtils.equals(this.redisModel, "cluster")) {
                    this.redisClient = RedisClusterClient.getInstance(this.redisHost, this.redisAuth);
                } else if (StringUtils.equals(this.redisModel, "sentinel")) {
                    this.redisClient = RedisSentinelClient.getInstance(this.redisHost, this.masterName, this.redisAuth, this.redisDb);
                } else {
                    this.redisClient = RedisClient.getInstance(this.redisHost, Integer.parseInt(this.redisPort), this.redisAuth);
                }
            }
        }
    }

    public void sendLog(String index, List<String> sendList) {
        try {
            if(sendList.size()>0) {
                String esType = StringUtils.isBlank(indexType)? LogMessageConstant.ES_TYPE: indexType;
                elasticLowerClient.insertList(sendList, index, esType);
            }
        } catch (Exception e) {
            LOGGER.error("插入运行时日志内容至es中异常。", e);
        }
    }

    /**
     * 获取运行时日志es索引（例如：plume_log_run_20220724）
     */
    public String getRunLogIndex(Date date){
        String datestr = DateUtils.formateDateToString(date, DateTimeFormatter.ofPattern(DateUtils.sdf_ymd, Locale.CHINA));
        return LogMessageConstant.ES_INDEX + LogMessageConstant.LOG_TYPE_RUN + "_" + datestr;
    }

}
