package cn.gtmap.realestate.engine.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/12
 * @description Token操作
 */
@Component
public class TokenUtil {
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
     * @return {String}  token
     * @description 获取权限访问Token
     */
    public String getAccessToken(){
        // 先从Redis缓存获取
        String token = stringRedisTemplate.opsForValue().get(Constants.REDIS_RULE_TOKEN);

        // 缓存没有再单独获取
        if(StringUtils.isBlank(token)){
            String accessTokenStr =  restTemplate.postForObject(Constants.TOKEN_URL, "", String.class);
            token =  JSONObject.parseObject(accessTokenStr).getString("access_token");

            // 缓存到Redis
            stringRedisTemplate.opsForValue().set(Constants.REDIS_RULE_TOKEN, token);
        }

        return token;
    }
}
