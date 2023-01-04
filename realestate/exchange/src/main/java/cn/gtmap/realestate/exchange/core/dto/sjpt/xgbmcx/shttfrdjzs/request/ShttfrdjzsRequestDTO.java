package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.shttfrdjzs.request;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0
 * @description
 */
public class ShttfrdjzsRequestDTO {
    private ShttfrdjzsRequestHead head;

    private ShttfrdjzsRequestData data;

    public ShttfrdjzsRequestHead getHead() {
        return head;
    }

    public void setHead(ShttfrdjzsRequestHead head) {
        this.head = head;
    }

    public ShttfrdjzsRequestData getData() {
        return data;
    }

    public void setData(ShttfrdjzsRequestData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ShttfrdjzsRequestDTO{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }
}
