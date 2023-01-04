package cn.gtmap.realestate.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/12
 * @description Token操作
 */
@Component
public class TokenUtils {
    /**
     * Token键定义
     */
    private static final String REDIS_RULE_TOKEN = "REDIS_RULE_TOKEN";

    @Value("${app.oauth:}")
    private String authUrl;

    @Value("${security.oauth2.client.client-id:}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret:}")
    private String clientSecret;

    /**
     * Token请求地址
     */
    private static final String TOKEN_URL = "http://account/account/oauth/token?grant_type=client_credentials" +
            "&client_id=accountUIClientID&client_secret=accountUIClientSecret";

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
        String token = stringRedisTemplate.opsForValue().get(REDIS_RULE_TOKEN);

        // 缓存没有再单独获取
        if(StringUtils.isBlank(token)){
            String accessTokenStr =  restTemplate.postForObject(TOKEN_URL, "", String.class);
            token =  JSONObject.parseObject(accessTokenStr).getString("access_token");

            // 缓存到Redis
            stringRedisTemplate.opsForValue().set(REDIS_RULE_TOKEN, token);
        }
        return token;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    public String getToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = null;
        if (null != authentication && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            token = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
        }
        if (StringUtils.isBlank(token)) {
            String authPath = "";
            if (authUrl != null && authUrl.lastIndexOf("/") != (authUrl.length() - 1)) {
                authPath = authUrl + "/";
            } else {
                authPath = authUrl;
            }
            StringBuilder builder = new StringBuilder(authPath);
            builder.append("oauth/token?grant_type=client_credentials&client_id=").append(clientId).append("&client_secret=").append(clientSecret);
            String accessTokenStr = restTemplate.postForObject(builder.toString(), "", String.class);
            if(StringUtils.isNotBlank(accessTokenStr)){
                token =JSONObject.parseObject(accessTokenStr).getString("access_token");
            }
        }
        return  token;
    }
}
