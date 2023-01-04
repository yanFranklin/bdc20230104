package cn.gtmap.realestate.exchange.core.dto.nantong.hlwtj.response;

import cn.gtmap.realestate.exchange.core.dto.nantong.hlwtj.request.DjlxtjRequestHead;

import java.util.List;

public class DjlxtjResponDTO {

    private DjlxtjReponseHead head;

    private List<DjlxtjResponseData> data;

    public DjlxtjReponseHead getHead() {
        return head;
    }

    public void setHead(DjlxtjReponseHead head) {
        this.head = head;
    }

    public List<DjlxtjResponseData> getData() {
        return data;
    }

    public void setData(List<DjlxtjResponseData> data) {
        this.data = data;
    }
}
