package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.zgf.decisionresponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/4 17:42
 * @description
 */
@XmlRootElement(name = "RESOURCE")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupremeCourtDecisionResponseResponseDTO {

    @XmlElement(name = "BRIEF")
    private SupremeCourtDecisionResponseBriefDTO briefDTO;

    @XmlElement(name = "data")
    private SupremeCourtDecisionResponseDataDTO dataDTO;

    public SupremeCourtDecisionResponseBriefDTO getBriefDTO() {
        return briefDTO;
    }

    public void setBriefDTO(SupremeCourtDecisionResponseBriefDTO briefDTO) {
        this.briefDTO = briefDTO;
    }

    public SupremeCourtDecisionResponseDataDTO getDataDTO() {
        return dataDTO;
    }

    public void setDataDTO(SupremeCourtDecisionResponseDataDTO dataDTO) {
        this.dataDTO = dataDTO;
    }

    @Override
    public String toString() {
        return "SupremeCourtDecisionResponseResponseDTO{" +
                "briefDTO=" + briefDTO +
                ", dataDTO=" + dataDTO +
                '}';
    }
}
