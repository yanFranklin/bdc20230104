package cn.gtmap.realestate.accept.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0, 2022/1/10
 * @description 南通获取房产交易信息配置
 */
@Component
@ConfigurationProperties(prefix = "fcjyxx")
public class FcjyxxConfig {
    /**
     * 存放住建pdf的文件夹名称
     */
    private String wjjmc;
    /**
     * 存放商品房住建pdf的文件夹名称
     */
    private String spfWjjmc;
    /**
     * 根据区县代码配置调用接口的beanName
     */
    private Map<String, String> qxdmBeanNameMap = new HashMap<>();

    public String getWjjmc() {
        return wjjmc;
    }

    public void setWjjmc(String wjjmc) {
        this.wjjmc = wjjmc;
    }

    public String getSpfWjjmc() {
        return spfWjjmc;
    }

    public void setSpfWjjmc(String spfWjjmc) {
        this.spfWjjmc = spfWjjmc;
    }

    public Map<String, String> getQxdmBeanNameMap() {
        return qxdmBeanNameMap;
    }

    public void setQxdmBeanNameMap(Map<String, String> qxdmBeanNameMap) {
        this.qxdmBeanNameMap = qxdmBeanNameMap;
    }
}
