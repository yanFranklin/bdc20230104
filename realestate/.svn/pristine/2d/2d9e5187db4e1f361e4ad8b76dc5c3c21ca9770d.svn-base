package cn.gtmap.realestate.building.config.liqui;

import cn.gtmap.realestate.building.core.dbs.DynamicDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Map;

/**
 *
 */

@Configuration
public class LiDataSourceMutilConfig {
    @Autowired
    LiDataSourceConfig liDataSourceConfig;

    @Autowired
    DynamicDataSource dynamicDataSource;

    @Autowired
    public void configExporters() {
        for (Map.Entry<Object, Object> objectObjectEntry : dynamicDataSource.getDataSourceMap().entrySet()) {
            liDataSourceConfig.SpringLiquibaseDefault();
        }
    }
}
