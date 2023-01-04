package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.dmxxcx.request;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0
 * @description
 */
public class DmxxRequestDTO {
    private DmxxRequestHead head;

    private DmxxcxRequestData data;

    public DmxxRequestHead getHead() {
        return head;
    }

    public void setHead(DmxxRequestHead head) {
        this.head = head;
    }

    public DmxxcxRequestData getData() {
        return data;
    }

    public void setData(DmxxcxRequestData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DmxxRequestDTO{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }
}
