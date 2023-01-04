package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcSlqkDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyucehng@gtmap.cn">chenyucehng</a>
 * @version 1.0, 2019/5/15
 * @description 查询子系统：工作量统计
 */
public interface BdcGzlTjRestService {
    /**
     * @author  <a href="mailto:chenyucehng@gtmap.cn">chenyucehng</a>
     * @param  gzltjParamJson 查询条件
     * @return {Page} 工作量统计分页数据
     * @description  工作量统计
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/gzltj")
    List<Map> listBdcGzltj(@RequestParam(name = "gzltjParamJson", required = false) String gzltjParamJson);

    /**
     * @author  <a href="mailto:chenyucehng@gtmap.cn">chenyucehng</a>
     * @param  gzltjParamJson 查询条件
     * @return {Page} 受理情况统计
     * @description  受理情况统计
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/slqktj/slqk")
    List<Map> listSlqkCount(@RequestParam(name = "gzltjParamJson", required = false) String gzltjParamJson);


    /**
     * @author  <a href="mailto:chenyucehng@gtmap.cn">chenyucehng</a>
     * @param  gzltjParamJson 查询条件
     * @return {Page} 工作量统计分页数据
     * @description  工作量统计
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/gzltjBmry")
    List<Map> listBdcGzltjBmry(@RequestParam(name = "gzltjParamJson", required = false) String gzltjParamJson);


    /**
     * @author  <a href="mailto:chenyucehng@gtmap.cn">chenyucehng</a>
     * @param  gzltjParamJson 查询条件
     * @return {Page} 工作量统计分页数据
     * @description  工作量统计
     * 兼容查询和打印，mapping加print
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/print/gzltjBmmx")
    List<Map> listGzltjMxBjl(@RequestParam(name = "gzltjParamJson", required = false) String gzltjParamJson);


    /**
     * @author  <a href="mailto:wutao2@gtmap.cn">wutao</a>
     * @param  gzltjParamJson 查询条件
     * @return {Page} 收件量统计分页数据
     * @description  收件量统计
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/print/gzltjSjlmx")
    List<Map> listGzltjMxSjl(@RequestParam(name = "gzltjParamJson", required = false) String gzltjParamJson);

    /**
     * @author  <a href="mailto:chenyucehng@gtmap.cn">chenyucehng</a>
     * @param  gzltjParamJson 查询条件
     * @return {Page} 受理情况统计明细
     * @description  受理情况统计明细
     * 兼容查询和打印，mapping加print
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/slqktj/slqk/mx")
    Page<BdcSlqkDTO> listSlqkMx(@RequestParam(name = "gzltjParamJson", required = false) String gzltjParamJson,
            Pageable pageable);

    /**
     * 辦件量打印
     * @param gzltjParamJson
     * @return
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/print/gzltjBmmxPrint")
    List<Map> listGzltjMxBjlPrint(@RequestParam(name = "gzltjParamJson", required = false) String gzltjParamJson);

    /**
     * djyy多选框
     * @param djxl
     * @return
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/slqktj/djyy/select")
    List<Map> djyyList (@RequestParam(name = "djxl", required = false) String djxl);

}
