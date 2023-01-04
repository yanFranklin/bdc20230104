package cn.gtmap.realestate.register.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @create: 2021-10-26 15:03
 * @description: 46984 【常州】武进档案号生成规则
 **/
@Component
@ConfigurationProperties(prefix = "dajj.dahprefix2")
public class DajjDahPrefix2Config {
    /**
     * key：编号类型  value：登记小类（多个逗号拼接）
     */
    private Map<Integer, String> configMap;

    private Map<Integer, String> mlhconfigMap;

    private String qzh;
    public Map<Integer, String> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(Map<Integer, String> configMap) {
        this.configMap = configMap;
    }

    public Map<Integer, String> getMlhconfigMap() {
        return mlhconfigMap;
    }

    public void setMlhconfigMap(Map<Integer, String> mlhconfigMap) {
        this.mlhconfigMap = mlhconfigMap;
    }

    public String getQzh() {
        return qzh;
    }

    public void setQzh(String qzh) {
        this.qzh = qzh;
    }
}
