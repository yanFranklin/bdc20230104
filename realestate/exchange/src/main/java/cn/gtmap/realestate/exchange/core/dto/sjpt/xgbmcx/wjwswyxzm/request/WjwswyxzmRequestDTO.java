package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.wjwswyxzm.request;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">wyh</a>
 * @version 1.0
 * @description
 */
public class WjwswyxzmRequestDTO {
    private WjwswyxzmRequestHead head;

    private WjwswyxzmRequestData data;

    public WjwswyxzmRequestHead getHead() {
        return head;
    }

    public void setHead(WjwswyxzmRequestHead head) {
        this.head = head;
    }

    public WjwswyxzmRequestData getData() {
        return data;
    }

    public void setData(WjwswyxzmRequestData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WjwswyxzmRequestDTO{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }
}
