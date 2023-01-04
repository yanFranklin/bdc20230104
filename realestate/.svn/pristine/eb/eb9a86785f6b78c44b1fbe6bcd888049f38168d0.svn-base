package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcDjxlDjyyGxDO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxlDjyyQO;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/5/31
 * @description 登记类型、登记原因关系业务类
 */
public interface BdcDjxlDjyyGxService {
    /**
     * 查询
     * @param bdcDjxlDjyyGxDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询
     */
    List<BdcDjxlDjyyGxDO> listBdcDjxlDjyyGx(BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO);

    /**
     * 查询登记原因配置顺序最大号
     * @param djxl 登记小类
     * @return 登记原因顺序最大号
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a></a>
     */
    int queryDjyyMaxCount(String djxl);

    /**
     * 新增
     * @param bdcDjxlDjyyGxDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增
     */
    int insertBdcDjxlDjyyGx(BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO);

    /**
     * 修改
     * @param bdcDjxlDjyyGxDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改
     */
    int updateBdcDjxlDjyyGx(BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO);

    /**
     * 批量更新不动产登记小类、登记原因关系
     * @param bdcDjxlDjyyGxDOList 业务类型与登记原因关系集合
     * @return 更新结果
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void batchUpdateBdcDjxlDjyyGx(List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOList);

    /**
     * 删除
     * @param bdcDjxlDjyyGxDOList
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除
     */
    int deleteBdcDjxlDjyyGx(List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOList);


    /**
     * 根据登记小类查询所有的登记原因
     * @param djxls
     * @return List<String> 登记原因集合
     * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 查询
     */
    List<String> listDjyys(List<String> djxls);

    /**
      * @param bdcDjxlDjyyQO 登记小类登记原因QO
      * @return 登记小类登记原因集合
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 查询登记原因登记小类关系,djxl必须匹配,其余参数可匹配或配置为空
      */
    List<BdcDjxlDjyyGxDO> listBdcDjxlDjyyGxWithParam(BdcDjxlDjyyQO bdcDjxlDjyyQO);
}
