package cn.gtmap.realestate.exchange.core.dto.wwsq.init.zydy;

import cn.gtmap.realestate.exchange.core.dto.wwsq.RequestHead;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-05
 * @description
 */
public class InitZyDyRequestDTO {

    private RequestHead requestHead;

    private InitZyDyRequestData data;

    public RequestHead getRequestHead() {
        return requestHead;
    }

    public void setRequestHead(RequestHead requestHead) {
        this.requestHead = requestHead;
    }

    public InitZyDyRequestData getData() {
        return data;
    }

    public void setData(InitZyDyRequestData data) {
        this.data = data;
    }
}
