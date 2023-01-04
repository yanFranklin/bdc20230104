package cn.gtmap.realestate.common.core.dto.exchange.wwsq;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateSlztRequestBody{");
        sb.append("head=").append(head);
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
