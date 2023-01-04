package cn.gtmap.realestate.common.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/10/31
 * @description 传入参数检查类
 */
public final class CheckParameter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckParameter.class);

    private CheckParameter() {

    }

    /**
     * 确认传入参数是否有至少一条存在数据
     * @param paramNames 最起码有一个要必填的
     * @param checkData 传入实体
     * @return
     */
    public static Boolean checkAnyParameter(Object checkData,String... paramNames) {
        if (checkData == null) {
            return false;
        }
        Class checkClass = checkData.getClass();
        try {
            List<Field> fieldList = new ArrayList<>();
            AnnotationsUtils.getClassFields(checkClass,fieldList);
            if(CollectionUtils.isNotEmpty(fieldList)){
                for (Field field : fieldList) {
                    field.setAccessible(true);
                    if(StringUtils.equals("serialVersionUID",field.getName())){
                        continue;
                    }

                    Object val = field.get(checkData);
                    if (null != val && !"".equals(val) && !"[]".equals(String.valueOf(val)) && !"null".equals(String.valueOf(val))) {
                        if(paramNames.length==0 || ArrayUtils.contains(paramNames,field.getName())){
                            return true;
                        }
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

        return false;
    }

    /**
     * 确认传入参数是否全部存在
     *
     * @param checkData 传入实体
     * @return
     */
    public static Boolean checkAllParameter(Object checkData) {
        if (checkData == null) {
            return false;
        }
        Class checkClass = checkData.getClass();
        try {
            Field[] fields = checkClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(checkData) == null) {
                    return false;
                }
            }
        } catch (IllegalAccessException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

        return true;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   obj 需要校验的实体
     * @param   exceptElements 不需要校验的属性名称集合
     * @return  {Boolean}   都不存在值：true，有部分属性存在值：false
     * @description
     *      校验实体（除了指定属性之外的）属性是否都不存在值
     */
    public static Boolean checkPartElementsNotAllExist(Object obj, Collection<Object> exceptElements){
        if (null == obj) {
            return true;
        }

        Class checkClass = obj.getClass();
        try {
            Field[] fields = checkClass.getDeclaredFields();
            for (Field field : fields) {
                // 除去不需要校验的属性元素
                if(CollectionUtils.isNotEmpty(exceptElements) && exceptElements.contains(field.getName())){
                    continue;
                }

                field.setAccessible(true);
                // 有一个属性存在值，则校验结束
                if (null != field.get(obj)) {
                    return false;
                }
            }
        } catch (IllegalAccessException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

        return true;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   obj 需要校验的实体
     * @param   elements 需要校验的属性名称集合
     * @return  {Boolean}   都不存在值：true，有部分属性存在值：false
     * @description
     *      校验实体（指定属性）属性是否都不存在值
     */
    public static Boolean checkAppointedElementsAllNotExist(Object obj, Collection<Object> elements){
        if (null == obj) {
            return true;
        }

        Class checkClass = obj.getClass();
        try {
            Field[] fields = checkClass.getDeclaredFields();
            for (Field field : fields) {
                // 校验指定的属性元素
                if(CollectionUtils.isNotEmpty(elements) && elements.contains(field.getName())){
                    field.setAccessible(true);
                    // 有一个属性存在值，则校验结束
                    if (null != field.get(obj)) {
                        return false;
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

        return true;
    }

    /**
      * 检查需要检查的字段是否不为空
      * @param
      * @return
      * @Date 2021/4/15
      * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
         */
    public static Boolean checkParameter(Object checkData,String... paramNames) {
        if (checkData == null) {
            return false;
        }
        Class checkClass = checkData.getClass();
        try {
            List<Field> fieldList = new ArrayList<>();
            AnnotationsUtils.getClassFields(checkClass,fieldList);
            if(CollectionUtils.isNotEmpty(fieldList)){
                for (Field field : fieldList) {
                    field.setAccessible(true);
                    if(StringUtils.equals("serialVersionUID",field.getName())){
                        continue;
                    }
                    if(ArrayUtils.contains(paramNames,field.getName())){
                        if (null == field.get(checkData) || "".equals( field.get(checkData))){
                            return false;
                        }
                    }
                }
                return true;
            }
        } catch (IllegalAccessException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

        return false;
    }
}
