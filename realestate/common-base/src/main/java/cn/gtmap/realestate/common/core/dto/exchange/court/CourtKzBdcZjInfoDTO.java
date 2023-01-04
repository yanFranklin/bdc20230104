package cn.gtmap.realestate.common.core.dto.exchange.court;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@XStreamAlias("ZJLIST")
public class CourtKzBdcZjInfoDTO {
    @XStreamAsAttribute
    String ErrorMSG;

    @XmlElement(name = "ZJLIST")
    @XStreamImplicit(itemFieldName="ZJINFO")
    List<CourtKzBdcZjInfo> courtKzBdcZjInfos;

    public List<CourtKzBdcZjInfo> getCourtKzBdcZjInfos() {
        return courtKzBdcZjInfos;
    }

    public void setCourtKzBdcZjInfos(List<CourtKzBdcZjInfo> courtKzBdcZjInfos) {
        this.courtKzBdcZjInfos = courtKzBdcZjInfos;
    }
}
