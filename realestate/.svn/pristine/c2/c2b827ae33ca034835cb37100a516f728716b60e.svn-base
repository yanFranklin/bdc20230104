package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.core.bo.xsd.LogBO;
import cn.gtmap.realestate.exchange.service.inf.log.LogService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-02-26
 * @description 日志记录方式的 配置类
 */
@Component
@ConfigurationProperties(prefix = "log-service")
public class LogUtil {

    // 默认服务
    @Value("${defaultService:logInEs}")
    private String defaultService;

    // 是否默认全部记录ES日志
    @Value("${allInEs:false}")
    private boolean allInEs;

    /**
     * 市级共享记录日志类型集合， 默认为：ES日志，支持ES和数据库同步
     */
    @Value("#{'${shijigxRzTypes:logInEs}'.split(',')}")
    private List<String> shijigxRzTypes;

    // 键值对  key值为logService beanId value为接口地址
    private Map<String,List<String>> config;

    // config处理后的Map结构 key值是接口地址 value是logService
    private volatile Map<String,String> configMap = new HashMap<>();

    private static final String LOGSERVICE_LOGINES = "logInEs";

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param beanId
     * @return cn.gtmap.realestate.exchange.service.inf.log.LogService
     * @description 根据BeanId获取bean
     */
    public LogService getServiceWithBeanId(String beanId){
        LogService logService = (LogService)Container.getBean(beanId);
        return logService;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param logBO
     * @return cn.gtmap.realestate.exchange.service.inf.log.LogService
     * @description
     */
    public LogService getServiceWithLogBo(LogBO logBO){
        LogService logService = null;
        // 用 beanId 做的配置
        if(StringUtils.isNotBlank(logBO.getBeanId())){
            logService = getBeanIdWithBdcdz("-"+logBO.getBeanId());
        }
        // 1.用接口地址获取yml中配置的日志服务
        if(logService == null && StringUtils.isNotBlank(logBO.getBdcdz())){
            logService = getBeanIdWithBdcdz(logBO.getBdcdz());
        }
        // 2.如果没有配置，再使用log注解或者xml中配置的日志服务
        if(logService == null && StringUtils.isNotBlank(logBO.getLogService())){
            logService = getServiceWithBeanId(logBO.getLogService());
        }
        // 3.使用yml配置的默认日志记录方式
        if(logService == null){
            logService = getServiceWithBeanId(getDefaultService());
        }
        return logService;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param logBO
     * @return java.util.List<cn.gtmap.realestate.exchange.service.inf.log.LogService>
     * @description 默认添加 记录在ES 中的 处理方式
     */
    public List<LogService> getServiceList(LogBO logBO){
        List<LogService> logServiceList = new ArrayList<>();
        LogService pzService = getServiceWithLogBo(logBO);
        logServiceList.add(pzService);
        if(allInEs){
            LogService esService = getServiceWithBeanId(LOGSERVICE_LOGINES);
            if(!esService.equals(pzService)){
                logServiceList.add(esService);
            }
        }
        return logServiceList;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdz
     * @return cn.gtmap.realestate.exchange.service.inf.log.LogService
     * @description 根据请求地址 获取 记录日志服务
     */
    public LogService getBeanIdWithBdcdz(String bdcdz){
        if(MapUtils.isEmpty(configMap)){
            initConfigMap();
        }
        String beanId = configMap.get(bdcdz);
        if(StringUtils.isNotBlank(beanId)){
            return getServiceWithBeanId(beanId);
        }
        return null;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return void
     * @description 处理config 为 configMap
     */
    private synchronized void initConfigMap(){
        if(MapUtils.isNotEmpty(config)){
            Iterator<Map.Entry<String, List<String>>> iterator = config.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, List<String>> temp = iterator.next();
                String key = temp.getKey();
                List<String> bdcdzs = temp.getValue();
                if(bdcdzs.size() > 0){
                    for(int i = 0 ;i < bdcdzs.size() ; i++){
                        configMap.put(bdcdzs.get(i),key);
                    }
                }
            }
        }
    }

    public String getDefaultService() {
        return defaultService;
    }

    public void setDefaultService(String defaultService) {
        this.defaultService = defaultService;
    }


    public Map<String, String> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(Map<String, String> configMap) {
        this.configMap = configMap;
    }

    public void setConfig(Map<String, List<String>> config) {
        this.config = config;
    }

    public Map<String, List<String>> getConfig() {
        return config;
    }

    public List<String> getShijigxRzTypes() {
        return shijigxRzTypes;
    }

    public void setShijigxRzTypes(List<String> shijigxRzTypes) {
        this.shijigxRzTypes = shijigxRzTypes;
    }
}
