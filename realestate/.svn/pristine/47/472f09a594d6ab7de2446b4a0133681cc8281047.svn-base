package cn.gtmap.realestate.inquiry.ui.config;

import org.apache.axis.utils.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "html")
public class HtmlVersionConfig {

    private String version;
    /**
     * 页面父版本
     */
    private String parversion;

    public String getVersion() {
        if (StringUtils.isEmpty(version)) {
            return "";
        }
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getParversion() {
        return parversion;
    }

    public void setParversion(String parversion) {
        this.parversion = parversion;
    }

    @Override
    public String toString() {
        return "HtmlVersionConfig{" +
                "version='" + version + '\'' +
                ", parversion='" + parversion + '\'' +
                '}';
    }
}
