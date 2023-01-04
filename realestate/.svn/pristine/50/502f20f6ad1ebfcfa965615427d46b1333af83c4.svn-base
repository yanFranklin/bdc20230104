package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlDyaqDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlDeleteCsDTO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/3/27
 * @description 受理抵押信息
 */
public interface BdcSlDyaqService {

    /**
     * @param xmid 项目ID
     * @return 受理抵押信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据项目ID查询受理抵押信息
     */
     BdcSlDyaqDO queryBdcSlDyaqByXmid(String xmid);

     /**
      * @param bdcSlDeleteCsDTO 受理删除参数
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  根据参数批量删除受理抵押信息
      */
      void deleteBdcSlDyaq(BdcSlDeleteCsDTO bdcSlDeleteCsDTO);

      /**
       * @param map 更新字段
       * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
       * @description 批量更新受理抵押权冗余字段
       */
      void updateBdcSlDyaqRyzdPl(Map map);

 /**
  * @param xmidList 项目ID集合
  * @return
  * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
  * @description 根据项目ID集合批量查询受理抵押信息
  */
 List<BdcSlDyaqDO> listSlDyaqPl(List<String> xmidList);




}

