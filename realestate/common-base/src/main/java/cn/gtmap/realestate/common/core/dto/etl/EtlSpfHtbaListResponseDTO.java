package cn.gtmap.realestate.common.core.dto.etl;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;


/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 2020/11/03,1.0
 * @description
 */
public class EtlSpfHtbaListResponseDTO {

    private List<EtlSpfHtbaResponseDTo> etlSpfHtbaResponseDTos;

    private Long total;

    public List<EtlSpfHtbaResponseDTo> getEtlSpfHtbaResponseDTos() {
        return etlSpfHtbaResponseDTos;
    }

    public void setEtlSpfHtbaResponseDTos(List<EtlSpfHtbaResponseDTo> etlSpfHtbaResponseDTos) {
        this.etlSpfHtbaResponseDTos = etlSpfHtbaResponseDTos;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
