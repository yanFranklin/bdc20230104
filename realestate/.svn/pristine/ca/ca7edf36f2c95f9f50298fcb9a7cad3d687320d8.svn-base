package cn.gtmap.realestate.building.service;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.building.FwhsBatchUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-04
 * @description 房屋户室业务服务
 */
public interface FwhsYwService {


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwhsBatchUpdateRequestDTO
     * @return void
     * @description 批量更新
     */
    void batchUpdate(FwhsBatchUpdateRequestDTO fwhsBatchUpdateRequestDTO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwYchsDO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @description 查询预测户室关联的房屋户室
     */
    List<FwHsDO> listGlFwhs(FwYchsDO fwYchsDO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @description 关联户室分页查询
     */
    Page<Map> glListHsByPage(Pageable pageable, Map map);
}
