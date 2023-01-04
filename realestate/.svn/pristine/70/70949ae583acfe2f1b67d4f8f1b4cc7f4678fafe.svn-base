package cn.gtmap.realestate.exchange.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">caolu</a>
 * @version 1.0, 2022/5/17
 * @description 合肥ca签章配置项
 */
@Component
@ConfigurationProperties(prefix = "caqz")
public class CaqzConfig {
    /**
     * 安徽ca_pdf文档签章接口密钥
     */
    private Map<String,String> secretkey;
    /**
     * 安徽ca_pdf文档签章接口唯一标识
     */
    private Map<String,String> uniqueid;
    /**
     * 安徽ca_pdf文档签章接口请求url
     */
    private Map<String,String> fwdpdfqzurl;

    public Map<String, String> getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(Map<String, String> secretkey) {
        this.secretkey = secretkey;
    }

    public Map<String, String> getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(Map<String, String> uniqueid) {
        this.uniqueid = uniqueid;
    }

    public Map<String, String> getFwdpdfqzurl() {
        return fwdpdfqzurl;
    }

    public void setFwdpdfqzurl(Map<String, String> fwdpdfqzurl) {
        this.fwdpdfqzurl = fwdpdfqzurl;
    }
}
