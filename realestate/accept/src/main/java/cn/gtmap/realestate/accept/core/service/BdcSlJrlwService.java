package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJrLwDO;

import java.util.List;

/**
 * @program: bdcdj3.0
 * @description: 接入例外service
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-10-05 16:09
 **/
public interface BdcSlJrlwService {

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增或者更新
     * @date : 2022/10/5 16:09
     */
    int saveOrUpdateJrlw(List<BdcSlJrLwDO> bdcSlJrLwDOList);
}
