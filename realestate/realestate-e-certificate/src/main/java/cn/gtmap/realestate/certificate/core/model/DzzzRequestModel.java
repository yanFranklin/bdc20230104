package cn.gtmap.realestate.certificate.core.model;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/28
 */
public class DzzzRequestModel<T> {
    /**
     * 请求头
     */
    protected RequestHead head;

    /**
     * 请求数据
     */
    protected T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public RequestHead getHead() {
        return head;
    }

    public void setHead(RequestHead head) {
        this.head = head;
    }
}
