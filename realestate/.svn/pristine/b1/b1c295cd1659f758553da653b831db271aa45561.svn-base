package cn.gtmap.realestate.common.core.dto.inquiry;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcCxqlrDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcCxsqsDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/8/26
 * @description 查询申请书DTO，增加权利人对象
 */
@Api(value = "BdcCxsqsDTO", description = "查询申请书DTO，增加权利人对象")
public class BdcCxsqsDTO extends BdcCxsqsDO {
    @ApiModelProperty(value = "查询申请书权利人信息")
    private List<BdcCxqlrDO> bdcCxqlrDOList;

    public List<BdcCxqlrDO> getBdcCxqlrDOList() {
        return bdcCxqlrDOList;
    }

    public void setBdcCxqlrDOList(List<BdcCxqlrDO> bdcCxqlrDOList) {
        this.bdcCxqlrDOList = bdcCxqlrDOList;
    }

    @Override
    public String toString() {
        return "BdcCxsqsDTO{" +
                "bdcCxqlrDOList=" + bdcCxqlrDOList +
                '}';
    }
}
