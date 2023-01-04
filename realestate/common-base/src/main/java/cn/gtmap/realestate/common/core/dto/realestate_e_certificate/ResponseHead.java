package cn.gtmap.realestate.common.core.dto.realestate_e_certificate;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0 2019/1/21
 * @description  响应头
 */
public class ResponseHead {

    /**
    * 执行结果
    */
    private String status;

    /**
    * 异常信息
    */
    private String message;

    public ResponseHead(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseHead(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseHead{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
