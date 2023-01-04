package cn.gtmap.realestate.common.core.dto.exchange.court.kz;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 司法控制信息返回
 *
 * @author wangyinghao
 */
@XmlRootElement(name = "Message")
@XStreamAlias("Message")
public class CourtKzDTO {
    @XmlAttribute(name = "ErrorMSG")
    @XStreamAsAttribute()
    String ErrorMSG;

    @XmlElement(name = "Head")
    @XStreamAlias("Head")
    CourtKzHeader courtKzHeader;

    @XmlElement(name = "Data")
    @XStreamAlias("Data")
    CourtKzDataDTO courtKzData;


    public String getErrorMSG() {
        return ErrorMSG;
    }

    public void setErrorMSG(String errorMSG) {
        ErrorMSG = errorMSG;
    }

    public CourtKzHeader getCourtKzHeader() {
        return courtKzHeader;
    }

    public void setCourtKzHeader(CourtKzHeader courtKzHeader) {
        this.courtKzHeader = courtKzHeader;
    }

    public CourtKzDataDTO getCourtKzData() {
        return courtKzData;
    }

    public void setCourtKzData(CourtKzDataDTO courtKzData) {
        this.courtKzData = courtKzData;
    }
}
