package cn.gtmap.realestate.portal.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务转移参数配置
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 10:13 上午 2020/4/29
 */
@Component
@ConfigurationProperties(prefix = "ywzy")
public class YwzyParamsConfig {
    /**
     * 参数对应处理
     */
    private Map<String, Map<String, String>> paramMap = new HashMap();

    public Map<String, Map<String, String>> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Map<String, String>> paramMap) {
        this.paramMap = paramMap;
    }
}
