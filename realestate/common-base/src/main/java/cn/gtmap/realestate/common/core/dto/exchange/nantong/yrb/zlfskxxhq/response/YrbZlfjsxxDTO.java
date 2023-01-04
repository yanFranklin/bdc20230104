package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfskxxhq.response;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.YrbFhmResponse;

import java.util.List;

public class YrbZlfjsxxDTO {

    private YrbFhmResponse zlfjsjglist;

    private List<YrbZlfskxxResponse> zlfskxxhqlist;


    public YrbFhmResponse getZlfjsjglist() {
        return zlfjsjglist;
    }

    public void setZlfjsjglist(YrbFhmResponse zlfjsjglist) {
        this.zlfjsjglist = zlfjsjglist;
    }

    public List<YrbZlfskxxResponse> getZlfskxxhqlist() {
        return zlfskxxhqlist;
    }

    public void setZlfskxxhqlist(List<YrbZlfskxxResponse> zlfskxxhqlist) {
        this.zlfskxxhqlist = zlfskxxhqlist;
    }

    @Override
    public String toString() {
        return "YrbZlfjsxxDTO{" +
                "zlfjsjglist=" + zlfjsjglist +
                ", zlfskxxhqlist=" + zlfskxxhqlist +
                '}';
    }
}
