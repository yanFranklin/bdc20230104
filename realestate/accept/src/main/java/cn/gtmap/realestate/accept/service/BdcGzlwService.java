package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcGzlwShDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCshSlxmDTO;

import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/8/26
 * @description
 */
public interface BdcGzlwService {

    /**
     * 增加审核信息
     * @param data
     * @param slbh
     * @param xmid
     */
    BdcGzlwShDO addShxxData(Map data, String slbh, String xmid);

    /**
     * 增加审核信息
     * @param bdcCshSlxmDTO
     * @param qllx
     */
    void addShxxDataWithoutSlbh(BdcCshSlxmDTO bdcCshSlxmDTO, String qllx);
}
