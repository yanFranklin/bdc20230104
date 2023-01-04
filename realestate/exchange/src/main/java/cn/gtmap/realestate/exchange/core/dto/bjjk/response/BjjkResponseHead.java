package cn.gtmap.realestate.exchange.core.dto.bjjk.response;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-02
 * @description
 */
public class BjjkResponseHead {

    private String msg;

    private String requestType;

    private String cxqqdh;

    private String businessNumber;

    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getCxqqdh() {
        return cxqqdh;
    }

    public void setCxqqdh(String cxqqdh) {
        this.cxqqdh = cxqqdh;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
