package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/1/17
 * @description
 */
public interface BdcDypzService {


    /**
     * @param bdcDysjPzDO
     * @return List<BdcDysjPzDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取不动产打印配置主表数据
     */
    List<BdcDysjPzDO> listBdcDysjPz(BdcDysjPzDO bdcDysjPzDO);

    /**
     * @param  bdcDysjZbPzDO
     * @return  List<BdcDysjZbPzDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印配置子表数据
     */
    List<BdcDysjZbPzDO> listBdcDysjZbPz(BdcDysjZbPzDO bdcDysjZbPzDO);

}
