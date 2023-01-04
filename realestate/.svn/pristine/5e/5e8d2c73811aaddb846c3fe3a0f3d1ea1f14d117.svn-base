package cn.gtmap.realestate.common.converter;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/03/24
 * @description 配置项String转换为Map转换器
 */
@Component
@ConfigurationPropertiesBinding
public class StringToMapConverter implements Converter<String, Map<String, String>> {
    @Override
    public Map<String, String> convert(String s) {
        if(StringUtils.isBlank(s)) {
            return null;
        }

        return JSON.parseObject(s, Map.class);
    }
}
