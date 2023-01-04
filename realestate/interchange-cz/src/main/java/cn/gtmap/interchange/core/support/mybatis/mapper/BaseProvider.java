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

package cn.gtmap.interchange.core.support.mybatis.mapper;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.reflection.MetaObject;

import java.util.List;
import java.util.Map;

/**
 * 基础类
 *
 * @author liuzh
 */
public class BaseProvider {
    private static final String RECORD = "record";
    private static final String CONDITION = "condition";
    private static final String TYPEHANDLER = "typeHandler";
    private static final String JSON = "json";

    /**
     * 主键字段不能为空
     *
     * @param property
     * @param value
     */
    protected void notNullKeyProperty(String property, Object value) {
        if (value == null || (value instanceof String && isEmpty((String) value))) {
            throwNullKeyException(property);
        }
    }

    protected void throwNullKeyException(String property) {
        throw new NullPointerException("主键属性" + property + "不能为空!");
    }

    /**
     * 获取实体类型
     *
     * @param params
     * @return
     */
    protected Class<?> getEntityClass(Map<String, Object> params) {
        Class<?> entityClass = null;
        if (params.containsKey(RECORD)) {
            entityClass = getEntity(params).getClass();
        } else if (params.containsKey("entityClass")) {
            entityClass = (Class<?>) params.get("entityClass");
        }
        if (entityClass == null) {
            throw new NoClassDefFoundError("无法获取实体类型!");
        }
        return entityClass;
    }

    /**
     * 获取实体类
     *
     * @param params
     * @return
     */
    protected Object getEntity(Map<String, Object> params) {
        Object result;
        if (params.containsKey(RECORD)) {
            if (params.get(RECORD) != null && (params.get(RECORD) instanceof List || params.get(RECORD) instanceof java.util.ArrayList)) {
                result = ((List) params.get(RECORD)).get(0);
            } else {
                result = params.get(RECORD);
            }
        } else if (params.containsKey("key")) {
            result = params.get("key");
        } else {
            throw new NoClassDefFoundError("当前方法没有实体或主键参数!");
        }
        if (result == null) {
            throw new NullPointerException("实体或者主键参数不能为空!");
        }
        return result;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   params 参数集合
     * @return  {JSONObject}  要更新的属性json
     * @description  获取实体要保存更新的属性JSON对象
     */
    public JSONObject getJSONObject(Map<String, Object> params){
        if(MapUtils.isEmpty(params) || !params.containsKey(JSON)){
            throw new NullPointerException("实体不能为空!");
        }

        return (JSONObject) MapUtils.getObject(params, JSON);
    }

    /**
     * 获取Example类 - 不在此校验是否为合法的Example类
     *
     * @param params
     * @return
     */
    protected MetaObject getExample(Map<String, Object> params) {
        Object result = null;
        if (params.containsKey("example")) {
            result = params.get("example");
        }
        if (result == null) {
            return null;
        }
        //根据Example的结构，通过判断是否包含某些属性来判断条件是否为合法的example类型
        MetaObject example = MapperTemplate.forObject(result);
        if (example.hasGetter("orderByClause")
                && example.hasGetter("oredCriteria")
                && example.hasGetter("distinct")) {
            return example;
        }
        throw new IllegalArgumentException("Example参数不是合法的Mybatis Example对象!");
    }

    /**
     * Example条件
     */
    protected void applyOrderBy(SQL sql, MetaObject example) {
        if (example == null) {
            return;
        }
        Object orderBy = example.getValue("orderByClause");
        if (orderBy != null) {
            sql.ORDER_BY((String) orderBy);
        }
    }

    /**
     * Example条件
     */
    protected void applyWhere(SQL sql, MetaObject example) {
        if (example == null) {
            return;
        }
        String parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
        String parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
        String parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
        String parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
        String parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
        String parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";

        StringBuilder sb = new StringBuilder();

        List<?> oredCriteria = (List<?>) example.getValue("oredCriteria");
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            MetaObject criteria = MapperTemplate.forObject(oredCriteria.get(i));
            List<?> criterions = (List<?>) criteria.getValue("criteria");
            if (!criterions.isEmpty()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }

                sb.append('(');
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    MetaObject criterion = MapperTemplate.forObject(criterions.get(j));
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }

                    if ((Boolean) criterion.getValue("noValue")) {
                        sb.append(criterion.getValue(CONDITION));
                    } else if ((Boolean) criterion.getValue("singleValue")) {
                        if (criterion.getValue(TYPEHANDLER) == null) {
                            sb.append(String.format(parmPhrase1, criterion.getValue(CONDITION), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getValue(CONDITION), i, j, criterion.getValue(TYPEHANDLER)));
                        }
                    } else if ((Boolean) criterion.getValue("betweenValue")) {
                        if (criterion.getValue(TYPEHANDLER) == null) {
                            sb.append(String.format(parmPhrase2, criterion.getValue(CONDITION), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getValue(CONDITION), i, j, criterion.getValue(TYPEHANDLER), i, j, criterion.getValue(TYPEHANDLER)));
                        }
                    } else if ((Boolean) criterion.getValue("listValue")) {
                        sb.append(criterion.getValue(CONDITION));
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue("value");
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getValue(TYPEHANDLER) == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getValue(TYPEHANDLER)));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }

    protected boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }

    protected boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }
}
