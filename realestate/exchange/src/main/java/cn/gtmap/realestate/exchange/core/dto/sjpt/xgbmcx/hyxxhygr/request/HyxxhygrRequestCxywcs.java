package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.hyxxhygr.request;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2020-11-02
 * @description
 */
public class HyxxhygrRequestCxywcs {
    /**
     * 身份证号
     */
    private String cert_num_man;

    /**
     * 姓名
     */
    private String name_man;

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

    @Override
    public String toString() {
        return "HyxxhygrRequestCxywcs{" +
                "cert_num_man='" + cert_num_man + '\'' +
                ", name_man='" + name_man + '\'' +
                '}';
    }
}
