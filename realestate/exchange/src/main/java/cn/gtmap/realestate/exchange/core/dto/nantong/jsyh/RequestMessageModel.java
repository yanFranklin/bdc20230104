package cn.gtmap.realestate.exchange.core.dto.nantong.jsyh;
/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2018/8/6
 * @description 建行查询请求xml对象model
 */

import javax.xml.bind.annotation.*;

@XmlType(name = "requestMessageModel", propOrder = {"requestHeadModel", "requestBodyModel"})
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "message")
public class RequestMessageModel {

    private RequestHeadModel requestHeadModel;

    private RequestBodyModel requestBodyModel;

    @XmlElement(name = "head")
    public RequestHeadModel getRequestHeadModel() {
        return requestHeadModel;
    }

    public void setRequestHeadModel(RequestHeadModel requestHeadModel) {
        this.requestHeadModel = requestHeadModel;
    }

    @XmlElement(name = "body")
    public RequestBodyModel getRequestBodyModel() {
        return requestBodyModel;
    }

    public void setRequestBodyModel(RequestBodyModel requestBodyModel) {
        this.requestBodyModel = requestBodyModel;
    }

    @Override
    public String toString() {
        return "RequestMessageModel{" +
                "requestHeadModel=" + requestHeadModel +
                ", requestBodyModel=" + requestBodyModel +
                '}';
    }
}
