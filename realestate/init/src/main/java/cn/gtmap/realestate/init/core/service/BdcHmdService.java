package cn.gtmap.realestate.init.core.service;


import cn.gtmap.realestate.common.core.domain.BdcHmdDO;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2021/10/25
 * @description 不动产黑名单服务
 */
public interface BdcHmdService {

    /**
     * 查询不动产黑名单信息
     * @param bdcHmdDO 不动产黑名单DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 黑名单信息
     */
    List<BdcHmdDO> queryBdcHmd(BdcHmdDO bdcHmdDO);
    /**
     * @param bdcHmdDO 不动产黑名单DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 更新不动产黑名单
     */
    void updateBdcHmd(BdcHmdDO bdcHmdDO);

    /**
     * @param bdcHmdDO 不动产黑名单DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 新增不动产黑名单
     */
    BdcHmdDO saveBdcHmd(BdcHmdDO bdcHmdDO);

    /**
     * 解锁黑名单
     * <p>大云调用接口，用于业务审核通过后，自动解锁原项目的不动产权证书的不良记录</p>
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void jsBljl(String gzlslid);

    /**
     * @param hmdIdList 黑名单ID集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 批量删除黑名单信息
     */
    void batchDeleteBdcHmd(List<String> hmdIdList);

    /**
     * 批量解锁黑名单
     * @param bdcHmdDOList 不动产黑名单集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void jsBljlxx(List<BdcHmdDO> bdcHmdDOList);


}
