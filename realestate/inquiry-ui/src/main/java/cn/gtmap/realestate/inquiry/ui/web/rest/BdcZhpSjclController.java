package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZhpSjclFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/16
 * @description
 */
@RestController
@RequestMapping(value = "/rest/v1.0")
public class BdcZhpSjclController extends BaseController {
    @Autowired
    private BdcZhpSjclFeignService bdcZhpSjclFeignService;

    /**
     * version 1.0
     *
     * @param json 队列信息
     * @description 综合屏队列信息处理
     * @date 2019/7/16
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @GetMapping("/dlxx")
    public void dlxx(@RequestBody String json) {
        bdcZhpSjclFeignService.dlxx(json);
    }

    /**
     * version 1.0
     *
     * @param json 传入信息
     * @description 综合屏叫号、暂停、解除暂停、评分信息处理
     * @date 2019/7/16
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @GetMapping("/xxcl")
    public void xxcl(@RequestBody String json) {
        bdcZhpSjclFeignService.xxcl(json);
    }
}
