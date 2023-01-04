package cn.gtmap.realestate.exchange.core.dto.nantong.hlwtj.response;

import java.util.List;

public class BlhjtjResponDTO {

    private BlhjtjReponseHead head;

    private List<BlhjtjResponseData> data;

    public BlhjtjReponseHead getHead() {
        return head;
    }

    public void setHead(BlhjtjReponseHead head) {
        this.head = head;
    }

    public List<BlhjtjResponseData> getData() {
        return data;
    }

    public void setData(List<BlhjtjResponseData> data) {
        this.data = data;
    }
}
