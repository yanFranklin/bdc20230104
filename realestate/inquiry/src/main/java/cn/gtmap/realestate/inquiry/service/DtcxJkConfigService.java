package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.domain.inquiry.*;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO;

import java.util.List;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/3/19
 * @description
 */
public interface DtcxJkConfigService {

    /**
     * 逻辑删除查询
     *
     * @param cxid
     * @return
     */
    void deleteDtcxCx(String cxid);

    /**
     * 获取查询结果List
     *
     * @param cxid
     * @return
     */
    List<DtcxCxjgDO> getCxjgList(String cxid);

    /**
     * 通过查询代号获取当前代号查询的所有配置信息，包括查询基本信息、查询条件、结果的配置
     * @date 2019.03.22 15:09
     * @author hanyaning
     * @param cxdh 查询代号，查询标识唯一值
     * @return cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO
     */
    BdcDtcxDTO getConfigsByCxdh(String cxdh);
}
