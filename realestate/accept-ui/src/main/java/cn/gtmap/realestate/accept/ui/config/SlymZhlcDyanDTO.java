package cn.gtmap.realestate.accept.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: realestate
 * @description: 受理页面组合流程分开打印按钮
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-05-31 08:42
 **/
@Component
@ConfigurationProperties("slym.print")
public class SlymZhlcDyanDTO {

    private Map<String, Map<String, Map<String, String>>> zhlcbtn;


    public Map<String, Map<String, Map<String, String>>> getZhlcbtn() {
        return zhlcbtn;
    }

    public void setZhlcbtn(Map<String, Map<String, Map<String, String>>> zhlcbtn) {
        this.zhlcbtn = zhlcbtn;
    }

    @Override
    public String toString() {
        return "SlymZhlcDyanDTO{" +
                "zhlcbtn=" + zhlcbtn +
                '}';
    }
}
