package cn.gtmap.realestate.common.core.service.rest.inquiry;


import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcDpMxtjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcDpTjQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/04/07/14:16
 * @Description:
 */
public interface BdcDpCxRestService {
    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏今日登记类型数据接口
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcdp/listJrdjlx")
    Object listJrdjlx(@RequestBody BdcDpTjQO bdcDpTjQO);
    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏发证量数据接口
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcdp/listFzslTj")
    Object listFzslTj(@RequestBody BdcDpTjQO bdcDpTjQO);

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏抵押融资数据接口
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcdp/getTyrzTj")
    BdcDyrzTjDTO getTyrzTj(@RequestBody BdcDpTjQO bdcDpTjQO);

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取共享次数情况接口
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcdp/getZztj")
    BdcDpCxZzTjDTO getZzTj(@RequestBody BdcDpTjQO bdcDpTjQO);

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取共享次数情况接口
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcdp/getGxcsTj")
    Object getGxcsTj(@RequestBody BdcDpTjQO bdcDpTjQO);
    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取上报情况接口
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcdp/getSbqk")
    Object getSbqk(@RequestBody BdcDpTjQO bdcDpTjQO);
    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取规则检查情况接口
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcdp/getGzjcgk")
    Object getGzjcgk(@RequestBody BdcDpTjQO bdcDpTjQO);

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取本期得分情况接口
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcdp/getBqdfqk")
    Object getBqdfqk(@RequestBody BdcDpTjQO bdcDpTjQO);

    /**
     * @param paramJson 查询参数
     * @param pageable 分页信息
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏登记类型详细台账获取表格信息接口
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcdp/getDjlxtjMx")
    Page<BdcDpDjlxtjMxDTO> getDjlxtjMx(@LayuiPageable Pageable pageable, @RequestParam(name = "paramJson",required = false) String paramJson);

    /**
     * @param bdcDpMxtjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏登记类型详细台账获取图表信息接口
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcdp/getDjlxTb")
    List<BdcDpDjlxtjMxDTO> getDjlxTb(@RequestBody BdcDpMxtjQO bdcDpMxtjQO);

    /**
     * @param pageable 分页信息
     * @param paramJson 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏发证量详细台账获取表格信息接口
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcdp/getFzltjMx")
    Page<BdcDpFzltjMxDTO>  getFzltjMx(@LayuiPageable Pageable pageable,@RequestParam(name = "paramJson",required = false) String paramJson);

    /**
     * @param bdcDpMxtjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏发证量详细台账获取图表信息接口
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcdp/getFzltjTb")
    List<BdcDpFzltjMxDTO> getFzltjTb(@RequestBody BdcDpMxtjQO bdcDpMxtjQO);

    /**
     * @param pageable 分页信息
     * @param paramJson 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏抵押金额详细台账获取表格信息接口
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcdp/getDyjetjMx")
    Page<BdcDpDyjeMxDTO> getDyjetjMx(@LayuiPageable Pageable pageable,@RequestParam(name = "paramJson",required = false) String paramJson);

    /**
     * @param bdcDpTjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏抵押金额详细台账获取图表信息接口
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcdp/getDyjetjTb")
    Object getDyjetjTb(@RequestBody BdcDpTjQO bdcDpTjQO);

    /**
     * @param bdcDpTjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcdp/getZztjMx")
    Object getZztjMx(@RequestBody BdcDpTjQO bdcDpTjQO);

    /**
     * @param bdcDpTjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏住宅统计详细台账获取图表信息接口
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcdp/getZztjTb")
    Object getZztjTb(@RequestBody BdcDpTjQO bdcDpTjQO);

    /**
     * @param bdcDpTjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 根据”流程名称/登记小类“统计
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcdp/getLctjByDjlx")
    Object getLctjByDjlx(@RequestBody BdcDpTjQO bdcDpTjQO);

    /**
     * @param pageable 分页参数
     * @param paramJson 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取省级接入质量评分接口
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcdp/getQualityScore")
    List<SjzlpfResponseDTO> getQualityScore(@LayuiPageable Pageable pageable, @RequestParam(name = "paramJson",required = false) String paramJson);

    /**
     * @return 大屏查询统计配置信息
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取大屏查询统计配置信息
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/bdcdp/getBdcDpConfig")
    Object getBdcDpConfig();
}
