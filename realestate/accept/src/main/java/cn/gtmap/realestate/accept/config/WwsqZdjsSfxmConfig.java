package cn.gtmap.realestate.accept.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 外网申请自动计算收费项目配置
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-11-25 10:47
 **/
@Component
@ConfigurationProperties(prefix = "wwsq.zdjssfxm")
public class WwsqZdjsSfxmConfig {
    private Map<String, List<Integer>> lcsplymap=new HashMap<>();

    public Map<String, List<Integer>> getLcsplymap() {
        return lcsplymap;
    }

    public void setLcsplymap(Map<String, List<Integer>> lcsplymap) {
        this.lcsplymap = lcsplymap;
    }

    public List<Integer> getLcsplymap(String gzldyid) {
        if(StringUtils.isNotBlank(gzldyid)) {
            return lcsplymap.get(gzldyid);
        }
        return Collections.emptyList();
    }
}
