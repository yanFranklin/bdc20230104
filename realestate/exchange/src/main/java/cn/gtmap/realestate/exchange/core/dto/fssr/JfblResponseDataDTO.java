package cn.gtmap.realestate.exchange.core.dto.fssr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 1.0  2022/5/24 9:59
 * @description 缴费办理接口响应
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JfblResponseDataDTO {

    @XmlElement(name = "BUSINESS_ID")
    private String BUSINESS_ID;

    @XmlElement(name = "BUSINESSNUMBER")
    private String BUSINESSNUMBER;

    @XmlElement(name = "URL")
    private String URL;

    public String getBUSINESS_ID() {
        return BUSINESS_ID;
    }

    public void setBUSINESS_ID(String BUSINESS_ID) {
        this.BUSINESS_ID = BUSINESS_ID;
    }

    public String getBUSINESSNUMBER() {
        return BUSINESSNUMBER;
    }

    public void setBUSINESSNUMBER(String BUSINESSNUMBER) {
        this.BUSINESSNUMBER = BUSINESSNUMBER;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public String toString() {
        return "JfblResponseDataDTO{" +
                "BUSINESS_ID='" + BUSINESS_ID + '\'' +
                ", BUSINESSNUMBER='" + BUSINESSNUMBER + '\'' +
                ", URL='" + URL + '\'' +
                '}';
    }
}
