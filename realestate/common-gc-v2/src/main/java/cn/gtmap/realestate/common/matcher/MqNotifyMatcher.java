package cn.gtmap.realestate.common.matcher;

import cn.gtmap.gtc.start.config.message.consumer.NotifyConsumer;
import cn.gtmap.gtc.start.config.message.consumer.NotifyMsgSource;
import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/03/28
 * @description  处理mq信息服务 V21.x版本适配
 */
public abstract class MqNotifyMatcher implements NotifyConsumer {
    /**
     * 登记适配接口进行具体消息处理（为了V1 V2版本参数兼容，采用Map参数）
     * @param msg MQ消息
     */
    public abstract void resolveNotify(Map msg);

    /**
     * 大云接口接收消息
     * @param notify
     */
    @Override
    public void recNotify(NotifyMsgSource notify) {
        resolveNotify(JSON.parseObject(JSON.toJSONString(notify), Map.class));
    }
}
