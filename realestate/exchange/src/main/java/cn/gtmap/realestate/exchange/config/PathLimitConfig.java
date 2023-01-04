package cn.gtmap.realestate.exchange.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2020/1/6
 * @description 用于配置访问次数限制的配置文件
 */
@Component
@ConfigurationProperties(prefix = "intercept.tries")
public class PathLimitConfig {

    private Map<String, Map<String, List>> limit;

    public Map<String, Map<String, List>> getLimit() {
        return limit;
    }

    public void setLimit(Map<String, Map<String, List>> limit) {
        this.limit = limit;
    }
}
