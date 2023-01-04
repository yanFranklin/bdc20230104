package cn.gtmap.realestate.init.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/05/06/19:23
 * @Description:
 */
@Component
@ConfigurationProperties(prefix = "bdcgg")
public class BdcGgConfig {
    Map<String,List<String>> ggpzMap;

    public Map<String, List<String>> getGgpzMap() {
        return ggpzMap;
    }

    public void setGgpzMap(Map<String, List<String>> ggpzMap) {
        this.ggpzMap = ggpzMap;
    }
}
