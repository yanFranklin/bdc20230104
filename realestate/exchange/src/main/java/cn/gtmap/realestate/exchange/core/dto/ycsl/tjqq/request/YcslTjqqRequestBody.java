package cn.gtmap.realestate.exchange.core.dto.ycsl.tjqq.request;

public class YcslTjqqRequestBody {

    private YcslTjqqRequestHead head;

    private YcslTjqqRequestData data;

    public YcslTjqqRequestHead getHead() {
        return head;
    }

    public void setHead(YcslTjqqRequestHead head) {
        this.head = head;
    }

    public YcslTjqqRequestData getData() {
        return data;
    }

    public void setData(YcslTjqqRequestData data) {
        this.data = data;
    }
}
