package cn.gtmap.realestate.exchange.config;

import cn.gtmap.realestate.common.matcher.DataSourceBuilderMatcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 数据库配置类
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @date v1.0, 2019/3/15 09:52
 */
@Configuration
@ImportResource(locations = {
        "classpath:conf/spring/config-mybatis.xml",
        "classpath:conf/spring/config-service.xml",
        "classpath:conf/spring/config-quartz.xml",
        "classpath:conf/spring/config-dozer.xml"})
public class DataBaseExchangeConfig {
    @Bean(name = "serverDataSource")
    @Qualifier("serverDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.server")
    public DataSource serverDataSource() {
        return DataSourceBuilderMatcher.dataSourceBuilder.create().build();
    }

    @Bean(name = "gxDataSource")
    @Qualifier("gxDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.gx")
    public DataSource secondaryDataSource() {
        return DataSourceBuilderMatcher.dataSourceBuilder.create().build();
    }

    @Bean(name = "sjptDataSource")
    @Qualifier("sjptDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.sjpt")
    public DataSource sjptDataSource() {
        return DataSourceBuilderMatcher.dataSourceBuilder.create().build();
    }
}
