package cn.gtmap.interchange.core.dto;

public class CommonResponse<T> {

    /**
     * 错误编码
     */
    public static final String ERROR_CODE = "9999";

    /**
     * 没有数据错误编码
     */
    public static final String ERROR_CODE_WITHOUT_DATA = "9998";

    /**
     * 是否业务成功
     */
    private boolean success;

    /**
     * 业务数据
     */
    private T data;

    /**
     * 业务失败详情
     */
    private FailInfo fail;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public FailInfo getFail() {
        return fail;
    }

    public static <T> CommonResponse<T> fail(String code, String message, T data) {
        CommonResponse<T> resp = new CommonResponse<>();
        resp.success = false;
        resp.fail = FailInfo.builder()
                .code(code)
                .message(message)
                .build();
        // 出错时仍然把当前数据返回，以便排障
        resp.data = data;
        return resp;
    }

    public static <T> CommonResponse<T> fail(String message) {
        CommonResponse<T> resp = new CommonResponse<>();
        resp.success = false;
        resp.fail = FailInfo.builder()
                .code(ERROR_CODE)
                .message(message)
                .build();
        return resp;
    }

    public static <T> CommonResponse<T> fail(T data) {
        CommonResponse<T> resp = new CommonResponse<>();
        resp.success = false;
        resp.data = data;
        resp.fail = FailInfo.builder()
                .code(ERROR_CODE)
                .message("error")
                .build();
        return resp;
    }

    public static <T> CommonResponse<T> ok(T data) {
        CommonResponse<T> resp = new CommonResponse<>();
        resp.success = true;
        resp.data = data;
        return resp;
    }

    public static <T> CommonResponse<T> ok() {
        CommonResponse<T> resp = new CommonResponse<>();
        resp.success = true;
        return resp;
    }

    public static <T> CommonResponse<T> fail(String code, String message) {
        CommonResponse resp = new CommonResponse();
        resp.success = false;
        resp.fail = FailInfo.builder()
                .code(code)
                .message(message)
                .build();
        return resp;
    }

    public static <T> CommonResponse<T> fail(FailInfo failInfo) {
        CommonResponse response = new CommonResponse();
        response.success = false;
        response.fail = failInfo;
        return response;
    }

}
