package cn.gtmap.realestate.register.config;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/11
 * @description 配置项信息处理类
 * <p>
 * 1、对应配置文件application-config.yml中register下配置项
 * 2、本配置类只负责相关配置项自动装配，具体获取bean操作已提取至common中
 * 3、为了便于业务层维护等，不再提供自动批量执行方法功能
 * </p>
 */
@Component
@ConfigurationProperties(prefix = "register")
public class PropsConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsConfig.class);

    /**
     * 配置文件中tdCqBeans项对应值集合，application-config.yml中同名配置项会设值到该集合
     */
    private List<String> ffwCqBeans = new ArrayList<>();

    /**
     * 配置文件中fwCqBeans项对应值集合，application-config.yml中同名配置项会设值到该集合
     */
    private List<String> fwCqBeans = new ArrayList<>();

    /**
     * 配置文件中templatePaths项对应值集合，application-config.yml中同名配置项会设值到该集合
     */
    private Map<String, String> templatePaths = new HashMap<>();


    /**
     * @param key 目标模板对应键值
     * @return 模板路径
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取指定的模板路径
     */
    public String getTemplatePathOfMapItem(Object key) {
        if (MapUtils.isEmpty(this.getTemplatePaths()) || StringUtils.isBlank(String.valueOf(key))) {
            LOGGER.info("获取指定的模板配置有误！");
            return null;
        }
        return MapUtils.getString(this.getTemplatePaths(), key);
    }

    public List<String> getFfwCqBeans() {
        return ffwCqBeans;
    }

    public void setFfwCqBeans(List<String> ffwCqBeans) {
        this.ffwCqBeans = ffwCqBeans;
    }

    public List<String> getFwCqBeans() {
        return fwCqBeans;
    }

    public void setFwCqBeans(List<String> fwCqBeans) {
        this.fwCqBeans = fwCqBeans;
    }

    public Map<String, String> getTemplatePaths() {
        return templatePaths;
    }

    public void setTemplatePaths(Map<String, String> templatePaths) {
        this.templatePaths = templatePaths;
    }
}
