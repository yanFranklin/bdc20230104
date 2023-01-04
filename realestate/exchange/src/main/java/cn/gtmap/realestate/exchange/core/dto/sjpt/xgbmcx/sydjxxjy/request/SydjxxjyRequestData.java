package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.sydjxxjy.request;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">wyh</a>
 * @version 1.0
 * @description
 */
public class SydjxxjyRequestData {
    private String cxywlb;

    private List<SydjxxjyRequestCxywcs> cxywcs;

    public String getCxywlb() {
        return cxywlb;
    }

    public void setCxywlb(String cxywlb) {
        this.cxywlb = cxywlb;
    }

    public List<SydjxxjyRequestCxywcs> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<SydjxxjyRequestCxywcs> cxywcs) {
        this.cxywcs = cxywcs;
    }

    @Override
    public String toString() {
        return "SydjxxjyRequestData{" +
                "cxywlb='" + cxywlb + '\'' +
                ", cxywcs=" + cxywcs +
                '}';
    }
}
