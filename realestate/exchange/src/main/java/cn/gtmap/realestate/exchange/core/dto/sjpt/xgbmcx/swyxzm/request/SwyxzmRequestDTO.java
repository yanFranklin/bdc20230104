package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.swyxzm.request;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0
 * @description
 */
public class SwyxzmRequestDTO {
    private SwyxzmRequestHead head;

    private SwyxzmRequestData data;

    public SwyxzmRequestHead getHead() {
        return head;
    }

    public void setHead(SwyxzmRequestHead head) {
        this.head = head;
    }

    public SwyxzmRequestData getData() {
        return data;
    }

    public void setData(SwyxzmRequestData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SwyxzmRequestDTO{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }
}
