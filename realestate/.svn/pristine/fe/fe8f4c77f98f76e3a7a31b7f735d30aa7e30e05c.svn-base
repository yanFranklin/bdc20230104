package cn.gtmap.realestate.common.config.accept;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2020/3/24
 * @description 默认收费信息
 */
@Component
@ConfigurationProperties(prefix = "mrsfxx")
public class MrsfxxConfig {
    /**
     * 默认收费信息配置
     */
    private Map<String, Map<String,String>> mrsfxxMap = new HashMap<>();

    public Map<String, Map<String, String>> getMrsfxxMap() {
        return mrsfxxMap;
    }

    public void setMrsfxxMap(Map<String, Map<String, String>> mrsfxxMap) {
        this.mrsfxxMap = mrsfxxMap;
    }
}
