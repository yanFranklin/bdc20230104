package cn.gtmap.realestate.certificate.util;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class PublicUtil {
    private static final Logger logger = LoggerFactory.getLogger(PublicUtil.class);

    /**
     * @param date 日期
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn 字符串时间
     * @description 日期转换为字符串
     */
    public static String convertDateToStr(Date date) {
        if (null != date) {
            SimpleDateFormat SDF_Y_M_D = new SimpleDateFormat("yyyy年MM月dd日");
            return SDF_Y_M_D.format(date);
        } else {
            return null;
        }
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(Date date) {
        String res;
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * @param date 日期
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn 字符串时间
     * @description 日期转换为字符串
     */
    public static String convertDateToStr2(Date date) {
        if (null != date) {
            SimpleDateFormat SDF_Y_M_D_H_M_S = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return SDF_Y_M_D_H_M_S.format(date);
        } else {
            return null;
        }
    }

    /**
     * @param date 日期
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn 字符串时间
     * @description 日期转换为字符串
     */
    public static String convertDateToStr3(Date date) {
        if (null != date) {
            SimpleDateFormat SDF_Y_M_D_H_M_S = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            return SDF_Y_M_D_H_M_S.format(date);
        } else {
            return null;
        }
    }

    /**
     * @param date 日期
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn 字符串时间
     * @description 日期转换为字符串
     */
    public static String convertDateToStr4(Date date) {
        if (null != date) {
            SimpleDateFormat SDF_Y_M_D_H_M_S = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss SSS");
            return SDF_Y_M_D_H_M_S.format(date);
        } else {
            return null;
        }
    }
    /**
     * @param str
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 字符串转日期
     */
    public static Date convertStrTodate(String str) {
        Date date = null;
        try {
            if (StringUtils.isNotBlank(str)) {
                SimpleDateFormat SDF_Y_M_D_H_M_S = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = SDF_Y_M_D_H_M_S.parse(str);
            }
        } catch (ParseException e) {
            logger.error("convertStrTodate", e);
        }
        return date;
    }

    /**
     * @param str
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 字符串转日期
     */
    public static Date convertStrTodate2(String str) {
        Date date = null;
        try {
            if (StringUtils.isNotBlank(str)) {
                SimpleDateFormat SDF_Y_M_D_H_M_S = new SimpleDateFormat("yyyy年MM月dd日");
                date = SDF_Y_M_D_H_M_S.parse(str);
            }
        } catch (ParseException e) {
            logger.error("convertStrTodate", e);
        }
        return date;
    }

    public static void deleteFile(String filePath){
        File file = new File(filePath);
        if(file.exists() && file.isFile())
        {
            file.delete();
        }
    }

    /**
     * @param fileName
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 获取文件路径
     */
    public static String getFilePath(String fileName) {
        try {
            String newFilePath = getPath(fileName);
            File newFile = new File(newFilePath);
            if (newFile.exists() && newFile.isFile()) {
                return newFilePath;
            }
            ClassPathResource classPathResource = new ClassPathResource(fileName);
            InputStream certStream = classPathResource.getInputStream();
            byte[] certData = IOUtils.toByteArray(certStream);

            return byteToFile(newFilePath , certData);
        } catch (IOException e) {
            logger.error("getFilePath", e);
        }
        return null;
    }

    public static String byteToFile(String filePath, byte[] file){
        try{
            FileUtils.writeByteArrayToFile(new File(filePath), file);
            return filePath;
        } catch (IOException e) {
            logger.error("byteToFile", e);
        }
        return null;
    }

    public static String getPath(String fileName){
        String newFilePath = BdcDzzzPdfUtil.DZZZ_LOCAL_PATH + fileName;
        newFilePath = newFilePath.replace("/", File.separator);
        return newFilePath;
    }

    public static String fileMkdirs(String path){
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }

        return path;
    }

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param pdfFilePath
     * @return
     * @description 绝对路径文件转数组
     */
    public static byte[] fileToByte(String pdfFilePath) {
        byte[] bytes = null;
        try (InputStream in = new FileInputStream(pdfFilePath);
             BufferedInputStream bufIn = new BufferedInputStream(in);
             ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             BufferedOutputStream bufOut = new BufferedOutputStream(byteOut)) {
            byte[] buffer = new byte[1024];
            int len = bufIn.read(buffer);
            while (len != -1) {
                bufOut.write(buffer, 0, len);
                len = bufIn.read(buffer);
            }
            bufOut.flush();
            bytes = byteOut.toByteArray();
        } catch (IOException e) {
            logger.error("fileToByte", e);
        }
        return bytes;
    }

    /**
     *
     * @param filePath
     * @return
     * @description 相对路径文件转数组
     */
    public static byte[] getFileByte(String filePath) {
        byte[] result = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource(filePath);
            InputStream certStream = classPathResource.getInputStream();
            result = IOUtils.toByteArray(certStream);
        } catch (IOException e) {
            logger.error("getFileByte", e);
        }
        return result;
    }

    private PublicUtil() {
    }


    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param fieldName
     * @param o
     * @return
     * @description 根据属性名获取属性对象
     */
    public static Field getDeclaredFieldByName(String fieldName, Object o) {
        Field field = null;
        try {
            field = o.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            logger.error("getDeclaredFieldByName:NoSuchFieldException", e);
        }
        return field;
    }

    public static Object getFieldValueByName(String fieldName, Object o) {
        Object value = null;

        List<Field> fieldList = getFieldList(o.getClass());
        if (CollectionUtils.isNotEmpty(fieldList)) {
            for (Field f : fieldList) {
                if (StringUtils.equals(fieldName, f.getName())) {
                    value = getFieldValue(f, o);
                    break;
                }
            }
        }
        return value;
    }

    public static List<Field> getFieldList(Class<?> clazz){
        if(null == clazz){
            return null;
        }
        List<Field> fieldList = new LinkedList<Field>();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            /** 过滤静态属性**/
            if(Modifier.isStatic(field.getModifiers())){
                continue;
            }
            /** 过滤transient 关键字修饰的属性**/
            if(Modifier.isTransient(field.getModifiers())){
                continue;
            }
            fieldList.add(field);
        }
        /** 处理父类字段**/
        Class<?> superClass = clazz.getSuperclass();
        if (superClass.equals(Object.class)) {
            return fieldList;
        }
        fieldList.addAll(getFieldList(superClass));
        return fieldList;
    }

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param fieldName
     * @param o
     * @return
     * @description 根据属性名获取属性值 包括public、private和proteced，但是不包括父类的申明字段
     */
    public static Object getDeclaredFieldValueByName(String fieldName, Object o) {
        Object value = null;
        try {
            Field field = o.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            value = field.get(o);
        } catch (IllegalAccessException e) {
            logger.error("getDeclaredFieldValueByName:IllegalAccessException", e);
        } catch (NoSuchFieldException e) {
            logger.error("getDeclaredFieldValueByName:NoSuchFieldException", e);
        }
        return value;
    }

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param field
     * @param o
     * @return
     * @description 根据属性对象获取属性值
     */
    public static Object getFieldValue(Field field, Object o){
        Object value = null;
        try {
            field.setAccessible(true);
            value = field.get(o);
        } catch (IllegalArgumentException e) {
            logger.error("getFieldValue:IllegalArgumentException", e);
        } catch (IllegalAccessException e) {
            logger.error("getFieldValue:IllegalAccessException", e);
        }
        return value;
    }

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param o
     * @return
     * @description 浅度赋值对象
     */
    public static Object shallowCloneObject(Object o) {
        Object o1 = null;
        try{
            o1 = BeanUtils.cloneBean(o);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return o1;
    }

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param urlStr
     * @return
     * @description 获取url中的ip
     */
    public static String getIpByUrl(String urlStr) {
        String ip = "";
        try {
            java.net.URL  url = new  java.net.URL(urlStr);
            ip = url.getHost();
        } catch (MalformedURLException e) {
            logger.error("getIpByUrl:MalformedURLException", e);
        }

        return ip;
    }

}



