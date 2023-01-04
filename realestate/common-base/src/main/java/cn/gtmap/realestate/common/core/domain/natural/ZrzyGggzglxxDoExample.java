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

public class ZrzyGggzglxxDoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ZrzyGggzglxxDoExample() {
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

        public Criteria andGlxxidIsNull() {
            addCriterion("GLXXID is null");
            return (Criteria) this;
        }

        public Criteria andGlxxidIsNotNull() {
            addCriterion("GLXXID is not null");
            return (Criteria) this;
        }

        public Criteria andGlxxidEqualTo(String value) {
            addCriterion("GLXXID =", value, "glxxid");
            return (Criteria) this;
        }

        public Criteria andGlxxidNotEqualTo(String value) {
            addCriterion("GLXXID <>", value, "glxxid");
            return (Criteria) this;
        }

        public Criteria andGlxxidGreaterThan(String value) {
            addCriterion("GLXXID >", value, "glxxid");
            return (Criteria) this;
        }

        public Criteria andGlxxidGreaterThanOrEqualTo(String value) {
            addCriterion("GLXXID >=", value, "glxxid");
            return (Criteria) this;
        }

        public Criteria andGlxxidLessThan(String value) {
            addCriterion("GLXXID <", value, "glxxid");
            return (Criteria) this;
        }

        public Criteria andGlxxidLessThanOrEqualTo(String value) {
            addCriterion("GLXXID <=", value, "glxxid");
            return (Criteria) this;
        }

        public Criteria andGlxxidLike(String value) {
            addCriterion("GLXXID like", value, "glxxid");
            return (Criteria) this;
        }

        public Criteria andGlxxidNotLike(String value) {
            addCriterion("GLXXID not like", value, "glxxid");
            return (Criteria) this;
        }

        public Criteria andGlxxidIn(List<String> values) {
            addCriterion("GLXXID in", values, "glxxid");
            return (Criteria) this;
        }

        public Criteria andGlxxidNotIn(List<String> values) {
            addCriterion("GLXXID not in", values, "glxxid");
            return (Criteria) this;
        }

        public Criteria andGlxxidBetween(String value1, String value2) {
            addCriterion("GLXXID between", value1, value2, "glxxid");
            return (Criteria) this;
        }

        public Criteria andGlxxidNotBetween(String value1, String value2) {
            addCriterion("GLXXID not between", value1, value2, "glxxid");
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

        public Criteria andGggzyslxIsNull() {
            addCriterion("GGGZYSLX is null");
            return (Criteria) this;
        }

        public Criteria andGggzyslxIsNotNull() {
            addCriterion("GGGZYSLX is not null");
            return (Criteria) this;
        }

        public Criteria andGggzyslxEqualTo(String value) {
            addCriterion("GGGZYSLX =", value, "gggzyslx");
            return (Criteria) this;
        }

        public Criteria andGggzyslxNotEqualTo(String value) {
            addCriterion("GGGZYSLX <>", value, "gggzyslx");
            return (Criteria) this;
        }

        public Criteria andGggzyslxGreaterThan(String value) {
            addCriterion("GGGZYSLX >", value, "gggzyslx");
            return (Criteria) this;
        }

        public Criteria andGggzyslxGreaterThanOrEqualTo(String value) {
            addCriterion("GGGZYSLX >=", value, "gggzyslx");
            return (Criteria) this;
        }

        public Criteria andGggzyslxLessThan(String value) {
            addCriterion("GGGZYSLX <", value, "gggzyslx");
            return (Criteria) this;
        }

        public Criteria andGggzyslxLessThanOrEqualTo(String value) {
            addCriterion("GGGZYSLX <=", value, "gggzyslx");
            return (Criteria) this;
        }

        public Criteria andGggzyslxLike(String value) {
            addCriterion("GGGZYSLX like", value, "gggzyslx");
            return (Criteria) this;
        }

        public Criteria andGggzyslxNotLike(String value) {
            addCriterion("GGGZYSLX not like", value, "gggzyslx");
            return (Criteria) this;
        }

        public Criteria andGggzyslxIn(List<String> values) {
            addCriterion("GGGZYSLX in", values, "gggzyslx");
            return (Criteria) this;
        }

        public Criteria andGggzyslxNotIn(List<String> values) {
            addCriterion("GGGZYSLX not in", values, "gggzyslx");
            return (Criteria) this;
        }

        public Criteria andGggzyslxBetween(String value1, String value2) {
            addCriterion("GGGZYSLX between", value1, value2, "gggzyslx");
            return (Criteria) this;
        }

        public Criteria andGggzyslxNotBetween(String value1, String value2) {
            addCriterion("GGGZYSLX not between", value1, value2, "gggzyslx");
            return (Criteria) this;
        }

        public Criteria andGggzfqbsmIsNull() {
            addCriterion("GGGZFQBSM is null");
            return (Criteria) this;
        }

        public Criteria andGggzfqbsmIsNotNull() {
            addCriterion("GGGZFQBSM is not null");
            return (Criteria) this;
        }

        public Criteria andGggzfqbsmEqualTo(String value) {
            addCriterion("GGGZFQBSM =", value, "gggzfqbsm");
            return (Criteria) this;
        }

        public Criteria andGggzfqbsmNotEqualTo(String value) {
            addCriterion("GGGZFQBSM <>", value, "gggzfqbsm");
            return (Criteria) this;
        }

        public Criteria andGggzfqbsmGreaterThan(String value) {
            addCriterion("GGGZFQBSM >", value, "gggzfqbsm");
            return (Criteria) this;
        }

        public Criteria andGggzfqbsmGreaterThanOrEqualTo(String value) {
            addCriterion("GGGZFQBSM >=", value, "gggzfqbsm");
            return (Criteria) this;
        }

        public Criteria andGggzfqbsmLessThan(String value) {
            addCriterion("GGGZFQBSM <", value, "gggzfqbsm");
            return (Criteria) this;
        }

        public Criteria andGggzfqbsmLessThanOrEqualTo(String value) {
            addCriterion("GGGZFQBSM <=", value, "gggzfqbsm");
            return (Criteria) this;
        }

        public Criteria andGggzfqbsmLike(String value) {
            addCriterion("GGGZFQBSM like", value, "gggzfqbsm");
            return (Criteria) this;
        }

        public Criteria andGggzfqbsmNotLike(String value) {
            addCriterion("GGGZFQBSM not like", value, "gggzfqbsm");
            return (Criteria) this;
        }

        public Criteria andGggzfqbsmIn(List<String> values) {
            addCriterion("GGGZFQBSM in", values, "gggzfqbsm");
            return (Criteria) this;
        }

        public Criteria andGggzfqbsmNotIn(List<String> values) {
            addCriterion("GGGZFQBSM not in", values, "gggzfqbsm");
            return (Criteria) this;
        }

        public Criteria andGggzfqbsmBetween(String value1, String value2) {
            addCriterion("GGGZFQBSM between", value1, value2, "gggzfqbsm");
            return (Criteria) this;
        }

        public Criteria andGggzfqbsmNotBetween(String value1, String value2) {
            addCriterion("GGGZFQBSM not between", value1, value2, "gggzfqbsm");
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

        public Criteria andGggznrIsNull() {
            addCriterion("GGGZNR is null");
            return (Criteria) this;
        }

        public Criteria andGggznrIsNotNull() {
            addCriterion("GGGZNR is not null");
            return (Criteria) this;
        }

        public Criteria andGggznrEqualTo(String value) {
            addCriterion("GGGZNR =", value, "gggznr");
            return (Criteria) this;
        }

        public Criteria andGggznrNotEqualTo(String value) {
            addCriterion("GGGZNR <>", value, "gggznr");
            return (Criteria) this;
        }

        public Criteria andGggznrGreaterThan(String value) {
            addCriterion("GGGZNR >", value, "gggznr");
            return (Criteria) this;
        }

        public Criteria andGggznrGreaterThanOrEqualTo(String value) {
            addCriterion("GGGZNR >=", value, "gggznr");
            return (Criteria) this;
        }

        public Criteria andGggznrLessThan(String value) {
            addCriterion("GGGZNR <", value, "gggznr");
            return (Criteria) this;
        }

        public Criteria andGggznrLessThanOrEqualTo(String value) {
            addCriterion("GGGZNR <=", value, "gggznr");
            return (Criteria) this;
        }

        public Criteria andGggznrLike(String value) {
            addCriterion("GGGZNR like", value, "gggznr");
            return (Criteria) this;
        }

        public Criteria andGggznrNotLike(String value) {
            addCriterion("GGGZNR not like", value, "gggznr");
            return (Criteria) this;
        }

        public Criteria andGggznrIn(List<String> values) {
            addCriterion("GGGZNR in", values, "gggznr");
            return (Criteria) this;
        }

        public Criteria andGggznrNotIn(List<String> values) {
            addCriterion("GGGZNR not in", values, "gggznr");
            return (Criteria) this;
        }

        public Criteria andGggznrBetween(String value1, String value2) {
            addCriterion("GGGZNR between", value1, value2, "gggznr");
            return (Criteria) this;
        }

        public Criteria andGggznrNotBetween(String value1, String value2) {
            addCriterion("GGGZNR not between", value1, value2, "gggznr");
            return (Criteria) this;
        }

        public Criteria andGdsdsjIsNull() {
            addCriterion("GDSDSJ is null");
            return (Criteria) this;
        }

        public Criteria andGdsdsjIsNotNull() {
            addCriterion("GDSDSJ is not null");
            return (Criteria) this;
        }

        public Criteria andGdsdsjEqualTo(LocalDateTime value) {
            addCriterion("GDSDSJ =", value, "gdsdsj");
            return (Criteria) this;
        }

        public Criteria andGdsdsjNotEqualTo(LocalDateTime value) {
            addCriterion("GDSDSJ <>", value, "gdsdsj");
            return (Criteria) this;
        }

        public Criteria andGdsdsjGreaterThan(LocalDateTime value) {
            addCriterion("GDSDSJ >", value, "gdsdsj");
            return (Criteria) this;
        }

        public Criteria andGdsdsjGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("GDSDSJ >=", value, "gdsdsj");
            return (Criteria) this;
        }

        public Criteria andGdsdsjLessThan(LocalDateTime value) {
            addCriterion("GDSDSJ <", value, "gdsdsj");
            return (Criteria) this;
        }

        public Criteria andGdsdsjLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("GDSDSJ <=", value, "gdsdsj");
            return (Criteria) this;
        }

        public Criteria andGdsdsjIn(List<LocalDateTime> values) {
            addCriterion("GDSDSJ in", values, "gdsdsj");
            return (Criteria) this;
        }

        public Criteria andGdsdsjNotIn(List<LocalDateTime> values) {
            addCriterion("GDSDSJ not in", values, "gdsdsj");
            return (Criteria) this;
        }

        public Criteria andGdsdsjBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("GDSDSJ between", value1, value2, "gdsdsj");
            return (Criteria) this;
        }

        public Criteria andGdsdsjNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("GDSDSJ not between", value1, value2, "gdsdsj");
            return (Criteria) this;
        }

        public Criteria andSzdwIsNull() {
            addCriterion("SZDW is null");
            return (Criteria) this;
        }

        public Criteria andSzdwIsNotNull() {
            addCriterion("SZDW is not null");
            return (Criteria) this;
        }

        public Criteria andSzdwEqualTo(String value) {
            addCriterion("SZDW =", value, "szdw");
            return (Criteria) this;
        }

        public Criteria andSzdwNotEqualTo(String value) {
            addCriterion("SZDW <>", value, "szdw");
            return (Criteria) this;
        }

        public Criteria andSzdwGreaterThan(String value) {
            addCriterion("SZDW >", value, "szdw");
            return (Criteria) this;
        }

        public Criteria andSzdwGreaterThanOrEqualTo(String value) {
            addCriterion("SZDW >=", value, "szdw");
            return (Criteria) this;
        }

        public Criteria andSzdwLessThan(String value) {
            addCriterion("SZDW <", value, "szdw");
            return (Criteria) this;
        }

        public Criteria andSzdwLessThanOrEqualTo(String value) {
            addCriterion("SZDW <=", value, "szdw");
            return (Criteria) this;
        }

        public Criteria andSzdwLike(String value) {
            addCriterion("SZDW like", value, "szdw");
            return (Criteria) this;
        }

        public Criteria andSzdwNotLike(String value) {
            addCriterion("SZDW not like", value, "szdw");
            return (Criteria) this;
        }

        public Criteria andSzdwIn(List<String> values) {
            addCriterion("SZDW in", values, "szdw");
            return (Criteria) this;
        }

        public Criteria andSzdwNotIn(List<String> values) {
            addCriterion("SZDW not in", values, "szdw");
            return (Criteria) this;
        }

        public Criteria andSzdwBetween(String value1, String value2) {
            addCriterion("SZDW between", value1, value2, "szdw");
            return (Criteria) this;
        }

        public Criteria andSzdwNotBetween(String value1, String value2) {
            addCriterion("SZDW not between", value1, value2, "szdw");
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