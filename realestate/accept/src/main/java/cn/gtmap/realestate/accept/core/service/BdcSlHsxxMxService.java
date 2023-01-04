package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxMxDO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/25
 * @description 核税信息明细
 */
public interface BdcSlHsxxMxService {

    /**
     * @param hsxxid 核税信息ID
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据核税信息ID删除受理核税信息明细
     */
    int delBdcSlHsxxMxByHsxxid(String hsxxid);

    /**
     * @param hsxxid 核税信息ID
     * @return 受理核税信息明细
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据核税信息ID查询受理核税信息明细
     */
    List<BdcSlHsxxMxDO> listBdcSlHsxxMxByHsxxid(String hsxxid);

    /**
     * @param bdcSlHsxxMxDO 不动产受理核税信息明细
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新不动产受理核税信息明细
     */
    int updateBdcSlHsxxMx(BdcSlHsxxMxDO bdcSlHsxxMxDO);

    /**
     * @param json 集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  批量更新受理核税信息明细
     */
    int batchUpdateBdcSlHsxxMx(String json);



}
