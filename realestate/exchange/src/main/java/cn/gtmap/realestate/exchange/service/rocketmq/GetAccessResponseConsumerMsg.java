package cn.gtmap.realestate.exchange.service.rocketmq;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.exchange.service.national.NationalAccessUpload;
import cn.gtmap.realestate.exchange.util.UploadServiceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author <a href="mailto:gaofeng@gtmap.cn">zedq</a>
 * @version 1.0, 2021/08/23
 * @description 上报报文获取客户端消费者服务
 */
@Component
//@Profile(value = "rocketMQ")
public class GetAccessResponseConsumerMsg {

    private static final Logger logger = LoggerFactory.getLogger(GetAccessResponseConsumerMsg.class);

    private DefaultMQPushConsumer consumer;

    //组名称
    @Value("${custom.rocketMQ.clientGroupName:}")
    private String groupName;

    //mq地址
    @Value("${custom.rocketMQ.ip:}")
    private String ipAddress;

    @Value("${custom.rocketMQ.topicClient:}")
    private String clientTopic;

    @Value("${custom.rocketMQ.tag:}")
    private String tag;

    @Value("${custom.rocketMQ.message.encode:utf-8}")
    private String encode;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        if (StringUtils.isNotBlank(ipAddress)){
            logger.info("上报报文获取客户端消费者服务初始化:{},topic:{},tag:{},groupName:{}",ipAddress,clientTopic,tag,groupName);
            try {
                consumer = new DefaultMQPushConsumer(groupName);

                consumer.setNamesrvAddr(ipAddress);

                consumer.subscribe(clientTopic, tag);
//                consumer.setVipChannelEnabled(false);

                registerMsgListener();//注册监听

                consumer.start();
            } catch (Exception e) {
                logger.error("初始化客户端消费者服务异常！{}", e.getMessage());
            }
        }else {
            logger.info("未配置获取响应报文mq相关信息:{},topic:{},tag:{},groupName:{}",ipAddress,clientTopic,tag,groupName);
        }
    }


    /**
     * 注册消息监听
     */
    public void registerMsgListener() {
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
                for (MessageExt messageExt : msgs) {
                    try {
                        logger.info("上报响应报文消息id:{}",messageExt.getMsgId());
//                            System.out.println(new String(messageExt.getBody(), "UTF-8"));
                        String accessResponse = new String(messageExt.getBody(), encode);
                        logger.info("上报响应报文消息:{}",accessResponse);
                        JSONObject jsonObject = JSON.parseObject(accessResponse);
                        if ("0000".equals(jsonObject.getString("code")) && jsonObject.containsKey("msg") && StringUtils.isNotBlank(jsonObject.getString("msg"))){
                            List<NationalAccessUpload> list= UploadServiceUtil.listNationalAccessUpload();
                            //获取响应
                            for (NationalAccessUpload nationalAccessUpload : list) {
                                CommonResponse commonResponse = nationalAccessUpload.dealWithResponseForMq(StringEscapeUtils.unescapeJava(jsonObject.getString("msg")));
                                if (!commonResponse.isSuccess()){
                                    logger.info("上报响应报文消息处理失败:{}",JSON.toJSONString(commonResponse));
                                    throw new RuntimeException("上报响应报文处理失败:"+commonResponse.getFail().getMessage());
                                }
                            }

                        }else {
                            logger.info("上报响应报文消息格式有误:{}",accessResponse);
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        }

                    } catch (Exception e) {
                        logger.error("报文转换错误:{}", e.getMessage());
                    }
                }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
    }



}
