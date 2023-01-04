package cn.gtmap.realestate.common.config;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.*;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * 从数据库加载特殊业务配置
 * CertificateApp未配置
 */
@Configuration
public class AutomaticLoadingConfig {

    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    @Autowired
    private RedisUtils redisUtils;

    private static final Logger LOGGER = LoggerFactory.getLogger(AutomaticLoadingConfig.class);

    private static final Pattern pattern = Pattern.compile("^applicationConfig.*");

    @PostConstruct
    public void initSystemConfig() {

        /**
         * 1.判断是否启用数据库配置
         */
        // 获取所有的系统配置 MutablePropertySources 是PropertySources的一个实现类,它将所有的PropertySource都放置在一个名叫propertySourceList集合
        MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
        String loadConfigFromDataBaseFlag = "false";

        // 获取apollo所有的namespaces,以及yml里的配置
        Map<Object, Object> ymlConfig = new ConcurrentHashMap<>();
        for (PropertySource<?> source : propertySources) {
//            ymlConfig.putAll((Map) JSON.parse(JSON.toJSON(source.getSource()).toString()));
            Object obj = JSON.parse(JSON.toJSON(source.getSource()).toString());
            if(obj instanceof JSONObject) {
                ymlConfig.putAll((Map)obj);
            } else if(obj instanceof JSONArray){
                for(Object object : (JSONArray)obj) {
                    ymlConfig.putAll((Map) JSON.parseObject(JSON.toJSONString(object), Map.class));
                }
            } else {
                // TODO
            }
        }

        if (ymlConfig.containsKey("config.loadingConfigFlag") && Objects.nonNull(ymlConfig.get("config.loadingConfigFlag"))) {
            loadConfigFromDataBaseFlag = ymlConfig.get("config.loadingConfigFlag").toString();
            LOGGER.info("loadConfigFromDataBaseFlag from yml:{}", loadConfigFromDataBaseFlag);
        }

        if (ymlConfig.containsKey("apollo.bootstrap.namespaces") && Objects.nonNull(ymlConfig.get("apollo.bootstrap.namespaces"))) {
            String[] namespaces = ymlConfig.get("apollo.bootstrap.namespaces").toString().split(",");
            for (String namespace : namespaces) {
                Config appConfig = ConfigService.getConfig(namespace);
                Set<String> propertyNames = appConfig.getPropertyNames();
                if (propertyNames.contains("config.loadingConfigFlag")) {
                    loadConfigFromDataBaseFlag = appConfig.getProperty("config.loadingConfigFlag", "false");
                    LOGGER.info("loadConfigFromDataBaseFlag from apollo:{}", loadConfigFromDataBaseFlag);
                }
            }
        }

        String loadConfigFromRedis = redisUtils.getHashValue(CommonConstantUtils.REDIS_TSYW_PZ, "config.loadingConfigFlag");
        if (StringUtils.isNotBlank(loadConfigFromRedis) && !"null".equals(loadConfigFromRedis)) {
            loadConfigFromDataBaseFlag = loadConfigFromRedis;
            LOGGER.info("获取数据库配置中是否启用数据库配置值:{}", loadConfigFromDataBaseFlag);
        }

        LOGGER.info("是否启动数据库配置:{}", loadConfigFromDataBaseFlag);

        /**
         * 2.从数据库加载配置项
         */
        if (StringUtils.equals("true", loadConfigFromDataBaseFlag)) {
            Map<Object, Object> newConfig = new HashMap<>();
            /**
             * 2.1从redis获取自定义变量列表,将转换后的列表加入属性中,将属性转换为属性集合,并指定名称为当前sourceName,替换配置项的值
             */
            //先查询共用的配置项
            Map<Object, Object> configFromDataBase = redisUtils.getHash(CommonConstantUtils.REDIS_TSYW_PZ);
            Map<Object, Object> publicconfigFromDataBase = redisUtils.getHash(CommonConstantUtils.REDIS_TSYW_PZ+ "_" +"public-app");
            //再查询当前子系统的配置项
            Map<Object, Object> zxtconfigFromDataBase = redisUtils.getHash(CommonConstantUtils.REDIS_TSYW_PZ + "_" + ymlConfig.get("spring.application.name"));
            LOGGER.info("config date size from redis:{}", configFromDataBase.size());
            LOGGER.info("config date from redis:{}", JSON.toJSONString(configFromDataBase));
            newConfig.putAll(configFromDataBase);
            newConfig.putAll(publicconfigFromDataBase);
            newConfig.putAll(zxtconfigFromDataBase);

            String name = null;
//            Pattern p = Pattern.compile("^applicationConfig.*");
            for (PropertySource<?> source : propertySources) {
                LOGGER.info("===>source name:{}", source.getName());
                if (pattern.matcher(source.getName()).matches()) {
                    name = source.getName();

                    Map<Object, Object> origin = (Map<Object, Object>) source.getSource();
                    Map<Object, Object> map = mergeMap(origin, newConfig);

                    LOGGER.info("===>name:{}", name);
                    LOGGER.info("===>source:{}", map);
                    Properties properties = new Properties();
                    properties.putAll(map);
                    PropertiesPropertySource dataBaseConstants = new PropertiesPropertySource(name, properties);
                    propertySources.replace(name, dataBaseConstants);
                }
            }
        }
    }

    /**
     * 合并map 合并策略:source和target中相同key,source优先级高
     *
     * @param target
     * @param source
     * @return
     */
    private Map<Object, Object> mergeMap(Map<Object, Object> target, Map<Object, Object> source) {
        Map<Object, Object> map = new HashMap<>();
        map.putAll(target);
        map.putAll(source);
        return map;
    }
}
