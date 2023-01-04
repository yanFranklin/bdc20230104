package cn.gtmap.realestate.common.core.vo.accept.ui;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwtcxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.YcslYmxxDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/4/19
 * @description 不动产家庭成员查询页面信息VO
 */
@ApiModel(value = "BdcJtcyCxYmxxVO", description = "不动产受理项目信息VO")
public class BdcJtcyCxYmxxVO extends YcslYmxxDTO {

    @ApiModelProperty(value = "不动产受理基本信息")
    private BdcSlJbxxDO bdcSlJbxxDO;

    @ApiModelProperty(value = "不动产受理项目信息")
    private BdcSlXmDO bdcSlXmDO;

    @ApiModelProperty(value = "转入方房屋套次信息")
    private List<BdcSlFwtcxxDO> bdcZrfZfxxDTOList;

    @ApiModelProperty(value = "转出方房屋套次信息")
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

    public BdcSlJbxxDO getBdcSlJbxxDO() {
        return bdcSlJbxxDO;
    }

    public void setBdcSlJbxxDO(BdcSlJbxxDO bdcSlJbxxDO) {
        this.bdcSlJbxxDO = bdcSlJbxxDO;
    }

    @Override
    public BdcSlXmDO getBdcSlXmDO() {
        return bdcSlXmDO;
    }

    @Override
    public void setBdcSlXmDO(BdcSlXmDO bdcSlXmDO) {
        this.bdcSlXmDO = bdcSlXmDO;
    }
}
