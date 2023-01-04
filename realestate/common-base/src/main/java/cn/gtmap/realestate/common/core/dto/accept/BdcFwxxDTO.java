package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwtcxxDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/7/12
 * @description 不动产房屋信息
 */
@ApiModel(value = "BdcFwxxDTO", description = "不动产房屋信息")
public class BdcFwxxDTO {

    @ApiModelProperty(value = "转入方房屋信息")
    private List<BdcSlFwtcxxDO> bdcZrfZfxxDTOList;

    @ApiModelProperty(value = "转出方房屋信息")
    private List<BdcSlFwtcxxDO> bdcZcfZfxxDTOList;

    public List<BdcSlFwtcxxDO> getBdcZrfZfxxDTOList() {
        return bdcZrfZfxxDTOList;
    }

    public void setBdcZrfZfxxDTOList(List<BdcSlFwtcxxDO> bdcZrfZfxxDTOList) {
        this.bdcZrfZfxxDTOList = bdcZrfZfxxDTOList;
    }

    public List<BdcSlFwtcxxDO> getBdcZcfZfxxDTOList() {
        return bdcZcfZfxxDTOList;
    }

    public void setBdcZcfZfxxDTOList(List<BdcSlFwtcxxDO> bdcZcfZfxxDTOList) {
        this.bdcZcfZfxxDTOList = bdcZcfZfxxDTOList;
    }
}
