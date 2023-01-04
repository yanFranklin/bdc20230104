package cn.gtmap.realestate.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2021/4/21 18:31
 * @description
 */
public class MbConvertUtils {

    /**
     * 去除未赋值的部分
     *
     * @param mb 模板
     * @return
     */
    public static String mbUnsetRowReplace(String mb) {
        // 添加换行符
        int i = 0;
        String fgf = "\n";
        while (mb.indexOf("#{") > -1) {
            int end = mb.indexOf("#{");
            //后边换行符的位置
            int endHh = mb.indexOf(fgf, end);
            if (end > -1) {
                int begin = mb.substring(0, end).lastIndexOf(fgf);
                if (begin < 0) {
                    begin = 0;
                    endHh += 1;
                }
                if (endHh <= 0) {
                    mb = mb.substring(0, begin);
                } else {
                    mb = mb.substring(0, begin) + mb.substring(endHh);
                }
            }
            i++;
            if (i > 100) {
                break;
            }
        }
        return mb;
    }

    /**
     * 通过map对模板中的值进行替换
     * @param mb  模板
     * @param map 替换模板参数map
     * @return
     */
    public static String mbParamReplace(String mb, Map map) {
        // 获取参数
        if (map != null && StringUtils.isNotBlank(mb)) {
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                String key = object.toString();
                if (StringUtils.isNotBlank(key) && map.get(key) != null) {
                    mb = mb.replaceFirst("(?i)#\\{" + key + "\\}", map.get(key).toString());
                }
            }
        }
        return mb;
    }

}
