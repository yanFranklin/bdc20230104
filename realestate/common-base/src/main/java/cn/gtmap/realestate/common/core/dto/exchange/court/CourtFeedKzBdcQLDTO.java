package cn.gtmap.realestate.common.core.dto.exchange.court;

import cn.gtmap.realestate.common.core.dto.exchange.court.kz.CourtKzHeader;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 司法控制信息返回
 *
 * @author wangyinghao
 */
@XmlRootElement(name = "Message")
public class CourtFeedKzBdcQLDTO {
    @XmlElement(name = "Head")
    CourtKzHeader courtKzHeader;

    @XmlElement(name = "Data")
    CourtKzKzxxIData courtKzKzxxIData;

    public void setCourtKzHeader(CourtKzHeader courtKzHeader) {
        this.courtKzHeader = courtKzHeader;
    }

    public void setCourtKzKzxxIData(CourtKzKzxxIData courtKzKzxxIData) {
        this.courtKzKzxxIData = courtKzKzxxIData;
    }
}
