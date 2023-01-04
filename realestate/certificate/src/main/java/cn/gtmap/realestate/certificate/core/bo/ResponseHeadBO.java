package cn.gtmap.realestate.certificate.core.bo;

/**
 * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwenao</a>
 * @version 1.0 2019/12/18
 * @description 响应头
 */
public class ResponseHeadBO {

    /**
     * 执行结果
     */
    private String status;

    /**
     * 异常信息
     */
    private String message;

    public ResponseHeadBO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseHeadBO() {
    }

    @Override
    public String toString() {
        return "ResponseHeadBO{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

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
