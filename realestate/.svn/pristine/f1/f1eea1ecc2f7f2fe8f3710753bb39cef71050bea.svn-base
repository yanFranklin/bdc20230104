package cn.gtmap.realestate.accept.ui.web.rest;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.service.feign.building.AcceptBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwLjzFeginService;
import io.swagger.annotations.Api;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: realestate
 * @description: 逻辑幢楼盘信息资源
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-01-14 09:05
 **/
@RestController
@RequestMapping("/rest/v1.0/lpxx")
@Api(tags = "逻辑幢信息资源台账")
public class LpxxController extends BaseController {

    @Autowired
    FwLjzFeginService fwLjzFeginService;
    @Autowired
    AcceptBdcdyFeignService acceptBdcdyFeignService;

    @GetMapping("/{gzlslid}")
    @ResponseBody
    public Object getLpxx(@NotBlank(message = "获取楼盘信息缺失工作流实例id") @PathVariable(name = "gzlslid") String gzlslid) {
        return acceptBdcdyFeignService.listFwLjzFzHsxx(gzlslid,"");
    }
}
