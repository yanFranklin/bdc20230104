package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.dto.inquiry.xuancheng.BdcLqtjMxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.xuancheng.BdcLqtjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcLqtjQO;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0, 2022/11/21
 * @description 林权查询
 */
public interface BdcLqCxMapper {
    /**
     * 林权统计查询
     *
     * @param bdcLqtjQO
     * @return BdcLqtjDTO 林权数据
     */
    BdcLqtjDTO queryBdcLqxx(BdcLqtjQO bdcLqtjQO);

    /**
     * 林权统计查询
     *
     * @param bdcLqtjQO
     * @return BdcLqtjDTO 林权数据
     */
    BdcLqtjMxDTO queryBdcLqxxmx(BdcLqtjQO bdcLqtjQO);
}
