package cn.gtmap.realestate.building.service;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dto.building.FwhsAndFwQlrRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.FwychsAndFwQlrRequestDTO;

import java.util.List;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2018/12/26
 * @description 导入楼盘表相关信息
 */
public interface LpbImportService {
    /**
     * @param fwLjzDO
     * @param fgyyhs
     * @param fwhsAndFwQlrList
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 楼盘表导入服务
     */
    void lpbImport(FwLjzDO fwLjzDO, Boolean fgyyhs, List<FwhsAndFwQlrRequestDTO> fwhsAndFwQlrList);
    /**
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 导入预测楼盘表数据
     */
    void lpbYcImport(FwLjzDO fwLjzDO, Boolean fgyyhs, List<FwychsAndFwQlrRequestDTO> fwychsAndFwQlrRequestDTOList);

    /**
     * @param fwDcbIndex
     * @param fwHsDOList
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 实测面积导入服务
     */
    void scmjImport(String fwDcbIndex, List<FwHsDO> fwHsDOList);
}
