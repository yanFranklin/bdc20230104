package cn.gtmap.interchange.config;

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
@ImportResource(locations={"classpath:spring/config-mybatis.xml"})
public class DataBaseConfig {

    @Bean(name = "gxDataSource")
    @Qualifier("gxDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.gx")
    public DataSource gxDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "yzwDataSource")
    @Qualifier("yzwDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.yzw")
    public DataSource yzwDataSource() {
        return DataSourceBuilder.create().build();
    }
}
