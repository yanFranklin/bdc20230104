package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.AnnotationsUtils;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.exchange.core.bo.anno.CastField;
import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


/**
 * 公共工具类
 */
public class CommonUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

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
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param map
     * @param objectClass
     * @return T
     * @description 使用JSON 将MAP结构转成 OBJECT
     */
    public static <T> T mapToObject(Map map,Class<T> objectClass){
        T result = null;
        try{
            String jsonStr = JSONObject.toJSONString(map);
            if(StringUtils.isNotBlank(jsonStr)){
                result = JSONObject.parseObject(jsonStr,objectClass);
            }
        }catch (Exception e){
            LOGGER.error("map转Object异常,map:{}",map,e);
        }
        return result;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param  object
     * @return java.util.Map
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
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.lang.String
     * @description 将Map 转成 URL 后传递的参数结构
     */
    public static String mapToUrlParam(Map paramMap){
        return appendMap(paramMap,"&");
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap, split
     * @return java.lang.String
     * @description
     */
    public static String appendMap(Map paramMap,String split){
        if(MapUtils.isNotEmpty(paramMap)){
            StringBuilder paramSb = new StringBuilder("");
            Iterator<Map.Entry<String,Object>> entrySetIter = paramMap.entrySet().iterator();
            while (entrySetIter.hasNext()){
                Map.Entry<String,Object> entry = entrySetIter.next();
                if(entry.getValue() != null ){
                    String key = entry.getKey();
                    String value = entry.getValue().toString();
                    paramSb.append(key+"="+value+split);
                }
            }
            String param = paramSb.toString();
            if(param.endsWith(split)){
                param = param.substring(0,param.length()-1);
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
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param obj
     * @param clazz
     * @return boolean
     * @description 通过字段名判断OBJ是否能强转为CLASS类
     */
    public static boolean checkObjectCastToClass(Object obj,Class<?> clazz){
        JSONObject source = JSONObject.parseObject(JSONObject.toJSONString(obj));
        List<Field> fieldList = new ArrayList<>();
        AnnotationsUtils.getClassFields(clazz,fieldList);
        IgnoreCast anno = clazz.getAnnotation(IgnoreCast.class);
        int ignoreNum = 0;
        if(anno != null){
            ignoreNum = anno.ignoreNum();
        }
        List<String> diffKeyList = new ArrayList<>();
        List<String> sameKeyList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(fieldList)){
            Map<String,Field> fieldNameMap = new HashMap<>();
            List<String> castFields = new ArrayList<>();
            for (Field field : fieldList) {
                fieldNameMap.put(field.getName(),field);
                if(field.getAnnotation(CastField.class) != null){
                    castFields.add(field.getName());
                }
            }
            // 先判断必须存在的字段是否存在
            if(CollectionUtils.isNotEmpty(castFields)){
                for(String field:castFields){
                    if(!source.keySet().contains(field)){
                        return false;
                    }
                }
            }
            for(String key : source.keySet()){
                if(!key.startsWith("@") && !fieldNameMap.containsKey(key) && !fieldNameMap.containsKey(StringUtils.lowerCase(key))){
                    diffKeyList.add(key);
                }else {
                    sameKeyList.add(key);
                }
            }
        }
        if(diffKeyList.size() <= ignoreNum){
            return true;
//        }else if (CollectionUtils.isNotEmpty(sameKeyList) && sameKeyList.size()>10){
//            return true;
        }else{
            return false;
        }
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param qxdm
     * @return boolean
     * @description 判断当前区县代码  是否属于合肥房产上报配置
     */
    public static boolean isHefeiFcAccessQxdm(String qxdm){
        if(Constants.HEFEI_QXDM_ARR.length > 0
                && ArrayUtils.contains(Constants.HEFEI_QXDM_ARR,qxdm)){
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
    public static String wmQlrMcWithList(List<BdcQlrDO> qlrList,String qlrlb) {
        StringBuilder qlrSb = new StringBuilder("");
        if (CollectionUtils.isNotEmpty(qlrList)) {
            for (BdcQlrDO qlr : qlrList) {
                if(StringUtils.isNotBlank(qlrlb)
                        && StringUtils.equals(qlrlb,qlr.getQlrlb())){
                    qlrSb.append(qlr.getQlrmc()).append(",");
                }
                if(StringUtils.isBlank(qlrlb)){
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
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param objList 实体列表
     * @param methodName 方法名
     * @param split 分割符
     * @return java.lang.String
     * @description
     */
    public static String wmString(List objList,String methodName,String split){
        StringBuilder sb = new StringBuilder("");
        try {
            if(CollectionUtils.isNotEmpty(objList)){
                for(Object obj : objList){
                    Method method = obj.getClass().getMethod(methodName);
                    Object value = method.invoke(obj);
                    if(value != null && value instanceof String){
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
        if(StringUtils.isNotBlank(result)){
            result = result.substring(0,result.length()-1);
        }
        return result;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param inputStream
     * @return java.lang.String
     * @description  inputStream 转String
     */
    public static String inputStreamToString(InputStream inputStream){
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
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.lang.String
     * @description 综合查询平台 随机数格式
     */
    public static String openApiUUID(){
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
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.lang.String
     * @description  获取当前登录名
     */
    public static String aopLogGetPrincipal(){
        String principal = "unknown";
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            principal = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return principal;
    }


    /**
     * @param sfzjhm 证件号
     * @return 是否成年
     * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
     * @description 验证是否未成年
     */
    public static boolean checkSfcn(String sfzjhm){
        boolean result = true;
        if (StringUtils.isNotBlank(sfzjhm) && sfzjhm.length() ==18) {
            int yearBirth = Integer.valueOf(StringUtils.substring(sfzjhm, 6, 10));
            int monthBirth = Integer.valueOf(StringUtils.substring(sfzjhm, 10, 12));
            int dayBirth = Integer.valueOf(StringUtils.substring(sfzjhm, 12, 14));

            Calendar today = Calendar.getInstance();
            int nowYear = today.get(Calendar.YEAR);
            int nowMonth = today.get(Calendar.MONTH) +1;
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
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param sourceObj
     * @param appendObj
     * @return java.lang.Object
     * @description 键值对类型的OBJ 做追加操作
     */
    public static Object objectAppendObj(Object sourceObj,Object appendObj){
        if(sourceObj != null && appendObj != null){
            // 排除常见基本数据类型
            if(!(sourceObj instanceof String)
                    && !(sourceObj instanceof Integer)
                    && !(sourceObj instanceof JSONObject)
                    && !(sourceObj instanceof Map)){
                sourceObj = JSONObject.parseObject(JSONObject.toJSONString(sourceObj));
            }
            if(sourceObj instanceof JSONObject){
                if(appendObj instanceof Map){
                    ((JSONObject) sourceObj).putAll((Map)appendObj);
                }else if(appendObj instanceof JSONObject){
                    ((JSONObject) sourceObj).putAll((JSONObject)appendObj);
                }
            }
            if(sourceObj instanceof Map){
                if(appendObj instanceof Map){
                    ((Map) sourceObj).putAll((Map)appendObj);
                }else if(appendObj instanceof JSONObject){
                    ((Map) sourceObj).putAll((JSONObject)appendObj);
                }
            }

        }else if(appendObj != null){
            return appendObj;
        }
        return sourceObj;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param jsonObject
     * @param key
     * @return java.lang.Object
     * @description 获取JSON结构中 key对应的值
     */
    public static Object getKeyFromJsonObject(JSONObject jsonObject,String key){
        if(jsonObject.containsKey(key)){
            return jsonObject.get(key);
        }
        Iterator<Map.Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> entry = iterator.next();
            if(StringUtils.equals(key,entry.getKey())){
                return entry.getValue();
            }
        }
        iterator = jsonObject.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> entry = iterator.next();
            JSONObject innerJson = new JSONObject();
            if(entry.getValue() instanceof JSONObject){
                innerJson = (JSONObject)entry.getValue();
            }else if(entry.getValue() instanceof JSONArray){
                innerJson = ((JSONArray)entry.getValue()).getJSONObject(0);
            }
            return getKeyFromJsonObject(innerJson,key);
        }
        return null;
    }


    /**
     * @Author xiejianan
     * @Description
     * @Date 20:25 2019/4/9
     * @Param [commonMapper, record]
     * @return int
     */
    public static  <T> int insertBatchSelectiveBySplitedList(EntityMapper entityMapper, List<T> record) {
        int limit = 50;
        int size = record.size();
        int times = size % limit > 0 ? size / limit + 1 : size / limit;
        int end;
        int infectedRows = 0 ;
        for (int i = 0; i < times; i++) {
            end = (i + 1) * limit > size ? size : (i + 1) * limit;
            infectedRows += entityMapper.insertBatchSelective(record.subList(i * limit, end));
        }
        return infectedRows;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param entity
     * @param fieldName
     * @param fieldVal
     * @return void
     * @description 给实体填充属性
     */
    public static void setFieldValue(Object entity,String fieldName,Object fieldVal){
        // 保存共有情况
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            if (field != null) {
                field.setAccessible(true);
                field.set(entity,fieldVal);
            }
        } catch (NoSuchFieldException e) {
            LOGGER.error("获取属性异常，类名：{}，属性名：{}",entity.getClass().getSimpleName(),fieldName,e);
        } catch (IllegalAccessException e) {
            LOGGER.error("set属性异常，类名：{}，属性名：{}，属性值:{}",entity.getClass().getSimpleName(),
                    fieldName,fieldVal,e);
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
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.lang.Object
     * @description 从OBJECT（必须是 JSON 或 MAP实例)中移除某些属性
     */
    public static Object excludeObjectKey(List<String> excludeKey,Object obj){
        if(obj != null && !(obj instanceof Collection)){
            if(obj instanceof Map){
                for(String key : excludeKey){
                    ((Map)obj).remove(key);
                }
            }else{
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(obj));
                for(String key : excludeKey){
                    jsonObject.remove(key);
                }
                return jsonObject;
            }
        }
        return obj;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return boolean
     * @description 验证是否是需要过滤的BDCDYH
     */
    public static boolean checkFilterBdcdyh(String bdcdyh){

        // 判断是否是 虚拟的BDCDYH
        String xnBdcdyhAccessFlag = EnvironmentConfig.getEnvironment().getProperty("access.xnbdcdyh","false");
        if(!BooleanUtils.toBoolean(xnBdcdyhAccessFlag)){
            if(StringUtils.isNotBlank(bdcdyh)
                    && bdcdyh.length() == 28
                    && BdcdyhToolUtils.checkXnbdcdyh(bdcdyh)){
                return true;
            }
        }


        // 判断是否 是需要过滤的 行政区的上报请求
        if(Constants.ACCESS_FILTER_ARR.length > 0
                && StringUtils.isNotBlank(bdcdyh)
                && bdcdyh.length() == 28){
            String bdcdyhXqzdm = bdcdyh.substring(0,6);
            for(int i = 0 ; i< Constants.ACCESS_FILTER_ARR.length ; i++){
                if(StringUtil.equals(bdcdyhXqzdm,Constants.ACCESS_FILTER_ARR[i])){
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
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param obj
     * @return java.lang.String
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
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param classAndMethodName 包路径.类名.方法名
     * @param params
     * @return java.lang.Object
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
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcSlSqrDOS
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @description 拼凑多个权利人
     */
    public static BdcSlSqrDO wmBdcSlSqlr(List<BdcSlSqrDO> bdcSlSqrDOS){
        if(CollectionUtils.isNotEmpty(bdcSlSqrDOS)){
            BdcSlSqrDO temp = new BdcSlSqrDO();
            temp.setSqrmc("");
            temp.setZjh("");
            temp.setDh("");
            String split = ",";
            for(int i=0;i<bdcSlSqrDOS.size();i++){
                BdcSlSqrDO bdcSlSqrDO = bdcSlSqrDOS.get(i);
                if(i==0){
                    split = "";
                }else{
                    split = ",";
                }
                temp.setSqrmc(temp.getSqrmc()+split+bdcSlSqrDO.getSqrmc());
                temp.setZjh(temp.getZjh()+split+bdcSlSqrDO.getZjh());
                temp.setDh(temp.getDh()+split+bdcSlSqrDO.getDh());
                // 证件种类 只保存一个
                temp.setZjzl(bdcSlSqrDO.getZjzl());
            }
            return temp;
        }
        return null;
    }

    /**
     * @param source
     * @param target
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 对象赋值，NULL忽略
     */
    public static void copyPropertiesWithOutNull(Object source, Object target) {
        BeanUtil.copyProperties(source, target, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
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
     * 根据区县代码获取指定区划配置
     * @param configName 当前配置项内容（例如 fs.config.beanName）
     * @param defaultValue 配置项默认配置值 （例如 fs.config.beanName = test，则当前参数传 test）
     * @param qxdm 区县代码
     * @return {Object} 对应区划配置项值，例如 （fs.config.beanName.340124 = test2，fs.config.beanName = test1，如果区县代码为340124，返回test2；区县代码为空，返回test1；区县代码不等于340124，返回test1）
     */
    public static Object getConfigValueByQxdm(String configName, Object defaultValue, String qxdm) {
        if(StringUtils.isBlank(configName)) {
            LOGGER.info("获取配置项{}值，返回默认设值{}，因为：未声明配置项", configName, defaultValue);
            return defaultValue;
        }

        try {
            Object configValue;
            if (StringUtils.isNotBlank(qxdm)){
                configValue = getConfigValue2(configName+"."+qxdm, defaultValue);
                if (configValue == null){
                    configValue = getConfigValue2(configName, defaultValue);
                }
            }else {
                configValue = getConfigValue2(configName, defaultValue);
            }
            return null == configValue ? defaultValue : configValue;
        } catch (Exception e) {
            LOGGER.error("获取区县代码指定区划配置项{}值，发生异常，返回默认设值{}", configName, defaultValue, e);
            return defaultValue;
        }
    }
    /**
     * 获取配置项值，defaultValue为null返回String类型，不为null，返回defaultValue对应类型
     * @param configName 当前配置项内容
     * @param defaultValue 配置项默认配置值
     * @return {Object} 配置项值
     */
    public  static Object getConfigValue2(String configName, Object defaultValue) {
        Object configValue;

        if(null == defaultValue) {
            configValue = EnvironmentConfig.getEnvironment().getProperty(configName);
        } else {
            configValue = EnvironmentConfig.getEnvironment().getProperty(configName, defaultValue.getClass());
        }
        return configValue;
    }
}
