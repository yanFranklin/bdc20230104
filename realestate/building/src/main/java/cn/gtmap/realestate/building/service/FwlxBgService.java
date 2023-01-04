package cn.gtmap.realestate.building.service;

import cn.gtmap.realestate.building.service.bg.bdcdyfwlxbg.BdcdyfwlxBgService;
import cn.gtmap.realestate.common.core.dto.building.FwlxBgRequestDTO;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019/1/17
 * @description
 */
public interface FwlxBgService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return cn.gtmap.realestate.building.service.bg.bdcdyfwlxbg.BdcdyfwlxBgService
     * @description 根据前后BDCDYFWLX 判断使用哪个实现
     */
    BdcdyfwlxBgService getBean(FwlxBgRequestDTO requestDTO);

}
