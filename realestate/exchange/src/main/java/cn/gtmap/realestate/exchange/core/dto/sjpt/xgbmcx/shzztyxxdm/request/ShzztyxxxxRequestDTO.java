package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.shzztyxxdm.request;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0
 * @description
 */
public class ShzztyxxxxRequestDTO {
    private ShzztyxxxxRequestHead head;

    private ShzztyxxRequestData data;

    public ShzztyxxxxRequestHead getHead() {
        return head;
    }

    public void setHead(ShzztyxxxxRequestHead head) {
        this.head = head;
    }

    public ShzztyxxRequestData getData() {
        return data;
    }

    public void setData(ShzztyxxRequestData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ShzztyxxxxRequestDTO{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }
}
