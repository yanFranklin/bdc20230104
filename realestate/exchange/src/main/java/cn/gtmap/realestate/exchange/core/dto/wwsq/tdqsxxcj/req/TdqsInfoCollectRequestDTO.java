package cn.gtmap.realestate.exchange.core.dto.wwsq.tdqsxxcj.req;

public class TdqsInfoCollectRequestDTO {

    private TdqsInfoCollectRequestHeadDTO head;
    private TdqsInfoCollectRequestBodyDTO body;

    public TdqsInfoCollectRequestHeadDTO getHead() {
        return head;
    }

    public void setHead(TdqsInfoCollectRequestHeadDTO head) {
        this.head = head;
    }

    public TdqsInfoCollectRequestBodyDTO getBody() {
        return body;
    }

    public void setBody(TdqsInfoCollectRequestBodyDTO body) {
        this.body = body;
    }


    @Override
    public String toString() {
        return "TdqsInfoCollectRequestDTO{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }
}
