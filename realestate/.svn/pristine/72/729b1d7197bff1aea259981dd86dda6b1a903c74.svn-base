package cn.gtmap.realestate.natural.ui.web.rest;

import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.service.feign.natural.ZrzyGzyzFeignService;
import cn.gtmap.realestate.natural.ui.web.main.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/18
 * @description 规则验证
 */
@Controller
@RequestMapping("/rest/v1.0/bdcGzyz")
public class ZrzyGzYzController extends BaseController {
    @Autowired
    ZrzyGzyzFeignService zrzyGzyzFeignService;


    /**
     * @param bdcGzYzQO 规则验证查询实体
     * @return 验证结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 流程规则验证
     */
    @ResponseBody
    @PostMapping("")
    public List<Map<String, Object>> yzBdcgz(@RequestBody(required = false) BdcGzYzQO bdcGzYzQO) {
        LOGGER.info("受理页面传入规则验证参数{}", bdcGzYzQO);
        List<Map<String, Object>> maps = zrzyGzyzFeignService.yzBdcgz(bdcGzYzQO);
        LOGGER.info("受理页面传入规则验证参数{},验证结果：{}",bdcGzYzQO, maps);
        return maps;
    }

    /**
     * @param bdcGzYzQO 规则验证查询实体
     * @return 验证结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 其他规则验证（非流程）
     */
    @ResponseBody
    @PostMapping("/qtyz")
    public List<Map<String, Object>> qtyz(@RequestBody(required = false) BdcGzYzQO bdcGzYzQO) {
        return zrzyGzyzFeignService.qtyz(bdcGzYzQO);
    }
}
