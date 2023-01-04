package cn.gtmap.realestate.engine.config;

import cn.gtmap.realestate.engine.util.Constants;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/15
 * @description  定时任务获取Token
 *  1、场景说明：
 *   子规则在执行服务数据流时调用其它服务，需要Token，但是请求account应用
 *   获取Token会很慢，导致规则执行超时，所以这里定义获取Token缓存起来以备用
 */
@Component
public class TokenTask {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenTask.class);
    /**
     * 当前限定类名
     */
    private static final String CLASS_NAME = TokenTask.class.getName();
    /**
     * 发起 REST请求
     */
    @Autowired
    private RestTemplate restTemplate;
    /**
     * Redis String类型操作
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  每一分钟获取一次，且异步执行
     */
    @Async
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void getToken(){
        // 先获取
        String accessTokenStr =  restTemplate.postForObject(Constants.TOKEN_URL, "", String.class);
        String token = JSONObject.parseObject(accessTokenStr).getString("access_token");
        LOGGER.debug("{}：规则子系统获取Token成功，Token为：{}", CLASS_NAME, token);

        // 缓存到Redis
        stringRedisTemplate.opsForValue().set(Constants.REDIS_RULE_TOKEN, token);
    }
}
