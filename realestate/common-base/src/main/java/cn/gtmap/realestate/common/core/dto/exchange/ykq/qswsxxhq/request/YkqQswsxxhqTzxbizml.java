package cn.gtmap.realestate.common.core.dto.exchange.ykq.qswsxxhq.request;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswsxxhq.request.YrbQswsxxhqRequest;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @Date 2022/8/11
 * @description 1.7.    契税完税信息获取【A009】
 */

public class YkqQswsxxhqTzxbizml {

    private List<YrbQswsxxhqRequest> fcjyskxxlist;

    public List<YrbQswsxxhqRequest> getFcjyskxxlist() {
        return fcjyskxxlist;
    }

    public void setFcjyskxxlist(List<YrbQswsxxhqRequest> fcjyskxxlist) {
        this.fcjyskxxlist = fcjyskxxlist;
    }

    @Override
    public String toString() {
        return "YkqQswsxxhqTzxbizml{" +
                "fcjyskxxlist=" + fcjyskxxlist +
                '}';
    }
}
