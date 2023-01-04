package cn.gtmap.realestate.portal.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/2/26.
 * @description
 */
@Component
@ConfigurationProperties(prefix = "zrzy-task")
public class ZrzyQueryParamsConfig {
    /**
     * 参数对应处理
     */
    private  Map<String, Map<String,String>> paramMap = new HashMap();


    /**
     * 列表默认的查询参数
     */
    private Map<String, Map<String,Map<String, String>>> defaultParamMap = new HashMap();


    public Map<String, Map<String, String>> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Map<String, String>> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Map<String, Map<String, String>>> getDefaultParamMap() {
        return defaultParamMap;
    }

    public void setDefaultParamMap(Map<String, Map<String, Map<String, String>>> defaultParamMap) {
        this.defaultParamMap = defaultParamMap;
    }
}
