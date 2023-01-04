package cn.gtmap.realestate.accept.config;


import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/05/22
 * @description
 *      配置项信息处理类
 *     1、对应配置文件application-config.yml中yjxx下配置项
 */
@Component
@ConfigurationProperties(prefix = "yjxx")
public class BdcYjxxConfig {

    /**
     * 默认邮编号
     */
    public final static String DEFAULT_YB = "000000";
    /**
     * 收件人邮编
     */
    private String sjryb;
    /**
     * 根据区县代码配置对应的寄件信息
     */
    private Map<String, String> jjrxxMap =  new HashMap<>();

    public Map getJjrxxByQxdm(String qxdm){
        if(StringUtils.isNotBlank(qxdm) && MapUtils.isNotEmpty(jjrxxMap)){
            for(Map.Entry<String, String> entry : jjrxxMap.entrySet()){
                if(null != entry && qxdm.equals(entry.getKey())){
                    String value = entry.getValue();
                    if(StringUtils.isNotBlank(value)){
                        return JSON.parseObject(value, Map.class);
                    }
                }
            }
        }
        return null;
    }

    public String getSjryb() {
        return sjryb;
    }

    public void setSjryb(String sjryb) {
        this.sjryb = sjryb;
    }

    public Map<String, String> getJjrxxMap() {
        return jjrxxMap;
    }

    public void setJjrxxMap(Map<String, String> jjrxxMap) {
        this.jjrxxMap = jjrxxMap;
    }
}
