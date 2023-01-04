package cn.gtmap.realestate.common.core.dto.building;

import java.util.List;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018-11-13
 * @description 修改不动产单元状态的DTO
 */
public class BatchBdcdyhztRequestDTO {

    private List<BdcdyhZtRequestDTO> bdcdyhZtList;

    public List<BdcdyhZtRequestDTO> getBdcdyhZtList() {
        return bdcdyhZtList;
    }

    public void setBdcdyhZtList(List<BdcdyhZtRequestDTO> bdcdyhZtList) {
        this.bdcdyhZtList = bdcdyhZtList;
    }

    @Override
    public String toString() {
        return "BatchBdcdyhztRequestDTO{" +
                "bdcdyhZtList=" + bdcdyhZtList +
                '}';
    }
}
