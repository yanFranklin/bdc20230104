package cn.gtmap.realestate.common.core.dto.exchange.ykq.fcjysbxxqr.request;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxxqr.request.YrbFcjysbxxQrRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.yxzlxxjs.request.YrbYwwjxx;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/9
 * @description 1.15.    房产交易申报信息确认【A015】
 */

public class YkqFcjysbxxQrTaxbizml {

    private List<YrbFcjysbxxQrRequestDTO> fcjysbqrxxlist;

    private List<YrbYwwjxx> fcjysbqrfjxxlist;

    public List<YrbFcjysbxxQrRequestDTO> getFcjysbqrxxlist() {
        return fcjysbqrxxlist;
    }

    public void setFcjysbqrxxlist(List<YrbFcjysbxxQrRequestDTO> fcjysbqrxxlist) {
        this.fcjysbqrxxlist = fcjysbqrxxlist;
    }

    public List<YrbYwwjxx> getFcjysbqrfjxxlist() {
        return fcjysbqrfjxxlist;
    }

    public void setFcjysbqrfjxxlist(List<YrbYwwjxx> fcjysbqrfjxxlist) {
        this.fcjysbqrfjxxlist = fcjysbqrfjxxlist;
    }

    @Override
    public String toString() {
        return "YkqFcjysbxxQrTaxbizml{" +
                "fcjysbqrxxlist=" + fcjysbqrxxlist +
                ", fcjysbqrfjxxlist=" + fcjysbqrfjxxlist +
                '}';
    }
}
