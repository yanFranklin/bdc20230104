package cn.gtmap.realestate.common.core.dto.etl;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-25
 * @description ETL 批量上报请求实体
 */
public class EtlBatchNationalAccessRequestDTO {

    private List<EtlNationalAccessRequestDTO> requestDTOList;

    public List<EtlNationalAccessRequestDTO> getRequestDTOList() {
        return requestDTOList;
    }

    public void setRequestDTOList(List<EtlNationalAccessRequestDTO> requestDTOList) {
        this.requestDTOList = requestDTOList;
    }
}
