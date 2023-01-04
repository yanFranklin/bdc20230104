package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFgyhsfDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlFghysfDTO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFgyhsfFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/10/22/10:37
 * @Description:
 */
@RestController
@RequestMapping("/slym/fgyhsf")
public class BdcSlFgyhsfController extends BaseController {
    @Autowired
    BdcSlFgyhsfFeignService bdcSlFgyhsfFeignService;

    /**
     * @param bdcSlFgyhsfDO 不动产受理房改优惠售房信息
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 新增不动产受理房屋信息
     */
    @PostMapping("/saveOrUpdate")
    public Integer saveOrUpdateBdcSlFgyhsf(@RequestBody BdcSlFgyhsfDO bdcSlFgyhsfDO){
        return bdcSlFgyhsfFeignService.saveOrUpdateBdcSlFgyhsf(bdcSlFgyhsfDO);
    }

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 根据工作流实例id获取不动产受理房屋信息
     */
    @GetMapping("/getFgyhsf")
    public BdcSlFgyhsfDO getBdcSlFgyhsf(@RequestParam(value="gzlslid") String gzlslid){
        return bdcSlFgyhsfFeignService.getBdcSlFgyhsf(gzlslid);
    }

    @GetMapping("/getBdcSlFghysfDTO")
    public BdcSlFghysfDTO getBdcSlFghysfDTO(@RequestParam(value="gzlslid") String gzlslid){
        return bdcSlFgyhsfFeignService.getBdcSlFghysfDTO(gzlslid);
    }
}
