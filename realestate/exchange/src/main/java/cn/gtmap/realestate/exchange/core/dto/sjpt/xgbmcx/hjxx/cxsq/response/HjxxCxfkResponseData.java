package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.hjxx.cxsq.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-01-09
 * @description
 */
public class HjxxCxfkResponseData {

    @JSONField(name = "cxjg")
    private List<HjxxCxfkResponseCxjg> cxjg;

    public List<HjxxCxfkResponseCxjg> getCxjg() {
        return cxjg;
    }

    public void setCxjg(List<HjxxCxfkResponseCxjg> cxjg) {
        this.cxjg = cxjg;
    }
}
