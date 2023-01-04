package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.mbfqydjzs.request;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0
 * @description
 */
public class MbfqydjzsRequestDTO {
    private MbfqydjzsRequestHead head;

    private MbfqydjzsRequestData data;

    public MbfqydjzsRequestHead getHead() {
        return head;
    }

    public void setHead(MbfqydjzsRequestHead head) {
        this.head = head;
    }

    public MbfqydjzsRequestData getData() {
        return data;
    }

    public void setData(MbfqydjzsRequestData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MbfqydjzsRequestDTO{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }
}
