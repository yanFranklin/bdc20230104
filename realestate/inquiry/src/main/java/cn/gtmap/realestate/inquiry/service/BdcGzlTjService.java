package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcSlqkDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.GzltjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.SlqktjQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/5/15
 * @description 查询子系统：工作量统计
 */
public interface BdcGzlTjService {
    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param gzltjQO 查询条件
     * @return {Page} 工作量统计部门统计值
     * @description 工作量统计部门统计
     */
    List<Map> listBdcGzltj(GzltjQO gzltjQO);


    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param slqktjQO 查询条件
     * @return {Page} 受理情况    * @description 受理情况统计
     */
    List<Map> listSlqkCount(SlqktjQO slqktjQO);

    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param gzltjQO 查询条件
     * @return {Page} 工作量统计部门人员统计值
     * @description 工作量统计部门人员统计
     */
    List<Map> listBdcGzltjBmry(GzltjQO gzltjQO);


    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param gzltjQO 查询条件
     * @return {Page} 工作量统计部门人员统计值
     * @description 工作量统计办件量明细统计
     */
    List<Map> listGzltjMxBjl(GzltjQO gzltjQO);

    /**
     * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
     * @param gzltjQO 查询条件
     * @return {Page} 收件量明细统计
     * @description 办件量明细统计
     */
    List<Map> listGzltjMxSjl(GzltjQO gzltjQO);

    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param slqktjQO 查询条件
     * @return {Page} 工作量统计部门人员统计值
     * @description 工作量统计办件量明细统计
     */
    Page<BdcSlqkDTO> listSlqkMx(SlqktjQO slqktjQO, Pageable pageable);


    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param gzltjQO 查询条件
     * @return {Page} 工作量统计部门人员打印
     * @description 工作量统计部门人员打印
     */
    List<Map> listGzltjMxBjlPrint(GzltjQO gzltjQO);

    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param djxl 查询条件
     * @return {Page} djyylist
     * @description 获取djyylist,用于formselect
     */
    List<Map> djyyList(String djxl);


}
