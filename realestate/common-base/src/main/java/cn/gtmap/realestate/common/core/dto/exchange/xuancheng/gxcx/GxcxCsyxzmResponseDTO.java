package cn.gtmap.realestate.common.core.dto.exchange.xuancheng.gxcx;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 *
 */
@XmlRootElement(name = "MDEML")
@XStreamAlias("MDEML")
public class GxcxCsyxzmResponseDTO {
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
//        @XStreamAlias("data")
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
        private String babyName;
        private String babySexCode;
        private String babySex;
        private String birthArea;
        private String birthTime;
        private String birthCode;
        private String momName;
        private String momIdCode;
        private String dadName;
        private String dadIdCode;

        public String getBabyName() {
            return babyName;
        }

        public void setBabyName(String babyName) {
            this.babyName = babyName;
        }

        public String getBabySexCode() {
            return babySexCode;
        }

        public void setBabySexCode(String babySexCode) {
            this.babySexCode = babySexCode;
        }

        public String getBabySex() {
            return babySex;
        }

        public void setBabySex(String babySex) {
            this.babySex = babySex;
        }

        public String getBirthArea() {
            return birthArea;
        }

        public void setBirthArea(String birthArea) {
            this.birthArea = birthArea;
        }

        public String getBirthTime() {
            return birthTime;
        }

        public void setBirthTime(String birthTime) {
            this.birthTime = birthTime;
        }

        public String getBirthCode() {
            return birthCode;
        }

        public void setBirthCode(String birthCode) {
            this.birthCode = birthCode;
        }

        public String getMomName() {
            return momName;
        }

        public void setMomName(String momName) {
            this.momName = momName;
        }

        public String getMomIdCode() {
            return momIdCode;
        }

        public void setMomIdCode(String momIdCode) {
            this.momIdCode = momIdCode;
        }

        public String getDadName() {
            return dadName;
        }

        public void setDadName(String dadName) {
            this.dadName = dadName;
        }

        public String getDadIdCode() {
            return dadIdCode;
        }

        public void setDadIdCode(String dadIdCode) {
            this.dadIdCode = dadIdCode;
        }
    }
}
