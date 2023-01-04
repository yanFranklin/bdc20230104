package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.zgf.decisionresponse;

import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/4 17:29
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SupremeCourtDecisionResponseAjDTO {

    @ApiModelProperty(value = "实体码")
    @XmlElement(name = "c_stm")
    private String stm;

    @ApiModelProperty(value = "案号")
    @XmlElement(name = "c_ah")
    private String ah;

    @ApiModelProperty(value = "格式化案号")
    @XmlElement(name = "c_gshah")
    private String gshah;

    @ApiModelProperty(value = "当事人信息")
    @XmlElement(name = "t_aj_dsr")
    private List<SupremeCourtDecisionResponseAjDsrDTO> ajDsrDTOList;

    @ApiModelProperty(value = "文书内容")
    @XmlElement(name = "t_ws")
    private  SupremeCourtDecisionResponseWsDTO wsDTO;


    public String getStm() {
        return stm;
    }

    public void setStm(String stm) {
        this.stm = stm;
    }

    public String getAh() {
        return ah;
    }

    public void setAh(String ah) {
        this.ah = ah;
    }

    public String getGshah() {
        return gshah;
    }

    public void setGshah(String gshah) {
        this.gshah = gshah;
    }

    public List<SupremeCourtDecisionResponseAjDsrDTO> getAjDsrDTOList() {
        return ajDsrDTOList;
    }

    public void setAjDsrDTOList(List<SupremeCourtDecisionResponseAjDsrDTO> ajDsrDTOList) {
        this.ajDsrDTOList = ajDsrDTOList;
    }

    public SupremeCourtDecisionResponseWsDTO getWsDTO() {
        return wsDTO;
    }

    public void setWsDTO(SupremeCourtDecisionResponseWsDTO wsDTO) {
        this.wsDTO = wsDTO;
    }

    @Override
    public String toString() {
        return "SupremeCourtDecisionResponseAjDTO{" +
                "stm='" + stm + '\'' +
                ", ah='" + ah + '\'' +
                ", gshah='" + gshah + '\'' +
                ", ajDsrDTOList=" + ajDsrDTOList +
                ", wsDTO=" + wsDTO +
                '}';
    }
}
