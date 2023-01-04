package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.qo.init.BdcDysdQO;
import cn.gtmap.realestate.common.core.qo.init.BdcZssdQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/08/26
 * @description 不动产锁定查询Mapper处理
 */
public interface BdcSdMapper {
    /**
     * 根据bdcdyh获取不动产单元锁定
     * @param bdcdyh
     * @return
     */
    List<BdcDysdDO> queryBdcdySdByBdcdyh(@Param("bdcdyh") String bdcdyh);

    /**
     * 根据cqzh获取不动产证书锁定
     * @param cqzh
     * @return
     */
    List<BdcZssdDO> queryBdcZsSdByCqzh(@Param("cqzh") String cqzh);

    /**
     * 根据bdcdyh更新不动产单元锁定信息
     * @param bdcDysdDO
     */
    void updateBdcdySdByBdcdyh(BdcDysdDO bdcDysdDO);

    /**
     * 根据cqzh更新不动产证书锁定信息
     * @param bdcZssdDO
     */
    void updateBdczsSdByCqzh(BdcZssdDO bdcZssdDO);

    /**
     * 根据cqzhs获取不动产证书锁定
     * @param cqzhs
     * @return
     */
    List<BdcZssdDO> queryBdcZsSdByCqzhs(@Param("list") List<String> cqzhs);

    /**
     * 根据证书锁定ID批量删除证书锁定信息
     * @param zssdIdList 证书锁定ID集合
     * @author <a herf="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    int batchDeleteBdcZssd(@Param("list") List<String> zssdIdList);

    /**
     * 查询证书锁定信息
     * @param bdcZssdQO 证书锁定QO
     * @return 不动产证书锁定DO集合
     */
    List<BdcZssdDO> queryBdcZssd(BdcZssdQO bdcZssdQO);

    /**
     * 查询单元锁定信息
     * @param bdcDysdQO 不动产单元锁定QO对象
     * @return 不动产单元锁定DO集合
     */
    List<BdcDysdDO> queryBdcDysd(BdcDysdQO bdcDysdQO);

    /**
     * 根据xmid获取不动产证书锁定
     * @param xmid
     * @return
     */
    List<BdcZssdDO> queryYxmBdcqzhByXmid(@Param("xmid") String xmid);
}
