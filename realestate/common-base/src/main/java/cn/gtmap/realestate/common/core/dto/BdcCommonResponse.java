package cn.gtmap.realestate.common.core.dto;

public class BdcCommonResponse<T> {

    /**
     * 成功编码
     */
    public static final String SUCCESS_CODE = "1";

    /**
     * 成功描述
     */
    public static final String SUCCESS_MESSAGE = "成功";

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
    private String message;

    /**
     * 业务数据
     */
    private T data;

    public static <T> BdcCommonResponse<T> fail(String code, String message, T data) {
        BdcCommonResponse<T> resp = new BdcCommonResponse<>();
        resp.code = code;
        resp.message = message;
        // 出错时仍然把当前数据返回，以便排障
        resp.data = data;
        return resp;
    }

    public static <T> BdcCommonResponse<T> fail(String message) {
        BdcCommonResponse<T> resp = new BdcCommonResponse<>();
        resp.code = ERROR_CODE;
        resp.message = message;
        return resp;
    }

    public static <T> BdcCommonResponse<T> ok(T data) {
        BdcCommonResponse<T> resp = new BdcCommonResponse<>();
        resp.code = SUCCESS_CODE;
        resp.message = SUCCESS_MESSAGE;
        resp.data = data;
        return resp;
    }

    public static <T> BdcCommonResponse<T> ok() {
        BdcCommonResponse<T> resp = new BdcCommonResponse<>();
        resp.code = SUCCESS_CODE;
        resp.message = SUCCESS_MESSAGE;
        return resp;
    }

    public static <T> BdcCommonResponse<T> ok(String message) {
        BdcCommonResponse<T> resp = new BdcCommonResponse<>();
        resp.code = SUCCESS_CODE;
        resp.message = message;
        return resp;
    }

    public static <T> BdcCommonResponse<T> ok(T data,String message) {
        BdcCommonResponse<T> resp = new BdcCommonResponse<>();
        resp.code = SUCCESS_CODE;
        resp.message = message;
        resp.data = data;
        return resp;
    }

    public static <T> BdcCommonResponse<T> fail(String code, String message) {
        BdcCommonResponse<T> resp = new BdcCommonResponse();
        resp.code = code;
        resp.message = message;
        return resp;
    }

    public static <T> BdcCommonResponse<T> fail(String message, T data) {
        BdcCommonResponse<T> resp = new BdcCommonResponse();
        resp.code = ERROR_CODE;
        resp.data = data;
        resp.message = message;
        return resp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
