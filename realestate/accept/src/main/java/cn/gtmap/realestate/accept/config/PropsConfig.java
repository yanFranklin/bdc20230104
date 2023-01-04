package cn.gtmap.realestate.accept.config;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/12/12,1.0
 * @description
 */
@Component
@ConfigurationProperties(prefix = "accept")
public class PropsConfig {

    /**
     * 配置文件中yxbdcdyKgs项对应值集合，application-config.yml中同名配置项会设值到该集合
     */
    private Map<String, String> yxbdcdyKgs = new HashMap<>();


    public Map<String, String> getYxbdcdyKgs() {
        return yxbdcdyKgs;
    }

    public void setYxbdcdyKgs(Map<String, String> yxbdcdyKgs) {
        this.yxbdcdyKgs = yxbdcdyKgs;
    }

    /**
     * @param key 配置字段
     * @return 是否开启
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取配置的已选不动产单元开关
     */
    public Boolean getYxbdcdyKgOfMapItem(Object key) {
        if (MapUtils.isEmpty(this.getYxbdcdyKgs()) || StringUtils.isBlank(String.valueOf(key))) {
            return Boolean.FALSE;
        }
        return MapUtils.getBooleanValue(this.getYxbdcdyKgs(), key);
    }
}
