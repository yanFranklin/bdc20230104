package cn.gtmap.realestate.exchange.core.dto.sjpt.token.request;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/8/27.
 * @description
 */
public class SjptGetTokenRequestDTO {

    private SjptGetTokenRequestHead head;

    private SjptGetTokenRequestData data;

    public SjptGetTokenRequestHead getHead() {
        return head;
    }

    public void setHead(SjptGetTokenRequestHead head) {
        this.head = head;
    }

    public SjptGetTokenRequestData getData() {
        return data;
    }

    public void setData(SjptGetTokenRequestData data) {
        this.data = data;
    }
}
