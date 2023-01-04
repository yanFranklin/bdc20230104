package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.hyxxhygr.request;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0
 * @description
 */
public class HyxxhygrRequestDTO {
    private HyxxhygrRequestHead head;

    private HyxxhygrRequestData data;

    public HyxxhygrRequestHead getHead() {
        return head;
    }

    public void setHead(HyxxhygrRequestHead head) {
        this.head = head;
    }

    public HyxxhygrRequestData getData() {
        return data;
    }

    public void setData(HyxxhygrRequestData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HyxxhygrRequestDTO{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }
}
