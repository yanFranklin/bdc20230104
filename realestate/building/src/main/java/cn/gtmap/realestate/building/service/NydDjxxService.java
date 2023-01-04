package cn.gtmap.realestate.building.service;

import cn.gtmap.realestate.common.core.domain.building.NydDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.NydQlrDO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-10
 * @description 农用地 地籍信息相关服务
 */
public interface NydDjxxService extends DjxxDozerService<NydDjdcbDO,NydQlrDO>{

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据不动产单元号和承包宗地关系主键查询承包宗地地籍信息
     */
    DjxxResponseDTO getCbzdDjxxForInit(String bdcdyh, String cbzdDcbcbfrelIndex);

}
