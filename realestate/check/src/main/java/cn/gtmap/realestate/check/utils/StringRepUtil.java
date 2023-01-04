package cn.gtmap.realestate.check.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @author hqz
 * @version 1.0, 2016-12-1
 * @description 提示信息模板替换工具类
 */
public class StringRepUtil {

    /**
     * 占位符开始标识常量
     * @author hqz
     * @description 占位符开始标识常量,如${proid}
     */
    private static final String START_SIGN = "${";

    /**
     * 占位符结束标识常量
     * @author hqz
     * @description 占位符结束标识常量，如${proid}
     */
    private static final String END_SIGN = "}";

    /**
     * 通过传入的map对象替换提示信息的模板，生成新的字符串
     * @author hqz
     * @param tsxx 提示信息
     * @param parMap map对象
     * @return 替换模板后的字符串
     */
    public  static String replaceTsxxFromMap(String tsxx, Map<String, String> parMap) {
        if (null != parMap && StringUtils.isNotBlank(tsxx)) {
            StringBuilder buf = new StringBuilder(tsxx);
            int startIndex = buf.indexOf(START_SIGN);
            String parVal;
            while (startIndex != -1) {
                int endIndex = buf.indexOf(END_SIGN, startIndex);
                if (endIndex != -1) {
                    String replaceParm = buf.substring(startIndex + START_SIGN.length(), endIndex).toUpperCase();
                    int nextIndex = endIndex + END_SIGN.length();
                    if (parMap.containsKey(replaceParm)) {
                        parVal = parMap.get(replaceParm);
                        if (StringUtils.isBlank(parVal)) {
                            parVal= StringUtils.EMPTY;
                        }
                        buf.replace(startIndex, endIndex + END_SIGN.length(), parVal);
                        nextIndex = startIndex + parVal.length();
                        startIndex = buf.indexOf(START_SIGN, nextIndex);
                    } else {
                        startIndex = -1;
                    }
                } else {
                    startIndex = -1;
                }
            }
            return buf.toString();
        } else {
            return tsxx;
        }
    }


    public static String showLocalDate(){
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = dateformat.format(System.currentTimeMillis());
        return dateStr;
    }
}
