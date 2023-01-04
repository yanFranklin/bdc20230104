package cn.gtmap.realestate.accept.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a herf="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/5/21
 * @description 交易信息用途一对多映射bdc_zd_fwyt的字典
 */
@Component
@ConfigurationProperties(prefix = "jyxx")
public class JyxxFwytYddConfig {
    private Map<String, List<String>> fwytMap=new HashMap<>();

    public Map<String, List<String>> getFwytMap() {
        return fwytMap;
    }

    public void setFwytMap(Map<String, List<String>> fwytMap) {
        this.fwytMap = fwytMap;
    }
}
