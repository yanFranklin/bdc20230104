package cn.gtmap.realestate.inquiry.config;

import cn.gtmap.realestate.common.matcher.DataSourceBuilderMatcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/07/24
 * @description
 */
@Configuration
@ImportResource(locations={"classpath:spring/config-mybatis.xml"})
public class DataBaseConfig {
    // 主数据源 登记库
    @Bean(name = "master")
    @Qualifier("master")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilderMatcher.dataSourceBuilder.create().build();
    }

    // 从数据源 大云
    @Bean(name = "cloudDataSource")
    @Qualifier("cloudDataSource")
    @ConfigurationProperties(prefix = "spring.cloud-data-source")
    public DataSource cloudDataSource() {
        return DataSourceBuilderMatcher.dataSourceBuilder.create().build();
    }
}
