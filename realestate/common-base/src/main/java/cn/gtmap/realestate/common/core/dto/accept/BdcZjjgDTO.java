package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlZjjgDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/11/7
 * @description 资金监管信息
 */
public class BdcZjjgDTO {

    @ApiModelProperty(value = "资金监管信息")
    private List<BdcSlZjjgDO> bdcSlZjjgDOS;

    @ApiModelProperty(value = "资金监管主键集合")
    private List<String> zjjgidList;

    public List<BdcSlZjjgDO> getBdcSlZjjgDOS() {
        return bdcSlZjjgDOS;
    }

    public void setBdcSlZjjgDOS(List<BdcSlZjjgDO> bdcSlZjjgDOS) {
        this.bdcSlZjjgDOS = bdcSlZjjgDOS;
    }

    public List<String> getZjjgidList() {
        return zjjgidList;
    }

    public void setZjjgidList(List<String> zjjgidList) {
        this.zjjgidList = zjjgidList;
    }
}
