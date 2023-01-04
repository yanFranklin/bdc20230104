package cn.gtmap.realestate.common.core.dto.exchange.court.cx;

import cn.gtmap.realestate.common.core.dto.exchange.court.kz.CourtKzHeader;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 司法控制信息返回
 *
 * @author wangyinghao
 */
@XmlRootElement(name = "Message")
public class CourtFeedCxBdcQLDTO {
    @XmlElement(name = "Head")
    private CourtKzHeader courtKzHeader;

    @XmlElement(name = "Data")
    CourtFeedCxDATA courtFeedCxDATA;

    public void setCourtKzHeader(CourtKzHeader courtKzHeader) {
        this.courtKzHeader = courtKzHeader;
    }

    public void setCourtFeedCxDATA(CourtFeedCxDATA courtFeedCxDATA) {
        this.courtFeedCxDATA = courtFeedCxDATA;
    }
}
