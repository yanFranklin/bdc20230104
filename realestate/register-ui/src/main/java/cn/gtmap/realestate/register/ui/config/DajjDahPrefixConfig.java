package cn.gtmap.realestate.register.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: realestate
 * @description: 档案交接档案号前置配置
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-06-18 09:57
 **/
@Component
@ConfigurationProperties(prefix = "dajj.dahprefix")
public class DajjDahPrefixConfig {

    private Map<Integer, String> qllxMap;

    public Map<Integer, String> getQllxMap() {
        return qllxMap;
    }

    public void setQllxMap(Map<Integer, String> qllxMap) {
        this.qllxMap = qllxMap;
    }
}
