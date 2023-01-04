package cn.gtmap.realestate.engine.core.service;

import cn.gtmap.realestate.common.core.dto.engine.BdcGzZhgzDTO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;

/**
 * 规则针对业务场景处理逻辑
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 */
public interface BdcGzYwService {
    /**
     * 过滤不需要验证的子规则
     * @param bdcGzYzQO 验证参数
     * @param bdcGzZhgzDTO 组合规则信息
     */
    void filterNotNeedCheckZgz(BdcGzYzQO bdcGzYzQO, BdcGzZhgzDTO bdcGzZhgzDTO);
}
