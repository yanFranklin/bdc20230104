package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.hjxx.cxsq.request;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-18
 * @description
 */
public class HjxxCxsqRequestData {

    private List<HjxxCxsqRequestCxywcs> cxywcs;

    private String cxywlb;

    public List<HjxxCxsqRequestCxywcs> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<HjxxCxsqRequestCxywcs> cxywcs) {
        this.cxywcs = cxywcs;
    }

    public String getCxywlb() {
        return cxywlb;
    }

    public void setCxywlb(String cxywlb) {
        this.cxywlb = cxywlb;
    }
}
