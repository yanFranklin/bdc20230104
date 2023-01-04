package cn.gtmap.realestate.exchange.core.dto.accessLog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Auther lst
 * @Date 2018-12-21
 * @Description
 */
@XmlRootElement(
        name = "RespondLog"
)
@XmlAccessorType(XmlAccessType.FIELD)
public class AccessRespondModel {

    @XmlElement(
            name = "responseLogInfo"
    )
    private String responseLogInfo;
    @XmlElement(
            name = "responseLogCode"
    )
    private String responseLogCode;

    public String getResponseLogInfo() {
        return responseLogInfo;
    }

    public void setResponseLogInfo(String responseLogInfo) {
        this.responseLogInfo = responseLogInfo;
    }

    public String getResponseLogCode() {
        return responseLogCode;
    }

    public void setResponseLogCode(String responseLogCode) {
        this.responseLogCode = responseLogCode;
    }

    @Override
    public String toString() {
        return "AccessRespondModel{" +
                "responseLogInfo='" + responseLogInfo + '\'' +
                ", responseLogCode='" + responseLogCode + '\'' +
                '}';
    }
}
