package cn.gtmap.realestate.common.core.dto.exchange.xuancheng.gxcx;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 *
 */
@XmlRootElement(name = "MDEML")
public class GxcxSwyxzmDTO {
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

        private String GMSFZH;
        private String XM;

        public String getGMSFZH() {
            return GMSFZH;
        }

        public void setGMSFZH(String GMSFZH) {
            this.GMSFZH = GMSFZH;
        }

        public String getXM() {
            return XM;
        }

        public void setXM(String XM) {
            this.XM = XM;
        }
    }
}
