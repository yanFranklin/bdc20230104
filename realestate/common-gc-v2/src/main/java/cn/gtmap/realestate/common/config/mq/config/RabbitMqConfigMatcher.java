package cn.gtmap.realestate.common.config.mq.config;

import cn.gtmap.gtc.start.config.audit.rabbit.RabbitMqConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/03/28
 * @description MQ处理配置导入 V2.x版本适配
 */
@Configuration
@Import({RabbitMqConfig.class})
public class RabbitMqConfigMatcher {
}
