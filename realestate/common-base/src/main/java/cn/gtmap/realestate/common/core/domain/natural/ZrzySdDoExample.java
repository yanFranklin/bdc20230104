package cn.gtmap.realestate.common.core.domain.natural;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

public class ZrzySdDoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ZrzySdDoExample() {
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

        public Criteria andSdlxIsNull() {
            addCriterion("SDLX is null");
            return (Criteria) this;
        }

        public Criteria andSdlxIsNotNull() {
            addCriterion("SDLX is not null");
            return (Criteria) this;
        }

        public Criteria andSdlxEqualTo(String value) {
            addCriterion("SDLX =", value, "sdlx");
            return (Criteria) this;
        }

        public Criteria andSdlxNotEqualTo(String value) {
            addCriterion("SDLX <>", value, "sdlx");
            return (Criteria) this;
        }

        public Criteria andSdlxGreaterThan(String value) {
            addCriterion("SDLX >", value, "sdlx");
            return (Criteria) this;
        }

        public Criteria andSdlxGreaterThanOrEqualTo(String value) {
            addCriterion("SDLX >=", value, "sdlx");
            return (Criteria) this;
        }

        public Criteria andSdlxLessThan(String value) {
            addCriterion("SDLX <", value, "sdlx");
            return (Criteria) this;
        }

        public Criteria andSdlxLessThanOrEqualTo(String value) {
            addCriterion("SDLX <=", value, "sdlx");
            return (Criteria) this;
        }

        public Criteria andSdlxLike(String value) {
            addCriterion("SDLX like", value, "sdlx");
            return (Criteria) this;
        }

        public Criteria andSdlxNotLike(String value) {
            addCriterion("SDLX not like", value, "sdlx");
            return (Criteria) this;
        }

        public Criteria andSdlxIn(List<String> values) {
            addCriterion("SDLX in", values, "sdlx");
            return (Criteria) this;
        }

        public Criteria andSdlxNotIn(List<String> values) {
            addCriterion("SDLX not in", values, "sdlx");
            return (Criteria) this;
        }

        public Criteria andSdlxBetween(String value1, String value2) {
            addCriterion("SDLX between", value1, value2, "sdlx");
            return (Criteria) this;
        }

        public Criteria andSdlxNotBetween(String value1, String value2) {
            addCriterion("SDLX not between", value1, value2, "sdlx");
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

        public Criteria andZmjIsNull() {
            addCriterion("ZMJ is null");
            return (Criteria) this;
        }

        public Criteria andZmjIsNotNull() {
            addCriterion("ZMJ is not null");
            return (Criteria) this;
        }

        public Criteria andZmjEqualTo(BigDecimal value) {
            addCriterion("ZMJ =", value, "zmj");
            return (Criteria) this;
        }

        public Criteria andZmjNotEqualTo(BigDecimal value) {
            addCriterion("ZMJ <>", value, "zmj");
            return (Criteria) this;
        }

        public Criteria andZmjGreaterThan(BigDecimal value) {
            addCriterion("ZMJ >", value, "zmj");
            return (Criteria) this;
        }

        public Criteria andZmjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZMJ >=", value, "zmj");
            return (Criteria) this;
        }

        public Criteria andZmjLessThan(BigDecimal value) {
            addCriterion("ZMJ <", value, "zmj");
            return (Criteria) this;
        }

        public Criteria andZmjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZMJ <=", value, "zmj");
            return (Criteria) this;
        }

        public Criteria andZmjIn(List<BigDecimal> values) {
            addCriterion("ZMJ in", values, "zmj");
            return (Criteria) this;
        }

        public Criteria andZmjNotIn(List<BigDecimal> values) {
            addCriterion("ZMJ not in", values, "zmj");
            return (Criteria) this;
        }

        public Criteria andZmjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZMJ between", value1, value2, "zmj");
            return (Criteria) this;
        }

        public Criteria andZmjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZMJ not between", value1, value2, "zmj");
            return (Criteria) this;
        }

        public Criteria andZblxIsNull() {
            addCriterion("ZBLX is null");
            return (Criteria) this;
        }

        public Criteria andZblxIsNotNull() {
            addCriterion("ZBLX is not null");
            return (Criteria) this;
        }

        public Criteria andZblxEqualTo(String value) {
            addCriterion("ZBLX =", value, "zblx");
            return (Criteria) this;
        }

        public Criteria andZblxNotEqualTo(String value) {
            addCriterion("ZBLX <>", value, "zblx");
            return (Criteria) this;
        }

        public Criteria andZblxGreaterThan(String value) {
            addCriterion("ZBLX >", value, "zblx");
            return (Criteria) this;
        }

        public Criteria andZblxGreaterThanOrEqualTo(String value) {
            addCriterion("ZBLX >=", value, "zblx");
            return (Criteria) this;
        }

        public Criteria andZblxLessThan(String value) {
            addCriterion("ZBLX <", value, "zblx");
            return (Criteria) this;
        }

        public Criteria andZblxLessThanOrEqualTo(String value) {
            addCriterion("ZBLX <=", value, "zblx");
            return (Criteria) this;
        }

        public Criteria andZblxLike(String value) {
            addCriterion("ZBLX like", value, "zblx");
            return (Criteria) this;
        }

        public Criteria andZblxNotLike(String value) {
            addCriterion("ZBLX not like", value, "zblx");
            return (Criteria) this;
        }

        public Criteria andZblxIn(List<String> values) {
            addCriterion("ZBLX in", values, "zblx");
            return (Criteria) this;
        }

        public Criteria andZblxNotIn(List<String> values) {
            addCriterion("ZBLX not in", values, "zblx");
            return (Criteria) this;
        }

        public Criteria andZblxBetween(String value1, String value2) {
            addCriterion("ZBLX between", value1, value2, "zblx");
            return (Criteria) this;
        }

        public Criteria andZblxNotBetween(String value1, String value2) {
            addCriterion("ZBLX not between", value1, value2, "zblx");
            return (Criteria) this;
        }

        public Criteria andZbmjIsNull() {
            addCriterion("ZBMJ is null");
            return (Criteria) this;
        }

        public Criteria andZbmjIsNotNull() {
            addCriterion("ZBMJ is not null");
            return (Criteria) this;
        }

        public Criteria andZbmjEqualTo(BigDecimal value) {
            addCriterion("ZBMJ =", value, "zbmj");
            return (Criteria) this;
        }

        public Criteria andZbmjNotEqualTo(BigDecimal value) {
            addCriterion("ZBMJ <>", value, "zbmj");
            return (Criteria) this;
        }

        public Criteria andZbmjGreaterThan(BigDecimal value) {
            addCriterion("ZBMJ >", value, "zbmj");
            return (Criteria) this;
        }

        public Criteria andZbmjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZBMJ >=", value, "zbmj");
            return (Criteria) this;
        }

        public Criteria andZbmjLessThan(BigDecimal value) {
            addCriterion("ZBMJ <", value, "zbmj");
            return (Criteria) this;
        }

        public Criteria andZbmjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZBMJ <=", value, "zbmj");
            return (Criteria) this;
        }

        public Criteria andZbmjIn(List<BigDecimal> values) {
            addCriterion("ZBMJ in", values, "zbmj");
            return (Criteria) this;
        }

        public Criteria andZbmjNotIn(List<BigDecimal> values) {
            addCriterion("ZBMJ not in", values, "zbmj");
            return (Criteria) this;
        }

        public Criteria andZbmjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZBMJ between", value1, value2, "zbmj");
            return (Criteria) this;
        }

        public Criteria andZbmjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZBMJ not between", value1, value2, "zbmj");
            return (Criteria) this;
        }

        public Criteria andZyyszwzIsNull() {
            addCriterion("ZYYSZWZ is null");
            return (Criteria) this;
        }

        public Criteria andZyyszwzIsNotNull() {
            addCriterion("ZYYSZWZ is not null");
            return (Criteria) this;
        }

        public Criteria andZyyszwzEqualTo(String value) {
            addCriterion("ZYYSZWZ =", value, "zyyszwz");
            return (Criteria) this;
        }

        public Criteria andZyyszwzNotEqualTo(String value) {
            addCriterion("ZYYSZWZ <>", value, "zyyszwz");
            return (Criteria) this;
        }

        public Criteria andZyyszwzGreaterThan(String value) {
            addCriterion("ZYYSZWZ >", value, "zyyszwz");
            return (Criteria) this;
        }

        public Criteria andZyyszwzGreaterThanOrEqualTo(String value) {
            addCriterion("ZYYSZWZ >=", value, "zyyszwz");
            return (Criteria) this;
        }

        public Criteria andZyyszwzLessThan(String value) {
            addCriterion("ZYYSZWZ <", value, "zyyszwz");
            return (Criteria) this;
        }

        public Criteria andZyyszwzLessThanOrEqualTo(String value) {
            addCriterion("ZYYSZWZ <=", value, "zyyszwz");
            return (Criteria) this;
        }

        public Criteria andZyyszwzLike(String value) {
            addCriterion("ZYYSZWZ like", value, "zyyszwz");
            return (Criteria) this;
        }

        public Criteria andZyyszwzNotLike(String value) {
            addCriterion("ZYYSZWZ not like", value, "zyyszwz");
            return (Criteria) this;
        }

        public Criteria andZyyszwzIn(List<String> values) {
            addCriterion("ZYYSZWZ in", values, "zyyszwz");
            return (Criteria) this;
        }

        public Criteria andZyyszwzNotIn(List<String> values) {
            addCriterion("ZYYSZWZ not in", values, "zyyszwz");
            return (Criteria) this;
        }

        public Criteria andZyyszwzBetween(String value1, String value2) {
            addCriterion("ZYYSZWZ between", value1, value2, "zyyszwz");
            return (Criteria) this;
        }

        public Criteria andZyyszwzNotBetween(String value1, String value2) {
            addCriterion("ZYYSZWZ not between", value1, value2, "zyyszwz");
            return (Criteria) this;
        }

        public Criteria andGjzdbhdzysdnlIsNull() {
            addCriterion("GJZDBHDZYSDNL is null");
            return (Criteria) this;
        }

        public Criteria andGjzdbhdzysdnlIsNotNull() {
            addCriterion("GJZDBHDZYSDNL is not null");
            return (Criteria) this;
        }

        public Criteria andGjzdbhdzysdnlEqualTo(String value) {
            addCriterion("GJZDBHDZYSDNL =", value, "gjzdbhdzysdnl");
            return (Criteria) this;
        }

        public Criteria andGjzdbhdzysdnlNotEqualTo(String value) {
            addCriterion("GJZDBHDZYSDNL <>", value, "gjzdbhdzysdnl");
            return (Criteria) this;
        }

        public Criteria andGjzdbhdzysdnlGreaterThan(String value) {
            addCriterion("GJZDBHDZYSDNL >", value, "gjzdbhdzysdnl");
            return (Criteria) this;
        }

        public Criteria andGjzdbhdzysdnlGreaterThanOrEqualTo(String value) {
            addCriterion("GJZDBHDZYSDNL >=", value, "gjzdbhdzysdnl");
            return (Criteria) this;
        }

        public Criteria andGjzdbhdzysdnlLessThan(String value) {
            addCriterion("GJZDBHDZYSDNL <", value, "gjzdbhdzysdnl");
            return (Criteria) this;
        }

        public Criteria andGjzdbhdzysdnlLessThanOrEqualTo(String value) {
            addCriterion("GJZDBHDZYSDNL <=", value, "gjzdbhdzysdnl");
            return (Criteria) this;
        }

        public Criteria andGjzdbhdzysdnlLike(String value) {
            addCriterion("GJZDBHDZYSDNL like", value, "gjzdbhdzysdnl");
            return (Criteria) this;
        }

        public Criteria andGjzdbhdzysdnlNotLike(String value) {
            addCriterion("GJZDBHDZYSDNL not like", value, "gjzdbhdzysdnl");
            return (Criteria) this;
        }

        public Criteria andGjzdbhdzysdnlIn(List<String> values) {
            addCriterion("GJZDBHDZYSDNL in", values, "gjzdbhdzysdnl");
            return (Criteria) this;
        }

        public Criteria andGjzdbhdzysdnlNotIn(List<String> values) {
            addCriterion("GJZDBHDZYSDNL not in", values, "gjzdbhdzysdnl");
            return (Criteria) this;
        }

        public Criteria andGjzdbhdzysdnlBetween(String value1, String value2) {
            addCriterion("GJZDBHDZYSDNL between", value1, value2, "gjzdbhdzysdnl");
            return (Criteria) this;
        }

        public Criteria andGjzdbhdzysdnlNotBetween(String value1, String value2) {
            addCriterion("GJZDBHDZYSDNL not between", value1, value2, "gjzdbhdzysdnl");
            return (Criteria) this;
        }

        public Criteria andSzlbIsNull() {
            addCriterion("SZLB is null");
            return (Criteria) this;
        }

        public Criteria andSzlbIsNotNull() {
            addCriterion("SZLB is not null");
            return (Criteria) this;
        }

        public Criteria andSzlbEqualTo(String value) {
            addCriterion("SZLB =", value, "szlb");
            return (Criteria) this;
        }

        public Criteria andSzlbNotEqualTo(String value) {
            addCriterion("SZLB <>", value, "szlb");
            return (Criteria) this;
        }

        public Criteria andSzlbGreaterThan(String value) {
            addCriterion("SZLB >", value, "szlb");
            return (Criteria) this;
        }

        public Criteria andSzlbGreaterThanOrEqualTo(String value) {
            addCriterion("SZLB >=", value, "szlb");
            return (Criteria) this;
        }

        public Criteria andSzlbLessThan(String value) {
            addCriterion("SZLB <", value, "szlb");
            return (Criteria) this;
        }

        public Criteria andSzlbLessThanOrEqualTo(String value) {
            addCriterion("SZLB <=", value, "szlb");
            return (Criteria) this;
        }

        public Criteria andSzlbLike(String value) {
            addCriterion("SZLB like", value, "szlb");
            return (Criteria) this;
        }

        public Criteria andSzlbNotLike(String value) {
            addCriterion("SZLB not like", value, "szlb");
            return (Criteria) this;
        }

        public Criteria andSzlbIn(List<String> values) {
            addCriterion("SZLB in", values, "szlb");
            return (Criteria) this;
        }

        public Criteria andSzlbNotIn(List<String> values) {
            addCriterion("SZLB not in", values, "szlb");
            return (Criteria) this;
        }

        public Criteria andSzlbBetween(String value1, String value2) {
            addCriterion("SZLB between", value1, value2, "szlb");
            return (Criteria) this;
        }

        public Criteria andSzlbNotBetween(String value1, String value2) {
            addCriterion("SZLB not between", value1, value2, "szlb");
            return (Criteria) this;
        }

        public Criteria andSybjzkIsNull() {
            addCriterion("SYBJZK is null");
            return (Criteria) this;
        }

        public Criteria andSybjzkIsNotNull() {
            addCriterion("SYBJZK is not null");
            return (Criteria) this;
        }

        public Criteria andSybjzkEqualTo(String value) {
            addCriterion("SYBJZK =", value, "sybjzk");
            return (Criteria) this;
        }

        public Criteria andSybjzkNotEqualTo(String value) {
            addCriterion("SYBJZK <>", value, "sybjzk");
            return (Criteria) this;
        }

        public Criteria andSybjzkGreaterThan(String value) {
            addCriterion("SYBJZK >", value, "sybjzk");
            return (Criteria) this;
        }

        public Criteria andSybjzkGreaterThanOrEqualTo(String value) {
            addCriterion("SYBJZK >=", value, "sybjzk");
            return (Criteria) this;
        }

        public Criteria andSybjzkLessThan(String value) {
            addCriterion("SYBJZK <", value, "sybjzk");
            return (Criteria) this;
        }

        public Criteria andSybjzkLessThanOrEqualTo(String value) {
            addCriterion("SYBJZK <=", value, "sybjzk");
            return (Criteria) this;
        }

        public Criteria andSybjzkLike(String value) {
            addCriterion("SYBJZK like", value, "sybjzk");
            return (Criteria) this;
        }

        public Criteria andSybjzkNotLike(String value) {
            addCriterion("SYBJZK not like", value, "sybjzk");
            return (Criteria) this;
        }

        public Criteria andSybjzkIn(List<String> values) {
            addCriterion("SYBJZK in", values, "sybjzk");
            return (Criteria) this;
        }

        public Criteria andSybjzkNotIn(List<String> values) {
            addCriterion("SYBJZK not in", values, "sybjzk");
            return (Criteria) this;
        }

        public Criteria andSybjzkBetween(String value1, String value2) {
            addCriterion("SYBJZK between", value1, value2, "sybjzk");
            return (Criteria) this;
        }

        public Criteria andSybjzkNotBetween(String value1, String value2) {
            addCriterion("SYBJZK not between", value1, value2, "sybjzk");
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