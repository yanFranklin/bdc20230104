package cn.gtmap.realestate.common.config.mq.consumer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie/a>"
 * @version 1.0, 2019/07/16
 * @description MQ消费者监听工厂
 */
@Configuration
public class MQConsumerFactory {

    private static final Logger logger = LoggerFactory.getLogger(MQConsumerFactory.class);

    /**
     * 测试MQ（其中Bean的name，需要和消费者@RabbitListener的containerFactory值对应）
     *
     * @param connectionFactory
     * @return
     */
    @Bean({"rabbitListenerContainerFactory"})
    @ConditionalOnMissingBean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //ATUO为自动确认模式，MANUAL为手动确认模式
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

}
