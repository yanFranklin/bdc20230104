/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package cn.gtmap.realestate.common.core.support.mybatis.mapper;


import com.alibaba.fastjson.JSON;
import org.apache.avalon.framework.parameters.ParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 实体类工具类 - 处理实体和数据库表以及字段关键的一个类
 * <p/>
 * <p>项目地址 : <a href="https://github.com/abel533/Mapper" target="_blank">https://github.com/abel533/Mapper</a></p>
 *
 * @author liuzh
 */
public final class EntityHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityHelper.class);

    private static final Pattern pattern = Pattern.compile("_[a-z]");
    private static final String UUID = "UUID";
    private static final String JDBC = "JDBC";

    /**
     * 实体类 => 表对象
     */
    private static final Map<Class<?>, EntityTable> entityTableMap = new HashMap();

    private EntityHelper() {
    }

    /**
     * 获取表对象
     *
     * @param entityClass
     * @return
     */
    public static EntityTable getEntityTable(Class<?> entityClass) {
        EntityTable entityTable = entityTableMap.get(entityClass);
        if (entityTable == null) {
            initEntityNameMap(entityClass);
            entityTable = entityTableMap.get(entityClass);
        }
        if (entityTable == null) {
            throw new NoClassDefFoundError("无法获取实体类" + entityClass.getCanonicalName() + "对应的表名!");
        }
        return entityTable;
    }

    /**
     * 获取全部列
     *
     * @param entityClass
     * @return
     */
    public static Set<EntityColumn> getColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getEntityClassColumns();
    }

    /**
     * 获取主键信息
     *
     * @param entityClass
     * @return
     */
    public static Set<EntityColumn> getPKColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getEntityClassPKColumns();
    }

    /**
     * 获取字段映射关系
     *
     * @param entityClass
     * @return
     */
    public static Map<String, String> getColumnAlias(Class<?> entityClass) {
        EntityTable entityTable = getEntityTable(entityClass);
        if (entityTable.aliasMap != null) {
            return entityTable.aliasMap;
        }
        Set<EntityColumn> columnList = entityTable.getEntityClassColumns();
        entityTable.aliasMap = new HashMap(columnList.size());
        for (EntityColumn column : columnList) {
            entityTable.aliasMap.put(column.getColumn(), column.getProperty());
        }
        return entityTable.aliasMap;
    }

    /**
     * 获取查询的Select
     *
     * @param entityClass
     * @return
     */
    public static String getSelectColumns(Class<?> entityClass) {
        Set<EntityColumn> columnList = getColumns(entityClass);
        StringBuilder selectBuilder = new StringBuilder();
        boolean skipAlias = Map.class.isAssignableFrom(entityClass);
        for (EntityColumn entityColumn : columnList) {
            selectBuilder.append(entityColumn.getColumn());
            if (!skipAlias && !entityColumn.getColumn().equalsIgnoreCase(entityColumn.getProperty())) {
                selectBuilder.append(" ").append(entityColumn.getProperty().toUpperCase()).append(",");
            } else {
                selectBuilder.append(",");
            }
        }
        return selectBuilder.substring(0, selectBuilder.length() - 1);
    }

    /**
     * 获取查询的Select
     *
     * @param entityClass
     * @return
     */
    public static String getAllColumns(Class<?> entityClass) {
        Set<EntityColumn> columnList = getColumns(entityClass);
        StringBuilder selectBuilder = new StringBuilder();
        for (EntityColumn entityColumn : columnList) {
            selectBuilder.append(entityColumn.getColumn()).append(",");
        }
        return selectBuilder.substring(0, selectBuilder.length() - 1);
    }

    /**
     * 获取主键的Where语句
     *
     * @param entityClass
     * @return
     */
    public static String getPrimaryKeyWhere(Class<?> entityClass) {
        Set<EntityColumn> entityColumns = getPKColumns(entityClass);
        StringBuilder whereBuilder = new StringBuilder();
        for (EntityColumn column : entityColumns) {
            whereBuilder.append(column.getColumn()).append(" = ?").append(" AND ");
        }
        return whereBuilder.substring(0, whereBuilder.length() - 4);
    }

    /**
     * 初始化实体属性
     *
     * @param entityClass
     */
    public static synchronized void initEntityNameMap(Class<?> entityClass) {
        if (entityTableMap.get(entityClass) != null) {
            return;
        }
        //表名
        EntityTable entityTable = null;
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            if (!"".equals(table.name())) {
                entityTable = new EntityTable();
                entityTable.setTable(table);
            }
        }
        if (entityTable == null) {
            entityTable = new EntityTable();
            entityTable.name = camelhumpToUnderline(entityClass.getSimpleName()).toUpperCase();
        }
        //列
        List<Field> fieldList = getAllField(entityClass, null);
        Set<EntityColumn> columnSet = new HashSet();
        Set<EntityColumn> pkColumnSet = new HashSet();
        for (Field field : fieldList) {
            //排除字段
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            EntityColumn entityColumn = new EntityColumn();
            if (field.isAnnotationPresent(Id.class)) {
                entityColumn.setId(true);
            }
            String columnName = null;
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                columnName = column.name();
            }
            if (columnName == null || "".equals(columnName)) {
                columnName = camelhumpToUnderline(field.getName());
            }
            entityColumn.setProperty(field.getName());
            entityColumn.setColumn(columnName.toUpperCase());
            entityColumn.setJavaType(field.getType());
            //order by
            if (field.isAnnotationPresent(OrderBy.class)) {
                OrderBy orderBy = field.getAnnotation(OrderBy.class);
                if ("".equals(orderBy.value())) {
                    entityColumn.setOrderBy("ASC");
                } else {
                    entityColumn.setOrderBy(orderBy.value());
                }
            }
            //主键策略 - Oracle序列，MySql自动增长，UUID
            if (field.isAnnotationPresent(SequenceGenerator.class)) {
                SequenceGenerator sequenceGenerator = field.getAnnotation(SequenceGenerator.class);
                if ("".equals(sequenceGenerator.sequenceName())) {
                    throw new NoClassDefFoundError(entityClass + "字段" + field.getName() + "的注解@SequenceGenerator未指定sequenceName!");
                }
                entityColumn.setSequenceName(sequenceGenerator.sequenceName());
            } else if (field.isAnnotationPresent(GeneratedValue.class)) {
                GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
                if (generatedValue.generator().equals(UUID)) {
                    if (field.getType().equals(String.class)) {
                        entityColumn.setUuid(true);
                    } else {
                        throw new ClassFormatError(field.getName() + " - 该字段@GeneratedValue配置为UUID，但该字段类型不是String");
                    }
                } else if (generatedValue.generator().equals(JDBC)) {
                    if (Number.class.isAssignableFrom(field.getType())) {
                        entityColumn.setIdentity(true);
                        entityColumn.setGenerator(JDBC);
                    } else {
                        throw new ClassFormatError(field.getName() + " - 该字段@GeneratedValue配置为UUID，但该字段类型不是String");
                    }
                } else {
                    //允许通过generator来设置获取id的sql,例如mysql=CALL IDENTITY(),hsqldb=SELECT SCOPE_IDENTITY()
                    //允许通过拦截器参数设置公共的generator
                    if (generatedValue.strategy() == GenerationType.IDENTITY) {
                        //mysql的自动增长
                        entityColumn.setIdentity(true);
                        if (!"".equals(generatedValue.generator())) {
                            String generator;
                            MapperHelper.IdentityDialect identityDialect = MapperHelper.IdentityDialect.getDatabaseDialect(generatedValue.generator());
                            if (identityDialect != null) {
                                generator = identityDialect.getIdentityRetrievalStatement();
                            } else {
                                generator = generatedValue.generator();
                            }
                            entityColumn.setGenerator(generator);
                        }
                    } else {
                        throw new ClassFormatError(field.getName()
                                + " - 该字段@GeneratedValue配置只允许以下几种形式:" +
                                "\n1.全部数据库通用的@GeneratedValue(generator=\"UUID\")" +
                                "\n2.useGeneratedKeys的@GeneratedValue(generator=\\\"JDBC\\\")  " +
                                "\n3.类似mysql数据库的@GeneratedValue(strategy=GenerationType.IDENTITY[,generator=\"Mysql\"])");
                    }
                }
            }
            columnSet.add(entityColumn);
            if (entityColumn.isId()) {
                pkColumnSet.add(entityColumn);
            }
        }
        entityTable.entityClassColumns = columnSet;
        if (pkColumnSet.isEmpty()) {
            entityTable.entityClassPKColumns = columnSet;
        } else {
            entityTable.entityClassPKColumns = pkColumnSet;
        }
        //缓存
        entityTableMap.put(entityClass, entityTable);
    }

    public static void main(String[] args) {
        LOGGER.info("user_name");
        LOGGER.info("user_passWord");
        LOGGER.info("ISO9001");
        LOGGER.info("hello_world");
    }

    /**
     * 将驼峰风格替换为下划线风格
     */
    public static String camelhumpToUnderline(String str) {
        final char[] chars = str.toCharArray();
        final int size = chars.length;
        final StringBuilder sb = new StringBuilder(
                size * 3 / 2 + 1);
        char c;
        for (int i = 0; i < size; i++) {
            c = chars[i];
            if (isUppercaseAlpha(c)) {
                sb.append('_').append(c);
            } else {
                sb.append(toUpperAscii(c));
            }
        }
        return sb.charAt(0) == '_' ? sb.substring(1) : sb.toString();
    }

    /**
     * 将下划线风格替换为驼峰风格
     */
    public static String underlineToCamelhump(String str) {
        Matcher matcher = pattern.matcher(str);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; matcher.find(); i++) {
            builder.replace(matcher.start() - i, matcher.end() - i, matcher.group().substring(1).toUpperCase());
        }
        if (Character.isUpperCase(builder.charAt(0))) {
            builder.replace(0, 1, String.valueOf(Character.toLowerCase(builder.charAt(0))));
        }
        return builder.toString();
    }

    public static boolean isUppercaseAlpha(char c) {
        return (c >= 'A') && (c <= 'Z');
    }

    public static char toUpperAscii(char c) {
        if (isUppercaseAlpha(c)) {
            c -= (char) 0x20;
        }
        return c;
    }

    /**
     * 获取全部的Field
     *
     * @param entityClass
     * @param fieldList
     * @return
     */
    private static List<Field> getAllField(Class<?> entityClass, List<Field> fieldList) {
        if (fieldList == null) {
            fieldList = new ArrayList();
        }
        if (entityClass.equals(Object.class)) {
            return fieldList;
        }
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            //排除静态字段，解决bug#2
            if (!Modifier.isStatic(field.getModifiers())) {
                fieldList.add(field);
            }
        }
        Class<?> superClass = entityClass.getSuperclass();
        if (superClass != null
                && !superClass.equals(Object.class)
                && (superClass.isAnnotationPresent(Entity.class)
                || (!Map.class.isAssignableFrom(superClass)
                && !Collection.class.isAssignableFrom(superClass)))) {
            return getAllField(entityClass.getSuperclass(), fieldList);
        }
        return fieldList;
    }

    /**
     * map转换为Map
     *
     * @param map
     * @param beanClass
     * @return
     */
    public static Map<String, Object> map2AliasMap(Map<String, Object> map, Class<?> beanClass) {
        if (map == null) {
            return null;
        }
        Map<String, String> alias = getColumnAlias(beanClass);
        Map<String, Object> result = new HashMap();
        for (Map.Entry<String, Object> name : map.entrySet()) {
            String alia = name.getKey();
            //sql在被其他拦截器处理后，字段可能发生变化，例如分页插件增加rownum
            if (alias.containsKey(name.getKey())) {
                alia = alias.get(name.getKey());
            }
            result.put(alia, name.getValue());
        }
        return result;
    }

    /**
     * map转换为bean
     *
     * @param map
     * @param beanClass
     * @return
     */
    public static Object map2Bean(Map<String, Object> map, Class<?> beanClass) {
        try {
            if (map == null) {
                return null;
            }
            Map<String, Object> aliasMap = map2AliasMap(map, beanClass);
            Object bean = beanClass.newInstance();
            BeanUtilsEx.copyProperties(bean, aliasMap);
            return bean;
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage(), e);
            throw new NoSuchMethodError(beanClass.getCanonicalName() + "类没有默认空的构造方法!");
        } catch (Exception e) {
            try {
                //如果用beancopy的方式处理出错，则用json的方式再次尝试一下
                Map<String, Object> aliasMap = map2AliasMap(map, beanClass);
                return JSON.parseObject(JSON.toJSONString(aliasMap), beanClass);
            } catch (Exception exception) {
                LOGGER.error(exception.getMessage(), exception);
            }
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * mapList转换为beanList
     *
     * @param mapList
     * @param beanClass
     * @return
     */
    public static List maplist2BeanList(List<?> mapList, Class<?> beanClass) {
        if (mapList == null || mapList.isEmpty()) {
            return null;
        }
        List list = new ArrayList<Object>(mapList.size());
        for (Object map : mapList) {
            if (map instanceof Map) {
                list.add(map2Bean((Map) map, beanClass));
            } else {
                list.add(map);
            }
        }
        mapList.clear();
        mapList.addAll(list);
        return mapList;
    }

    /**
     * 实体对应表的配置信息
     */
    public static class EntityTable {
        private String name;
        private String catalog;
        private String schema;
        //实体类 => 全部列属性
        private Set<EntityColumn> entityClassColumns;
        //实体类 => 主键信息
        private Set<EntityColumn> entityClassPKColumns;
        //字段名和属性名的映射
        private Map<String, String> aliasMap;

        public void setTable(Table table) {
            this.name = table.name();
            this.catalog = table.catalog();
            this.schema = table.schema();
        }

        public String getName() {
            return name;
        }

        public String getCatalog() {
            return catalog;
        }

        public String getSchema() {
            return schema;
        }

        public String getPrefix() {
            if (catalog != null && catalog.length() > 0) {
                return catalog;
            }
            if (schema != null && schema.length() > 0) {
                return catalog;
            }
            return "";
        }

        public Set<EntityColumn> getEntityClassColumns() {
            return entityClassColumns;
        }

        public Set<EntityColumn> getEntityClassPKColumns() {
            return entityClassPKColumns;
        }

        public Map<String, String> getAliasMap() {
            return aliasMap;
        }
    }

    /**
     * 实体字段对应数据库列的信息
     */
    public static class EntityColumn {
        private String property;
        private String column;
        private Class<?> javaType;
        private String sequenceName;
        private boolean id = false;
        private boolean uuid = false;
        private boolean identity = false;
        private String generator;
        private String orderBy;

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public Class<?> getJavaType() {
            return javaType;
        }

        public void setJavaType(Class<?> javaType) {
            this.javaType = javaType;
        }

        public String getSequenceName() {
            return sequenceName;
        }

        public void setSequenceName(String sequenceName) {
            this.sequenceName = sequenceName;
        }

        public boolean isId() {
            return id;
        }

        public void setId(boolean id) {
            this.id = id;
        }

        public boolean isUuid() {
            return uuid;
        }

        public void setUuid(boolean uuid) {
            this.uuid = uuid;
        }

        public boolean isIdentity() {
            return identity;
        }

        public void setIdentity(boolean identity) {
            this.identity = identity;
        }

        public String getGenerator() {
            return generator;
        }

        public void setGenerator(String generator) {
            this.generator = generator;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            EntityColumn that = (EntityColumn) o;

            if (id != that.id) {
                return false;
            }
            if (identity != that.identity) {
                return false;
            }
            if (uuid != that.uuid) {
                return false;
            }
            if (column != null ? !column.equals(that.column) : that.column != null) {
                return false;
            }
            if (generator != null ? !generator.equals(that.generator) : that.generator != null) {
                return false;
            }
            if (javaType != null ? !javaType.equals(that.javaType) : that.javaType != null) {
                return false;
            }
            if (orderBy != null ? !orderBy.equals(that.orderBy) : that.orderBy != null) {
                return false;
            }
            if (property != null ? !property.equals(that.property) : that.property != null) {
                return false;
            }
            if (sequenceName != null ? !sequenceName.equals(that.sequenceName) : that.sequenceName != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = property != null ? property.hashCode() : 0;
            result = 31 * result + (column != null ? column.hashCode() : 0);
            result = 31 * result + (javaType != null ? javaType.hashCode() : 0);
            result = 31 * result + (sequenceName != null ? sequenceName.hashCode() : 0);
            result = 31 * result + (id ? 1 : 0);
            result = 31 * result + (uuid ? 1 : 0);
            result = 31 * result + (identity ? 1 : 0);
            result = 31 * result + (generator != null ? generator.hashCode() : 0);
            result = 31 * result + (orderBy != null ? orderBy.hashCode() : 0);
            return result;
        }
    }
}