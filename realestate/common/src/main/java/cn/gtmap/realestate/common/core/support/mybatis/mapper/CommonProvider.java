package cn.gtmap.realestate.common.core.support.mybatis.mapper;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.reflection.MetaObject;

import java.util.*;

/**
 * @author liuzh
 * @author hanxue
 */
public class CommonProvider extends BaseProvider {
    private static final String RECORDMARK = "=#{record.";
    private static final String RECORD = "record";

    /**
     * 查询，入参可以是Entity.class或new Entity()
     *
     * @param params
     * @return
     */
    public String select(final Map<String, Object> params) {
        SQL sql = new SQL();
        Object entity = getEntity(params);
        Class<?> entityClass = getEntityClass(params);
        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        sql.SELECT(EntityHelper.getAllColumns(entityClass));
        sql.FROM(entityTable.getName());
        if (entity != null) {
            final MetaObject metaObject = MapperTemplate.forObject(entity);
            for (EntityHelper.EntityColumn column : entityTable.getEntityClassColumns()) {
                Object value = metaObject.getValue(column.getProperty());
                if (value == null) {
                    continue;
                } else if (column.getJavaType().equals(String.class)) {
                    if (isNotEmpty((String) value)) {
                        sql.WHERE(column.getColumn() + RECORDMARK + column.getProperty() + "}");
                    }
                } else {
                    sql.WHERE(column.getColumn() + RECORDMARK + column.getProperty() + "}");
                }
            }
        }
        StringBuilder orderBy = new StringBuilder();
        for (EntityHelper.EntityColumn column : entityTable.getEntityClassColumns()) {
            if (column.getOrderBy() != null) {
                orderBy.append(column.getColumn()).append(" ").append(column.getOrderBy()).append(",");
            }
        }
        if (orderBy.length() > 0) {
            sql.ORDER_BY(orderBy.substring(0, orderBy.length() - 1));
        }

        return sql.toString();
    }

    /**
     * 查询，入参可以是Entity.class或new Entity()
     *
     * @param params
     * @return
     */
    public String count(final Map<String, Object> params) {
        SQL sql = new SQL();

        Object entity = getEntity(params);
        Class<?> entityClass;
        if (entity instanceof Class<?>) {
            entityClass = (Class<?>) entity;
            entity = null;
        } else {
            entityClass = getEntityClass(params);
        }
        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        sql.SELECT("count(*)");
        sql.FROM(entityTable.getName());
        if (entity != null) {
            MetaObject metaObject = MapperTemplate.forObject(entity);
            for (EntityHelper.EntityColumn column : entityTable.getEntityClassColumns()) {
                Object value = metaObject.getValue(column.getProperty());
                if (value == null) {
                    continue;
                } else if (column.getJavaType().equals(String.class)) {
                    if (isNotEmpty((String) value)) {
                        sql.WHERE(column.getColumn() + RECORDMARK + column.getProperty() + "}");
                    }
                } else {
                    sql.WHERE(column.getColumn() + RECORDMARK + column.getProperty() + "}");
                }
            }
        }

        return sql.toString();
    }

    /**
     * 通过主键查询，主键字段都不能为空
     *
     * @param params
     * @return
     */
    public String selectByPrimaryKey(final Map<String, Object> params) {
        SQL sql = new SQL();

        Object entity = getEntity(params);
        Class<?> entityClass = getEntityClass(params);
        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        sql.SELECT(EntityHelper.getAllColumns(entityClass));
        sql.FROM(entityTable.getName());
        if (entityTable.getEntityClassPKColumns().size() == 1) {
            EntityHelper.EntityColumn column = entityTable.getEntityClassPKColumns().iterator().next();
            notNullKeyProperty(column.getProperty(), entity);
            sql.WHERE(column.getColumn() + "=#{key}");
        } else {
            MetaObject metaObject = MapperTemplate.forObject(entity);
            for (EntityHelper.EntityColumn column : entityTable.getEntityClassPKColumns()) {
                Object value = metaObject.getValue(column.getProperty());
                notNullKeyProperty(column.getProperty(), value);
                sql.WHERE(column.getColumn() + "=#{key." + column.getProperty() + "}");
            }
        }

        return sql.toString();
    }

    /**
     * 新增
     *
     * @param params
     * @return
     */
    public String insert(final Map<String, Object> params) {
        SQL sql = new SQL();

        Class<?> entityClass = getEntityClass(params);
        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        sql.INSERT_INTO(entityTable.getName());
        for (EntityHelper.EntityColumn column : entityTable.getEntityClassColumns()) {
            sql.VALUES(column.getColumn(), "#{record." + column.getProperty() + "}");
        }

        return sql.toString();
    }

    /**
     * 新增非空字段，空字段可以使用表的默认值
     *
     * @param params
     * @return
     */
    public String insertSelective(final Map<String, Object> params) {
        SQL sql = new SQL();

        Object entity = getEntity(params);
        Class<?> entityClass = getEntityClass(params);
        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        MetaObject metaObject = MapperTemplate.forObject(entity);
        sql.INSERT_INTO(entityTable.getName());
        for (EntityHelper.EntityColumn column : entityTable.getEntityClassColumns()) {
            Object value = metaObject.getValue(column.getProperty());
            if (column.isId() || value != null) {
                sql.VALUES(column.getColumn(), "#{record." + column.getProperty() + "}");
            }
        }

        return sql.toString();
    }

    /**
     * 新增非空字段，空字段可以使用表的默认值
     *
     * @param params
     * @return
     */
    public String insertBatchSelective(final Map<String, Object> params) {
        String insertBatchSql = "";
        List<String> columns = new ArrayList();
        List<String> values = new ArrayList();
        Class<?> entityClass = getEntityClass(params);
        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        insertBatchSql += "insert into " + entityTable.getName();
        if (params.containsKey(RECORD) && params.get(RECORD) != null && (params.get(RECORD) instanceof List || params.get(RECORD) instanceof ArrayList)) {
            List list = (List) params.get(RECORD);
            for (int i = 0; i < list.size(); i++) {
                StringBuffer sbf = new StringBuffer("select ");
                int j = 0;
                for (EntityHelper.EntityColumn column : entityTable.getEntityClassColumns()) {
                    j += 1;
                    if (i == 0) {
                        columns.add(column.getColumn());
                    }
                    String type = getjdbcType(column.getJavaType().getSimpleName());
                    if (j == entityTable.getEntityClassColumns().size()) {
                        sbf.append("#{record[" + i + "]." + column.getProperty() + "," + type + "}");
                    } else {
                        sbf.append("#{record[" + i + "]." + column.getProperty() + "," + type + "},");
                    }
                }
                sbf.append(" from dual");
                values.add(sbf.toString());
            }
        }
        insertBatchSql += " " + batchSql(columns, ",");
        insertBatchSql += " " + batchSql(values, "union all");
        return insertBatchSql;
    }

    /**
     * lst 拼接批量插入的sql
     *
     * @param list
     * @param split
     * @return
     */
    private String batchSql(List<?> list, String split) {
        StringBuffer sbf = new StringBuffer("(");
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                sbf.append(list.get(i)).append(" ");
                if (list.size() - i != 1) {
                    sbf.append(split).append(" ");
                }
            }
        }
        sbf.append(")");
        return sbf.toString();
    }

    /**
     * lst 获取字段的类型
     *
     * @param type
     * @return
     */
    private String getjdbcType(String type) {
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


    /**
     * 通过查询条件删除
     *
     * @param params
     * @return
     */
    public String delete(final Map<String, Object> params) {
        SQL sql = new SQL();

        Object entity = getEntity(params);
        Class<?> entityClass = getEntityClass(params);
        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        MetaObject metaObject = MapperTemplate.forObject(entity);
        sql.DELETE_FROM(entityTable.getName());
        boolean hasValue = false;
        for (EntityHelper.EntityColumn column : entityTable.getEntityClassColumns()) {
            Object value = metaObject.getValue(column.getProperty());
            if (value == null) {
                continue;
            } else if (column.getJavaType().equals(String.class)) {
                if (isNotEmpty((String) value)) {
                    sql.WHERE(column.getColumn() + RECORDMARK + column.getProperty() + "}");
                    hasValue = true;
                }
            } else {
                sql.WHERE(column.getColumn() + RECORDMARK + column.getProperty() + "}");
                hasValue = true;
            }
        }
        if (!hasValue) {
            throw new UnsupportedOperationException("delete方法不支持删除全表的操作!");
        }

        return sql.toString();
    }

    /**
     * 通过主键删除
     *
     * @param params
     * @return
     */
    public String deleteByPrimaryKey(final Map<String, Object> params) {
        SQL sql = new SQL();

        Object entity = getEntity(params);
        Class<?> entityClass = getEntityClass(params);
        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        sql.DELETE_FROM(entityTable.getName());
        if (entityTable.getEntityClassPKColumns().size() == 1) {
            EntityHelper.EntityColumn column = entityTable.getEntityClassPKColumns().iterator().next();
            notNullKeyProperty(column.getProperty(), entity);
            sql.WHERE(column.getColumn() + "=#{key}");
        } else {
            MetaObject metaObject = MapperTemplate.forObject(entity);
            for (EntityHelper.EntityColumn column : entityTable.getEntityClassPKColumns()) {
                Object value = metaObject.getValue(column.getProperty());
                notNullKeyProperty(column.getProperty(), value);
                sql.WHERE(column.getColumn() + "=#{key." + column.getProperty() + "}");
            }
        }

        return sql.toString();
    }

    /**
     * 通过主键更新
     *
     * @param params
     * @return
     */
    public String updateByPrimaryKey(final Map<String, Object> params) {
        SQL sql = new SQL();

        Object entity = getEntity(params);
        Class<?> entityClass = getEntityClass(params);
        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        MetaObject metaObject = MapperTemplate.forObject(entity);
        sql.UPDATE(entityTable.getName());
        for (EntityHelper.EntityColumn column : entityTable.getEntityClassColumns()) {
            //更新不是ID的字段，因为根据主键查询的...更新后还是一样。
            if (!column.isId()) {
                sql.SET(column.getColumn() + RECORDMARK + column.getProperty() + "}");
            }
        }
        if (entityTable.getEntityClassPKColumns().size() == 1) {
            EntityHelper.EntityColumn column = entityTable.getEntityClassPKColumns().iterator().next();
            notNullKeyProperty(column.getProperty(), metaObject.getValue(column.getProperty()));
            sql.WHERE(column.getColumn() + RECORDMARK + column.getProperty() + "}");
        } else {
            for (EntityHelper.EntityColumn column : entityTable.getEntityClassPKColumns()) {
                Object value = metaObject.getValue(column.getProperty());
                notNullKeyProperty(column.getProperty(), value);
                sql.WHERE(column.getColumn() + RECORDMARK + column.getProperty() + "}");
            }
        }

        return sql.toString();
    }

    /**
     * lst 通过主键更新
     * null也会更新
     *
     * @param params
     * @return
     */
    public String updateByPrimaryKeyNull(final Map<String, Object> params) {
        SQL sql = new SQL();

        Object entity = getEntity(params);
        Class<?> entityClass = getEntityClass(params);
        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        MetaObject metaObject = MapperTemplate.forObject(entity);
        sql.UPDATE(entityTable.getName());
        for (EntityHelper.EntityColumn column : entityTable.getEntityClassColumns()) {
            //更新不是ID的字段，因为根据主键查询的...更新后还是一样。
            String type = getjdbcType(column.getJavaType().getSimpleName());
            if (!column.isId()) {
                sql.SET(column.getColumn() + RECORDMARK + column.getProperty() + "," + type + "}");
            }
        }
        if (entityTable.getEntityClassPKColumns().size() == 1) {
            EntityHelper.EntityColumn column = entityTable.getEntityClassPKColumns().iterator().next();
            notNullKeyProperty(column.getProperty(), metaObject.getValue(column.getProperty()));
            sql.WHERE(column.getColumn() + RECORDMARK + column.getProperty() + "}");
        } else {
            for (EntityHelper.EntityColumn column : entityTable.getEntityClassPKColumns()) {
                Object value = metaObject.getValue(column.getProperty());
                notNullKeyProperty(column.getProperty(), value);
                sql.WHERE(column.getColumn() + RECORDMARK + column.getProperty() + "}");
            }
        }

        return sql.toString();
    }

    /**
     * 通过主键更新非空字段
     *
     * @param params
     * @return
     */
    public String updateByPrimaryKeySelective(final Map<String, Object> params) {
        SQL sql = new SQL();

        Object entity = getEntity(params);
        Class<?> entityClass = getEntityClass(params);
        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        MetaObject metaObject = MapperTemplate.forObject(entity);
        sql.UPDATE(entityTable.getName());
        for (EntityHelper.EntityColumn column : entityTable.getEntityClassColumns()) {
            Object value = metaObject.getValue(column.getProperty());
            //更新不是ID的字段，因为根据主键查询的...更新后还是一样。
            if (value != null && !column.isId()) {
                sql.SET(column.getColumn() + RECORDMARK + column.getProperty() + "}");
            }
        }
        if (entityTable.getEntityClassPKColumns().size() == 1) {
            EntityHelper.EntityColumn column = entityTable.getEntityClassPKColumns().iterator().next();
            notNullKeyProperty(column.getProperty(), metaObject.getValue(column.getProperty()));
            sql.WHERE(column.getColumn() + RECORDMARK + column.getProperty() + "}");
        } else {
            for (EntityHelper.EntityColumn column : entityTable.getEntityClassPKColumns()) {
                notNullKeyProperty(column.getProperty(), metaObject.getValue(column.getProperty()));
                sql.WHERE(column.getColumn() + RECORDMARK + column.getProperty() + "}");
            }
        }

        return sql.toString();
    }

    public String countByExample(final Map<String, Object> params) {
        SQL sql = new SQL();

        MetaObject example = getExample(params);
        Class<?> entityClass = getEntityClass(params);
        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        sql.SELECT("count(*)");
        sql.FROM(entityTable.getName());
        applyWhere(sql, example);

        return sql.toString();
    }

    public String deleteByExample(final Map<String, Object> params) {
        SQL sql = new SQL();

        MetaObject example = getExample(params);
        Class<?> entityClass = getEntityClass(params);
        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        sql.DELETE_FROM(entityTable.getName());
        applyWhere(sql, example);

        return sql.toString();
    }

    public String selectByExample(final Map<String, Object> params) {
        SQL sql = new SQL();

        MetaObject example = getExample(params);
        Class<?> entityClass = getEntityClass(params);
        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        sql.SELECT(EntityHelper.getAllColumns(entityClass));
        sql.FROM(entityTable.getName());
        applyWhere(sql, example);
        applyOrderBy(sql, example);

        return sql.toString();
    }

    public String updateByExampleSelective(final Map<String, Object> params) {
        SQL sql = new SQL();

        Object entity = getEntity(params);
        MetaObject example = getExample(params);
        Class<?> entityClass = getEntityClass(params);
        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        MetaObject metaObject = MapperTemplate.forObject(entity);
        sql.UPDATE(entityTable.getName());
        for (EntityHelper.EntityColumn column : entityTable.getEntityClassColumns()) {
            Object value = metaObject.getValue(column.getProperty());
            //更新不是ID的字段，因为根据主键查询的...更新后还是一样。
            if (value != null) {
                sql.SET(column.getColumn() + RECORDMARK + column.getProperty() + "}");
            }
        }
        applyWhere(sql, example);

        return sql.toString();
    }

    public String updateByExample(final Map<String, Object> params) {
        SQL sql = new SQL();

        MetaObject example = getExample(params);
        Class<?> entityClass = getEntityClass(params);
        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        sql.UPDATE(entityTable.getName());
        for (EntityHelper.EntityColumn column : entityTable.getEntityClassColumns()) {
            //更新不是ID的字段，因为根据主键查询的...更新后还是一样。
            if (!column.isId()) {
                sql.SET(column.getColumn() + RECORDMARK + column.getProperty() + "}");
            }
        }
        applyWhere(sql, example);

        return sql.toString();
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   params 保存实体参数集合
     * @return  {int} 更新数据记录数量
     * @description
     *     <p>
     *         获取更新实体对象部分属性SQL  <br>
     *         1、json字符串为要更新的属性 <br>
     *         2、json中属性为 null值的会更新为null值
     *     </p>
     */
    public String updateByJsonEntity(final Map<String, Object> params){
        SQL sql = new SQL();

        Object entity = getEntity(params);
        Class<?> entityClass = getEntityClass(params);

        // 获取实体属性字段名称集合
        JSONObject jsonObject = getJSONObject(params);
        Set<String> keySet = jsonObject.keySet();

        EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        MetaObject metaObject = MapperTemplate.forObject(entity);
        sql.UPDATE(entityTable.getName());
        for (EntityHelper.EntityColumn column : entityTable.getEntityClassColumns()) {
            //更新不是ID的字段，因为根据主键查询的...更新后还是一样。
            String type = getjdbcType(column.getJavaType().getSimpleName());

            // 只更新JSONObject中定义的属性（例如JSON是由表单提交的更新字段）
            if (!column.isId() && keySet.contains(column.getProperty())) {
                sql.SET(column.getColumn() + RECORDMARK + column.getProperty() + "," + type + "}");
            }
        }
        if (entityTable.getEntityClassPKColumns().size() == 1) {
            EntityHelper.EntityColumn column = entityTable.getEntityClassPKColumns().iterator().next();
            notNullKeyProperty(column.getProperty(), metaObject.getValue(column.getProperty()));
            sql.WHERE(column.getColumn() + RECORDMARK + column.getProperty() + "}");
        } else {
            for (EntityHelper.EntityColumn column : entityTable.getEntityClassPKColumns()) {
                Object value = metaObject.getValue(column.getProperty());
                notNullKeyProperty(column.getProperty(), value);
                sql.WHERE(column.getColumn() + RECORDMARK + column.getProperty() + "}");
            }
        }

        return sql.toString();
    }
}
