package cn.gtmap.realestate.building.config.liqui;

import cn.gtmap.realestate.building.core.dbs.DynamicDataSource;
import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.context.annotation.*;


import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.*;

/**
 *
 */

@Configuration
public class LiDataSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(LiDataSourceConfig.class);

    @Autowired
    DynamicDataSource dynamicDataSource;


    private static List<String> dataSorceKey = new ArrayList<>();
    /**
     * Liquibase多数据源
     *
     * @return
     */
    @Bean
    @Primary
    @LiquibaseDataSource
    @Scope("prototype")
    public SpringLiquibase SpringLiquibaseDefault() {
        Map<Object, Object> dataSourceMap = dynamicDataSource.getDataSourceMap();
        for (Map.Entry<Object, Object> objectObjectEntry : dataSourceMap.entrySet()) {
            if (dataSorceKey.contains(objectObjectEntry.getKey().toString())) {
                continue;
            }else {
                if(testConnection((DruidDataSource) objectObjectEntry.getValue())) {
                    SpringLiquibase liquibase = new SpringLiquibase();
                    liquibase.setDataSource((DataSource) objectObjectEntry.getValue());
                    liquibase.setChangeLog("classpath:changelog/master.xml");
                    liquibase.setContexts(objectObjectEntry.getKey().toString());
                    dataSorceKey.add(objectObjectEntry.getKey().toString());
                    return liquibase;
                }
            }
        }
        return null;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  测试能否连接成功
     */
    private boolean testConnection(DruidDataSource druidDataSource){
        boolean conn =false;
        try {
            Driver driver =DriverManager.getDriver(druidDataSource.getUrl());
            Properties properties =new Properties();
            properties.put("user",druidDataSource.getUsername());
            properties.put("password",ConfigTools.decrypt(druidDataSource.getConnectProperties().getProperty("config.decrypt.key"), druidDataSource.getPassword()));
            driver.connect(druidDataSource.getUrl(),properties);
            conn =true;
        }catch (Exception e){
            logger.error("数据库连接:{}连接失败:{}",druidDataSource.getUrl(),e);
        }
        return conn;
    }
}
