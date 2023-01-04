package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.BdcZhcxLogDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcGzltjXmxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcYbtjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.GzltjQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description 综合查询
 */
public interface BdcZhcxRestService {

    /**
     * 保存综合查询台账查询和打印操作日志
     * @param bdcZhcxLogDO
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/zhcxlog")
    void insertZhcxLog(@RequestBody BdcZhcxLogDO bdcZhcxLogDO);

    /**
     * 统计人员打印有房无房证明、权属证明、登记簿的工作量信息
     * @param gzltjQO 工作量统计QO对象
     * @return 打印统计信息
     * @author  <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/zhcxlog/count/ry/printxx")
    List<BdcGzltjXmxxDTO> countRyPrintxx(@RequestBody GzltjQO gzltjQO);

    /**
     * 统计人员综合查询量信息
     * @param gzltjQO 工作量统计QO对象
     * @return 打印统计信息
     * @author  <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/zhcxlog/count/ry/zhcxxx")
    List<BdcGzltjXmxxDTO> countRyZhcxxx(@RequestBody GzltjQO gzltjQO);

    /**
     * 统计部门打印有房无房证明、权属证明、登记簿的工作量信息
     * @param gzltjQO 工作量统计QO对象
     * @return 打印统计信息
     * @author  <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/zhcxlog/count/bm/printxx")
    List<BdcGzltjXmxxDTO> countBmPrintxx(@RequestBody GzltjQO gzltjQO);

    /**
     * 统计部门综合查询量信息
     * @param gzltjQO 工作量统计QO对象
     * @return 综合查询统计信息
     * @author  <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/zhcxlog/count/bm/zhcxxx")
    List<BdcGzltjXmxxDTO> countBmZhcxxx(@RequestBody GzltjQO gzltjQO);

    /**
     * 月报统计查询部门的查询量
     * @param bdcYbtjQO
     * @return 综合查询统计信息
     * @author  <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/zhcxlog/count/ybtj/zhcxxx")
    Integer countYbtjZhcx(@RequestBody BdcYbtjQO bdcYbtjQO);
}
