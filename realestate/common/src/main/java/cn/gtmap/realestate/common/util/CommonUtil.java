package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.ex.AppException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.assertj.core.util.Lists;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by trr on 2016/9/26.
 */
public class CommonUtil {
    public static String formatEmptyValue(Object object) {
        return object != null ? object.toString() : "";
    }

    public static String getCurrStrDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = sdf.format(new Date());
        return str;
    }


    /**
     * @author <a href="mailto:liangqing@gtmap.cn;">liangqing</a>
     * @version 1.0, 2017-06-08
     * @description 封装三目运算符转换公共方法
     */
    public static String ternaryOperator(Object object) {
        return object != null ? object.toString() : "";
    }

    /**
     * @author <a href="mailto:liangqing@gtmap.cn;">liangqing</a>
     * @version 1.0, 2017-06-08
     * @description 封装三目运算符转换公共方法
     */
    public static String ternaryOperatorNotBlank(Object object) {
        String str = ternaryOperator(object);
        return StringUtils.isNotBlank(str) ? str : "/";
    }

    /**
     * @author <a href="mailto:xiejianan@gtmap.cn;">xiejianan</a>
     * @version 1.0, 2017-06-08
     * @description 封装三目运算符转换公共方法, 添加默认值写入
     */
    public static String ternaryOperator(Object object, String defaultValue) {
        String str = object != null ? object.toString() : ternaryOperator(defaultValue);
        return StringUtils.isNotBlank(str) ? str : ternaryOperator(defaultValue);
    }

    /**
     * @author <a href="mailto:xiejianan@gtmap.cn;">xiejianan</a>
     * @version 1.0, 2017-06-08
     * @description 封装三目运算符转换公共方法，适用于获取非字符串数据
     */
    public static <T> T ternaryOperator(Object object, Object defaultValue) {
        return object != null ? (T) object : (T) defaultValue;
    }

    /**
     * @author <a href="mailto:liangqing@gtmap.cn;">liangqing</a>
     * @version 1.0, 2017-08-30
     * @description 判断某个字符串在某一字符串中出现多少次
     */
    public static int getDisplayTimes(String str, String s) {
        int count = str.length() - str.replace(s, "").length();
        return count;
    }

    /**
     * @author <a href="mailto:liangqing@gtmap.cn;">liangqing</a>
     * @version 1.0, 2017-08-30
     * @description 获取某个字符在某个
     */
    public static int getCharacterPosition(String string, String s, int psition) {
        //这里是获取"-"符号的位置
        Matcher slashMatcher = Pattern.compile(s).matcher(string);
        int mIdx = 0;
        while (slashMatcher.find()) {
            mIdx++;
            if (mIdx == psition) {
                break;
            }
        }
        return slashMatcher.start();
    }


    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    /**
     * @param a
     * @param b
     * @return boolean
     * @author <a href ="mailto:liangqing@gtmap.cn"></a>
     * @version 1.3
     * @date 10:00 2017/11/21
     * @description 验证某个字符串是否包含在数组中
     */
    public static boolean indexOfStrs(String[] a, String b) {
        boolean msg = false;
        if (a != null) {
            for (String temp : a) {
                if (StringUtils.equals(temp, b)) {
                    msg = true;
                    break;
                }
            }
        }
        return msg;
    }

    /**
     * @param str String
     * @return boolean
     * @author <a href ="mailto:xiejianan@gtmap.cn"></a>
     * @date: Created in 17:03 2017/12/6
     * description 严格检测字符串匹配true，大小写不限
     * @version 1.3
     */
    public static boolean equalsExcatTrueIgnoreCase(String str) {
        return StringUtils.equalsIgnoreCase(str, String.valueOf(Boolean.TRUE));
    }

    /**
     * @param str String
     * @return boolean
     * @author <a href ="mailto:xiejianan@gtmap.cn"></a>
     * @date: Created in 17:03 2017/12/6
     * description 严格检测字符串匹配false，大小写不限
     * @version 1.3
     */
    public static boolean equalsExcatFalseIgnoreCase(String str) {
        return StringUtils.equalsIgnoreCase(str, String.valueOf(Boolean.FALSE));
    }

    public static String removeDuplicateStr(String str, String splitStr) {
        if (StringUtils.isNotBlank(str)) {
            LinkedHashSet<String> set = new LinkedHashSet();
            String[] strs = str.split(splitStr);
            StringBuilder finalStr = new StringBuilder();
            for (int i = 0; i < strs.length; i++) {
                set.add(strs[i]);
            }
            for (Object strTemp : set.toArray()) {
                if (finalStr.length() > 0) {
                    finalStr.append(splitStr);
                }
                finalStr.append(strTemp.toString());
            }
            return finalStr.toString();
        } else {
            return ternaryOperator(str);
        }
    }

    /**
     * @param param 查询参数 exceptions 不需要删除空格的参数条件
     * @return
     * @author <a href ="mailto:xiejianan@gtmap.cn"></a>
     * @version 1.3
     * @date 16:19 2018/4/18 0018
     * @description 处理查询参数中的空参数
     */
    public static Map<String, Object> removeEmptyParam(Map<String, Object> param, String... exceptions) {
        Iterator it = param.entrySet().iterator();
        Map<String, Object> newParam = new HashMap<String, Object>();
        List<String> exceptionList = new ArrayList<String>();
        if (exceptions != null) {
            exceptionList = Arrays.asList(exceptions);
        }
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (StringUtils.isNotBlank(String.valueOf(entry.getValue())) && !exceptionList.contains(String.valueOf(entry.getKey()))) {
                newParam.put(String.valueOf(entry.getKey()), StringUtils.deleteWhitespace(String.valueOf(entry.getValue())));
            } else if (StringUtils.isNotBlank(String.valueOf(entry.getValue())) && exceptionList.contains(String.valueOf(entry.getKey()))) {
                newParam.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        return newParam;
    }

    /**
     * @param list      待分割的list
     * @param sizeLimit 每个分割后的列表的大小
     * @return 分割后的列表
     * @description 按列表大小将大列表分割为小列表
     */
    public static Map<String, List> seperateBigListSamllList(List list, int sizeLimit) {
        return seperateBigListSamllList(list, sizeLimit, "list");
    }

    /**
     * @param list      待分割的list
     * @param sizeLimit 每个分割后的列表的大小
     * @return 分割后的列表
     * @description 按列表大小将大列表分割为小列表
     */
    public static Map<String, List> seperateBigListSamllList(List list, int sizeLimit, String prefix) {
        Map<String, List> map = new LinkedHashMap<String, List>();
        if (CollectionUtils.isNotEmpty(list)) {
            // 获取列表长度
            int size = CollectionUtils.size(list);
            // 计算列表分割次数
            int times = size / sizeLimit + (size % sizeLimit > 0 ? 1 : 0);
            for (int i = 0; i < times; i++) {
                map.put(prefix + i, list.subList(i * sizeLimit, (i + 1) * sizeLimit < size ? (i + 1) * sizeLimit : size));
            }
        }
        return map;
    }

    /**
     * @Description: 获取URL中参数值
     * @Param:
     * @return:
     * @Author: ww
     * @Date: 2018/10/24
     */
    public static String getUrlParameters(String url, String para) {
        if (StringUtils.isBlank(url) || StringUtils.isBlank(para)) {
            return null;
        }
        String paraStr = url.contains("?") ? url.substring(url.indexOf("?") + 1) : "";
        String[] paraArr = StringUtils.split(paraStr, "&");
        if (paraArr.length > 0) {
            for (int i = 0; i < paraArr.length; i++) {
                String[] p = StringUtils.split(paraArr[i], "=");
                if (p.length > 0 && StringUtils.equals(p[0], para)) {
                    return p[1];
                }
            }
        }
        return null;
    }

    /**
     * 组织查询条件
     *
     * @param keyId
     * @param param
     * @return
     */
    public static String conbineQueryCondition(String keyId, Map<String, List> param) {
        StringBuilder sql = new StringBuilder("(");
        // 计数使用，用来判断是否需要拼接or
        int i = 0;
        // 拼接查询条件
        for (String key : param.keySet()) {
            if (i > 0) {
                sql.append(" or");
            }
            // 查询条件使用别名
            sql.append(keyId).append(" in (:").append(key).append(")");
            i++;
        }
        sql.append(")");
        return sql.toString();
    }

    /**
     * 将list 用key值分割为 key - list形式，简化后期的循环匹配过程，提高效率
     *
     * @param keyId
     * @param list
     * @return
     */
    public static Map<String, List<Map<String, Object>>> divideListToMap(String keyId, List<Map<String, Object>> list) {
        Map<String, List<Map<String, Object>>> map = new LinkedHashMap<String, List<Map<String, Object>>>();
        List<Map<String, Object>> tempList;
        String keyValue;
        if (CollectionUtils.isNotEmpty(list)) {
            for (Map<String, Object> row : list) {
                keyValue = CommonUtil.ternaryOperator(row.get(keyId));
                tempList = CommonUtil.ternaryOperator(map.get(keyValue), new ArrayList<Map<String, Object>>());
                tempList.add(row);
                map.put(keyValue, tempList);
            }
        }
        return map;
    }

    /***
     * 删除最后一个逗号
     * @param param
     * @return
     */
    public static void deleteLastComma(StringBuilder param) {
        if (param != null && param.indexOf(",") != -1) {
            param.deleteCharAt(param.lastIndexOf(","));
        }
    }

    /**
     * version 1.0
     *
     * @param source          源对象
     * @param dest            目标对象
     * @param sourceFiledName 要复制的源对象属性名，个数与目标对象属性一致且对应
     * @param destFieldName   要复制的目标对象属性名，个数与源对象属性一致且对应
     * @return
     * @description 复制对象属性 可为不同对象，
     * @date 2019/1/8
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    public static void copyFieldValue(Object source, Object dest, List<String> sourceFiledName, List<String> destFieldName) {
        if (source == null) {
            throw new AppException("复制属性出错：源对象为空");
        }
        if (dest == null) {
            throw new AppException("复制属性出错：目标对象为空");
        }
        if (CollectionUtils.isEmpty(sourceFiledName) || CollectionUtils.isEmpty(destFieldName)) {
            throw new AppException("复制属性出错：未指定复制属性");
        }
        if (sourceFiledName.size() != destFieldName.size()) {
            throw new AppException("复制属性出错：复制个数不匹配, 源对象属性个数:" + sourceFiledName.size() +
                    ", 目标对象属性个数:" + destFieldName.size());
        }
        Method getMethod = null;
        Method setMethod = null;
        String getMethodName = null;
        String setMethodName = null;
        Field sourceField = null;
        Field destField = null;
        Object value = null;
        for (int i = 0; i < sourceFiledName.size(); i++) {
            try {
                sourceField = source.getClass().getDeclaredField(sourceFiledName.get(i));
            } catch (NoSuchFieldException e) {
                throw new AppException("复制属性出错：源对象中无属性: " + destFieldName.get(i));
            }
            try {
                destField = dest.getClass().getDeclaredField(destFieldName.get(i));
            } catch (NoSuchFieldException e) {
                throw new AppException("复制属性出错：目标对象中无属性: " + destFieldName.get(i));
            }
            if (!sourceField.getType().equals(destField.getType())) {
                throw new AppException("复制属性出错：属性类型不同: " + sourceFiledName.get(i));
            }
            getMethodName = String.format("get%s%s", sourceFiledName.get(i).substring(0, 1).toUpperCase(), sourceFiledName.get(i).substring(1));
            setMethodName = String.format("set%s%s", destFieldName.get(i).substring(0, 1).toUpperCase(), destFieldName.get(i).substring(1));

            try {
                getMethod = source.getClass().getMethod(getMethodName);
            } catch (NoSuchMethodException e) {
                throw new AppException("复制属性出错：源对象无方法: " + getMethodName);
            }
            try {
                setMethod = dest.getClass().getMethod(setMethodName, new Class[]{destField.getType()});
            } catch (NoSuchMethodException e) {
                throw new AppException("复制属性出错：目标对象无方法: " + setMethodName);
            }
            try {
                value = getMethod.invoke(source);
                if (value != null) {
                    setMethod.invoke(dest, value);
                }
            } catch (IllegalAccessException e) {
                throw new AppException("复制属性出错：" + e);
            } catch (InvocationTargetException e) {
                throw new AppException("复制属性出错：" + e);
            }
        }
    }

    /**
     * version 1.0
     *
     * @param
     * @return
     * @description 按拼接的key值 将value分组放入Map中
     * @date 2019/2/28
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    public static Map<String, List<String>> splitKeyList(List<String> sourceList, String defaultKey, String separate) {
        Map<String, List<String>> result = Maps.newHashMap();
        String[] strings = null;
        String key = null;
        String value = null;
        if (CollectionUtils.isNotEmpty(sourceList)) {
            separate = StringUtils.isNotEmpty(separate) ? separate : ",";
            defaultKey = StringUtils.isNotEmpty(defaultKey) ? defaultKey : "normal";
            for (String str : sourceList) {
                strings = StringUtils.splitByWholeSeparator(str, separate);
                if (strings.length > 1) {
                    key = strings[0];
                    value = strings[1];
                } else {
                    key = defaultKey;
                    value = strings[0];
                }
                if (!result.containsKey(key)) {
                    result.put(key, Lists.newArrayList());
                }
                result.get(key).add(value);
            }
        }
        return result;
    }

    /**
     * version 1.0
     *
     * @param
     * @return
     * @description 判断字符串是否为JSON格式字符串
     * @date 2019/3/14
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    public static boolean isJSONObject(String text) {
        if (StringUtils.isBlank(text)) {
            return false;
        }
        try {
            JSONObject.parseObject(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * version 1.0
     *
     * @param
     * @return
     * @description 判断字符串是否为JSON数组格式字符串
     * @date 2019/3/14
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    public static boolean isJSONOArray(String text) {
        if (StringUtils.isBlank(text)) {
            return false;
        }
        try {
            JSONArray.parseArray(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //获取字符串中的数字部分
    public static int getNumberByStr(String str) {
        str = str.trim();  // 删除字符串头尾空格
        if (str.length() == 0) return 0;
        int flag = 1;  // 符号位标识
        int rev = 0;  // 数值（无符号）
        int edge = Integer.MAX_VALUE / 10;  // 判断数值是否超过范围的边界线，这样写可以节省时间
        if (str.charAt(0) == '-') {
            flag = -1;
            str = str.substring(1, str.length());  // 跳过符号位，可不写第二参数
        } else if (str.charAt(0) == '+') {
            str = str.substring(1, str.length());  // 跳过符号位，可不写第二参数
        } else if (!(str.charAt(0) >= '0' && str.charAt(0) <= '9')) {  // 如果开始非空字符不为符号或数字，则直接返回 0
            return 0;
        }
        for (char s : str.toCharArray()) {
            if (s >= '0' && s <= '9') {
                int n = s - '0';  // 计算字符代表值
                if (rev >= edge) {  // 超过边界情况较少，故该判断写于外侧
                    if (flag == 1) {
                        if (rev > edge || n > 7) return Integer.MAX_VALUE;
                    } else {
                        if (rev > edge || n > 8) return Integer.MIN_VALUE;
                    }
                }
                rev = rev * 10 + n;
            } else {
                break;
            }
        }
        return rev * flag;
    }

    /**
     * 判断传入的{@code object}是否为null, 为null返回{@code defaultObject}
     * 不为null返回{@code object}
     *
     * <p> 采用JDK8的Optional类解决对象判空时需要返回默认值的问题
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: obj         需要校验为null的对象
     * @param: defaultObj  为null时返回的默认对象
     * @return: T           引用对象
     */
    public static <T> T getOrElse(final T obj, final T defaultObj) {
        return Optional.ofNullable(obj).orElse(defaultObj);
    }

    /**
     * 通过传入的{@code condition}验证{@code obj}是否满足条件
     * 满足条件{@code condition}时，返回{@code obj}
     * 不满足条件{@code condition}时，返回{@code defaultObj}
     * <blockquote><pre>
     *     public Foo(String obj){
     *          String value  = getOrElse(obj, "default", StringUtils::isNotBlank)
     *     }
     * </pre></blockquote>
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: obj         需要校验为null的对象
     * @param: condition   判断条件
     * @param: defaultObj  不满足condition返回的默认对象
     * @return: T           引用对象
     */
    public static <T> T getOrElse(final T obj, final Predicate<T> condition, final T defaultObj) {
        return Optional.ofNullable(obj).filter(condition).orElse(defaultObj);
    }


    /**
     * @param
     * @return java.lang.String
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 两个interger想加，判断是否为空
     */
    public static Integer plusTwoInterger(Integer a, Integer b) {
        int aVal = (a == null ? NumberUtils.INTEGER_ZERO : a);
        int bVal = (b == null ? NumberUtils.INTEGER_ZERO : b);
        return aVal + bVal;
    }

    /**
     * 平方米转换成亩
     *
     * @param pfmValue
     * @return
     */
    public static Double convertPfmToM(Double pfmValue) {
        if (pfmValue != null) {
            pfmValue = pfmValue * 0.0015;
            BigDecimal bg = new BigDecimal(pfmValue).setScale(2, RoundingMode.DOWN);
            return bg.doubleValue();
        }
        return pfmValue;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 将request.getParameterMap()转换为普通的Map的工具方法
     */
    public static Map<String, String> convertMap(Map<String, String[]> requestParamMap) {
        Map<String, String> returnMap = new HashMap<>();
        if(MapUtils.isNotEmpty(requestParamMap)){
            returnMap = new HashMap<>(requestParamMap.size());
            for (Map.Entry<String, String[]> entry : requestParamMap.entrySet()) {
                String valueStr = (entry.getValue() == null) ? null : entry.getValue()[0];
                returnMap.put(entry.getKey(), valueStr);
            }
        }
        return returnMap;
    }

    /**
     * 捕获内置校验异常
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param errors
     * @return
     */
    public static String validMsg(Errors errors) {
        StringBuilder sBuilder = new StringBuilder();
        List<ObjectError> list = errors.getAllErrors();
        for (ObjectError error : list) {
            sBuilder.append(error.getDefaultMessage());
        }
        return sBuilder.toString();
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.lang.String
     * @description 获取当前请求的地址
     */
    public static String getCurrentRequestPath(){
        if(RequestContextHolder.getRequestAttributes() != null){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if(request != null){
                return request.getServletPath();
            }
        }
        return null;
    }

    /**
     * 截取4000长度的字符串
     * <p>对于长度大于4000的值，进行截取处理</p>
     * @param str 字符串内容
     * @return 截取之后的字符串
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    public static String subLongStr(String str){
        if(StringUtils.isNotBlank(str) && str.length() > 4000){
            return str.substring(0, 4000);
        }
        return str;
    }

}
