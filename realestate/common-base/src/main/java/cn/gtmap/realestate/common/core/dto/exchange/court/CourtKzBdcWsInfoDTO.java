package cn.gtmap.realestate.common.core.dto.exchange.court;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@XStreamAlias("WSLIST")
public class CourtKzBdcWsInfoDTO {
    @XStreamAsAttribute
    String ErrorMSG;

    @XmlElement(name = "WSLIST")
//    @XStreamAlias("WSINFO")
    @XStreamImplicit(itemFieldName="WSINFO")
    List<CourtKzBdcWsInfo> courtKzBdcWsInfos;

    public String getErrorMSG() {
        return ErrorMSG;
    }

    public void setErrorMSG(String errorMSG) {
        ErrorMSG = errorMSG;
    }

    public List<CourtKzBdcWsInfo> getCourtKzBdcWsInfos() {
        return courtKzBdcWsInfos;
    }

    public void setCourtKzBdcWsInfos(List<CourtKzBdcWsInfo> courtKzBdcWsInfos) {
        this.courtKzBdcWsInfos = courtKzBdcWsInfos;
    }
}
