package cn.gtmap.realestate.accept.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: realestate
 * @description: #查询企业或事业单位的法定代表人的接口benid key值为qlrlx 2:企业 3:事业单位
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2021-04-8 11:28
 **/
@Component
@ConfigurationProperties(prefix = "gxcxinterface.qlrlx")
public class QyxxInterfaceConfig {
    private Map<String, String> beanid;

    private String qlrlb;

    public Map<String, String> getBeanid() {
        return beanid;
    }

    public void setBeanid(Map<String, String> beanid) {
        this.beanid = beanid;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }
}
