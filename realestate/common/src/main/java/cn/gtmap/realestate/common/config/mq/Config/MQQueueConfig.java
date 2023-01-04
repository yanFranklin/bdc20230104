package cn.gtmap.realestate.common.config.mq.Config;

import cn.gtmap.realestate.common.config.mq.enums.RabbitMqEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0.2019/07/17
 * @description 定义队列（exchange）默认为持久化队列
 */
@Configuration
public class MQQueueConfig {
    private static final Logger logger = LoggerFactory.getLogger(MQQueueConfig.class);

    @Autowired
    RabbitAdmin rabbitAdmin;


    @Bean
    Queue queueSynBdczt(){
        Queue queue = new Queue(RabbitMqEnum.QueueName.BDCDYZTQUEUE.getCode());
        rabbitAdmin.declareQueue(queue);
        logger.debug("不动产单元状态队列实例化完成");
        return queue;
    }

    @Bean
    Queue queueSynBdcsdzt(){
        Queue queue = new Queue(RabbitMqEnum.QueueName.BDCDYSDZTQUEUE.getCode());
        rabbitAdmin.declareQueue(queue);
        logger.debug("不动产单元锁定状态队列实例化完成");
        return queue;
    }

    @Bean
    Queue queueSynBdcxx(){
        Queue queue = new Queue(RabbitMqEnum.QueueName.BDCDYXXQUEUE.getCode());
        rabbitAdmin.declareQueue(queue);
        logger.debug("不动产单元信息队列实例化完成");
        return queue;
    }

    @Bean
    Queue queuePpSynBdczt(){
        Queue queue = new Queue(RabbitMqEnum.QueueName.SYNCBDCDYZTQUEUE.getCode());
        rabbitAdmin.declareQueue(queue);
        logger.debug("匹配不动产单元同步状态队列实例化完成");
        return queue;
    }

    @Bean
    Queue hxQlFj() {
        Queue queue = new Queue(RabbitMqEnum.QueueName.HXQLFJQUEUE.getCode());
        rabbitAdmin.declareQueue(queue);
        logger.debug("回写权利附记完成");
        return queue;
    }

    @Bean
    Queue queueBdcdjInsertAuditLog() {
        Queue queue = new Queue(RabbitMqEnum.QueueName.BDCDJINSERTAUDITLOGQUEUE.getCode());
        rabbitAdmin.declareQueue(queue);
        logger.debug("落表操作完成");
        return queue;
    }

    @Bean
    Queue queueHdhsfe() {
        Queue queue = new Queue(RabbitMqEnum.QueueName.SYNCHDHSFEQUEUE.getCode());
        rabbitAdmin.declareQueue(queue);
        logger.debug("核定户室份额完成");
        return queue;
    }

    @Bean
    Queue queueLogRecord() {
        Queue queue = new Queue(RabbitMqEnum.QueueName.LOGRECORDQUEUE.getCode());
        rabbitAdmin.declareQueue(queue);
        logger.debug("日志记录完成");
        return queue;
    }

    @Bean
    Queue queueDbjrDb() {
        Queue queue = new Queue(RabbitMqEnum.QueueName.BDCDBHJSBQUEUE.getCode());
        rabbitAdmin.declareQueue(queue);
        logger.debug("登簿接入数据对比");
        return queue;
    }

    @Bean
    Queue queueJkglLog() {
        Queue queue = new Queue(RabbitMqEnum.QueueName.JKGLLOGQUEUE.getCode(), false, false , false, null);
        rabbitAdmin.declareQueue(queue);
        logger.debug("接口管理日志记录队列实例化完成");
        return queue;
    }
}
