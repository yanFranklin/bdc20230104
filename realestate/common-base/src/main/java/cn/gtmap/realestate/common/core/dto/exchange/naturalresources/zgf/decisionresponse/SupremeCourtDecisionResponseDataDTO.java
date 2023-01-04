package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.zgf.decisionresponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/5 14:55
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SupremeCourtDecisionResponseDataDTO {

    @XmlElement(name = "t_aj")
    private List<SupremeCourtDecisionResponseAjDTO> ajDTOList;

    public List<SupremeCourtDecisionResponseAjDTO> getAjDTOList() {
        return ajDTOList;
    }

    public void setAjDTOList(List<SupremeCourtDecisionResponseAjDTO> ajDTOList) {
        this.ajDTOList = ajDTOList;
    }

    @Override
    public String toString() {
        return "SupremeCourtDecisionResponseDataDTO{" +
                "ajDTOList=" + ajDTOList +
                '}';
    }
}
