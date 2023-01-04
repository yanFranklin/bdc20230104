package cn.gtmap.realestate.exchange.service.national;

import cn.gtmap.realestate.common.core.domain.exchange.DjfDjYwxxDO;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-04-26
 * @description
 */
public interface DjfDjYwxxService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param xmid
     * @return cn.gtmap.realestate.common.core.domain.exchange.DjfDjYwxxDO
     * @description 根据XMID查询业务信息
     */
    DjfDjYwxxDO queryYwxxByXmid(String xmid);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djfDjYwxxDO
     * @return void
     * @description 保存业务信息
     */
    void saveYwxx(DjfDjYwxxDO djfDjYwxxDO);
}
