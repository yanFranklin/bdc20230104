package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.sydjxxcx.request;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">wyh</a>
 * @version 1.0
 * @description
 */
public class SydjxxcxRequestDTO {
    private SydjxxcxRequestHead head;

    private SydjxxcxRequestData data;

    public SydjxxcxRequestHead getHead() {
        return head;
    }

    public void setHead(SydjxxcxRequestHead head) {
        this.head = head;
    }

    public SydjxxcxRequestData getData() {
        return data;
    }

    public void setData(SydjxxcxRequestData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SydjxxcxRequestDTO{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }
}
