package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.rwzt.response;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.YrbFhmResponse;

public class YrbRwztDTO {

    private YrbFhmResponse rwjshouselist;

    public YrbFhmResponse getRwjshouselist() {
        return rwjshouselist;
    }

    public void setRwjshouselist(YrbFhmResponse rwjshouselist) {
        this.rwjshouselist = rwjshouselist;
    }

    @Override
    public String toString() {
        return "YrbZlfjsxxDTO{" +
                "rwjshouselist=" + rwjshouselist +
                '}';
    }
}
