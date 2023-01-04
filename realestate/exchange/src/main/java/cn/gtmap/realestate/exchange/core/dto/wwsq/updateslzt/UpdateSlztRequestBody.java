package cn.gtmap.realestate.exchange.core.dto.wwsq.updateslzt;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/10/16
 * @description 更新受理状态 请求体
 */
public class UpdateSlztRequestBody {
    private UpdateSlztRequestHead head;
    private UpdateSlztRequestData data;

    public UpdateSlztRequestHead getHead() {
        return head;
    }

    public void setHead(UpdateSlztRequestHead head) {
        this.head = head;
    }

    public UpdateSlztRequestData getData() {
        return data;
    }

    public void setData(UpdateSlztRequestData data) {
        this.data = data;
    }
}
