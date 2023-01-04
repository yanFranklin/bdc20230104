package cn.gtmap.realestate.exchange.core.dto.bjjk.shzz.response;

import cn.gtmap.realestate.exchange.core.dto.bjjk.response.BjjkResponseHead;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-02
 * @description 部级接口 社会组织统一信用代码信息接口响应实体
 */
public class BjjkShzzResponseDTO {

    private BjjkResponseHead head;

    private BjjkShzzResponseData data;

    public BjjkResponseHead getHead() {
        return head;
    }

    public void setHead(BjjkResponseHead head) {
        this.head = head;
    }

    public BjjkShzzResponseData getData() {
        return data;
    }

    public void setData(BjjkShzzResponseData data) {
        this.data = data;
    }
}
