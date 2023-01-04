package cn.gtmap.realestate.common.core.dto.exchange.court;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 司法控制信息返回
 *
 * @author wangyinghao
 */
//@XmlRootElement(name = "HZXX")
public class CourtFeedKzZhzDATA {
    @XmlElement(name = "HZXXLIST")
    List<CourtFeedKzZhzHz> courtFeedKzZhzHzs;

    public List<CourtFeedKzZhzHz> getCourtFeedKzZhzHzs() {
        return courtFeedKzZhzHzs;
    }

    public void setCourtFeedKzZhzHzs(List<CourtFeedKzZhzHz> courtFeedKzZhzHzs) {
        this.courtFeedKzZhzHzs = courtFeedKzZhzHzs;
    }
}
