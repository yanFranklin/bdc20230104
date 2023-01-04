package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/17
 * @description
 */
@ApiModel(value = "BdcYxYwxxDTO", description = "不动产已选业务信息前台")
public class BdcYxYwxxDTO implements Serializable {
    private static final long serialVersionUID = -8191054359116563518L;
    @ApiModelProperty(value = "不动产受理项目前台")
    private BdcSlYwxxDTO bdcSlYwxxDTO;
    @ApiModelProperty(value = "不动产已选外联证书")
    private List<BdcYxWlzsDTO> bdcYxWlzsDTOList;

    public BdcSlYwxxDTO getBdcSlYwxxDTO() {
        return bdcSlYwxxDTO;
    }

    public void setBdcSlYwxxDTO(BdcSlYwxxDTO bdcSlYwxxDTO) {
        this.bdcSlYwxxDTO = bdcSlYwxxDTO;
    }

    public List<BdcYxWlzsDTO> getBdcYxWlzsDTOList() {
        return bdcYxWlzsDTOList;
    }

    public void setBdcYxWlzsDTOList(List<BdcYxWlzsDTO> bdcYxWlzsDTOList) {
        this.bdcYxWlzsDTOList = bdcYxWlzsDTOList;
    }

    @Override
    public String toString() {
        return "BdcYxYwxxDTO{" +
                "bdcSlYwxxDTO=" + bdcSlYwxxDTO +
                ", bdcYxWlzsDTOList=" + bdcYxWlzsDTOList +
                '}';
    }
}
