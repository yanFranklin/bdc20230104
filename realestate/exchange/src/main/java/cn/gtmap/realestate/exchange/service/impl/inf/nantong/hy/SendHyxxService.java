package cn.gtmap.realestate.exchange.service.impl.inf.nantong.hy;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;

/**
 * 南通海域-相关处理
 */
public interface SendHyxxService {

    /**
     * 获取相应的实现类
     *
     * @return
     */
    String getHandleServiceName();

    CommonResponse commonSendHyxx(BdcXmDO bdcXmDO);

}
