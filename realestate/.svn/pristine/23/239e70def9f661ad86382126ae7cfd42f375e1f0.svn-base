package cn.gtmap.realestate.common.core.dto.engine;

import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/9/4
 * @description  子规则验证DTO对象（主要用于封装下多参数）
 */
public class BdcGzYzDTO {
    @ApiModelProperty(value = "组合规则对应的子规则集合")
    private List<BdcGzZgzDTO> bdcGzZgzDTOList;

    @ApiModelProperty(value = "验证参数")
    private BdcGzYzQO bdcGzYzQO;

    public List<BdcGzZgzDTO> getBdcGzZgzDTOList() {
        return bdcGzZgzDTOList;
    }

    public void setBdcGzZgzDTOList(List<BdcGzZgzDTO> bdcGzZgzDTOList) {
        this.bdcGzZgzDTOList = bdcGzZgzDTOList;
    }

    public BdcGzYzQO getBdcGzYzQO() {
        return bdcGzYzQO;
    }

    public void setBdcGzYzQO(BdcGzYzQO bdcGzYzQO) {
        this.bdcGzYzQO = bdcGzYzQO;
    }

    @Override
    public String toString() {
        return "BdcGzYzDTO{" +
                "bdcGzZgzDTOList=" + bdcGzZgzDTOList +
                ", bdcGzYzQO=" + bdcGzYzQO +
                '}';
    }
}
