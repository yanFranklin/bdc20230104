package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.sydjxxcx.request;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">wyh</a>
 * @version 1.0  2020-11-02
 * @description 民政部-收养登记信息查询
 */
public class SydjxxcxRequestCxywcs {
    /**
     * sfz
     */
    private String querytype;


    /**
     * sfz
     */
    private String cert_num;

    /**
     * 名称
     */
    private String name;

    public String getCert_num() {
        return cert_num;
    }

    public void setCert_num(String cert_num) {
        this.cert_num = cert_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuerytype() {
        return querytype;
    }

    public void setQuerytype(String querytype) {
        this.querytype = querytype;
    }

    @Override
    public String toString() {
        return "SydjxxcxRequestCxywcs{" +
                "org_name='" + cert_num + '\'' +
                ", usc_code='" + name + '\'' +
                '}';
    }
}
