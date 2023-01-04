package cn.gtmap.realestate.exchange.core.dto.bjjk.zbb.response;

import cn.gtmap.realestate.exchange.core.dto.bjjk.response.BjjkResponseHead;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-02
 * @description 部级接口 中编办查询接口
 */
public class BjjkZbbResponseDTO {

    private BjjkResponseHead head;

    private List<BjjkZbbResponseData> data;

    public BjjkResponseHead getHead() {
        return head;
    }

    public void setHead(BjjkResponseHead head) {
        this.head = head;
    }

    public List<BjjkZbbResponseData> getData() {
        return data;
    }

    public void setData(List<BjjkZbbResponseData> data) {
        this.data = data;
    }
}
