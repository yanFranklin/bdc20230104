package cn.gtmap.realestate.common.core.dto.exchange.ykq.clfskxxhq.request;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfskxxhq.request.YrbClfskxxhqRequestDTO;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @Date 2022/8/9
 * @description 1.5.存量房计税信息获取【A005】Taxbizml
 */
public class YkqClfskxxhqTaxbizml {

    private List<YrbClfskxxhqRequestDTO> clfskxxhqlist;

    public List<YrbClfskxxhqRequestDTO> getClfskxxhqlist() {
        return clfskxxhqlist;
    }

    public void setClfskxxhqlist(List<YrbClfskxxhqRequestDTO> clfskxxhqlist) {
        this.clfskxxhqlist = clfskxxhqlist;
    }

    @Override
    public String toString() {
        return "YrbClfskxxhqTaxbizml{" +
                "clfskxxhqlist=" + clfskxxhqlist +
                '}';
    }
}
