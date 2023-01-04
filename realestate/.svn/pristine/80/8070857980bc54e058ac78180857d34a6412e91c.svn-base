package cn.gtmap.realestate.accept.core.mapper;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcGzltjXmxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcYbtjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.GzltjQO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/6/28
 * @description 综合查询日志
 */
@Repository
public interface BdcZhcxLogMapper {

    /**
     * 统计人员打印有房无房证明、权属证明、登记簿的工作量信息
     * @param gzltjQO 工作量统计QO对象
     * @return 打印统计信息
     * @author  <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcGzltjXmxxDTO> countZhcxRyPrintxx(GzltjQO gzltjQO);

    /**
     * 统计人员打印有房无房证明、权属证明、登记簿的工作量信息
     * @param gzltjQO 工作量统计QO对象
     * @return 查询量统计信息
     * @author  <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcGzltjXmxxDTO> countZhcxRyCxxx(GzltjQO gzltjQO);

    /**
     * 统计部门打印有房无房证明、权属证明、登记簿的工作量信息
     * @param gzltjQO 工作量统计QO对象
     * @return 打印统计信息
     * @author  <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcGzltjXmxxDTO> countZhcxBmPrintxx(GzltjQO gzltjQO);

    /**
     * 统计部门综合查询的工作量信息
     * @param gzltjQO 工作量统计QO对象
     * @return 综合查询统计信息
     * @author  <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    List<BdcGzltjXmxxDTO> countZhcxBmCxxx(GzltjQO gzltjQO);

    /**
     * 统计部门综合查询的工作量信息
     * @param bdcYbtjQO 工作量统计QO对象
     * @return 综合查询统计信息
     * @author  <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    Integer countYbtjZhcx(BdcYbtjQO bdcYbtjQO);

}
