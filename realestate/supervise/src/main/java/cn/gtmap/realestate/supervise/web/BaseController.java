package cn.gtmap.realestate.supervise.web;

import cn.gtmap.gtc.starter.gcas.util.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Layui请求返回成功编码
     */
    public final static int LAYUI_SUCCESS_CODE = 0;

    protected static final String wjglqx = "{\"CanRefresh\":1,\"CanCreateNewFolder\":0,\"CanDelete\":0,\"CanRename\":0,\"CanPrint\":1,\"CanDownload\":1,\"CanUpload\":0,\"CanTakePhoto\":0,\"CanScan\":0,\"CanEdit\":-1}";

    @Value("${app.oauth}")
    private String authUrl;
    @Value("${security.oauth2.client.client-id}")
    private String clientId;
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    /**
     * 处理分页结构数据 添加code字段
     *
     * @param page
     * @return
     */
    public Object addLayUiCode(Page page) {
        Map map = null;
        if (page != null) {
            map = JSONObject.parseObject(JSONObject.toJSONString(page), Map.class);
            if (map != null) {
                map.put("msg", "成功");
            }
            if (map == null) {
                map = new HashMap(1);
                map.put("msg", "无数据");
            }
            map.put("code", LAYUI_SUCCESS_CODE);
        }
        return map;
    }

    /**
     * 处理list结构数据 添加code字段
     *
     * @param list
     * @return
     */
    public Object addLayUiCode(List list) {
        Map<String, Object> map = new HashMap(3);
        if (CollectionUtils.isEmpty(list)) {
            map.put("msg", "无数据");
        } else {
            map.put("msg", "成功");
            map.put("data", list);
        }
        map.put("code", LAYUI_SUCCESS_CODE);

        return map;
    }

    public String getAccountToken() {
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
            String tokenString = HttpUtils.post(builder.toString(), null, null, null);
            JSONObject json = JSON.parseObject(tokenString);
            token = json.getString("access_token");
        }
        return token;

    }
}
