package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcYjSfDdxxDO;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/11/10
 * @description 不动产银行月结收费订单信息
 */
public interface BdcYjSfDdxxService {

    /**
     * @param bdcYjSfDdxxDO 不动产月结收费订单信息
     * @return 新增数量
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 新增月结收费订单信息
     */
    int insertYjSfDdxx(BdcYjSfDdxxDO bdcYjSfDdxxDO);

    /**
     * @param bdcYjSfDdxxDO 不动产月结收费订单信息
     * @return 新增数量
     * @author <a href="mailto:gaolining@gtmap.cn">yaoyi</a>
     * @description 根据月结单号更新月结收费订单信息
     */
    int updateYjSfDdxxByYjdh(BdcYjSfDdxxDO bdcYjSfDdxxDO);

    /**
     * 作废月结订单信息内容
     * @param yjdhList 月结订单号集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void zfYjSfDdxxByYjdh(List<String> yjdhList);

    /**
     * @param yjdh 月结单号
     * @return 不动产月结收费订单信息DO
     * @author <a href="mailto:gaolining@gtmap.cn">yaoyi</a>
     * @description 根据月结单号获取月结收费订单信息
     */
    BdcYjSfDdxxDO queryBdcYjSfDdxxByYjdh(String yjdh);

}
