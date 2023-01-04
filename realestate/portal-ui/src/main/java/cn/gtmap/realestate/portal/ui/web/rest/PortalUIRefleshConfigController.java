package cn.gtmap.realestate.portal.ui.web.rest;

import cn.gtmap.realestate.common.config.ConfigUpdateEvent;
import cn.gtmap.realestate.common.core.domain.BdcTsywPzDO;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/11/09/15:57
 * @Description:
 */
@Controller
@RequestMapping("/rest/v1.0/refleshConfig")
public class PortalUIRefleshConfigController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortalUIRefleshConfigController.class);
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    ConfigurableEnvironment environment;

    @ResponseBody
    @GetMapping("")
    public String updateBdcTsywPzDOValue(@RequestParam (name = "bdcTsywPzDOStr") String bdcTsywPzDOStr) throws UnsupportedEncodingException {
        String decodeStr = URLDecoder.decode(bdcTsywPzDOStr, "UTF-8");
        BdcTsywPzDO bdcTsywPzDO = JSON.parseObject(decodeStr, BdcTsywPzDO.class);
        LOGGER.info("特殊台账配置刷新配置信息：{}",bdcTsywPzDO);

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
