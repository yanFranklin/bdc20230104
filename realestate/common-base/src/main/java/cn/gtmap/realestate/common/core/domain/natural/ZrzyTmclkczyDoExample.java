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

public class ZrzyTmclkczyDoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ZrzyTmclkczyDoExample() {
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

        public Criteria andZkxxidIsNull() {
            addCriterion("ZKXXID is null");
            return (Criteria) this;
        }

        public Criteria andZkxxidIsNotNull() {
            addCriterion("ZKXXID is not null");
            return (Criteria) this;
        }

        public Criteria andZkxxidEqualTo(String value) {
            addCriterion("ZKXXID =", value, "zkxxid");
            return (Criteria) this;
        }

        public Criteria andZkxxidNotEqualTo(String value) {
            addCriterion("ZKXXID <>", value, "zkxxid");
            return (Criteria) this;
        }

        public Criteria andZkxxidGreaterThan(String value) {
            addCriterion("ZKXXID >", value, "zkxxid");
            return (Criteria) this;
        }

        public Criteria andZkxxidGreaterThanOrEqualTo(String value) {
            addCriterion("ZKXXID >=", value, "zkxxid");
            return (Criteria) this;
        }

        public Criteria andZkxxidLessThan(String value) {
            addCriterion("ZKXXID <", value, "zkxxid");
            return (Criteria) this;
        }

        public Criteria andZkxxidLessThanOrEqualTo(String value) {
            addCriterion("ZKXXID <=", value, "zkxxid");
            return (Criteria) this;
        }

        public Criteria andZkxxidLike(String value) {
            addCriterion("ZKXXID like", value, "zkxxid");
            return (Criteria) this;
        }

        public Criteria andZkxxidNotLike(String value) {
            addCriterion("ZKXXID not like", value, "zkxxid");
            return (Criteria) this;
        }

        public Criteria andZkxxidIn(List<String> values) {
            addCriterion("ZKXXID in", values, "zkxxid");
            return (Criteria) this;
        }

        public Criteria andZkxxidNotIn(List<String> values) {
            addCriterion("ZKXXID not in", values, "zkxxid");
            return (Criteria) this;
        }

        public Criteria andZkxxidBetween(String value1, String value2) {
            addCriterion("ZKXXID between", value1, value2, "zkxxid");
            return (Criteria) this;
        }

        public Criteria andZkxxidNotBetween(String value1, String value2) {
            addCriterion("ZKXXID not between", value1, value2, "zkxxid");
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

        public Criteria andZrlxIsNull() {
            addCriterion("ZRLX is null");
            return (Criteria) this;
        }

        public Criteria andZrlxIsNotNull() {
            addCriterion("ZRLX is not null");
            return (Criteria) this;
        }

        public Criteria andZrlxEqualTo(String value) {
            addCriterion("ZRLX =", value, "zrlx");
            return (Criteria) this;
        }

        public Criteria andZrlxNotEqualTo(String value) {
            addCriterion("ZRLX <>", value, "zrlx");
            return (Criteria) this;
        }

        public Criteria andZrlxGreaterThan(String value) {
            addCriterion("ZRLX >", value, "zrlx");
            return (Criteria) this;
        }

        public Criteria andZrlxGreaterThanOrEqualTo(String value) {
            addCriterion("ZRLX >=", value, "zrlx");
            return (Criteria) this;
        }

        public Criteria andZrlxLessThan(String value) {
            addCriterion("ZRLX <", value, "zrlx");
            return (Criteria) this;
        }

        public Criteria andZrlxLessThanOrEqualTo(String value) {
            addCriterion("ZRLX <=", value, "zrlx");
            return (Criteria) this;
        }

        public Criteria andZrlxLike(String value) {
            addCriterion("ZRLX like", value, "zrlx");
            return (Criteria) this;
        }

        public Criteria andZrlxNotLike(String value) {
            addCriterion("ZRLX not like", value, "zrlx");
            return (Criteria) this;
        }

        public Criteria andZrlxIn(List<String> values) {
            addCriterion("ZRLX in", values, "zrlx");
            return (Criteria) this;
        }

        public Criteria andZrlxNotIn(List<String> values) {
            addCriterion("ZRLX not in", values, "zrlx");
            return (Criteria) this;
        }

        public Criteria andZrlxBetween(String value1, String value2) {
            addCriterion("ZRLX between", value1, value2, "zrlx");
            return (Criteria) this;
        }

        public Criteria andZrlxNotBetween(String value1, String value2) {
            addCriterion("ZRLX not between", value1, value2, "zrlx");
            return (Criteria) this;
        }

        public Criteria andQkbhIsNull() {
            addCriterion("QKBH is null");
            return (Criteria) this;
        }

        public Criteria andQkbhIsNotNull() {
            addCriterion("QKBH is not null");
            return (Criteria) this;
        }

        public Criteria andQkbhEqualTo(String value) {
            addCriterion("QKBH =", value, "qkbh");
            return (Criteria) this;
        }

        public Criteria andQkbhNotEqualTo(String value) {
            addCriterion("QKBH <>", value, "qkbh");
            return (Criteria) this;
        }

        public Criteria andQkbhGreaterThan(String value) {
            addCriterion("QKBH >", value, "qkbh");
            return (Criteria) this;
        }

        public Criteria andQkbhGreaterThanOrEqualTo(String value) {
            addCriterion("QKBH >=", value, "qkbh");
            return (Criteria) this;
        }

        public Criteria andQkbhLessThan(String value) {
            addCriterion("QKBH <", value, "qkbh");
            return (Criteria) this;
        }

        public Criteria andQkbhLessThanOrEqualTo(String value) {
            addCriterion("QKBH <=", value, "qkbh");
            return (Criteria) this;
        }

        public Criteria andQkbhLike(String value) {
            addCriterion("QKBH like", value, "qkbh");
            return (Criteria) this;
        }

        public Criteria andQkbhNotLike(String value) {
            addCriterion("QKBH not like", value, "qkbh");
            return (Criteria) this;
        }

        public Criteria andQkbhIn(List<String> values) {
            addCriterion("QKBH in", values, "qkbh");
            return (Criteria) this;
        }

        public Criteria andQkbhNotIn(List<String> values) {
            addCriterion("QKBH not in", values, "qkbh");
            return (Criteria) this;
        }

        public Criteria andQkbhBetween(String value1, String value2) {
            addCriterion("QKBH between", value1, value2, "qkbh");
            return (Criteria) this;
        }

        public Criteria andQkbhNotBetween(String value1, String value2) {
            addCriterion("QKBH not between", value1, value2, "qkbh");
            return (Criteria) this;
        }

        public Criteria andKqdzIsNull() {
            addCriterion("KQDZ is null");
            return (Criteria) this;
        }

        public Criteria andKqdzIsNotNull() {
            addCriterion("KQDZ is not null");
            return (Criteria) this;
        }

        public Criteria andKqdzEqualTo(String value) {
            addCriterion("KQDZ =", value, "kqdz");
            return (Criteria) this;
        }

        public Criteria andKqdzNotEqualTo(String value) {
            addCriterion("KQDZ <>", value, "kqdz");
            return (Criteria) this;
        }

        public Criteria andKqdzGreaterThan(String value) {
            addCriterion("KQDZ >", value, "kqdz");
            return (Criteria) this;
        }

        public Criteria andKqdzGreaterThanOrEqualTo(String value) {
            addCriterion("KQDZ >=", value, "kqdz");
            return (Criteria) this;
        }

        public Criteria andKqdzLessThan(String value) {
            addCriterion("KQDZ <", value, "kqdz");
            return (Criteria) this;
        }

        public Criteria andKqdzLessThanOrEqualTo(String value) {
            addCriterion("KQDZ <=", value, "kqdz");
            return (Criteria) this;
        }

        public Criteria andKqdzLike(String value) {
            addCriterion("KQDZ like", value, "kqdz");
            return (Criteria) this;
        }

        public Criteria andKqdzNotLike(String value) {
            addCriterion("KQDZ not like", value, "kqdz");
            return (Criteria) this;
        }

        public Criteria andKqdzIn(List<String> values) {
            addCriterion("KQDZ in", values, "kqdz");
            return (Criteria) this;
        }

        public Criteria andKqdzNotIn(List<String> values) {
            addCriterion("KQDZ not in", values, "kqdz");
            return (Criteria) this;
        }

        public Criteria andKqdzBetween(String value1, String value2) {
            addCriterion("KQDZ between", value1, value2, "kqdz");
            return (Criteria) this;
        }

        public Criteria andKqdzNotBetween(String value1, String value2) {
            addCriterion("KQDZ not between", value1, value2, "kqdz");
            return (Criteria) this;
        }

        public Criteria andClgsjzrIsNull() {
            addCriterion("CLGSJZR is null");
            return (Criteria) this;
        }

        public Criteria andClgsjzrIsNotNull() {
            addCriterion("CLGSJZR is not null");
            return (Criteria) this;
        }

        public Criteria andClgsjzrEqualTo(LocalDateTime value) {
            addCriterion("CLGSJZR =", value, "clgsjzr");
            return (Criteria) this;
        }

        public Criteria andClgsjzrNotEqualTo(LocalDateTime value) {
            addCriterion("CLGSJZR <>", value, "clgsjzr");
            return (Criteria) this;
        }

        public Criteria andClgsjzrGreaterThan(LocalDateTime value) {
            addCriterion("CLGSJZR >", value, "clgsjzr");
            return (Criteria) this;
        }

        public Criteria andClgsjzrGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("CLGSJZR >=", value, "clgsjzr");
            return (Criteria) this;
        }

        public Criteria andClgsjzrLessThan(LocalDateTime value) {
            addCriterion("CLGSJZR <", value, "clgsjzr");
            return (Criteria) this;
        }

        public Criteria andClgsjzrLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("CLGSJZR <=", value, "clgsjzr");
            return (Criteria) this;
        }

        public Criteria andClgsjzrIn(List<LocalDateTime> values) {
            addCriterion("CLGSJZR in", values, "clgsjzr");
            return (Criteria) this;
        }

        public Criteria andClgsjzrNotIn(List<LocalDateTime> values) {
            addCriterion("CLGSJZR not in", values, "clgsjzr");
            return (Criteria) this;
        }

        public Criteria andClgsjzrBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("CLGSJZR between", value1, value2, "clgsjzr");
            return (Criteria) this;
        }

        public Criteria andClgsjzrNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("CLGSJZR not between", value1, value2, "clgsjzr");
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

        public Criteria andKqyqtzmjIsNull() {
            addCriterion("KQYQTZMJ is null");
            return (Criteria) this;
        }

        public Criteria andKqyqtzmjIsNotNull() {
            addCriterion("KQYQTZMJ is not null");
            return (Criteria) this;
        }

        public Criteria andKqyqtzmjEqualTo(BigDecimal value) {
            addCriterion("KQYQTZMJ =", value, "kqyqtzmj");
            return (Criteria) this;
        }

        public Criteria andKqyqtzmjNotEqualTo(BigDecimal value) {
            addCriterion("KQYQTZMJ <>", value, "kqyqtzmj");
            return (Criteria) this;
        }

        public Criteria andKqyqtzmjGreaterThan(BigDecimal value) {
            addCriterion("KQYQTZMJ >", value, "kqyqtzmj");
            return (Criteria) this;
        }

        public Criteria andKqyqtzmjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("KQYQTZMJ >=", value, "kqyqtzmj");
            return (Criteria) this;
        }

        public Criteria andKqyqtzmjLessThan(BigDecimal value) {
            addCriterion("KQYQTZMJ <", value, "kqyqtzmj");
            return (Criteria) this;
        }

        public Criteria andKqyqtzmjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("KQYQTZMJ <=", value, "kqyqtzmj");
            return (Criteria) this;
        }

        public Criteria andKqyqtzmjIn(List<BigDecimal> values) {
            addCriterion("KQYQTZMJ in", values, "kqyqtzmj");
            return (Criteria) this;
        }

        public Criteria andKqyqtzmjNotIn(List<BigDecimal> values) {
            addCriterion("KQYQTZMJ not in", values, "kqyqtzmj");
            return (Criteria) this;
        }

        public Criteria andKqyqtzmjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("KQYQTZMJ between", value1, value2, "kqyqtzmj");
            return (Criteria) this;
        }

        public Criteria andKqyqtzmjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("KQYQTZMJ not between", value1, value2, "kqyqtzmj");
            return (Criteria) this;
        }

        public Criteria andClgsfwmjIsNull() {
            addCriterion("CLGSFWMJ is null");
            return (Criteria) this;
        }

        public Criteria andClgsfwmjIsNotNull() {
            addCriterion("CLGSFWMJ is not null");
            return (Criteria) this;
        }

        public Criteria andClgsfwmjEqualTo(BigDecimal value) {
            addCriterion("CLGSFWMJ =", value, "clgsfwmj");
            return (Criteria) this;
        }

        public Criteria andClgsfwmjNotEqualTo(BigDecimal value) {
            addCriterion("CLGSFWMJ <>", value, "clgsfwmj");
            return (Criteria) this;
        }

        public Criteria andClgsfwmjGreaterThan(BigDecimal value) {
            addCriterion("CLGSFWMJ >", value, "clgsfwmj");
            return (Criteria) this;
        }

        public Criteria andClgsfwmjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CLGSFWMJ >=", value, "clgsfwmj");
            return (Criteria) this;
        }

        public Criteria andClgsfwmjLessThan(BigDecimal value) {
            addCriterion("CLGSFWMJ <", value, "clgsfwmj");
            return (Criteria) this;
        }

        public Criteria andClgsfwmjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CLGSFWMJ <=", value, "clgsfwmj");
            return (Criteria) this;
        }

        public Criteria andClgsfwmjIn(List<BigDecimal> values) {
            addCriterion("CLGSFWMJ in", values, "clgsfwmj");
            return (Criteria) this;
        }

        public Criteria andClgsfwmjNotIn(List<BigDecimal> values) {
            addCriterion("CLGSFWMJ not in", values, "clgsfwmj");
            return (Criteria) this;
        }

        public Criteria andClgsfwmjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLGSFWMJ between", value1, value2, "clgsfwmj");
            return (Criteria) this;
        }

        public Criteria andClgsfwmjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLGSFWMJ not between", value1, value2, "clgsfwmj");
            return (Criteria) this;
        }

        public Criteria andKczhIsNull() {
            addCriterion("KCZH is null");
            return (Criteria) this;
        }

        public Criteria andKczhIsNotNull() {
            addCriterion("KCZH is not null");
            return (Criteria) this;
        }

        public Criteria andKczhEqualTo(String value) {
            addCriterion("KCZH =", value, "kczh");
            return (Criteria) this;
        }

        public Criteria andKczhNotEqualTo(String value) {
            addCriterion("KCZH <>", value, "kczh");
            return (Criteria) this;
        }

        public Criteria andKczhGreaterThan(String value) {
            addCriterion("KCZH >", value, "kczh");
            return (Criteria) this;
        }

        public Criteria andKczhGreaterThanOrEqualTo(String value) {
            addCriterion("KCZH >=", value, "kczh");
            return (Criteria) this;
        }

        public Criteria andKczhLessThan(String value) {
            addCriterion("KCZH <", value, "kczh");
            return (Criteria) this;
        }

        public Criteria andKczhLessThanOrEqualTo(String value) {
            addCriterion("KCZH <=", value, "kczh");
            return (Criteria) this;
        }

        public Criteria andKczhLike(String value) {
            addCriterion("KCZH like", value, "kczh");
            return (Criteria) this;
        }

        public Criteria andKczhNotLike(String value) {
            addCriterion("KCZH not like", value, "kczh");
            return (Criteria) this;
        }

        public Criteria andKczhIn(List<String> values) {
            addCriterion("KCZH in", values, "kczh");
            return (Criteria) this;
        }

        public Criteria andKczhNotIn(List<String> values) {
            addCriterion("KCZH not in", values, "kczh");
            return (Criteria) this;
        }

        public Criteria andKczhBetween(String value1, String value2) {
            addCriterion("KCZH between", value1, value2, "kczh");
            return (Criteria) this;
        }

        public Criteria andKczhNotBetween(String value1, String value2) {
            addCriterion("KCZH not between", value1, value2, "kczh");
            return (Criteria) this;
        }

        public Criteria andCljldwIsNull() {
            addCriterion("CLJLDW is null");
            return (Criteria) this;
        }

        public Criteria andCljldwIsNotNull() {
            addCriterion("CLJLDW is not null");
            return (Criteria) this;
        }

        public Criteria andCljldwEqualTo(String value) {
            addCriterion("CLJLDW =", value, "cljldw");
            return (Criteria) this;
        }

        public Criteria andCljldwNotEqualTo(String value) {
            addCriterion("CLJLDW <>", value, "cljldw");
            return (Criteria) this;
        }

        public Criteria andCljldwGreaterThan(String value) {
            addCriterion("CLJLDW >", value, "cljldw");
            return (Criteria) this;
        }

        public Criteria andCljldwGreaterThanOrEqualTo(String value) {
            addCriterion("CLJLDW >=", value, "cljldw");
            return (Criteria) this;
        }

        public Criteria andCljldwLessThan(String value) {
            addCriterion("CLJLDW <", value, "cljldw");
            return (Criteria) this;
        }

        public Criteria andCljldwLessThanOrEqualTo(String value) {
            addCriterion("CLJLDW <=", value, "cljldw");
            return (Criteria) this;
        }

        public Criteria andCljldwLike(String value) {
            addCriterion("CLJLDW like", value, "cljldw");
            return (Criteria) this;
        }

        public Criteria andCljldwNotLike(String value) {
            addCriterion("CLJLDW not like", value, "cljldw");
            return (Criteria) this;
        }

        public Criteria andCljldwIn(List<String> values) {
            addCriterion("CLJLDW in", values, "cljldw");
            return (Criteria) this;
        }

        public Criteria andCljldwNotIn(List<String> values) {
            addCriterion("CLJLDW not in", values, "cljldw");
            return (Criteria) this;
        }

        public Criteria andCljldwBetween(String value1, String value2) {
            addCriterion("CLJLDW between", value1, value2, "cljldw");
            return (Criteria) this;
        }

        public Criteria andCljldwNotBetween(String value1, String value2) {
            addCriterion("CLJLDW not between", value1, value2, "cljldw");
            return (Criteria) this;
        }

        public Criteria andGtkctdzylIsNull() {
            addCriterion("GTKCTDZYL is null");
            return (Criteria) this;
        }

        public Criteria andGtkctdzylIsNotNull() {
            addCriterion("GTKCTDZYL is not null");
            return (Criteria) this;
        }

        public Criteria andGtkctdzylEqualTo(BigDecimal value) {
            addCriterion("GTKCTDZYL =", value, "gtkctdzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctdzylNotEqualTo(BigDecimal value) {
            addCriterion("GTKCTDZYL <>", value, "gtkctdzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctdzylGreaterThan(BigDecimal value) {
            addCriterion("GTKCTDZYL >", value, "gtkctdzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctdzylGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("GTKCTDZYL >=", value, "gtkctdzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctdzylLessThan(BigDecimal value) {
            addCriterion("GTKCTDZYL <", value, "gtkctdzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctdzylLessThanOrEqualTo(BigDecimal value) {
            addCriterion("GTKCTDZYL <=", value, "gtkctdzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctdzylIn(List<BigDecimal> values) {
            addCriterion("GTKCTDZYL in", values, "gtkctdzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctdzylNotIn(List<BigDecimal> values) {
            addCriterion("GTKCTDZYL not in", values, "gtkctdzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctdzylBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("GTKCTDZYL between", value1, value2, "gtkctdzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctdzylNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("GTKCTDZYL not between", value1, value2, "gtkctdzyl");
            return (Criteria) this;
        }

        public Criteria andGtkckzzylIsNull() {
            addCriterion("GTKCKZZYL is null");
            return (Criteria) this;
        }

        public Criteria andGtkckzzylIsNotNull() {
            addCriterion("GTKCKZZYL is not null");
            return (Criteria) this;
        }

        public Criteria andGtkckzzylEqualTo(BigDecimal value) {
            addCriterion("GTKCKZZYL =", value, "gtkckzzyl");
            return (Criteria) this;
        }

        public Criteria andGtkckzzylNotEqualTo(BigDecimal value) {
            addCriterion("GTKCKZZYL <>", value, "gtkckzzyl");
            return (Criteria) this;
        }

        public Criteria andGtkckzzylGreaterThan(BigDecimal value) {
            addCriterion("GTKCKZZYL >", value, "gtkckzzyl");
            return (Criteria) this;
        }

        public Criteria andGtkckzzylGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("GTKCKZZYL >=", value, "gtkckzzyl");
            return (Criteria) this;
        }

        public Criteria andGtkckzzylLessThan(BigDecimal value) {
            addCriterion("GTKCKZZYL <", value, "gtkckzzyl");
            return (Criteria) this;
        }

        public Criteria andGtkckzzylLessThanOrEqualTo(BigDecimal value) {
            addCriterion("GTKCKZZYL <=", value, "gtkckzzyl");
            return (Criteria) this;
        }

        public Criteria andGtkckzzylIn(List<BigDecimal> values) {
            addCriterion("GTKCKZZYL in", values, "gtkckzzyl");
            return (Criteria) this;
        }

        public Criteria andGtkckzzylNotIn(List<BigDecimal> values) {
            addCriterion("GTKCKZZYL not in", values, "gtkckzzyl");
            return (Criteria) this;
        }

        public Criteria andGtkckzzylBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("GTKCKZZYL between", value1, value2, "gtkckzzyl");
            return (Criteria) this;
        }

        public Criteria andGtkckzzylNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("GTKCKZZYL not between", value1, value2, "gtkckzzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctmzylIsNull() {
            addCriterion("GTKCTMZYL is null");
            return (Criteria) this;
        }

        public Criteria andGtkctmzylIsNotNull() {
            addCriterion("GTKCTMZYL is not null");
            return (Criteria) this;
        }

        public Criteria andGtkctmzylEqualTo(BigDecimal value) {
            addCriterion("GTKCTMZYL =", value, "gtkctmzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctmzylNotEqualTo(BigDecimal value) {
            addCriterion("GTKCTMZYL <>", value, "gtkctmzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctmzylGreaterThan(BigDecimal value) {
            addCriterion("GTKCTMZYL >", value, "gtkctmzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctmzylGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("GTKCTMZYL >=", value, "gtkctmzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctmzylLessThan(BigDecimal value) {
            addCriterion("GTKCTMZYL <", value, "gtkctmzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctmzylLessThanOrEqualTo(BigDecimal value) {
            addCriterion("GTKCTMZYL <=", value, "gtkctmzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctmzylIn(List<BigDecimal> values) {
            addCriterion("GTKCTMZYL in", values, "gtkctmzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctmzylNotIn(List<BigDecimal> values) {
            addCriterion("GTKCTMZYL not in", values, "gtkctmzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctmzylBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("GTKCTMZYL between", value1, value2, "gtkctmzyl");
            return (Criteria) this;
        }

        public Criteria andGtkctmzylNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("GTKCTMZYL not between", value1, value2, "gtkctmzyl");
            return (Criteria) this;
        }

        public Criteria andYqtmdzclIsNull() {
            addCriterion("YQTMDZCL is null");
            return (Criteria) this;
        }

        public Criteria andYqtmdzclIsNotNull() {
            addCriterion("YQTMDZCL is not null");
            return (Criteria) this;
        }

        public Criteria andYqtmdzclEqualTo(BigDecimal value) {
            addCriterion("YQTMDZCL =", value, "yqtmdzcl");
            return (Criteria) this;
        }

        public Criteria andYqtmdzclNotEqualTo(BigDecimal value) {
            addCriterion("YQTMDZCL <>", value, "yqtmdzcl");
            return (Criteria) this;
        }

        public Criteria andYqtmdzclGreaterThan(BigDecimal value) {
            addCriterion("YQTMDZCL >", value, "yqtmdzcl");
            return (Criteria) this;
        }

        public Criteria andYqtmdzclGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("YQTMDZCL >=", value, "yqtmdzcl");
            return (Criteria) this;
        }

        public Criteria andYqtmdzclLessThan(BigDecimal value) {
            addCriterion("YQTMDZCL <", value, "yqtmdzcl");
            return (Criteria) this;
        }

        public Criteria andYqtmdzclLessThanOrEqualTo(BigDecimal value) {
            addCriterion("YQTMDZCL <=", value, "yqtmdzcl");
            return (Criteria) this;
        }

        public Criteria andYqtmdzclIn(List<BigDecimal> values) {
            addCriterion("YQTMDZCL in", values, "yqtmdzcl");
            return (Criteria) this;
        }

        public Criteria andYqtmdzclNotIn(List<BigDecimal> values) {
            addCriterion("YQTMDZCL not in", values, "yqtmdzcl");
            return (Criteria) this;
        }

        public Criteria andYqtmdzclBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("YQTMDZCL between", value1, value2, "yqtmdzcl");
            return (Criteria) this;
        }

        public Criteria andYqtmdzclNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("YQTMDZCL not between", value1, value2, "yqtmdzcl");
            return (Criteria) this;
        }

        public Criteria andZyzfpjpwIsNull() {
            addCriterion("ZYZFPJPW is null");
            return (Criteria) this;
        }

        public Criteria andZyzfpjpwIsNotNull() {
            addCriterion("ZYZFPJPW is not null");
            return (Criteria) this;
        }

        public Criteria andZyzfpjpwEqualTo(BigDecimal value) {
            addCriterion("ZYZFPJPW =", value, "zyzfpjpw");
            return (Criteria) this;
        }

        public Criteria andZyzfpjpwNotEqualTo(BigDecimal value) {
            addCriterion("ZYZFPJPW <>", value, "zyzfpjpw");
            return (Criteria) this;
        }

        public Criteria andZyzfpjpwGreaterThan(BigDecimal value) {
            addCriterion("ZYZFPJPW >", value, "zyzfpjpw");
            return (Criteria) this;
        }

        public Criteria andZyzfpjpwGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZYZFPJPW >=", value, "zyzfpjpw");
            return (Criteria) this;
        }

        public Criteria andZyzfpjpwLessThan(BigDecimal value) {
            addCriterion("ZYZFPJPW <", value, "zyzfpjpw");
            return (Criteria) this;
        }

        public Criteria andZyzfpjpwLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZYZFPJPW <=", value, "zyzfpjpw");
            return (Criteria) this;
        }

        public Criteria andZyzfpjpwIn(List<BigDecimal> values) {
            addCriterion("ZYZFPJPW in", values, "zyzfpjpw");
            return (Criteria) this;
        }

        public Criteria andZyzfpjpwNotIn(List<BigDecimal> values) {
            addCriterion("ZYZFPJPW not in", values, "zyzfpjpw");
            return (Criteria) this;
        }

        public Criteria andZyzfpjpwBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZYZFPJPW between", value1, value2, "zyzfpjpw");
            return (Criteria) this;
        }

        public Criteria andZyzfpjpwNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZYZFPJPW not between", value1, value2, "zyzfpjpw");
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