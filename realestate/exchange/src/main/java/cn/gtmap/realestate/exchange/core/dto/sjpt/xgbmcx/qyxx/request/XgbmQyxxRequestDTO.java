package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.qyxx.request;
/**
 * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
 * @version 1.0  2019-08-27
 * @description
 */
public class XgbmQyxxRequestDTO {
    private XgbmQyxxRequestHead head;

    private XgbmQyxxRequestData data;

    public XgbmQyxxRequestHead getHead() {
        return head;
    }

    public void setHead(XgbmQyxxRequestHead head) {
        this.head = head;
    }

    public XgbmQyxxRequestData getData() {
        return data;
    }

    public void setData(XgbmQyxxRequestData data) {
        this.data = data;
    }
}
