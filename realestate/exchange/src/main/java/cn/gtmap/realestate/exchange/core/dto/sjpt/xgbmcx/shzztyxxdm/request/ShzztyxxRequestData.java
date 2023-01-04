package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.shzztyxxdm.request;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0
 * @description
 */
public class ShzztyxxRequestData {
    private String cxywlb;

    private List<ShzztyxxRequestCxywcs> cxywcs;

    public String getCxywlb() {
        return cxywlb;
    }

    public void setCxywlb(String cxywlb) {
        this.cxywlb = cxywlb;
    }

    public List<ShzztyxxRequestCxywcs> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<ShzztyxxRequestCxywcs> cxywcs) {
        this.cxywcs = cxywcs;
    }

    @Override
    public String toString() {
        return "ShzztyxxRequestData{" +
                "cxywlb='" + cxywlb + '\'' +
                ", cxywcs=" + cxywcs +
                '}';
    }
}
