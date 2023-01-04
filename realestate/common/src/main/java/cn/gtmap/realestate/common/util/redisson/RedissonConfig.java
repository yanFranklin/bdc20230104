package cn.gtmap.realestate.common.util.redisson;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/4
 * @description Redisson配置类
 */
@Configuration
public class RedissonConfig {
    @Resource
    private RedisProperties redisProperties;

    @Value("${redis.config.subscriptionConnectionMinimumIdleSize:3}")
    private Integer subscriptionConnectionMinimumIdleSize;

    @Value("${redis.config.subscriptionConnectionPoolSize:200}")
    private Integer subscriptionConnectionPoolSize;

    @Value("${redis.config.slaveConnectionMinimumIdleSize:64}")
    private Integer slaveConnectionMinimumIdleSize;

    @Value("${redis.config.slaveConnectionPoolSize:256}")
    private Integer slaveConnectionPoolSize;

    @Value("${redis.config.masterConnectionMinimumIdleSize:64}")
    private Integer masterConnectionMinimumIdleSize;

    @Value("${redis.config.masterConnectionPoolSize:256}")
    private Integer masterConnectionPoolSize;

    @Value("${redis.config.idleConnectionTimeout:30000}")
    private Integer idleConnectionTimeout;

    @Value("${redis.config.retryAttempts:10}")
    private Integer retryAttempts;

    @Value("${redis.config.singleConnectionMinimumIdleSize:24}")
    private Integer singleConnectionMinimumIdleSize;

    @Value("${redis.config.singleConnectionPoolSize:64}")
    private Integer singleConnectionPoolSize;

    @Bean
    public RedissonClient getRedissonClient(){
        Config config = new Config();

        if (redisProperties.getCluster() != null && CollectionUtils.isNotEmpty(redisProperties.getCluster().getNodes())){
            // 集群配置
            List<String> nodeList = redisProperties.getCluster().getNodes();
            String[] arr = new String[nodeList.size()];
            for(int i = 0 ;i < nodeList.size();i++){
                arr[i] = "redis://"+nodeList.get(i);
            }
            config.useClusterServers().addNodeAddress(arr);

            if(StringUtils.isNotBlank(redisProperties.getPassword())) {
                config.useClusterServers().setPassword(redisProperties.getPassword());
            }
            config.useClusterServers().setSubscriptionConnectionMinimumIdleSize(subscriptionConnectionMinimumIdleSize);
            config.useClusterServers().setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize);
            config.useClusterServers().setSlaveConnectionMinimumIdleSize(slaveConnectionMinimumIdleSize);
            config.useClusterServers().setSlaveConnectionPoolSize(slaveConnectionPoolSize);
            config.useClusterServers().setMasterConnectionMinimumIdleSize(masterConnectionMinimumIdleSize);
            config.useClusterServers().setMasterConnectionPoolSize(masterConnectionPoolSize);
            config.useClusterServers().setIdleConnectionTimeout(idleConnectionTimeout);
            config.useClusterServers().setRetryAttempts(retryAttempts);
        } else {
            // 单机Redis
            SingleServerConfig serverConfig = config.useSingleServer();
            serverConfig.setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort());
            if(StringUtils.isNotBlank(redisProperties.getPassword())) {
                serverConfig.setPassword(redisProperties.getPassword());
            }
            serverConfig.setConnectionMinimumIdleSize(singleConnectionMinimumIdleSize);
            serverConfig.setConnectionPoolSize(singleConnectionPoolSize);
        }
        return Redisson.create(config);
    }
}
