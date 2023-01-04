package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcGzdjDO;
import cn.gtmap.realestate.common.core.qo.init.BdcGzdjQO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/9/10
 * @description 更正登记服务
 */
public interface BdcGzdjService {

    /**
     * @param xmid 项目ID
     * @return 不动产更正登记集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据xmid查询不动产更正信息
     */
    List<BdcGzdjDO> listBdcGzdjByXmid(String xmid);

    /**
      * @param bdcGzdjQO 更正登记查询对象
      * @return 不动产更正登记集合
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 查询不动产更正信息
      */
    List<BdcGzdjDO> listBdcGzdj(BdcGzdjQO bdcGzdjQO);

    /**
      * @param xmidList 项目ID集合
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 批量删除更正信息
      */
    void deleteBdcGzdjPl(List<String> xmidList);


}
