package cn.gtmap.realestate.common.core.dto.accept;


import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2021-01-28
 * @description 房屋套次信息比对结果
 */
public class CompareFwtcxxResultDTO {

   private List<CompareFwtcxxResultDataDTO> resultDataDTOList;

    public List<CompareFwtcxxResultDataDTO> getResultDataDTOList() {
        return resultDataDTOList;
    }

    public void setResultDataDTOList(List<CompareFwtcxxResultDataDTO> resultDataDTOList) {
        this.resultDataDTOList = resultDataDTOList;
    }
}
