package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcYhyjTjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcYhyjTjQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXtJgFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcYhyjTjFeignService;
import cn.gtmap.realestate.common.util.PageUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2019-03-01
 * @description 不动产月结银行统计服务
 */
@RestController
@RequestMapping(value = "/rest/v1.0/yhyjtj")
public class BdcYhyjTjController extends BaseController {

    @Autowired
    private BdcYhyjTjFeignService bdcYhyjTjFeignService;

    @Autowired
    private BdcXtJgFeignService bdcXtJgFeignService;

    /**
     * 分页查询银行月结统计分页数据
     *
     * @param pageable    分页对象
     * @param bdcYhyjTjQO 查询条件
     * @return 银行月结统计分页数据
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/page")
    public Object listBdcZszm(@LayuiPageable Pageable pageable, BdcYhyjTjQO bdcYhyjTjQO) {
        List<BdcYhyjTjDTO> bdcYhyjTjDTOList = this.bdcYhyjTjFeignService.listBdcYhyjTj(bdcYhyjTjQO);
        return super.addLayUiCode(PageUtils.listToPage(bdcYhyjTjDTOList, pageable));
    }

    /**
     * 获取按月结算 银行结构
     *
     * @return 银行数据
     */
    @GetMapping("/queryXtjg")
    public Object getXtjgData() {
        return this.bdcXtJgFeignService.listAyjsBdcXtJg();
    }

    /**
     * 银行月结统计数据集合
     *
     * @param bdcYhyjTjQO 不动产银行月结统计QO
     * @return 银行月结统计数据集合
     */
    @PostMapping(value = "/getExportData")
    public Object getExportData(@RequestBody BdcYhyjTjQO bdcYhyjTjQO) {
        List<BdcYhyjTjDTO> bdcYhyjTjDTOList = this.bdcYhyjTjFeignService.listBdcYhyjTj(bdcYhyjTjQO);
        return bdcYhyjTjDTOList;
    }

}