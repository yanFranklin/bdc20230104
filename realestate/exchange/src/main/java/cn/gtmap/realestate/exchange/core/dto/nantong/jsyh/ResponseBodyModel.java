package cn.gtmap.realestate.exchange.core.dto.nantong.jsyh;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2018/8/6
 * @description
 */
@XmlRootElement(name = "body")
public class ResponseBodyModel {
    private List<ResponseGroupModel> responseGroupModelList;

    @XmlElement(name = "GROUP",required = true,nillable = true)
    public List<ResponseGroupModel> getResponseGroupModelList() {
        return responseGroupModelList;
    }

    public void setResponseGroupModelList(List<ResponseGroupModel> responseGroupModelList) {
        this.responseGroupModelList = responseGroupModelList;
    }

    @Override
    public String toString() {
        return "ResponseBodyModel{" +
                "responseGroupModelList=" + responseGroupModelList +
                '}';
    }
}
