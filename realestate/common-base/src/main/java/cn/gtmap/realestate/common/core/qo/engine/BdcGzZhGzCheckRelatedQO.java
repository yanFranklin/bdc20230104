package cn.gtmap.realestate.common.core.qo.engine;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/5/29
 * @description 不动产规则验证查询参数QO定义
 *
 */
@Data
@ApiModel(value = "BdcGzZhGzCheckRelatedQO",description = "规则验证查询实体")
public class BdcGzZhGzCheckRelatedQO {

    @ApiModelProperty(value = "组合id")
    private String zhId;

    @ApiModelProperty(value = "验证场景")
    private List<String> zhBsList;
}
