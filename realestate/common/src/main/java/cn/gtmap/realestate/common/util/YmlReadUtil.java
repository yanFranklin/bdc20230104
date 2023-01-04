package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.domain.BdcTsywPzDO;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/3/16.
 * @description 读取YMl配置工具类
 */
public class YmlReadUtil {
    /**
     * 正则表达式:用于匹配 ***.yml文件
     */
    public static final String YML_REGEX = "^.+.yml$";
    /**
     * springboot配置文件模板名
     */
    public static final String YML_NAME = "application-%s.yml";
    /**
     * springboot当前生效的配置key
     */
    public static final String ACTIVE_YML_KEY = "spring.profiles.active";

    public static final String INCLUDE_YML_KEY = "spring.profiles.include";

    public static final String ROOT_YML_NAME = "bootstrap.yml";

    private static final Logger LOGGER = LoggerFactory.getLogger(YmlReadUtil.class);

    /**
     * 读取所有Yml配置数据，返回Map数据结构
     * <p>先读取<code>bootstrap.yml</code>文件，根据配置active名称获取
     * <code>application-**(active).yml</code>配置文件。
     * 在通过active里面配置的include信息，获取对应的配置文件内容。
     */
    public static Map readAllYmlForMap(String location) throws IOException {
        // 加载bootstrap.yml配置文件
        Map map = readYmlByMap(location, ROOT_YML_NAME);
        if(Objects.nonNull(map)){
            // 获取当前应用生效的Yml配置
            final String activeYmlKey= (String) getValue(map, ACTIVE_YML_KEY);
            if(StringUtils.isNotBlank(activeYmlKey)){
                map.putAll(Optional.ofNullable(readYmlByMap(location, String.format(YML_NAME, activeYmlKey)))
                        .orElse(new HashMap()));
                // 获取spring.profiles.include中配置Yml内容
                final String includeYmlKey = (String) getValue(map, INCLUDE_YML_KEY);
                if(StringUtils.isNotBlank(includeYmlKey)){
                    String[] inculdeYmlKeys = includeYmlKey.split(",");
                    if(Objects.nonNull(inculdeYmlKeys) && inculdeYmlKeys.length > 0){
                        for(String key : inculdeYmlKeys){
                            if(StringUtils.isNotBlank(key)){
                                map.putAll(Optional.ofNullable(readYmlByMap(location, String.format(YML_NAME, key)))
                                        .orElse(new HashMap()));
                            }
                        }
                    }
                }
            }
        }
        return map;
    }

    // 递归调用通过key获取对应的值
    private static Object getValue(final Map map, final String key){
        if(Objects.isNull(map)){
            return null;
        }
        String[] keys = key.split("\\.", 2);
        if(keys.length == 2){
            return getValue((Map) map.get(keys[0]), keys[1]);
        }else{
            return map.get(keys[0]);
        }
    }

    /**
     * 读取对应<code>@param filename</code> Yml文件，并返回key-value格式的Map
     * 例子： 要获取spring.active.profiles
     *       Map key: spring value:(Map) a ; a key:active value:(Map) b; b key:profiles value:test
     */
    public static Map readYmlByMap(String location, String fileName){
        String path = location + fileName;
        LOGGER.info("获取文件：{}", path);
        try{
            YamlMapFactoryBean yamlMapFactoryBean = new YamlMapFactoryBean();
            yamlMapFactoryBean.setResources(new ClassPathResource(path));
            yamlMapFactoryBean.afterPropertiesSet();
            return yamlMapFactoryBean.getObject();
        }catch(Exception e){
            LOGGER.error("read {} file for Map has exception: {}", path, e.getMessage(), e);
        }
        return null;
    }

    /**
     * 通过key获取对应配置的值
     * 例：key为 spring.data.url，需要split key后获取到url后在通过Map.get("url")获取。
     * <p> 通过key获取到的配置值为Map时，将Map对象转为为JSONString对象
     *  其余其余转为String对象进行返回
     */
    public static String getMapValWithKeySplit(final Map map, final String key){
        Object val = getValue(map,key);
        if(Objects.isNull(val)){
            return null;
        }
        if(val instanceof Map){
            return JSON.toJSONString(val);
        }else{
            return String.valueOf(val);
        }
    }
    /**
     * 通过key获取对应配置的值
     * 例：key为spring.data.url,通过Map.get("spring.data.url")获取。
     * <p> 通过key获取到的配置值为Map时，将Map对象转为为JSONString对象
     *  其余其余转为String对象进行返回
     */
    public static String getMapVal(final Map map, final String key){
        Object val = map.get(key);
        if(Objects.isNull(val)){
            return null;
        }
        if(val instanceof Map){
            return JSON.toJSONString(val);
        }else{
            return String.valueOf(val);
        }
    }

    // 处理数组格式的数据，数据为 a.b.c  1 配置所需为 a.b  c:1
    public static String handleArrayData(Map<String,String> importConfigMap, BdcTsywPzDO bdcTsywPzDO){
        Map<String,String> arrayDataMap = new HashMap<>();
        String pzmc = bdcTsywPzDO.getPzmc();
        for (Map.Entry<String, String> entry : importConfigMap.entrySet()) {
            String entryKey = entry.getKey();
            if(entryKey.contains(pzmc) &&entryKey.length() >= (pzmc.length()+1)){
                arrayDataMap.put(entryKey.substring(pzmc.length()+1), entry.getValue());
            }
        }
        if(MapUtils.isNotEmpty(arrayDataMap)){
            return JSON.toJSONString(arrayDataMap);
        }
        return null;
    }

}
