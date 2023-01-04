package cn.gtmap.realestate.common.config.mq.Config;

import cn.gtmap.realestate.common.config.mq.enums.RabbitMqEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0.2019/07/17
 * @description 定义交换机（exchange）默认为持久化
 * 1.路由模式（DirectExchange）
 * 2 广播模式（FanoutExchange）
 * 3 模糊匹配模式（TopicExchange）
 */
@Configuration
public class MQExchangeConfig {

    private static final Logger logger = LoggerFactory.getLogger(MQExchangeConfig.class);

    @Autowired
    RabbitAdmin rabbitAdmin;

    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 路由方式交换机
     */
    @Bean
    DirectExchange directExchange() {
        DirectExchange directExchange = new DirectExchange(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode());
        rabbitAdmin.declareExchange(directExchange);
        logger.debug("创建路由方式交换机成功");
        return directExchange;
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return
     * @description 模糊匹配方式交换机
     * （需要时开启并删除此注释）
     */
    /*@Bean
    TopicExchange topicExchange() {
        TopicExchange topicExchange = new TopicExchange(RabbitMqEnum.Exchange.CONTRACT_TOPIC.getCode());
        rabbitAdmin.declareExchange(topicExchange);
        logger.debug("创建模糊匹配方式交换机成功");
        return topicExchange;
    }*/

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return
     * @description 广播方式交换机
     * （需要时开启并删除此注释）
     */
    /*FanoutExchange fanoutExchange(){
        FanoutExchange fanoutExchange=new FanoutExchange(RabbitMqEnum.Exchange.CONTRACT_FANOUT.getCode());
        rabbitAdmin.declareExchange(fanoutExchange);
        logger.debug("创建广播方式交换机成功");
        return fanoutExchange;
    }*/


}
