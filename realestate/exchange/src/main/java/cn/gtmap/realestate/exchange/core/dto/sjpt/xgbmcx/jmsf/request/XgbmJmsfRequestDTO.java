package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.jmsf.request;
/**
 * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
 * @version 1.0  2019-08-27
 * @description
 */
public class XgbmJmsfRequestDTO {
    private XgbmJmsfRequestHead head;

    private XgbmJmsfRequestData data;

    public XgbmJmsfRequestHead getHead() {
        return head;
    }

    public void setHead(XgbmJmsfRequestHead head) {
        this.head = head;
    }

    public XgbmJmsfRequestData getData() {
        return data;
    }

    public void setData(XgbmJmsfRequestData data) {
        this.data = data;
    }
}
