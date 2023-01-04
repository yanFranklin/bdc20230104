package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcByslDO;

import java.util.List;


/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @version 1.0, 2022/08/24.
 * @description Accept不予登记/不予受理服务
 */
public interface BdcSlBysldjService {

    /**
     * @param gzlslid 工作流定义ID
     * @return 不予受理/不予登记信息
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 依据工作流受理id获取不予受理，不予登记信息
     */
    List<BdcByslDO> queryBdcByslDOBygzlslid(String gzlslid);

    /**
     * @description 新增不予受理不予登记单
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     */
    BdcByslDO insertBdcByslDO(BdcByslDO bdcByslDO);

    /**
     * @param byslid 不予受理ID
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 依据不予受理ID删除不予受理信息
     */
    Integer deleteBdcByslDOByByslid(String byslid);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 依据工作流实例ID删除不予受理信息
     */
    Integer deleteBdcByslDOBygzlslid(String gzlslid);

    /**
     * @description 新增不予受理不予登记单
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     */
    Integer saveBdcByslDOBygzlslid(String json);


}
