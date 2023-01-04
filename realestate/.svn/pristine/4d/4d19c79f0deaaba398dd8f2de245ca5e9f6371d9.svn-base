package cn.gtmap.realestate.accept.core.mapper;

import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/10/30
 * @description 不动产流水号数据mapper
 */
@Repository
public interface BdcLshMapper {
    /**
     * @param map ：beginDate 开始时间   endDate 结束时间  ywlx 业务类型
     * @return lsh 最大流水号
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 获取最大流水号 beginDate  endDate  ywlx
     */
    Integer getMaxLsh(Map map);

    /**
     * @param map ：lshid 流水号ID   lsh 流水号  cjsj 创建时间  wlx 业务类型
     * @return lsh 最大流水号
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 获取最大流水号 beginDate  endDate  ywlx
     */
    void insertLsh(Map map);
}
