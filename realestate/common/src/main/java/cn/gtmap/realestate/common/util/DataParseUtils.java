package cn.gtmap.realestate.common.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/3/10
 * @description  数据解析工具类
 */
public class DataParseUtils {
    /**
     * 正则表达式：数字
     */
    private static final String REGEX_NUMBER = "[0-9]+";
    /**
     * 布尔类型对应字符串：TRUE
     */
    private static final String BOOLEAN_CAP_TRUE = "TRUE";
    /**
     * 布尔类型对应字符串：FALSE
     */
    private static final String BOOLEAN_CAP_FALSE = "FALSE";
    /**
     * 布尔类型对应字符串：true
     */
    private static final String BOOLEAN_LOW_TRUE = "true";
    /**
     * 布尔类型对应字符串：false
     */
    private static final String BOOLEAN_LOW_FALSE = "false";


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param str 字符串
     * @return {Object} 实际数据对象
     * @description 判断字符串实际数据类型，返回对应数据类型对象
     */
    public static <T> Object parseType(String str) {
        if(StringUtils.isBlank(str)){
            return null;
        }

        // 数字类型
        if(str.matches(REGEX_NUMBER)){
            return Integer.parseInt(str);
        }

        // 布尔类型
        if(BOOLEAN_CAP_TRUE.equals(str) || BOOLEAN_CAP_FALSE.equals(str) ||
                BOOLEAN_LOW_TRUE.equals(str) || BOOLEAN_LOW_FALSE.equals(str)){
            return Boolean.parseBoolean(str);
        }

        // List<T>类型
        try{
            return new Gson().fromJson(str, new TypeToken<List<T>>() {}.getType());
        } catch (Exception e){
            // do nothing
        }

        // List<String>类型
        try{
            return new Gson().fromJson(str, new TypeToken<List<String>>() {}.getType());
        } catch (Exception e){
            // do nothing
        }

        // List<Integer>类型
        try{
            return new Gson().fromJson(str, new TypeToken<List<Integer>>() {}.getType());
        } catch (Exception e){
            // do nothing
        }

        // List<Map>类型 （对于接口返回实体对象的可以转成这个List<Map>类型）
        try{
            return new Gson().fromJson(str, new TypeToken<List<Map>>() {}.getType());
        } catch (Exception e){
            // do nothing
        }

        // Map类型
        try{
            return new Gson().fromJson(str, new TypeToken<Map>() {}.getType());
        } catch (Exception e){
            // do nothing
        }

        return str;
    }
}
