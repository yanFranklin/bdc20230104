package cn.gtmap.realestate.common.config.mq.Config;

import cn.gtmap.realestate.common.config.mq.enums.RabbitMqEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0.2019/07/17
 * @description 交换机（exchange）和队列（queue）绑定
 */
@Configuration
public class MQBindingConfig {

    private static final Logger logger = LoggerFactory.getLogger(MQBindingConfig.class);

    @Autowired
    RabbitAdmin rabbitAdmin;
    @Autowired
    MQExchangeConfig exchange;
    @Autowired
    MQQueueConfig queue;

    @Bean
    Binding bindingQueueSynBdcxx(){
        Binding binding = BindingBuilder.bind(queue.queueSynBdcxx()).to(exchange.directExchange()).with(RabbitMqEnum.QueueEnum.BDCDYXXQUEUE.getCode());
        rabbitAdmin.declareBinding(binding);
        logger.debug("不动产单元信息队列与直连型交换机绑定完成");
        return binding;
    }

    @Bean
    Binding bindingQueuePpSynBdczt(){
        Binding binding = BindingBuilder.bind(queue.queuePpSynBdczt()).to(exchange.directExchange()).with(RabbitMqEnum.QueueEnum.SYNCBDCDYZTQUEUE.getCode());
        rabbitAdmin.declareBinding(binding);
        logger.debug("不动产单元信息队列与直连型交换机绑定完成");
        return binding;
    }

    @Bean
    Binding bindingQueueSynBdcsdzt(){
        Binding binding = BindingBuilder.bind(queue.queueSynBdcsdzt()).to(exchange.directExchange()).with(RabbitMqEnum.QueueEnum.BDCDYSDZTQUEUE.getCode());
        rabbitAdmin.declareBinding(binding);
        logger.debug("不动产单元信息队列与直连型交换机绑定完成");
        return binding;
    }

    @Bean
    Binding bindingQueueHxQlFj() {
        Binding binding = BindingBuilder.bind(queue.hxQlFj()).to(exchange.directExchange()).with(RabbitMqEnum.QueueEnum.HXQLFJQUEUE.getCode());
        rabbitAdmin.declareBinding(binding);
        logger.debug("回写权利附记队列与直连型交换机绑定完成");
        return binding;
    }

    @Bean
    Binding bindingQueueBdcdjInsertAuditLog() {
        Binding binding = BindingBuilder.bind(queue.queueBdcdjInsertAuditLog()).to(exchange.directExchange()).with(RabbitMqEnum.QueueEnum.BDCDJINSERTAUDITLOGQUEUE.getCode());
        rabbitAdmin.declareBinding(binding);
        logger.debug("exchange日志落表操作队列与直连型交换机绑定完成");
        return binding;
    }

    @Bean
    Binding bindingQueueHdhsfe() {
        Binding binding = BindingBuilder.bind(queue.queueHdhsfe()).to(exchange.directExchange()).with(RabbitMqEnum.QueueEnum.SYNCHDHSFEQUEUE.getCode());
        rabbitAdmin.declareBinding(binding);
        logger.debug("exchange日志落表操作队列与直连型交换机绑定完成");
        return binding;
    }

    @Bean
    Binding bindingQueueLogRecord() {
        Binding binding = BindingBuilder.bind(queue.queueLogRecord()).to(exchange.directExchange()).with(RabbitMqEnum.QueueEnum.LOGRECORDQUEUE.getCode());
        rabbitAdmin.declareBinding(binding);
        logger.debug("日志记录队列与直连型交换机绑定完成");
        return binding;
    }


    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 登簿接入数据对比队列绑定
     * @date : 2022/6/28 21:01
     */
    @Bean
    Binding bindingQueueDbrzDb() {
        Binding binding = BindingBuilder.bind(queue.queueDbjrDb()).to(exchange.directExchange()).with(RabbitMqEnum.QueueEnum.BDCDJDBHJQUNEUE.getCode());
        rabbitAdmin.declareBinding(binding);
        logger.debug("登簿接入数据对比队列与直连型交换机绑定完成");
        return binding;
    }

}
