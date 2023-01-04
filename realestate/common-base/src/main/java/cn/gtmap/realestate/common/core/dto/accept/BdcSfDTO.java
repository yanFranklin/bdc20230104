package cn.gtmap.realestate.common.core.dto.accept;


import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @program: realestate
 * @description: 南通缴费页面税务信息
 * @author: <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @create: 2022/9/27
 **/
@ApiModel(value = "BdcSfDTO", description = "南通缴费页面税务信息")
public class BdcSfDTO {

    @ApiModelProperty(value = "不动产受理转入方税务信息")
    private List<BdcSlHsxxDO> bdcZrfSwxxList;

    @ApiModelProperty(value = "不动产受理转出方税务信息")
    private List<BdcSlHsxxDO> bdcZcfSwxxList;

    public List<BdcSlHsxxDO> getBdcZrfSwxxList() {
        return bdcZrfSwxxList;
    }

    public void setBdcZrfSwxxList(List<BdcSlHsxxDO> bdcZrfSwxxList) {
        this.bdcZrfSwxxList = bdcZrfSwxxList;
    }

    public List<BdcSlHsxxDO> getBdcZcfSwxxList() {
        return bdcZcfSwxxList;
    }

    public void setBdcZcfSwxxList(List<BdcSlHsxxDO> bdcZcfSwxxList) {
        this.bdcZcfSwxxList = bdcZcfSwxxList;
    }

    @Override
    public String toString() {
        return "BdcSfDTO{" +
                "bdcZrfSwxxList=" + bdcZrfSwxxList +
                ", bdcZcfSwxxList=" + bdcZcfSwxxList +
                '}';
    }
}
