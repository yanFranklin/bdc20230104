package cn.gtmap.realestate.common.core.dto.exchange.xuancheng.gxcx;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 *
 */
@XmlRootElement(name = "MDEML")
public class GxcxCsyxzmDTO {
    private RequestInfo requestinfo;
    private Body body;

    public void setRequestinfo(RequestInfo requestinfo) {
        this.requestinfo = requestinfo;
    }

    public RequestInfo getRequestinfo() {
        return requestinfo;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    public static class RequestInfo {

        private String requestDate;
        private String requestOrgCode;
        private String requestOrgName;

        public void setRequestDate(String requestDate) {
            this.requestDate = requestDate;
        }

        public String getRequestDate() {
            return requestDate;
        }

        public void setRequestOrgCode(String requestOrgCode) {
            this.requestOrgCode = requestOrgCode;
        }

        public String getRequestOrgCode() {
            return requestOrgCode;
        }

        public void setRequestOrgName(String requestOrgName) {
            this.requestOrgName = requestOrgName;
        }

        public String getRequestOrgName() {
            return requestOrgName;
        }

    }

    public static class Body {

        private Data data;

        public void setData(Data data) {
            this.data = data;
        }

        public Data getData() {
            return data;
        }

    }

    public static class Data {
        @XmlElement(name = "BirthCode")
        private String BirthCode;
        @XmlElement(name = "MomName")
        private String MomName;
        @XmlElement(name = "MomIdCode")
        private String MomIdCode;

        public void setBirthCode(String BirthCode) {
            this.BirthCode = BirthCode;
        }

        public void setMomName(String MomName) {
            this.MomName = MomName;
        }

        public void setMomIdCode(String MomIdCode) {
            this.MomIdCode = MomIdCode;
        }
    }
}
