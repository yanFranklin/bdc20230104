package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;

import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/9/12
 * @description 受理信息回写
 */
public interface BdcSlxxHxService {

    /**
     * 保存或更新受理信息到平台
     * @param bdcSlJbxxDO 受理基本信息
     * @throws Exception
     */
    void hxBdcSlxx(BdcSlJbxxDO bdcSlJbxxDO) throws Exception;

    /**
     * 保存或更新受理信息和自定义信息至大云平台
     * @param bdcSlJbxxDO 受理基本信息
     * @param map 自定义信息
     * @throws Exception
     */
    void hxBdcSlxxWithZdyxx(BdcSlJbxxDO bdcSlJbxxDO, Map map) throws Exception;
}
