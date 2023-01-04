package cn.gtmap.realestate.exchange.core.dto.wwsq;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-01
 * @description
 */
public class WwsqResponse {

    private ResponseHead head;

    private Object data;

    public ResponseHead getHead() {
        return head;
    }

    public void setHead(ResponseHead head) {
        this.head = head;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static WwsqResponse newResponse(String code, String msg, Object data) {
        WwsqResponse response = new WwsqResponse();
        ResponseHead head = new ResponseHead();
        head.setStatusCode(code);
        head.setMsg(msg);
        response.setHead(head);
        response.setData(data);
        return response;
    }
}
