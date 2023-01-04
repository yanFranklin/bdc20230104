package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.rkkjzxxcx.request;

/**
 * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
 * @version 1.0  2019-08-26
 * @description
 */
public class XgbmRkkjzxxcxRequestDTO {
    private XgbmRkkjzxxcxRequestHead head;

    private XgbmRkkjzxxcxRequestData data;

    public XgbmRkkjzxxcxRequestHead getHead() {
        return head;
    }

    public void setHead(XgbmRkkjzxxcxRequestHead head) {
        this.head = head;
    }

    public XgbmRkkjzxxcxRequestData getData() {
        return data;
    }

    public void setData(XgbmRkkjzxxcxRequestData data) {
        this.data = data;
    }
}


