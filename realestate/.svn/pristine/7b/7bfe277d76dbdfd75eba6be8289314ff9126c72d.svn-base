package cn.gtmap.realestate.init.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/1
 * @description 权利类型对应关系信息
 */
@Component
@ConfigurationProperties(prefix = "dzgx")
public class BdcDzgxConfig {

    /**
     * 权利类型对应关系
     */
    private Map<String, String> qllxMap=new HashMap<>();

    /**
     * 抵押不动产类型对应关系
     */
    private Map<Integer, Integer> dybdclxMap=new HashMap<>();


    public Map<Integer, Integer> getDybdclxMap() {
        return dybdclxMap;
    }

    public void setDybdclxMap(Map<Integer, Integer> dybdclxMap) {
        this.dybdclxMap = dybdclxMap;
    }

    public Map<String, String> getQllxMap() {
        return qllxMap;
    }

    public void setQllxMap(Map<String, String> qllxMap) {
        this.qllxMap = qllxMap;
    }
}
