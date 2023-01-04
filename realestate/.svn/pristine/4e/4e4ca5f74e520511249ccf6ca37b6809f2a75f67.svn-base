package cn.gtmap.realestate.accept.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/9/30
 * @description 部分解押解封 权利类型 子规则对应关系
 */
@Component
@ConfigurationProperties(prefix = "bfjyjf")
public class BfjyjfQllxZgzidConfig {
    /**
     * 权利类型对应关系
     */
    private Map<String, List<String>> qllxMap=new HashMap<>();

    public Map<String, List<String>> getQllxMap() {
        return qllxMap;
    }

    public void setQllxMap(Map<String, List<String>> qllxMap) {
        this.qllxMap = qllxMap;
    }

}
