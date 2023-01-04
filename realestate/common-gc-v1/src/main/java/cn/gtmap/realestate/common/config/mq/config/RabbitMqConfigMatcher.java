package cn.gtmap.realestate.common.config.mq.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import cn.gtmap.gtc.msg.rabbitmq.config.RabbitMqConfig;
import cn.gtmap.gtc.msg.rabbitmq.config.RabbitProduceConfig;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/03/28
 * @description MQ处理配置导入 V1.x版本适配
 */
@Configuration
@Import({RabbitMqConfig.class, RabbitProduceConfig.class})
public class RabbitMqConfigMatcher {
}
