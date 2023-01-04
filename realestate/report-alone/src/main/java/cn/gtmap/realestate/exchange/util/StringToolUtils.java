package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.exchange.core.domain.BdcQlrDO;
import cn.gtmap.realestate.exchange.core.ex.AppException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/14
 * @description 字符串操作工具类（为了避免重名，不命名为StringUtils）
 */
public class StringToolUtils extends StringUtils{
    /**
     * 日志操作
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StringToolUtils.class);
    /**
     * StringToolUtils限定类名
     */
    private static final String CLASS_NAME = StringToolUtils.class.getName();

    public static final String ENCODING_UTF8 = "UTF-8";
    public static final String ENCODING_GBK = "GBK";

    /**
     * 拼接实体字段（字段去重、字段值存在多个子项拼接则拆分成子项拼接）
     * @param   beanList 需要执行的bean集合
     * @param   methodName 执行方法名
     * @param   sign 字符串拼接分隔符
     * @return  {String} 拼接的字符串
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    public static <T> String joinBeanAttribute(List<T> beanList, String methodName, String sign) {
        if(CollectionUtils.isEmpty(beanList) || StringUtils.isBlank(methodName)){
            LOGGER.warn("拼接字符串终止，原因：未指定需执行bean、方法名");
            return "";
        }

        // 如果未指定分隔符号，默认逗号
        sign = StringUtils.isEmpty(sign) ? "," : sign;
        try{
            Set<String> set = new LinkedHashSet<>();
            for (T bean : beanList){
                if(null == bean) continue;

                Method method = bean.getClass().getMethod(methodName);
                Object obj = method.invoke(bean);
                if(obj == null)  continue;

                set.addAll(Arrays.asList(String.valueOf(obj).split(",")));
            }
            return StringUtils.join(set, sign);
        } catch (Exception e) {
            LOGGER.error("解析bean拼接字符串异常, {}", e.getMessage());
            return "";
        }
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   beanList 需要执行的bean集合
     * @param   methodName 执行方法名
     * @param   sign 字符串拼接分隔符
     * @return  {String} 拼接的字符串
     * @description   执行bean方法，拼接结果字符串
     */
    public static <T> String resolveBeanToAppendStr(List<T> beanList, String methodName, String sign) {
        if(CollectionUtils.isEmpty(beanList) || StringUtils.isBlank(methodName)){
            LOGGER.warn("{}：拼接字符串终止，原因：未指定需执行bean、方法名", CLASS_NAME);
            return null;
        }

        // 如果未指定分隔符号，默认逗号
        if(StringUtils.isEmpty(sign)){
            sign = ",";
        }

        StringBuilder builder = new StringBuilder(100);
        try{
            for (T bean : beanList){
                if(null != bean){
                    // 获取目标方法
                    Method method = bean.getClass().getMethod(methodName);
                    // 执行bean目标方法，拼接结果字符串
                    Object obj = method.invoke(bean);
                    if(obj==null){
                        continue;
                    }
                    builder.append(String.valueOf(obj)).append(sign);
                }
            }
        } catch (NoSuchMethodException e) {
            LOGGER.error("{}：解析bean拼接字符串失败，原因：目标bean没有指定方法{}", CLASS_NAME, methodName);
            LOGGER.error(e.getMessage(), e);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("{}：解析bean拼接字符串失败，原因：目标bean执行失败", CLASS_NAME);
            LOGGER.error(e.getMessage(), e);
        }

        String str = builder.toString();
        if(StringUtils.isBlank(str)){
            //LOGGER.warn("{}：解析bean拼接字符串结果为空！", CLASS_NAME);
            return null;
        }

        //返回拼接字符串，需要去掉最后多余的分隔符号
        return str.substring(0, str.length() - sign.length());
    }

    /**
     * @param beanList    需要执行的bean集合
     * @param methodNames 执行方法名
     * @param sign        字符串拼接分隔符
     * @return {String} 拼接的字符串
     * @author chenchunxue
     * @description 执行多个bean方法，拼接结果字符串
     */
    public static <T> String resolveBeanToAppendStr(List<T> beanList, String[] methodNames, String sign,String methodSign) {
        if(CollectionUtils.isEmpty(beanList) || methodNames == null){
            LOGGER.warn("{}：拼接字符串终止，原因：未指定需执行bean、方法名", CLASS_NAME);
            return null;
        }
        // 如果未指定分隔符号，默认逗号
        if(StringUtils.isEmpty(sign)){
            sign = ",";
        }
        if(StringUtils.isEmpty(methodSign)){
            methodSign = ",";
        }
        StringBuilder builder = new StringBuilder(300);
        try{
            for (T bean : beanList){
                if(null != bean){
                    // 循环方法
                    for(String methodName:methodNames){
                        // 获取目标方法
                        Method method = bean.getClass().getMethod(methodName);
                        // 执行bean目标方法，拼接结果字符串
                        Object obj = method.invoke(bean);
                        if(obj==null){
                            continue;
                        }
                        // 增加方法执行结果的标记
                        builder.append(String.valueOf(obj)).append(methodSign);
                    }
                }
                // 第一条数据执行完 去掉最后一个标记
                builder.substring(0, builder.length() - methodSign.length());
                // 增加数据之间的标记
                builder.append(sign);
            }
        } catch (NoSuchMethodException e) {
            LOGGER.error("{}：解析bean拼接字符串失败，原因：目标bean没有指定方法{}", CLASS_NAME, methodNames);
            LOGGER.error(e.getMessage(), e);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("{}：解析bean拼接字符串失败，原因：目标bean执行失败", CLASS_NAME);
            LOGGER.error(e.getMessage(), e);
        }
        String str = builder.toString();
        if(StringUtils.isBlank(str)){
            LOGGER.debug("解析bean拼接字符串结果为空！");
            return null;
        }
        //返回拼接字符串，需要去掉最后多余的分隔符号
        return str.substring(0, str.length() - methodSign.length());
    }
    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   bdcQlrList 权利人或者义务人集合
     * @param   zjzlZd 证件种类字典
     * @return  {String} 证件种类名称
     * @description  获取权利人/义务人拼接的证件种类名称（之所以传字典是方便UI应用使用，因为UI不能直接使用字典操作）
     */
    public static String getZjzlOfZd(List<BdcQlrDO> bdcQlrList, List<Map> zjzlZd) {
        return StringToolUtils.convertBeanPropertiesValueOfZd(bdcQlrList, "zjzl", zjzlZd);
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   beansList 实体对象集合
     * @param   propertyName 要转换字典的属性名称
     * @param   zdList 字典集合
     * @return  {String} 属性转换字典后的名称
     * @description  获取多个实体对象属性对应的字典名称，且以逗号拼接名称值
     */
    public static <T> String convertBeanPropertiesValueOfZd(List<T> beansList, String propertyName, List<Map> zdList){
        if(CollectionUtils.isEmpty(beansList) || StringUtils.isBlank(propertyName) || CollectionUtils.isEmpty(zdList)){
            LOGGER.warn("{}：参数为空，转换实体属性字典中止！", CLASS_NAME);
            return null;
        }

        StringBuilder builder = new StringBuilder(100);
        try{
            for(T bean : beansList){
                if(null != bean){
                    Field field = bean.getClass().getDeclaredField(propertyName);
                    field.setAccessible(true);

                    for(Map map : zdList){
                        if (null != field.get(bean) && MapUtils.getInteger(map, "DM")!=null &&  Integer.parseInt(String.valueOf(field.get(bean))) == MapUtils.getInteger(map, "DM").intValue()) {
                            builder.append(MapUtils.getString(map, "MC")).append(",");
                        }
                    }
                }
            }
            String value = builder.toString();
            if (StringUtils.isBlank(value)) {
                return value;
            }
            return value.substring(0, value.length() - 1);
        } catch (NoSuchFieldException | IllegalAccessException e){
            LOGGER.error(e.getMessage(), e);
            throw new AppException("转换实体属性字典出错！");
        }
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   propertyValue 要转换字典的属性值
     * @param   zdList 字典集合
     * @return  {String} 属性转换字典后的名称
     * @description  获取实体对象属性对应的字典名称
     */
    public static String convertBeanPropertyValueOfZd(Integer propertyValue, List<Map> zdList){
        if(null == propertyValue || CollectionUtils.isEmpty(zdList)){
            return null;
        }

        for (Map map : zdList) {
            if (propertyValue.intValue() == MapUtils.getInteger(map, "DM").intValue()) {
                return MapUtils.getString(map, "MC");
            }
        }
        return propertyValue.toString();
    }

    public static String convertBeanPropertyValueOfZdByString(String propertyValue, List<Map> zdList) {
        if (null == propertyValue || CollectionUtils.isEmpty(zdList)) {
            return null;
        }

        for (Map map : zdList) {
            if (propertyValue.toString().equals(MapUtils.getString(map, "DM").toString())) {
                return MapUtils.getString(map, "MC");
            }
        }
        return propertyValue;
    }

    /**
      * @param propertyValue 转换前的值
      * @return 转换后的第三方字典值或不动产字典值
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 第三方字典值与不动产字典值相互转换
      */
    public static String convertBeanPropertyValueOfDsfZd(String propertyValue,List<Map> dsfzdList,String propertyName){
        if(StringUtils.isBlank(propertyValue) || CollectionUtils.isEmpty(dsfzdList)){
            return null;
        }

        for (Map map : dsfzdList) {
            if (StringUtils.equals(propertyName,"dsfzdz") &&StringUtils.equals(propertyValue,MapUtils.getString(map, "DSFZDZ"))) {
                return MapUtils.getString(map, "BDCZDZ");
            }
            if (StringUtils.equals(propertyName,"bdczdz") &&StringUtils.equals(propertyValue,MapUtils.getString(map, "DSFZDZ"))) {
                return MapUtils.getString(map, "BDCZDZ");
            }
        }
        return propertyValue;
    }

    /**
     * @param str          源字符串
     * @param targetStrSet 目标字符串集合
     * @return {Boolean} 包含：true，不包含：false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 判断字符串是否包含指定内容（包含一个即true）
     */
    public static Boolean containsTargetStr(String str, Set<String> targetStrSet) {
        if (StringUtils.isBlank(str) || CollectionUtils.isEmpty(targetStrSet)) {
            return false;
        }

        for(String targerStr : targetStrSet){
            if(str.contains(targerStr)){
                return true;
            }
        }
        return false;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   str 源字符串
     * @param   targetStrCollection 目标字符串集合
     * @return  {Boolean} 包含：true，不包含：false
     * @description  判断字符串是否包含指定内容（包含一个即true）
     */
    public static Boolean containsTargetStr(String str, String... targetStrCollection){
        if(StringUtils.isBlank(str) || ArrayUtils.isEmpty(targetStrCollection)){
            return false;
        }

        for(String targerStr : targetStrCollection){
            if(str.contains(targerStr)){
                return true;
            }
        }
        return false;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   strArray 字符串集合
     * @return  {Boolean} 存在空：true，都不为空：false
     * @description 判断指定字符串集合是否存在空字符串
     */
    public static boolean existItemNullOrEmpty(String... strArray){
        if(ArrayUtils.isEmpty(strArray)){
            return true;
        }

        for(String str : strArray){
            if(StringUtils.isEmpty(str)){
                return true;
            }
        }
        return false;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   integerArray Integer集合
     * @return  {Boolean} 存在空：true，都不为空：false
     * @description  判断指定Integer集合是否存在空
     */
    public static boolean existIntegerItemNullOrEmpty(Integer... integerArray){
        if(ArrayUtils.isEmpty(integerArray)){
            return true;
        }

        for(Integer item : integerArray){
            if(null == item){
                return true;
            }
        }
        return false;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   str 源字符串
     * @param   strArray 字符串集合
     * @return  {Boolean} 存在至少一个相等：true ，都不相等：false
     * @description  判断字符串是否和目标字符串数据中某个相等
     */
    public static boolean existItemEquals(String str, String... strArray){
        if(StringUtils.isBlank(str) || ArrayUtils.isEmpty(strArray)){
            return false;
        }

        for(String item : strArray){
           if(StringUtils.equals(str, item)){
               return true;
           }
        }
        return false;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   strArray 字符串集合
     * @return  {Boolean} 都为空：true ，有一个不为空：false
     * @description  判断字符串是否都为空
     */
    public static boolean isAllNullOrEmpty(String... strArray){
        if(ArrayUtils.isEmpty(strArray)){
            return true;
        }

        for(String str : strArray){
            if(StringUtils.isNotBlank(str)){
                return false;
            }
        }
        return true;
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 替换中文括号 为英文括号
     */
    public static String replaceBracket(String str){
        if(StringUtils.isBlank(str)){
            return str;
        }
        if(str.indexOf(CommonConstantUtils.BDCQ_BH_LEFT_BRACKET_CN)!=-1){
            str=str.replaceAll(CommonConstantUtils.BDCQ_BH_LEFT_BRACKET_CN,CommonConstantUtils.BDCQ_BH_LEFT_BRACKET);
        }
        if(str.indexOf(CommonConstantUtils.BDCQ_BH_RIGHT_BRACKET_CN)!=-1){
            str=str.replaceAll(CommonConstantUtils.BDCQ_BH_RIGHT_BRACKET_CN,CommonConstantUtils.BDCQ_BH_RIGHT_BRACKET);
        }
        return str;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   str 目标字符串
     * @param   len 新字符串总长度
     * @description  字符串前置补0达到指定长度
     */
    public static String appendZero(String str, int len){
        if(StringUtils.isBlank(str) || len < str.length()){
            return str;
        }

        if(str.length() < len){
            do{
                str = StringUtils.join("0", str);
            }while (str.length() < len);
        }
        return str;
    }

    private final static String[] Numbers = {"零","一","二","三","四","五","六","七","八","九"};
    private final static String[] TenString = {"十","二十","三十"};
    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">yaoyi</a>
     * @description  获取当前时间，格式  二零一九年八月二十一日
     */
    public static String getChineseDate(){
        LocalDateTime localDate = LocalDateTime.now();
        String[] year = String.valueOf(localDate.getYear()).split("");
        StringBuilder chinesDate = new StringBuilder();
        for(int i = 0; i<year.length;i++){
            chinesDate.append(convert(year[i]));
        }
        chinesDate.append("年");
        String month = String.valueOf(localDate.getMonthValue());
        chinesDate.append(convertMonthAndDay(month)).append("月");
        String day = String.valueOf(localDate.getDayOfMonth());
        chinesDate.append(convertMonthAndDay(day)).append("日");
        return chinesDate.toString();
    }

    private static String convertMonthAndDay(String value){
        String[] number = value.split("");
        if(number.length == 1) {
            return convert(number[0]);
        }
        if(number[0].equals("0")){
            return convert(number[1]);
        }
        String tenStr = TenString[Integer.valueOf(number[0])-1];
        if(number[1].equals("0")){
            return tenStr;
        }
        return tenStr + convert(number[1]);
    }

    private static String convert(String val){
        return Numbers[Integer.valueOf(val)];
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 替换 xml 中的值
     */
    public static String replaceXml(String key,Object value,String xml){
        xml=xml.replaceAll("\\>\\$"+StringUtils.upperCase(key)+"\\<", "\\>" + value + "\\<");
        xml=xml.replaceAll("\\>\\$"+StringUtils.lowerCase(key)+"\\<", "\\>" + value + "\\<");
        xml = xml.replaceAll("\\>\\$" + key + "\\<", "\\>" + value + "\\<");
        xml=xml.replaceAll("\\$\\{["+StringUtils.upperCase(key)+"}]+\\}",String.valueOf(value));
        xml=xml.replaceAll("\\$\\{["+StringUtils.lowerCase(key)+"}]+\\}",String.valueOf(value));
        xml = xml.replaceAll("\\$\\{[" + key + "}]+\\}", String.valueOf(value));
        return xml;
    }

    /**
     * @param stringList 字符串List
     * @return String 拼接好的sql字符串 “(xmid1,xmid2,...)”
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将字符串List拼接成sql中的in 查询条件
     */
    public static String appendSqlInStr(List<String> stringList) {
        String sqlStr = "";
        StringBuilder stringBuilder = new StringBuilder(sqlStr);
        if (CollectionUtils.isNotEmpty(stringList)) {
            stringBuilder.append("(");
            for (String str : stringList) {
                stringBuilder.append("'").append(str).append("',");
            }
            sqlStr = stringBuilder.toString();

            sqlStr = sqlStr.substring(0, sqlStr.length() - 1);
            sqlStr += ")";
        }
        return sqlStr;
    }

    /**
     * 将字符串List拼接成sql中的in 查询条件，不带括号
     * @param stringList 字符串List
     * @return String 拼接好的sql字符串 xmid1,xmid2,xmid3
     */
    public static String appendSqlInStrWithNoBracket(List<String> stringList) {
        if(CollectionUtils.isEmpty(stringList)) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String str : stringList) {
            stringBuilder.append("'").append(str).append("',");
        }
        String sqlStr = stringBuilder.toString();
        return sqlStr.substring(0, sqlStr.length() - 1);
    }

    /**
     * @param str
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 字符串转byte数组 编码UTF-8
     */
    public static byte[] strToByteUtf8(String str) {
        return strToByte(str, ENCODING_UTF8);
    }

    /**
     * @param str
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 字符串转byte数组
     */
    public static byte[] strToByte(String str, String encode) {
        byte[] b = null;
        if (StringUtils.isNotBlank(str)) {
            try {
                b = str.getBytes(encode);
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("StringUtils-strToByteUtf8 --》", e);
            }
        }
        return b;
    }

    /**
     * @param b
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description byte数组转字符串 编码UTF-8
     */
    public static String byteToStrUtf8(byte[] b) {
        return byteToStr(b, ENCODING_UTF8);
    }

    /**
     * @param b
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description byte数组转字符串
     */
    public static String byteToStr(byte[] b, String encode) {
        String str = null;
        if (null != b) {
            try {
                str = new String(b, encode);
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("StringUtils-byteToStrUtf8 --》", e);
            }
        }
        return str;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    /**
     * @author  <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @param   mapList 需要执行的map集合
     * @param   key key值
     * @param   sign 字符串拼接分隔符
     * @return  {String} 拼接的字符串
     * @description   拼接结果字符串
     */
    public static String resolveMapListToAppendStr(List<Map> mapList, String key, String sign) {
        if(CollectionUtils.isEmpty(mapList) || StringUtils.isBlank(key)){
            LOGGER.warn("{}：拼接字符串终止，原因：未指定需执行集合、key值", CLASS_NAME);
            return null;
        }

        // 如果未指定分隔符号，默认逗号
        if(StringUtils.isEmpty(sign)){
            sign = ",";
        }

        StringBuilder builder = new StringBuilder(100);
        try{
            for (Map map : mapList){
                if(null != map &&map.get(key) != null){
                    Object obj = map.get(key);
                    if(obj==null){
                        continue;
                    }
                    builder.append(String.valueOf(obj)).append(sign);
                }
            }
        }  catch (Exception e) {
            LOGGER.error("{}：解析map集合拼接字符串失败", CLASS_NAME);
            LOGGER.error(e.getMessage(), e);
        }

        String str = builder.toString();
        if(StringUtils.isBlank(str)){
            LOGGER.warn("{}：解析map集合拼接字符串结果为空！", CLASS_NAME);
            return null;
        }

        //返回拼接字符串，需要去掉最后多余的分隔符号
        return str.substring(0, str.length() - sign.length());
    }

    public static String valueOf(Object obj, String defaultVal) {
        return (obj == null) ? defaultVal : obj.toString();
    }

    /**
      * @param sfpjBjstr 是否拼接指定字符
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 截取字符串str中指定字符 strStart、strEnd之间的字符串
      */
    public static String subString(String str, String strStart, String strEnd,boolean sfpjBjstr) {
        if(StringUtils.isAnyBlank(str)){
            LOGGER.error("字符串为空");
            return "";
        }
        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            LOGGER.error("字符串：{}中不存在：{},无法截取目标字符串",str,strStart);
            return "";
        }
        if (strEndIndex < 0) {
            LOGGER.error("字符串：{}中不存在：{},无法截取目标字符串",str,strEnd);
            return "";
        }
        /* 开始截取 */
        if(sfpjBjstr) {
            return str.substring(strStartIndex, strEndIndex +strEnd.length());
        }else{
            return str.substring(strStartIndex, strEndIndex).substring(strStart.length());

        }
    }


    }
