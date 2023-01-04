package cn.gtmap.realestate.exchange.core.dto.wwsq.jtcy.response;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019-07-05
 * @description 家庭成员查询返回结果data信息
 */
public class WwsqQueryJtcyResponseData {
    /**
     * 权利人证件号
     */
    private String qlrzjh;
    /**
     * 权利人名称
     */
    private String qlrmc;
    /**
     * 权与户主关系
     */
    private String yhzgx;

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getYhzgx() {
        return yhzgx;
    }

    public void setYhzgx(String yhzgx) {
        this.yhzgx = yhzgx;
    }
}
