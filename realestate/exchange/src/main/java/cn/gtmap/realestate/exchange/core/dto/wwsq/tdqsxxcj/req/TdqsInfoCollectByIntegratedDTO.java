package cn.gtmap.realestate.exchange.core.dto.wwsq.tdqsxxcj.req;

public class TdqsInfoCollectByIntegratedDTO {
    private TdqsInfoCollectRequestDTO request;

    public TdqsInfoCollectRequestDTO getRequest() {
        return request;
    }

    public void setRequest(TdqsInfoCollectRequestDTO request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "TdqsInfoCollectByIntegratedDTO{" +
                "request=" + request +
                '}';
    }
}
