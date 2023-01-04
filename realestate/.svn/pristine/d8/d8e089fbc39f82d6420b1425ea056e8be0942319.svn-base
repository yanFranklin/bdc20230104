package cn.gtmap.realestate.register.core.service;

import cn.gtmap.realestate.common.core.domain.BdcTddysfDyhDO;
import cn.gtmap.realestate.common.core.dto.register.BdcTddysfDyhDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcTddysfDyhShDTO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/10/12
 * @description 不动产项目土地抵押释放关系服务
 */
public interface BdcTddysfDyhService {

    /**
     * @param gzlslid 工作流实例ID
     * @return 单元号集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取土地抵押释放单元号集合
     */
    List<BdcTddysfDyhDO> listTddysfBdcdyhByGzlslid(String gzlslid);

    /**
     * @param bdcTddysfDyhDTO 土地抵押释放单元号信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 生成土地抵押释放单元号信息
     */
    void initBdcTddysfDyh(BdcTddysfDyhDTO bdcTddysfDyhDTO);

    /**
     * @param bdcTddysfDyhDTO 土地抵押释放单元号信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除土地抵押释放单元号信息
     */
    void deleteBdcTddysfDyh(BdcTddysfDyhDTO bdcTddysfDyhDTO);

    /**
     * @param bdcTddysfDyhShDTO 审核信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新土地抵押释放审核信息
     */
    void updateBdcTddysfDyhShxx(BdcTddysfDyhShDTO bdcTddysfDyhShDTO);
}
