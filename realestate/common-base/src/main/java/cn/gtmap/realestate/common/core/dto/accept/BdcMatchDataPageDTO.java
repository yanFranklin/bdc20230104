package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.dto.init.BdcQlPageResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/3/16
 * @description 分页查询匹配数据
 */
@ApiModel(value = "BdcQlPageResponseDTO",description = "分页查询匹配数据")
public class BdcMatchDataPageDTO extends BdcQlPageResponseDTO {

    @ApiModelProperty(value = "存量房数据落宗状态(0:未落宗 1：已落宗")
    private Integer clfsjlzzt;

    @ApiModelProperty(value = "存量房数据匹配状态(0:未匹配 1：已匹配")
    private Integer clfsjppzt;

    public Integer getClfsjlzzt() {
        return clfsjlzzt;
    }

    public void setClfsjlzzt(Integer clfsjlzzt) {
        this.clfsjlzzt = clfsjlzzt;
    }

    public Integer getClfsjppzt() {
        return clfsjppzt;
    }

    public void setClfsjppzt(Integer clfsjppzt) {
        this.clfsjppzt = clfsjppzt;
    }
}
