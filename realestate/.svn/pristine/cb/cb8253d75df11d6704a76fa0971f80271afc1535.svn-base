package cn.gtmap.realestate.inquiry.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/02/09
 * @description 水电气附件推送材料名称对照
 */
@Component
@ConfigurationProperties(prefix = "sdq.fjts")
public class SdqFjtsFjmcConfig {

    /**
     * 材料名称对照
     */
    private Map<String, String> fjmcdz = new HashMap<>();

    public Map<String, String> getFjmcdz() {
        return fjmcdz;
    }

    public void setFjmcdz(Map<String, String> fjmcdz) {
        this.fjmcdz = fjmcdz;
    }
}

