package cn.gtmap.realestate.engine.core.service;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzQO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0 2020/7/7 16:10
 * @description 规则系统固定子规则服务接口
 */

public interface BdcGzGdZgzService {
    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param bdcGzZgzQO bdcGzZgzQO
     * @return List<BdcGzZgzDO>
     * @description 查询固定子规则列表
     */
    List<BdcGzZgzDO> listBdcGzGdZgz(BdcGzZgzQO bdcGzZgzQO);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param bdcGzZgzDTO bdcGzZgzDTO
     * @return String
     * @description 保存固定子规则信息
     */
    String saveBdcGzGdZgz(BdcGzZgzDTO bdcGzZgzDTO);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param bdcGzZgzDOList bdcGzZgzDOList
     * @description 删除固定子规则
     */
    void deleteBdcGzZgz(List<BdcGzZgzDO> bdcGzZgzDOList);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param gzid gzid
     * @return 固定子规则DTO
     */
    BdcGzZgzDTO queryBdcGzGdZgzDTOByGzid(String gzid);
}
