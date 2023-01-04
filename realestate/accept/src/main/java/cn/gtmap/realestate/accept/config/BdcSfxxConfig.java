package cn.gtmap.realestate.accept.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 1.0  2022/5/31
 * @description 不动产收费信息配置项
 */
@Component
@ConfigurationProperties(prefix = "sfxx")
public class BdcSfxxConfig {

    /**
     * 根据区县代码调用缴费办理beanid
     */
    private Map<String, String> jfblbeanidmap = new HashMap<>();

    /**
     * 根据区县代码调用缴费确认beanid
     */
    private Map<String, String> jfqrbeanidmap = new HashMap<>();

    /**
     * 根据区县代码调用非税开票beanid
     */
    private Map<String, String> fskpbeanidmap = new HashMap<>();

    /**
     * 根据区县代码调用作废缴费通知书beanid
     */
    private Map<String, String> zfjktzsbeanidmap = new HashMap<>();

    /**
     * 根据区县代码调用非税单位信息
     */
    private Map<String, Map<String, String>> dwxx = new HashMap<>();

    public Map<String, String> getJfblbeanidmap() {
        return jfblbeanidmap;
    }

    public void setJfblbeanidmap(Map<String, String> jfblbeanidmap) {
        this.jfblbeanidmap = jfblbeanidmap;
    }

    public Map<String, String> getJfqrbeanidmap() {
        return jfqrbeanidmap;
    }

    public void setJfqrbeanidmap(Map<String, String> jfqrbeanidmap) {
        this.jfqrbeanidmap = jfqrbeanidmap;
    }

    public Map<String, String> getFskpbeanidmap() {
        return fskpbeanidmap;
    }

    public void setFskpbeanidmap(Map<String, String> fskpbeanidmap) {
        this.fskpbeanidmap = fskpbeanidmap;
    }

    public Map<String, String> getZfjktzsbeanidmap() {
        return zfjktzsbeanidmap;
    }

    public void setZfjktzsbeanidmap(Map<String, String> zfjktzsbeanidmap) {
        this.zfjktzsbeanidmap = zfjktzsbeanidmap;
    }

    public Map<String, Map<String, String>> getDwxx() {
        return dwxx;
    }

    public void setDwxx(Map<String, Map<String, String>> dwxx) {
        this.dwxx = dwxx;
    }
}


