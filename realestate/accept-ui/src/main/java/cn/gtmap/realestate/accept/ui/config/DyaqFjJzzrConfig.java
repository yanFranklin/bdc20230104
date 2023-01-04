package cn.gtmap.realestate.accept.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: realestate
 * @description: 抵押权附记根据是否禁止转让带入附记内容
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-03-24 11:28
 **/
@Component
@ConfigurationProperties(prefix = "dyaq.fj")
public class DyaqFjJzzrConfig {
    private Map<String, String> sfjzzr;

    public Map<String, String> getSfjzzr() {
        return sfjzzr;
    }

    public void setSfjzzr(Map<String, String> sfjzzr) {
        this.sfjzzr = sfjzzr;
    }
}
