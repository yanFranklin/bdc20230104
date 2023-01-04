package cn.gtmap.realestate.accept.core.mapper;

import cn.gtmap.realestate.common.core.domain.accept.BdcYjdhSfxxGxDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/10
 * @description 不动产银行月结单号与受理编号关系
 */
@Repository
public interface BdcYjdhSfxxGxMapper {

    /**
     * @param yjdh 月结单号
     * @return 收费信息ID集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据月结单号查询关联收费信息ID集合
     */
    List<String> queryYjSfxxidList(String yjdh);

    /**
     * 根据收费信息ID集合查询关系数据
     * @param sfxxidList 收费信息ID结合
     * @return 月结单号收费信息关系数据
     */
    List<BdcYjdhSfxxGxDO> queryYjSfxxGxBySfxxidList(@Param("sfxxidList") List<String> sfxxidList);
    /**
     * 根据收费信息ID查询月结单号信息
     * @param sfxxid 收费信息ID
     * @return 月结单号信息数据
     */
    List<String> queryYjdhxxBySfxxid(@Param("sfxxid") String sfxxid);

    /**
     * 根据收费信息ID,获取已缴费的月结单号信息
     * @param sfxxid 收费信息ID
     * @return 月结单号
     */
    String queryYjfYjdh(String sfxxid);
}
