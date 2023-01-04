package cn.gtmap.realestate.engine.util;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlCsDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzSjlDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.EntityException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.engine.core.enums.BdcGzSjlCsEnum;
import cn.gtmap.realestate.engine.core.enums.BdcGzSjllyEnum;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/5/4
 * @description 数据处理
 */
@Service
public class DataUtil {
    private static final String PARAM_NO_FOUND = "数据流执行发生异常，原因：数据流参数名称未配置！";
    private static final String PARAM_SET_EX = "数据流执行发生异常，原因：数据流参数格式配置错误！ ";

    /**
     * 数据流动态参数处理策略
     * 1、true ： 当前数据流动参从上一个数据流没有获取到值，抛出异常
     * 2、false : 当前数据流动参从上一个数据流没有获取到值，忽略，默认返回空字符
     */
    @Value("${dataflow.isNeedException:true}")
    private Boolean isNeedException;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param sjlResultMap 数据流执行结果集
     * @param sjlcsmc 数据流参数名称
     * @return {String} 参数值
     * @description 解析获取数据流参数值
     */
    public <T> String getParamValue(Map<String, T> sjlResultMap, String sjlcsmc, String code){
        if(StringUtils.isBlank(sjlcsmc)){
            throw new AppException(ErrorCode.RULE_NO_PARAM_EX, PARAM_NO_FOUND);
        }

        if(sjlcsmc.contains(Constants.STR_POINT)){
            return this.getObjAttrVal(sjlResultMap, sjlcsmc, code);
        }
        else if(sjlcsmc.contains(Constants.DOU_GREATER)){
            return this.getListItemVal(sjlResultMap, sjlcsmc, code);
        }
        else {
            return "'" + sjlcsmc + "'";
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param sjlResultMap 数据流执行结果集
     * @param sjlcsmc 数据流参数名称
     * @return {String} 参数值
     * @description 解析Map获取指定字段值
     *   说明：
     *     数据流参数配置格式：xmDO.bdcdyh，即从实体中取属性
     */
    public <T> String getObjAttrVal(Map<String, T> sjlResultMap, String sjlcsmc, String code) {
        String dtoName = sjlcsmc.split(Constants.ESC_POINT)[0];
        T dto = (T) MapUtils.getObject(sjlResultMap, dtoName);
        if(null == dto){
           return this.resolveNullValue(sjlcsmc, code);
        }

        try {
            Map<String, Object> map = (Map<String, Object>) dto;
            Object val = map.get(sjlcsmc.split(Constants.ESC_POINT)[1]);
            if(null == val) {
                return resolveNullValue(sjlcsmc, code);
            }

            // SQL默认前后加引号，服务不加
            if(BdcGzSjllyEnum.SQL.getCode().equals(code)){
                return "'" + val + "'";
            } else {
                return String.valueOf(val);
            }
        }catch (Exception e){
            throw new AppException(ErrorCode.RULE_PARAM_SET_EX,  PARAM_SET_EX + sjlcsmc);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param sjlResultMap 数据流执行结果集
     * @param sjlcsmc 数据流参数名称
     * @return {String} 参数值
     * @description 解析List<Map>获取指定字段值
     *   说明：
     *     数据流参数配置格式：bdcdyhList>>bdcdyh，即从SQL结果集中取字段值
     */
    public <T> String getListItemVal(Map<String, T> sjlResultMap, String sjlcsmc, String code) {
        String objName = sjlcsmc.split(Constants.DOU_GREATER)[0];
        T obj = (T) MapUtils.getObject(sjlResultMap, objName);
        if(null == obj){
            return this.resolveNullValue(sjlcsmc, code);
        }

        List<Map<String, Object>> list;
        try {
            list = (List<Map<String, Object>>) obj;
        }catch (Exception e){
            throw new AppException(ErrorCode.RULE_PARAM_SET_EX, PARAM_SET_EX + sjlcsmc);
        }

        if(CollectionUtils.isEmpty(list)){
            return this.resolveNullValue(sjlcsmc, code);
        }

        String attr = sjlcsmc.split(Constants.DOU_GREATER)[1].toUpperCase();

        if(BdcGzSjllyEnum.SQL.getCode().equals(code)){
            // 传入SQL中一般作为 in 后入参，将多个数据拼接成 a,b,c 格式
            StringBuilder builder = new StringBuilder(list.size());
            for(Map<String, Object> item : list){
                Object val = MapUtils.getObject(item, attr);
                if(null == val || StringUtils.isBlank(String.valueOf(val))){
                    continue;
                }
                builder.append("'").append(val).append("'").append(",");
            }
            String str = builder.toString();
            if(StringUtils.isBlank(str)){
                return this.resolveNullValue(sjlcsmc, code);
            }
            return str.substring(0, str.length() - 1);
        } else {
            // 传入服务中直接取第一个数据，一般不会以 a,b,c格式传值
            Map<String, Object> item = list.get(0);
            if(!item.containsKey(attr)) {
                return resolveNullValue(sjlcsmc, code);
            }
            return String.valueOf(MapUtils.getObject(item, attr));
        }
    }

    /**
     * 处理动参空值异常情况
     * @param sjlcsmc 数据流名称
     */
    private String resolveNullValue(String sjlcsmc, String code){
        if(isNeedException){
            throw new AppException(ErrorCode.RULE_PARAM_NULL_ERROR, "当前规则数据流（" + sjlcsmc + "）参数值为空!");
        }

        if(BdcGzSjllyEnum.SQL.getCode().equals(code)){
            return "'" + CommonConstantUtils.BDC_GZ_SJL_RC_MRZ + "'";
        } else {
            return CommonConstantUtils.BDC_GZ_RPC_RC_MRZ;
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzSjlDTO  数据流
     * @param sjlResultMap 数据流执行结果集
     * @return {Object} 传递参数
     * @description 获取需要提交的实体
     */
    public static <T> Object getPostedObj(BdcGzSjlDTO bdcGzSjlDTO, Map<String, T> sjlResultMap) {
        if(CollectionUtils.isEmpty(bdcGzSjlDTO.getBdcGzSjlCsDOList())){
            return null;
        }

        for(BdcGzSjlCsDO bdcGzSjlCsDO : bdcGzSjlDTO.getBdcGzSjlCsDOList()){
            if(BdcGzSjlCsEnum.ST.getCode().equals(bdcGzSjlCsDO.getSjlcszl())){
                String sjlcsmc = bdcGzSjlCsDO.getSjlcsmc();

                if(sjlcsmc.contains(Constants.DOU_GREATER)) {
                    // 处理SQL传值
                    return DataUtil.parseSqlResult(sjlcsmc, sjlResultMap);
                } else {
                    // 优先从外部传入的参数中取值
                    if(null != bdcGzSjlCsDO.getSjlcsz()){
                        Object obj = bdcGzSjlCsDO.getSjlcsz();

                        // 处理JSON格式对象参数
                       if(null != obj && obj instanceof String){
                            String objStr = String.valueOf(obj);
                            if(StringUtils.isBlank(objStr)){
                                return null;
                            } else if(JSON.isValid(objStr)){
                                obj = JSON.parse(objStr);
                            }
                        }

                        return obj;
                    }
                    // 没有从之前数据流结果中取值
                    return MapUtils.getObject(sjlResultMap, sjlcsmc);
                }
            }
        }
        return null;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param sjlcsmc  实体参数名称
     * @param sjlResultMap 数据流执行结果集
     * @return {Object} 传递参数
     * @description 解析SQL结果实体传参到服务
     */
    public static <T> Object parseSqlResult(String sjlcsmc, Map<String, T> sjlResultMap){
        String objName = sjlcsmc.split(Constants.DOU_GREATER)[0];
        T obj = (T) MapUtils.getObject(sjlResultMap, objName);
        if (null == obj) {
            throw new EntityException("");
        }

        List<Map<String, Object>> list;
        try {
            list = (List<Map<String, Object>>) obj;
        } catch (Exception e) {
            throw new AppException(ErrorCode.RULE_PARAM_SET_EX, PARAM_SET_EX + sjlcsmc);
        }

        if (CollectionUtils.isEmpty(list)) {
            throw new EntityException("");
        }

        String attr = sjlcsmc.split(Constants.DOU_GREATER)[1].toUpperCase();
        List<Object> resList = new ArrayList<>(list.size());
        for (Map<String, Object> item : list) {
            resList.add(MapUtils.getObject(item, attr));
        }

        return resList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param tipInfo  提示信息
     * @param queryResult 数据流执行结果
     * @param paramsMap 外部参数
     * @description  处理单个提示信息
     */
    public static  <T> String resolveTipInfo(String tipInfo, Map<String, T> queryResult, Map<String, T> paramsMap){
        if(StringUtils.isBlank(tipInfo)){
            return null;
        }

        if(tipInfo.contains("#{") && MapUtils.isNotEmpty(paramsMap)){
            // 替换提示信息中的参数变量，例如：'当前不动产单元 #{bdcdyh}存在抵押；占位符：#{入参变量名称}'
            for(Map.Entry<String, T> entry : paramsMap.entrySet()){
                tipInfo = tipInfo.replaceAll("\\#\\{" + entry.getKey() + "\\}", String.valueOf(entry.getValue()));
            }
        }

        if(tipInfo.contains("${") && MapUtils.isNotEmpty(queryResult)){
            // 替换提示信息中的结果变量；'占位符：${结果变量名称}'
            for(Map.Entry<String, T> entry : queryResult.entrySet()){
                tipInfo = tipInfo.replaceAll("\\$\\{" + entry.getKey() + "\\}", String.valueOf(entry.getValue()));
            }
        }

        if(tipInfo.contains("&{") && MapUtils.isNotEmpty(queryResult)){
            // 提示信息从数据流执行结果中取值，例如数据流SQL：select xmid from bdc_xm where ... ，提示信息：项目&{xmxx.xmid}存在
            Pattern pattern = compile("&\\{[a-zA-Z0-9.>]+\\}");
            Matcher matcher = pattern.matcher(tipInfo);
            Set<String> variables = new HashSet<>();
            while (matcher.find()){
                variables.add(matcher.group().replace("&{", "").replace("}", ""));
            }

            if(CollectionUtils.isNotEmpty(variables)) {
                for(String variable : variables) {
                    String value = parseVariableValue(queryResult, variable);
                    if(StringUtils.isNotBlank(value)) {
                        tipInfo = tipInfo.replaceAll("&\\{" + variable + "\\}", value);
                    }
                }
            }
        }

        return tipInfo;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param queryResult 数据流执行结果
     * @param variable 变量名称
     * @return {String} 变量值
     * @description 从数据流执行结果中获取指定变量值
     */
    public static  <T> String parseVariableValue(Map<String, T> queryResult, String variable){
        if(StringUtils.isBlank(variable)){
            throw new AppException(ErrorCode.RULE_NO_PARAM_EX, PARAM_NO_FOUND);
        }

        if(variable.contains(Constants.STR_POINT)){
            return parseObjVariableValue(queryResult, variable);
        }
        else if(variable.contains(Constants.DOU_GREATER)){
            return parseListVariableValue(queryResult, variable);
        }
        else {
           if(queryResult.keySet().contains(variable)) {
               return String.valueOf(queryResult.get(variable));
           } else {
               return "";
           }
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param queryResult 数据流执行结果
     * @param variable 变量名称
     * @return {String} 变量值
     * @description 解析Map/实体获取指定属性值，配置格式：xmDO.bdcdyh
     */
    public static <T> String parseObjVariableValue(Map<String, T> queryResult, String variable) {
        try {
            String dtoName = variable.split(Constants.ESC_POINT)[0];
            T dto = (T) MapUtils.getObject(queryResult, dtoName);
            if(null == dto){
                return "";
            }

            Map<String, Object> map = (Map<String, Object>) dto;
            String name = variable.split(Constants.ESC_POINT)[1];
            Object val = getNotNullValue(map.get(name), map.get(name.toLowerCase()), map.get(name.toUpperCase()));
            return null == val ? "" : String.valueOf(val);
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param queryResult 数据流执行结果
     * @param variable 变量名称
     * @return {String} 变量值
     * @description 解析List<Map>获取指定字段值，配置格式：bdcdyhList>>bdcdyh
     */
    public static <T> String parseListVariableValue(Map<String, T> queryResult, String variable) {
        try {
            String objName = variable.split(Constants.DOU_GREATER)[0];
            T obj = (T) MapUtils.getObject(queryResult, objName);
            if(null == obj){
                return "";
            }

            List<Map<String, Object>> list = (List<Map<String, Object>>) obj;
            if(CollectionUtils.isEmpty(list)){
                return "";
            }

            String name = variable.split(Constants.DOU_GREATER)[1];
            StringBuilder builder = new StringBuilder(list.size());
            for(Map<String, Object> map : list){
                Object val = getNotNullValue(map.get(name), map.get(name.toLowerCase()), map.get(name.toUpperCase()));
                if(null == val || StringUtils.isBlank(String.valueOf(val))){
                    continue;
                }
                builder.append(val).append(",");
            }
            String str = builder.toString();
            return StringUtils.isBlank(str) ? "" : str.substring(0, str.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static Object getNotNullValue(Object... values) {
        for(Object value : values) {
            if(null != value) {
                return value;
            }
        }
        return null;
    }

    private DataUtil(){
        // for sonar check
    }
}
