package cn.gtmap.realestate.exchange.core.dto.nantong.jsyh;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2018/8/6
 * @description
 */
@XmlType(name = "responseHeadModel", propOrder = {"lhResponseStatus", "lhResponseCode", "lhResponseMsg"})
@XmlRootElement(name = "head")
public class ResponseHeadModel {
    private String lhResponseStatus;//交易状态
    private String lhResponseCode;//返回码
    private String lhResponseMsg;//返回信息

    @XmlElement(name = "LH_RESPONSE_STATUS",required = true,nillable = true)
    public String getLhResponseStatus() {
        return lhResponseStatus;
    }

    public void setLhResponseStatus(String lhResponseStatus) {
        this.lhResponseStatus = lhResponseStatus;
    }

    @XmlElement(name = "LH_RESPONSE_CODE",required = true,nillable = true)
    public String getLhResponseCode() {
        return lhResponseCode;
    }

    public void setLhResponseCode(String lhResponseCode) {
        this.lhResponseCode = lhResponseCode;
    }

    @XmlElement(name = "LH_RESPONSE_MSG",required = true,nillable = true)
    public String getLhResponseMsg() {
        return lhResponseMsg;
    }

    public void setLhResponseMsg(String lhResponseMsg) {
        this.lhResponseMsg = lhResponseMsg;
    }

    @Override
    public String toString() {
        return "ResponseHeadModel{" +
                "lhResponseStatus='" + lhResponseStatus + '\'' +
                ", lhResponseCode='" + lhResponseCode + '\'' +
                ", lhResponseMsg='" + lhResponseMsg + '\'' +
                '}';
    }
}
