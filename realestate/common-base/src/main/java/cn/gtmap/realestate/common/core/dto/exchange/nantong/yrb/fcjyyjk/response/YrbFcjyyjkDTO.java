package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyyjk.response;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/10
 * @description 1.11.    房产交易应缴款列表【A017】
 */
public class YrbFcjyyjkDTO {

    private List<YrbFcjyyjkResponse> fcjyyjsklist;

    public List<YrbFcjyyjkResponse> getFcjyyjsklist() {
        return fcjyyjsklist;
    }

    public void setFcjyyjsklist(List<YrbFcjyyjkResponse> fcjyyjsklist) {
        this.fcjyyjsklist = fcjyyjsklist;
    }

    @Override
    public String toString() {
        return "YrbFcjyyjkDTO{" +
                "fcjyyjsklist=" + fcjyyjsklist +
                '}';
    }
}
