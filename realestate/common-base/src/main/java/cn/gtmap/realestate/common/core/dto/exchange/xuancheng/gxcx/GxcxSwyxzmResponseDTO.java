package cn.gtmap.realestate.common.core.dto.exchange.xuancheng.gxcx;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *
 */
@XmlRootElement(name = "MDEML")
@XStreamAlias("MDEML")
public class GxcxSwyxzmResponseDTO {
    @XmlAttribute(name = "resultinfo")
    @XStreamAlias("resultinfo")
    private ResultInfo resultinfo;

    @XmlAttribute(name = "body")
    @XStreamAlias("body")
    private Body body;

    public void setBody(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    public ResultInfo getResultinfo() {
        return resultinfo;
    }

    public void setResultinfo(ResultInfo resultinfo) {
        this.resultinfo = resultinfo;
    }

    public static class ResultInfo {
        private String success;
        private String message;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class Body {
        @XmlElement(name = "data")
        @XStreamImplicit(itemFieldName="data")
        private List<Data> data;

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }
    }

    public static class Data {
        private String GMSFHM;
        private String XM;
        private String XBDM;
        private String MZDM;
        private String SWYY;

        public String getGMSFHM() {
            return GMSFHM;
        }

        public void setGMSFHM(String GMSFHM) {
            this.GMSFHM = GMSFHM;
        }

        public String getXM() {
            return XM;
        }

        public void setXM(String XM) {
            this.XM = XM;
        }

        public String getXBDM() {
            return XBDM;
        }

        public void setXBDM(String XBDM) {
            this.XBDM = XBDM;
        }

        public String getMZDM() {
            return MZDM;
        }

        public void setMZDM(String MZDM) {
            this.MZDM = MZDM;
        }

        public String getSWYY() {
            return SWYY;
        }

        public void setSWYY(String SWYY) {
            this.SWYY = SWYY;
        }
    }
}
