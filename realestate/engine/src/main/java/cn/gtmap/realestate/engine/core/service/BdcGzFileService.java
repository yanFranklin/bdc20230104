package cn.gtmap.realestate.engine.core.service;

import cn.gtmap.realestate.common.core.dto.engine.BdcGzZhgzDTO;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/3/7 18:37
 * @description 规则导出文件service
 */

public interface BdcGzFileService {

    /**
     * 查询规则数据，重新组织
     *
     * @param zhid zhid
     * @return BdcGzZhgzDTO BdcGzZhgzDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    BdcGzZhgzDTO queryZhgzDTO(String zhid);

}
