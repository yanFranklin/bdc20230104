package cn.gtmap.realestate.common.core.domain.natural;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

public class ZrzyXmLsgxDoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ZrzyXmLsgxDoExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
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
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andGxidIsNull() {
            addCriterion("GXID is null");
            return (Criteria) this;
        }

        public Criteria andGxidIsNotNull() {
            addCriterion("GXID is not null");
            return (Criteria) this;
        }

        public Criteria andGxidEqualTo(String value) {
            addCriterion("GXID =", value, "gxid");
            return (Criteria) this;
        }

        public Criteria andGxidNotEqualTo(String value) {
            addCriterion("GXID <>", value, "gxid");
            return (Criteria) this;
        }

        public Criteria andGxidGreaterThan(String value) {
            addCriterion("GXID >", value, "gxid");
            return (Criteria) this;
        }

        public Criteria andGxidGreaterThanOrEqualTo(String value) {
            addCriterion("GXID >=", value, "gxid");
            return (Criteria) this;
        }

        public Criteria andGxidLessThan(String value) {
            addCriterion("GXID <", value, "gxid");
            return (Criteria) this;
        }

        public Criteria andGxidLessThanOrEqualTo(String value) {
            addCriterion("GXID <=", value, "gxid");
            return (Criteria) this;
        }

        public Criteria andGxidLike(String value) {
            addCriterion("GXID like", value, "gxid");
            return (Criteria) this;
        }

        public Criteria andGxidNotLike(String value) {
            addCriterion("GXID not like", value, "gxid");
            return (Criteria) this;
        }

        public Criteria andGxidIn(List<String> values) {
            addCriterion("GXID in", values, "gxid");
            return (Criteria) this;
        }

        public Criteria andGxidNotIn(List<String> values) {
            addCriterion("GXID not in", values, "gxid");
            return (Criteria) this;
        }

        public Criteria andGxidBetween(String value1, String value2) {
            addCriterion("GXID between", value1, value2, "gxid");
            return (Criteria) this;
        }

        public Criteria andGxidNotBetween(String value1, String value2) {
            addCriterion("GXID not between", value1, value2, "gxid");
            return (Criteria) this;
        }

        public Criteria andXmidIsNull() {
            addCriterion("XMID is null");
            return (Criteria) this;
        }

        public Criteria andXmidIsNotNull() {
            addCriterion("XMID is not null");
            return (Criteria) this;
        }

        public Criteria andXmidEqualTo(String value) {
            addCriterion("XMID =", value, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidNotEqualTo(String value) {
            addCriterion("XMID <>", value, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidGreaterThan(String value) {
            addCriterion("XMID >", value, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidGreaterThanOrEqualTo(String value) {
            addCriterion("XMID >=", value, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidLessThan(String value) {
            addCriterion("XMID <", value, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidLessThanOrEqualTo(String value) {
            addCriterion("XMID <=", value, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidLike(String value) {
            addCriterion("XMID like", value, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidNotLike(String value) {
            addCriterion("XMID not like", value, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidIn(List<String> values) {
            addCriterion("XMID in", values, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidNotIn(List<String> values) {
            addCriterion("XMID not in", values, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidBetween(String value1, String value2) {
            addCriterion("XMID between", value1, value2, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidNotBetween(String value1, String value2) {
            addCriterion("XMID not between", value1, value2, "xmid");
            return (Criteria) this;
        }

        public Criteria andYxmidIsNull() {
            addCriterion("YXMID is null");
            return (Criteria) this;
        }

        public Criteria andYxmidIsNotNull() {
            addCriterion("YXMID is not null");
            return (Criteria) this;
        }

        public Criteria andYxmidEqualTo(String value) {
            addCriterion("YXMID =", value, "yxmid");
            return (Criteria) this;
        }

        public Criteria andYxmidNotEqualTo(String value) {
            addCriterion("YXMID <>", value, "yxmid");
            return (Criteria) this;
        }

        public Criteria andYxmidGreaterThan(String value) {
            addCriterion("YXMID >", value, "yxmid");
            return (Criteria) this;
        }

        public Criteria andYxmidGreaterThanOrEqualTo(String value) {
            addCriterion("YXMID >=", value, "yxmid");
            return (Criteria) this;
        }

        public Criteria andYxmidLessThan(String value) {
            addCriterion("YXMID <", value, "yxmid");
            return (Criteria) this;
        }

        public Criteria andYxmidLessThanOrEqualTo(String value) {
            addCriterion("YXMID <=", value, "yxmid");
            return (Criteria) this;
        }

        public Criteria andYxmidLike(String value) {
            addCriterion("YXMID like", value, "yxmid");
            return (Criteria) this;
        }

        public Criteria andYxmidNotLike(String value) {
            addCriterion("YXMID not like", value, "yxmid");
            return (Criteria) this;
        }

        public Criteria andYxmidIn(List<String> values) {
            addCriterion("YXMID in", values, "yxmid");
            return (Criteria) this;
        }

        public Criteria andYxmidNotIn(List<String> values) {
            addCriterion("YXMID not in", values, "yxmid");
            return (Criteria) this;
        }

        public Criteria andYxmidBetween(String value1, String value2) {
            addCriterion("YXMID between", value1, value2, "yxmid");
            return (Criteria) this;
        }

        public Criteria andYxmidNotBetween(String value1, String value2) {
            addCriterion("YXMID not between", value1, value2, "yxmid");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
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
    }
}