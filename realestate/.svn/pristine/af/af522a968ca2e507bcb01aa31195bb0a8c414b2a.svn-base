package cn.gtmap.realestate.certificate.core.model;


import com.alibaba.fastjson.annotation.JSONType;

/**
 * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
 * @version 1.0 2019/1/21
 * @description 电子证照检索 响应对象模型
 */
@JSONType(orders={"head","data"})
public class DzzzResponseModel<T> {

    /**
    * 响应头
    */
    private ResponseHead head;

    /**
    * 响应数据
    */
    private T data;

    public DzzzResponseModel(ResponseHead head, T data) {
        this.head = head;
        this.data = data;
    }

    public DzzzResponseModel(){}

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
}
