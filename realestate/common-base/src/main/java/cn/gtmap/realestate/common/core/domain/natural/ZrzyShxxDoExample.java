package cn.gtmap.realestate.common.core.domain.natural;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

public class ZrzyShxxDoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ZrzyShxxDoExample() {
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

        public Criteria andShxxidIsNull() {
            addCriterion("SHXXID is null");
            return (Criteria) this;
        }

        public Criteria andShxxidIsNotNull() {
            addCriterion("SHXXID is not null");
            return (Criteria) this;
        }

        public Criteria andShxxidEqualTo(String value) {
            addCriterion("SHXXID =", value, "shxxid");
            return (Criteria) this;
        }

        public Criteria andShxxidNotEqualTo(String value) {
            addCriterion("SHXXID <>", value, "shxxid");
            return (Criteria) this;
        }

        public Criteria andShxxidGreaterThan(String value) {
            addCriterion("SHXXID >", value, "shxxid");
            return (Criteria) this;
        }

        public Criteria andShxxidGreaterThanOrEqualTo(String value) {
            addCriterion("SHXXID >=", value, "shxxid");
            return (Criteria) this;
        }

        public Criteria andShxxidLessThan(String value) {
            addCriterion("SHXXID <", value, "shxxid");
            return (Criteria) this;
        }

        public Criteria andShxxidLessThanOrEqualTo(String value) {
            addCriterion("SHXXID <=", value, "shxxid");
            return (Criteria) this;
        }

        public Criteria andShxxidLike(String value) {
            addCriterion("SHXXID like", value, "shxxid");
            return (Criteria) this;
        }

        public Criteria andShxxidNotLike(String value) {
            addCriterion("SHXXID not like", value, "shxxid");
            return (Criteria) this;
        }

        public Criteria andShxxidIn(List<String> values) {
            addCriterion("SHXXID in", values, "shxxid");
            return (Criteria) this;
        }

        public Criteria andShxxidNotIn(List<String> values) {
            addCriterion("SHXXID not in", values, "shxxid");
            return (Criteria) this;
        }

        public Criteria andShxxidBetween(String value1, String value2) {
            addCriterion("SHXXID between", value1, value2, "shxxid");
            return (Criteria) this;
        }

        public Criteria andShxxidNotBetween(String value1, String value2) {
            addCriterion("SHXXID not between", value1, value2, "shxxid");
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

        public Criteria andJdmcIsNull() {
            addCriterion("JDMC is null");
            return (Criteria) this;
        }

        public Criteria andJdmcIsNotNull() {
            addCriterion("JDMC is not null");
            return (Criteria) this;
        }

        public Criteria andJdmcEqualTo(String value) {
            addCriterion("JDMC =", value, "jdmc");
            return (Criteria) this;
        }

        public Criteria andJdmcNotEqualTo(String value) {
            addCriterion("JDMC <>", value, "jdmc");
            return (Criteria) this;
        }

        public Criteria andJdmcGreaterThan(String value) {
            addCriterion("JDMC >", value, "jdmc");
            return (Criteria) this;
        }

        public Criteria andJdmcGreaterThanOrEqualTo(String value) {
            addCriterion("JDMC >=", value, "jdmc");
            return (Criteria) this;
        }

        public Criteria andJdmcLessThan(String value) {
            addCriterion("JDMC <", value, "jdmc");
            return (Criteria) this;
        }

        public Criteria andJdmcLessThanOrEqualTo(String value) {
            addCriterion("JDMC <=", value, "jdmc");
            return (Criteria) this;
        }

        public Criteria andJdmcLike(String value) {
            addCriterion("JDMC like", value, "jdmc");
            return (Criteria) this;
        }

        public Criteria andJdmcNotLike(String value) {
            addCriterion("JDMC not like", value, "jdmc");
            return (Criteria) this;
        }

        public Criteria andJdmcIn(List<String> values) {
            addCriterion("JDMC in", values, "jdmc");
            return (Criteria) this;
        }

        public Criteria andJdmcNotIn(List<String> values) {
            addCriterion("JDMC not in", values, "jdmc");
            return (Criteria) this;
        }

        public Criteria andJdmcBetween(String value1, String value2) {
            addCriterion("JDMC between", value1, value2, "jdmc");
            return (Criteria) this;
        }

        public Criteria andJdmcNotBetween(String value1, String value2) {
            addCriterion("JDMC not between", value1, value2, "jdmc");
            return (Criteria) this;
        }

        public Criteria andSxhIsNull() {
            addCriterion("SXH is null");
            return (Criteria) this;
        }

        public Criteria andSxhIsNotNull() {
            addCriterion("SXH is not null");
            return (Criteria) this;
        }

        public Criteria andSxhEqualTo(Short value) {
            addCriterion("SXH =", value, "sxh");
            return (Criteria) this;
        }

        public Criteria andSxhNotEqualTo(Short value) {
            addCriterion("SXH <>", value, "sxh");
            return (Criteria) this;
        }

        public Criteria andSxhGreaterThan(Short value) {
            addCriterion("SXH >", value, "sxh");
            return (Criteria) this;
        }

        public Criteria andSxhGreaterThanOrEqualTo(Short value) {
            addCriterion("SXH >=", value, "sxh");
            return (Criteria) this;
        }

        public Criteria andSxhLessThan(Short value) {
            addCriterion("SXH <", value, "sxh");
            return (Criteria) this;
        }

        public Criteria andSxhLessThanOrEqualTo(Short value) {
            addCriterion("SXH <=", value, "sxh");
            return (Criteria) this;
        }

        public Criteria andSxhIn(List<Short> values) {
            addCriterion("SXH in", values, "sxh");
            return (Criteria) this;
        }

        public Criteria andSxhNotIn(List<Short> values) {
            addCriterion("SXH not in", values, "sxh");
            return (Criteria) this;
        }

        public Criteria andSxhBetween(Short value1, Short value2) {
            addCriterion("SXH between", value1, value2, "sxh");
            return (Criteria) this;
        }

        public Criteria andSxhNotBetween(Short value1, Short value2) {
            addCriterion("SXH not between", value1, value2, "sxh");
            return (Criteria) this;
        }

        public Criteria andShryxmIsNull() {
            addCriterion("SHRYXM is null");
            return (Criteria) this;
        }

        public Criteria andShryxmIsNotNull() {
            addCriterion("SHRYXM is not null");
            return (Criteria) this;
        }

        public Criteria andShryxmEqualTo(String value) {
            addCriterion("SHRYXM =", value, "shryxm");
            return (Criteria) this;
        }

        public Criteria andShryxmNotEqualTo(String value) {
            addCriterion("SHRYXM <>", value, "shryxm");
            return (Criteria) this;
        }

        public Criteria andShryxmGreaterThan(String value) {
            addCriterion("SHRYXM >", value, "shryxm");
            return (Criteria) this;
        }

        public Criteria andShryxmGreaterThanOrEqualTo(String value) {
            addCriterion("SHRYXM >=", value, "shryxm");
            return (Criteria) this;
        }

        public Criteria andShryxmLessThan(String value) {
            addCriterion("SHRYXM <", value, "shryxm");
            return (Criteria) this;
        }

        public Criteria andShryxmLessThanOrEqualTo(String value) {
            addCriterion("SHRYXM <=", value, "shryxm");
            return (Criteria) this;
        }

        public Criteria andShryxmLike(String value) {
            addCriterion("SHRYXM like", value, "shryxm");
            return (Criteria) this;
        }

        public Criteria andShryxmNotLike(String value) {
            addCriterion("SHRYXM not like", value, "shryxm");
            return (Criteria) this;
        }

        public Criteria andShryxmIn(List<String> values) {
            addCriterion("SHRYXM in", values, "shryxm");
            return (Criteria) this;
        }

        public Criteria andShryxmNotIn(List<String> values) {
            addCriterion("SHRYXM not in", values, "shryxm");
            return (Criteria) this;
        }

        public Criteria andShryxmBetween(String value1, String value2) {
            addCriterion("SHRYXM between", value1, value2, "shryxm");
            return (Criteria) this;
        }

        public Criteria andShryxmNotBetween(String value1, String value2) {
            addCriterion("SHRYXM not between", value1, value2, "shryxm");
            return (Criteria) this;
        }

        public Criteria andShridIsNull() {
            addCriterion("SHRID is null");
            return (Criteria) this;
        }

        public Criteria andShridIsNotNull() {
            addCriterion("SHRID is not null");
            return (Criteria) this;
        }

        public Criteria andShridEqualTo(String value) {
            addCriterion("SHRID =", value, "shrid");
            return (Criteria) this;
        }

        public Criteria andShridNotEqualTo(String value) {
            addCriterion("SHRID <>", value, "shrid");
            return (Criteria) this;
        }

        public Criteria andShridGreaterThan(String value) {
            addCriterion("SHRID >", value, "shrid");
            return (Criteria) this;
        }

        public Criteria andShridGreaterThanOrEqualTo(String value) {
            addCriterion("SHRID >=", value, "shrid");
            return (Criteria) this;
        }

        public Criteria andShridLessThan(String value) {
            addCriterion("SHRID <", value, "shrid");
            return (Criteria) this;
        }

        public Criteria andShridLessThanOrEqualTo(String value) {
            addCriterion("SHRID <=", value, "shrid");
            return (Criteria) this;
        }

        public Criteria andShridLike(String value) {
            addCriterion("SHRID like", value, "shrid");
            return (Criteria) this;
        }

        public Criteria andShridNotLike(String value) {
            addCriterion("SHRID not like", value, "shrid");
            return (Criteria) this;
        }

        public Criteria andShridIn(List<String> values) {
            addCriterion("SHRID in", values, "shrid");
            return (Criteria) this;
        }

        public Criteria andShridNotIn(List<String> values) {
            addCriterion("SHRID not in", values, "shrid");
            return (Criteria) this;
        }

        public Criteria andShridBetween(String value1, String value2) {
            addCriterion("SHRID between", value1, value2, "shrid");
            return (Criteria) this;
        }

        public Criteria andShridNotBetween(String value1, String value2) {
            addCriterion("SHRID not between", value1, value2, "shrid");
            return (Criteria) this;
        }

        public Criteria andShkssjIsNull() {
            addCriterion("SHKSSJ is null");
            return (Criteria) this;
        }

        public Criteria andShkssjIsNotNull() {
            addCriterion("SHKSSJ is not null");
            return (Criteria) this;
        }

        public Criteria andShkssjEqualTo(LocalDateTime value) {
            addCriterion("SHKSSJ =", value, "shkssj");
            return (Criteria) this;
        }

        public Criteria andShkssjNotEqualTo(LocalDateTime value) {
            addCriterion("SHKSSJ <>", value, "shkssj");
            return (Criteria) this;
        }

        public Criteria andShkssjGreaterThan(LocalDateTime value) {
            addCriterion("SHKSSJ >", value, "shkssj");
            return (Criteria) this;
        }

        public Criteria andShkssjGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("SHKSSJ >=", value, "shkssj");
            return (Criteria) this;
        }

        public Criteria andShkssjLessThan(LocalDateTime value) {
            addCriterion("SHKSSJ <", value, "shkssj");
            return (Criteria) this;
        }

        public Criteria andShkssjLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("SHKSSJ <=", value, "shkssj");
            return (Criteria) this;
        }

        public Criteria andShkssjIn(List<LocalDateTime> values) {
            addCriterion("SHKSSJ in", values, "shkssj");
            return (Criteria) this;
        }

        public Criteria andShkssjNotIn(List<LocalDateTime> values) {
            addCriterion("SHKSSJ not in", values, "shkssj");
            return (Criteria) this;
        }

        public Criteria andShkssjBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("SHKSSJ between", value1, value2, "shkssj");
            return (Criteria) this;
        }

        public Criteria andShkssjNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("SHKSSJ not between", value1, value2, "shkssj");
            return (Criteria) this;
        }

        public Criteria andShjssjIsNull() {
            addCriterion("SHJSSJ is null");
            return (Criteria) this;
        }

        public Criteria andShjssjIsNotNull() {
            addCriterion("SHJSSJ is not null");
            return (Criteria) this;
        }

        public Criteria andShjssjEqualTo(LocalDateTime value) {
            addCriterion("SHJSSJ =", value, "shjssj");
            return (Criteria) this;
        }

        public Criteria andShjssjNotEqualTo(LocalDateTime value) {
            addCriterion("SHJSSJ <>", value, "shjssj");
            return (Criteria) this;
        }

        public Criteria andShjssjGreaterThan(LocalDateTime value) {
            addCriterion("SHJSSJ >", value, "shjssj");
            return (Criteria) this;
        }

        public Criteria andShjssjGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("SHJSSJ >=", value, "shjssj");
            return (Criteria) this;
        }

        public Criteria andShjssjLessThan(LocalDateTime value) {
            addCriterion("SHJSSJ <", value, "shjssj");
            return (Criteria) this;
        }

        public Criteria andShjssjLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("SHJSSJ <=", value, "shjssj");
            return (Criteria) this;
        }

        public Criteria andShjssjIn(List<LocalDateTime> values) {
            addCriterion("SHJSSJ in", values, "shjssj");
            return (Criteria) this;
        }

        public Criteria andShjssjNotIn(List<LocalDateTime> values) {
            addCriterion("SHJSSJ not in", values, "shjssj");
            return (Criteria) this;
        }

        public Criteria andShjssjBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("SHJSSJ between", value1, value2, "shjssj");
            return (Criteria) this;
        }

        public Criteria andShjssjNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("SHJSSJ not between", value1, value2, "shjssj");
            return (Criteria) this;
        }

        public Criteria andShyjIsNull() {
            addCriterion("SHYJ is null");
            return (Criteria) this;
        }

        public Criteria andShyjIsNotNull() {
            addCriterion("SHYJ is not null");
            return (Criteria) this;
        }

        public Criteria andShyjEqualTo(String value) {
            addCriterion("SHYJ =", value, "shyj");
            return (Criteria) this;
        }

        public Criteria andShyjNotEqualTo(String value) {
            addCriterion("SHYJ <>", value, "shyj");
            return (Criteria) this;
        }

        public Criteria andShyjGreaterThan(String value) {
            addCriterion("SHYJ >", value, "shyj");
            return (Criteria) this;
        }

        public Criteria andShyjGreaterThanOrEqualTo(String value) {
            addCriterion("SHYJ >=", value, "shyj");
            return (Criteria) this;
        }

        public Criteria andShyjLessThan(String value) {
            addCriterion("SHYJ <", value, "shyj");
            return (Criteria) this;
        }

        public Criteria andShyjLessThanOrEqualTo(String value) {
            addCriterion("SHYJ <=", value, "shyj");
            return (Criteria) this;
        }

        public Criteria andShyjLike(String value) {
            addCriterion("SHYJ like", value, "shyj");
            return (Criteria) this;
        }

        public Criteria andShyjNotLike(String value) {
            addCriterion("SHYJ not like", value, "shyj");
            return (Criteria) this;
        }

        public Criteria andShyjIn(List<String> values) {
            addCriterion("SHYJ in", values, "shyj");
            return (Criteria) this;
        }

        public Criteria andShyjNotIn(List<String> values) {
            addCriterion("SHYJ not in", values, "shyj");
            return (Criteria) this;
        }

        public Criteria andShyjBetween(String value1, String value2) {
            addCriterion("SHYJ between", value1, value2, "shyj");
            return (Criteria) this;
        }

        public Criteria andShyjNotBetween(String value1, String value2) {
            addCriterion("SHYJ not between", value1, value2, "shyj");
            return (Criteria) this;
        }

        public Criteria andQmsjIsNull() {
            addCriterion("QMSJ is null");
            return (Criteria) this;
        }

        public Criteria andQmsjIsNotNull() {
            addCriterion("QMSJ is not null");
            return (Criteria) this;
        }

        public Criteria andQmsjEqualTo(LocalDateTime value) {
            addCriterion("QMSJ =", value, "qmsj");
            return (Criteria) this;
        }

        public Criteria andQmsjNotEqualTo(LocalDateTime value) {
            addCriterion("QMSJ <>", value, "qmsj");
            return (Criteria) this;
        }

        public Criteria andQmsjGreaterThan(LocalDateTime value) {
            addCriterion("QMSJ >", value, "qmsj");
            return (Criteria) this;
        }

        public Criteria andQmsjGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("QMSJ >=", value, "qmsj");
            return (Criteria) this;
        }

        public Criteria andQmsjLessThan(LocalDateTime value) {
            addCriterion("QMSJ <", value, "qmsj");
            return (Criteria) this;
        }

        public Criteria andQmsjLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("QMSJ <=", value, "qmsj");
            return (Criteria) this;
        }

        public Criteria andQmsjIn(List<LocalDateTime> values) {
            addCriterion("QMSJ in", values, "qmsj");
            return (Criteria) this;
        }

        public Criteria andQmsjNotIn(List<LocalDateTime> values) {
            addCriterion("QMSJ not in", values, "qmsj");
            return (Criteria) this;
        }

        public Criteria andQmsjBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("QMSJ between", value1, value2, "qmsj");
            return (Criteria) this;
        }

        public Criteria andQmsjNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("QMSJ not between", value1, value2, "qmsj");
            return (Criteria) this;
        }

        public Criteria andQmidIsNull() {
            addCriterion("QMID is null");
            return (Criteria) this;
        }

        public Criteria andQmidIsNotNull() {
            addCriterion("QMID is not null");
            return (Criteria) this;
        }

        public Criteria andQmidEqualTo(String value) {
            addCriterion("QMID =", value, "qmid");
            return (Criteria) this;
        }

        public Criteria andQmidNotEqualTo(String value) {
            addCriterion("QMID <>", value, "qmid");
            return (Criteria) this;
        }

        public Criteria andQmidGreaterThan(String value) {
            addCriterion("QMID >", value, "qmid");
            return (Criteria) this;
        }

        public Criteria andQmidGreaterThanOrEqualTo(String value) {
            addCriterion("QMID >=", value, "qmid");
            return (Criteria) this;
        }

        public Criteria andQmidLessThan(String value) {
            addCriterion("QMID <", value, "qmid");
            return (Criteria) this;
        }

        public Criteria andQmidLessThanOrEqualTo(String value) {
            addCriterion("QMID <=", value, "qmid");
            return (Criteria) this;
        }

        public Criteria andQmidLike(String value) {
            addCriterion("QMID like", value, "qmid");
            return (Criteria) this;
        }

        public Criteria andQmidNotLike(String value) {
            addCriterion("QMID not like", value, "qmid");
            return (Criteria) this;
        }

        public Criteria andQmidIn(List<String> values) {
            addCriterion("QMID in", values, "qmid");
            return (Criteria) this;
        }

        public Criteria andQmidNotIn(List<String> values) {
            addCriterion("QMID not in", values, "qmid");
            return (Criteria) this;
        }

        public Criteria andQmidBetween(String value1, String value2) {
            addCriterion("QMID between", value1, value2, "qmid");
            return (Criteria) this;
        }

        public Criteria andQmidNotBetween(String value1, String value2) {
            addCriterion("QMID not between", value1, value2, "qmid");
            return (Criteria) this;
        }

        public Criteria andCzjgIsNull() {
            addCriterion("CZJG is null");
            return (Criteria) this;
        }

        public Criteria andCzjgIsNotNull() {
            addCriterion("CZJG is not null");
            return (Criteria) this;
        }

        public Criteria andCzjgEqualTo(Short value) {
            addCriterion("CZJG =", value, "czjg");
            return (Criteria) this;
        }

        public Criteria andCzjgNotEqualTo(Short value) {
            addCriterion("CZJG <>", value, "czjg");
            return (Criteria) this;
        }

        public Criteria andCzjgGreaterThan(Short value) {
            addCriterion("CZJG >", value, "czjg");
            return (Criteria) this;
        }

        public Criteria andCzjgGreaterThanOrEqualTo(Short value) {
            addCriterion("CZJG >=", value, "czjg");
            return (Criteria) this;
        }

        public Criteria andCzjgLessThan(Short value) {
            addCriterion("CZJG <", value, "czjg");
            return (Criteria) this;
        }

        public Criteria andCzjgLessThanOrEqualTo(Short value) {
            addCriterion("CZJG <=", value, "czjg");
            return (Criteria) this;
        }

        public Criteria andCzjgIn(List<Short> values) {
            addCriterion("CZJG in", values, "czjg");
            return (Criteria) this;
        }

        public Criteria andCzjgNotIn(List<Short> values) {
            addCriterion("CZJG not in", values, "czjg");
            return (Criteria) this;
        }

        public Criteria andCzjgBetween(Short value1, Short value2) {
            addCriterion("CZJG between", value1, value2, "czjg");
            return (Criteria) this;
        }

        public Criteria andCzjgNotBetween(Short value1, Short value2) {
            addCriterion("CZJG not between", value1, value2, "czjg");
            return (Criteria) this;
        }

        public Criteria andBzIsNull() {
            addCriterion("BZ is null");
            return (Criteria) this;
        }

        public Criteria andBzIsNotNull() {
            addCriterion("BZ is not null");
            return (Criteria) this;
        }

        public Criteria andBzEqualTo(String value) {
            addCriterion("BZ =", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotEqualTo(String value) {
            addCriterion("BZ <>", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzGreaterThan(String value) {
            addCriterion("BZ >", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzGreaterThanOrEqualTo(String value) {
            addCriterion("BZ >=", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzLessThan(String value) {
            addCriterion("BZ <", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzLessThanOrEqualTo(String value) {
            addCriterion("BZ <=", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzLike(String value) {
            addCriterion("BZ like", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotLike(String value) {
            addCriterion("BZ not like", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzIn(List<String> values) {
            addCriterion("BZ in", values, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotIn(List<String> values) {
            addCriterion("BZ not in", values, "bz");
            return (Criteria) this;
        }

        public Criteria andBzBetween(String value1, String value2) {
            addCriterion("BZ between", value1, value2, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotBetween(String value1, String value2) {
            addCriterion("BZ not between", value1, value2, "bz");
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