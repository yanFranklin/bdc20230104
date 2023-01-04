package cn.gtmap.realestate.common.core.domain.natural;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

public class ZrzySlDoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ZrzySlDoExample() {
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

        public Criteria andSllxIsNull() {
            addCriterion("SLLX is null");
            return (Criteria) this;
        }

        public Criteria andSllxIsNotNull() {
            addCriterion("SLLX is not null");
            return (Criteria) this;
        }

        public Criteria andSllxEqualTo(String value) {
            addCriterion("SLLX =", value, "sllx");
            return (Criteria) this;
        }

        public Criteria andSllxNotEqualTo(String value) {
            addCriterion("SLLX <>", value, "sllx");
            return (Criteria) this;
        }

        public Criteria andSllxGreaterThan(String value) {
            addCriterion("SLLX >", value, "sllx");
            return (Criteria) this;
        }

        public Criteria andSllxGreaterThanOrEqualTo(String value) {
            addCriterion("SLLX >=", value, "sllx");
            return (Criteria) this;
        }

        public Criteria andSllxLessThan(String value) {
            addCriterion("SLLX <", value, "sllx");
            return (Criteria) this;
        }

        public Criteria andSllxLessThanOrEqualTo(String value) {
            addCriterion("SLLX <=", value, "sllx");
            return (Criteria) this;
        }

        public Criteria andSllxLike(String value) {
            addCriterion("SLLX like", value, "sllx");
            return (Criteria) this;
        }

        public Criteria andSllxNotLike(String value) {
            addCriterion("SLLX not like", value, "sllx");
            return (Criteria) this;
        }

        public Criteria andSllxIn(List<String> values) {
            addCriterion("SLLX in", values, "sllx");
            return (Criteria) this;
        }

        public Criteria andSllxNotIn(List<String> values) {
            addCriterion("SLLX not in", values, "sllx");
            return (Criteria) this;
        }

        public Criteria andSllxBetween(String value1, String value2) {
            addCriterion("SLLX between", value1, value2, "sllx");
            return (Criteria) this;
        }

        public Criteria andSllxNotBetween(String value1, String value2) {
            addCriterion("SLLX not between", value1, value2, "sllx");
            return (Criteria) this;
        }

        public Criteria andBhtbslIsNull() {
            addCriterion("BHTBSL is null");
            return (Criteria) this;
        }

        public Criteria andBhtbslIsNotNull() {
            addCriterion("BHTBSL is not null");
            return (Criteria) this;
        }

        public Criteria andBhtbslEqualTo(String value) {
            addCriterion("BHTBSL =", value, "bhtbsl");
            return (Criteria) this;
        }

        public Criteria andBhtbslNotEqualTo(String value) {
            addCriterion("BHTBSL <>", value, "bhtbsl");
            return (Criteria) this;
        }

        public Criteria andBhtbslGreaterThan(String value) {
            addCriterion("BHTBSL >", value, "bhtbsl");
            return (Criteria) this;
        }

        public Criteria andBhtbslGreaterThanOrEqualTo(String value) {
            addCriterion("BHTBSL >=", value, "bhtbsl");
            return (Criteria) this;
        }

        public Criteria andBhtbslLessThan(String value) {
            addCriterion("BHTBSL <", value, "bhtbsl");
            return (Criteria) this;
        }

        public Criteria andBhtbslLessThanOrEqualTo(String value) {
            addCriterion("BHTBSL <=", value, "bhtbsl");
            return (Criteria) this;
        }

        public Criteria andBhtbslLike(String value) {
            addCriterion("BHTBSL like", value, "bhtbsl");
            return (Criteria) this;
        }

        public Criteria andBhtbslNotLike(String value) {
            addCriterion("BHTBSL not like", value, "bhtbsl");
            return (Criteria) this;
        }

        public Criteria andBhtbslIn(List<String> values) {
            addCriterion("BHTBSL in", values, "bhtbsl");
            return (Criteria) this;
        }

        public Criteria andBhtbslNotIn(List<String> values) {
            addCriterion("BHTBSL not in", values, "bhtbsl");
            return (Criteria) this;
        }

        public Criteria andBhtbslBetween(String value1, String value2) {
            addCriterion("BHTBSL between", value1, value2, "bhtbsl");
            return (Criteria) this;
        }

        public Criteria andBhtbslNotBetween(String value1, String value2) {
            addCriterion("BHTBSL not between", value1, value2, "bhtbsl");
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

        public Criteria andZdgnIsNull() {
            addCriterion("ZDGN is null");
            return (Criteria) this;
        }

        public Criteria andZdgnIsNotNull() {
            addCriterion("ZDGN is not null");
            return (Criteria) this;
        }

        public Criteria andZdgnEqualTo(String value) {
            addCriterion("ZDGN =", value, "zdgn");
            return (Criteria) this;
        }

        public Criteria andZdgnNotEqualTo(String value) {
            addCriterion("ZDGN <>", value, "zdgn");
            return (Criteria) this;
        }

        public Criteria andZdgnGreaterThan(String value) {
            addCriterion("ZDGN >", value, "zdgn");
            return (Criteria) this;
        }

        public Criteria andZdgnGreaterThanOrEqualTo(String value) {
            addCriterion("ZDGN >=", value, "zdgn");
            return (Criteria) this;
        }

        public Criteria andZdgnLessThan(String value) {
            addCriterion("ZDGN <", value, "zdgn");
            return (Criteria) this;
        }

        public Criteria andZdgnLessThanOrEqualTo(String value) {
            addCriterion("ZDGN <=", value, "zdgn");
            return (Criteria) this;
        }

        public Criteria andZdgnLike(String value) {
            addCriterion("ZDGN like", value, "zdgn");
            return (Criteria) this;
        }

        public Criteria andZdgnNotLike(String value) {
            addCriterion("ZDGN not like", value, "zdgn");
            return (Criteria) this;
        }

        public Criteria andZdgnIn(List<String> values) {
            addCriterion("ZDGN in", values, "zdgn");
            return (Criteria) this;
        }

        public Criteria andZdgnNotIn(List<String> values) {
            addCriterion("ZDGN not in", values, "zdgn");
            return (Criteria) this;
        }

        public Criteria andZdgnBetween(String value1, String value2) {
            addCriterion("ZDGN between", value1, value2, "zdgn");
            return (Criteria) this;
        }

        public Criteria andZdgnNotBetween(String value1, String value2) {
            addCriterion("ZDGN not between", value1, value2, "zdgn");
            return (Criteria) this;
        }

        public Criteria andZyszIsNull() {
            addCriterion("ZYSZ is null");
            return (Criteria) this;
        }

        public Criteria andZyszIsNotNull() {
            addCriterion("ZYSZ is not null");
            return (Criteria) this;
        }

        public Criteria andZyszEqualTo(String value) {
            addCriterion("ZYSZ =", value, "zysz");
            return (Criteria) this;
        }

        public Criteria andZyszNotEqualTo(String value) {
            addCriterion("ZYSZ <>", value, "zysz");
            return (Criteria) this;
        }

        public Criteria andZyszGreaterThan(String value) {
            addCriterion("ZYSZ >", value, "zysz");
            return (Criteria) this;
        }

        public Criteria andZyszGreaterThanOrEqualTo(String value) {
            addCriterion("ZYSZ >=", value, "zysz");
            return (Criteria) this;
        }

        public Criteria andZyszLessThan(String value) {
            addCriterion("ZYSZ <", value, "zysz");
            return (Criteria) this;
        }

        public Criteria andZyszLessThanOrEqualTo(String value) {
            addCriterion("ZYSZ <=", value, "zysz");
            return (Criteria) this;
        }

        public Criteria andZyszLike(String value) {
            addCriterion("ZYSZ like", value, "zysz");
            return (Criteria) this;
        }

        public Criteria andZyszNotLike(String value) {
            addCriterion("ZYSZ not like", value, "zysz");
            return (Criteria) this;
        }

        public Criteria andZyszIn(List<String> values) {
            addCriterion("ZYSZ in", values, "zysz");
            return (Criteria) this;
        }

        public Criteria andZyszNotIn(List<String> values) {
            addCriterion("ZYSZ not in", values, "zysz");
            return (Criteria) this;
        }

        public Criteria andZyszBetween(String value1, String value2) {
            addCriterion("ZYSZ between", value1, value2, "zysz");
            return (Criteria) this;
        }

        public Criteria andZyszNotBetween(String value1, String value2) {
            addCriterion("ZYSZ not between", value1, value2, "zysz");
            return (Criteria) this;
        }

        public Criteria andLzIsNull() {
            addCriterion("LZ is null");
            return (Criteria) this;
        }

        public Criteria andLzIsNotNull() {
            addCriterion("LZ is not null");
            return (Criteria) this;
        }

        public Criteria andLzEqualTo(String value) {
            addCriterion("LZ =", value, "lz");
            return (Criteria) this;
        }

        public Criteria andLzNotEqualTo(String value) {
            addCriterion("LZ <>", value, "lz");
            return (Criteria) this;
        }

        public Criteria andLzGreaterThan(String value) {
            addCriterion("LZ >", value, "lz");
            return (Criteria) this;
        }

        public Criteria andLzGreaterThanOrEqualTo(String value) {
            addCriterion("LZ >=", value, "lz");
            return (Criteria) this;
        }

        public Criteria andLzLessThan(String value) {
            addCriterion("LZ <", value, "lz");
            return (Criteria) this;
        }

        public Criteria andLzLessThanOrEqualTo(String value) {
            addCriterion("LZ <=", value, "lz");
            return (Criteria) this;
        }

        public Criteria andLzLike(String value) {
            addCriterion("LZ like", value, "lz");
            return (Criteria) this;
        }

        public Criteria andLzNotLike(String value) {
            addCriterion("LZ not like", value, "lz");
            return (Criteria) this;
        }

        public Criteria andLzIn(List<String> values) {
            addCriterion("LZ in", values, "lz");
            return (Criteria) this;
        }

        public Criteria andLzNotIn(List<String> values) {
            addCriterion("LZ not in", values, "lz");
            return (Criteria) this;
        }

        public Criteria andLzBetween(String value1, String value2) {
            addCriterion("LZ between", value1, value2, "lz");
            return (Criteria) this;
        }

        public Criteria andLzNotBetween(String value1, String value2) {
            addCriterion("LZ not between", value1, value2, "lz");
            return (Criteria) this;
        }

        public Criteria andZxjlIsNull() {
            addCriterion("ZXJL is null");
            return (Criteria) this;
        }

        public Criteria andZxjlIsNotNull() {
            addCriterion("ZXJL is not null");
            return (Criteria) this;
        }

        public Criteria andZxjlEqualTo(String value) {
            addCriterion("ZXJL =", value, "zxjl");
            return (Criteria) this;
        }

        public Criteria andZxjlNotEqualTo(String value) {
            addCriterion("ZXJL <>", value, "zxjl");
            return (Criteria) this;
        }

        public Criteria andZxjlGreaterThan(String value) {
            addCriterion("ZXJL >", value, "zxjl");
            return (Criteria) this;
        }

        public Criteria andZxjlGreaterThanOrEqualTo(String value) {
            addCriterion("ZXJL >=", value, "zxjl");
            return (Criteria) this;
        }

        public Criteria andZxjlLessThan(String value) {
            addCriterion("ZXJL <", value, "zxjl");
            return (Criteria) this;
        }

        public Criteria andZxjlLessThanOrEqualTo(String value) {
            addCriterion("ZXJL <=", value, "zxjl");
            return (Criteria) this;
        }

        public Criteria andZxjlLike(String value) {
            addCriterion("ZXJL like", value, "zxjl");
            return (Criteria) this;
        }

        public Criteria andZxjlNotLike(String value) {
            addCriterion("ZXJL not like", value, "zxjl");
            return (Criteria) this;
        }

        public Criteria andZxjlIn(List<String> values) {
            addCriterion("ZXJL in", values, "zxjl");
            return (Criteria) this;
        }

        public Criteria andZxjlNotIn(List<String> values) {
            addCriterion("ZXJL not in", values, "zxjl");
            return (Criteria) this;
        }

        public Criteria andZxjlBetween(String value1, String value2) {
            addCriterion("ZXJL between", value1, value2, "zxjl");
            return (Criteria) this;
        }

        public Criteria andZxjlNotBetween(String value1, String value2) {
            addCriterion("ZXJL not between", value1, value2, "zxjl");
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