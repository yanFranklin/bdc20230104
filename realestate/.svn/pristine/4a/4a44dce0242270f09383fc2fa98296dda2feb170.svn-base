package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/12/7
 * @description 外联带抵押土地证数据处理结果
 */
public class WltdzWithdyResultDTO {

    @ApiModelProperty(value = "需要插入的项目历史关系集合")
    private List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList;

    @ApiModelProperty(value = "需要更新的受理项目集合")
    private List<BdcSlXmDO> bdcSlXmDOList;

    @ApiModelProperty(value = "需要插入的抵押业务信息集合")
    private List<BdcYwxxDTO> bdcYwxxDTOList;

    @ApiModelProperty(value = "需要插入的抵押业务信息集合")
    private List<BdcSlXmDO> dySlXmList;

    public List<BdcSlXmLsgxDO> getBdcSlXmLsgxDOList() {
        return bdcSlXmLsgxDOList;
    }

    public void setBdcSlXmLsgxDOList(List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList) {
        this.bdcSlXmLsgxDOList = bdcSlXmLsgxDOList;
    }

    public List<BdcSlXmDO> getBdcSlXmDOList() {
        return bdcSlXmDOList;
    }

    public void setBdcSlXmDOList(List<BdcSlXmDO> bdcSlXmDOList) {
        this.bdcSlXmDOList = bdcSlXmDOList;
    }

    public List<BdcYwxxDTO> getBdcYwxxDTOList() {
        return bdcYwxxDTOList;
    }

    public void setBdcYwxxDTOList(List<BdcYwxxDTO> bdcYwxxDTOList) {
        this.bdcYwxxDTOList = bdcYwxxDTOList;
    }

    public List<BdcSlXmDO> getDySlXmList() {
        return dySlXmList;
    }

    public void setDySlXmList(List<BdcSlXmDO> dySlXmList) {
        this.dySlXmList = dySlXmList;
    }

    @Override
    public String toString() {
        return "WltdzWithdyResultDTO{" +
                "bdcSlXmLsgxDOList=" + bdcSlXmLsgxDOList +
                ", bdcSlXmDOList=" + bdcSlXmDOList +
                ", bdcYwxxDTOList=" + bdcYwxxDTOList +
                ", dySlXmList=" + dySlXmList +
                '}';
    }
}
