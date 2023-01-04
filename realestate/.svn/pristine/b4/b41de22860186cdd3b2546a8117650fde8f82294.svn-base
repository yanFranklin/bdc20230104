package cn.gtmap.realestate.register.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: realestate
 * @description: 审核信息页面打印按钮展示
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-05-31 14:36
 **/
@Component
@ConfigurationProperties("shxx.print")
public class ShxxZhlcPrintDTO {

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
