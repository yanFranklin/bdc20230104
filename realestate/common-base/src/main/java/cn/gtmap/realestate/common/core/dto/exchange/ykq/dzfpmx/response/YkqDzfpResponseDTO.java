package cn.gtmap.realestate.common.core.dto.exchange.ykq.dzfpmx.response;


import cn.gtmap.realestate.common.core.dto.exchange.ResponseHead;

public class YkqDzfpResponseDTO {

    private ResponseHead head;

    private YkqDzfpmxDTO data;

    public ResponseHead getHead() {
        return head;
    }

    public void setHead(ResponseHead head) {
        this.head = head;
    }

    public YkqDzfpmxDTO getData() {
        return data;
    }

    public void setData(YkqDzfpmxDTO data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "YkqDzfpResponseDTO{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }
}
