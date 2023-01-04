package cn.gtmap.realestate.exchange.core.dto.changzhou.zzdzj;

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.ResponseHead;
import cn.gtmap.realestate.exchange.util.constants.Constants;

public class ChangzhouDzzzResponseModel<T> {

    /**
     * 响应头
     */
    private ResponseHead head;

    /**
     * 响应数据
     */
    private T data;

    public ChangzhouDzzzResponseModel(ResponseHead head, T data) {
        this.head = head;
        this.data = data;
    }

    public ChangzhouDzzzResponseModel(){}

    public ResponseHead getHead() {
        return head;
    }

    public void setHead(ResponseHead head) {
        this.head = head;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DzzzResponseModel{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }

    public static <T> ChangzhouDzzzResponseModel<T> fail(String code, String message, T data) {
        ChangzhouDzzzResponseModel<T> resp = new ChangzhouDzzzResponseModel<>();
        ResponseHead head = new ResponseHead();
        head.setStatus(code);
        head.setMessage(message);
        resp.setHead(head);
        // 出错时仍然把当前数据返回，以便排障
        resp.data = data;
        return resp;
    }

    public static <T> ChangzhouDzzzResponseModel<T> ok(String code, String message, T data) {
        ChangzhouDzzzResponseModel<T> resp = new ChangzhouDzzzResponseModel<>();
        ResponseHead head = new ResponseHead();
        head.setStatus(code);
        head.setMessage(message);
        resp.setHead(head);
        // 出错时仍然把当前数据返回，以便排障
        resp.data = data;
        return resp;
    }

    public static <T> ChangzhouDzzzResponseModel<T> ok(T data) {
        ChangzhouDzzzResponseModel<T> resp = new ChangzhouDzzzResponseModel<>();
        ResponseHead head = new ResponseHead();
        head.setStatus(Constants.RESPONSE_SUCCESS_CODE);
        head.setMessage(Constants.RESPONSE_SUCCESS_MSG);
        resp.setHead(head);
        // 出错时仍然把当前数据返回，以便排障
        resp.data = data;
        return resp;
    }

}
