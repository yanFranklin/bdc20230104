package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcCqtjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCqTjQO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @Description: 超期统计Service
 */
public interface BdcCqtjService {

    /**
     * 查询超期台账
     * @param bdcCqTjQO 查询参数
     * @Date 2022/7/4
     * @author <a href="mailto:sunyuzhuang.cn">sunyuzhuang</a>
     */
    List<BdcCqtjDTO> bdcCqtjMx(BdcCqTjQO bdcCqTjQO);
    /**
     * 查询超期统计图表
     * @param bdcCqTjQO 查询参数
     * @Date 2022/7/4
     * @author <a href="mailto:sunyuzhuang.cn">sunyuzhuang</a>
     */
    Object bdcCqtjMxTb(BdcCqTjQO bdcCqTjQO);

    /**
     * 查询效能统计台账
     * @param bdcCqTjQO 查询参数
     * @Date 2022/7/4
     * @author <a href="mailto:sunyuzhuang.cn">sunyuzhuang</a>
     */
    List<BdcCqtjDTO> xntjmx(BdcCqTjQO bdcCqTjQO);

    /**
     * 查询效能统计图表
     * @param bdcCqTjQO 查询参数
     * @Date 2022/7/4
     * @author <a href="mailto:sunyuzhuang.cn">sunyuzhuang</a>
     */
    Object xntjCount(BdcCqTjQO bdcCqTjQO);
}
