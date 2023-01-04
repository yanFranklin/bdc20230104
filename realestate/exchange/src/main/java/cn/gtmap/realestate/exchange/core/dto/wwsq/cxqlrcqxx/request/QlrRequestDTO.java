package cn.gtmap.realestate.exchange.core.dto.wwsq.cxqlrcqxx.request;

import java.util.List;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 1.0, 2022/7/14 8:54
 * @description 权利人产权信息入参
 */
public class QlrRequestDTO {

    /**
     * 权利人
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

    @Override
    public String toString() {
        return "QlrRequestDTO{" +
                "qlrmc='" + qlrmc + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                '}';
    }
}
