package cn.gtmap.realestate.accept.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/8/31
 * @description 评价器配置
 */
@Component
@ConfigurationProperties(prefix = "pjq")
public class PjqConfig {

    /**
     * 评价器调用接口地址
     */
    private String url;
    /**
     * 根据区县代码分类评价器的调用接口地址
     */
    private Map<String, String> qxdmUrlMap = new HashMap<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getQxdmUrlMap() {
        return qxdmUrlMap;
    }

    public void setQxdmUrlMap(Map<String, String> qxdmUrlMap) {
        this.qxdmUrlMap = qxdmUrlMap;
    }
}
