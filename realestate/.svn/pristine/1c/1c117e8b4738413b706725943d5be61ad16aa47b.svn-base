package cn.gtmap.realestate.inquiry.ui.web.engine;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzBmdDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzBmdFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/2/28 16:48
 * @description 白名单台账
 */
@Controller
@RequestMapping("bdcBmd")
public class BdcGzBmdController extends BaseController {

    @Autowired
    BdcGzBmdFeignService bdcGzBmdFeignService;


    /**
     * @param czry 操作人员姓名
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 查询操作人员列表
     */
    @ResponseBody
    @GetMapping(value = "/listBmdByPageJson")
    public Object listBdcBmdByPageJson(@LayuiPageable Pageable pageable, String czry) {
        return addLayUiCode(bdcGzBmdFeignService.bdcBmdPage(pageable, czry));
    }


    /**
     * @param bmdDOList bmdDOList
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 删除人员信息
     */
    @ResponseBody
    @DeleteMapping
    public Object deleteBmd(@RequestBody List<BdcGzBmdDO> bmdDOList) {
        return bdcGzBmdFeignService.deleteBmd(bmdDOList);
    }

    /**
     * @param bdcGzBmdDO bdcGzBmdDO
     * @return 新增
     * @author <a href ="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 新增人员
     */
    @ResponseBody
    @PutMapping
    public Object insertBdcBmd(@RequestBody BdcGzBmdDO bdcGzBmdDO) {
        if (bdcGzBmdDO == null) {
            throw new AppException("保存的数据不能为空！");
        }

        return bdcGzBmdFeignService.insertBdcBmd(bdcGzBmdDO);
    }

    /**
     * @param bdcGzBmdDO bdcGzBmdDO
     * @return 修改的数据量
     * @author <a href ="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 修改白名单
     */
    @PostMapping
    @ResponseBody
    public Object updateBdcBmd(@RequestBody BdcGzBmdDO bdcGzBmdDO) {
        if (bdcGzBmdDO == null) {
            throw new AppException("保存的数据不能为空！");
        }

        return bdcGzBmdFeignService.updateBdcBmd(bdcGzBmdDO);
    }


}
