package cn.gtmap.realestate.exchange.core.dto.wwsq.jtBdcxx.response;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 1.0, 2022/8/3 20:08
 * @description 权利人
 */
public class JtQlrRespDTO {

    private String fwsyqr;

    private String qlrzjh;

    public String getFwsyqr() {
        return fwsyqr;
    }

    public void setFwsyqr(String fwsyqr) {
        this.fwsyqr = fwsyqr;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    @Override
    public String toString() {
        return "JtQlrRespDTO{" +
                "fwsyqr='" + fwsyqr + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                '}';
    }
}
