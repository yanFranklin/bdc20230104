package cn.gtmap.realestate.common.core.dto.exchange.ykq.clfrwjs.request;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfrwjs.request.YrbClfRwjshouseRequest;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/14
 * @description 推送税务DTO
 */
public class YkqClfTsxxTaxbizml {

    private List<YkqClfRwjshouseRequest> rwjshouselist;

    public List<YkqClfRwjshouseRequest> getRwjshouselist() {
        return rwjshouselist;
    }

    public void setRwjshouselist(List<YkqClfRwjshouseRequest> rwjshouselist) {
        this.rwjshouselist = rwjshouselist;
    }

    @Override
    public String toString() {
        return "YkqClfTsxxTaxbizml{" +
                "rwjshouselist=" + rwjshouselist +
                '}';
    }
}
