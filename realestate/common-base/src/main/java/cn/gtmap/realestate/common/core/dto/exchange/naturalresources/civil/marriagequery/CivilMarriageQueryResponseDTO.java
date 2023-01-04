package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.civil.marriagequery;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/3 10:03
 * @description 2.3民政部-婚姻登记信息查询服务接口
 */
public class CivilMarriageQueryResponseDTO {

    private Integer count;

    private List<CivilMarriageQueryDataDTO> dataDTOList;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<CivilMarriageQueryDataDTO> getDataDTOList() {
        return dataDTOList;
    }

    @JSONField(name="rows")
    public void setDataDTOList(List<CivilMarriageQueryDataDTO> dataDTOList) {
        this.dataDTOList = dataDTOList;
    }

    @Override
    public String toString() {
        return "CivilMarriageQueryResponseDTO{" +
                "count=" + count +
                ", dataDTOList=" + dataDTOList +
                '}';
    }
}
