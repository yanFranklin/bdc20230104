package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.hyxx.request;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-22
 * @description 婚姻信息查询 业务参数实体
 */
public class HyxxCxywcsRequestDTO {

    private String qlrmc;

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
