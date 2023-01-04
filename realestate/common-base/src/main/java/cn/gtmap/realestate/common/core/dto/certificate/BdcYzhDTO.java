package cn.gtmap.realestate.common.core.dto.certificate;

import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhsymxDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/4
 * @description 不动产印制号DTO对象
 */
@ApiModel(value = "BdcYzhDTO", description = "不动产印制号DTO对象")
public class BdcYzhDTO extends BdcYzhDO {

    @ApiModelProperty(value = "不动产印制号使用说明")
    private List<BdcYzhsymxDO> bdcYzhsymxDOList;

    public List<BdcYzhsymxDO> getBdcYzhsymxDOList() {
        return bdcYzhsymxDOList;
    }

    public void setBdcYzhsymxDOList(List<BdcYzhsymxDO> bdcYzhsymxDOList) {
        this.bdcYzhsymxDOList = bdcYzhsymxDOList;
    }

    @Override
    public String toString() {
        return "BdcYzhDTO{" +
                "bdcYzhsymxDOList=" + bdcYzhsymxDOList +
                '}';
    }
}
