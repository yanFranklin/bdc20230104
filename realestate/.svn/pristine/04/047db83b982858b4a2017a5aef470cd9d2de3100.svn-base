package cn.gtmap.realestate.portal.ui.config;

import cn.gtmap.realestate.portal.ui.service.BdcCheckValidSqlConditionService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-10-12
 * @description
 */
@Component
@ConfigurationProperties(prefix = "btxyz.condition")
public class ValidSqlConditionBeans implements ApplicationContextAware {
    private Map<String, Class> serviceMap = new ConcurrentHashMap<>();
    /**
     * bean工厂集合
     */
    private Map<String, BdcCheckValidSqlConditionService> checkConditionBeanMap = new ConcurrentHashMap<>();

    public Map<String, Class> getServiceMap() {
        return serviceMap;
    }

    public void setServiceMap(Map<String, Class> serviceMap) {
        this.serviceMap = serviceMap;
    }

    public Map<String, BdcCheckValidSqlConditionService> getCheckConditionBeanMap() {
        return checkConditionBeanMap;
    }

    public void setCheckConditionBeanMap(Map<String, BdcCheckValidSqlConditionService> checkConditionBeanMap) {
        this.checkConditionBeanMap = checkConditionBeanMap;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (MapUtils.isNotEmpty(serviceMap)) {
            serviceMap.forEach((k, v) -> {
                checkConditionBeanMap.put(k, applicationContext.getBean((Class<BdcCheckValidSqlConditionService>) v));
            });
        }
    }
}
