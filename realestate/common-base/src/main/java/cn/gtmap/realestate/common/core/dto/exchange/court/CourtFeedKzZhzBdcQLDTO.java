package cn.gtmap.realestate.common.core.dto.exchange.court;

import cn.gtmap.realestate.common.core.dto.exchange.court.kz.CourtKzHeader;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 司法控制信息返回
 *
 * @author wangyinghao
 */
@XmlRootElement
public class CourtFeedKzZhzBdcQLDTO {
    @XmlElement(name = "Head")
    CourtKzHeader courtKzHeader;
    @XmlElement(name = "Data")
    CourtFeedKzZhzDATA courtFeedKzZhzBdcQLDATA;

    public CourtKzHeader getCourtKzHeader() {
        return courtKzHeader;
    }

    public void setCourtKzHeader(CourtKzHeader courtKzHeader) {
        this.courtKzHeader = courtKzHeader;
    }

    public CourtFeedKzZhzDATA getCourtFeedKzZhzBdcQLDATA() {
        return courtFeedKzZhzBdcQLDATA;
    }

    public void setCourtFeedKzZhzBdcQLDATA(CourtFeedKzZhzDATA courtFeedKzZhzBdcQLDATA) {
        this.courtFeedKzZhzBdcQLDATA = courtFeedKzZhzBdcQLDATA;
    }
}
