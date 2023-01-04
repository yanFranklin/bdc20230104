package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.shttfrdjzs.request;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0
 * @description 社会团体法人登记证书查询
 */
public class ShttfrdjzsRequestData {
    private String cxywlb;

    private List<ShttfrdjzsRequestCxywcs> cxywcs;

    public String getCxywlb() {
        return cxywlb;
    }

    public void setCxywlb(String cxywlb) {
        this.cxywlb = cxywlb;
    }

    public List<ShttfrdjzsRequestCxywcs> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<ShttfrdjzsRequestCxywcs> cxywcs) {
        this.cxywcs = cxywcs;
    }

    @Override
    public String toString() {
        return "ShttfrdjzsRequestData{" +
                "cxywlb='" + cxywlb + '\'' +
                ", cxywcs=" + cxywcs +
                '}';
    }
}
