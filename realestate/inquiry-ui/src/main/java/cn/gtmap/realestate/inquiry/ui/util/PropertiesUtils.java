package cn.gtmap.realestate.inquiry.ui.util;

import cn.gtmap.realestate.inquiry.ui.config.UrlConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author: <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version: V1.0, 2019/3/4
 * @description: .properties配置文件读取工具类
 */
@Component
public class PropertiesUtils {
    private static UrlConfig urlConfig;

    /**
     * @author: <a href="@mailto:hynkoala@163.com">hanyaning</a>
     * @description:  静态对象不能直接注入，此处对静态变量添加set方法，再标注Autowired,即可实现对该变量值的自动装载
     *     新的配置类（新添加.properties配置文件），均需要对配置类的对象通过此方法转化为静态变量，以便再静态上下文中使用
     */
    @Autowired
    public void setUrlConfig(UrlConfig urlConfig){
        PropertiesUtils.urlConfig = urlConfig;
    }

    /**
     *  读取urlConfig文件的对应的url配置
     * @date 2019/3/4
     * @author hanyi
     * @param urlName 文件对应对应的key值，如zsUrl
     * @return java.lang.String
     */
    public static String getUrl(String urlName) {
        String url = null;
            List<Map> urlList = urlConfig.getList();
            if (CollectionUtils.isNotEmpty(urlList)) {
                Iterator<Map> iterator = urlList.iterator();
                while (iterator.hasNext()) {
                    Map map = iterator.next();
                    if (map.containsKey(urlName) && StringUtils.isNotBlank(map.get(urlName).toString())) {
                        url = map.get(urlName).toString();
                        break;
                    }
                }
            }
        return url;
    }
}