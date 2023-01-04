package cn.gtmap.realestate.register.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: realestate
 * @description: 属地审核区县代码与组织对照
 * @author: <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @create: 2021-06-18 09:57
 **/
@Component
@ConfigurationProperties(prefix = "sdsh")
public class QxdmXzdmConfig {

    private Map<String, String> qxdmMap;

    public Map<String, String> getQxdmMap() {
        return qxdmMap;
    }

    public void setQxdmMap(Map<String, String> qxdmMap) {
        this.qxdmMap = qxdmMap;
    }
}
