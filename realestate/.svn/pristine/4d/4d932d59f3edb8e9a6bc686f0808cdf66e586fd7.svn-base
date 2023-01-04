package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.BdcZhcxLogDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcGzltjXmxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcYbtjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcYbtjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.GzltjQO;
import java.util.List;


/**
 * @description 不动产综合查询服务
 */
public interface BdcZhcxService {

    /**
     * @param bdcZhcxLogDO
     * @description 综合查询
     */
    void insertZhcxLog(BdcZhcxLogDO bdcZhcxLogDO);

    /**
     * 统计人员打印有房无房证明、权属证明、登记簿的工作量信息
     * @param gzltjQO 工作量统计QO对象
     * @return 打印统计信息
     * @author  <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcGzltjXmxxDTO> countRyPrintxx(GzltjQO gzltjQO);

    /**
     * 统计部门综合查询量信息
     * @param gzltjQO 工作量统计QO对象
     * @return 综合查询统计信息
     * @author  <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    List<BdcGzltjXmxxDTO> countRyZhcxxx(GzltjQO gzltjQO);

    /**
     * 统计部门打印有房无房证明、权属证明、登记簿的工作量信息
     * @param gzltjQO 工作量统计QO对象
     * @return 打印统计信息
     * @author  <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcGzltjXmxxDTO> countBmPrintxx(GzltjQO gzltjQO);

    /**
     * 统计部门综合查询量信息
     * @param gzltjQO 工作量统计QO对象
     * @return 综合查询统计信息
     * @author  <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcGzltjXmxxDTO> countBmZhcxxx(GzltjQO gzltjQO);

    /**
     * 统计部门综合查询量信息
     * @param bdcYbtjQO
     * @return 综合查询统计信息
     * @author  <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    Integer countYbtjZhcx(BdcYbtjQO bdcYbtjQO);

}
