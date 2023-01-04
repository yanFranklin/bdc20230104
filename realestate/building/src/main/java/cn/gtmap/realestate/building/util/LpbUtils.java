package cn.gtmap.realestate.building.util;

import cn.gtmap.realestate.building.config.lpb.LpbConfig;
import cn.gtmap.realestate.building.core.bo.ColumnBO;
import cn.gtmap.realestate.building.core.resource.StatusResource;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.building.FwhsAndFwQlrRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.FwychsAndFwQlrRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.ImportLpbRequestDTO;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.AnnotationsUtils;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.ReflectUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-03
 * @description 构造楼盘表相关 工具类、 静态方法
 */
public class LpbUtils {
    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LpbConfig.class);

    /**
     * URL 转换前缀
     */
    private static final char PATTERN_PRE = '{';

    /**
     * URL 转换后缀
     */
    private static final char PATTERN_END = '}';


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param object
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @description 实体装换成MAP
     */
    public static Map<String,Object> parseObjectToMap(Object object){
        // 实体转JSON
        String json = JSONObject.toJSONString(object);
        return JSONObject.parseObject(json, LinkedHashMap.class);
    }

    /**
     * @param object
     * @param columnBOList
     * @return java.util.Map<java.lang.String   ,   java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 将实体转成Map
     */
    public static Map<String, Object> parseObjectToMap(Object object, List<ColumnBO> columnBOList) {
        // 实体转JSON
        String json = JSONObject.toJSONString(object);
        Map<String, Object> yMap = JSONObject.parseObject(json, LinkedHashMap.class);
        Map<String, Object> resultMap = new LinkedHashMap<>();
        // 处理 info 配置
        if (CollectionUtils.isNotEmpty(columnBOList)) {
            for (ColumnBO column : columnBOList) {
                String value = column.getValue();
                String desc = column.getDesc();
                String alias = column.getAlias();
                Map<String,Object> tempMap = new HashMap<>();
                tempMap.put("desc",desc);
                if (StringUtils.isNotBlank(value)
                        && MapUtils.getObject(yMap, value) != null) {
                    tempMap.put("value",yMap.get(value));
                }
                if(StringUtils.isNotBlank(column.getTabType())){
                    tempMap.put("tabType",column.getTabType());
                }
                if(StringUtils.isNotBlank(alias)){
                    resultMap.put(alias,tempMap);
                }else{
                    resultMap.put(value,tempMap);
                }
            }
        } else {
            resultMap.putAll(yMap);
        }
        return resultMap;
    }

    /**
     * @param code
     * @param resource
     * @param statusList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 实例化  statusList 中的 状态类
     */
    public static void instanceStatus(String code, List<String> statusList, StatusResource resource) {
        if (CollectionUtils.isNotEmpty(statusList)) {
            Constructor constructor;
            for (String statustemp : statusList) {
                FwHsStatusEnum statusEnum = FwHsStatusEnum.getEnum(code, statustemp);
                if (statusEnum != null) {
                    try {
                        constructor = statusEnum.getStatusClass().getConstructor(StatusResource.class);
                        constructor.newInstance(resource);
                    } catch (NoSuchMethodException e) {
                        LOGGER.error("获取构造函数失败 NoSuchMethodException", e);
                    } catch (IllegalAccessException e) {
                        LOGGER.error("获取构造函数失败 IllegalAccessException", e);
                    } catch (InstantiationException e) {
                        LOGGER.error("获取构造函数失败 InstantiationException", e);
                    } catch (InvocationTargetException e) {
                        LOGGER.error("获取构造函数失败 InvocationTargetException", e);
                    }
                }
            }
        }
    }

    /**
     * @param strWithPlaceholder
     * @param paramMap
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 将带有参数{}的URL 替换成明确URL
     */
    public static String revertPlaceholder(String strWithPlaceholder, Map<String, Object> paramMap) {
        StringBuilder strSb = new StringBuilder();
        if (StringUtils.isNotBlank(strWithPlaceholder)) {
            StringBuilder paramSb = new StringBuilder();
            boolean paramFlag = false;
            for (int i = 0; i < strWithPlaceholder.length(); i++) {
                char cur = strWithPlaceholder.charAt(i);
                if (PATTERN_PRE == cur) {
                    paramSb = new StringBuilder();
                    paramFlag = true;
                } else if (PATTERN_END == cur) {
                    paramFlag = false;
                    String paramName = paramSb.toString();
                    // 先查查询结果实体
                    String paramVal = MapUtils.getString(paramMap, paramName);
                    // 不存在 查询 属性配置
                    if (StringUtils.isBlank(paramVal)) {
                        paramVal = EnvironmentConfig.getEnvironment().getProperty(paramName);
                    }
                    strSb.append(paramVal);
                } else {
                    if (paramFlag) {
                        paramSb.append(cur);
                    } else {
                        strSb.append(cur);
                    }
                }
            }
        }
        return strSb.toString();
    }

    /**
     * 从一个excel数据中读取房屋户室与对应权利人数据
     * @param lpbList
     * @return
     */
    public static List<FwhsAndFwQlrRequestDTO> getfwhsAndFwQlrList(List<Map<String, Object>> lpbList) {
        List<FwhsAndFwQlrRequestDTO> fwhsAndFwQlrS = new ArrayList<>();
        for (Map<String, Object> lpb : lpbList) {
            FwHsDO fwHsDO = (FwHsDO) getEntityByLpbInfo(FwHsDO.class, lpb);
            if (fwHsDO != null) {
                FwhsAndFwQlrRequestDTO fwhsAndFwQlrRequestDTO = new FwhsAndFwQlrRequestDTO();
                fwhsAndFwQlrRequestDTO.setFwHsDO(fwHsDO);
                fwhsAndFwQlrRequestDTO.setQlrList(getFwfcQlrListByLpbMap(lpb));
                fwhsAndFwQlrS.add(fwhsAndFwQlrRequestDTO);
            }
        }
        return fwhsAndFwQlrS;
    }

    /**
     * 从一个excel数据中读取房屋户室与对应权利人数据
     * @param lpbList
     * @return
     */
    public static List<FwychsAndFwQlrRequestDTO> getfwychsAndFwQlrList(List<Map<String, Object>> lpbList) {
        List<FwychsAndFwQlrRequestDTO> fwhsAndFwQlrS = new ArrayList<>();
        for (Map<String, Object> lpb : lpbList) {
            FwYchsDO fwYchsDO = (FwYchsDO) getEntityByLpbInfo(FwYchsDO.class, lpb);
            if (fwYchsDO != null) {
                FwychsAndFwQlrRequestDTO fwychsAndFwQlrRequestDTO = new FwychsAndFwQlrRequestDTO();
                fwychsAndFwQlrRequestDTO.setFwYchsDO(fwYchsDO);
                fwychsAndFwQlrRequestDTO.setQlrList(getFwfcQlrListByLpbMap(lpb));
                fwhsAndFwQlrS.add(fwychsAndFwQlrRequestDTO);
            }
        }
        return fwhsAndFwQlrS;
    }

    /**
     * 从一个excel数据中读取房屋户室数据
     *
     * @param lpbList
     * @return
     */
    public static List<FwHsDO> getfwhsList(List<Map<String, Object>> lpbList) {
        List<FwHsDO> fwHsDOList = new ArrayList<>();
        for (Map<String, Object> lpb : lpbList) {
            FwHsDO fwHsDO = (FwHsDO) getEntityByLpbInfo(FwHsDO.class, lpb);
            if (fwHsDO != null) {
                fwHsDOList.add(fwHsDO);
            }
        }
        return fwHsDOList;
    }

    /**
     * 从一个excel数据中读取权利人数据
     * @param lpb
     * @return
     */
    public static List<FwFcqlrDO> getFwfcQlrListByLpbMap(Map<String, Object> lpb) {
        List<FwFcqlrDO> qlrList = new ArrayList<>();
        List<Field> fieldList = AnnotationsUtils.getAnnotationField(new FwFcqlrDO(), ApiModelProperty.class);
        for (int i = 1; i <= 5; i++) {
            Map fwFcqlrMap = new HashMap();
            for (Field field : fieldList) {
                ApiModelProperty entityFieldId = field.getAnnotation(ApiModelProperty.class);
                for (Map.Entry<String, Object> lpbField : lpb.entrySet()) {
                    if (StringUtils.equals(entityFieldId.value() + String.valueOf(i), lpbField.getKey()) && validateObjectStringIsNotNull(lpbField.getValue())) {
                        fwFcqlrMap.put(field.getName(), lpbField.getValue());
                    }
                }
            }
            if (MapUtils.isNotEmpty(fwFcqlrMap)) {
                qlrList.add(JSONObject.parseObject(JSONObject.toJSONString(fwFcqlrMap), FwFcqlrDO.class));
            }
        }
        return qlrList;
    }

    /**
     * 从一个excel数据中读取逻辑幢数据
     * @param importLpbRequestDTO
     * @return
     */
    public static FwLjzDO getLjzInfoByExcel(ImportLpbRequestDTO importLpbRequestDTO) {
        Map<String, Object> lpbLjzInfo = null;
        for (Map lpb : importLpbRequestDTO.getLpbList()) {
            if (MapUtils.isNotEmpty(lpb)) {
                lpbLjzInfo = lpb;
                break;
            }
        }
        FwLjzDO fwLjzDO = (FwLjzDO) getEntityByLpbInfo(FwLjzDO.class, lpbLjzInfo);
        if (fwLjzDO != null) {
            fwLjzDO.setFwDcbIndex(importLpbRequestDTO.getFwDcbIndex());
        }
        return fwLjzDO;
    }

    /**
     * 从一个excel导出的楼盘表map数据（一行）处理成一个实体
     * @param convertEntityClass
     * @param lpbLjzInfo
     * @return
     */
    public static Object getEntityByLpbInfo(Class convertEntityClass, Map<String, Object> lpbLjzInfo) {
        Map entityMap = new HashMap();
        Object convertEntityDO = ReflectUtils.newInstance(convertEntityClass);
        List<Field> fieldList = AnnotationsUtils.getAnnotationField(convertEntityDO, ApiModelProperty.class);
        if (lpbLjzInfo != null && CollectionUtils.isNotEmpty(fieldList)) {
            for (Field field : fieldList) {
                ApiModelProperty entityFieldId = field.getAnnotation(ApiModelProperty.class);
                for (Map.Entry<String, Object> lpbField : lpbLjzInfo.entrySet()) {
                    if (StringUtils.equals(entityFieldId.value(), StringUtils.remove(lpbField.getKey(), "(㎡)")) && validateObjectStringIsNotNull(lpbField.getValue())) {
                        //处理excel中的调查时间
                        if(StringUtils.equals(field.getName(),"dcsj")){
                            String dcsj=lpbField.getValue().toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            try {
                                entityMap.put(field.getName(), sdf.parse(dcsj));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else {
                            String value = StringUtils.deleteWhitespace(lpbField.getValue().toString());
                            entityMap.put(field.getName(), value);
                        }
                        break;
                    }
                }
            }
        }
        if (MapUtils.isNotEmpty(entityMap)) {
            convertEntityDO = JSONObject.parseObject(JSONObject.toJSONString(entityMap), convertEntityDO.getClass());
        }
        return convertEntityDO;
    }

    /**
     * 检查一个String类型的object是否为空
     * @param object
     * @return
     */
    public static Boolean validateObjectStringIsNotNull(Object object) {
        return object != null && StringUtils.isNotBlank(object.toString());
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param seq
     * @param strList
     * @return java.lang.String
     * @description 拼接字符串
     */
    public static String wmStr(String seq,List<String> strList){
        String result = "";
        if(CollectionUtils.isNotEmpty(strList)){
            StringBuilder strSb = new StringBuilder("");
            for(String str:strList){
                if(StringUtils.isNotBlank(str)){
                    strSb.append(str).append(seq);
                }
            }
            result = strSb.toString();
            if(result.endsWith(seq)){
                result = result.substring(0,result.length()-seq.length());
            }
        }
        return result;
    }
}
