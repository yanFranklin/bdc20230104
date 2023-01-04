package cn.gtmap.realestate.exchange.core.dto.nantong.gjj.response;

import cn.gtmap.realestate.exchange.core.dto.nantong.gjj.request.GjjRequestHead;

import java.util.List;

public class GjjCqxxResponseDTO {
    private GjjRequestHead head;
    private List<GjjCzxxResponseData> data;

    public GjjRequestHead getHead() {
        return head;
    }

    public void setHead(GjjRequestHead head) {
        this.head = head;
    }

    public List<GjjCzxxResponseData> getData() {
        return data;
    }

    public void setData(List<GjjCzxxResponseData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GjjCqxxResponseDTO{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }
}
