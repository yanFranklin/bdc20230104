package cn.gtmap.realestate.exchange.core.dto.nantong.jsyh;
/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2018/8/6
 * @description 建行查询应答xml对象model
 */

import javax.xml.bind.annotation.*;

@XmlType(name = "responseMessageModel", propOrder = {"responseHeadModel", "responseBodyModel"})
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "message")
public class ResponseMessageModel {

    private ResponseHeadModel responseHeadModel;

    private ResponseBodyModel responseBodyModel;

    @XmlElement(name = "head")
    public ResponseHeadModel getResponseHeadModel() {
        return responseHeadModel;
    }

    public void setResponseHeadModel(ResponseHeadModel responseHeadModel) {
        this.responseHeadModel = responseHeadModel;
    }

    @XmlElement(name = "body")
    public ResponseBodyModel getResponseBodyModel() {
        return responseBodyModel;
    }

    public void setResponseBodyModel(ResponseBodyModel responseBodyModel) {
        this.responseBodyModel = responseBodyModel;
    }

    @Override
    public String toString() {
        return "ResponseMessageModel{" +
                "responseHeadModel=" + responseHeadModel +
                ", responseBodyModel=" + responseBodyModel +
                '}';
    }
}
