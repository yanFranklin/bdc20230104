package cn.gtmap.realestate.natural.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/10/30
 * @description 数据库配置类
 */
@Configuration
@ImportResource(locations={"classpath:spring/config-mybatis.xml"})
public class DataBaseConfig {
}
