package cn.gtmap.realestate.certificate.core.model;

/**
 * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
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
}
