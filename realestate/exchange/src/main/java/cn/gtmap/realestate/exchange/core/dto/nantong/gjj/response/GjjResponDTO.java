package cn.gtmap.realestate.exchange.core.dto.nantong.gjj.response;

import cn.gtmap.realestate.exchange.core.dto.nantong.gjj.request.GjjRequestHead;

import java.util.List;

public class GjjResponDTO {

    private GjjRequestHead head;

    private List<GjjFwxxResponseData> data;

    public GjjRequestHead getHead() {
        return head;
    }

    public void setHead(GjjRequestHead head) {
        this.head = head;
    }

    public List<GjjFwxxResponseData> getData() {
        return data;
    }

    public void setData(List<GjjFwxxResponseData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GjjResponDTO{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }
}
