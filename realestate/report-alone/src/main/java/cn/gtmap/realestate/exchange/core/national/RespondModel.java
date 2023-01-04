package cn.gtmap.realestate.exchange.core.national;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Auther xutao
 * @Date 2018-12-21
 * @Description
 */
@XmlRootElement(
        name = "respond"
)
@XmlAccessorType(XmlAccessType.FIELD)
public class RespondModel {
    @XmlElement(
            name = "BizMsgID"
    )
    private String bizMsgID;
    @XmlElement(
            name = "SuccessFlag"
    )
    private Integer successFlag;
    @XmlElement(
            name = "ResponseCode"
    )
    private String responseCode;
    @XmlElement(
            name = "ResponseInfo"
    )
    private String responseInfo;
    @XmlElement(
            name = "CertID"
    )
    private String certID;
    @XmlElement(
            name = "QRCode"
    )
    private String QRCode;
    @XmlElement(
            name = "AdditionalData"
    )
    private String additionalData;
    @XmlElement(
            name = "AdditionalData2"
    )
    private String additionalData2;

    public RespondModel() {
    }

    public String getBizMsgID() {
        return this.bizMsgID;
    }

    public void setBizMsgID(String bizMsgID) {
        this.bizMsgID = bizMsgID;
    }


    public Integer getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(Integer successFlag) {
        this.successFlag = successFlag;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseInfo() {
        return this.responseInfo;
    }

    public void setResponseInfo(String responseInfo) {
        this.responseInfo = responseInfo;
    }

    public String getCertID() {
        return this.certID;
    }

    public void setCertID(String certID) {
        this.certID = certID;
    }

    public String getQRCode() {
        return this.QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public String getAdditionalData() {
        return this.additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }

    public String getAdditionalData2() {
        return this.additionalData2;
    }

    public void setAdditionalData2(String additionalData2) {
        this.additionalData2 = additionalData2;
    }

    @Override
    public String toString() {
        return "RespondModel{" +
                "bizMsgID='" + bizMsgID + '\'' +
                ", successFlag='" + successFlag + '\'' +
                ", responseCode='" + responseCode + '\'' +
                ", responseInfo='" + responseInfo + '\'' +
                ", certID='" + certID + '\'' +
                ", QRCode='" + QRCode + '\'' +
                ", additionalData='" + additionalData + '\'' +
                ", additionalData2='" + additionalData2 + '\'' +
                '}';
    }
}
