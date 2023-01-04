package cn.gtmap.realestate.exchange.core.dto.sjpt.cxsqFk.request;

import cn.gtmap.realestate.exchange.core.dto.sjpt.SjptCxRequestHead;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxws.request.SjptCxwsRequestData;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-23
 * @description
 */
public class SjptCxsqFkRequestDTO {

    private SjptCxRequestHead head;

    private SjptCxwsRequestData data;

    public SjptCxRequestHead getHead() {
        return head;
    }

    public void setHead(SjptCxRequestHead head) {
        this.head = head;
    }

    public SjptCxwsRequestData getData() {
        return data;
    }

    public void setData(SjptCxwsRequestData data) {
        this.data = data;
    }
}
