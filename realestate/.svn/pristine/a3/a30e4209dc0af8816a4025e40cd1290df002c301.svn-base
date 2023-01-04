package cn.gtmap.realestate.common.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: realestate
 * @description: 字典排序
 * @author: <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @create: 2021-06-18 09:57
 **/
@Component
@ConfigurationProperties(prefix = "zd")
public class ZdpxConfig {

    /**
     * key为表名（全大写）  value为排序规则
     */
    private Map<String, String> dbpxgzmap;

    public Map<String, String> getDbpxgzmap() {
        return dbpxgzmap;
    }

    public void setDbpxgzmap(Map<String, String> dbpxgzmap) {
        this.dbpxgzmap = dbpxgzmap;
    }
}
