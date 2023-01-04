package cn.gtmap.realestate.register.core.mapper;

import cn.gtmap.realestate.common.core.domain.register.BdcBdcdyhxsztDO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/8/27
 * @description 状态服务
 */
public interface BdcdyZtMapper {

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyhList 单元号集合
     * @return {List} 现势状态信息
     * @description 批量查询不动产单元现势状态信息(登记库)
     */
    List<BdcBdcdyhxsztDO> listDjBdcdyXszt(List<String> bdcdyhList);
}
