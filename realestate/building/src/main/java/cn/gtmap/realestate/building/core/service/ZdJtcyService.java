package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.ZdJtcyDO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/11
 * @description 宗地家庭成员
 */
public interface ZdJtcyService {

    /**
     * @param djh 地籍号
     * @return 宗地家庭成员信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据地籍号获取宗地家庭成员信息
     */
    List<ZdJtcyDO> listZdJtcyByDjh(String djh);
}
