package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.hyxxhysf.request;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2020-11-02
 * @description 民政部-民政部-婚姻登记信息核验（双方）接口
 */
public class HyxxhysfCxywcsDTO {
    /**
     * 男方身份证号
     */
    private String cert_num_man;

    /**
     * 男方姓名
     */
    private String name_man;

    /**
     * 女方姓名
     */
    private String name_woman;

    /**
     * 女方身份证号
     */
    private String cert_num_woman;

    public String getCert_num_man() {
        return cert_num_man;
    }

    public void setCert_num_man(String cert_num_man) {
        this.cert_num_man = cert_num_man;
    }

    public String getName_man() {
        return name_man;
    }

    public void setName_man(String name_man) {
        this.name_man = name_man;
    }

    public String getName_woman() {
        return name_woman;
    }

    public void setName_woman(String name_woman) {
        this.name_woman = name_woman;
    }

    public String getCert_num_woman() {
        return cert_num_woman;
    }

    public void setCert_num_woman(String cert_num_woman) {
        this.cert_num_woman = cert_num_woman;
    }

    @Override
    public String toString() {
        return "HyxxhysfCxywcsDTO{" +
                "cert_num_man='" + cert_num_man + '\'' +
                ", name_man='" + name_man + '\'' +
                ", name_woman='" + name_woman + '\'' +
                ", cert_num_woman='" + cert_num_woman + '\'' +
                '}';
    }
}
