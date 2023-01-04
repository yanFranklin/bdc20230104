package cn.gtmap.realestate.exchange.core.dto.bengbu.djxx;

/**
 * @description 登记信息响应
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @date 2022/12/22 11:38s
 */
public class DjxxCommonResponse<T> {

    /**
     * 错误编码
     */
    public static final String ERROR_STATUS = "500";
    public static final String ERROR_MESSAGE = "失败";
    /**
     * 成功编码
     */
    public static final String SUCCESS_STATUS = "200";
    public static final String SUCCESS_MESSAGE = "成功";

    private String status;

    private String message;

    private T result;

    public static <T> DjxxCommonResponse<T> ok() {
        DjxxCommonResponse<T> resp = new DjxxCommonResponse<>();
        resp.status = SUCCESS_STATUS;
        resp.message = SUCCESS_MESSAGE;
        return resp;
    }

    public static <T> DjxxCommonResponse<T> ok(T result) {
        DjxxCommonResponse<T> resp = new DjxxCommonResponse<>();
        resp.status = SUCCESS_STATUS;
        resp.message = SUCCESS_MESSAGE;
        resp.result = result;
        return resp;
    }



    public static <T> DjxxCommonResponse<T> fail() {
        DjxxCommonResponse<T> resp = new DjxxCommonResponse<>();
        resp.status = ERROR_STATUS;
        resp.message = ERROR_MESSAGE;
        return resp;
    }

    public static <T> DjxxCommonResponse<T> fail(String message) {
        DjxxCommonResponse<T> resp = new DjxxCommonResponse<>();
        resp.status = ERROR_STATUS;
        resp.message = message;
        return resp;
    }

    public static <T> DjxxCommonResponse<T> fail(String status, String message) {
        DjxxCommonResponse<T> resp = new DjxxCommonResponse<>();
        resp.status = status;
        resp.message = message;
        return resp;
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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "DjxxCommonResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
