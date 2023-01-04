package cn.gtmap.realestate.common.support;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/3/17.
 * @description 用于各个项目启动完成后，获取所有配置文件内容至缓存。
 */
@Component
public class InitYmlDataAfterRuner implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(InitYmlDataAfterRuner.class);

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    @Value("${spring.config.location:}")
    public String springConfigLocation;
    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("项目启动完成,开始执行初始化InitYmlDataAfterRuner任务...spring.config.location:{}", springConfigLocation);
        RedisTemplate redisTemplate =new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
        {
            RedisSerializer stringSerializer = new StringRedisSerializer();
            redisTemplate.setKeySerializer(stringSerializer);
            redisTemplate.setValueSerializer(stringSerializer);
        }

        /*
         * MutablePropertySources 是PropertySources的一个实现类,它将所有的PropertySource都放置在一个名叫propertySourceList集合
         */
        MutablePropertySources mutablePropertySources = configurableEnvironment.getPropertySources();
        Map map =new HashMap();
        //获取application配置
        mutablePropertySources.forEach(propertySource -> {
            if (propertySource instanceof MapPropertySource &&propertySource.getName().startsWith("applicationConfig")) {
                MapPropertySource mps = (MapPropertySource) propertySource;
                Map<String, Object> targetMap =mps.getSource();
                if(Objects.nonNull(targetMap)){
                    map.putAll(targetMap);
                }
            }
        });
        logger.info("读取到的配置内容: {}", JSON.toJSONString(map));
        if(Objects.nonNull(map)){
            String value = (String) redisTemplate.opsForValue().get(CommonConstantUtils.REDIS_INIT_YML);
            if(StringUtils.isNotBlank(value)){
                Map cacheMap = (Map) JSON.parse(value);
                cacheMap.putAll(map);
                redisTemplate.opsForValue().set(CommonConstantUtils.REDIS_INIT_YML, JSON.toJSONString(cacheMap));
            }else{
                redisTemplate.opsForValue().set(CommonConstantUtils.REDIS_INIT_YML, JSON.toJSONString(map));
            }
        }
    }
}
