package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.etl.web.main.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 获取字典信息rest
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-15 11:24
 **/
@Controller
@RequestMapping("/realestate-etl/rest/v1.0/bdczd")
public class ZdRestController extends BaseController {

    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    private BdcSlZdFeignService bdcSlZdFeignService;

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取下拉框数据
     */
    @ResponseBody
    @PostMapping("")
    public Map<String, List<Map>> initzd() {
        Map<String, List<Map>> zdMap = new HashMap<>();
        Map<String, List<Map>> slZdMap = new HashMap<>();
        try {
            zdMap = bdcZdFeignService.listBdcZd();
            slZdMap = bdcSlZdFeignService.listBdcSlzd();
        } catch (Exception e) {
            LOGGER.error("字典项服务获取失败");
        }
        zdMap.putAll(slZdMap);
        return zdMap;
    }
}
