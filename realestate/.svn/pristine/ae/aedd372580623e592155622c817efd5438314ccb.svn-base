//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.gtmap.realestate.exchange.config;

import cn.gtmap.gtc.starter.gscas.token.TokenDataRepo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import okhttp3.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:yangyang@gtmap.cn">yangyang</a>
 * @date V1.0, 2019/3/15 22:22
 * @description
 */
public class ExOAuth2FeignRequestInterceptor implements RequestInterceptor {

    private final Log logger = LogFactory.getLog(ExOAuth2FeignRequestInterceptor.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_TYPE = "bearer";
    private OAuth2RestTemplate oAuth2RestTemplate;
    private final OAuth2ProtectedResourceDetails details;
    private OkHttpClient okHttpClient;

    public ExOAuth2FeignRequestInterceptor(OAuth2RestTemplate oAuth2RestTemplate, OAuth2ProtectedResourceDetails details) {

        Assert.notNull(oAuth2RestTemplate, "Context can not be null");
        this.oAuth2RestTemplate = oAuth2RestTemplate;
        this.details = details;
        this.okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.MINUTES))
                .connectTimeout(10L, TimeUnit.SECONDS)
                .readTimeout(10L, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public void apply(RequestTemplate template) {
        String token = getAccessToken();
        if (!StringUtils.isEmpty(token)) {
            template.header(AUTHORIZATION_HEADER, new String[]{String.format("%s %s", BEARER_TOKEN_TYPE,token)});
        }
    }

    private String postFormParams(String url, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        if (!CollectionUtils.isEmpty(params)){
            for (Map.Entry<String,String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        return execNewCall(request);
    }

    private String execNewCall(Request request){
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }

            return null;
        } catch (Exception e) {
            logger.error("okhttp3 put error >> ex = {}",e);
            return null;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    public String getAccessToken() {
        if (isExpire()) {
            return getNewAccessToken();
        } else {
            return TokenDataRepo.getInstance().getToken();
        }
    }

    private boolean isExpire() {
        int time = TokenDataRepo.getInstance().getTime();
        int now = (int)(System.currentTimeMillis()/ 1000);
        return now > (time - 10);
    }

    private String getNewAccessToken () {
        String authPath = details.getAccessTokenUri();
        if (!StringUtils.isEmpty(authPath)) {
            StringBuilder builder = new StringBuilder(authPath);
            builder.append("?grant_type=client_credentials&client_id=").append(details.getClientId()).append("&client_secret=").append(details.getClientSecret());
            String res = postFormParams(builder.toString(), null);
            if (!StringUtils.isEmpty(res)) {
                JSONObject object = JSON.parseObject(res);
                Map map = object.getInnerMap();
                if (map != null && null != map.get("access_token")) {
                    String accessToken = (String) map.get("access_token");
                    int expires = (Integer) map.get("expires_in");
                    if (expires > 200) {
                        expires = 200;
                    }
                    int time = ((int)(System.currentTimeMillis() / 1000)) + expires;
                    TokenDataRepo.getInstance().setTime(time);
                    TokenDataRepo.getInstance().setToken(accessToken);
                    return accessToken;
                }
            }
        }

        return null;
    }
}
