package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcYjdhSfxxGxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcYjSfxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcYjSfxxQO;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/10
 * @description 不动产银行月结单号与受理编号关系服务
 */
public interface BdcYjdhSfxxGxService {

    /**
     * @param yjdh 月结单号
     * @return 收费信息ID集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据月结单号查询关联收费信息ID集合
     */
    List<String> queryYjSfxxidList(String yjdh);

    /**
     * 根据月结单号查询月结收费信息数据
     * @param yjdh 月结单号
     * @return 月结单号收费关系数据
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcYjdhSfxxGxDO> queryBdcYjdhSfxxGxByYjdh(String yjdh);

    /**
     * 生成月结收费信息关系数据
     * @param bdcYjdhSfxxGxDOList 不动产银行月结单号与收费信息DO集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcYjdhSfxxGxDO> generateYjSfxxGx(List<BdcYjdhSfxxGxDO> bdcYjdhSfxxGxDOList);

    /**
     * 根据月结收费信息QO查询月结收费信息
     * @param bdcYjSfxxQO  不动产月结收费信息DO
     * @return BdcYjSfxxDTO 不动产月结收费信息DTO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcYjSfxxDTO> listBdcYjSfxx(BdcYjSfxxQO bdcYjSfxxQO);

    /**
     * 校验收费信息是否已经下单
     * @param bdcYjdhSfxxGxDOList  银行月结单号与收费信息关系DO
     * @return key: 月结单号， value： 受理编号信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    Map<String, Set<String>> checkSfxxSfxd(List<BdcYjdhSfxxGxDO> bdcYjdhSfxxGxDOList);

    /**
     * @param sfxxid 收费信息ID
     * @return 月结单号信息
     * @author <a href="mailto:gaolining@gtmap.cn">yaoyi</a>
     * @description 根据收费信息查询月结单号内容
     */
    List<String> queryYjdhxxBySfxxid(String sfxxid);

    /**
     * 根据收费信息ID,获取已缴费的月结单号信息
     *
     * @param sfxxid 收费信息ID
     * @return 月结单号
     * @author <a href="mailto:gaolining@gtmap.cn">yaoyi</a>
     */
    String queryYjfYjdh(String sfxxid);

    /**
     * 根据月结缴费查询参数，生成月结单号
     * @param bdcYjSfxxQO 不动产月结收费信息DO
     * @return String  月结单号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    BdcYjSfxxDTO queryYjdh(BdcYjSfxxQO bdcYjSfxxQO);
}
