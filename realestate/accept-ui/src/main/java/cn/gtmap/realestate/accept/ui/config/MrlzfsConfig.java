package cn.gtmap.realestate.accept.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.mail.search.SearchTerm;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
 * @version 1.0, 2022/7/6
 * @description 默认领证方式配置
 */
@Component
@ConfigurationProperties(prefix = "mrlzfs")
public class MrlzfsConfig {

    /**
     * 权利和对应的默认领证方式映射
     */
    private Map<String,  Set<String>> lzfsQldmMap = new HashMap<>();

    public Map<String, Set<String>> getLzfsQldmMap() {
        return lzfsQldmMap;
    }

    public void setLzfsQldmMap(Map<String, Set<String>> lzfsQldmMap) {
        this.lzfsQldmMap = lzfsQldmMap;
    }
}
