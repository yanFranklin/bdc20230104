package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.scopsr.organquery;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/3 14:50
 * @description 2.6中编办-事业单位登记信息（含机关、群团信息）查询接口
 */
public class ScopsrOrganQueryResponseDTO {

    private List<ScopsrOrganQueryDataDTO> dataDTOS;

    public List<ScopsrOrganQueryDataDTO> getDataDTOS() {
        return dataDTOS;
    }

    @JSONField(name = "data")
    public void setDataDTOS(List<ScopsrOrganQueryDataDTO> dataDTOS) {
        this.dataDTOS = dataDTOS;
    }

    @Override
    public String toString() {
        return "ScopsrOrganQueryResponseDTO{" +
                "dataDTOS=" + dataDTOS +
                '}';
    }
}
