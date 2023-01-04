package cn.gtmap.realestate.exchange.core.dto.sjpt.cxsq.response;

import cn.gtmap.realestate.exchange.core.dto.sjpt.SjptCxResponseHead;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-21
 * @description
 */
public class SjptCxsqResponseDTO {

    private SjptCxResponseHead head;

    private List<SjptCxsqResponseData> data;

    public SjptCxResponseHead getHead() {
        return head;
    }

    public void setHead(SjptCxResponseHead head) {
        this.head = head;
    }

    public List<SjptCxsqResponseData> getData() {
        return data;
    }

    public void setData(List<SjptCxsqResponseData> data) {
        this.data = data;
    }
}
