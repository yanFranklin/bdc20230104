package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXgDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.support.mybatis.utils.AnnotationsUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 信息补录日志保存的方法
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/7/8 13:54
 */
public class LogCompareUtils {

    private LogCompareUtils() {
    }

    /**
     * 初始化 日志 data 数据
     *
     * @param bdcYwxxDTOBefore 业务信息对象修改前
     * @param bdcYwxxDTOAfter  业务信息对象修改后
     * @param xmid             项目 id
     * @return {Map} 返回日志保存需要的对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    public static Map<String, Object> initData(String xmid, BdcYwxxDTO bdcYwxxDTOBefore, BdcYwxxDTO bdcYwxxDTOAfter) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put(CommonConstantUtils.XXBL_AFTER, RSAEncryptUtils.encrypt(JSON.toJSONString(bdcYwxxDTOAfter)));
        data.put(CommonConstantUtils.XXBL_BEFORE, RSAEncryptUtils.encrypt(JSON.toJSONString(bdcYwxxDTOBefore)));
        data.put(CommonConstantUtils.XXBL_PARAMCH, xmid);
        String change = change(bdcYwxxDTOBefore, bdcYwxxDTOAfter, xmid);
        data.put(CommonConstantUtils.XXBL_CHANGE, RSAEncryptUtils.encrypt(change));
        data.put(CommonConstantUtils.VIEW_TYPE_NAME, "信息补录更新信息");
        data.put("eventName", CommonConstantUtils.XXBL);
        return data;
    }

    public static Map<String, String> initDataString(String xmid, BdcYwxxDTO bdcYwxxDTOBefore, BdcYwxxDTO bdcYwxxDTOAfter) throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put(CommonConstantUtils.XXBL_AFTER, RSAEncryptUtils.encrypt(JSON.toJSONString(bdcYwxxDTOAfter)));
        data.put(CommonConstantUtils.XXBL_BEFORE, RSAEncryptUtils.encrypt(JSON.toJSONString(bdcYwxxDTOBefore)));
        data.put(CommonConstantUtils.XXBL_PARAMCH, xmid);
        String change = change(bdcYwxxDTOBefore, bdcYwxxDTOAfter, xmid);
        data.put(CommonConstantUtils.XXBL_CHANGE, RSAEncryptUtils.encrypt(change));
        data.put(CommonConstantUtils.VIEW_TYPE_NAME, "信息补录更新信息");
        data.put("eventName", CommonConstantUtils.XXBL);
        return data;
    }

    /**
     * 返回 BdcYwxxDTO 变化前后的情况，返回结果为字符串
     *
     * @param ywxxBefore 业务信息修改前
     * @param ywxxAfter  业务信息修改后
     * @param xmid       项目 id
     * @return {String} 两个对象之间变化的字符串
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    public static String change(BdcYwxxDTO ywxxBefore, BdcYwxxDTO ywxxAfter, String xmid) throws Exception {
        Field[] fields = BdcYwxxDTO.class.getDeclaredFields();
        StringBuilder change = new StringBuilder();
        for (Field field : fields) {
            // 取消域的访问权限
            field.setAccessible(true);
            // 返回指定对象上此 Field 表示的字段的值。
            Object objBefore = field.get(ywxxBefore);
            Object objAfter = field.get(ywxxAfter);
            if (objAfter != null || objBefore != null) {
                String objName = getFieldName(field);
                // 判断是否是 list 类型的数据
                if (field.getType() != List.class) {
                    change.append(compareNull(objBefore, objAfter, xmid, objName));
                } else {
                    List beforelist = (List) objBefore;
                    List afterlist = (List) objAfter;
                    Method keyMethod;
                    if (CollectionUtils.isEmpty(beforelist) || CollectionUtils.isEmpty(afterlist)) {
                        // 如果 before 和 after 有一个为空直接记录添加或删除了 id 和数据类型
                        change.append(compareNull(beforelist, afterlist, xmid, objName));
                    } else {
                        HashMap<String, Object> beforeMap = Maps.newHashMap();
                        // 获取主键的方法 （if 中已经判断了 afterlist 和 before 均不为空）
                        keyMethod = AnnotationsUtils.getAnnotationsName(afterlist.get(0));
                        // 将 before 数据存到 map 中用于对比分析
                        for (Object o : beforelist) {
                            String id = keyMethod.invoke(o).toString();
                            if (StringUtils.isBlank(id)) {
                                throw new AppException(objName + "主键丢失异常");
                            } else {
                                beforeMap.put(id, o);
                            }
                        }
                        // 遍历 after 集合对比数据，判断数据是否是新增或删除
                        for (Object o : afterlist) {
                            String afterid = keyMethod.invoke(o).toString();
                            Object temp = beforeMap.get(afterid);
                            if (temp == null) {
                                // beforeMap 中不存在对应 id 表示属于新增的 id
                                change.append("列表中新增 id 为 ")
                                        .append(afterid)
                                        .append(" 的")
                                        .append(objName)
                                        .append("对象\n");
                            } else {
                                // 存在则对于两个对象进行对比，然后删除 beforeMap 中对应的 key
                                change.append(compare(beforeMap.get(afterid), o));
                                beforeMap.remove(afterid);
                            }
                        }

                        // beforeMap 中剩下的键值对表示被删除了的数据
                        if (beforeMap.size() > 0) {
                            // 获取表名
                            for (Map.Entry<String, Object> entry : beforeMap.entrySet()) {
                                change.append("列表中删除 id 为 ")
                                        .append(entry.getKey())
                                        .append(" 的")
                                        .append(objName)
                                        .append("对象\n");
                            }
                        }
                    }
                }
            }
        }
        return change.toString();
    }

    /**
     * 对于 compare 方法的进一步封装，避免对 null 对象进行遍历
     *
     * @param before  修改前对象
     * @param after   修改后对象
     * @param xmid    项目 id
     * @param objName 对象名
     * @return {String} 两个对象之间变化的字符串
     */
    private static String compareNull(Object before, Object after, String xmid, String objName) throws Exception {
        String change;
        if (before == null) {
            change = "【数据项】新增 " + objName + "，项目 id 为：" + xmid + "\n";
        } else if (after == null) {
            change = "【数据项】删除 " + objName + "数据，项目 id 为：" + xmid + "\n";
        } else {
            change = compare(before, after);
        }
        return change;
    }

    /**
     * 比较两个对象变化的情况
     *
     * @param before 修改前的对象
     * @param after  修改后的对象
     * @return {String} 两个对象之间变化的字符串
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private static String compare(Object before, Object after) throws Exception {
        StringBuilder result = new StringBuilder();
        String beforeStr;
        String afterStr;
        Object objBefore;
        Object objAfter;
        // 获取所有的属性，包括私有
        Field[] fields = before.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 取消域的访问权限
            field.setAccessible(true);
            // 返回指定对象上此 Field 表示的字段的值。
            objBefore = field.get(before);
            objAfter = field.get(after);
            // 获取到属性对应的值
            beforeStr = objBefore == null ? "空" : objBefore.toString();
            afterStr = objAfter == null ? "空" : objAfter.toString();
            // 判断属性是否相同，不同则存入到 change 字段
            if (!StringUtils.equals(afterStr, beforeStr)) {
                String value = getFieldName(field);
                result.append(value).append(" 由：")
                        .append(beforeStr)
                        .append(" 修改为：")
                        .append(afterStr).append("；\n");
            }

        }
        if (StringUtils.isNotBlank(result.toString())) {
            return getClassName(before) + ":\n" + result.toString();
        }
        return "";
    }


    /**
     * 获取 field 属性 对应的 中文名称 <br/>
     * <p>
     * 取 field 注解 ApiModelProperty 中的 value 值 <br/>
     * 获取不到以上值，返回 field 的 name 值
     *
     * @param field 属性
     * @return 属性对应的中文名，如果注解中不包含返回 field 的 name
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    public static String getFieldName(Field field) {
        String fieldName = field.getName();
        ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
        if (apiModelProperty != null && StringUtils.isNotBlank(apiModelProperty.value())) {
            fieldName = apiModelProperty.value();
        }
        return fieldName;
    }

    /**
     * 获取 对象 对应的 中文名称 <br>
     * 取 field 注解 ApiModel 中的 description 值 <br/>
     * 如果注解中不包含 description 返回类的 simpleName
     *
     * @param object 对象
     * @return 类对应的中文名，如果注解中不包含 description 返回类的 simpleName
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    public static String getClassName(Object object) {
        String className = object.getClass().getSimpleName();
        if (object instanceof BdcQl) {
            className = "权利信息";
        }
        ApiModel apiModelProperty = object.getClass().getAnnotation(ApiModel.class);
        if (apiModelProperty != null && StringUtils.isNotBlank(apiModelProperty.description())) {
            className = apiModelProperty.description();
        }
        if (object instanceof BdcQlrDO) {
            className = ((BdcQlrDO) object).getQlrmc();
        }
        return className;
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 组织修改信息
      */
    public static Map<String, Object> initBdcXgData(String param, Object xgxxBefore, Object xgxxAfter,String viewTypeName) throws Exception {
        Map<String, Object> data = new HashMap<>();
        BdcXgDTO bdcXgDTO =new BdcXgDTO();
        bdcXgDTO.setParam(param);
        bdcXgDTO.setViewTypeName(viewTypeName);
        bdcXgDTO.setBefore(RSAEncryptUtils.encrypt(JSON.toJSONString(xgxxBefore)));
        bdcXgDTO.setAfter(RSAEncryptUtils.encrypt(JSON.toJSONString(xgxxAfter)));
        data.putAll(Object2MapUtils.object2MapExceptNull(bdcXgDTO));
        return data;
    }
}
