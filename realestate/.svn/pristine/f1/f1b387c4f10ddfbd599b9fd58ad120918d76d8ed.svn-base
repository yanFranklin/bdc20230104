package cn.gtmap.realestate.exchange.core.dto;

public class ExchangeDsfCommonMsgResponse<T> {

    /**
     * 成功编码
     */
    public static final String SUCCESS_CODE = "1";

    /**
     * 成功描述
     */
    public static final String SUCCESS_msg = "成功";

    /**
     * 错误编码
     */
    public static final String ERROR_CODE = "0";

    /**
     * 响应编码
     */
    private String code;

    /**
     * 响应描述
     */
    private String msg;

    /**
     * 业务数据
     */
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getmsg() {
        return msg;
    }

    public void setmsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ExchangeDsfCommonMsgResponse<T> fail(String code, String msg, T data) {
        ExchangeDsfCommonMsgResponse<T> resp = new ExchangeDsfCommonMsgResponse<>();
        resp.code = code;
        resp.msg = msg;
        // 出错时仍然把当前数据返回，以便排障
        resp.data = data;
        return resp;
    }

    public static <T> ExchangeDsfCommonMsgResponse<T> fail(String msg) {
        ExchangeDsfCommonMsgResponse<T> resp = new ExchangeDsfCommonMsgResponse<>();
        resp.code = ERROR_CODE;
        resp.msg = msg;
        return resp;
    }

    public static <T> ExchangeDsfCommonMsgResponse<T> ok(T data) {
        ExchangeDsfCommonMsgResponse<T> resp = new ExchangeDsfCommonMsgResponse<>();
        resp.code = SUCCESS_CODE;
        resp.msg = SUCCESS_msg;
        resp.data = data;
        return resp;
    }

    public static <T> ExchangeDsfCommonMsgResponse<T> ok() {
        ExchangeDsfCommonMsgResponse<T> resp = new ExchangeDsfCommonMsgResponse<>();
        resp.code = SUCCESS_CODE;
        resp.msg = SUCCESS_msg;
        return resp;
    }

    public static <T> ExchangeDsfCommonMsgResponse<T> fail(String code, String msg) {
        ExchangeDsfCommonMsgResponse<T> resp = new ExchangeDsfCommonMsgResponse();
        resp.code = code;
        resp.msg = msg;
        return resp;
    }

    public static <T> ExchangeDsfCommonMsgResponse<T> fail(String msg, T data) {
        ExchangeDsfCommonMsgResponse<T> resp = new ExchangeDsfCommonMsgResponse();
        resp.data = data;
        resp.msg = msg;
        return resp;
    }

}
