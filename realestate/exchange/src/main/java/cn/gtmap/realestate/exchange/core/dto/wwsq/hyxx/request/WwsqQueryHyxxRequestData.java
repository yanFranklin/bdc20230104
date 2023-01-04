package cn.gtmap.realestate.exchange.core.dto.wwsq.hyxx.request;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-06
 * @description 外网申请 婚姻信息
 */
public class WwsqQueryHyxxRequestData {

    /**
     * 权利人名称
     */
    private String qlrmc;

    /**
     * 权利人证件号
     */
    private String qlrzjh;

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }
}
