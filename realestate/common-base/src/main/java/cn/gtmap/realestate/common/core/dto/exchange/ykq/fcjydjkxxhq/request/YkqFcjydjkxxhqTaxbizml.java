package cn.gtmap.realestate.common.core.dto.exchange.ykq.fcjydjkxxhq.request;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjydjkxxhq.request.FcjydjkxxhqRequest;

import java.util.List;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @Date 2022/9/20
 * @description 2.23. 房产交易银行端待缴款信息获取【A022】
 */
public class YkqFcjydjkxxhqTaxbizml {

    private List<FcjydjkxxhqRequest> fcjydjkxxhqlist;

    public List<FcjydjkxxhqRequest> getFcjydjkxxhqlist() {
        return fcjydjkxxhqlist;
    }

    public void setFcjydjkxxhqlist(List<FcjydjkxxhqRequest> fcjydjkxxhqlist) {
        this.fcjydjkxxhqlist = fcjydjkxxhqlist;
    }

    @Override
    public String toString() {
        return "YkqFcjydjkxxhqTaxbizml{" +
                "fcjydjkxxhqlist=" + fcjydjkxxhqlist +
                '}';
    }
}
