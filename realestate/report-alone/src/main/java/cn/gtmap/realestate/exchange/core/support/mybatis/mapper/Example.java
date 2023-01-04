package cn.gtmap.realestate.exchange.core.support.mybatis.mapper;


import java.util.*;

/**
 * 通用的Example查询对象
 *
 * @author liuzh
 */
public class Example {
    protected String orderByClause;

    protected boolean distinct;

    protected boolean exists;

    protected List<Criteria> oredCriteria;

    protected Class<?> entityClass;

    protected EntityHelper.EntityTable table;
    //属性和列对应
    protected Map<String, EntityHelper.EntityColumn> propertyMap;

    /**
     * 默认exists为true
     *
     * @param entityClass
     */
    public Example(Class<?> entityClass) {
        this(entityClass, true);
    }

    /**
     * 带exists参数的构造方法
     *
     * @param entityClass
     * @param exists      - true时，如果字段不存在就抛出异常，false时，如果不存在就不使用该字段的条件
     */
    public Example(Class<?> entityClass, boolean exists) {
        this.exists = exists;
        oredCriteria = new ArrayList();
        this.entityClass = entityClass;
        table = EntityHelper.getEntityTable(entityClass);
        propertyMap = new HashMap(table.getEntityClassColumns().size());
        for (EntityHelper.EntityColumn column : table.getEntityClassColumns()) {
            propertyMap.put(column.getProperty(), column);
        }
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.isEmpty()) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria(propertyMap, exists);
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;
        //字段是否必须存在
        protected boolean exists;
        //属性和列对应
        protected Map<String, EntityHelper.EntityColumn> propertyMap;

        protected GeneratedCriteria(Map<String, EntityHelper.EntityColumn> propertyMap) {
            this(propertyMap, true);
        }

        protected GeneratedCriteria(Map<String, EntityHelper.EntityColumn> propertyMap, boolean exists) {
            super();
            this.exists = exists;
            criteria = new ArrayList();
            this.propertyMap = propertyMap;
        }

        private String column(String property) {
            if (propertyMap.containsKey(property)) {
                return propertyMap.get(property).getColumn();
            } else if (exists) {
                throw new NoSuchElementException("当前实体类不包含名为" + property + "的属性!");
            } else {
                return null;
            }
        }

        private String property(String property) {
            if (propertyMap.containsKey(property)) {
                return property;
            } else if (exists) {
                throw new NoSuchElementException("当前实体类不包含名为" + property + "的属性!");
            } else {
                return null;
            }
        }

        public boolean isValid() {
            return !criteria.isEmpty();
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new NoSuchElementException("Value for condition cannot be null");
            }
            if (condition.startsWith("null")) {
                return;
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new NoSuchElementException("Value for " + property + " cannot be null");
            }
            if (property == null) {
                return;
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new NoSuchElementException("Between values for " + property + " cannot be null");
            }
            if (property == null) {
                return;
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIsNull(String property) {
            addCriterion(column(property) + " is null");
            return (Criteria) this;
        }

        public Criteria andIsNotNull(String property) {
            addCriterion(column(property) + " is not null");
            return (Criteria) this;
        }

        public Criteria andEqualTo(String property, Object value) {
            addCriterion(column(property) + " =", value, property(property));
            return (Criteria) this;
        }

        /**
         * @param property    列名
         * @param value       参数值
         * @param datePattern 时间格式
         * @return 创建时间查询条件
         */
        public Criteria andFormatDateEqualTo(String property, Object value, String datePattern) {
            addCriterion("to_char(" + column(property) + ",'" + datePattern + "') =", value, property(property));
            return (Criteria) this;
        }

        public Criteria andNotEqualTo(String property, Object value) {
            addCriterion(column(property) + " <>", value, property(property));
            return (Criteria) this;
        }

        public Criteria andNotEqualNvlTo(String property, Object value1, Object value2) {
            addCriterion("nvl(" + column(property) + ",'" + value2 + "')" + " <>", value1, property(property));
            return (Criteria) this;
        }

        public Criteria andEqualNvlTo(String property, Object value1, Object value2) {
            addCriterion("nvl(" + column(property) + ",'" + value2 + "')" + " =", value1, property(property));
            return (Criteria) this;
        }


        public Criteria andGreaterThan(String property, Object value) {
            addCriterion(column(property) + " >", value, property(property));
            return (Criteria) this;
        }

        public Criteria andGreaterThanOrEqualTo(String property, Object value) {
            addCriterion(column(property) + " >=", value, property(property));
            return (Criteria) this;
        }

        public Criteria andLessThan(String property, Object value) {
            addCriterion(column(property) + " <", value, property(property));
            return (Criteria) this;
        }

        public Criteria andLessThanOrEqualTo(String property, Object value) {
            addCriterion(column(property) + " <=", value, property(property));
            return (Criteria) this;
        }

        public Criteria andIn(String property, List<Object> values) {
            addCriterion(column(property) + " in", values, property(property));
            return (Criteria) this;
        }

        public Criteria andInWithListString(String property, List<String> values) {
            addCriterion(column(property) + " in", values, property(property));
            return (Criteria) this;
        }

        public Criteria andNotIn(String property, List<Object> values) {
            addCriterion(column(property) + " not in", values, property(property));
            return (Criteria) this;
        }

        public Criteria andBetween(String property, Object value1, Object value2) {
            addCriterion(column(property) + " between", value1, value2, property(property));
            return (Criteria) this;
        }

        public Criteria andNotBetween(String property, Object value1, Object value2) {
            addCriterion(column(property) + " not between", value1, value2, property(property));
            return (Criteria) this;
        }

        public Criteria andLike(String property, String value) {
            addCriterion(column(property) + "  like", "%" + value + "%", property(property));
            return (Criteria) this;
        }

        public Criteria andRightLike(String property, String value) {
            addCriterion(column(property) + "  like", value + "%", property(property));
            return (Criteria) this;
        }

        public Criteria andNotLike(String property, String value) {
            addCriterion(column(property) + "  not like", value, property(property));
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria(Map<String, EntityHelper.EntityColumn> propertyMap) {
            super(propertyMap);
        }

        protected Criteria(Map<String, EntityHelper.EntityColumn> propertyMap, boolean exists) {
            super(propertyMap, exists);
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }
    }
}