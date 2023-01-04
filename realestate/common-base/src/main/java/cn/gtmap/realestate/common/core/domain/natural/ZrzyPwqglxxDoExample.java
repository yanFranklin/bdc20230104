package cn.gtmap.realestate.common.core.domain.natural;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

public class ZrzyPwqglxxDoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ZrzyPwqglxxDoExample() {
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

        public Criteria andPwxkzhIsNull() {
            addCriterion("PWXKZH is null");
            return (Criteria) this;
        }

        public Criteria andPwxkzhIsNotNull() {
            addCriterion("PWXKZH is not null");
            return (Criteria) this;
        }

        public Criteria andPwxkzhEqualTo(String value) {
            addCriterion("PWXKZH =", value, "pwxkzh");
            return (Criteria) this;
        }

        public Criteria andPwxkzhNotEqualTo(String value) {
            addCriterion("PWXKZH <>", value, "pwxkzh");
            return (Criteria) this;
        }

        public Criteria andPwxkzhGreaterThan(String value) {
            addCriterion("PWXKZH >", value, "pwxkzh");
            return (Criteria) this;
        }

        public Criteria andPwxkzhGreaterThanOrEqualTo(String value) {
            addCriterion("PWXKZH >=", value, "pwxkzh");
            return (Criteria) this;
        }

        public Criteria andPwxkzhLessThan(String value) {
            addCriterion("PWXKZH <", value, "pwxkzh");
            return (Criteria) this;
        }

        public Criteria andPwxkzhLessThanOrEqualTo(String value) {
            addCriterion("PWXKZH <=", value, "pwxkzh");
            return (Criteria) this;
        }

        public Criteria andPwxkzhLike(String value) {
            addCriterion("PWXKZH like", value, "pwxkzh");
            return (Criteria) this;
        }

        public Criteria andPwxkzhNotLike(String value) {
            addCriterion("PWXKZH not like", value, "pwxkzh");
            return (Criteria) this;
        }

        public Criteria andPwxkzhIn(List<String> values) {
            addCriterion("PWXKZH in", values, "pwxkzh");
            return (Criteria) this;
        }

        public Criteria andPwxkzhNotIn(List<String> values) {
            addCriterion("PWXKZH not in", values, "pwxkzh");
            return (Criteria) this;
        }

        public Criteria andPwxkzhBetween(String value1, String value2) {
            addCriterion("PWXKZH between", value1, value2, "pwxkzh");
            return (Criteria) this;
        }

        public Criteria andPwxkzhNotBetween(String value1, String value2) {
            addCriterion("PWXKZH not between", value1, value2, "pwxkzh");
            return (Criteria) this;
        }

        public Criteria andDwmcIsNull() {
            addCriterion("DWMC is null");
            return (Criteria) this;
        }

        public Criteria andDwmcIsNotNull() {
            addCriterion("DWMC is not null");
            return (Criteria) this;
        }

        public Criteria andDwmcEqualTo(String value) {
            addCriterion("DWMC =", value, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcNotEqualTo(String value) {
            addCriterion("DWMC <>", value, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcGreaterThan(String value) {
            addCriterion("DWMC >", value, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcGreaterThanOrEqualTo(String value) {
            addCriterion("DWMC >=", value, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcLessThan(String value) {
            addCriterion("DWMC <", value, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcLessThanOrEqualTo(String value) {
            addCriterion("DWMC <=", value, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcLike(String value) {
            addCriterion("DWMC like", value, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcNotLike(String value) {
            addCriterion("DWMC not like", value, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcIn(List<String> values) {
            addCriterion("DWMC in", values, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcNotIn(List<String> values) {
            addCriterion("DWMC not in", values, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcBetween(String value1, String value2) {
            addCriterion("DWMC between", value1, value2, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcNotBetween(String value1, String value2) {
            addCriterion("DWMC not between", value1, value2, "dwmc");
            return (Criteria) this;
        }

        public Criteria andWrwzlIsNull() {
            addCriterion("WRWZL is null");
            return (Criteria) this;
        }

        public Criteria andWrwzlIsNotNull() {
            addCriterion("WRWZL is not null");
            return (Criteria) this;
        }

        public Criteria andWrwzlEqualTo(String value) {
            addCriterion("WRWZL =", value, "wrwzl");
            return (Criteria) this;
        }

        public Criteria andWrwzlNotEqualTo(String value) {
            addCriterion("WRWZL <>", value, "wrwzl");
            return (Criteria) this;
        }

        public Criteria andWrwzlGreaterThan(String value) {
            addCriterion("WRWZL >", value, "wrwzl");
            return (Criteria) this;
        }

        public Criteria andWrwzlGreaterThanOrEqualTo(String value) {
            addCriterion("WRWZL >=", value, "wrwzl");
            return (Criteria) this;
        }

        public Criteria andWrwzlLessThan(String value) {
            addCriterion("WRWZL <", value, "wrwzl");
            return (Criteria) this;
        }

        public Criteria andWrwzlLessThanOrEqualTo(String value) {
            addCriterion("WRWZL <=", value, "wrwzl");
            return (Criteria) this;
        }

        public Criteria andWrwzlLike(String value) {
            addCriterion("WRWZL like", value, "wrwzl");
            return (Criteria) this;
        }

        public Criteria andWrwzlNotLike(String value) {
            addCriterion("WRWZL not like", value, "wrwzl");
            return (Criteria) this;
        }

        public Criteria andWrwzlIn(List<String> values) {
            addCriterion("WRWZL in", values, "wrwzl");
            return (Criteria) this;
        }

        public Criteria andWrwzlNotIn(List<String> values) {
            addCriterion("WRWZL not in", values, "wrwzl");
            return (Criteria) this;
        }

        public Criteria andWrwzlBetween(String value1, String value2) {
            addCriterion("WRWZL between", value1, value2, "wrwzl");
            return (Criteria) this;
        }

        public Criteria andWrwzlNotBetween(String value1, String value2) {
            addCriterion("WRWZL not between", value1, value2, "wrwzl");
            return (Criteria) this;
        }

        public Criteria andPfndxzIsNull() {
            addCriterion("PFNDXZ is null");
            return (Criteria) this;
        }

        public Criteria andPfndxzIsNotNull() {
            addCriterion("PFNDXZ is not null");
            return (Criteria) this;
        }

        public Criteria andPfndxzEqualTo(String value) {
            addCriterion("PFNDXZ =", value, "pfndxz");
            return (Criteria) this;
        }

        public Criteria andPfndxzNotEqualTo(String value) {
            addCriterion("PFNDXZ <>", value, "pfndxz");
            return (Criteria) this;
        }

        public Criteria andPfndxzGreaterThan(String value) {
            addCriterion("PFNDXZ >", value, "pfndxz");
            return (Criteria) this;
        }

        public Criteria andPfndxzGreaterThanOrEqualTo(String value) {
            addCriterion("PFNDXZ >=", value, "pfndxz");
            return (Criteria) this;
        }

        public Criteria andPfndxzLessThan(String value) {
            addCriterion("PFNDXZ <", value, "pfndxz");
            return (Criteria) this;
        }

        public Criteria andPfndxzLessThanOrEqualTo(String value) {
            addCriterion("PFNDXZ <=", value, "pfndxz");
            return (Criteria) this;
        }

        public Criteria andPfndxzLike(String value) {
            addCriterion("PFNDXZ like", value, "pfndxz");
            return (Criteria) this;
        }

        public Criteria andPfndxzNotLike(String value) {
            addCriterion("PFNDXZ not like", value, "pfndxz");
            return (Criteria) this;
        }

        public Criteria andPfndxzIn(List<String> values) {
            addCriterion("PFNDXZ in", values, "pfndxz");
            return (Criteria) this;
        }

        public Criteria andPfndxzNotIn(List<String> values) {
            addCriterion("PFNDXZ not in", values, "pfndxz");
            return (Criteria) this;
        }

        public Criteria andPfndxzBetween(String value1, String value2) {
            addCriterion("PFNDXZ between", value1, value2, "pfndxz");
            return (Criteria) this;
        }

        public Criteria andPfndxzNotBetween(String value1, String value2) {
            addCriterion("PFNDXZ not between", value1, value2, "pfndxz");
            return (Criteria) this;
        }

        public Criteria andPwqyxqxIsNull() {
            addCriterion("PWQYXQX is null");
            return (Criteria) this;
        }

        public Criteria andPwqyxqxIsNotNull() {
            addCriterion("PWQYXQX is not null");
            return (Criteria) this;
        }

        public Criteria andPwqyxqxEqualTo(String value) {
            addCriterion("PWQYXQX =", value, "pwqyxqx");
            return (Criteria) this;
        }

        public Criteria andPwqyxqxNotEqualTo(String value) {
            addCriterion("PWQYXQX <>", value, "pwqyxqx");
            return (Criteria) this;
        }

        public Criteria andPwqyxqxGreaterThan(String value) {
            addCriterion("PWQYXQX >", value, "pwqyxqx");
            return (Criteria) this;
        }

        public Criteria andPwqyxqxGreaterThanOrEqualTo(String value) {
            addCriterion("PWQYXQX >=", value, "pwqyxqx");
            return (Criteria) this;
        }

        public Criteria andPwqyxqxLessThan(String value) {
            addCriterion("PWQYXQX <", value, "pwqyxqx");
            return (Criteria) this;
        }

        public Criteria andPwqyxqxLessThanOrEqualTo(String value) {
            addCriterion("PWQYXQX <=", value, "pwqyxqx");
            return (Criteria) this;
        }

        public Criteria andPwqyxqxLike(String value) {
            addCriterion("PWQYXQX like", value, "pwqyxqx");
            return (Criteria) this;
        }

        public Criteria andPwqyxqxNotLike(String value) {
            addCriterion("PWQYXQX not like", value, "pwqyxqx");
            return (Criteria) this;
        }

        public Criteria andPwqyxqxIn(List<String> values) {
            addCriterion("PWQYXQX in", values, "pwqyxqx");
            return (Criteria) this;
        }

        public Criteria andPwqyxqxNotIn(List<String> values) {
            addCriterion("PWQYXQX not in", values, "pwqyxqx");
            return (Criteria) this;
        }

        public Criteria andPwqyxqxBetween(String value1, String value2) {
            addCriterion("PWQYXQX between", value1, value2, "pwqyxqx");
            return (Criteria) this;
        }

        public Criteria andPwqyxqxNotBetween(String value1, String value2) {
            addCriterion("PWQYXQX not between", value1, value2, "pwqyxqx");
            return (Criteria) this;
        }

        public Criteria andFzjgIsNull() {
            addCriterion("FZJG is null");
            return (Criteria) this;
        }

        public Criteria andFzjgIsNotNull() {
            addCriterion("FZJG is not null");
            return (Criteria) this;
        }

        public Criteria andFzjgEqualTo(String value) {
            addCriterion("FZJG =", value, "fzjg");
            return (Criteria) this;
        }

        public Criteria andFzjgNotEqualTo(String value) {
            addCriterion("FZJG <>", value, "fzjg");
            return (Criteria) this;
        }

        public Criteria andFzjgGreaterThan(String value) {
            addCriterion("FZJG >", value, "fzjg");
            return (Criteria) this;
        }

        public Criteria andFzjgGreaterThanOrEqualTo(String value) {
            addCriterion("FZJG >=", value, "fzjg");
            return (Criteria) this;
        }

        public Criteria andFzjgLessThan(String value) {
            addCriterion("FZJG <", value, "fzjg");
            return (Criteria) this;
        }

        public Criteria andFzjgLessThanOrEqualTo(String value) {
            addCriterion("FZJG <=", value, "fzjg");
            return (Criteria) this;
        }

        public Criteria andFzjgLike(String value) {
            addCriterion("FZJG like", value, "fzjg");
            return (Criteria) this;
        }

        public Criteria andFzjgNotLike(String value) {
            addCriterion("FZJG not like", value, "fzjg");
            return (Criteria) this;
        }

        public Criteria andFzjgIn(List<String> values) {
            addCriterion("FZJG in", values, "fzjg");
            return (Criteria) this;
        }

        public Criteria andFzjgNotIn(List<String> values) {
            addCriterion("FZJG not in", values, "fzjg");
            return (Criteria) this;
        }

        public Criteria andFzjgBetween(String value1, String value2) {
            addCriterion("FZJG between", value1, value2, "fzjg");
            return (Criteria) this;
        }

        public Criteria andFzjgNotBetween(String value1, String value2) {
            addCriterion("FZJG not between", value1, value2, "fzjg");
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