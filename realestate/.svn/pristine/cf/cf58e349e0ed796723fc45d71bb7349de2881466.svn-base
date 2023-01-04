package cn.gtmap.realestate.accept.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">caolu</a>
 * @version 1.0, 2021/11/24
 * @description 南通获取税票配置
 */
@Component
@ConfigurationProperties(prefix = "swsp")
public class SwspConfig {
    /**
     * 存放文件的文件夹名称
     */
    private String wjjmc;
    /**
     * 根据区县代码配置调用接口的beanName
     */
    private Map<String, String> qxdmBeanNameMap = new HashMap<>();
    /**
     * 根据区县代码配置数据归属地
     */
    private Map<String, String> qxdmSjgsdMap = new HashMap<>();

    public String getWjjmc() {
        return wjjmc;
    }

    public void setWjjmc(String wjjmc) {
        this.wjjmc = wjjmc;
    }

    public Map<String, String> getQxdmBeanNameMap() {
        return qxdmBeanNameMap;
    }

    public void setQxdmBeanNameMap(Map<String, String> qxdmBeanNameMap) {
        this.qxdmBeanNameMap = qxdmBeanNameMap;
    }

    public Map<String, String> getQxdmSjgsdMap() {
        return qxdmSjgsdMap;
    }

    public void setQxdmSjgsdMap(Map<String, String> qxdmSjgsdMap) {
        this.qxdmSjgsdMap = qxdmSjgsdMap;
    }
}
