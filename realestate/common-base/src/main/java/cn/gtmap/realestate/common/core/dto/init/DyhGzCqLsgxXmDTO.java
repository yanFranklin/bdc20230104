package cn.gtmap.realestate.common.core.dto.init;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/9/21
 * @description 单元号更正产权对应的历史关系项目数据
 */
public class DyhGzCqLsgxXmDTO {

    @ApiModelProperty(value = "产权项目")
    private BdcXmDO bdcXmDO;

    @ApiModelProperty(value = "产权限制权利历史关系结构模型")
    private List<CqXzqlLsgxModel> cqXzqlLsgxModelList;

    public BdcXmDO getBdcXmDO() {
        return bdcXmDO;
    }

    public void setBdcXmDO(BdcXmDO bdcXmDO) {
        this.bdcXmDO = bdcXmDO;
    }

    public List<CqXzqlLsgxModel> getCqXzqlLsgxModelList() {
        return cqXzqlLsgxModelList;
    }

    public void setCqXzqlLsgxModelList(List<CqXzqlLsgxModel> cqXzqlLsgxModelList) {
        this.cqXzqlLsgxModelList = cqXzqlLsgxModelList;
    }
}
