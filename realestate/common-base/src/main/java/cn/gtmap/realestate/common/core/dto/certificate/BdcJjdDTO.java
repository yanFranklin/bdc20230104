package cn.gtmap.realestate.common.core.dto.certificate;


import cn.gtmap.realestate.common.core.domain.BdcJjdDO;

import java.util.List;

/**
 * 交接单 DTO
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/11/20
 */
public class BdcJjdDTO extends BdcJjdDO {
    /**
     * 关联工作流实例 ID
     */
    private List<String> gzlslidList;

}
