package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityHelper;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Set;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/7/24
 * @description sql组织
 */
public class SqlUtils {
    private static final String RECORDMARK = "=#{record.";

    /**
     * @param jsonObject 更新属性对象
     *
     * @return sql语句（不包括where子句)
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取更新语句(更新空值)
     */
    public static String getBatchUpdateStatement(JSONObject jsonObject,String className){
        SQL sql = new SQL();
        Set<String> keySet = jsonObject.keySet();
        try {
            EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(Class.forName(className));
            sql.UPDATE(entityTable.getName());
            for (EntityHelper.EntityColumn column : entityTable.getEntityClassColumns()) {
                //更新不是ID的字段，因为根据主键查询的...更新后还是一样。
                String type = getjdbcType(column.getJavaType().getSimpleName());

                // 只更新JSONObject中定义的属性（例如JSON是由表单提交的更新字段）
                if (!column.isId() && keySet.contains(column.getProperty())) {
                    sql.SET(column.getColumn() + RECORDMARK + column.getProperty() + "," + type + "}");
                }
            }
        }catch (ClassNotFoundException e) {
            throw new AppException("更新实体数据异常！");
        }
        return sql.toString();


    }

    /**
     * lst 获取字段的类型
     *
     * @param type
     * @return
     */
    private static String getjdbcType(String type) {
        String str = "";
        if (StringUtils.equals(type.toUpperCase(), "DATE")) {
            str = "jdbcType=TIMESTAMP";
        }
        if (StringUtils.equals(type.toUpperCase(), "STRING")) {
            str = "jdbcType=VARCHAR";
        }
        if (StringUtils.equals(type.toUpperCase(), "INTEGER")) {
            str = "jdbcType=INTEGER";
        }
        if (StringUtils.equals(type.toUpperCase(), "DOUBLE")) {
            str = "jdbcType=DOUBLE";
        }
        if (StringUtils.equals(type.toUpperCase(), "FLOAT")) {
            str = "jdbcType=FLOAT";
        }
        return str;
    }

}
