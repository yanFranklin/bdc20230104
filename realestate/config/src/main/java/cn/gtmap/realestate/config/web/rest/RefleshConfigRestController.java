package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.config.ConfigUpdateEvent;
import cn.gtmap.realestate.common.core.domain.BdcTsywPzDO;
import cn.gtmap.realestate.common.core.service.rest.refleshconfig.ConfigAppRefleshConfigRestService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/11/08/18:45
 * @Description:
 */
@RestController
public class RefleshConfigRestController implements ConfigAppRefleshConfigRestService {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    ConfigurableEnvironment environment;
    @Override
    public String refleshConfigValue(@RequestParam (name = "bdcTsywPzDOStr") String bdcTsywPzDOStr) throws UnsupportedEncodingException {
        String decodeStr = URLDecoder.decode(bdcTsywPzDOStr, "UTF-8");
        BdcTsywPzDO bdcTsywPzDO = JSON.parseObject(decodeStr, BdcTsywPzDO.class);
        if(bdcTsywPzDO != null) {
            MutablePropertySources propertySources = environment.getPropertySources();
            for (PropertySource<?> propertySource : propertySources) {
                String sourceName = propertySource.getName();
                Object obj = JSON.parse(JSON.toJSON(propertySource.getSource()).toString());
                Map<String, Object> source = (Map<String, Object>) obj;
                Map<String, Object> map = new HashMap<>(source.size());
                map.putAll(source);
                map.put(bdcTsywPzDO.getPzmc(), bdcTsywPzDO.getPzz());
                environment.getPropertySources().replace(sourceName, new MapPropertySource(sourceName, map));
                applicationContext.publishEvent(new ConfigUpdateEvent(this,bdcTsywPzDO.getPzmc()));
            }
        }
        return null;
    }


}
