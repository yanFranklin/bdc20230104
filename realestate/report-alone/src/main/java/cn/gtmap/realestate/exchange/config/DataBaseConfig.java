package cn.gtmap.realestate.exchange.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/10/30
 * @description
 */
@Configuration
@ImportResource(locations = {
        "classpath:spring/config-mybatis.xml",
        "classpath:spring/config-service.xml",
        "classpath:spring/config-dozer.xml",
        "classpath:spring/config-quartz.xml"})
public class DataBaseConfig {

    @Bean(name = "serverDataSource")
    @Qualifier("serverDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.server")
    public DataSource serverDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "gxDataSource")
    @Qualifier("gxDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.gx")
    public DataSource gxDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "qjDataSource")
    @Qualifier("qjDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.qj")
    public DataSource qjDataSource() {
        return DataSourceBuilder.create().build();
    }
}
