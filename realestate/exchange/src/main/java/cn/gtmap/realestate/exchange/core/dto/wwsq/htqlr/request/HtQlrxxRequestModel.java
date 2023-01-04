package cn.gtmap.realestate.exchange.core.dto.wwsq.htqlr.request;

import cn.gtmap.realestate.exchange.core.dto.wwsq.RequestHead;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-24
 * @description 合同权利人信息请求结构
 */
public class HtQlrxxRequestModel {

    private RequestHead head;

    private HtQlrxxRequestData data;

    public RequestHead getHead() {
        return head;
    }

    public void setHead(RequestHead head) {
        this.head = head;
    }

    public HtQlrxxRequestData getData() {
        return data;
    }

    public void setData(HtQlrxxRequestData data) {
        this.data = data;
    }
}
