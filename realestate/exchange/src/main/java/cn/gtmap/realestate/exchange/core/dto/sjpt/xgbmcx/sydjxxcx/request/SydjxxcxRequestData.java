package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.sydjxxcx.request;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">wyh</a>
 * @version 1.0
 * @description
 */
public class SydjxxcxRequestData {
    private String cxywlb;

    private List<SydjxxcxRequestCxywcs> cxywcs;

    public String getCxywlb() {
        return cxywlb;
    }

    public void setCxywlb(String cxywlb) {
        this.cxywlb = cxywlb;
    }

    public List<SydjxxcxRequestCxywcs> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<SydjxxcxRequestCxywcs> cxywcs) {
        this.cxywcs = cxywcs;
    }

    @Override
    public String toString() {
        return "SydjxxcxRequestData{" +
                "cxywlb='" + cxywlb + '\'' +
                ", cxywcs=" + cxywcs +
                '}';
    }
}
