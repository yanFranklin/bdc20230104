package cn.gtmap.realestate.natural.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/10
 * @description 查询打印配置
 */
@Component
public interface ZrzyDypzMapper {
    /**
     * @param bdcDysjPzDO
     * @return List<Map>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取 不动产打印主表配置
     */
    List<BdcDysjPzDO> listBdcDyPz(BdcDysjPzDO bdcDysjPzDO);

    /**
     * @param bdcDysjZbPzDO
     * @return List<Map>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取 不动产打印子表配置
     */
    List<BdcDysjZbPzDO> listBdcDyzbPz(BdcDysjZbPzDO bdcDysjZbPzDO);


}
