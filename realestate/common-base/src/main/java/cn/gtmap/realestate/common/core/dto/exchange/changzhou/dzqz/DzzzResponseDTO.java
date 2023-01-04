package cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz;

public class DzzzResponseDTO {
    private DzzzResponseHead head;
    private DzzzResponseData data;

    public DzzzResponseHead getHead() {
        return head;
    }

    public void setHead(DzzzResponseHead head) {
        this.head = head;
    }

    public DzzzResponseData getData() {
        return data;
    }

    public void setData(DzzzResponseData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ChangzhouDzqzResponseDTO{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }
}
