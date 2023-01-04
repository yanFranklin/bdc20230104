package cn.gtmap.realestate.exchange.core.dto.bjjk.shzz.response;

import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-02
 * @description
 */
@IgnoreCast
public class BjjkShzzResponseRows {

    // 发证机关
    private String issue_certificate_dept;

    // 统一社会信用代码
    private String usc_code;

    // 证书有效期起
    private String valid_from;

    // 住所_具体地址
    private String domicile_addres;

    // 开办资金
    private String registered_capital;

    // 是否慈善组织 1代表是慈善组织单位，2代表不是慈善组织单位
    private String ifcharity;

    // 业务范围
    private String business_scope;

    // 发证日期
    private String registration_date;

    // 证书有效期止
    private String valid_to;

    // 活动地域
    private String activity_range;

    // 法定代表人
    private String legal_name;

    // 社会组织名称
    private String org_name;

    // 主管单位
    private String manager_dept;

    public String getIssue_certificate_dept() {
        return issue_certificate_dept;
    }

    public void setIssue_certificate_dept(String issue_certificate_dept) {
        this.issue_certificate_dept = issue_certificate_dept;
    }

    public String getUsc_code() {
        return usc_code;
    }

    public void setUsc_code(String usc_code) {
        this.usc_code = usc_code;
    }

    public String getValid_from() {
        return valid_from;
    }

    public void setValid_from(String valid_from) {
        this.valid_from = valid_from;
    }

    public String getDomicile_addres() {
        return domicile_addres;
    }

    public void setDomicile_addres(String domicile_addres) {
        this.domicile_addres = domicile_addres;
    }

    public String getRegistered_capital() {
        return registered_capital;
    }

    public void setRegistered_capital(String registered_capital) {
        this.registered_capital = registered_capital;
    }

    public String getIfcharity() {
        return ifcharity;
    }

    public void setIfcharity(String ifcharity) {
        this.ifcharity = ifcharity;
    }

    public String getBusiness_scope() {
        return business_scope;
    }

    public void setBusiness_scope(String business_scope) {
        this.business_scope = business_scope;
    }

    public String getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(String registration_date) {
        this.registration_date = registration_date;
    }

    public String getValid_to() {
        return valid_to;
    }

    public void setValid_to(String valid_to) {
        this.valid_to = valid_to;
    }

    public String getActivity_range() {
        return activity_range;
    }

    public void setActivity_range(String activity_range) {
        this.activity_range = activity_range;
    }

    public String getLegal_name() {
        return legal_name;
    }

    public void setLegal_name(String legal_name) {
        this.legal_name = legal_name;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getManager_dept() {
        return manager_dept;
    }

    public void setManager_dept(String manager_dept) {
        this.manager_dept = manager_dept;
    }
}
