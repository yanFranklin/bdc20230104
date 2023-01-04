package cn.gtmap.realestate.exchange.core.dto.changzhou.jyj;

public class ChangZhouJyjCommonResponse<T> {

    private Boolean success;

    private T data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ChangZhouJyjCommonResponse() {
    }

    public static <T> ChangZhouJyjCommonResponse<T> fail(T message) {
        ChangZhouJyjCommonResponse<T> resp = new ChangZhouJyjCommonResponse<>();
        resp.success = false;
        resp.data = message;
        return resp;
    }

    public static <T> ChangZhouJyjCommonResponse<T> ok(T data) {
        ChangZhouJyjCommonResponse<T> resp = new ChangZhouJyjCommonResponse<>();
        resp.success = true;
        resp.data = data;
        return resp;
    }

}
