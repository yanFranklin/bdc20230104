package cn.gtmap.realestate.common.core.domain.natural;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

public class ZrzyZsDoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ZrzyZsDoExample() {
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

        public Criteria andZsidIsNull() {
            addCriterion("ZSID is null");
            return (Criteria) this;
        }

        public Criteria andZsidIsNotNull() {
            addCriterion("ZSID is not null");
            return (Criteria) this;
        }

        public Criteria andZsidEqualTo(String value) {
            addCriterion("ZSID =", value, "zsid");
            return (Criteria) this;
        }

        public Criteria andZsidNotEqualTo(String value) {
            addCriterion("ZSID <>", value, "zsid");
            return (Criteria) this;
        }

        public Criteria andZsidGreaterThan(String value) {
            addCriterion("ZSID >", value, "zsid");
            return (Criteria) this;
        }

        public Criteria andZsidGreaterThanOrEqualTo(String value) {
            addCriterion("ZSID >=", value, "zsid");
            return (Criteria) this;
        }

        public Criteria andZsidLessThan(String value) {
            addCriterion("ZSID <", value, "zsid");
            return (Criteria) this;
        }

        public Criteria andZsidLessThanOrEqualTo(String value) {
            addCriterion("ZSID <=", value, "zsid");
            return (Criteria) this;
        }

        public Criteria andZsidLike(String value) {
            addCriterion("ZSID like", value, "zsid");
            return (Criteria) this;
        }

        public Criteria andZsidNotLike(String value) {
            addCriterion("ZSID not like", value, "zsid");
            return (Criteria) this;
        }

        public Criteria andZsidIn(List<String> values) {
            addCriterion("ZSID in", values, "zsid");
            return (Criteria) this;
        }

        public Criteria andZsidNotIn(List<String> values) {
            addCriterion("ZSID not in", values, "zsid");
            return (Criteria) this;
        }

        public Criteria andZsidBetween(String value1, String value2) {
            addCriterion("ZSID between", value1, value2, "zsid");
            return (Criteria) this;
        }

        public Criteria andZsidNotBetween(String value1, String value2) {
            addCriterion("ZSID not between", value1, value2, "zsid");
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

        public Criteria andZrzycqzhIsNull() {
            addCriterion("ZRZYCQZH is null");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhIsNotNull() {
            addCriterion("ZRZYCQZH is not null");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhEqualTo(String value) {
            addCriterion("ZRZYCQZH =", value, "zrzycqzh");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhNotEqualTo(String value) {
            addCriterion("ZRZYCQZH <>", value, "zrzycqzh");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhGreaterThan(String value) {
            addCriterion("ZRZYCQZH >", value, "zrzycqzh");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhGreaterThanOrEqualTo(String value) {
            addCriterion("ZRZYCQZH >=", value, "zrzycqzh");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhLessThan(String value) {
            addCriterion("ZRZYCQZH <", value, "zrzycqzh");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhLessThanOrEqualTo(String value) {
            addCriterion("ZRZYCQZH <=", value, "zrzycqzh");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhLike(String value) {
            addCriterion("ZRZYCQZH like", value, "zrzycqzh");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhNotLike(String value) {
            addCriterion("ZRZYCQZH not like", value, "zrzycqzh");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhIn(List<String> values) {
            addCriterion("ZRZYCQZH in", values, "zrzycqzh");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhNotIn(List<String> values) {
            addCriterion("ZRZYCQZH not in", values, "zrzycqzh");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhBetween(String value1, String value2) {
            addCriterion("ZRZYCQZH between", value1, value2, "zrzycqzh");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhNotBetween(String value1, String value2) {
            addCriterion("ZRZYCQZH not between", value1, value2, "zrzycqzh");
            return (Criteria) this;
        }

        public Criteria andZhlshIsNull() {
            addCriterion("ZHLSH is null");
            return (Criteria) this;
        }

        public Criteria andZhlshIsNotNull() {
            addCriterion("ZHLSH is not null");
            return (Criteria) this;
        }

        public Criteria andZhlshEqualTo(String value) {
            addCriterion("ZHLSH =", value, "zhlsh");
            return (Criteria) this;
        }

        public Criteria andZhlshNotEqualTo(String value) {
            addCriterion("ZHLSH <>", value, "zhlsh");
            return (Criteria) this;
        }

        public Criteria andZhlshGreaterThan(String value) {
            addCriterion("ZHLSH >", value, "zhlsh");
            return (Criteria) this;
        }

        public Criteria andZhlshGreaterThanOrEqualTo(String value) {
            addCriterion("ZHLSH >=", value, "zhlsh");
            return (Criteria) this;
        }

        public Criteria andZhlshLessThan(String value) {
            addCriterion("ZHLSH <", value, "zhlsh");
            return (Criteria) this;
        }

        public Criteria andZhlshLessThanOrEqualTo(String value) {
            addCriterion("ZHLSH <=", value, "zhlsh");
            return (Criteria) this;
        }

        public Criteria andZhlshLike(String value) {
            addCriterion("ZHLSH like", value, "zhlsh");
            return (Criteria) this;
        }

        public Criteria andZhlshNotLike(String value) {
            addCriterion("ZHLSH not like", value, "zhlsh");
            return (Criteria) this;
        }

        public Criteria andZhlshIn(List<String> values) {
            addCriterion("ZHLSH in", values, "zhlsh");
            return (Criteria) this;
        }

        public Criteria andZhlshNotIn(List<String> values) {
            addCriterion("ZHLSH not in", values, "zhlsh");
            return (Criteria) this;
        }

        public Criteria andZhlshBetween(String value1, String value2) {
            addCriterion("ZHLSH between", value1, value2, "zhlsh");
            return (Criteria) this;
        }

        public Criteria andZhlshNotBetween(String value1, String value2) {
            addCriterion("ZHLSH not between", value1, value2, "zhlsh");
            return (Criteria) this;
        }

        public Criteria andNfIsNull() {
            addCriterion("NF is null");
            return (Criteria) this;
        }

        public Criteria andNfIsNotNull() {
            addCriterion("NF is not null");
            return (Criteria) this;
        }

        public Criteria andNfEqualTo(Short value) {
            addCriterion("NF =", value, "nf");
            return (Criteria) this;
        }

        public Criteria andNfNotEqualTo(Short value) {
            addCriterion("NF <>", value, "nf");
            return (Criteria) this;
        }

        public Criteria andNfGreaterThan(Short value) {
            addCriterion("NF >", value, "nf");
            return (Criteria) this;
        }

        public Criteria andNfGreaterThanOrEqualTo(Short value) {
            addCriterion("NF >=", value, "nf");
            return (Criteria) this;
        }

        public Criteria andNfLessThan(Short value) {
            addCriterion("NF <", value, "nf");
            return (Criteria) this;
        }

        public Criteria andNfLessThanOrEqualTo(Short value) {
            addCriterion("NF <=", value, "nf");
            return (Criteria) this;
        }

        public Criteria andNfIn(List<Short> values) {
            addCriterion("NF in", values, "nf");
            return (Criteria) this;
        }

        public Criteria andNfNotIn(List<Short> values) {
            addCriterion("NF not in", values, "nf");
            return (Criteria) this;
        }

        public Criteria andNfBetween(Short value1, Short value2) {
            addCriterion("NF between", value1, value2, "nf");
            return (Criteria) this;
        }

        public Criteria andNfNotBetween(Short value1, Short value2) {
            addCriterion("NF not between", value1, value2, "nf");
            return (Criteria) this;
        }

        public Criteria andQzysxlhIsNull() {
            addCriterion("QZYSXLH is null");
            return (Criteria) this;
        }

        public Criteria andQzysxlhIsNotNull() {
            addCriterion("QZYSXLH is not null");
            return (Criteria) this;
        }

        public Criteria andQzysxlhEqualTo(String value) {
            addCriterion("QZYSXLH =", value, "qzysxlh");
            return (Criteria) this;
        }

        public Criteria andQzysxlhNotEqualTo(String value) {
            addCriterion("QZYSXLH <>", value, "qzysxlh");
            return (Criteria) this;
        }

        public Criteria andQzysxlhGreaterThan(String value) {
            addCriterion("QZYSXLH >", value, "qzysxlh");
            return (Criteria) this;
        }

        public Criteria andQzysxlhGreaterThanOrEqualTo(String value) {
            addCriterion("QZYSXLH >=", value, "qzysxlh");
            return (Criteria) this;
        }

        public Criteria andQzysxlhLessThan(String value) {
            addCriterion("QZYSXLH <", value, "qzysxlh");
            return (Criteria) this;
        }

        public Criteria andQzysxlhLessThanOrEqualTo(String value) {
            addCriterion("QZYSXLH <=", value, "qzysxlh");
            return (Criteria) this;
        }

        public Criteria andQzysxlhLike(String value) {
            addCriterion("QZYSXLH like", value, "qzysxlh");
            return (Criteria) this;
        }

        public Criteria andQzysxlhNotLike(String value) {
            addCriterion("QZYSXLH not like", value, "qzysxlh");
            return (Criteria) this;
        }

        public Criteria andQzysxlhIn(List<String> values) {
            addCriterion("QZYSXLH in", values, "qzysxlh");
            return (Criteria) this;
        }

        public Criteria andQzysxlhNotIn(List<String> values) {
            addCriterion("QZYSXLH not in", values, "qzysxlh");
            return (Criteria) this;
        }

        public Criteria andQzysxlhBetween(String value1, String value2) {
            addCriterion("QZYSXLH between", value1, value2, "qzysxlh");
            return (Criteria) this;
        }

        public Criteria andQzysxlhNotBetween(String value1, String value2) {
            addCriterion("QZYSXLH not between", value1, value2, "qzysxlh");
            return (Criteria) this;
        }

        public Criteria andZrzyzkIsNull() {
            addCriterion("ZRZYZK is null");
            return (Criteria) this;
        }

        public Criteria andZrzyzkIsNotNull() {
            addCriterion("ZRZYZK is not null");
            return (Criteria) this;
        }

        public Criteria andZrzyzkEqualTo(String value) {
            addCriterion("ZRZYZK =", value, "zrzyzk");
            return (Criteria) this;
        }

        public Criteria andZrzyzkNotEqualTo(String value) {
            addCriterion("ZRZYZK <>", value, "zrzyzk");
            return (Criteria) this;
        }

        public Criteria andZrzyzkGreaterThan(String value) {
            addCriterion("ZRZYZK >", value, "zrzyzk");
            return (Criteria) this;
        }

        public Criteria andZrzyzkGreaterThanOrEqualTo(String value) {
            addCriterion("ZRZYZK >=", value, "zrzyzk");
            return (Criteria) this;
        }

        public Criteria andZrzyzkLessThan(String value) {
            addCriterion("ZRZYZK <", value, "zrzyzk");
            return (Criteria) this;
        }

        public Criteria andZrzyzkLessThanOrEqualTo(String value) {
            addCriterion("ZRZYZK <=", value, "zrzyzk");
            return (Criteria) this;
        }

        public Criteria andZrzyzkLike(String value) {
            addCriterion("ZRZYZK like", value, "zrzyzk");
            return (Criteria) this;
        }

        public Criteria andZrzyzkNotLike(String value) {
            addCriterion("ZRZYZK not like", value, "zrzyzk");
            return (Criteria) this;
        }

        public Criteria andZrzyzkIn(List<String> values) {
            addCriterion("ZRZYZK in", values, "zrzyzk");
            return (Criteria) this;
        }

        public Criteria andZrzyzkNotIn(List<String> values) {
            addCriterion("ZRZYZK not in", values, "zrzyzk");
            return (Criteria) this;
        }

        public Criteria andZrzyzkBetween(String value1, String value2) {
            addCriterion("ZRZYZK between", value1, value2, "zrzyzk");
            return (Criteria) this;
        }

        public Criteria andZrzyzkNotBetween(String value1, String value2) {
            addCriterion("ZRZYZK not between", value1, value2, "zrzyzk");
            return (Criteria) this;
        }

        public Criteria andFjIsNull() {
            addCriterion("FJ is null");
            return (Criteria) this;
        }

        public Criteria andFjIsNotNull() {
            addCriterion("FJ is not null");
            return (Criteria) this;
        }

        public Criteria andFjEqualTo(String value) {
            addCriterion("FJ =", value, "fj");
            return (Criteria) this;
        }

        public Criteria andFjNotEqualTo(String value) {
            addCriterion("FJ <>", value, "fj");
            return (Criteria) this;
        }

        public Criteria andFjGreaterThan(String value) {
            addCriterion("FJ >", value, "fj");
            return (Criteria) this;
        }

        public Criteria andFjGreaterThanOrEqualTo(String value) {
            addCriterion("FJ >=", value, "fj");
            return (Criteria) this;
        }

        public Criteria andFjLessThan(String value) {
            addCriterion("FJ <", value, "fj");
            return (Criteria) this;
        }

        public Criteria andFjLessThanOrEqualTo(String value) {
            addCriterion("FJ <=", value, "fj");
            return (Criteria) this;
        }

        public Criteria andFjLike(String value) {
            addCriterion("FJ like", value, "fj");
            return (Criteria) this;
        }

        public Criteria andFjNotLike(String value) {
            addCriterion("FJ not like", value, "fj");
            return (Criteria) this;
        }

        public Criteria andFjIn(List<String> values) {
            addCriterion("FJ in", values, "fj");
            return (Criteria) this;
        }

        public Criteria andFjNotIn(List<String> values) {
            addCriterion("FJ not in", values, "fj");
            return (Criteria) this;
        }

        public Criteria andFjBetween(String value1, String value2) {
            addCriterion("FJ between", value1, value2, "fj");
            return (Criteria) this;
        }

        public Criteria andFjNotBetween(String value1, String value2) {
            addCriterion("FJ not between", value1, value2, "fj");
            return (Criteria) this;
        }

        public Criteria andSqsjcIsNull() {
            addCriterion("SQSJC is null");
            return (Criteria) this;
        }

        public Criteria andSqsjcIsNotNull() {
            addCriterion("SQSJC is not null");
            return (Criteria) this;
        }

        public Criteria andSqsjcEqualTo(String value) {
            addCriterion("SQSJC =", value, "sqsjc");
            return (Criteria) this;
        }

        public Criteria andSqsjcNotEqualTo(String value) {
            addCriterion("SQSJC <>", value, "sqsjc");
            return (Criteria) this;
        }

        public Criteria andSqsjcGreaterThan(String value) {
            addCriterion("SQSJC >", value, "sqsjc");
            return (Criteria) this;
        }

        public Criteria andSqsjcGreaterThanOrEqualTo(String value) {
            addCriterion("SQSJC >=", value, "sqsjc");
            return (Criteria) this;
        }

        public Criteria andSqsjcLessThan(String value) {
            addCriterion("SQSJC <", value, "sqsjc");
            return (Criteria) this;
        }

        public Criteria andSqsjcLessThanOrEqualTo(String value) {
            addCriterion("SQSJC <=", value, "sqsjc");
            return (Criteria) this;
        }

        public Criteria andSqsjcLike(String value) {
            addCriterion("SQSJC like", value, "sqsjc");
            return (Criteria) this;
        }

        public Criteria andSqsjcNotLike(String value) {
            addCriterion("SQSJC not like", value, "sqsjc");
            return (Criteria) this;
        }

        public Criteria andSqsjcIn(List<String> values) {
            addCriterion("SQSJC in", values, "sqsjc");
            return (Criteria) this;
        }

        public Criteria andSqsjcNotIn(List<String> values) {
            addCriterion("SQSJC not in", values, "sqsjc");
            return (Criteria) this;
        }

        public Criteria andSqsjcBetween(String value1, String value2) {
            addCriterion("SQSJC between", value1, value2, "sqsjc");
            return (Criteria) this;
        }

        public Criteria andSqsjcNotBetween(String value1, String value2) {
            addCriterion("SQSJC not between", value1, value2, "sqsjc");
            return (Criteria) this;
        }

        public Criteria andSzqxqcIsNull() {
            addCriterion("SZQXQC is null");
            return (Criteria) this;
        }

        public Criteria andSzqxqcIsNotNull() {
            addCriterion("SZQXQC is not null");
            return (Criteria) this;
        }

        public Criteria andSzqxqcEqualTo(String value) {
            addCriterion("SZQXQC =", value, "szqxqc");
            return (Criteria) this;
        }

        public Criteria andSzqxqcNotEqualTo(String value) {
            addCriterion("SZQXQC <>", value, "szqxqc");
            return (Criteria) this;
        }

        public Criteria andSzqxqcGreaterThan(String value) {
            addCriterion("SZQXQC >", value, "szqxqc");
            return (Criteria) this;
        }

        public Criteria andSzqxqcGreaterThanOrEqualTo(String value) {
            addCriterion("SZQXQC >=", value, "szqxqc");
            return (Criteria) this;
        }

        public Criteria andSzqxqcLessThan(String value) {
            addCriterion("SZQXQC <", value, "szqxqc");
            return (Criteria) this;
        }

        public Criteria andSzqxqcLessThanOrEqualTo(String value) {
            addCriterion("SZQXQC <=", value, "szqxqc");
            return (Criteria) this;
        }

        public Criteria andSzqxqcLike(String value) {
            addCriterion("SZQXQC like", value, "szqxqc");
            return (Criteria) this;
        }

        public Criteria andSzqxqcNotLike(String value) {
            addCriterion("SZQXQC not like", value, "szqxqc");
            return (Criteria) this;
        }

        public Criteria andSzqxqcIn(List<String> values) {
            addCriterion("SZQXQC in", values, "szqxqc");
            return (Criteria) this;
        }

        public Criteria andSzqxqcNotIn(List<String> values) {
            addCriterion("SZQXQC not in", values, "szqxqc");
            return (Criteria) this;
        }

        public Criteria andSzqxqcBetween(String value1, String value2) {
            addCriterion("SZQXQC between", value1, value2, "szqxqc");
            return (Criteria) this;
        }

        public Criteria andSzqxqcNotBetween(String value1, String value2) {
            addCriterion("SZQXQC not between", value1, value2, "szqxqc");
            return (Criteria) this;
        }

        public Criteria andFzrIsNull() {
            addCriterion("FZR is null");
            return (Criteria) this;
        }

        public Criteria andFzrIsNotNull() {
            addCriterion("FZR is not null");
            return (Criteria) this;
        }

        public Criteria andFzrEqualTo(String value) {
            addCriterion("FZR =", value, "fzr");
            return (Criteria) this;
        }

        public Criteria andFzrNotEqualTo(String value) {
            addCriterion("FZR <>", value, "fzr");
            return (Criteria) this;
        }

        public Criteria andFzrGreaterThan(String value) {
            addCriterion("FZR >", value, "fzr");
            return (Criteria) this;
        }

        public Criteria andFzrGreaterThanOrEqualTo(String value) {
            addCriterion("FZR >=", value, "fzr");
            return (Criteria) this;
        }

        public Criteria andFzrLessThan(String value) {
            addCriterion("FZR <", value, "fzr");
            return (Criteria) this;
        }

        public Criteria andFzrLessThanOrEqualTo(String value) {
            addCriterion("FZR <=", value, "fzr");
            return (Criteria) this;
        }

        public Criteria andFzrLike(String value) {
            addCriterion("FZR like", value, "fzr");
            return (Criteria) this;
        }

        public Criteria andFzrNotLike(String value) {
            addCriterion("FZR not like", value, "fzr");
            return (Criteria) this;
        }

        public Criteria andFzrIn(List<String> values) {
            addCriterion("FZR in", values, "fzr");
            return (Criteria) this;
        }

        public Criteria andFzrNotIn(List<String> values) {
            addCriterion("FZR not in", values, "fzr");
            return (Criteria) this;
        }

        public Criteria andFzrBetween(String value1, String value2) {
            addCriterion("FZR between", value1, value2, "fzr");
            return (Criteria) this;
        }

        public Criteria andFzrNotBetween(String value1, String value2) {
            addCriterion("FZR not between", value1, value2, "fzr");
            return (Criteria) this;
        }

        public Criteria andFzridIsNull() {
            addCriterion("FZRID is null");
            return (Criteria) this;
        }

        public Criteria andFzridIsNotNull() {
            addCriterion("FZRID is not null");
            return (Criteria) this;
        }

        public Criteria andFzridEqualTo(String value) {
            addCriterion("FZRID =", value, "fzrid");
            return (Criteria) this;
        }

        public Criteria andFzridNotEqualTo(String value) {
            addCriterion("FZRID <>", value, "fzrid");
            return (Criteria) this;
        }

        public Criteria andFzridGreaterThan(String value) {
            addCriterion("FZRID >", value, "fzrid");
            return (Criteria) this;
        }

        public Criteria andFzridGreaterThanOrEqualTo(String value) {
            addCriterion("FZRID >=", value, "fzrid");
            return (Criteria) this;
        }

        public Criteria andFzridLessThan(String value) {
            addCriterion("FZRID <", value, "fzrid");
            return (Criteria) this;
        }

        public Criteria andFzridLessThanOrEqualTo(String value) {
            addCriterion("FZRID <=", value, "fzrid");
            return (Criteria) this;
        }

        public Criteria andFzridLike(String value) {
            addCriterion("FZRID like", value, "fzrid");
            return (Criteria) this;
        }

        public Criteria andFzridNotLike(String value) {
            addCriterion("FZRID not like", value, "fzrid");
            return (Criteria) this;
        }

        public Criteria andFzridIn(List<String> values) {
            addCriterion("FZRID in", values, "fzrid");
            return (Criteria) this;
        }

        public Criteria andFzridNotIn(List<String> values) {
            addCriterion("FZRID not in", values, "fzrid");
            return (Criteria) this;
        }

        public Criteria andFzridBetween(String value1, String value2) {
            addCriterion("FZRID between", value1, value2, "fzrid");
            return (Criteria) this;
        }

        public Criteria andFzridNotBetween(String value1, String value2) {
            addCriterion("FZRID not between", value1, value2, "fzrid");
            return (Criteria) this;
        }

        public Criteria andFzsjIsNull() {
            addCriterion("FZSJ is null");
            return (Criteria) this;
        }

        public Criteria andFzsjIsNotNull() {
            addCriterion("FZSJ is not null");
            return (Criteria) this;
        }

        public Criteria andFzsjEqualTo(LocalDateTime value) {
            addCriterion("FZSJ =", value, "fzsj");
            return (Criteria) this;
        }

        public Criteria andFzsjNotEqualTo(LocalDateTime value) {
            addCriterion("FZSJ <>", value, "fzsj");
            return (Criteria) this;
        }

        public Criteria andFzsjGreaterThan(LocalDateTime value) {
            addCriterion("FZSJ >", value, "fzsj");
            return (Criteria) this;
        }

        public Criteria andFzsjGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("FZSJ >=", value, "fzsj");
            return (Criteria) this;
        }

        public Criteria andFzsjLessThan(LocalDateTime value) {
            addCriterion("FZSJ <", value, "fzsj");
            return (Criteria) this;
        }

        public Criteria andFzsjLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("FZSJ <=", value, "fzsj");
            return (Criteria) this;
        }

        public Criteria andFzsjIn(List<LocalDateTime> values) {
            addCriterion("FZSJ in", values, "fzsj");
            return (Criteria) this;
        }

        public Criteria andFzsjNotIn(List<LocalDateTime> values) {
            addCriterion("FZSJ not in", values, "fzsj");
            return (Criteria) this;
        }

        public Criteria andFzsjBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("FZSJ between", value1, value2, "fzsj");
            return (Criteria) this;
        }

        public Criteria andFzsjNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("FZSJ not between", value1, value2, "fzsj");
            return (Criteria) this;
        }

        public Criteria andSzrIsNull() {
            addCriterion("SZR is null");
            return (Criteria) this;
        }

        public Criteria andSzrIsNotNull() {
            addCriterion("SZR is not null");
            return (Criteria) this;
        }

        public Criteria andSzrEqualTo(String value) {
            addCriterion("SZR =", value, "szr");
            return (Criteria) this;
        }

        public Criteria andSzrNotEqualTo(String value) {
            addCriterion("SZR <>", value, "szr");
            return (Criteria) this;
        }

        public Criteria andSzrGreaterThan(String value) {
            addCriterion("SZR >", value, "szr");
            return (Criteria) this;
        }

        public Criteria andSzrGreaterThanOrEqualTo(String value) {
            addCriterion("SZR >=", value, "szr");
            return (Criteria) this;
        }

        public Criteria andSzrLessThan(String value) {
            addCriterion("SZR <", value, "szr");
            return (Criteria) this;
        }

        public Criteria andSzrLessThanOrEqualTo(String value) {
            addCriterion("SZR <=", value, "szr");
            return (Criteria) this;
        }

        public Criteria andSzrLike(String value) {
            addCriterion("SZR like", value, "szr");
            return (Criteria) this;
        }

        public Criteria andSzrNotLike(String value) {
            addCriterion("SZR not like", value, "szr");
            return (Criteria) this;
        }

        public Criteria andSzrIn(List<String> values) {
            addCriterion("SZR in", values, "szr");
            return (Criteria) this;
        }

        public Criteria andSzrNotIn(List<String> values) {
            addCriterion("SZR not in", values, "szr");
            return (Criteria) this;
        }

        public Criteria andSzrBetween(String value1, String value2) {
            addCriterion("SZR between", value1, value2, "szr");
            return (Criteria) this;
        }

        public Criteria andSzrNotBetween(String value1, String value2) {
            addCriterion("SZR not between", value1, value2, "szr");
            return (Criteria) this;
        }

        public Criteria andSzridIsNull() {
            addCriterion("SZRID is null");
            return (Criteria) this;
        }

        public Criteria andSzridIsNotNull() {
            addCriterion("SZRID is not null");
            return (Criteria) this;
        }

        public Criteria andSzridEqualTo(String value) {
            addCriterion("SZRID =", value, "szrid");
            return (Criteria) this;
        }

        public Criteria andSzridNotEqualTo(String value) {
            addCriterion("SZRID <>", value, "szrid");
            return (Criteria) this;
        }

        public Criteria andSzridGreaterThan(String value) {
            addCriterion("SZRID >", value, "szrid");
            return (Criteria) this;
        }

        public Criteria andSzridGreaterThanOrEqualTo(String value) {
            addCriterion("SZRID >=", value, "szrid");
            return (Criteria) this;
        }

        public Criteria andSzridLessThan(String value) {
            addCriterion("SZRID <", value, "szrid");
            return (Criteria) this;
        }

        public Criteria andSzridLessThanOrEqualTo(String value) {
            addCriterion("SZRID <=", value, "szrid");
            return (Criteria) this;
        }

        public Criteria andSzridLike(String value) {
            addCriterion("SZRID like", value, "szrid");
            return (Criteria) this;
        }

        public Criteria andSzridNotLike(String value) {
            addCriterion("SZRID not like", value, "szrid");
            return (Criteria) this;
        }

        public Criteria andSzridIn(List<String> values) {
            addCriterion("SZRID in", values, "szrid");
            return (Criteria) this;
        }

        public Criteria andSzridNotIn(List<String> values) {
            addCriterion("SZRID not in", values, "szrid");
            return (Criteria) this;
        }

        public Criteria andSzridBetween(String value1, String value2) {
            addCriterion("SZRID between", value1, value2, "szrid");
            return (Criteria) this;
        }

        public Criteria andSzridNotBetween(String value1, String value2) {
            addCriterion("SZRID not between", value1, value2, "szrid");
            return (Criteria) this;
        }

        public Criteria andSzsjIsNull() {
            addCriterion("SZSJ is null");
            return (Criteria) this;
        }

        public Criteria andSzsjIsNotNull() {
            addCriterion("SZSJ is not null");
            return (Criteria) this;
        }

        public Criteria andSzsjEqualTo(LocalDateTime value) {
            addCriterion("SZSJ =", value, "szsj");
            return (Criteria) this;
        }

        public Criteria andSzsjNotEqualTo(LocalDateTime value) {
            addCriterion("SZSJ <>", value, "szsj");
            return (Criteria) this;
        }

        public Criteria andSzsjGreaterThan(LocalDateTime value) {
            addCriterion("SZSJ >", value, "szsj");
            return (Criteria) this;
        }

        public Criteria andSzsjGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("SZSJ >=", value, "szsj");
            return (Criteria) this;
        }

        public Criteria andSzsjLessThan(LocalDateTime value) {
            addCriterion("SZSJ <", value, "szsj");
            return (Criteria) this;
        }

        public Criteria andSzsjLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("SZSJ <=", value, "szsj");
            return (Criteria) this;
        }

        public Criteria andSzsjIn(List<LocalDateTime> values) {
            addCriterion("SZSJ in", values, "szsj");
            return (Criteria) this;
        }

        public Criteria andSzsjNotIn(List<LocalDateTime> values) {
            addCriterion("SZSJ not in", values, "szsj");
            return (Criteria) this;
        }

        public Criteria andSzsjBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("SZSJ between", value1, value2, "szsj");
            return (Criteria) this;
        }

        public Criteria andSzsjNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("SZSJ not between", value1, value2, "szsj");
            return (Criteria) this;
        }

        public Criteria andEwmnrIsNull() {
            addCriterion("EWMNR is null");
            return (Criteria) this;
        }

        public Criteria andEwmnrIsNotNull() {
            addCriterion("EWMNR is not null");
            return (Criteria) this;
        }

        public Criteria andEwmnrEqualTo(String value) {
            addCriterion("EWMNR =", value, "ewmnr");
            return (Criteria) this;
        }

        public Criteria andEwmnrNotEqualTo(String value) {
            addCriterion("EWMNR <>", value, "ewmnr");
            return (Criteria) this;
        }

        public Criteria andEwmnrGreaterThan(String value) {
            addCriterion("EWMNR >", value, "ewmnr");
            return (Criteria) this;
        }

        public Criteria andEwmnrGreaterThanOrEqualTo(String value) {
            addCriterion("EWMNR >=", value, "ewmnr");
            return (Criteria) this;
        }

        public Criteria andEwmnrLessThan(String value) {
            addCriterion("EWMNR <", value, "ewmnr");
            return (Criteria) this;
        }

        public Criteria andEwmnrLessThanOrEqualTo(String value) {
            addCriterion("EWMNR <=", value, "ewmnr");
            return (Criteria) this;
        }

        public Criteria andEwmnrLike(String value) {
            addCriterion("EWMNR like", value, "ewmnr");
            return (Criteria) this;
        }

        public Criteria andEwmnrNotLike(String value) {
            addCriterion("EWMNR not like", value, "ewmnr");
            return (Criteria) this;
        }

        public Criteria andEwmnrIn(List<String> values) {
            addCriterion("EWMNR in", values, "ewmnr");
            return (Criteria) this;
        }

        public Criteria andEwmnrNotIn(List<String> values) {
            addCriterion("EWMNR not in", values, "ewmnr");
            return (Criteria) this;
        }

        public Criteria andEwmnrBetween(String value1, String value2) {
            addCriterion("EWMNR between", value1, value2, "ewmnr");
            return (Criteria) this;
        }

        public Criteria andEwmnrNotBetween(String value1, String value2) {
            addCriterion("EWMNR not between", value1, value2, "ewmnr");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhjcIsNull() {
            addCriterion("ZRZYCQZHJC is null");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhjcIsNotNull() {
            addCriterion("ZRZYCQZHJC is not null");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhjcEqualTo(String value) {
            addCriterion("ZRZYCQZHJC =", value, "zrzycqzhjc");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhjcNotEqualTo(String value) {
            addCriterion("ZRZYCQZHJC <>", value, "zrzycqzhjc");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhjcGreaterThan(String value) {
            addCriterion("ZRZYCQZHJC >", value, "zrzycqzhjc");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhjcGreaterThanOrEqualTo(String value) {
            addCriterion("ZRZYCQZHJC >=", value, "zrzycqzhjc");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhjcLessThan(String value) {
            addCriterion("ZRZYCQZHJC <", value, "zrzycqzhjc");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhjcLessThanOrEqualTo(String value) {
            addCriterion("ZRZYCQZHJC <=", value, "zrzycqzhjc");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhjcLike(String value) {
            addCriterion("ZRZYCQZHJC like", value, "zrzycqzhjc");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhjcNotLike(String value) {
            addCriterion("ZRZYCQZHJC not like", value, "zrzycqzhjc");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhjcIn(List<String> values) {
            addCriterion("ZRZYCQZHJC in", values, "zrzycqzhjc");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhjcNotIn(List<String> values) {
            addCriterion("ZRZYCQZHJC not in", values, "zrzycqzhjc");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhjcBetween(String value1, String value2) {
            addCriterion("ZRZYCQZHJC between", value1, value2, "zrzycqzhjc");
            return (Criteria) this;
        }

        public Criteria andZrzycqzhjcNotBetween(String value1, String value2) {
            addCriterion("ZRZYCQZHJC not between", value1, value2, "zrzycqzhjc");
            return (Criteria) this;
        }

        public Criteria andZrzydyhIsNull() {
            addCriterion("ZRZYDYH is null");
            return (Criteria) this;
        }

        public Criteria andZrzydyhIsNotNull() {
            addCriterion("ZRZYDYH is not null");
            return (Criteria) this;
        }

        public Criteria andZrzydyhEqualTo(String value) {
            addCriterion("ZRZYDYH =", value, "zrzydjdyh");
            return (Criteria) this;
        }

        public Criteria andZrzydyhNotEqualTo(String value) {
            addCriterion("ZRZYDYH <>", value, "zrzydjdyh");
            return (Criteria) this;
        }

        public Criteria andZrzydyhGreaterThan(String value) {
            addCriterion("ZRZYDYH >", value, "zrzydjdyh");
            return (Criteria) this;
        }

        public Criteria andZrzydyhGreaterThanOrEqualTo(String value) {
            addCriterion("ZRZYDYH >=", value, "zrzydjdyh");
            return (Criteria) this;
        }

        public Criteria andZrzydyhLessThan(String value) {
            addCriterion("ZRZYDYH <", value, "zrzydjdyh");
            return (Criteria) this;
        }

        public Criteria andZrzydyhLessThanOrEqualTo(String value) {
            addCriterion("ZRZYDYH <=", value, "zrzydjdyh");
            return (Criteria) this;
        }

        public Criteria andZrzydyhLike(String value) {
            addCriterion("ZRZYDYH like", value, "zrzydjdyh");
            return (Criteria) this;
        }

        public Criteria andZrzydyhNotLike(String value) {
            addCriterion("ZRZYDYH not like", value, "zrzydjdyh");
            return (Criteria) this;
        }

        public Criteria andZrzydyhIn(List<String> values) {
            addCriterion("ZRZYDYH in", values, "zrzydjdyh");
            return (Criteria) this;
        }

        public Criteria andZrzydyhNotIn(List<String> values) {
            addCriterion("ZRZYDYH not in", values, "zrzydjdyh");
            return (Criteria) this;
        }

        public Criteria andZrzydyhBetween(String value1, String value2) {
            addCriterion("ZRZYDYH between", value1, value2, "zrzydjdyh");
            return (Criteria) this;
        }

        public Criteria andZrzydyhNotBetween(String value1, String value2) {
            addCriterion("ZRZYDYH not between", value1, value2, "zrzydjdyh");
            return (Criteria) this;
        }

        public Criteria andDjdymcIsNull() {
            addCriterion("DJDYMC is null");
            return (Criteria) this;
        }

        public Criteria andDjdymcIsNotNull() {
            addCriterion("DJDYMC is not null");
            return (Criteria) this;
        }

        public Criteria andDjdymcEqualTo(String value) {
            addCriterion("DJDYMC =", value, "djdymc");
            return (Criteria) this;
        }

        public Criteria andDjdymcNotEqualTo(String value) {
            addCriterion("DJDYMC <>", value, "djdymc");
            return (Criteria) this;
        }

        public Criteria andDjdymcGreaterThan(String value) {
            addCriterion("DJDYMC >", value, "djdymc");
            return (Criteria) this;
        }

        public Criteria andDjdymcGreaterThanOrEqualTo(String value) {
            addCriterion("DJDYMC >=", value, "djdymc");
            return (Criteria) this;
        }

        public Criteria andDjdymcLessThan(String value) {
            addCriterion("DJDYMC <", value, "djdymc");
            return (Criteria) this;
        }

        public Criteria andDjdymcLessThanOrEqualTo(String value) {
            addCriterion("DJDYMC <=", value, "djdymc");
            return (Criteria) this;
        }

        public Criteria andDjdymcLike(String value) {
            addCriterion("DJDYMC like", value, "djdymc");
            return (Criteria) this;
        }

        public Criteria andDjdymcNotLike(String value) {
            addCriterion("DJDYMC not like", value, "djdymc");
            return (Criteria) this;
        }

        public Criteria andDjdymcIn(List<String> values) {
            addCriterion("DJDYMC in", values, "djdymc");
            return (Criteria) this;
        }

        public Criteria andDjdymcNotIn(List<String> values) {
            addCriterion("DJDYMC not in", values, "djdymc");
            return (Criteria) this;
        }

        public Criteria andDjdymcBetween(String value1, String value2) {
            addCriterion("DJDYMC between", value1, value2, "djdymc");
            return (Criteria) this;
        }

        public Criteria andDjdymcNotBetween(String value1, String value2) {
            addCriterion("DJDYMC not between", value1, value2, "djdymc");
            return (Criteria) this;
        }

        public Criteria andDjdylxIsNull() {
            addCriterion("DJDYLX is null");
            return (Criteria) this;
        }

        public Criteria andDjdylxIsNotNull() {
            addCriterion("DJDYLX is not null");
            return (Criteria) this;
        }

        public Criteria andDjdylxEqualTo(String value) {
            addCriterion("DJDYLX =", value, "djdylx");
            return (Criteria) this;
        }

        public Criteria andDjdylxNotEqualTo(String value) {
            addCriterion("DJDYLX <>", value, "djdylx");
            return (Criteria) this;
        }

        public Criteria andDjdylxGreaterThan(String value) {
            addCriterion("DJDYLX >", value, "djdylx");
            return (Criteria) this;
        }

        public Criteria andDjdylxGreaterThanOrEqualTo(String value) {
            addCriterion("DJDYLX >=", value, "djdylx");
            return (Criteria) this;
        }

        public Criteria andDjdylxLessThan(String value) {
            addCriterion("DJDYLX <", value, "djdylx");
            return (Criteria) this;
        }

        public Criteria andDjdylxLessThanOrEqualTo(String value) {
            addCriterion("DJDYLX <=", value, "djdylx");
            return (Criteria) this;
        }

        public Criteria andDjdylxLike(String value) {
            addCriterion("DJDYLX like", value, "djdylx");
            return (Criteria) this;
        }

        public Criteria andDjdylxNotLike(String value) {
            addCriterion("DJDYLX not like", value, "djdylx");
            return (Criteria) this;
        }

        public Criteria andDjdylxIn(List<String> values) {
            addCriterion("DJDYLX in", values, "djdylx");
            return (Criteria) this;
        }

        public Criteria andDjdylxNotIn(List<String> values) {
            addCriterion("DJDYLX not in", values, "djdylx");
            return (Criteria) this;
        }

        public Criteria andDjdylxBetween(String value1, String value2) {
            addCriterion("DJDYLX between", value1, value2, "djdylx");
            return (Criteria) this;
        }

        public Criteria andDjdylxNotBetween(String value1, String value2) {
            addCriterion("DJDYLX not between", value1, value2, "djdylx");
            return (Criteria) this;
        }

        public Criteria andKjfwIsNull() {
            addCriterion("KJFW is null");
            return (Criteria) this;
        }

        public Criteria andKjfwIsNotNull() {
            addCriterion("KJFW is not null");
            return (Criteria) this;
        }

        public Criteria andKjfwEqualTo(String value) {
            addCriterion("KJFW =", value, "kjfw");
            return (Criteria) this;
        }

        public Criteria andKjfwNotEqualTo(String value) {
            addCriterion("KJFW <>", value, "kjfw");
            return (Criteria) this;
        }

        public Criteria andKjfwGreaterThan(String value) {
            addCriterion("KJFW >", value, "kjfw");
            return (Criteria) this;
        }

        public Criteria andKjfwGreaterThanOrEqualTo(String value) {
            addCriterion("KJFW >=", value, "kjfw");
            return (Criteria) this;
        }

        public Criteria andKjfwLessThan(String value) {
            addCriterion("KJFW <", value, "kjfw");
            return (Criteria) this;
        }

        public Criteria andKjfwLessThanOrEqualTo(String value) {
            addCriterion("KJFW <=", value, "kjfw");
            return (Criteria) this;
        }

        public Criteria andKjfwLike(String value) {
            addCriterion("KJFW like", value, "kjfw");
            return (Criteria) this;
        }

        public Criteria andKjfwNotLike(String value) {
            addCriterion("KJFW not like", value, "kjfw");
            return (Criteria) this;
        }

        public Criteria andKjfwIn(List<String> values) {
            addCriterion("KJFW in", values, "kjfw");
            return (Criteria) this;
        }

        public Criteria andKjfwNotIn(List<String> values) {
            addCriterion("KJFW not in", values, "kjfw");
            return (Criteria) this;
        }

        public Criteria andKjfwBetween(String value1, String value2) {
            addCriterion("KJFW between", value1, value2, "kjfw");
            return (Criteria) this;
        }

        public Criteria andKjfwNotBetween(String value1, String value2) {
            addCriterion("KJFW not between", value1, value2, "kjfw");
            return (Criteria) this;
        }

        public Criteria andSyqztIsNull() {
            addCriterion("SYQZT is null");
            return (Criteria) this;
        }

        public Criteria andSyqztIsNotNull() {
            addCriterion("SYQZT is not null");
            return (Criteria) this;
        }

        public Criteria andSyqztEqualTo(String value) {
            addCriterion("SYQZT =", value, "syqzt");
            return (Criteria) this;
        }

        public Criteria andSyqztNotEqualTo(String value) {
            addCriterion("SYQZT <>", value, "syqzt");
            return (Criteria) this;
        }

        public Criteria andSyqztGreaterThan(String value) {
            addCriterion("SYQZT >", value, "syqzt");
            return (Criteria) this;
        }

        public Criteria andSyqztGreaterThanOrEqualTo(String value) {
            addCriterion("SYQZT >=", value, "syqzt");
            return (Criteria) this;
        }

        public Criteria andSyqztLessThan(String value) {
            addCriterion("SYQZT <", value, "syqzt");
            return (Criteria) this;
        }

        public Criteria andSyqztLessThanOrEqualTo(String value) {
            addCriterion("SYQZT <=", value, "syqzt");
            return (Criteria) this;
        }

        public Criteria andSyqztLike(String value) {
            addCriterion("SYQZT like", value, "syqzt");
            return (Criteria) this;
        }

        public Criteria andSyqztNotLike(String value) {
            addCriterion("SYQZT not like", value, "syqzt");
            return (Criteria) this;
        }

        public Criteria andSyqztIn(List<String> values) {
            addCriterion("SYQZT in", values, "syqzt");
            return (Criteria) this;
        }

        public Criteria andSyqztNotIn(List<String> values) {
            addCriterion("SYQZT not in", values, "syqzt");
            return (Criteria) this;
        }

        public Criteria andSyqztBetween(String value1, String value2) {
            addCriterion("SYQZT between", value1, value2, "syqzt");
            return (Criteria) this;
        }

        public Criteria andSyqztNotBetween(String value1, String value2) {
            addCriterion("SYQZT not between", value1, value2, "syqzt");
            return (Criteria) this;
        }

        public Criteria andDbxsztIsNull() {
            addCriterion("DBXSZT is null");
            return (Criteria) this;
        }

        public Criteria andDbxsztIsNotNull() {
            addCriterion("DBXSZT is not null");
            return (Criteria) this;
        }

        public Criteria andDbxsztEqualTo(String value) {
            addCriterion("DBXSZT =", value, "dbxszt");
            return (Criteria) this;
        }

        public Criteria andDbxsztNotEqualTo(String value) {
            addCriterion("DBXSZT <>", value, "dbxszt");
            return (Criteria) this;
        }

        public Criteria andDbxsztGreaterThan(String value) {
            addCriterion("DBXSZT >", value, "dbxszt");
            return (Criteria) this;
        }

        public Criteria andDbxsztGreaterThanOrEqualTo(String value) {
            addCriterion("DBXSZT >=", value, "dbxszt");
            return (Criteria) this;
        }

        public Criteria andDbxsztLessThan(String value) {
            addCriterion("DBXSZT <", value, "dbxszt");
            return (Criteria) this;
        }

        public Criteria andDbxsztLessThanOrEqualTo(String value) {
            addCriterion("DBXSZT <=", value, "dbxszt");
            return (Criteria) this;
        }

        public Criteria andDbxsztLike(String value) {
            addCriterion("DBXSZT like", value, "dbxszt");
            return (Criteria) this;
        }

        public Criteria andDbxsztNotLike(String value) {
            addCriterion("DBXSZT not like", value, "dbxszt");
            return (Criteria) this;
        }

        public Criteria andDbxsztIn(List<String> values) {
            addCriterion("DBXSZT in", values, "dbxszt");
            return (Criteria) this;
        }

        public Criteria andDbxsztNotIn(List<String> values) {
            addCriterion("DBXSZT not in", values, "dbxszt");
            return (Criteria) this;
        }

        public Criteria andDbxsztBetween(String value1, String value2) {
            addCriterion("DBXSZT between", value1, value2, "dbxszt");
            return (Criteria) this;
        }

        public Criteria andDbxsztNotBetween(String value1, String value2) {
            addCriterion("DBXSZT not between", value1, value2, "dbxszt");
            return (Criteria) this;
        }

        public Criteria andQlxsfsIsNull() {
            addCriterion("QLXSFS is null");
            return (Criteria) this;
        }

        public Criteria andQlxsfsIsNotNull() {
            addCriterion("QLXSFS is not null");
            return (Criteria) this;
        }

        public Criteria andQlxsfsEqualTo(String value) {
            addCriterion("QLXSFS =", value, "qlxsfs");
            return (Criteria) this;
        }

        public Criteria andQlxsfsNotEqualTo(String value) {
            addCriterion("QLXSFS <>", value, "qlxsfs");
            return (Criteria) this;
        }

        public Criteria andQlxsfsGreaterThan(String value) {
            addCriterion("QLXSFS >", value, "qlxsfs");
            return (Criteria) this;
        }

        public Criteria andQlxsfsGreaterThanOrEqualTo(String value) {
            addCriterion("QLXSFS >=", value, "qlxsfs");
            return (Criteria) this;
        }

        public Criteria andQlxsfsLessThan(String value) {
            addCriterion("QLXSFS <", value, "qlxsfs");
            return (Criteria) this;
        }

        public Criteria andQlxsfsLessThanOrEqualTo(String value) {
            addCriterion("QLXSFS <=", value, "qlxsfs");
            return (Criteria) this;
        }

        public Criteria andQlxsfsLike(String value) {
            addCriterion("QLXSFS like", value, "qlxsfs");
            return (Criteria) this;
        }

        public Criteria andQlxsfsNotLike(String value) {
            addCriterion("QLXSFS not like", value, "qlxsfs");
            return (Criteria) this;
        }

        public Criteria andQlxsfsIn(List<String> values) {
            addCriterion("QLXSFS in", values, "qlxsfs");
            return (Criteria) this;
        }

        public Criteria andQlxsfsNotIn(List<String> values) {
            addCriterion("QLXSFS not in", values, "qlxsfs");
            return (Criteria) this;
        }

        public Criteria andQlxsfsBetween(String value1, String value2) {
            addCriterion("QLXSFS between", value1, value2, "qlxsfs");
            return (Criteria) this;
        }

        public Criteria andQlxsfsNotBetween(String value1, String value2) {
            addCriterion("QLXSFS not between", value1, value2, "qlxsfs");
            return (Criteria) this;
        }

        public Criteria andDlxsztIsNull() {
            addCriterion("DLXSZT is null");
            return (Criteria) this;
        }

        public Criteria andDlxsztIsNotNull() {
            addCriterion("DLXSZT is not null");
            return (Criteria) this;
        }

        public Criteria andDlxsztEqualTo(String value) {
            addCriterion("DLXSZT =", value, "dlxszt");
            return (Criteria) this;
        }

        public Criteria andDlxsztNotEqualTo(String value) {
            addCriterion("DLXSZT <>", value, "dlxszt");
            return (Criteria) this;
        }

        public Criteria andDlxsztGreaterThan(String value) {
            addCriterion("DLXSZT >", value, "dlxszt");
            return (Criteria) this;
        }

        public Criteria andDlxsztGreaterThanOrEqualTo(String value) {
            addCriterion("DLXSZT >=", value, "dlxszt");
            return (Criteria) this;
        }

        public Criteria andDlxsztLessThan(String value) {
            addCriterion("DLXSZT <", value, "dlxszt");
            return (Criteria) this;
        }

        public Criteria andDlxsztLessThanOrEqualTo(String value) {
            addCriterion("DLXSZT <=", value, "dlxszt");
            return (Criteria) this;
        }

        public Criteria andDlxsztLike(String value) {
            addCriterion("DLXSZT like", value, "dlxszt");
            return (Criteria) this;
        }

        public Criteria andDlxsztNotLike(String value) {
            addCriterion("DLXSZT not like", value, "dlxszt");
            return (Criteria) this;
        }

        public Criteria andDlxsztIn(List<String> values) {
            addCriterion("DLXSZT in", values, "dlxszt");
            return (Criteria) this;
        }

        public Criteria andDlxsztNotIn(List<String> values) {
            addCriterion("DLXSZT not in", values, "dlxszt");
            return (Criteria) this;
        }

        public Criteria andDlxsztBetween(String value1, String value2) {
            addCriterion("DLXSZT between", value1, value2, "dlxszt");
            return (Criteria) this;
        }

        public Criteria andDlxsztNotBetween(String value1, String value2) {
            addCriterion("DLXSZT not between", value1, value2, "dlxszt");
            return (Criteria) this;
        }

        public Criteria andXsnrIsNull() {
            addCriterion("XSNR is null");
            return (Criteria) this;
        }

        public Criteria andXsnrIsNotNull() {
            addCriterion("XSNR is not null");
            return (Criteria) this;
        }

        public Criteria andXsnrEqualTo(String value) {
            addCriterion("XSNR =", value, "xsnr");
            return (Criteria) this;
        }

        public Criteria andXsnrNotEqualTo(String value) {
            addCriterion("XSNR <>", value, "xsnr");
            return (Criteria) this;
        }

        public Criteria andXsnrGreaterThan(String value) {
            addCriterion("XSNR >", value, "xsnr");
            return (Criteria) this;
        }

        public Criteria andXsnrGreaterThanOrEqualTo(String value) {
            addCriterion("XSNR >=", value, "xsnr");
            return (Criteria) this;
        }

        public Criteria andXsnrLessThan(String value) {
            addCriterion("XSNR <", value, "xsnr");
            return (Criteria) this;
        }

        public Criteria andXsnrLessThanOrEqualTo(String value) {
            addCriterion("XSNR <=", value, "xsnr");
            return (Criteria) this;
        }

        public Criteria andXsnrLike(String value) {
            addCriterion("XSNR like", value, "xsnr");
            return (Criteria) this;
        }

        public Criteria andXsnrNotLike(String value) {
            addCriterion("XSNR not like", value, "xsnr");
            return (Criteria) this;
        }

        public Criteria andXsnrIn(List<String> values) {
            addCriterion("XSNR in", values, "xsnr");
            return (Criteria) this;
        }

        public Criteria andXsnrNotIn(List<String> values) {
            addCriterion("XSNR not in", values, "xsnr");
            return (Criteria) this;
        }

        public Criteria andXsnrBetween(String value1, String value2) {
            addCriterion("XSNR between", value1, value2, "xsnr");
            return (Criteria) this;
        }

        public Criteria andXsnrNotBetween(String value1, String value2) {
            addCriterion("XSNR not between", value1, value2, "xsnr");
            return (Criteria) this;
        }

        public Criteria andMjIsNull() {
            addCriterion("MJ is null");
            return (Criteria) this;
        }

        public Criteria andMjIsNotNull() {
            addCriterion("MJ is not null");
            return (Criteria) this;
        }

        public Criteria andMjEqualTo(BigDecimal value) {
            addCriterion("MJ =", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjNotEqualTo(BigDecimal value) {
            addCriterion("MJ <>", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjGreaterThan(BigDecimal value) {
            addCriterion("MJ >", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MJ >=", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjLessThan(BigDecimal value) {
            addCriterion("MJ <", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MJ <=", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjIn(List<BigDecimal> values) {
            addCriterion("MJ in", values, "mj");
            return (Criteria) this;
        }

        public Criteria andMjNotIn(List<BigDecimal> values) {
            addCriterion("MJ not in", values, "mj");
            return (Criteria) this;
        }

        public Criteria andMjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MJ between", value1, value2, "mj");
            return (Criteria) this;
        }

        public Criteria andMjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MJ not between", value1, value2, "mj");
            return (Criteria) this;
        }

        public Criteria andGymjIsNull() {
            addCriterion("GYMJ is null");
            return (Criteria) this;
        }

        public Criteria andGymjIsNotNull() {
            addCriterion("GYMJ is not null");
            return (Criteria) this;
        }

        public Criteria andGymjEqualTo(BigDecimal value) {
            addCriterion("GYMJ =", value, "gymj");
            return (Criteria) this;
        }

        public Criteria andGymjNotEqualTo(BigDecimal value) {
            addCriterion("GYMJ <>", value, "gymj");
            return (Criteria) this;
        }

        public Criteria andGymjGreaterThan(BigDecimal value) {
            addCriterion("GYMJ >", value, "gymj");
            return (Criteria) this;
        }

        public Criteria andGymjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("GYMJ >=", value, "gymj");
            return (Criteria) this;
        }

        public Criteria andGymjLessThan(BigDecimal value) {
            addCriterion("GYMJ <", value, "gymj");
            return (Criteria) this;
        }

        public Criteria andGymjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("GYMJ <=", value, "gymj");
            return (Criteria) this;
        }

        public Criteria andGymjIn(List<BigDecimal> values) {
            addCriterion("GYMJ in", values, "gymj");
            return (Criteria) this;
        }

        public Criteria andGymjNotIn(List<BigDecimal> values) {
            addCriterion("GYMJ not in", values, "gymj");
            return (Criteria) this;
        }

        public Criteria andGymjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("GYMJ between", value1, value2, "gymj");
            return (Criteria) this;
        }

        public Criteria andGymjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("GYMJ not between", value1, value2, "gymj");
            return (Criteria) this;
        }

        public Criteria andJtmjIsNull() {
            addCriterion("JTMJ is null");
            return (Criteria) this;
        }

        public Criteria andJtmjIsNotNull() {
            addCriterion("JTMJ is not null");
            return (Criteria) this;
        }

        public Criteria andJtmjEqualTo(BigDecimal value) {
            addCriterion("JTMJ =", value, "jtmj");
            return (Criteria) this;
        }

        public Criteria andJtmjNotEqualTo(BigDecimal value) {
            addCriterion("JTMJ <>", value, "jtmj");
            return (Criteria) this;
        }

        public Criteria andJtmjGreaterThan(BigDecimal value) {
            addCriterion("JTMJ >", value, "jtmj");
            return (Criteria) this;
        }

        public Criteria andJtmjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("JTMJ >=", value, "jtmj");
            return (Criteria) this;
        }

        public Criteria andJtmjLessThan(BigDecimal value) {
            addCriterion("JTMJ <", value, "jtmj");
            return (Criteria) this;
        }

        public Criteria andJtmjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("JTMJ <=", value, "jtmj");
            return (Criteria) this;
        }

        public Criteria andJtmjIn(List<BigDecimal> values) {
            addCriterion("JTMJ in", values, "jtmj");
            return (Criteria) this;
        }

        public Criteria andJtmjNotIn(List<BigDecimal> values) {
            addCriterion("JTMJ not in", values, "jtmj");
            return (Criteria) this;
        }

        public Criteria andJtmjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("JTMJ between", value1, value2, "jtmj");
            return (Criteria) this;
        }

        public Criteria andJtmjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("JTMJ not between", value1, value2, "jtmj");
            return (Criteria) this;
        }

        public Criteria andZyqmjIsNull() {
            addCriterion("ZYQMJ is null");
            return (Criteria) this;
        }

        public Criteria andZyqmjIsNotNull() {
            addCriterion("ZYQMJ is not null");
            return (Criteria) this;
        }

        public Criteria andZyqmjEqualTo(BigDecimal value) {
            addCriterion("ZYQMJ =", value, "zyqmj");
            return (Criteria) this;
        }

        public Criteria andZyqmjNotEqualTo(BigDecimal value) {
            addCriterion("ZYQMJ <>", value, "zyqmj");
            return (Criteria) this;
        }

        public Criteria andZyqmjGreaterThan(BigDecimal value) {
            addCriterion("ZYQMJ >", value, "zyqmj");
            return (Criteria) this;
        }

        public Criteria andZyqmjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZYQMJ >=", value, "zyqmj");
            return (Criteria) this;
        }

        public Criteria andZyqmjLessThan(BigDecimal value) {
            addCriterion("ZYQMJ <", value, "zyqmj");
            return (Criteria) this;
        }

        public Criteria andZyqmjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZYQMJ <=", value, "zyqmj");
            return (Criteria) this;
        }

        public Criteria andZyqmjIn(List<BigDecimal> values) {
            addCriterion("ZYQMJ in", values, "zyqmj");
            return (Criteria) this;
        }

        public Criteria andZyqmjNotIn(List<BigDecimal> values) {
            addCriterion("ZYQMJ not in", values, "zyqmj");
            return (Criteria) this;
        }

        public Criteria andZyqmjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZYQMJ between", value1, value2, "zyqmj");
            return (Criteria) this;
        }

        public Criteria andZyqmjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZYQMJ not between", value1, value2, "zyqmj");
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