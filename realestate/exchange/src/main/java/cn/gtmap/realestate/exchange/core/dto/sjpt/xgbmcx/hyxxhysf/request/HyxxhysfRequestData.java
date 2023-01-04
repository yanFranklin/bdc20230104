package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.hyxxhysf.request;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0
 * @description
 */
public class HyxxhysfRequestData {
    private String cxywlb;

    private List<HyxxhysfRequestCxywcs> cxywcs;

    public String getCxywlb() {
        return cxywlb;
    }

    public void setCxywlb(String cxywlb) {
        this.cxywlb = cxywlb;
    }

    public List<HyxxhysfRequestCxywcs> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<HyxxhysfRequestCxywcs> cxywcs) {
        this.cxywcs = cxywcs;
    }

    @Override
    public String toString() {
        return "HyxxhysfRequestData{" +
                "cxywlb='" + cxywlb + '\'' +
                ", cxywcs=" + cxywcs +
                '}';
    }
}
