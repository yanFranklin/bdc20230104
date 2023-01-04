package cn.gtmap.realestate.portal.ui.config;

import cn.gtmap.realestate.common.matcher.MqNotifyMatcher;
import cn.gtmap.realestate.portal.ui.utils.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;

import java.util.Map;

/**
 * 处理mq信息服务
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/3/28.
 * @description
 */
@Configuration
public class NotifyConfig extends MqNotifyMatcher {
    @Autowired
    private MyWebSocketHandler handler;

    @Override
    public void resolveNotify(Map msg) {
        try {
            String type = Constants.UNDERLINE + msg.get("recUsername");
            // 其他客户端登录，修改 websocket 发送对象
            if (StringUtils.equals(String.valueOf(msg.get("MsgType")), Constants.USER_DISCONNECT)) {
                JSONObject msgContent = JSON.parseObject(String.valueOf(msg.get("msgContent")));
                // remoteAddr 中记录了被踢的用户 ip, username 记录了 用户名
                String ip = msgContent.getString("remoteAddr");
                String username = msgContent.getString("username");
                type = ip + Constants.UNDERLINE + username;
            }
            //空为默认
            String[] msgType={"HOME","TODO",""};
            for(String str:msgType){
                if(StringUtils.isNotBlank(str)){
                    handler.sendMsgToHtml(new TextMessage(JSONObject.toJSONString(msg)), type+Constants.UNDERLINE +str);
                }else{
                    handler.sendMsgToHtml(new TextMessage(JSONObject.toJSONString(msg)), type);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
