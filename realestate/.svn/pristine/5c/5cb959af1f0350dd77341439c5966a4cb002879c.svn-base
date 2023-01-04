package cn.gtmap.realestate.accept.ui.web.utils;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/12/18,1.0
 * @description 不动产字典
 */
@Controller
@RequestMapping("/bdczd")
public class BdcZdController extends BaseController {
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

    @ResponseBody
    @PostMapping("/sl")
    public Map<String, List<Map>> slzd(@RequestParam(value="zdmc", required = false) String zdmc){
        Map<String, List<Map>> slZdMap = new HashMap<>();
        try {
            if(StringUtils.isBlank(zdmc)){
                slZdMap = bdcSlZdFeignService.listBdcSlzd();
            }else{
                List<String> zdList = Lists.newArrayList(zdmc.split(","));
                for(String zd : zdList){
                    List<Map> zdxx = bdcSlZdFeignService.queryBdcSlzd(zd);
                    slZdMap.put(zd, zdxx);
                }
            }

        } catch (Exception e) {
            LOGGER.error("字典项服务获取失败");
        }
        return slZdMap;
    }

}
