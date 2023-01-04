package cn.gtmap.realestate.exchange.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">caolu</a>
 * @version 1.0, 2022/5/9
 * @description 非税配置
 */
@Component
@ConfigurationProperties(prefix = "fs")
public class FsConfig {
    // 财政电子票据 项目编码
    private Map<String, Map<String,String>> itemcode;

    public Map<String, Map<String, String>> getItemcode() {
        return itemcode;
    }

    public void setItemcode(Map<String, Map<String, String>> itemcode) {
        this.itemcode = itemcode;
    }
}
