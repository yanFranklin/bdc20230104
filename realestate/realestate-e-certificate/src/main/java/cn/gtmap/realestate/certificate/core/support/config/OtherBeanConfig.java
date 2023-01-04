package cn.gtmap.realestate.certificate.core.support.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author <a href="mailto:lst@gtmap.cn">lst</a>
 * @version 1.0  2018/10/30
 * @description 其他系统服务
 */
@Configuration
@ImportResource(locations={"classpath:conf/spring/config-service.xml"})
public class OtherBeanConfig {
}
