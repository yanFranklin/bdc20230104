package cn.gtmap.realestate.exchange.core.dto.wwsq.zsyz.response;

import cn.gtmap.realestate.exchange.core.dto.wwsq.RequestHead;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-01
 * @description
 */
public class ZsyzResponse {

    private RequestHead head;

    private List<ZsyzResponseData> data;

    public RequestHead getHead() {
        return head;
    }

    public void setHead(RequestHead head) {
        this.head = head;
    }

    public List<ZsyzResponseData> getData() {
        return data;
    }

    public void setData(List<ZsyzResponseData> data) {
        this.data = data;
    }
}
