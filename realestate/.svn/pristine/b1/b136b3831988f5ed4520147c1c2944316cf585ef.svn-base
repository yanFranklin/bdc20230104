package cn.gtmap.realestate.accept.ui.config;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 受理页面权限设置配置信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-06-02 11:21
 **/
@Component
@ConfigurationProperties(prefix = "slym.authority")
public class SlymAuthorityConfig {

    @ApiModelProperty()
    private Map<String, Map<String, Map<String, List<String>>>> idMap;


    public Map<String, Map<String, Map<String, List<String>>>> getIdMap() {
        return idMap;
    }

    public void setIdMap(Map<String, Map<String, Map<String, List<String>>>> idMap) {
        this.idMap = idMap;
    }
}
