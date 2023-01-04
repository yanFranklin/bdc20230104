package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcCqtjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCqTjQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcCqtjFeignService;
import cn.gtmap.realestate.inquiry.ui.util.PageUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/06/23/11:42
 * @Description: 超期统计
 */
@RestController
@RequestMapping("/bdccqtj")
public class BdcCqtjController extends BaseController {

    @Autowired
    BdcCqtjFeignService bdcCqtjFeignService;

    /**
     * 查询超期台账
     * @param bdcCqTjQO 查询参数
     * @Date 2022/7/4
     * @author <a href="mailto:sunyuzhuang.cn">sunyuzhuang</a>
     */
    @PostMapping("/cqtjmx")
    public Object cqtjmx(@RequestBody BdcCqTjQO bdcCqTjQO){
        List<BdcCqtjDTO> bdcCqtjDTOS = bdcCqtjFeignService.bdcCqtjMx(bdcCqTjQO);
        return super.addLayUiCode(PageUtils.listToPage(bdcCqtjDTOS, new PageRequest(bdcCqTjQO.getPage()-1, bdcCqTjQO.getSize(),null)));
    }
    /**
     * 查询超期统计图表
     * @param bdcCqTjQO 查询参数
     * @Date 2022/7/4
     * @author <a href="mailto:sunyuzhuang.cn">sunyuzhuang</a>
     */
    @PostMapping("/cqtjCount")
    public Object cqtjCount(@RequestBody BdcCqTjQO bdcCqTjQO){
        return bdcCqtjFeignService.bdcCqtjMxTb(bdcCqTjQO);
    }

    /**
     * 查询效能统计台账
     * @param bdcCqTjQO 查询参数
     * @Date 2022/7/4
     * @author <a href="mailto:sunyuzhuang.cn">sunyuzhuang</a>
     */
    @PostMapping("/xntjmx")
    public Object xntjmx(@RequestBody BdcCqTjQO bdcCqTjQO){
        List<BdcCqtjDTO> bdcCqtjDTOS = bdcCqtjFeignService.xntjmx(bdcCqTjQO);
        return super.addLayUiCode(PageUtils.listToPage(bdcCqtjDTOS, new PageRequest(bdcCqTjQO.getPage()-1, bdcCqTjQO.getSize(),null)));
    }

    /**
     * 查询效能统计图表
     * @param bdcCqTjQO 查询参数
     * @Date 2022/7/4
     * @author <a href="mailto:sunyuzhuang.cn">sunyuzhuang</a>
     */
    @PostMapping("/xntjCount")
    public Object xntjCount(@RequestBody BdcCqTjQO bdcCqTjQO){
        return bdcCqtjFeignService.xntjCount(bdcCqTjQO);
    }

}
