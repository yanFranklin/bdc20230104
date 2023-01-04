package cn.gtmap.realestate.common.core.dto.exchange.ykq.fcjysbxx.request;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxx.request.YrbFcjysbxxRequestDTO;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/9
 * @description 1.14.    房产交易申报信息获取【A014】
 */
public class YkqFcjysbxxTaxbizml {

    private List<YrbFcjysbxxRequestDTO> fcjysbxxlist;

    public List<YrbFcjysbxxRequestDTO> getFcjysbxxlist() {
        return fcjysbxxlist;
    }

    public void setFcjysbxxlist(List<YrbFcjysbxxRequestDTO> fcjysbxxlist) {
        this.fcjysbxxlist = fcjysbxxlist;
    }

    @Override
    public String toString() {
        return "YkqFcjysbxxTaxbizml{" +
                "fcjysbxxlist=" + fcjysbxxlist +
                '}';
    }
}
