package cn.gtmap.interchange;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * 常州部署于中间服务器进行数据中转应用
 */
@ComponentScan(basePackages = "cn.gtmap.interchange", excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE)})
@SpringBootApplication
public class InterchangeApp {

    public static void main(String[] args) {
        SpringApplication.run(InterchangeApp.class, args);
    }

}
