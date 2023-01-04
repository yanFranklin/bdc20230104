package cn.gtmap.realestate.register.core.dto;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcFdcqFdcqxmDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/11
 * @description 不动产房地产权，同时包含项目内多幢信息
 */
public class BdcFdcqDTO {
    /**
     * 不动产房地产权
     */
    @ApiModelProperty(value = "房地产权")
    private BdcFdcqDO bdcFdcqDO;
    /**
     * 项目内多幢，项目信息
     */
    @ApiModelProperty(value = "项目内多幢，项目信息")
    private List<BdcFdcqFdcqxmDO> bdcFdcqFdcqxmDOList;

    public List<BdcFdcqFdcqxmDO> getBdcFdcqFdcqxmDOList() {
        return bdcFdcqFdcqxmDOList;
    }

    public void setBdcFdcqFdcqxmDOList(List<BdcFdcqFdcqxmDO> bdcFdcqFdcqxmDOList) {
        this.bdcFdcqFdcqxmDOList = bdcFdcqFdcqxmDOList;
    }

    public BdcFdcqDO getBdcFdcqDO() {
        return bdcFdcqDO;
    }

    public void setBdcFdcqDO(BdcFdcqDO bdcFdcqDO) {
        this.bdcFdcqDO = bdcFdcqDO;
    }

    @Override
    public String toString() {
        return "BdcFdcqDTO{" +
                "bdcFdcqDO=" + bdcFdcqDO +
                ", bdcFdcqFdcqxmDOList=" + bdcFdcqFdcqxmDOList +
                '}';
    }
}
