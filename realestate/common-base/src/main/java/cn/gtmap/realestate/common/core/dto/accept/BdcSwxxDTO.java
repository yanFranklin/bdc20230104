package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxMxDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/25
 * @description 税务信息
 */
@ApiModel(value = "BdcSwxxDTO", description = "税务信息类模型")
public class BdcSwxxDTO {

    @ApiModelProperty(value = "不动产受理核税信息")
    private BdcSlHsxxDO bdcSlHsxxDO;
    @ApiModelProperty(value = "不动产受理核税信息明细集合")
    private List<BdcSlHsxxMxDO> bdcSlHsxxMxDOList;

    public BdcSlHsxxDO getBdcSlHsxxDO() {
        return bdcSlHsxxDO;
    }

    public void setBdcSlHsxxDO(BdcSlHsxxDO bdcSlHsxxDO) {
        this.bdcSlHsxxDO = bdcSlHsxxDO;
    }

    public List<BdcSlHsxxMxDO> getBdcSlHsxxMxDOList() {
        return bdcSlHsxxMxDOList;
    }

    public void setBdcSlHsxxMxDOList(List<BdcSlHsxxMxDO> bdcSlHsxxMxDOList) {
        this.bdcSlHsxxMxDOList = bdcSlHsxxMxDOList;
    }

    @Override
    public String toString() {
        return "BdcSwxxDTO{" +
                "bdcSlHsxxDO=" + bdcSlHsxxDO +
                ", bdcSlHsxxMxDOList=" + bdcSlHsxxMxDOList +
                '}';
    }
}
