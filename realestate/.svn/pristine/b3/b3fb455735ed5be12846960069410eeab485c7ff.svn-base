package cn.gtmap.realestate.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/10/28
 * @description 短信模板及电话号码配置项解析
 */
@Component
@ConfigurationProperties(prefix = "msg")
public class SendMsgConfig {

    private Map<String, Map<String,String>> msgxxMap = new HashMap<>();

    public Map<String, Map<String, String>> getMsgxxMap() {
        return msgxxMap;
    }

    public void setMsgxxMap(Map<String, Map<String, String>> msgxxMap) {
        this.msgxxMap = msgxxMap;
    }
}
