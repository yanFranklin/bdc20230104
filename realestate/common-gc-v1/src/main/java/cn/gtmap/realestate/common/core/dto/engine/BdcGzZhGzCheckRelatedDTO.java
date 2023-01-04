package cn.gtmap.realestate.common.core.dto.engine;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzZhgzDO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/5/29
 * @description 不动产规则验证查询参数QO定义
 *
 */
@Data
@ApiModel(value = "BdcGzZhGzCheckRelatedDTO",description = "")
public class BdcGzZhGzCheckRelatedDTO {

    private List<BdcGzZhGzYzCjCheckDTO> bdcGzZhGzYzCjCheckDTOList;

    private List<BdcGzZhgzDO> bdcGzZhgzDOList;

}
