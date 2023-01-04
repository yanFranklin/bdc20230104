package cn.gtmap.realestate.exchange.core.dto.changzhou.zzcxj;

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.ResponseHead;
import cn.gtmap.realestate.exchange.util.constants.Constants;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022/4/15
 * @description 常州自助查询机，查询登记簿信息返回格式
 */
public class ChangzhouDjbResponseModel<T> {

    /**
     * 响应头
     */
    private ResponseHead head;

    /**
     * 响应数据
     */
    private T data;

    public ChangzhouDjbResponseModel(ResponseHead head, T data) {
        this.head = head;
        this.data = data;
    }

    public ChangzhouDjbResponseModel(){}

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
        return "ChangzhouDjbResponseModel{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }

    public static <T> ChangzhouDjbResponseModel<T> fail(String code, String message, T data) {
        ChangzhouDjbResponseModel<T> resp = new ChangzhouDjbResponseModel<>();
        ResponseHead head = new ResponseHead();
        head.setStatus(code);
        head.setMessage(message);
        resp.setHead(head);
        // 出错时仍然把当前数据返回，以便排障
        resp.data = data;
        return resp;
    }

    public static <T> ChangzhouDjbResponseModel<T> ok(String code, String message, T data) {
        ChangzhouDjbResponseModel<T> resp = new ChangzhouDjbResponseModel<>();
        ResponseHead head = new ResponseHead();
        head.setStatus(code);
        head.setMessage(message);
        resp.setHead(head);
        // 出错时仍然把当前数据返回，以便排障
        resp.data = data;
        return resp;
    }

    public static <T> ChangzhouDjbResponseModel<T> ok(T data) {
        ChangzhouDjbResponseModel<T> resp = new ChangzhouDjbResponseModel<>();
        ResponseHead head = new ResponseHead();
        head.setStatus(Constants.RESPONSE_SUCCESS_CODE);
        head.setMessage(Constants.RESPONSE_SUCCESS_MSG);
        resp.setHead(head);
        // 出错时仍然把当前数据返回，以便排障
        resp.data = data;
        return resp;
    }

}
