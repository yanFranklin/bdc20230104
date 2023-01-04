package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.exchange.core.domain.BdcQlrDO;
import cn.gtmap.realestate.exchange.core.support.spring.EnvironmentConfig;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 公共工具类
 */
public class CommonUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * @return 年月日
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据当天获取年月日格式字符串
     */
    public static String getCurrDay() {
        Date date = new Date(System.currentTimeMillis());
        DateFormat format = new SimpleDateFormat(Constants.DATE_NYR);
        return format.format(date);
    }

    /**
     * 获取值，排除空值
     *
     * @param object
     * @return
     */
    public static String formatEmptyValue(Object object) {
        if (object != null) {
            return object.toString();
        }
        return "";
    }

    /**
     * @param map
     * @param objectClass
     * @return T
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 使用JSON 将MAP结构转成 OBJECT
     */
    public static <T> T mapToObject(Map map, Class<T> objectClass) {
        T result = null;
        try {
            String jsonStr = JSONObject.toJSONString(map);
            if (StringUtils.isNotBlank(jsonStr)) {
                result = JSONObject.parseObject(jsonStr, objectClass);
            }
        } catch (Exception e) {
            LOGGER.error("map转Object异常,map:{}", map, e);
        }
        return result;
    }

    /**
     * @param object
     * @return java.util.Map
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 使用JSON 将Object 转成Map
     */
    public static Map objectToMap(Object object) {
        Map result = null;
        try {
            String jsonStr = JSONObject.toJSONString(object);
            if (StringUtils.isNotBlank(jsonStr)) {
                result = JSONObject.parseObject(jsonStr, Map.class);
            }
        } catch (Exception e) {
            LOGGER.debug("Object转map异常");
        }
        return result;
    }

    /**
     * @return java.util.Map
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 使用JSON 将String 转成Map
     */
    public static Map stringToMap(String json) {
        Map result = null;
        try {
            if (StringUtils.isNotBlank(json)) {
                result = JSONObject.parseObject(json, Map.class);
            }
        } catch (Exception e) {
            LOGGER.debug("String转map异常,json:{}", json);
        }
        return result;
    }

    /**
     * @param paramMap
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 将Map 转成 URL 后传递的参数结构
     */
    public static String mapToUrlParam(Map paramMap) {
        return appendMap(paramMap, "&");
    }

    /**
     * @param paramMap, split
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    public static String appendMap(Map paramMap, String split) {
        if (MapUtils.isNotEmpty(paramMap)) {
            StringBuilder paramSb = new StringBuilder("");
            Iterator<Map.Entry<String, Object>> entrySetIter = paramMap.entrySet().iterator();
            while (entrySetIter.hasNext()) {
                Map.Entry<String, Object> entry = entrySetIter.next();
                if (entry.getValue() != null) {
                    String key = entry.getKey();
                    String value = entry.getValue().toString();
                    paramSb.append(key + "=" + value + split);
                }
            }
            String param = paramSb.toString();
            if (param.endsWith(split)) {
                param = param.substring(0, param.length() - 1);
            }
            return param;
        }
        return "";
    }

    /**
     * @param paramMap
     * @return
     * @author <a href="mailto:shaoliyap@gtmap.cn">shaoliyap</a>
     * @description
     */
    public static Map lowerMapKey(Map paramMap) {
        if (MapUtils.isNotEmpty(paramMap)) {
            Map resultMap = new HashMap();
            Iterator<Map.Entry<String, Object>> entrySetIter = paramMap.entrySet().iterator();
            while (entrySetIter.hasNext()) {
                Map.Entry<String, Object> entry = entrySetIter.next();
                if (entry != null && StringUtils.isNotBlank(entry.getKey())) {
                    resultMap.put(StringUtils.lowerCase(entry.getKey()), entry.getValue());
                }
            }
            paramMap.clear();
            paramMap.putAll(resultMap);
        }
        return paramMap;
    }

    /**
     * @param qxdm
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 判断当前区县代码  是否属于合肥房产上报配置
     */
    public static boolean isHefeiFcAccessQxdm(String qxdm) {
        if (Constants.HEFEI_QXDM_ARR.length > 0
                && ArrayUtils.contains(Constants.HEFEI_QXDM_ARR, qxdm)) {
            return true;
        }
        return false;
    }

    /**
     * @param qlrList
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 循环List拼接权利人名称
     */
    public static String wmQlrMcWithList(List<BdcQlrDO> qlrList, String qlrlb) {
        StringBuilder qlrSb = new StringBuilder("");
        if (CollectionUtils.isNotEmpty(qlrList)) {
            for (BdcQlrDO qlr : qlrList) {
                if (StringUtils.isNotBlank(qlrlb)
                        && StringUtils.equals(qlrlb, qlr.getQlrlb())) {
                    qlrSb.append(qlr.getQlrmc()).append(",");
                }
                if (StringUtils.isBlank(qlrlb)) {
                    qlrSb.append(qlr.getQlrmc()).append(",");
                }
            }
        }
        String qlr = qlrSb.toString();
        if (qlr.endsWith(",")) {
            qlr = qlr.substring(0, qlr.length() - 1);
        }
        return qlr;
    }

    /**
     * @param objList    实体列表
     * @param methodName 方法名
     * @param split      分割符
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    public static String wmString(List objList, String methodName, String split) {
        StringBuilder sb = new StringBuilder("");
        try {
            if (CollectionUtils.isNotEmpty(objList)) {
                for (Object obj : objList) {
                    Method method = obj.getClass().getMethod(methodName);
                    Object value = method.invoke(obj);
                    if (value != null && value instanceof String) {
                        sb.append(value).append(split);
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        String result = sb.toString();
        if (StringUtils.isNotBlank(result)) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }


    /**
     * @param inputStream
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description inputStream 转String
     */
    public static String inputStreamToString(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * @param
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 综合查询平台 随机数格式
     */
    public static String openApiUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * @param gybl
     * @return 共有比例的小数格式
     * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
     * @description 提供一个工具类  将共有比例转换为小数格式
     */
    public static String changeQlblToFloatStr(String gybl) {
        String qlbl = "";
        String result = "";
        if (StringUtils.isNotBlank(StringUtils.deleteWhitespace(gybl))) {
            qlbl = StringUtils.endsWith(StringUtils.deleteWhitespace(gybl), "%") ? StringUtils.substring(StringUtils.deleteWhitespace(gybl), 0, StringUtils.deleteWhitespace(gybl).length() - 1) : StringUtils.deleteWhitespace(gybl);
            Double qlbls = Double.valueOf(qlbl);
            if (qlbls > 0 && qlbls < 1) {
                result = String.valueOf(qlbls);
            } else if (qlbls >= 1 && qlbls < 100) {
                result = String.valueOf(Double.valueOf(qlbls / 100));
            } else if (StringUtils.equals(qlbl, "100")) {
                result = "1";
            }
        }
        return result;
    }

    /**
     * @param sfzjhm 证件号
     * @return 是否成年
     * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
     * @description 验证是否未成年
     */
    public static boolean checkSfcn(String sfzjhm) {
        boolean result = true;
        if (StringUtils.isNotBlank(sfzjhm) && sfzjhm.length() == 18) {
            int yearBirth = Integer.valueOf(StringUtils.substring(sfzjhm, 6, 10));
            int monthBirth = Integer.valueOf(StringUtils.substring(sfzjhm, 10, 12));
            int dayBirth = Integer.valueOf(StringUtils.substring(sfzjhm, 12, 14));

            Calendar today = Calendar.getInstance();
            int nowYear = today.get(Calendar.YEAR);
            int nowMonth = today.get(Calendar.MONTH) + 1;
            int nowDay = today.get(Calendar.DAY_OF_MONTH);
            int age = nowYear - yearBirth;
            if (age < 18) {
                result = false;
            } else if (age == 18) {
                if (nowMonth < monthBirth) {
                    result = false;
                } else if (nowMonth == monthBirth) {
                    if (nowDay < dayBirth) {
                        result = false;
                    }
                }
            }
        }
        return result;
    }


    /**
     * @param sourceObj
     * @param appendObj
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 键值对类型的OBJ 做追加操作
     */
    public static Object objectAppendObj(Object sourceObj, Object appendObj) {
        if (sourceObj != null && appendObj != null) {
            // 排除常见基本数据类型
            if (!(sourceObj instanceof String)
                    && !(sourceObj instanceof Integer)
                    && !(sourceObj instanceof JSONObject)
                    && !(sourceObj instanceof Map)) {
                sourceObj = JSONObject.parseObject(JSONObject.toJSONString(sourceObj));
            }
            if (sourceObj instanceof JSONObject) {
                if (appendObj instanceof Map) {
                    ((JSONObject) sourceObj).putAll((Map) appendObj);
                } else if (appendObj instanceof JSONObject) {
                    ((JSONObject) sourceObj).putAll((JSONObject) appendObj);
                }
            }
            if (sourceObj instanceof Map) {
                if (appendObj instanceof Map) {
                    ((Map) sourceObj).putAll((Map) appendObj);
                } else if (appendObj instanceof JSONObject) {
                    ((Map) sourceObj).putAll((JSONObject) appendObj);
                }
            }

        } else if (appendObj != null) {
            return appendObj;
        }
        return sourceObj;
    }

    /**
     * @param jsonObject
     * @param key
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取JSON结构中 key对应的值
     */
    public static Object getKeyFromJsonObject(JSONObject jsonObject, String key) {
        if (jsonObject.containsKey(key)) {
            return jsonObject.get(key);
        }
        Iterator<Map.Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            if (StringUtils.equals(key, entry.getKey())) {
                return entry.getValue();
            }
        }
        iterator = jsonObject.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            JSONObject innerJson = new JSONObject();
            if (entry.getValue() instanceof JSONObject) {
                innerJson = (JSONObject) entry.getValue();
            } else if (entry.getValue() instanceof JSONArray) {
                innerJson = ((JSONArray) entry.getValue()).getJSONObject(0);
            }
            return getKeyFromJsonObject(innerJson, key);
        }
        return null;
    }

    /**
     * @param entity
     * @param fieldName
     * @param fieldVal
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 给实体填充属性
     */
    public static void setFieldValue(Object entity, String fieldName, Object fieldVal) {
        // 保存共有情况
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            if (field != null) {
                field.setAccessible(true);
                field.set(entity, fieldVal);
            }
        } catch (NoSuchFieldException e) {
            LOGGER.error("获取属性异常，类名：{}，属性名：{}", entity.getClass().getSimpleName(), fieldName, e);
        } catch (IllegalAccessException e) {
            LOGGER.error("set属性异常，类名：{}，属性名：{}，属性值:{}", entity.getClass().getSimpleName(),
                    fieldName, fieldVal, e);
        }
    }

    /**
     * @param throwable 异常
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取错误的堆栈信息
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = null;
        PrintWriter pw = null;
        String result = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw, true);
            throwable.printStackTrace(pw);
            pw.flush();
            sw.flush();
            result = sw.toString();
            if (result.length() > 500) {
                result = result.substring(0, 500);
            }
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e) {
                    LOGGER.error("errorMsg:", e);
                }
            }
        }
        return result;
    }

    /**
     * @param
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从OBJECT（必须是 JSON 或 MAP实例)中移除某些属性
     */
    public static Object excludeObjectKey(List<String> excludeKey, Object obj) {
        if (obj != null && !(obj instanceof Collection)) {
            if (obj instanceof Map) {
                for (String key : excludeKey) {
                    ((Map) obj).remove(key);
                }
            } else {
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(obj));
                for (String key : excludeKey) {
                    jsonObject.remove(key);
                }
                return jsonObject;
            }
        }
        return obj;
    }

    /**
     * @param bdcdyh
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 验证是否是需要过滤的BDCDYH
     */
    public static boolean checkFilterBdcdyh(String bdcdyh) {

        // 判断是否是 虚拟的BDCDYH
        String xnBdcdyhAccessFlag = EnvironmentConfig.getEnvironment().getProperty("access.xnbdcdyh", "false");
        if (!BooleanUtils.toBoolean(xnBdcdyhAccessFlag)) {
            if (StringUtils.isNotBlank(bdcdyh)
                    && bdcdyh.length() == 28
                    && BdcdyhToolUtils.checkXnbdcdyh(bdcdyh)) {
                return true;
            }
        }


        // 判断是否 是需要过滤的 行政区的上报请求
        if (Constants.ACCESS_FILTER_ARR.length > 0
                && StringUtils.isNotBlank(bdcdyh)
                && bdcdyh.length() == 28) {
            String bdcdyhXqzdm = bdcdyh.substring(0, 6);
            for (int i = 0; i < Constants.ACCESS_FILTER_ARR.length; i++) {
                if (StringUtils.equals(bdcdyhXqzdm, Constants.ACCESS_FILTER_ARR[i])) {
                    return true;
                }
            }
        }
        return false;
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
     * @param obj
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取实体表名
     */
    public static String getTableName(Object obj) {
        String tableName = "";
        Annotation[] aos = obj.getClass().getAnnotations();
        Boolean bol = false;
        for (Annotation ao : aos) {
            if (ao.annotationType().equals(XmlRootElement.class)) {
                tableName = AnnotationUtils.getValue(ao, "name").toString();
                break;
            }
        }
        return tableName;
    }


    /**
     * @param classAndMethodName 包路径.类名.方法名
     * @param params
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 执行静态方法
     */
    public static Object executeStaticMethod(String classAndMethodName, Object... params) {
        String methodName = classAndMethodName.substring(classAndMethodName.lastIndexOf(".") + 1, classAndMethodName.length());
        String className = classAndMethodName.substring(0, classAndMethodName.lastIndexOf("."));
        Method targetMethod = null;
        try {
            Class sourceClazz = Class.forName(className);
            Method[] methods = sourceClazz.getMethods();
            if (methods.length > 0) {
                for (Method method : methods) {
                    if (StringUtils.equals(method.getName(), methodName)) {
                        targetMethod = method;
                        break;
                    }
                }
            }
            if (targetMethod != null) {
                return targetMethod.invoke(sourceClazz.newInstance(), params);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.error("ClassNotFoundException", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("IllegalAccessException", e);
        } catch (InstantiationException e) {
            LOGGER.error("InstantiationException", e);
        } catch (InvocationTargetException e) {
            LOGGER.error("InvocationTargetException", e);
        }
        return null;
    }

    /**
     * @param
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取当前请求的地址
     */
    public static String getCurrentRequestPath() {
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if (request != null) {
                return request.getServletPath();
            }
        }
        return null;
    }
}
