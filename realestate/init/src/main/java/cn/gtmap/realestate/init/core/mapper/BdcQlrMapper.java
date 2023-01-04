package cn.gtmap.realestate.init.core.mapper;


import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 权利人服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/13.
 * @description
 */
public interface BdcQlrMapper {
    /**
     * 删除权利人
     * @param paramMap
     * @return
     */
    void deleteQlr(Map paramMap);

    /**
     * 根据权利人 ID 批量删除权利人
     *
     * @param qlridlist 权利人id 集合
     */
    void deleteBatchQlr(@Param("qlridlist") List qlridlist);

    /**
     * 根据 gzlslid或slbh或bdcdyh 查询全部的权利人
     * @param paramMap
     * @return
     */
    List<BdcQlrDO> listAllBdcQlr(Map paramMap);

    /**
     * 根据工作流实例id更新权利人的zsid (可增加qlrmc和zjh条件)
     *@param map zsid 证书id 和gzlslid 工作流实例id 必填
     */
    void updateQlrZsid(Map map);

    /**
     * 批量更新不动产权利人
     *
     * @param map
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新不动产权利人
     */
    int updateBatchBdcQlr(Map map);

    /**
     * 删除第三权利人
     *
     * @param paramMap
     * @return
     */
    void deleteDsQlr(Map paramMap);

    /**
     * 根据查询参数查询权利人信息，支持按模糊类型查询
     * @param bdcQlrQO 查询参数
     * @return List<bdcQlrDO> 权利人信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcQlrDO> queryBdcQlrWithMhlx(BdcQlrQO bdcQlrQO);

    /**
     * 查询证书（证明）关联的权利人（义务人）信息
     * @param bdcQlrQO 参数（zsid、qlrlb）
     * @return {List} 权利人信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<BdcQlrDO> listBdcQlrByZsid(BdcQlrQO bdcQlrQO);
}
