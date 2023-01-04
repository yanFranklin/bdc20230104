package cn.gtmap.realestate.common.config.accept;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: realestate
 * @description: 自定义增量初始化选择台账配置
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-08-31 15:55
 **/
@Component
@ConfigurationProperties(prefix = "zdyzlcsh")
public class ZdyZlcshXztzPzConfig {

    private Map<String, String> xztzpz;

    private Map<String, String> qllxAndYwrsjly;

    public Map<String, String> getXztzpz() {
        return xztzpz;
    }

    public void setXztzpz(Map<String, String> xztzpz) {
        this.xztzpz = xztzpz;
    }

    public Map<String, String> getQllxAndYwrsjly() {
        return qllxAndYwrsjly;
    }

    public void setQllxAndYwrsjly(Map<String, String> qllxAndYwrsjly) {
        this.qllxAndYwrsjly = qllxAndYwrsjly;
    }

    @Override
    public String toString() {
        return "ZdyZlcshXztzPzConfig{" +
                "xztzpz=" + xztzpz +
                ", qllxAndYwrsjly=" + qllxAndYwrsjly +
                '}';
    }
}
