package cn.gtmap.realestate.common.core.support.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import cn.gtmap.server.core.dto.BaseLogMessage;
import cn.gtmap.server.core.dto.RunLogMessage;
import cn.gtmap.server.core.factory.MessageAppenderFactory;
import cn.gtmap.server.core.redis.RedisAbstractClient;
import cn.gtmap.server.core.redis.RedisClient;
import cn.gtmap.server.core.redis.RedisClusterClient;
import cn.gtmap.server.core.redis.RedisSentinelClient;
import cn.gtmap.server.logback.utils.LogMessageUtil;
import cn.gtmap.server.utils.LogMessageConstant;
import cn.gtmap.server.utils.ThreadPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 记录运行时日志内容至Reids中方法类
 */
@Component
public class RedisAppender extends AppenderBase<ILoggingEvent> implements InitializingBean {
    private RedisAbstractClient redisClient;
    @Value("${log.appender.appname:}")
    private String appName;
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
    @Value("${log.appender.enabled:false}")
    private boolean enabled;

    /**
     * logback日志记录开关
     */
    private boolean logbackSwitch;
    /**
     * log日志记录至redis中开关
     */

    private String ignores;

    private int redisDb = 0;
    private int maxCount = 1000;
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtil.getPool();

    public RedisAppender() {
    }

    @Override
    protected void append(ILoggingEvent event) {
        if(this.isLogbackSwitch()){
            if(isAllowed(event.getThreadName())){
                BaseLogMessage logMessage = LogMessageUtil.getLogMessage(this.appName, event);
                if (logMessage instanceof RunLogMessage) {
                    String message = LogMessageUtil.getLogMessage(logMessage, event);
                    MessageAppenderFactory.pushRundataQueue(message);
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(this.isEnabled()){
            if (this.runModel != null) {
                LogMessageConstant.RUN_MODEL = Integer.parseInt(this.runModel);
            }

            if (this.expand != null && LogMessageConstant.EXPANDS.contains(this.expand)) {
                LogMessageConstant.EXPAND = this.expand;
            }

            if (this.redisClient == null) {
                if (StringUtils.equals(this.redisModel, "cluster")) {
                    this.redisClient = RedisClusterClient.getInstance(this.redisHost, this.redisAuth);
                } else if (StringUtils.equals(this.redisModel, "sentinel")) {
                    this.redisClient = RedisSentinelClient.getInstance(this.redisHost, this.masterName, this.redisAuth, this.redisDb);
                } else {
                    this.redisClient = RedisClient.getInstance(this.redisHost, Integer.parseInt(this.redisPort), this.redisAuth);
                }
            }

            for(int a = 0; a < 5; ++a) {
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        MessageAppenderFactory.startRunLog(RedisAppender.this.redisClient, RedisAppender.this.maxCount);
                    }
                });
            }
        }
    }

    private boolean isAllowed(String name){
        if(null == ignores || "".equals(ignores)){
            return true;
        }
        if(null == name || "".equals(name)){
            return true;
        }
        return ignores.indexOf(name) < 0;
    }

    public String getExpand() {
        return this.expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public void setRedisPort(String redisPort) {
        this.redisPort = redisPort;
    }

    public void setRedisAuth(String redisAuth) {
        this.redisAuth = redisAuth;
    }

    public void setRunModel(String runModel) {
        this.runModel = runModel;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public void setRedisModel(String redisModel) {
        this.redisModel = redisModel;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public void setRedisDb(int redisDb) {
        this.redisDb = redisDb;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getIgnores() {
        return ignores;
    }

    public void setIgnores(String ignores) {
        this.ignores = ignores;
    }

    public boolean isLogbackSwitch() {
        return logbackSwitch;
    }

    public void setLogbackSwitch(boolean logbackSwitch) {
        this.logbackSwitch = logbackSwitch;
    }
}
