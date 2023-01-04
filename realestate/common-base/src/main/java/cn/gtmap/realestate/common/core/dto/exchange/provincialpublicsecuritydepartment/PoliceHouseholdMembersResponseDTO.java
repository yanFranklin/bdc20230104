package cn.gtmap.realestate.common.core.dto.exchange.provincialpublicsecuritydepartment;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/17 10:52
 * @description 4.3省公安厅-居民户成员信息在线查询接口
 */
public class PoliceHouseholdMembersResponseDTO {

    private String total;

    private List<PoliceHouseholdMembersResponseDataDTO> responseDataDTOList;

    public String getTotal() {
        return total;
    }

    @JSONField(name="total")
    public void setTotal(String total) {
        this.total = total;
    }

    public List<PoliceHouseholdMembersResponseDataDTO> getResponseDataDTOList() {
        return responseDataDTOList;
    }

    @JSONField(name="results")
    public void setResponseDataDTOList(List<PoliceHouseholdMembersResponseDataDTO> responseDataDTOList) {
        this.responseDataDTOList = responseDataDTOList;
    }

    @Override
    public String toString() {
        return "PoliceHouseholdMembersResponseDTO{" +
                "total='" + total + '\'' +
                ", responseDataDTOList=" + responseDataDTOList +
                '}';
    }
}
