package cn.gtmap.realestate.common.core.dto.exchange.ykq.qswspzhq.request;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswspzhq.request.YrbQswspzhqRequest;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/11
 * @description 2.20    契税完税凭证获取【A020】
 */
public class YkqQswspzhqTaxbizml {

    private List<YrbQswspzhqRequest> fcjyqswspzlist;

    public List<YrbQswspzhqRequest> getFcjyqswspzlist() {
        return fcjyqswspzlist;
    }

    public void setFcjyqswspzlist(List<YrbQswspzhqRequest> fcjyqswspzlist) {
        this.fcjyqswspzlist = fcjyqswspzlist;
    }

    @Override
    public String toString() {
        return "YkqQswspzhqTaxbizml{" +
                "fcjyqswspzlist=" + fcjyqswspzlist +
                '}';
    }
}
