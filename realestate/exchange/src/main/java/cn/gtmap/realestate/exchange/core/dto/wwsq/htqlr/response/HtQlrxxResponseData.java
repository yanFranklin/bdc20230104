package cn.gtmap.realestate.exchange.core.dto.wwsq.htqlr.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-24
 * @description
 */
public class HtQlrxxResponseData {

    private String fchtbh;

    private String bah;

    private List<HtQlrxxResponseGxr> gxrxx;

    public String getFchtbh() {
        return fchtbh;
    }

    public void setFchtbh(String fchtbh) {
        this.fchtbh = fchtbh;
    }

    public String getBah() {
        return bah;
    }

    public void setBah(String bah) {
        this.bah = bah;
    }

    public List<HtQlrxxResponseGxr> getGxrxx() {
        return gxrxx;
    }

    public void setGxrxx(List<HtQlrxxResponseGxr> gxrxx) {
        this.gxrxx = gxrxx;
    }
}
