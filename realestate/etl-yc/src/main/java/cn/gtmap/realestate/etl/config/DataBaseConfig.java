package cn.gtmap.realestate.etl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/30
 * @description 数据库配置类
 */
@Configuration
@ImportResource(locations={"classpath:conf/spring/config-mybatis.xml"})
public class DataBaseConfig {

}
