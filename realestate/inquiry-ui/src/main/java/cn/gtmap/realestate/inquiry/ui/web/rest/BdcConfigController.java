package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.analysis.BdcConfigFeignService;
import cn.gtmap.realestate.inquiry.ui.util.PropertiesUtils;
import com.google.common.collect.Maps;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/3/4
 * @description
 */
@RestController
@RequestMapping(value = "/config")
public class BdcConfigController {

    private static final Logger logger = LoggerFactory.getLogger(BdcConfigController.class);
    @Value("${security.oauth2.client.client-id}")
    private String clientId;
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;
    @Value("${app.oauth}")
    private String oauthUrl;
    @Autowired
    private BdcConfigFeignService bdcConfigFeignService;
    /**综合查询中查询条件的模糊类型配置*/
    @Value("#{${zhcx.cxlx:null}}")
    private Map<String, Integer> mhlxMap;

    /**
     * version 1.0
     *
     * @param
     * @return
     * @description 獲取 clientId 和 clientSecret， 根據這兩個查詢token
     * @date 2019/3/4
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping(value = "/security/param", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getSecurityParam() {
        Map<String, String> param = Maps.newHashMap();
        if (StringUtils.isNoneBlank(clientId, clientSecret, oauthUrl)) {
            if (!oauthUrl.endsWith("/")) {
                oauthUrl += "/";
            }
            param.put("clientId", clientId);
            param.put("clientSecret", clientSecret);
            param.put("oauthUrl", oauthUrl + "oauth/token?grant_type=client_credentials");
        }
        return param;
    }

    /**
     * version 1.0
     *
     * @return
     * @description 获取端口号
     * @date 2019/3/5
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping(value = "/port")
    public Object getServicePort() {
        Map<String, String> portMap = new HashMap<>();
        Maps.newHashMap();
        portMap.put("ANALYSIS_PORT", bdcConfigFeignService.getAnalysisPort());
        return portMap;
    }



    /**
     * 获取点击行按钮时页面跳转的url
     *
     * @param urlLx 对应url.properties文件的key值
     * @return java.lang.String
     * @date 2019.03.01 20:16
     * @author hanyaning
     */
    @GetMapping("/url")
    public String getUrl(String urlLx) {
        if (StringUtils.isBlank(urlLx)) {
            throw new MissingArgumentException("访问路径类型");
        }
        String url;
        try {
            url = PropertiesUtils.getUrl(urlLx);
        } catch (Exception e) {
            logger.error("获取访问路径失败，原因：{}" + e.getMessage());
            throw new AppException("获取访问路径失败");
        }
        if (org.apache.commons.lang.StringUtils.isBlank(url)) {
            throw new AppException("访问路径缺失，请检查配置");
        }
        return url;
    }
    /**
     * 获取综合查询查询条件模糊类型的配置
     */
    @GetMapping("/zszm/getmhlx")
    public Object getMhlx(){
        return mhlxMap;
    }
}
