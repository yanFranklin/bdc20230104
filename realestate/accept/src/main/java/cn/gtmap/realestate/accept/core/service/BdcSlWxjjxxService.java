package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlWxjjxxDO;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/12/22
 * @description 不动产受理维修基金数据服务
 */
public interface BdcSlWxjjxxService {

    /**
     * 根据维修基金信息DO获取不动产受理维修基金信息
     *
     * @param bdcSlWxjjxxDO 维修基金信息DO
     * @return 受理维修基金信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcSlWxjjxxDO> queryBdcSlWxjjxx(BdcSlWxjjxxDO bdcSlWxjjxxDO);

    /**
     * 新增不动产受理维修基金信息
     * @param bdcSlWxjjxxDO 不动产受理维修基金信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 维修基金信息
     */
    BdcSlWxjjxxDO insertBdcSlWxjjxxDO(BdcSlWxjjxxDO bdcSlWxjjxxDO);

    /**
     * 新增不动产受理维修基金信息
     * @param bdcSlWxjjxxDOList 不动产受理维修基金信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @return 维修基金信息
     */
    void insertBatchBdcSlWxjjxxDO(List<BdcSlWxjjxxDO>  bdcSlWxjjxxDOList);

    /**
     * 根据维修基金信息ID删除不动产受理维修基金信息
     * @param wxjjxxid 维修基金信息ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 删除结果
     */
    Integer deleteWxjjxxByWxjjxxid(String wxjjxxid);

    /**
     * 根据工作流实例ID删除不动产受理维修基金信息
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 删除结果
     */
    Integer deleteWxjjxxByGzlslid(String gzlslid);

    /**
     * 更新不动产受理维修基金信息
     * @param bdcSlWxjjxxDO 不动产受理维修基金信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 更新情况
     */
    int updateBdcSlWxjjxx(BdcSlWxjjxxDO bdcSlWxjjxxDO);


}
