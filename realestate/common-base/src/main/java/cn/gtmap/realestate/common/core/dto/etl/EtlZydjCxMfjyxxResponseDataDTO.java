package cn.gtmap.realestate.common.core.dto.etl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/23 15:44
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class EtlZydjCxMfjyxxResponseDataDTO {

    @XmlElement(name = "fcxx")
    private EtlZydjCxMfjyxxResponseDataFcxxDTO fcxxDTO;

    @XmlElement(name = "qlrxx")
    private List<EtlZydjCxMfjyxxResponseDataQlrxxDTO>  qlrxxDTOS;

    @XmlElement(name = "xzxx")
    private EtlZydjCxMfjyxxResponseDataXzxxDTO xzxxDTO;

    public EtlZydjCxMfjyxxResponseDataFcxxDTO getFcxxDTO() {
        return fcxxDTO;
    }

    public void setFcxxDTO(EtlZydjCxMfjyxxResponseDataFcxxDTO fcxxDTO) {
        this.fcxxDTO = fcxxDTO;
    }

    public List<EtlZydjCxMfjyxxResponseDataQlrxxDTO> getQlrxxDTOS() {
        return qlrxxDTOS;
    }

    public void setQlrxxDTOS(List<EtlZydjCxMfjyxxResponseDataQlrxxDTO> qlrxxDTOS) {
        this.qlrxxDTOS = qlrxxDTOS;
    }

    public EtlZydjCxMfjyxxResponseDataXzxxDTO getXzxxDTO() {
        return xzxxDTO;
    }

    public void setXzxxDTO(EtlZydjCxMfjyxxResponseDataXzxxDTO xzxxDTO) {
        this.xzxxDTO = xzxxDTO;
    }
}
