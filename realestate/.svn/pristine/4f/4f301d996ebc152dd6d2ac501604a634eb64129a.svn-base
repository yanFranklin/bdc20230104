package cn.gtmap.realestate.inquiry.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;

/**
 * @author: <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version: V1.0, 2019/3/4
 * @description:
 */
@Configuration
@ConfigurationProperties("url")
@PropertySource(value="classpath:conf/url.properties")
public class UrlConfig {
    // list中是一系列的map，后续可通过key值取值
    private List<Map> list;

    public List<Map> getList() {
        return list;
    }
    public void setList(List<Map> list) {
        this.list = list;
    }
}