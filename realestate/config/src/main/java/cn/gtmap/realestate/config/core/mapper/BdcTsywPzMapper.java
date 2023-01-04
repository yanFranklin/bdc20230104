package cn.gtmap.realestate.config.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcTsywPzDO;
import cn.gtmap.realestate.common.core.qo.config.BdcTsywPzQO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/3/5
 * @description YML配置服务
 */
public interface BdcTsywPzMapper {

    /**
     * @param bdcTsywPzQO 不动产YML配置查询QO
     * @return 配置集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询YML配置
     */
    List<BdcTsywPzDO> listBdcTsywPz(BdcTsywPzQO bdcTsywPzQO);

}
