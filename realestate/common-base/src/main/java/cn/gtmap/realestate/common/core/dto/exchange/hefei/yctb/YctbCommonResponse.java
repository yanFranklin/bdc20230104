package cn.gtmap.realestate.common.core.dto.exchange.hefei.yctb;

import java.io.Serializable;

public class YctbCommonResponse<E> implements Serializable {

    /**
     * 错误编码
     */
    public static final int ERROR_CODE = 400;
    public static final String ERROR_MSG = "失败";
    /**
     * 成功编码
     */
    public static final int SUCCESS_CODE = 200;
    public static final String SUCCESS_MSG = "成功";

    private static final long serialVersionUID = -2299076233504784016L;

    private Integer code;

    private String msg;

    private E data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public static <T> YctbCommonResponse<T> ok(T data) {
        YctbCommonResponse<T> resp = new YctbCommonResponse<>();
        resp.data = data;
        resp.code = SUCCESS_CODE;
        resp.msg = SUCCESS_MSG;
        return resp;
    }

    public static <T> YctbCommonResponse<T> ok() {
        YctbCommonResponse<T> resp = new YctbCommonResponse<>();
        resp.code = SUCCESS_CODE;
        resp.msg = SUCCESS_MSG;
        return resp;
    }

    public static <T> YctbCommonResponse<T> fail() {
        YctbCommonResponse<T> resp = new YctbCommonResponse<>();
        resp.code = ERROR_CODE;
        resp.msg = ERROR_MSG;
        return resp;
    }

    public static <T> YctbCommonResponse<T> fail(int code,String msg) {
        YctbCommonResponse<T> resp = new YctbCommonResponse<>();
        resp.code = code;
        resp.msg = msg;
        return resp;
    }

    public static <T> YctbCommonResponse<T> fail(String msg) {
        YctbCommonResponse<T> resp = new YctbCommonResponse<>();
        resp.code = ERROR_CODE;
        resp.msg = msg;
        return resp;
    }

    @Override
    public String toString() {
        return "YctbCommonResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
