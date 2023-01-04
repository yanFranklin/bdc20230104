package cn.gtmap.realestate.exchange.core.dto.wwsq.ychslist.response;

import cn.gtmap.realestate.exchange.core.dto.wwsq.hslist.response.FwhsResponseInfos;
import cn.gtmap.realestate.exchange.core.dto.wwsq.hslist.response.FwhsResponseStatus;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-03
 * @description
 */
public class FwYchsResponseData {

    private FwYchsResponseInfos info;

    private FwYchsResponseStatus status;

    public FwYchsResponseInfos getInfo() {
        return info;
    }

    public void setInfo(FwYchsResponseInfos info) {
        this.info = info;
    }

    public FwYchsResponseStatus getStatus() {
        return status;
    }

    public void setStatus(FwYchsResponseStatus status) {
        this.status = status;
    }
}
