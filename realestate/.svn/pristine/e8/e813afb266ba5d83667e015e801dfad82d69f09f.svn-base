package cn.gtmap.realestate.accept.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/8/31
 * @description 评价器配置
 */
@Component
@ConfigurationProperties(prefix = "pjq")
public class AcceptPjqConfig {
    /**
     * 评价器版本,可选项为MK_L(摩科老版本),MK_N（摩科新版本）,QDWS(青大维森)
     */
    private String pjqbb;

    /**
     * 根据区县代码分类评价器的版本
     */
    private Map<String, String> qxdmPjqbbMap = new HashMap<>();

    /**
     * 评价器调用接口地址
     */
    private String url;

    /**
     * 人证比对调用接口地址
     */
    private String rzbdUrl;
    /**
     * 根据区县代码分类评价器的调用接口地址
     */
    private Map<String, String> qxdmUrlMap = new HashMap<>();
    /**
     * 根据区县代码分类人证对比的调用接口地址
     */
    private Map<String, String> qxdmrzbdUrlMap = new HashMap<>();

    /**
     * 根据区县代码分类评价器的评价beanName
     */
    private Map<String, String> qxdmPjBeanNameMap = new HashMap<>();

    /**
     * 根据区县代码分类评价器的人证比对beanName
     */
    private Map<String, String> qxdmRzbdBeanNameMap = new HashMap<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getQxdmUrlMap() {
        return qxdmUrlMap;
    }

    public void setQxdmUrlMap(Map<String, String> qxdmUrlMap) {
        this.qxdmUrlMap = qxdmUrlMap;
    }

    public String getPjqbb() {
        return pjqbb;
    }

    public void setPjqbb(String pjqbb) {
        this.pjqbb = pjqbb;
    }

    public Map<String, String> getQxdmPjqbbMap() {
        return qxdmPjqbbMap;
    }

    public void setQxdmPjqbbMap(Map<String, String> qxdmPjqbbMap) {
        this.qxdmPjqbbMap = qxdmPjqbbMap;
    }

    public String getRzbdUrl() {
        return rzbdUrl;
    }

    public void setRzbdUrl(String rzbdUrl) {
        this.rzbdUrl = rzbdUrl;
    }

    public Map<String, String> getQxdmrzbdUrlMap() {
        return qxdmrzbdUrlMap;
    }

    public void setQxdmrzbdUrlMap(Map<String, String> qxdmrzbdUrlMap) {
        this.qxdmrzbdUrlMap = qxdmrzbdUrlMap;
    }

    public Map<String, String> getQxdmPjBeanNameMap() {
        return qxdmPjBeanNameMap;
    }

    public void setQxdmPjBeanNameMap(Map<String, String> qxdmPjBeanNameMap) {
        this.qxdmPjBeanNameMap = qxdmPjBeanNameMap;
    }

    public Map<String, String> getQxdmRzbdBeanNameMap() {
        return qxdmRzbdBeanNameMap;
    }

    public void setQxdmRzbdBeanNameMap(Map<String, String> qxdmRzbdBeanNameMap) {
        this.qxdmRzbdBeanNameMap = qxdmRzbdBeanNameMap;
    }
}
