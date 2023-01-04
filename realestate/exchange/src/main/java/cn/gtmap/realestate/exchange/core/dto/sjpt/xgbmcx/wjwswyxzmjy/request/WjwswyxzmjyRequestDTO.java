package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.wjwswyxzmjy.request;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">wyh</a>
 * @version 1.0
 * @description
 */
public class WjwswyxzmjyRequestDTO {
    private WjwswyxzmjyRequestHead head;

    private WjwswyxzmjyRequestData data;

    public WjwswyxzmjyRequestHead getHead() {
        return head;
    }

    public void setHead(WjwswyxzmjyRequestHead head) {
        this.head = head;
    }

    public WjwswyxzmjyRequestData getData() {
        return data;
    }

    public void setData(WjwswyxzmjyRequestData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WjwswyxzmjyRequestDTO{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }
}
