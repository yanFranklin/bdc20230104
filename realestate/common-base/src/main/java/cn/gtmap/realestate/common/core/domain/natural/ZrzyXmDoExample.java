package cn.gtmap.realestate.common.core.domain.natural;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

public class ZrzyXmDoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ZrzyXmDoExample() {
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

        public Criteria andSlbhIsNull() {
            addCriterion("SLBH is null");
            return (Criteria) this;
        }

        public Criteria andSlbhIsNotNull() {
            addCriterion("SLBH is not null");
            return (Criteria) this;
        }

        public Criteria andSlbhEqualTo(String value) {
            addCriterion("SLBH =", value, "slbh");
            return (Criteria) this;
        }

        public Criteria andSlbhNotEqualTo(String value) {
            addCriterion("SLBH <>", value, "slbh");
            return (Criteria) this;
        }

        public Criteria andSlbhGreaterThan(String value) {
            addCriterion("SLBH >", value, "slbh");
            return (Criteria) this;
        }

        public Criteria andSlbhGreaterThanOrEqualTo(String value) {
            addCriterion("SLBH >=", value, "slbh");
            return (Criteria) this;
        }

        public Criteria andSlbhLessThan(String value) {
            addCriterion("SLBH <", value, "slbh");
            return (Criteria) this;
        }

        public Criteria andSlbhLessThanOrEqualTo(String value) {
            addCriterion("SLBH <=", value, "slbh");
            return (Criteria) this;
        }

        public Criteria andSlbhLike(String value) {
            addCriterion("SLBH like", value, "slbh");
            return (Criteria) this;
        }

        public Criteria andSlbhNotLike(String value) {
            addCriterion("SLBH not like", value, "slbh");
            return (Criteria) this;
        }

        public Criteria andSlbhIn(List<String> values) {
            addCriterion("SLBH in", values, "slbh");
            return (Criteria) this;
        }

        public Criteria andSlbhNotIn(List<String> values) {
            addCriterion("SLBH not in", values, "slbh");
            return (Criteria) this;
        }

        public Criteria andSlbhBetween(String value1, String value2) {
            addCriterion("SLBH between", value1, value2, "slbh");
            return (Criteria) this;
        }

        public Criteria andSlbhNotBetween(String value1, String value2) {
            addCriterion("SLBH not between", value1, value2, "slbh");
            return (Criteria) this;
        }

        public Criteria andGzlslidIsNull() {
            addCriterion("GZLSLID is null");
            return (Criteria) this;
        }

        public Criteria andGzlslidIsNotNull() {
            addCriterion("GZLSLID is not null");
            return (Criteria) this;
        }

        public Criteria andGzlslidEqualTo(String value) {
            addCriterion("GZLSLID =", value, "gzlslid");
            return (Criteria) this;
        }

        public Criteria andGzlslidNotEqualTo(String value) {
            addCriterion("GZLSLID <>", value, "gzlslid");
            return (Criteria) this;
        }

        public Criteria andGzlslidGreaterThan(String value) {
            addCriterion("GZLSLID >", value, "gzlslid");
            return (Criteria) this;
        }

        public Criteria andGzlslidGreaterThanOrEqualTo(String value) {
            addCriterion("GZLSLID >=", value, "gzlslid");
            return (Criteria) this;
        }

        public Criteria andGzlslidLessThan(String value) {
            addCriterion("GZLSLID <", value, "gzlslid");
            return (Criteria) this;
        }

        public Criteria andGzlslidLessThanOrEqualTo(String value) {
            addCriterion("GZLSLID <=", value, "gzlslid");
            return (Criteria) this;
        }

        public Criteria andGzlslidLike(String value) {
            addCriterion("GZLSLID like", value, "gzlslid");
            return (Criteria) this;
        }

        public Criteria andGzlslidNotLike(String value) {
            addCriterion("GZLSLID not like", value, "gzlslid");
            return (Criteria) this;
        }

        public Criteria andGzlslidIn(List<String> values) {
            addCriterion("GZLSLID in", values, "gzlslid");
            return (Criteria) this;
        }

        public Criteria andGzlslidNotIn(List<String> values) {
            addCriterion("GZLSLID not in", values, "gzlslid");
            return (Criteria) this;
        }

        public Criteria andGzlslidBetween(String value1, String value2) {
            addCriterion("GZLSLID between", value1, value2, "gzlslid");
            return (Criteria) this;
        }

        public Criteria andGzlslidNotBetween(String value1, String value2) {
            addCriterion("GZLSLID not between", value1, value2, "gzlslid");
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

        public Criteria andDjlxIsNull() {
            addCriterion("DJLX is null");
            return (Criteria) this;
        }

        public Criteria andDjlxIsNotNull() {
            addCriterion("DJLX is not null");
            return (Criteria) this;
        }

        public Criteria andDjlxEqualTo(Short value) {
            addCriterion("DJLX =", value, "djlx");
            return (Criteria) this;
        }

        public Criteria andDjlxNotEqualTo(Short value) {
            addCriterion("DJLX <>", value, "djlx");
            return (Criteria) this;
        }

        public Criteria andDjlxGreaterThan(Short value) {
            addCriterion("DJLX >", value, "djlx");
            return (Criteria) this;
        }

        public Criteria andDjlxGreaterThanOrEqualTo(Short value) {
            addCriterion("DJLX >=", value, "djlx");
            return (Criteria) this;
        }

        public Criteria andDjlxLessThan(Short value) {
            addCriterion("DJLX <", value, "djlx");
            return (Criteria) this;
        }

        public Criteria andDjlxLessThanOrEqualTo(Short value) {
            addCriterion("DJLX <=", value, "djlx");
            return (Criteria) this;
        }

        public Criteria andDjlxIn(List<Short> values) {
            addCriterion("DJLX in", values, "djlx");
            return (Criteria) this;
        }

        public Criteria andDjlxNotIn(List<Short> values) {
            addCriterion("DJLX not in", values, "djlx");
            return (Criteria) this;
        }

        public Criteria andDjlxBetween(Short value1, Short value2) {
            addCriterion("DJLX between", value1, value2, "djlx");
            return (Criteria) this;
        }

        public Criteria andDjlxNotBetween(Short value1, Short value2) {
            addCriterion("DJLX not between", value1, value2, "djlx");
            return (Criteria) this;
        }

        public Criteria andDjyyIsNull() {
            addCriterion("DJYY is null");
            return (Criteria) this;
        }

        public Criteria andDjyyIsNotNull() {
            addCriterion("DJYY is not null");
            return (Criteria) this;
        }

        public Criteria andDjyyEqualTo(String value) {
            addCriterion("DJYY =", value, "djyy");
            return (Criteria) this;
        }

        public Criteria andDjyyNotEqualTo(String value) {
            addCriterion("DJYY <>", value, "djyy");
            return (Criteria) this;
        }

        public Criteria andDjyyGreaterThan(String value) {
            addCriterion("DJYY >", value, "djyy");
            return (Criteria) this;
        }

        public Criteria andDjyyGreaterThanOrEqualTo(String value) {
            addCriterion("DJYY >=", value, "djyy");
            return (Criteria) this;
        }

        public Criteria andDjyyLessThan(String value) {
            addCriterion("DJYY <", value, "djyy");
            return (Criteria) this;
        }

        public Criteria andDjyyLessThanOrEqualTo(String value) {
            addCriterion("DJYY <=", value, "djyy");
            return (Criteria) this;
        }

        public Criteria andDjyyLike(String value) {
            addCriterion("DJYY like", value, "djyy");
            return (Criteria) this;
        }

        public Criteria andDjyyNotLike(String value) {
            addCriterion("DJYY not like", value, "djyy");
            return (Criteria) this;
        }

        public Criteria andDjyyIn(List<String> values) {
            addCriterion("DJYY in", values, "djyy");
            return (Criteria) this;
        }

        public Criteria andDjyyNotIn(List<String> values) {
            addCriterion("DJYY not in", values, "djyy");
            return (Criteria) this;
        }

        public Criteria andDjyyBetween(String value1, String value2) {
            addCriterion("DJYY between", value1, value2, "djyy");
            return (Criteria) this;
        }

        public Criteria andDjyyNotBetween(String value1, String value2) {
            addCriterion("DJYY not between", value1, value2, "djyy");
            return (Criteria) this;
        }

        public Criteria andAjztIsNull() {
            addCriterion("AJZT is null");
            return (Criteria) this;
        }

        public Criteria andAjztIsNotNull() {
            addCriterion("AJZT is not null");
            return (Criteria) this;
        }

        public Criteria andAjztEqualTo(Short value) {
            addCriterion("AJZT =", value, "ajzt");
            return (Criteria) this;
        }

        public Criteria andAjztNotEqualTo(Short value) {
            addCriterion("AJZT <>", value, "ajzt");
            return (Criteria) this;
        }

        public Criteria andAjztGreaterThan(Short value) {
            addCriterion("AJZT >", value, "ajzt");
            return (Criteria) this;
        }

        public Criteria andAjztGreaterThanOrEqualTo(Short value) {
            addCriterion("AJZT >=", value, "ajzt");
            return (Criteria) this;
        }

        public Criteria andAjztLessThan(Short value) {
            addCriterion("AJZT <", value, "ajzt");
            return (Criteria) this;
        }

        public Criteria andAjztLessThanOrEqualTo(Short value) {
            addCriterion("AJZT <=", value, "ajzt");
            return (Criteria) this;
        }

        public Criteria andAjztIn(List<Short> values) {
            addCriterion("AJZT in", values, "ajzt");
            return (Criteria) this;
        }

        public Criteria andAjztNotIn(List<Short> values) {
            addCriterion("AJZT not in", values, "ajzt");
            return (Criteria) this;
        }

        public Criteria andAjztBetween(Short value1, Short value2) {
            addCriterion("AJZT between", value1, value2, "ajzt");
            return (Criteria) this;
        }

        public Criteria andAjztNotBetween(Short value1, Short value2) {
            addCriterion("AJZT not between", value1, value2, "ajzt");
            return (Criteria) this;
        }

        public Criteria andQsztIsNull() {
            addCriterion("QSZT is null");
            return (Criteria) this;
        }

        public Criteria andQsztIsNotNull() {
            addCriterion("QSZT is not null");
            return (Criteria) this;
        }

        public Criteria andQsztEqualTo(Short value) {
            addCriterion("QSZT =", value, "qszt");
            return (Criteria) this;
        }

        public Criteria andQsztNotEqualTo(Short value) {
            addCriterion("QSZT <>", value, "qszt");
            return (Criteria) this;
        }

        public Criteria andQsztGreaterThan(Short value) {
            addCriterion("QSZT >", value, "qszt");
            return (Criteria) this;
        }

        public Criteria andQsztGreaterThanOrEqualTo(Short value) {
            addCriterion("QSZT >=", value, "qszt");
            return (Criteria) this;
        }

        public Criteria andQsztLessThan(Short value) {
            addCriterion("QSZT <", value, "qszt");
            return (Criteria) this;
        }

        public Criteria andQsztLessThanOrEqualTo(Short value) {
            addCriterion("QSZT <=", value, "qszt");
            return (Criteria) this;
        }

        public Criteria andQsztIn(List<Short> values) {
            addCriterion("QSZT in", values, "qszt");
            return (Criteria) this;
        }

        public Criteria andQsztNotIn(List<Short> values) {
            addCriterion("QSZT not in", values, "qszt");
            return (Criteria) this;
        }

        public Criteria andQsztBetween(Short value1, Short value2) {
            addCriterion("QSZT between", value1, value2, "qszt");
            return (Criteria) this;
        }

        public Criteria andQsztNotBetween(Short value1, Short value2) {
            addCriterion("QSZT not between", value1, value2, "qszt");
            return (Criteria) this;
        }

        public Criteria andQxdmIsNull() {
            addCriterion("QXDM is null");
            return (Criteria) this;
        }

        public Criteria andQxdmIsNotNull() {
            addCriterion("QXDM is not null");
            return (Criteria) this;
        }

        public Criteria andQxdmEqualTo(String value) {
            addCriterion("QXDM =", value, "qxdm");
            return (Criteria) this;
        }

        public Criteria andQxdmNotEqualTo(String value) {
            addCriterion("QXDM <>", value, "qxdm");
            return (Criteria) this;
        }

        public Criteria andQxdmGreaterThan(String value) {
            addCriterion("QXDM >", value, "qxdm");
            return (Criteria) this;
        }

        public Criteria andQxdmGreaterThanOrEqualTo(String value) {
            addCriterion("QXDM >=", value, "qxdm");
            return (Criteria) this;
        }

        public Criteria andQxdmLessThan(String value) {
            addCriterion("QXDM <", value, "qxdm");
            return (Criteria) this;
        }

        public Criteria andQxdmLessThanOrEqualTo(String value) {
            addCriterion("QXDM <=", value, "qxdm");
            return (Criteria) this;
        }

        public Criteria andQxdmLike(String value) {
            addCriterion("QXDM like", value, "qxdm");
            return (Criteria) this;
        }

        public Criteria andQxdmNotLike(String value) {
            addCriterion("QXDM not like", value, "qxdm");
            return (Criteria) this;
        }

        public Criteria andQxdmIn(List<String> values) {
            addCriterion("QXDM in", values, "qxdm");
            return (Criteria) this;
        }

        public Criteria andQxdmNotIn(List<String> values) {
            addCriterion("QXDM not in", values, "qxdm");
            return (Criteria) this;
        }

        public Criteria andQxdmBetween(String value1, String value2) {
            addCriterion("QXDM between", value1, value2, "qxdm");
            return (Criteria) this;
        }

        public Criteria andQxdmNotBetween(String value1, String value2) {
            addCriterion("QXDM not between", value1, value2, "qxdm");
            return (Criteria) this;
        }

        public Criteria andSlsjIsNull() {
            addCriterion("SLSJ is null");
            return (Criteria) this;
        }

        public Criteria andSlsjIsNotNull() {
            addCriterion("SLSJ is not null");
            return (Criteria) this;
        }

        public Criteria andSlsjEqualTo(LocalDateTime value) {
            addCriterion("SLSJ =", value, "slsj");
            return (Criteria) this;
        }

        public Criteria andSlsjNotEqualTo(LocalDateTime value) {
            addCriterion("SLSJ <>", value, "slsj");
            return (Criteria) this;
        }

        public Criteria andSlsjGreaterThan(LocalDateTime value) {
            addCriterion("SLSJ >", value, "slsj");
            return (Criteria) this;
        }

        public Criteria andSlsjGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("SLSJ >=", value, "slsj");
            return (Criteria) this;
        }

        public Criteria andSlsjLessThan(LocalDateTime value) {
            addCriterion("SLSJ <", value, "slsj");
            return (Criteria) this;
        }

        public Criteria andSlsjLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("SLSJ <=", value, "slsj");
            return (Criteria) this;
        }

        public Criteria andSlsjIn(List<LocalDateTime> values) {
            addCriterion("SLSJ in", values, "slsj");
            return (Criteria) this;
        }

        public Criteria andSlsjNotIn(List<LocalDateTime> values) {
            addCriterion("SLSJ not in", values, "slsj");
            return (Criteria) this;
        }

        public Criteria andSlsjBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("SLSJ between", value1, value2, "slsj");
            return (Criteria) this;
        }

        public Criteria andSlsjNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("SLSJ not between", value1, value2, "slsj");
            return (Criteria) this;
        }

        public Criteria andSlridIsNull() {
            addCriterion("SLRID is null");
            return (Criteria) this;
        }

        public Criteria andSlridIsNotNull() {
            addCriterion("SLRID is not null");
            return (Criteria) this;
        }

        public Criteria andSlridEqualTo(String value) {
            addCriterion("SLRID =", value, "slrid");
            return (Criteria) this;
        }

        public Criteria andSlridNotEqualTo(String value) {
            addCriterion("SLRID <>", value, "slrid");
            return (Criteria) this;
        }

        public Criteria andSlridGreaterThan(String value) {
            addCriterion("SLRID >", value, "slrid");
            return (Criteria) this;
        }

        public Criteria andSlridGreaterThanOrEqualTo(String value) {
            addCriterion("SLRID >=", value, "slrid");
            return (Criteria) this;
        }

        public Criteria andSlridLessThan(String value) {
            addCriterion("SLRID <", value, "slrid");
            return (Criteria) this;
        }

        public Criteria andSlridLessThanOrEqualTo(String value) {
            addCriterion("SLRID <=", value, "slrid");
            return (Criteria) this;
        }

        public Criteria andSlridLike(String value) {
            addCriterion("SLRID like", value, "slrid");
            return (Criteria) this;
        }

        public Criteria andSlridNotLike(String value) {
            addCriterion("SLRID not like", value, "slrid");
            return (Criteria) this;
        }

        public Criteria andSlridIn(List<String> values) {
            addCriterion("SLRID in", values, "slrid");
            return (Criteria) this;
        }

        public Criteria andSlridNotIn(List<String> values) {
            addCriterion("SLRID not in", values, "slrid");
            return (Criteria) this;
        }

        public Criteria andSlridBetween(String value1, String value2) {
            addCriterion("SLRID between", value1, value2, "slrid");
            return (Criteria) this;
        }

        public Criteria andSlridNotBetween(String value1, String value2) {
            addCriterion("SLRID not between", value1, value2, "slrid");
            return (Criteria) this;
        }

        public Criteria andJssjIsNull() {
            addCriterion("JSSJ is null");
            return (Criteria) this;
        }

        public Criteria andJssjIsNotNull() {
            addCriterion("JSSJ is not null");
            return (Criteria) this;
        }

        public Criteria andJssjEqualTo(LocalDateTime value) {
            addCriterion("JSSJ =", value, "jssj");
            return (Criteria) this;
        }

        public Criteria andJssjNotEqualTo(LocalDateTime value) {
            addCriterion("JSSJ <>", value, "jssj");
            return (Criteria) this;
        }

        public Criteria andJssjGreaterThan(LocalDateTime value) {
            addCriterion("JSSJ >", value, "jssj");
            return (Criteria) this;
        }

        public Criteria andJssjGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("JSSJ >=", value, "jssj");
            return (Criteria) this;
        }

        public Criteria andJssjLessThan(LocalDateTime value) {
            addCriterion("JSSJ <", value, "jssj");
            return (Criteria) this;
        }

        public Criteria andJssjLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("JSSJ <=", value, "jssj");
            return (Criteria) this;
        }

        public Criteria andJssjIn(List<LocalDateTime> values) {
            addCriterion("JSSJ in", values, "jssj");
            return (Criteria) this;
        }

        public Criteria andJssjNotIn(List<LocalDateTime> values) {
            addCriterion("JSSJ not in", values, "jssj");
            return (Criteria) this;
        }

        public Criteria andJssjBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("JSSJ between", value1, value2, "jssj");
            return (Criteria) this;
        }

        public Criteria andJssjNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("JSSJ not between", value1, value2, "jssj");
            return (Criteria) this;
        }

        public Criteria andDjjgIsNull() {
            addCriterion("DJJG is null");
            return (Criteria) this;
        }

        public Criteria andDjjgIsNotNull() {
            addCriterion("DJJG is not null");
            return (Criteria) this;
        }

        public Criteria andDjjgEqualTo(String value) {
            addCriterion("DJJG =", value, "djjg");
            return (Criteria) this;
        }

        public Criteria andDjjgNotEqualTo(String value) {
            addCriterion("DJJG <>", value, "djjg");
            return (Criteria) this;
        }

        public Criteria andDjjgGreaterThan(String value) {
            addCriterion("DJJG >", value, "djjg");
            return (Criteria) this;
        }

        public Criteria andDjjgGreaterThanOrEqualTo(String value) {
            addCriterion("DJJG >=", value, "djjg");
            return (Criteria) this;
        }

        public Criteria andDjjgLessThan(String value) {
            addCriterion("DJJG <", value, "djjg");
            return (Criteria) this;
        }

        public Criteria andDjjgLessThanOrEqualTo(String value) {
            addCriterion("DJJG <=", value, "djjg");
            return (Criteria) this;
        }

        public Criteria andDjjgLike(String value) {
            addCriterion("DJJG like", value, "djjg");
            return (Criteria) this;
        }

        public Criteria andDjjgNotLike(String value) {
            addCriterion("DJJG not like", value, "djjg");
            return (Criteria) this;
        }

        public Criteria andDjjgIn(List<String> values) {
            addCriterion("DJJG in", values, "djjg");
            return (Criteria) this;
        }

        public Criteria andDjjgNotIn(List<String> values) {
            addCriterion("DJJG not in", values, "djjg");
            return (Criteria) this;
        }

        public Criteria andDjjgBetween(String value1, String value2) {
            addCriterion("DJJG between", value1, value2, "djjg");
            return (Criteria) this;
        }

        public Criteria andDjjgNotBetween(String value1, String value2) {
            addCriterion("DJJG not between", value1, value2, "djjg");
            return (Criteria) this;
        }

        public Criteria andDjsjIsNull() {
            addCriterion("DJSJ is null");
            return (Criteria) this;
        }

        public Criteria andDjsjIsNotNull() {
            addCriterion("DJSJ is not null");
            return (Criteria) this;
        }

        public Criteria andDjsjEqualTo(LocalDateTime value) {
            addCriterion("DJSJ =", value, "djsj");
            return (Criteria) this;
        }

        public Criteria andDjsjNotEqualTo(LocalDateTime value) {
            addCriterion("DJSJ <>", value, "djsj");
            return (Criteria) this;
        }

        public Criteria andDjsjGreaterThan(LocalDateTime value) {
            addCriterion("DJSJ >", value, "djsj");
            return (Criteria) this;
        }

        public Criteria andDjsjGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("DJSJ >=", value, "djsj");
            return (Criteria) this;
        }

        public Criteria andDjsjLessThan(LocalDateTime value) {
            addCriterion("DJSJ <", value, "djsj");
            return (Criteria) this;
        }

        public Criteria andDjsjLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("DJSJ <=", value, "djsj");
            return (Criteria) this;
        }

        public Criteria andDjsjIn(List<LocalDateTime> values) {
            addCriterion("DJSJ in", values, "djsj");
            return (Criteria) this;
        }

        public Criteria andDjsjNotIn(List<LocalDateTime> values) {
            addCriterion("DJSJ not in", values, "djsj");
            return (Criteria) this;
        }

        public Criteria andDjsjBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("DJSJ between", value1, value2, "djsj");
            return (Criteria) this;
        }

        public Criteria andDjsjNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("DJSJ not between", value1, value2, "djsj");
            return (Criteria) this;
        }

        public Criteria andDbrIsNull() {
            addCriterion("DBR is null");
            return (Criteria) this;
        }

        public Criteria andDbrIsNotNull() {
            addCriterion("DBR is not null");
            return (Criteria) this;
        }

        public Criteria andDbrEqualTo(String value) {
            addCriterion("DBR =", value, "dbr");
            return (Criteria) this;
        }

        public Criteria andDbrNotEqualTo(String value) {
            addCriterion("DBR <>", value, "dbr");
            return (Criteria) this;
        }

        public Criteria andDbrGreaterThan(String value) {
            addCriterion("DBR >", value, "dbr");
            return (Criteria) this;
        }

        public Criteria andDbrGreaterThanOrEqualTo(String value) {
            addCriterion("DBR >=", value, "dbr");
            return (Criteria) this;
        }

        public Criteria andDbrLessThan(String value) {
            addCriterion("DBR <", value, "dbr");
            return (Criteria) this;
        }

        public Criteria andDbrLessThanOrEqualTo(String value) {
            addCriterion("DBR <=", value, "dbr");
            return (Criteria) this;
        }

        public Criteria andDbrLike(String value) {
            addCriterion("DBR like", value, "dbr");
            return (Criteria) this;
        }

        public Criteria andDbrNotLike(String value) {
            addCriterion("DBR not like", value, "dbr");
            return (Criteria) this;
        }

        public Criteria andDbrIn(List<String> values) {
            addCriterion("DBR in", values, "dbr");
            return (Criteria) this;
        }

        public Criteria andDbrNotIn(List<String> values) {
            addCriterion("DBR not in", values, "dbr");
            return (Criteria) this;
        }

        public Criteria andDbrBetween(String value1, String value2) {
            addCriterion("DBR between", value1, value2, "dbr");
            return (Criteria) this;
        }

        public Criteria andDbrNotBetween(String value1, String value2) {
            addCriterion("DBR not between", value1, value2, "dbr");
            return (Criteria) this;
        }

        public Criteria andDjxlIsNull() {
            addCriterion("DJXL is null");
            return (Criteria) this;
        }

        public Criteria andDjxlIsNotNull() {
            addCriterion("DJXL is not null");
            return (Criteria) this;
        }

        public Criteria andDjxlEqualTo(String value) {
            addCriterion("DJXL =", value, "djxl");
            return (Criteria) this;
        }

        public Criteria andDjxlNotEqualTo(String value) {
            addCriterion("DJXL <>", value, "djxl");
            return (Criteria) this;
        }

        public Criteria andDjxlGreaterThan(String value) {
            addCriterion("DJXL >", value, "djxl");
            return (Criteria) this;
        }

        public Criteria andDjxlGreaterThanOrEqualTo(String value) {
            addCriterion("DJXL >=", value, "djxl");
            return (Criteria) this;
        }

        public Criteria andDjxlLessThan(String value) {
            addCriterion("DJXL <", value, "djxl");
            return (Criteria) this;
        }

        public Criteria andDjxlLessThanOrEqualTo(String value) {
            addCriterion("DJXL <=", value, "djxl");
            return (Criteria) this;
        }

        public Criteria andDjxlLike(String value) {
            addCriterion("DJXL like", value, "djxl");
            return (Criteria) this;
        }

        public Criteria andDjxlNotLike(String value) {
            addCriterion("DJXL not like", value, "djxl");
            return (Criteria) this;
        }

        public Criteria andDjxlIn(List<String> values) {
            addCriterion("DJXL in", values, "djxl");
            return (Criteria) this;
        }

        public Criteria andDjxlNotIn(List<String> values) {
            addCriterion("DJXL not in", values, "djxl");
            return (Criteria) this;
        }

        public Criteria andDjxlBetween(String value1, String value2) {
            addCriterion("DJXL between", value1, value2, "djxl");
            return (Criteria) this;
        }

        public Criteria andDjxlNotBetween(String value1, String value2) {
            addCriterion("DJXL not between", value1, value2, "djxl");
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

        public Criteria andYcqzhIsNull() {
            addCriterion("YCQZH is null");
            return (Criteria) this;
        }

        public Criteria andYcqzhIsNotNull() {
            addCriterion("YCQZH is not null");
            return (Criteria) this;
        }

        public Criteria andYcqzhEqualTo(String value) {
            addCriterion("YCQZH =", value, "ycqzh");
            return (Criteria) this;
        }

        public Criteria andYcqzhNotEqualTo(String value) {
            addCriterion("YCQZH <>", value, "ycqzh");
            return (Criteria) this;
        }

        public Criteria andYcqzhGreaterThan(String value) {
            addCriterion("YCQZH >", value, "ycqzh");
            return (Criteria) this;
        }

        public Criteria andYcqzhGreaterThanOrEqualTo(String value) {
            addCriterion("YCQZH >=", value, "ycqzh");
            return (Criteria) this;
        }

        public Criteria andYcqzhLessThan(String value) {
            addCriterion("YCQZH <", value, "ycqzh");
            return (Criteria) this;
        }

        public Criteria andYcqzhLessThanOrEqualTo(String value) {
            addCriterion("YCQZH <=", value, "ycqzh");
            return (Criteria) this;
        }

        public Criteria andYcqzhLike(String value) {
            addCriterion("YCQZH like", value, "ycqzh");
            return (Criteria) this;
        }

        public Criteria andYcqzhNotLike(String value) {
            addCriterion("YCQZH not like", value, "ycqzh");
            return (Criteria) this;
        }

        public Criteria andYcqzhIn(List<String> values) {
            addCriterion("YCQZH in", values, "ycqzh");
            return (Criteria) this;
        }

        public Criteria andYcqzhNotIn(List<String> values) {
            addCriterion("YCQZH not in", values, "ycqzh");
            return (Criteria) this;
        }

        public Criteria andYcqzhBetween(String value1, String value2) {
            addCriterion("YCQZH between", value1, value2, "ycqzh");
            return (Criteria) this;
        }

        public Criteria andYcqzhNotBetween(String value1, String value2) {
            addCriterion("YCQZH not between", value1, value2, "ycqzh");
            return (Criteria) this;
        }

        public Criteria andGzldyidIsNull() {
            addCriterion("GZLDYID is null");
            return (Criteria) this;
        }

        public Criteria andGzldyidIsNotNull() {
            addCriterion("GZLDYID is not null");
            return (Criteria) this;
        }

        public Criteria andGzldyidEqualTo(String value) {
            addCriterion("GZLDYID =", value, "gzldyid");
            return (Criteria) this;
        }

        public Criteria andGzldyidNotEqualTo(String value) {
            addCriterion("GZLDYID <>", value, "gzldyid");
            return (Criteria) this;
        }

        public Criteria andGzldyidGreaterThan(String value) {
            addCriterion("GZLDYID >", value, "gzldyid");
            return (Criteria) this;
        }

        public Criteria andGzldyidGreaterThanOrEqualTo(String value) {
            addCriterion("GZLDYID >=", value, "gzldyid");
            return (Criteria) this;
        }

        public Criteria andGzldyidLessThan(String value) {
            addCriterion("GZLDYID <", value, "gzldyid");
            return (Criteria) this;
        }

        public Criteria andGzldyidLessThanOrEqualTo(String value) {
            addCriterion("GZLDYID <=", value, "gzldyid");
            return (Criteria) this;
        }

        public Criteria andGzldyidLike(String value) {
            addCriterion("GZLDYID like", value, "gzldyid");
            return (Criteria) this;
        }

        public Criteria andGzldyidNotLike(String value) {
            addCriterion("GZLDYID not like", value, "gzldyid");
            return (Criteria) this;
        }

        public Criteria andGzldyidIn(List<String> values) {
            addCriterion("GZLDYID in", values, "gzldyid");
            return (Criteria) this;
        }

        public Criteria andGzldyidNotIn(List<String> values) {
            addCriterion("GZLDYID not in", values, "gzldyid");
            return (Criteria) this;
        }

        public Criteria andGzldyidBetween(String value1, String value2) {
            addCriterion("GZLDYID between", value1, value2, "gzldyid");
            return (Criteria) this;
        }

        public Criteria andGzldyidNotBetween(String value1, String value2) {
            addCriterion("GZLDYID not between", value1, value2, "gzldyid");
            return (Criteria) this;
        }

        public Criteria andGzldymcIsNull() {
            addCriterion("GZLDYMC is null");
            return (Criteria) this;
        }

        public Criteria andGzldymcIsNotNull() {
            addCriterion("GZLDYMC is not null");
            return (Criteria) this;
        }

        public Criteria andGzldymcEqualTo(String value) {
            addCriterion("GZLDYMC =", value, "gzldymc");
            return (Criteria) this;
        }

        public Criteria andGzldymcNotEqualTo(String value) {
            addCriterion("GZLDYMC <>", value, "gzldymc");
            return (Criteria) this;
        }

        public Criteria andGzldymcGreaterThan(String value) {
            addCriterion("GZLDYMC >", value, "gzldymc");
            return (Criteria) this;
        }

        public Criteria andGzldymcGreaterThanOrEqualTo(String value) {
            addCriterion("GZLDYMC >=", value, "gzldymc");
            return (Criteria) this;
        }

        public Criteria andGzldymcLessThan(String value) {
            addCriterion("GZLDYMC <", value, "gzldymc");
            return (Criteria) this;
        }

        public Criteria andGzldymcLessThanOrEqualTo(String value) {
            addCriterion("GZLDYMC <=", value, "gzldymc");
            return (Criteria) this;
        }

        public Criteria andGzldymcLike(String value) {
            addCriterion("GZLDYMC like", value, "gzldymc");
            return (Criteria) this;
        }

        public Criteria andGzldymcNotLike(String value) {
            addCriterion("GZLDYMC not like", value, "gzldymc");
            return (Criteria) this;
        }

        public Criteria andGzldymcIn(List<String> values) {
            addCriterion("GZLDYMC in", values, "gzldymc");
            return (Criteria) this;
        }

        public Criteria andGzldymcNotIn(List<String> values) {
            addCriterion("GZLDYMC not in", values, "gzldymc");
            return (Criteria) this;
        }

        public Criteria andGzldymcBetween(String value1, String value2) {
            addCriterion("GZLDYMC between", value1, value2, "gzldymc");
            return (Criteria) this;
        }

        public Criteria andGzldymcNotBetween(String value1, String value2) {
            addCriterion("GZLDYMC not between", value1, value2, "gzldymc");
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