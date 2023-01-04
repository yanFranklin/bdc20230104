package cn.gtmap.realestate.common.core.dto.exchange.ykq.zlfskxxhq.request;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfskxxhq.request.YrbZlfskxxRequestDTO;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/9
 * @description 1.4.    增量房计税信息获取【A004】Taxbizml
 */
public class YkqZlfskxxTaxbizml {

    private List<YrbZlfskxxRequestDTO> zlfskxxhqlist;

    public List<YrbZlfskxxRequestDTO> getZlfskxxhqlist() {
        return zlfskxxhqlist;
    }

    public void setZlfskxxhqlist(List<YrbZlfskxxRequestDTO> zlfskxxhqlist) {
        this.zlfskxxhqlist = zlfskxxhqlist;
    }

    @Override
    public String toString() {
        return "YkqZlfskxxTaxbizml{" +
                "zlfskxxhqlist=" + zlfskxxhqlist +
                '}';
    }
}
