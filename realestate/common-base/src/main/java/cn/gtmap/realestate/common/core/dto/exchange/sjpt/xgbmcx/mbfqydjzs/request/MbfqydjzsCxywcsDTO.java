package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.mbfqydjzs.request;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2020-11-02
 * @description 民政部-民办非企业单位登记证书查询接口
 */
public class MbfqydjzsCxywcsDTO {
    /**
     * 社会组织名称
     */
    private String org_name;

    /**
     * 统一社会信用代码
     */
    private String usc_code;

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getUsc_code() {
        return usc_code;
    }

    public void setUsc_code(String usc_code) {
        this.usc_code = usc_code;
    }

    @Override
    public String toString() {
        return "MbfqydjzsCxywcsDTO{" +
                "org_name='" + org_name + '\'' +
                ", usc_code='" + usc_code + '\'' +
                '}';
    }
}
