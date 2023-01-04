package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlXztzPzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXztzPzDTO;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2019/1/30
 * @description 不动产受理选择台账配置 数据服务
 */
public interface BdcSlXztzPzService {
    /**
     * @param gzldyid 工作流定义ID
     * @return 不动产受理选择台账配置
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流定义ID获取不动产受理选择台账配置
     */
    BdcSlXztzPzDO queryBdcSlXztzPzDOByGzldyid(String gzldyid);

    /**
     * @param bdcSlXztzPzDO 不动产受理选择台账配置
     * @return 保存数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存不动产受理选择台账配置
     */
    int saveBdcSlXztzPzDO(BdcSlXztzPzDO bdcSlXztzPzDO);

    /**
     * @param gzldyid 工作流定义ID
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流定义ID删除受理选择台账配置
     */
    int deleteBdcSlXztzPzDOByGzldyid(String gzldyid);

    /**
     * @param gzldyid 工作流定义ID
     * @return 不动产受理选择台账配置
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流定义ID获取不动产受理选择台账配置
     */
    BdcSlXztzPzDTO queryBdcSlXztzPzDTOByGzldyid(String gzldyid);
}
