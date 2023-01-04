package cn.gtmap.realestate.register.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcFgfDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/12/17
 * @description 房改房查询Mapper
 */
public interface BdcFgfMapper {
    /**
     * @param bdcXmQO 项目查询QO
     * @return List<BdcFgfDO> 房改房信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询房改房信息
     */
    List<BdcFgfDO> listBdcFgf(BdcXmQO bdcXmQO);
}
