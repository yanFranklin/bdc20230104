package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.exchange.core.annotations.Zd;
import cn.gtmap.realestate.exchange.core.cache.BdcZdCache;
import cn.gtmap.realestate.exchange.core.dto.BdcMapZdConvertDTO;
import cn.gtmap.realestate.exchange.core.service.impl.BdcZdGlServiceImpl;
import cn.gtmap.realestate.exchange.core.support.mybatis.utils.AnnotationsUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2018/11/7
 * @description 转换object中的字典项
 */
@Service("entityZdConvertUtils")
@Import({BdcZdCache.class, BdcZdGlServiceImpl.class})
public class EntityZdConvertUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityZdConvertUtils.class);

    private static final String CONVERTTOMC = "mc";
    private static final String CONVERTTODM = "dm";

    @Autowired
    BdcZdCache bdcZdCache;


    /**
     * 转换实体类中的字典项 默认不覆盖空子段 转换所有字典项
     *
     * @param entity 要转换的实体
     */
    public void convertEntityToMc(Object entity) {
        convertEntity(entity, false, null, CONVERTTOMC);
    }

    /**
     * sly 转换实体类中的字典项
     *
     * @param entity           要转换的实体
     * @param isCoverNullField 是否需要覆盖没有查询出来的空子段
     * @param convertfieldList 有些实体可能不需要全部转换，此处为要转换得实体字段名称 如果为空则全部转换
     */
    public void convertEntityToMc(Object entity, Boolean isCoverNullField, String[] convertfieldList) {
        convertEntity(entity, isCoverNullField, convertfieldList, CONVERTTOMC);
    }

    /**
     * sly 转换实体类中的字典项 默认不覆盖空子段 转换所有字典项
     *
     * @param entity 要转换的实体
     */
    public void convertEntityToDm(Object entity) {
        convertEntity(entity, false, null, CONVERTTODM);
    }

    /**
     * sly 转换实体类中的字典项
     *
     * @param entity           要转换的实体
     * @param isCoverNullField 是否需要覆盖没有查询出来的空子段
     * @param convertfieldList 有些实体可能不需要全部转换，此处为要转换得实体字段名称 如果为空则全部转换
     */
    public void convertEntityToDm(Object entity, Boolean isCoverNullField, String[] convertfieldList) {
        convertEntity(entity, isCoverNullField, convertfieldList, CONVERTTODM);
    }

    /**
     * @param entity
     * @param isCoverNullField
     * @param convertfieldList
     * @param convertTo
     */
    private void convertEntity(Object entity, Boolean isCoverNullField, String[] convertfieldList, String convertTo) {
        //获得标注了zd注解的所有字段
        List<Field> fieldList = AnnotationsUtils.getAnnotationField(entity, Zd.class);
        Boolean isConvertAll = ArrayUtils.isEmpty(convertfieldList);
        if (CollectionUtils.isNotEmpty(fieldList)) {
            for (Field entityField : fieldList) {
                if (isConvertAll || ArrayUtils.contains(convertfieldList, entityField.getName())) {
                    Zd entityFieldId = entityField.getAnnotation(Zd.class);
                    String table = entityFieldId.table();
                    String dm = entityFieldId.dm();
                    String mc = entityFieldId.mc();
                    Class tableClass = entityFieldId.tableClass();
                    if (StringUtils.isBlank(table)) {
                        table = tableClass.getSimpleName();
                    }
                    //如果这个字段中标注了字典表的表名并且当前字段有值
                    if (StringUtils.isNotBlank(table) && getEntityFieldValue(entityField, entity) != null) {
                        Object value;
                        if (StringUtils.equals(convertTo, CONVERTTOMC)) {
                            value = bdcZdCache.getFeildValue(table, getEntityFieldValue(entityField, entity), mc, dm, tableClass);
                        } else {
                            value = bdcZdCache.getFeildValue(table, getEntityFieldValue(entityField, entity), dm, mc, tableClass);
                        }
                        if (value != null || isCoverNullField) {
                            setEntityFieldValue(entityField, entity, value);
                        }
                    }
                }
            }
        }
    }

    /**
     * sly 通过反射获取实体类某个字段的值
     *
     * @param entityField 实体类的某个字段
     * @param entity      实体类
     * @return 返回获取的实体类字段的值
     */
    private static Object getEntityFieldValue(Field entityField, Object entity) {
        if (entityField != null && entity != null) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("get");
                sb.append(entityField.getName().substring(0, 1).toUpperCase());
                sb.append(entityField.getName().substring(1));
                Method method = entity.getClass().getMethod(sb.toString());
                return method.invoke(entity);
            } catch (Exception e) {
                LOGGER.error("获取值时报错", e);
            }
        }
        return null;
    }

    /**
     * sly 通过反射赋值实体类某个字段的值
     *
     * @param entityField 实体类中的一个字段
     * @param entity      实体类
     * @param value       要赋的值
     */
    private static void setEntityFieldValue(Field entityField, Object entity, Object value) {
        if (entityField != null && entity != null) {
            try {
                Class[] parameterTypes = new Class[1];
                parameterTypes[0] = entityField.getType();
                StringBuilder sb = new StringBuilder();
                sb.append("set");
                sb.append(entityField.getName().substring(0, 1).toUpperCase());
                sb.append(entityField.getName().substring(1));
                Method method = entity.getClass().getMethod(sb.toString(), parameterTypes);
                method.invoke(entity, value);
            } catch (Exception e) {
                LOGGER.error("赋值时报错", e);
            }
        }
    }

    /**
     * 转换map中的所有字典代码项为名称，因为只传了map 所以默认使用BdcZdCache.defaultConvertVoList
     *
     * @param convertMap 要转换的map，记得对map进行处理
     */
    public void convertMapToMc(Map convertMap) {
        convertMap(convertMap, false, CONVERTTOMC, BdcZdCache.defaultConvertVoList);
    }

    /**
     * 转换map中的所有字典代码项为名称
     *
     * @param convertMap    要转换的map
     * @param convertVoList 需要转换的字段list
     */
    public void convertMapToMc(Map convertMap, List<BdcMapZdConvertDTO> convertVoList) {
        convertMap(convertMap, false, CONVERTTOMC, convertVoList);
    }

    /**
     * 转换map中的所有字典代码项为名称
     *
     * @param convertMap       要转换的map，如果convertVoList为空  默认使用BdcZdCache.defaultConvertVoList
     * @param isCoverNullField 如果根据值在字典表中查询不到，是否需要覆盖原值
     * @param convertVoList    需要转换的字段list
     */
    public void convertMapToMc(Map convertMap, Boolean isCoverNullField, List<BdcMapZdConvertDTO> convertVoList) {
        convertMap(convertMap, isCoverNullField, CONVERTTOMC, convertVoList);
    }

    /**
     * 转换map中的所有字典代码项为代码，因为只传了map 所以默认使用BdcZdCache.defaultConvertVoList
     *
     * @param convertMap 要转换的map
     */
    public void convertMapToDm(Map convertMap) {
        convertMap(convertMap, false, CONVERTTODM, BdcZdCache.defaultConvertVoList);
    }

    /**
     * 转换map中的所有字典代码项为代码
     *
     * @param convertMap    要转换的map
     * @param convertVoList 需要转换的字段list
     */
    public void convertMapToDm(Map convertMap, List<BdcMapZdConvertDTO> convertVoList) {
        convertMap(convertMap, false, CONVERTTODM, convertVoList);
    }

    /**
     * 转换map中的所有字典代码项为代码
     *
     * @param convertMap       要转换的map
     * @param isCoverNullField 如果根据值在字典表中查询不到，是否需要覆盖原值
     * @param convertVoList    需要转换的字段list
     */
    public void convertMapToDm(Map convertMap, Boolean isCoverNullField, List<BdcMapZdConvertDTO> convertVoList) {
        convertMap(convertMap, isCoverNullField, CONVERTTODM, convertVoList);
    }

    /**
     * * 转换map中的字典项
     *
     * @param convertMap
     * @param isCoverNullField
     * @param convertTo
     * @param convertVoList
     */
    private void convertMap(Map convertMap, Boolean isCoverNullField, String convertTo, List<BdcMapZdConvertDTO> convertVoList) {
        if (CollectionUtils.isNotEmpty(convertVoList)) {
            for (BdcMapZdConvertDTO convertVo : convertVoList) {
                String fieldName;
                if (convertMap.containsKey(StringUtils.upperCase(convertVo.getName()))) {
                    fieldName = StringUtils.upperCase(convertVo.getName());
                } else {
                    fieldName = StringUtils.lowerCase(convertVo.getName());
                }
                Object value;
                String fieldValue = MapUtils.getString(convertMap, fieldName);
                if (StringUtils.isNotBlank(fieldValue)) {
                    if (StringUtils.equals(convertTo, CONVERTTOMC)) {
                        value = bdcZdCache.getFeildValue(convertVo.getTable(), fieldValue, convertVo.getMc(), convertVo.getDm(), convertVo.getTableClass());
                    } else {
                        value = bdcZdCache.getFeildValue(convertVo.getTable(), fieldValue, convertVo.getDm(), convertVo.getMc(), convertVo.getTableClass());
                    }
                    if (value != null || isCoverNullField) {
                        convertMap.put(fieldName, value);
                    }
                }
            }
        }
    }
}
