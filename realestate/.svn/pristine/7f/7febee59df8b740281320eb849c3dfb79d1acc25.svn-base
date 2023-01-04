package cn.gtmap.realestate.building.util;

import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.BeanUtilsEx;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityHelper;
import cn.gtmap.realestate.common.util.AnnotationsUtils;
import cn.gtmap.realestate.common.util.CheckEntityUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.druid.proxy.jdbc.ClobProxy;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-14
 * @description 楼盘表子系统工具类
 */
public class BuildingUtils {

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BuildingUtils.class);

    private static final Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");


    /**
     * AOP日志开关  默认关闭
     */
    public static boolean AOP_LOG = false;

    /**
     * @param qlrmcList
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 循环List拼接权利人名称
     */
    public static String wmQlrMcWithList(List<String> qlrmcList) {
        StringBuilder qlrSb = new StringBuilder("");
        if (CollectionUtils.isNotEmpty(qlrmcList)) {
            for (String qlrmc : qlrmcList) {
                qlrSb.append(qlrmc).append(",");
            }
        }
        String qlr = qlrSb.toString();
        if (qlr.endsWith(",")) {
            qlr = qlr.substring(0, qlr.length() - 1);
        }
        return qlr;
    }

    /**
     * @param dataMap
     * @return java.util.Map
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 将Map 的 key 值转成 小写
     */
    public static void convertKeyToLowerCase(Map dataMap) {
        Map tempMap = new HashMap();
        tempMap.putAll(dataMap);
        if (MapUtils.isNotEmpty(tempMap)) {
            Iterator<Map.Entry<String, Object>> iterator = tempMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                String key = entry.getKey();
                Object value = entry.getValue();
                dataMap.remove(key);
                dataMap.put(StringUtils.lowerCase(key), value);
            }
        }
    }


    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从BDCDYH中截取DJH
     */
    public static String getDjhByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() == 28) {
            return bdcdyh.substring(0, 19);
        }
        return "";
    }


    /**
     * @param value
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description ascii码转String
     */
    public static String asciiToString(String value) {
        StringBuilder sbu = new StringBuilder();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }

    /**
     * @param value
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description String码转ascii
     */
    public static String stringToAscii(String value) {
        StringBuilder sbu = new StringBuilder();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }

    /**
     * @param parameter
     * @param lenth
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过长度，格式化数字
     */
    public static String formatNumberByLengh(final Integer parameter, String lenth) {
        final DecimalFormat df;
        df = new DecimalFormat(lenth);
        return df.format(parameter);
    }

    /**
     * @param source
     * @param target
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 对象赋值，NULL忽略
     */
    public static void copyPropertiesWithOutNull(Object source, Object target) {
        BeanUtil.copyProperties(source, target, true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
    }


    /**
     * @param object
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取主键值
     */
    public static Object getObjectPk(Object object) {
        List<Field> fieldPkList = AnnotationsUtils.getAnnotationField(object, Id.class);
        if (CollectionUtils.isNotEmpty(fieldPkList)) {
            Field field = fieldPkList.get(0);
            return CheckEntityUtils.getFieldValue(field, object);
        }
        return null;
    }

    /**
     * 获取反射方法
     *
     * @param serviceClass
     * @param methodName
     * @return
     */
    public static Method getMethod(Class serviceClass, String methodName, Class... paramType) {
        try {
            return serviceClass.getMethod(methodName, paramType);
        } catch (NoSuchMethodException e) {
            LOGGER.error("方法不存在", e);
        }
        return null;
    }


    /**
     * @param
     * @return boolean
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 判断是否是整数
     */
    public static boolean isInteger(String str) {
//        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


    /**
     * @param list
     * @return java.util.List
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 处理list中的重复数据
     */
    public static List removeDuplicate(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }


    /**
     * @param entity
     * @param fieldName
     * @param val
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 给 实体 set 属性值
     */
    public static void setVal(Object entity, String fieldName, Object val) {
        Method method;
        try {
            method = entity.getClass().getMethod(getSetMethodName(fieldName), String.class);
            method.invoke(entity, val);
        } catch (NoSuchMethodException e) {
            LOGGER.error("给实体set属性值异常",e);
        } catch (IllegalAccessException e) {
            LOGGER.error("给实体set属性值异常",e);
        } catch (InvocationTargetException e) {
            LOGGER.error("给实体set属性值异常",e);
        }
    }

    /**
     * @param entity
     * @param fieldName
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取实体中的属性值
     */
    public static Object getVal(Object entity, String fieldName) {
        // set field值
        Method method = null;
        Object val = null;
        try {
            method = entity.getClass().getMethod(getGetMethodName(fieldName));
            val = method.invoke(entity);
        } catch (NoSuchMethodException e) {
            LOGGER.error("获取实体中的属性值",e);
        } catch (IllegalAccessException e) {
            LOGGER.error("获取实体中的属性值",e);
        } catch (InvocationTargetException e) {
            LOGGER.error("获取实体中的属性值",e);
        }
        return val;
    }

    private static String getSetMethodName(String ztColumnName) {
        return "set" + StringUtils.upperCase(ztColumnName.substring(0, 1))
                + ztColumnName.substring(1, ztColumnName.length());
    }

    private static String getGetMethodName(String fieldName) {
        return "get" + StringUtils.upperCase(fieldName.substring(0, 1))
                + fieldName.substring(1, fieldName.length());
    }

    public static void openAopLog() {
        AOP_LOG = true;
    }

    public static void closeAopLog() {
        AOP_LOG = false;
    }

    public static Object gnqyzGetDO(String jsonData) {
        JSONArray jsonArray = JSONArray.parseArray(jsonData);
        JSONObject jsonObjectDO = JSONObject.parseObject(jsonArray.getString(0));
        JSONObject jsonObject = jsonObjectDO.getJSONObject("info");
        return jsonObject.toJavaObject(FwXmxxDO.class);
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param in
     * @return java.lang.String
     * @description 文件流转base64位码
     */
    public static String getBase64FromInputStream(InputStream in) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = in.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new String(Base64.encodeBase64(data));
    }

    /**
     * map转换为bean
     * 主要为解决 entityMapper 对clob 字段 解析问题
     * @param map
     * @param beanClass
     * @return
     */
    public static Object map2Bean(Map<String, Object> map, Class<?> beanClass) {
        try {
            if (map == null) {
                return null;
            }
            Map<String, Object> aliasMap = EntityHelper.map2AliasMap(map, beanClass);
            Object bean = beanClass.newInstance();
            Iterator<Map.Entry<String, Object>> iterator = aliasMap.entrySet().iterator();
            Map<String,Object> revertMap = new HashMap<>();
            while (iterator.hasNext()){
                Map.Entry<String, Object> it = iterator.next();
                String key = it.getKey();
                Object value = it.getValue();
                if((value instanceof ClobProxy) && value != null){
                    ClobProxy clob = (ClobProxy) value;
                    String clobValue = clob.getSubString((long)1,(int)clob.length());
                    revertMap.put(key,clobValue);
                }else{
                    revertMap.put(key,value);
                }
            }
            BeanUtilsEx.copyProperties(bean, revertMap);
            LOGGER.error("转换完的bean:{}",JSONObject.toJSONString(bean));
            return bean;
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage(), e);
            throw new NoSuchMethodError(beanClass.getCanonicalName() + "类没有默认空的构造方法!");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param userName
     * @param file
     * @return cn.gtmap.gtc.storage.domain.dto.MultipartDto
     * @description 构造上传参数
     */
    public static MultipartDto getUploadParamDto(String userName, MultipartFile file,String fileName) throws IOException {
        MultipartDto multipartDto = new MultipartDto();
        if(file != null){
            multipartDto.setData(file.getBytes());
            multipartDto.setOwner(userName);
            multipartDto.setContentType(file.getContentType());
            multipartDto.setSize(file.getSize());
            multipartDto.setOriginalFilename(fileName);
            multipartDto.setName(fileName);
        }
        return multipartDto;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param blob
     * @return java.lang.String
     * @description BLOB强转Str
     */
    public static String blobToStr(Blob blob){
        String blobToStr = "";
        if(blob != null){
            try{
                int Bloblen = (int) blob.length();
                byte[] data = blob.getBytes(1,Bloblen);
                String type = BuildingUtils.bytesToHexString(data).toUpperCase();
                LOGGER.info("type:{}",type);
                // 判断是否是 WMF 格式
                if(type.startsWith("D7CDC")){
                    LOGGER.info("wmf格式");
                    data = WmfToJpgUtils.convert(data);
                }
                InputStream is = new ByteArrayInputStream(data);
                blobToStr = Base64Util.ImageToBase64ByIn(is);
                LOGGER.info("blobToStr: {}",blobToStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return blobToStr;
    }

    /**
     * byte数组转换成16进制字符串
     * @param src add by sgh
     * @return
     */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        if(src.length >= 128){
            for (int i = 0; i < 128; i++) {
                int v = src[i] & 0xFF;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(hv);
            }
        }
        return stringBuilder.toString();
    }
}
