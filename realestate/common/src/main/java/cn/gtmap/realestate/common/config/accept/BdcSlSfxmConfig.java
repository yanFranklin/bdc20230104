package cn.gtmap.realestate.common.config.accept;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 收费项目特殊配置
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-06-23 10:49
 **/
@Component
@ConfigurationProperties(prefix = "sfxx")
public class BdcSlSfxmConfig {
    private Map<String, List<String>> nosfxm;

    private Map<String, List<String>> tsdjyysf;

    public Map<String, List<String>> getNosfxm() {
        return nosfxm;
    }

    public void setNosfxm(Map<String, List<String>> nosfxm) {
        this.nosfxm = nosfxm;
    }

    public Map<String, List<String>> getTsdjyysf() {
        return tsdjyysf;
    }

    public void setTsdjyysf(Map<String, List<String>> tsdjyysf) {
        this.tsdjyysf = tsdjyysf;
    }

    @Override
    public String toString() {
        return "BdcSlSfxmConfig{" +
                "nosfxm=" + nosfxm +
                ", tsdjyysf=" + tsdjyysf +
                '}';
    }
}
