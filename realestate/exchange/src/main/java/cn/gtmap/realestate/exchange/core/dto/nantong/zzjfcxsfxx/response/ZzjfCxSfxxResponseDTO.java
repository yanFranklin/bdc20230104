package cn.gtmap.realestate.exchange.core.dto.nantong.zzjfcxsfxx.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-29
 * @description
 */
public class ZzjfCxSfxxResponseDTO {

    private ZzjfCxSfxxResponseHead head;

    private List<ZzjfCxSfxxResponseData> data;

    public ZzjfCxSfxxResponseHead getHead() {
        return head;
    }

    public void setHead(ZzjfCxSfxxResponseHead head) {
        this.head = head;
    }

    public List<ZzjfCxSfxxResponseData> getData() {
        return data;
    }

    public void setData(List<ZzjfCxSfxxResponseData> data) {
        this.data = data;
    }
}
