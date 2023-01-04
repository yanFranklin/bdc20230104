package cn.gtmap.realestate.exchange.core.dto.hefei.swxx.jswszt;

import cn.gtmap.realestate.exchange.core.dto.wwsq.RequestHead;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-08
 * @description
 */
public class JsWsztRequestModel {

    private RequestHead head;

    private JsWsztRequestBody data;

    public RequestHead getHead() {
        return head;
    }

    public void setHead(RequestHead head) {
        this.head = head;
    }

    public JsWsztRequestBody getData() {
        return data;
    }

    public void setData(JsWsztRequestBody data) {
        this.data = data;
    }
}
