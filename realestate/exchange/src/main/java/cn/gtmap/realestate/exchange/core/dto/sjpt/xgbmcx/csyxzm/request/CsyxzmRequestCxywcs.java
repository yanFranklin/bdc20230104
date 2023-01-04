package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.csyxzm.request;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2020-11-02
 * @description
 */
public class CsyxzmRequestCxywcs {
    /**
     * 出生证明编号
     */
    private String cszmbh;

    /**
     * 母亲姓名
     */
    private String mqxm;

    /**
     * 母亲证件号码
     */
    private String mqzjbm;

    public String getCszmbh() {
        return cszmbh;
    }

    public void setCszmbh(String cszmbh) {
        this.cszmbh = cszmbh;
    }

    public String getMqxm() {
        return mqxm;
    }

    public void setMqxm(String mqxm) {
        this.mqxm = mqxm;
    }

    public String getMqzjbm() {
        return mqzjbm;
    }

    public void setMqzjbm(String mqzjbm) {
        this.mqzjbm = mqzjbm;
    }

    @Override
    public String toString() {
        return "CsyxzmRequestCxywcs{" +
                "cszmbh='" + cszmbh + '\'' +
                ", mqxm='" + mqxm + '\'' +
                ", mqzjbm='" + mqzjbm + '\'' +
                '}';
    }
}
