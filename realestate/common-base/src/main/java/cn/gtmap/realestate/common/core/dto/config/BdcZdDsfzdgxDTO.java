package cn.gtmap.realestate.common.core.dto.config;


import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/7/21
 * @description 第三方字典关系对照表DTO
 */
public class BdcZdDsfzdgxDTO {

    @ApiModelProperty(value = "字典表名称")
    private String zdbs;

    @ApiModelProperty(value = "第三方系统标识")
    private String dsfxtbs;

    @ApiModelProperty(value = "字典项配置值")
    private List<BdcZdDsfzdgxDO> bdcZdDsfzdgxDOList;

    public String getZdbs() {
        return zdbs;
    }

    public void setZdbs(String zdbs) {
        this.zdbs = zdbs;
    }

    public String getDsfxtbs() {
        return dsfxtbs;
    }

    public void setDsfxtbs(String dsfxtbs) {
        this.dsfxtbs = dsfxtbs;
    }

    public List<BdcZdDsfzdgxDO> getBdcZdDsfzdgxDOList() {
        return bdcZdDsfzdgxDOList;
    }

    public void setBdcZdDsfzdgxDOList(List<BdcZdDsfzdgxDO> bdcZdDsfzdgxDOList) {
        this.bdcZdDsfzdgxDOList = bdcZdDsfzdgxDOList;
    }
}
