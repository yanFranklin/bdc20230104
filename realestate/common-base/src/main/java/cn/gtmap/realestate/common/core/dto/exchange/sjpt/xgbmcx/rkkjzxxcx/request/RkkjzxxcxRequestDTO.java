package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.rkkjzxxcx.request;

import java.util.List;

/**
 * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
 * @version 1.0  2019-08-26
 * @description 人口基准信息查询请求实体
 */
public class RkkjzxxcxRequestDTO {
    private List<RkkjzxxcxCxywcsRequestDTO> cxywcs;

    public List<RkkjzxxcxCxywcsRequestDTO> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<RkkjzxxcxCxywcsRequestDTO> cxywcs) {
        this.cxywcs = cxywcs;
    }

}
