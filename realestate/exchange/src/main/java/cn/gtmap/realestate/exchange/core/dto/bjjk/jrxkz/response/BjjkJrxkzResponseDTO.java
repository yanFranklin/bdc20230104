package cn.gtmap.realestate.exchange.core.dto.bjjk.jrxkz.response;

import cn.gtmap.realestate.exchange.core.dto.bjjk.response.BjjkResponseHead;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-02
 * @description 金融许可证查询响应实体
 */
public class BjjkJrxkzResponseDTO {

    private BjjkResponseHead head;

    private BjjkJrxkzResponseData data;

    public BjjkResponseHead getHead() {
        return head;
    }

    public void setHead(BjjkResponseHead head) {
        this.head = head;
    }

    public BjjkJrxkzResponseData getData() {
        return data;
    }

    public void setData(BjjkJrxkzResponseData data) {
        this.data = data;
    }
}
