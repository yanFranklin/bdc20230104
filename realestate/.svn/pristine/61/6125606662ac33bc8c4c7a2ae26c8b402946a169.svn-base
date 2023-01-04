package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO;
import cn.gtmap.realestate.common.core.dto.building.FwHsBgHistoryDTO;
import cn.gtmap.realestate.common.core.dto.building.FwHsBgHistoryNewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/4
 * @description 户室历史操作服务
 */
public interface FwHsHistoryService {
    /**
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询户室历史变更记录
     */
    Page<SSjHsbgljbDO> listHsbgHsitroyByPageJson(Pageable pageable, Map map);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwHsIndex
     * @return java.util.Map
     * @description 查询户室变更记录
     */
    List<List<FwHsBgHistoryDTO>> getHsBgHistory(String fwHsIndex);
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return java.util.Map
     * @description 查询户室变更记录通过不动产单元号
     */
    List<List<FwHsBgHistoryDTO>> getHsBgHistoryByBdcdyh(String bdcdyh);
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwHsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.HFwHsDO
     * @description 根据房屋户室主键查询房屋户室信息
     */
    FwHsDO getHFwHsByFwHsIndex(String fwHsIndex, boolean last);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwHsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.HFwHsDO
     * @description 根据房屋户室主键查询房屋户室信息(新)
     */
    FwHsDO getHFwHsByFwHsIndexNew(String fwHsIndex);
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return java.util.Map
     * @description 查询户室变更记录(新)
     */
    List<FwHsBgHistoryNewDTO> getHsBgHistoryNewByBdcdyh(String bdcdyh);
}
