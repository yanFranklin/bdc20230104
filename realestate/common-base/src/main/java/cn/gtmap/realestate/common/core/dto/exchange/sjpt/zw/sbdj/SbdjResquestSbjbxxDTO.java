package cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdj;

import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdjfk.SbdjfkResquestSbjbxxCallInfoDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.*;

/**
 * 4.3.1	申报登记
 *
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0
 * @date 2020/12/07 15:13
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RECORD")
public class SbdjResquestSbjbxxDTO {

    @XmlElement(name = "CALLINFO")
    @ApiModelProperty(value = "调用方信息")
    private SbdjfkResquestSbjbxxCallInfoDTO callInfoDTO;

    @XmlElement(name = "SERVICEINFO")
    @ApiModelProperty(value = "")
    private SbdjResquestSbjbxxServiceInfoDTO serviceInfoDTO;

    public SbdjfkResquestSbjbxxCallInfoDTO getCallInfoDTO() {
        return callInfoDTO;
    }

    public void setCallInfoDTO(SbdjfkResquestSbjbxxCallInfoDTO callInfoDTO) {
        this.callInfoDTO = callInfoDTO;
    }

    public SbdjResquestSbjbxxServiceInfoDTO getServiceInfoDTO() {
        return serviceInfoDTO;
    }

    public void setServiceInfoDTO(SbdjResquestSbjbxxServiceInfoDTO serviceInfoDTO) {
        this.serviceInfoDTO = serviceInfoDTO;
    }
}