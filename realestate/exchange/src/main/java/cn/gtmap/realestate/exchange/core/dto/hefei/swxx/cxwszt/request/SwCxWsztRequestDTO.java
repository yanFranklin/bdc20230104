package cn.gtmap.realestate.exchange.core.dto.hefei.swxx.cxwszt.request;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-04
 * @description
 */
public class SwCxWsztRequestDTO {

    private SwCxWsztRequestHead head;

    private SwCxWsztRequestBody body;

    public SwCxWsztRequestHead getHead() {
        return head;
    }

    public void setHead(SwCxWsztRequestHead head) {
        this.head = head;
    }

    public SwCxWsztRequestBody getBody() {
        return body;
    }

    public void setBody(SwCxWsztRequestBody body) {
        this.body = body;
    }
}
