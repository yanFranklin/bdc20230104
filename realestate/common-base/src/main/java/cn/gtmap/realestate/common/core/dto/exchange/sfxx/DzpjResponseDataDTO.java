package cn.gtmap.realestate.common.core.dto.exchange.sfxx;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 2022/05/25
 * @description 电子票据data
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DzpjResponseDataDTO {

    // -业务唯一码
    @XmlElement(name = "BUSINESS_ID")
    private String businessId;

    // 缴款码
    @XmlElement(name = "BUSINESSNUMBER")
    private String businessNumber;

    // 电子票据URL
    @XmlElement(name = "URL")
    private String url;

    // 电子票据base64
    @XmlElement(name = "PdfFileData")
    private String pdfFileData;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPdfFileData() {
        return pdfFileData;
    }

    public void setPdfFileData(String pdfFileData) {
        this.pdfFileData = pdfFileData;
    }

    @Override
    public String toString() {
        return "DzpjResponseDataDTO{" +
                "businessId='" + businessId + '\'' +
                ", businessNumber='" + businessNumber + '\'' +
                ", url='" + url + '\'' +
                ", pdfFileData='" + pdfFileData + '\'' +
                '}';
    }
}
