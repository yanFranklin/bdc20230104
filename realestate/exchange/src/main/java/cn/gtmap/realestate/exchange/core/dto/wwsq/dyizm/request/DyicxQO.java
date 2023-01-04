package cn.gtmap.realestate.exchange.core.dto.wwsq.dyizm.request;

import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/6/28
 * @description 3构（建）筑物信息查询（不动产单元信息）QO
 */
@IgnoreCast(ignoreNum = 6)
public class DyicxQO {


    private String dyzmh;

    public String getDyzmh() {
        return dyzmh;
    }

    public void setDyzmh(String dyzmh) {
        this.dyzmh = dyzmh;
    }
}
