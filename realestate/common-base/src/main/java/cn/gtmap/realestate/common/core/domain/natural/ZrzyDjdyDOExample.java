package cn.gtmap.realestate.common.core.domain.natural;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

public class ZrzyDjdyDOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ZrzyDjdyDOExample() {
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

        public Criteria andZlIsNull() {
            addCriterion("ZL is null");
            return (Criteria) this;
        }

        public Criteria andZlIsNotNull() {
            addCriterion("ZL is not null");
            return (Criteria) this;
        }

        public Criteria andZlEqualTo(String value) {
            addCriterion("ZL =", value, "zl");
            return (Criteria) this;
        }

        public Criteria andZlNotEqualTo(String value) {
            addCriterion("ZL <>", value, "zl");
            return (Criteria) this;
        }

        public Criteria andZlGreaterThan(String value) {
            addCriterion("ZL >", value, "zl");
            return (Criteria) this;
        }

        public Criteria andZlGreaterThanOrEqualTo(String value) {
            addCriterion("ZL >=", value, "zl");
            return (Criteria) this;
        }

        public Criteria andZlLessThan(String value) {
            addCriterion("ZL <", value, "zl");
            return (Criteria) this;
        }

        public Criteria andZlLessThanOrEqualTo(String value) {
            addCriterion("ZL <=", value, "zl");
            return (Criteria) this;
        }

        public Criteria andZlLike(String value) {
            addCriterion("ZL like", value, "zl");
            return (Criteria) this;
        }

        public Criteria andZlNotLike(String value) {
            addCriterion("ZL not like", value, "zl");
            return (Criteria) this;
        }

        public Criteria andZlIn(List<String> values) {
            addCriterion("ZL in", values, "zl");
            return (Criteria) this;
        }

        public Criteria andZlNotIn(List<String> values) {
            addCriterion("ZL not in", values, "zl");
            return (Criteria) this;
        }

        public Criteria andZlBetween(String value1, String value2) {
            addCriterion("ZL between", value1, value2, "zl");
            return (Criteria) this;
        }

        public Criteria andZlNotBetween(String value1, String value2) {
            addCriterion("ZL not between", value1, value2, "zl");
            return (Criteria) this;
        }

        public Criteria andDjdyzmjIsNull() {
            addCriterion("DJDYZMJ is null");
            return (Criteria) this;
        }

        public Criteria andDjdyzmjIsNotNull() {
            addCriterion("DJDYZMJ is not null");
            return (Criteria) this;
        }

        public Criteria andDjdyzmjEqualTo(BigDecimal value) {
            addCriterion("DJDYZMJ =", value, "djdyzmj");
            return (Criteria) this;
        }

        public Criteria andDjdyzmjNotEqualTo(BigDecimal value) {
            addCriterion("DJDYZMJ <>", value, "djdyzmj");
            return (Criteria) this;
        }

        public Criteria andDjdyzmjGreaterThan(BigDecimal value) {
            addCriterion("DJDYZMJ >", value, "djdyzmj");
            return (Criteria) this;
        }

        public Criteria andDjdyzmjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DJDYZMJ >=", value, "djdyzmj");
            return (Criteria) this;
        }

        public Criteria andDjdyzmjLessThan(BigDecimal value) {
            addCriterion("DJDYZMJ <", value, "djdyzmj");
            return (Criteria) this;
        }

        public Criteria andDjdyzmjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DJDYZMJ <=", value, "djdyzmj");
            return (Criteria) this;
        }

        public Criteria andDjdyzmjIn(List<BigDecimal> values) {
            addCriterion("DJDYZMJ in", values, "djdyzmj");
            return (Criteria) this;
        }

        public Criteria andDjdyzmjNotIn(List<BigDecimal> values) {
            addCriterion("DJDYZMJ not in", values, "djdyzmj");
            return (Criteria) this;
        }

        public Criteria andDjdyzmjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DJDYZMJ between", value1, value2, "djdyzmj");
            return (Criteria) this;
        }

        public Criteria andDjdyzmjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DJDYZMJ not between", value1, value2, "djdyzmj");
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

        public Criteria andDyszdIsNull() {
            addCriterion("DYSZD is null");
            return (Criteria) this;
        }

        public Criteria andDyszdIsNotNull() {
            addCriterion("DYSZD is not null");
            return (Criteria) this;
        }

        public Criteria andDyszdEqualTo(String value) {
            addCriterion("DYSZD =", value, "dyszd");
            return (Criteria) this;
        }

        public Criteria andDyszdNotEqualTo(String value) {
            addCriterion("DYSZD <>", value, "dyszd");
            return (Criteria) this;
        }

        public Criteria andDyszdGreaterThan(String value) {
            addCriterion("DYSZD >", value, "dyszd");
            return (Criteria) this;
        }

        public Criteria andDyszdGreaterThanOrEqualTo(String value) {
            addCriterion("DYSZD >=", value, "dyszd");
            return (Criteria) this;
        }

        public Criteria andDyszdLessThan(String value) {
            addCriterion("DYSZD <", value, "dyszd");
            return (Criteria) this;
        }

        public Criteria andDyszdLessThanOrEqualTo(String value) {
            addCriterion("DYSZD <=", value, "dyszd");
            return (Criteria) this;
        }

        public Criteria andDyszdLike(String value) {
            addCriterion("DYSZD like", value, "dyszd");
            return (Criteria) this;
        }

        public Criteria andDyszdNotLike(String value) {
            addCriterion("DYSZD not like", value, "dyszd");
            return (Criteria) this;
        }

        public Criteria andDyszdIn(List<String> values) {
            addCriterion("DYSZD in", values, "dyszd");
            return (Criteria) this;
        }

        public Criteria andDyszdNotIn(List<String> values) {
            addCriterion("DYSZD not in", values, "dyszd");
            return (Criteria) this;
        }

        public Criteria andDyszdBetween(String value1, String value2) {
            addCriterion("DYSZD between", value1, value2, "dyszd");
            return (Criteria) this;
        }

        public Criteria andDyszdNotBetween(String value1, String value2) {
            addCriterion("DYSZD not between", value1, value2, "dyszd");
            return (Criteria) this;
        }

        public Criteria andDysznIsNull() {
            addCriterion("DYSZN is null");
            return (Criteria) this;
        }

        public Criteria andDysznIsNotNull() {
            addCriterion("DYSZN is not null");
            return (Criteria) this;
        }

        public Criteria andDysznEqualTo(String value) {
            addCriterion("DYSZN =", value, "dyszn");
            return (Criteria) this;
        }

        public Criteria andDysznNotEqualTo(String value) {
            addCriterion("DYSZN <>", value, "dyszn");
            return (Criteria) this;
        }

        public Criteria andDysznGreaterThan(String value) {
            addCriterion("DYSZN >", value, "dyszn");
            return (Criteria) this;
        }

        public Criteria andDysznGreaterThanOrEqualTo(String value) {
            addCriterion("DYSZN >=", value, "dyszn");
            return (Criteria) this;
        }

        public Criteria andDysznLessThan(String value) {
            addCriterion("DYSZN <", value, "dyszn");
            return (Criteria) this;
        }

        public Criteria andDysznLessThanOrEqualTo(String value) {
            addCriterion("DYSZN <=", value, "dyszn");
            return (Criteria) this;
        }

        public Criteria andDysznLike(String value) {
            addCriterion("DYSZN like", value, "dyszn");
            return (Criteria) this;
        }

        public Criteria andDysznNotLike(String value) {
            addCriterion("DYSZN not like", value, "dyszn");
            return (Criteria) this;
        }

        public Criteria andDysznIn(List<String> values) {
            addCriterion("DYSZN in", values, "dyszn");
            return (Criteria) this;
        }

        public Criteria andDysznNotIn(List<String> values) {
            addCriterion("DYSZN not in", values, "dyszn");
            return (Criteria) this;
        }

        public Criteria andDysznBetween(String value1, String value2) {
            addCriterion("DYSZN between", value1, value2, "dyszn");
            return (Criteria) this;
        }

        public Criteria andDysznNotBetween(String value1, String value2) {
            addCriterion("DYSZN not between", value1, value2, "dyszn");
            return (Criteria) this;
        }

        public Criteria andDyszxIsNull() {
            addCriterion("DYSZX is null");
            return (Criteria) this;
        }

        public Criteria andDyszxIsNotNull() {
            addCriterion("DYSZX is not null");
            return (Criteria) this;
        }

        public Criteria andDyszxEqualTo(String value) {
            addCriterion("DYSZX =", value, "dyszx");
            return (Criteria) this;
        }

        public Criteria andDyszxNotEqualTo(String value) {
            addCriterion("DYSZX <>", value, "dyszx");
            return (Criteria) this;
        }

        public Criteria andDyszxGreaterThan(String value) {
            addCriterion("DYSZX >", value, "dyszx");
            return (Criteria) this;
        }

        public Criteria andDyszxGreaterThanOrEqualTo(String value) {
            addCriterion("DYSZX >=", value, "dyszx");
            return (Criteria) this;
        }

        public Criteria andDyszxLessThan(String value) {
            addCriterion("DYSZX <", value, "dyszx");
            return (Criteria) this;
        }

        public Criteria andDyszxLessThanOrEqualTo(String value) {
            addCriterion("DYSZX <=", value, "dyszx");
            return (Criteria) this;
        }

        public Criteria andDyszxLike(String value) {
            addCriterion("DYSZX like", value, "dyszx");
            return (Criteria) this;
        }

        public Criteria andDyszxNotLike(String value) {
            addCriterion("DYSZX not like", value, "dyszx");
            return (Criteria) this;
        }

        public Criteria andDyszxIn(List<String> values) {
            addCriterion("DYSZX in", values, "dyszx");
            return (Criteria) this;
        }

        public Criteria andDyszxNotIn(List<String> values) {
            addCriterion("DYSZX not in", values, "dyszx");
            return (Criteria) this;
        }

        public Criteria andDyszxBetween(String value1, String value2) {
            addCriterion("DYSZX between", value1, value2, "dyszx");
            return (Criteria) this;
        }

        public Criteria andDyszxNotBetween(String value1, String value2) {
            addCriterion("DYSZX not between", value1, value2, "dyszx");
            return (Criteria) this;
        }

        public Criteria andDyszbIsNull() {
            addCriterion("DYSZB is null");
            return (Criteria) this;
        }

        public Criteria andDyszbIsNotNull() {
            addCriterion("DYSZB is not null");
            return (Criteria) this;
        }

        public Criteria andDyszbEqualTo(String value) {
            addCriterion("DYSZB =", value, "dyszb");
            return (Criteria) this;
        }

        public Criteria andDyszbNotEqualTo(String value) {
            addCriterion("DYSZB <>", value, "dyszb");
            return (Criteria) this;
        }

        public Criteria andDyszbGreaterThan(String value) {
            addCriterion("DYSZB >", value, "dyszb");
            return (Criteria) this;
        }

        public Criteria andDyszbGreaterThanOrEqualTo(String value) {
            addCriterion("DYSZB >=", value, "dyszb");
            return (Criteria) this;
        }

        public Criteria andDyszbLessThan(String value) {
            addCriterion("DYSZB <", value, "dyszb");
            return (Criteria) this;
        }

        public Criteria andDyszbLessThanOrEqualTo(String value) {
            addCriterion("DYSZB <=", value, "dyszb");
            return (Criteria) this;
        }

        public Criteria andDyszbLike(String value) {
            addCriterion("DYSZB like", value, "dyszb");
            return (Criteria) this;
        }

        public Criteria andDyszbNotLike(String value) {
            addCriterion("DYSZB not like", value, "dyszb");
            return (Criteria) this;
        }

        public Criteria andDyszbIn(List<String> values) {
            addCriterion("DYSZB in", values, "dyszb");
            return (Criteria) this;
        }

        public Criteria andDyszbNotIn(List<String> values) {
            addCriterion("DYSZB not in", values, "dyszb");
            return (Criteria) this;
        }

        public Criteria andDyszbBetween(String value1, String value2) {
            addCriterion("DYSZB between", value1, value2, "dyszb");
            return (Criteria) this;
        }

        public Criteria andDyszbNotBetween(String value1, String value2) {
            addCriterion("DYSZB not between", value1, value2, "dyszb");
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

        public Criteria andDynzrzyzmjIsNull() {
            addCriterion("DYNZRZYZMJ is null");
            return (Criteria) this;
        }

        public Criteria andDynzrzyzmjIsNotNull() {
            addCriterion("DYNZRZYZMJ is not null");
            return (Criteria) this;
        }

        public Criteria andDynzrzyzmjEqualTo(BigDecimal value) {
            addCriterion("DYNZRZYZMJ =", value, "dynzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andDynzrzyzmjNotEqualTo(BigDecimal value) {
            addCriterion("DYNZRZYZMJ <>", value, "dynzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andDynzrzyzmjGreaterThan(BigDecimal value) {
            addCriterion("DYNZRZYZMJ >", value, "dynzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andDynzrzyzmjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DYNZRZYZMJ >=", value, "dynzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andDynzrzyzmjLessThan(BigDecimal value) {
            addCriterion("DYNZRZYZMJ <", value, "dynzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andDynzrzyzmjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DYNZRZYZMJ <=", value, "dynzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andDynzrzyzmjIn(List<BigDecimal> values) {
            addCriterion("DYNZRZYZMJ in", values, "dynzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andDynzrzyzmjNotIn(List<BigDecimal> values) {
            addCriterion("DYNZRZYZMJ not in", values, "dynzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andDynzrzyzmjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DYNZRZYZMJ between", value1, value2, "dynzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andDynzrzyzmjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DYNZRZYZMJ not between", value1, value2, "dynzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andSzymjIsNull() {
            addCriterion("SZYMJ is null");
            return (Criteria) this;
        }

        public Criteria andSzymjIsNotNull() {
            addCriterion("SZYMJ is not null");
            return (Criteria) this;
        }

        public Criteria andSzymjEqualTo(BigDecimal value) {
            addCriterion("SZYMJ =", value, "szymj");
            return (Criteria) this;
        }

        public Criteria andSzymjNotEqualTo(BigDecimal value) {
            addCriterion("SZYMJ <>", value, "szymj");
            return (Criteria) this;
        }

        public Criteria andSzymjGreaterThan(BigDecimal value) {
            addCriterion("SZYMJ >", value, "szymj");
            return (Criteria) this;
        }

        public Criteria andSzymjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SZYMJ >=", value, "szymj");
            return (Criteria) this;
        }

        public Criteria andSzymjLessThan(BigDecimal value) {
            addCriterion("SZYMJ <", value, "szymj");
            return (Criteria) this;
        }

        public Criteria andSzymjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SZYMJ <=", value, "szymj");
            return (Criteria) this;
        }

        public Criteria andSzymjIn(List<BigDecimal> values) {
            addCriterion("SZYMJ in", values, "szymj");
            return (Criteria) this;
        }

        public Criteria andSzymjNotIn(List<BigDecimal> values) {
            addCriterion("SZYMJ not in", values, "szymj");
            return (Criteria) this;
        }

        public Criteria andSzymjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SZYMJ between", value1, value2, "szymj");
            return (Criteria) this;
        }

        public Criteria andSzymjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SZYMJ not between", value1, value2, "szymj");
            return (Criteria) this;
        }

        public Criteria andSdmjIsNull() {
            addCriterion("SDMJ is null");
            return (Criteria) this;
        }

        public Criteria andSdmjIsNotNull() {
            addCriterion("SDMJ is not null");
            return (Criteria) this;
        }

        public Criteria andSdmjEqualTo(BigDecimal value) {
            addCriterion("SDMJ =", value, "sdmj");
            return (Criteria) this;
        }

        public Criteria andSdmjNotEqualTo(BigDecimal value) {
            addCriterion("SDMJ <>", value, "sdmj");
            return (Criteria) this;
        }

        public Criteria andSdmjGreaterThan(BigDecimal value) {
            addCriterion("SDMJ >", value, "sdmj");
            return (Criteria) this;
        }

        public Criteria andSdmjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SDMJ >=", value, "sdmj");
            return (Criteria) this;
        }

        public Criteria andSdmjLessThan(BigDecimal value) {
            addCriterion("SDMJ <", value, "sdmj");
            return (Criteria) this;
        }

        public Criteria andSdmjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SDMJ <=", value, "sdmj");
            return (Criteria) this;
        }

        public Criteria andSdmjIn(List<BigDecimal> values) {
            addCriterion("SDMJ in", values, "sdmj");
            return (Criteria) this;
        }

        public Criteria andSdmjNotIn(List<BigDecimal> values) {
            addCriterion("SDMJ not in", values, "sdmj");
            return (Criteria) this;
        }

        public Criteria andSdmjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SDMJ between", value1, value2, "sdmj");
            return (Criteria) this;
        }

        public Criteria andSdmjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SDMJ not between", value1, value2, "sdmj");
            return (Criteria) this;
        }

        public Criteria andCymjIsNull() {
            addCriterion("CYMJ is null");
            return (Criteria) this;
        }

        public Criteria andCymjIsNotNull() {
            addCriterion("CYMJ is not null");
            return (Criteria) this;
        }

        public Criteria andCymjEqualTo(BigDecimal value) {
            addCriterion("CYMJ =", value, "cymj");
            return (Criteria) this;
        }

        public Criteria andCymjNotEqualTo(BigDecimal value) {
            addCriterion("CYMJ <>", value, "cymj");
            return (Criteria) this;
        }

        public Criteria andCymjGreaterThan(BigDecimal value) {
            addCriterion("CYMJ >", value, "cymj");
            return (Criteria) this;
        }

        public Criteria andCymjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CYMJ >=", value, "cymj");
            return (Criteria) this;
        }

        public Criteria andCymjLessThan(BigDecimal value) {
            addCriterion("CYMJ <", value, "cymj");
            return (Criteria) this;
        }

        public Criteria andCymjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CYMJ <=", value, "cymj");
            return (Criteria) this;
        }

        public Criteria andCymjIn(List<BigDecimal> values) {
            addCriterion("CYMJ in", values, "cymj");
            return (Criteria) this;
        }

        public Criteria andCymjNotIn(List<BigDecimal> values) {
            addCriterion("CYMJ not in", values, "cymj");
            return (Criteria) this;
        }

        public Criteria andCymjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CYMJ between", value1, value2, "cymj");
            return (Criteria) this;
        }

        public Criteria andCymjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CYMJ not between", value1, value2, "cymj");
            return (Criteria) this;
        }

        public Criteria andSlmjIsNull() {
            addCriterion("SLMJ is null");
            return (Criteria) this;
        }

        public Criteria andSlmjIsNotNull() {
            addCriterion("SLMJ is not null");
            return (Criteria) this;
        }

        public Criteria andSlmjEqualTo(BigDecimal value) {
            addCriterion("SLMJ =", value, "slmj");
            return (Criteria) this;
        }

        public Criteria andSlmjNotEqualTo(BigDecimal value) {
            addCriterion("SLMJ <>", value, "slmj");
            return (Criteria) this;
        }

        public Criteria andSlmjGreaterThan(BigDecimal value) {
            addCriterion("SLMJ >", value, "slmj");
            return (Criteria) this;
        }

        public Criteria andSlmjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SLMJ >=", value, "slmj");
            return (Criteria) this;
        }

        public Criteria andSlmjLessThan(BigDecimal value) {
            addCriterion("SLMJ <", value, "slmj");
            return (Criteria) this;
        }

        public Criteria andSlmjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SLMJ <=", value, "slmj");
            return (Criteria) this;
        }

        public Criteria andSlmjIn(List<BigDecimal> values) {
            addCriterion("SLMJ in", values, "slmj");
            return (Criteria) this;
        }

        public Criteria andSlmjNotIn(List<BigDecimal> values) {
            addCriterion("SLMJ not in", values, "slmj");
            return (Criteria) this;
        }

        public Criteria andSlmjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SLMJ between", value1, value2, "slmj");
            return (Criteria) this;
        }

        public Criteria andSlmjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SLMJ not between", value1, value2, "slmj");
            return (Criteria) this;
        }

        public Criteria andHdmjIsNull() {
            addCriterion("HDMJ is null");
            return (Criteria) this;
        }

        public Criteria andHdmjIsNotNull() {
            addCriterion("HDMJ is not null");
            return (Criteria) this;
        }

        public Criteria andHdmjEqualTo(BigDecimal value) {
            addCriterion("HDMJ =", value, "hdmj");
            return (Criteria) this;
        }

        public Criteria andHdmjNotEqualTo(BigDecimal value) {
            addCriterion("HDMJ <>", value, "hdmj");
            return (Criteria) this;
        }

        public Criteria andHdmjGreaterThan(BigDecimal value) {
            addCriterion("HDMJ >", value, "hdmj");
            return (Criteria) this;
        }

        public Criteria andHdmjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("HDMJ >=", value, "hdmj");
            return (Criteria) this;
        }

        public Criteria andHdmjLessThan(BigDecimal value) {
            addCriterion("HDMJ <", value, "hdmj");
            return (Criteria) this;
        }

        public Criteria andHdmjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("HDMJ <=", value, "hdmj");
            return (Criteria) this;
        }

        public Criteria andHdmjIn(List<BigDecimal> values) {
            addCriterion("HDMJ in", values, "hdmj");
            return (Criteria) this;
        }

        public Criteria andHdmjNotIn(List<BigDecimal> values) {
            addCriterion("HDMJ not in", values, "hdmj");
            return (Criteria) this;
        }

        public Criteria andHdmjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("HDMJ between", value1, value2, "hdmj");
            return (Criteria) this;
        }

        public Criteria andHdmjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("HDMJ not between", value1, value2, "hdmj");
            return (Criteria) this;
        }

        public Criteria andQtmjIsNull() {
            addCriterion("QTMJ is null");
            return (Criteria) this;
        }

        public Criteria andQtmjIsNotNull() {
            addCriterion("QTMJ is not null");
            return (Criteria) this;
        }

        public Criteria andQtmjEqualTo(BigDecimal value) {
            addCriterion("QTMJ =", value, "qtmj");
            return (Criteria) this;
        }

        public Criteria andQtmjNotEqualTo(BigDecimal value) {
            addCriterion("QTMJ <>", value, "qtmj");
            return (Criteria) this;
        }

        public Criteria andQtmjGreaterThan(BigDecimal value) {
            addCriterion("QTMJ >", value, "qtmj");
            return (Criteria) this;
        }

        public Criteria andQtmjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("QTMJ >=", value, "qtmj");
            return (Criteria) this;
        }

        public Criteria andQtmjLessThan(BigDecimal value) {
            addCriterion("QTMJ <", value, "qtmj");
            return (Criteria) this;
        }

        public Criteria andQtmjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("QTMJ <=", value, "qtmj");
            return (Criteria) this;
        }

        public Criteria andQtmjIn(List<BigDecimal> values) {
            addCriterion("QTMJ in", values, "qtmj");
            return (Criteria) this;
        }

        public Criteria andQtmjNotIn(List<BigDecimal> values) {
            addCriterion("QTMJ not in", values, "qtmj");
            return (Criteria) this;
        }

        public Criteria andQtmjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("QTMJ between", value1, value2, "qtmj");
            return (Criteria) this;
        }

        public Criteria andQtmjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("QTMJ not between", value1, value2, "qtmj");
            return (Criteria) this;
        }

        public Criteria andFzrzyzmjIsNull() {
            addCriterion("FZRZYZMJ is null");
            return (Criteria) this;
        }

        public Criteria andFzrzyzmjIsNotNull() {
            addCriterion("FZRZYZMJ is not null");
            return (Criteria) this;
        }

        public Criteria andFzrzyzmjEqualTo(BigDecimal value) {
            addCriterion("FZRZYZMJ =", value, "fzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andFzrzyzmjNotEqualTo(BigDecimal value) {
            addCriterion("FZRZYZMJ <>", value, "fzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andFzrzyzmjGreaterThan(BigDecimal value) {
            addCriterion("FZRZYZMJ >", value, "fzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andFzrzyzmjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FZRZYZMJ >=", value, "fzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andFzrzyzmjLessThan(BigDecimal value) {
            addCriterion("FZRZYZMJ <", value, "fzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andFzrzyzmjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FZRZYZMJ <=", value, "fzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andFzrzyzmjIn(List<BigDecimal> values) {
            addCriterion("FZRZYZMJ in", values, "fzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andFzrzyzmjNotIn(List<BigDecimal> values) {
            addCriterion("FZRZYZMJ not in", values, "fzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andFzrzyzmjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FZRZYZMJ between", value1, value2, "fzrzyzmj");
            return (Criteria) this;
        }

        public Criteria andFzrzyzmjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FZRZYZMJ not between", value1, value2, "fzrzyzmj");
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