package cn.gtmap.realestate.exchange.core.dto.hefei.spfbaxxhttp.request;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-12
 * @description 商品房接口 请求实体
 */
public class SpfBaxxRequestDTO {

    private String fwbm;

    private String badh;

    public String getFwbm() {
        return fwbm;
    }

    public void setFwbm(String fwbm) {
        this.fwbm = fwbm;
    }

    public String getBadh() {
        return badh;
    }

    public void setBadh(String badh) {
        this.badh = badh;
    }
}
