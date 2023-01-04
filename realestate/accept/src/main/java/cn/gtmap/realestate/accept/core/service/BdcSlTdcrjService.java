package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlTdcrjDO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/8
 * @description 土地出让金
 */
public interface BdcSlTdcrjService {

    /**
      * @param xmid 项目ID
      * @return 出让金信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据项目ID获取出让金信息
      */
    List<BdcSlTdcrjDO> listBdcSlTdcrjByXmid(String xmid);

    /**
      * @param bdcSlTdcrjDO 土地出让金信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 新增土地出让金信息
      */
    void insertBdcSlTdcrj(BdcSlTdcrjDO bdcSlTdcrjDO);

    /**
     * @param tdcrjid 土地出让金ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除土地出让金信息
     */
    void deleteBdcSlTdcrj(String tdcrjid);
}
