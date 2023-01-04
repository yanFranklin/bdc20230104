package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.sydjxxjy.request;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">wyh</a>
 * @version 1.0  2020-11-02
 * @description 民政部-收养登记信息校验
 */
public class SydjxxjyRequestCxywcs {
    /**
     * 查询类型
     */
    private String check_type;

    /**
     * 父身份证
     */
    private String parent_cert_num;

    /**
     * 父名称
     */
    private String parent_name;

    /**
     * 孩子身份证
     */
    private String baby_cert_num;

    /**
     * 孩子名称
     */
    private String baby_name;

    public String getCheck_type() {
        return check_type;
    }

    public void setCheck_type(String check_type) {
        this.check_type = check_type;
    }

    public String getParent_cert_num() {
        return parent_cert_num;
    }

    public void setParent_cert_num(String parent_cert_num) {
        this.parent_cert_num = parent_cert_num;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getBaby_cert_num() {
        return baby_cert_num;
    }

    public void setBaby_cert_num(String baby_cert_num) {
        this.baby_cert_num = baby_cert_num;
    }

    public String getBaby_name() {
        return baby_name;
    }

    public void setBaby_name(String baby_name) {
        this.baby_name = baby_name;
    }
}
