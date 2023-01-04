package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.hyxxhygr.request;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0
 * @description
 */
public class HyxxhygrRequestData {
    private String cxywlb;

    private List<HyxxhygrRequestCxywcs> cxywcs;

    public String getCxywlb() {
        return cxywlb;
    }

    public void setCxywlb(String cxywlb) {
        this.cxywlb = cxywlb;
    }

    public List<HyxxhygrRequestCxywcs> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<HyxxhygrRequestCxywcs> cxywcs) {
        this.cxywcs = cxywcs;
    }

    @Override
    public String toString() {
        return "HyxxhygrRequestData{" +
                "cxywlb='" + cxywlb + '\'' +
                ", cxywcs=" + cxywcs +
                '}';
    }
}
