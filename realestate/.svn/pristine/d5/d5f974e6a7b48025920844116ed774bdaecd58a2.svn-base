package cn.gtmap.realestate.common.converter;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/03/24
 * @description 配置项String转换为List<Map>转换器
 */
@Component
@ConfigurationPropertiesBinding
public class StringToMapListConverter implements Converter<String, Map<String, List<String>>> {
    @Override
    public Map<String, List<String>> convert(String s) {
        if(StringUtils.isBlank(s)) {
            return null;
        }

        Map<String, String> map = JSON.parseObject(s, Map.class);
        Map<String, List<String>> result = new HashMap<>();
        for(Map.Entry<String, String> entry : map.entrySet()) {
            result.put(entry.getKey(), Arrays.asList(entry.getValue().split(",")));
        }
        return result;
    }
}
