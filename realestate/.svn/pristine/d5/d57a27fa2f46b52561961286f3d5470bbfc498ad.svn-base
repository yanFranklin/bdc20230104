package cn.gtmap.realestate.common.property.acceptui;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/11/23
 * @description 合肥不同流程的不同djyy控制不同的字段是否必填
 */
@Component
@ConfigurationProperties(prefix = "djyyControlColConfig")
public class DjyyControlColConfig {

    private Map<String, String> colIdAndDjyyValMap = new HashMap<>();

    public Map<String, String> getColIdAndDjyyValMap() {
        return colIdAndDjyyValMap;
    }

    public void setColIdAndDjyyValMap(Map<String, String> colIdAndDjyyValMap) {
        this.colIdAndDjyyValMap = colIdAndDjyyValMap;
    }
}
