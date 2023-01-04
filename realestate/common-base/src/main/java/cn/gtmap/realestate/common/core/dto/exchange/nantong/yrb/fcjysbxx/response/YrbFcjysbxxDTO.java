package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxx.response;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.YrbFhmResponse;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/9
 * @description 1.9.    房产交易申报信息获取【A014】
 */
public class YrbFcjysbxxDTO {

    private YrbFhmResponse fcjysbxxlist;

    private List<YrbFcjysbxxFjxxResponse> fcjysbfjxxlist;

    public YrbFhmResponse getFcjysbxxlist() {
        return fcjysbxxlist;
    }

    public void setFcjysbxxlist(YrbFhmResponse fcjysbxxlist) {
        this.fcjysbxxlist = fcjysbxxlist;
    }

    public List<YrbFcjysbxxFjxxResponse> getFcjysbfjxxlist() {
        return fcjysbfjxxlist;
    }

    public void setFcjysbfjxxlist(List<YrbFcjysbxxFjxxResponse> fcjysbfjxxlist) {
        this.fcjysbfjxxlist = fcjysbfjxxlist;
    }

    @Override
    public String toString() {
        return "YrbFcjysbxxDTO{" +
                "fcjysbxxlist=" + fcjysbxxlist +
                ", fcjysbfjxxlist=" + fcjysbfjxxlist +
                '}';
    }
}
