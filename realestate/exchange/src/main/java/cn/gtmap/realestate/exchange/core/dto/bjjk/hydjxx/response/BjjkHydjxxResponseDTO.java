package cn.gtmap.realestate.exchange.core.dto.bjjk.hydjxx.response;

import cn.gtmap.realestate.exchange.core.dto.bjjk.response.BjjkResponseHead;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-02
 * @description 部级接口 婚姻登记信息 响应结果
 */
public class BjjkHydjxxResponseDTO {

    private BjjkResponseHead head;

    private BjjkHydjxxResponseData data;

    public BjjkResponseHead getHead() {
        return head;
    }

    public void setHead(BjjkResponseHead head) {
        this.head = head;
    }

    public BjjkHydjxxResponseData getData() {
        return data;
    }

    public void setData(BjjkHydjxxResponseData data) {
        this.data = data;
    }
}
