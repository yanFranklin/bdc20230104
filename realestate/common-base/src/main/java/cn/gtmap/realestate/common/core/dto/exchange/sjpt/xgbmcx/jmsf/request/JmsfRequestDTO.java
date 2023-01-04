package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.jmsf.request;


import java.util.List;

/**
 * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
 * @version 1.0  2019-08-27
 * @description 居民身份信息查询请求实体
 */
public class JmsfRequestDTO {

    private List<JmsfCxywcsRequestDTO> cxywcs;

    public List<JmsfCxywcsRequestDTO> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<JmsfCxywcsRequestDTO> cxywcs) {
        this.cxywcs = cxywcs;
    }


}
