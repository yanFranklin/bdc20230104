package cn.gtmap.realestate.exchange.core.dto.wwsq.tdqsxxcj.resp;


public class TdqsxxcjResponseResultDto {

    private TdqsxxcjResponseResultHeadDto head;
    private TdqsxxcjResponseResultBodyDto body;

    public TdqsxxcjResponseResultHeadDto getHead() {
        return head;
    }

    public void setHead(TdqsxxcjResponseResultHeadDto head) {
        this.head = head;
    }

    public TdqsxxcjResponseResultBodyDto getBody() {
        return body;
    }

    public void setBody(TdqsxxcjResponseResultBodyDto body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "TdqsxxcjResponseResultDto{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }
}
