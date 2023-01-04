package cn.gtmap.realestate.common.core.dto.exchange.court.cx;

import cn.gtmap.realestate.common.core.dto.exchange.court.kz.CourtKzHeader;
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
public class CourtCxDTO {
    @XmlAttribute(name = "ErrorMSG")
    @XStreamAsAttribute()
    String ErrorMSG;

    @XmlElement(name = "Head")
    @XStreamAlias("Head")
    CourtKzHeader courtKzHeader;

    @XmlElement(name = "Data")
    @XStreamAlias("Data")
    CourtCxDataDTO courtCxData;

    public CourtKzHeader getCourtKzHeader() {
        return courtKzHeader;
    }

    public void setCourtKzHeader(CourtKzHeader courtKzHeader) {
        this.courtKzHeader = courtKzHeader;
    }

    public String getErrorMSG() {
        return ErrorMSG;
    }

    public void setErrorMSG(String errorMSG) {
        ErrorMSG = errorMSG;
    }

    public CourtCxDataDTO getCourtCxData() {
        return courtCxData;
    }

    public void setCourtCxData(CourtCxDataDTO courtCxData) {
        this.courtCxData = courtCxData;
    }
}
