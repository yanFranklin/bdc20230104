package cn.gtmap.realestate.building.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.Properties;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-10-30
 * @description
 */
@Configuration
@ImportResource(locations = {"classpath:conf/spring/config-mybatis.xml"})
public class DataBaseConfig {

    /**
     * @param
     * @return org.apache.ibatis.session.Configuration
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 定义Configuration Bean
     * 由于XML配置全局变量时，无法读取属性文件
     */
    @Bean
    public org.apache.ibatis.session.Configuration configuration(@Value("${fwkTableName}")String fwkTableName,
                                                                 @Value("${maxBgbhSeq}") String maxBgbhSeq) {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        Properties properties = new Properties();
        properties.setProperty("fwk", fwkTableName);
        properties.setProperty("maxBgbhSeq",maxBgbhSeq);
        // 定义mybatis全局变量
        configuration.setVariables(properties);
        // 全局映射器启用缓存
        configuration.setCacheEnabled(false);
        // 驼峰命名
        configuration.setMapUnderscoreToCamelCase(true);
        // 将空值字段也映射到map中
        configuration.setCallSettersOnNulls(true);
        return configuration;
    }
}
