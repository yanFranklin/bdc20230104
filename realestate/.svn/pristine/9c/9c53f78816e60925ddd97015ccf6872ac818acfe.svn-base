package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcCqtjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCqTjQO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @Description: 超期统计台账
 */
public interface BdcCqtjRestService {
    /**
     * 查询超期台账
     * @param bdcCqTjQO 查询参数
     * @Date 2022/7/4
     * @author <a href="mailto:sunyuzhuang.cn">sunyuzhuang</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/cqtjmx")
    List<BdcCqtjDTO> bdcCqtjMx(@RequestBody BdcCqTjQO bdcCqTjQO);
    /**
     * 查询超期统计图表
     * @param bdcCqTjQO 查询参数
     * @Date 2022/7/4
     * @author <a href="mailto:sunyuzhuang.cn">sunyuzhuang</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/cxtjtb")
    Object bdcCqtjMxTb(@RequestBody BdcCqTjQO bdcCqTjQO);

    /**
     * 查询效能统计台账
     * @param bdcCqTjQO 查询参数
     * @Date 2022/7/4
     * @author <a href="mailto:sunyuzhuang.cn">sunyuzhuang</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/xntjmx")
    List<BdcCqtjDTO> xntjmx(@RequestBody BdcCqTjQO bdcCqTjQO);

    /**
     * 查询效能统计图表
     * @param bdcCqTjQO 查询参数
     * @Date 2022/7/4
     * @author <a href="mailto:sunyuzhuang.cn">sunyuzhuang</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/xntjCount")
    Object xntjCount(@RequestBody BdcCqTjQO bdcCqTjQO);

}
